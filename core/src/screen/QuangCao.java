package screen;

import CLib.mGraphics;
import coreLG.CCanvas;
import model.Font;
import model.IAction;
import model.Language;
import network.Command;

public class QuangCao extends CScreen {
   public static String content;
   public static String link;
   public static String[] contents;

   public QuangCao() {
      this.nameCScreen = " QuangCao screen!";
   }

   public void getCommand() {
      contents = Font.borderFont.splitFontBStrInLine(content, CCanvas.width - 30);
      this.right = new Command(Language.exit(), new IAction() {
         public void perform() {
            CCanvas.menuScr.show();
         }
      });
      this.center = new Command("Tải game", new IAction() {
         public void perform() {
         }
      });
   }

   public void paint(mGraphics g) {
      paintDefaultBg(g);
      if (contents != null) {
         for(int i = 0; i < contents.length; ++i) {
            Font.borderFont.drawString(g, contents[i], CCanvas.width / 2, 30 + i * 20, 2);
         }
      }

      super.paint(g);
   }
}
