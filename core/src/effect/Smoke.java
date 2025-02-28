package effect;

import CLib.Image;
import CLib.mGraphics;
import CLib.mImage;
import coreLG.CCanvas;
import coreLG.CONFIG;
import map.MM;
import model.CRes;
import model.FilePack;
import model.FrameImage;
import model.IAction2;
import screen.GameScr;

public class Smoke {
    FrameImage frmImg;
    int x;
    int y;
    int vx;
    int vy;
    int endY;
    int curFrame;
    int delay;
    boolean isSmallSmoke;
    int smallSmokeDelay;
    boolean isWaterBum;
    public static mImage smoke;
    public static mImage smoke2;
    public static mImage blackSmokeImg;
    public static mImage rockImg;
    public static mImage rock2Img;
    public static mImage glassFly;
    public static mImage chickenHair;
    public static mImage lacay;
    public static mImage blueSmoke;
    public static mImage lazerSmoke;
    public static mImage chuotBay;
    public static mImage gatruilong;
    public static mImage water;
    public static mImage imgTornado;
    public static mImage explode;
    public static mImage glassFly2;
    public static mImage smokeNuke;
    public static mImage bat;
    public static mImage star;
    public static mImage smokeFire;
    public static mImage lua;
    public static mImage wind;
    public static FilePack filePack;
    byte type;
    public static final byte SMOKE = 0;
    public static final byte BLACK_SMOKE = 1;
    public static final byte ROCK = 2;
    public static final byte ROCK_2 = 3;
    public static final byte BLACKSMOKE_EX = 4;
    public static final byte SMALL_BLACKSMOKE_EX = 5;
    public static final byte GLASS_FLY = 6;
    public static final byte REDSMOKE = 7;
    public static final byte CHICKEN_HAIR = 8;
    public static final byte CHUOT = 9;
    public static final byte GA = 10;
    public static final byte WATER = 11;
    public static final byte LACAY = 12;
    public static final byte LAZER_SMOKE = 13;
    public static final byte GLASS_FLY_2 = 14;
    public static final byte NUKE_SMOKE = 15;
    public static final byte LAZER = 16;
    public static final byte BAT = 17;
    public static final byte SMOKE_BG = 18;
    public static final byte LONG_SMOKE = 19;
    public static final byte LUCKY = 20;
    public static final byte FIRE_SMOKE = 21;
    public static final byte WIND = 22;
    private static FrameImage[] frameImages;
    int xLazer;
    int yLazer;
    int num;
    int[] smokeX;
    int[] smokeY;
    int[] smokeRadius;
    int smokeHeight;
    int typeLazer;
    public static final int REDLAZER = 0;
    public static final int GREENLAZER = 1;
    public int xBat;
    public int yBat;
    public int angle;
    public int tWind;
    int va;
    public boolean isStop = false;
    public int timeStop;
    boolean activeLazer;
    int wLazer;

    static {
        try {
            filePack = new FilePack(CCanvas.getClassPathConfig(CONFIG.PATH_EFFECT + "effect"));
            smoke = filePack.loadImage("smoke.png", new IAction2() {
                public void perform(Object object) {
                    Smoke.frameImages[0] = new FrameImage((Image) object, 14, 15);
                    CRes.onSaveToFile((Image) object, "smoke", true);
                }
            });
            smoke2 = filePack.loadImage("smoke2.png", new IAction2() {
                public void perform(Object object) {
                    Smoke.frameImages[19] = new FrameImage((Image) object, 14, 15);
                    CRes.onSaveToFile((Image) object, "smoke2", true);
                }
            });
            chickenHair = filePack.loadImage("longga.png", new IAction2() {
                public void perform(Object object) {
                    Smoke.frameImages[8] = new FrameImage((Image) object, 16, 10);
                    CRes.onSaveToFile((Image) object, "longga", true);
                }
            });
            blackSmokeImg = filePack.loadImage("blacksmoke.png", new IAction2() {
                public void perform(Object object) {
                    Smoke.frameImages[1] = new FrameImage((Image) object, 12, 13);
                    CRes.onSaveToFile((Image) object, "blacksmoke", true);
                }
            });
            blueSmoke = filePack.loadImage("blueSmoke.png", new IAction2() {
                public void perform(Object object) {
                    Smoke.frameImages[7] = new FrameImage((Image) object, 10, 10);
                    CRes.onSaveToFile((Image) object, "blueSmoke", true);
                }
            });
            chuotBay = filePack.loadImage("chuotbay.png", new IAction2() {
                public void perform(Object object) {
                    Smoke.frameImages[9] = new FrameImage((Image) object, 22, 18);
                    CRes.onSaveToFile((Image) object, "chuotbay", true);
                }
            });
            gatruilong = filePack.loadImage("gatruilong.png", new IAction2() {
                public void perform(Object object) {
                    Smoke.frameImages[10] = new FrameImage((Image) object, 19, 17);
                    CRes.onSaveToFile((Image) object, "gatruilong", true);
                }
            });
            water = filePack.loadImage("water.png", new IAction2() {
                public void perform(Object object) {
                    Smoke.frameImages[11] = new FrameImage((Image) object, 3, 3);
                    CRes.onSaveToFile((Image) object, "water", true);
                }
            });
            imgTornado = filePack.loadImage("locxoay.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "locxoay", true);
                }
            });
            explode = filePack.loadImage("ex3.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "ex3", true);
                }
            });
            lacay = filePack.loadImage("lacay.png", new IAction2() {
                public void perform(Object object) {
                    Smoke.frameImages[12] = new FrameImage((Image) object, 8, 5);
                    CRes.onSaveToFile((Image) object, "lacay", true);
                }
            });
            lazerSmoke = filePack.loadImage("blueSmoke2.png", new IAction2() {
                public void perform(Object object) {
                    Smoke.frameImages[13] = new FrameImage((Image) object, 10, 10);
                    CRes.onSaveToFile((Image) object, "blueSmoke2", true);
                }
            });
            glassFly2 = filePack.loadImage("lacay2.png", new IAction2() {
                public void perform(Object object) {
                    Smoke.frameImages[14] = new FrameImage((Image) object, 16, 10);
                    CRes.onSaveToFile((Image) object, "lacay2", true);
                }
            });
            smokeNuke = filePack.loadImage("khoi.png", new IAction2() {
                public void perform(Object object) {
                    Smoke.frameImages[15] = new FrameImage((Image) object, 27, 27);
                    CRes.onSaveToFile((Image) object, "khoi", true);
                }
            });
            smokeFire = filePack.loadImage("smokeFire.png", new IAction2() {
                public void perform(Object object) {
                    Smoke.frameImages[21] = new FrameImage((Image) object, 14, 15);
                    CRes.onSaveToFile((Image) object, "smokeFire", true);
                }
            });
            lua = filePack.loadImage("lua.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "lua", true);
                }
            });
            filePack = new FilePack(CCanvas.getClassPathConfig(CONFIG.PATH_MAP + "bg"));
            rockImg = filePack.loadImage("rock1.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "Smoke_ rock1");
                }
            });
            rock2Img = filePack.loadImage("rock2.png", new IAction2() {
                public void perform(Object object) {
                    Smoke.frameImages[3] = new FrameImage((Image) object, 12, 12);
                    CRes.onSaveToFile((Image) object, "Smoke_ rock2");
                }
            });
            glassFly = filePack.loadImage("cobay.png", new IAction2() {
                public void perform(Object object) {
                    Smoke.frameImages[6] = new FrameImage((Image) object, 16, 10);
                    CRes.onSaveToFile((Image) object, "Smoke_ cobay");
                }
            });
            bat = mImage.createImage("/effect/bat.png");
            star = mImage.createImage("/effect/star.png");
            wind = mImage.createImage("/effect/locxoay.png");
        } catch (Exception var1) {
            var1.printStackTrace();
        }

        filePack = null;
        frameImages = new FrameImage[22];
    }

    public Smoke(int xF, int yF, int xT, int yT, int typeLazer) {
        this.xLazer = xF;
        this.yLazer = yF;
        this.x = xT;
        this.y = yT;
        this.typeLazer = typeLazer;
        this.delay = 0;
        this.type = 16;
        this.activeLazer = true;
        if (typeLazer == 0) {
            this.wLazer = 25;
        }

        if (typeLazer == 1) {
            this.wLazer = 15;
        }

    }

    public Smoke(int xF, int yF, int xT, int yT) {
        this.x = xF;
        this.y = yF;
        this.xBat = xT;
        this.yBat = yT;
        this.angle = 90;
        this.va = 256;
        this.type = 17;
        this.frmImg = new FrameImage(bat.image, 19, 19);
    }

    public Smoke(int x, int y, byte Type) {
        this.x = x;
        this.y = y;
        this.endY = y - CRes.random(60, 80);
        this.delay = 0;
        this.type = Type;
        switch (Type) {
            case 0:
                this.frmImg = frameImages[Type];
                break;
            case 1:
                this.frmImg = frameImages[Type];
            case 2:
            case 3:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 17:
            case 20:
            default:
                break;
            case 4:
            case 5:
            case 18:
                byte max = 10;
                if (Type == 5) {
                    max = 6;
                }

                if (GameScr.curGRAPHIC_LEVEL == 1) {
                    max = (byte) (max - 4);
                } else if (GameScr.curGRAPHIC_LEVEL == 2) {
                    max = (byte) (max - 6);
                }

                if (max < 3) {
                    max = 3;
                }

                this.num = CRes.random(max / 2, max);
                this.vx = GameScr.windx / 12;
                this.vy = CRes.random(-3, -1);
                this.smokeHeight = 25;
                this.smokeX = new int[this.num];
                this.smokeY = new int[this.num];
                this.smokeRadius = new int[this.num];
                int range = 20;
                int radius = 16;
                if (Type == 5) {
                    range = 4;
                    radius = 8;
                }

                for (int i = 0; i < this.num; ++i) {
                    this.smokeX[i] = CRes.random(x - range, x + range);
                    this.smokeY[i] = CRes.random(y - range / 2, y + range / 2);
                    this.smokeRadius[i] = CRes.random(6, radius);
                }

                return;
            case 7:
                this.frmImg = frameImages[Type];
                break;
            case 11:
                this.frmImg = frameImages[Type];
                break;
            case 13:
                this.frmImg = frameImages[Type];
                break;
            case 15:
                this.frmImg = frameImages[Type];
                break;
            case 16:
                this.xLazer = CRes.random(Camera.x, Camera.x + CCanvas.width);
                this.yLazer = Camera.y;
                this.activeLazer = true;
                break;
            case 19:
                this.curFrame = CRes.random(0, 4);
                this.frmImg = frameImages[Type];
                break;
            case 21:
                this.curFrame = CRes.random(1, 2);
                this.frmImg = frameImages[Type];
                break;
            case 22:
                this.frmImg = new FrameImage(wind.image, 32, 32);
        }

    }

    public Smoke(int x, int y, int vx, int vy, byte Type) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.type = Type;
        if (Type == 3) {
            this.frmImg = frameImages[Type];
        } else if (Type == 6) {
            this.frmImg = frameImages[Type];
        } else if (Type == 8) {
            this.frmImg = frameImages[Type];
        } else if (Type == 12) {
            this.frmImg = frameImages[Type];
        } else if (Type == 9) {
            this.frmImg = frameImages[Type];
        } else if (Type == 10) {
            this.frmImg = frameImages[Type];
        } else if (Type == 14) {
            this.frmImg = frameImages[Type];
        }

        this.isSmallSmoke = CRes.r.nextInt(3) == 0;
    }

    public void createSmoke(byte TYPE) {
        ++this.smallSmokeDelay;
        if (this.smallSmokeDelay > 2) {
            GameScr.sm.addSmoke(this.x, this.y, TYPE);
            this.smallSmokeDelay = 0;
        }

    }

    public void checkWaterCollide() {
        if (MM.isHaveWaterOrGlass && !this.isWaterBum && MM.checkWaterBum(this.x, this.y, (byte) 0)) {
            this.isWaterBum = true;
        }

    }

    public static int checkWay(int x, int y, int xTo, int yTo) {
        int L = 0;
        int R = 1;
        int U = 2;
        int D = 3;
        int way = -1;
        int dx;
        int dy;
        if (xTo >= x && yTo <= y) {
            dx = xTo - x;
            dy = y - yTo;
            if (dx > dy) {
                way = R;
            } else {
                way = U;
            }
        }

        if (xTo >= x && yTo >= y) {
            dx = xTo - x;
            dy = yTo - y;
            if (dx > dy) {
                way = R;
            } else {
                way = D;
            }
        }

        if (xTo <= x && yTo <= y) {
            dx = x - xTo;
            dy = y - yTo;
            if (dx > dy) {
                way = L;
            } else {
                way = U;
            }
        }

        if (xTo <= x && yTo >= y) {
            dx = x - xTo;
            dy = yTo - y;
            if (dx > dy) {
                way = L;
            } else {
                way = D;
            }
        }

        return way;
    }

    public void flyTo() {
        int dx = this.xBat - this.x;
        int dy = this.yBat - this.y;
        if (Math.abs(dx) < 16 && Math.abs(dy) < 16) {
            GameScr.sm.removeSmoke(this);
        } else {
            int a = CRes.angle(dx, dy);
            if (Math.abs(a - this.angle) < 90 || dx * dx + dy * dy > 4096) {
                if (Math.abs(a - this.angle) < 15) {
                    this.angle = a;
                } else if ((a - this.angle < 0 || a - this.angle >= 180) && a - this.angle >= -180) {
                    this.angle = CRes.fixangle(this.angle - 15);
                } else {
                    this.angle = CRes.fixangle(this.angle + 15);
                }
            }

            if (this.va < 8192) {
                this.va += 1024;
            }

            this.vx = this.va * CRes.cos(this.angle) >> 10;
            this.vy = this.va * CRes.sin(this.angle) >> 10;
            dx += this.vx;
            int deltaX = dx >> 10;
            this.x += deltaX;
            dx &= 1023;
            dy += this.vy;
            int deltaY = dy >> 10;
            this.y += deltaY;
            dy &= 1023;
        }
    }

    public void update() {
        switch (this.type) {
            case 0:
            case 7:
            case 11:
            case 13:
                if (this.curFrame == 3) {
                    GameScr.sm.removeSmoke(this);
                    return;
                }

                this.updateFrame();
                break;
            case 1:
                if (this.curFrame == 3) {
                    GameScr.sm.removeSmoke(this);
                    return;
                }

                this.updateFrame();
                break;
            case 2:
            case 3:
                if (this.type == 3) {
                    this.updateFrame();
                    if (this.curFrame > 3) {
                        this.curFrame = 0;
                    }
                }

                if (this.y > MM.mapHeight) {
                    GameScr.sm.removeSmoke(this);
                    return;
                }

                this.x += this.vx;
                this.y += this.vy;
                ++this.vy;
                this.checkWaterCollide();
                if (this.isSmallSmoke) {
                    this.createSmoke((byte) 1);
                }
                break;
            case 4:
            case 5:
            case 18:
                for (int i = 0; i < this.num; ++i) {
                    int[] var10000 = this.smokeX;
                    var10000[i] += this.vx;
                    var10000 = this.smokeY;
                    var10000[i] += this.vy;
                    if (this.smokeY[i] < this.endY) {
                        GameScr.sm.removeSmoke(this);
                        return;
                    }
                }

                this.smokeHeight += 5;
                if (this.smokeHeight > 255) {
                    this.smokeHeight = 255;
                }
                break;
            case 6:
            case 8:
            case 12:
            case 14:
                this.updateFrame();
                if (this.vy > 0) {
                    this.vx = GameScr.windx / 20;
                    if (this.curFrame > 3) {
                        this.curFrame = 0;
                    }
                } else {
                    this.curFrame = 0;
                }

                if (this.y > MM.mapHeight) {
                    GameScr.sm.removeSmoke(this);
                    return;
                }

                this.x += this.vx;
                this.y += this.vy;
                if (this.vy < 2) {
                    ++this.vy;
                }
                break;
            case 9:
            case 10:
                if (this.y > MM.mapHeight) {
                    GameScr.sm.removeSmoke(this);
                    return;
                }

                this.x += this.vx;
                this.y += this.vy;
                ++this.vy;
                this.checkWaterCollide();
                break;
            case 15:
                if (this.curFrame == 3) {
                    GameScr.sm.removeSmoke(this);
                    return;
                }

                this.updateFrame();
            case 16:
            default:
                break;
            case 17:
                ++this.delay;
                if (this.delay == 10) {
                    this.delay = 0;
                }

                if (this.delay < 5) {
                    this.curFrame = 0;
                } else {
                    this.curFrame = 1;
                }

                this.flyTo();
                break;
            case 19:
                if (!this.isStop) {
                    ++this.timeStop;
                    if (this.timeStop == 10) {
                        this.timeStop = 0;
                        this.isStop = true;
                    }

                    if (this.curFrame > 4) {
                        this.curFrame = 0;
                    }

                    if (CCanvas.gameTick % 2 == 0) {
                        ++this.curFrame;
                    }
                } else if (CCanvas.gameTick % 2 == 0) {
                    if (this.curFrame == 7) {
                        GameScr.sm.removeSmoke(this);
                        return;
                    }

                    ++this.curFrame;
                }
                break;
            case 20:
                if (this.y > MM.mapHeight) {
                    GameScr.sm.removeSmoke(this);
                    return;
                }

                this.x += this.vx;
                this.y += this.vy;
                ++this.vy;
                break;
            case 21:
                if (this.curFrame == 7) {
                    GameScr.sm.removeSmoke(this);
                    return;
                }

                ++this.curFrame;
                break;
            case 22:
                if (this.curFrame == 3) {
                    this.curFrame = 0;
                }

                ++this.tWind;
                if (this.tWind == 50) {
                    this.tWind = 0;
                    GameScr.sm.removeSmoke(this);
                }

                this.updateFrame();
        }

    }

    private void updateFrame() {
        ++this.delay;
        if (this.delay > 1) {
            ++this.curFrame;
            this.delay = 0;
        }

    }

    public void paintEffect(mGraphics g) {
        int i;
        int dark;
        switch (this.type) {
            case 0:
            case 1:
            case 7:
            case 13:
            case 15:
            case 19:
            case 21:
                this.frmImg.drawFrame(this.curFrame, this.x, this.y, 0, mGraphics.HCENTER | mGraphics.VCENTER, g);
                break;
            case 2:
                g.drawImage(rockImg, this.x, this.y, mGraphics.HCENTER | mGraphics.VCENTER, false);
                break;
            case 3:
                this.frmImg.drawFrame(this.curFrame, this.x, this.y, 0, mGraphics.HCENTER | mGraphics.VCENTER, g);
                break;
            case 4:
            case 5:
                for (i = 0; i < this.num; ++i) {
                    dark = CRes.random(this.smokeHeight - 20, this.smokeHeight);
                    g.setColor(dark, dark, dark);
                    g.fillArc(this.smokeX[i], this.smokeY[i], this.smokeRadius[i], this.smokeRadius[i], 0, 360, false);
                }

                return;
            case 6:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 14:
                this.frmImg.drawFrame(this.curFrame, this.x, this.y, 0, mGraphics.HCENTER | mGraphics.VCENTER, g);
                break;
            case 16:
                if (this.activeLazer) {
                    for (i = 0; i < this.wLazer; ++i) {
                        if (i < this.wLazer / 2 + this.wLazer / 4 && i > this.wLazer / 2 - this.wLazer / 4) {
                            g.setColor(16777215);
                        } else {
                            if (this.typeLazer == 1) {
                                g.setColor(718162);
                            }

                            if (this.typeLazer == 0) {
                                g.setColor(16729670);
                            }
                        }

                        g.drawLine(this.xLazer - this.wLazer / 2 + i, this.yLazer, this.x - this.wLazer / 2 + i, this.y, false);
                    }

                    --this.wLazer;
                    if (this.wLazer == 0) {
                        this.activeLazer = false;
                        GameScr.sm.removeSmoke(this);
                        return;
                    }
                }
                break;
            case 17:
                this.frmImg.drawFrame(this.curFrame, this.x, this.y, 0, mGraphics.HCENTER | mGraphics.VCENTER, g);
                break;
            case 18:
                for (i = 0; i < this.num; ++i) {
                    dark = CRes.random(190, 200);
                    g.setColor(dark, dark, dark);
                    g.fillArc(this.smokeX[i], this.smokeY[i], this.smokeRadius[i], this.smokeRadius[i], 0, 360, false);
                }

                return;
            case 20:
                g.drawImage(star, this.x, this.y, mGraphics.HCENTER | mGraphics.VCENTER, false);
                break;
            case 22:
                this.frmImg.drawFrame(this.curFrame, this.x, this.y, 0, 33, g);
        }

    }

    public void paint(mGraphics g) {
        this.paintEffect(g);
    }

    public void onClearMap() {
    }
}
