package model;

import CLib.mGraphics;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import coreLG.CCanvas;
import network.Command;
import screen.CScreen;

import javax.swing.*;

public class TField {
    public String name;
    public int x;
    public int y;
    public int width;
    public int height;
    private boolean isFocus;
    public boolean lockArrow = false;
    public boolean paintFocus = true;
    public static int typeXpeed = 2;
    private static final int[] MAX_TIME_TO_CONFIRM_KEY = new int[]{18, 14, 11, 9, 6, 4, 2};
    private static int CARET_HEIGHT = 0;
    private static final int CARET_WIDTH = 1;
    private static final int CARET_SHOWING_TIME = 5;
    private static final int TEXT_GAP_X = 4;
    private static final int MAX_SHOW_CARET_COUNER = 10;
    public static final int INPUT_TYPE_ANY = 0;
    public static final int INPUT_TYPE_NUMERIC = 1;
    public static final int INPUT_TYPE_PASSWORD = 2;
    public static final int INPUT_ALPHA_NUMBER_ONLY = 3;
    private static String[] print = new String[]{"", ""};
    private static String[] printA = new String[]{"0", "1", "abc2", "def3", "ghi4", "jkl5", "mno6", "pqrs7", "tuv8", "wxyz9", "0", "0"};
    private String text = "";
    private String passwordText = "";
    private String paintedText = "";
    private int caretPos = 0;
    private int counter = 0;
    private int maxTextLenght = 500;
    private int offsetX = 0;
    private int lastKey = -1984;
    private int keyInActiveState = 0;
    private int indexOfActiveChar = 0;
    private int showCaretCounter = 10;
    private int inputType = 0;
    public static boolean isQwerty;
    public static int typingModeAreaWidth;
    public static int mode = 0;
    public static final String[] modeNotify = new String[]{"abc", "Abc", "ABC", "123"};
    public static final int NOKIA = 0;
    public static final int MOTO = 1;
    public static final int ORTHER = 2;
    public static int changeModeKey = 11;
    private boolean isVisible = true;
    private static Font curFont;
    private boolean isOpenInput;
    public String title = "";
    public String textPreferent = "";
    public Command cmdClear;
    private CScreen parentScr;
    private String subStringContent = "";
    public String nameDebug = "";
    int holdCount;

    static {
        curFont = Font.normalYFont;
    }

    public TField(CScreen parentScr) {
        this.text = "";
        this.parentScr = parentScr;
        this.init();
    }

    public TField() {
        this.text = "";
        this.init();
    }

    public TField(String text, int maxLen, int inputType) {
        this.text = text;
        this.maxTextLenght = maxLen;
        this.inputType = inputType;
        this.init();
    }

    private void init() {
        curFont = Font.normalYFont;
        CARET_HEIGHT = curFont.getHeight() + 1;
        this.cmdClear = new Command(Language.delete(), new IAction() {
            public void perform() {
                TField.this.clear();
            }
        });
        if (this.parentScr != null) {
            this.parentScr.right = this.cmdClear;
        }

        typingModeAreaWidth = this.width - 13;
        this.subStringContent = "";
    }

    public void doChangeToTextBox() {
        if (!this.isVisible || this.isOpenInput) {
            return;
        }

        this.isOpenInput = true;

        String input = JOptionPane.showInputDialog(null, "Nhập thông tin:");

        if (input != null && !input.isEmpty()) {
            TField.this.text = input;

            if (TField.this.inputType == 2) {
                TField.this.setText(TField.this.text);
            }

            TField.this.subStringContent = TField.this.getSubString(TField.this.width - 10);
        }

        this.isOpenInput = false;
    }

    public void setisFocus(boolean isFocus) {
        this.isFocus = isFocus;
    }

    public void setisVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public void setHint(String utf) {
        this.text = utf;
    }

    public static void setVendorTypeMode(int mode) {
        if (mode == 1) {
            print[0] = "0";
            print[10] = " *";
            print[11] = "#";
            changeModeKey = 35;
        } else if (mode == 0) {
            print[0] = " 0";
            print[10] = "*";
            print[11] = "#";
            changeModeKey = 35;
        } else if (mode == 2) {
            print[0] = "0";
            print[10] = "*";
            print[11] = " #";
            changeModeKey = 42;
        }

    }

    public void clear() {
        if (this.caretPos > 0 && this.text.length() > 0) {
            this.text = this.text.substring(0, this.caretPos - 1) + this.text.substring(this.caretPos, this.text.length());
            --this.caretPos;
            this.setOffset();
            this.setPasswordTest();
            this.subStringContent = this.getSubString(this.width - 10);
        }

    }

    private void keyPressedAny(int keyCode) {
        String[] print;
        if (this.inputType != 2 && this.inputType != 3) {
            print = TField.print;
        } else {
            print = printA;
        }

        char c;
        String ttext;
        if (keyCode == this.lastKey) {
            this.indexOfActiveChar = (this.indexOfActiveChar + 1) % print[keyCode - 48].length();
            c = print[keyCode - 48].charAt(this.indexOfActiveChar);
            if (mode == 0) {
                c = Character.toLowerCase(c);
            } else if (mode == 1) {
                c = Character.toUpperCase(c);
            } else if (mode == 2) {
                c = Character.toUpperCase(c);
            } else {
                c = print[keyCode - 48].charAt(print[keyCode - 48].length() - 1);
            }

            ttext = this.text.substring(0, this.caretPos - 1) + c;
            if (this.caretPos < this.text.length()) {
                ttext = ttext + this.text.substring(this.caretPos, this.text.length());
            }

            this.text = ttext;
            this.keyInActiveState = MAX_TIME_TO_CONFIRM_KEY[typeXpeed];
            this.setPasswordTest();
        } else if (this.text.length() < this.maxTextLenght) {
            if (mode == 1 && this.lastKey != -1984) {
                mode = 0;
            }

            this.indexOfActiveChar = 0;
            c = print[keyCode - 48].charAt(this.indexOfActiveChar);
            if (mode == 0) {
                c = Character.toLowerCase(c);
            } else if (mode == 1) {
                c = Character.toUpperCase(c);
            } else if (mode == 2) {
                c = Character.toUpperCase(c);
            } else {
                c = print[keyCode - 48].charAt(print[keyCode - 48].length() - 1);
            }

            ttext = this.text.substring(0, this.caretPos) + c;
            if (this.caretPos < this.text.length()) {
                ttext = ttext + this.text.substring(this.caretPos, this.text.length());
            }

            this.text = ttext;
            this.keyInActiveState = MAX_TIME_TO_CONFIRM_KEY[typeXpeed];
            ++this.caretPos;
            this.setPasswordTest();
            this.setOffset();
        }

        this.lastKey = keyCode;
    }

    private void keyPressedAscii(int keyCode) {
        if (this.inputType != 2 && this.inputType != 3 || keyCode >= 48 && keyCode <= 57 || keyCode >= 65 && keyCode <= 90 || keyCode >= 97 && keyCode <= 122) {
            if (this.text.length() < this.maxTextLenght) {
                String ttext = this.text.substring(0, this.caretPos) + (char) keyCode;
                if (this.caretPos < this.text.length()) {
                    ttext = ttext + this.text.substring(this.caretPos, this.text.length());
                }

                this.text = ttext;
                ++this.caretPos;
                this.setPasswordTest();
                this.setOffset();
            }

        }
    }

    public void keyHold(int keyCode) {
        ++this.holdCount;
        if (this.holdCount > 15 && !isQwerty && keyCode < print.length) {
            this.clear();
            this.keyPressedAscii(print[keyCode].charAt(print[keyCode].length() - 1));
            this.keyInActiveState = MAX_TIME_TO_CONFIRM_KEY[typeXpeed];
            this.holdCount = 0;
        }

        if (this.holdCount > 20 && keyCode == 19) {
            this.setText("");
            this.holdCount = 0;
        }

    }

    public boolean keyPressed(int keyCode) {
        if (keyCode != 8 && keyCode != -8 && keyCode != 204) {
            this.holdCount = 0;
            if (keyCode >= 65 && keyCode <= 122) {
                isQwerty = true;
                typingModeAreaWidth = 0;
            }

            if (isQwerty) {
                if (keyCode == 45) {
                    if (keyCode == this.lastKey && this.keyInActiveState < MAX_TIME_TO_CONFIRM_KEY[typeXpeed]) {
                        this.text = this.text.substring(0, this.caretPos - 1) + '_';
                        this.paintedText = this.text;
                        this.setPasswordTest();
                        this.setOffset();
                        this.lastKey = -1984;
                        return false;
                    }

                    this.lastKey = 45;
                }

                if (keyCode >= 32) {
                    this.keyPressedAscii(keyCode);
                    return false;
                }
            }

            if (keyCode == changeModeKey) {
                ++mode;
                if (mode > 3) {
                    mode = 0;
                }

                this.keyInActiveState = 1;
                this.lastKey = keyCode;
                return false;
            } else {
                if (keyCode == 42) {
                    keyCode = 58;
                }

                if (keyCode == 35) {
                    keyCode = 59;
                }

                if (keyCode >= 48 && keyCode <= 59) {
                    if (this.inputType != 0 && this.inputType != 2 && this.inputType != 3) {
                        if (this.inputType == 1) {
                            this.keyPressedAscii(keyCode);
                            this.keyInActiveState = 1;
                        }
                    } else {
                        this.keyPressedAny(keyCode);
                    }
                } else {
                    this.indexOfActiveChar = 0;
                    this.lastKey = -1984;
                    if (keyCode == 14 && !this.lockArrow) {
                        if (this.caretPos > 0) {
                            --this.caretPos;
                            this.setOffset();
                            this.showCaretCounter = 10;
                            return false;
                        }
                    } else if (keyCode == 15 && !this.lockArrow) {
                        if (this.caretPos < this.text.length()) {
                            ++this.caretPos;
                            this.setOffset();
                            this.showCaretCounter = 10;
                            return false;
                        }
                    } else {
                        if (keyCode == 19) {
                            this.clear();
                            return false;
                        }

                        this.lastKey = keyCode;
                    }
                }

                return true;
            }
        } else {
            this.clear();
            return true;
        }
    }


    public void paint(mGraphics g) {
        if (this.isVisible) {
            boolean isFocus = this.isFocused();
            if (this.inputType == 2) {
                this.paintedText = this.passwordText;
            } else {
                this.paintedText = this.text;
            }

            g.setColor(1521982);
            if (isFocus) {
                if (this.paintFocus) {
                    g.setColor(4156571);
                    g.fillRect(this.x + 1, this.y + 1 - 2, this.width - 1 + 2, this.height - 1, false);
                }
            } else {
                g.setColor(1521982);
                g.fillRect(this.x + 1, this.y + 1 - 2, this.width - 1 + 2, this.height - 1, false);
            }

            int xPaintText = 4 + this.offsetX + this.x + 1;
            curFont.drawString(g, this.subStringContent, this.x + 5, this.y + 2, 0);
            if (isFocus && this.keyInActiveState == 0 && (this.showCaretCounter > 0 || this.counter / 5 % 2 == 0)) {
                this.caretPos = this.paintedText.length();
                g.setColor(16777215);
                int xGap = 4 + this.x + curFont.getWidth(this.subStringContent) - 1 + 1;
                int yGap = this.y + (this.height - CARET_HEIGHT) / 2 - 1;
                if (xGap > this.x + 1 + this.width - 2) {
                    xGap = this.x + 1 + this.width - 2;
                }

                g.fillRect(xGap, yGap, 1, CARET_HEIGHT, false);
            }

        }
    }

    private String getSubString(int sizeWidth) {
        String subStringDes = "";

        for (int i = this.text.length() - 1; i >= 0; --i) {
            if (this.inputType == 2) {
                subStringDes = subStringDes + "*";
            } else {
                subStringDes = this.text.charAt(i) + subStringDes;
            }

            if (curFont.getWidth(subStringDes) > sizeWidth) {
                break;
            }
        }

        return subStringDes;
    }

    public void setOffset() {
        if (this.inputType == 2) {
            this.paintedText = this.passwordText;
        } else {
            this.paintedText = this.text;
        }

    }

    private void updateOffsetx() {
        if (this.caretPos > this.paintedText.length()) {
            this.caretPos = this.paintedText.length();
        }

        if (this.offsetX < 0 && curFont.getWidth(this.paintedText) + this.offsetX < this.width - 4 - 13 - typingModeAreaWidth) {
            this.offsetX = this.width - 10 - typingModeAreaWidth - curFont.getWidth(this.paintedText);
        }

        if (this.offsetX + curFont.getWidth(this.paintedText.substring(0, this.caretPos)) <= 0) {
            this.offsetX = -curFont.getWidth(this.paintedText.substring(0, this.caretPos));
            this.offsetX += 40;
        } else if (this.offsetX + curFont.getWidth(this.paintedText.substring(0, this.caretPos)) >= this.width - 12 - typingModeAreaWidth) {
            this.offsetX = this.width - 10 - typingModeAreaWidth - curFont.getWidth(this.paintedText.substring(0, this.caretPos)) - 8;
        }

        if (this.offsetX > 0) {
            this.offsetX = 0;
        }

    }

    public boolean isFocused() {
        return this.isFocus;
    }

    private void setPasswordTest() {
        if (this.inputType == 2) {
            this.passwordText = "";

            for (int i = 0; i < this.text.length(); ++i) {
                this.passwordText = this.passwordText + "*";
            }

            if (this.keyInActiveState > 0 && this.caretPos > 0) {
                this.passwordText = this.passwordText.substring(0, this.caretPos - 1) + this.text.charAt(this.caretPos - 1) + this.passwordText.substring(this.caretPos, this.passwordText.length());
            }
        }

    }

    public void update() {
        if (this.isVisible) {
            ++this.counter;
            if (this.keyInActiveState > 0) {
                --this.keyInActiveState;
                if (this.keyInActiveState == 0) {
                    this.indexOfActiveChar = 0;
                    if (mode == 1 && this.lastKey != changeModeKey) {
                        mode = 0;
                    }

                    this.lastKey = -1984;
                    this.setPasswordTest();
                }
            }

            if (this.showCaretCounter > 0) {
                --this.showCaretCounter;
            }

            this.updateOffsetx();
        }
    }

    public void setTextBox() {
        if (CCanvas.isPointer(this.x, this.y, this.width, this.height, 0)) {
            if (!this.isFocus) {
                this.isFocus = true;
            } else {
                this.doChangeToTextBox();
            }
        } else {
            this.isFocus = false;
        }

    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        if (text != null) {
            this.lastKey = -1984;
            this.keyInActiveState = 0;
            this.indexOfActiveChar = 0;
            this.text = text;
            this.paintedText = text;
            this.caretPos = text.length();
            this.subStringContent = this.getSubString(this.width - 10);
            this.setPasswordTest();
            this.setOffset();
        }
    }

    public void insertText(String text) {
        this.text = this.text.substring(0, this.caretPos) + text + this.text.substring(this.caretPos);
        this.caretPos += text.length();
        this.setPasswordTest();
        this.setOffset();
    }

    public int getMaxTextLenght() {
        return this.maxTextLenght;
    }

    public void setMaxTextLenght(int maxTextLenght) {
        this.maxTextLenght = maxTextLenght;
    }

    public int getIputType() {
        return this.inputType;
    }

    public void setIputType(int iputType) {
        this.inputType = iputType;
    }

    public boolean isNotNumber() {
        try {
            int number = Integer.parseInt(this.getText());
            return false;
        } catch (Exception var2) {
            return true;
        }
    }

    public boolean isNonOrEmpty(String textInput) {
        return false;
    }

    public void resetTextBox() {
        this.text = "";
        this.title = "";
        this.textPreferent = "";
        this.paintedText = "";
        this.subStringContent = "";
        this.offsetX = 0;
        this.caretPos = 0;
    }
}
