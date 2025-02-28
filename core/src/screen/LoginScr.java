package screen;

import CLib.RMS;
import CLib.mGraphics;
import CLib.mImage;
import CLib.mSound;
import CLib.mSystem;
import com.badlogic.gdx.Gdx;
import com.teamobi.mobiarmy2.GameMidlet;
import coreLG.CCanvas;
import coreLG.CONFIG;
import coreLG.TerrainMidlet;
import effect.Cloud;
import effect.Explosion;
import effect.SmokeManager;
import item.Bullet;

import java.util.Vector;

import map.Background;
import map.CMap;
import map.MM;
import model.CRes;
import model.FilePack;
import model.Font;
import model.GetString;
import model.IAction;
import model.Language;
import model.TField;
import network.Command;
import network.GameLogicHandler;
import network.GameService;
import network.Session_ME;

public class LoginScr extends CScreen {
    public static String user = "";
    public static String pass = "";
    private TField tUser;
    private TField tPass;
    private TField tEmail;
    public int focus;
    int xC;
    int wC;
    int yL;
    int defYL;
    public static mImage lgGame;
    public static mImage imgPlane;
    public static mImage stone;
    public static mImage missile;
    public int plX;
    public int plY;
    public int lY;
    public int lX;
    public int logoDes;
    public int lineX;
    public int lineY;
    public static boolean isWait;
    public static int currTime = 0;
    public static int maxTime = 15;
    public boolean isDemo;
    public boolean isPlane;
    public boolean isCloud;
    public boolean isAskSound;
    public boolean isForward;
    public boolean isMenu;
    public int demoStat;
    public static boolean isLoadData = false;
    public static mImage imgVMB;
    public static mImage imgCheck;
    public static int startTimeOut;
    String numSupport = "";
    int xLog;
    int yLog;
    public int cmy = -1500;
    private boolean isRegister;
    int finishDemo;
    Command cmdSignIn;
    Command cmdMenu;
    Command cmdForward;
    Command cmdRemember;
    Command cmdYes;
    Command cmdNo;
    Command cmdSelect;
    Command cmdRegister;
    Command cmdBack;
    private static String charName;
    private static String email_phone;
    private static String password;
    int dyLogo;
    int dyT = 0;
    int ty = 17;
    int deltaX;
    int tX;
    boolean activeFall;
    boolean explore;
    boolean logoII;
    public int[] xB;
    public int[] yB;
    int speed;
    int tII;
    int[] xCl1;
    int[] xCl2;
    int[] xBl;
    public static int remember;
    boolean isStone;
    int[] cloudX;
    int[] cloudY;
    int[] cloudVX;
    int[] cloudVY;
    public static int volume;

    static {
        try {
            new FilePack(CCanvas.getClassPathConfig(CONFIG.PATH_GUI + "gui"));
            imgCheck = mImage.createImage("/remember.png");
            imgPlane = Background.bigBalloon;
            missile = Bullet.rocket2;
            Object var0 = null;
        } catch (Exception var1) {
            var1.printStackTrace();
        }

        charName = "";
        email_phone = "";
        password = "";
        volume = 50;
    }

    public void show() {
        CCanvas.splashScr = null;
        CScreen.isSetClip = true;
        this.resetTF();
        this.tUser.name = "TUser";
        this.tPass.name = "TPass";
        super.show();
    }

    public LoginScr() {
        this.xB = new int[]{CCanvas.width + 15, CCanvas.width + 15};
        this.yB = new int[2];
        this.xCl1 = new int[]{-50, 0, 30, CCanvas.width / 2, CCanvas.width - 10, CCanvas.width + 20, 100};
        this.xCl2 = new int[]{CCanvas.width / 2 - 20, 50, CCanvas.width / 2 + 40, 100, CCanvas.width - 40, CCanvas.width - 10, 100};
        this.xBl = new int[]{CCanvas.width - 80, CCanvas.width / 2, CCanvas.width / 2 - 50, 30};
        this.isStone = true;
        this.cloudX = new int[]{CCanvas.width, CCanvas.width + 100, CCanvas.width - 10, CCanvas.width + 50};
        this.cloudY = new int[]{40, 70, 90, 160};
        this.cloudVX = new int[]{-5, -3, -4, -3};
        this.cloudVY = new int[4];
        this.nameCScreen = " LoginScr screen!";
        this.initDemoData();
        this.isAskSound = true;
        this.isDemo = true;
        lgGame = GameScr.logoGame;
        if (GameScr.sm == null) {
            GameScr.sm = new SmokeManager();
        }

        GameScr.sm.addSmoke(-100, -100, (byte) 19);
        GameScr.exs = new Vector();
        new Explosion(-100, -100, (byte) 0);
        GameScr.curGRAPHIC_LEVEL = (byte) CRes.loadRMSInt("Graphic");
        if (GameScr.curGRAPHIC_LEVEL == -1) {
            GameScr.curGRAPHIC_LEVEL = 1;
        }

        CMap.isDrawRGB = CRes.loadRMSInt("drawRGB") == 0;
        this.yLog = CCanvas.width >= 200 ? CCanvas.hieght - cmdH - 125 : CCanvas.hieght - 125;
        this.plX = -100;
        this.plY = this.cmy + 60;
        this.lX = -100;
        this.lY = this.plY + 120;
        this.yB[0] = this.lY - 39;
        this.yB[1] = -1450;
        this.defYL = CCanvas.hh - 80;
        MM.mapHeight = CCanvas.hieght;
        this.wC = CCanvas.width - 30;
        if (this.wC < 70) {
            this.wC = 70;
        }

        if (this.wC > 99) {
            this.wC = 99;
        }

        if (CCanvas.width < 200) {
            this.wC = 70;
        }

        this.xC = (CCanvas.width - this.wC >> 1) + 29;
        this.tUser = new TField();
        this.tUser.y = CCanvas.hh - ITEM_HEIGHT - 9;
        this.tUser.width = this.wC;
        this.tUser.height = ITEM_HEIGHT + 2;
        this.tUser.setisFocus(true);
        this.tUser.setIputType(3);
        this.tPass = new TField();
        this.tPass.y = CCanvas.hh - 4;
        this.tPass.width = this.wC;
        this.tPass.height = ITEM_HEIGHT + 2;
        this.tPass.setisFocus(false);
        this.tPass.setIputType(2);
        this.tEmail = new TField();
        this.tEmail.y = CCanvas.hh - 8;
        this.tEmail.width = this.wC;
        this.tEmail.height = ITEM_HEIGHT + 2;
        this.tEmail.setisFocus(false);
        this.tEmail.setIputType(3);
        this.tUser.nameDebug = "TField ===> tUser login";
        this.tPass.nameDebug = "TField ===> tPass login";
        this.tEmail.nameDebug = "TField ===> tEmail login";
        this.tUser.setText(CRes.loadRMS_String("caroun"));
        this.tPass.setText(CRes.loadRMS_String("caropass"));
        remember = CRes.loadRMSInt("remember");
        if (remember == -1) {
            remember = 0;
        }

        this.initSignIn();
    }

    public String getUrlUpdateGame() {
        return "http://wap.teamobi.com?info=checkupdate&game=3&version=" + GameMidlet.version + "&provider=" + TerrainMidlet.PROVIDER;
    }

    public void connect() {
        Session_ME.gI().connect(GameMidlet.IP, GameMidlet.PORT);
        if (TerrainMidlet.isTeamClient) {
            GameService.gI().setProvider(TerrainMidlet.PROVIDER);
            new GetString();
            GameService.gI().getString("abc");
            GameService.gI().platform_request();
        } else {
            TerrainMidlet.PROVIDER = (byte) CRes.loadRMSInt("provider");
            TerrainMidlet.AGENT = CRes.loadRMS_String("agent");
            if (TerrainMidlet.AGENT == null) {
                TerrainMidlet.AGENT = "";
            }

            if (TerrainMidlet.PROVIDER != -1) {
                GameService.gI().setProvider(TerrainMidlet.PROVIDER);
                GameService.gI().getString(TerrainMidlet.AGENT);
            }
        }

        CCanvas.startWaitDlgWithoutCancel(Language.connecting(), 111111);
    }

    protected void doForgetPass(final String user) {
        CCanvas.startYesNoDlg(Language.usingPhone(), new IAction() {
            public void perform() {
                if (!Session_ME.gI().isConnected()) {
                    LoginScr.this.connect();
                } else {
                    CCanvas.startWaitDlg(Language.pleaseWait());
                }

                GameService.gI().requestService((byte) 4, user);
            }
        }, new IAction() {
            public void perform() {
                CCanvas.startOKDlg(Language.usingPhone2());
            }
        });
    }

    public void setRegister() {
        this.isRegister = true;
        this.tUser.resetTextBox();
        this.tEmail.resetTextBox();
        this.tPass.resetTextBox();
    }

    public void setLogin() {
        this.isRegister = false;
        this.center = this.cmdSignIn;
        this.initSignIn();
    }

    private void initDemoData() {
        this.isAskSound = false;
        this.demoStat = 0;
        volume = CRes.loadRMSInt("sound");
        if (volume > 0) {
            mSound.setVolume(volume);
        }

    }

    public void doRegister() {
        CCanvas.startYesNoDlg(Language.dangKyGam(), new IAction() {
            public void perform() {
                mSystem.openUrl(GameMidlet.linkReg);
            }
        }, new IAction() {
            public void perform() {
                CCanvas.endDlg();
            }
        });
    }

    public void doLogin() {
        user = this.tUser.getText().toLowerCase().trim();
        pass = this.tPass.getText();
        if (user.equals("")) {
            CCanvas.startOKDlg(Language.idPlease());
        } else if (pass.equals("")) {
            CCanvas.msgdlg.setInfo(Language.passPlease(), (Command) null, new Command("OK", new IAction() {
                public void perform() {
                    CCanvas.endDlg();
                    LoginScr.this.focus = 1;
                    LoginScr.this.tUser.setisFocus(false);
                    LoginScr.this.tPass.setisFocus(true);
                    LoginScr.this.right = LoginScr.this.tPass.cmdClear;
                }
            }), (Command) null);
            CCanvas.msgdlg.show();
        } else {
            if (!Session_ME.gI().connected) {
                this.connect();
            } else {
                CCanvas.startWaitDlgWithoutCancel(Language.logging(), 1);
            }

            if (!Session_ME.gI().isConnected()) {
                this.connect();
            }

            GameService.gI().getProviderAgent();
            GameService.gI().login(user, pass, GameMidlet.version);
        }
    }

    public void Effect() {
        this.dyT += 3;
        this.lY += this.dyT;
        if (this.lY > this.logoDes) {
            this.lY = this.logoDes;
            this.dyT = -this.ty;
            this.ty -= 4;
        }

    }

    public void planeUpdate() {
        this.plX += 2 + this.speed;
        int[] var10000;
        if (this.plX >= CCanvas.width / 2 - 10) {
            var10000 = this.xB;
            var10000[0] -= 10 + this.speed;
            if (!this.activeFall) {
                GameScr.sm.addSmoke(this.xB[0] + 20, this.yB[0] + 5, (byte) 19);
            }
        }

        if (this.xB[1] > -15 && this.plX > CCanvas.width / 3) {
            var10000 = this.xB;
            var10000[1] -= 12 + this.speed;
            if (!this.explore && this.speed == 0) {
                GameScr.sm.addSmoke(this.xB[1] + 20, this.yB[1] + 5, (byte) 19);
            }
        }

        if (!this.activeFall) {
            this.lX = this.plX + this.deltaX;
            this.lineX = this.lX - 1;
            this.lineY = this.lY - 40;
            ++this.tX;
            if (this.tX == 30) {
                this.tX = 0;
            }

            if (CCanvas.gameTick % 2 == 0) {
                if (this.tX <= 15) {
                    --this.deltaX;
                } else {
                    ++this.deltaX;
                }
            }
        }

        int i;
        int xR;
        int yR;
        if (this.xB[1] <= CCanvas.width && this.plX >= 0 && this.xB[1] <= this.plX + 80 && !this.explore) {
            this.explore = true;
            new Explosion(this.plX + 80, this.plY, (byte) 0);
            if (this.speed == 0) {
                for (i = 0; i < 6; ++i) {
                    xR = CRes.random(i + 1);
                    yR = CRes.random(-8, -5);
                    GameScr.sm.addRock(this.plX + 80, this.plY, xR, yR, (byte) 3);
                }
            }
        }

        if (this.xB[0] <= CCanvas.width && this.plX >= 0 && this.xB[0] <= this.plX - 12) {
            if (!this.activeFall) {
                this.activeFall = true;
                new Explosion(this.lineX, this.lineY, (byte) 0);
                if (this.speed == 0) {
                    for (i = 0; i < 6; ++i) {
                        xR = CRes.random(i + 1);
                        yR = CRes.random(-8, -5);
                        GameScr.sm.addRock(this.lineX, this.lineY, xR, yR, (byte) 3);
                    }
                }
            }

            if (this.activeFall) {
                this.lX -= 2 + this.speed / 3;
            }

            if (this.speed == 15) {
                this.lX -= 2 + this.speed / 3;
            }

            if (this.lX < CCanvas.width / 2 - 20) {
                this.lX = CCanvas.width / 2 - 20;
            }

            this.dyLogo += 1 + this.speed;
            this.lY += this.dyLogo / 2 + this.speed;

            for (i = 0; i < this.cloudY.length; ++i) {
                var10000 = this.cloudY;
                var10000[i] -= 2 + this.speed;
            }

            if (GameScr.exs.size() == 0 || this.speed != 0) {
                this.cmy += this.dyLogo / 2 + 2 + this.speed;
            }

            if (this.cmy > 0) {
                this.cmy = 0;
            }

            this.lineX = this.plX + this.deltaX - 1;
            if (this.lY > this.logoDes + 10) {
                this.lY = this.logoDes + 10;
                this.isDemo = false;
                this.isMenu = true;
                this.isForward = false;
                this.logoII = true;
                this.cmy = 0;
                this.right = this.tUser.cmdClear;
                this.left = this.cmdMenu;
                this.center = this.cmdSignIn;
            }
        }

    }

    public void update() {
        if (this.cmy > 0) {
            this.cmy = 0;
        }

        if (this.isForward) {
            this.speed = 15;
        } else {
            this.speed = 0;
        }

        int j;
        if (this.isAskSound) {
            Cloud.updateCloud();
        } else if (this.isDemo) {
            this.updateCloud();
            this.planeUpdate();
        } else {
            if (this.logoII) {
                ++this.tII;
                if (this.tII == 20) {
                    this.tII = 0;
                    this.logoII = false;
                    GameScr.sm.addLazer(CCanvas.width, 0, CCanvas.width / 2 + 45, this.tUser.y - 50, 0);
                    if (!this.isForward) {
                        new Explosion(CCanvas.width / 2 + 40, this.tUser.y - 80, (byte) 0);
                        new Explosion(CCanvas.width / 2 + 40, this.tUser.y - 50, (byte) 0);
                        new Explosion(CCanvas.width / 2 + 40, this.tUser.y - 20, (byte) 0);
                    }

                    for (j = 0; j < 6; ++j) {
                        GameScr.sm.addRock(CCanvas.width / 2 + 40, this.tUser.y - 50, CRes.random(j + 1), CRes.random(-8, -5), (byte) 3);
                    }

                    for (j = 0; j < 6; ++j) {
                        GameScr.sm.addRock(CCanvas.width / 2 + 40, this.tUser.y - 50, -CRes.random(j + 1), CRes.random(-8, -5), (byte) 2);
                    }

                    this.isStone = false;
                }
            }

            if (this.isMenu) {
                this.Effect();
            }

            Cloud.updateCloud();
            Cloud.balloonUpdate();
            this.tUser.update();
            this.tPass.update();
            this.tEmail.update();
            if (isWait) {
                currTime += 2;
                if (currTime > maxTime) {
                    currTime = maxTime;
                }
            }
        }

        GameScr.sm.update();

        for (j = 0; j < GameScr.exs.size(); ++j) {
            ((Explosion) GameScr.exs.elementAt(j)).update();
        }

        super.update();
    }

    public void keyPressed(int keyCode) {
        if (CCanvas.currentDialog == null) {
            if (this.focus == 0) {
                this.tUser.keyPressed(keyCode);
            } else {
                this.tPass.keyPressed(keyCode);
            }
        }

        super.keyPressed(keyCode);
    }

    public void paintBackG(mGraphics g) {
        Background.paintMenuBackGround(g);
    }

    public void paintDemo(mGraphics g) {
        g.setColor(6606845);
        g.fillRect(0, this.cmy, CCanvas.width, CCanvas.hieght, false);
        g.setColor(7612928);

        int i;
        for (i = 0; i < 4; ++i) {
            g.drawImage(Cloud.imgCloud[i % 2], this.cloudX[i], this.cloudY[i] - 1500, 0, false);
        }

        int[] var10000;
        int Y;
        for (i = 0; i < 7; ++i) {
            Y = -1200 + i * 120;
            if (Y >= this.cmy - 50 && Y <= this.cmy + CCanvas.hieght) {
                g.drawImage(Cloud.imgCloud[0], this.xCl1[i], Y, 0, false);
                var10000 = this.xCl1;
                var10000[i] += this.speed;
            }
        }

        for (i = 0; i < 7; ++i) {
            Y = -1100 + i * 200;
            if (Y >= this.cmy - 50 && Y <= this.cmy + CCanvas.hieght) {
                g.drawImage(Cloud.imgCloud[1], this.xCl2[i] + i * 4 - 90, Y, 0, false);
                if (CCanvas.gameTick % 2 == 0) {
                    var10000 = this.xCl2;
                    var10000[i] += this.speed;
                }
            }
        }

        for (i = 0; i < 4; ++i) {
            Y = -700 + i * 300;
            if (Y >= this.cmy - 50 && Y <= this.cmy + CCanvas.hieght) {
                g.drawImage(Background.balloon, this.xBl[i] - 50, Y, 0, false);
            }
        }

        g.drawLine(this.plX - 5, this.plY + 48, this.lineX, this.lineY, true);
        g.drawLine(this.plX + 5, this.plY + 48, this.lineX, this.lineY, true);
        g.drawImage(imgPlane, this.plX, this.plY, mGraphics.VCENTER | mGraphics.HCENTER, true);
        if (!this.activeFall) {
            g.drawImage(Background.mocxich, this.lX, this.lY - 39, mGraphics.TOP | mGraphics.HCENTER, true);
            g.drawRegion(missile, 0, 0, 15, 15, 2, this.xB[0], this.yB[0], 0, true);
        }

        if (!this.explore) {
            g.drawRegion(missile, 0, 0, 15, 15, 2, this.xB[1], this.yB[1], 0, true);
        }

    }

    public void remember() {
        if (remember == 0) {
            remember = 1;
        } else {
            remember = 0;
        }

        CRes.saveRMSInt("remember", remember);
    }

    public void paintMenuLogin(mGraphics g) {
        this.paintBackG(g);
        Cloud.paintBalloonWithCloud(g);
        Background.paintTree(g);
        int nB = CCanvas.width >= 200 ? 4 : 3;
        int dis = CCanvas.isTouch ? 120 : 100;
        paintBorderRect(g, this.yLog, nB, dis, "");
        g.drawImage(lgGame, this.lX, this.lY, 3, false);
        g.drawImage(GameScr.logoII, lgGame.image.getWidth() + this.lX, this.tUser.y - 50, 3, false);
        if (this.isStone) {
            g.drawImage(Background.b, CCanvas.width / 2 + 20, this.tUser.y - 95, 0, false);
            g.drawImage(Background.b, CCanvas.width / 2 + 25, this.tUser.y - 70, 0, false);
            g.drawImage(Background.b, CCanvas.width / 2 + 17, this.tUser.y - 63, 0, false);
        }

        int n = (CCanvas.hieght - this.yLog - h - cmdH) / 21;

        int deltaX;
        for (deltaX = 0; deltaX < n + 1; ++deltaX) {
            g.drawImage(Background.a, this.tUser.x + 65, this.yLog + h + deltaX * 21, 0, false);
        }

        GameScr.sm.paint(g);

        for (deltaX = 0; deltaX < GameScr.exs.size(); ++deltaX) {
            ((Explosion) GameScr.exs.elementAt(deltaX)).paint(g);
        }

        this.tUser.paint(g);
        Font.borderFont.drawString(g, Language.id() + ":", this.tUser.x - 59, this.tUser.y + 4, 0, false);
        g.setClip(0, 0, CCanvas.width, CCanvas.hieght);
        this.tPass.y = this.tUser.y + 28;
        Font.borderFont.drawString(g, Language.pass() + ":", this.tPass.x - 59, this.tPass.y, 0);
        deltaX = CCanvas.width >= 200 ? 0 : 30;
        int yDraw = this.tPass.y + 36 - 10;
        Font.borderFont.drawString(g, Language.remember(), this.xLog + 120 - deltaX - (Language.language == 0 ? 0 : 30), yDraw, 0, true);
        g.drawRegion(imgCheck, 0, remember * 16, 18, 16, 0, this.xLog + 150 - deltaX, yDraw, 0, false);
        Font.normalFont.drawString(g, Language.forgotPass() + "?", this.xLog + 10 - deltaX - (Language.language == 0 ? 0 : 30), this.tPass.y + 35 + 10, 0, true);
        Font.normalFont.drawString(g, Language.reg(), this.xLog + 120 - deltaX - (Language.language == 0 ? 0 : 30), this.tPass.y + 35 + 10, 0, true);
        this.tPass.paint(g);
    }

    public void paintMenuRegisterFreeAccount(mGraphics g) {
        this.paintBackG(g);
        Cloud.paintBalloonWithCloud(g);
        Background.paintTree(g);
        Font.smallFont.drawString(g, GameMidlet.version, CCanvas.width - 2, 2, 1);
        int nB = CCanvas.width >= 200 ? 4 : 3;
        int dis = CCanvas.isTouch ? 120 : 100;
        paintBorderRect(g, this.yLog, nB, dis, "");
        g.drawImage(lgGame, this.lX, this.lY, 3, false);
        g.drawImage(GameScr.logoII, CCanvas.width / 2 + 50, this.tUser.y - 50, 3, false);
        if (this.isStone) {
            g.drawImage(Background.b, CCanvas.width / 2 + 20, this.tUser.y - 95, 0, false);
            g.drawImage(Background.b, CCanvas.width / 2 + 25, this.tUser.y - 70, 0, false);
            g.drawImage(Background.b, CCanvas.width / 2 + 17, this.tUser.y - 63, 0, false);
        }

        int n = (CCanvas.hieght - this.yLog - h - cmdH) / 21;

        int i;
        for (i = 0; i < n + 1; ++i) {
            g.drawImage(Background.a, this.tUser.x + 65, this.yLog + h + i * 21, 0, false);
        }

        GameScr.sm.paint(g);

        for (i = 0; i < GameScr.exs.size(); ++i) {
            ((Explosion) GameScr.exs.elementAt(i)).paint(g);
        }

        this.tUser.paint(g);
        Font.borderFont.drawString(g, Language.id() + ":", this.tUser.x - 59, this.tUser.y + 4, 0, false);
        g.setClip(0, 0, CCanvas.width, CCanvas.hieght);
        this.tEmail.y = this.tUser.y + 28;
        Font.borderFont.drawString(g, Language.email_phone() + ":", this.tEmail.x - 59, this.tEmail.y, 0);
        this.tEmail.paint(g);
        g.setClip(0, 0, CCanvas.width, CCanvas.hieght);
        this.tPass.y = this.tUser.y + 56;
        Font.borderFont.drawString(g, Language.pass() + ":", this.tPass.x - 59, this.tPass.y, 0);
        this.tPass.paint(g);
    }

    private void updateCloud() {
        for (int i = 0; i < 4; ++i) {
            int[] var10000 = this.cloudX;
            var10000[i] += this.cloudVX[i] * (2 - i % 2) - this.speed;
            var10000 = this.cloudY;
            var10000[i] += this.cloudVY[i] * (2 - i % 2);
            if (this.demoStat == 0 && this.cloudX[i] + Cloud.imgCloud[i % 2].image.getWidth() < 0) {
                this.cloudX[i] = CCanvas.width + 50;
            }

            if (this.demoStat == 1 && this.cloudY[i] + Cloud.imgCloud[i % 2].image.getHeight() < 0) {
                this.cloudY[i] = CCanvas.hieght + 30 * i;
                this.cloudVY[i] = -1;
                this.cloudVX[i] = 0;
                this.cloudX[i] = CCanvas.gameTick % 100 + i * 30;
            }
        }

    }

    public void paint(mGraphics g) {
        int nTab = 4;
        int W = nTab * 32 + 23 + 33;
        if (W >= CCanvas.width) {
            nTab--;
            W = nTab * 32 + 23 + 33;
        }

        this.xLog = CCanvas.width / 2 - W / 2;
        this.tUser.x = this.xLog + 70;
        this.tUser.y = this.yLog + 25;
        this.tPass.x = this.xLog + 70;
        this.tPass.y = this.yLog + 50;
        this.tEmail.x = this.xLog + 70;
        this.tPass.y = this.yLog + 75;
        if (CCanvas.isTouch) {
            this.tPass.y = this.yLog + 60;
        }

        if (this.isAskSound) {
            this.paintBackG(g);
            this.paintAskSound(g);
        } else {
            g.translate(0, -this.cmy);
            if (this.isDemo) {
                this.paintDemo(g);
            }

            if (this.isRegister) {
                this.paintMenuRegisterFreeAccount(g);
            } else {
                this.paintMenuLogin(g);
            }

            if (isWait && !GameLogicHandler.isServerThongBao) {
                CCanvas.msgdlg.setInfo(Language.download() + " " + currTime + "%", (Command) null, (Command) null, (Command) null);
            }

            GameMidlet.serverInformation(Font.normalFont, g);
        }

        super.paint(g);
    }

    private void paintAskSound(mGraphics g) {
        this.paintCommand(g);
        int y = CCanvas.hh - 38;
        Font.borderFont.drawString(g, Language.graphicQuality(), CCanvas.hw, y, 2);
        Font.borderFont.drawString(g, ConfigScr.graphicText[GameScr.curGRAPHIC_LEVEL], CCanvas.hw, y + 18, 2);
        g.drawRegion(PrepareScr.imgReady[3], 0, 0, 13, 11, 4, CCanvas.hw - 30 + CCanvas.gameTick % 3, y + 27, 3, false);
        g.drawRegion(PrepareScr.imgReady[3], 0, 0, 13, 11, 7, CCanvas.hw + 30 - CCanvas.gameTick % 3, y + 27, 3, false);
        Font.normalFont.drawString(g, Language.soundOn(), 3, CCanvas.hieght - 17, 0);
        Font.normalFont.drawString(g, Language.soundOff(), CCanvas.width - 8, CCanvas.hieght - 17, 1);
    }

    public void focusUpdate() {
        if (this.isRegister) {
            if (this.focus == 0) {
                this.tUser.setisFocus(true);
                this.tPass.setisFocus(false);
                this.tEmail.setisFocus(false);
                this.right = this.tUser.cmdClear;
                this.center = this.cmdRegister;
                this.left = this.cmdBack;
            }

            if (this.focus == 1) {
                this.tUser.setisFocus(false);
                this.tPass.setisFocus(true);
                this.tEmail.setisFocus(false);
                this.right = this.tPass.cmdClear;
                this.center = this.cmdRegister;
                this.left = this.cmdBack;
            }

            if (this.focus == 3) {
                this.tUser.setisFocus(false);
                this.tPass.setisFocus(false);
                this.tEmail.setisFocus(true);
                this.right = this.tEmail.cmdClear;
                this.center = this.cmdRegister;
                this.left = this.cmdBack;
            }
        } else {
            if (this.focus == 0) {
                this.tUser.setisFocus(true);
                this.tPass.setisFocus(false);
                this.right = this.tUser.cmdClear;
                this.center = this.cmdSignIn;
            }

            if (this.focus == 1) {
                this.tUser.setisFocus(false);
                this.tPass.setisFocus(true);
                this.right = this.tPass.cmdClear;
                this.center = this.cmdSignIn;
            }

            if (this.focus == 2) {
                this.tUser.setisFocus(false);
                this.tPass.setisFocus(false);
                this.right = null;
                this.center = this.cmdRemember;
            }
        }

    }

    public void onPointerPressed(int x, int y2, int index) {
        try {
            super.onPointerPressed(x, y2, index);
            if (this.isMenu) {
                if (CCanvas.keyPressed[2]) {
                    --this.focus;
                    if (this.focus < 0) {
                        this.focus = 2;
                    }

                    this.focusUpdate();
                    CScreen.clearKey();
                }

                if (CCanvas.keyPressed[8]) {
                    ++this.focus;
                    if (this.focus > 2) {
                        this.focus = 0;
                    }

                    CScreen.clearKey();
                    this.focusUpdate();
                }

                if (this.isRegister) {
                    if (CCanvas.isPointer(this.tUser.x, this.tUser.y, this.tUser.width, this.tUser.height, index)) {
                        this.tUser.textPreferent = this.tUser.getText();
                        if (this.focus != 0) {
                            this.focus = 0;
                        } else {
                            this.tUser.doChangeToTextBox();
                        }
                        this.focusUpdate();
                    }

                    if (CCanvas.isPointer(this.tPass.x, this.tPass.y, this.tPass.width, this.tPass.height, index)) {
                        this.tPass.textPreferent = this.tPass.getText();
                        if (this.focus != 1) {
                            this.focus = 1;
                        } else {
                            this.tPass.doChangeToTextBox();
                        }

                        this.focusUpdate();
                    }

                    if (CCanvas.isPointer(this.tEmail.x, this.tEmail.y, this.tEmail.width, this.tEmail.height, index)) {
                        this.tEmail.textPreferent = this.tEmail.getText();
                        if (this.focus != 3) {
                            this.focus = 3;
                        } else {
                            this.tEmail.doChangeToTextBox();
                        }

                        this.focusUpdate();
                    }
                } else {
                    if (CCanvas.isPointer(this.xLog + 140 - this.deltaX, this.tPass.y + 28, 30, 30, index)) {
                        this.remember();
                    }

                    if (CCanvas.isPointer(this.tUser.x, this.tUser.y, this.tUser.width, this.tUser.height, index)) {
                        this.tUser.title = Language.signIn();
                        this.tUser.textPreferent = this.tUser.getText();
                        if (this.focus != 0) {
                            this.focus = 0;
                        } else {
                            this.tUser.doChangeToTextBox();
                        }

                        this.focusUpdate();
                    }

                    if (CCanvas.isPointer(this.tPass.x, this.tPass.y, this.tPass.width, this.tPass.height, index)) {
                        this.tPass.title = Language.signIn();
                        this.tPass.textPreferent = this.tPass.getText();
                        if (this.focus != 1) {
                            this.focus = 1;
                        } else {
                            this.tPass.doChangeToTextBox();
                        }

                        this.focusUpdate();
                    }
                }
            }

            if (this.isAskSound) {
                boolean var10000;
                if (!CCanvas.keyPressed[4]) {
                    var10000 = keyLeft;
                }

                if (!CCanvas.keyPressed[6]) {
                    var10000 = keyRight;
                }

                var10000 = CCanvas.isPointerClick[index];
            } else if (this.isDemo) {
                if (CCanvas.keyPressed[13] || CCanvas.isPointer(CCanvas.hw, CCanvas.hh - 25, 50, 50, index)) {
                    this.isForward = true;
                    clearKey();
                }
            } else if (this.isForward) {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void onPointerReleased(int x, int y2, int index) {
        super.onPointerReleased(x, y2, index);
        if (this.isAskSound) {
            if (CCanvas.isPointer(CCanvas.hw - 50, CCanvas.hh - 25, 50, 50, index)) {
                ++GameScr.curGRAPHIC_LEVEL;
                if (GameScr.curGRAPHIC_LEVEL > 2) {
                    GameScr.curGRAPHIC_LEVEL = 0;
                }

                RMS.saveRMSInt("Graphic", GameScr.curGRAPHIC_LEVEL);
                clearKey();
            }

            if (CCanvas.isPointer(CCanvas.hw, CCanvas.hh - 25, 50, 50, index)) {
                --GameScr.curGRAPHIC_LEVEL;
                if (GameScr.curGRAPHIC_LEVEL < 0) {
                    GameScr.curGRAPHIC_LEVEL = 2;
                }

                RMS.saveRMSInt("Graphic", GameScr.curGRAPHIC_LEVEL);
                clearKey();
            }
        }

        if (!this.isDemo) {
            if (!this.isRegister && CCanvas.isPointer(this.xLog + 10 - this.deltaX - (Language.language == 0 ? 0 : 30), this.tPass.y + 35 + 10, 100, 25, index)) {
                if (CCanvas.curScr == CCanvas.loginScr) {
                    CCanvas.inputDlg.setInfo(Language.id(), new IAction() {
                        public void perform() {
                            String name = CCanvas.inputDlg.tfInput.getText();
                            if (!name.equals("")) {
                                LoginScr.this.doForgetPass(name);
                            }
                        }
                    }, new IAction() {
                        public void perform() {
                            CCanvas.endDlg();
                        }
                    }, 3);
                    CCanvas.inputDlg.show();
                } else {
                    this.doForgetPass(TerrainMidlet.myInfo.name);
                }
            }

            if (CCanvas.isPointer(this.xLog + 120 - this.deltaX - (Language.language == 0 ? 0 : 30), this.tPass.y + 35 + 10, 100, 25, index)) {
                if (GameMidlet.server == 2) {
                    this.isRegister = true;
                    this.left = this.cmdBack;
                    this.focus = 0;
                    this.tUser.setisFocus(true);
                    this.tPass.setisFocus(false);
                    this.tEmail.setisFocus(false);
                    this.center = this.cmdRegister;
                    this.right = null;
                    this.setRegister();
                } else {
                    this.doRegister();
                }
            }

        }
    }

    public void resetTF() {
        this.tUser.x = -this.xC;
        this.tPass.x = CCanvas.width + this.xC;
        this.tEmail.x = -this.xC;
    }

    public void initSignIn() {
        this.logoDes = CCanvas.width >= 200 ? this.yLog - 40 : this.tUser.y - 40;
        this.focus = 0;
        this.cmdSignIn = new Command(Language.signIn(), new IAction() {
            public void perform() {
                LoginScr.this.doLogin();
            }
        });
        this.cmdMenu = new Command("Menu", new IAction() {
            public void perform() {
                Vector<Command> menu = new Vector();
                menu.addElement(new Command(Language.callhotline(), new IAction() {
                    public void perform() {
                        if (LoginScr.this.numSupport.equals("")) {
                            if (!Session_ME.gI().isConnected()) {
                                CCanvas.startWaitDlgWithoutCancel(Language.connecting(), 10);
                                LoginScr.this.connect();
                            } else {
                                CCanvas.startWaitDlg(Language.pleaseWait());
                            }

                            GameService.gI().requestService((byte) 5, (String) null);
                        }

                    }
                }));
                menu.addElement(new Command(Language.xoadulieu(), new IAction() {
                    public void perform() {
                        CRes.delRMS();
                    }
                }));
                menu.addElement(new Command(Language.chonmaychu(), new IAction() {
                    public void perform() {
                        GameService.gI().disconnect();
                        CCanvas.serverListScreen.show();
                    }
                }));
                if (CCanvas.isDebugging()) {
                    menu.addElement(new Command(Language.backVersion(), new IAction() {
                        public void perform() {
                            GameMidlet.versionByte = 239;
                            GameMidlet.version = "2.3.9";
                            GameService.gI().disconnect();
                            GameMidlet.doUpdateServer();
                            CCanvas.serverListScreen.show();
                        }
                    }));
                }

                menu.addElement(new Command(Language.exit(), new IAction() {
                    public void perform() {
                        Gdx.app.exit();
                        System.exit(-1);
                        GameService.gI().disconnect();
                        Session_ME.gI().close(1111);
                    }
                }));
                CCanvas.menu.startAt(menu, 0);
            }
        });
        this.cmdForward = new Command(Language.forward(), new IAction() {
            public void perform() {
                LoginScr.this.isForward = true;
            }
        });
        this.cmdSelect = new Command(Language.select(), new IAction() {
            public void perform() {
                LoginScr.this.isAskSound = false;
                LoginScr.this.isDemo = true;
                LoginScr.this.center = null;
                LoginScr.this.right = LoginScr.this.cmdForward;
                RMS.saveRMSInt("Graphic", GameScr.curGRAPHIC_LEVEL);
            }
        });
        this.cmdRemember = new Command(Language.remember(), new IAction() {
            public void perform() {
                LoginScr.this.remember();
            }
        });
        this.cmdYes = new Command(Language.soundOn(), new IAction() {
            public void perform() {
                LoginScr.this.isAskSound = false;
                LoginScr.this.isDemo = true;
                mSound.setVolume(LoginScr.volume);
                CRes.saveRMSInt("sound", LoginScr.volume);
                LoginScr.this.right = LoginScr.this.cmdForward;
                LoginScr.this.left = null;
                LoginScr.clearKey();
            }
        });
        this.cmdNo = new Command(Language.soundOff(), new IAction() {
            public void perform() {
                LoginScr.this.isAskSound = false;
                LoginScr.this.isDemo = true;
                LoginScr.volume = 0;
                mSound.setVolume(0);
                CRes.saveRMSInt("sound", LoginScr.volume);
                LoginScr.this.right = LoginScr.this.cmdForward;
                LoginScr.this.left = null;
                LoginScr.clearKey();
            }
        });
        this.cmdBack = new Command(Language.back(), new IAction() {
            public void perform() {
                LoginScr.this.isRegister = false;
                LoginScr.this.isDemo = false;
                LoginScr.this.isMenu = true;
                LoginScr.this.isForward = false;
                LoginScr.this.logoII = true;
                LoginScr.this.cmy = 0;
                LoginScr.this.right = LoginScr.this.tUser.cmdClear;
                LoginScr.this.left = LoginScr.this.cmdMenu;
                LoginScr.this.center = LoginScr.this.cmdSignIn;
                LoginScr.clearKey();
            }
        });
        this.cmdRegister = new Command(Language.reg(), new IAction() {
            public void perform() {
                LoginScr.charName = LoginScr.this.tUser.getText().toLowerCase();
                LoginScr.password = LoginScr.this.tPass.getText().toLowerCase();
                LoginScr.email_phone = LoginScr.this.tEmail.getText().toLowerCase();
                if (!LoginScr.charName.equals("") && !LoginScr.email_phone.equals("") && !LoginScr.password.equals("")) {
                    GameService.gI().requestRegister(LoginScr.charName, LoginScr.email_phone, LoginScr.password);
                    CCanvas.startWaitDlg(Language.pleaseWait());
                } else {
                    CCanvas.msgdlg.setInfo(Language.dangkyFail(), (Command) null, new Command("OK", new IAction() {
                        public void perform() {
                            CCanvas.endDlg();
                            LoginScr.this.focus = 0;
                            LoginScr.this.tUser.setisFocus(true);
                            LoginScr.this.tPass.setisFocus(false);
                            LoginScr.this.tEmail.setisFocus(false);
                        }
                    }), (Command) null);
                    CCanvas.msgdlg.show();
                }
            }
        });
        if (GameScr.curGRAPHIC_LEVEL != 2) {
            GameScr.mm.createBackGround();
        }

        if (this.isAskSound && RMS.loadRMSInt("Graphic") != -1) {
            this.isDemo = true;
            this.isAskSound = false;
            this.right = this.cmdForward;
        }

        if (RMS.loadRMSInt("Graphic") == -1) {
            this.isAskSound = true;
            this.center = this.cmdSelect;
        }

    }
}
