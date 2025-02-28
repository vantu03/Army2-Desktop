package model;

import CLib.mGraphics;
import coreLG.CCanvas;
import effect.Camera;
import effect.Explosion;
import item.Bullet;
import screen.GameScr;

public class TimeBomb {
   public int id;
   public int x;
   public int y;
   public boolean isExplore;
   public boolean isFall;
   public boolean falling;
   int dy;

   public TimeBomb(int id, int x, int y) {
      this.id = id;
      this.x = x;
      this.y = y;
      new Explosion(x, y, (byte)1);
   }

   public void paint(mGraphics g) {
      int frame = CCanvas.gameTick % 3 == 0 ? 0 : 1;
      g.drawRegion(Explosion.timeBomb, 0, frame * 15, 28, 15, 0, this.x, this.y, mGraphics.HCENTER | mGraphics.BOTTOM, false);
   }

   public void update() {
      int y1;
      if (this.isExplore) {
         Camera.shaking = 2;
         int[][] array = GameScr.getPointAround(this.x, this.y, 7);

         for(y1 = 0; y1 < 7; ++y1) {
            new Explosion(array[0][y1], array[1][y1], (byte)7);
         }

         GameScr.timeBombs.removeElement(this);
      }

      int x1;
      if (this.isFall) {
         this.falling = true;

         for(x1 = 0; x1 < 14; ++x1) {
            if (GameScr.mm.isLand(this.x - 7 + x1, this.y)) {
               this.falling = false;
               this.dy = 0;
               break;
            }
         }

         this.isFall = false;
      }

      if (this.falling) {
         x1 = this.x;
         y1 = this.y;
         ++this.dy;
         this.y += this.dy;

         for(int i = 0; i < 14; ++i) {
            if (GameScr.mm.isLand(this.x - 7 + i, this.y)) {
               this.falling = false;
               this.dy = 0;
               int[] p = Bullet.getCollisionPoint(x1, y1, this.x, this.y);
               if (p != null) {
                  this.y = p[1];
               }
               break;
            }
         }
      }

   }
}
