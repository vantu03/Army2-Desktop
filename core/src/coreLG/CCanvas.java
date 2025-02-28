package coreLG;

import CLib.Image;
import CLib.RMS;
import CLib.mGraphics;
import CLib.mImage;
import CLib.mSound;
import CLib.mSystem;
import Equipment.Equip;
import Equipment.EquipGlass;
import Equipment.PlayerEquip;
import Equipment.TypeEquip;
import com.teamobi.mobiarmy2.GameMidlet;
import com.teamobi.mobiarmy2.IActionListener;
import com.teamobi.mobiarmy2.MotherCanvas;

import java.io.IOException;
import java.util.Random;
import java.util.Vector;

import map.CMap;
import map.MM;
import map.MapFile;
import model.CRes;
import model.Dialog;
import model.IAction;
import model.IAction2;
import model.IconManager;
import model.InfoPopup;
import model.InputDlg;
import model.Language;
import model.Menu;
import model.MsgDlg;
import model.MsgPopup;
import model.PauseMenu;
import model.PlayerInfo;
import model.Popup;
import model.Position;
import network.Command;
import network.GameLogicHandler;
import network.GameService;
import network.Message;
import network.Session_ME;
import player.CPlayer;
import screen.ArchivementScr;
import screen.BoardListScr;
import screen.CScreen;
import screen.ChangePlayerCSr;
import screen.ClanScreen;
import screen.ConfigScr;
import screen.EquipScreen;
import screen.FomulaScreen;
import screen.GameScr;
import screen.Inventory;
import screen.LevelScreen;
import screen.ListScr;
import screen.LoginScr;
import screen.LuckyGame;
import screen.LuckyGifrScreen;
import screen.MenuScr;
import screen.MissionScreen;
import screen.MoneyScr;
import screen.MoneyScrIOS;
import screen.MsgScreen;
import screen.PrepareScr;
import screen.QuangCao;
import screen.RoomListScr;
import screen.RoomListScr2;
import screen.ServerListScreen;
import screen.SplashScr;
import shop.ShopBietDoi;
import shop.ShopEquipment;
import shop.ShopItem;
import shop.ShopLinhTinh;

public class CCanvas extends MotherCanvas implements IActionListener {
    private static boolean currentScreen;
    boolean isRunning = true;
    public static int gameTick;
    public static int width;
    public static int hieght;
    public static int hh;
    public static int hw;
    public static CScreen curScr;
    public static SplashScr splashScr;
    public static MenuScr menuScr;
    public static GameScr gameScr;
    public static LoginScr loginScr;
    public static RoomListScr roomListScr;
    public static RoomListScr2 roomListScr2;
    public static BoardListScr boardListScr;
    public static ListScr listScr;
    public static PrepareScr prepareScr;
    public static MsgScreen msgScr;
    public static ShopItem shopItemScr;
    public static ShopEquipment shopEquipScr;
    public static ShopLinhTinh shopLinhtinh;
    public static ChangePlayerCSr changePScr;
    public static LuckyGame luckyGame;
    public static ServerListScreen serverListScreen;
    public static MissionScreen missionScreen;
    public static ShopBietDoi shopBietDoi;
    public static boolean isVirHorizontal;
    public static InputDlg inputDlg;
    public static Menu menu = new Menu();
    public static PauseMenu pausemenu = new PauseMenu();
    public static boolean isMoto;
    public static boolean isWifi = false;
    public static boolean isBB;
    public static Command cmdMenu;
    public static MsgDlg msgdlg = new MsgDlg();
    public static Dialog currentDialog;
    public static Vector<Popup> arrPopups = new Vector();
    public static MsgPopup msgPopup;
    public static InfoPopup infoPopup;
    public static int waitSendMessage;
    public static MoneyScr moneyScr;
    public static MoneyScrIOS moneyScrIOS;
    public static ConfigScr configScr;
    public static LevelScreen levelScreen;
    public static EquipScreen equipScreen;
    public static Inventory inventory;
    public static ClanScreen clanScreen;
    public static ClanScreen topClanScreen;
    public static LuckyGifrScreen luckyGifrScreen;
    public static FomulaScreen fomulaScreen;
    public static ArchivementScr archScreen;
    public static QuangCao quangCaoScr;
    public static byte[] fileData5;
    public static int[] pX = new int[2];
    public static int[] pY = new int[2];
    public static int[] pxLast = new int[2];
    public static int[] pyLast = new int[2];
    public static int[] pxFirst = new int[2];
    public static int[] pyFirst = new int[2];
    public static boolean[] keyPressed = new boolean[55];
    public static boolean[] keyReleased = new boolean[55];
    public static boolean[] keyHold = new boolean[55];
    public static boolean[] isPointerDown = new boolean[2];
    public static boolean[] isPointerRelease = new boolean[2];
    public static boolean[] isPointerSelect = new boolean[2];
    public static boolean[] isPointerMove = new boolean[2];
    public static boolean[] isPointerClick = new boolean[2];
    public static int pointer;
    public static int button;
    public static boolean isTouch;
    public static IconManager iconMn;
    public static int nBigImage;
    public static boolean isPurchaseIOS;
    int t;
    public static boolean isDoubleClick;
    private static int MAX_TIME_CLICK = 120;
    private static long timeClick;
    public static boolean isInGameRunTime;
    public static boolean lockNotify;
    public static int countNotify;
    public static int tNotify;
    public static boolean isReconnect;
    public static byte tileMapVersion;
    public static byte mapIconVersion;
    public static byte mapValuesVersion;
    public static byte playerVersion;
    public static byte equipVersion;
    public static byte levelCVersion;
    boolean isTestMap = false;
    private static int indexBullet = 0;
    public static Image imgTest;
    public static long timeHideStartWaitingDlg;
    public static Random r = new Random();
    public static long timeNow;
    public static boolean isSmallScreen;
    private Vector<Position> listPoint = new Vector();
    private int curPos;
    long timeHold = 0L;

    public CCanvas() {
        setFullScreenMode(true);
        isTouch = true;
        this.screenInit(true);
        CScreen.cmdH = 35;
        isInGameRunTime = false;
        mSound.init();
    }

    public void screenInit(boolean setV) {
        setFullScreenMode(true);
        width = w;
        hieght = h;
        hh = MotherCanvas.hh;
        hw = MotherCanvas.hw;
        splashScr = new SplashScr();
        isTouch = true;
        splashScr.show();
        CScreen.w = width;
        CScreen.h = hieght;
        loadScreen();
    }

    public static void loadScreen() {
        luckyGame = new LuckyGame();
        shopEquipScr = new ShopEquipment();
        shopItemScr = new ShopItem();
        shopLinhtinh = new ShopLinhTinh();
        listScr = new ListScr();
        msgScr = new MsgScreen();
        infoPopup = new InfoPopup();
        prepareScr = new PrepareScr();
        inputDlg = new InputDlg();
        msgPopup = new MsgPopup();
        clanScreen = new ClanScreen(1);
        topClanScreen = new ClanScreen(0);
        serverListScreen = new ServerListScreen();
        luckyGifrScreen = new LuckyGifrScreen();
        fomulaScreen = new FomulaScreen();
        archScreen = new ArchivementScr();
        moneyScr = new MoneyScr();
        moneyScrIOS = new MoneyScrIOS();
        inventory = new Inventory();
        equipScreen = new EquipScreen();
        pausemenu = new PauseMenu();
        menu = new Menu();
        GameScr.mm = new MM();
        CMap.onInitCmap();
        iconMn = new IconManager();
    }

    public int getGameAction(int keyCode) {
        return this.getGameAction(keyCode);
    }

    public void mainLoop() {
        if (menu != null && menu.showMenu) {
            menu.update();
        }

        if (curScr != null) {
            curScr.mainLoop();
        }

    }

    public void update() {
        ++gameTick;
        if (gameTick > 10000) {
            gameTick = 0;
        }

        if (gameTick % 50 == 0) {
            GameService.gI().ping(gameTick, -1L);
            if (Session_ME.gI().connected) {
                ++GameMidlet.pingCount;
            }
        }

        if (GameMidlet.server == 2 && GameMidlet.pingCount > 15 && !isReconnect && Session_ME.gI().connected) {
            isReconnect = true;
            startYesNoDlg(Language.reConnect(), new IAction() {
                public void perform() {
                    CCanvas.endDlg();
                    CCanvas.isReconnect = false;
                }
            }, new IAction() {
                public void perform() {
                    GameLogicHandler.gI().onResetGame();
                    CCanvas.isReconnect = false;
                }
            });
            if (GameMidlet.ping) {
                GameMidlet.pingCount = 0;
                if (currentDialog != null) {
                    endDlg();
                }
            }
        }

        if (waitSendMessage > 0) {
            --waitSendMessage;
        }

        if (lockNotify) {
            ++tNotify;
            if (tNotify == 20) {
                tNotify = 0;
                lockNotify = false;
                Session_ME.receiveSynchronized = 0;
            }
        }

        if (Session_ME.receiveSynchronized == 1) {
            ++countNotify;
            if (countNotify > 3000) {
                countNotify = 0;
                Session_ME.receiveSynchronized = 0;
            }
        }

        if (currentDialog != null) {
            currentDialog.update();
        }

        for (int i = 0; i < arrPopups.size(); ++i) {
            ((Popup) arrPopups.elementAt(i)).update();
        }

        if (curScr != null) {
            curScr.update();
        }

    }

    public void paint(mGraphics g) {
        if (curScr != null) {
            curScr.paint(g);
        }

        if (currentDialog != null) {
            currentDialog.paint(g);
        }

        if (menu != null && menu.showMenu) {
            menu.paintMenu(g);
        } else if (pausemenu != null && pausemenu.isShow) {
            pausemenu.paint(g);
        }

        int i;
        for (i = 0; i < arrPopups.size(); ++i) {
            if (!(arrPopups.elementAt(i) instanceof MsgPopup)) {
                ((Popup) arrPopups.elementAt(i)).paint(g);
            }
        }

        for (i = 0; i < arrPopups.size(); ++i) {
            Popup p = (Popup) arrPopups.elementAt(i);
            if (p instanceof MsgPopup) {
                ((Popup) arrPopups.elementAt(i)).paint(g);
                break;
            }
        }

    }

    public void keyPressed(int keyCode) {
        this.mapKeyPress(keyCode);
    }

    public void mapKeyPress(int keyCode) {
        if (currentDialog != null) {
            currentDialog.keyPress(keyCode);
        } else {
            boolean var10000 = menu.showMenu;
        }

        switch (keyCode) {
            case -39:
            case -2:
                keyHold[8] = true;
                keyPressed[8] = true;
                break;
            case -38:
            case -1:
                keyHold[2] = true;
                keyPressed[2] = true;
                break;
            case -22:
            case -7:
                keyHold[13] = true;
                keyPressed[13] = true;
                break;
            case -21:
            case -6:
                keyHold[12] = true;
                keyPressed[12] = true;
                break;
            case -5:
            case 10:
                keyHold[5] = true;
                keyPressed[5] = true;
                break;
            case -4:
                keyHold[6] = true;
                keyPressed[6] = true;
                break;
            case -3:
                keyHold[4] = true;
                keyPressed[4] = true;
                break;
            case 35:
                keyHold[11] = true;
                keyPressed[11] = true;
                break;
            case 42:
                keyHold[10] = true;
                keyPressed[10] = true;
        }

        if (curScr != null) {
            curScr.keyPressed(keyCode);
        }

    }

    public void keyReleased(int keyCode) {
        this.mapKeyRelease(keyCode);
    }

    public void mapKeyRelease(int keyCode) {
        switch (keyCode) {
            case -39:
            case -2:
                keyHold[8] = false;
                return;
            case -38:
            case -1:
                keyHold[2] = false;
                return;
            case -22:
            case -7:
                keyHold[13] = false;
                keyReleased[13] = true;
                return;
            case -21:
            case -6:
                keyHold[12] = false;
                keyReleased[12] = true;
                return;
            case -5:
            case 10:
                keyHold[5] = false;
                keyReleased[5] = true;
                return;
            case -4:
                keyHold[6] = false;
                return;
            case -3:
                keyHold[4] = false;
                return;
            case 35:
                keyHold[11] = false;
                keyReleased[11] = true;
                return;
            case 42:
                keyHold[10] = false;
                keyReleased[10] = true;
                return;
            default:
                if (curScr != null) {
                    curScr.keyReleased(keyCode);
                }

        }
    }

    public static void sendMapData() {
        CRes.out("=============================> SEND MAP DATA");
        mapIconVersion = (byte) loadVersion("iconversion2");
        if (mapIconVersion == -1) {
            mapIconVersion = 0;
        }

        mapValuesVersion = (byte) loadVersion("valuesversion2");
        if (mapValuesVersion == -1) {
            mapValuesVersion = 0;
        }

        playerVersion = (byte) loadVersion("playerVersion2");
        if (playerVersion == -1) {
            playerVersion = 0;
        }

        equipVersion = (byte) loadVersion("equipVersion2");
        if (equipVersion == -1) {
            equipVersion = 0;
        }

        levelCVersion = (byte) loadVersion("levelCVersion2");
        if (levelCVersion == -1) {
            levelCVersion = 0;
        }

        GameService.gI().sendVersion((byte) 2, mapValuesVersion);
        if (loadData("valuesdata2") != null) {
            readMess(loadData("valuesdata2"), (byte) 0);
        } else {
            LoginScr.isWait = true;
        }

        if (loadData("icondata2") != null) {
            PrepareScr.fileData = loadData("icondata2");
            if (PrepareScr.fileData != null) {
                PrepareScr.init();
            }
        }

        if (loadData("tiledata2") != null) {
            MM.fullData = loadData("tiledata2");
        }

        CPlayer.fileData = loadData("playerdata2");
        if (CPlayer.fileData != null) {
            CPlayer.init();
        }

        CRes.out("=======> loadData(\"equipdata2\") != null " + (loadData("equipdata2") != null));
        byte[] filepackDataRaw;
        if (loadData("equipdata2") != null) {
            filepackDataRaw = loadData("equipdata2");
            if (filepackDataRaw != null) {
                readMess(filepackDataRaw, (byte) 1);
            }
        }

        if (loadData("levelCData2") != null) {
            filepackDataRaw = loadData("levelCData2");
            if (filepackDataRaw != null) {
                readMess(filepackDataRaw, (byte) 2);
            }
        }

    }

    public static void readMess(byte[] data, byte command) {
        Message msg = new Message(command, data);
        byte len6;
        int i;
        short maxDame;
        switch (command) {
            case 0:
                try {
                    len6 = msg.reader().readByte();
                    MM.NUM_MAP = len6;
                    MM.mapName = new String[len6];
                    MM.mapFileName = new String[len6];

                    for (i = 0; i < len6; ++i) {
                        byte fileID = msg.reader().readByte();
                        maxDame = msg.reader().readShort();
                        byte[] fData = new byte[maxDame];
                        msg.reader().read(fData, 0, maxDame);
                        short[] values = new short[5];

                        for (int j = 0; j < values.length; ++j) {
                            values[j] = msg.reader().readShort();
                        }

                        MM.mapName[i] = msg.reader().readUTF();
                        MM.mapFileName[i] = msg.reader().readUTF();
                        
                        MapFile mf = new MapFile(fData, fileID, values);
                        MM.mapFiles.addElement(mf);
                        Object var31 = null;
                    }

                    CRes.out("=============================> MM.mapFileName  " + len6);
                } catch (Exception var23) {
                    var23.getMessage();
                }
                break;
            case 1:
                CRes.out("=============================> read Trang bi  type = 1 ");
                PlayerEquip.playerData = new Vector();

                try {
                    Vector<EquipGlass> vGlass = new Vector();
                    byte nglass = msg.reader().readByte();
                    nBigImage = nglass;
                    byte[] glass = new byte[nglass];
                    maxDame = 0;
                    byte nFrame = 6;
                    byte nid = 0;

                    for (i = 0; i < nglass; ++i) {
                        glass[i] = msg.reader().readByte();
                        maxDame = msg.reader().readShort();
                        EquipGlass eqGlass = new EquipGlass(glass[i]);
                        eqGlass.maxDamage = maxDame;
                        byte ntype = msg.reader().readByte();
                        byte[] type = new byte[ntype];
                        Vector<TypeEquip> vType = new Vector();

                        for (int j = 0; j < ntype; ++j) {
                            type[j] = msg.reader().readByte();
                            TypeEquip tEquip = new TypeEquip(type[j]);
                            Vector<Equip> vEquip = new Vector();
                            nid = msg.reader().readByte();

                            for (int a = 0; a < nid; ++a) {
                                Equip e = new Equip();
                                e.id = msg.reader().readShort();
                                if (type[j] == 0) {
                                    e.bullet = msg.reader().readByte();
                                }

                                e.type = type[j];
                                e.glass = glass[i];
                                e.icon = msg.reader().readShort();
                                e.level = msg.reader().readByte();
                                e.x = new short[nFrame];
                                e.y = new short[nFrame];
                                e.w = new byte[nFrame];
                                e.h = new byte[nFrame];
                                e.dx = new byte[nFrame];
                                e.dy = new byte[nFrame];

                                for (int k = 0; k < nFrame; ++k) {
                                    e.x[k] = msg.reader().readShort();
                                    e.y[k] = msg.reader().readShort();
                                    e.w[k] = msg.reader().readByte();
                                    e.h[k] = msg.reader().readByte();
                                    e.dx[k] = msg.reader().readByte();
                                    e.dy[k] = msg.reader().readByte();
                                }

                                byte[] aibity = new byte[10];

                                for (int b = 0; b < 10; ++b) {
                                    aibity[b] = msg.reader().readByte();
                                }

                                e.setInvAtribute();
                                e.getInvAtribute(aibity);
                                e.getShopAtribute(aibity);
                                vEquip.addElement(e);
                            }

                            tEquip.addEquip(vEquip);
                            vType.addElement(tEquip);
                        }

                        eqGlass.addType(vType);
                        vGlass.addElement(eqGlass);
                    }

                    PlayerEquip.addGlassEquip(vGlass);
                    short lenIcon = msg.reader().readShort();
                    byte[] iconImg = new byte[lenIcon];
                    msg.reader().read(iconImg, 0, lenIcon);
                    CRes.out("2 =============================> read Trang bi  type = 1 ");
                    mImage.createImage("/equip/01.png", new IAction2() {
                        public void perform(Object object) {
                            EquipScreen.imgIconEQ[0] = new mImage((Image) object);
                        }
                    });
                    mImage.createImage("/equip/02.png", new IAction2() {
                        public void perform(Object object) {
                            EquipScreen.imgIconEQ[1] = new mImage((Image) object);
                        }
                    });
                    mImage.createImage("/equip/03.png", new IAction2() {
                        public void perform(Object object) {
                            EquipScreen.imgIconEQ[2] = new mImage((Image) object);
                        }
                    });
                    mImage.createImage("/equip/04.png", new IAction2() {
                        public void perform(Object object) {
                            EquipScreen.imgIconEQ[3] = new mImage((Image) object);
                        }
                    });
                    mImage.createImage("/equip/05.png", new IAction2() {
                        public void perform(Object object) {
                            EquipScreen.imgIconEQ[4] = new mImage((Image) object);
                        }
                    });
                    CRes.out("3 =============================> read Trang bi  type = 1 ");
                    byte[] bullets = null;

                    for (int c = 0; c < 10; ++c) {
                        short lentBullet = msg.reader().readShort();
                        bullets = new byte[lentBullet];
                        msg.reader().read(bullets, 0, lentBullet);
                        mImage.createImage((byte[]) bullets, 0, lentBullet, (IAction2) (new IAction2() {
                            public void perform(Object object) {
                                try {
                                    PlayerEquip.bullets[CCanvas.indexBullet] = new mImage((Image) object);
                                    CCanvas.indexBullet = CCanvas.indexBullet + 1;
                                } catch (Exception var3) {
                                }

                            }
                        }));
                    }

                    bullets = null;
                    CRes.out("===================> create PlayerEquip.playerData to set myEquip!");
                    CRes.out("__ =============================> read Trang bi  type = 1 have PlayerEquip.playerData " + (PlayerEquip.playerData != null));
                    CRes.out("4 =============================> read Trang bi  type = 1 !!!!! DONE!!!!");
                } catch (Exception var21) {
                    var21.printStackTrace();
                }
                break;
            case 2:
                try {
                    len6 = msg.reader().readByte();
                    PlayerInfo.strLevelCaption = new String[len6];
                    PlayerInfo.levelCaption = new int[len6];

                    for (i = 0; i < len6; ++i) {
                        String name = msg.reader().readUTF();
                        int type = msg.reader().readUnsignedByte();
                        PlayerInfo.strLevelCaption[i] = name;
                        PlayerInfo.levelCaption[i] = type;
                    }

                    TerrainMidlet.myInfo.getQuanHam();
                } catch (IOException var22) {
                    var22.printStackTrace();
                }
        }

        msg.cleanup();
        msg = null;
    }

    public static void saveData(String name, byte[] data) {
        try {
            RMS.saveRMS(name, data);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public static byte[] loadData(String name) {
        return CRes.loadRMSData(name);
    }

    public static void saveVersion(String name, byte version) {
        CRes.saveRMSInt(name, version);
    }

    public static int loadVersion(String name) {
        return CRes.loadRMSInt(name);
    }

    public static Image cutImage(mImage img, int pos) {
        int sw = img.image.getWidth();
        int[] data = new int[sw * sw];
        img.getRGB(data, 0, sw, 0, pos * sw, sw, sw);
        return Image.createRGBImage(data, sw, sw, true);
    }

    public static Image cutImage(mImage img, int pos, IAction2 iAction) {
        int sw = img.image.getWidth();
        int[] data = new int[sw * sw];
        img.getRGB(data, 0, sw, 0, pos * sw, sw, sw);
        return Image.createRGBImage(data, sw, sw, true, iAction);
    }

    public static Image rotateImage(Image src, int angle, mGraphics g, int x, int y, boolean isRGB) {
        int sw = src.getWidth();
        int sh = src.getHeight();
        int[] srcData = new int[sw * sh];
        src.getRGB(srcData, 0, sw, 0, 0, sw, sh);
        int[] dstData = new int[sw * sh];
        float sa = (float) CRes.sin(angle);
        float ca = (float) CRes.cos(angle);
        int isa = (int) (256.0F * sa) / 1000;
        int ica = (int) (256.0F * ca) / 1000;
        int my = -(sh >> 1);

        for (int i = 0; i < sh; ++i) {
            int wpos = i * sw;
            int xacc = my * isa - (sw >> 1) * ica + (sw >> 1 << 8);
            int yacc = my * ica + (sw >> 1) * isa + (sh >> 1 << 8);

            for (int j = 0; j < sw; ++j) {
                int srcx = xacc >> 8;
                int srcy = yacc >> 8;
                if (srcx < 0) {
                    srcx = 0;
                }

                if (srcy < 0) {
                    srcy = 0;
                }

                if (srcx > sw - 1) {
                    srcx = sw - 1;
                }

                if (srcy > sh - 1) {
                    srcy = sh - 1;
                }

                dstData[wpos++] = srcData[srcx + srcy * sw];
                xacc += ica;
                yacc -= isa;
            }

            ++my;
        }

        isRGB = false;
        if (isRGB) {
            g.drawRGB(dstData, 0, sw, x - sw / 2, y - sw / 2, sw, sh, true);
            return null;
        } else {
            return Image.createRGBImage(dstData, sw, sh, true);
        }
    }

    public static void rotateImage(Image src, int angle, mGraphics g, int x, int y, boolean isRGB, IAction2 action) {
        int sw = src.getWidth();
        int sh = src.getHeight();
        int[] srcData = new int[sw * sh];
        src.getRGB(srcData, 0, sw, 0, 0, sw, sh);
        int[] dstData = new int[sw * sh];
        float sa = (float) CRes.sin(angle);
        float ca = (float) CRes.cos(angle);
        int isa = (int) (256.0F * sa) / 1000;
        int ica = (int) (256.0F * ca) / 1000;
        int my = -(sh >> 1);

        for (int i = 0; i < sh; ++i) {
            int wpos = i * sw;
            int xacc = my * isa - (sw >> 1) * ica + (sw >> 1 << 8);
            int yacc = my * ica + (sw >> 1) * isa + (sh >> 1 << 8);

            for (int j = 0; j < sw; ++j) {
                int srcx = xacc >> 8;
                int srcy = yacc >> 8;
                if (srcx < 0) {
                    srcx = 0;
                }

                if (srcy < 0) {
                    srcy = 0;
                }

                if (srcx > sw - 1) {
                    srcx = sw - 1;
                }

                if (srcy > sh - 1) {
                    srcy = sh - 1;
                }

                dstData[wpos++] = srcData[srcx + srcy * sw];
                xacc += ica;
                yacc -= isa;
            }

            ++my;
        }

        Image.createRGBImage(dstData, sw, sh, true, action);
    }

    public static void rotateImage(mImage dan, int de, mGraphics g2, int x, int y, boolean b) {
        int sw = dan.image.getWidth();
        int sh = dan.image.getHeight();
        g2.rotate(de, sw / 2, sh / 2);
        g2.drawImage(dan, x, y, mGraphics.VCENTER | mGraphics.HCENTER, false);
        g2.resetRotate();
    }

    public static boolean isPointer(int x, int y, int w, int h, int index) {
        if (!isPointerDown[index] && !isPointerClick[index]) {
            return false;
        } else {
            return pX[index] >= x && pX[index] <= x + w && pY[index] >= y && pY[index] <= y + h;
        }
    }

    public static boolean isPointerLast(int x, int y, int w, int h, int index) {
        if (!isPointerDown[index] && !isPointerClick[index]) {
            return false;
        } else {
            return pxLast[index] >= x && pxLast[index] <= x + w && pyLast[index] >= y && pyLast[index] <= y + h;
        }
    }

    public static void startOKDlg(String info) {
        msgdlg.setInfo(info, (Command) null, new Command("OK", new IAction() {
            public void perform() {
                CCanvas.currentDialog = null;
                if (CCanvas.curScr.menuScroll) {
                    CCanvas.menuScr.startScrollDown();
                }

            }
        }), (Command) null);
        currentDialog = msgdlg;
    }

    public static void startOKDlg(String info, final IAction action) {
        msgdlg.setInfo(info, (Command) null, new Command("OK", new IAction() {
            public void perform() {
                if (CCanvas.curScr.menuScroll) {
                    CCanvas.menuScr.startScrollDown();
                }

                CCanvas.currentDialog = null;
                if (action != null) {
                    action.perform();
                }

            }
        }), (Command) null);
        currentDialog = msgdlg;
    }

    public static void startYesNoDlg(String info, IAction yesAction) {
        msgdlg.setInfo(info, new Command(Language.yes(), yesAction), new Command("", yesAction), new Command(Language.no(), new IAction() {
            public void perform() {
                if (CCanvas.curScr.menuScroll) {
                    CCanvas.menuScr.startScrollDown();
                }

                CCanvas.currentDialog = null;
            }
        }));
        currentDialog = msgdlg;
    }

    public static void startYesNoDlg(String info, IAction yesAction, IAction noAction) {
        msgdlg.setInfo(info, new Command(Language.yes(), yesAction), new Command("", yesAction), new Command(Language.no(), noAction));
        currentDialog = msgdlg;
    }

    public static void startWaitDlg(String info) {
        msgdlg.setInfo(info, (Command) null, new Command("Cancel", new IAction() {
            public void perform() {
                if (CCanvas.curScr.menuScroll) {
                    CCanvas.menuScr.startScrollDown();
                }

                CCanvas.currentDialog = null;
            }
        }), (Command) null);
        currentDialog = msgdlg;
    }

    public static void startWaitDlgWithoutCancel(String info, int index) {
        msgdlg.setInfo(info, (Command) null, (Command) null, (Command) null);
        currentDialog = msgdlg;
    }

    public static void startWaitDlgWithoutCancel(String info, long timeShow, IAction callback) {
        msgdlg.setInfo(info, timeShow, callback, (Command) null, (Command) null, (Command) null);
        currentDialog = msgdlg;
    }

    public static int random(int a, int b) {
        return a + r.nextInt(b - a);
    }

    public static void endDlg() {
        if (currentDialog != null) {
            currentDialog.close();
        }

        currentDialog = null;
    }

    public void stopGame() {
        this.isRunning = false;
        if (gameScr != null) {
            GameService.gI().leaveBoard();
        }

    }

    public void onPointerDragged(int x, int y, int index) {
        isPointerSelect[index] = false;
        pX[index] = x;
        pY[index] = y;
        if (isPointerMove[index]) {
            this.listPoint.addElement(new Position(x, y));
        } else if (CRes.abs(pX[index] - pxLast[index]) >= 15 || CRes.abs(pY[index] - pyLast[index]) >= 15) {
            isPointerMove[index] = true;
        }

        ++this.curPos;
        if (this.curPos > 3) {
            this.curPos = 0;
        }

        if (currentDialog == null) {
            if (menu != null && menu.showMenu) {
                menu.onPointerDragged(x, y, index);
            } else {
                if (curScr != null) {
                    curScr.onPointerDragged(x, y, index);
                }

            }
        }
    }

    public void onPointerPressed(int xScreen, int yScreen, int index, int button) {
        CRes.out("==> press " + (mSystem.currentTimeMillis() - timeClick));
        isDoubleClick = mSystem.currentTimeMillis() - timeClick < (long) MAX_TIME_CLICK;
        isPointerDown[index] = true;
        isPointerRelease[index] = false;
        isPointerMove[index] = false;
        isPointerSelect[index] = false;
        pxFirst[index] = xScreen;
        pyFirst[index] = yScreen;
        pX[index] = xScreen;
        pY[index] = yScreen;
        pointer = index;
        CCanvas.button = button;

        for (int i = 0; i < arrPopups.size(); ++i) {
            ((Popup) arrPopups.elementAt(i)).onPointerPressed(xScreen, yScreen, index);
        }

        if (menu != null && menu.showMenu) {
            menu.onPointerPressed(xScreen, yScreen, index);
        } else if (currentDialog != null) {
            currentDialog.onPointerPressed(xScreen, yScreen, index);
            if (inputDlg != null) {
                inputDlg.onPointerPressed(xScreen, yScreen, index);
            }

        } else if (pausemenu != null && pausemenu.isShow) {
            pausemenu.onPointerPressed(xScreen, yScreen, index);
        } else {
            if (curScr != null) {
                curScr.onPointerPressed(xScreen, yScreen, index);
            }

        }
    }

    public void onPointerReleased(int x, int y, int index, int button) {
        timeClick = mSystem.currentTimeMillis();
        if (!isPointerMove[index]) {
            isPointerSelect[index] = true;
        }

        isPointerDown[index] = false;
        isPointerRelease[index] = true;
        isPointerMove[index] = false;
        isPointerClick[index] = true;
        pxLast[index] = x;
        pyLast[index] = y;
        pointer = index;
        CCanvas.button = button;
        if (menu != null && menu.showMenu) {
            menu.onPointerReleased(x, y, index);
        } else if (currentDialog != null) {
            currentDialog.onPointerReleased(x, y, index);
        } else {
            for (int i = 0; i < arrPopups.size(); ++i) {
                Popup p = (Popup) arrPopups.elementAt(i);
                if (p instanceof MsgPopup) {
                    ((Popup) arrPopups.elementAt(i)).onPointerReleased(x, y, index);
                }
            }

            if (curScr != null) {
                System.out.printf("%d - %d - %d - %d\n", x, y, index, button);
                curScr.onPointerReleased(x, y, index);
            }

        }
    }

    public void onPointerHolder(int xScreen, int yScreen, int index) {
        if (index != -1) {
            if (mSystem.currentTimeMillis() >= this.timeHold) {
                this.timeHold = mSystem.currentTimeMillis() + 50L;
                isPointerDown[index] = true;
                isPointerRelease[index] = false;
                isPointerMove[index] = false;
                isPointerSelect[index] = false;
                pX[index] = xScreen;
                pY[index] = yScreen;
                pointer = index;
                button = -1;
                if (curScr != null) {
                    curScr.onPointerHold(xScreen, yScreen, index);
                }

            }
        }
    }

    public void onPointerHolder() {
    }

    public void keyHold(int keycode) {
    }

    public void keyHold(char keycode) {
    }

    public void perform(int idAction, Object p) {
    }

    public static void clearKeyHold() {
    }

    public static void resetTrans(mGraphics g) {
    }

    public void onClearMap() {
    }

    public static boolean isTouchAndKey() {
        return false;
    }

    public static boolean isTouchNoOrPC() {
        return !isTouch || isTouchAndKey();
    }

    public static boolean isJ2ME() {
        return GameMidlet.DEVICE == 0;
    }

    public static boolean isPc() {
        return GameMidlet.DEVICE == 4;
    }

    public static boolean isIos() {
        return GameMidlet.DEVICE == 2 || GameMidlet.DEVICE == 6;
    }

    public static boolean isIosStore() {
        return GameMidlet.DEVICE == 6;
    }

    public static boolean isGDX() {
        return isPc() || isIos();
    }

    public static boolean isAndroid() {
        return GameMidlet.DEVICE == 1 || GameMidlet.DEVICE == 5;
    }

    public static boolean isAndroidStore() {
        return GameMidlet.DEVICE == 5;
    }

    public static boolean isStore() {
        return GameMidlet.DEVICE == 5 || GameMidlet.DEVICE == 6;
    }

    public static boolean isDebugging() {
        return GameMidlet.COMPILE == 0;
    }

    public static boolean isTabScreen() {
        return false;
    }

    public static boolean isTabClanScreen() {
        return false;
    }

    public static int getIPdx() {
        return isIos() ? 20 : 0;
    }

    public static String getClassPathConfig(String pathConfig) {
        return isPc() ? "/res/" + pathConfig : "res/" + pathConfig;
    }

    public void backAndroid() {
        if (curScr != null && curScr.right != null) {
            curScr.right.action.perform();
        }

    }

    public static void onClearCCanvas() {
        luckyGame = null;
        shopEquipScr = null;
        shopItemScr = null;
        shopLinhtinh = null;
        listScr = null;
        msgScr = null;
        infoPopup = null;
        prepareScr = null;
        inputDlg = null;
        msgPopup = null;
        clanScreen = null;
        topClanScreen = null;
        serverListScreen = null;
        luckyGifrScreen = null;
        fomulaScreen = null;
        archScreen = null;
        moneyScr = null;
        moneyScrIOS = null;
        inventory = null;
        equipScreen = null;
        gameScr = null;
        pausemenu = null;
        msgdlg = new MsgDlg();
        currentDialog = null;
        arrPopups = new Vector();
        msgPopup = null;
        infoPopup = null;
    }
}
