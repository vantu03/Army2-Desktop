package player;

import CLib.Image;
import CLib.mGraphics;
import CLib.mImage;
import Equipment.PlayerEquip;
import coreLG.CCanvas;
import effect.Camera;
import effect.Explosion;

import java.util.Random;

import map.MM;
import model.CRes;
import model.CTime;
import network.MessageHandler;
import network.Session_ME;
import screen.GameScr;
import screen.PrepareScr;

public class Boss extends CPlayer {
    static Image umbrella;
    int[] xWeb = new int[6];
    int[] yWeb = new int[6];
    boolean bombShoot = false;
    boolean isGift_1 = false;
    boolean isGift_2 = false;
    boolean up = true;
    boolean down = false;
    int yGift_1 = 0;
    int yGift_2;
    int dy;
    int frameG;
    int tG;
    public static mImage gift_1;
    public static mImage gift_2;
    public static mImage gift_empty;
    public static mImage bongbong;
    int fanFrame;
    static int deltaYKC;
    static int tKC;
    public static int xTo;
    int dxUFO;
    int dyUFO;
    int dtXUFO;
    int dtYUFO;
    int wLazer;
    boolean changeSign;
    int deltaY;
    int tB;
    int deltaX1;
    int deltaY1;
    int deltaX2;
    int gFrame;
    int tg;
    int ghostLook;
    int tBit;
    int fireFrame;
    int tFire;
    int t;
    Random r;
    int vy;
    int dyRobot;
    public static int camY;
    public boolean getGift;

    static {
        try {
            gift_1 = mImage.createImage("/item/box.png");
            bongbong = mImage.createImage("/item/bongbong.png");
            gift_2 = mImage.createImage("/item/box2.png");
            gift_empty = mImage.createImage("/item/boxEmpty.png");
        } catch (Exception var1) {
            var1.printStackTrace();
        }

    }

    public Boss(int IDDB, byte index, int X, int Y, boolean isCom, int look, byte gunType, int maxHp) {
        super(IDDB, index, X, Y, isCom, look, gunType, (PlayerEquip) null, maxHp);
        this.yGift_2 = MM.mapHeight + 30;
        this.wLazer = 8;
        this.deltaX1 = 0;
        this.deltaY1 = 0;
        this.deltaX2 = 0;
        this.r = new Random();
        this.vy = this.random(5, 6);
        this.dyRobot = 18;
        if (this.gun == 11) {
            new Explosion(this.x, this.y, (byte) 1);
        }

        if (this.gun == 13 || this.gun == 14) {
            this.isJump = true;
        }

        if (this.gun == 16 || this.gun == 17 || this.gun == 19 || this.gun == 18 || this.gun == 21 || this.gun == 20 || this.gun == 22 || this.gun == 24 || this.gun == 25 || this.gun == 26) {
            this.flyPlayer = true;
        }

        if (this.gun == 22) {
            for (int i = 0; i < 6; ++i) {
                if (i == 0) {
                    this.xWeb[i] = CRes.random(20, CCanvas.width / 3);
                    this.yWeb[i] = CRes.random(this.y - 44, this.y);
                } else {
                    this.xWeb[i] = this.xWeb[i - 1] + 25 + CRes.random(0, MM.mapWidth / 3 - 20);
                    this.yWeb[i] = CRes.random(this.y - 44, this.y);
                }
            }
        }

        if (this.gun == 23) {
            new Explosion(this.x, this.yGift_1, (byte) 1);
        }

        if (this.gun == 25 || this.gun == 26) {
            new Explosion(this.x, this.y, (byte) 1);
        }

    }

    public void onPointerPressed(int x, int y, int index) {
        super.onPointerPressed(x, y, index);
    }

    public void paintBigRobot(mGraphics g) {
        if (this.state == 5) {
            g.drawImage(robotInjured, this.x, this.y, 33, false);
        } else {
            if (this.curFrame > 3) {
                this.curFrame = 1;
            }

            g.drawImage(robotLeg, this.x, this.y, 33, false);
            int align = this.curFrame != 3 ? 3 : 33;
            if (this.curFrame != 2 && this.curFrame != 3) {
                this.deltaX1 = -20;
            } else {
                this.deltaX1 = -15;
            }

            this.pFrameImg.drawFrame(this.curFrame, this.x + this.deltaX1, this.y - 22 + this.deltaY, 0, align, g);
            g.drawImage(robotBody, this.x - 1, this.y - 10 + this.deltaY, 33, false);
            this.pFrameImg.drawFrame(this.curFrame, this.x, this.y - 20 + this.deltaY, 0, align, g);
            if (this.isBum) {
                ++this.tFire;
                if (this.tFire == 10) {
                    this.tFire = 0;
                }

                if (this.tFire <= 5) {
                    this.fireFrame = 0;
                } else {
                    this.fireFrame = 1;
                }

                g.drawRegion(fire, 0, 10 * this.fireFrame, 5, 10, 0, this.x - 10, this.y, 17, false);
                g.drawRegion(fire, 0, 10 * this.fireFrame, 5, 10, 0, this.x + 10, this.y, 17, false);
                g.drawRegion(fire, 0, 10 * this.fireFrame, 5, 10, 0, this.x - 15, this.y, 17, false);
                g.drawRegion(fire, 0, 10 * this.fireFrame, 5, 10, 0, this.x + 15, this.y, 17, false);
            }

        }
    }

    public void paintKhiCau(mGraphics g) {
        g.drawImage(khicau, this.x, this.y + deltaYKC, 33, false);
        g.drawRegion(fan1, 0, this.fanFrame * 23, 4, 23, 0, this.x - 8, this.y - 18 + deltaYKC, 33, false);
        if (this.fanFrame == 0) {
            this.fanFrame = 1;
        } else if (this.fanFrame == 1) {
            this.fanFrame = 0;
        }

        if (CCanvas.gameTick % 2 == 0) {
            g.drawImage(fan2, this.x + 10, this.y - 35 + deltaYKC, 33, false);
            g.drawImage(fan2, this.x + 34, this.y - 35 + deltaYKC, 33, false);
        }

        g.drawImage(front_fan, this.x - 5, this.y - 43 + deltaYKC, 33, false);
        g.drawRegion(fan1, 0, this.fanFrame * 23, 4, 23, 0, this.x, this.y - 46 + deltaYKC, 33, false);
        int[] xinjured = new int[]{this.x, this.x - 30, this.x + 20};
        int[] yinjured = new int[]{this.y - 20, this.y - 35, this.y - 25};
        if (this.state == 5) {
            for (int i = 0; i < 3; ++i) {
                g.drawRegion(injured, 0, i * 12, 14, 12, 0, xinjured[i], yinjured[i] + deltaYKC, 0, false);
            }
        }

    }

    public void checkFrameBalloonGun() {
        if (xTo <= this.x + 50 && xTo >= this.x - 50) {
            this.curFrame = 1;
        } else if (xTo > this.x + 50) {
            this.curFrame = 0;
            this.look = 0;
        } else if (xTo < this.x - 50) {
            this.curFrame = 0;
            this.look = 2;
        }

    }

    public void paintFan1(mGraphics g) {
        int d = this.state == 5 ? 0 : deltaYKC;
        g.drawImage(back_fan, this.x, this.y + d, 33, false);
    }

    public void paintFan2(mGraphics g) {
    }

    public void paintEye(mGraphics g) {
        if (this.state == 5) {
            this.pFrameImg.drawFrame(2, this.x + 2, this.y + deltaYKC - 1, 0, 33, g);
        } else {
            if (CCanvas.gameTick % 2 == 0) {
                this.pFrameImg.drawFrame(0, this.x + 2, this.y + deltaYKC - 1, 0, 33, g);
            } else {
                this.pFrameImg.drawFrame(1, this.x + 2, this.y + deltaYKC - 1, 0, 33, g);
            }

        }
    }

    public void paintGunKhiCau(mGraphics g) {
        this.checkFrameBalloonGun();
        if (this.curFrame > 1) {
            this.curFrame = 0;
        }

        int d = this.state == 5 ? 0 : deltaYKC;
        g.drawImage(gunkhicau, this.x, this.y - 7 + d, mGraphics.BOTTOM | mGraphics.HCENTER, false);
        this.pFrameImg.drawFrame(this.curFrame, this.x + 2, this.y + d - 7, this.look, 3, g);
    }

    public void paintBombKhiCau(mGraphics g) {
        int d = this.state == 5 ? 0 : deltaYKC;
        g.drawImage(bombKhiCau, this.x, this.y + d, 33, false);
    }

    public void paintUFO(mGraphics g) {
        if (this.state == 8) {
            g.drawRegion(imgUFO, 0, 46, 51, 46, 0, this.x + this.dxUFO, this.y + this.dyUFO, 33, false);
        } else if (this.state == 5) {
            g.drawRegion(imgUFO, 0, 92, 51, 46, 0, this.x + this.dxUFO, this.y + this.dyUFO, 33, false);
        } else {
            g.drawRegion(imgUFO, 0, 0, 51, 46, 0, this.x + this.dxUFO, this.y + this.dyUFO, 33, false);
        }

        if (this.state != 5) {
            if (CCanvas.gameTick % 3 == 0) {
                g.drawRegion(imgUFOFire, 0, 0, 16, 11, 0, this.x + this.dxUFO, this.y + this.dyUFO - 5, 17, false);
            } else {
                g.drawRegion(imgUFOFire, 0, 11, 16, 11, 0, this.x + this.dxUFO, this.y + this.dyUFO - 5, 17, false);
            }

            if (this.isPointActive) {
                g.setColor(149905);

                for (int i = 0; i < this.wLazer; ++i) {
                    if (i < this.wLazer / 2 + this.wLazer / 3 && i > this.wLazer / 2 - this.wLazer / 3) {
                        g.setColor(16777215);
                    } else {
                        g.setColor(5880763);
                    }

                    g.drawLine(this.x - this.wLazer / 2 + i + this.dxUFO, this.y + this.dyUFO, this.x - this.wLazer / 2 + i + this.dxUFO, this.yPoint, false);
                }

                if (this.wLazer == 0) {
                    this.changeSign = true;
                }

                if (this.wLazer == 8) {
                    this.changeSign = false;
                }

                if (CCanvas.gameTick % 2 == 0) {
                    if (!this.changeSign) {
                        --this.wLazer;
                    } else {
                        ++this.wLazer;
                    }
                }
            }

        }
    }

    public void paintSpider(mGraphics g) {
        g.setColor(8026746);

        int i;
        for (i = 0; i < 3; ++i) {
            g.drawLine(0, this.capY + i * 22 - 44, MM.mapWidth, this.capY + i * 22 - 44, false);
        }

        for (i = 0; i < 3; ++i) {
            g.drawImage(web, this.xWeb[i], this.yWeb[i], 3, false);
        }

        if (this.sLook == 0) {
            g.drawRegion(spider, 0, this.fspider * 22, 41, 22, 1, this.x + 1, this.y, 33, false);
            g.drawRegion(spider, 0, this.fspider * 22, 41, 22, 0, this.x, this.y - 22, 33, false);
        }

        if (this.sLook == 1) {
            g.drawRegion(spider, 0, this.fspider * 22, 41, 22, 3, this.x, this.y, 33, false);
            g.drawRegion(spider, 0, this.fspider * 22, 41, 22, 2, this.x, this.y - 22, 33, false);
        }

        if (this.sLook == 2) {
            g.drawRegion(spider, 0, this.fspider * 22, 41, 22, 4, this.x, this.y, 40, false);
            g.drawRegion(spider, 0, this.fspider * 22, 41, 22, 5, this.x + 22, this.y, 40, false);
        }

        if (this.sLook == 3) {
            g.drawRegion(spider, 0, this.fspider * 22, 41, 22, 6, this.x, this.y, 40, false);
            g.drawRegion(spider, 0, this.fspider * 22, 41, 22, 7, this.x + 22, this.y, 40, false);
        }

        if (this.isCapture) {
            g.setColor(5395026);
            g.fillRect(this.capX, this.capY - 41, 1, this.y - this.capY, false);
        }

    }

    public void paintBugRobot(mGraphics g) {
        byte LOOK;
        if (this.look == 2) {
            LOOK = 0;
            if (this.curFrame == 1) {
                this.deltaX1 = 15;
                this.deltaY1 = 0;
            }

            if (this.curFrame == 2) {
                this.deltaX1 = 15;
                this.deltaY1 = -5;
            }

            if (this.curFrame == 3) {
                this.deltaX1 = 7;
                this.deltaY1 = -12;
            }

            this.deltaX2 = 4;
        } else {
            LOOK = 2;
            if (this.curFrame == 1) {
                this.deltaX1 = -15;
                this.deltaY1 = 0;
            }

            if (this.curFrame == 2) {
                this.deltaX1 = -15;
                this.deltaY1 = -5;
            }

            if (this.curFrame == 3) {
                this.deltaX1 = -7;
                this.deltaY1 = -12;
            }

            this.deltaX2 = -4;
        }

        if (this.look == 0) {
            this.xBugBack = -this.xBugBack;
        }

        int xBody = this.x - this.deltaX2 + (this.curFrame != 1 && this.curFrame != 2 ? 0 : this.xBugBack);
        int yBody = this.y - 10 + this.deltaY + (this.curFrame != 2 && this.curFrame != 3 ? 0 : this.yBugBack);
        int xGun = this.x + this.deltaX1 + (this.curFrame != 1 && this.curFrame != 2 ? 0 : this.xBugBack);
        int yGun = this.y - 15 + this.deltaY + this.deltaY1 + (this.curFrame != 2 && this.curFrame != 3 ? 0 : this.yBugBack);
        g.drawRegion(bugbody, 0, 30 * this.framebd_1, 42, 30, LOOK, xBody, yBody, mGraphics.BOTTOM | mGraphics.HCENTER, false);
        g.drawRegion(bugleg, 0, 25 * this.frameleg_1, 44, 25, LOOK, this.x, this.y, mGraphics.BOTTOM | mGraphics.HCENTER, false);
        if (this.curFrame == 0 || this.curFrame == 6) {
            this.curFrame = 1;
        }

        this.pFrameImg.drawFrame(this.curFrame, xGun, yGun, this.look, mGraphics.BOTTOM | mGraphics.HCENTER, g);
    }

    void paintBossHP(mGraphics g, int h) {
        if (this.state != 5 && this.hp > 0) {
            g.setColor(16777215);
            g.fillRect(this.x - 15 + this.dxUFO, this.y + 5 - h + this.dyUFO, 25, 4, false);
            if (this.hpRectW > 16) {
                g.setColor(65280);
            } else if (this.hpRectW > 8) {
                g.setColor(16744512);
            } else {
                g.setColor(16711680);
            }

            if (this.hpRectW > 25) {
                this.hpRectW = 25;
            }

            g.fillRect(this.x - 15 + this.dxUFO, this.y + 5 - h + this.dyUFO, this.hpRectW, 4, false);
            g.setColor(0);
            g.drawRect(this.x - 15 + this.dxUFO, this.y + 5 - h + this.dyUFO, 25, 4, false);
        }

    }

    public void paintGhost(mGraphics g, int type) {
        g.drawRegion(type == 0 ? ghost : ghost2, 0, this.gFrame * 32, 35, 32, this.look, this.x, this.y, 33, false);
    }

    public void paint(mGraphics g) {
        if (this.isPaint) {
            if (this.gun == 13) {
                this.paintBugRobot(g);
                this.paintBossHP(g, 50);
                this.painthpChange(g);
            } else if (this.gun == 15) {
                this.paintBigRobot(g);
                this.paintBossHP(g, 60);
                this.painthpChange(g);
            } else if (this.gun == 16) {
                this.paintUFO(g);
                this.paintBossHP(g, 55);
                this.painthpChange(g);
            } else if (this.gun == 17) {
                this.paintKhiCau(g);
            } else if (this.gun == 18) {
                this.paintGunKhiCau(g);
                this.paintBossHP(g, -7);
                this.painthpChange(g);
            } else if (this.gun == 21) {
                this.paintEye(g);
                this.paintBossHP(g, 15);
                this.painthpChange(g);
            } else if (this.gun == 19) {
                this.paintBombKhiCau(g);
                this.paintBossHP(g, -7);
                this.painthpChange(g);
            } else if (this.gun == 20) {
                this.paintFan1(g);
                this.paintBossHP(g, 30);
                this.painthpChange(g);
            } else if (this.gun == 22) {
                this.paintSpider(g);
                this.paintBossHP(g, 48);
                this.painthpChange(g);
            } else {
                if (this.gun == 14 && (this.isFly || this.isBum)) {
                    ++this.tFire;
                    if (this.tFire == 10) {
                        this.tFire = 0;
                    }

                    if (this.tFire <= 5) {
                        this.fireFrame = 0;
                    } else {
                        this.fireFrame = 1;
                    }

                    g.drawRegion(fire, 0, 10 * this.fireFrame, 5, 10, 0, this.x - 5, this.y + 2, mGraphics.HCENTER | mGraphics.TOP, false);
                    g.drawRegion(fire, 0, 10 * this.fireFrame, 5, 10, 0, this.x + 5, this.y + 2, mGraphics.HCENTER | mGraphics.TOP, false);
                }

                if (this.gun == 25) {
                    this.paintGhost(g, 0);
                    this.paintBossHP(g, 40);
                    this.painthpChange(g);
                } else if (this.gun == 26) {
                    this.paintGhost(g, 1);
                    this.paintBossHP(g, 40);
                    this.painthpChange(g);
                } else if (this.gun == 23) {
                    if (this.state != 5 && this.hp > 0) {
                        g.drawImage(gift_1, this.x, this.yGift_1, 33, false);
                    }

                } else if (this.gun == 24) {
                    if (this.state != 5) {
                        ++this.tG;
                        if (this.tG == 10) {
                            this.tG = 0;
                        }

                        if (this.tG < 5) {
                            this.frameG = 0;
                        } else {
                            this.frameG = 1;
                        }

                        g.drawImage(gift_2, this.x, this.yGift_2 - 8, 33, false);
                        g.drawRegion(bongbong, 0, this.frameG * 35, 35, 35, 0, this.x, this.yGift_2, 33, false);
                    }

                } else {
                    super.paint(g);
                }
            }
        }
    }

    public void frameMoveCheck() {
        ++this.t;
        if (this.t == 10) {
            this.t = 0;
        }

        if (this.t < 5) {
            this.curFrame = 1;
        } else {
            this.curFrame = 2;
        }

    }

    public void frameStandCheck() {
        if (this.bombShoot) {
            this.curFrame = 4;
        } else {
            ++this.t;
            if (this.t == 10) {
                this.t = 0;
            }

            if (this.t < 5) {
                this.curFrame = 0;
            } else {
                this.curFrame = 1;
            }
        }

    }

    public int random(int a, int b) {
        return a + this.r.nextInt(b - a);
    }

    public void update() {
        super.update();
        int i;
        switch (this.gun) {
            case 11:
            case 12:
                if (this.state == 5) {
                    this.curFrame = 3;
                    return;
                }

                if (this.isCom && !this.isJump) {
                    if (this.nextx < this.x && !this.falling) {
                        this.move(0);
                        this.frameMoveCheck();
                        return;
                    }

                    if (this.nextx > this.x && !this.falling) {
                        this.move(2);
                        this.frameMoveCheck();
                        return;
                    }

                    if (this.nextx == this.x && this.nexty != this.y && this.state == 0 && !this.falling) {
                        this.y = this.nexty;
                    }
                }

                if (this.shootFrame) {
                    if (this.gun == 12) {
                        this.bombShoot = true;
                    }
                } else {
                    this.bombShoot = false;
                }

                this.frameStandCheck();
                break;
            case 13:
                if (this.state == 5) {
                    this.frameleg_1 = 2;
                    this.framebd_1 = 1;
                    this.curFrame = 0;
                    return;
                }

                if (this.xBugBack < 0) {
                    this.xBugBack += 2;
                }

                if (this.xBugBack > 0) {
                    this.xBugBack = 0;
                }

                if (this.yBugBack > 0) {
                    --this.yBugBack;
                }

                if (this.yBugBack < 0) {
                    this.yBugBack = 0;
                }

                if (this.state != 4) {
                    ++this.tB;
                    if (this.tB == 10) {
                        this.tB = 0;
                    }

                    if (this.tB == 5) {
                        this.deltaY = 1;
                    }

                    if (this.tB == 0) {
                        this.deltaY = 0;
                    }
                }

                if (this.state == 4) {
                    this.tB = 0;
                    this.deltaY = 0;
                }

                if (this.frameleg_1 != 0) {
                    if (this.x < this.xBug) {
                        this.look = 2;
                    } else {
                        this.look = 0;
                    }
                }
                break;
            case 14:
                if (this.isBum) {
                    this.y -= this.dyRobot;
                    --this.dyRobot;
                    if (this.dyRobot == 0) {
                        this.isBum = false;
                        this.dyRobot = 18;
                        this.falling = true;
                    }
                }
                break;
            case 15:
                if (this.state != 4) {
                    ++this.tB;
                    if (this.tB == 10) {
                        this.tB = 0;
                    }

                    if (this.tB == 5) {
                        this.deltaY = 1;
                    }

                    if (this.tB == 0) {
                        this.deltaY = 0;
                    }
                }

                if (this.isBum) {
                    this.y -= this.dyRobot;
                    --this.dyRobot;
                    if (this.dyRobot == 0) {
                        this.falling = true;
                    }
                }

                if (this.isBum && GameScr.mm.isLand(this.x, this.y)) {
                    this.earthwakeActive = true;
                    this.dyRobot = 18;
                    this.isBum = false;
                }

                if (this.earthwakeActive) {
                    this.earthwakeActive = false;

                    for (i = 0; i < 4; ++i) {
                        GameScr.sm.addRock(this.x - i * 20, this.y, CRes.random(4 + this.force / 3), CRes.random(-8 - this.force / 3, -5 - this.force / 3), (byte) 3);
                        GameScr.sm.addRock(this.x + i * 20, this.y, CRes.random(4 + this.force / 3), CRes.random(-8 - this.force / 3, -5 - this.force / 3), (byte) 3);
                    }

                    Camera.shaking = 3;
                }
                break;
            case 16:
                if (this.flyActive) {
                    this.flyTo(10);
                } else if (this.state != 4 && this.state != 5) {
                    if (CCanvas.gameTick % 2 == 0) {
                        ++this.dtXUFO;
                        if (this.dtXUFO == 20) {
                            this.dtXUFO = 0;
                        }

                        if (this.dtXUFO < 10) {
                            ++this.dxUFO;
                        } else {
                            --this.dxUFO;
                        }

                        ++this.dtYUFO;
                        if (this.dtYUFO == 10) {
                            this.dtYUFO = 0;
                        }

                        if (this.dtYUFO < 5) {
                            --this.dyUFO;
                        } else {
                            ++this.dyUFO;
                        }
                    }
                } else {
                    this.dxUFO = 0;
                    this.dyUFO = 0;
                }
                break;
            case 17:
                if (this.state == 5) {
                    ++this.y;
                    this.falling = false;
                    if (CCanvas.gameTick % 5 == 0) {
                        new Explosion(CRes.random(this.x - 50, this.x + 50), CRes.random(this.y - 40, this.y), (byte) 0);
                    }
                }

                if (this.flyActive) {
                    this.flyTo(10);
                    deltaYKC = 0;
                    if (this.bulletType == 43) {
                        camY += 2;
                        GameScr.cam.setTargetPointMode(this.x, this.y + camY);
                    }
                } else {
                    camY = 0;
                    deltaYKC = PM.deltaYKC;
                }

                for (i = 0; i < PM.p.length; ++i) {
                    if (PM.p[i] != null) {
                        if (PM.p[i].state != 5) {
                            if (PM.p[i].gun == 18) {
                                PM.p[i].x = this.x + 51;
                                PM.p[i].y = this.y + 10;
                            }

                            if (PM.p[i].gun == 19) {
                                PM.p[i].x = this.x - 10;
                                PM.p[i].y = this.y + 30;
                            }

                            if (PM.p[i].gun == 20) {
                                PM.p[i].x = this.x - 60;
                                PM.p[i].y = this.y - 13;
                            }
                        }

                        if (PM.p[i].gun == 21) {
                            PM.p[i].x = this.x + 53;
                            PM.p[i].y = this.y - 24;
                        }
                    }
                }
            case 18:
            case 19:
            case 20:
            case 21:
            default:
                break;
            case 22:
                this.runSpeed = 5;
                if (this.flyActive) {
                    ++this.t;
                    if (this.t == 4) {
                        this.t = 0;
                    }

                    if (this.t < 2) {
                        this.fspider = 0;
                    } else {
                        this.fspider = 1;
                    }
                } else if (this.state == 5) {
                    this.fspider = 3;
                } else if (this.state == 8) {
                    this.fspider = 2;
                } else {
                    this.fspider = 0;
                }

                if (this.flyActive) {
                    this.flyTo(8);
                }

                if (this.isCapture) {
                    this.capturePlayer();
                }
                break;
            case 23:
                if (PrepareScr.currLevel == 7 && MessageHandler.nextTurnFlag) {
                    this.yGift_1 = this.y;
                    this.isGift_1 = true;
                    Session_ME.receiveSynchronized = 0;
                }

                if (!this.isGift_1) {
                    ++this.dy;
                    this.yGift_1 += this.dy;
                    GameScr.cam.setTargetPointMode(this.x, this.yGift_1);
                    if (this.yGift_1 > this.y) {
                        this.isGift_1 = true;
                        this.yGift_1 = this.y;
                        CTime.seconds += 2;
                        CCanvas.tNotify = 0;
                        CCanvas.lockNotify = true;
                    }
                }

                if (this.state == 5 && !this.getGift) {
                    this.getGift = true;
                    new Explosion(this.x, this.y, (byte) 1);
                }
                break;
            case 24:
                if (PrepareScr.currLevel == 7 && MessageHandler.nextTurnFlag) {
                    this.yGift_2 = this.y;
                    this.isGift_2 = true;
                    Session_ME.receiveSynchronized = 0;
                }

                if (!this.isGift_2) {
                    this.yGift_2 -= 9;
                    GameScr.cam.setTargetPointMode(this.x, this.yGift_2);
                    if (this.yGift_2 < this.y) {
                        this.isGift_2 = true;
                        this.yGift_2 = this.y;
                        CTime.seconds += 2;
                        CCanvas.tNotify = 0;
                        CCanvas.lockNotify = true;
                    }
                } else {
                    if (this.up) {
                        --this.yGift_2;
                    }

                    if (this.down) {
                        ++this.yGift_2;
                    }

                    if (this.yGift_2 < this.y - 3) {
                        this.down = true;
                        this.up = false;
                    }

                    if (this.yGift_2 > this.y + 3) {
                        this.down = false;
                        this.up = true;
                    }
                }

                if (this.state == 5 && !this.getGift) {
                    this.getGift = true;
                    new Explosion(this.x, this.y, (byte) 1);
                }
                break;
            case 25:
            case 26:
                if (!this.ghostBit) {
                    ++this.tg;
                    if (this.tg == 10) {
                        this.tg = 0;
                    }

                    if (this.tg < 5) {
                        this.gFrame = 0;
                    } else {
                        this.gFrame = 1;
                    }

                    this.tBit = 0;
                } else {
                    ++this.tBit;
                    if (this.tBit == 5) {
                        this.gFrame = 2;
                    }

                    if (this.tBit == 10) {
                        this.gFrame = 3;
                        PM.p[this.playerHit].activeHurt(this.look == 2 ? 0 : 2);
                        GameScr.sm.addSmoke(PM.p[this.playerHit].x, PM.p[this.playerHit].y, (byte) 22);
                    }

                    if (this.tBit == 18) {
                        this.ghostBit = false;
                        Session_ME.receiveSynchronized = 0;
                    }
                }

                if (this.flyActive) {
                    this.flyTo(10);
                }
        }

    }
}
