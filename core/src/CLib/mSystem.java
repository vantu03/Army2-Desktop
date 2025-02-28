package CLib;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.teamobi.mobiarmy2.TemMidlet;
import coreLG.CCanvas;
import java.util.Calendar;
import java.util.Date;
import model.CRes;
import model.IAction2;

public class mSystem {
   public static mImage imgCircle_30;
   public static mImage imgCircle_20;
   public static mImage imgCircle_0;
   public static mImage imgCircle_45;
   public static float deltaTime;
   public static float fixedDeltaTime = 0.02F;
   public static String ww = "";
   public static boolean isMaHoa = false;
   public static boolean isIP_TrucTiep = false;
   public static boolean isIP_GDX = true;
   public static boolean isj2me = false;
   public static int dyCharStep = 0;
   public static boolean isImgLocal = true;
   public static final byte INDEX_SV_GLOBAL = 2;
   public static boolean isIphone = true;
   public static int ID_REGION;
   public static String[][] listServer = new String[][]{{"name_Server", "com.teamobi.army2"}, {"name_Server", "com.teamobi.army2"}, {"name_Server", "com.teamobi.army2"}, {"name_Server", "com.teamobi.army2"}, {"name_Server", "com.teamobi.army2"}, {"name_Server", "com.teamobi.army2"}};
   public static String[][] listServer_VN = new String[][]{{"name_Server", "com.teamobi.army2"}, {"name_Server", "com.teamobi.army2"}, {"Global Server", "com.teamobi.army2"}, {"name_Server", "com.teamobi.army2"}, {"name_Server", "com.teamobi.army2"}, {"name_Server", "com.teamobi.army2"}};
   public static String[][] listServer_In_Do = new String[][]{{"Indo Naga", "com.teamobi.army2"}, {"Indo Garuda(new)", "com.teamobi.army2"}, {"Knight Age (ENG)", "com.teamobi.army2"}};
   public static String[][] listServer_Usa = new String[][]{{"Fire Dragon (ENG)", "46.137.254.172"}, {"Sky Dragon (SPN)", "54.254.156.202"}, {"Knight Age (ENG)", "com.teamobi.army2"}};
   public static boolean isOnConnectFail;
   public static boolean isOnConnectOK;
   public static boolean isOnDisconnect;
   public static boolean isOnLoginFail;
   public static String reasonFail;

   public static long currentTimeMillis() {
      return System.currentTimeMillis();
   }

   public static float deltaTime() {
      return Gdx.graphics.getDeltaTime();
   }

   public static void my_Gc() {
      System.gc();
   }

   public static int[][] new_M_Int(int value1, int value2) {
      int[][] m;
      if (TemMidlet.DIVICE == 2) {
         m = new int[value1][];

         for(int i = 0; i < m.length; ++i) {
            m[i] = new int[value2];
         }
      } else {
         m = new int[value1][value2];
      }

      return m;
   }

   public static String[][] new_M_String(int value1, int value2) {
      String[][] m;
      if (TemMidlet.DIVICE == 2) {
         m = new String[value1][];

         for(int i = 0; i < m.length; ++i) {
            m[i] = new String[value2];
         }
      } else {
         m = new String[value1][value2];
      }

      return m;
   }

   public static byte[][] new_M_Byte(int value1, int value2) {
      byte[][] m;
      if (TemMidlet.DIVICE == 2) {
         m = new byte[value1][];

         for(int i = 0; i < m.length; ++i) {
            m[i] = new byte[value2];
         }
      } else {
         m = new byte[value1][value2];
      }

      return m;
   }

   public static byte[][][] new_M_Byte(int value1, int value2, int value3) {
      byte[][][] m;
      if (TemMidlet.DIVICE == 2) {
         m = new byte[value1][][];

         for(int i = 0; i < m.length; ++i) {
            m[i] = new_M_Byte(value2, value3);
         }
      } else {
         m = new byte[value1][value2][value3];
      }

      return m;
   }

   public static Color setColor(int rgb) {
      float R = (float)(rgb >> 16 & 255);
      float G = (float)(rgb >> 8 & 255);
      float B = (float)(rgb & 255);
      float b = B / 256.0F;
      float g = G / 256.0F;
      float r = R / 256.0F;
      return new Color(r, g, b, 1.0F);
   }

   public static void openUrl(String url) {
      try {
         Gdx.net.openURI(url);
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   public static Pixmap createPixmap(String url) {
      try {
         url = "/x" + mGraphics.zoomLevel + url;
         Pixmap p = new Pixmap(Gdx.files.internal(LibSysTem.res + url));
         return p;
      } catch (Exception var2) {
         System.out.println("KHONG LOAD DC PIXMAP:" + url);
         return null;
      }
   }

   public static Pixmap createPixmap(int w, int h) {
      w *= mGraphics.zoomLevel;
      h *= mGraphics.zoomLevel;
      return new Pixmap(w, h, Pixmap.Format.RGBA4444);
   }

   public static String connectHTTP(String url) {
      Net.HttpRequest httpGet = new Net.HttpRequest("GET");
      httpGet.setUrl(url);
      Gdx.net.sendHttpRequest(httpGet, new Net.HttpResponseListener() {
         public void handleHttpResponse(Net.HttpResponse httpResponse) {
            mSystem.ww = httpResponse.getResultAsString();
            CRes.out("Conennect http =======> " + mSystem.ww);
         }

         public void failed(Throwable t) {
            CRes.out("Conennect http Fail =======> " + mSystem.ww);
         }

         public void cancelled() {
            CRes.out("Conennect cancle =======> " + mSystem.ww);
         }
      });
      return ww;
   }

   public static void connectHTTP(final String url, final IAction2 iAction2) {
      Net.HttpRequest httpGet = new Net.HttpRequest("GET");
      httpGet.setUrl(url);
      boolean status = false;
      Gdx.net.sendHttpRequest(httpGet, new Net.HttpResponseListener() {
         public void handleHttpResponse(Net.HttpResponse httpResponse) {
            mSystem.ww = httpResponse.getResultAsString();
            iAction2.perform(mSystem.ww);
         }

         public void failed(Throwable t) {
            CCanvas.startOKDlg("Error connect to " + url);
         }

         public void cancelled() {
            CRes.out("========================> camcle!");
         }
      });
   }

   public static String[] split(String original, String separator) {
      mVector nodes = new mVector();

      for(int index = original.indexOf(separator); index >= 0; index = original.indexOf(separator)) {
         nodes.addElement(original.substring(0, index));
         original = original.substring(index + separator.length());
      }

      nodes.addElement(original);
      String[] result = new String[nodes.size()];
      if (nodes.size() > 0) {
         for(int loop = 0; loop < nodes.size(); ++loop) {
            result[loop] = (String)nodes.elementAt(loop);
         }
      }

      return result;
   }

   public static void setClientType(byte type, boolean isTrucTiep) {
      TemMidlet.DIVICE = type;
      isIP_TrucTiep = isTrucTiep;
   }

   public static String getPackageName() {
      return "";
   }

   public static int getHour() {
      Calendar cl = Calendar.getInstance();
      return cl.get(11);
   }

   public static int getMinute() {
      Calendar cl = Calendar.getInstance();
      return cl.get(12);
   }

   public static String getMoth() {
      Calendar calendar = Calendar.getInstance();
      Date date = calendar.getTime();
      return String.valueOf(date.getMonth());
   }

   public static String getDayOfMonth() {
      Calendar calendar = Calendar.getInstance();
      Date date = calendar.getTime();
      return String.valueOf(date.getDate());
   }

   public static String getLong() {
      return "";
   }

   public static String getLat() {
      return "";
   }

   public static boolean isHideNaptien() {
      return false;
   }

   public static boolean isIpAppstore() {
      return true;
   }

   public static void doChangeMenuNapapple() {
   }

   public static String getImei() {
      String out = "";

      try {
         out = System.getProperty("com.imei");
         if (out == null || out.equals("null") || out.equals("")) {
            out = System.getProperty("phone.imei");
         }

         if (out == null || out.equals("null") || out.equals("")) {
            out = System.getProperty("com.nokia.IMEI");
         }

         if (out == null || out.equals("null") || out.equals("")) {
            out = System.getProperty("com.nokia.mid.imei");
         }

         if (out == null || out.equals("null") || out.equals("")) {
            out = System.getProperty("com.sonyericsson.imei");
         }

         if (out == null || out.equals("null") || out.equals("")) {
            out = System.getProperty("IMEI");
         }

         if (out == null || out.equals("null") || out.equals("")) {
            out = System.getProperty("com.motorola.IMEI");
         }

         if (out == null || out.equals("null") || out.equals("")) {
            out = System.getProperty("com.samsung.imei");
         }

         if (out == null || out.equals("null") || out.equals("")) {
            out = System.getProperty("com.siemens.imei");
         }

         if (out == null || out.equals("null") || out.equals("")) {
            out = System.getProperty("imei");
         }
      } catch (Exception var2) {
         return out == null ? "" : out;
      }

      return out == null ? "" : out;
   }

   public static String getModel() {
      return "";
   }

   public static int getRam() {
      return 0;
   }

   public static void onConnectFail() {
      isOnConnectFail = true;
   }

   public static void onConnectOK() {
      isOnConnectOK = true;
   }

   public static void onDisconnect() {
      isOnDisconnect = true;
   }

   public static void onLoginFail(String reason) {
      isOnLoginFail = true;
      reasonFail = reason;
   }
}
