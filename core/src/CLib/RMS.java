package CLib;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.teamobi.mobiarmy2.GameMidlet;
import model.CRes;

public class RMS {
   public static final String path = "rms/";

   public static void saveRMSInt(String file, int x) {
      try {
         saveRMS(file, new byte[]{(byte)x});
      } catch (Exception var3) {
      }

   }

   public static void saveRMS(String filename, byte[] data) throws Exception {
      FileHandle file = null;
      if (GameMidlet.DEVICE == 4) {
         file = Gdx.files.local("rms/" + filename);
      } else {
         file = Gdx.files.external("rms/" + filename);
      }

      CRes.out("1============> Vi tri save filename " + filename + "  !");
      CRes.out("1============> Vi tri save " + Gdx.files.isLocalStorageAvailable() + "  !");
      CRes.out("2============> Vi tri save " + Gdx.files.isExternalStorageAvailable() + "  !");
      CRes.out("3============> Vi tri save " + Gdx.files.getExternalStoragePath() + "  !");
      CRes.out("4============> Vi tri save " + Gdx.files.getLocalStoragePath() + "  !");
      if (file != null) {
         file.writeBytes(data, false);
      } else {
         CRes.out("============> RMS be not found " + filename + "  !");
      }

   }

   public static byte[] loadRMS(String filename) {
      byte[][] data = new byte[1][];

      try {
         FileHandle file = null;
         if (GameMidlet.DEVICE == 4) {
            file = Gdx.files.local("rms/" + filename);
            if (file != null) {
               data[0] = file.readBytes();
            }
         } else if (GameMidlet.DEVICE == 1) {
            if (Gdx.files.isExternalStorageAvailable()) {
               file = Gdx.files.external("rms/" + filename);
            } else if (Gdx.files.isLocalStorageAvailable()) {
               file = Gdx.files.local("rms/" + filename);
            } else {
               file = Gdx.files.internal("rms/" + filename);
            }

            if (file != null) {
               data[0] = file.readBytes();
            }
         } else {
            if (Gdx.files.isExternalStorageAvailable()) {
               file = Gdx.files.external("rms/" + filename);
            } else if (Gdx.files.isLocalStorageAvailable()) {
               file = Gdx.files.local("rms/" + filename);
            } else {
               file = Gdx.files.internal("rms/" + filename);
            }

            if (file != null) {
               data[0] = file.readBytes();
               CRes.out("1 ====> load FIle Name = " + filename);
            }
         }
      } catch (Exception var3) {
         return null;
      }

      return data == null ? null : data[0];
   }

   public static int loadRMSInt(String file) {
      byte[] b = loadRMS(file);
      return b == null ? -1 : b[0];
   }

   public static void saveRMSString(String filename, String s) {
      try {
         saveRMS(filename, s.getBytes("UTF-8"));
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   public static String loadRMSString(String filename) {
      byte[] data = loadRMS(filename);
      if (data == null) {
         return null;
      } else {
         try {
            String s = new String(data, "UTF-8");
            return s;
         } catch (Exception var3) {
            return new String(data);
         }
      }
   }

   public static void clearRMS(String fileName) {
      try {
         FileHandle external;
         if (Gdx.files.isLocalStorageAvailable()) {
            external = Gdx.files.getFileHandle(fileName, Files.FileType.Local);
            external.delete();
         }

         if (Gdx.files.isExternalStorageAvailable()) {
            external = Gdx.files.getFileHandle(fileName, Files.FileType.External);
            external.delete();
         }
      } catch (Exception var2) {
         CRes.out("RMS clear " + fileName + " error is " + var2.getCause());
      }

   }

   public static String getPathRMS() {
      return "rms/" + Gdx.files.getLocalStoragePath();
   }

   public static void clearAll() {
      try {
         FileHandle external;
         if (Gdx.files.isLocalStorageAvailable()) {
            external = Gdx.files.getFileHandle("rms/", Files.FileType.Local);
            external.emptyDirectory();
         }

         if (Gdx.files.isExternalStorageAvailable()) {
            external = Gdx.files.getFileHandle("rms/", Files.FileType.External);
            external.emptyDirectory();
         }
      } catch (Exception var1) {
      }

   }
}
