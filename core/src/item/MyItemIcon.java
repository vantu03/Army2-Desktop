package item;

import CLib.mGraphics;
import coreLG.CCanvas;
import model.CRes;
import model.Font;
import model.IAction;
import screen.CScreen;
import screen.PrepareScr;

public class MyItemIcon {
    int[] icon;
    public int select;
    int blank;
    int hBlank;
    int indexW;
    int indexH;
    int rangeCheck;
    public int shopW;
    int shopH;
    int size;
    int fullSize;
    int num;
    public static final byte UP = 0;
    public static final byte DOWN = 1;
    public static final byte LEFT = 2;
    public static final byte RIGHT = 3;
    public boolean isSetClip;
    int wP = 4;
    int line;
    private IAction _iAction;
    public String titleTem;
    int pa = 0;
    boolean trans = false;
    static int cmtoY;
    static int cmy;
    static int cmdy;
    static int cmvy;
    int x;
    int y;
    private int xTitle;
    private int yTitle;

    public MyItemIcon(int[] Icon, int Blank, int IndexW, int ShopH) {
        this.icon = Icon;
        this.blank = Blank;
        this.init();
        this.indexW = IndexW;
        this.indexH = this.icon.length / this.indexW;
        if (Icon.length % this.indexW != 0) {
            ++this.indexH;
        }

        this.shopW = this.indexW * (this.blank + this.size);
        this.shopH = ShopH * (this.blank + this.size);
        if (CCanvas.isTouch) {
            this.shopH -= 2;
        }

        this.rangeCheck = (this.blank + this.size) * 2;
        this.isSetClip = true;
    }

    public MyItemIcon(int[] Icon, int Blank, int IndexW, int IndexH, boolean isSimple) {
        this.icon = Icon;
        this.blank = Blank;
        this.init();
        this.indexW = IndexW;
        this.indexH = IndexH;
        this.isSetClip = false;
    }

    void init() {
        this.num = this.icon.length;
        this.hBlank = this.blank / 2;
        if (!CCanvas.isTouch) {
            this.size = 16;
            this.wP = 0;
        } else {
            this.size = 37;
            this.wP = 9;
        }

        this.fullSize = this.blank + this.size;
    }

    public void setIAction(IAction iAction) {
        this._iAction = iAction;
    }

    public void checkCmtoY(int Select) {
        int a = Select / this.indexW * (this.blank + this.size);
        if (a > cmy + this.rangeCheck) {
            cmtoY = a - this.rangeCheck;
        } else if (a < cmy) {
            cmtoY = a;
        }

    }

    public void moveCamera() {
        if (cmy != cmtoY) {
            cmvy = cmtoY - cmy << 2;
            cmdy += cmvy;
            cmy += cmdy >> 4;
            cmdy &= 15;
        }

    }

    public void update() {
    }

    public void mainLoop() {
        this.moveCamera();
    }

    public void paint(int x, int y, mGraphics g, boolean isPainNum, int[] numInIcon) {
        this.x = x;
        this.y = y;
        if (this.isSetClip) {
            g.setClip(x - 2, y - 2, this.shopW + 4, this.shopH + 18);
            g.setColor(4156571);
            g.fillRoundRect(x - 2, y - 2, this.shopW + 4, this.shopH + 6, 6, 7, true);
            g.translate(0, -cmy);
        }

        int j = 0;
        int i = 0;
        int x1 = 0;
        int y1 = 0;
        if (CCanvas.isTouch) {
            g.setColor(16767817);
        } else {
            g.setColor(CCanvas.gameTick % 5 > 2 ? 16777215 : 0);
        }

        int SIZE;
        if (CCanvas.isTouch) {
            SIZE = 37 + this.blank;
        } else {
            SIZE = this.fullSize;
        }

        for (int n = 0; n < this.num; ++n) {
            x1 = x + i * SIZE + this.hBlank;
            y1 = y + j * SIZE + this.hBlank;
            g.setClip(x, y + cmy, this.shopW, this.shopH);
            if (this.select == n) {
                g.fillRect(x1 - (CCanvas.isTouch ? 2 : 1), y1 - (CCanvas.isTouch ? 2 : 1), this.size + this.hBlank, this.size + this.hBlank, true);
            }

            Item.DrawItem(g, this.icon[n], x1 + this.wP, y1 + this.wP);
            if (isPainNum && numInIcon[n] >= 0 && n <= numInIcon.length - 1) {
                Font.smallFontYellow.drawString(g, String.valueOf(numInIcon[n]), x1 + 9 + this.wP, y1 + 10 + this.wP, 0);
            }

            ++i;
            if (i > this.indexW - 1) {
                i = 0;
                ++j;
            }
        }

        if (this.isSetClip) {
            g.translate(0, cmy);
            g.setClip(0, 0, CCanvas.width, CCanvas.hieght);
        }

        if (!CRes.isNullOrEmpty(this.titleTem)) {
            Font.borderFont.drawString(g, this.titleTem, this.xTitle, this.yTitle, 2, false);
        }

    }

    public void setPosTitle(int x, int y) {
        this.xTitle = x;
        this.yTitle = y;
    }

    public void onPointerDragged(int x, int y, int index) {
        int SIZE = 37 + this.blank;
        if (CCanvas.isPointer(this.x, this.y, this.shopW + 4, this.shopH + 6, index)) {
            if (!this.trans) {
                this.pa = cmy;
                this.trans = true;
            }

            cmtoY = this.pa + (CCanvas.pyFirst[index] - y) + 2;
            if (cmtoY < 0) {
                cmtoY = 0;
            }

            if (cmtoY > this.indexH * SIZE - this.shopH) {
                cmtoY = this.indexH * SIZE - this.shopH;
            }
        }

    }

    public void onPointerPressed(int xScreen, int yScreen, int index) {
        
       if (CCanvas.keyPressed[2] || CCanvas.keyPressed[4] || CCanvas.keyPressed[6] || CCanvas.keyPressed[8]) {
            if (CCanvas.keyPressed[2]) {
                this.select -= 4;
            }
            if (CCanvas.keyPressed[8]) {
                this.select += 4;
            }
            if (CCanvas.keyPressed[4]) {
                this.select--;
            }
            if (CCanvas.keyPressed[6]) {
                this.select++;
            }
            if (select > this.icon.length - 1) {
                select = 0;
            }
            if (select < 0) {
                select = this.icon.length - 1;
            }
            cmtoY = (select / 4) * 40 - 20;
//            if (cmtoY > ) {
//                cmtoY = cmyILim;
//            }
            if (cmtoY < 0) {
                cmtoY = 0;
            }
            CScreen.clearKey();
       }
    }

    public void onPointerReleased(int xRealsed, int yRealsed, int index) {
        this.trans = false;
        int SIZE = 37 + this.blank;
        if (CCanvas.isPointer(this.x, this.y, this.shopW + 4, this.shopH + 6, index)) {
            int aa = (cmtoY + yRealsed - this.y - this.hBlank) / SIZE * this.indexW + (xRealsed - this.x - this.hBlank) / SIZE;
            if (aa == this.select) {
                if (CCanvas.curScr == CCanvas.shopItemScr && CCanvas.shopItemScr.left != null && CCanvas.isDoubleClick) {
                    CCanvas.shopItemScr.left.action.perform();
                }

                if (CCanvas.curScr == CCanvas.prepareScr && CCanvas.isDoubleClick && this._iAction != null) {
                    this._iAction.perform();
                }
            }

            if (aa >= 0 && aa < this.icon.length) {
                this.select = aa;
            }
        } else if (CCanvas.curScr == CCanvas.prepareScr) {
            PrepareScr var10000 = CCanvas.prepareScr;
        }

    }

    public void onPointerHolder(int xScreen, int yScreen, int index) {
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return this.shopW;
    }

    public int getHeight() {
        return this.shopH;
    }

    public void close() {
    }

    public void resetTranslate(mGraphics g) {
        g.translate(0, cmy);
    }
}
