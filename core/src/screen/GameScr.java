package screen;

import CLib.Image;
import CLib.mGraphics;
import CLib.mImage;
import CLib.mSystem;
import Equipment.Equip;
import com.teamobi.mobiarmy2.GameMidlet;
import com.teamobi.mobiarmy2.MainGame;
import coreLG.CCanvas;
import coreLG.CONFIG;
import coreLG.TerrainMidlet;
import effect.Camera;
import effect.Effect;
import effect.Explosion;
import effect.GiftEffect;
import effect.SmokeManager;
import effect.Snow;
import item.BM;
import item.Bullet;
import item.Item;

import java.util.Vector;

import map.Background;
import map.MM;
import model.CRes;
import model.CTime;
import model.ChatPopup;
import model.FilePack;
import model.Font;
import model.FrameImage;
import model.IAction;
import model.IAction2;
import model.Language;
import model.TField;
import model.TimeBomb;
import network.Command;
import network.GameService;
import network.Session_ME;
import player.Boss;
import player.CPlayer;
import player.PM;

public class GameScr extends CScreen {
    static int WIDTH = 1000;
    public static int HEIGHT = 1000;
    public static final byte GRAPHIC_HIGH = 0;
    public static final byte GRAPHIC_MEDIUM = 1;
    public static final byte GRAPHIC_LOW = 2;
    public static byte curGRAPHIC_LEVEL = 0;
    public static boolean whiteEffect;
    public static boolean electricEffect;
    public static boolean freezeEffect;
    public static boolean suicideEffect;
    public static boolean poisonEffect;
    public boolean nukeEffect;
    public int tN;
    public int tW = 0;
    public int wE;
    public int xE;
    public int tE = 0;
    public static int xNuke;
    public static int yNuke;
    public static int yElectric;
    public static int xElectric;
    public static int xFreeze;
    public static int yFreeze;
    public static int xSuicide;
    public static int ySuicide;
    public static int xPoison;
    public static int yPoison;
    public static mImage airFighter;
    public static mImage imgMode;
    public static mImage lock;
    public static mImage lockImg;
    public static mImage crosshair;
    public static mImage imgInfoPopup;
    public static mImage s_imgITEM;
    public static mImage imgTeam;
    public static mImage imgPlane;
    public static mImage logoGame;
    public static mImage logoII;
    public static mImage imgQuanHam;
    public static mImage imgBack;
    public static mImage imgMap;
    public static mImage trangbiTileImg;
    public static mImage shopTileImg;
    public static mImage tienBarImg;
    public static mImage soLuongBarImg;
    public static mImage buyBar;
    public static mImage ladySexyImg;
    public static mImage imgCurPos;
    public static mImage imgSmallCloud;
    public static mImage imgArrowRed;
    public static mImage imgRoomStat;
    public static mImage imgTrs;
    public static mImage imgIcon;
    public static mImage itemBarImg;
    public static mImage imgChat;
    public static mImage s_imgTransparent;
    public static mImage arrowMenu;
    public static mImage wind1;
    public static mImage wind2;
    public static mImage wind3;
    public static mImage trai;
    public static mImage phai;
    public static mImage crossHair2;
    public static mImage nut_up;
    public static mImage nut_down;
    public static mImage[] imgReady = new mImage[9];
    public static mImage[] imgMsg = new mImage[2];
    public static Vector<TimeBomb> timeBombs = new Vector();
    public static TField tfChat;
    public static MM mm;
    public static PM pm;
    public static Camera cam;
    public static BM bm;
    public static Vector<Explosion> exs;
    public static SmokeManager sm;
    public static CTime time;
    public Vector vGift = new Vector();
    public static int windx;
    public static int windy;
    int teamSize;
    int mapID;
    public static boolean trainingMode;
    mImage pause;
    public static int tickCount;
    public static byte ID_Turn;
    public static mImage s_imgAngle;
    public static FrameImage s_frBar;
    public static FrameImage s_frWind;
    public static boolean isDarkEffect;
    public static int s_iPlane_x;
    public static int s_iPlane_y;
    public static int s_iBombTargetX;
    public static byte room;
    public static byte board;
    byte exBonus;
    int moneyBonus;
    int moneyY = -100;
    public static String res;
    int moneyBonus2;
    int moneyY2;
    boolean isMoney2Fly;
    int whoGetMoney2;
    boolean isMoneyFly;
    int nBoLuot;
    private boolean isSelectItem;
    public static int curItemSelec;
    private long timeDelayClosePauseMenu;
    public static byte myIndex;
    Vector chatList = new Vector();
    int chatDelay;
    int MAX_CHAT_DELAY = 40;
    Snow snow;
    public static boolean iconOnOf;
    public boolean isShowPausemenu;
    public long timeShowPauseMenu;
    int chatWait = 0;
    boolean isChat;
    public boolean isFly;
    public String text = "";
    public int xFly;
    public int yFly;
    public int tFly;
    public Equip equip;
    public static int trainingStep;
    public static boolean isUpdateHP;
    int left = 0;
    int right = 1;
    int up = 2;
    int down = 3;
    public static boolean cantSee;
    public byte whoCantSee;
    public static int xL;
    public static int yL;
    public static int xR;
    public static int yR;
    public static int xF;
    public static int yF;
    public static int xU;
    public static int yU;
    public static int xD;
    public static int yD;
    static mImage imgArrow;
    public static int windAngle;
    public static int windPower;
    public int t1;
    public int t2;
    public int dem;
    boolean b;
    public static byte[] num;
    boolean isPressXL;
    boolean isPressXR;
    boolean isPressXF;

    static {
        FilePack filePak = null;

        try {
            filePak = new FilePack(CCanvas.getClassPathConfig(CONFIG.PATH_GUI + "gui"));
            airFighter = filePak.loadImage("fighter.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "fighter");
                }
            });
            imgMode = filePak.loadImage("mode.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "mode");
                }
            });
            lock = filePak.loadImage("lock2.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "lock2");
                }
            });
            lockImg = filePak.loadImage("lock.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "lock");
                }
            });
            crosshair = filePak.loadImage("hongTam.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "hongTam");
                }
            });
            imgInfoPopup = filePak.loadImage("popupRound.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "popupRound");
                }
            });
            s_imgITEM = filePak.loadImage("item.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "item");
                }
            });
            imgPlane = filePak.loadImage("fighter.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "fighter");
                }
            });
            logoGame = mImage.createImage("/gui/logoGame.png");
            imgReady[0] = filePak.loadImage("on.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "on");
                }
            });
            imgReady[1] = filePak.loadImage("off.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "off");
                }
            });
            imgReady[2] = filePak.loadImage("r2.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "r2");
                }
            });
            imgReady[3] = filePak.loadImage("arrowup.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "arrowup");
                }
            });
            imgReady[4] = filePak.loadImage("tile1.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "tile1");
                }
            });
            imgQuanHam = filePak.loadImage("quanham.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "quanham");
                }
            });
            imgBack = filePak.loadImage("menubg.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "menubg");
                }
            });
            imgCurPos = filePak.loadImage("curMapPos.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "curMapPos");
                }
            });
            imgSmallCloud = PrepareScr.cloud1;
            imgArrowRed = filePak.loadImage("arrowRed.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "arrowRed");
                }
            });
            imgRoomStat = filePak.loadImage("stat.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "stat");
                }
            });
            imgTrs = filePak.loadImage("trs.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "trs");
                }
            });
            imgIcon = filePak.loadImage("icon.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "icon");
                }
            });
            s_imgAngle = filePak.loadImage("angle.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "angle");
                }
            });
            imgChat = filePak.loadImage("chat.png", new IAction2() {
                public void perform(Object object) {
                }
            });
            s_imgTransparent = filePak.loadImage("transparent.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "transparent");
                }
            });
            imgMsg[0] = filePak.loadImage("msg0.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "msg0");
                }
            });
            imgMsg[1] = filePak.loadImage("msg1.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "msg1");
                }
            });
            logoII = filePak.loadImage("logo_2.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "logo_2");
                }
            });
            arrowMenu = filePak.loadImage("arrowMenu.png", new IAction2() {
                public void perform(Object object) {
                    CRes.onSaveToFile((Image) object, "arrowMenu");
                }
            });
            mImage.createImage("/gui/nut2.png", new IAction2() {
                public void perform(Object object) {
                    GameScr.trai = new mImage((Image) object);
                    CRes.onSaveToFile((Image) object, "nut2");
                }
            });
            mImage.createImage("/gui/nut1.png", new IAction2() {
                public void perform(Object object) {
                    GameScr.phai = new mImage((Image) object);
                    CRes.onSaveToFile((Image) object, "nut1");
                }
            });
            mImage.createImage("/gui/nut3.png", new IAction2() {
                public void perform(Object object) {
                    GameScr.crossHair2 = new mImage((Image) object);
                    CRes.onSaveToFile((Image) object, "nut3");
                }
            });
            mImage.createImage("/gui/nut_up.png", new IAction2() {
                public void perform(Object object) {
                    GameScr.nut_up = new mImage((Image) object);
                    CRes.onSaveToFile((Image) object, "nut_up");
                }
            });
            mImage.createImage("/gui/nut_down.png", new IAction2() {
                public void perform(Object object) {
                    GameScr.nut_down = new mImage((Image) object);
                    CRes.onSaveToFile((Image) object, "nut_down");
                }
            });
            mImage.createImage("/wind.png", new IAction2() {
                public void perform(Object object) {
                    GameScr.wind1 = new mImage((Image) object);
                    CRes.onSaveToFile((Image) object, "wind");
                }
            });
            mImage.createImage("/wind2.png", new IAction2() {
                public void perform(Object object) {
                    GameScr.wind2 = new mImage((Image) object);
                }
            });
            mImage.createImage("/gui/wind3.png", new IAction2() {
                public void perform(Object object) {
                    GameScr.wind3 = new mImage((Image) object);
                }
            });
            mImage.createImage("/gui/barMove.png", new IAction2() {
                public void perform(Object object) {
                    GameScr.s_frBar = new FrameImage((Image) object, 53, 12, false);
                }
            });
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        filePak = null;
        tickCount = 0;
        ID_Turn = 0;
        isDarkEffect = false;
        s_iPlane_x = -1;
        s_iPlane_y = -1;
        s_iBombTargetX = -1;
        res = "";

        try {
            imgArrow = mImage.createImage("/arrow.png");
        } catch (Exception var2) {
        }

        num = new byte[8];
    }

    public GameScr() {
        this.initGamescr();
    }

    public void initGamescr() {
        mm = new MM();
        pm = new PM();
        cam = new Camera();
        bm = new BM();
        sm = new SmokeManager();
        time = new CTime();
        tfChat = new TField();
        tfChat.x = 2;
        tfChat.y = CCanvas.hieght - ITEM_HEIGHT - 25;
        if (CCanvas.isTouch) {
            tfChat.y = CCanvas.hieght - CScreen.cmdH - ITEM_HEIGHT;
        }

        tfChat.width = CCanvas.width - 4;
        tfChat.height = ITEM_HEIGHT + 2;
        tfChat.setisFocus(true);
        tfChat.nameDebug = "Tfield ====> Gamescr";
        this.nameCScreen = "GameScr screen!";
    }

    public void initGame(byte MapID, byte timeInterval, short[] playerX, short[] playerY, short[] maxHP, int team) {
        isDarkEffect = false;
        s_iPlane_x = -1;
        s_iPlane_y = -1;
        s_iBombTargetX = -1;
        this.isMoneyFly = false;
        res = "";
        this.nBoLuot = 3;
        iconOnOf = true;
        if (curGRAPHIC_LEVEL != 2) {
            mm.createBackGround();
        }

        switch (curGRAPHIC_LEVEL) {
            case 0:
            case 1:
                if (!Background.isLoadImage) {
                    Background.isLoadImage = true;
                    Background.initImage();
                }
                break;
            case 2:
                Background.removeImage();
        }

        this.initGamescr();
        pm.init();
        CPlayer.isStopFire = false;
        bm.onInitSpecialBullet();
        exs = new Vector();
        timeBombs = new Vector();
        if (team != 0) {
            CCanvas.gameScr.flyText("+" + team + Language.diemdongdoi(), CCanvas.width / 2, CCanvas.hieght - 50, (Equip) null);
        }

        time.initTimeInterval(timeInterval);
        this.snow = null;
        if (curGRAPHIC_LEVEL != 2) {
            if (Background.curBGType == 2) {
                this.snow = new Snow();
                this.snow.startSnow(0);
            }

            if (Background.curBGType == 10) {
                this.snow = new Snow();
                if (MM.mapID == 34) {
                    this.snow.waterY = 35;
                }

                if (MM.mapID == 35) {
                    this.snow.waterY = 30;
                }

                if (MM.mapID == 38) {
                    this.snow.waterY = 80;
                }

                if (MM.mapID == 39) {
                    this.snow.waterY = 0;
                }

                this.snow.startSnow(1);
            }
        }

        pm.initPlayer(playerX, playerY, maxHP);

        for (int i = 0; i < PM.p.length; ++i) {
            if (PM.p[i] != null) {
                PM.p[i].cantSee = false;
            }
        }

        cantSee = false;
        Bullet.webId = 200;
    }

    private void onDragCamera(int xDrag, int yDrag, int index) {
        if (Camera.mode == 0) {
            int deltaX = xDrag - CCanvas.pxFirst[index];
            int deltaY = yDrag - CCanvas.pyFirst[index];
            if (deltaX > 1) {
                Camera.dx2 -= Math.abs(deltaX) >> 2;
            } else if (deltaX < -1) {
                Camera.dx2 += Math.abs(deltaX) >> 2;
            }

            if (deltaY > 1) {
                Camera.dy2 -= Math.abs(deltaY) >> 2;
            } else if (deltaY < -1) {
                Camera.dy2 += Math.abs(deltaY) >> 2;
            }
        }

    }

    public void selectedItemPanelRealeased(int x, int y2, int index) {
        int num = PM.getMyPlayer().item.length;
        int itemW = 0;
        int itemH = 0;
        int xItem = CCanvas.hw - 80;
        int yItem = CCanvas.hh - 33;
        if (CCanvas.isPointer(xItem, yItem, 170, 90, index)) {
            int aa = (y2 - yItem) / 40 * 4 + (x - xItem) / 40;
            if (aa >= 0 && aa <= 7) {
                if (aa != curItemSelec) {
                    curItemSelec = aa;
                } else if (CCanvas.isDoubleClick) {
                    int[] itemList = PM.getMyPlayer().item;
                    if (trainingMode) {
                        clearKey();
                        PM.getMyPlayer().UseItem(itemList[curItemSelec], true, curItemSelec);
                        if (itemList[curItemSelec] == 0) {
                            CPlayer var10000 = PM.p[0];
                            var10000.hp += 30;
                        }

                        this.isSelectItem = false;
                        this.timeDelayClosePauseMenu = mSystem.currentTimeMillis() + 300L;
                    } else {
                        CRes.out("itemList: " + itemList.length);

                        for (int i = 0; i < itemList.length; ++i) {
                            CRes.out("itemList: " + i + "_value_" + itemList[i]);
                        }

                        CRes.out("index/idItem/ItemUsed: " + curItemSelec + "/" + itemList[curItemSelec] + "/" + PM.getMyPlayer().itemUsed);
                        if (PM.getMyPlayer().itemUsed != -1 || itemList[curItemSelec] == -2 || itemList[curItemSelec] == -1) {
                            this.isSelectItem = false;
                            return;
                        }

                        if (pm.isYourTurn()) {
                            if (PrepareScr.currLevel == 7) {
                                if (GameScr.num[curItemSelec] != 0) {
                                    PM.getMyPlayer().UseItem(itemList[curItemSelec], false, curItemSelec);
                                    this.isSelectItem = false;
                                    this.timeDelayClosePauseMenu = mSystem.currentTimeMillis() + 300L;
                                }
                            } else {
                                PM.getMyPlayer().UseItem(itemList[curItemSelec], false, curItemSelec);
                                this.isSelectItem = false;
                                this.timeDelayClosePauseMenu = mSystem.currentTimeMillis() + 300L;
                            }
                        }

                        clearKey();
                    }
                }
            }
        }

        if (!CCanvas.isPointer(xItem - 50, yItem - 50, 210, 130, index) && this.isSelectItem) {
            this.isSelectItem = false;
            this.timeDelayClosePauseMenu = mSystem.currentTimeMillis() + 550L;
        }

    }

    public void flyText(String text, int xFly, int yFly, Equip equip) {
        this.isFly = true;
        this.text = text;
        this.xFly = xFly;
        this.yFly = yFly;
        this.equip = equip;
    }
    
    protected void doSetForce() {
        CCanvas.inputDlg.setInfo("Lực max 1-30", new IAction() {
            public void perform() {
                try {
                    PM.getMyPlayer().maxforce = Integer.parseInt(CCanvas.inputDlg.tfInput.getText());
                    if (PM.getMyPlayer().maxforce < 1) {
                        PM.getMyPlayer().maxforce = 1;
                    }
                    if (PM.getMyPlayer().maxforce > 30) {
                        PM.getMyPlayer().maxforce = 30;
                    }
                    CCanvas.endDlg();
                    if (PM.getMyPlayer().gun == 6 || PM.getMyPlayer().gun == 8) {
                        CCanvas.inputDlg.setInfo("Lực max 2 1-30", new IAction() {
                            public void perform() {
                                try {
                                    PM.getMyPlayer().maxforce2 = Integer.parseInt(CCanvas.inputDlg.tfInput.getText());
                                    if (PM.getMyPlayer().maxforce2 < 1) {
                                        PM.getMyPlayer().maxforce2 = 1;
                                    }
                                    if (PM.getMyPlayer().maxforce2 > 30) {
                                        PM.getMyPlayer().maxforce2 = 30;
                                    }
                                } catch (Exception var3) {
                                    PM.getMyPlayer().maxforce2 = 30;
                                }
                                CCanvas.endDlg();
                            }
                        }, new IAction() {
                            public void perform() {
                                CCanvas.endDlg();
                            }
                        }, 1);
                        CCanvas.inputDlg.tfInput.setText(""+ PM.getMyPlayer().maxforce2);
                        CCanvas.inputDlg.show();
                    }
                } catch (Exception var3) {
                    PM.getMyPlayer().maxforce = 30;
                }

            }
        }, new IAction() {
            public void perform() {
                CCanvas.endDlg();
            }
        }, 1);
        CCanvas.inputDlg.tfInput.setText(""+ PM.getMyPlayer().maxforce);
        CCanvas.inputDlg.show();
    }

    public void doShowPauseMenu() {
        this.isShowPausemenu = true;
        Vector<Command> menu = new Vector();
        menu.addElement(new Command(Language.CONTINUE(), new IAction() {
            public void perform() {
                GameScr.this.isShowPausemenu = false;
                GameScr.this.timeShowPauseMenu = mSystem.currentTimeMillis() + 300L;
            }
        }));
        
        menu.addElement(new Command("LỰC MAX", new IAction() {
            public void perform() {
                doSetForce();
            }
        }));
        
        if (pm.isYourTurn()) {
            menu.addElement(new Command(Language.USEITEM(), new IAction() {
                public void perform() {
                    if (GameScr.pm.isYourTurn()) {
                        GameScr.this.isSelectItem = true;
                    }

                    GameScr.this.isShowPausemenu = false;
                    GameScr.curItemSelec = 7;
                    GameScr.this.timeShowPauseMenu = mSystem.currentTimeMillis() + 300L;
                }
            }));
        }

        if (pm.isYourTurn() && PM.getCurPlayer().isAngry && !PM.getCurPlayer().isUsedItem) {
            menu.addElement(new Command(Language.SPECIAL(), new IAction() {
                public void perform() {
                    GameService.gI().useItem((byte) 100);
                    PM.getCurPlayer().isUsedItem = true;
                    PM.getCurPlayer().itemUsed = 100;
                    PM.getCurPlayer().angryX = 0;
                    PM.getCurPlayer().currAngry = 0;
                    PM.getCurPlayer().is2TurnItem = true;
                    GameScr.this.isShowPausemenu = false;
                    GameScr.this.timeShowPauseMenu = mSystem.currentTimeMillis() + 300L;
                }
            }));
        }

        menu.addElement(new Command(Language.battaticon(), new IAction() {
            public void perform() {
                GameScr.iconOnOf = !GameScr.iconOnOf;
                GameScr.this.isShowPausemenu = false;
                GameScr.this.timeShowPauseMenu = mSystem.currentTimeMillis() + 300L;
            }
        }));
        if (pm.isYourTurn() && !trainingMode && !BM.active && PM.getMyPlayer().active && this.nBoLuot > 0) {
            menu.addElement(new Command(Language.SKIP(), new IAction() {
                public void perform() {
                    GameScr.time.skipTurn();
                    --GameScr.this.nBoLuot;
                    GameScr.this.isShowPausemenu = false;
                    GameScr.this.timeShowPauseMenu = mSystem.currentTimeMillis() + 300L;
                }
            }));
        }

        menu.addElement(new Command(Language.LEAVEBATTLE(), new IAction() {
            public void perform() {
                if (GameScr.trainingMode) {
                    GameService.gI().training((byte) 1);
                    GameScr.trainingMode = false;
                    GameScr.this.isShowPausemenu = false;
                    GameScr.this.timeShowPauseMenu = mSystem.currentTimeMillis() + 300L;
                    CScreen.isSetClip = true;
                } else if (PM.p[GameScr.myIndex].getState() == 5) {
                    CCanvas.startYesNoDlg(Language.youWillLose(), new IAction() {
                        public void perform() {
                            GameScr.this.exitGiuaChung();
                            CCanvas.endDlg();
                            GameScr.this.isShowPausemenu = false;
                            GameScr.this.timeShowPauseMenu = mSystem.currentTimeMillis() + 300L;
                        }
                    });
                } else {
                    CCanvas.startYesNoDlg(Language.wantExit(), new IAction() {
                        public void perform() {
                            GameScr.this.exitGiuaChung();
                            CCanvas.endDlg();
                            GameScr.this.isShowPausemenu = false;
                            GameScr.this.timeShowPauseMenu = mSystem.currentTimeMillis() + 300L;
                        }
                    });
                }
            }
        }));
        CCanvas.pausemenu.startAt(menu);
    }

    public void addTimeBomb(TimeBomb bomb) {
        timeBombs.addElement(bomb);
        waitting();
    }

    public void explodeTimeBomb(int id) {
        for (int i = 0; i < timeBombs.size(); ++i) {
            TimeBomb b = (TimeBomb) timeBombs.elementAt(i);
            if (b.id == id) {
                b.isExplore = true;
                mm.makeHole(b.x, b.y, (byte) 57, 9);
                return;
            }
        }

        waitting();
    }

    public static void waitting() {
        CTime.seconds += 2;
        CCanvas.tNotify = 0;
        CCanvas.lockNotify = true;
        if (CCanvas.curScr == CCanvas.gameScr) {
            Session_ME.receiveSynchronized = 1;
        }

    }

    public void exitGiuaChung() {
        if (pm != null && PM.p != null && PM.p[myIndex] != null) {
            GameService.gI().leaveBoard();
            CCanvas.startWaitDlgWithoutCancel(Language.leaveBattle(), 9);
            GameService.gI().requestRoomList();
            CScreen.isSetClip = true;
        }

    }

    public void doExit() {
        for (int i = 0; i < PM.MAX_PLAYER; ++i) {
            if (PM.p[i] != null) {
                PM.p[i] = null;
            }
        }

        CCanvas.prepareScr.show();
        Session_ME.receiveSynchronized = 0;
    }

    public void update() {
        if (trainingMode) {
            this.doTraining();
        }

        if (this.chatWait > 0) {
            --this.chatWait;
        }

        tfChat.update();
        this.updateChat();
        bm.update();
        pm.update();
        sm.update();
        cam.update();
        if (this.snow != null) {
            this.snow.update();
        }

        int i;
        for (i = 0; i < timeBombs.size(); ++i) {
            TimeBomb b = (TimeBomb) timeBombs.elementAt(i);
            if (b != null) {
                b.update();
            }
        }

        for (i = 0; i < exs.size(); ++i) {
            ((Explosion) exs.elementAt(i)).update();
        }

        time.update();
        ++tickCount;
        if (tickCount > 10000) {
            tickCount = 0;
        }

        if (this.isMoneyFly) {
            --this.moneyY;
            if (this.moneyY < 50) {
                this.isMoneyFly = false;
                this.moneyY = h / 2 - 15;
            }
        }

        if (this.isMoney2Fly) {
            --this.moneyY2;
            if (this.moneyY2 < PM.p[this.whoGetMoney2].y + 100) {
                this.isMoney2Fly = false;
            }
        }

        if (this.vGift.size() != 0) {
            ++this.tFly;
            if (this.tFly == 10) {
                for (i = 0; i < this.vGift.size(); ++i) {
                    if (!((GiftEffect) this.vGift.elementAt(i)).isFly) {
                        ((GiftEffect) this.vGift.elementAt(i)).isFly = true;
                        break;
                    }
                }

                this.tFly = 0;
            }
        }

        for (i = 0; i < this.vGift.size(); ++i) {
            ((GiftEffect) this.vGift.elementAt(i)).update();
        }

    }

    public void mainLoop() {
        super.mainLoop();
        mm.update();
        cam.mainLoop();
    }

    private void doTraining() {
        switch (trainingStep) {
            case 0:
                if (!PM.p[0].falling) {
                    trainingStep = -1;
                    CCanvas.startOKDlg(Language.training1(), new IAction() {
                        public void perform() {
                            CCanvas.startOKDlg(Language.training2(), new IAction() {
                                public void perform() {
                                    GameScr.trainingStep = 1;
                                }
                            });
                        }
                    });
                }
                break;
            case 1:
                if (PM.p[0].movePoint > 20) {
                    trainingStep = -1;
                    CCanvas.startOKDlg(Language.trainin3(), new IAction() {
                        public void perform() {
                            CCanvas.startOKDlg(Language.training4(), new IAction() {
                                public void perform() {
                                    CCanvas.startOKDlg(Language.training5(), new IAction() {
                                        public void perform() {
                                            GameScr.trainingStep = 2;
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
                break;
            case 2:
                if (PM.p[1].y > 514) {
                    trainingStep = -1;
                    CCanvas.startOKDlg(Language.training6(), new IAction() {
                        public void perform() {
                            GameScr.trainingStep = 3;
                        }
                    });
                }
                break;
            case 3:
                if (PM.getMyPlayer().hp == 100 || PM.p[1].y > MM.mapHeight || PM.getMyPlayer().y > MM.mapHeight) {
                    trainingStep = -1;
                    CCanvas.startOKDlg(Language.training7(), new IAction() {
                        public void perform() {
                            GameScr.trainingStep = 0;
                            GameScr.trainingMode = false;
                            GameService.gI().training((byte) 1);
                            CCanvas.menuScr.show();
                        }
                    });
                }
        }

    }

    public void activeMoney2Fly(int money, int ID_of_whoget) {
        if (PM.p[PM.getIndexByIDDB(ID_of_whoget)] != null && PM.p != null) {
            if (money > 0) {
                this.showChat(ID_of_whoget, " +" + money + Language.xu());
            } else {
                this.showChat(ID_of_whoget, " " + money + Language.xu());
            }

        }
    }

    public void setWin(byte isWin, byte _exBonus, int _moneyBonus) {
        this.chatList.removeAllElements();
        this.exBonus = _exBonus;
        this.moneyBonus = _moneyBonus;
        this.moneyY = CScreen.h / 2;
        this.isMoneyFly = true;
        time.stop();
        if (isWin == 0) {
            res = Language.RAW();
            pm.setPlayerAfterDraw();
        } else if (PM.p[myIndex] != null) {
            boolean teamWin = false;
            if (isWin == 1) {
                res = Language.WIN();
                if (PM.p[myIndex].team) {
                    teamWin = true;
                } else {
                    teamWin = false;
                }
            } else {
                res = Language.LOSE();
                if (PM.p[myIndex].team) {
                    teamWin = false;
                } else {
                    teamWin = true;
                }
            }

            if (teamWin == PM.p[myIndex].team) {
                pm.setPlayerAfterSetWin(teamWin);
            }
        }

    }

    public static int[][] getPointAround(int x, int y, int number) {
        int[] xPoint = new int[number];
        int[] yPoint = new int[number];
        xPoint[6] = x - 10;
        yPoint[6] = y;
        xPoint[5] = x + 10;
        yPoint[5] = y;
        xPoint[4] = x;
        yPoint[4] = y - 35;
        xPoint[3] = x;
        yPoint[3] = y - 70;
        xPoint[2] = x - 30;
        yPoint[2] = y - 70;
        xPoint[1] = x + 30;
        yPoint[1] = y - 70;
        xPoint[0] = x;
        yPoint[0] = y - 85;
        return new int[][]{xPoint, yPoint};
    }

    public void checkEyeSmoke(byte whoCantSee, byte typeSee) {
        if (typeSee == 1) {
            PM.p[whoCantSee].cantSee = false;
            if (whoCantSee == myIndex) {
                cantSee = false;
            }
        } else {
            PM.p[whoCantSee].cantSee = true;
            if (whoCantSee == myIndex) {
                cantSee = true;
            }
        }

        waitting();
    }

    public void checkInvisible2(byte whoInvisible) {
        PM.p[whoInvisible].isInvisible = false;
        cam.setPlayerMode(whoInvisible);
        waitting();
    }

    public void checkVampire(byte whoVampire) {
        PM.p[whoVampire].isVampire = false;
        cam.setPlayerMode(whoVampire);
        waitting();
    }

    public void checkFreeze(byte whoCantMove, byte type) {
        if (type == 1) {
            PM.p[whoCantMove].isFreeze = false;
        } else {
            PM.p[whoCantMove].isFreeze = true;
        }

        waitting();
    }

    public void checkPostion(byte whoPoison) {
        PM.p[whoPoison].isPoison = true;
        waitting();
    }

    private void paintTouch(mGraphics g) {
        int xCenter = 0;
        int yCenter = CCanvas.hieght - phai.image.height - 10;
        xL = xCenter + 35;
        yL = yCenter;
        xR = xCenter + 140;
        yR = yCenter;
        xF = CCanvas.width - (crossHair2.image.width + crossHair2.image.width / 2);
        yF = CCanvas.hieght - (crossHair2.image.height + crossHair2.image.height / 2);
        byte xOffset;
        byte yOffset;
        if (CCanvas.isDebugging()) {
            g.setColor(16765440);
            xOffset = 30;
            yOffset = 30;
            g.fillRect(xF - xOffset / 2, yF - yOffset / 2, crossHair2.image.getWidth() + xOffset, crossHair2.image.getHeight() + yOffset, false);
        }

        if (this.isPressXF) {
            g.drawImage(crossHair2, xF + 2, yF + 2, mGraphics.TOP | mGraphics.LEFT, false);
        } else {
            g.drawImage(crossHair2, xF, yF, mGraphics.TOP | mGraphics.LEFT, false);
        }

        if (CCanvas.isDebugging()) {
            g.setColor(16765440);
            xOffset = 30;
            yOffset = 30;
            g.fillRect(xL - trai.image.width / 2 - xOffset / 2, yL - trai.image.height / 2 - yOffset / 2, trai.image.getWidth() + xOffset, trai.image.getHeight() + yOffset, false);
        }

        if (this.isPressXL) {
            g.drawImage(trai, xL + 2, yL + 2, mGraphics.VCENTER | mGraphics.HCENTER, false);
        } else {
            g.drawImage(trai, xL, yL, mGraphics.VCENTER | mGraphics.HCENTER, false);
        }

        if (CCanvas.isDebugging()) {
            g.setColor(16765440);
            xOffset = 30;
            yOffset = 30;
            g.fillRect(xR - phai.image.width / 2 - xOffset / 2, yR - trai.image.height / 2 - yOffset / 2, phai.image.getWidth() + xOffset, phai.image.getHeight() + yOffset, false);
        }

        if (this.isPressXR) {
            g.drawImage(phai, xR + 2, yR + 2, mGraphics.VCENTER | mGraphics.HCENTER, false);
        } else {
            g.drawImage(phai, xR, yR, mGraphics.VCENTER | mGraphics.HCENTER, false);
        }

    }

    public void paint(mGraphics g) {
        Camera.translate(g);
        if (curGRAPHIC_LEVEL != 2) {
            mm.paintBackGround(g);
        } else {
            g.setColor(6483442);
            g.fillRect(Camera.x, Camera.y, CCanvas.width, CCanvas.hieght, false);
        }

        if (this.snow != null) {
            this.snow.paintSmallSnow(g);
        }

        mm.paint(g);

        int i;
        for (i = 0; i < this.vGift.size(); ++i) {
            ((GiftEffect) this.vGift.elementAt(i)).paint(g);
        }

        if (isDarkEffect) {
            Effect.FillTransparentRect(g, Camera.x, Camera.y, w, h);
        }

        if (cantSee) {
            g.setColor(16777215);
            g.fillRect(Camera.x, Camera.y, w, h, false);
        }

        for (i = 0; i < timeBombs.size(); ++i) {
            TimeBomb bomb = (TimeBomb) timeBombs.elementAt(i);
            if (bomb != null) {
                bomb.paint(g);
            }
        }

        pm.paint(g);

        for (i = 0; i < PM.p.length; ++i) {
            if (PM.p[i] != null && PM.p[i].isFreeze) {
                g.drawImage(Explosion.dongbang, PM.p[i].x, PM.p[i].y - 12, 3, false);
            }
        }

        sm.paint(g);
        bm.paint(g);
        if (MM.isHaveWaterOrGlass) {
            mm.paintWater(g);
        }

        for (i = 0; i < exs.size(); ++i) {
            ((Explosion) exs.elementAt(i)).paint(g);
        }

        if (this.snow != null) {
            this.snow.paintBigSnow(g);
        }

        int xPaint;
        if (whiteEffect) {
            g.setColor(16777215);
            g.fillArc(Camera.x + CCanvas.width / 2 - this.xE, Camera.y + CCanvas.width / 2 - this.xE, this.wE, this.wE, 0, 360, false);
            this.xE += 30;
            this.wE += 60;
            if (this.xE > CCanvas.width + 100) {
                this.xE = 0;
                this.wE = 0;
                int[][] array = getPointAround(xNuke, yNuke, 7);

                for (xPaint = 0; xPaint < 7; ++xPaint) {
                    new Explosion(array[0][xPaint], array[1][xPaint], (byte) 7);
                }

                whiteEffect = false;
            }
        }

        if (electricEffect) {
            ++this.tE;
            if (this.tE % 2 == 0) {
                new Explosion(xElectric + CRes.random(-20, 20), yElectric + CRes.random(-20, 20), (byte) 8);
            }

            if (this.tE == 10) {
                this.tE = 0;
                electricEffect = false;
            }
        }

        if (freezeEffect) {
            ++this.tE;
            if (this.tE % 2 == 0) {
                new Explosion(xFreeze + CRes.random(-50, 50), yFreeze + CRes.random(-50, 50), (byte) 14);
            }

            if (this.tE == 30) {
                this.tE = 0;
                freezeEffect = false;
            }
        }

        if (suicideEffect) {
            ++this.tE;
            if (this.tE % 2 == 0) {
                new Explosion(xSuicide + CRes.random(-50, 50), ySuicide + CRes.random(-50, 50), (byte) 0);
            }

            if (this.tE == 60) {
                this.tE = 0;
                suicideEffect = false;
            }
        }

        if (poisonEffect) {
            ++this.tE;
            if (this.tE % 2 == 0) {
                new Explosion(xPoison + CRes.random(-50, 50), yPoison + CRes.random(-50, 50), (byte) 15);
            }

            if (this.tE == 60) {
                this.tE = 0;
                poisonEffect = false;
            }
        }

        if (CCanvas.isDebugging()) {
            for (i = 0; i < MM.mapWidth / 100; ++i) {
                g.setColor(16711680);
                g.drawLine(100 * (i + 1), 0, 100 * (i + 1), MM.mapHeight, false);
                Font.normalFont.drawString(g, String.valueOf(i), 50 + i * 100, CCanvas.h / 2, 0);
            }
        }

        if (Camera.shaking == 2 && tickCount / 2 % 2 == 0) {
            g.setColor(16711680);
            g.fillRect(Camera.x, Camera.y, w, 10, false);
            g.fillRect(Camera.x, Camera.y + h - 10, w, 10, false);
            g.fillRect(Camera.x, Camera.y, 10, h, false);
            g.fillRect(Camera.x + w - 10, Camera.y, 10, h, false);
        }

        if (!trainingMode) {
            time.paint(g);
        }

        int yPaint;
        if (pm.isYourTurn() && PM.getCurPlayer() != null) {
            int forceT1 = PM.getMyPlayer().getState() == 3 ? PM.getMyPlayer().force : 0;
            int forceT2 = PM.getMyPlayer().getState() == 3 ? PM.getMyPlayer().force_2 : 0;
            yPaint = PM.getMyPlayer().movePoint;
            int lastForcePoint1 = PM.getMyPlayer().lastForcePoint;
            int lastForcePoint2 = PM.getMyPlayer().lastForcePoint_2;
            if (!this.isSelectItem && MainGame.getNumberFingerOnScreen() < 2 && Camera.mode != 0 && !CPlayer.isShooting) {
                onDrawPowerBar(g, Camera.x + (w >> 1), Camera.y + h - 25 + 5, forceT1, lastForcePoint1, yPaint);
                if (PM.getMyPlayer().isDoublePower) {
                    onDrawSecondPowerBar(g, Camera.x + (w >> 1), Camera.y + h - 25 - 15 + 5, forceT2, lastForcePoint2, yPaint);
                }

                onDrawAngleBar(g, Camera.x + (w >> 1), Camera.y + h - 25 + 8, PM.getMyPlayer().angle);
            }
            PM.getMyPlayer().drawKegoc(g);
        }

        if (!pm.isYourTurn()) {
            g.translate(-g.getTranslateX(), -g.getTranslateY());
        } else if (CCanvas.isTouch) {
            g.translate(-g.getTranslateX(), -g.getTranslateY());
            if (!this.isSelectItem && MainGame.getNumberFingerOnScreen() < 2 && Camera.mode != 0 && !CPlayer.isShooting) {
                this.paintTouch(g);
            }
        }

        if (this.isSelectItem) {
            if (CCanvas.isTouch) {
                g.translate(-g.getTranslateX(), -g.getTranslateY());
                paintBorderRect(g, CCanvas.hh - 65, 4, 130, Language.chonItem());
                onDrawItem(g, CCanvas.hw - 67, CCanvas.hh - 20);
                g.drawImage(CRes.imgMenu, 25, 5, 0, false);
            } else {
                onDrawItem(g, Camera.x + (CCanvas.hw - 27), Camera.y + CCanvas.hh);
            }
        }

        this.drawSCORE(g);
        String text;
        if (PM.getCurPlayer() != null) {
            text = PM.getCurPlayer().name;
            yPaint = CCanvas.isTouch ? 25 : 0;
            if (text != null && !(PM.getCurPlayer() instanceof Boss)) {
                (PM.getCurPlayer().team ? Font.smallFontRed : Font.smallFontYellow).drawString(g, text.toUpperCase(), CScreen.w - 16, 22 + yPaint, 2);
            }
        }

        this.drawWind(g);
        if (Camera.mode == 0) {
            this.drawWhenFreeCam(g);
        }

        if (CCanvas.currentDialog == null && !this.isSelectItem) {
            this.drawMenuCameraIcon(g);
        }

        if (!CRes.isNullOrEmpty(tfChat.getText()) && this.isChat) {
            this.isChat = false;
            if (this.chatWait == 0) {
                text = tfChat.getText();
                GameService.gI().chatToBoard(text);
                tfChat.setText("");
                this.showChat(TerrainMidlet.myInfo.IDDB, text);
                CCanvas.gameScr.showChat(TerrainMidlet.myInfo.IDDB, text, 90);
                this.chatWait = this.chatDelay;
            } else {
                tfChat.setText("");
            }

            clearKey();
        }

        this.drawChat(g);
        if (CCanvas.isDebugging()) {
            xPaint = CCanvas.width - 2 - Font.normalRFont.getWidth(GameMidlet.version);
            yPaint = CCanvas.hieght - Font.normalRFont.getHeight() * 2;
            Font.normalRFont.drawString(g, String.valueOf(GameMidlet.timePingPaint), xPaint, yPaint, 2, false);
            Font.normalRFont.drawString(g, "CAM: " + Camera.getMode(), xPaint, yPaint - 15, 2, false);
            if (pm.isYourTurn()) {
                Font.normalRFont.drawString(g, "SHOOT: " + CPlayer.isShooting, xPaint, yPaint - 30, 2, false);
            }

            if (CCanvas.isPointerDown[0]) {
                Font.normalFont.drawString(g, CCanvas.pX[0] + "/" + CCanvas.pY[0], CCanvas.pX[0], CCanvas.pY[0] - 15, 2, false);
            }
        }

        if (CCanvas.currentDialog != null) {
            super.paintCommand(g);
        }

    }

    private void drawWhenFreeCam(mGraphics g) {
        Font.borderFont.drawString(g, Language.cameraMode(), CCanvas.hw, CCanvas.hh - 15, 2);
        int dx = 0;
        if (CCanvas.gameTick % 10 > 4) {
            dx = 2;
        }

        g.drawImage(imgArrow, 0 + dx, CCanvas.hh, mGraphics.LEFT | mGraphics.VCENTER, false);
        g.drawRegion(imgArrow, 0, 0, imgArrow.image.getWidth(), imgArrow.image.getHeight(), 2, CCanvas.width - dx, CCanvas.hh, mGraphics.VCENTER | mGraphics.RIGHT, false);
        g.drawRegion(imgArrow, 0, 0, imgArrow.image.getWidth(), imgArrow.image.getHeight(), 5, CCanvas.hw, 25 + dx, mGraphics.TOP | mGraphics.HCENTER, false);
        g.drawRegion(imgArrow, 0, 0, imgArrow.image.getWidth(), imgArrow.image.getHeight(), 6, CCanvas.hw, CCanvas.hieght - 30 - dx, mGraphics.BOTTOM | mGraphics.HCENTER, false);
    }

    private void drawMenuCameraIcon(mGraphics g) {
        if (!CCanvas.isTouch) {
            if (Camera.mode == 0) {
                if (CCanvas.gameTick % 10 > 5) {
                    g.drawImage(CRes.imgCam, Camera.x + w - 20, Camera.y + h - 18, 0, false);
                }
            } else {
                g.drawImage(CRes.imgCam, Camera.x + w - 20, Camera.y + h - 18, 0, false);
            }
        } else {
            int hDraw = CRes.imgMenu.image.getHeight();
            if (Camera.mode == 0) {
                if (CCanvas.gameTick % 10 > 5) {
                    g.drawImage(CRes.imgCam, w - CRes.imgCam.image.getWidth() - 5, hDraw + 5, 0, false);
                }
            } else {
                g.drawImage(CRes.imgMenu, 20, hDraw + 5, 0, false);
                g.drawImage(PrepareScr.iconChat, 70, hDraw + 5, 0, false);
                g.drawImage(CRes.imgCam, w - CRes.imgCam.image.getWidth() - 5, hDraw + 5, 0, false);
            }
        }

    }

    public static void changeWind(int NextWindX, int NextWindY) {
        windx = NextWindX;
        windy = NextWindY;
        windAngle = CRes.fixangle(CRes.angle(windx, -windy));
        windPower = CRes.sqrt(windx * windx + windy * windy);
    }

    public void drawWind(mGraphics g) {
        if (Camera.mode != 0) {
            g.drawImage(wind1, CCanvas.width / 2, 22, 3, true);
            if (!this.b) {
                ++this.dem;
            } else {
                --this.dem;
            }

            if (this.dem > 5) {
                this.b = true;
            }

            if (this.dem < 0) {
                this.b = false;
            }

            g.drawImage(wind2, CCanvas.w / 2, 22, 3, true);
            if (windPower != 0) {
                int DX = 13 * CRes.cos(CRes.fixangle(windAngle)) >> 10;
                int DY = 13 * CRes.sin(CRes.fixangle(windAngle)) >> 10;
                g.drawImage(wind3, CCanvas.w / 2 + 2 + DX, 22 - DY, mGraphics.VCENTER | mGraphics.HCENTER, true);
            }

            Font.borderFont.drawString(g, String.valueOf(windPower), CCanvas.w / 2, 15, 3);
            Font.borderFont.drawString(g, Language.windAngle() + ": " + windAngle, CCanvas.w / 2, 45, 2);
        }
    }

    public void drawSCORE(mGraphics g) {
        if (!res.equals("")) {
            Font.bigFont.drawString(g, res, w / 2, 80, mGraphics.HCENTER | mGraphics.VCENTER);
            Font.borderFont.drawString(g, Language.money() + ": " + this.moneyBonus + Language.xu(), w / 2, this.moneyY, mGraphics.HCENTER | mGraphics.VCENTER);
        }

        if (this.isMoney2Fly) {
            Font.borderFont.drawString(g, "+" + this.moneyBonus2 + Language.xu(), PM.p[this.whoGetMoney2].x, this.moneyY2, mGraphics.HCENTER | mGraphics.VCENTER);
        }

    }

    public void show(CScreen lastScreen) {
        lastSCreen = lastScreen;
        CScreen.isSetClip = false;
        super.show();
    }

    public void show() {
        super.show();
        CScreen.isSetClip = false;
    }

    protected void onClose() {
        super.onClose();
        CScreen.isSetClip = true;
    }

    public static void onDrawPowerBar(mGraphics g, int x, int y, int power, int mark, int movePoint) {
        if (s_frBar != null) {
            s_frBar.drawFrame(1, x - 54 - 10, y + 5, 3, 0, g, false);
            s_frBar.drawFrame(3, x + 2 + 10, y + 5, 3, 0, g, false);
            s_frBar.fillFrame(0, x - 54 - 10, y + 5, (60 - movePoint) * 100 / 60, 3, 0, g, true);
            s_frBar.fillFrame(2, x + 2 + 10, y + 5, power * 100 / 30, 3, 0, g, true);
            s_frBar.drawFrame(5, x - 53 - 10, y - 7, 3, 0, g, true);
            s_frBar.fillFrame(4, x - 54 - 10, y - 7, PM.getMyPlayer().angryX * 100 / 100, 3, 0, g, true);
            if (mark > 0) {
                g.setColor(16482175);
                g.drawLine(x + 2 + mark * 49 / 30 + 10, y + 7, x + 2 + mark * 49 / 30 + 10, y + 7 + 7, false);
            }

        }
    }

    public static void onDrawSecondPowerBar(mGraphics g, int x, int y, int power, int mark, int movePoint) {
        if (Camera.mode != 0) {
            s_frBar.drawFrame(3, x + 2 + 10, y + 8, 0, 0, g);
            s_frBar.fillFrame(2, x + 2 + 10, y + 8, power * 100 / 30, 3, 0, g, true);
            if (mark > 0) {
                g.setColor(16482175);
                g.drawLine(x + 2 + mark * 49 / 30 + 10, y + 10, x + 2 + mark * 49 / 30 + 10, y + 8 + 9, false);
            }

        }
    }

    public static void onDrawAngleBar(mGraphics g, int x, int y, int angle) {
        if (Camera.mode != 0) {
            g.drawImage(s_imgAngle, x, y + 2, mGraphics.TOP | mGraphics.HCENTER, false);
            Font.smallFontYellow.drawString(g, "" + (angle >= 90 ? 180 - angle : angle), x, y + 4, 2);
        }
    }

    public static void onDrawItem(mGraphics g, int x, int y) {
        if (!CCanvas.isTouch) {
            g.setColor(16767817);
            g.fillRect(Camera.x, y - 1, CCanvas.width, CCanvas.isTouch ? 43 : 36, false);
        }

        Item.DrawSetItem(g, PM.getMyPlayer().item, curItemSelec, x, y, CCanvas.isTouch, PrepareScr.currLevel == 7 ? num : null);
        Font.borderFont.drawString(g, Language.use(), Camera.x + 5, Camera.y + CCanvas.hieght - Font.normalFont.getHeight() - 4, 0);
        Font.borderFont.drawString(g, Language.close(), Camera.x + CCanvas.width - 5, Camera.y + CCanvas.hieght - Font.normalFont.getHeight() - 4, 1);
    }

    public static void onDrawArrow(mGraphics g, int x, int y, int color, boolean isDynamic) {
        if (isDynamic) {
            y += 2 * (tickCount / 2 % 2);
        }

        g.setColor(color);
        g.fillRect(x, y, 11, 2, false);
        g.fillTriangle(x, y + 3, x + 11, y + 3, x + 5, y + 9, false);
    }

    public void showChat(int fromID, String text) {
        if (PrepareScr.currLevel != 7) {
            this.chatList.addElement(CCanvas.prepareScr.getPlayerNameFromID(fromID) + ": " + text);
        } else {
            this.chatList.addElement(pm.getPlayerNameFromID(fromID) + ": " + text);
        }

        if (this.chatDelay == 0) {
            this.chatDelay = this.MAX_CHAT_DELAY;
        }

    }

    public void updateChat() {
        if (this.chatDelay > 0) {
            --this.chatDelay;
            if (this.chatDelay == 0) {
                if (this.chatList.size() > 0) {
                    this.chatList.removeElementAt(0);
                }

                if (this.chatList.size() > 0) {
                    this.chatDelay = this.MAX_CHAT_DELAY;
                }
            }
        }

    }

    public void drawChat(mGraphics g) {
        if (this.chatList.size() != 0) {
            String chat = (String) this.chatList.elementAt(0);
            int nDevision = this.MAX_CHAT_DELAY - this.chatDelay;
            if (nDevision > 10) {
                nDevision = 10;
            }

            int xChat = CCanvas.width;

            for (int i = 0; i < nDevision; ++i) {
                xChat >>= 1;
            }

            Font.borderFont.drawString(g, chat, 3 + xChat, CCanvas.hieght - 14, 0);
        }
    }

    public void showChat(int fromID, String text, int Interval) {
        ChatPopup cp = new ChatPopup();
        CPlayer _player = pm.getPlayerFromID(fromID);
        if (_player != null) {
            cp.show(Interval, _player.x - Camera.x, _player.y - Camera.y - 30, text);
            CCanvas.arrPopups.addElement(cp);
        }

    }

    public void onClearMap() {
        mm.onClearMap();
        sm.onClearMap();
        System.gc();
    }

    public void onPointerPressed(int xScreen, int yScreen, int index) {
        if (Camera.mode == 1 && mSystem.currentTimeMillis() - this.timeDelayClosePauseMenu > 550L) {
            pm.onPointerPressed(xScreen, yScreen, index);
        }
        if (CCanvas.keyPressed[5]) {
            if (GameScr.pm != null && GameScr.pm.isYourTurn()) {
                PM.getMyPlayer().holdFire();
                CScreen.clearKey();
            }
        }

        super.onPointerPressed(xScreen, yScreen, index);
    }

    public void onPointerHold(int xScreen, int yScreen, int index) {
        if (!this.isSelectItem) {
            if (!this.isShowPausemenu) {
                if (mSystem.currentTimeMillis() - this.timeDelayClosePauseMenu >= 300L) {
                    if (mSystem.currentTimeMillis() - this.timeShowPauseMenu >= 300L) {
                        if (Camera.mode == 1) {
                            pm.onPointerHold(xScreen, yScreen, index);
                            int xOffset = 30;
                            int yOffset = 30;
                            if (CCanvas.isPointer(xL - trai.image.width / 2 - xOffset / 2, yL - trai.image.height / 2 - yOffset / 2, trai.image.getWidth() + xOffset, trai.image.getHeight() + yOffset, index) && pm.isYourTurn()) {
                                this.isPressXL = true;
                                return;
                            }

                            if (CCanvas.isPointer(xR - phai.image.width / 2 - xOffset / 2, yR - trai.image.height / 2 - yOffset / 2, phai.image.getWidth() + xOffset, phai.image.getHeight() + yOffset, index) && pm.isYourTurn()) {
                                this.isPressXR = true;
                                return;
                            }

                            xOffset = 30;
                            yOffset = 30;
                            if (CCanvas.isPointer(xF - xOffset / 2, yF - yOffset / 2, crossHair2.image.getWidth() + xOffset, crossHair2.image.getHeight() + yOffset, index) && pm.isYourTurn()) {
                                this.isPressXF = true;
                            }
                        }

                    }
                }
            }
        }
    }

    public void onPointerDragged(int xScreen, int yScreen, int index) {
        if (!this.isSelectItem) {
            if (!this.isShowPausemenu) {
                if (mSystem.currentTimeMillis() - this.timeDelayClosePauseMenu >= 300L) {
                    if (mSystem.currentTimeMillis() - this.timeShowPauseMenu >= 300L) {
                        try {
                            if (pm.isYourTurn() || !(PM.p[PM.curP] instanceof Boss)) {
                                if (MainGame.getNumberFingerOnScreen() >= 2) {
                                    if (Camera.mode == 1 && index == 1) {
                                        pm.onPointerDragRighCorner(xScreen, yScreen, index);
                                    }
                                } else {
                                    if (Camera.mode == 1) {
                                        pm.onPointerDrag(xScreen, yScreen, index);
                                    }

                                    if (Camera.mode == 0) {
                                        this.onDragCamera(xScreen, yScreen, index);
                                    }
                                }
                            }
                        } catch (Exception var5) {
                        }

                        super.onPointerDragged(xScreen, yScreen, index);
                    }
                }
            }
        }
    }

    public void onPointerReleased(int x, int y2, int index) {
        this.isPressXL = this.isPressXR = this.isPressXF = false;
        if (Camera.mode == 1 && !this.isSelectItem) {
            pm.onPointerReleased(x, y2, index);
        }

        if (this.isSelectItem) {
            if (!CCanvas.isPointer(x - 50, y2 - 50, 210, 130, index)) {
                this.timeDelayClosePauseMenu = mSystem.currentTimeMillis() + 550L;
            } else {
                this.isSelectItem = true;
                this.timeDelayClosePauseMenu = mSystem.currentTimeMillis() + 550L;
                this.selectedItemPanelRealeased(x, y2, index);
            }
        } else {
            if (CCanvas.pausemenu.isShow) {
                CCanvas.pausemenu.onPointerRealeased(x, y2, index);
            } else if (CCanvas.isPointer(0, 0, 50, 50, index)) {
                this.doShowPauseMenu();
            }

            this.isShowPausemenu = CCanvas.pausemenu.isShow;
            if (CCanvas.pausemenu.isShow) {
                return;
            }

            if (CCanvas.isPointer(60, 0, 60, 60, index)) {
                this.isChat = true;
                tfChat.doChangeToTextBox();
            }

            if (PM.getCurPlayer() != null && CCanvas.isPointer(CCanvas.width - 50, 0, 50, 50, index)) {
                if (Camera.mode != 0) {
                    if (!(PM.getCurPlayer() instanceof Boss) && CCanvas.isPointer(CCanvas.width - 50, 0, 50, 50, index)) {
                        Camera.mode = 0;
                        clearKey();
                    }
                } else if (Camera.mode == 0) {
                    if (BM.active && bm.bullets.size() > 0) {
                        cam.setBulletMode((Bullet) bm.bullets.elementAt(0));
                    } else {
                        cam.setPlayerMode(PM.curP);
                    }
                }
            }
        }

    }

    public void onPaintSliderRightConer(mGraphics gr, int xPaint, int yPaint) {
    }

    public void notClearMap(int indexClear) {
        System.gc();
    }
}
