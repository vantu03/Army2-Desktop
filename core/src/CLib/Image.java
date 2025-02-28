package CLib;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import coreLG.CCanvas;
import java.io.DataInputStream;
import model.CRes;
import model.IAction2;

public class Image {
   public Texture texture;
   public TextureRegion tRegion;
   public int width;
   public int height;

   public static Image createImage(final String url) {
      final Image img = new Image();
      new AssetManager();
      Gdx.app.postRunnable(new Runnable() {
         public void run() {
            Texture t = null;
            t = new Texture(Gdx.files.internal(LibSysTem.res + url));
            img.texture = t;
            img.width = img.texture.getWidth();
            img.height = img.texture.getHeight();
         }
      });
      return img;
   }

   public static void createImage(final String url, final IAction2 action2) {
      Gdx.app.postRunnable(new Runnable() {
         public void run() {
            Image img = new Image();
            Texture t = null;
            FileHandle file = null;
            if (CCanvas.isPc()) {
               file = Gdx.files.local(LibSysTem.res + url);
            } else {
               file = Gdx.files.internal(LibSysTem.res + url);
            }

            t = new Texture(file);
            img.texture = t;
            img.width = img.texture.getWidth();
            img.height = img.texture.getHeight();
            if (action2 != null) {
               action2.perform(img);
            }

         }
      });
   }

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   public int _getWidth() {
      return this.width;
   }

   public int _getHeight() {
      return this.height;
   }

   public static Image createImageTextureRegion() {
      Image img = new Image();
      img.tRegion = new TextureRegion();
      img.tRegion.flip(false, true);
      return img;
   }

   public static Image createImage(int w, int h) {
      Image img = new Image();
      Texture t = new Texture(w, h, Pixmap.Format.RGBA4444);
      img.texture = t;
      img.width = img.texture.getWidth();
      img.height = img.texture.getHeight();
      return img;
   }

   public static Image createImage(byte[] encodedData, int offset, int len) {
      Image img = new Image();
      Pixmap p = new Pixmap(encodedData, offset, len);
      img.texture = new Texture(p);
      img.width = img.texture.getWidth();
      img.height = img.texture.getHeight();
      p.dispose();
      return img;
   }

   public static Image createImage(final byte[] encodedData, final int offset, final int len, final IAction2 callback) {
      final Image img = new Image();
      Gdx.app.postRunnable(new Runnable() {
         public void run() {
            Pixmap p = new Pixmap(encodedData, offset, len);
            img.texture = new Texture(p, Pixmap.Format.RGBA8888, false);
            img.width = img.texture.getWidth();
            img.height = img.texture.getHeight();
            img.texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
            if (callback != null) {
               callback.perform(img);
            }

         }
      });
      return img;
   }

   public static Image createImage(final byte[] encodedData, final int offset, final int len, String path) {
      final Image img = new Image();
      Gdx.app.postRunnable(new Runnable() {
         public void run() {
            Pixmap p = new Pixmap(encodedData, offset, len);
            img.texture = new Texture(p, Pixmap.Format.RGBA8888, false);
            img.width = img.texture.getWidth();
            img.height = img.texture.getHeight();
            img.texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
            p.dispose();
         }
      });
      return img;
   }

   public static Image createImage(byte[] encodedData, int offset, int len, boolean isFont) {
      Image img = new Image();

      try {
         Pixmap p = new Pixmap(encodedData, offset, len);
         img.texture = new Texture(p);
         img.width = img.texture.getWidth();
         img.height = img.texture.getHeight();
         p.dispose();
      } catch (Exception var6) {
         var6.printStackTrace();
      }

      return img;
   }

   public static byte[] int2byte(int[] src) {
      int srcLength = src.length;
      byte[] dst = new byte[srcLength << 2];

      for(int i = 0; i < srcLength; ++i) {
         int x = src[i];
         int j = i << 2;
         dst[j++] = (byte)(x >>> 0 & 255);
         dst[j++] = (byte)(x >>> 8 & 255);
         dst[j++] = (byte)(x >>> 16 & 255);
         dst[j++] = (byte)(x >>> 24 & 255);
      }

      return dst;
   }

   public static Image createImage(final int[] encodedData, final int w, final int h) {
      final Image img = new Image();
      Gdx.app.postRunnable(new Runnable() {
         public void run() {
            Color cl = null;
            Pixmap p = new Pixmap(w, h, Pixmap.Format.RGBA8888);

            for(int i = 0; i < w; ++i) {
               for(int j = 0; j < h; ++j) {
                  if (encodedData[j * w + i] != 16777215 && encodedData[j * w + i] != -16777215) {
                     p.setColor(mSystem.setColor(encodedData[j * w + i]));
                  } else {
                     p.setColor(new Color(0.0F, 0.0F, 0.0F, 0.0F));
                  }

                  p.drawPixel(i, j);
               }
            }

            img.texture = new Texture(p);
            img.width = img.texture.getWidth();
            img.height = img.texture.getHeight();
            img.texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
            p.dispose();
         }
      });
      return img;
   }

   public static Image createImageNotRunable(int[] encodedData, int w, int h) {
      Image img = new Image();
      Color cl = null;
      Pixmap p = new Pixmap(w, h, Pixmap.Format.RGBA8888);

      for(int i = 0; i < w; ++i) {
         for(int j = 0; j < h; ++j) {
            if (encodedData[j * w + i] != 16777215 && encodedData[j * w + i] != -16777215) {
               p.setColor(mSystem.setColor(encodedData[j * w + i]));
            } else {
               p.setColor(0.0F, 0.0F, 0.0F, 0.0F);
            }

            p.drawPixel(i, j);
         }
      }

      img.texture = new Texture(p);
      img.width = img.texture.getWidth();
      img.height = img.texture.getHeight();
      img.texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
      p.dispose();
      return img;
   }

   public static Image createImage(final int[] encodedData, final int w, final int h, final IAction2 callback) {
      final Image img = new Image();
      Gdx.app.postRunnable(new Runnable() {
         public void run() {
            Color cl = null;
            Pixmap p = new Pixmap(w, h, Pixmap.Format.RGBA8888);

            for(int i = 0; i < w; ++i) {
               for(int j = 0; j < h; ++j) {
                  if (encodedData[j * w + i] != 16777215 && encodedData[j * w + i] != -16777215) {
                     p.setColor(mSystem.setColor(encodedData[j * w + i]));
                  } else {
                     p.setColor(new Color(0.0F, 0.0F, 0.0F, 0.0F));
                  }

                  p.drawPixel(i, j);
               }
            }

            img.texture = new Texture(p);
            img.width = img.texture.getWidth();
            img.height = img.texture.getHeight();
            img.texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
            if (callback != null) {
               callback.perform(img);
            }

            p.dispose();
         }
      });
      return img;
   }

   public static Image createImage(final Image scr, final int x, final int y, final int w, final int h) {
      if (scr == null) {
         throw new IllegalArgumentException("Image scr is NULL-----------.");
      } else {
         final Image img = new Image();
         Gdx.app.postRunnable(new Runnable() {
            public void run() {
               scr.texture.getTextureData().prepare();
               Pixmap a = scr.texture.getTextureData().consumePixmap();
               Pixmap b = new Pixmap(w, h, Pixmap.Format.RGBA8888);
               b.drawPixmap(a, 0, 0, x, y, w, h);
               img.texture = new Texture(b);
               img.width = w;
               img.height = h;
               a.dispose();
               b.dispose();
            }
         });
         return img;
      }
   }

   public static Image createImageMiniMap(Image imgTile, int wMap, int hMap, int[] dataMap, int testValue, int sizeMini) {
      if (imgTile == null) {
         throw new IllegalArgumentException("Image imgTile is NULL-----------.");
      } else {
         Image img = new Image();
         imgTile.texture.getTextureData().prepare();
         Pixmap a = imgTile.texture.getTextureData().consumePixmap();
         Pixmap b = new Pixmap(wMap * sizeMini, hMap * sizeMini, Pixmap.Format.RGBA8888);

         for(int i = 0; i < wMap; ++i) {
            for(int j = 0; j < hMap; ++j) {
               int u = dataMap[j * wMap + i] - 1;
               if (u > testValue) {
                  b.drawPixmap(a, i * sizeMini, (hMap - 1 - j) * sizeMini, 0, u * sizeMini, sizeMini, sizeMini);
               }
            }
         }

         img.texture = new Texture(b);
         img.width = wMap * sizeMini;
         img.height = hMap * sizeMini;
         b.dispose();
         return img;
      }
   }

   public static DataInputStream openFile(String path) {
      DataInputStream is = new DataInputStream(LibSysTem.getResourceAsStream(path));
      return is;
   }

   public static int argb(int alpha, int red, int green, int blue) {
      return alpha << 24 | red << 16 | green << 8 | blue;
   }

   public void getRGB(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height) {
      if (this.texture == null) {
         throw new IllegalArgumentException("texture Image getRGB is NULL-----------.");
      } else {
         TextureData tData = this.texture.getTextureData();
         if (!tData.isPrepared()) {
            tData.prepare();
         }

         Pixmap a = tData.consumePixmap();
         boolean isRGB888 = false;
         if (a.getFormat() == Pixmap.Format.RGBA8888) {
         }

         int[] r = new int[width * height];
         Color color = new Color();

         int i;
         for(i = 0; i < width; ++i) {
            for(int j = 0; j < height; ++j) {
               int val = a.getPixel(i + x, j + y);
               if (isRGB888) {
                  Color.rgb888ToColor(color, val);
               } else {
                  Color.rgba8888ToColor(color, val);
               }

               int R = (int)(color.r * 255.0F);
               int G = (int)(color.g * 255.0F);
               int B = (int)(color.b * 255.0F);
               int A = (int)(color.a * 255.0F);
               if (isRGB888) {
                  A = 255;
               }

               if (color.a == 0.0F) {
                  r[j * width + i] = 16777215;
               } else {
                  r[j * width + i] = argb(A, R, G, B);
               }
            }
         }

         for(i = 0; i < rgbData.length; ++i) {
            rgbData[i] = r[i];
         }

      }
   }

   public static Image createRGBImage(int[] rbg, int w, int h, boolean bl) {
      return createImage(rbg, w, h);
   }

   public static Image createRGBImage(int[] rbg, int w, int h, boolean bl, IAction2 action) {
      return createImage(rbg, w, h, action);
   }

   public static Image createImage(final byte[] encodedData, final int offset, final int len, final String path, final String name) {
      final Image img = new Image();
      Gdx.app.postRunnable(new Runnable() {
         public void run() {
            Pixmap p = new Pixmap(encodedData, offset, len);
            img.texture = new Texture(p, Pixmap.Format.RGBA8888, false);
            img.width = img.texture.getWidth();
            img.height = img.texture.getHeight();
            img.texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
            CRes.onSaveToFile(img, path, name);
            p.dispose();
         }
      });
      return img;
   }
}
