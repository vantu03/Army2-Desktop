package model;

import CLib.mImage;

public class ImageIcon {
   public int id;
   public mImage img;

   public ImageIcon(int id, mImage img) {
      this.id = id;
      this.img = img;
   }

   public ImageIcon(int id, byte[] dataRaw, int len) {
      this.img = mImage.createImage((byte[])dataRaw, 0, len, (String)"");
   }
}
