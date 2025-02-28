package shop;

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
import screen.CScreen;
import screen.GameScr;
import screen.TabScreen;

public class ShopEquipment extends TabScreen {
   int select;
   private Vector items = new Vector();
   private int W;
   private int nLine;
   private int hLine;
   private int wTab;
   private int wXp;
   private int wYp;
   private int cmtoYI;
   private int cmyI;
   private int cmdyI;
   private int cmvyI;
   private int cmyILim;
   private int cmtoYID;
   private int cmyID;
   private int cmdyID;
   private int cmvyID;
   private int cmyIDLim;
   Vector myShop;
   Position transText1;
   Position transText2;
   Equip eSelect;
   public String equipDetail;
   public String equipName;
   public String price;
   int size;
   public boolean expandDetail;
   private int xExpand;
   private int yExpand;
   public static int pa = 0;
   public static int paID = 0;
   public static boolean trans = false;
   int speed;

   public ShopEquipment() {
      this.W = CCanvas.width;
      this.nLine = 4;
      this.wTab = 40;
      this.wXp = 9;
      this.wYp = 5;
      this.myShop = new Vector();
      this.transText1 = new Position(0, 1);
      this.transText2 = new Position(0, 1);
      this.equipDetail = "";
      this.equipName = "";
      this.price = "";
      this.size = 0;
      this.speed = 1;
      this.nameCScreen = " ShopEquipment screen!";
      this.right = new Command(Language.back(), new IAction() {
         public void perform() {
            ShopEquipment.this.doClose();
         }
      });
      this.hTabScreen = 180;
      this.n = 4;
      this.title = Language.shoptrangbi();
      this.getW();
      this.xPaint = CCanvas.width / 2 - 85;
      this.yPaint = (CCanvas.hieght - CScreen.cmdH) / 2 - 85;
      this.nLine = 4;
      this.wXp = this.wBlank / 4;
      this.wYp = 5;
      this.wTab = 40;
   }

   public void show(CScreen lastScreen) {
      super.show(lastScreen);
      this.cmtoYI = 0;
      this.cmtoYID = 0;
      this.getCommand();
      this.getDetail();
   }

   public void paint(mGraphics g) {
      super.paint(g);
      this.paintEquip(g, this.xPaint, this.yPaint + 29, this.myShop, this.select);
      this.paintDetail(g, this.xPaint, this.yPaint + 103);
      this.paintSuper(g);
   }

   public void update() {
      super.update();
   }

   public void mainLoop() {
      super.mainLoop();
      this.itemCamera();
   }

   public void getCommand() {
      final Command xu = new Command(Language.muaXu(), new IAction() {
         public void perform() {
            CCanvas.startYesNoDlg(Language.bancochac() + ShopEquipment.this.eSelect.xu + Language.xu(), new IAction() {
               public void perform() {
                  CCanvas.startOKDlg(Language.pleaseWait());
                  GameService.gI().buy_sell_Equip((byte)0, (int[])null, (short)ShopEquipment.this.getCurrEq().index, (byte)0);
               }
            });
         }
      });
      final Command luong = new Command(Language.muaLuong(), new IAction() {
         public void perform() {
            CCanvas.startYesNoDlg(Language.bancochac() + ShopEquipment.this.eSelect.luong + Language.luong(), new IAction() {
               public void perform() {
                  CCanvas.startOKDlg(Language.pleaseWait());
                  GameService.gI().buy_sell_Equip((byte)0, (int[])null, (short)ShopEquipment.this.getCurrEq().index, (byte)1);
               }
            });
         }
      });
      Command menuLeft = new Command("Menu", new IAction() {
         public void perform() {
            Vector menu = new Vector();
            menu.addElement(xu);
            menu.addElement(luong);
            CCanvas.menu.startAt(menu, 0);
         }
      });
      if (this.eSelect == null) {
         this.left = xu;
      } else {
         if (this.eSelect.luong != -1 && this.eSelect.xu != -1) {
            this.left = menuLeft;
         } else if (this.eSelect.xu == -1 && this.eSelect.luong != -1) {
            this.left = luong;
         } else {
            this.left = xu;
         }

      }
   }

   private void itemCamera() {
      if (this.cmyI != this.cmtoYI) {
         this.cmvyI = this.cmtoYI - this.cmyI << 2;
         this.cmdyI += this.cmvyI;
         this.cmyI += this.cmdyI >> 4;
         this.cmdyI &= 15;
      }

      if (this.cmyI > this.cmyILim) {
         this.cmyI = this.cmyILim;
      }

      if (this.cmyI < 0) {
         this.cmyI = 0;
      }

      if (this.cmyID != this.cmtoYID) {
         this.cmvyID = this.cmtoYID - this.cmyID << 2;
         this.cmdyID += this.cmvyID;
         this.cmyID += this.cmdyID >> 4;
         this.cmdyID &= 15;
      }

      if (this.cmyID > this.cmyIDLim) {
         this.cmyID = this.cmyIDLim;
      }

      if (this.cmyID < 0) {
         this.cmyID = 0;
      }

   }

   public void doClose() {
      this.isClose = true;
   }

   public Equip getCurrEq() {
      Equip e = (Equip)this.myShop.elementAt(this.select);
      return e;
   }

   public void getMyShop() {
      this.myShop.removeAllElements();

      for(int i = 0; i < this.items.size(); ++i) {
         Equip e = (Equip)this.items.elementAt(i);
         if (e.glass == TerrainMidlet.myInfo.gun) {
            this.myShop.addElement(e);
         }
      }

   }

   public void setItems(Vector item) {
      this.select = 0;
      this.items.removeAllElements();
      this.items = item;
      this.getMyShop();
      this.size = this.myShop.size();
      this.hLine = this.myShop.size() / this.nLine;
      if (this.myShop.size() % this.nLine != 0) {
         ++this.hLine;
      }

      this.eSelect = (Equip)this.myShop.elementAt(this.select);
      this.equipDetail = this.eSelect.getStrShopDetail();
      this.equipName = this.eSelect.name;
      this.price = "Giờ: " + this.eSelect.xu + Language.xu() + " (" + this.eSelect.date + "ngày " + ")";
      this.cmyILim = this.hLine * this.wTab - 70;
      if (this.eSelect != null && this.eSelect.shopDetailNunStrs != null) {
         this.cmyIDLim = this.eSelect.shopDetailNunStrs.size() * this.wTab;
      }

   }

   private void paintEquip(mGraphics g, int X, int Y, Vector it, int select) {
      g.setColor(3832504);
      g.fillRoundRect(this.xPaint, this.yPaint + 23, this.wTabScreen, this.hTabScreen, 6, 6, false);
      int a = 0;
      int b = 0;

      g.setClip(X - 2, Y - 2, 170, 75);
      g.translate(0, -this.cmyI);
      g.setColor(16767817);

      for(int i = 0; i < it.size(); ++i) {
         Equip e = (Equip)it.elementAt(i);
         int xIcon = X + a * this.wTab + this.wXp;
         int yIcon = Y + b * this.wTab + this.wYp;
         if (i == select) {
            int xDraw = xIcon - (CCanvas.isTouch ? 12 : 2);
            int yDraw = yIcon - (CCanvas.isTouch ? 12 : 2);
            int width = CCanvas.isTouch ? 40 : 20;
            int height = CCanvas.isTouch ? 40 : 20;
            g.fillRect(xDraw, yDraw, width, height, true);
         }

         if (e.isSelect) {
            g.setColor(5612786);
            g.fillRect(xIcon, yIcon, 16, 16, true);
         }

         e.drawIcon(g, xIcon, yIcon, true);
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
      String myMoney = Language.money() + ": " + m.xu + Language.xu() + " - " + m.luong + Language.luong();

      int bb = Font.normalFont.getWidth(this.equipDetail);
      int ee = Font.normalFont.getWidth(this.price);
      int cc = this.transText1.x;
      int dd = this.transText2.x;
      Font.normalFont.drawString(g, myMoney, this.W / 2, Y - 1, 3);
      g.setColor(2378093);
      g.fillRoundRect(X, Y + 14, 170, 16, 6, 6, false);
      g.fillRoundRect(X, Y + 34, 170, 16, 6, 6, false);
      g.fillRoundRect(X, Y + 54, 170, 16, 6, 6, false);
      Font.normalGFont.drawString(g, this.equipName, X + 6, Y + 15, 0);
      Font.normalYFont.drawString(g, this.price, X + 6 + dd, Y + 35, 0);
      if (this.eSelect != null || this.eSelect.shopDetailNunStrs != null) {
         this.xExpand = X + 6 + cc + 100 + 50;
         this.yExpand = Y + 55;
         if (this.expandDetail) {
            g.setClip(X, Y + 54, 170, 32);
            g.setColor(2378093);
            g.fillRoundRect(X, Y + 54, 170, 32, 6, 6, false);
            g.translate(0, -this.cmyID);
            int index = 0;

            for(int i = 0; i < this.eSelect.shopDetailNunStrs.size(); ++i) {
               if (this.eSelect.shopDetailNunStrs.elementAt(i) != null && !((String)this.eSelect.shopDetailNunStrs.elementAt(i)).equals("")) {
                  Font.normalYFont.drawString(g, (String)this.eSelect.shopDetailNunStrs.elementAt(i), X + 6 + cc, Y + 55 + ITEM_HEIGHT * index++, 0, true);
               }
            }

            g.translate(0, -g.getTranslateY());
         } else if (this.eSelect.shopDetailNunStrs.elementAt(0) != null) {
            Font.normalYFont.drawString(g, (String)this.eSelect.shopDetailNunStrs.elementAt(0), X + 6 + cc, Y + 55, 0);
         }

         if (this.eSelect.shopDetailNunStrs.size() > 1) {
            g.drawImage(GameScr.imgArrowRed, this.xExpand, this.yExpand, 0, false);
         }

         g.setClip(0, 0, 10000, 10000);
         g.translate(0, -g.getTranslateY());
      }
   }

   public void getDetail() {
      if (this.select < this.size) {
         this.eSelect = (Equip)this.myShop.elementAt(this.select);
         this.equipDetail = this.eSelect.getStrShopDetail();
         String glass = null;
         if (this.eSelect.glass == 0) {
            glass = "Gunner";
         }

         if (this.eSelect.glass == 1) {
            glass = "Miss 6";
         }

         if (this.eSelect.glass == 2) {
            glass = "Electician";
         }

         if (this.eSelect.glass == 3) {
            glass = "KingKong";
         }

         if (this.eSelect.glass == 4) {
            glass = "Rocketer";
         }

         if (this.eSelect.glass == 5) {
            glass = "Granos";
         }

         if (this.eSelect.glass == 6) {
            glass = "Chicken";
         }

         if (this.eSelect.glass == 7) {
            glass = "Tarzan";
         }

         if (this.eSelect.glass == 8) {
            glass = "Apache";
         }

         if (this.eSelect.glass == 9) {
            glass = "Magenta";
         }

         this.equipName = this.eSelect.name + " (lvl " + this.eSelect.level + ")";
         String luong = (this.eSelect.xu != -1 ? "-" : "") + this.eSelect.luong + Language.luong();
         if (this.eSelect.luong == -1) {
            luong = "";
         }

         String xu = this.eSelect.xu + Language.xu();
         if (this.eSelect.xu == -1) {
            xu = "";
         }

         String ngay = this.eSelect.date >= 0 ? " (" + this.eSelect.date + Language.ngay() + ")" : "";
         this.price = Language.price() + ": " + xu + luong + ngay;
         this.getCommand();
         this.transText1.x = 0;
      }
   }

   public void onPointerPressed(int x, int y2, int index) {
      super.onPointerPressed(x, y2, index);
       if (CCanvas.keyPressed[2] || CCanvas.keyPressed[4] || CCanvas.keyPressed[6] || CCanvas.keyPressed[8]) {
            if (CCanvas.keyPressed[2]) {
                this.select -= nLine;
            }
            if (CCanvas.keyPressed[8]) {
                this.select += nLine;
            }
            if (CCanvas.keyPressed[4]) {
                this.select--;
            }
            if (CCanvas.keyPressed[6]) {
                this.select++;
            }
            if (select > this.myShop.size() - 1) {
                select = 0;
            }
            if (select < 0) {
                select = this.myShop.size() - 1;
            }
            cmtoYI = (select / nLine) * 40 - 20;
            if (cmtoYI > cmyILim) {
                cmtoYI = cmyILim;
            }
            if (cmtoYI < 0) {
                cmtoYI = 0;
            }
            CScreen.clearKey();
       }
   }

   public void onPointerReleased(int xRealse, int yRealse, int index) {
      super.onPointerReleased(xRealse, yRealse, index);
      trans = false;
      if (CCanvas.isPointer(this.xExpand, this.yExpand, 150, 150, index)) {
         this.expandDetail = !this.expandDetail;
         if (this.expandDetail) {
            this.hTabScreen = 200;
         } else {
            this.hTabScreen = 180;
         }

      } else {
         if (CCanvas.isPointer(this.xPaint, this.yPaint, 170, 120, index)) {
            int aa = (this.cmtoYI + yRealse - this.yPaint - 20) / this.wTab * this.nLine + (xRealse - this.xPaint - 8) / this.wTab;
            CRes.out("====>collum " + aa / 4);
            CRes.out("====>row " + (xRealse - this.xPaint - 8) / this.wTab);
            if (aa == this.select && this.left != null && CCanvas.isDoubleClick) {
               this.left.action.perform();
            }

            if (aa >= 0 && aa < this.myShop.size()) {
               this.select = aa;
               this.getDetail();
            }
         }

      }
   }

   public void onPointerDragged(int xDrag, int yDrag, int index) {
      super.onPointerDragged(xDrag, yDrag, index);
      if (!trans) {
         pa = this.cmyI;
         paID = this.cmyID;
         trans = true;
      }

      this.speed = 1;
      if (CCanvas.isPointer(this.W / 2 - 85, this.yPaint + 29, 170, 78, index)) {
         this.cmtoYI = pa + (CCanvas.pyFirst[index] - yDrag) * this.speed;
         if (this.cmtoYI < 0) {
            this.cmtoYI = 0;
         }

         if (this.cmtoYI > this.cmyILim) {
            this.cmtoYI = this.cmyILim;
         }

         CRes.out("cmtoYI = " + this.cmtoYI);
      }

      if (CCanvas.isPointer(this.W / 2 - 85, this.yPaint + 103, 170, 78, index)) {
         this.cmtoYID = paID + (CCanvas.pyFirst[index] - yDrag) * this.speed;
         if (this.cmtoYID < 0) {
            this.cmtoYID = 0;
         }

         if (this.cmtoYID > this.hTabScreen * 40 - 40) {
            this.cmtoYID = this.hTabScreen * 40 - 40;
         }
      }

   }
}
