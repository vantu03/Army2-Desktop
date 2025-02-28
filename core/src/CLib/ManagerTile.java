package CLib;

import com.badlogic.gdx.graphics.Pixmap;

public class ManagerTile {
   public static final byte[] totalTile = new byte[]{73, 60, 57, 64, 47, 43, 66, 61, 52, 51, 61, 62, 67, 69, 59};
   public static mImage[] tileBig;
   public static mImage[] tileMapLogin;
   public static Pixmap[] miniTile;
   public static Pixmap[] bigTile;

   public static void loadTileBig(int id) {
      freeBigTile(id);
      tileBig = new mImage[totalTile[id]];
      miniTile = new Pixmap[totalTile[id]];

      int j;
      String path;
      for(j = 0; j < tileBig.length; ++j) {
         path = j < 9 ? "tile" + id + "_0" : "tile" + id + "_";
         mImage img = mImage.createImage("/Tile/tile" + id + "/" + path + (j + 1) + ".png");
         tileBig[j] = img;
      }

      for(j = 0; j < miniTile.length; ++j) {
         path = j < 9 ? "tile_small" + id + "_0" : "tile_small" + id + "_";
         miniTile[j] = mSystem.createPixmap("/Tile/tile_small" + id + "/" + path + (j + 1) + ".png");
      }

   }

   public static void loadTileBigLogin(int id) {
      tileMapLogin = new mImage[totalTile[id]];

      for(int i = 0; i < tileMapLogin.length; ++i) {
         String path = i < 9 ? "tile" + id + "_0" : "tile" + id + "_";
         mImage img = mImage.createImage("/Tile/tile" + id + "/" + path + (i + 1) + ".png");
         tileMapLogin[i] = img;
      }

   }

   public static void freeBigTile(int id) {
      try {
         int i;
         for(i = 0; i < tileBig.length; ++i) {
            if (tileBig[i] != null && tileBig[i].image != null) {
               tileBig[i].image.texture.dispose();
               tileBig[i].image = null;
               tileBig[i] = null;
            }
         }

         for(i = 0; i < miniTile.length; ++i) {
            if (miniTile[i] != null) {
               miniTile[i].dispose();
            }
         }
      } catch (Exception var2) {
      }

      System.gc();
   }

   public static void freeBigTile1(int id) {
      try {
         int i;
         for(i = 0; i < bigTile.length; ++i) {
            if (bigTile[i] != null) {
               bigTile[i].dispose();
            }
         }

         for(i = 0; i < miniTile.length; ++i) {
            if (miniTile[i] != null) {
               miniTile[i].dispose();
            }
         }
      } catch (Exception var2) {
      }

      System.gc();
   }
}
