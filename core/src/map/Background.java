package map;

import CLib.mGraphics;
import CLib.mImage;
import coreLG.CCanvas;
import coreLG.CONFIG;
import effect.Camera;
import effect.Snow;
import model.CRes;
import model.FilePack;
import model.FrameImage;
import model.IAction2;
import player.CPlayer;
import screen.CScreen;
import screen.GameScr;
import screen.PrepareScr;

public class Background {
   public static final byte BACKGR_HALONG = 0;
   public static final byte BACKGR_RUONGLUA = 1;
   public static final byte BACKGR_SIMPLESKY = 2;
   public static final byte BACKGR_RUNGRAM = 3;
   public static final byte BACKGR_HOANGTAN = 4;
   public static final byte BACKGR_SONGNUI = 5;
   public static final byte BACKGR_CITY = 6;
   public static final byte BACKGR_ICE = 7;
   public static final byte BACKGR_FORREST = 8;
   public static final byte BACKGR_BOSS_1 = 9;
   public static final byte BACKGR_CITY_NIGHT = 10;
   public static final byte BACKGR_NIGHT_FORREST = 12;
   public static final byte BACKGR_CAVE = 13;
   public static final byte BACKGR_CLOUD = 14;
   public static final byte BACKGR_GREY = 15;
   public static mImage sun;
   public static mImage sun2;
   public static mImage haLongbg;
   public static mImage cloud;
   public static mImage water;
   public static mImage inWater;
   public static mImage canhdong;
   public static mImage co;
   public static mImage co2;
   public static mImage rungRam;
   public static mImage bang;
   public static mImage back01;
   public static mImage back02;
   public static mImage back03;
   public static mImage back04;
   public static mImage back05;
   public static mImage back06;
   public static mImage back07;
   public static mImage back08;
   public static mImage back11;
   public static mImage back12;
   public static mImage back14;
   public static mImage back15;
   public static mImage back16;
   public static mImage back17;
   public static mImage thaprua;
   public static mImage balloon;
   public static mImage a;
   public static mImage b;
   public static mImage bigBalloon;
   public static mImage mocxich;
   public static mImage bg_cloud;
   public static mImage bg_cloud_1;
   public static mImage logo;
   public static mImage[] may;
   public static mImage rock_up;
   public static mImage map_spider_layout;
   public static mImage rock_down;
   public static mImage stone;
   public static boolean isLoadImage = false;
   public static byte curBGType;
   public int yBackGr = 0;
   public int yCloud = 130;
   static FrameImage waterSp;
   static int skyLine;
   static int sunX;
   static int sunY;
   static int nBgX;
   static int nBgY;
   static int nBgX2;
   public static int waterY;
   public static int glassY;
   int[] cloudx;
   int[] cloudy;
   int[] cloudz;
   int[] cloudx2;
   int[] cloudy2;
   static int nWave;
   static int[] wavex;
   static int[] wavex2;
   static int[] wavey;
   static int[] length;
   static int[] delay;
   Snow snow;
   boolean boltActive;
   int tBolt;
   int wLazer = 8;
   boolean changeSign;
   int limit = 613;
   int[] t = new int[10];
   static int[] xT;
   static int[] yT;

   static {
      FilePack filePack;
      try {
         filePack = new FilePack(CCanvas.getClassPathConfig(CONFIG.PATH_MAP + "bg"));
         PrepareScr.rockImg = filePack.loadImage("rock1.png");
         PrepareScr.rock2Img = filePack.loadImage("rock2.png");
         PrepareScr.glassFly = filePack.loadImage("cobay.png");
         PrepareScr.cloud1 = filePack.loadImage("cloud1.png");
         PrepareScr.chickenHair = filePack.loadImage("longga.png");
         cloud = filePack.loadImage("cl2.png");
         sun = filePack.loadImage("sun0.png");
         sun2 = filePack.loadImage("sun1.png");
         water = filePack.loadImage("wts.png");
         co = filePack.loadImage("co.png");
         logo = filePack.loadImage("lg.png", new IAction2() {
            public void perform(Object object) {
               CRes.out("===> isData");
            }
         });
         CPlayer.web = filePack.loadImage("web.png");
         co2 = filePack.loadImage("co2.png");
         inWater = filePack.loadImage("inWater.png");
         may = new mImage[3];
         PrepareScr.imgSun = filePack.loadImage("sun0.png");
         PrepareScr.imgCloud = new mImage[3];

         for(int i = 0; i < 3; ++i) {
            PrepareScr.imgCloud[i] = filePack.loadImage("cl" + (i + 1) + ".png");
         }

         balloon = filePack.loadImage("miniballoon.png");
         a = filePack.loadImage("a.png");
         b = filePack.loadImage("b.png");
         bigBalloon = filePack.loadImage("bigballoon.png");
         mocxich = filePack.loadImage("mocxich.png");
         stone = filePack.loadImage("stone.png");
      } catch (Exception var2) {
         var2.printStackTrace();
      }

      filePack = null;
      nWave = 10;
      wavex = new int[nWave];
      wavex2 = new int[nWave];
      wavey = new int[nWave];
      length = new int[nWave];
      delay = new int[]{1, 1, 10, 10, 20, 20, 30, 30, 40, 40, 40};
      xT = new int[]{CCanvas.width / 2 - 150, CCanvas.width / 2 - 110, CCanvas.width / 2 - 80, CCanvas.width / 2 - 10, CCanvas.width / 2 + 90, CCanvas.width / 2 + 110, CCanvas.width / 2 + 140};
      int dx = CCanvas.hieght >= 320 ? CCanvas.hieght - 320 : -(320 - CCanvas.hieght);
      yT = new int[]{221 + dx, 201 + dx, 240 + dx, 271 + dx, 223 + dx, 265 + dx, 243 + dx};
   }

   public void loadImage(int Type) {
      try {
         switch(Type) {
         case 0:
            haLongbg = mImage.createImage("/map/bgItem/halongkaka.png");
            break;
         case 1:
            canhdong = mImage.createImage("/map/bgItem/canhdong.png");
            break;
         case 2:
            bang = mImage.createImage("/map/bgItem/bang.png");
            break;
         case 3:
            rungRam = mImage.createImage("/map/bgItem/rungRam.png");
            break;
         case 4:
            back01 = mImage.createImage("/map/bgItem/back1.png");
            back02 = mImage.createImage("/map/bgItem/back2.png");
            break;
         case 5:
            back03 = mImage.createImage("/map/bgItem/back3.png");
            back04 = mImage.createImage("/map/bgItem/back4.png");
            break;
         case 6:
            back05 = mImage.createImage("/map/bgItem/back5.png");
            back06 = mImage.createImage("/map/bgItem/back6.png");
            break;
         case 7:
            back07 = mImage.createImage("/map/bgItem/back7.png");
            back08 = mImage.createImage("/map/bgItem/back8.png");
         case 8:
         case 11:
         default:
            break;
         case 9:
            back05 = mImage.createImage("/map/bgItem/back5.png");
            back06 = mImage.createImage("/map/bgItem/back6.png");
            break;
         case 10:
            back11 = mImage.createImage("/map/bgItem/back11.png");
            back12 = mImage.createImage("/map/bgItem/back12.png");
            break;
         case 12:
            back14 = mImage.createImage("/map/bgItem/back14.png");
            back15 = mImage.createImage("/map/bgItem/back15.png");
            break;
         case 13:
            rock_up = mImage.createImage("/map/bgItem/rock_up.png");
            map_spider_layout = mImage.createImage("/map/bgItem/map_spider_layout.png");
            rock_down = mImage.createImage("/map/bgItem/rock_down.png");
            break;
         case 14:
            bg_cloud = mImage.createImage("/map/bgItem/bg-cloud.png");
            bg_cloud_1 = mImage.createImage("/map/bgItem/bg_cloud1.png");
            break;
         case 15:
            back16 = mImage.createImage("/map/bgItem/back16.png");
            back17 = mImage.createImage("/map/bgItem/back17.png");
         }
      } catch (Exception var3) {
      }

   }

   public static void removeImage() {
      haLongbg = null;
      canhdong = null;
      rungRam = null;
      bang = null;
      back01 = null;
      back02 = null;
      back03 = null;
      back04 = null;
      back05 = null;
      back06 = null;
      back07 = null;
      back08 = null;
      back11 = null;
      back12 = null;
      back14 = null;
      back15 = null;
      back16 = null;
      back17 = null;
      bg_cloud = null;
      bg_cloud_1 = null;
      rock_up = null;
      map_spider_layout = null;
      rock_down = null;
   }

   public static void initImage() {
   }

   public Background(byte BGType) {
      removeImage();
      this.cloudx = new int[]{52, 110, 250};
      this.cloudy = new int[]{100, 180, 150};
      this.cloudz = new int[]{45, 40, 50};
      this.cloudx2 = new int[]{100, 200, 300};
      this.cloudy2 = new int[]{80, 50, 100};
      skyLine = CScreen.h - 135;
      curBGType = BGType;
      waterSp = new FrameImage(water.image, 24, 24);
      glassY = MM.mapHeight - co.image.getHeight();
      sunX = CScreen.w - 60;
      sunY = skyLine - 75;
      switch(BGType) {
      case 0:
         nBgX = CScreen.w / 128;
         break;
      case 1:
         nBgX = CScreen.w / 72;
         break;
      case 2:
         nBgX = CScreen.w / 64;
         break;
      case 3:
         nBgX = CScreen.w / 72;
         break;
      case 4:
         nBgX = CScreen.w / 241;
         nBgX2 = CScreen.w / 226;
         break;
      case 5:
         nBgX = CScreen.w / 241;
         break;
      case 6:
      case 10:
         nBgX = CScreen.w / 238;
         nBgX2 = CScreen.w / 225;
         break;
      case 7:
         nBgX = GameScr.w / 219;
         nBgX2 = GameScr.w / 218;
         break;
      case 8:
         nBgX = GameScr.w / 219;
         nBgX2 = GameScr.w / 210;
      case 9:
      case 11:
      default:
         break;
      case 12:
         nBgX = CScreen.w / 108;
         nBgX2 = CScreen.w / 108;
         this.snow = new Snow();
         this.snow.min = 300;
         this.snow.max = 400;
         this.snow.vymin = 5;
         this.snow.vymax = 7;
         this.snow.vxmin = 3;
         this.snow.waterY = 150;
         this.snow.startSnow(1);
         this.snow.waterY = -50;
         break;
      case 13:
         nBgX = GameScr.w / 219;
         nBgX2 = GameScr.w / 218;
         break;
      case 14:
         nBgX = GameScr.w / 128;
         nBgX2 = GameScr.w / 128;
         break;
      case 15:
         nBgX = GameScr.w / 241;
         nBgX = GameScr.w / 241;
      }

      this.loadImage(BGType);
   }

   public void update() {
      if (GameScr.curGRAPHIC_LEVEL != 2 && curBGType != 3) {
         this.updateCloud();
      }

      if (this.snow != null) {
         this.snow.update();
      }

   }

   private void updateCloud() {
      for(int i = 0; i < this.cloudx.length; ++i) {
         int[] var10000 = this.cloudx;
         var10000[i] -= (Camera.x - Camera.startx) * this.cloudz[i] / 100;
         var10000 = this.cloudy;
         var10000[i] -= (Camera.y - Camera.starty) * this.cloudz[i] / 100;
         var10000 = this.cloudx2;
         var10000[i] -= (Camera.x - Camera.startx) * this.cloudz[i] / 100;
         var10000 = this.cloudy2;
         var10000[i] -= (Camera.y - Camera.starty) * this.cloudz[i] / 100;
      }

   }

   public void updateWave() {
      for(int i = 0; i < nWave; ++i) {
         if (delay[i] > 50) {
            wavex[i] = CRes.random(0, CScreen.w - 10);
            wavey[i] = CRes.random(skyLine, CScreen.h);
            length[i] = CRes.random(1, 5);
            delay[i] = 0;
         } else {
            int var10002 = delay[i]++;
         }
      }

   }

   public void paint(mGraphics g) {
      this.drawBackGround(curBGType, g);
   }

   private void drawBackGround(byte Type, mGraphics g) {
      g.translate(-g.getTranslateX(), -g.getTranslateY());
      int dXKeo;
      int dXKeo2;
      int dYKeo;
      int i;
      switch(Type) {
      case 0:
         g.setColor(8831994);
         g.fillRect(0, 0, CScreen.w + 10, CScreen.h + 10, false);
         if (GameScr.curGRAPHIC_LEVEL != 2) {
            dXKeo = (Camera.x >> 2) % 128;
            dXKeo2 = CScreen.h - (Camera.y + CScreen.h >> 2) + this.yBackGr;
            g.setColor(2002158);
            g.fillRect(0, dXKeo2, CScreen.w, CScreen.h - dXKeo2, false);

            for(dYKeo = 0; dYKeo <= nBgX + 1; ++dYKeo) {
               g.drawImage(haLongbg, -dXKeo + dYKeo * 128, dXKeo2, mGraphics.LEFT | mGraphics.VCENTER, false);
            }

            g.drawImage(sun, sunX, dXKeo2 - 100, 0, false);
            g.translate(-g.getTranslateX(), -g.getTranslateY() + 1);
            this.drawCloud(g);
         } else {
            g.setColor(8438010);
            g.fillRect(0, 0, CScreen.w, CScreen.h, false);
         }
         break;
      case 1:
         g.setColor(8180459);
         g.fillRect(0, 0, CScreen.w + 10, CScreen.h + 10, false);
         if (GameScr.curGRAPHIC_LEVEL != 2) {
            dXKeo = (Camera.x >> 2) % 64;
            dXKeo2 = CScreen.h - (Camera.y + CScreen.h >> 2) + this.yBackGr;

            for(dYKeo = 0; dYKeo <= nBgX + 1; ++dYKeo) {
               g.drawImage(canhdong, -dXKeo + dYKeo * 64, dXKeo2, mGraphics.LEFT | mGraphics.BOTTOM, false);
            }

            g.drawImage(sun, sunX, dXKeo2 - 200, 0, false);
            g.translate(-g.getTranslateX(), -g.getTranslateY() + 1);
            this.drawCloud(g);
         }
         break;
      case 2:
         g.setColor(8180459);
         g.fillRect(0, 0, CScreen.w + 10, CScreen.h + 10, false);
         if (GameScr.curGRAPHIC_LEVEL != 2) {
            dXKeo = (Camera.x >> 2) % 64;
            dXKeo2 = CScreen.h - (Camera.y + CScreen.h >> 2) + 150 + this.yBackGr;

            for(dYKeo = 0; dYKeo <= nBgX + 1; ++dYKeo) {
               g.drawImage(bang, -dXKeo + dYKeo * 64, dXKeo2, mGraphics.LEFT | mGraphics.BOTTOM, false);
            }

            g.drawImage(sun, sunX, dXKeo2 - 200, 0, false);
            g.translate(-g.getTranslateX(), -g.getTranslateY() + 1);
            this.drawCloud(g);
            g.setColor(18797);
            g.fillRect(0, dXKeo2, CCanvas.width, 500, false);
         }
         break;
      case 3:
         g.setColor(8711932);
         g.fillRect(0, 0, CScreen.w + 10, CScreen.h + 10, false);
         if (GameScr.curGRAPHIC_LEVEL != 2) {
            dXKeo = (Camera.x >> 2) % 72;
            dXKeo2 = CScreen.h - (Camera.y + CScreen.h >> 2) + this.yBackGr;

            for(dYKeo = 0; dYKeo <= nBgX + 1; ++dYKeo) {
               g.drawImage(rungRam, -dXKeo + dYKeo * 72, dXKeo2, mGraphics.LEFT | mGraphics.BOTTOM, false);
            }
         }
         break;
      case 4:
         g.setColor(14282750);
         g.fillRect(0, 0, CScreen.w + 10, CScreen.h + 10, false);
         if (GameScr.curGRAPHIC_LEVEL != 2) {
            dXKeo = (Camera.x >> 3) % 241;
            dXKeo2 = (Camera.x >> 2) % 226;
            dYKeo = CScreen.h - (Camera.y + CScreen.h >> 2) + this.yBackGr;

            for(i = 0; i <= nBgX + 1; ++i) {
               g.drawImage(back02, -dXKeo + i * 241, dYKeo - 5, mGraphics.LEFT | mGraphics.BOTTOM, false);
            }

            for(i = 0; i <= nBgX2 + 1; ++i) {
               g.drawImage(back01, -dXKeo2 + i * 226, dYKeo, mGraphics.LEFT | mGraphics.BOTTOM, false);
            }

            g.setColor(7434609);
            g.fillRect(0, dYKeo, CCanvas.width, 100, false);
            g.translate(-g.getTranslateX(), -g.getTranslateY() + 1);
            this.drawCloud(g);
         }
         break;
      case 5:
         g.setColor(16775152);
         g.fillRect(0, 0, CScreen.w + 10, CScreen.h + 10, false);
         if (GameScr.curGRAPHIC_LEVEL != 2) {
            dXKeo = (Camera.x >> 3) % 241;
            dXKeo2 = (Camera.x >> 2) % 241;
            dYKeo = CScreen.h - Camera.y / 2 + this.yBackGr;

            for(i = 0; i <= nBgX + 1; ++i) {
               g.drawImage(back04, -dXKeo + i * 241, dYKeo, mGraphics.LEFT | mGraphics.BOTTOM, false);
            }

            for(i = 0; i <= nBgX + 1; ++i) {
               g.drawImage(back03, -dXKeo2 + i * 241, dYKeo, mGraphics.LEFT | mGraphics.BOTTOM, false);
            }

            g.setColor(7905231);
            g.fillRect(0, dYKeo, CCanvas.width, 100, false);
         }
         break;
      case 6:
         g.setColor(16706268);
         g.fillRect(0, 0, CScreen.w + 10, CScreen.h + 10, false);
         if (GameScr.curGRAPHIC_LEVEL != 2) {
            dXKeo = (Camera.x >> 3) % 238;
            dXKeo2 = (Camera.x >> 2) % 225;
            dYKeo = CScreen.h - Camera.y / 2 + this.yBackGr;

            for(i = 0; i <= nBgX + 1; ++i) {
               g.drawImage(back06, -dXKeo + i * 238, dYKeo, mGraphics.LEFT | mGraphics.BOTTOM, false);
            }

            for(i = 0; i <= nBgX + 1; ++i) {
               g.drawImage(back05, -dXKeo2 + i * 225, dYKeo, mGraphics.LEFT | mGraphics.BOTTOM, false);
            }

            g.setColor(10268132);
            g.fillRect(0, dYKeo, CCanvas.width, 600, false);
            g.translate(-g.getTranslateX(), -g.getTranslateY() + 1);
            this.drawCloud(g);
         }
         break;
      case 7:
         g.setColor(15267327);
         g.fillRect(0, 0, CScreen.w + 10, CScreen.h + 10, false);
         if (GameScr.curGRAPHIC_LEVEL != 2) {
            dXKeo = (Camera.x >> 3) % 269;
            dXKeo2 = (Camera.x >> 2) % 368;
            dYKeo = CScreen.h - Camera.y / 3 + this.yBackGr - 100;
            i = CScreen.h - Camera.y / 2 + this.yBackGr;

            for(i = 0; i <= nBgX + 1; ++i) {
               g.drawImage(back08, -dXKeo + i * 269, dYKeo, mGraphics.LEFT | mGraphics.BOTTOM, false);
            }

            for(i = 0; i <= nBgX + 1; ++i) {
               g.drawImage(back08, -dXKeo + i * 269 + 30, dYKeo + 150, mGraphics.LEFT | mGraphics.BOTTOM, false);
            }

            for(i = 0; i <= nBgX + 1; ++i) {
               g.drawImage(back07, -dXKeo2 + i * 368 + 50, i, mGraphics.LEFT | mGraphics.BOTTOM, false);
            }
         }
      case 8:
      case 11:
      default:
         break;
      case 9:
         g.setColor(16752448);
         g.fillRect(0, 0, CScreen.w + 10, CScreen.h + 10, false);
         if (GameScr.curGRAPHIC_LEVEL != 2) {
            dXKeo = (Camera.x >> 3) % 238;
            dXKeo2 = (Camera.x >> 2) % 225;
            dYKeo = CScreen.h - Camera.y / 2 + this.yBackGr;

            for(i = 0; i <= nBgX + 1; ++i) {
               g.drawImage(back06, -dXKeo + i * 238, dYKeo, mGraphics.LEFT | mGraphics.BOTTOM, false);
            }

            for(i = 0; i <= nBgX + 1; ++i) {
               g.drawImage(back05, -dXKeo2 + i * 225, dYKeo, mGraphics.LEFT | mGraphics.BOTTOM, false);
            }

            g.setColor(12373247);
            g.fillRect(0, dYKeo, CCanvas.width, 100, false);
            g.translate(-g.getTranslateX(), -g.getTranslateY() + 1);
            this.drawCloud(g);
         }
         break;
      case 10:
         g.setColor(7106965);
         g.fillRect(0, 0, CScreen.w + 10, CScreen.h + 10, false);
         if (GameScr.curGRAPHIC_LEVEL != 2) {
            dXKeo = (Camera.x >> 3) % 238;
            dXKeo2 = (Camera.x >> 2) % 225;
            dYKeo = CScreen.h - Camera.y / 2 + this.yBackGr;

            for(i = 0; i <= nBgX + 1; ++i) {
               g.drawImage(back12, -dXKeo + i * 238, dYKeo, mGraphics.LEFT | mGraphics.BOTTOM, false);
            }

            for(i = 0; i <= nBgX + 1; ++i) {
               g.drawImage(back11, -dXKeo2 + i * 225, dYKeo, mGraphics.LEFT | mGraphics.BOTTOM, false);
            }

            g.setColor(3093573);
            g.fillRect(0, dYKeo, CCanvas.width, 600, false);
            g.translate(-g.getTranslateX(), -g.getTranslateY() + 1);
         }
         break;
      case 12:
         if (CCanvas.gameTick % 200 == 0) {
            this.boltActive = true;
         }

         g.setColor(530454);
         g.fillRect(0, 0, CScreen.w + 10, CScreen.h + 10, false);
         if (GameScr.curGRAPHIC_LEVEL != 2) {
            dXKeo = (Camera.x >> 4) % 108;
            dXKeo2 = (Camera.x >> 3) % 108;
            dYKeo = CScreen.h - (Camera.y + CScreen.h >> 2) + this.yBackGr;

            for(i = 0; i <= nBgX + 1; ++i) {
               g.drawImage(back15, -dXKeo + i * 108, dYKeo, mGraphics.LEFT | mGraphics.VCENTER, false);
            }

            if (this.boltActive) {
               ++this.tBolt;
               if (this.tBolt == 10) {
                  this.tBolt = 0;
                  this.boltActive = false;
               }

               if (this.tBolt % 2 == 0) {
                  g.setColor(16777215);
                  g.fillRect(0, dYKeo - 60, CCanvas.width, 130, false);
               }
            }

            if (this.snow != null) {
               g.setClip(0, dYKeo - 50, 1000, 120);
               this.snow.paintOnlySmall(g);
               g.setClip(0, 0, 2000, 2000);
            }

            g.setClip(0, dYKeo - 50, 1000, 100);
            g.setClip(0, 0, 2000, 2000);

            for(i = 0; i <= nBgX + 1; ++i) {
               g.drawImage(back14, -dXKeo2 + i * 108, dYKeo, mGraphics.LEFT | mGraphics.VCENTER, false);
            }

            g.translate(-g.getTranslateX(), -g.getTranslateY() + 1);
         }
         break;
      case 13:
         g.setColor(8229794);
         g.fillRect(0, 0, CScreen.w + 10, CScreen.h + 10, false);
         if (GameScr.curGRAPHIC_LEVEL != 2) {
            dXKeo = (Camera.x >> 2) % 144;
            dXKeo2 = (Camera.x >> 2) % 176;
            dYKeo = (Camera.x >> 3) % 69;
            i = CScreen.h - Camera.y / 3 + this.yBackGr;
            i = CScreen.h - Camera.y / 4 + this.yBackGr - 80;

            for(i = 0; i <= CCanvas.width / 69 + 1; ++i) {
               g.drawImage(map_spider_layout, -dYKeo + i * 69, i, mGraphics.LEFT | mGraphics.BOTTOM, false);
            }

            g.setColor(9728620);
            g.fillRect(0, i, CCanvas.width, 300, false);

            for(i = 0; i <= CCanvas.width / 144 + 1; ++i) {
               g.drawImage(rock_down, -dXKeo + i * 144, i, mGraphics.LEFT | mGraphics.BOTTOM, false);
            }

            g.setColor(3223857);
            g.fillRect(0, -500, CCanvas.width, -Camera.y / 3 + 120 + 500 - 88, false);

            for(i = 0; i <= CCanvas.width / 176 + 1; ++i) {
               g.drawImage(rock_up, -dXKeo2 + i * 176, -Camera.y / 3 + 120, mGraphics.LEFT | mGraphics.BOTTOM, false);
            }

            g.setColor(4996403);
            g.fillRect(0, i, CCanvas.width, 300, false);
         }
         break;
      case 14:
         g.setColor(6606845);
         g.fillRect(0, 0, CCanvas.width + 10, CCanvas.hieght + 10, false);
         if (GameScr.curGRAPHIC_LEVEL != 2) {
            dXKeo = (Camera.x >> 2) % 128;
            dXKeo2 = (Camera.x >> 3) % 128;
            dYKeo = CCanvas.hieght;

            for(i = 0; i <= CCanvas.width / 128 + 1; ++i) {
               g.drawImage(bg_cloud_1, -dXKeo2 + i * 128, dYKeo + 10, mGraphics.LEFT | mGraphics.BOTTOM, false);
            }

            for(i = 0; i <= CCanvas.width / 128 + 1; ++i) {
               g.drawImage(bg_cloud, -dXKeo + i * 128, dYKeo, mGraphics.LEFT | mGraphics.BOTTOM, false);
            }

            this.drawSun(g);
         }
         break;
      case 15:
         g.setColor(16694934);
         g.fillRect(0, 0, CCanvas.width + 10, CCanvas.hieght + 10, false);
         if (GameScr.curGRAPHIC_LEVEL != 2) {
            this.drawSun2(g);
            dXKeo = (Camera.x >> 2) % 241;
            dXKeo2 = (Camera.x >> 3) % 241;
            dYKeo = CCanvas.hieght - Camera.y / 3 - this.yBackGr;
            i = CCanvas.hieght - 10 - Camera.y / 4 - this.yBackGr;

            for(i = 0; i <= CCanvas.width / 241 + 1; ++i) {
               g.drawImage(back16, -dXKeo2 + i * 241, i, mGraphics.LEFT | mGraphics.BOTTOM, false);
            }

            g.setColor(7628133);
            g.fillRect(0, i, CCanvas.width, 200, false);

            for(i = 0; i <= CCanvas.width / 241 + 1; ++i) {
               g.drawImage(back17, -dXKeo + i * 241, dYKeo, mGraphics.LEFT | mGraphics.BOTTOM, false);
            }

            g.setColor(2491910);
            g.fillRect(0, dYKeo, CCanvas.width, 200, false);
         }
      }

      g.translate(-Camera.x, -Camera.y);
   }

   public void drawSun(mGraphics g) {
      g.drawImage(sun, sunX, sunY, 0, false);
   }

   public void drawSun2(mGraphics g) {
      g.drawImage(sun2, sunX, sunY, 0, false);
   }

   public void drawWave(mGraphics g) {
      if (GameScr.curGRAPHIC_LEVEL == 0) {
         g.setColor(16777215);

         for(int i = 0; i < nWave; ++i) {
            g.drawLine(wavex[i], wavey[i], wavex[i] + length[i], wavey[i], false);
         }

      }
   }

   private void drawCloud(mGraphics g) {
      for(int i = 0; i < this.cloudx.length; ++i) {
         g.drawImage(cloud, this.cloudx[i], this.cloudy[i] + this.yCloud, 0, false);
      }

   }

   public static void drawWater(byte WaterOrGlass, mGraphics g) {
      int i;
      int start;
      int end;
      if (WaterOrGlass == 0) {
         start = Camera.x / 24;
         end = (Camera.x + CScreen.w) / 24;

         for(i = start; i <= end; ++i) {
            waterSp.drawFrame(CCanvas.gameTick % 8 > 4 ? 0 : 1, i * 24, waterY - 12 + inWater.image.getHeight() / 2, 0, 0, g);
            g.drawImage(inWater, i * 24, waterY - 12 + 24 + inWater.image.getHeight() / 2, 0, false);
         }
      } else if (WaterOrGlass == 1) {
         start = Camera.x / 64;
         end = (Camera.x + CScreen.w) / 64;

         for(i = start; i <= end; ++i) {
            g.drawImage(co, i * 64, glassY, 0, false);
         }
      } else if (WaterOrGlass == 2) {
         start = Camera.x / 64;
         end = (Camera.x + CScreen.w) / 64;

         for(i = start; i <= end; ++i) {
            g.drawImage(co2, i * 64, glassY, 0, false);
         }
      }

   }

   public static void paintTree(mGraphics g) {
      for(int i = 0; i < 7; ++i) {
         if (xT[i] >= 0 && xT[i] <= CCanvas.width) {
            g.drawImage(b, xT[i], yT[i], mGraphics.HCENTER | mGraphics.VCENTER, false);
         }

         for(int j = 0; j < (CCanvas.hieght - yT[i]) / 21 + 1; ++j) {
            g.drawImage(a, xT[i], yT[i] + j * 21 + b.image.getHeight() / 2, mGraphics.TOP | mGraphics.HCENTER, false);
         }
      }

   }

   public static void paintMenuBackGround(mGraphics g) {
      g.setColor(6606845);
      g.fillRect(0, 0, CCanvas.width, CCanvas.hieght, false);

      for(int i = 0; i <= CCanvas.width; i += 32) {
         g.drawImage(PrepareScr.imgBack, i, CCanvas.hieght - 62, 0, false);
      }

   }

   public void onClearMap() {
   }
}
