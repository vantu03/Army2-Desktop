package CLib;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.teamobi.mobiarmy2.TemCanvas;
import com.teamobi.mobiarmy2.TemMidlet;

public class TField {
   public int x;
   public int y;
   public int width;
   public int height;
   public int widthTouch;
   public boolean isFocus;
   public boolean lockArrow;
   public boolean paintFocus;
   public boolean isChangeFocus;
   public static int typeXpeed = 2;
   public static final int CARET_WIDTH = 1;
   public static final int CARET_SHOWING_TIME = 5;
   public static final int TEXT_GAP_X = 4;
   private static final int MAX_SHOW_CARET_COUNER = 10;
   public static final int INPUT_TYPE_ANY = 0;
   public static final int INPUT_TYPE_NUMERIC = 1;
   public static final int INPUT_TYPE_PASSWORD = 2;
   public static final int INPUT_ALPHA_NUMBER_ONLY = 3;
   private String text;
   private String passwordText;
   public String paintedText;
   public int caretPos;
   public int counter;
   private int maxTextLenght;
   public int offsetX;
   public boolean isCloseKey;
   public int keyInActiveState;
   public int showCaretCounter;
   public int inputType;
   public static boolean isQwerty;
   public static int typingModeAreaWidth;
   public static int mode = 0;
   public static final String[] modeNotify = new String[]{"abc", "Abc", "ABC", "123"};
   public static final int NOKIA = 0;
   public static final int MOTO = 1;
   public static final int ORTHER = 2;
   public static int changeModeKey = 11;
   public static TemCanvas c;
   public static TemMidlet m;
   public static int timeChangeMode;
   public static int xDu;
   public static int yDu;
   public String sDefaust;
   public static boolean isOpenTextBox;
   public boolean visible;
   public String title;
   public String strnull;
   public myEditText editText;
   public int ID;
   boolean isposition;
   int yt;
   int tempTime;
   int xCamText;
   int timeFocus;

   public void doChangeToTextBox() {
      Gdx.input.getTextInput(new Input.TextInputListener() {
         public void input(String text) {
            TField.this.setText(text);
            TField.this.isFocus = false;
         }

         public void canceled() {
            TField.this.isFocus = false;
         }
      }, this.strnull, "", "");
   }

   public static int getHeight() {
      return 20;
   }

   public void positionLogin() {
   }

   public static boolean setNormal(char ch) {
      return ch >= '0' && ch <= '9' || ch >= 'A' && ch <= 'Z' || ch >= 'a' && ch <= 'z';
   }

   public static void setVendorTypeMode(int may) {
   }

   private void init() {
   }

   public TField() {
      this.width = 120 * mGraphics.zoomLevel;
      this.height = 30 * mGraphics.zoomLevel;
      this.widthTouch = 0;
      this.lockArrow = false;
      this.paintFocus = true;
      this.isChangeFocus = true;
      this.text = "";
      this.passwordText = "";
      this.paintedText = "";
      this.caretPos = 0;
      this.counter = 0;
      this.maxTextLenght = 500;
      this.offsetX = 0;
      this.isCloseKey = true;
      this.keyInActiveState = 0;
      this.showCaretCounter = 10;
      this.inputType = 0;
      this.sDefaust = "";
      this.visible = false;
      this.title = "";
      this.strnull = "";
      this.isposition = false;
      this.yt = 0;
      this.tempTime = -1;
      this.xCamText = 0;
      this.timeFocus = 0;
      this.text = "";
      this.init();
      this.setheightText();
   }

   public TField(int x, int y) {
      this.width = 120 * mGraphics.zoomLevel;
      this.height = 30 * mGraphics.zoomLevel;
      this.widthTouch = 0;
      this.lockArrow = false;
      this.paintFocus = true;
      this.isChangeFocus = true;
      this.text = "";
      this.passwordText = "";
      this.paintedText = "";
      this.caretPos = 0;
      this.counter = 0;
      this.maxTextLenght = 500;
      this.offsetX = 0;
      this.isCloseKey = true;
      this.keyInActiveState = 0;
      this.showCaretCounter = 10;
      this.inputType = 0;
      this.sDefaust = "";
      this.visible = false;
      this.title = "";
      this.strnull = "";
      this.isposition = false;
      this.yt = 0;
      this.tempTime = -1;
      this.xCamText = 0;
      this.timeFocus = 0;
      this.text = "";
      this.x = x;
      this.y = y;
      this.init();
      this.setFocus(false);
      this.setheightText();
   }

   public TField(int x, int y, int width) {
      this.width = 120 * mGraphics.zoomLevel;
      this.height = 30 * mGraphics.zoomLevel;
      this.widthTouch = 0;
      this.lockArrow = false;
      this.paintFocus = true;
      this.isChangeFocus = true;
      this.text = "";
      this.passwordText = "";
      this.paintedText = "";
      this.caretPos = 0;
      this.counter = 0;
      this.maxTextLenght = 500;
      this.offsetX = 0;
      this.isCloseKey = true;
      this.keyInActiveState = 0;
      this.showCaretCounter = 10;
      this.inputType = 0;
      this.sDefaust = "";
      this.visible = false;
      this.title = "";
      this.strnull = "";
      this.isposition = false;
      this.yt = 0;
      this.tempTime = -1;
      this.xCamText = 0;
      this.timeFocus = 0;
      this.text = "";
      this.x = x;
      this.y = y;
      this.width = width;
      this.widthTouch = 0;
      this.init();
      this.setFocus(false);
      this.setheightText();
   }

   public TField(int x, int y, int width, int widthTouch) {
      this.width = 120 * mGraphics.zoomLevel;
      this.height = 30 * mGraphics.zoomLevel;
      this.widthTouch = 0;
      this.lockArrow = false;
      this.paintFocus = true;
      this.isChangeFocus = true;
      this.text = "";
      this.passwordText = "";
      this.paintedText = "";
      this.caretPos = 0;
      this.counter = 0;
      this.maxTextLenght = 500;
      this.offsetX = 0;
      this.isCloseKey = true;
      this.keyInActiveState = 0;
      this.showCaretCounter = 10;
      this.inputType = 0;
      this.sDefaust = "";
      this.visible = false;
      this.title = "";
      this.strnull = "";
      this.isposition = false;
      this.yt = 0;
      this.tempTime = -1;
      this.xCamText = 0;
      this.timeFocus = 0;
      this.text = "";
      this.x = x;
      this.y = y;
      this.width = width;
      this.widthTouch = widthTouch;
      this.init();
      this.setFocus(false);
      this.setheightText();
   }

   public TField(String text, int maxLen, int inputType) {
      this.width = 120 * mGraphics.zoomLevel;
      this.height = 30 * mGraphics.zoomLevel;
      this.widthTouch = 0;
      this.lockArrow = false;
      this.paintFocus = true;
      this.isChangeFocus = true;
      this.text = "";
      this.passwordText = "";
      this.paintedText = "";
      this.caretPos = 0;
      this.counter = 0;
      this.maxTextLenght = 500;
      this.offsetX = 0;
      this.isCloseKey = true;
      this.keyInActiveState = 0;
      this.showCaretCounter = 10;
      this.inputType = 0;
      this.sDefaust = "";
      this.visible = false;
      this.title = "";
      this.strnull = "";
      this.isposition = false;
      this.yt = 0;
      this.tempTime = -1;
      this.xCamText = 0;
      this.timeFocus = 0;
      this.text = text;
      this.maxTextLenght = maxLen;
      this.inputType = inputType;
      this.setheightText();
      this.init();
   }

   public void setheightText() {
      this.height = 20;
   }

   public void setStringNull(String str) {
      this.strnull = str;
   }

   public void clear() {
      if (this.caretPos > 0 && this.text.length() > 0) {
         this.text = this.text.substring(0, this.caretPos - 1) + this.text.substring(this.caretPos, this.text.length());
         --this.caretPos;
         this.setOffset();
         this.setPasswordTest();
      }

   }

   public void setPoiter() {
      isOpenTextBox = true;
      this.doChangeToTextBox();
   }

   public void setOffset() {
      if (this.inputType == 2) {
         this.paintedText = this.passwordText;
      } else {
         this.paintedText = this.text;
      }

      if (this.offsetX < 0 && mFont.tahoma_8b_brown.getWidth(this.paintedText) + this.offsetX < this.width - 4 - 13 - typingModeAreaWidth) {
         this.offsetX = this.width - 10 - typingModeAreaWidth - mFont.tahoma_8b_brown.getWidth(this.paintedText);
      }

      if (this.offsetX + mFont.tahoma_7_white.getWidth(this.paintedText.substring(0, this.caretPos)) <= 0) {
         this.offsetX = -mFont.tahoma_8b_brown.getWidth(this.paintedText.substring(0, this.caretPos));
         this.offsetX += 40;
      } else if (this.offsetX + mFont.tahoma_8b_brown.getWidth(this.paintedText.substring(0, this.caretPos)) >= this.width - 12 - typingModeAreaWidth) {
         this.offsetX = this.width - 10 - typingModeAreaWidth - mFont.tahoma_8b_brown.getWidth(this.paintedText.substring(0, this.caretPos)) - 8;
      }

      if (this.offsetX > 0) {
         this.offsetX = 0;
      }

   }

   public void keyHold(int keyCode) {
   }

   public boolean keyPressed(int keyCode) {
      return true;
   }

   public void pointerRelease(int px, int py) {
   }

   public void paint(mGraphics g) {
      if (this.inputType == 2) {
         this.paintedText = this.passwordText;
      } else {
         this.paintedText = this.text;
      }

      this.paintTf(g);
   }

   public void paintByList(mGraphics g) {
      if (this.inputType == 2) {
         this.paintedText = this.passwordText;
      } else {
         this.paintedText = this.text;
      }

      this.paintTfByList(g);
   }

   public void paintTfByList(mGraphics g) {
      boolean isFocus = this.isFocused();
      int ypass = 0;
      mFont f = mFont.tahoma_8b_black;
      if (this.inputType == 2) {
         this.paintedText = this.passwordText;
         ypass = 2;
      } else {
         this.paintedText = this.text;
      }

      int xLine = 0;
      g.setColor(-4155296);
      ++this.timeFocus;
      if (isFocus) {
         int le = this.paintedText.length();
         if (le > 0 && this.caretPos > 0) {
            xLine = mFont.tahoma_8b_black.getWidth(this.paintedText.substring(0, this.caretPos));
         }
      }

      if (this.paintedText.length() == 0 && !isFocus) {
         ypass = 0;
         this.paintedText = this.strnull;
         f = mFont.tahoma_8b_brown;
      }

      f.drawString(g, this.paintedText, this.x + 4, this.y + this.height / 2 - 5 + ypass, 0, true);
      if (isFocus && this.timeFocus % 10 > 6) {
         g.setColor(0);
         g.fillRect(this.x + 3 + xLine, this.y + this.height / 2 - 7, 1, 14, false);
      }

   }

   public void paintTf(mGraphics g) {
      boolean isFocus = this.isFocused();
      int ypass = 0;
      mFont f = mFont.tahoma_8b_black;
      if (this.inputType == 2) {
         this.paintedText = this.passwordText;
         ypass = 2;
      } else {
         this.paintedText = this.text;
      }

      int xLine = 0;
      g.setColor(-4155296);
      ++this.timeFocus;
      int xg;
      if (isFocus) {
         xg = this.paintedText.length();
         if (xg > 0 && this.caretPos > 0) {
            xLine = mFont.tahoma_8b_black.getWidth(this.paintedText.substring(0, this.caretPos));
         }
      }

      g.setClip(this.x + 2, this.y + 2, this.width - 4, this.height - 3);
      xg = g.getTranslateX();
      int yg = g.getTranslateY();
      g.translate(-this.xCamText, 0);
      if (this.paintedText.length() == 0 && !isFocus) {
         ypass = 0;
         this.paintedText = this.strnull;
         f = mFont.tahoma_8b_brown;
      }

      f.drawString(g, this.paintedText, this.x + 4, this.y + this.height / 2 - 5 + ypass, 0, true);
      if (isFocus && this.timeFocus % 10 > 6) {
         g.setColor(0);
         g.fillRect(this.x + 3 + xLine, this.y + this.height / 2 - 7, 1, 14, false);
      }

      g.translate(xg, yg);
   }

   public boolean isFocused() {
      return this.isFocus;
   }

   private void setPasswordTest() {
      if (this.inputType == 2) {
         this.passwordText = "";

         for(int i = 0; i < this.text.length(); ++i) {
            this.passwordText = this.passwordText + "*";
         }

         if (this.keyInActiveState > 0 && this.caretPos > 0) {
            this.passwordText = this.passwordText.substring(0, this.caretPos - 1) + this.text.charAt(this.caretPos - 1) + this.passwordText.substring(this.caretPos, this.passwordText.length());
         }
      }

   }

   public void update() {
      if (this.isFocused()) {
         String str;
         if (this.inputType == 2) {
            str = this.passwordText;
         } else {
            str = this.text;
         }

         this.xCamText = -this.width / 2 + this.caretPos * 5 + 4;
         int max = mFont.tahoma_8b_black.getWidth(str) - this.width + 8;
         if (this.xCamText > max) {
            this.xCamText = max;
         }

         if (this.xCamText < 0) {
            this.xCamText = 0;
         }
      } else {
         this.xCamText = 0;
      }

   }

   public void updatePoiter() {
   }

   public void updatepointerByList() {
      this.doChangeToTextBox();
   }

   public void setTextBox() {
   }

   public String getText() {
      return this.text;
   }

   public void setText(String text) {
      if (text != null) {
         this.text = text;
         this.paintedText = text;
         this.setPasswordTest();
         this.caretPos = text.length();
         this.setOffset();
      }
   }

   public void insertText(String text) {
      this.text = this.text.substring(0, this.caretPos) + text + this.text.substring(this.caretPos);
      this.setPasswordTest();
      this.caretPos += text.length();
      this.setOffset();
   }

   public int getMaxTextLenght() {
      return this.maxTextLenght;
   }

   public void setMaxTextLenght(int max) {
      this.maxTextLenght = max;
   }

   public int getIputType() {
      return this.inputType;
   }

   public void setIputType(int iputType) {
      this.inputType = iputType;
   }

   public void setFocus(boolean b) {
      this.isFocus = b;
   }
}
