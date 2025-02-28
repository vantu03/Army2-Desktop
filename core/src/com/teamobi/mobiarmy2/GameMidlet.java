package com.teamobi.mobiarmy2;

import CLib.RMS;
import CLib.mGraphics;
import CLib.mSystem;
import coreLG.CCanvas;
import coreLG.CONFIG;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import model.CRes;
import model.Font;
import model.IAction2;
import model.Language;
import model.PlayerInfo;
import network.GameLogicHandler;
import network.GameService;
import network.Message;
import network.MessageHandler;
import network.Session_ME;
import screen.ServerListScreen;

public class GameMidlet extends MIDlet implements IActionListener {
    public static GameMidlet instance = new GameMidlet();
    public static CCanvas gameCanvas;
    public static String version = "2.4.1";
    public static short versionByte = 241;
    public static byte versioncode = 11;
    public static byte server = -2;
    public static String serverName;
    public static int timePingPaint;
    public static int pingCount;
    public static boolean ping;
    public static short versionServer = 3;
    public static boolean isStartGame;
    public static final byte NONE = 0;
    public static final byte NOKIA_STORE = 1;
    public static final byte GOOGLE_STORE = 2;
    public static final byte IOS_STORE = 3;
    public static final byte DEVICE_TYPE_JAVA = 0;
    public static final byte DEVICE_TYPE_ANDROID = 1;
    public static final byte DEVICE_TYPE_IOS = 2;
    public static final byte DEVICE_TYPE_WINPHONE = 3;
    public static final byte DEVICE_TYPE_PC = 4;
    public static final byte DEVICE_TYPE_ANDROID_STORE = 5;
    public static final byte DEVICE_TYPE_IOS_STORE = 6;
    public static final byte DEVICE_TYPE_DEV = 7;
    public static byte DEVICE = 4;
    public static final byte DEVELOPING = 0;
    public static final byte BUILD = 1;
    public static final byte OTHER = 2;
    public static byte COMPILE = 1;
    public static boolean lowGraphic = false;
    public static byte currentIAPStore = 0;
    public static byte langServer = 0;
    public static byte ZOOM_IOS = 1;
    public static byte PROVIDER = 0;
    public static final byte BIG_PROVIDER = 0;
    public static String IP = "192.168.1.88";
    public static int PORT = 19152;
    public static PlayerInfo myInfo;
    public static String AGENT;
    public static byte filePackVersion;
    public static boolean[] isVip = new boolean[10];
    public static boolean isTeamClient = true;
    public static String linkGetHost = "https://sv.pro.vn/server.txt";
    public static String linkReg = "http://my.teamobi.com/app/view/register.php";
    public static String latitude = "";
    public static String longitude = "";


    public void initGame() {
        gameCanvas = new CCanvas();
        this.initGame2();
    }

    private void initGame2() {
        InputStream in = this.getClass().getResourceAsStream("/provider.txt");

        String str;
        try {
            byte[] array = new byte[in.available()];
            in.read(array);
            str = new String(array, "UTF-8");
            PROVIDER = Byte.parseByte(str);
        } catch (Exception var8) {
        }

        str = GameLogicHandler.loadIP();
        if (str != null && str.length() > 0) {
            try {
                int p = str.indexOf(":");
                String IP = str.substring(0, p);
                String port = str.substring(p + 1);
                GameMidlet.IP = IP;
                PORT = Integer.parseInt(port);
            } catch (Exception var7) {
                System.err.println("===> error midlet connects " + var7);
            }
        }

        gameCanvas.start();
        MessageHandler.gI().setGameLogicHandler(GameLogicHandler.gI());
        Session_ME.gI().setHandler(MessageHandler.gI());
        GameService.gI().setSession(Session_ME.gI());
        setcurrentIAPStore();
    }

    public static void doUpdateServer() {
        CCanvas.startWaitDlg(Language.pleaseWait());
        connectHTTP(linkGetHost, new IAction2() {
            public void perform(Object object) {
                String dataReceive = "";
                if (object != null) {
                    dataReceive = (String) object;
                }
                GameMidlet.getServerList(dataReceive);
                GameMidlet.saveIP();
                CCanvas.startOKDlg(Language.updateServer());
            }
        });
    }

    public static void saveIP() {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bo);

        try {
            int leng = ServerListScreen.nameServer.length;

            int i;
            for (i = 0; i < ServerListScreen.nameServer.length; ++i) {
                if (versionByte < 240) {
                    if (ServerListScreen.nameServer[i].equals("Trái Đất")) {
                        --leng;
                    }

                    if (ServerListScreen.nameServer[i].equals("Sao Hỏa")) {
                        --leng;
                    }
                }

                if (ServerListScreen.nameServer[i].equals("LOCAL")) {
                    --leng;
                }
            }

            dos.writeByte(leng);

            for (i = 0; i < leng; ++i) {
                dos.writeUTF(ServerListScreen.nameServer[i]);
                dos.writeUTF(ServerListScreen.address[i]);
                dos.writeShort(ServerListScreen.port[i]);
            }

            try {
                RMS.saveRMS("ipArmy2", bo.toByteArray());
            } catch (Exception var4) {
                var4.printStackTrace();
            }

            dos.close();
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }

    public static void loadIP() {
        byte[] bData = CRes.loadRMSData("ipArmy2");
        if (bData == null) {
            doUpdateServer();
        } else {
            CRes.out(" 1 ==================> loadIP");
            ByteArrayInputStream bi = new ByteArrayInputStream(bData);
            DataInputStream dis = new DataInputStream(bi);
            if (dis != null) {
                try {
                    byte len = dis.readByte();
                    ServerListScreen.nameServer = new String[len];
                    ServerListScreen.address = new String[len];
                    ServerListScreen.port = new short[len];
                    if (versionByte < 240) {
                        ServerListScreen.nameServer = new String[len + 1];
                        ServerListScreen.address = new String[len + 1];
                        ServerListScreen.port = new short[len + 1];
                    }

                    if (versionByte < 240) {
                        ServerListScreen.nameServer[len] = "Mặt Trời";
                        ServerListScreen.address[len] = "27.0.12.164";
                        ServerListScreen.port[len] = 19149;
                    }

                    for (int i = 0; i < len; ++i) {
                        ServerListScreen.nameServer[i] = dis.readUTF();
                        ServerListScreen.address[i] = dis.readUTF();
                        ServerListScreen.port[i] = dis.readShort();
                    }

                    dis.close();
                } catch (IOException var5) {
                    var5.printStackTrace();
                }

            }
        }
    }

    public static void getServerList(String str) {
        String[] temp = CRes.split(str, ",");
        ServerListScreen.nameServer = new String[temp.length];
        ServerListScreen.address = new String[temp.length];
        ServerListScreen.port = new short[temp.length];

        for (int i = 0; i < temp.length; ++i) {
            String tempRaw = temp[i].trim();
            String[] sub = CRes.split(tempRaw, ":");
            ServerListScreen.nameServer[i] = sub[0];
            ServerListScreen.address[i] = sub[1];
            ServerListScreen.port[i] = Short.parseShort(sub[2].trim());
        }

    }

    public static void setZOOM_IOS() {
        ZOOM_IOS = (byte) mGraphics.zoomLevel;
        int i = 1;
        if (CCanvas.isPc()) {
            i = 1;
        }

        if (mGraphics.zoomLevel > i) {
            ZOOM_IOS = 2;
        }

    }

    public static void setcurrentIAPStore() {
        if (DEVICE == 5) {
            currentIAPStore = 2;
        } else if (DEVICE == 6) {
            currentIAPStore = 3;
        } else {
            currentIAPStore = 0;
        }

    }

    protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
    }

    public void destroy() {
        try {
            instance.destroyApp(true);
        } catch (MIDletStateChangeException var2) {
            var2.printStackTrace();
        }

    }

    protected void pauseApp() {
    }

    public void startApp() throws MIDletStateChangeException {
        if (!isStartGame) {
            this.initGame();
            gameCanvas.displayMe(instance);
            isStartGame = true;
        }

    }

    public static void exit() {
        MotherCanvas.bRun = false;
        System.gc();
        notifyDestroyed();
    }

    public void perform(int idAction, Object p) {
    }

    public static void openUrl(String url) {
        mSystem.openUrl(url);
    }

    public static String loginPlus() {
        return "";
    }

    public static String connectHTTP(String url) {
        return mSystem.connectHTTP(url);
    }

    public static void connectHTTP(String url, IAction2 action2) {
        mSystem.connectHTTP(url, action2);
    }

    public void CheckPerGPS() {
        this.getLocation();
    }

    public void getLocation() {
        longitude = "";
        latitude = "";
    }

    public static void handleMessage(Message msg) {
        try {
            String var1 = msg.reader().readUTF();
        } catch (Exception var2) {
        }

    }

    public static void handleAllMessage() {
    }

    public static void serverInformation(Font paint, mGraphics g) {
        paint.drawString(g, version, CCanvas.width - 2 - paint.getWidth(version), 2 + paint.getHeight(), 0, false);
        if (CCanvas.isDebugging()) {
            paint.drawString(g, String.valueOf(timePingPaint), CCanvas.width - 2 - paint.getWidth(version), 2 + paint.getHeight() * 2, 0, false);
        }

    }
}
