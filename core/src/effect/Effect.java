package effect;

import CLib.mGraphics;
import CLib.mImage;
import screen.GameScr;

public class Effect {
   private static int[] clips = new int[4];
   private static mImage s_imgTransparent;
   static int[] s_transparentBuf;
   static mImage s_transparentImg;
   static final int TRANSPARENT_BUF_H = 1;
   static final int BACK_IMAGE_HEIGHT = 1;
   static final boolean DRAW_TransparentRect_USE_DrawRGB = false;

   static {
      s_imgTransparent = GameScr.s_imgTransparent;
   }

   public static void Flash(mGraphics g, int x, int y, int width, int height, int alpha) {
      int[] data = new int[width * height];

      for(int i = 0; i < data.length; ++i) {
         data[i] = alpha << 24 | 16777215;
      }

      g.drawRGB(data, 0, width, x, y, width, height, true);
   }

   public static void DrawBlurImage(mGraphics g, mImage img, int x, int y, int blurLevel) {
      if (blurLevel == 0) {
         g.drawImage(img, x, y, mGraphics.TOP | mGraphics.LEFT, false);
      } else {
         clips[0] = g.getClipX();
         clips[1] = g.getClipY();
         clips[2] = g.getClipWidth();
         clips[3] = g.getClipHeight();
         int blockSize = blurLevel * 2;
         int width = img.image.getWidth();
         int height = img.image.getHeight();
         int[] imgData = new int[width * height];
         img.getRGB(imgData, 0, width, 0, 0, width, height);
         if (imgData != null) {
            int x1 = Math.max(x, clips[0]);
            int y1 = Math.max(y, clips[1]);
            int width1 = Math.min(x + width, clips[0] + clips[2]) - x1;
            int height1 = Math.min(y + height, clips[1] + clips[3]) - y1;
            if (width1 > 0 && height1 > 0) {
               g.setClip(x1, y1, width1, height1);

               for(int i = 0; i < height; i += blockSize) {
                  for(int j = 0; j < width; j += blockSize) {
                     if ((i + blockSize / 2) * width + j + blockSize / 2 <= imgData.length) {
                        g.fillRect(x + j, y + i, blockSize, blockSize, false);
                     }
                  }
               }

               g.setClip(clips[0], clips[1], clips[2], clips[3]);
            }
         }
      }
   }

   public static void FillTransparentRect(mGraphics g, int x, int y, int width, int height) {
      clips[0] = g.getClipX();
      clips[1] = g.getClipY();
      clips[2] = g.getClipWidth();
      clips[3] = g.getClipHeight();
      int x1 = Math.max(x, clips[0]);
      int y1 = Math.max(y, clips[1]);
      int width1 = Math.min(x + width, clips[0] + clips[2]) - x1;
      int height1 = Math.min(y + height, clips[1] + clips[3]) - y1;
      if (width1 > 0 && height1 > 0) {
         g.setClip(x1, y1, width1, height1);

         for(int i = 0; i < width / 120 + 1; ++i) {
            for(int j = 0; j < height / 40 + 1; ++j) {
               g.drawImage(s_imgTransparent, x + 120 * i, y + 40 * j, 0, false);
            }
         }

         g.setClip(clips[0], clips[1], clips[2], clips[3]);
      }
   }

   public static void FillTransparentRectRGB(mGraphics g, int x, int y, int width, int height, int color) {
      clips[0] = g.getClipX();
      clips[1] = g.getClipY();
      clips[2] = g.getClipWidth();
      clips[3] = g.getClipHeight();
      int x1 = Math.max(x, clips[0]);
      int y1 = Math.max(y, clips[1]);
      int width1 = Math.min(x + width, clips[0] + clips[2]) - x1;
      int height1 = Math.min(y + height, clips[1] + clips[3]) - y1;
      if (width1 > 0 && height1 > 0) {
         g.setClip(x1, y1, width1, height1);
         int[] data = new int[width * height];
         g.setClip(clips[0], clips[1], clips[2], clips[3]);
      }
   }

   private static void fillTransparentRect(mGraphics gra, int x, int y, int width, int height, int r, int g, int b, int transPercent, boolean useDrawRGB) {
      transPercent = 255 * transPercent / 100;
      if (s_transparentBuf == null || s_transparentBuf.length / 1 < width) {
         s_transparentBuf = new int[width * 1];
      }

      int len = s_transparentBuf.length;
      int argb = (transPercent << 24) + (r << 16) + (g << 8) + b;

      int i;
      for(i = 0; i < len && s_transparentBuf[i] != argb; ++i) {
         s_transparentBuf[i] = argb;
      }

      if (!useDrawRGB) {
         s_transparentImg = mImage.createImage((int[])s_transparentBuf, width, 1);
      }

      for(i = 0; i < height; ++i) {
         if (useDrawRGB) {
            gra.drawRGB(s_transparentBuf, 0, width, x, y + i, width, 1, true);
         } else {
            gra.drawRegion(s_transparentImg, 0, 0, width, 1, 0, x, y + i, 0, false);
         }
      }

   }

   public static void fillTransparentRect(mGraphics g, int x, int y, int width, int height, int color, int transPercent, boolean useDrawRGB) {
      if (width > 0 && height > 0) {
         fillTransparentRect(g, x, y, width, height, color >> 16 & 255, color >> 8 & 255, color & 255, transPercent, useDrawRGB);
      }
   }

   public static void fillTransparentRect(mGraphics g, int x, int y, int width, int height, int color, int transPercent) {
      fillTransparentRect(g, x, y, width, height, color >> 16 & 255, color >> 8 & 255, color & 255, transPercent, false);
   }

   public static void transparentRGB(int[] rgbData, int transPercent) {
      transPercent = 255 * transPercent / 100;
      int len = rgbData.length;

      for(int i = 0; i < len; ++i) {
         rgbData[i] &= 16777215;
         if (rgbData[i] != 16711935) {
            rgbData[i] |= transPercent << 24;
         }
      }

   }
}
