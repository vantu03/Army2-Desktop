package screen;

import CLib.mGraphics;
import CLib.mImage;
import CLib.mSystem;
import InApp.MainActivity;
import coreLG.CCanvas;
import effect.Cloud;

import java.util.Vector;

import model.AvatarInfo;
import model.CRes;
import model.Font;
import model.IAction;
import model.Language;
import model.MoneyInfo;
import network.Command;

public class MoneyScrIOS extends CScreen {
    Vector avs;
    public int selected;
    public int cmtoY;
    public int cmy;
    public int cmdy;
    public int cmvy;
    public int cmyLim;
    public int xL;
    public static mImage imgCoin;
    public static String url_Nap;
    int pa = 0;
    boolean trans = false;

    static {
        try {
            imgCoin = mImage.createImage("/coin.png");
        } catch (Exception var1) {
            var1.printStackTrace();
        }

    }

    public int priceFromID(int id) {
        for (int i = 0; i < this.avs.size(); ++i) {
            AvatarInfo av = (AvatarInfo) this.avs.elementAt(i);
            if (av.ID == id) {
                return av.price;
            }
        }

        return 0;
    }

    public void startAnimLeft() {
        this.xL = -CCanvas.width;
    }

    public MoneyScrIOS() {
        this.nameCScreen = " MoneyScrIOS screen!";
        IAction buyAction = new IAction() {
            public void perform() {
                MoneyScrIOS.this.doBuy();
            }
        };
        this.center = new Command(Language.select(), buyAction);
        this.right = new Command(Language.close(), new IAction() {
            public void perform() {
                CCanvas.menuScr.show();
            }
        });
    }

    protected void doBuy() {
        if (this.avs != null && this.avs.size() > 0) {
            if (this.selected >= 5) {
                MoneyInfo mi = (MoneyInfo) this.avs.elementAt(this.selected);
                if (mi != null && mi.id.equals("napWeb") && !CRes.isNullOrEmpty(url_Nap)) {
                    mSystem.openUrl(url_Nap);
                }
            } else {
                MainActivity.makePurchase(MainActivity.google_productIds[this.selected]);
            }

        } else {
            CCanvas.startOKDlg("Error Inapp purchase");
        }
    }

    public MoneyInfo getSelectMoney() {
        return this.avs == null ? null : (MoneyInfo) this.avs.elementAt(this.selected);
    }

    public void showInputCard() {
    }

    public void startAnimRight() {
        this.xL = CCanvas.width << 1;
    }

    public void moveCamera() {
        if (this.cmy != this.cmtoY) {
            this.cmvy = this.cmtoY - this.cmy << 2;
            this.cmdy += this.cmvy;
            this.cmy += this.cmdy >> 4;
            this.cmdy &= 15;
        }

    }

    public void paint(mGraphics g) {
        paintDefaultBg(g);
        Cloud.paintCloud(g);

        for (int i = 0; i <= CCanvas.width; i += 32) {
            g.drawImage(PrepareScr.imgBack, i, CCanvas.hieght - 62, 0, false);
        }

        g.translate(this.xL, 0);
        Font.bigFont.drawString(g, Language.charge(), 10, 3, 0);
        g.setColor(1407674);
        g.fillRect(0, 25, CCanvas.width, ITEM_HEIGHT, false);
        Font.normalYFont.drawString(g, Language.payMethod(), 10, 28, 0);
        this.paintRichList(g);
        super.paint(g);
    }

    private void paintRichList(mGraphics g) {
        if (this.avs != null) {
            if (this.avs.size() > 0) {
                g.translate(0, ITEM_HEIGHT + 25);
                g.translate(0, -this.cmy);
                int y = 0;

                for (int i = 0; i < this.avs.size(); ++i) {
                    if (i == this.selected) {
                        g.setColor(16765440);
                        g.fillRect(0, y, CCanvas.width, 20, false);
                    }

                    MoneyInfo avi = (MoneyInfo) this.avs.elementAt(i);
                    g.drawImage(imgCoin, 10, y + 2, 0, false);
                    String content = avi.info + "          " + avi.smsContent;
                    Font.borderFont.drawString(g, content, 40, y + 2, 0);
                    if (!CCanvas.isTouch) {
                        y += 20;
                    } else {
                        y += 30;
                    }
                }

            }
        }
    }

    public void onPointerPressed(int xPress, int yPress, int index) {
        super.onPointerPressed(xPress, yPress, index);
    }

    public void onPointerDragged(int xDrag, int yDrag, int index) {
        super.onPointerDragged(xDrag, yDrag, index);
        if (this.avs != null) {
            if (!this.trans) {
                this.pa = this.cmy;
                this.trans = true;
            }

            this.cmtoY = this.pa + (CCanvas.pyFirst[index] - yDrag);
            if (this.cmtoY < 0) {
                this.cmtoY = 0;
            }

            if (this.cmtoY > this.cmyLim) {
                this.cmtoY = this.cmyLim;
            }

            if (this.selected >= this.avs.size() - 1 || this.selected == 0) {
                this.cmy = this.cmtoY;
            }

        }
    }

    public void onPointerHold(int x, int y2, int index) {
        super.onPointerHold(x, y2, index);
    }

    public void onPointerReleased(int xReleased, int yReleased, int index) {
        super.onPointerReleased(xReleased, yReleased, index);
        if (this.avs != null) {
            if (CCanvas.isPointerDown[index]) {
                if (!this.trans) {
                    this.pa = this.cmy;
                    this.trans = true;
                }

                this.cmtoY = this.pa + (CCanvas.pyFirst[index] - yReleased);
                if (this.cmtoY < 0) {
                    this.cmtoY = 0;
                }

                if (this.cmtoY > this.cmyLim) {
                    this.cmtoY = this.cmyLim;
                }

                if (this.selected >= this.avs.size() - 1 || this.selected == 0) {
                    this.cmy = this.cmtoY;
                }
            }

            this.trans = false;
            int aa = (this.cmtoY + yReleased - ITEM_HEIGHT - 25) / 30;
            if (aa == this.selected && CCanvas.isDoubleClick) {
                this.center.action.perform();
            }

            this.selected = aa;
            if (this.selected < 0) {
                this.selected = 0;
            }

            if (this.selected > this.avs.size() - 1) {
                this.selected = this.avs.size() - 1;
            }

        }
    }

    public void update() {
        if (this.xL != 0) {
            this.xL += -this.xL >> 1;
        }

        if (this.xL == -1) {
            this.xL = 0;
        }

        this.moveCamera();
        Cloud.updateCloud();
    }

    public void setAvatarList(Vector avatarList) {
        this.avs = avatarList;
        this.selected = 0;
        this.cmy = this.cmtoY = 0;
        this.cmyLim = avatarList.size() * 20 - (CCanvas.hh - 40);
        if (this.cmyLim < 0) {
            this.cmyLim = 0;
        }

    }
}
