package CLib;

public class mFont {
   public static final int LEFT = 0;
   public static final byte RIGHT = 1;
   public static final byte CENTER = 2;
   public static final byte RED = 0;
   public static final byte YELLOW = 1;
   public static final byte GREEN = 2;
   public static final byte FATAL = 3;
   public static final byte MISS = 4;
   public static final byte ORANGE = 5;
   public static final byte ADDMONEY = 6;
   public static final byte MISS_ME = 7;
   public static final byte FATAL_ME = 8;
   private int space;
   private int height;
   private mImage imgFont;
   private String strFont;
   private int[][] fImages;
   public static String str = " 0123456789+-*='_?.,<>/[]{}!@#$%^&*():aÃ¡Ã áº£Ã£áº¡Ã¢áº¥áº§áº©áº«áº\u00adÄƒáº¯áº±áº³áºµáº·bcdÄ‘eÃ©Ã¨áº»áº½áº¹Ãªáº¿á»�á»ƒá»…á»‡fghiÃ\u00adÃ¬á»‰Ä©á»‹jklmnoÃ³Ã²á»�Ãµá»�Ã´á»‘á»“á»•á»—á»™Æ¡á»›á»�á»Ÿá»¡á»£pqrstuÃºÃ¹á»§Å©á»¥Æ°á»©á»«á»\u00adá»¯á»±vxyÃ½á»³á»·á»¹á»µzwAÃ�Ã€áº¢Ãƒáº Ä‚áº°áº®áº²áº´áº¶Ã‚áº¤áº¦áº¨áºªáº¬BCDÄ�EÃ‰Ãˆáººáº¼áº¸ÃŠáº¾á»€á»‚á»„á»†FGHIÃ�ÃŒá»ˆÄ¨á»ŠJKLMNOÃ“Ã’á»ŽÃ•á»ŒÃ”á»�á»’á»”á»–á»˜Æ á»šá»œá»žá» á»¢PQRSTUÃšÃ™á»¦Å¨á»¤Æ¯á»¨á»ªá»¬á»®á»°VXYÃ�á»²á»¶á»¸á»´ZW";
   public static mFont tahoma_7b_orange;
   public static mFont tahoma_7b_blue;
   public static mFont tahoma_7b_black;
   public static mFont tahoma_7b_yellow;
   public static mFont tahoma_7b_violet;
   public static mFont tahoma_7b_white;
   public static mFont tahoma_7b_green;
   public static mFont tahoma_7b_red;
   public static mFont tahoma_7b_brown;
   public static mFont tahoma_7_black;
   public static mFont tahoma_7_white;
   public static mFont tahoma_7_yellow;
   public static mFont tahoma_7_orange;
   public static mFont tahoma_7_red;
   public static mFont tahoma_7_blue;
   public static mFont tahoma_7_green;
   public static mFont tahoma_7_violet;
   public static mFont number_yellow;
   public static mFont number_red;
   public static mFont number_green;
   public static mFont number_white;
   public static mFont number_orange;
   public static mFont number_Yellow_Small;
   public static mFont tahoma_8b_brown;
   public static mFont tahoma_8b_black;
   public static mFont tahoma_7_gray;
   SysTemFont temfont;
   String pathImage;
   public static int[] colorJava = new int[]{-90838, -9265665, -15527149, -197061, -4947201, -1, -10035407, -12052464, -65536, -15527149, -1, -197061, -90838, -65536, -9265665, -10035407, -4947201, -197061, -65536, -10035407, -1, -90838, -197061, -4819663, -15527149, -10000537};
   public static int[] colorJava1 = new int[]{-90838, -9265665, -15527149, -197061, -4947201, -1, -10035407, -12052464, -65536, -15527149, -1, -197061, -90838, -65536, -9265665, -10035407, -4947201, -197061, -65536, -10035407, -1, -90838, -197061, -4819663, -15527149, -10000537};
   public SysTemFont f;

   public static void loadmFont() {
      tahoma_7b_orange = new mFont(0, -90838);
      tahoma_7b_blue = new mFont(1, -9265665);
      tahoma_7b_black = new mFont(2, -15527149);
      tahoma_7b_yellow = new mFont(3, -197061);
      tahoma_7b_violet = new mFont(4, -4947201);
      tahoma_7b_white = new mFont(5, -1);
      tahoma_7b_green = new mFont(6, -10035407);
      tahoma_7b_brown = new mFont(7, -12052464);
      tahoma_7b_red = new mFont(8, -65536);
      tahoma_7_black = new mFont(9, -15527149);
      tahoma_7_white = new mFont(10, -1);
      tahoma_7_yellow = new mFont(11, -197061);
      tahoma_7_orange = new mFont(12, -90838);
      tahoma_7_red = new mFont(13, -65536);
      tahoma_7_blue = new mFont(14, -9265665);
      tahoma_7_green = new mFont(15, -10035407);
      tahoma_7_violet = new mFont(21, -4947201);
      number_yellow = new mFont(16, -197061);
      number_red = new mFont(17, -65536);
      number_green = new mFont(18, -10035407);
      number_white = new mFont(19, -1);
      number_orange = new mFont(20, -90838);
      number_Yellow_Small = new mFont(22, -197061);
      tahoma_8b_brown = new mFont(30, -4819663);
      tahoma_8b_black = new mFont(31, -15527149);
      tahoma_7_gray = new mFont(10, -10000537);
   }

   public int setColoFont(int id) {
      return colorJava[id + 1];
   }

   public mFont(int ID, int color) {
      this.f = new SysTemFont(ID, color);
   }

   public mFont(String charList, byte[] charWidth, int charHeight, String fontFile, int charSpace, int ID) {
      this.f = new SysTemFont(ID, this.setColoFont(ID));
   }

   public void reloadImage() {
   }

   public void freeImage() {
   }

   public int getHeight() {
      return this.f.getHeight();
   }

   public void setHeight(int height) {
      this.height = height;
   }

   public int getWidth(String st) {
      return this.f.getWidth(st);
   }

   public void drawString(mGraphics g, String st, int x, int y, int align, boolean isClip) {
      this.f.drawString(g, st, x, y, align, isClip);
   }

   public mVector splitFontVector(String src, int lineWidth) {
      return this.f.splitFontVector(src, lineWidth);
   }

   public static String[] split(String original, String separator) {
      mVector nodes = new mVector();

      for(int index = original.indexOf(separator); index >= 0; index = original.indexOf(separator)) {
         nodes.addElement(original.substring(0, index));
         original = original.substring(index + separator.length());
      }

      nodes.addElement(original);
      String[] result = new String[nodes.size()];
      if (nodes.size() > 0) {
         for(int loop = 0; loop < nodes.size(); ++loop) {
            result[loop] = (String)nodes.elementAt(loop);
         }
      }

      return result;
   }

   public String splitFirst(String str) {
      String line = "";
      boolean isSplit = false;

      for(int i = 0; i < str.length(); ++i) {
         if (!isSplit) {
            String strEnd = str.substring(i, str.length());
            if (this.compare(strEnd, " ")) {
               line = line + str.charAt(i) + "-";
            } else {
               line = line + strEnd;
            }

            isSplit = true;
         } else if (str.charAt(i) == ' ') {
            isSplit = false;
         }
      }

      return line;
   }

   public String[] splitFontArray(String src, int lineWidth) {
      mVector lines = this.splitFontVector(src, lineWidth);
      String[] arr = new String[lines.size()];

      for(int i = 0; i < lines.size(); ++i) {
         arr[i] = lines.elementAt(i).toString();
      }

      return arr;
   }

   public boolean compare(String strSource, String str) {
      for(int i = 0; i < strSource.length(); ++i) {
         if (String.valueOf(strSource.charAt(i)).equals(str)) {
            return true;
         }
      }

      return false;
   }
}
