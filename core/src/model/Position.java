package model;

public class Position {
   public int x;
   public int y;
   public int xF;
   public int yF;
   public int xT;
   public int yT;
   int frame;

   public Position(int x, int y) {
      this.x = x;
      this.y = y;
   }

   public Position(int x, int y, int frame) {
      this.x = x;
      this.y = y;
      this.frame = frame;
   }

   public Position(int xF, int yF, int xT, int yT) {
      this.xF = xF;
      this.yF = yF;
      this.xT = xT;
      this.yT = yT;
   }
}
