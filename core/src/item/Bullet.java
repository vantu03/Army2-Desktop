package item;

import CLib.Image;
import CLib.mGraphics;
import CLib.mImage;
import CLib.mSound;
import CLib.mSystem;
import Equipment.PlayerEquip;
import coreLG.CCanvas;
import coreLG.CONFIG;
import effect.Camera;
import effect.Explosion;
import effect.Smoke;
import effect.Tornado;

import java.util.Vector;

import map.CMap;
import map.MM;
import model.CRes;
import model.FilePack;
import model.FrameImage;
import model.IAction2;
import model.Position;
import player.CPlayer;
import player.PM;
import screen.GameScr;

public class Bullet {
    int x;
    int y;
    int xDestination;
    int yDestination;
    int x_Last;
    int y_Last;
    int _xStart;
    int _yStart;
    int x_1;
    int x_2;
    int y_1;
    int y_2;
    int _w;
    int _h;
    int vx;
    int vy;
    int x_hammer;
    int y_hammer;
    int g = 8;
    int g1 = 0;
    int wind1 = 0;
    int explDelay;
    int vyLazer;
    int lazerColor;
    int subVx = 0;
    int subVy = 0;
    int subBmrVx = 0;
    int mouseP = 0;
    int trans = 0;
    int xS;
    int yS;
    int xD;
    int yD;
    boolean isActiveMissile = false;
    boolean isActiveHammer = false;
    boolean isActiveEgg = false;
    boolean isActiveRain = false;
    byte whoShot;
    private int color;
    boolean rotate = false;
    boolean translate = false;
    mImage img = null;
    FrameImage frmImg = null;
    int curFrame = 0;
    int smokeDelay = 0;
    boolean smoke = false;
    boolean longSmoke = false;
    boolean fireSmoke = false;
    boolean blackSmoke = false;
    boolean changeFrame = false;
    boolean lazerBullet = false;
    boolean changeFrameLeft = false;
    boolean isLightBlink = false;
    boolean isWaterBum;
    boolean lazerShoot = false;
    boolean lazerStop = true;
    boolean tonardo = false;
    boolean beginTimeCount = false;
    boolean chickenBull = false;
    boolean chickenHair = false;
    public static int dXLaser;
    public static int dYLaser;
    public int angle;
    int force;
    public int wind;
    public static mImage missile;
    public static mImage stone;
    public static mImage electric;
    public static mImage bomb;
    public static mImage bomb_flag;
    public static mImage rangCuaImg;
    public static mImage grenadeImg;
    public static mImage webImg;
    public static mImage chuoiImg;
    public static mImage chuoiImg2;
    public static mImage rocket;
    public static mImage superShoot;
    public boolean isSuper;
    int sFrame = 0;
    int ts = 0;
    public static mImage rocket2;
    static mImage rocket3;
    static mImage smallStar;
    static mImage chicken;
    static mImage chicken2;
    static mImage egg;
    static mImage egg2;
    static mImage boomerang;
    static mImage mouse;
    static mImage meteor;
    static mImage hammer;
    static mImage hammer2;
    static mImage rainmissile;
    static mImage khoang;
    static mImage daodat;
    static mImage daodat2;
    static mImage saobang;
    static mImage chatlong;
    static mImage mortar;
    static mImage boomerangBig;
    public static mImage imgGun;
    static mImage medic;
    static mImage nuke;
    static mImage lazer;
    static mImage ak2;
    static mImage axit;
    static mImage cannon2;
    static mImage balloonBull;
    public static FilePack filePack;
    public byte type;
    public static final byte BULL_CANNON = 0;
    public static final byte BULL_AK = 1;
    public static final byte BULL_PROTON = 2;
    public static final byte BULL_BOMBAY = 3;
    public static final byte BULL_BOMB_FLAG = 4;
    public static final byte BULL_TELEPORT = 5;
    public static final byte BULL_RANG_CUA = 6;
    public static final byte BULL_GENERADE = 7;
    public static final byte BULL_WEB = 8;
    public static final byte BULL_CHUOI = 9;
    public static final byte BULL_ROCKET = 10;
    public static final byte BULL_MORTAR = 11;
    public static final byte BULL_TORNADO = 13;
    public static final byte BULL_TRAI_PHA = 12;
    public static final byte BULL_LAZER_FLAG = 14;
    public static final byte BULL_LAZER = 15;
    public static final byte BULL_TRAI_PHA_FLAG = 16;
    public static final byte BULL_EXPLORE_FLAG = 17;
    public static final byte BULL_EXPLORE = 18;
    public static final byte BULL_CHICKEN = 19;
    public static final byte BULL_EGG = 20;
    public static final byte BULL_BOOMERANG = 21;
    public static final byte BULL_MOUSE = 22;
    public static final byte BULL_METEOR_FLAG = 23;
    public static final byte BULL_METEOR = 24;
    public static final byte BULL_UNDERGROUND = 25;
    public static final byte BULL_4MISSILE = 26;
    public static final byte BULL_1MISSILE = 27;
    public static final byte BULL_MISSILE_RAIN_FLAG = 28;
    public static final byte BULL_MISSILE_RAIN = 29;
    public static final byte BULL_HOLE = 30;
    public static final byte BULL_BOMB_BOSS = 31;
    public static final byte BULL_BOMB_BOSS_SMALL = 32;
    public static final byte BULL_BOSS_MISSILE = 33;
    public static final byte BULL_MINI_BOMB = 34;
    public static final byte BULL_ROBOT_ATTACK = 35;
    public static final byte BULL_TELEPORT_2 = 36;
    public static final byte BULL_NUKE = 37;
    public static final byte BULL_4LAZER = 38;
    public static final byte BULL_1LAZER = 39;
    public static final byte BULL_LAZER2 = 40;
    public static final byte BULL_SPEACIAL = 41;
    public static final byte BULL_UFO_LAZER = 42;
    public static final byte BULL_10_BOMB = 43;
    public static final byte BULL_BALLOON_GUN = 44;
    public static final byte BULL_BALLOON_LAZER = 45;
    public static final byte BULL_AXIT = 47;
    public static final byte BULL_PINGPONG = 48;
    public static final byte BULL_SUICIDE = 50;
    public static final byte BULL_SMOKE = 51;
    public static final byte BULL_BIG_HOLE = 52;
    public static final byte BULL_UFO = 53;
    public static final byte BULL_FREEZE = 54;
    public static final byte BULL_POISION = 55;
    public static final byte BULL_WEB3 = 56;
    public static final byte BULL_TIME_BOMB = 57;
    public static final byte BULL_LAZER_GIRL = 49;
    public static byte[] BULLset_WIND_AFFECT;
    public static byte[] BULLset_WEIGHT;
    public short[] xPaint;
    public short[] yPaint;
    public int paintCount = 0;
    public Vector postion;
    public mImage dan = null;
    private int idBullet;
    int xLG;
    int yLG;
    int n1 = 10;
    int n2 = 8;
    boolean isMirror;
    int tL;
    int xLaser1;
    int yLaser1;
    int xLaser2;
    int yLaser2;
    long timeDelayPaint;
    int lx;
    int ly;
    int pw = 1;
    int ph = 1;
    int px;
    int py;
    int nCheck;
    boolean changePositon = false;
    int dis = 0;
    int count = 0;
    int dis_ChPosition = 4;
    int dis_delay = 0;
    int x_3;
    int y_3;
    int x_4;
    int y_4;
    int timecount = 0;
    boolean isfall = false;
    int dym = 0;
    boolean beginUdgr = false;
    int bx;
    int missileTime = 0;
    int pingFrame;
    int pingColor;
    int t;
    int lastAngle = 0;
    boolean isPaintLazer;
    boolean pingChange = false;
    int mainFrame;
    int pos;
    public static int webId;
    public boolean notPaint = false;
    int angleRotate;
    Image tam;

    static {
        try {
            filePack = new FilePack(CCanvas.getClassPathConfig(CONFIG.PATH_ITEM + "item"));
            bomb = filePack.loadImage("bomb.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "bomb");
                }
            });
            bomb_flag = filePack.loadImage("specialbullet.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "specialbullet");
                }
            });
            filePack.loadImage("grenade.png", new IAction2() {
                public void perform(Object object) {
                    Bullet.grenadeImg = new mImage((Image) object);
                    CRes.onSaveToFile((Image) object, "grenade");
                }
            });
            webImg = filePack.loadImage("web.png");
            filePack.loadImage("chuoi2.png", new IAction2() {
                public void perform(Object object) {
                    Bullet.chuoiImg2 = new mImage((Image) object);
                    CRes.onSaveToFile((Image) object, "chuoi2");
                }
            });
            chicken2 = filePack.loadImage("gaBull2.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "gaBull2");
                }
            });
            rocket2 = filePack.loadImage("rocket2.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "rocket2");
                }
            });
            egg = filePack.loadImage("trungvang.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "trungvang");
                }
            });
            egg2 = filePack.loadImage("trung2.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "trung2");
                }
            });
            mouse = filePack.loadImage("chuotBull.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "chuotBull");
                }
            });
            meteor = filePack.loadImage("thienthach.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "thienthach");
                }
            });
            mortar = filePack.loadImage("dan.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "dan");
                }
            });
            rainmissile = filePack.loadImage("13Missile.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "13Missile");
                }
            });
            khoang = filePack.loadImage("khoang.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "khoang");
                }
            });
            daodat = filePack.loadImage("daodat.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "daodat");
                }
            });
            daodat2 = filePack.loadImage("daodat2.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "daodat2");
                }
            });
            saobang = filePack.loadImage("saobang.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "saobang");
                }
            });
            imgGun = filePack.loadImage("gunIcon.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "gunIcon");
                }
            });
            nuke = filePack.loadImage("bigMissile.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "bigMissile");
                }
            });
            lazer = filePack.loadImage("laser.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "laser");
                }
            });
            cannon2 = filePack.loadImage("cannon.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "cannon");
                }
            });
            chatlong = filePack.loadImage("chatlong.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "chatlong");
                }
            });
            filePack.loadImage("boomerang-big.png", new IAction2() {
                public void perform(Object object) {
                    Bullet.boomerangBig = new mImage((Image) object);
                    CRes.onSaveToFile((Image) object, "boomerang-big");
                }
            });
            superShoot = mImage.createImage("/effect/no.png");
            axit = mImage.createImage("/item/axit.png");
        } catch (Exception var1) {
            var1.printStackTrace();
        }

        filePack = null;
        BULLset_WIND_AFFECT = null;
        BULLset_WEIGHT = null;
        webId = 200;
    }

    public Bullet(byte type, short[] X, short[] Y, byte whoShot) {
        this.init(type, X, Y, whoShot);
    }

    public Bullet(byte type, short[] X, short[] Y, byte whoShot, int idBullet) {
        this.idBullet = idBullet;
        this.init(type, X, Y, whoShot);
    }

    public Bullet(byte type, short[] X, short[] Y, byte whoShot, short[] xHit, short[] yHit) {
        this.init(type, X, Y, whoShot);
        this.getCollisionPingPong(xHit, yHit);
    }

    public void init(byte type, short[] X, short[] Y, byte whoShot) {
        CRes.err("======> init = " + type);
        this.type = type;
        this.whoShot = whoShot;
        this.xPaint = X;
        this.yPaint = Y;
        this.x = this.xPaint[0];
        this.y = this.yPaint[0];
        this.dan = null;
        this.initBulletType(type);
        if (type == 49) {
            this.xLG = this.getXYMirror(X, Y)[0];
            this.yLG = this.getXYMirror(X, Y)[1];
            CPlayer.xM = this.xLG;
            CPlayer.yM = this.yLG;
            CPlayer.isMirror = true;
        }

        Camera.shaking = 0;
        this.getPlayerEffect();
        mSound.playSound(2, mSound.volumeSound, 1);
    }

    public void getCollisionPingPong(short[] X, short[] Y) {
        this.postion = new Vector();
        if (this.type == 48) {
            for (int i = 0; i < X.length; ++i) {
                this.postion.addElement(new Position(X[i], Y[i]));
            }
        }

    }

    public int[] getXYMirror(short[] X, short[] Y) {
        short minY = min(Y);

        for (int i = 0; i < Y.length; ++i) {
            if (Y[i] == minY) {
                return new int[]{X[i], Y[i]};
            }
        }

        return null;
    }

    public void getPlayerEffect() {
        if (this.type == 31 || this.type == 32 || this.type == 35) {
            this.explode(1);
            if (PM.getCurPlayer().gun != 15) {
                Camera.shaking = 2;
            }
        }

        this.checkBackBody();
        if (GameScr.bm.critical == 1) {
            if (this.type != 11 && this.type != 18) {
                new Explosion(this.xPaint[0], this.yPaint[0], (byte) 0);
            }
        } else if (this.type != 42 && this.type != 47) {
            GameScr.sm.addSmoke(this.xPaint[0], this.yPaint[0], (byte) 0);
        }

        if (this.type == 45) {
            CCanvas.lockNotify = true;
            CCanvas.tNotify = 0;
        }

    }

    public void paintLazerGirl(mGraphics g) {
        int color = GameScr.bm.critical == 1 ? 718162 : this.color;
        if (!this.isMirror) {
            this.xLaser1 += dXLaser;
            this.yLaser1 += dYLaser;
            GameScr.cam.setTargetPointMode(this.xPaint[0] + this.xLaser1, this.yPaint[0] + this.yLaser1);
            if (this.yPaint[0] + this.yLaser1 < this.yLG) {
                this.yLaser1 = this.yLG - this.yPaint[0];
                this.xLaser1 = this.xLG - this.xPaint[0];
                this.isMirror = true;
            }
        } else {
            GameScr.cam.setTargetPointMode(this.xLG + this.xLaser2, this.yLG - this.yLaser2);
            if (this.yLG - this.yLaser2 >= this.yPaint[this.yPaint.length - 1]) {
                this.x = this.xPaint[this.xPaint.length - 1];
                this.y = this.yPaint[this.yPaint.length - 1];
                this.explode(2);
            }

            if (mSystem.currentTimeMillis() - this.timeDelayPaint > 20L) {
                this.xLaser2 += dXLaser;
                this.yLaser2 += dYLaser;
                this.timeDelayPaint = mSystem.currentTimeMillis() + 20L;
            }
        }

        if (GameScr.curGRAPHIC_LEVEL != 2) {
            int i;
            for (i = 0; i < this.n1; ++i) {
                if (i < this.n1 / 2 + this.n1 / 4 && i > this.n1 / 2 - this.n1 / 4) {
                    g.setColor(16777215);
                } else {
                    g.setColor(color);
                }

                g.drawLine(this.xPaint[0] - this.n1 / 2 + i, this.yPaint[0], this.xPaint[0] - this.n1 / 2 + i + this.xLaser1, this.yPaint[0] + this.yLaser1, false);
            }

            for (i = 0; i < this.n1; ++i) {
                if (i < this.n1 / 2 + this.n1 / 4 && i > this.n1 / 2 - this.n1 / 4) {
                    g.setColor(16777215);
                } else {
                    g.setColor(color);
                }

                g.drawLine(this.xLG - this.n1 / 2 + i, this.yLG, this.xLG - this.n1 / 2 + i + this.xLaser2, this.yLG - this.yLaser2, true);
            }
        } else {
            g.setColor(color);
            g.drawLine(this.xPaint[0], this.yPaint[0], this.xPaint[0] + this.xLaser1, this.yPaint[0] + this.yLaser1, true);
            g.setColor(color);
            g.drawLine(this.xLG, this.yLG, this.xLG + this.xLaser2, this.yLG - this.yLaser2, true);
        }

    }

    public static short min(short[] array) {
        short min = array[0];

        for (int i = 1; i < array.length; ++i) {
            if (array[i] < min) {
                min = array[i];
            }
        }

        return min;
    }

    public void checkBackBody() {
        if (PM.getCurPlayer().gun == 13) {
            PM.getCurPlayer().xBugBack = -5;
            PM.getCurPlayer().yBugBack = 5;
            if (this.type == 36) {
                PM.getCurPlayer().frameleg_1 = 1;
                PM.getCurPlayer().xBug = this.xPaint[this.xPaint.length - 1];
                this.notPaint = true;
            }
        }

        if (PM.getCurPlayer().gun == 14 && this.type == 36) {
            PM.getCurPlayer().xBug = this.xPaint[this.xPaint.length - 1];
            PM.getCurPlayer().isFly = true;
            this.notPaint = true;
        }

    }

    public void checkFlyPlayer() {
        if (this.type == 36 && PM.getCurPlayer().isJump) {
            PM.getCurPlayer().x = this.x;
            PM.getCurPlayer().y = this.y;
        }

    }

    private void initBulletType(int type) {
        int critical = GameScr.bm.critical;
        CRes.err("======> initBulletType = " + type);
        switch (type) {
            case 0:
                if (critical == 0) {
                    this.img = PlayerEquip.bullets[0];
                    this.longSmoke = true;
                    this.isLightBlink = true;
                    this.frmImg = null;
                }

                if (critical == 1) {
                    this.fireSmoke = true;
                    this.frmImg = new FrameImage(cannon2.image, 14, 14);
                    this.changeFrame = true;
                    this.img = null;
                }
                break;
            case 1:
                CCanvas.cutImage(PlayerEquip.bullets[1], this.idBullet, new IAction2() {
                    public void perform(Object object) {
                        Bullet.this.dan = new mImage((Image) object);
                    }
                });
                this.rotate = true;
                this.smoke = true;
                break;
            case 2:
                if (critical == 0) {
                    this.frmImg = GameScr.bm.frameBulletSpecial[2][0];
                    this.frmImg.nFrame = 2;
                    this.changeFrame = true;
                }

                if (critical == 1) {
                    this.frmImg = GameScr.bm.frameBulletSpecial[2][1];
                    this.frmImg.nFrame = 2;
                    this.changeFrame = true;
                }
                break;
            case 3:
                this.frmImg = new FrameImage(bomb.image, 24, 24);
                this.changeFrame = true;
                this.blackSmoke = true;
                break;
            case 4:
            case 16:
                this.frmImg = new FrameImage(bomb_flag.image, 32, 32);
                this.smoke = true;
                this.changeFrame = true;
                break;
            case 5:
            case 36:
                this.frmImg = new FrameImage(bomb_flag.image, 32, 32);
                if (!PM.getCurPlayer().isJump) {
                    this.smoke = true;
                }
                break;
            case 6:
                this.frmImg = new FrameImage(PlayerEquip.bullets[5].image, 20, 20);
                this.frmImg.nFrame = 4;
                this.changeFrame = true;
                break;
            case 7:
            case 31:
                this.frmImg = GameScr.bm.frameBulletSpecial[7][0];
                this.checkFrameDirect();
                this.smoke = true;
                break;
            case 8:
            case 56:
                this.frmImg = new FrameImage(webImg.image, 12, 12);
                this.checkFrameDirect();
                this.smoke = true;
                break;
            case 9:
                if (critical == 0) {
                    this.frmImg = GameScr.bm.frameBulletSpecial[9][0];
                }

                if (critical == 1) {
                    this.frmImg = GameScr.bm.frameBulletSpecial[9][1];
                }

                this.checkTranslate();
                this.changeFrame = true;
                break;
            case 10:
                if (critical == 0) {
                    CCanvas.cutImage(PlayerEquip.bullets[4], this.idBullet, new IAction2() {
                        public void perform(Object object) {
                            Bullet.this.dan = new mImage((Image) object);
                            Bullet.this.rotate = true;
                        }
                    });
                }

                if (critical == 1) {
                    CCanvas.cutImage(PlayerEquip.bullets[4], this.idBullet, new IAction2() {
                        public void perform(Object object) {
                            Bullet.this.dan = new mImage((Image) object);
                            Bullet.this.rotate = true;
                            Bullet.this.fireSmoke = true;
                        }
                    });
                }
                break;
            case 11:
                if (critical == 0) {
                    this.frmImg = new FrameImage(PlayerEquip.bullets[5].image, 20, 20);
                    this.frmImg.nFrame = 4;
                    this.blackSmoke = true;
                    this.changeFrame = true;
                }

                if (critical == 1) {
                    this.idBullet = 0;
                    this.frmImg = new FrameImage(mortar.image, 18, 18);
                    this.smoke = true;
                    this.changeFrame = true;
                }
                break;
            case 12:
                this.frmImg = new FrameImage(PlayerEquip.bullets[5].image, 20, 20);
                this.frmImg.nFrame = 4;
                this.blackSmoke = true;
                this.changeFrame = true;
                break;
            case 13:
                this.tonardo = true;
                break;
            case 14:
            case 40:
                this.lazerBullet = true;
                break;
            case 15:
                this.lazerShoot = true;
                this.lazerStop = false;
                break;
            case 17:
            case 18:
                this.frmImg = GameScr.bm.frameBulletSpecial[17][0];
                if (critical == 1) {
                    this.fireSmoke = true;
                }

                this.changeFrame = true;
                this.translate = true;
                break;
            case 19:
                if (critical == 0) {
                    this.dan = GameScr.bm.bulletChicken;
                }

                if (critical == 1) {
                    this.frmImg = new FrameImage(chicken2.image, 25, 25);
                    this.dan = chicken2;
                    this.chickenHair = true;
                    this.longSmoke = true;
                }

                this.chickenBull = true;
                this.rotate = true;
                break;
            case 20:
                if (critical == 0) {
                    this.frmImg = new FrameImage(egg.image, 12, 11);
                }

                if (critical == 1) {
                    this.frmImg = new FrameImage(egg2.image, 20, 20);
                }

                this.changeFrame = true;
                break;
            case 21:
                if (critical == 0) {
                    this.frmImg = GameScr.bm.frameBulletSpecial[21][0];
                    this.changeFrame = true;
                }

                if (critical == 1) {
                    this.frmImg = GameScr.bm.frameBulletSpecial[21][1];
                    this.changeFrame = true;
                    this.fireSmoke = true;
                }
                break;
            case 22:
                this.frmImg = new FrameImage(mouse.image, 25, 13);
                this.changeFrame = true;
                this.translate = true;
                break;
            case 23:
                this.frmImg = new FrameImage(saobang.image, 15, 15);
                this.changeFrame = true;
                this.rotate = true;
                break;
            case 24:
                this.frmImg = new FrameImage(meteor.image, 27, 28);
                this.changeFrame = true;
                break;
            case 25:
                this.frmImg = new FrameImage(daodat.image, 16, 16);
                this.changeFrame = true;
                break;
            case 26:
            case 27:
            case 33:
                this.dan = new mImage(rocket2.image);
                this.rotate = true;
                this.smoke = true;
                break;
            case 28:
                this.frmImg = new FrameImage(rainmissile.image, 23, 23);
                this.changeFrame = false;
                break;
            case 29:
                this.dan = new mImage(rocket2.image);
                this.rotate = true;
                break;
            case 30:
                this.frmImg = new FrameImage(khoang.image, 32, 32);
                this.changeFrame = true;
                break;
            case 32:
            case 41:
                this.img = stone;
                this.smoke = true;
                this.isLightBlink = true;
                break;
            case 34:
                this.tonardo = true;
            case 35:
            case 38:
            case 39:
            case 42:
            case 45:
            case 46:
            case 50:
            default:
                break;
            case 37:
                this.frmImg = new FrameImage(nuke.image, 18, 39);
                this.changeFrame = true;
                break;
            case 43:
                this.img = CPlayer.bomb;
                break;
            case 44:
                this.frmImg = new FrameImage(lazer.image, 19, 19);
                this.rotate = true;
                break;
            case 47:
                this.frmImg = new FrameImage(axit.image, 15, 15);
                this.rotate = true;
                break;
            case 48:
                this.frmImg = new FrameImage(balloonBull.image, 14, 14);
                break;
            case 49:
                this.img = PlayerEquip.bullets[9];
                if (GameScr.curGRAPHIC_LEVEL == 0) {
                    this.longSmoke = true;
                }

                this.color = this.getColor(CCanvas.cutImage(this.img, this.idBullet));
                break;
            case 51:
            case 53:
                this.longSmoke = true;
                break;
            case 52:
                this.frmImg = new FrameImage(daodat2.image, 24, 24);
                this.changeFrame = true;
                break;
            case 54:
                this.frmImg = new FrameImage(saobang.image, 15, 15);
                this.changeFrame = true;
                this.rotate = true;
                break;
            case 55:
                this.frmImg = new FrameImage(chatlong.image, 20, 20);
                this.changeFrame = true;
                this.img = null;
                break;
            case 57:
                this.tonardo = true;
        }

        if (this.frmImg != null) {
            this._w = this.frmImg.frameWidth;
            this._h = this.frmImg.frameHeight;
        } else if (this.img != null) {
            this._w = this.img.image.getWidth();
            this._h = this.img.image.getHeight();
        } else {
            this._w = 2;
            this._h = 2;
        }

    }

    public int getColor(Image img) {
        try {
            int width = img.getWidth();
            int height = img.getHeight();
            int[] imgData = new int[width * height];
            img.getRGB(imgData, 0, width, 0, 0, width, height);
            return imgData[5];
        } catch (Exception var5) {
            return 16722988;
        }
    }

    public static byte setBulletType(byte curGunType) {
        CRes.err("======> setBulletType = " + curGunType);
        switch (curGunType) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 9;
            case 4:
                return 10;
            case 5:
                return 11;
            case 6:
                return 19;
            case 7:
                return 21;
            case 8:
                return 17;
            case 9:
                return 49;
            case 10:
                return 48;
            case 11:
            case 12:
            case 13:
            default:
                return 0;
            case 14:
                return 33;
            case 15:
                return 37;
        }
    }

    public void checkTranslate() {
        PM var10001 = GameScr.pm;
        if (this.x > PM.getCurPlayer().x) {
            this.trans = 0;
        } else {
            this.trans = 2;
        }

    }

    public void checkFrameDirect() {
        PM var10001 = GameScr.pm;
        if (this.x > PM.getCurPlayer().x) {
            this.changeFrame = true;
        } else {
            this.changeFrameLeft = true;
        }

    }

    public void lazerBullUpdate(int type) {
        if (this.changePositon) {
            this.dis += this.dis_ChPosition;
        } else {
            this.dis -= this.dis_ChPosition;
        }

        this.dis_ChPosition = 2;
        this.x_1 = this.x + (CRes.cos(CRes.fixangle(BM.angle)) * this.dis >> 10);
        this.y_1 = this.y + -(CRes.sin(CRes.fixangle(BM.angle)) * this.dis >> 10);
        this.x_2 = this.x - (CRes.cos(CRes.fixangle(BM.angle)) * this.dis >> 10);
        this.y_2 = this.y - -(CRes.sin(CRes.fixangle(BM.angle)) * this.dis >> 10);
        GameScr.sm.addSmoke(this.x_1, this.y_1, (byte) (type == 0 ? 7 : 13));
        GameScr.sm.addSmoke(this.x_2, this.y_2, (byte) (type == 0 ? 7 : 13));
        if (type == 1) {
            this.x_3 = this.x + (CRes.cos(CRes.fixangle(BM.angle)) * this.dis >> 9);
            this.y_3 = this.y + -(CRes.sin(CRes.fixangle(BM.angle)) * this.dis >> 9);
            this.x_4 = this.x - (CRes.cos(CRes.fixangle(BM.angle)) * this.dis >> 9);
            this.y_4 = this.y - -(CRes.sin(CRes.fixangle(BM.angle)) * this.dis >> 9);
            GameScr.sm.addSmoke(this.x_3, this.y_3, (byte) 7);
            GameScr.sm.addSmoke(this.x_4, this.y_4, (byte) 7);
        }

        ++this.count;
        if (this.count == 3) {
            this.count = 0;
            this.changePositon = !this.changePositon;
        }

    }

    public boolean collisionTornado() {
        Vector tornados = BM.vTornado;
        if (tornados.size() >= 0) {
            for (int i = 0; i < tornados.size(); ++i) {
                Tornado tornado = (Tornado) tornados.elementAt(i);
                if (CRes.inRect(this.x, this.y, tornado.x, -100, 20, tornado.y + 100)) {
                    return true;
                }
            }
        }

        return false;
    }

    public void tornadoBullet() {
        GameScr.sm.addSmoke(this.x, this.y, (byte) 1);
    }

    public boolean isSkyBullet() {
        return this.type == 3 || this.type == 15;
    }

    public void mouseUpdate() {
        if (this.isfall) {
            ++this.dym;
            this.y += this.dym / 2;
        }

        if (this.collisionTornado()) {
            this.y -= 4;
        }

        ++this.timecount;
        if (this.timecount < 3 * this.force) {
            if (!GameScr.mm.isLand(this.x, this.y - 5)) {
                this.x += this.mouseP;
                if (!GameScr.mm.isLand(this.x - 8, this.y + 4) && !GameScr.mm.isLand(this.x + 8, this.y + 4) && !GameScr.mm.isLand(this.x, this.y + 4)) {
                    if (!this.collisionTornado()) {
                        this.isfall = true;
                    }
                } else {
                    --this.y;
                    this.dym = 0;
                    this.isfall = false;
                }
            }
        } else if (this.timecount == 3 * this.force) {
            this.explode(3);
        }

    }

    public void undergUpdate() {
        ++this.timecount;
        if (GameScr.mm.isLand(this.x, this.y)) {
            GameScr.sm.addRock(this.x, this.y, CRes.random(4 + this.force / 3), CRes.random(-8 - this.force / 3, -5 - this.force / 3), (byte) 2);
            CRes.err("===> undergUpdate() = " + this.type);
            GameScr.mm.makeHole(this.x, this.y, this.type, 1);
        }

        if (this.x < 0 || this.x > MM.mapWidth || this.y > MM.mapHeight || this.y < 100) {
            this.explode(4);
        }
    }

    public void missileRainUpdate() {
        if (this.isActiveRain) {
            ++this.timecount;
            if (this.timecount == 20) {
                this.timecount = 0;
                this.explode(5);
            }
        }

    }

    public void holeBulletUpdate() {
        this.y += 2;
        int var10000 = CCanvas.gameTick % 3;
        if (GameScr.mm.isLand(this.x, this.y)) {
            CRes.err("===> holeBulletUpdate() = " + this.type);
            GameScr.mm.makeHole(this.x, this.y - 5, this.type, 2);
            GameScr.sm.addRock(this.x, this.y, CRes.random(5), CRes.random(5), (byte) 2);
            GameScr.sm.addRock(this.x, this.y, -CRes.random(5), CRes.random(5), (byte) 2);
        }

        ++this.timecount;
        if (this.timecount == this.force * 2) {
            this.explode(6);
        }

    }

    public void nBullUpdate() {
        if (this.type == 26) {
            this.isActiveMissile = false;
            GameScr.bm.nBull = 1;
        }

        if (this.type == 17) {
            this.isActiveHammer = false;
            GameScr.bm.nBull = 1;
        }

    }

    public void fixedUpdate() {
        int x1 = this.x;
        int y1 = this.y;
        this.x = this.xPaint[this.paintCount];
        this.y = this.yPaint[this.paintCount];
        if (this.type == 49) {
            if (dXLaser != 0 && dYLaser != 0) {
                if (this.paintCount == this.xPaint.length - 2) {
                    this.xLG = this.xPaint[this.paintCount];
                    this.yLG = this.yPaint[this.paintCount];
                    if (this.xLG < 0 || this.xLG > MM.mapWidth) {
                        this.explode(7);
                    }

                    ++this.tL;
                    if (this.tL == 15) {
                        this.tL = 0;
                        this.isPaintLazer = true;
                    }

                    return;
                }
            } else if (this.paintCount == this.xPaint.length - 1) {
                this.explode(8);
                return;
            }
        }

        if (GameScr.curGRAPHIC_LEVEL != 2 && this.longSmoke) {
            GameScr.sm.addSmoke(this.x + CRes.random(-3, 3), this.y + CRes.random(-3, 3), (byte) 19);
        }

        if (this.type == 36) {
            if (this.paintCount < 1) {
                this.paintCount = 1;
            }

            if (PM.getCurPlayer().isJump) {
                PM.getCurPlayer().x = this.xPaint[this.paintCount - 1];
                PM.getCurPlayer().y = this.yPaint[this.paintCount - 1];
            }
        }

        if (this.paintCount > this.xPaint.length - 1) {
            this.paintCount = this.xPaint.length - 1;
            if (this.type == 27) {
                new Explosion(this.x, this.y, (byte) 0);
            }

            this.nBullUpdate();
            this.x = this.xPaint[this.paintCount];
            this.y = this.yPaint[this.paintCount];
            if (this.type != 28) {
                this.explode(9);
            }

            if (this.type == 28) {
                this.isActiveRain = true;
                this.changeFrame = true;
            }
        }

        if (this.smoke) {
            this.createSmoke((byte) 0);
        }

        if (this.blackSmoke) {
            this.createSmoke((byte) 1);
        }

        int dis;
        int deltaY;
        if (this.fireSmoke) {
            dis = CRes.random(-7, 7);
            deltaY = CRes.random(-7, 7);
            GameScr.sm.addSmoke(this.x + dis, this.y + deltaY, (byte) 21);
        }

        if (this.x == CPlayer.xSuper && this.y == CPlayer.ySuper) {
            new Explosion(this.x, this.y, (byte) 0);
            this.isSuper = true;
        }

        if (this.type == 48 && this.pingFrame < this.postion.size()) {
            Position p = (Position) this.postion.elementAt(this.pingFrame);
            deltaY = p.x;
            int yHit = p.y;
            int way = Smoke.checkWay(x1, y1, this.x, this.y);
            byte f;
            if (way != 0 && way != 1) {
                f = 1;
            } else {
                f = 3;
            }

            if (this.x == deltaY && this.y == yHit) {
                this.getPingPongFrame(0, f);
                ++this.pingFrame;
                this.pingColor += 4;
            }
        }

        if (this.pingChange) {
            this.curFrame = this.mainFrame + this.pos + this.pingColor;
            ++this.t;
            if (this.t == 3) {
                this.t = 0;
                this.pingChange = false;
                this.curFrame = this.pingColor;
            }
        }

        if (this.longSmoke && (Math.abs(this.x - x1) >= 10 || Math.abs(this.y - y1) >= 10)) {
            GameScr.sm.addSmoke((this.x + x1) / 2 + CRes.random(-3, 3), (this.y + y1) / 2 + CRes.random(-3, 3), (byte) 19);
        }

        if (this.chickenHair && CCanvas.gameTick % 10 == 0) {
            GameScr.sm.addRock(this.x, this.y, CRes.random(4 + this.force / 3), CRes.random(-8 - this.force / 3, -5 - this.force / 3), (byte) 8);
        }

        if (this.fireSmoke) {
            dis = CRes.random(-7, 7);
            deltaY = CRes.random(-7, 7);
            if (Math.abs(this.x - x1) >= 10 || Math.abs(this.y - y1) >= 10) {
                GameScr.sm.addSmoke((this.x + x1) / 2 + dis, (this.y + y1) / 2 + deltaY, (byte) 21);
            }
        }

        if (isDoubleBull(this.type)) {
            ++this.timecount;
            if (BM.nOrbit > 1) {
                if (this.timecount == BM.force2 && this.type == 17) {
                    this.isActiveHammer = true;
                    BM var10000 = GameScr.bm;
                    var10000.nBull += 3;
                    return;
                }

                if (this.timecount == BM.force2 && this.type == 19) {
                    this.isActiveEgg = true;
                    GameScr.bm.activeEgg(this.x, this.y);
                }
            }
        }

        if (this.lazerBullet) {
            if (this.type == 14) {
                this.lazerBullUpdate(0);
            }

            if (this.type == 40) {
                this.lazerBullUpdate(1);
            }
        }

        if (this.type == 25) {
            this.undergUpdate();
        }

        if (this.type == 52 && GameScr.mm.isLand(this.x, this.y)) {
            GameScr.sm.addRock(this.x, this.y, CRes.random(4 + this.force / 3), CRes.random(-8 - this.force / 3, -5 - this.force / 3), (byte) 2);
            new Explosion(this.x, this.y, (byte) 0);
            CRes.err("===> fixedUpdate() = " + this.type);
            GameScr.mm.makeHole(this.x, this.y, this.type, 3);
        }

        if (this.type == 28) {
            this.missileRainUpdate();
        }

        if (this.type == 30) {
            this.holeBulletUpdate();
        }

        if (this.tonardo) {
            this.tornadoBullet();
        }

        if (this.rotate) {
            this.lastAngle = this.angleRotate;
            dis = CRes.angle(this.x - x1, this.y - y1);
            this.curFrame = (dis << 1) / 45;
            this.angleRotate = dis;
            if (this.type == 44 && this.curFrame > 7) {
                this.curFrame = 15 - this.curFrame;
            }
        }

        if (this.translate) {
            this.checkTranslate();
        }

        if (this.changeFrame) {
            if (this.type == 37) {
                this.angle = CRes.angle(this.x - x1, this.y - y1);
                if (this.angle == -90 || this.angle == 270) {
                    this.trans = 0;
                    if (CCanvas.gameTick % 2 == 0) {
                        GameScr.sm.addSmoke(this.x, this.y + 15, (byte) 15);
                    }
                }

                if (this.angle == 90) {
                    this.trans = 3;
                    if (CCanvas.gameTick % 2 == 0) {
                        GameScr.sm.addSmoke(this.x, this.y - 15, (byte) 15);
                    }
                }
            }

            this.changeFrame(false);
        } else if (this.changeFrameLeft) {
            this.changeFrame(true);
        }

        if (this.type == 4 && GameScr.curGRAPHIC_LEVEL == 0) {
            GameScr.isDarkEffect = true;
        }

        if (this.type == 23 || this.type == 54) {
            GameScr.sm.addSmoke(this.x - CRes.random(5), this.y - CRes.random(5), (byte) 11);
            GameScr.sm.addSmoke(this.x + CRes.random(5), this.y + CRes.random(5), (byte) 11);
            GameScr.sm.addSmoke(this.x + CRes.random(5), this.y - CRes.random(5), (byte) 11);
        }

        if (this.type == 15) {
            if (!this.lazerStop) {
                ++this.explDelay;
                if (this.explDelay == 26) {
                    GameScr.bm.removeBullet(this, true, this.x, this.y, this.x_Last, this.y_Last, this.whoShot);
                    return;
                }

                if (this.explDelay == 5 || this.explDelay == 8) {
                    this.lazerShoot = true;
                }
            }

            if (this.lazerShoot) {
                this.lazerShoot = false;
                this.explode(10);
            }
        }

        if (MM.isHaveWaterOrGlass && !this.isWaterBum) {
            byte waterBumType = 0;
            if (this.type == 0) {
                waterBumType = 1;
            }

            if (MM.checkWaterBum(this.x, this.y, waterBumType)) {
                this.isWaterBum = true;
            }
        }

        dis = this.type != 19 ? 100 : 500;
        if (this.x >= -dis && this.x <= MM.mapWidth + dis && this.y <= MM.mapHeight + 100) {
            if (this.paintCount < this.xPaint.length - 1) {
                ++this.paintCount;
            } else {
                if (this.type == 26) {
                    if (BM.nOrbit != 1) {
                        this.isActiveMissile = true;
                        GameScr.bm.nBull = 4;
                    } else {
                        GameScr.bm.nBull = 1;
                    }
                }

                if (this.type != 15) {
                    this.explode(11);
                }
            }

        } else {
            if (GameScr.isDarkEffect) {
                GameScr.isDarkEffect = false;
            }

            this.x_Last = this.x;
            this.y_Last = this.y;
            this.nBullUpdate();
            GameScr.bm.removeBullet(this, false, this.x, this.y, this.x_Last, this.y_Last, this.whoShot);
        }
    }

    public void getPingPongFrame(int mainFrame, int pos) {
        this.mainFrame = mainFrame;
        this.pos = pos;
        this.pingChange = true;
    }

    public void explode(int index) {
        CRes.err("===> explode() = " + this.type + " ___  index " + index);
        if (this.type != 6 && this.type != 5 && this.type != 22 && this.type != 30) {
            for (int i = 0; i < PM.p.length; ++i) {
                if (PM.p[i] != null) {
                    this.px = PM.p[i].x - 12;
                    this.py = PM.p[i].y - 24;
                    if (CRes.inRect(this.x, this.y, this.px - 5, this.py - 5, 34, 34)) {
                        PM.p[i].activeHurt(PM.p[i].look == 0 ? 0 : 2);
                        break;
                    }
                }
            }
        }

        if (this.type == 34) {
            new Explosion(this.x, this.y, (byte) 1);
        } else if (this.type == 4) {
            GameScr.bm.activeAirplane(this.x, this.y);
        } else if (this.type == 14) {
            CRes.err("===> type == BULL_LAZER_FLAG ACTIVE Lazer ");
            GameScr.bm.activeLazer(this.x, this.y);
        } else if (this.type == 13) {
            GameScr.bm.activeTornado(this.x, this.y);
        } else if (this.type == 23) {
            GameScr.bm.activeMeteor(this.x, this.y, this.angle);
        } else if (this.type == 16) {
            new Explosion(this.x, this.y, (byte) 0);
            GameScr.bm.activeMortarBum(this.x, this.y);
        } else if (this.type == 17) {
            if (this.isActiveHammer) {
                GameScr.bm.activeExplore(this.xD, this.yD, this.x, this.y, this.vx, this.vy, (byte) this.force, this.angle);
            }

            new Explosion(this.x, this.y, (byte) 0);
            GameScr.mm.makeHole(this.x, this.y, this.type, 4);
        } else if (this.type == 26) {
            if (this.isActiveMissile) {
                GameScr.bm.active4Missle(this.xS, this.yS, this.xD, this.yD);
            }

            new Explosion(this.x, this.y, (byte) 0);
            GameScr.mm.makeHole(this.x, this.y, this.type, 5);
        } else if (this.type == 28) {
            GameScr.bm.activeMissleRain(this.x, this.y);
            new Explosion(this.x, this.y, (byte) 0);
        } else if (this.type == 30) {
            CPlayer c = PM.getCurPlayer();
            GameScr.pm.updatePlayerXY(this.whoShot, (short) c.x, (short) c.y);
            new Explosion(this.x, this.y, (byte) 1);
        } else if (this.type != 5 && this.type != 36) {
            if (this.type != 8 && this.type != 56) {
                if (this.type == 57) {
                    new Explosion(this.x, this.y, (byte) 1);
                } else if (this.type != 53) {
                    if (this.type == 47) {
                        new Explosion(this.x, this.y, (byte) 10);
                        GameScr.mm.makeHole(this.x, this.y, this.type, 6);
                    } else {
                        GameScr.mm.makeHole(this.x, this.y, this.type, 7);
                        int range = 3;
                        if (this.type == 0 || this.type == 7 || this.type == 3 || this.type == 31 || this.type == 32) {
                            if (this.type == 31 || this.type == 32) {
                                PM.getCurPlayer().isPaint = false;
                            }

                            range = 2;
                        }

                        if (this.type == 31) {
                            new Explosion(this.x - 30, this.y + 5, (byte) 0);
                            new Explosion(this.x, this.y, (byte) 0);
                            new Explosion(this.x + 30, this.y + 5, (byte) 0);
                            GameScr.sm.addSmoke(this.x - 30, this.y + 5, (byte) 4);
                            GameScr.sm.addSmoke(this.x, this.y, (byte) 4);
                            GameScr.sm.addSmoke(this.x + 30, this.y + 5, (byte) 4);
                            GameScr.sm.addRock(this.x, this.y, CRes.random(4 + this.force / 3), CRes.random(-8 - this.force / 3, -5 - this.force / 3), (byte) 3);
                            GameScr.sm.addRock(this.x, this.y, CRes.random(3 + this.force / 3), CRes.random(-8 - this.force / 3, -4 - this.force / 3), (byte) 3);
                            GameScr.sm.addRock(this.x, this.y, CRes.random(2 + this.force / 3), CRes.random(-8 - this.force / 3, -4 - this.force / 3), (byte) 3);
                        }

                        byte rockType = 0;

                        int i;
                        for (i = 0; i < 2; ++i) {
                            rockType = (byte) (CRes.r.nextInt(range) < 1 ? 3 : 2);
                            if (this.type == 19) {
                                rockType = 8;
                            }

                            GameScr.sm.addRock(this.x, this.y, Math.abs(CRes.random(4 + this.force / 3)), -CRes.random(8 + this.force / 3, 10 + this.force / 3), (byte) rockType);
                            GameScr.sm.addRock(this.x, this.y, -Math.abs(CRes.random(4 + this.force / 3)), -CRes.random(8 + this.force / 3, 10 + this.force / 3), (byte) rockType);
                        }

                        if (this.type == 3) {
                            new Explosion(this.x - 30, this.y + 5, (byte) 0);
                            new Explosion(this.x, this.y, (byte) 0);
                            new Explosion(this.x + 30, this.y + 5, (byte) 0);
                            GameScr.sm.addSmoke(this.x - 30, this.y + 5, (byte) 4);
                            GameScr.sm.addSmoke(this.x, this.y, (byte) 4);
                            GameScr.sm.addSmoke(this.x + 30, this.y + 5, (byte) 4);
                        } else if (this.type == 15) {
                            new Explosion(this.x, this.y + 20, (byte) 0);
                            GameScr.sm.addSmoke(this.x, this.y, (byte) 4);
                        } else if (this.type != 13) {
                            if (this.type == 19) {
                                GameScr.sm.addRock(this.x, this.y, CRes.random(4 + this.force / 3), CRes.random(-8 - this.force / 3, -5 - this.force / 3), (byte) 10);
                            } else if (this.type == 22) {
                                new Explosion(this.x - 30, this.y + 5, (byte) 0);
                                new Explosion(this.x, this.y, (byte) 0);
                                new Explosion(this.x + 30, this.y + 5, (byte) 0);
                                GameScr.sm.addRock(this.x, this.y, CRes.random(4 + this.force / 3), CRes.random(-8 - this.force / 3, -5 - this.force / 3), (byte) 9);
                            } else if (this.type == 35) {
                                if (PM.getCurPlayer().gun != 15) {
                                    new Explosion(this.x - 15, this.y + 15, (byte) 0);
                                    new Explosion(this.x + 15, this.y + 15, (byte) 0);
                                }
                            } else if (this.type == 37) {
                                GameScr.whiteEffect = true;
                                GameScr.xNuke = this.x;
                                GameScr.yNuke = this.y;
                            } else if (this.type == 40) {
                                GameScr.electricEffect = true;
                                GameScr.xElectric = this.x;
                                GameScr.yElectric = this.y;
                            } else if (this.type == 54) {
                                GameScr.freezeEffect = true;
                                GameScr.xFreeze = this.x;
                                GameScr.yFreeze = this.y;
                            } else if (this.type == 50) {
                                GameScr.suicideEffect = true;
                                GameScr.xSuicide = this.x;
                                GameScr.ySuicide = this.y;
                                Camera.shaking = 2;
                            } else if (this.type == 55) {
                                GameScr.poisonEffect = true;
                                GameScr.xPoison = this.x;
                                GameScr.yPoison = this.y;
                            } else if (this.type == 55) {
                                new Explosion(this.x, this.y, (byte) 15);
                            } else if (this.type == 54) {
                                new Explosion(this.x, this.y, (byte) 14);
                            } else if (this.type == 42) {
                                for (i = 0; i < PM.p.length; ++i) {
                                    CPlayer p = PM.p[i];
                                    if (p != null && p.gun == 16 && p.getState() != 5) {
                                        BM.lazerPosition.addElement(new Position(p.x, p.y, this.x, this.y));
                                    }
                                }

                                BM.activeUFOLazer = true;
                            } else if (this.type == 45) {
                                GameScr.sm.addLazer(PM.p[this.whoShot].x, PM.p[this.whoShot].y, this.x, this.y, 0);
                                GameScr.electricEffect = true;
                                GameScr.xElectric = this.x;
                                GameScr.yElectric = this.y;
                            } else if (this.type == 43) {
                                new Explosion(this.x, this.y, (byte) 7);
                            } else if (this.type == 44) {
                                new Explosion(this.x, this.y, (byte) 9);
                            } else if (this.type == 48) {
                                new Explosion(this.x, this.y, (byte) 7);
                            } else if (this.type == 51) {
                                new Explosion(this.x, this.y, (byte) 12);
                            } else if (this.type != 53) {
                                if (GameScr.bm.critical == 0) {
                                    if (this.type == 49) {
                                        new Explosion(this.x, this.y, (byte) 9);
                                    } else {
                                        new Explosion(this.x, this.y, (byte) 0);
                                        GameScr.sm.addSmoke(this.x, this.y, (byte) 4);
                                    }
                                } else {
                                    if (this.type == 0) {
                                        new Explosion(this.x, this.y, (byte) 0);
                                        new Explosion(this.x - 35, this.y, (byte) 0);
                                        new Explosion(this.x + 35, this.y, (byte) 0);
                                    } else if (this.type == 10) {
                                        new Explosion(this.x, this.y, (byte) 7);
                                    } else if (this.type == 49) {
                                        for (i = 0; i < 3; ++i) {
                                            BM.lazerPosition.addElement(new Position(CRes.random(Camera.x, CCanvas.width + Camera.x), 0, this.x, this.y));
                                        }

                                        BM.activeUFOLazer = true;
                                        new Explosion(this.x, this.y, (byte) 0);
                                    } else if (this.type != 20 && this.type != 9) {
                                        new Explosion(this.x, this.y, (byte) 0);
                                        GameScr.sm.addSmoke(this.x, this.y, (byte) 4);
                                    } else {
                                        new Explosion(this.x, this.y, (byte) 7);
                                    }

                                    Camera.shaking = 2;
                                }
                            }
                        }

                        if (BM.isActiveTornado) {
                            Vector tonardos = BM.vTornado;

                            for (int c = 0; c < tonardos.size(); ++c) {
                                Tornado t = (Tornado) tonardos.elementAt(c);
                                if (this.x < t.x + 10) {
                                    int var10000 = t.x;
                                }
                            }
                        }

                        mSound.playSound(0, mSound.volumeSound, 2);
                    }
                }
            } else {
                CMap newCMap = new CMap(webId++, this.x - 21, this.y - 20, CMap.MANGNHEN, true);
                newCMap.index = MM.maps.size();
                GameScr.mm.addMap(newCMap);
                new Explosion(this.x, this.y, (byte) 1);
            }
        } else {
            GameScr.pm.updatePlayerXY(this.whoShot, (short) this.x, (short) this.y);
            PM.p[this.whoShot].falling = true;
            PM.p[this.whoShot].active = false;
            if (!PM.getCurPlayer().isJump) {
                new Explosion(this.x, this.y, (byte) 1);
            } else {
                if (PM.getCurPlayer().gun == 13) {
                    PM.getCurPlayer().frameleg_1 = 0;
                    PM.getCurPlayer().yBugBack = 8;
                    GameScr.sm.addRock(this.x, this.y, CRes.random(4 + this.force / 3), CRes.random(-8 - this.force / 3, -5 - this.force / 3), (byte) 3);
                    GameScr.sm.addRock(this.x, this.y, CRes.random(3 + this.force / 3), CRes.random(-8 - this.force / 3, -4 - this.force / 3), (byte) 3);
                    GameScr.sm.addRock(this.x, this.y, CRes.random(2 + this.force / 3), CRes.random(-8 - this.force / 3, -4 - this.force / 3), (byte) 3);
                }

                if (PM.getCurPlayer().gun == 14) {
                    PM.getCurPlayer().isBum = false;
                    PM.getCurPlayer().isFly = false;
                }
            }
        }

        if (this.type != 15) {
            GameScr.bm.removeBullet(this, true, this.x, this.y, this.x_Last, this.y_Last, this.whoShot);
        }

        if (CPlayer.isGetPosition) {
            PM.getXYResult();
            CPlayer.isGetPosition = false;
        }

    }

    public static boolean isFlagBull(int type) {
        return type == 4 || type == 14 || type == 16 || type == 23 || type == 28;
    }

    public static boolean isDoubleBull(int type) {
        return type == 17 || type == 19;
    }

    public void changeFrame(boolean isLeftDirect) {
        if (!isLeftDirect) {
            if (this.type == 3) {
                this.curFrame = GameScr.tickCount / 2 % 2;
            } else if (this.type == 4) {
                this.curFrame = GameScr.tickCount / 2 % 2;
            } else if (this.type == 20) {
                this.curFrame = GameScr.tickCount / 2 % 2;
            } else if (this.type == 55) {
                if (CCanvas.gameTick % 2 == 0) {
                    ++this.curFrame;
                }
            } else {
                ++this.curFrame;
            }

            if (this.curFrame == this.frmImg.nFrame) {
                this.curFrame = 0;
            }
        } else if (this.curFrame < 0) {
            this.curFrame = this.frmImg.nFrame - 1;
        } else if (this.type == 4) {
            this.curFrame = GameScr.tickCount / 2 % 2;
        } else {
            --this.curFrame;
        }

    }

    public void createSmoke(byte TYPE) {
        ++this.smokeDelay;
        if (this.smokeDelay > 3) {
            GameScr.sm.addSmoke(this.x, this.y, TYPE);
            this.smokeDelay = 0;
        }

    }

    public void paintLazer(mGraphics g) {
        int holeH = 20;
        int dis = -500;
        int xp = this.x - 10;
        if (this.explDelay < 15) {
            g.setColor(16771821);
            g.fillRect(xp, dis, 20, this.y + holeH - dis, false);
            g.setColor(16756407);
            g.fillRect(xp + 2, dis, 16, this.y + holeH - dis, false);
            g.setColor(16737907);
            g.fillRect(xp + 5, dis, 10, this.y + holeH - dis, false);
            g.setColor(15745615);
            g.fillRect(xp + 8, dis, 4, this.y + holeH - dis, false);
        }

        if (this.explDelay >= 15 && this.explDelay < 17) {
            g.setColor(16771821);
            g.fillRect(xp + 3, dis, 14, this.y + holeH - dis, false);
            g.setColor(16737907);
            g.fillRect(xp + 5, dis, 10, this.y + holeH - dis, false);
            g.setColor(15745615);
            g.fillRect(xp + 8, dis, 4, this.y + holeH - dis, false);
        }

        if (this.explDelay >= 17 && this.explDelay < 20) {
            g.setColor(16771821);
            g.fillRect(xp + 6, dis, 8, this.y + holeH - dis, false);
            g.setColor(15745615);
            g.fillRect(xp + 8, dis, 4, this.y + holeH - dis, false);
        }

        if (this.explDelay >= 20 && this.explDelay < 23) {
            g.setColor(16771821);
            g.fillRect(xp + 8, dis, 4, this.y + holeH - dis, false);
        }

    }

    public void paint(mGraphics g) {
        if (!this.notPaint) {
            if (this.type != 31) {
                if (this.isSuper) {
                    ++this.sFrame;
                    if (this.sFrame == 5) {
                        this.sFrame = 0;
                    }

                    g.drawRegion(superShoot, 0, 32 * this.sFrame, 32, 32, 0, this.x, this.y, 3, false);
                }

                if (this.dan != null) {
                    CCanvas.rotateImage(this.dan, this.angleRotate, g, this.x, this.y, true);
                } else if (this.frmImg != null) {
                    if (this.type != 2 && this.type != 11) {
                        this.frmImg.drawFrame(this.curFrame, this.x, this.y, this.trans, 3, g, false);
                    } else if (this.type == 2) {
                        this.frmImg.drawRegionFrame(this.curFrame, this.x, this.y, 0, 3, g, this.idBullet, 2);
                    } else if (this.type == 11) {
                        this.frmImg.drawRegionFrame(this.curFrame, this.x, this.y, 0, 3, g, this.idBullet, 4);
                    }
                } else if (this.img != null) {
                    if ((this.type < 0 || this.type > 8) && this.type != 49) {
                        g.drawImage(this.img, this.x, this.y, mGraphics.VCENTER | mGraphics.HCENTER, false);
                    } else {
                        g.drawRegion(this.img, 0, this.idBullet * this.img.image.getWidth(), this.img.image.getWidth(), this.img.image.getWidth(), 0, this.x, this.y, mGraphics.VCENTER | mGraphics.HCENTER, false);
                    }
                } else if (!this.lazerStop) {
                    this.paintLazer(g);
                }

                if (this.type == 49 && this.isPaintLazer) {
                    this.paintLazerGirl(g);
                }

            }
        }
    }

    public static int[] getCollisionPoint(int x1, int y1, int x2, int y2) {
        int w = x2 - x1;
        int h = y2 - y1;
        int dy2 = 0;
        int dx2 = 0;
        int dy1 = 0;
        int dx1 = 0;
        if (w < 0) {
            dx2 = -1;
            dx1 = -1;
        } else if (w > 0) {
            dx2 = 1;
            dx1 = 1;
        }

        if (h < 0) {
            dy1 = -1;
        } else if (h > 0) {
            dy1 = 1;
        }

        int longest = Math.abs(w);
        int shortest = Math.abs(h);
        if (longest <= shortest) {
            longest = Math.abs(h);
            shortest = Math.abs(w);
            if (h < 0) {
                dy2 = -1;
            } else if (h > 0) {
                dy2 = 1;
            }

            dx2 = 0;
        }

        int numerator = longest >> 1;

        for (int i = 0; i <= longest; ++i) {
            if (GameScr.mm.isLand(x1, y1)) {
                return new int[]{x1, y1};
            }

            numerator += shortest;
            if (numerator >= longest) {
                numerator -= longest;
                x1 += dx1;
                y1 += dy1;
            } else {
                x1 += dx2;
                y1 += dy2;
            }
        }

        return null;
    }

    public void onClearMap() {
    }
}
