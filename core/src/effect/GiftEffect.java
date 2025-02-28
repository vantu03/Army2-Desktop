package effect;

import CLib.mGraphics;
import Equipment.Equip;
import coreLG.CCanvas;
import model.Font;

public class GiftEffect {
    public String text;
    public Equip equip;
    int xFly;
    int yFly;
    int tFly = 0;
    public boolean isFly;

    public GiftEffect(String text, Equip equip) {
        this.text = text;
        this.equip = equip;
        this.xFly = CCanvas.width / 2;
        this.yFly = CCanvas.hieght - 50;
    }

    public void paint(mGraphics g) {
        if (this.isFly) {
            if (this.equip == null) {
                Font.borderFont.drawString(g, this.text, this.xFly + Camera.x, this.yFly + Camera.y, 3);
            }

            if (this.equip != null) {
                Font.borderFont.drawString(g, this.text, this.xFly + Camera.x, this.yFly + Camera.y, 3);
                int X = CCanvas.width / 2 + Camera.x;
                int Y = this.yFly + Camera.y + 17;
                g.setColor(4156571);
                g.fillRoundRect(X - 2, Y - 2, 20, 20, 4, 4, false);
                g.setColor(16774532);
                g.fillRect(X - 1, Y - 1, 18, 18, false);
                this.equip.drawIcon(g, X, Y, false);
            }
        }

    }

    public void update() {
        if (this.isFly) {
            ++this.tFly;
            this.yFly -= 2;
            if (this.tFly == 80) {
                this.tFly = 0;
                CCanvas.gameScr.vGift.removeElement(this);
            }
        }

    }
}
