package javax.microedition.lcdui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Graphics {
   public SpriteBatch g;
   public static int HCENTER = 1;
   public static int VCENTER = 2;
   public static int LEFT = 4;
   public static int RIGHT = 8;
   public static int TOP = 16;
   public static int BOTTOM = 32;
   public static final int TRANS_NONE = 0;
   public static final int TRANS_ROT90 = 5;
   public static final int TRANS_ROT180 = 3;
   public static final int TRANS_ROT270 = 6;
   public static final int TRANS_MIRROR = 2;
   public static final int TRANS_MIRROR_ROT90 = 7;
   public static final int TRANS_MIRROR_ROT180 = 1;
   public static final int TRANS_MIRROR_ROT270 = 4;

   public Graphics(SpriteBatch g) {
      this.g = g;
   }
}
