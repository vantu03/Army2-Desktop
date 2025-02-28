package model;

import CLib.Image;
import CLib.LibSysTem;
import CLib.RMS;
import CLib.mImage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.teamobi.mobiarmy2.GameMidlet;
import com.teamobi.mobiarmy2.MainGame;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import javax.imageio.ImageIO;

public final class CRes {
    public static final int COLOR_FOCUS_MENU = 16767817;
    public static final int LIGHT_BLUE = 8040447;
    public static final int DARK_BLUE = 84643;
    public static final int LIGHT_BLUE_POUP = 2276847;
    public static final int DARK_BLUE_POUP = 1144763;
    public static final int LIGHT_BLUE_COMMAND = 2263535;
    public static final int DARK_BLUE_COMMAND = 1133755;
    public static final float PI = 3.14F;
    public static Random r = new Random();
    private static short[] sin = new short[]{0, 18, 36, 54, 71, 89, 107, 125, 143, 160, 178, 195, 213, 230, 248, 265, 282, 299, 316, 333, 350, 367, 384, 400, 416, 433, 449, 465, 481, 496, 512, 527, 543, 558, 573, 587, 602, 616, 630, 644, 658, 672, 685, 698, 711, 724, 737, 749, 761, 773, 784, 796, 807, 818, 828, 839, 849, 859, 868, 878, 887, 896, 904, 912, 920, 928, 935, 943, 949, 956, 962, 968, 974, 979, 984, 989, 994, 998, 1002, 1005, 1008, 1011, 1014, 1016, 1018, 1020, 1022, 1023, 1023, 1024, 1024};
    private static short[] cos;
    private static int[] tan;
    public static mImage imgMenu;
    public static mImage imgCam;
    public static mImage imgX;
    public static mImage imgEr;
    public static mImage imgTickGreen;
    public static mImage imgTickGreen_0;
    public static mImage newServer;
    public static mImage empty;
    public static FilePack filePak;
    public static final String TEXT_RESET = "\u001b[0m";
    public static final String TEXT_BLACK = "\u001b[30m";
    public static final String TEXT_RED = "\u001b[31m";
    public static final String TEXT_GREEN = "\u001b[32m";
    public static final String TEXT_YELLOW = "\u001b[33m";
    public static final String TEXT_BLUE = "\u001b[34m";
    public static final String TEXT_PURPLE = "\u001b[35m";
    public static final String TEXT_CYAN = "\u001b[36m";
    public static final String TEXT_WHITE = "\u001b[37m";
    static Color color;

    static {
        try {
            imgMenu = mImage.createImage("/iconmenu.png");
            imgCam = mImage.createImage("/iconcam.png");
            imgX = mImage.createImage("/x.png");
            imgEr = mImage.createImage("/er.png");
            imgTickGreen = mImage.createImage("/tick1.png");
            imgTickGreen_0 = mImage.createImage("/tick0.png");
            newServer = mImage.createImage("/gui/new.png");
            empty = mImage.createImage("/gui/nothing.png");
        } catch (Exception var1) {
            var1.printStackTrace();
        }

    }

    public static void init() {
        cos = new short[91];
        tan = new int[91];

        for (int i = 0; i <= 90; ++i) {
            cos[i] = sin[90 - i];
            if (cos[i] == 0) {
                tan[i] = Integer.MAX_VALUE;
            } else {
                tan[i] = (sin[i] << 10) / cos[i];
            }
        }

    }

    public static final int sin(int a) {
        a = fixangle(a);
        if (a >= 0 && a < 90) {
            return sin[a];
        } else if (a >= 90 && a < 180) {
            return sin[180 - a];
        } else {
            return a >= 180 && a < 270 ? -sin[a - 180] : -sin[360 - a];
        }
    }

    public static final int cos(int a) {
        a = fixangle(a);
        if (a >= 0 && a < 90) {
            return cos[a];
        } else if (a >= 90 && a < 180) {
            return -cos[180 - a];
        } else {
            return a >= 180 && a < 270 ? -cos[a - 180] : cos[360 - a];
        }
    }

    public static final int tan(int a) {
        a = fixangle(a);
        if (a >= 0 && a < 90) {
            return tan[a];
        } else if (a >= 90 && a < 180) {
            return -tan[180 - a];
        } else {
            return a >= 180 && a < 270 ? tan[a - 180] : -tan[360 - a];
        }
    }

    public static final int atan(int a) {
        for (int i = 0; i <= 90; ++i) {
            if (tan[i] >= a) {
                return i;
            }
        }

        return 0;
    }

    public static final int angle(int dx, int dy) {
        int angle;
        if (dx != 0) {
            int tan = Math.abs((dy << 10) / dx);
            angle = atan(tan);
            if (dy >= 0 && dx < 0) {
                angle = 180 - angle;
            }

            if (dy < 0 && dx < 0) {
                angle += 180;
            }

            if (dy < 0 && dx >= 0) {
                angle = 360 - angle;
            }
        } else {
            angle = dy > 0 ? 90 : 270;
        }

        return angle;
    }

    public static String formatIntoDDHHMMSS(int secsIn, boolean isSecond) {
        int day = secsIn / 86400;
        int remainder = secsIn % 86400;
        int hours = remainder / 3600;
        remainder %= 3600;
        int minutes = remainder / 60;
        int second = remainder % 60;
        if (second < 0) {
            second = 0;
        }

        if (minutes < 0) {
            minutes = 0;
        }

        if (hours < 0) {
            hours = 0;
        }

        return isSecond ? (day > 0 ? day + "n " + (hours < 10 ? "0" : "") : "") + hours + ":" + (minutes < 10 ? "0" : "") + minutes + ":" + (second < 10 ? "0" : "") + second : (day > 0 ? day + " ngày " : "") + hours + " ngày.";
    }

    public static int myAngle(int dx, int dy) {
        int angle = angle(dx, dy);
        if (angle >= 315) {
            angle = 360 - angle;
        }

        return angle;
    }

    public static final int fixangle(int angle) {
        if (angle >= 360) {
            angle -= 360;
        }

        if (angle < 0) {
            angle += 360;
        }

        return angle;
    }

    public static final int subangle(int a1, int a2) {
        int a = a2 - a1;
        if (a < -180) {
            return a + 360;
        } else {
            return a > 180 ? a - 360 : a;
        }
    }

    public static int random(int a, int b) {
        return a + r.nextInt(b - a);
    }

    public static int random(int a) {
        return r.nextInt() % a;
    }

    public static boolean isLand(int color) {
        return (color & -16777216) != 0;
    }

    public static boolean inRect(int x, int y, int xrect, int yrect, int width, int height) {
        return x >= xrect && x < xrect + width && y >= yrect && y < yrect + height;
    }

    public static boolean isHit(int x, int y, int w, int h, int tX, int tY, int tW, int tH) {
        return x + w >= tX && x <= tX + tW && y + h >= tY && y <= tY + tH;
    }

    public static int sqrt(int a) {
        if (a <= 0) {
            return 0;
        } else {
            int x = (a + 1) / 2;

            int x1;
            do {
                x1 = x;
                x = x / 2 + a / (2 * x);
            } while (Math.abs(x1 - x) > 1);

            return x;
        }
    }

    public static int distance(int x1, int y1, int x2, int y2) {
        return sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    public static final byte[] loadFile(String fileName) {
        try {
            InputStream is = "".getClass().getResourceAsStream(fileName);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();

            int i;
            while ((i = is.read()) != -1) {
                bytes.write(i);
            }

            is.close();
            is = null;
            byte[] b = bytes.toByteArray();
            bytes.close();
            return b;
        } catch (Exception var5) {
            return null;
        }
    }

    public static final byte[] loadRMSData(String name) {
        try {
            return RMS.loadRMS(name);
        } catch (Exception var2) {
            return null;
        }
    }

    public static void saveRMSInt(String file, int x) {
        try {
            RMS.saveRMS(file, new byte[]{(byte) x});
        } catch (Exception var3) {
        }

    }

    public static void saveRMS_String(String filename, String data) {
        try {
            RMS.saveRMS(filename, data.getBytes());
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public static String loadRMS_String(String filename) {
        byte[] data = loadRMSData(filename);
        return data == null ? "" : new String(data);
    }

    public static int loadRMSInt(String file) {
        try {
            byte[] b = loadRMSData(file);
            return b == null ? -1 : b[0];
        } catch (Exception var2) {
            return -1;
        }
    }

    public static final int byte2int(byte b) {
        return b & 255;
    }

    public static final int getShort(int off, byte[] data) {
        return byte2int(data[off]) << 8 | byte2int(data[off + 1]);
    }

    public static void delRMS() {
        try {
            RMS.clearAll();
        } catch (Exception var1) {
        }

    }

    public static Position transTextLimit(Position pos, int limit) {
        pos.x += pos.y;
        if (pos.y == -1 && Math.abs(pos.x) > limit) {
            pos.y *= -1;
        }

        if (pos.y == 1 && pos.x > 5) {
            pos.y *= -1;
        }

        return pos;
    }

    public static String getMoneys(int m) {
        String str = "";
        int mm = m / 1000 + 1;

        for (int i = 0; i < mm; ++i) {
            if (m < 1000) {
                str = m + str;
                break;
            }

            int a = m % 1000;
            if (a == 0) {
                str = ".000" + str;
            } else if (a < 10) {
                str = ".00" + a + str;
            } else if (a < 100) {
                str = ".0" + a + str;
            } else {
                str = "." + a + str;
            }

            m /= 1000;
        }

        return str;
    }

    public static final void out(String text, String aa) {
        if (GameMidlet.COMPILE == 0) {
            System.out.println("\u001b[31mThis text is red!\u001b[0m");
        }

    }

    public static final void out(String text) {
        if (GameMidlet.COMPILE == 0) {
            System.out.println(text);
        }

    }

    public static final void err(String text) {
        if (GameMidlet.COMPILE == 0) {
            System.err.println(text);
        }

    }

    public static boolean CheckDelRMS(String str) {
        return !str.equals("MAIN_user_pass") && !str.equals("MAIN_CONFIG") && !str.equals("MAIN_gameversion") && !str.equals("MAIN_IndexServer") && !str.equals("MAIN_ListServer") && !str.equals("MAIN_LevelScreen") && !str.equals("PLAYER_AUTO_HPMP") && !str.equals("isLowDevice") && !str.equals("PLAYER_AUTO_SKILL");
    }

    public static int abs(int a) {
        return a < 0 ? -a : a;
    }

    public static DataInputStream openFile(String path) {
        return new DataInputStream(LibSysTem.getResourceAsStream(path));
    }

    public static boolean isIosNetwork() {
        return Thread.currentThread().getName() != MainGame.mainThreadName;
    }

    public static String SubStr(String str, int begin, int end) {
        return str.substring(begin, end);
    }

    public static String format(String strformat, String str) {
        return format(strformat, new String[]{str});
    }

    public static String format(String format, String[] args) {
        int argIndex = 0;
        int startOffset = 0;
        int placeholderOffset = format.indexOf("%s");
        if (placeholderOffset == -1) {
            return format;
        } else {
            int capacity = format.length();
            if (args != null) {
                for (int i = 0; i < args.length; ++i) {
                    capacity += args[i].length();
                }
            }

            StringBuffer sb;
            for (sb = new StringBuffer(capacity); placeholderOffset != -1; placeholderOffset = format.indexOf("%s", startOffset)) {
                sb.append(format.substring(startOffset, placeholderOffset));
                if (args != null && argIndex < args.length) {
                    sb.append(args[argIndex]);
                }

                ++argIndex;
                startOffset = placeholderOffset + 2;
            }

            if (startOffset < format.length()) {
                sb.append(format.substring(startOffset));
            }

            return sb.toString();
        }
    }

    public static String fixString(String chat) {
        return chat;
    }

    public static void saveRMSShort(String name, short[] value) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(bos);

            for (int i = 0; i < value.length; ++i) {
                dos.writeShort(value[i]);
            }

            RMS.saveRMS(name, bos.toByteArray());
            dos.close();
        } catch (Exception var5) {
        }

    }

    public static byte[] loadRMS(String filename) {
        return RMS.loadRMS(filename);
    }

    public static void delRMS(String fileName) {
        RMS.clearRMS(fileName);
    }

    public static void delAllRms() {
        RMS.clearAll();
    }

    public static void onSaveToFile(String path, String content) {
    }

    public static void onSaveToFile(Image image, String nameSave, boolean isAA) {
    }

    public static void onSaveToFile(Image image, String name, String path) {
    }

    public static void onSaveToFile(Image image, String nameSave) {
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

    public static float Lerp(float a, float b, float timeLerp) {
        return a + timeLerp * (b - a);
    }

    public static float Clamp01(float value) {
        if (value < 0.0F) {
            value = 0.0F;
        } else if (value > 1.0F) {
            value = 1.0F;
        }

        return value;
    }

    public static int Clamp(int value, int min, int max) {
        if (value < min) {
            value = min;
        } else if (value > max) {
            value = max;
        }

        return value;
    }

    public static byte[] toByteArray(BufferedImage bi, String format) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, format, baos);
        byte[] bytes = baos.toByteArray();
        return bytes;
    }

    public static BufferedImage toBufferedImage(byte[] bytes) throws IOException {
        InputStream is = new ByteArrayInputStream(bytes);
        BufferedImage bi = ImageIO.read(is);
        return bi;
    }

    public static byte[] screenShot() {
        return ScreenUtils.getFrameBufferPixels(false);
    }

    public static void ONCal() {
        byte[] pixels = ScreenUtils.getFrameBufferPixels(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), true);

        for (int i = 4; i <= pixels.length; i += 4) {
            pixels[i - 1] = -1;
        }

        color = new Color();
        color.set(0.0F, 0.0F, 0.0F, 0.0F);
        Pixmap pixmap = new Pixmap(Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), Pixmap.Format.RGBA8888);
        BufferUtils.copy((byte[]) pixels, 0, pixmap.getPixels(), pixels.length);
        int width = pixmap.getWidth();
        int height = pixmap.getHeight();
        out("====> external = " + Gdx.files.getExternalStoragePath());
        PixmapIO.writePNG(Gdx.files.external("ScreenShot/mypixma3p.png"), pixmap);
        pixmap.dispose();
    }

    public static boolean isNullOrEmpty(String text) {
        boolean check = false;

        try {
            check = text == null || text.equals("");
        } catch (Exception var3) {
            check = false;
        }

        return check;
    }
}
