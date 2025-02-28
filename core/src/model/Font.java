package model;

import CLib.Image;
import CLib.mGraphics;
import CLib.mImage;
import java.util.Vector;

public class Font {
   public static final int LEFT = 0;
   public static final int RIGHT = 1;
   public static final int CENTER = 2;
   public mImage imgFont;
   private String charList;
   public byte[] charWidth;
   private int charHeight;
   private int charSpace;
   public String nameFont;
   public int imgWidth;
   public static Font normalFont = new Font(" 0123456789.,:!?()+*$#/-%abcdefghijklmnopqrstuvwxyzáàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵđABCDEFGHIJKLMNOPQRSTUVWXYZĐ$", new byte[]{4, 6, 5, 6, 6, 7, 6, 6, 6, 6, 6, 3, 3, 3, 4, 5, 4, 4, 6, 5, 8, 8, 6, 6, 10, 6, 7, 5, 7, 6, 4, 7, 7, 3, 4, 6, 3, 9, 7, 7, 7, 7, 5, 5, 4, 7, 6, 9, 6, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 6, 6, 3, 3, 3, 5, 3, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 7, 7, 7, 7, 8, 7, 7, 7, 7, 7, 6, 6, 7, 7, 3, 5, 7, 6, 10, 8, 7, 7, 7, 6, 7, 7, 7, 7, 9, 7, 7, 8, 8, 6}, 13, "fb.png", 0);
   public static Font normalYFont = new Font(" 0123456789.,:!?()+*$#/-%abcdefghijklmnopqrstuvwxyzáàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵđABCDEFGHIJKLMNOPQRSTUVWXYZĐ$@", new byte[]{4, 6, 5, 6, 6, 7, 6, 6, 6, 6, 6, 3, 3, 3, 4, 5, 4, 4, 6, 5, 8, 8, 6, 6, 10, 6, 7, 5, 7, 6, 4, 7, 7, 3, 4, 6, 3, 9, 7, 7, 7, 7, 5, 5, 4, 7, 6, 9, 6, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 6, 6, 3, 3, 3, 5, 3, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 7, 7, 7, 7, 8, 7, 7, 7, 7, 7, 6, 6, 7, 7, 3, 5, 7, 6, 10, 8, 7, 7, 7, 6, 7, 7, 7, 7, 9, 7, 7, 8, 8, 6, 9}, 13, "fb2.png", 0);
   public static Font normalGFont = new Font(" 0123456789.,:!?()+*$#/-%abcdefghijklmnopqrstuvwxyzáàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵđABCDEFGHIJKLMNOPQRSTUVWXYZÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴĐ$", new byte[]{4, 6, 5, 6, 6, 7, 6, 6, 6, 6, 6, 3, 3, 3, 4, 5, 4, 4, 6, 5, 8, 8, 6, 6, 10, 6, 7, 5, 7, 6, 4, 7, 7, 3, 4, 6, 3, 9, 7, 7, 7, 7, 5, 5, 4, 7, 6, 9, 6, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 6, 6, 3, 3, 3, 5, 3, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 7, 7, 7, 7, 8, 7, 7, 7, 7, 7, 6, 6, 7, 7, 3, 5, 7, 6, 10, 8, 7, 7, 7, 6, 7, 7, 7, 7, 9, 7, 7, 8, 8, 6}, 13, "fb3.png", 0);
   public static Font normalRFont = new Font(" 0123456789.,:!?()+*$#/-%abcdefghijklmnopqrstuvwxyzáàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵđABCDEFGHIJKLMNOPQRSTUVWXYZĐ$", new byte[]{4, 6, 5, 6, 6, 7, 6, 6, 6, 6, 6, 3, 3, 3, 4, 5, 4, 4, 6, 5, 8, 8, 6, 6, 10, 6, 7, 5, 7, 6, 4, 7, 7, 3, 4, 6, 3, 9, 7, 7, 7, 7, 5, 5, 4, 7, 6, 9, 6, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 6, 6, 3, 3, 3, 5, 3, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 7, 7, 7, 7, 8, 7, 7, 7, 7, 7, 6, 6, 7, 7, 3, 5, 7, 6, 10, 8, 7, 7, 7, 6, 7, 7, 7, 7, 9, 7, 7, 8, 8, 6}, 13, "fb4.png", 0);
   public static Font borderFont = new Font(" 0123456789.,:!?()+*$#/-%abcdefghijklmnopqrstuvwxyzáàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵđABCDEFGHIJKLMNOPQRSTUVWXYZĐ$", new byte[]{4, 6, 5, 6, 6, 7, 6, 6, 6, 6, 6, 3, 3, 3, 4, 5, 4, 4, 6, 5, 8, 8, 6, 6, 10, 6, 7, 5, 7, 6, 4, 7, 7, 3, 4, 6, 3, 9, 7, 7, 7, 7, 5, 5, 4, 7, 6, 9, 6, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 6, 6, 3, 3, 3, 5, 3, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 7, 7, 7, 7, 8, 7, 7, 7, 7, 7, 6, 6, 7, 7, 3, 5, 7, 6, 10, 8, 7, 7, 7, 6, 7, 7, 7, 7, 9, 7, 7, 8, 8, 6}, 15, "fb1.png", 0);
   public static Font bigFont = new Font(" 0123456789.,:!?()-'/ABCDEFGHIJKLMNOPQRSTUVWXYZÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴĐ", new byte[]{4, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 4, 4, 4, 4, 8, 6, 6, 6, 3, 7, 10, 10, 10, 10, 8, 8, 10, 10, 5, 8, 9, 8, 13, 11, 10, 10, 10, 10, 10, 9, 10, 10, 13, 11, 11, 9, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 5, 5, 5, 5, 5, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10}, 21, "fcg14m.png", 0);
   public static Font numberFont = new Font("0123456789$+-", new byte[]{15, 12, 15, 15, 15, 15, 15, 15, 15, 15, 15, 10, 10}, 13, "so.png", -3);
   public static Font smallFontRed = new Font("0123456789+-%$:ABCDEFGHIJKLMNOPQRSTUVWXYZ", new byte[]{5, 3, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 7, 5, 3, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 7, 6, 5, 5, 5, 5, 5, 5, 5, 5, 7, 5, 5, 5}, 8, "fs0.png", -1);
   public static Font smallFontYellow = new Font("0123456789+-%$:ABCDEFGHIJKLMNOPQRSTUVWXYZ", new byte[]{5, 3, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 7, 5, 3, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 7, 6, 5, 5, 5, 5, 5, 5, 5, 5, 7, 5, 5, 5}, 8, "fs1.png", -1);
   public static Font smallFont = new Font(" 0123456789.,:!?()+*$#/-%abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ", new byte[]{2, 5, 4, 5, 5, 6, 5, 5, 5, 5, 5, 2, 3, 2, 2, 5, 4, 4, 6, 6, 6, 6, 4, 3, 8, 5, 5, 4, 5, 5, 4, 5, 5, 2, 3, 5, 2, 8, 5, 5, 5, 5, 4, 4, 3, 5, 6, 8, 4, 4, 4, 6, 5, 6, 6, 5, 5, 6, 6, 4, 4, 5, 5, 8, 7, 7, 5, 7, 6, 6, 6, 6, 6, 8, 6, 6, 5}, 10, "tahoma9.png", 0);
   public static Font arialFont = new Font(" 0123456789.,:!?()+*$#/-%abcdefghijklmnopqrstuvwxyzáàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵđABCDEFGHIJKLMNOPQRSTUVWXYZ", new byte[]{3, 5, 3, 5, 5, 5, 5, 5, 5, 5, 5, 1, 1, 1, 1, 5, 3, 2, 5, 4, 5, 6, 2, 2, 9, 5, 5, 4, 5, 5, 3, 5, 5, 1, 1, 5, 1, 9, 5, 5, 5, 5, 3, 5, 2, 5, 4, 8, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 2, 1, 2, 3, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 5, 5, 5, 5, 5, 7, 7, 7, 7, 7, 7, 4, 4, 4, 4, 4, 6, 6, 6, 7, 7, 6, 5, 7, 7, 1, 4, 7, 6, 7, 7, 7, 6, 7, 7, 6, 6, 7, 6, 8, 6, 6, 6, 7, 4}, 14, "arialf.png", 1);
   public static Font blackF = new Font(" 0123456789.,:!?()+*$#/-%abcdefghijklmnopqrstuvwxyzáàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵđABCDEFGHIJKLMNOPQRSTUVWXYZ", new byte[]{4, 6, 4, 6, 6, 6, 6, 6, 6, 6, 6, 2, 2, 2, 2, 6, 4, 4, 6, 5, 6, 7, 4, 4, 10, 6, 6, 6, 6, 6, 4, 6, 6, 2, 2, 5, 2, 8, 6, 6, 6, 6, 4, 6, 3, 6, 6, 10, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 3, 2, 3, 4, 2, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 7, 7, 7, 7, 7, 6, 6, 6, 6, 6, 8, 8, 8, 8, 8, 8, 6, 6, 6, 6, 6, 7, 8, 7, 7, 7, 6, 6, 8, 7, 2, 5, 7, 6, 8, 7, 8, 6, 8, 7, 7, 6, 7, 8, 11, 7, 8, 7, 7, 6, 7}, 13, "arial11b.png", 0);

   public Font(String charList, byte[] charWidth, int charHeight, String fontFile, int charSpace) {
      try {
         this.charSpace = charSpace;
         this.charList = charList;
         this.charWidth = charWidth;
         this.charHeight = charHeight;
         this.nameFont = fontFile;
         mImage.createImage("/font/fontT/" + fontFile, new IAction2() {
            public void perform(Object object) {
               Font.this.imgFont = new mImage((Image)object);
               Font.this.imgWidth = Font.this.imgFont.image.getWidth();
            }
         });
      } catch (Exception var7) {
         var7.printStackTrace();
      }

   }

   public void drawString(mGraphics g, String st, int x, int y, int align, boolean isClip) {
      int len = st.length();
      int x1;
      if (align == 0) {
         x1 = x;
      } else if (align == 1) {
         x1 = x - this.getWidth(st);
      } else {
         x1 = x - (this.getWidth(st) >> 1);
      }

      for(int i = 0; i < len; ++i) {
         int pos = this.charList.indexOf(st.charAt(i));
         if (pos == -1) {
            pos = 0;
         }

         if (pos > -1) {
            g.drawRegion(this.imgFont, 0, pos * this.charHeight, this.imgWidth, this.charHeight, 0, x1, y, 20, isClip);
         }

         if (pos > this.charWidth.length - 1) {
            pos = this.charWidth.length - 1;
         }

         x1 += this.charWidth[pos] + this.charSpace;
      }

   }

   public void drawString(mGraphics g, String st, int x, int y, int align) {
      int len = st.length();
      int x1;
      if (align == 0) {
         x1 = x;
      } else if (align == 1) {
         x1 = x - this.getWidth(st);
      } else {
         x1 = x - (this.getWidth(st) >> 1);
      }

      for(int i = 0; i < len; ++i) {
         int pos = this.charList.indexOf(st.charAt(i));
         if (pos == -1) {
            pos = 0;
         }

         if (pos > -1) {
            g.drawRegion(this.imgFont, 0, pos * this.charHeight, this.imgWidth, this.charHeight, 0, x1, y, mGraphics.BOTTOM | mGraphics.VCENTER, true);
         }

         if (pos > this.charWidth.length - 1) {
            pos = this.charWidth.length - 1;
         }

         x1 += this.charWidth[pos] + this.charSpace;
      }

   }

   public int getWidth(String st) {
      int len = 0;

      for(int i = 0; i < st.length(); ++i) {
         int pos = this.charList.indexOf(st.charAt(i));
         if (pos == -1) {
            pos = 0;
         }

         if (pos > this.charWidth.length - 1) {
            pos = this.charWidth.length - 1;
         }

         len += this.charWidth[pos];
      }

      return len;
   }

   public static String replace(String _text, String _searchStr, String _replacementStr) {
      StringBuffer sb = new StringBuffer();
      int searchStringPos = _text.indexOf(_searchStr);
      int startPos = 0;

      for(int searchStringLength = _searchStr.length(); searchStringPos != -1; searchStringPos = _text.indexOf(_searchStr, startPos)) {
         sb.append(_text.substring(startPos, searchStringPos)).append(_replacementStr);
         startPos = searchStringPos + searchStringLength;
      }

      sb.append(_text.substring(startPos, _text.length()));
      return sb.toString();
   }

   public String[] splitFontBStrInLine(String src, int lineWidth) {
      Vector<String> list = new Vector();
      src = src.trim();
      int srclen = src.length();
      if (srclen == 0) {
         return null;
      } else if (srclen == 1) {
         return new String[]{src};
      } else {
         String tem = "";
         int start = 0;
         int end = 0;

         while(true) {
            while(this.getWidth(tem) < lineWidth) {
               tem = tem + src.charAt(end);
               ++end;
               if (src.charAt(end) == '\n') {
                  break;
               }

               if (end >= srclen - 1) {
                  end = srclen - 1;
                  break;
               }
            }

            if (end != srclen - 1 && src.charAt(end + 1) != ' ') {
               int endAnyway;
               for(endAnyway = end; src.charAt(end + 1) != '\n' && (src.charAt(end + 1) != ' ' || src.charAt(end) == ' ') && end != start; --end) {
               }

               if (end == start) {
                  end = endAnyway;
               }
            }

            list.addElement(src.substring(start, end + 1));
            if (end == srclen - 1) {
               break;
            }

            for(start = end + 1; start != srclen - 1 && src.charAt(start) == ' '; ++start) {
            }

            if (start == srclen - 1) {
               break;
            }

            end = start;
            tem = "";
         }

         String[] strs = new String[list.size()];

         for(int i = 0; i < list.size(); ++i) {
            strs[i] = (String)list.elementAt(i);
         }

         return strs;
      }
   }

   public int getHeight() {
      return this.charHeight;
   }

   public static void OnSaveImageFont() {
   }
}
