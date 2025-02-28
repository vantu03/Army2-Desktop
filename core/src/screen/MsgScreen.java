package screen;

import CLib.Image;
import CLib.mGraphics;
import coreLG.CCanvas;
import coreLG.TerrainMidlet;
import java.util.Vector;
import model.Font;
import model.IAction;
import model.Language;
import model.MsgInfo;
import model.MsgPopup;
import network.Command;
import network.GameService;

public class MsgScreen extends CScreen {
   public static Image imgBoard;
   public Vector list = new Vector();
   int selected;
   public int page;
   public int cmtoY;
   public int cmy;
   public int cmdy;
   public int cmvy;
   public int cmyLim;
   public int xL;
   public byte roomID;
   public CScreen lastScr;
   int pa = 0;
   boolean trans = false;

   public void show(CScreen lastScr) {
      this.nameCScreen = " MsgScreen screen!";
      this.lastScr = lastScr;
      CCanvas.arrPopups.removeAllElements();
      CCanvas.msgPopup.nMessage = 0;
      super.show();
   }

   public void moveCamera() {
      if (this.cmy != this.cmtoY) {
         this.cmvy = this.cmtoY - this.cmy << 2;
         this.cmdy += this.cmvy;
         this.cmy += this.cmdy >> 4;
         this.cmdy &= 15;
      }

   }

   public MsgScreen() {
      this.center = new Command(Language.see(), new IAction() {
         public void perform() {
            MsgScreen.this.doDetail();
         }
      });
      this.right = new Command(Language.close(), new IAction() {
         public void perform() {
            MsgScreen.this.doClose();
         }
      });
      this.left = new Command("Menu", new IAction() {
         public void perform() {
            MsgScreen.this.doShowMenu();
         }
      });
   }

   protected void doShowMenu() {
      Vector<Command> menu = new Vector();
      menu.addElement(new Command(Language.delete(), new IAction() {
         public void perform() {
            MsgScreen.this.doDelete();
         }
      }));
      menu.addElement(new Command(Language.deleteAll(), new IAction() {
         public void perform() {
            MsgScreen.this.doDeleteAll();
         }
      }));
      menu.addElement(new Command(Language.newMess(), new IAction() {
         public void perform() {
            CCanvas.startOKDlg(Language.toNewMess());
         }
      }));
      menu.addElement(new Command(Language.addFriend(), new IAction() {
         public void perform() {
            MsgScreen.this.doAddFriend();
         }
      }));
      CCanvas.menu.startAt(menu, 0);
   }

   protected void doClose() {
      this.lastScr.show();
   }

   protected void doDetail() {
      if (this.selected >= 0 && this.selected < this.list.size()) {
         MsgInfo m = (MsgInfo)this.list.elementAt(this.selected);
         IAction actionReply = new IAction() {
            public void perform() {
               MsgScreen.this.doSendMessage();
            }
         };
         CCanvas.msgdlg.setInfo(m.message, new Command(Language.reply(), actionReply), new Command("", actionReply), new Command(Language.close(), new IAction() {
            public void perform() {
               CCanvas.endDlg();
            }
         }));
         CCanvas.msgdlg.show();
      }

   }

   protected void doDeleteAll() {
      this.list.removeAllElements();
   }

   protected void doAddFriend() {
      if (this.selected >= 0 && this.selected < this.list.size()) {
         MsgInfo m = (MsgInfo)this.list.elementAt(this.selected);
         if (m.fromID != TerrainMidlet.myInfo.IDDB) {
            GameService.gI().addFriend(m.fromID);
            CCanvas.startWaitDlg(Language.adding() + "...");
         }
      }

   }

   protected void doDelete() {
      if (this.selected >= 0 && this.selected < this.list.size()) {
         this.list.removeElementAt(this.selected);
      }

      this.cmyLim = this.list.size() * 40 - (CCanvas.hieght - 100);
   }

   protected void doSendMessage() {
      if (this.selected >= 0 && this.selected < this.list.size()) {
         if (CCanvas.waitSendMessage > 0) {
            CCanvas.startOKDlg(Language.justSent());
         } else {
            MsgInfo p = (MsgInfo)this.list.elementAt(this.selected);
            CCanvas.inputDlg.setInfo(Language.sendTo() + p.fromName + ":", new IAction() {
               public void perform() {
                  MsgInfo p = (MsgInfo)MsgScreen.this.list.elementAt(MsgScreen.this.selected);
                  String text = CCanvas.inputDlg.tfInput.getText();
                  if (text.length() != 0) {
                     GameService.gI().chatTo(p.fromID, text);
                     p.isReply = true;
                     CCanvas.startOKDlg(Language.hasSent());
                     CCanvas.waitSendMessage = 100;
                  }
               }
            }, new IAction() {
               public void perform() {
                  CCanvas.endDlg();
               }
            }, 0);
            CCanvas.inputDlg.show();
         }
      }
   }

   public void paint(mGraphics g) {
      this.lastScr.paint(g);
      g.setClip(10, 20, CCanvas.width - 19, CCanvas.hieght - 59);
      g.setColor(13302783);
      g.fillRect(10, 20, CCanvas.width - 20, CCanvas.hieght - 60, false);
      g.setColor(5215093);
      g.drawRect(10, 20, CCanvas.width - 20, CCanvas.hieght - 60, false);
      g.translate(10, 20);
      g.translate(this.xL, 0);
      Font.bigFont.drawString(g, Language.MESS(), 10, 3, 0);
      g.setColor(1407674);
      g.fillRect(1, 25, CCanvas.width - 21, ITEM_HEIGHT, false);
      Font.normalYFont.drawString(g, Language.message(), 10, 28, 0);
      Font.normalYFont.drawString(g, Language.reply(), CCanvas.width - 45, 28, 2);
      if (this.list.size() == 0) {
         Font.borderFont.drawString(g, Language.noMess1(), CCanvas.hw - 10, 50, 2);
         Font.borderFont.drawString(g, Language.noMess2(), CCanvas.hw - 10, 75, 2);
         Font.borderFont.drawString(g, Language.noMess3(), CCanvas.hw - 10, 90, 2);
      }

      this.paintRichList(g);
      super.paint(g);
   }

   private void paintRichList(mGraphics g) {
      g.translate(0, ITEM_HEIGHT + 25);
      g.setClip(0, 0, CCanvas.width - 20, CCanvas.hieght - 25 - 21 - 40 - ITEM_HEIGHT);
      g.translate(0, -this.cmy);
      int y = 0;

      for(int i = 0; i < this.list.size(); ++i) {
         if (i == this.selected) {
            g.setColor(16765440);
            g.fillRect(2, y, CCanvas.width - 21 - 2, 38, false);
         }

         MsgInfo m = (MsgInfo)this.list.elementAt(i);
         g.drawImage(MsgPopup.imgMsg[m.isRead ? 1 : 0], 10, y + 4, 0, false);
         Font.borderFont.drawString(g, m.fromName, 30, y + 3, 0);
         Font.borderFont.drawString(g, Font.borderFont.splitFontBStrInLine(m.message, CCanvas.width - 80)[0], 10, y + ITEM_HEIGHT, 0);
         g.drawImage(PrepareScr.imgReady[m.isReply ? 0 : 1], CCanvas.width - 40, y + 20, 3, false);
         y += 40;
      }

   }

   public void setBoardList(Vector boardList) {
      this.list = boardList;
      this.cmyLim = boardList.size() * 40 - (CCanvas.hieght - 100);
   }

   public void update() {
      if (this.xL != 0) {
         this.xL += -this.xL >> 1;
      }

      if (this.xL == -1) {
         this.xL = 0;
      }

      this.moveCamera();
   }

   public void addMsg(MsgInfo msg1) {
      this.list.insertElementAt(msg1, 0);
      this.cmyLim = this.list.size() * 40 - (CCanvas.hieght - 100);
   }

   public void onPointerDragged(int x, int y, int index) {
      super.onPointerDragged(x, y, index);
      if (!this.trans) {
         this.pa = this.cmy;
         this.trans = true;
      }

      this.cmtoY = this.pa + (CCanvas.pyFirst[index] - y);
      if (this.cmtoY > this.cmyLim) {
         this.cmtoY = this.cmyLim;
      }

      if (this.cmtoY < 0) {
         this.cmtoY = 0;
      }

   }

   public void onPointerPressed(int xScreen, int yScreen, int index) {
      super.onPointerPressed(xScreen, yScreen, index);
   }

   public void onPointerReleased(int x, int y, int index) {
      super.onPointerReleased(x, y, index);
      this.trans = false;
      if (CCanvas.isPointer(0, 20, w, CCanvas.hieght - 60, index)) {
         int b = 20 + 2 * ITEM_HEIGHT;
         int aa = (this.cmtoY + y - b) / 40;
         if (aa == this.selected) {
            if (this.center != null) {
               if (CCanvas.isDoubleClick) {
                  this.center.action.perform();
               }
            } else if (this.left != null && CCanvas.isDoubleClick) {
               this.left.action.perform();
            }
         }

         this.selected = aa;
         if (this.selected < 0) {
            this.selected = 0;
         }

         if (this.selected >= this.list.size()) {
            this.selected = this.list.size() - 1;
         }
      }

   }

   public void onPointerHolder(int xScreen, int yScreen, int index) {
   }

   public void input() {
   }
}
