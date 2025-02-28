package screen;

import CLib.mGraphics;
import CLib.mImage;
import CLib.mSystem;
import com.teamobi.mobiarmy2.MainGame;
import coreLG.CCanvas;
import effect.Cloud;
import java.util.Vector;
import model.CRes;
import model.Font;
import model.IAction;
import model.Language;
import model.RoomInfo;
import network.Command;
import network.GameService;

public class RoomListScr2 extends CScreen {
   private static RoomListScr instance;
   public int cmtoY;
   public int cmy;
   public int cmdy;
   public int cmvy;
   public int cmyLim;
   int hTab = 0;
   public int selected;
   public static String[] roomLevelText = null;
   public String title = "Room";
   public static mImage imgRoomStat;
   public static mImage imgRoom;
   public static mImage imgTrs;
   public static mImage imgIcon;
   public static Command cmdFriendList;
   public static Command findRoom;
   public Vector<RoomInfo> roomList = new Vector();
   public boolean isEmptyRoom;
   int pa = 0;
   boolean trans = false;

   static {
      imgRoomStat = GameScr.imgRoomStat;
      imgTrs = GameScr.imgTrs;
      imgIcon = GameScr.imgIcon;
   }

   public static RoomListScr gI() {
      if (instance == null) {
         instance = new RoomListScr();
      }

      return instance;
   }

   public RoomListScr2() {
      this.nameCScreen = " => RoomListScr2";
      this.center = new Command(Language.enter(), new IAction() {
         public void perform() {
            RoomListScr2.this.doSelectRoom();
         }
      });
      findRoom = new Command(Language.findArea(), new IAction() {
         int roomID;
         int zoneID;

         public void perform() {
            CCanvas.inputDlg.setInfo(Language.nhapSoPhong(), new IAction() {
               public void perform() {
                  if (CCanvas.inputDlg.tfInput.getText() != null && !CCanvas.inputDlg.tfInput.getText().equals("")) {
                     roomID = Integer.parseInt(CCanvas.inputDlg.tfInput.getText());
                     CCanvas.inputDlg.setInfo(Language.nhapKhuVuc(), new IAction() {
                        public void perform() {
                           if (CCanvas.inputDlg.tfInput.getText() != null && !CCanvas.inputDlg.tfInput.getText().equals("")) {
                              zoneID = Integer.parseInt(CCanvas.inputDlg.tfInput.getText());
                              GameService.gI().requestEmptyRoom((byte)2, (byte)-1, String.valueOf(roomID * 1000 + zoneID));
                              CCanvas.endDlg();
                              CCanvas.startOKDlg(Language.pleaseWait());
                           }
                        }
                     }, new IAction() {
                        public void perform() {
                           CCanvas.endDlg();
                        }
                     }, 1);
                  }
               }
            }, new IAction() {
               public void perform() {
                  CCanvas.endDlg();
               }
            }, 1);
            CCanvas.inputDlg.show();
         }
      });
      this.left = new Command("Menu", new IAction() {
         public void perform() {
            Vector<Command> menu = new Vector();
            menu.addElement(new Command(Language.update(), new IAction() {
               public void perform() {
                  RoomListScr2.this.doUpdate();
               }
            }));
            menu.addElement(RoomListScr2.findRoom);
            CCanvas.menu.startAt(menu, 0);
         }
      });
      this.right = new Command(Language.close(), new IAction() {
         public void perform() {
            CCanvas.curScr = null;
            CCanvas.menuScr.show();
         }
      });
   }

   private void doUpdate() {
      GameService.gI().requestEmptyRoom((byte)0, (byte)-1, (String)null);
      CCanvas.startWaitDlg(Language.pleaseWait());
   }

   protected void doSelectRoom() {
      if (this.selected != -1) {
         RoomInfo info = (RoomInfo)this.roomList.elementAt(this.selected);
         if (info.boardID == -1) {
            GameService.gI().requestEmptyRoom((byte)1, info.lv, (String)null);
         }

         BoardListScr.boardName = Language.area() + " " + info.boardID;
         PrepareScr.currentRoom = info.id;
         GameService.gI().joinBoard(info.id, info.boardID, "");
         CCanvas.startWaitDlgWithoutCancel(Language.pleaseWait(), mSystem.currentTimeMillis() + 10000L, new IAction() {
            public void perform() {
               CCanvas.startOKDlg(Language.notReady());
            }
         });
      }
   }

   public void show() {
      super.show();
   }

   public void paint(mGraphics g) {
      paintDefaultBg(g);
      Cloud.paintCloud(g);

      for(int i = 0; i <= CCanvas.width; i += 32) {
         g.drawImage(PrepareScr.imgBack, i, CCanvas.hieght - 62, 0, false);
      }

      this.paintRoomList(g);
      super.paint(g);
   }

   public void changeName(int id, String name) {
      RoomInfo roomInfo = (RoomInfo)this.roomList.elementAt(id);
      roomInfo.name = name;
   }

   private void paintRoomList(mGraphics g) {
      if (CCanvas.isTouch) {
         g.translate(0, 6);
      }

      g.translate(0, -this.cmy);
      int y = 0;
      int tam = CCanvas.isTouch ? 10 : 0;

      for(int i = 0; i < this.roomList.size(); ++i) {
         RoomInfo roomInfo = (RoomInfo)this.roomList.elementAt(i);
         if (i == this.selected && roomInfo.id != -1) {
            g.setColor(16767817);
            g.fillRect(1, y + 3 - tam, CCanvas.width - 2, ITEM_HEIGHT + 2 * tam, false);
         }

         int a;
         if (roomInfo.id != -1) {
            try {
               a = imgIcon.image.getHeight() / 14 - roomInfo.lv;
               g.drawRegion(imgIcon, 0, 14 * a, 12, 14, 0, 10, y + 6, 0, false);
            } catch (Exception var7) {
               g.drawRegion(imgIcon, 0, 0 * roomInfo.lv, 12, 14, 0, 10, y + 6, 0, false);
            }

            if (CRes.isNullOrEmpty(roomInfo.name)) {
               Font.borderFont.drawString(g, Language.room() + " " + roomInfo.id, 30, y + 5, 0);
            } else {
               Font.borderFont.drawString(g, roomInfo.name, 30, y + 5, 0);
            }

            if (roomInfo.boardID != -1) {
               Font.borderFont.drawString(g, roomInfo.playerMax, CCanvas.width - 5, y + 5, 1);
            }

            if (roomInfo.money != 0) {
               Font.borderFont.drawString(g, roomInfo.money + " " + Language.xu(), CCanvas.width - 35, y + 5, 1);
            }

            y += CCanvas.isTouch ? 40 : ITEM_HEIGHT;
         } else {
            if (!CCanvas.isTouch) {
               for(a = 0; a <= CCanvas.width / imgTrs.image.getWidth(); ++a) {
                  g.drawImage(imgTrs, a * imgTrs.image.getWidth(), y + 2, 0, false);
               }
            }

            Font.bigFont.drawString(g, roomInfo.name.toUpperCase(), 10, y + 3, 0);
            y += CCanvas.isTouch ? 43 : ITEM_HEIGHT + 3;
         }
      }

   }

   public void moveCamera() {
      if (this.cmy != this.cmtoY) {
         this.cmvy = this.cmtoY - this.cmy << 2;
         this.cmdy += this.cmvy;
         this.cmy += this.cmdy >> 4;
         this.cmdy &= 15;
      }

      if (Math.abs(this.cmtoY - this.cmy) < 15 && this.cmy < 0) {
         this.cmtoY = 0;
      }

      if (Math.abs(this.cmtoY - this.cmy) < 10 && this.cmy > this.cmyLim) {
         this.cmtoY = this.cmyLim;
      }

   }

   public void update() {
      super.update();
      Cloud.updateCloud();
   }

   public void mainLoop() {
      super.mainLoop();
      this.moveCamera();
   }

   public void setRoomList(Vector roomList) {
      this.roomList = roomList;
      this.setCam();
   }

   private void setCam() {
      this.cmy = this.cmtoY = 0;
      this.selected = 1;
      int tam = CCanvas.isTouch ? 40 : ITEM_HEIGHT;
      this.cmyLim = this.roomList.size() * tam - (CCanvas.hieght - 70 - this.hTab);
      if (this.cmyLim < 0) {
         this.cmyLim = 0;
      }

   }

   public static void quickSort(Vector roomList) {
      recQuickSort(roomList, 0, roomList.size() - 1);
   }

   private static void recQuickSort(Vector actors, int left, int right) {
      if (right - left > 0) {
         int pivot = ((RoomInfo)actors.elementAt(right)).lv;
         int partition = partitionIt(actors, left, right, pivot);
         recQuickSort(actors, left, partition - 1);
         recQuickSort(actors, partition + 1, right);
      }
   }

   private static int partitionIt(Vector actors, int left, int right, int pivot) {
      int leftPtr = left - 1;
      int rightPtr = right;

      while(true) {
         do {
            ++leftPtr;
         } while(((RoomInfo)actors.elementAt(leftPtr)).lv < pivot);

         while(rightPtr > 0) {
            --rightPtr;
            if (((RoomInfo)actors.elementAt(rightPtr)).lv <= pivot) {
               break;
            }
         }

         if (leftPtr >= rightPtr) {
            swap(actors, leftPtr, right);
            return leftPtr;
         }

         swap(actors, leftPtr, rightPtr);
      }
   }

   private static void swap(Vector actors, int dex1, int dex2) {
      Object temp = actors.elementAt(dex2);
      actors.setElementAt(actors.elementAt(dex1), dex2);
      actors.setElementAt(temp, dex1);
   }

   public void onPointerPressed(int xPress, int yPress, int index) {
      super.onPointerPressed(xPress, yPress, index);
   }

   public void onPointerReleased(int xReleased, int yReleased, int index) {
      super.onPointerReleased(xReleased, yReleased, index);
      this.trans = false;
      int b = 5;
      int tam = CCanvas.isTouch ? 40 : ITEM_HEIGHT;
      int aa = (this.cmtoY + yReleased - b) / tam;
      if (!MainGame.touchDrag && CCanvas.isPointer(0, 0, w, CCanvas.hieght - cmdH, index)) {
         if (aa == this.selected) {
            if (this.center != null) {
               if (CCanvas.isDoubleClick) {
                  this.center.action.perform();
               }
            } else if (this.left != null && CCanvas.isDoubleClick) {
               this.left.action.perform();
            }
         }

         if (aa >= 0 && aa < this.roomList.size()) {
            this.selected = aa;
         }
      }

   }

   public void onPointerDragged(int xDrag, int yDrag, int index) {
      super.onPointerDragged(xDrag, yDrag, index);
      if (!this.trans) {
         this.pa = this.cmy;
         this.trans = true;
      }

      this.cmtoY = this.pa + (CCanvas.pyFirst[index] - yDrag);
      if (this.cmtoY < 0) {
         this.cmtoY = 0;
      }

      if (this.cmtoY > this.cmyLim) {
         this.cmtoY = this.cmyLim;
      }

   }
}
