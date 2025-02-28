package model;

import CLib.mGraphics;
import coreLG.CCanvas;

public class Popup {
   public void paint(mGraphics g) {
   }

   public void update() {
   }

   public void show() {
      CCanvas.arrPopups.addElement(this);
   }

   public void showSingle() {
      if (CCanvas.arrPopups.contains(this)) {
         CCanvas.arrPopups.removeElement(this);
      }

      this.show();
   }

   public void keyPress(int keyCode) {
   }

   public void onPointerDragged(int xDrag, int yDrag, int index) {
   }

   public void onPointerPressed(int xScreen, int yScreen, int index) {
   }

   public void onPointerReleased(int xReleased, int yReleased, int index) {
   }

   public void hide() {
      if (CCanvas.arrPopups.contains(this)) {
         CCanvas.arrPopups.removeElement(this);
      }

   }
}
