package screen;

import CLib.mGraphics;
import CLib.mImage;
import coreLG.CCanvas;
import coreLG.TerrainMidlet;
import model.Font;
import model.IAction;
import model.Language;
import model.PlayerInfo;
import network.Command;

public class ArchivementScr extends CScreen {
   public int level;
   public int levelPercen;
   public int xu;
   public int luong;
   public int exp;
   public int nextExp;
   public int cup;
   public String rank;
   public mImage imgClan;

   public ArchivementScr() {
      this.right = new Command(Language.back(), new IAction() {
         public void perform() {
            CCanvas.menuScr.show();
         }
      });
      this.nameCScreen = "Achivement screen!";
   }

   public void paint(mGraphics g) {
      paintDefaultBg(g);
      Font.bigFont.drawString(g, "THÀNH TÍCH", CCanvas.width / 2, 10, 3);
      String name = TerrainMidlet.myInfo.name;
      if (this.imgClan != null) {
         g.drawImage(this.imgClan, CCanvas.width / 2 - Font.borderFont.getWidth(name) / 2 - 20, 41, 0, false);
      }

      Font.borderFont.drawString(g, name, CCanvas.width / 2, 40, 2);
      g.drawImage(CScreen.cup, CCanvas.width / 2 - 20, 60, 0, false);
      Font.borderFont.drawString(g, String.valueOf(this.cup), CCanvas.width / 2 + 3, 62, 0);
      Font.borderFont.drawString(g, "(" + this.rank + ")", CCanvas.width / 2, 80, 2);
      Font.normalFont.drawString(g, this.xu + Language.xu() + " - " + this.luong + Language.luong(), CCanvas.width / 2, 100, 2);
      PlayerInfo m = TerrainMidlet.myInfo;
      Font.borderFont.drawString(g, "Level " + m.level2 + (this.level + this.levelPercen >= 0 ? " +" : " ") + this.levelPercen + "%", CCanvas.width / 2, 120, 2);
      g.setColor(1521982);
      g.fillRect(CCanvas.width / 2 - 50, 145, 102, 17, false);
      g.setColor(2378093);
      g.fillRect(CCanvas.width / 2 - 50 + 1, 146, 100, 15, false);
      int percen = m.level2Percen * 100 / 100;
      g.setColor(16767817);
      g.fillRect(CCanvas.width / 2 - 50 + 1, 146, percen, 15, false);
      Font.borderFont.drawString(g, this.exp + "/" + this.nextExp, CCanvas.width / 2, 145, 2);
      super.paint(g);
   }

   public void update() {
      super.update();
   }
}
