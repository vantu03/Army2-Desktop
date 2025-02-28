package screen;

import CLib.RMS;
import CLib.mGraphics;
import CLib.mImage;
import CLib.mSound;
import coreLG.CCanvas;
import effect.Cloud;
import map.CMap;
import model.CRes;
import model.Font;
import model.IAction;
import model.Language;
import network.Command;

public class ConfigScr extends CScreen {
   public static int vibrate;
   public int selected;
   public static mImage[] imgTick;
   public int[] level;
   boolean curDrawRGBType;
   public static String[] graphicText;

   static {
      try {
         imgTick = new mImage[2];
         imgTick[0] = mImage.createImage("/tick0.png");
         imgTick[1] = mImage.createImage("/tick1.png");
         vibrate = CRes.loadRMSInt("vibrate");
         if (CRes.loadRMSInt("vibrate") == -1) {
            vibrate = 10;
            CRes.saveRMSInt("vibrate", 10);
         } else {
            mSound.volumeSound = (float)CRes.loadRMSInt("vibrate");
         }

         if (CRes.loadRMSInt("sound") != -1) {
            mSound.volumeSound = (float)CRes.loadRMSInt("sound") / 100.0F;
         } else {
            CRes.saveRMSInt("sound", 100);
            mSound.volumeSound = 1.0F;
         }
      } catch (Exception var1) {
         var1.printStackTrace();
      }

      graphicText = Language.quality();
   }

   public ConfigScr() {
      this.curDrawRGBType = CMap.isDrawRGB;
      this.level = new int[2];
      this.center = new Command("OK", new IAction() {
         public void perform() {
            CCanvas.menuScr.show();
         }
      });
      this.nameCScreen = "ConfigScr screen!";
   }

   public void show() {
      this.level[0] = CRes.loadRMSInt("sound") / 10;
      this.level[1] = CRes.loadRMSInt("vibrate");
      if (GameScr.curGRAPHIC_LEVEL == -1) {
         GameScr.curGRAPHIC_LEVEL = 1;
      }

      this.curDrawRGBType = CRes.loadRMSInt("drawRGB") == 0;
      super.show();
   }

   public void paint(mGraphics g) {
      g.setClip(0, 0, CCanvas.width, CCanvas.hieght);
      paintDefaultBg(g);
      Cloud.paintCloud(g);

      int x;
      for(x = 0; x <= CCanvas.width; x += 32) {
         g.drawImage(PrepareScr.imgBack, x, CCanvas.hieght - 62, 0, false);
      }

      Font.bigFont.drawString(g, Language.option(), w >> 1, 5, 2);
      x = CCanvas.hw - 50;
      int y = CCanvas.hh - 60;
      if (CCanvas.gameTick % 10 > 2) {
         Font.borderFont.drawString(g, "$", x - 15, y + 14 + 33 * this.selected, 0);
         Font.borderFont.drawString(g, "#", x + 103, y + 14 + 33 * this.selected, 0);
      }

      Font.borderFont.drawString(g, Language.amthanh() + ":", x, y, 0);
      y += ITEM_HEIGHT;

      int i;
      for(i = 0; i < 10; ++i) {
         g.drawImage(imgTick[i < this.level[0] ? 0 : 1], x + i * 10, y, 0, false);
      }

      y += 14;
      Font.borderFont.drawString(g, Language.vibrate() + ":", x, y, 0);
      y += ITEM_HEIGHT;

      for(i = 0; i < 10; ++i) {
         g.drawImage(imgTick[i < this.level[1] ? 0 : 1], x + i * 10, y, 0, false);
      }

      Font.borderFont.drawString(g, Language.imageQuality() + ":", x, y + 13, 0);
      Font.borderFont.drawString(g, graphicText[GameScr.curGRAPHIC_LEVEL], CCanvas.hw, y + 29, 2);
      Font.borderFont.drawString(g, Language.graphicQuality() + ":", x, y + 48, 0);
      Font.borderFont.drawString(g, this.curDrawRGBType ? Language.macdinh() : Language.khac(), CCanvas.hw, y + 62, 2);
      super.paint(g);
   }

   public void onPointerDragged(int xDrag, int yDrag, int index) {
      super.onPointerDragged(xDrag, yDrag, index);
   }

   public void onPointerPressed(int xScreen, int yScreen, int index) {
      super.onPointerPressed(xScreen, yScreen, index);
   }

   public void onPointerReleased(int xRealse, int yRealse, int index) {
      super.onPointerReleased(xRealse, yRealse, index);
      int x = CCanvas.hw - 50;
      int y = CCanvas.hh - 60;
      int aa = (yRealse - y) / 33;
      if (this.selected != aa) {
         this.selected = aa;
      } else {
         if (CCanvas.isPointer(CCanvas.width / 2 - 100, 0, 100, CCanvas.hieght, index)) {
            CCanvas.keyPressed[4] = true;
         }

         if (CCanvas.isPointer(CCanvas.width / 2, 0, 100, CCanvas.hieght, index)) {
            CCanvas.keyPressed[6] = true;
         }
      }

      if (this.selected > 3) {
         this.selected = 0;
      } else if (this.selected < 0) {
         this.selected = 3;
      }

      boolean isChangeValue = false;
      int var10002;
      if (!CCanvas.keyPressed[4] && !keyLeft) {
         if (CCanvas.keyPressed[6] || keyRight) {
            isChangeValue = true;
            CCanvas.keyPressed[6] = false;
            keyRight = false;
            if (this.selected == 2) {
               --GameScr.curGRAPHIC_LEVEL;
               if (GameScr.curGRAPHIC_LEVEL < 0) {
                  GameScr.curGRAPHIC_LEVEL = 1;
               }
            } else if (this.selected == 3) {
               this.curDrawRGBType = !this.curDrawRGBType;
            } else {
               var10002 = this.level[this.selected]++;
               if (this.level[this.selected] > 10) {
                  this.level[this.selected] = 10;
               }
            }
         }
      } else {
         isChangeValue = true;
         CCanvas.keyPressed[4] = false;
         keyLeft = false;
         if (this.selected == 2) {
            ++GameScr.curGRAPHIC_LEVEL;
            if (GameScr.curGRAPHIC_LEVEL > 1) {
               GameScr.curGRAPHIC_LEVEL = 0;
            }
         } else if (this.selected == 3) {
            this.curDrawRGBType = !this.curDrawRGBType;
         } else {
            var10002 = this.level[this.selected]--;
            if (this.level[this.selected] < 0) {
               this.level[this.selected] = 1;
            }
         }
      }

      if (isChangeValue) {
         isChangeValue = false;
         if (this.selected == 0) {
            mSound.setVolume(this.level[this.selected] * 10);
         } else if (this.selected == 1) {
            CRes.saveRMSInt("vibrate", this.level[this.selected]);
            vibrate = this.level[this.selected];
         } else {
            this.saveGraphicAndDrawRGB_RMS();
         }
      }

   }

   private void saveGraphicAndDrawRGB_RMS() {
      RMS.saveRMSInt("Graphic", GameScr.curGRAPHIC_LEVEL);
      CMap.isDrawRGB = this.curDrawRGBType;
      CRes.saveRMSInt("drawRGB", this.curDrawRGBType ? 0 : 1);
      if (GameScr.curGRAPHIC_LEVEL != 2) {
         GameScr.mm.createBackGround();
      } else {
         GameScr.mm.clearBackGround();
      }

   }

   public void update() {
      super.update();
      Cloud.updateCloud();
   }
}
