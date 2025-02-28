package screen;

import CLib.mGraphics;
import coreLG.CCanvas;
import effect.Cloud;
import java.util.Vector;
import model.Font;
import model.IAction;
import model.Language;
import model.Mission;
import network.Command;
import network.GameService;

public class MissionScreen extends CScreen {
   public Vector mission = new Vector();
   public int cmtoY;
   public int cmy;
   public int cmdy;
   public int cmvy;
   public int cmyLim;
   int selected;
   int disY;
   int pa = 0;
   boolean trans = false;
   int pxFirst;

   public MissionScreen() {
      this.right = new Command(Language.back(), new IAction() {
         public void perform() {
            CCanvas.menuScr.show();
         }
      });
      this.nameCScreen = " MissionScreen screen!";
   }

   public void show() {
      super.show();
   }

   public Mission getCurrMiss() {
      return this.selected < this.mission.size() ? (Mission)this.mission.elementAt(this.selected) : null;
   }

   public void paintList(mGraphics g) {
      g.translate(0, ITEM_HEIGHT + 25);
      g.setClip(0, 1, CCanvas.width, CCanvas.hieght - 25 - 21 - ITEM_HEIGHT);
      g.translate(0, -this.cmy);
      int y = 0;

      for(int i = 0; i < this.mission.size(); ++i) {
         if (i == this.selected) {
            g.setColor(16765440);
            g.fillRect(0, y, CCanvas.width, this.disY, true);
         }

         if (i * this.disY + this.disY > -g.getTranslateY() && i * this.disY < -g.getTranslateY() + CScreen.h) {
            Mission m = (Mission)this.mission.elementAt(i);
            Font.borderFont.drawString(g, m.name, 7, y + 10, 0);
            g.setColor(1521982);
            g.fillRect(5, y + 26, 102, 17, true);
            g.setColor(2378093);
            g.fillRect(6, y + 26 + 1, 100, 15, true);
            int percen = m.have * 100 / m.require;
            g.setColor(16767817);
            g.fillRect(6, y + 26 + 1, percen, 15, true);
            Font.borderFont.drawString(g, m.have + "/" + m.require, 56, y + 26, 2);
            Font.borderFont.drawString(g, m.reward, 7, y + 43, 0);
            if (!m.isComplete) {
               g.drawImage(yes, CCanvas.width - 20, y + 32, 3, true);
            } else {
               g.drawImage(no, CCanvas.width - 20, y + 32, 3, true);
            }
         }

         y += this.disY;
      }

   }

   public void paint(mGraphics g) {
      g.setClip(0, 0, CCanvas.width, CCanvas.hieght);
      paintDefaultBg(g);
      Cloud.paintCloud(g);

      for(int i = 0; i <= CCanvas.width; i += 32) {
         g.drawImage(PrepareScr.imgBack, i, CCanvas.hieght - 62, 0, false);
      }

      g.setColor(1407674);
      g.fillRect(0, 25, CCanvas.width, ITEM_HEIGHT, false);
      Font.bigFont.drawString(g, Language.mission().toUpperCase(), CCanvas.width / 2, 3, mGraphics.HCENTER | mGraphics.TOP);
      Font.normalYFont.drawString(g, Language.mission(), 10, 28, 0);
      Font.normalYFont.drawString(g, Language.missionComplete(), CCanvas.width - 10, 28, 1);
      this.paintList(g);
      super.paint(g);
   }

   public void moveCamera() {
      if (this.cmy != this.cmtoY) {
         this.cmvy = this.cmtoY - this.cmy >> 2;
         this.cmy += this.cmvy;
      }

   }

   public void update() {
      this.moveCamera();
      Cloud.updateCloud();
      super.update();
   }

   public void getCommand() {
      final Mission _missionRes = this.getCurrMiss();
      if (_missionRes != null && this.getCurrMiss().isComplete) {
         this.center = new Command(Language.nhanthuong(), new IAction() {
            public void perform() {
               GameService.gI().mission((byte)1, (byte)_missionRes.id);
               CCanvas.startOKDlg(Language.pleaseWait());
            }
         });
      } else {
         this.center = null;
      }
   }

   public void onPointerDragged(int xDrag, int yDrag, int index) {
      super.onPointerDragged(xDrag, yDrag, index);
      if (!this.trans) {
         this.pa = this.cmy;
         this.trans = true;
      }

      this.cmtoY = this.pa + (CCanvas.pyFirst[index] - yDrag);
      if (this.cmtoY > this.cmyLim) {
         this.cmtoY = this.cmyLim;
      }

      if (this.cmtoY < 0) {
         this.cmtoY = 0;
      }

   }

   public void onPointerPressed(int xScreen, int yScreen, int index) {
      super.onPointerPressed(xScreen, yScreen, index);
      int b = 2 * ITEM_HEIGHT;
      int aa = (this.cmtoY + yScreen - b) / this.disY;
      this.selected = aa;
      this.getCommand();
      if (this.selected < 0) {
         this.selected = 0;
      }

      if (this.selected >= this.mission.size()) {
         this.selected = this.mission.size() - 1;
      }

   }

   public void onPointerReleased(int xRealsed, int yRealsed, int index) {
      super.onPointerReleased(xRealsed, yRealsed, index);
      this.trans = false;
      if (CCanvas.isPointer(0, 0, w, CCanvas.hieght - cmdH, index)) {
         int b = 2 * ITEM_HEIGHT;
         int aa = (this.cmtoY + yRealsed - b) / this.disY;
         if (aa == this.selected) {
            if (this.center != null) {
               if (CCanvas.isDoubleClick) {
                  this.center.action.perform();
               }
            } else if (this.left != null && CCanvas.isDoubleClick) {
               this.left.action.perform();
            }
         }

         this.selected = aa;
         if (this.selected < 0) {
            this.selected = 0;
         }

         if (this.selected >= this.mission.size()) {
            this.selected = this.mission.size() - 1;
         }
      }

   }

   public void onPointerHolder(int xScreen, int yScreen, int index) {
   }

   public void setMission(Vector m) {
      this.mission = m;
      this.disY = 65;
      this.cmyLim = this.mission.size() * this.disY - (CCanvas.hieght - (ITEM_HEIGHT * 2 + cmdH)) + 10;
      if (this.cmyLim < 0) {
         this.cmyLim = 0;
      }

      this.getCommand();
   }
}
