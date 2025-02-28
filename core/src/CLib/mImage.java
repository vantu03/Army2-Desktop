package CLib;

import java.io.DataInputStream;
import model.IAction2;
import network.Command;

public class mImage {
   public Image image;
   private static Command cmd;

   public mImage() {
   }

   public mImage(Image image) {
      this.image = image;
   }

   public static String getLink(String str) {
      return str;
   }

   public static String replaceImg(String str) {
      String tam = str.replace(".img", ".png");
      return tam;
   }

   public static mImage createImage(String url) {
      url = replaceImg(url);
      mImage img = new mImage();

      try {
         img.image = Image.createImage(url);
      } catch (Exception var3) {
         var3.printStackTrace();
      }

      return img.image == null ? null : img;
   }

   public static void createImage(String url, IAction2 iAction2) {
      url = replaceImg(url);
      new mImage();

      try {
         Image.createImage(url, iAction2);
      } catch (Exception var4) {
         var4.printStackTrace();
      }

   }

   public static mImage createImageAll(String url) {
      mImage img = new mImage();

      try {
         img.image = Image.createImage(url);
      } catch (Exception var3) {
      }

      return img.image == null ? null : img;
   }

   public static DataInputStream openFile(String path) {
      DataInputStream file = null;
      file = new DataInputStream(LibSysTem.getResourceAsStream(path));
      return file;
   }

   public static mImage createImage(int w, int h) {
      mImage img = new mImage();
      img.image = Image.createImage(w, h);
      return img;
   }

   public static mImage createImageTextureRegion() {
      mImage img = new mImage();
      img.image = Image.createImageTextureRegion();
      return img;
   }

   public static mImage createImage(byte[] encodedData, int offset, int len) {
      mImage img = new mImage();
      img.image = Image.createImage(encodedData, offset, len);
      return img;
   }

   public static mImage createImage(byte[] encodedData, int offset, int len, IAction2 callback) {
      mImage img = new mImage();
      img.image = Image.createImage(encodedData, offset, len, callback);
      return img;
   }

   public static mImage createImage(int[] encodedData, int offset, int len, IAction2 callback) {
      mImage img = new mImage();
      img.image = Image.createImage(encodedData, offset, len, callback);
      return img;
   }

   public static mImage createImage(byte[] encodedData, int offset, int len, String path) {
      mImage img = new mImage();
      img.image = Image.createImage(encodedData, offset, len, path);
      return img;
   }

   public static mImage createImage(byte[] encodedData, int offset, int len, boolean isFont) {
      mImage img = new mImage();
      img.image = Image.createImage(encodedData, offset, len, isFont);
      return img;
   }

   public static mImage createImage(int[] encodedData, int w, int h) {
      mImage img = new mImage();
      img.image = Image.createImage(encodedData, w, h);
      return img;
   }

   public static mImage createImageNotRunable(int[] encodedData, int w, int h) {
      mImage img = new mImage();
      img.image = Image.createImageNotRunable(encodedData, w, h);
      return img;
   }

   public TemGraphics getGraphics() {
      TemGraphics tem = new TemGraphics();
      return tem;
   }

   public static int getImageWidth(Image image) {
      return image == null ? 0 : image._getWidth();
   }

   public static int getImageHeight(Image image) {
      return image == null ? 0 : image._getHeight();
   }

   public void getRGB(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height) {
      this.image.getRGB(rgbData, offset, scanlength, x, y, width, height);
   }

   public static mImage createImage(byte[] encodedData, int offset, int len, String path, String name) {
      mImage img = new mImage();
      img.image = Image.createImage(encodedData, offset, len, path, name);
      return img;
   }
}
