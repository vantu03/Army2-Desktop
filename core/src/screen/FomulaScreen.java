package screen;

import CLib.mGraphics;
import coreLG.CCanvas;

import java.util.Vector;

import model.Fomula;
import model.IAction;
import model.Language;
import network.Command;
import network.GameService;

public class FomulaScreen extends CScreen {
    public Vector fomulas = new Vector();
    public int select = 0;
    public CScreen lastScr;
    public int cmtoY;
    public int cmy;
    public int cmdy;
    public int cmvy;
    public int cmyLim;
    int pa = 0;
    boolean trans = false;

    public void setFomula(Fomula f) {
        this.fomulas.addElement(f);
    }

    public void show() {
    }

    public void show(CScreen last) {
        this.nameCScreen = "FomulaScreen screen!";
        this.lastScr = last;
        this.commandInit();
        super.show();
    }

    public void moveCamera() {
        if (this.cmy != this.cmtoY) {
            this.cmvy = this.cmtoY - this.cmy << 2;
            this.cmdy += this.cmvy;
            this.cmy += this.cmdy >> 4;
            this.cmdy &= 15;
        }

    }

    public void input() {
    }

    public void commandInit() {
        this.right = new Command(Language.back(), new IAction() {
            public void perform() {
                FomulaScreen.this.lastScr.show();
            }
        });
        this.left = new Command("Menu", new IAction() {
            public void perform() {
                Vector menu = new Vector();

                for (int i = 0; i < FomulaScreen.this.fomulas.size(); ++i) {
                    final int dem = i;
                    menu.addElement(new Command(Language.congthuccap() + " " + (i + 1), new IAction() {
                        public void perform() {
                            FomulaScreen.this.select = dem;
                            FomulaScreen.this.commandInit();
                        }
                    }));
                }

                CCanvas.menu.startAt(menu, 0);
            }
        });
        if (((Fomula) this.fomulas.elementAt(this.select)).finish) {
            this.center = new Command(Language.chedo(), new IAction() {
                public void perform() {
                    Fomula f = (Fomula) FomulaScreen.this.fomulas.elementAt(FomulaScreen.this.select);
                    GameService.gI().getFomula((byte) f.ID, (byte) 2, (byte) FomulaScreen.this.select);
                }
            });
        } else {
            this.center = null;
        }

    }

    public void paint(mGraphics g) {
        paintDefaultBg(g);
        g.translate(0, -this.cmy);
        ((Fomula) this.fomulas.elementAt(this.select)).paint(g);
        super.paint(g);
    }

    public void update() {
        this.moveCamera();
        ((Fomula) this.fomulas.elementAt(this.select)).update();
        this.cmyLim = 105 + this.fomulas.size() * 30 - (CCanvas.hieght - cmdH - 25);
        if (this.cmyLim < 0) {
            this.cmyLim = 0;
        }

    }

    public Fomula getFomula(int index) {
        return (Fomula) this.fomulas.elementAt(index);
    }
}
