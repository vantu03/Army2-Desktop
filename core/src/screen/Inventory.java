package screen;

import CLib.mGraphics;
import Equipment.Equip;
import coreLG.CCanvas;
import coreLG.TerrainMidlet;
import java.util.Vector;
import model.CRes;
import model.Font;
import model.IAction;
import model.Language;
import model.PlayerInfo;
import model.Position;
import network.Command;
import network.GameService;

public class Inventory extends TabScreen {
   int wTab;
   static int cmtoYI;
   static int cmyI;
   static int cmdyI;
   static int cmvyI;
   static int cmyILim;
   int size = 0;
   int nLine = 8;
   private int wXp;
   private int wYp;
   private Vector equips = new Vector();
   Command cmdXacnhan;
   Command menu;
   int dem;
   boolean isCombine = false;
   Position transText1 = new Position(0, 1);
   int combineSelect;
   public int select2;
   Equip eSelect;
   String equipDetail;
   String equipName;
   String date;
   int numCombine = 1;
   boolean isCombineNum;
   int hLine;
   int pa = 0;
   boolean trans = false;
   Command cmdCombine;

   public Inventory() {
      this.xPaint = CCanvas.width / 2 - 85;
      this.yPaint = (CCanvas.hieght - CScreen.cmdH) / 2 - 85;
      this.hTabScreen = 180;
      this.title = Language.ruongdo();
      this.getW();
      if (CCanvas.isTouch) {
         this.nLine = 4;
         this.wXp = this.wBlank / 4;
         this.wYp = 5;
         this.wTab = 40;
      } else {
         this.nLine = 8;
         this.wXp = 0;
         this.wYp = 0;
         this.wTab = 20;
      }

      this.cmdCombine = new Command(Language.select(), new IAction() {
         public void perform() {
            if (!Inventory.this.isCombineNum) {
               Inventory.this.doCombineSelect();
            } else {
               Inventory.this.isCombineNum = false;
            }

         }
      });
      this.nameCScreen = "Inventory screen!";
   }

   public void show(CScreen lastScreen) {
      super.show(lastScreen);
      this.init();
   }

   public void init() {
      this.select2 = 0;
      this.menuScroll = false;
      if (this.size == 0) {
         cmtoYI = 0;
      }

      this.size = EquipScreen.inventory.size();
      this.hLine = this.size / this.nLine;
      if (this.size % this.nLine != 0) {
         ++this.hLine;
      }

      this.select = 0;
      this.getCommand();
      if (this.size != 0) {
         this.getDetail();
      }
   }

   public void getCommand() {
      this.center = new Command(Language.select(), new IAction() {
         public void perform() {
         }
      });
      this.center = this.cmdCombine;
      new Command(Language.xacnhan(), new IAction() {
         public void perform() {
            Inventory.this.doCombine();
         }
      });
      this.menu = new Command("Menu", new IAction() {
         public void perform() {
            Vector<Command> menu = new Vector();
            menu.addElement(Inventory.this.cmdXacnhan);
            menu.addElement(new Command(Language.detail(), new IAction() {
               public void perform() {
                  if (!Inventory.this.getEquipSelect().isMaterial) {
                     CCanvas.startOKDlg(Inventory.this.getEquipSelect().getStrInvDetail());
                  } else if (Inventory.this.getEquipSelect().strDetail.startsWith(Language.fomula())) {
                     CCanvas.startOKDlg(Language.pleaseWait());
                     GameService.gI().getFomula((byte)Inventory.this.getEquipSelect().id, (byte)1, (byte)-1);
                  } else {
                     CCanvas.startOKDlg(Inventory.this.getEquipSelect().strDetail);
                  }

               }
            }));
            CCanvas.menu.startAt(menu, 0);
         }
      });
      this.cmdXacnhan = new Command(Language.use(), new IAction() {
         public void perform() {
            Inventory.this.doCombine();
         }
      });
      this.right = new Command(Language.back(), new IAction() {
         public void perform() {
            if (!Inventory.this.isCombineNum) {
               CCanvas.equipScreen.isClose = false;
               CCanvas.equipScreen.show(CCanvas.menuScr);
            } else {
               Inventory.this.isCombineNum = false;
               Equip e = Inventory.this.getEquipSelect();
               if (e != null) {
                  e.isSelect = false;
               }
            }

         }
      });
   }

   public void doUse() {
      if (this.size != 0) {
         int[] ind = new int[this.dem];
         int a = 0;
         this.size = EquipScreen.inventory.size();

         for(int i = 0; i < this.size; ++i) {
            Equip e = (Equip)EquipScreen.inventory.elementAt(i);
            if (e.isSelect) {
               ind[a] = e.dbKey;
               ++a;
            }
         }

      }
   }

   public Equip getEquipSelect() {
      Equip eS = null;
      this.size = EquipScreen.inventory.size();
      if (this.size > 0) {
         eS = (Equip)EquipScreen.inventory.elementAt(this.select2);
      }

      return eS;
   }

   public void unSelectEquip() {
      this.size = EquipScreen.inventory.size();

      for(int i = 0; i < this.size; ++i) {
         ((Equip)EquipScreen.inventory.elementAt(i)).isSelect = false;
         ((Equip)EquipScreen.inventory.elementAt(i)).numSelected = 0;
      }

      this.dem = 0;
   }

   public void combineYesNo(String info) {
      CCanvas.startYesNoDlg(info, new IAction() {
         public void perform() {
            GameService.gI().imbue((byte)1, (byte)-1, (int[])null, (byte[])null);
            Inventory.this.unSelectEquip();
            CCanvas.endDlg();
         }
      }, new IAction() {
         public void perform() {
            CCanvas.endDlg();
            Inventory.this.unSelectEquip();
         }
      });
   }

   public void doCombine() {
      this.size = EquipScreen.inventory.size();
      if (this.size > 0) {
         this.isCombine = false;
         this.size = EquipScreen.inventory.size();

         for(int i = 0; i < EquipScreen.inventory.size(); ++i) {
            Equip e = (Equip)EquipScreen.inventory.elementAt(i);
            if (e.isSelect) {
               ++this.dem;
            }
         }

         int[] ind = new int[this.dem];
         byte[] numSl = new byte[this.dem];
         int a = 0;

         int i;
         for(i = 0; i < this.size; ++i) {
            Equip e = (Equip)EquipScreen.inventory.elementAt(i);
            if (e.isSelect) {
               if (e.isMaterial) {
                  this.isCombine = true;
                  ind[a] = e.id;
               } else {
                  ind[a] = e.dbKey;
               }

               numSl[a] = (byte)e.numSelected;
               ++a;
               e.isSelect = false;
            }
         }

         if (this.isCombine) {
            GameService.gI().imbue((byte)0, (byte)this.dem, ind, numSl);
         } else {
            for(i = 0; i < ind.length; ++i) {
               GameService.gI().buy_sell_Equip((byte)1, ind, (short)-1, (byte)-1);
            }
         }

         this.dem = 0;
      }
   }

   public void doCombineSelect() {
      this.size = EquipScreen.inventory.size();
      if (this.size != 0) {
         Equip e = this.getEquipSelect();
         if (e != null) {
            if (e.num > 1) {
               e.numSelected = 1;
               this.isCombineNum = true;
               this.numCombine = 1;
               e.isSelect = true;
            } else {
               e.numSelected = 1;
               e.isSelect = !e.isSelect;
            }
         }

      }
   }

   public void paint(mGraphics g) {
      super.paint(g);
      g.setColor(3832504);
      g.fillRoundRect(CCanvas.width / 2 - 85, this.yPaint + 23, 170, 115, 6, 6, true);
      PlayerInfo m = TerrainMidlet.myInfo;
      String myMoney = Language.money() + ": " + m.xu + Language.xu() + "-" + m.luong + Language.luong();
      Font.normalFont.drawString(g, myMoney, CCanvas.width / 2, this.yPaint + 160, 3);
      Font.normalFont.drawString(g, this.equipName, CCanvas.width / 2, this.yPaint + 142, 3);
      Font.borderFont.drawString(g, " ", this.xPaint, this.yPaint + 12, 2, false);
      this.paintMaterial(g, CCanvas.width / 2 - 78, this.yPaint + 29);
      if (this.isCombineNum) {
         this.paintCombineSelect(this.combineSelect, CCanvas.width / 2, CCanvas.hieght / 2, g);
      }

      this.paintSuper(g);
   }

   public void paintMaterial(mGraphics g, int X, int Y) {
      int a = 0;
      int b = 0;
      g.setClip(X - 2, Y - 2, 170, 105);
      g.translate(0, -cmyI);
      g.setColor(16767817);
      this.size = EquipScreen.inventory.size();

      for(int i = 0; i < this.size; ++i) {
         Equip e = (Equip)EquipScreen.inventory.elementAt(i);
         int xIcon = X + a * this.wTab + this.wXp;
         int yIcon = Y + b * this.wTab + this.wYp;
         if (i == this.select2) {
            g.fillRect(xIcon - (CCanvas.isTouch ? 12 : 2), yIcon - (CCanvas.isTouch ? 12 : 2), CCanvas.isTouch ? 40 : 20, CCanvas.isTouch ? 40 : 20, true);
            if (!CCanvas.isTouch) {
               cmtoYI = yIcon - (Y + 55);
            }
         }

         if (e.isSelect) {
            g.setColor(5612786);
            g.fillRect(xIcon, yIcon, 16, 16, true);
         }

         e.drawIcon(g, xIcon, yIcon, true);
         if (!e.isMaterial) {
            for(int z = 0; z < 3 - e.slot; ++z) {
               if (i != this.select2) {
                  g.setColor(16377901);
               } else {
                  g.setColor(0);
               }

               g.fillRect(xIcon + z * 4, yIcon, 2, 2, true);
            }
         }

         ++a;
         if (a == this.nLine) {
            a = 0;
            ++b;
         }
      }

      g.setClip(0, 0, 1000, 1000);
      g.translate(0, -g.getTranslateY());
   }

   public void paintDetail(mGraphics g, int X, int Y) {
      PlayerInfo m = TerrainMidlet.myInfo;
      String myMoney = Language.money() + ": " + m.xu + Language.xu() + "-" + m.luong + Language.luong();
      int bb = Font.normalFont.getWidth(this.equipDetail);
      if (bb > 155) {
         CRes.transTextLimit(this.transText1, bb - 155);
      }

      int cc = this.transText1.x;
      Font.borderFont.drawString(g, " ", this.xPaint, this.yPaint + 2, 2, false);
      Font.normalFont.drawString(g, myMoney, CCanvas.width / 2, Y - 1, 3, false);
      g.setColor(2378093);
      g.fillRoundRect(X, Y + 14, 170, 16, 6, 6, false);
      g.fillRoundRect(X, Y + 34, 170, 16, 6, 6, false);
      g.fillRoundRect(X, Y + 54, 170, 16, 6, 6, false);
      Font.borderFont.drawString(g, " ", this.xPaint, this.yPaint + 2, 2, false);
      Font.normalGFont.drawString(g, this.equipName, X + 6, Y + 15, 0, false);
      Font.normalYFont.drawString(g, this.date, X + 6, Y + 35, 0, false);
      Font.borderFont.drawString(g, " ", this.xPaint, this.yPaint + 2, 2, false);
      Font.normalYFont.drawString(g, this.equipDetail, X + 6 + cc, Y + 55, 0, false);
   }

   public void requestServer(String info) {
      CCanvas.startYesNoDlg(info, new IAction() {
         public void perform() {
            GameService.gI().buy_sell_Equip((byte)2, (int[])null, (short)-1, (byte)-1);
            CCanvas.startWaitDlgWithoutCancel(Language.pleaseWait(), 19);
            Inventory.this.select2 = 0;
            Inventory.this.dem = 0;
         }
      }, new IAction() {
         public void perform() {
            CCanvas.endDlg();
            Inventory.this.select2 = 0;
         }
      });
   }

   public void getDetail() {
      this.eSelect = (Equip)EquipScreen.inventory.elementAt(this.select2);
      this.equipDetail = this.eSelect.getStrShopDetail();
      this.equipName = this.eSelect.name;
      this.date = Language.expr() + ": " + this.eSelect.date;
      this.transText1.x = 0;
   }

   public void itemCamera() {
      if (cmyI != cmtoYI) {
         cmvyI = cmtoYI - cmyI << 2;
         cmdyI += cmvyI;
         cmyI += cmdyI >> 4;
         cmdyI &= 15;
      }

      if (cmyI > cmyILim) {
         cmyI = cmyILim;
      }

      if (cmyI < 0) {
         cmyI = 0;
      }

   }

   public void paintCombineSelect(int Select, int x, int y, mGraphics g) {
      paintDefaultPopup(x - 75, y - 30, 150, 60, g);
      Font.normalFont.drawString(g, Language.nhapsoluong(), CCanvas.hw, y - 15, 2);
      Font.normalFont.drawString(g, this.numCombine + " " + Language.per(), x, y + 18 - 15, 2);
//      Font.borderFont.drawString(g, "Test", x, y, 2, false);
      g.drawRegion(PrepareScr.imgReady[3], 0, 0, 13, 11, 4, x - 40 + CCanvas.gameTick % 3, y + 20 - 15, 0, true);
      g.drawRegion(PrepareScr.imgReady[3], 0, 0, 13, 11, 7, x + 30 - CCanvas.gameTick % 3, y + 20 - 15, 0, true);
   }

   public void update() {
      super.update();
      cmyILim = this.hLine * this.wTab - 110;
      this.itemCamera();
      if (this.isCombineNum) {
         this.left = null;
      } else {
         this.left = this.menu;
      }

   }

   public void removeEquip(int dbKey, int nDelete) {
      this.size = EquipScreen.inventory.size();

      for(int i = 0; i < this.size; ++i) {
         Equip e = (Equip)EquipScreen.inventory.elementAt(i);
         if (!e.isMaterial) {
            if (e.dbKey == dbKey) {
               e.num -= nDelete;
               if (e.num <= 0) {
                  e.num = 0;
                  EquipScreen.inventory.removeElement(e);
               }

               return;
            }
         } else if (e.id == dbKey) {
            e.num -= nDelete;
            if (e.num <= 0) {
               e.num = 0;
               EquipScreen.inventory.removeElement(e);
            }

            return;
         }
      }

   }

   public Equip getEquip(int dbKey) {
      this.size = EquipScreen.inventory.size();

      for(int i = 0; i < this.size; ++i) {
         Equip e = (Equip)EquipScreen.inventory.elementAt(i);
         CRes.out("DB KEY E= " + e.dbKey);
         if (e.dbKey == dbKey) {
            return e;
         }
      }

      return null;
   }

   public void onPointerDragged(int xDragged, int yDragged, int index) {
      super.onPointerDragged(xDragged, yDragged, index);
      if (!CCanvas.isPointer(xDragged, yDragged, 150, 60, index)) {
         this.isCombineNum = false;
      }

      if (!this.isCombineNum) {
         if (!this.trans) {
            this.pa = cmyI;
            this.trans = true;
         }

         cmtoYI = this.pa + (CCanvas.pyFirst[index] - yDragged);
         if (cmtoYI < 0) {
            cmtoYI = 0;
         }

         if (cmtoYI > this.hLine * 40 - 40) {
            cmtoYI = this.hLine * 40 - 40;
         }

      }
   }

   public void onPointerPressed(int xScreen, int yScreen, int index) {
      super.onPointerPressed(xScreen, yScreen, index);
        if (this.isCombineNum) {
            if (CCanvas.keyPressed[2] || CCanvas.keyPressed[4] || CCanvas.keyPressed[6] || CCanvas.keyPressed[8]) {
                
                Equip e = this.getEquipSelect();
                if (CCanvas.keyPressed[4] || CCanvas.keyPressed[8]) {
                   if (e.num > 5) {
                      this.numCombine -= 5;
                   } else {
                      --this.numCombine;
                   }

                   if (this.numCombine <= 0) {
                      e.isSelect = false;
                      this.numCombine = 0;
                   }

                   e.numSelected = this.numCombine;
                }

                if (CCanvas.keyPressed[6] || CCanvas.keyPressed[2]) {
                   if (e.num > 5) {
                      this.numCombine += this.numCombine == 1 ? 4 : 5;
                      if (this.numCombine > e.num) {
                         this.numCombine -= 5;
                      }
                   } else if (this.numCombine >= e.num) {
                      this.numCombine = e.num;
                   } else {
                      ++this.numCombine;
                   }

                   e.numSelected = this.numCombine;
                   e.isSelect = true;
                }
                CScreen.clearKey();
            }
        } else 
      if (CCanvas.keyPressed[2] || CCanvas.keyPressed[4] || CCanvas.keyPressed[6] || CCanvas.keyPressed[8]) {
            if (CCanvas.keyPressed[2]) {
                this.select2 -= this.nLine;
            }
            if (CCanvas.keyPressed[8]) {
                this.select2 += this.nLine;
            }
            if (CCanvas.keyPressed[4]) {
                this.select2--;
            }
            if (CCanvas.keyPressed[6]) {
                this.select2++;
            }
            if (select2 > EquipScreen.inventory.size() - 1) {
                select2 = 0;
            }
            if (select2 < 0) {
                select2 = EquipScreen.inventory.size() - 1;
            }
            cmtoYI = (select2 / this.nLine) * 40 - 20;
//            if (cmtoYI > cm) {
//                cmtoYI = cmyILim;
//            }
            if (cmtoYI < 0) {
                cmtoYI = 0;
            }
            CScreen.clearKey();
       }
   }

   public void onPointerReleased(int xReleased, int yReleased, int index) {
      this.trans = false;
      super.onPointerReleased(xReleased, yReleased, index);
      if (!CCanvas.isPointer(xReleased, yReleased, 150, 60, index)) {
         this.isCombineNum = false;
      }

      if (this.isCombineNum) {
         if (!CCanvas.isPointer(xReleased - 75, yReleased - 30, 150, 60, index)) {
            this.isCombineNum = false;
         } else {
            Equip e = this.getEquipSelect();
            if (CCanvas.isPointer(CCanvas.width / 2 - 100, CCanvas.hieght / 2 - 100, 100, 200, index)) {
               if (e.num > 5) {
                  this.numCombine -= 5;
               } else {
                  --this.numCombine;
               }

               if (this.numCombine <= 0) {
                  e.isSelect = false;
                  this.numCombine = 0;
               }

               e.numSelected = this.numCombine;
            }

            if (CCanvas.isPointer(CCanvas.width / 2, CCanvas.hieght / 2 - 100, 100, 200, index)) {
               if (e.num > 5) {
                  this.numCombine += this.numCombine == 1 ? 4 : 5;
                  if (this.numCombine > e.num) {
                     this.numCombine -= 5;
                  }
               } else if (this.numCombine >= e.num) {
                  this.numCombine = e.num;
               } else {
                  ++this.numCombine;
               }

               e.numSelected = this.numCombine;
               e.isSelect = true;
            }
         }

      } else {
         if (CCanvas.isPointer(this.xPaint, this.yPaint, this.wTabScreen, this.hTabScreen, index)) {
            int paintX = CCanvas.width / 2 - 78;
            int paintY = this.yPaint + 29;
            if (CCanvas.isPointer(paintX, paintY, 160, 120, index)) {
               int aa = (cmtoYI + yReleased - paintY) / this.wTab * this.nLine + (xReleased - paintX - 8) / this.wTab;
               if (aa == -1) {
                  return;
               }

               if (aa == this.select2 && this.center != null) {
                  this.center.action.perform();
               }

               this.select2 = aa;
               if (this.select2 < 0) {
                  this.select2 = 0;
               }

               if (this.select2 > this.size - 1) {
                  this.select2 = this.size - 1;
               }

               this.getDetail();
            }
         }

      }
   }
}
