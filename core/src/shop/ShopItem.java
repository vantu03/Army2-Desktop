package shop;

import CLib.mGraphics;
import coreLG.CCanvas;
import coreLG.TerrainMidlet;
import item.Item;
import item.MyItemIcon;
import java.util.Vector;
import model.CRes;
import model.Font;
import model.IAction;
import model.Language;
import model.PlayerInfo;
import network.Command;
import network.GameService;
import screen.CScreen;
import screen.PrepareScr;
import screen.TabScreen;

public class ShopItem extends TabScreen {
   Command cmdTroRa;
   Command cmdHoanTatGiaoDich;
   Command cmdBatDauMua;
   public static MyItemIcon ItemIcon;
   static Vector sellItem = new Vector();
   static final int maxItemBuy = 99;
   boolean isChooseAItem = false;
   static int numItemMua;
   static int tongTien;
   private boolean trans;
   byte money;
   final int XU = 0;
   final int LUONG = 0;
   String giaText;

   public void show(CScreen lastScreen) {
      super.show(lastScreen);
      this.xPaint = CScreen.w - ItemIcon.shopW >> 1;
      this.yPaint = (CCanvas.hieght - CScreen.cmdH) / 2 - 80;
      if (!CCanvas.isTouch) {
         this.hTabScreen = 157;
         this.n = 3;
      } else {
         this.n = 4;
         this.hTabScreen = 177;
      }

      this.title = Language.cuahang();
      this.getCommand();
   }

   public ShopItem() {
      this.nameCScreen = " ShopItem screen!";
      this.title = Language.cuahang();
      this.getCommand();
   }

   public void getCommand() {
      this.center = new Command(Language.buy(), new IAction() {
         public void perform() {
            if (!ShopItem.this.isChooseAItem && (ShopItem.getCurI().type == 36 || ShopItem.getCurI().type == 37)) {
               CCanvas.startYesNoDlg(Language.muavasudung(), new IAction() {
                  public void perform() {
                     ShopItem.this.isChooseAItem = true;
                     CCanvas.endDlg();
                     ShopItem.this.center.action.perform();
                  }
               }, new IAction() {
                  public void perform() {
                     ShopItem.this.isChooseAItem = false;
                     CCanvas.endDlg();
                  }
               });
            }

            if (!ShopItem.this.isChooseAItem) {
               if (!ShopItem.this.isChooseAItem && !ShopItem.getCurI().isFreeItem && !ShopItem.getCurI().isCannotBuy) {
                  if (ShopItem.getCurI().num < 99) {
                     ShopItem.this.isChooseAItem = true;
                     if (99 - ShopItem.getCurI().num < ShopItem.getCurI().nCurBuyPackage) {
                        ShopItem.numItemMua = 99 - ShopItem.getCurI().num;
                     } else {
                        ShopItem.numItemMua = ShopItem.getCurI().nCurBuyPackage;
                     }

                     ShopItem.checkTongTien(ShopItem.ItemIcon.select, ShopItem.numItemMua);
                  } else {
                     CCanvas.startOKDlg(Language.fullItem());
                  }
               }
            } else if (ShopItem.getCurI().price != -1 && ShopItem.getCurI().price2 != -1) {
               Vector<Command> menu = new Vector();
               Command xu = new Command(Language.muaXu(), new IAction() {
                  public void perform() {
                     ShopItem.this.buyAChooseItem((byte)0, ShopItem.getCurI().type, (byte)ShopItem.numItemMua);
                  }
               });
               Command luong = new Command(Language.muaLuong(), new IAction() {
                  public void perform() {
                     ShopItem.this.buyAChooseItem((byte)1, ShopItem.getCurI().type, (byte)ShopItem.numItemMua);
                  }
               });
               menu.addElement(xu);
               menu.addElement(luong);
               CCanvas.menu.startAt(menu, 2);
            } else if (ShopItem.getCurI().price != -1) {
               ShopItem.this.buyAChooseItem((byte)0, ShopItem.getCurI().type, (byte)ShopItem.numItemMua);
            } else if (ShopItem.getCurI().price2 != -1) {
               ShopItem.this.buyAChooseItem((byte)1, ShopItem.getCurI().type, (byte)ShopItem.numItemMua);
            }

         }
      });
      this.cmdTroRa = new Command(Language.back(), new IAction() {
         public void perform() {
            if (ShopItem.this.isChooseAItem) {
               ShopItem.this.isChooseAItem = false;
            } else {
               ShopItem.this.isClose = true;
            }

         }
      });
      this.right = this.cmdTroRa;
      this.cmdHoanTatGiaoDich = new Command(Language.dathang(), new IAction() {
         public void perform() {
         }
      });
   }

   public static void setItemVector(Vector itemShop) {
      sellItem = itemShop;
      int Num = sellItem.size();
      CRes.out("item size= " + Num);
      int NumPrepareItemChose = Num;
      int[] icon = new int[Num];

      for(int i = 0; i < Num; ++i) {
         icon[i] = getI(i).type;
      }

      if (!CCanvas.isTouch) {
         ItemIcon = new MyItemIcon(icon, 4, 6, 3);
      } else {
         ItemIcon = new MyItemIcon(icon, 4, 4, 2);
      }

      int[] prepareItemIcon = new int[Num];

      for(int i = 0; i < NumPrepareItemChose; ++i) {
         prepareItemIcon[i] = getI(i).type;
      }

      if (!CCanvas.isTouch) {
         PrepareScr.prepareScrItemIcon = new MyItemIcon(prepareItemIcon, 4, 5, 3);
      } else {
         PrepareScr.prepareScrItemIcon = new MyItemIcon(prepareItemIcon, 4, 4, 2);
      }

   }

   public static Item getI(int elementAt) {
      return (Item)sellItem.elementAt(elementAt);
   }

   public static Item getCurI() {
      return (Item)sellItem.elementAt(ItemIcon.select);
   }

   public static int[] getItemNum() {
      int[] itemNum = new int[sellItem.size()];

      for(int i = 0; i < sellItem.size(); ++i) {
         itemNum[i] = getI(i).num;
         itemNum[0] = -1;
         itemNum[1] = -1;
      }

      return itemNum;
   }

   public static void resetItemBuy() {
      for(int i = 0; i < sellItem.size(); ++i) {
         getI(i).numToBuy = 0;
      }

      tongTien = 0;
   }

   public void update() {
      super.update();
   }

   public void mainLoop() {
      super.mainLoop();
      ItemIcon.mainLoop();
   }

   public void buyAChooseItem(byte money, byte itemID, byte numBuy) {
      checkTongTien(itemID, numBuy);
      if (getI(itemID).price * numBuy <= TerrainMidlet.myInfo.xu && getI(itemID).price2 * numBuy <= TerrainMidlet.myInfo.luong) {
         Item var10000 = getCurI();
         var10000.numToBuy += numBuy;
         var10000 = getCurI();
         var10000.num += numBuy;
         if (tongTien > 0 && this.n > 0) {
            GameService.gI().requestBuyItem(money, itemID, numBuy);
            resetItemBuy();
            CCanvas.endDlg();
         }

         if (this.isChooseAItem) {
            this.isChooseAItem = false;
         }
      } else {
         CCanvas.startOKDlg(Language.kocotien());
      }

   }

   public static void checkTongTien(int curidChoose, int numCurChoose) {
      tongTien = 0;

      for(int i = 0; i < sellItem.size(); ++i) {
         int cur;
         if (i == curidChoose) {
            cur = numCurChoose * getI(i).price;
            if (cur == -1) {
               cur = numCurChoose * getI(i).price2;
            }

            tongTien += cur;
         } else if (getI(i).numToBuy > 0) {
            cur = getI(i).numToBuy * getI(i).price;
            if (cur == -1) {
               cur = numCurChoose * getI(i).price2;
            }

            tongTien += cur;
         }
      }

   }

   public static void checkItemWhenChose(int[] itemUse) {
      int i;
      for(i = 0; i < sellItem.size(); ++i) {
         getI(i).numUsed = 0;
      }

      for(i = 0; i < itemUse.length; ++i) {
         if (itemUse[i] > 0) {
            ++getI(itemUse[i]).numUsed;
         }
      }

   }

   public static int[] checkSetItem(int[] itemOut) {
      int[] itemUse = itemOut;
      CRes.out(" item itemUse4 =  " + itemOut[itemOut.length - 4]);
      CRes.out(" item itemUse3 =  " + itemOut[itemOut.length - 3]);
      CRes.out(" item itemUse2 =  " + itemOut[itemOut.length - 2]);
      CRes.out(" item itemUse1 =  " + itemOut[itemOut.length - 1]);
      if (getI(12).num > 0) {
         if (itemOut[itemOut.length - 4] == -1) {
            itemOut[itemOut.length - 4] = -2;
         }
      } else {
         itemOut[itemOut.length - 4] = -1;
      }

      if (getI(13).num > 0) {
         if (itemOut[itemOut.length - 3] == -1) {
            itemOut[itemOut.length - 3] = -2;
         }
      } else {
         itemOut[itemOut.length - 3] = -1;
      }

      if (getI(14).num > 0) {
         if (itemOut[itemOut.length - 2] == -1) {
            itemOut[itemOut.length - 2] = -2;
         }
      } else {
         itemOut[itemOut.length - 2] = -1;
      }

      if (getI(15).num > 0) {
         if (itemOut[itemOut.length - 1] == -1) {
            itemOut[itemOut.length - 1] = -2;
         }
      } else {
         itemOut[itemOut.length - 1] = -1;
      }

      for(int i = 0; i < itemUse.length; ++i) {
         if (itemUse[i] > 0 && getI(itemUse[i]).num < 1) {
            itemUse[i] = -2;
         }

         if (i == itemUse.length - 1 && getI(15).num < 1) {
            itemUse[i] = -1;
         } else if (i == itemUse.length - 2 && getI(14).num < 1) {
            itemUse[i] = -1;
         } else if (i == itemUse.length - 3 && getI(13).num < 1) {
            itemUse[i] = -1;
         } else if (i == itemUse.length - 4 && getI(12).num < 1) {
            itemUse[i] = -1;
         }
      }

      return itemUse;
   }

   public static void receiveAItemBuy(byte n, byte[] itemID, byte[] nAfterBuy, int moneyAfterBuy, int moneyAfterBuy2) {
      for(int i = 0; i < n; ++i) {
         getI(itemID[i]).num = nAfterBuy[i];
      }

      TerrainMidlet.myInfo.xu = moneyAfterBuy;
      TerrainMidlet.myInfo.luong = moneyAfterBuy2;
      CCanvas.startOKDlg(Language.thanks());
   }

   public void paint(mGraphics g) {
      super.paint(g);
      Font.normalYFont.drawString(g, "  ", 0, 0, 0, true);
      Font.normalYFont.drawString(g, "  ", 0, 0, 0, true);
      paintItem(ItemIcon, this.xPaint, this.yPaint, g);
      Font.borderFont.drawString(g, " ", this.xPaint, this.yPaint, 2, false);
      g.setColor(2509680);
      int dis = CCanvas.isTouch ? 20 : 0;
      paintTien(this.xPaint - 2, this.yPaint + 88 + dis, g);
      Font.borderFont.drawString(g, " ", this.xPaint, this.yPaint - 5, 2, false);
      this.paintDetail(g, dis, ItemIcon.getWidth(), ItemIcon.getHeight());
      Font.borderFont.drawString(g, " ", this.xPaint, this.yPaint + 2, 2, false);
      if (this.isChooseAItem && getCurI().type != 36 && getCurI().type != 37) {
         paintBuyBar(ItemIcon.select, CScreen.w - 140 >> 1, CScreen.h - 80 >> 1, g);
      }

      Font.borderFont.drawString(g, " ", this.xPaint, this.yPaint + 5, 2, false);
      painSeller(CScreen.w - 5, CScreen.h - 20, g);
      Font.borderFont.drawString(g, " ", this.xPaint, this.yPaint + 10, 2, false);
      this.paintSuper(g);
   }

   public static void paintItem(MyItemIcon MyIcon, int x, int y, mGraphics g) {
      MyIcon.paint(x, y + 25, g, true, getItemNum());
   }

   public void paintDetail(mGraphics g, int dis, int w, int h) {
      this.giaText = !getCurI().isFreeItem ? (getCurI().price != -1 ? getCurI().price + Language.xu() : "") + (getCurI().price2 != -1 ? (getCurI().price != -1 ? "-" : "") + getCurI().price2 + " " + Language.luong() : "") : Language.price() + ": " + Language.freeItem();
      g.fillRoundRect(this.xPaint - 2, this.yPaint + 105 + dis, w + 4, 46, 6, 7, false);
      int var10003 = this.xPaint + 5;
      Font.normalYFont.drawString(g, getI(ItemIcon.select).decription, var10003, this.yPaint + 107 + dis, 0);
      Font.normalYFont.drawString(g, this.giaText, this.xPaint + 4, this.yPaint + 121 + dis, 0);
      Font.normalYFont.drawString(g, getCurI().num > 0 ? Language.having() + ": " + getCurI().num + " " + Language.per() : "", this.xPaint + 4, this.yPaint + 135 + dis, 0);
   }

   public static void paintTileBar(byte type, int x, int y, mGraphics g) {
      paintBorderRect(g, y, 3, 147, "=====");
   }

   public static void paintTien(int x, int y, mGraphics g) {
      PlayerInfo m = TerrainMidlet.myInfo;
      String money = m.xu + Language.xu() + "-" + m.luong + Language.luong();
      Font.normalFont.drawString(g, money, CCanvas.width / 2, y + 2, 3);
   }

   public static void paintSoluong(int Select, int x, int y, mGraphics g) {
   }

   public static void painSeller(int x, int y, mGraphics g) {
   }

   public static void paintBuyBar(int Select, int x, int y, mGraphics g) {
      paintDefaultPopup(x - 5, y, 150, 75, g);
      Font.normalFont.drawString(g, Language.howMuch(), CCanvas.hw, y + 7, 2);
      getI(Select).drawThisItem(g, x + 20, y + 25);
      Font.normalFont.drawString(g, numItemMua + " " + Language.per(), x + 70, y + 25, 0);
      Font.normalFont.drawString(g, (getI(Select).price != -1 ? numItemMua * getI(Select).price + Language.xu() : "") + (getI(Select).price2 != -1 ? (getI(Select).price != -1 ? "/" : "") + numItemMua * getI(Select).price2 + " luong" : ""), CCanvas.hw, y + 52, 2);
      g.drawRegion(PrepareScr.imgReady[3], 0, 0, 13, 11, 4, x + 45 + CCanvas.gameTick % 3, y + 27, 0, false);
      g.drawRegion(PrepareScr.imgReady[3], 0, 0, 13, 11, 7, x + 115 - CCanvas.gameTick % 3, y + 27, 0, false);
   }

   public void onPointerPressed(int x, int y2, int index) {
      super.onPointerPressed(x, y2, index);
      if (ItemIcon != null) {
         ItemIcon.onPointerPressed(x, y2, index);
      }

   }

   public void onPointerReleased(int xRealse, int yRealse, int index) {
      this.trans = false;
      if (CCanvas.isPointer(0, 0, CCanvas.width, CCanvas.hieght - CScreen.cmdH, index)) {
         int X = CScreen.w - 140 >> 1;
         int Y = CScreen.h - 80 >> 1;
         if (!this.isChooseAItem) {
            if (ItemIcon != null) {
               ItemIcon.onPointerReleased(xRealse, yRealse, index);
            }

            if (CCanvas.isDoubleClick && this.center != null) {
               this.center.action.perform();
            }
         } else {
            if (!CCanvas.isPointer(X - 5, Y, 150, 75, index)) {
               this.isChooseAItem = false;
               return;
            }

            if (CCanvas.isPointer(X + 45, Y + 27, 40, 40, index)) {
               numItemMua -= getCurI().nCurBuyPackage;
               if (numItemMua < getCurI().nCurBuyPackage) {
                  numItemMua = getCurI().nCurBuyPackage;
               }

               checkTongTien(ItemIcon.select, numItemMua);
            }

            if (CCanvas.isPointer(X + 115, Y + 27, 40, 40, index)) {
               numItemMua += getCurI().nCurBuyPackage;
               if (numItemMua > 99 - getCurI().num) {
                  numItemMua = 99 - getCurI().num;
               }

               checkTongTien(ItemIcon.select, numItemMua);
            }
         }
      }

      super.onPointerReleased(xRealse, yRealse, index);
   }

   public void onPointerDragged(int xDrag, int yDrag, int index) {
      super.onPointerDragged(xDrag, yDrag, index);
      if (!this.isChooseAItem && ItemIcon != null) {
         ItemIcon.onPointerDragged(xDrag, yDrag, index);
      }

   }
}
