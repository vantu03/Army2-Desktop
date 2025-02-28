package screen;

import CLib.mGraphics;
import CLib.mImage;
import coreLG.CCanvas;
import map.Background;
import model.CRes;
import model.Font;
import model.IAction;
import network.Command;

public abstract class CScreen {
   public String nameCScreen;
   protected byte indexScreen;
   public static int w;
   public static int h;
   public static CScreen instance;
   public static final int ITEM_HEIGHT;
   public static boolean isSetClip;
   public Command left;
   public Command center;
   public Command right;
   public int[] _x;
   public int[] _y;
   public boolean menuScroll = false;
   public int cmtoX;
   public int cmx;
   public int cmdx;
   public int cmvx;
   public int cmxLim;
   public static int cmdW;
   public static int cmdH;
   public static final byte CMD_LEFT = 0;
   public static final byte CMD_RIGHT = 1;
   public static final byte CMD_CENTER = 2;
   public int midCommandW = 32;
   public static mImage yes;
   public static mImage no;
   public static mImage imgInfoPopup;
   public static mImage tab_1;
   public static mImage tab_2;
   public static mImage tab_3;
   public static mImage tab_4;
   public static mImage tab_5;
   public static mImage conner;
   public static mImage arrow;
   public static mImage levelup;
   public static mImage cup;
   private boolean leftHold;
   private boolean rightHold;
   private boolean centerHold;
   public static boolean isMoto;
   public static boolean isBB;
   public static boolean isNX;
   public static CScreen lastSCreen;
   static int t;
   static int y;
   public static int currKey;
   public static boolean keyUp;
   public static boolean keyDown;
   public static boolean keyLeft;
   public static boolean keyRight;
   public static boolean keyFire;

   static {
      ITEM_HEIGHT = Font.normalFont.getHeight() + 6;
      isSetClip = true;
      cmdW = Font.normalFont.getWidth("ABCDEFGHJKL");
      cmdH = 50;
      imgInfoPopup = GameScr.imgInfoPopup;

      try {
         tab_1 = mImage.createImage("/map/tab_1.png");
         tab_2 = mImage.createImage("/map/tab_2.png");
         tab_3 = mImage.createImage("/map/tab_3.png");
         tab_4 = mImage.createImage("/map/tab_4.png");
         tab_5 = mImage.createImage("/map/tab_5.png");
         conner = mImage.createImage("/map/corner.png");
         arrow = mImage.createImage("/map/arrow.png");
         levelup = mImage.createImage("/lever-up.png");
         cup = mImage.createImage("/cup.png");
         yes = mImage.createImage("/x.png");
         no = mImage.createImage("/v.png");
      } catch (Exception var1) {
         var1.printStackTrace();
      }

      y = 1;
   }

   public CScreen() {
      instance = this;
      this._x = new int[3];
      this._y = new int[3];
   }

   public void paint(mGraphics _mGraphic) {
      _mGraphic.translate(-_mGraphic.getTranslateX(), -_mGraphic.getTranslateY());
      if (isSetClip) {
         _mGraphic.setClip(0, 0, w, h);
      }

      this.paintCommand(_mGraphic);
//      int dis = false;
      byte dis;
      if (CCanvas.isTouch) {
         dis = 12;
      } else {
         dis = 3;
      }

      if (CCanvas.currentDialog == null && !CCanvas.menu.showMenu) {
         this._x[0] = 5;
         this._y[0] = CCanvas.hieght - Font.normalFont.getHeight() - dis;
         this._x[1] = CCanvas.hw;
         this._y[1] = CCanvas.hieght - Font.normalFont.getHeight() - dis;
         this._x[2] = CCanvas.width - 5;
         this._y[2] = CCanvas.hieght - Font.normalFont.getHeight() - dis;
         if (CCanvas.isDebugging()) {
            _mGraphic.setColor(1407674);
            _mGraphic.fillRect(0, h - cmdH, cmdW, cmdH, false);
            _mGraphic.setColor(1407674);
            _mGraphic.fillRect(w - cmdW, h - cmdH, cmdW, cmdH, false);
            _mGraphic.setColor(1407674);
            _mGraphic.fillRect(w - cmdW >> 1, h - cmdH, cmdW, cmdH, false);
         }

         if (this.left != null) {
            if (this.leftHold) {
               Font.normalFont.drawString(_mGraphic, this.left.caption, this._x[0] + 2, this._y[0] + 2, 0, true);
            } else {
               Font.normalFont.drawString(_mGraphic, this.left.caption, this._x[0], this._y[0], 0, true);
            }
         }

         if (this.center != null) {
            if (this.centerHold) {
               Font.normalFont.drawString(_mGraphic, this.center.caption, this._x[1] + 2, this._y[1] + 2, 2, true);
            } else {
               Font.normalFont.drawString(_mGraphic, this.center.caption, this._x[1], this._y[1], 2, true);
            }
         }

         if (this.right != null) {
            if (this.rightHold) {
               Font.normalFont.drawString(_mGraphic, this.right.caption, this._x[2] + 2, this._y[2] + 2, 1, true);
            } else {
               Font.normalFont.drawString(_mGraphic, this.right.caption, this._x[2], this._y[2], 1, true);
            }
         }
      }

   }

   public static void paintDefaultBg(mGraphics g) {
      Background.paintMenuBackGround(g);
   }

   public static void paintLevelUp(mGraphics g, int x) {
   }

   public void update() {
   }

   public void mainLoop() {
   }

   public boolean isShowing() {
      return CCanvas.curScr == this;
   }

   public void moveCamera() {
      if (this.cmx != this.cmtoX) {
         this.cmvx = this.cmtoX - this.cmx << 2;
         this.cmdx += this.cmvx;
         this.cmx += this.cmdx >> 3;
         this.cmdx &= 15;
      }

   }

   public static void clearKey() {
      int i;
      for(i = 0; i < CCanvas.keyPressed.length; ++i) {
         CCanvas.keyPressed[i] = CCanvas.keyHold[i] = false;
      }

      for(i = 0; i < CCanvas.isPointerClick.length; ++i) {
         CCanvas.isPointerClick[i] = false;
      }

      keyUp = false;
      keyDown = false;
      keyLeft = false;
      keyRight = false;
   }

   public void show() {
      clearKey();
      CCanvas.curScr = this;
   }

   public void show(CScreen LastScreen) {
      lastSCreen = LastScreen;
      clearKey();
      CCanvas.curScr = this;
   }

   public void show(IAction actionCallback) {
      clearKey();
      CCanvas.curScr = this;
   }

   public void close() {
      clearKey();
   }

   public static void paintWhitePopup(mGraphics g, int y, int x, int width, int height) {
      g.setColor(16777215);
      g.fillRect(x, y, width, height, false);
      g.setColor(0);
      g.drawRect(x - 1, y - 1, width + 1, height + 1, false);
   }

   public void keyPressed(int keyCode) {
   }

   public void keyReleased(int keyCode) {
   }

   public static boolean getCmdPointerPressed(byte cmdType, int index, boolean isDialog) {
      if (!isDialog) {
         if (CCanvas.menu.showMenu) {
            return false;
         }

         if (CCanvas.currentDialog != null) {
            return false;
         }
      }

      if (CCanvas.isPointerClick[index]) {
         if (cmdType == 0) {
            if (CCanvas.isPointer(0, h - cmdH, cmdW, cmdH, index)) {
               return true;
            }
         } else if (cmdType == 1) {
            if (CCanvas.isPointer(w - cmdW, h - cmdH, cmdW, cmdH, index)) {
               return true;
            }
         } else if (CCanvas.isPointer(w - cmdW >> 1, h - cmdH, cmdW, cmdH, index)) {
            return true;
         }
      }

      return false;
   }

   public static boolean getCmdPointerLast(byte cmdType, int index) {
      if (CCanvas.menu.showMenu) {
         return false;
      } else if (CCanvas.currentDialog != null) {
         return false;
      } else {
         if (cmdType == 0) {
            if (CCanvas.isPointer(0, h - cmdH, cmdW, cmdH, index)) {
               return true;
            }
         } else if (cmdType == 1) {
            if (CCanvas.isPointer(w - cmdW, h - cmdH, cmdW, cmdH, index)) {
               return true;
            }
         } else if (CCanvas.isPointer(w - cmdW >> 1, h - cmdH, cmdW, cmdH, index)) {
            return true;
         }

         return false;
      }
   }

   public void paintCommand(mGraphics g) {
      int a = cmdH;
      g.setColor(12965614);
      g.fillRect(0, h - a, w + 10, a, false);
      g.setColor(3158064);
      g.drawLine(0, h - a, w + 10, h - a, false);
      g.setColor(16777215);
      g.drawLine(0, h - a + 1, w + 10, h - a + 1, false);
   }

   public void paintTapUp(mGraphics g) {
   }

   public static void paintBorderRect(mGraphics g, int y, int n, int height, String title) {
      int nTab = n;
      int W = n * 32 + 56;
      int x = CCanvas.width / 2 - W / 2;
      g.setColor(12965614);
      g.fillRect(x, y + 20, W - 1, height - 25, false);
      g.setColor(3158064);
      g.drawRect(x, y + 10, W - 1, height - 15, false);
      g.drawImage(tab_1, x, y, 0, false);

      int strW;
      for(strW = 0; strW < nTab; ++strW) {
         g.drawImage(tab_2, x + 23 + strW * 32, y, 0, false);
      }

      g.drawImage(tab_3, x + 23 + nTab * 32, y, 0, false);
      g.drawImage(tab_4, x, y + height - tab_4.image.getHeight(), 0, false);
      g.drawImage(tab_5, x + W - tab_5.image.getWidth(), y + height - tab_5.image.getHeight(), 0, false);
      g.setColor(12965614);
      g.fillRect(x + 23, y + height - 5, W - 46, 5, false);
      g.setColor(3158064);
      g.drawLine(x + 23, y + height - 1, x + W - 23, y + height - 1, false);
      g.setColor(8620444);
      g.drawLine(x + 23, y + height - 2, x + W - 23, y + height - 2, false);
      g.setColor(8620444);
      g.drawLine(x + 1, y + 20, x + 1, y + height - 5, false);
      if (!CRes.isNullOrEmpty(title)) {
         g.setColor(12965614);
         strW = Font.borderFont.getWidth(title);
         g.fillRect(x + W / 2 - strW / 2 - 5, y + 1, strW + 10, 18, false);
         g.setColor(6985149);
         g.drawRect(x + W / 2 - strW / 2 - 5, y + 1, strW + 10, 17, false);
         Font.borderFont.drawString(g, title, x + W / 2, y + 3, 2, false);
      }

   }

   public static void paintDefaultPopup(int x, int y, int w, int h, mGraphics g) {
      g.setClip(0, 0, 1000, 1000);
      g.setColor(12965614);
      g.fillRect(x + 5, y + 5, w - 10, h - 10, false);
      g.setColor(6457531);
      g.drawRect(x + 6, y + 6, w - 13, h - 13, false);
      g.drawImage(conner, x, y, 0, false);
      g.drawRegion(conner, 0, 0, 14, 14, 5, x + w, y, 24, false);
      g.drawRegion(conner, 0, 0, 14, 14, 3, x + w, y + h, 40, false);
      g.drawRegion(conner, 0, 0, 14, 14, 6, x, y + h, 36, false);
      g.setColor(6457531);
      g.fillRect(x + 14, y + 1, w - 28, 4, false);
      g.fillRect(x + 14, y + h - 6, w - 28, 4, false);
      g.fillRect(x + 1, y + 14, 4, h - 28, false);
      g.fillRect(x + w - 6, y + 14, 4, h - 28, false);
      g.setColor(4678279);
      g.drawRect(x + 14, y + 1, w - 28, 4, false);
      g.drawRect(x + 14, y + h - 6, w - 28, 4, false);
      g.drawRect(x + 1, y + 14, 4, h - 28, false);
      g.drawRect(x + w - 6, y + 14, 4, h - 28, false);
   }

   public void onPointerPressed(int xPress, int yPress, int index) {
      if (CCanvas.keyPressed[5] || getCmdPointerLast((byte)2, index)) {
         this.centerHold = true;
      }

      if (CCanvas.keyPressed[12] || getCmdPointerLast((byte)0, index)) {
         this.leftHold = true;
      }

      if (CCanvas.keyPressed[13] || getCmdPointerLast((byte)1, index)) {
         this.rightHold = true;
      }

      CRes.out("onPointerPressed " + xPress + "," + yPress);
   }

   public void onPointerReleased(int xReleased, int yReleased, int index) {
      this.input(xReleased, yReleased, index);
      this.centerHold = false;
      this.leftHold = false;
      this.rightHold = false;
      CRes.out("onPointerReleased " + xReleased + "," + yReleased);
   }

   public void onPointerDragged(int xDrag, int yDrag, int index) {
      if (CCanvas.keyPressed[5] || getCmdPointerLast((byte)2, index)) {
         this.centerHold = true;
      }

      if (CCanvas.keyPressed[12] || getCmdPointerLast((byte)0, index)) {
         this.leftHold = true;
      }

      if (CCanvas.keyPressed[13] || getCmdPointerLast((byte)1, index)) {
         this.rightHold = true;
      }

   }

   public void onPointerHold(int x, int y2, int index) {
      if (getCmdPointerLast((byte)2, index)) {
         this.centerHold = true;
      }

      if (getCmdPointerLast((byte)0, index)) {
         this.leftHold = true;
      }

      if (getCmdPointerLast((byte)1, index)) {
         this.rightHold = true;
      }

      CRes.out("onPointerHold " + this.leftHold + "," + this.centerHold + "," + this.rightHold);
   }

   public void onKeyPress(int keycode) {
   }

   public void onKeyPressHold(char keyCode) {
   }

   public void onKeyRelease(int keyCode) {
   }

   public byte getIndexSCreen() {
      return this.indexScreen;
   }

   private void input(int x, int y2, int index) {
      if (CCanvas.keyPressed[5] || getCmdPointerLast((byte)2, index)) {
         CCanvas.keyPressed[5] = false;
         if (this.center != null && this.center.action != null) {
            this.centerHold = false;
            this.center.action.perform();
         }
      }

      if (CCanvas.keyPressed[12] || getCmdPointerLast((byte)0, index)) {
         CCanvas.keyPressed[12] = false;
         if (this.left != null && this.left.action != null) {
            this.left.action.perform();
            this.leftHold = false;
         }
      }

      if (CCanvas.keyPressed[13] || getCmdPointerLast((byte)1, index)) {
         CCanvas.keyPressed[13] = false;
         if (this.right != null && this.right.action != null) {
            this.right.action.perform();
            this.rightHold = false;
         }
      }

   }

   public static int Clamp(int value, int min, int max) {
      if (value < min) {
         value = min;
      }

      if (value > max) {
         value = max;
      }

      return value;
   }

   protected void onClose() {
   }
}
