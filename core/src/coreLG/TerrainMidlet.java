package coreLG;

import CLib.RMS;
import CLib.mSystem;
import com.badlogic.gdx.Gdx;
import com.teamobi.mobiarmy2.TemCanvas;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;

import model.CRes;
import model.IAction;
import model.PlayerInfo;
import network.GameLogicHandler;
import network.GameService;
import network.MessageHandler;
import network.Session_ME;
import screen.ServerListScreen;

public class TerrainMidlet {
    public static final String version = "2.4.1";
    public static byte PROVIDER;
    public static final byte BIG_PROVIDER = 0;
    public static TemCanvas temCanvas;
    public static TerrainMidlet instance;
    int a = 0;
    public static int PORT = 19152;
    public static String IP = "192.168.1.88";
    public static PlayerInfo myInfo;
    public static String AGENT;
    public static byte filePackVersion;
    public static boolean[] isVip = new boolean[10];
    public static boolean isTeamClient = true;
    public static String linkGetHost = "http://gmb.teamobi.com/srvip/army2list.txt";

    public TerrainMidlet() {
        if (temCanvas == null) {
            CRes.init();
            temCanvas = new TemCanvas();
            temCanvas.gamecanvas = new CCanvas();
            temCanvas.start();
        }

        temCanvas.gamecanvas.isRunning = true;
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
                TerrainMidlet.IP = IP;
                PORT = Integer.parseInt(port);
            } catch (Exception var7) {
                System.err.println("===> error midlet connects " + var7);
            }
        }

        MessageHandler.gI().setGameLogicHandler(GameLogicHandler.gI());
        Session_ME.gI().setHandler(MessageHandler.gI());
        GameService.gI().setSession(Session_ME.gI());
    }

    public static void saveIP() {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bo);

        try {
            dos.writeByte(ServerListScreen.nameServer.length);

            for (int i = 0; i < ServerListScreen.nameServer.length; ++i) {
                dos.writeUTF(ServerListScreen.nameServer[i]);
                dos.writeUTF(ServerListScreen.address[i]);
                dos.writeShort(ServerListScreen.port[i]);
            }

            try {
                RMS.saveRMS("ipArmy2", bo.toByteArray());
            } catch (Exception var3) {
                var3.printStackTrace();
            }

            dos.close();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public static String[] split(String _text, String _searchStr) {
        int count = 0;
        int pos = 0;
        int searchStringLength = _searchStr.length();

        for (int aa = _text.indexOf(_searchStr, pos); aa != -1; ++count) {
            pos = aa + searchStringLength;
            aa = _text.indexOf(_searchStr, pos);
        }

        String[] sb = new String[count + 1];
        int searchStringPos = _text.indexOf(_searchStr);
        int startPos = 0;

        int index;
        for (index = 0; searchStringPos != -1; ++index) {
            sb[index] = _text.substring(startPos, searchStringPos);
            startPos = searchStringPos + searchStringLength;
            searchStringPos = _text.indexOf(_searchStr, startPos);
        }

        sb[index] = _text.substring(startPos, _text.length());
        return sb;
    }

    public static String connectHTTP(String url) {
        return mSystem.connectHTTP(url);
    }

    protected void destroyApp(boolean arg0) {
        if (temCanvas != null) {
            temCanvas.gamecanvas.stopGame();
            temCanvas = null;
        }

        this.notifyDestroyed();
    }

    public void showMyCanvas() {
    }

    protected void pauseApp() {
        this.notifyPaused();
    }

    protected void startApp() {
        this.showMyCanvas();
        instance = this;
    }

    public static void exit() {
        instance.destroyApp(false);
    }

    public static void sendSMS(String data, String to, IAction successAction, IAction failAction) {
    }

    public static void vibrate(int s) {
        try {
            Gdx.input.vibrate(CRes.abs(s * 10));
        } catch (SecurityException var2) {
            var2.printStackTrace();
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public void notifyDestroyed() {
        Gdx.app.exit();
    }

    public void notifyPaused() {
    }
}
