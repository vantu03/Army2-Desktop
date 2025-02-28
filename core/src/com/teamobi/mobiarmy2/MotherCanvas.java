package com.teamobi.mobiarmy2;

import CLib.mGraphics;
import CLib.mSystem;
import coreLG.CCanvas;
import model.CRes;

public abstract class MotherCanvas {
    public static int w;
    public static int h;
    public static int hw;
    public static int hh;
    public static int w4;
    public static int h4;
    public CCanvas tCanvas;
    public static int[] ipadPro = new int[]{2732, 2048};
    public static int[] ipadPro1 = new int[]{2224, 1668};
    public static int[] iphoneXSMAX = new int[]{2688, 1242};
    public static int[] ipad = new int[]{2048, 1563};
    public static int[] iphoneX = new int[]{2436, 1125};
    public static int[] iphoneXR = new int[]{1792, 828};
    public static int[] ipadMini = new int[]{1334, 750};
    public static int[] iphone5 = new int[]{1136, 640};
    public static int[] iphone4 = new int[]{960, 640};
    public static boolean bRun;
    public static int FPS = 50;

    private void checkZoomLevel(int wM, int hM) {
        if (GameMidlet.DEVICE == 0) {
            mGraphics.zoomLevel = 1;
        } else if (wM * hM >= 1228800) {
            mGraphics.zoomLevel = 4;
        } else if (wM * hM >= 691200) {
            mGraphics.zoomLevel = 3;
        } else if (wM * hM >= 240000) {
            mGraphics.zoomLevel = 2;
        }

        if (wM * hM >= 3166208) {
            mGraphics.zoomLevel = 6;
        } else if (wM * hM >= 2073600) {
            mGraphics.zoomLevel = 4;
        } else if (wM * hM >= 1000500) {
            mGraphics.zoomLevel = 3;
        } else if (wM * hM >= 727040) {
            mGraphics.zoomLevel = 3;
        } else if (wM * hM >= 384000) {
            mGraphics.zoomLevel = 2;
        } else {
            mGraphics.zoomLevel = 1;
        }

        if (mGraphics.zoomLevel > 1) {
            --mGraphics.zoomLevel;
        }

        w = wM / mGraphics.zoomLevel;
        h = hM / mGraphics.zoomLevel;
        hw = w / 2;
        hh = h / 2;
        w4 = w / 4;
        h4 = h / 4;
        CRes.out("======> Mother canvas zoom level = " + mGraphics.zoomLevel);
        if (CCanvas.isJ2ME() && CCanvas.width * CCanvas.hieght <= 82500) {
            GameMidlet.lowGraphic = true;
        }

    }

    public void displayMe(GameMidlet m) {
    }

//    public MotherCanvas(Context context) {
//        setFullScreenMode(true);
//        this.checkZoomLevel(this.getWidthL(), this.getHeightL());
//    }

    public int getHeightL() {
        return MainGame.getHeight();
    }

    public int getWidthL() {
        return MainGame.getWidth();
    }

    public static void setFullScreenMode(boolean i) {
    }

    public MotherCanvas() {
        setFullScreenMode(true);
        this.checkZoomLevel(this.getWidthL(), this.getHeightL());
    }

    public void start() {
    }

    public static String getPlatformName() {
        return System.getProperty("microedition.platform");
    }

    public int getWidthz() {
        int realWidth = this.getWidthL();
        return realWidth / mGraphics.zoomLevel + (realWidth % mGraphics.zoomLevel == 0 ? 0 : 1);
    }

    public int getHeightz() {
        int realHeight = this.getHeightL();
        return realHeight / mGraphics.zoomLevel + (realHeight % mGraphics.zoomLevel == 0 ? 0 : 1);
    }

    public abstract void onPointerDragged(int var1, int var2, int var3);

    public abstract void onPointerPressed(int var1, int var2, int var3, int var4);

    public abstract void onPointerReleased(int var1, int var2, int var3, int var4);

    public abstract void onPointerHolder(int var1, int var2, int var3);

    public abstract void onPointerHolder();

    protected abstract void update();

    public boolean hasPointerEvents() {
        return true;
    }

    public void run() {
    }

    public static int getSecond() {
        return (int) (mSystem.currentTimeMillis() / 1000L);
    }

    public boolean keyPressPc(int keycode) {
        switch (keycode) {
            case -22:
                CCanvas.keyHold[41] = true;
                CCanvas.keyPressed[41] = true;
                return true;
            case -21:
                CCanvas.keyPressed[40] = true;
                break;
            case 97:
                CCanvas.keyHold[34] = true;
                CCanvas.keyPressed[34] = true;
                return true;
            case 98:
                CCanvas.keyPressed[51] = true;
                return true;
            case 99:
                CCanvas.keyPressed[48] = true;
                return true;
            case 100:
                CCanvas.keyHold[36] = true;
                CCanvas.keyPressed[36] = true;
                return true;
            case 101:
                CCanvas.keyHold[43] = true;
                CCanvas.keyPressed[43] = true;
                return true;
            case 103:
                CCanvas.keyHold[31] = true;
                CCanvas.keyPressed[31] = true;
                return true;
            case 104:
                CCanvas.keyPressed[33] = true;
                return true;
            case 105:
                CCanvas.keyHold[46] = true;
                CCanvas.keyPressed[46] = true;
                return true;
            case 106:
                CCanvas.keyHold[35] = true;
                CCanvas.keyPressed[35] = true;
                return true;
            case 107:
                CCanvas.keyPressed[37] = true;
                return true;
            case 108:
                CCanvas.keyHold[39] = true;
                CCanvas.keyPressed[39] = true;
                break;
            case 109:
                CCanvas.keyHold[42] = true;
                CCanvas.keyPressed[42] = true;
                return true;
            case 111:
                CCanvas.keyHold[44] = true;
                CCanvas.keyPressed[44] = true;
                return true;
            case 112:
                CCanvas.keyPressed[50] = true;
                return true;
            case 113:
                CCanvas.keyPressed[47] = true;
                return true;
            case 115:
                CCanvas.keyPressed[38] = true;
                return true;
            case 119:
                CCanvas.keyPressed[32] = true;
                return true;
            case 120:
                CCanvas.keyPressed[49] = true;
                return true;
            case 121:
                CCanvas.keyHold[45] = true;
                CCanvas.keyPressed[45] = true;
                return true;
        }

        return false;
    }

    public boolean keyReleasedPc(int keycode) {
        switch (keycode) {
            case -22:
                CCanvas.keyHold[41] = false;
                CCanvas.keyPressed[41] = false;
                return true;
            case -21:
                CCanvas.keyPressed[40] = false;
                break;
            case 97:
                CCanvas.keyHold[34] = false;
                CCanvas.keyPressed[34] = false;
                return true;
            case 98:
                CCanvas.keyHold[51] = false;
                CCanvas.keyPressed[51] = false;
                return true;
            case 99:
                CCanvas.keyHold[48] = false;
                CCanvas.keyPressed[48] = false;
                return true;
            case 100:
                CCanvas.keyHold[36] = false;
                CCanvas.keyPressed[36] = false;
                return true;
            case 101:
                CCanvas.keyPressed[43] = false;
                return true;
            case 103:
                CCanvas.keyHold[31] = false;
                CCanvas.keyPressed[31] = false;
                return true;
            case 104:
                CCanvas.keyPressed[33] = false;
                return true;
            case 105:
                CCanvas.keyPressed[46] = false;
                return true;
            case 106:
                CCanvas.keyHold[35] = false;
                CCanvas.keyPressed[35] = false;
                return true;
            case 107:
                CCanvas.keyPressed[37] = false;
                return true;
            case 108:
                CCanvas.keyHold[39] = false;
                CCanvas.keyPressed[39] = false;
                break;
            case 109:
                CCanvas.keyPressed[42] = false;
                return true;
            case 111:
                CCanvas.keyPressed[44] = false;
                return true;
            case 112:
                CCanvas.keyHold[50] = false;
                CCanvas.keyPressed[50] = false;
                return true;
            case 113:
                CCanvas.keyHold[47] = false;
                CCanvas.keyPressed[47] = false;
                return true;
            case 115:
                CCanvas.keyPressed[38] = false;
                return true;
            case 119:
                CCanvas.keyPressed[32] = false;
                return true;
            case 120:
                CCanvas.keyHold[49] = false;
                CCanvas.keyPressed[49] = false;
                return true;
            case 121:
                CCanvas.keyPressed[45] = false;
                return true;
        }

        return false;
    }

    public abstract void onClearMap();
}
