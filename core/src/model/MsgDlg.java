package model;

import CLib.mGraphics;
import CLib.mSystem;
import coreLG.CCanvas;
import network.Command;
import screen.CScreen;

public class MsgDlg extends Dialog {
   protected String[] info = null;
   private long timeShow;
   private long timeCountDown;
   private IAction action;

   public void show() {
      CCanvas.currentDialog = this;
   }

   public void close() {
      this.action = null;
      this.timeShow = -1L;
   }

   public void setInfo(String info, Command left, Command center, Command right) {
      this.info = Font.normalFont.splitFontBStrInLine(info, CCanvas.width - 50);
      this.left = left;
      this.center = center;
      this.right = right;
      this.timeShow = -1L;
   }

   public void setInfo(String info, long timeShow, IAction action, Command left, Command center, Command right) {
      this.info = Font.normalFont.splitFontBStrInLine(info, CCanvas.width - 50);
      this.left = left;
      this.center = center;
      this.right = right;
      this.timeShow = timeShow;
      this.action = action;
   }

   public void setInfo(String info, long timeShow, IAction action) {
      this.info = Font.normalFont.splitFontBStrInLine(info, CCanvas.width - 50);
      this.timeCountDown = timeShow;
      this.timeShow = timeShow;
      this.action = action;
   }

   public void update() {
      if (this.timeShow != -1L) {
         if (this.timeShow <= mSystem.currentTimeMillis()) {
            CCanvas.endDlg();
            if (this.action != null) {
               this.action.perform();
               this.action = null;
            }
         }

      }
   }

   public void paint(mGraphics g) {
      g.translate(-g.getTranslateX(), -g.getTranslateY());
      int dy = 0;
      if (CCanvas.hieght < 200) {
         dy += 10;
      }

      int tam = 0;
      if (this.info != null) {
         if (this.info.length > 3) {
            tam = (this.info.length - 3) * 8;
         }

         int len = this.info.length;
         if (len > 0) {
            CScreen.paintDefaultPopup(8, CCanvas.hieght - 112 - tam, CCanvas.width - 16, 69 + 2 * tam, g);
            if (Font.normalFont != null) {
               int yStart = CCanvas.hieght - 20 - 50 - (len * Font.normalFont.getHeight() >> 1) + dy;
               int i = 0;

               for(int y = yStart; i < len; y += Font.normalFont.getHeight()) {
                  if (!CRes.isNullOrEmpty(this.info[i])) {
                     Font.normalFont.drawString(g, this.info[i], CCanvas.hw, y - 10, 2);
                  }

                  ++i;
               }
            }
         }

         super.paint(g);
      }
   }

   public void onPointerReleased(int x, int y, int index) {
      super.onPointerReleased(x, y, index);
   }

   public void onPointerPressed(int x, int y, int index) {
      super.onPointerPressed(x, y, index);
   }
}
