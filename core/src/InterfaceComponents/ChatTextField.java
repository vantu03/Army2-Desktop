package InterfaceComponents;

import CLib.TField;
import CLib.mGraphics;
import coreLG.CCanvas;

public class ChatTextField {
    public TField tfChat;
    public static ChatTextField instance;
    public static boolean isShow = false;
    byte typeTF;

    public static ChatTextField gI() {
        return instance == null ? (instance = new ChatTextField()) : instance;
    }

    public void setChat(byte type) {
        isShow = !isShow;
        this.typeTF = type;
        if (isShow) {
            this.tfChat.setPoiter();
        }

    }

    public void commandTab(int index, int subIndex) {
        switch (index) {
            case 0:
                this.tfChat.setText("");
                isShow = false;
                if (!CCanvas.isTouch) {
                    this.tfChat.setFocus(true);
                }
                break;
            case 1:
                this.sendChat();
        }

    }

    protected ChatTextField() {
    }

    public void init() {
        this.tfChat.width = CCanvas.hw;
    }

    public void keyPressed(int keyCode) {
        this.tfChat.keyPressed(keyCode);
    }

    public void updatekey() {
        this.tfChat.update();
    }

    public void paint(mGraphics g) {
        this.tfChat.paint(g);
    }

    public void updatePointer() {
    }

    public void sendChat() {
        isShow = false;
    }
}
