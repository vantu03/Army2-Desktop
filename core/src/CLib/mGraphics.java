package CLib;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.teamobi.mobiarmy2.MainGame;
import coreLG.CCanvas;

import java.util.Enumeration;

public class mGraphics {
    public static int zoomLevel = 1;
    public SpriteBatch g;
    private float r;
    private float gl;
    private float b;
    private float a;
    private boolean isTranslate;
    private boolean isClip;
    public int clipX;
    public int clipY;
    public int clipW;
    public int clipH;
    public static final boolean isTrue = true;
    public static final boolean isFalse = false;
    private int clipTX;
    private int clipTY;
    int translateX;
    int translateY;
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
    public static mHashtable cachedTextures = new mHashtable();
    public boolean isRorate;
    public int xRotate;
    public int yRotate;
    public float rotation;

    public void translate(int tx, int ty) {
        tx *= zoomLevel;
        ty *= zoomLevel;
        this.translateX += tx;
        this.translateY += ty;
        this.isTranslate = true;
        if (this.translateX == 0 && this.translateY == 0) {
            this.isTranslate = false;
        }

    }

    public void begin() {
        this.g.begin();
    }

    public void end() {
        this.translateX = 0;
        this.translateY = 0;
        this.isTranslate = false;
        this.isClip = false;
        this.g.end();
    }

    public int getTranslateX() {
        return this.translateX / zoomLevel;
    }

    public int getTranslateY() {
        return this.translateY / zoomLevel;
    }

    public void enableBlending(float alpha) {
        this.g.setColor(1.0F, 1.0F, 1.0F, alpha);
    }

    public void disableBlending() {
        this.g.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public void setClip(int x, int y, int w, int h) {
        if (w <= 0) {
            w = 1;
        }

        if (h <= 0) {
            h = 1;
        }

        x *= zoomLevel;
        y *= zoomLevel;
        w *= zoomLevel;
        h *= zoomLevel;
        if (this.isTranslate) {
            x += this.translateX;
            y += this.translateY;
        }

        this.clipX = x;
        this.clipY = y;
        this.clipW = w;
        this.clipH = h;
        this.isClip = true;
    }

    public void beginClip() {
        Rectangle scissors = new Rectangle();
        Rectangle clipBounds = new Rectangle((float) this.clipX, (float) this.clipY, (float) this.clipW, (float) this.clipH);
        ScissorStack.calculateScissors(MainGame.me.camera, this.g.getTransformMatrix(), clipBounds, scissors);
        ScissorStack.pushScissors(scissors);
    }

    public void endClip() {
        this.g.flush();
        ScissorStack.peekScissors();
    }

    public void endClip0() {
        this.g.flush();
        ScissorStack.popScissors();
    }

    public mGraphics(SpriteBatch g) {
        this.g = g;
    }

    public mGraphics() {
    }

    void cache(String key, Texture value) {
        if (cachedTextures.size() > 500) {
            Texture img;
            for (Enumeration k = cachedTextures.keys(); k.hasMoreElements(); img = null) {
                String k0 = (String) k.nextElement();
                img = (Texture) cachedTextures.get(k0);
                cachedTextures.remove(k0);
                cachedTextures.remove(img);
                img.dispose();
            }

            cachedTextures.clear();
            System.gc();
        }

        cachedTextures.put(key, value);
    }

    public void clearCache() {
        Texture img;
        for (Enumeration k = cachedTextures.keys(); k.hasMoreElements(); img = null) {
            String k0 = (String) k.nextElement();
            img = (Texture) cachedTextures.get(k0);
            cachedTextures.remove(img);
            cachedTextures.remove(k0);
            img.dispose();
        }

        cachedTextures.clear();
        System.gc();
    }

    public void drawTextureRegion(mImage tx, int x, int y, int alg) {
        x *= zoomLevel;
        y *= zoomLevel;
        if (this.isTranslate) {
            x += this.translateX;
            y += this.translateY;
        }

        this.g.draw(tx.image.tRegion, (float) x, (float) y);
    }

    public float getAngle(Vector2 centerPt, Vector2 target) {
        float angle = (float) Math.toDegrees(Math.atan2((double) (target.y - centerPt.y), (double) (target.x - centerPt.x)));
        if (angle < 0.0F) {
            angle += 360.0F;
        }

        return angle;
    }

    public void setColor(int rgb) {
        float R = (float) (rgb >> 16 & 255);
        float B = (float) (rgb >> 8 & 255);
        float G = (float) (rgb & 255);
        this.b = B / 256.0F;
        this.gl = G / 256.0F;
        this.r = R / 256.0F;
        this.a = 1.0F;
    }

    public void setColor(int rgb, float alpha) {
        float R = (float) (rgb >> 16 & 255);
        float B = (float) (rgb >> 8 & 255);
        float G = (float) (rgb & 255);
        this.b = B / 256.0F;
        this.gl = G / 256.0F;
        this.r = R / 256.0F;
        this.a = alpha;
    }

    public void setColor(int rgb, int rgb1, int grb2) {
        int num = rgb & 255;
        int num2 = rgb >> 8 & 255;
        int num3 = rgb >> 16 & 255;
        this.b = (float) num / 256.0F;
        this.gl = (float) num2 / 256.0F;
        this.r = (float) num3 / 256.0F;
        this.a = 255.0F;
    }

    public void drawRegion(mImage img, int x_src, int y_src, int width, int height, int flip, int x_dest, int y_dest, int anchor, boolean useClip) {
        if (img != null) {
            x_dest *= zoomLevel;
            y_dest *= zoomLevel;
            this._drawRegion(img.image.texture, x_src, y_src, width, height, flip, x_dest, y_dest, anchor, useClip, false);
        }
    }

    public void drawRegion(mImage img, int x_src, int y_src, int width, int height, int flip, int x_dest, int y_dest, int anchor, boolean isScale, boolean useClip) {
        if (img != null) {
            x_dest *= zoomLevel;
            y_dest *= zoomLevel;
            this._drawRegion(img.image.texture, x_src, y_src, width, height, flip, x_dest, y_dest, anchor, useClip, isScale);
        }
    }

    public void drawRegionNotSetClip(mImage img, int x, int y, int arg3, int arg4, int arg5, int arg6, int arg7, int anchor) {
        if (img != null) {
            x *= zoomLevel;
            y *= zoomLevel;
            arg3 *= zoomLevel;
            arg4 *= zoomLevel;
            this._drawRegion(img.image.texture, x, y, arg3, arg4, arg5, arg6, arg7, anchor, false, false);
        }
    }

    public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3, boolean useClip) {
        this.fillRect(x2, y2, x3, y3, useClip);
    }

    public void resetRotate() {
        this.isRorate = false;
        this.xRotate = 0;
        this.yRotate = 0;
    }

    public void rotate(int pAngle, int x, int y) {
        if (pAngle != 0) {
            this.isRorate = true;
            this.rotation = (float) pAngle;
            this.xRotate = x;
            this.yRotate = y;
        }
    }

    public void drawImageMap(mImage img, int x, int y) {
        if (img != null) {
            x *= zoomLevel;
            y *= zoomLevel;
            if (this.isTranslate) {
                x += this.translateX;
                y += this.translateY;
            }

            int w = img.image._getWidth();
            int h = img.image._getHeight();
            this.g.draw(img.image.texture, (float) x, (float) (y + h), (float) w, (float) (-h));
        }
    }

    public void drawImage(mImage img, int x, int y, int anchor, boolean useClip, boolean isScale) {
        x *= zoomLevel;
        y *= zoomLevel;
        this._drawRegion(img.image.texture, 0, 0, img.image._getWidth(), img.image._getHeight(), 0, x, y, anchor, useClip, isScale);
    }

    public void drawImage(mImage img, int x, int y, int anchor, boolean useClip) {
        x *= zoomLevel;
        y *= zoomLevel;
        this._drawRegion(img.image.texture, 0, 0, img.image._getWidth(), img.image._getHeight(), 0, x, y, anchor, useClip, false);
    }

    public void _drawImage(Texture img, int x, int y, int anchor, boolean useClip) {
        if (img != null) {
            if (this.isTranslate) {
                x += this.translateX;
                y += this.translateY;
            }

            int ixA = 0;
            int iyA = 0;
            int iyA1 = 0;
            int ixA1 = 0;
            int w = img.getWidth();
            int h = img.getHeight();
            switch (anchor) {
                case 0:
                case 20:
                    ixA = 0;
                    ixA1 = w;
                    iyA = h;
                    iyA1 = -h;
                    break;
                case 3:
                    ixA = -w / 2;
                    ixA1 = w;
                    iyA = h / 2;
                    iyA1 = -h;
                    break;
                case 6:
                    ixA = 0;
                    ixA1 = w;
                    iyA = h / 2;
                    iyA1 = -h;
                    break;
                case 10:
                    ixA = -w;
                    ixA1 = w;
                    iyA = h / 2;
                    iyA1 = -h;
                    break;
                case 17:
                    ixA = -w / 2;
                    ixA1 = w;
                    iyA = h;
                    iyA1 = -h;
                    break;
                case 24:
                    ixA = -w;
                    ixA1 = w;
                    iyA = h;
                    iyA1 = -h;
                    break;
                case 33:
                    ixA = -w / 2;
                    ixA1 = w;
                    iyA = 0;
                    iyA1 = -h;
                    break;
                case 36:
                    ixA = 0;
                    ixA1 = w;
                    iyA = 0;
                    iyA1 = -h;
                    break;
                case 40:
                    ixA = -w;
                    ixA1 = w;
                    iyA = 0;
                    iyA1 = -h;
            }

            if (this.isClip && useClip) {
                this.beginClip();
            }

            this.g.draw(img, (float) (x + ixA), (float) (y + iyA), (float) ixA1, (float) iyA1);
            if (this.isClip && useClip) {
                this.endClip0();
            }

        }
    }

    public void drawRect(int x, int y, int w, int h, boolean useClip) {
        int xx = 1;
        this.fillRect(x, y, w, xx, useClip);
        this.fillRect(x, y, xx, h, useClip);
        this.fillRect(x + w, y, xx, h + 1, useClip);
        this.fillRect(x, y + h, w + 1, xx, useClip);
    }

    public void drawRoundRect(int x, int y, int w, int h, int a, int b, boolean useClip) {
        this.drawRect(x, y, w, h, useClip);
    }

    public void fillRoundRect(int x, int y, int w, int h, int a, int b, boolean useClip) {
        this.fillRect(x, y, w, h, useClip);
    }

    public void drawString(mVector total) {
    }

    public void drawStringNotSetClip(mVector total) {
    }

    public void drawString(String s, float x, float y, BitmapFont font, int al, boolean useClip) {
    }

    public void setClipTrung(int x, int y, int w, int h) {
        x *= zoomLevel;
        y *= zoomLevel;
        w *= zoomLevel;
        h *= zoomLevel;
        if (this.isTranslate) {
            x += this.translateX;
            y += this.translateY;
        }

        this.clipX = x;
        this.clipY = y;
        this.clipW = w;
        this.clipH = h;
        this.isClip = true;
    }

    public static Image blend(Image img, float level, int color) {
        int[] var10000 = new int[img._getWidth() * img._getHeight()];
        img.texture.getTextureData().prepare();
        Pixmap a = img.texture.getTextureData().consumePixmap();
        int ww = img._getWidth();
        int hh = img._getHeight();
        int R = 255 & color >> 24;
        int B = 255 & color >> 16;
        int G = 255 & color >> 8;
        int A = 255 & color >> 0;

        for (int x = 0; x < ww; ++x) {
            for (int y = 0; y < hh; ++y) {
                int pixel = a.getPixel(x, y);
                if (pixel != -256) {
                    float r = (float) (R - (pixel >> 24 & 255)) * level + (float) (pixel >> 24 & 255);
                    float g = (float) (B - (pixel >> 16 & 255)) * level + (float) (pixel >> 16 & 255);
                    float b = (float) (G - (pixel >> 8 & 255)) * level + (float) (pixel >> 8 & 255);
                    float al = (float) (A - (pixel >> 0 & 255)) * level + (float) (pixel >> 0 & 255);
                    if (r > 255.0F) {
                        r = 255.0F;
                    }

                    if (r < 0.0F) {
                        r = 0.0F;
                    }

                    if (g > 255.0F) {
                        g = 255.0F;
                    }

                    if (g < 0.0F) {
                        g = 0.0F;
                    }

                    if (b < 0.0F) {
                        b = 0.0F;
                    }

                    if (b > 255.0F) {
                        b = 255.0F;
                    }

                    int rr = (int) r;
                    int gg = (int) g;
                    int bb = (int) b;
                    int aa = (int) al;
                    a.setColor((rr << 24) + (gg << 16) + (bb << 8) + (aa << 0));
                    a.drawPixel(x, y);
                }
            }
        }

        Image image = Image.createImage(ww, hh);
        image.texture = new Texture(a);
        a.dispose();
        return image;
    }

    public boolean isClipWithWHZero() {
        return this.isClip && (this.clipH == 0 || this.clipW == 0);
    }

    public void fillRecAlpla(int x, int y, int w, int h, int color) {
    }

    public void saveCanvas() {
    }

    public void ClipRec(int xbegin, int ypaint, int wScreen, int i) {
    }

    public static void resetTransAndroid(mGraphics g2) {
    }

    public void restoreCanvas() {
    }

    public void translateAndroid(int x, int i) {
    }

    public int getClipX() {
        return 0;
    }

    public int getClipY() {
        return 0;
    }

    public int getClipWidth() {
        return this.isClip ? CCanvas.width : 0;
    }

    public int getClipHeight() {
        return this.isClip ? CCanvas.hieght : 0;
    }

    public void fillArc(int x, int y, mImage imgCircle, int startAngle, int stopAngle, boolean uClip) {
        int num = Math.abs(stopAngle);
        mImage image = mSystem.imgCircle_45;
        if (num == 60) {
            image = mSystem.imgCircle_30;
        } else if (num == 70) {
            image = mSystem.imgCircle_20;
        } else if (num == 90) {
            image = mSystem.imgCircle_0;
        }

        if (stopAngle > 0) {
            this.drawRegion(image, 0, 0, image.image.getWidth(), image.image.getHeight(), 0, x, y, 0, uClip);
        } else if (stopAngle < 0) {
            this.drawRegion(image, 0, 0, image.image.getWidth(), image.image.getHeight(), 2, x, y, 0, uClip);
        }

    }

    public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle, boolean uClip) {
        this.fillRect(x, y, width, height, uClip);
    }

    public void fillArc(int x, int y, int radius, int startAngle, int arcAngle) {
        this._drawArc((Texture) null, x, y, (float) radius, (float) startAngle, (float) arcAngle, (ShapeRenderer.ShapeType) null);
    }

    public void drawRGB(int[] raw, int rectx, int recty, int w, int h, int destW, int destH, boolean useClip) {
    }

    public void fillRect(int x, int y, int w, int h, boolean useClip) {
        x *= zoomLevel;
        y *= zoomLevel;
        w *= zoomLevel;
        h *= zoomLevel;
        if (w >= 0 && h >= 0) {
            if (this.isTranslate) {
                x += this.translateX;
                y += this.translateY;
            }

            String key = "fr" + this.r + this.gl + this.b + this.a;
            Texture rgb_texture = (Texture) cachedTextures.get(key);
            if (rgb_texture == null) {
                Pixmap p = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
                p.setColor(this.r, this.b, this.gl, this.a);
                p.drawPixel(0, 0);
                rgb_texture = new Texture(p);
                p.dispose();
                this.cache(key, rgb_texture);
            }

            if (this.isClip && useClip) {
                this.beginClip();
            }

            this.g.draw(rgb_texture, (float) x, (float) y, 0.0F, 0.0F, 1.0F, 1.0F, (float) w, (float) h, 0.0F, 0, 0, 1, 1, false, false);
            if (this.isClip && useClip) {
                this.endClip0();
            }

        }
    }

    public void _drawRegion(Texture imgscr, int x_src, int y_src, int width, int height, int flip, int x_dest, int y_dest, int anchor, boolean isUseSetClip, boolean isScale) {
        if (imgscr != null) {
            if (this.isTranslate) {
                x_dest += this.translateX;
                y_dest += this.translateY;
            }

            boolean flipX = false;
            boolean flipY = true;
            int scX = 1;
            int scY = 1;
//         int y0 = 0;
            int orx = 0;
            int ory = 0;
            int ixA = 0;
            int iyA = 0;
            switch (anchor) {
                case 0:
                case 20:
                    ixA = 0;
                    iyA = 0;
                    break;
                case 3:
                    ixA = width / 2;
                    iyA = height / 2;
                    if (flip == 4 || flip == 7 || flip == 6 || flip == 5) {
                        ixA = height / 2;
                        iyA = width / 2;
                    }
                    break;
                case 6:
                    ixA = 0;
                    iyA = height / 2;
                    if (flip == 4 || flip == 7 || flip == 6 || flip == 5) {
                        iyA = width / 2;
                    }
                    break;
                case 10:
                    ixA = width;
                    iyA = height / 2;
                    if (flip == 4 || flip == 7 || flip == 6 || flip == 5) {
                        ixA = height;
                        iyA = width / 2;
                    }
                    break;
                case 17:
                    ixA = width / 2;
                    if (flip == 4 || flip == 6 || flip == 5 || flip == 7) {
                        ixA = height / 2;
                    }

                    iyA = 0;
                    break;
                case 24:
                    ixA = width;
                    if (flip == 4 || flip == 6 || flip == 5 || flip == 7) {
                        ixA = height;
                    }

                    iyA = 0;
                    break;
                case 33:
                    ixA = width / 2;
                    iyA = height;
                    if (flip == 4 || flip == 7 || flip == 6 || flip == 5) {
                        ixA = height / 2;
                        iyA = width;
                    }
                    break;
                case 36:
                    ixA = 0;
                    iyA = height;
                    if (flip == 4 || flip == 7 || flip == 6 || flip == 5) {
                        iyA = width;
                    }
                    break;
                case 40:
                    ixA = width;
                    iyA = height;
                    if (flip == 4 || flip == 7 || flip == 6 || flip == 5) {
                        ixA = height;
                        iyA = width;
                    }
            }

            x_dest -= ixA * zoomLevel;
            y_dest -= iyA * zoomLevel;
            int ix = 0;
            int iy = 0;
            switch (flip) {
                case 1:
                    flip = 0;
                    flipY = false;
                    break;
                case 2:
                    flip = 0;
                    flipX = true;
                    break;
                case 3:
                    flip = 180;
                    iy = -height;
                    ix = -width;
                    break;
                case 4:
                    flip = 90;
                    flipY = false;
                    flipX = true;
                    ix = -height;
                    iy = -width;
                    scX = -1;
                    break;
                case 5:
                    flip = 90;
                    flipX = true;
                    ix = -height;
                    iy = -width;
                    scX = -1;
                    break;
                case 6:
                    flip = 90;
                    flipX = true;
                    scY = -1;
                    break;
                case 7:
                    flip = 270;
                    flipY = false;
                    flipX = true;
                    scX = -1;
            }

            if (this.isRorate) {
                orx = this.xRotate;
                ory = this.yRotate;
                flip = (int) ((float) flip + this.rotation);
            }

            if (this.isClip && isUseSetClip) {
                this.beginClip();
            }

            this.g.draw(imgscr, (float) (x_dest - ix * zoomLevel), (float) (y_dest - iy * zoomLevel), (float) orx, (float) ory, (float) width, (float) height, (float) (scX * (!isScale ? zoomLevel : 1)), (float) (scY * (!isScale ? zoomLevel : 1)) + (isScale ? 0.05F : 0.0F), (float) flip, x_src, y_src, width, height, flipX, flipY);
            if (this.isClip && isUseSetClip) {
                this.endClip0();
            }

        }
    }

    public void drawLine(int x1, int y1, int x2, int y2, boolean useClip) {
        x1 *= zoomLevel;
        y1 *= zoomLevel;
        x2 *= zoomLevel;
        y2 *= zoomLevel;
        if (this.isTranslate) {
            x1 += this.translateX;
            y1 += this.translateY;
            x2 += this.translateX;
            y2 += this.translateY;
        }

        String key = "dl" + this.r + this.gl + this.b;
        Texture rgb_texture = (Texture) cachedTextures.get(key);
        if (rgb_texture == null) {
            Pixmap p = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
            p.setColor(this.r, this.b, this.gl, this.a);
            p.drawPixel(0, 0);
            rgb_texture = new Texture(p);
            p.dispose();
            this.cache(key, rgb_texture);
        } else {
            float xSl = (float) Math.sqrt((double) ((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1)));
            int ySl = zoomLevel;
            Vector2 start = new Vector2((float) x1, (float) y1);
            Vector2 end = new Vector2((float) x2, (float) y2);
            float angle = this.getAngle(start, end);
            if (this.isClip && useClip) {
                this.beginClip();
            }

            this.g.draw(rgb_texture, (float) x1, (float) y1, 0.0F, 0.0F, 1.0F, 1.0F, xSl, (float) ySl, angle, 0, 0, 1, 1, false, false);
            if (this.isClip && useClip) {
                this.endClip0();
            }

        }
    }

    public void drawRecAlpa(int x, int y, int w, int h, int color) {
        x *= zoomLevel;
        y *= zoomLevel;
        w *= zoomLevel;
        h *= zoomLevel;
        if (w >= 0 && h >= 0) {
            if (this.isTranslate) {
                x += this.translateX;
                y += this.translateY;
            }

            this.setColor(color);
            this.a = 0.5F;
            String key = "fr" + this.r + this.gl + this.b + this.a;
            Texture rgb_texture = (Texture) cachedTextures.get(key);
            if (rgb_texture == null) {
                Pixmap p = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
                p.setColor(this.r, this.b, this.gl, this.a);
                p.drawPixel(0, 0);
                rgb_texture = new Texture(p);
                p.dispose();
                this.cache(key, rgb_texture);
            } else {
                this.g.draw(rgb_texture, (float) x, (float) y, 0.0F, 0.0F, 1.0F, 1.0F, (float) w, (float) h, 0.0F, 0, 0, 1, 1, false, false);
            }
        }
    }

    public void fillRect(int x, int y, int w, int h, int color, int alpha, boolean useClip) {
        x *= zoomLevel;
        y *= zoomLevel;
        w *= zoomLevel;
        h *= zoomLevel;
        if (w >= 0 && h >= 0 && !this.isClipWithWHZero()) {
            if (this.isTranslate) {
                x += this.translateX;
                y += this.translateY;
            }

            String key = "fr" + this.r + this.gl + this.b + this.a + color;
            Texture rgb_texture = (Texture) cachedTextures.get(key);
            if (rgb_texture == null) {
                this.setColor(color, 0.5F);
                Pixmap p = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
                p.setColor(this.r, this.b, this.gl, this.a);
                p.drawPixel(0, 0);
                rgb_texture = new Texture(p);
                p.dispose();
                this.cache(key, rgb_texture);
            } else {
                if (this.isClip && useClip) {
                    this.beginClip();
                }

                this.g.draw(rgb_texture, (float) x, (float) y, 0.0F, 0.0F, 1.0F, 1.0F, (float) w, (float) h, 0.0F, 0, 0, 1, 1, false, false);
                if (this.isClip && useClip) {
                    this.endClip0();
                }

            }
        }
    }

    public void _drawArc(Texture imgscr, int x, int y, float radius, float start, float degrees, ShapeRenderer.ShapeType shapeType) {
        x *= zoomLevel;
        y *= zoomLevel;
        boolean flipX = false;
        boolean flipY = true;
        int scX = 1;
        int scY = 1;
        int y0 = 0;
        int orx = 0;
        int ory = 0;
        int ixA = 0, iyA = 0;
    }
}
