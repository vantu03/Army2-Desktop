package screen;

import CLib.mGraphics;
import CLib.mImage;
import Equipment.Equip;
import Equipment.PlayerEquip;
import coreLG.CCanvas;
import coreLG.TerrainMidlet;
import effect.Cloud;
import item.Bullet;

import java.util.Vector;

import model.CRes;
import model.Font;
import model.IAction;
import model.Language;
import model.PlayerInfo;
import network.Command;
import network.GameService;


public class ChangePlayerCSr extends CScreen {
    public CScreen lastScr;
    public static final String[] GunDecription = new String[]{Language.bulletNumber() + "        ", Language.damage() + "   ", Language.windEffect() + "  "};
    public static final int[][] GunInfo = new int[10][];
    public static mImage lockImg;
    public static int curMenu;
    private int blankW;
    private int[] _iconX;
    private int _centerIX;
    private int nMainIcon;
    public static int gunPassiveIndexSub;
    public static final byte IS_UNLOCK = 1;
    public static final byte IS_LOCK = 0;
    public static byte[] isUnlock;
    public static int[] gunXu;
    public static int[] gunLuong;
    public static byte[] power;
    public static byte[] number;
    PlayerEquip[] equip;
    boolean isShowInfo;

    static {
        lockImg = GameScr.lockImg;
        curMenu = 2;
        gunPassiveIndexSub = 3;
        isUnlock = new byte[10];
        gunXu = new int[10];
        gunLuong = new int[10];
    }

    public ChangePlayerCSr() {
        this.blankW = w / 2 - 50;
        this.nMainIcon = 10;
        this.equip = new PlayerEquip[10];
        GunInfo[0] = new int[]{number[0], power[0], Bullet.BULLset_WIND_AFFECT[0]};
        GunInfo[1] = new int[]{number[1], power[1], Bullet.BULLset_WIND_AFFECT[1]};
        GunInfo[2] = new int[]{number[2], power[2], Bullet.BULLset_WIND_AFFECT[2]};
        GunInfo[3] = new int[]{number[3], power[3], Bullet.BULLset_WIND_AFFECT[3]};
        GunInfo[4] = new int[]{number[4], power[4], Bullet.BULLset_WIND_AFFECT[4]};
        GunInfo[5] = new int[]{number[5], power[5], Bullet.BULLset_WIND_AFFECT[5]};
        GunInfo[6] = new int[]{number[6], power[6], Bullet.BULLset_WIND_AFFECT[6]};
        GunInfo[7] = new int[]{number[7], power[7], Bullet.BULLset_WIND_AFFECT[7]};
        GunInfo[8] = new int[]{number[8], power[8], Bullet.BULLset_WIND_AFFECT[8]};
        GunInfo[9] = new int[]{number[9], power[9], 0};
        this.right = new Command(Language.close(), new IAction() {
            public void perform() {
                ChangePlayerCSr.this.doClose();
            }
        });
        int menuX = (w >> 1) - this.nMainIcon * (24 + this.blankW) / 2;
        this._iconX = new int[this.nMainIcon];

        for (int i = 0; i < this.nMainIcon; ++i) {
            this._iconX[i] = menuX + i * this.blankW;
        }

        this._centerIX = w >> 1;
        this.nameCScreen = "ChangePlayerCSr screen!";
    }

    protected void doClose() {
        this.lastScr.show();
    }

    public void doChangePlayer() {
        if (isUnlock[curMenu] == 0) {
            final Command xu = new Command(Language.muaXu(), new IAction() {
                public void perform() {
                    CCanvas.startYesNoDlg(Language.buyCharactor() + TerrainMidlet.myInfo.xu + Language.xu(), new IAction() {
                        public void perform() {
                            if (TerrainMidlet.myInfo.xu >= ChangePlayerCSr.gunXu[ChangePlayerCSr.curMenu]) {
                                GameService.gI().buyGun((byte) (ChangePlayerCSr.curMenu - ChangePlayerCSr.gunPassiveIndexSub), (byte) 0);
                                CCanvas.startWaitDlgWithoutCancel(Language.trading(), 15);
                            } else {
                                CCanvas.startOKDlg(Language.noMoney());
                            }

                        }
                    });
                }
            });
            final Command luong = new Command(Language.muaLuong(), new IAction() {
                public void perform() {
                    CCanvas.startYesNoDlg(Language.buyCharactor() + TerrainMidlet.myInfo.luong + Language.luong(), new IAction() {
                        public void perform() {
                            if (TerrainMidlet.myInfo.luong >= ChangePlayerCSr.gunLuong[ChangePlayerCSr.curMenu]) {
                                GameService.gI().buyGun((byte) (ChangePlayerCSr.curMenu - ChangePlayerCSr.gunPassiveIndexSub), (byte) 1);
                                CCanvas.startWaitDlgWithoutCancel(Language.trading(), 16);
                            } else {
                                CCanvas.startOKDlg(Language.noMoney());
                            }

                        }
                    });
                }
            });
            Command menu = new Command(Language.select(), new IAction() {
                public void perform() {
                    Vector menu = new Vector();
                    menu.addElement(xu);
                    menu.addElement(luong);
                    CCanvas.menu.startAt(menu, 2);
                }
            });
            if (gunLuong[curMenu] != -1) {
                this.center = menu;
            } else {
                this.center = xu;
            }

        } else {
            this.center = new Command(Language.select(), new IAction() {
                public void perform() {
                    CCanvas.startWaitDlg(Language.pleaseWait());
                    GameService.gI().changeGun((byte) ChangePlayerCSr.curMenu);
                }
            });
        }
    }

    public void getCurrEquip() {
        PlayerInfo p = TerrainMidlet.myInfo;

        for (int i = 0; i < 10; ++i) {
            boolean isVip = TerrainMidlet.isVip[i];
            short[] sung = new short[]{(short) i, 0, !isVip ? p.equipID[i][0] : p.equipVipID[i][0]};
            short[] non = new short[]{(short) i, 1, !isVip ? p.equipID[i][1] : p.equipVipID[i][1]};
            short[] giap = new short[]{(short) i, 2, !isVip ? p.equipID[i][2] : p.equipVipID[i][2]};
            short[] kinh = new short[]{(short) i, 3, !isVip ? p.equipID[i][3] : p.equipVipID[i][3]};
            short[] canh = new short[]{(short) i, 4, !isVip ? p.equipID[i][4] : p.equipVipID[i][4]};
            this.equip[i] = new PlayerEquip(new short[][]{sung, non, giap, kinh, canh});
        }

    }

    public void show(CScreen LastScr) {
        this.lastScr = LastScr;
        CCanvas.arrPopups.removeAllElements();
        CCanvas.msgPopup.nMessage = 0;
        this.getCurrEquip();
        this.doChangePlayer();
        super.show();
    }

    public void onPointerPressed(int x, int y2, int index) {
        super.onPointerPressed(x, y2, index);
        int next;
        int nextTo;
        if (CCanvas.keyPressed[4] || keyLeft) {
            curMenu = this.getLastP(curMenu, 1);
            next = this.getLastP(curMenu, 1);
            nextTo = this.getLastP(next, 1);
            this._iconX[next] = this._iconX[curMenu] - this.blankW;
            this._iconX[nextTo] = this._iconX[curMenu] - this.blankW * 2;
            this.doChangePlayer();
        }

        if (CCanvas.keyPressed[6] || keyRight) {
            curMenu = this.getNextP(curMenu, 1);
            next = this.getNextP(curMenu, 1);
            nextTo = this.getNextP(next, 1);
            this._iconX[next] = this._iconX[curMenu] + this.blankW;
            this._iconX[nextTo] = this._iconX[curMenu] + this.blankW * 2;
            this.doChangePlayer();
        }

        if (CCanvas.isPointerClick[index]) {
            if (CCanvas.isPointer(0, 0, CCanvas.width / 2 - 30, CCanvas.hieght - cmdH, index)) {
                curMenu = this.getLastP(curMenu, 1);
                next = this.getLastP(curMenu, 1);
                nextTo = this.getLastP(next, 1);
                this._iconX[next] = this._iconX[curMenu] - this.blankW;
                this._iconX[nextTo] = this._iconX[curMenu] - this.blankW * 2;
                this.doChangePlayer();
            }

            if (CCanvas.isPointer(CCanvas.width / 2 + 30, 0, CCanvas.width / 2 - 30, CCanvas.hieght - cmdH, index)) {
                curMenu = this.getNextP(curMenu, 1);
                next = this.getNextP(curMenu, 1);
                nextTo = this.getNextP(next, 1);
                this._iconX[next] = this._iconX[curMenu] + this.blankW;
                this._iconX[nextTo] = this._iconX[curMenu] + this.blankW * 2;
                this.doChangePlayer();
            }

            if (CCanvas.isPointer(CCanvas.width / 2 - 30, 0, 60, CCanvas.hieght - cmdH, index)) {
                this.center.action.perform();
            }
        }

        clearKey();
    }

    public void keyPressed(int keyCode) {
        super.keyPressed(keyCode);
    }

    public void input() {
    }

    public void update() {
        Cloud.updateCloud();
        this.moveMenu();
    }

    public void moveMenu() {
        this.isShowInfo = false;
        int dx = Math.max(Math.abs(this._centerIX - this._iconX[curMenu] >> 1), 1);
        int[] var10000;
        int i;
        if (this._iconX[curMenu] < this._centerIX) {
            for (i = 0; i < this.nMainIcon; ++i) {
                var10000 = this._iconX;
                var10000[i] += dx;
            }
        } else if (this._iconX[curMenu] > this._centerIX) {
            for (i = 0; i < this.nMainIcon; ++i) {
                var10000 = this._iconX;
                var10000[i] -= dx;
            }
        } else {
            this.isShowInfo = true;
        }

    }

    public void paint(mGraphics g) {
        paintDefaultBg(g);
        Cloud.paintCloud(g);
        int rW = 140;
        int rH = 124;
        int menuY = h >> 1;
        painRoundR(w / 2 - 70, h - rH >> 1, rW, rH, g);
        Font.bigFont.drawString(g, PrepareScr.GUN_NAME[curMenu], w / 2, menuY - 62, 2);
        this.drawMenuIcon(menuY - 11, g);
        if (this.isShowInfo) {
            int y = menuY - 4;
            int x = w / 2 - 60;
            Font.borderFont.drawString(g, GunDecription[0] + GunInfo[curMenu][0], x, y + 20, 0);
            Font.borderFont.drawString(g, GunDecription[1], x, y + 34, 0);
            Font.borderFont.drawString(g, GunDecription[2], x, y + 48, 0);
            g.setColor(0);
            g.fillRect(x + 70, y + 38, 50, 10, false);
            g.fillRect(x + 70, y + 52, 50, 10, false);
            g.setColor(4868682);
            g.fillRect(x + 72, y + 40, 46, 6, false);
            g.fillRect(x + 72, y + 54, 46, 6, false);
            g.setColor(16741888);
            g.fillRect(x + 72, y + 40, GunInfo[curMenu][1] * 46 / 35, 6, false);
            g.fillRect(x + 72, y + 54, GunInfo[curMenu][2] * 46 / 100, 6, false);
        }

        g.setColor(16777215);
        super.paint(g);
    }

    public static void painRoundR(int x, int y, int W, int H, mGraphics g) {
        g.setColor(8040447);
        g.fillRoundRect(x, y, W, H, 10, 10, false);
        g.setColor(16777215);
        g.drawRoundRect(x, y, W, H, 10, 10, false);
    }

    private void drawMenuIcon(int y, mGraphics g) {
        for (int i = 0; i < this.nMainIcon; ++i) {
            this.equip[i].paint(g, 0, 0, this._iconX[i], y);
            if (isUnlock[i] == 0) {
                g.drawImage(lockImg, this._iconX[i], y + 5, mGraphics.TOP | mGraphics.HCENTER, false);
                if (curMenu == i) {
                    String xu = gunXu[curMenu] + Language.xu();
                    String luong = gunLuong[curMenu] + Language.luong();
                    if (gunLuong[curMenu] == -1) {
                        luong = "";
                    }

                    Font.borderFont.drawString(g, xu + "-" + luong, this._iconX[i], y + 30, 2);
                    this.isShowInfo = false;
                }
            }
        }

    }

    public void changeEquipAttribute() {
        Vector inventory = EquipScreen.inventory;
        PlayerInfo m = TerrainMidlet.myInfo;

        for (int j = 0; j < m.myEquip.equips.length; ++j) {
            Equip currE = m.myEquip.equips[j];
            if (currE != null) {
                for (int i = 0; i < inventory.size(); ++i) {
                    Equip e = (Equip) inventory.elementAt(i);
                    if (currE.id == m.myEquip.id) {
                        m.addCurrEquip(e);
                        CRes.out("TOI DAY TOI DAY TOI DAY");
                    }
                }
            }
        }

    }

    public void onChangeGun() {
        if (!TerrainMidlet.isVip[TerrainMidlet.myInfo.gun]) {
            TerrainMidlet.myInfo.myEquip = this.equip[curMenu];
        } else {
            TerrainMidlet.myInfo.myVipEquip = this.equip[curMenu];
        }

        this.doClose();
    }

    public int getNextP(int cur, int count) {
        return cur + count > this.nMainIcon - count ? 0 : cur + count;
    }

    public int getLastP(int cur, int count) {
        return cur - count < 0 ? this.nMainIcon - count : cur - count;
    }
}
