package model;

import CLib.mGraphics;
import CLib.mImage;
import Equipment.Equip;
import coreLG.CCanvas;
import coreLG.TerrainMidlet;

public class Fomula {
   public String[] numMaterial;
   public mImage[] imgMaterial;
   public int[] idImage;
   public Equip equipRequire;
   public boolean isHave;
   public int levelRequire;
   public Equip e;
   public Equip eRequire;
   public String[] materialName;
   public String[] ability;
   public boolean finish;
   public int ID;
   public int h1;
   public int h2;

   public mImage getImageMaterial(int id) {
      for(int i = 0; i < this.idImage.length; ++i) {
         if (this.idImage[i] == id) {
            return this.imgMaterial[i];
         }
      }

      return null;
   }

   public void paint(mGraphics g) {
      Font.bigFont.drawString(g, Language.congthucchetao(), CCanvas.width / 2, 5, 3);
      if (this.e != null) {
         Font.normalFont.drawString(g, this.e.name, CCanvas.width / 2, 30, 3);
         this.e.drawIcon(g, CCanvas.width / 2 + Font.normalFont.getWidth(this.e.name) / 2 + 10, 30, false);
      }

      int myLevel;
      for(myLevel = 0; myLevel < this.ability.length; ++myLevel) {
         Font.borderFont.drawString(g, this.ability[myLevel], CCanvas.width / 2, 50 + myLevel * 18, 3);
      }

      myLevel = TerrainMidlet.myInfo.level2;
      Font.borderFont.drawString(g, Language.levelRequire() + ": " + myLevel + "/" + this.levelRequire, CCanvas.width / 2, this.h1 + 53, 3);
      this.equipRequire.drawIcon(g, CCanvas.width / 2 - 70 - 8, this.h1 + 78 - 8, false);
      Font.normalFont.drawString(g, "1x " + this.equipRequire.name, CCanvas.width / 2 - 55, this.h1 + 71, 0);
      Font.normalFont.drawString(g, this.isHave ? "1/1" : "0/1", CCanvas.width / 2 + 70, this.h1 + 71, 3);

      for(int i = 0; i < this.imgMaterial.length; ++i) {
         if (this.imgMaterial[i] != null) {
            g.drawImage(this.imgMaterial[i], CCanvas.width / 2 - 70, this.h1 + 97 + i * 18, 3, false);
         }

         Font.normalFont.drawString(g, this.materialName[i], CCanvas.width / 2 - 55, this.h1 + 89 + i * 18, 0);
         Font.normalFont.drawString(g, this.numMaterial[i], CCanvas.width / 2 + 70, this.h1 + 89 + i * 18, 3);
      }

   }

   public void update() {
   }

   public void input() {
   }
}
