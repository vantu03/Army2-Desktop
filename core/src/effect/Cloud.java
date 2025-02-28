package effect;

import CLib.mGraphics;
import CLib.mImage;
import coreLG.CCanvas;
import map.Background;
import screen.PrepareScr;

public class Cloud {
    public static mImage imgSun;
    public static mImage[] imgCloud;
    private static int[] yCloud;
    private static int[] xCloud;
    private static int[] dxCloud;
    private static int xB1 = 60;
    private static int xB2 = 170;

    static {
        xCloud = new int[]{0, CCanvas.hw, 20, CCanvas.width / 2 + 10};
        yCloud = new int[]{30, 80, 40, 0};
        dxCloud = new int[]{2, 1, 1, 1};

        try {
            imgSun = PrepareScr.imgSun;
            imgCloud = PrepareScr.imgCloud;
        } catch (Exception var1) {
            var1.printStackTrace();
        }

    }

    public static void updateCloud() {
        int[] var10000;
        for (int i = 0; i < 2; ++i) {
            var10000 = xCloud;
            var10000[i] += dxCloud[i];
            if (xCloud[i] > CCanvas.width) {
                xCloud[i] = -imgCloud[i].image.getWidth();
            }
        }

        var10000 = xCloud;
        var10000[3] += dxCloud[3];
        if (xCloud[2] > CCanvas.width) {
            xCloud[2] = -imgCloud[2].image.getWidth();
        }

        if (xCloud[3] > CCanvas.width) {
            xCloud[3] = -imgCloud[2].image.getWidth();
        }

        if (CCanvas.gameTick % 2 == 0) {
            var10000 = xCloud;
            var10000[2] += dxCloud[2];
        }

    }

    public static void balloonUpdate() {
        if (CCanvas.gameTick % 2 == 0) {
            ++xB2;
        }

        if (CCanvas.gameTick % 3 == 0) {
            ++xB1;
        }

        if (xB1 > CCanvas.width) {
            xB1 = -Background.balloon.image.getWidth();
        }

        if (xB2 > CCanvas.width) {
            xB2 = -Background.balloon.image.getWidth();
        }

    }

    public static void paintCloud(mGraphics g) {
        g.drawImage(imgSun, 30, 40, 0, false);

        for (int i = 2; i >= 0; --i) {
            g.drawImage(imgCloud[i], xCloud[i], yCloud[i], 0, false);
        }

    }

    public static void paintBalloonWithCloud(mGraphics g) {
        g.drawImage(Background.sun, CCanvas.width - 20, 20, mGraphics.TOP | mGraphics.RIGHT, false);
        g.drawImage(imgCloud[2], xCloud[2], 100, 0, false);
        g.drawImage(imgCloud[2], xCloud[3], 20, 0, false);
        g.drawImage(imgCloud[1], xCloud[1], yCloud[1], 0, false);
        g.drawImage(Background.balloon, xB1, 20, 0, false);
        g.drawImage(Background.balloon, xB2, 50, 0, false);
        g.drawImage(imgCloud[0], xCloud[0], yCloud[0], 0, false);
    }

    public static void paintSimpleClound(int CloundType, int x, int y, mGraphics g) {
        g.drawImage(imgCloud[CloundType], x, y, 0, false);
    }
}
