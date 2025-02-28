package CLib;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class SysTemFont {
   public static String charLits = " áàảãạăắằẳẵặâấầẩẫậéèẻẽẹêế�?ểễệíìỉĩịóò�?õ�?ôốồổỗộơớ�?ởỡợúùủũụưứừửữựýỳỷỹỵđ�?ÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬÉÈẺẼẸÊẾỀỂỄỆ�?ÌỈĨỊÓÒỎÕỌÔ�?ỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰ�?ỲỶỸỴ�?";
   public BitmapFont font;
   private float r;
   private float g;
   private float b;
   private float a;
   public float[] charWidth;
   public byte charHeight;
   private float charSpace;
   float yAddFont;
   public Color cl;

   public SysTemFont(int ID, int color) {
      String name = "big";
      if (ID > 8 && ID < 30) {
         name = "mini";
      } else {
         name = "big";
      }

      if (mGraphics.zoomLevel == 1) {
         name = "big";
      }

      if (mGraphics.zoomLevel == 2) {
         this.yAddFont = 1.5F;
      } else if (mGraphics.zoomLevel == 3) {
         this.yAddFont = 2.0F;
      } else if (mGraphics.zoomLevel == 4) {
         this.yAddFont = 2.3F;
      }

      this.font = new BitmapFont(Gdx.files.internal(LibSysTem.font + mGraphics.zoomLevel + "/" + name + ".fnt"), Gdx.files.internal(LibSysTem.font + mGraphics.zoomLevel + "/" + name + ".png"), true);
      this.cl = mSystem.setColor(color);
      this.font.setColor(this.cl);
      this.charWidth = new float[charLits.length()];
      this.charWidth = new float[charLits.length()];
      GlyphLayout layout = new GlyphLayout();

      float width;
      for(int i = 0; i < this.charWidth.length; ++i) {
         layout.setText(this.font, String.valueOf(charLits.charAt(i)));
         width = layout.width;
         float height = layout.height;
         this.charWidth[i] = width / (float)mGraphics.zoomLevel;
      }

      layout.setText(this.font, "A");
      float height = layout.height;
      this.charHeight = (byte)((int)(height / (float)mGraphics.zoomLevel + 3.0F));
      layout.setText(this.font, " ");
      width = layout.width;
      this.charSpace = width / (float)mGraphics.zoomLevel;
   }

   public Color rgba8888ToColor(int rgba8888) {
      Color color = new Color();
      color.r = (float)((rgba8888 & -16777216) >>> 24) / 255.0F;
      color.g = (float)((rgba8888 & 16711680) >>> 16) / 255.0F;
      color.b = (float)((rgba8888 & '\uff00') >>> 8) / 255.0F;
      color.a = (float)(rgba8888 & 255) / 255.0F;
      return color;
   }

   public SysTemFont(String name, int color, float a) {
      this.font = new BitmapFont(Gdx.files.internal(LibSysTem.font + name + ".fnt"), Gdx.files.internal(LibSysTem.font + name + ".png"), true);
      this.cl = mSystem.setColor(color);
      this.font.setColor(this.cl);
      this.charWidth = new float[charLits.length()];
      GlyphLayout layout = new GlyphLayout();

      float width;
      for(int i = 0; i < this.charWidth.length; ++i) {
         layout.setText(this.font, String.valueOf(charLits.charAt(i)));
         width = layout.width;
         this.charWidth[i] = width / (float)mGraphics.zoomLevel;
      }

      layout.setText(this.font, "A");
      float height = layout.height;
      this.charHeight = (byte)((int)height);
      layout.setText(this.font, " ");
      width = layout.width;
      this.charSpace = width / (float)mGraphics.zoomLevel;
   }

   public int getWidth(String st) {
      int len = 0;

      for(int i = 0; i < st.length(); ++i) {
         int pos = charLits.indexOf(st.charAt(i));
         if (pos == -1) {
            pos = 0;
         }

         len = (int)((float)len + this.charWidth[pos] + this.charSpace);
      }

      return len;
   }

   public int convert_RGB_to_ARGB(int rgb) {
      int r = rgb >> 16 & 255;
      int g = rgb >> 8 & 255;
      int b = rgb >> 0 & 255;
      return -16777216 | r << 16 | g << 8 | b;
   }

//   public String[] splitString(String _text, String _searchStr) {
//      int count = 0;
//      int pos = 0;
//      int searchStringLength = _searchStr.length();
//
//      for(int aa = _text.indexOf(_searchStr, pos); aa != -1; ++count) {
//         int pos = aa + searchStringLength;
//         aa = _text.indexOf(_searchStr, pos);
//      }
//
//      String[] sb = new String[count + 1];
//      int searchStringPos = _text.indexOf(_searchStr);
//      int startPos = 0;
//
//      int index;
//      for(index = 0; searchStringPos != -1; ++index) {
//         sb[index] = _text.substring(startPos, searchStringPos);
//         startPos = searchStringPos + searchStringLength;
//         searchStringPos = _text.indexOf(_searchStr, startPos);
//      }
//
//      sb[index] = _text.substring(startPos, _text.length());
//      return sb;
//   }
//
//   public String[] splitFontArray(String src, int lineWidth) {
//      mVector lines = this._splitFont(src, lineWidth);
//      String[] arr = new String[lines.size()];
//
//      for(int i = 0; i < lines.size(); ++i) {
//         arr[i] = lines.elementAt(i).toString();
//      }
//
//      return arr;
//   }
//
//   public static String[] _splitString(String _text, String _searchStr) {
//      int count = 0;
//      int pos = 0;
//      int searchStringLength = _searchStr.length();
//
//      for(int aa = _text.indexOf(_searchStr, pos); aa != -1; ++count) {
//         int pos = aa + searchStringLength;
//         aa = _text.indexOf(_searchStr, pos);
//      }
//
//      String[] sb = new String[count + 1];
//      int searchStringPos = _text.indexOf(_searchStr);
//      int startPos = 0;
//
//      int index;
//      for(index = 0; searchStringPos != -1; ++index) {
//         sb[index] = _text.substring(startPos, searchStringPos);
//         startPos = searchStringPos + searchStringLength;
//         searchStringPos = _text.indexOf(_searchStr, startPos);
//      }
//
//      sb[index] = _text.substring(startPos, _text.length());
//      return sb;
//   }

   public String replace(String _text, String _searchStr, String _replacementStr) {
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

   public String[] splitFont(String src, int lineWidth) {
      mVector lines = this._splitFont(src, lineWidth);
      String[] arr = new String[lines.size()];

      for(int i = 0; i < lines.size(); ++i) {
         arr[i] = lines.elementAt(i).toString();
      }

      return arr;
   }

   public mVector _splitFont(String src, int lineWidth) {
      mVector lines = new mVector();
      if (lineWidth <= 0) {
         lines.add(src);
         return lines;
      } else {
         String line = "";

         for(int i = 0; i < src.length(); ++i) {
            if (src.charAt(i) == '\n') {
               lines.addElement(line);
               line = "";
            } else {
               line = line + src.charAt(i);
               if (this.getWidth(line) > lineWidth) {
                  int j = 0;

                  for(j = line.length() - 1; j >= 0 && line.charAt(j) != ' '; --j) {
                  }

                  if (j < 0) {
                     j = line.length() - 1;
                  }

                  lines.addElement(line.substring(0, j));
                  i = i - (line.length() - j) + 1;
                  line = "";
               }

               if (i == src.length() - 1 && !line.trim().equals("")) {
                  lines.addElement(line);
               }
            }
         }

         return lines;
      }
   }

   public int getHeight() {
      return this.charHeight;
   }

   public void drawString(mGraphics g, String st, int x, int y, int align, boolean useClip) {
      int al = 8;
      switch(align) {
      case 0:
      default:
         break;
      case 1:
         al = 16;
         break;
      case 2:
         al = 1;
      }

      g.drawString(st, (float)x, (float)y + this.yAddFont, this.font, al, useClip);
   }

   public mVector splitFontVector(String src, int lineWidth) {
      mVector lines = new mVector();
      if (lineWidth <= 0) {
         lines.add(src);
         return lines;
      } else {
         String line = "";

         for(int i = 0; i < src.length(); ++i) {
            if (src.charAt(i) != '\n' && src.charAt(i) != '\b') {
               line = line + src.charAt(i);
               if (this.getWidth(line) > lineWidth) {
                  int j = 0;
                  for(j = line.length() - 1; j >= 0 && line.charAt(j) != ' '; --j) {
                  }

                  if (j < 0) {
                     j = line.length() - 1;
                  }

                  lines.addElement(line.substring(0, j));
                  i = i - (line.length() - j) + 1;
                  line = "";
               }

               if (i == src.length() - 1 && !line.trim().equals("")) {
                  lines.addElement(line);
               }
            } else {
               lines.addElement(line);
               line = "";
            }
         }

         return lines;
      }
   }
}
