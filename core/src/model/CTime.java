package model;

import CLib.mGraphics;
import coreLG.CCanvas;
import effect.Camera;
import network.GameService;
import network.Session_ME;
import player.PM;
import screen.CScreen;
import screen.GameScr;

public class CTime {
   int timeInterval;
   public static int seconds;
   long last;
   long cur;
   boolean visible = true;

   public CTime() {
      this.last = this.cur = System.currentTimeMillis();
   }

   public void initTimeInterval(byte time) {
      this.timeInterval = time;
   }

   public void resetTime() {
      seconds = this.timeInterval;
      this.visible = true;
   }

   public void skipTurn() {
      if (GameScr.pm.isYourTurn()) {
         PM var10000 = GameScr.pm;
         PM.getMyPlayer().active = false;
         GameService.gI().skipTurn();
         this.visible = false;
      }

   }

   public void update() {
      if (this.visible && !GameScr.trainingMode) {
         this.cur = System.currentTimeMillis();
         if (this.cur - this.last >= 1000L) {
            --seconds;
            if (seconds <= -10 && seconds % 10 == 0) {
               --seconds;
               Session_ME.receiveSynchronized = 0;
               PM.getCurPlayer().x = PM.getCurPlayer().xToNow;
               PM.getCurPlayer().y = PM.getCurPlayer().yToNow;
            }

            if (CCanvas.curScr == CCanvas.gameScr && seconds <= 0) {
               if (PM.getCurPlayer() != null) {
                  seconds = 0;
                  if (PM.getCurPlayer().itemUsed != -1) {
                     PM.getCurPlayer().itemUsed = -1;
                  }
               }

               return;
            }

            this.last = this.cur;
         }
      }

   }

   public void paint(mGraphics g) {
      int dis = CCanvas.isTouch ? 25 : 0;
      if (this.visible) {
         if (CCanvas.curScr == CCanvas.gameScr) {
            Font.bigFont.drawString(g, Integer.toString(seconds), Camera.x + CScreen.w - 16, Camera.y + 2 + dis, 2, false);
         }

         if (CCanvas.curScr == CCanvas.luckyGifrScreen) {
            Font.bigFont.drawString(g, Integer.toString(seconds), CScreen.w - 16, 2, 2, false);
         }
      }

   }

   public void stop() {
      this.visible = false;
   }
}
