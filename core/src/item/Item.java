package item;

import CLib.mGraphics;
import CLib.mImage;
import coreLG.CCanvas;
import model.Font;
import model.Language;
import screen.GameScr;

public class Item {
   private static mImage s_imgITEM;
   public static final String[] ITEM_NAME;
   public static byte[] NUM_MAX_USED;
   public static byte[] NUM_BUY_PACKAGE;
   public static final int IMG_SIZE = 16;
   public static final int IMG_SIZE_2 = 37;
   public static final byte numItem = 36;
   public static final byte ITEM_NULL = -2;
   public static final byte ITEM_AFTER_USED = -1;
   public static final byte ITEM_HEALTH = 0;
   public static final byte ITEM_TELEPORT = 1;
   public static final byte ITEM_DOUBLE = 2;
   public static final byte ITEM_RUN_SPEED = 3;
   public static final byte ITEM_INVISIBLE = 4;
   public static final byte ITEM_STOP_WIND = 5;
   public static final byte ITEM_RANG_CUA_BULL = 6;
   public static final byte ITEM_GENERADE = 7;
   public static final byte ITEM_BOMB_BAY = 8;
   public static final byte ITEM_WEB = 9;
   public static final byte ITEM_HEALTH_FOR_TEAM = 10;
   public static final byte ITEM_DAN_TRAI_PHA = 11;
   public static final byte ITEM_SLOT_1 = 12;
   public static final byte ITEM_SLOT_2 = 13;
   public static final byte ITEM_SLOT_3 = 14;
   public static final byte ITEM_SLOT_4 = 15;
   public static final byte ITEM_LAZER = 16;
   public static final byte ITEM_TORNADO = 17;
   public static final byte ITEM_MOUSE = 18;
   public static final byte ITEM_4MISSILE = 19;
   public static final byte ITEM_UNDERGROUND = 20;
   public static final byte ITEM_METEOR = 21;
   public static final byte ITEM_MRAIN = 22;
   public static final byte ITEM_HOLE = 23;
   public static final byte ITEM_SUICIDE = 24;
   public static final byte ITEM_SMOKE = 25;
   public static final byte ITEM_BIG_HOLE = 26;
   public static final byte ITEM_UFO = 27;
   public static final byte ITEM_FREEZE = 28;
   public static final byte ITEM_POSION = 29;
   public static final byte ITEM_ANGRY = 100;
   public static final byte ITEM_WEB3 = 30;
   public static final byte ITEM_TIME_BOMB = 31;
   public static final byte ITEM_HEALTH_500 = 32;
   public static final byte ITEM_HEALTH_1000 = 33;
   public static final byte ITEM_INVISIBLE_2 = 34;
   public static final byte ITEM_VAMPIRE = 35;
   public static final byte ITEM_RESETPOINT = 36;
   public static final byte ITEM_X2EXP = 37;
   public byte type;
   public String decription;
   public byte num;
   public int price;
   public int price2;
   public byte numUsed;
   public byte numToBuy;
   public byte nCurBuyPackage;
   public byte nCurMaxUsed;
   public boolean isSell = true;
   public boolean isPassive_Item;
   public boolean isCannotBuy;
   public boolean isFreeItem;
   static int blank;
   public static int iWitdh;

   static {
      s_imgITEM = GameScr.s_imgITEM;
      ITEM_NAME = Language.items();
      NUM_MAX_USED = new byte[]{2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
      NUM_BUY_PACKAGE = new byte[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
      blank = 2;
      iWitdh = 4;
   }

   public Item(byte Type, byte Num, int Price, int Price2) {
      this.type = Type;
      this.num = Num;
      this.numUsed = 0;
      this.numToBuy = 0;
      this.price = Price;
      this.price2 = Price2;
      this.decription = ITEM_NAME[Type];
      this.nCurBuyPackage = NUM_BUY_PACKAGE[Type];
      this.nCurMaxUsed = NUM_MAX_USED[Type];
      if (Price < 0) {
         this.isSell = false;
      } else {
         this.isSell = true;
      }

      this.isPassive_Item = false;
      switch(this.type) {
      case 0:
         this.isFreeItem = true;
         this.num = 99;
         break;
      case 1:
         this.isFreeItem = true;
         this.num = 99;
         break;
      case 12:
         this.isPassive_Item = true;
         break;
      case 13:
         this.isPassive_Item = true;
         break;
      case 14:
         this.isPassive_Item = true;
         break;
      case 15:
         this.isPassive_Item = true;
      }

   }

   public static void DrawItem(mGraphics g, int itemID, int x, int y) {
      try {
         g.drawRegion(s_imgITEM, 0, (itemID + 2) * 16, 16, 16, 0, x + 8, y + 8, 3, true);
      } catch (Exception var5) {
         g.setColor(16777215);
         g.fillRect(x, y, 16, 16, true);
      }

   }

   public void drawThisItem(mGraphics g, int x, int y) {
      try {
         g.drawRegion(s_imgITEM, 0, (this.type + 2) * 16, 16, 16, 0, x, y, 0, true);
      } catch (Exception var5) {
         g.setColor(16777215);
         g.fillRect(x, y, 16, 16, true);
      }

   }

   public static void DrawSetItem(mGraphics g, int[] setItem, int curItem, int x, int y, boolean isTouch, byte[] num) {
      int i = 0;
      int j = 0;
      int k = 0;
      int l = 0;
      int dis = isTouch ? 22 : 0;

      int n;
      for(n = 0; n < setItem.length; ++n) {
         int x1 = x + i * (16 + blank + dis);
         int y1;
         if (CCanvas.curScr == CCanvas.gameScr) {
            y1 = y + j * (16 + blank + dis);
         } else {
            y1 = y + j * (16 + blank + dis);
         }

         if (curItem >= 0 && n == curItem) {
            g.setColor(CCanvas.gameTick % 5 > 2 ? 16776960 : 16711680);
            g.fillRect(x1 - blank / 2, y1 - blank / 2, 16 + blank, 16 + blank, false);
         }

         try {
            g.drawRegion(s_imgITEM, 0, (setItem[n] + 2) * 16, 16, 16, 0, x1, y1, 0, false);
         } catch (Exception var19) {
            g.fillRect(x1, y1, 16, 16, false);
         }

         ++i;
         if (i > iWitdh - 1) {
            i = 0;
            ++j;
         }
      }

      for(n = 0; n < setItem.length; ++n) {
         int a1 = x + k * (16 + blank + dis);
         int b1;
         if (CCanvas.curScr == CCanvas.gameScr) {
            b1 = y + l * (16 + blank + dis);
         } else {
            b1 = y + l * (16 + blank + dis);
         }

         try {
            String numStr = num[n] != 100 ? String.valueOf(num[n]) : "";
            Font.smallFontYellow.drawString(g, numStr, a1 + 12, b1 + 12, 0);
         } catch (Exception var18) {
         }

         ++k;
         if (k > iWitdh - 1) {
            k = 0;
            ++l;
         }
      }

   }
}
