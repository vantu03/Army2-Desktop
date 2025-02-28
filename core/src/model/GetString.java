package model;

import java.io.InputStream;

public class GetString {
   public static byte[] array;
   public static String strdata;

   public GetString() {
      InputStream in = this.getClass().getResourceAsStream("/agent.txt");

      try {
         array = new byte[in.available()];
         in.read(array);
         strdata = new String(array, "UTF-8");
      } catch (Exception var3) {
         strdata = "";
      }

   }
}
