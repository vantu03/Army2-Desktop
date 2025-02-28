package CLib;

import com.badlogic.gdx.Gdx;
import java.io.InputStream;

public class LibSysTem {
   public static String res = "res";
   public static String font = "FontSys/x";

   public static InputStream getResourceAsStream(String path) {
      InputStream in = null;
      in = Gdx.files.internal(res + path).read();
      if (in == null) {
         throw new IllegalArgumentException("InputStream cannot found path= " + path);
      } else {
         return in;
      }
   }

   public static void openWeb(String links) {
      Gdx.net.openURI(links);
   }
}
