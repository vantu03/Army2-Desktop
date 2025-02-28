package model;

import CLib.mGraphics;
import CLib.mImage;
import effect.Explosion;
import item.Item;
import player.Boss;

public class LuckyGift {
   public int id;
   public String info;
   public mImage icon;
   public int num;
   public int type;
   public int itemID = -1;
   public int luckyCode;
   public int tCount = 0;
   public int index = CRes.random(0, 5);
   public boolean isWait = false;
   public boolean isServerSend = false;
   public boolean isShow = false;
   int[] dx = new int[]{-2, 2, -2, 2, -2, 2, 0, 0, 0, 0};
   int x;
   int y;

   public void setIcon(byte[] dataRaw, int len) {
      this.icon = mImage.createImage((byte[])dataRaw, 0, len, (String)"");
   }

   public void paint(mGraphics g, int x, int y) {
      this.x = x;
      this.y = y;
      if (this.isShow) {
         if (this.type == 0 || this.type == 1) {
            Font.borderFont.drawString(g, this.info, x, y, 3);
         }

         if (this.type == 3) {
            Item.DrawItem(g, this.itemID, x - 8, y - 8);
            Font.borderFont.drawString(g, this.info, x + 8, y + 6, 3);
         }

         if (this.type == 2 && this.icon != null) {
            g.drawImage(this.icon, x, y, 3, false);
            Font.borderFont.drawString(g, this.info, x + 8, y + 6, 3);
         }
      } else {
         g.drawImage(Boss.gift_1, x + this.dx[this.index], y, 3, false);
      }

   }

   public void update() {
      if (this.isWait) {
         ++this.tCount;
         ++this.index;
         if (this.index == this.dx.length) {
            this.index = 0;
         }

         if (this.type != 2) {
            if (this.tCount > 15 && this.isServerSend) {
               this.isWait = false;
               this.isShow = true;
               this.isServerSend = false;
               new Explosion(this.x, this.y + 6, (byte)1);
            }
         } else if (this.tCount > 15 && this.icon != null && this.isServerSend) {
            this.isWait = false;
            this.isShow = true;
            this.isServerSend = false;
            new Explosion(this.x, this.y + 6, (byte)1);
         }
      }

   }
}
