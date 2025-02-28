package model;

import CLib.mGraphics;
import coreLG.CCanvas;
import network.Command;
import screen.CScreen;

public abstract class Dialog {
   public Command left;
   public Command center;
   public Command right;

   public void update() {
   }

   public void paint(mGraphics g) {
      g.translate(-g.getTranslateX(), -g.getTranslateY());
      g.setClip(0, 0, CCanvas.width, CCanvas.hieght);
      byte a;
      if (CCanvas.isTouch) {
         a = 10;
      } else {
         a = 3;
      }

      if (this.left != null) {
         Font.normalFont.drawString(g, this.left.caption, 5, CCanvas.hieght - Font.normalFont.getHeight() - a, 0);
      }

      if (this.center != null) {
         Font.normalFont.drawString(g, this.center.caption, CCanvas.hw, CCanvas.hieght - Font.normalFont.getHeight() - a, 2);
      }

      if (this.right != null) {
         Font.normalFont.drawString(g, this.right.caption, CCanvas.width - 5, CCanvas.hieght - Font.normalFont.getHeight() - a, 1);
      }

   }

   public void keyPress(int keyCode) {
   }

   public void onPointerPressed(int x, int y, int index) {
   }

   public void onPointerReleased(int x, int y, int index) {
      this.input(x, y, index);
   }

   public void onInputHolder(int x, int y, int index) {
   }

   private void input(int x, int y, int index) {
      if (CCanvas.keyPressed[5] || CScreen.getCmdPointerPressed((byte)2, index, true)) {
         CCanvas.keyPressed[5] = false;
         if (this.center != null && this.center.action != null) {
            this.center.action.perform();
         }
      }

      if (CCanvas.keyPressed[12] || CScreen.getCmdPointerPressed((byte)0, index, true)) {
         CCanvas.keyPressed[12] = false;
         if (this.left != null && this.left.action != null) {
            this.left.action.perform();
         }
      }

      if (CCanvas.keyPressed[13] || CScreen.getCmdPointerPressed((byte)1, index, true)) {
         CCanvas.keyPressed[13] = false;
         if (this.right != null && this.right.action != null) {
            this.right.action.perform();
         }
      }

   }

   public abstract void show();

   public abstract void close();
}
