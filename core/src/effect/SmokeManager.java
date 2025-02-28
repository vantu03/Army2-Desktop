package effect;

import CLib.mGraphics;

import java.util.Vector;

import model.CRes;
import screen.GameScr;

public class SmokeManager {
    public Vector<Smoke> smokes = new Vector();
    public boolean notPaint = false;

    public void addSmoke(int x, int y, byte Type) {
        int max = 300;
        if (GameScr.curGRAPHIC_LEVEL == 1) {
            max = 20;
        }

        if (GameScr.curGRAPHIC_LEVEL == 2) {
            max = 0;
        }

        if (this.smokes.size() < (Type != 7 && Type != 13 && Type != 19 && Type != 21 ? 30 : max)) {
            if (Type == 4) {
                if (CRes.random(2) == 0) {
                    this.smokes.addElement(new Smoke(x, y, Type));
                }
            } else {
                this.smokes.addElement(new Smoke(x, y, Type));
            }
        }

    }

    public void addLazer(int xF, int yF, int xT, int yT, int type) {
        this.smokes.addElement(new Smoke(xF, yF, xT, yT, type));
    }

    public void addBat(int xF, int yF, int xT, int yT) {
        this.smokes.addElement(new Smoke(xF, yF, xT, yT));
    }

    public void addRock(int x, int y, int vx, int vy, byte Type) {
        byte max = 100;
        if (GameScr.curGRAPHIC_LEVEL == 1) {
            max = 20;
        } else if (GameScr.curGRAPHIC_LEVEL == 2) {
            max = 10;
        }

        if (Type == 10) {
            this.smokes.addElement(new Smoke(x, y, vx, vy, Type));
        } else if (Type != 6 && Type != 8 && Type != 9 && Type != 12) {
            if (this.smokes.size() < max) {
                this.smokes.addElement(new Smoke(x, y, vx, vy, Type));
            }
        } else if (this.smokes.size() < max) {
            this.smokes.addElement(new Smoke(x, y, vx, vy, Type));
        }

    }

    public void update() {
        for (int i = 0; i < this.smokes.size(); ++i) {
            ((Smoke) this.smokes.elementAt(i)).update();
        }

    }

    public void paint(mGraphics g) {
        if (!this.notPaint) {
            for (int i = 0; i < this.smokes.size(); ++i) {
                ((Smoke) this.smokes.elementAt(i)).paint(g);
            }

        }
    }

    public void removeSmoke(Object smoke) {
        this.smokes.removeElement(smoke);
    }

    public void onClearMap() {
    }
}
