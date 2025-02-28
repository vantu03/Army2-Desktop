package javax.microedition.lcdui;

import CLib.LibSysTem;
import CLib.mSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Image {
    public Texture texture;
    public TextureRegion tRegion;
    public int width;
    public int height;
    public Sprite sp;
    public static int Zoom = 1;
    public boolean isZoom_IOS = false;
    public static int transColor = 16777215;

    public static Image createImage(String url) {
        Image img = new Image();
        img.texture = new Texture(Gdx.files.internal(LibSysTem.res + url), Pixmap.Format.RGBA8888, false);
        if (img.texture == null) {
            throw new IllegalArgumentException("!!! createImage scr is NULL-----------." + LibSysTem.res + url);
        } else {
            img.width = img.texture.getWidth();
            img.height = img.texture.getHeight();
            img.texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
            return img.texture == null ? null : img;
        }
    }

    public static Color getColor(int rgb) {
        int blue = rgb & 255;
        int green = rgb >> 8 & 255;
        int red = rgb >> 16 & 255;
        int al = rgb >> 24 & 255;
        float b = (float) blue / 256.0F;
        float g = (float) green / 256.0F;
        float r = (float) red / 256.0F;
        float a = (float) al / 256.0F;
        return new Color(r, g, b, a);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
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
        img.texture = new Texture(w, h, Pixmap.Format.RGBA4444);
        img.width = img.texture.getWidth();
        img.height = img.texture.getHeight();
        return img;
    }

    public static Image createImage(final byte[] encodedData, final int offset, final int len) {
        final Image img = new Image();
        Gdx.app.postRunnable(new Runnable() {
            public void run() {
                try {
                    img.texture = new Texture(new Pixmap(encodedData, offset, len));
                    img.width = img.texture.getWidth();
                    img.height = img.texture.getHeight();
                } catch (Exception var2) {
                }

            }
        });
        return img;
    }

    public static Image ScaleImage(Image scr, int w, int h) {
        return scr;
    }

    public static Image createImageMiniMap(Image imgTile, int wMap, int hMap, int[] dataMap, int testValue, int sizeMini) {
        if (imgTile == null) {
            throw new IllegalArgumentException("Image imgTile is NULL-----------.");
        } else {
            imgTile.texture.getTextureData().prepare();
            Pixmap a = imgTile.texture.getTextureData().consumePixmap();
            Pixmap b = new Pixmap(wMap * sizeMini, hMap * sizeMini, Pixmap.Format.RGBA8888);

            for (int i = 0; i < wMap; ++i) {
                for (int j = 0; j < hMap; ++j) {
                    int u = dataMap[j * wMap + i] - 1;
                    if (u > testValue) {
                        b.drawPixmap(a, i * sizeMini, (hMap - 1 - j) * sizeMini, 0, u * sizeMini, sizeMini, sizeMini);
                    }
                }
            }

            Image img = new Image();
            img.texture = new Texture(b);
            img.width = wMap * sizeMini;
            img.height = hMap * sizeMini;
            a.dispose();
            b.dispose();
            return img;
        }
    }

    public static Image createRGBImage(int[] rgb, int w, int h, boolean i) {
        return createImage(rgb, w, h);
    }

    public static byte[] int2byte(int[] values) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        for (int i = 0; i < values.length; ++i) {
            try {
                dos.writeInt(values[i]);
            } catch (IOException var5) {
                var5.printStackTrace();
            }
        }

        return baos.toByteArray();
    }

    public static Image createImage(int[] encodedData, int w, int h, boolean oi) {
        return createImage(encodedData, w, h);
    }

    public static Image createImage(final int[] encodedData, final int w, final int h) {
        final Image img = new Image();
        Gdx.app.postRunnable(new Runnable() {
            public void run() {
                try {
                    Color cl = null;
                    Pixmap p = new Pixmap(w, h, Pixmap.Format.RGBA8888);

                    for (int i = 0; i < w; ++i) {
                        for (int j = 0; j < h; ++j) {
                            if (encodedData[j * w + i] == Image.transColor) {
                                p.setColor(new Color(0.0F, 0.0F, 0.0F, 0.0F));
                            } else {
                                p.setColor(mSystem.setColor(encodedData[j * w + i]));
                            }

                            p.drawPixel(i, j);
                        }
                    }

                    img.texture = new Texture(p);
                    img.width = img.texture.getWidth();
                    img.height = img.texture.getHeight();
                    img.texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
                    img.sp = new Sprite(img.texture);
                    img.sp.setPosition(0.0F, 0.0F);
                    img.sp.setCenterX(0.0F);
                    img.sp.setOrigin(0.0F, 0.0F);
                    img.sp.flip(false, true);
                    img.sp.scale((float) (Image.Zoom - 1));
                    p.dispose();
                } catch (Exception var5) {
                }

            }
        });
        return img;
    }

    public static DataInputStream openFile(String path) {
        DataInputStream is = new DataInputStream(LibSysTem.getResourceAsStream(path));
        return is;
    }

    public static int getIntByColor(Color cl) {
        float r = cl.r * 255.0F;
        float b = cl.b * 255.0F;
        float g = cl.g * 255.0F;
        return ((int) r & 255) << 16 | ((int) g & 255) << 8 | (int) b & 255;
    }

    public static int argb(int alpha, int red, int green, int blue) {
        return alpha << 24 | red << 16 | green << 8 | blue;
    }

    public void getRGB(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height) {
        if (this.texture == null) {
            throw new IllegalArgumentException("texture Image getRGB is NULL-----------.");
        } else {
            try {
                TextureData tData = this.texture.getTextureData();
                if (!tData.isPrepared()) {
                    tData.prepare();
                }

                Pixmap a = tData.consumePixmap();
                boolean isRGB888 = false;
                if (a.getFormat() == Pixmap.Format.RGB888) {
                }

                int[] r = new int[width * height];
                Color color = new Color();

                int i;
                for (i = 0; i < width; ++i) {
                    for (int j = 0; j < height; ++j) {
                        int val = a.getPixel(i + x, j + y);
                        if (isRGB888) {
                            Color.rgb888ToColor(color, val);
                        } else {
                            Color.rgba8888ToColor(color, val);
                        }

                        int R = (int) (color.r * 255.0F);
                        int G = (int) (color.g * 255.0F);
                        int B = (int) (color.b * 255.0F);
                        int A = (int) (color.a * 255.0F);
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

                for (i = 0; i < rgbData.length; ++i) {
                    rgbData[i] = r[i];
                }
            } catch (Exception var20) {
            }

        }
    }

    public void dispose() {
        if (this.texture != null) {
            this.texture.dispose();
        }

    }
}
