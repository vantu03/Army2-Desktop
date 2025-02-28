package lib;

import java.io.InputStream;

public class MyStream {
   public static InputStream readFile(String path) {
      return "".getClass().getResourceAsStream(path);
   }
}
