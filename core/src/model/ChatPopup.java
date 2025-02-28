package model;

import CLib.mGraphics;
import CLib.mImage;
import coreLG.CCanvas;
import effect.Camera;
import screen.GameScr;

public class ChatPopup extends Popup {
   public static mImage imgChat;
   public int timeOut;
   public int xArrow;
   public int yArrow;
   public int x;
   public int y;
   public int h;
   public int popupW = 60;
   String[] chats;

   static {
      imgChat = GameScr.imgChat;
   }

   public void show(int time, int x, int y, String chat) {
      this.prepareData(time, x, y, chat);
   }

   private void prepareData(int time, int x, int y, String chat) {
      if (Font.smallFontYellow.getWidth(chat) < this.popupW) {
         this.popupW = Font.smallFontYellow.getWidth(chat) + 10;
      }

      this.chats = Font.smallFontYellow.splitFontBStrInLine(chat, this.popupW);
      this.h = 10 * this.chats.length + 4;
      this.xArrow = x - 2;
      this.yArrow = y - 4;
      this.x = this.xArrow - this.popupW / 2;
      this.y = this.yArrow - this.h;
      if (this.x < 2) {
         this.x = 2;
      }

      if (this.x + 60 > CCanvas.w - 2) {
         this.x = CCanvas.w - 62;
      }

      this.timeOut = time;
   }

   public void showSingle(int time, int x, int y, String chat) {
      this.prepareData(time, x, y, chat);
      super.showSingle();
   }

   public void update() {
      if (this.timeOut > 0) {
         --this.timeOut;
         if (this.timeOut == 0) {
            CCanvas.arrPopups.removeElement(this);
         }
      }

   }

   public void paint(mGraphics g) {
      if (Camera.mode == 0) {
         g.translate(g.getTranslateX(), g.getTranslateY());
      } else {
         g.translate(-g.getTranslateX(), -g.getTranslateY());
      }

      g.setColor(16711553);
      g.fillRect(this.x, this.y, this.popupW, this.h, false);
      g.setColor(0);
      g.drawRect(this.x, this.y, this.popupW, this.h, false);
      g.drawImage(imgChat, this.xArrow, this.yArrow, 0, false);
      int yy = this.y + 2;

      for(int i = 0; i < this.chats.length; ++i) {
         Font.smallFont.drawString(g, this.chats[i], this.x + 5, yy, 0);
         yy += 10;
      }

   }
}
