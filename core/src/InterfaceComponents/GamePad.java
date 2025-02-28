package InterfaceComponents;

import CLib.mGraphics;
import CLib.mImage;

public class GamePad {
    public static mImage imgAnalog1;
    public static mImage imgAnalog2;
    public int xC;
    public int yC;
    public int xM;
    public int yM;
    public int xMLast;
    public int yMLast;
    public int R = 28;
    public int r;
    public int d;
    public int xTemp;
    public int yTemp;
    int deltaX;
    int deltaY;
    int delta;
    int angle;
    boolean isGamePad = false;

    public void update(int i) {
    }

    private boolean checkPointerMove(int distance, int index) {
        return true;
    }

    private void resetHold() {
    }

    public void paint(mGraphics g) {
    }
}
