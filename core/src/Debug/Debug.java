package Debug;

import CLib.mGraphics;
import CLib.mImage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import coreLG.CCanvas;
import model.Font;

public class Debug {
    private static Debug instance;
    public static boolean isDraw;
    private mImage[] bigImage;
    public static int numTileInMap;
    int w;
    int h;
    public static int xM;
    public static int yM;
    public mImage imageTest;
    public static boolean isLockCam;
    Pixmap p;
    public static int inFillImage = 1;

    public static Debug gI() {
        if (instance == null) {
            instance = new Debug();
        }

        return instance;
    }

    public void setup() {
        this.w = Gdx.graphics.getWidth();
        this.h = Gdx.graphics.getHeight();
        this.bigImage = new mImage[10];
    }

    public void paint(mGraphics _mGraphic) {
    }

    public void create_RBGImage() {
    }

    public void update() {
    }

    private void onGizmoMouse(mGraphics g, int x, int y, int align) {
    }

    public static void paintZoneTouch(mGraphics graphics, int xDraw, int yyDraw, String content, int width, int height, int anchor) {
    }

//    public static void paintInfomationInMap(mGraphics graphics, String content) {
//        int xOgr = true;
//        int yIgr = false;
//    }

    public static void onPaintPerformanceInGame() {
    }

    public static void ActionClick(String content) {
    }

    public static void onKeyPress(int keycode) {
    }

//    public static void paintFPS(mGraphics graphics, String content) {
//        int xOgr = true;
//        int yIgr = false;
//        Font.borderFont.drawString(graphics, "FPS: " + (int) (1.0F / Gdx.graphics.getDeltaTime()), CCanvas.width / 2, 50, 3);
//    }

    public static int getNumberFingerOnScreen() {
        int number = 0;
        if (Gdx.input.isTouched()) {
            for (int i = 0; i < 10; ++i) {
                if (Gdx.input.isTouched(i)) {
                    ++number;
                }
            }
        }

        return number;
    }
}
