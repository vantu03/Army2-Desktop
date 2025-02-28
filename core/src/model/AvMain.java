package model;

import CLib.mFont;
import CLib.mGraphics;
import CLib.mImage;
import coreLG.CCanvas;

public class AvMain {
   public static final byte COLOR_WHITE = 0;
   public static final byte COLOR_GREEN = 1;
   public static final byte COLOR_PURPLE = 2;
   public static final byte COLOR_ORANGE = 3;
   public static final byte COLOR_BLUE = 4;
   public static final byte COLOR_YELLOW = 5;
   public static final byte COLOR_RED = 6;
   public static final byte COLOR_BLACK = 7;
   public static final byte COLOR_BROWN = 8;
   public static final int color_black = -15463396;
   public static final int color_black_light = -12698050;
   public static final int color_white = -855308;
   public static final int color_gray_dark_bg = -12502207;
   public static final int color_gray_bg = -11646386;
   public static final int color_gray_light_bg = -8948105;
   public static final int color_red_dark_bg = -12311500;
   public static final int color_red = -1685698;
   public static final int color_yellow_dark = -1392637;
   public static final int color_yellow_lemon = -2436002;
   public static final int color_brown = -5281755;
   public static final int color_brown_yellow = -805042;
   public static final int color_brown_light = -6924746;
   public static final int color_brown_dark = -8041424;
   public static final int color_purple_dark = -11063742;
   public static final int color_purple_violet = -6998324;
   public static final int color_blue = -12953632;
   public static final int color_orange = -1546729;
   public static final int color_orange_dark_bg = -2982612;
   public static final int color_green_dark = -13990400;
   public static final int color_green_light = -8329666;
   public static final int color_gold = -1127168;
   public static final int color_yellow = -4616367;
   public static final int color_yellow_bg_select = -3756155;
   public static final int color_brown_light_bg_select = -8300224;
   public static final int color_brown_bg_select = -10928849;
   public static final int color_brown_dark_bg_select = -13359589;
   public static final int color_coffee_light_bg_select = -1400237;
   public static final int color_coffee_bg_select = -3308998;
   public static final int color_coffee_dark_bg_select = -9944034;
   public static final int color_item_white = -789515;
   public static final int color_item_green = -10755328;
   public static final int color_item_purple = -6597485;
   public static final int color_item_orange = -3898817;
   public static final int color_item_blue = -12025137;
   public static final int color_item_yellow = -5790905;
   public static final int color_item_red = -2537914;
   public static final int color_item_black = -14737890;
   public static final int color_font_item_white = -789515;
   public static final int color_font_item_green = -10755328;
   public static final int color_font_item_purple = -4881665;
   public static final int color_font_item_orange = -1535687;
   public static final int color_font_item_blue = -10771969;
   public static final int color_font_item_yellow = -1844169;
   public static final int color_font_item_red = -42149;
   public static final int color_font_item_black = -14079960;
   public static final int color_font_item_brown = -11919604;
   public static final int color_hp_mon_0 = -16399358;
   public static final int color_hp_mon_1 = -12002232;
   public static final int color_hp_mon_2 = -10298782;
   public static final int color_hp_mon_3 = -7675510;
   public static final int color_hp_mon_4 = -4592711;
   public static final int color_hp_mon_5 = -65794;
   public static int[] sizeUpgradeEff = new int[]{2, 1, 1};
   public static int[][] colorUpgradeEffect = new int[][]{{-1, -1776411, -3289393, -5066061, -7105388, -8882056}, {-16715264, -16718592, -16724992, -16731392, -16738048, -16744448}, {-3276545, -4718363, -6094644, -7405389, -8781671, -10092416}, {-33024, -1740032, -3381760, -5023488, -6730752, -8372224}, {-16740097, -16743707, -16749108, -16752717, -16756071, -16759680}, {-1024, -1714176, -4142080, -5000960, -6713344, -8486912}, {16711705, 15007767, 13369364, 11730962, 10027023, 8388621}};
   public static int wimg;
   public static mImage[] imghitScr = new mImage[3];
   public static mImage[] imgTab = new mImage[22];
   public static mImage imgSelected_hand;
   public static mImage imgHpBar_0;
   public static mImage imgHpBar_1;
   public static mImage imgHpBar_2;
   public static mImage imgFocus;
   public static mImage imgFocus_rec;
   public static mImage imgDelay;
   public static mImage imgItem_bound;
   public static mImage imgBoard;
   public static mImage imgBg_corner;
   public static mImage imgMain_Corner;
   public static mImage imgVS;
   public static mImage imgDelay4;
   public static mImage imgRank2;
   public static mImage imgTicket;
   public static mImage imgMax;
   public static FrameImage fraPk;
   public static FrameImage fraFogetPass;
   public static FrameImage fraTextField;
   public static FrameImage fraTextField1;
   public static FrameImage imgLoadImage;
   public static FrameImage frabtn_vuong0;
   public static FrameImage frabtn_vuong1;
   public static FrameImage fra_Money;
   public static FrameImage fraQuest;
   public static FrameImage fraDelay;
   public static FrameImage fraDelay2;
   public static FrameImage fraStarIcon;
   public static FrameImage fraHPboss;
   public static FrameImage fraHPboss_1;
   public static FrameImage fraHPMon;
   public static FrameImage fraIconNpc;
   public static FrameImage fraPlusSkillPoint;
   public static FrameImage fraResist;
   public static FrameImage fraAura;
   public static FrameImage fraSmallArrow;
   public static FrameImage fraRank;
   public static FrameImage fraCorner;
   public static FrameImage fra_PVE_Bar_0;
   public static FrameImage fra_PVE_Bar_1;
   public static FrameImage fraDOT;
   public static FrameImage fraNoti;
   public static FrameImage fraBg_info;
   public static FrameImage fraSkull;
   public static FrameImage fraIconHoatDong;
   public static FrameImage frabtn_vuong_1_0;
   public static FrameImage frabtn_vuong_1_1;
   public static FrameImage fraIdClass;
   public static final int SELECTED_COLOR = -9930353;
   public static int x_cmdLeft;
   public static int y_cmdLeft;
   public static int x_cmdRight;
   public static int y_cmdRight;
   public static int x_cmdCenter;
   public static int y_cmdCenter;
   public static int cmx_tabname;
   public static int cmx_tabnamemax;
   public static int cmx_tabnamemax_old;
   public static int count_cmx_tabname;
   public static long time_cmx_tabname;
   FrameImage fraWarning;
   public static final byte stt_online = 0;
   public static final byte stt_off = 1;
   public static int[] color = new int[]{-15463396, -855308};

   public AvMain() {
      x_cmdLeft = 1;
   }

   public void paint(mGraphics g) {
      CCanvas.resetTrans(g);
      this.paintCmd(g);
   }

//   public static void paintCorner(mGraphics g, int x, int y, int w, int h, boolean isTick) {
//      if (imgBg_corner != null) {
//         int dx = true;
//         if (CCanvas.gameTick % 20 > 10) {
//            dx = true;
//         }
//
//      }
//   }

   public static void paintLoadImg(mGraphics g, int f, int xp, int yp, int anchor) {
      if (f >= 0) {
         imgLoadImage.drawFrame(f, xp, yp, 0, anchor, g);
      } else {
         imgLoadImage.drawFrame(CCanvas.gameTick / 2 % imgLoadImage.nFrame, xp, yp, 0, anchor, g);
      }

   }

   public void update() {
      update_cmx_TabName(cmx_tabnamemax);
   }

   public void keypress(int keyCode) {
   }

   public void paintCmd(mGraphics g) {
   }

   public void paintCmd_OnlyText(mGraphics g) {
      if (CCanvas.currentDialog == null) {
         CCanvas.resetTrans(g);
         if (CCanvas.isSmallScreen) {
            this.paintCmd_OnlyText_Small(g);
         }

      }
   }

   public void paintCmd_OnlyText_Small(mGraphics g) {
   }

   public void commandTab(int index, int subIndex) {
   }

   public void commandMenu(int index, int subIndex) {
   }

   public void commandPointer(int index, int subIndex) {
   }

   public static void paintRect_Item(mGraphics g, int x, int y, int w, int h, int wItem, boolean isSelect, int typeCheck, int[] color) {
   }

   public static void paintRect_Item(mGraphics g, int x, int y, int w, int h, int wItem, boolean isSelect, int typeCheck) {
      int[] var10000 = new int[]{-10928849, -8300224, -13359589};
      if (isSelect) {
         var10000 = new int[]{-3308998, -1400237, -9944034};
      }

   }

   public static void paintRect(mGraphics g, int x, int y, int w, int h, boolean isBlack) {
   }

   public static void paintTab_menu(mGraphics g, int xTab, int yTab, int wTab, int hTab, boolean isPaint_Dot) {
   }

   public static void paint_MainTab(mGraphics g, int xInTab, int yInTab, int wInTab, int hInTab, int typeLink, int numLink, int wItemBox, boolean isPaintDarkBg) {
      int h2 = mImage.getImageHeight(imgTab[2].image);
      int w2 = mImage.getImageWidth(imgTab[2].image);
      int h7 = mImage.getImageHeight(imgTab[7].image);
      int w7 = mImage.getImageWidth(imgTab[7].image);
      int w1 = mImage.getImageWidth(imgTab[1].image);
      int h1 = mImage.getImageHeight(imgTab[1].image);
      int h3 = mImage.getImageHeight(imgTab[3].image);
      if (isPaintDarkBg) {
         g.setColor(-11646386);
      } else {
         g.setColor(-2982612);
      }

      int x_fix = 2;
      int w_2 = wInTab - 2 * x_fix;
      int h_2 = hInTab - 2 * x_fix;
      int numw = w_2 / w2;
      int numh = h_2 / w2;

      int i;
      int var22;
      for(i = 0; i < numw; ++i) {
         var22 = xInTab + x_fix + i * w2;
      }

      for(i = 0; i < numh; ++i) {
         var22 = yInTab + x_fix + i * w2;
      }

   }

   public static void paintTabName(mGraphics g, int xTab, int yTab, int wTab, String name, int color) {
   }

   public static void update_cmx_TabName(int max) {
      if (cmx_tabnamemax_old != cmx_tabnamemax) {
         cmx_tabnamemax_old = cmx_tabnamemax;
         reset_cmx_tabname();
      }

      if (max > 0) {
         if (cmx_tabname >= cmx_tabnamemax) {
            ++count_cmx_tabname;
            if (count_cmx_tabname > 50) {
               reset_cmx_tabname();
            }
         } else if (time_cmx_tabname - CCanvas.timeNow < 0L) {
            ++cmx_tabname;
         }
      }

   }

   public static void reset_cmx_tabname() {
      cmx_tabname = 0;
      count_cmx_tabname = 0;
      time_cmx_tabname = CCanvas.timeNow + 1500L;
   }

   public static void paint_LinkTab(mGraphics g, int x, int y, int wcur, int type, int num) {
      g.setColor(-15463396);

      for(int i = 0; i < num; ++i) {
         int var10000;
         if (type == 0) {
            var10000 = x + i * wcur;
         }

         if (type != 0) {
            var10000 = y + i * wcur;
         }

         int trans = type == 0 ? 0 : 4;
         mImage img = imgTab[3];
         if (trans == 4) {
            img = imgTab[17];
         }
      }

   }

   public static void paintbutton(mGraphics g, int xDia, int yDia, int wDia, int hDia, int Indexcolor) {
      paintbutton_vuong(g, Indexcolor, xDia, yDia, wDia, hDia);
   }

   public static void paintbutton_vuong(mGraphics g, int Indexcolor, int xBut, int yBut, int wBut, int hBut) {
      FrameImage fra = Indexcolor == 0 ? frabtn_vuong0 : frabtn_vuong1;
      FrameImage fra2 = Indexcolor == 0 ? frabtn_vuong_1_0 : frabtn_vuong_1_1;
      if (wBut < fra.frameWidth * 2) {
         wBut = fra.frameWidth * 2;
      }

      if (hBut < fra.frameWidth * 2) {
         hBut = fra.frameWidth * 2;
      }

      int w = wBut - fra.frameWidth * 2;
      int h = hBut - fra.frameHeight * 2;
      if (w > 0 || h > 0) {
         int n = w / fra2.frameWidth;
         if (w % fra2.frameWidth > 0) {
            ++n;
         }

         int m;
         for(m = 0; m < n; ++m) {
         }

         if (h > 0) {
            m = h / fra2.frameHeight;
            if (h % fra2.frameHeight > 0) {
               ++m;
            }

            for(int i = 0; i < m; ++i) {
            }
         }
      }

   }

   public static void fill(int x, int y, int w, int h, int color, mGraphics g) {
      g.setColor(color);
   }

   public static void paintRectText(mGraphics g, int xText, int yText, int wText, int hText, boolean isFocus) {
      int wb = fraTextField.frameWidth;
      int hb = fraTextField.frameHeight;
      int wp = wText - 2 * wb;
      int hp = hText - 2 * hb;
      g.setColor(-8041424);
      int n = wp / wb;
      if (wp % wb > 0) {
         ++n;
      }

      int m;
      for(m = 0; m < n; ++m) {
      }

      m = hp / hb;
      if (hp % hb > 0) {
         ++m;
      }

      for(int i = 0; i < m; ++i) {
      }

   }

   public void updatekey() {
   }

   public void updatekeyPC() {
   }

   public void updatekeyPCForTField() {
   }

   public void updatePointer() {
      boolean var10000 = CCanvas.isTouch;
   }

   public static void Font3dWhite(mGraphics g, String str, int x, int y, int ar) {
   }

   public static void Font3dColor(mGraphics g, String str, int x, int y, int ar, byte color) {
   }

   public static void Font3dColorAndColor(mGraphics g, String str, int x, int y, int ar, byte color, byte color2) {
   }

   public static void FontBorderColor(mGraphics g, String str, int x, int y, int ar, int color) {
   }

   public static int getColor_ItemBg(int id) {
      switch(id) {
      case 1:
         return -13990400;
      case 2:
         return -6597485;
      case 3:
         return -3898817;
      case 4:
      default:
         return -11646386;
      case 5:
         return -5790905;
      }
   }

   public static byte getColor_Item_Upgrade(short lv) {
      switch(lv) {
      case 0:
      case 1:
         return 0;
      case 2:
      case 3:
         return 4;
      case 4:
      case 5:
         return 5;
      case 6:
      case 7:
         return 2;
      case 8:
      case 9:
      case 10:
         return 3;
      default:
         return 6;
      }
   }

   public static int getColor(int id) {
      switch(id) {
      case 0:
         return -855308;
      case 1:
         return -8329666;
      case 2:
         return -6998324;
      case 3:
         return -1546729;
      case 4:
         return -12953632;
      case 5:
         return -2436002;
      case 6:
         return -1685698;
      case 7:
         return -15463396;
      case 8:
         return -5281755;
      default:
         return -6998324;
      }
   }

   public static mFont setTextColor(int id, int type) {
      return mFont.tahoma_7b_white;
   }

   public static int resetSelect(int select, int max, boolean isreset) {
      if (select < 0) {
         if (isreset) {
            select = max;
         } else {
            select = 0;
         }
      } else if (select > max) {
         if (isreset) {
            select = 0;
         } else {
            select = max;
         }
      }

      return select;
   }

   public void paintWarning(mGraphics g, int x, int y, int w) {
      if (this.fraWarning != null) {
         int n = w / this.fraWarning.frameWidth;
         if (w % this.fraWarning.frameWidth != 0) {
            ++n;
         }

         for(int i = 0; i < n; ++i) {
            int f = CCanvas.gameTick / 3 % this.fraWarning.nFrame;
            int xp = x - w / 2 + i * this.fraWarning.frameWidth;
            int var10000 = y - this.fraWarning.frameHeight / 3;
            if (i == n - 1) {
               xp = x - w / 2 + w - this.fraWarning.frameWidth;
            }
         }
      }

   }

   public static void paintIconString(mGraphics g, String str, mFont f, FrameImage fra, int nframe, int beginframe, int x, int y, int align, int w2w, mFont fBorder) {
      try {
         if (fra == null) {
            return;
         }

         int xp = x;
         if (align == 1) {
            xp = x - (fra.frameWidth + f.getWidth(str) + w2w);
         } else if (align == 2) {
            xp = x - (fra.frameWidth + f.getWidth(str) + w2w) / 2;
         }

         int xstr = xp + fra.frameWidth + w2w;
         int frame = beginframe + CCanvas.gameTick / 3 % nframe;
         fra.drawFrame(frame, xp, y, 0, mGraphics.LEFT | mGraphics.TOP, g);
         int var14 = y + (fra.frameHeight - f.getHeight()) / 2 + 1;
      } catch (Exception var15) {
      }

   }

   public static int getRankImg(int rank) {
      int id = 3;
      if (rank > 0 && rank < 4) {
         id = rank - 1;
      }

      return id;
   }

   public static void paintIconRank(mGraphics g, int rank, int x, int y) {
   }

   public static int getColor_Rank(int rank) {
      switch(rank) {
      case 1:
         return -2537914;
      case 2:
         return -6597485;
      case 3:
         return -5790905;
      default:
         return -11646386;
      }
   }

//   public static void paintOnlineStatus(mGraphics g, byte type, int x, int y) {
//      int fra = false;
//      if (type == 0) {
//         fra = true;
//      }
//
//   }

   public static mImage getImage(String path, String nameImg) {
      mImage img = null;
      return (mImage)img;
   }

   public static FrameImage getFraImage(String path, String nameImg, int nFrame) {
      FrameImage fra = null;
      mImage img = null;
      return (FrameImage)fra;
   }

   public static void paintImgDeley(mGraphics g, int x, int y, int w, int h) {
   }

//   public static void paintArrow(mGraphics g, int x, int y, int dir) {
//      int ii = false;
//      if (CCanvas.gameTick % 6 == 0) {
//         ii = true;
//      }
//
//   }

//   public static void paintChatPopup(mGraphics g, int x, int y, int w, int h) {
//      try {
//         int dy = false;
//         if (CCanvas.gameTick % 6 < 3) {
//            dy = true;
//         }
//
//         g.setColor(-855308);
//         boolean var8 = true;
//      } catch (Exception var9) {
//      }
//
//   }

   public static void paintDeley(mGraphics g, int x, int y, int w, int h) {
      g.setClip(x, y, w, h);
      g.saveCanvas();
      g.ClipRec(x, y, w, h);
      int wd = 16;
      int nw = w / wd + (w % wd > 0 ? 1 : 0);
      int nh = h / wd + (h % wd > 0 ? 1 : 0);

      for(int j = 0; j < nh; ++j) {
         for(int i = 0; i < nw; ++i) {
            int var10000 = x + i * 16;
            var10000 = y + j * 16;
         }
      }

      g.restoreCanvas();
      CCanvas.resetTrans(g);
   }
}
