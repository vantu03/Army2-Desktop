package screen;

import CLib.mGraphics;
import coreLG.CCanvas;
import coreLG.TerrainMidlet;
import effect.Explosion;
import java.util.Vector;
import model.CTime;
import model.Font;
import model.IAction;
import model.Language;
import model.LuckyGift;
import model.PlayerInfo;
import network.Command;
import network.GameService;
import player.Boss;

public class LuckyGifrScreen extends CScreen {
   public static String[] info;
   private LuckyGift[] gifts = new LuckyGift[12];
   boolean showAll;
   public int num = 12;
   public static CTime time;
   public int[] giftDelete;
   int count;
   public int wLine;
   public int hLine;
   public int disW;
   public int x;
   public int y;
   int xSelect;
   int ySelect;
   public int select;
   public boolean isShow;
   int dem;
   boolean isDem;

   public LuckyGifrScreen() {
      this.giftDelete = new int[this.num];
      this.dem = 0;
      this.nameCScreen = " LuckyGifrScreen screen!";
      this.disW = CCanvas.width < 240 ? 40 : 50;
      this.wLine = 4;
      this.hLine = 3;
      this.x = (CCanvas.width - this.disW * this.wLine) / 2 + this.disW / 2;
      this.y = (CCanvas.width > CCanvas.hieght ? 40 : 70) + this.disW / 2;
      if (CCanvas.width < 200) {
         this.y = 25 + this.disW / 2;
      }

   }

   public void show() {
      GameScr.exs = new Vector();
      this.giftDelete = new int[this.num];
      this.count = 0;
      this.init();
      super.show();
   }

   private void init() {
      if (this.isShow) {
         this.right = new Command(Language.exit(), new IAction() {
            public void perform() {
               if (LuckyGifrScreen.this.isShow) {
                  if (CScreen.lastSCreen != null) {
                     CScreen.lastSCreen.show();
                  } else {
                     CCanvas.roomListScr2.show();
                  }
               } else {
                  GameService.gI().luckGift((byte)-2);
               }

            }
         });
         this.left = null;
         this.center = null;
      } else {
         if (this.gifts != null) {
            for(int i = 0; i < this.gifts.length; ++i) {
               if (this.gifts[i] != null) {
                  this.gifts[i].isShow = false;
                  this.gifts[i].isWait = false;
               }
            }
         }

         this.center = new Command(Language.select(), new IAction() {
            public void perform() {
               LuckyGifrScreen.this.isDem = true;
               if (LuckyGifrScreen.this.dem == 0) {
                  if (LuckyGifrScreen.this.giftDelete[LuckyGifrScreen.this.select] != -1) {
                     LuckyGifrScreen.this.giftDelete[LuckyGifrScreen.this.select] = -1;
                     LuckyGifrScreen.this.gifts[LuckyGifrScreen.this.select] = new LuckyGift();
                     LuckyGifrScreen.this.gifts[LuckyGifrScreen.this.select].isWait = true;
                     GameService.gI().luckGift((byte)LuckyGifrScreen.this.select);
                  }

                  new Explosion(LuckyGifrScreen.this.xSelect, LuckyGifrScreen.this.ySelect, (byte)1);
                  ++LuckyGifrScreen.this.count;
               }

            }
         });
         this.left = new Command("Xong", new IAction() {
            public void perform() {
               GameService.gI().luckGift((byte)-2);
               LuckyGifrScreen.this.left = null;
               LuckyGifrScreen.this.center = null;
            }
         });
         this.right = null;
      }

   }

   public void paint(mGraphics g) {
      paintDefaultBg(g);

      int i;
      for(i = 0; i < info.length; ++i) {
         Font.normalFont.drawString(g, info[i], CCanvas.width / 2, 5 + i * 20, 3);
      }

      i = 0;
      int j = 0;
      if (time != null) {
         time.paint(g);
      }

      int X;
      for(int a = 0; a < this.num; ++a) {
         X = this.x + i * this.disW;
         int Y = this.y + j * this.disW;
         if (a == this.select && !this.isShow) {
            this.xSelect = X;
            this.ySelect = Y;
            g.setColor(3374591);
            g.fillRect(X - 2 - Boss.gift_1.image.getWidth() / 2, Y - 2 - Boss.gift_1.image.getWidth() / 2, Boss.gift_1.image.getWidth() + 4, Boss.gift_1.image.getWidth() + 4, false);
         }

         if (this.gifts[a] != null) {
            this.gifts[a].paint(g, X, Y);
         } else {
            g.drawImage(Boss.gift_1, X, Y, 3, false);
         }

         ++i;
         if (i == this.wLine) {
            ++j;
            i = 0;
         }
      }

      PlayerInfo myInfo = TerrainMidlet.myInfo;
      Font.borderFont.drawString(g, myInfo.xu + " " + Language.xu() + " - " + myInfo.luong + " " + Language.luong(), 5, CCanvas.hieght - cmdH - ITEM_HEIGHT - 20, 0);

      for(X = 0; X < GameScr.exs.size(); ++X) {
         ((Explosion)GameScr.exs.elementAt(X)).paint(g);
      }

      super.paint(g);
   }

   public void update() {
      if (this.isDem) {
         ++this.dem;
         if (this.dem == 20) {
            this.dem = 0;
            this.isDem = false;
         }
      }

      if (time != null) {
         time.update();
         if (CTime.seconds < 0) {
            time = null;
         }
      }

      int i;
      for(i = 0; i < 12; ++i) {
         if (this.gifts[i] != null) {
            this.gifts[i].update();
         }
      }

      for(i = 0; i < GameScr.exs.size(); ++i) {
         ((Explosion)GameScr.exs.elementAt(i)).update();
      }

      super.update();
   }

   public void setGiftByItemID(LuckyGift luckyGift) {
      this.gifts[luckyGift.id] = luckyGift;
   }

   public LuckyGift getGiftByItemID(int id) {
      for(int i = 0; i < this.gifts.length; ++i) {
         if (this.gifts[i] != null && this.gifts[i].id == id) {
            return this.gifts[i];
         }
      }

      return null;
   }

   public void onPointerDragged(int xDrag, int yDrag, int index) {
      super.onPointerDragged(xDrag, yDrag, index);
   }

   public void onPointerPressed(int xScreen, int yScreen, int index) {
      super.onPointerPressed(xScreen, yScreen, index);
   }

   public void onPointerReleased(int xRealse, int yRealse, int index) {
      super.onPointerReleased(xRealse, yRealse, index);
      int aa = (CCanvas.pY[index] - this.y + this.disW / 2) / this.disW * this.wLine + (CCanvas.pX[index] - this.x + this.disW / 2) / this.disW;
      if (aa != -1) {
         if (aa == this.select && this.center != null && CCanvas.isDoubleClick) {
            this.center.action.perform();
         }

         if (aa >= 0 && aa < this.num) {
            this.select = aa;
         }

      }
   }
}
