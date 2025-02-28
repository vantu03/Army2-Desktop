package model;

import CLib.Image;
import CLib.mGraphics;
import CLib.mImage;
import Debug.Debug;
import coreLG.CCanvas;

public class FrameImage {
    public int frameWidth;
    public int frameHeight;
    public int nFrame;
    public mImage imgFrame;
    private int[] pos;
    private int totalHeight;
    private mImage[] imgList;
    private boolean isRotate;

    public FrameImage(Image img, int width, int height, boolean isTest) {
        this.imgFrame = new mImage(img);
        this.frameWidth = width;
        this.frameHeight = height;
        this.totalHeight = img.getHeight();
        this.nFrame = this.totalHeight / height;
        this.pos = new int[this.nFrame];

        for (int i = 0; i < this.nFrame; ++i) {
            this.pos[i] = i * height;
        }

    }

    public FrameImage(Image img, int width, int height) {
        this.imgFrame = new mImage(img);
        this.frameWidth = width;
        this.frameHeight = height;
        this.totalHeight = img.getHeight();
        this.nFrame = this.totalHeight / height;
        this.pos = new int[this.nFrame];

        for (int i = 0; i < this.nFrame; ++i) {
            this.pos[i] = i * height;
        }

    }

    public FrameImage(Image img, int n) {
        this.imgFrame = new mImage(img);
        this.frameWidth = img.getWidth();
        this.frameHeight = img.getHeight();
        this.nFrame = n;
        this.imgList = new mImage[this.nFrame];
        this.isRotate = true;
        int angle = 360 / n;

        for (int i = 1; i < this.nFrame; ++i) {
            int nextAngle = i * angle;
            final int icc = i;
            if (nextAngle != 0 && nextAngle != 90 && nextAngle != 180 && nextAngle != 270) {
                CCanvas.rotateImage(img, i * angle, (mGraphics) null, -1, -1, false, new IAction2() {
                    public void perform(Object object) {
                        FrameImage.this.imgList[icc] = new mImage((Image) object);
                    }
                });
            }
        }

    }

    public void drawRegionFrame(int idx, int x, int y, int trans, int anchor, mGraphics g, int bulletID, int n) {
        g.drawRegion(this.imgFrame, 0, (bulletID * n + idx) * this.frameWidth, this.frameWidth, this.frameHeight, trans, x, y, anchor, false);
    }

    public void drawFrame(int idx, int x, int y, int trans, int anchor, mGraphics g) {
        if (!this.isRotate) {
            if (idx >= 0 && idx < this.nFrame) {
                g.drawRegion(this.imgFrame, 0, this.pos[idx], this.frameWidth, this.frameHeight, trans, x, y, anchor, true);
            }
        } else if (idx >= 0 && idx < this.nFrame) {
            int nextAngle = idx * (360 / this.nFrame);
            Debug.isDraw = true;
            if (nextAngle == 0) {
                g.drawImage(this.imgFrame, x, y, 3, true);
            } else if (nextAngle == 90) {
                g.drawRegion(this.imgFrame, 0, 0, this.imgFrame.image.getWidth(), this.imgFrame.image.getHeight(), 4, x, y, anchor, true);
            } else if (nextAngle == 270) {
                g.drawRegion(this.imgFrame, 0, 0, this.frameWidth, this.frameHeight, 7, x, y, anchor, true);
            } else if (nextAngle == 180) {
                g.drawRegion(this.imgFrame, 0, 0, this.frameWidth, this.frameHeight, 1, x, y, anchor, true);
            } else {
                g.drawRegion(this.imgList[idx], 0, 0, this.frameWidth, this.frameHeight, trans, x, y, anchor, true);
            }

            Debug.isDraw = false;
        }

    }

    public void fillFrame(int idx, int x, int y, int percentFill, int trans, int anchor, mGraphics g, boolean isClip) {
        if (idx >= 0 && idx < this.nFrame) {
            g.drawRegion(this.imgFrame, 0, this.pos[idx], this.frameWidth * percentFill / 100, this.frameHeight, 0, x, y, anchor, isClip);
        }

    }

    public void drawFrame(int idx, int x, int y, int trans, int anchor, mGraphics g, boolean isClip) {
        if (!this.isRotate) {
            if (idx >= 0 && idx < this.nFrame) {
                g.drawRegion(this.imgFrame, 0, this.pos[idx], this.frameWidth, this.frameHeight, trans, x, y, anchor, isClip);
            }
        } else if (idx >= 0 && idx < this.nFrame) {
            int nextAngle = idx * (360 / this.nFrame);
            if (nextAngle == 0) {
                g.drawImage(this.imgFrame, x, y, 3, isClip);
            } else if (nextAngle == 90) {
                g.drawRegion(this.imgFrame, 0, 0, this.imgFrame.image.getWidth(), this.imgFrame.image.getHeight(), 4, x, y, anchor, isClip);
            } else if (nextAngle == 270) {
                g.drawRegion(this.imgFrame, 0, 0, this.frameWidth, this.frameHeight, 7, x, y, anchor, isClip);
            } else if (nextAngle == 180) {
                g.drawRegion(this.imgFrame, 0, 0, this.frameWidth, this.frameHeight, 1, x, y, anchor, isClip);
            } else {
                g.drawRegion(this.imgList[idx], 0, 0, this.frameWidth, this.frameHeight, trans, x, y, anchor, isClip);
            }
        }

    }

    public void unload() {
        this.imgFrame = null;
        this.pos = null;
    }
}
