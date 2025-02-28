package screen;

import CLib.mGraphics;
import CLib.mImage;
import coreLG.CCanvas;
import coreLG.TerrainMidlet;
import model.CRes;
import model.Font;
import model.IAction;
import model.Language;
import model.PlayerInfo;
import network.Command;
import network.GameService;

public class LevelScreen extends TabScreen {
   static mImage ability;
   static mImage plus;
   static mImage arrow;
   public static byte point;
   public static byte level;
   int select = 0;
   short currPoint;
   short[] currAbility = new short[5];
   byte[] deltaA = new byte[5];
   Command cmdSelect;
   public static final String[] strAbility = new String[]{Language.heath(), Language.dam(), Language.defend(), Language.lucky(), Language.team()};
   Command cmdLamlai;
   byte[] canUp = new byte[5];
   byte[] canDown = new byte[5];

   static {
      try {
         ability = mImage.createImage("/item/ability.png");
         plus = mImage.createImage("/item/+.png");
         arrow = mImage.createImage("/map/arrow1.png");
      } catch (Exception var1) {
         var1.printStackTrace();
      }

   }

   public void show(CScreen lastScr) {
      super.show(lastScr);
      CCanvas.arrPopups.removeAllElements();
      this.currPoint = TerrainMidlet.myInfo.point;

      for(int i = 0; i < 5; ++i) {
         this.currAbility[i] = TerrainMidlet.myInfo.ability[i];
         this.canDown[i] = 0;
      }

      this.deltaA = new byte[5];
      PlayerInfo m = TerrainMidlet.myInfo;
      this.title = "Lvl " + m.level2 + (m.level2Percen >= 0 ? "+" : "") + m.level2Percen + "%";
      if (m.point > 0) {
         for(int i = 0; i < 5; ++i) {
            this.canUp[i] = 1;
         }
      }

   }

   public void doClose() {
      for(int i = 0; i < 5; ++i) {
         this.canDown[0] = 0;
         this.canDown[0] = 0;
      }

      this.isClose = true;
   }

   public LevelScreen() {
      this.nameCScreen = "LevelScreen screen!";
      this.xPaint = CCanvas.width / 2 - 65;
      this.yPaint = (CCanvas.hieght - CScreen.cmdH) / 2 - 90;
      this.hTabScreen = 170;
      if (CCanvas.isTouch) {
         this.hTabScreen = 185;
      }

      this.right = new Command(Language.close(), new IAction() {
         public void perform() {
            LevelScreen.this.restartPoint();
            LevelScreen.this.doClose();
         }
      });
      this.cmdSelect = new Command(Language.xacnhan(), new IAction() {
         public void perform() {
            LevelScreen.this.doFire();
         }
      });
      this.cmdLamlai = new Command(Language.lamlai(), new IAction() {
         public void perform() {
            LevelScreen.this.restartPoint();
            CCanvas.menu.showMenu = false;
         }
      });
      this.title = "";
      this.n = CCanvas.isTouch ? 4 : 3;
      this.getW();
   }

   public void doFire() {
      CCanvas.startYesNoDlg(Language.areYouSure(), new IAction() {
         public void perform() {
            GameService.gI().addPoint(LevelScreen.this.deltaA);
            LevelScreen.this.currPoint = TerrainMidlet.myInfo.point;

            int i;
            for(i = 0; i < 5; ++i) {
               LevelScreen.this.canDown[i] = 0;
            }

            if (TerrainMidlet.myInfo.point > 0) {
               for(i = 0; i < 5; ++i) {
                  LevelScreen.this.canUp[i] = 1;
               }
            }

            for(i = 0; i < 5; ++i) {
               LevelScreen.this.deltaA[i] = 0;
               LevelScreen.this.canDown[i] = 0;
            }

            CCanvas.endDlg();
         }
      }, new IAction() {
         public void perform() {
            LevelScreen.this.restartPoint();
            CCanvas.endDlg();
         }
      });
   }

   public void restartPoint() {
      TerrainMidlet.myInfo.point = this.currPoint;
      if (this.currPoint != 0) {
         for(int i = 0; i < 5; ++i) {
            short[] var10000 = this.currAbility;
            var10000[i] = (short)(var10000[i] - this.deltaA[i]);
            this.deltaA[i] = 0;
            this.canDown[i] = 0;
            this.canUp[i] = 1;
         }

      }
   }

   public void doUp() {
      if (TerrainMidlet.myInfo.point > 0) {
         ++this.deltaA[this.select];
         --TerrainMidlet.myInfo.point;
         ++this.currAbility[this.select];
         this.canDown[this.select] = 1;
      }

      if (TerrainMidlet.myInfo.point == 0) {
         for(int i = 0; i < 5; ++i) {
            this.canUp[i] = 0;
         }
      }

   }

   public void doDown() {
      int ability = TerrainMidlet.myInfo.ability[this.select];
      if (this.currAbility[this.select] > ability) {
         --this.deltaA[this.select];
         ++TerrainMidlet.myInfo.point;
         --this.currAbility[this.select];
         this.canUp[this.select] = 1;
      }

      if (this.currAbility[this.select] == ability) {
         this.canDown[this.select] = 0;
      }

      if (TerrainMidlet.myInfo.point > 0) {
         for(int i = 0; i < 5; ++i) {
            this.canUp[i] = 1;
         }
      }

   }

   public static void paintLevelPercen(mGraphics g, int x, int y) {
      PlayerInfo m = TerrainMidlet.myInfo;
      g.setColor(1521982);
      g.fillRect(x, y, 102, 17, false);
      g.setColor(2378093);
      g.fillRect(x + 1, y + 1, 100, 15, false);
      int percen = m.level2Percen * 100 / 100;
      g.setColor(16767817);
      g.fillRect(x + 1, y + 1, percen, 15, false);
      Font.borderFont.drawString(g, m.exp + "/" + m.nextExp, x + 51, y, 2);
   }

   public void paint(mGraphics g) {
      super.paint(g);
      int W = 122;
      PlayerInfo m = TerrainMidlet.myInfo;
      int lv = m.level2;
      g.translate(-15, -33);
      int dis = 30;
      int aa = CCanvas.isTouch ? 15 : 0;
      int bb = CCanvas.isTouch ? 10 : 0;

      int i;
      for(i = 0; i < 5; ++i) {
         g.drawRegion(LevelScreen.ability, 0, i * 16, 16, 16, 0, this.xPaint, this.yPaint + 57 + i * dis, 0, false);
         if (!CCanvas.isTouch) {
            if (i == this.select) {
               Font.normalYFont.drawString(g, strAbility[i], this.xPaint + 25, this.yPaint + 58 + i * dis, 0);
            } else {
               Font.normalFont.drawString(g, strAbility[i], this.xPaint + 25, this.yPaint + 58 + i * dis, 0);
            }
         } else {
            Font.normalFont.drawString(g, strAbility[i], this.xPaint + 25, this.yPaint + 58 + i * dis, 0);
         }

         short ability = this.currAbility[i];
         g.setColor(1521982);
         g.fillRect(this.xPaint + 98 + aa, this.yPaint + 57 + i * dis, 30, 16, false);
         if (this.canDown[i] == 1) {
            Font.normalGFont.drawString(g, String.valueOf(ability), this.xPaint + 113 + aa, this.yPaint + 57 + i * dis, 3);
         } else {
            Font.normalYFont.drawString(g, String.valueOf(ability), this.xPaint + 113 + aa, this.yPaint + 57 + i * dis, 3);
         }

         if (this.canDown[i] == 1) {
            g.drawImage(arrow, this.xPaint + 96 + aa - bb, this.yPaint + 62 + i * dis, 24, false);
         }

         if (this.canUp[i] == 1) {
            g.drawRegion(arrow, 0, 0, 4, 7, 2, this.xPaint + 130 + aa + bb, this.yPaint + 62 + i * dis, 0, false);
         }
      }

      g.translate(0, -g.getTranslateY());
      i = CCanvas.isTouch ? 15 : 0;
      Font.borderFont.drawString(g, "Point: " + m.point, this.xPaint + W / 2 - i, this.yPaint + 57 + 90 + 2 + i, 3);
      this.paintSuper(g);
   }

   public void onPointerPressed(int xPress, int yPress, int index) {
      super.onPointerPressed(xPress, yPress, index);
   }

   public void onPointerReleased(int xReleased, int yReleased, int index) {
      super.onPointerReleased(xReleased, yReleased, index);
      int aa = (yReleased - (this.yPaint + 57 - 25)) / 25;
      this.select = aa;
      if (this.select < 0) {
         this.select = 0;
      }

      if (this.select > 4) {
         this.select = 4;
      }

      CRes.out("==> sellect = " + aa);
      if (CCanvas.isPointer(this.xPaint + 70, this.yPaint + 57 - 25 + this.select * 25, 30, 30, index)) {
         this.doDown();
      }

      if (CCanvas.isPointer(this.xPaint + 130, this.yPaint + 57 - 25 + this.select * 25, 30, 30, index)) {
         this.doUp();
      }

   }

   public void update() {
      super.update();
      GameScr.sm.update();
      if (TerrainMidlet.myInfo.point >= 0) {
         this.center = this.cmdSelect;
         this.left = this.cmdLamlai;
      } else {
         this.center = null;
         this.left = null;
      }

   }
}
