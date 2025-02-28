package item;

import CLib.Image;
import CLib.mGraphics;
import CLib.mImage;
import Equipment.PlayerEquip;
import coreLG.CCanvas;
import effect.Explosion;
import effect.Tornado;
import java.util.Vector;
import map.MM;
import model.CRes;
import model.FrameImage;
import model.IAction2;
import model.Position;
import network.GameService;
import player.CPlayer;
import player.PM;
import screen.GameScr;

public class BM {
   public static mImage airFighter;
   public Vector<Bullet> bullets = new Vector();
   public byte numShoot = 0;
   int x;
   int y;
   public static int angle;
   public static byte force;
   byte type;
   byte whoShot;
   byte delayBullCound = -1;
   byte delayBullType = -1;
   int nDelayBull = 1;
   int nLazerDelay = 0;
   int nMeteorDelay = 0;
   int timedelay = 0;
   boolean isEndDelayBull = true;
   public static boolean active;
   static boolean isActiveAirFly;
   static boolean isActiveBomBay;
   static boolean isActiveLazer;
   public static boolean isActiveTornado;
   static boolean isAciveExplore;
   static boolean isActiveEgg;
   static boolean isActiveMeteor;
   static boolean isActive4Missile;
   static boolean isActiveMissileRain;
   public int nBull;
   static int nArray;
   public static int nBum;
   public static int[] bumX;
   public static int[] bumY;
   public static int[] bumX_Last;
   public static int[] bumY_Last;
   static final int bombangle = 0;
   public static int airPlaneStartVx;
   static final byte bombForce = 5;
   public static final int rangeActive = 165;
   public static final int airPlaneStartX = 400;
   public static final int airPlaneStartY = 320;
   public static int airPlaneX;
   public static int airPlaneY;
   int airPlaneVx;
   static int lazerX;
   static int lazerY;
   static int tonardoX;
   static int tonardoY;
   public static Vector vTornado;
   static int exploreX;
   static int exploreY;
   static byte exploreForce;
   static int exploreAngel;
   static int exploreVx;
   static int exploreVy;
   public static boolean allSendENDSHOOT;
   public static boolean shootNextStep;
   static int eggX;
   static int eggY;
   static int meteorX;
   static int meteorY;
   static int meteorDesX;
   static int meteorDesY;
   static int missileXS;
   static int missileYS;
   static int missileXD;
   static int missileYD;
   static int missileP;
   static int missleAngle;
   static int mRainX;
   static int mRainY;
   int nWasShoot;
   static int xChicken;
   static int yChicken;
   public static int nOrbit;
   public static boolean activeUFOLazer;
   public static Vector lazerPosition;
   public FrameImage[][] frameBulletSpecial = new FrameImage[58][2];
   public mImage bulletChicken;
   short[][] xPaint;
   short[][] yPaint;
   short[][] xHit;
   short[][] yHit;
   int bIndex = 0;
   public static byte force2;
   public static boolean isBombBalloon;
   public byte critical;
   private int idBullet;
   boolean isDouble = false;
   boolean endShoot;

   static {
      airFighter = GameScr.airFighter;
      active = false;
      nArray = 20;
      nBum = 0;
      bumX = new int[nArray];
      bumY = new int[nArray];
      bumX_Last = new int[nArray];
      bumY_Last = new int[nArray];
      airPlaneStartVx = 20;
      vTornado = new Vector();
      allSendENDSHOOT = false;
      shootNextStep = true;
      lazerPosition = new Vector();
   }

   public BM() {
      active = false;
      this.nBull = 0;
      this.numShoot = 0;
   }

   public void setBullType(byte critical, byte whoShot, byte type, short[][] xPaint, short[][] yPaint, byte nShoot, byte force2, short[][] xHit, short[][] yHit, int idBullet) {
      this.type = type;
      this.xPaint = xPaint;
      this.yPaint = yPaint;
      this.xHit = xHit;
      this.yHit = yHit;
      this.critical = critical;
      this.idBullet = idBullet;
      xChicken = this.x;
      yChicken = this.y;
      this.whoShot = whoShot;
      BM.force2 = force2;
      active = true;
      this.numShoot = nShoot;
      if (this.numShoot == 2) {
         this.isDouble = true;
      }

      this.nWasShoot = 1;
      this.bIndex = 0;
      allSendENDSHOOT = false;
      shootNextStep = true;
      this.endShoot = false;
      isBombBalloon = false;
      if (type == 43) {
         isBombBalloon = true;
      }

   }

   private void createShootInfo() {
      System.out.println("===================>BM CReateBullet nShoot: " + this.type);
      this.isEndDelayBull = true;
      nBum = 0;

      for(int i = 0; i < nArray; ++i) {
         bumX[i] = -1;
         bumY[i] = -1;
         bumX_Last[i] = -1;
         bumY_Last[i] = -1;
      }

      this.createBullet(this.type);
      boolean isFocusCAM = true;
      int camIndex = 0;
      switch(this.type) {
      case 0:
      case 32:
      case 40:
      case 41:
      case 48:
      case 49:
         this.nBull = 1;
         break;
      case 1:
         this.delayBullType = 5;
         this.isEndDelayBull = false;
         this.nBull = this.critical == 0 ? 2 : 6;
         break;
      case 2:
         camIndex = 1;
         if (this.critical == 0) {
            this.nBull = 3;
         }

         if (this.critical == 1) {
            this.nBull = 7;
         }
      case 3:
      case 12:
      case 15:
      case 18:
      case 20:
      case 24:
      case 27:
      case 29:
      case 38:
      case 39:
      case 46:
      case 53:
      default:
         break;
      case 4:
         isActiveAirFly = false;
         isActiveBomBay = false;
         airPlaneX = 400;
         airPlaneY = airPlaneStartVx;
         this.nBull = 2;
         break;
      case 5:
      case 36:
         this.nBull = 1;
         break;
      case 6:
         camIndex = 1;
         this.nBull = 3;
         break;
      case 7:
      case 31:
         this.nBull = 1;
         break;
      case 8:
         this.nBull = 1;
         break;
      case 9:
         this.nBull = 4;
         break;
      case 10:
         if (this.critical == 0 || this.critical == 1) {
            this.delayBullType = 3;
            this.isEndDelayBull = false;
            this.nBull = 3;
         }
         break;
      case 11:
         this.delayBullType = 5;
         this.isEndDelayBull = false;
         this.nBull = 5;
         break;
      case 13:
         this.nBull = 1;
         break;
      case 14:
         this.nBull = 2;
         break;
      case 16:
         this.nBull = 7;
         break;
      case 17:
         this.nBull = 1;
         break;
      case 19:
         this.nBull = 1;
         break;
      case 21:
         this.nBull = 1;
         break;
      case 22:
         this.nBull = 1;
         break;
      case 23:
         this.nBull = 8;
         break;
      case 25:
         this.nBull = 1;
         break;
      case 26:
         this.nBull = 5;
         break;
      case 28:
         this.nBull = 14;
         break;
      case 30:
         this.nBull = 1;
         break;
      case 33:
         this.delayBullType = 5;
         this.isEndDelayBull = false;
         this.nBull = 5;
         break;
      case 34:
         this.nBull = 1;
      case 35:
         this.nBull = 1;
         break;
      case 37:
         this.nBull = 1;
         break;
      case 42:
         this.nBull = 1;
         break;
      case 43:
         this.delayBullType = 11;
         this.isEndDelayBull = false;
         this.nBull = 10;
         break;
      case 44:
         this.delayBullType = 3;
         this.isEndDelayBull = false;
         this.nBull = 15;
         break;
      case 45:
         this.nBull = 1;
         break;
      case 47:
         this.delayBullType = 2;
         this.isEndDelayBull = false;
         this.nBull = 5;
         break;
      case 50:
         this.nBull = 1;
         break;
      case 51:
         this.nBull = 1;
         break;
      case 52:
         this.nBull = 1;
         break;
      case 54:
         this.nBull = 1;
         break;
      case 55:
         this.nBull = 1;
         break;
      case 56:
         this.nBull = 3;
         break;
      case 57:
         this.nBull = 1;
      }

      if (isFocusCAM && this.type != 43) {
         GameScr.cam.setBulletMode((Bullet)this.bullets.elementAt(camIndex));
      }

      this.nDelayBull = this.nBull;
      this.delayBullCound = this.delayBullType;
   }

   private void createBullet(byte Type) {
      int var10000 = this.nBull * (this.nWasShoot - 1);
      int i;
      switch(Type) {
      case 0:
         Bullet b = new Bullet(Type, this.xPaint[this.bIndex], this.yPaint[this.bIndex], this.whoShot, this.idBullet);
         this.bullets.addElement(b);
         ++this.bIndex;
         break;
      case 1:
         this.bullets.addElement(new Bullet(Type, this.xPaint[this.bIndex], this.yPaint[this.bIndex], this.whoShot, this.idBullet));
         ++this.bIndex;
         break;
      case 2:
         int m = this.critical == 0 ? 3 : 7;

         for(i = 0; i < m; ++i) {
            this.bullets.addElement(new Bullet(Type, this.xPaint[i + this.bIndex], this.yPaint[i + this.bIndex], this.whoShot, this.idBullet));
         }

         this.bIndex += 3;
         break;
      case 3:
         this.bullets.addElement(new Bullet(Type, this.xPaint[1], this.yPaint[1], this.whoShot));
         GameScr.isDarkEffect = false;
         break;
      case 4:
         this.bullets.addElement(new Bullet(Type, this.xPaint[0], this.yPaint[0], this.whoShot));
         break;
      case 5:
      case 36:
         this.bullets.addElement(new Bullet(Type, this.xPaint[0], this.yPaint[0], this.whoShot));
         break;
      case 6:
         for(i = 0; i < 3; ++i) {
            this.bullets.addElement(new Bullet(Type, this.xPaint[i], this.yPaint[i], this.whoShot, this.idBullet));
         }

         return;
      case 7:
      case 31:
         this.bullets.addElement(new Bullet(Type, this.xPaint[0], this.yPaint[0], this.whoShot));
         break;
      case 8:
         this.bullets.addElement(new Bullet(Type, this.xPaint[0], this.yPaint[0], this.whoShot));
         break;
      case 9:
         for(i = 0; i < 4; ++i) {
            this.bullets.addElement(new Bullet(Type, this.xPaint[i + this.bIndex], this.yPaint[i + this.bIndex], this.whoShot, this.idBullet));
         }

         this.bIndex += 4;
         break;
      case 10:
         this.bullets.addElement(new Bullet(Type, this.xPaint[this.bIndex], this.yPaint[this.bIndex], this.whoShot, this.idBullet));
         ++this.bIndex;
         break;
      case 11:
         CRes.out("ID BULLET= " + this.idBullet);
         this.bullets.addElement(new Bullet(Type, this.xPaint[this.bIndex], this.yPaint[this.bIndex], this.whoShot, this.idBullet));
         ++this.bIndex;
         break;
      case 12:
         for(i = 1; i <= 6; ++i) {
            this.bullets.addElement(new Bullet(Type, this.xPaint[i], this.yPaint[i], this.whoShot));
         }

         return;
      case 13:
      case 17:
         this.bullets.addElement(new Bullet(Type, this.xPaint[this.bIndex], this.yPaint[this.bIndex], this.whoShot, this.idBullet));
         ++this.bIndex;
         break;
      case 14:
      case 40:
         this.bullets.addElement(new Bullet(Type, this.xPaint[0], this.yPaint[0], this.whoShot));
         break;
      case 15:
         this.bullets.addElement(new Bullet(Type, this.xPaint[1], this.yPaint[1], this.whoShot));
         break;
      case 16:
         this.bullets.addElement(new Bullet((byte)16, this.xPaint[0], this.yPaint[0], this.whoShot));
         break;
      case 18:
         if (!this.endShoot) {
            for(i = 0; i < 3; ++i) {
               this.bullets.addElement(new Bullet(Type, this.xPaint[this.bIndex], this.yPaint[this.bIndex], this.whoShot, this.idBullet));
               ++this.bIndex;
            }
         }
         break;
      case 19:
         this.bullets.addElement(new Bullet(Type, this.xPaint[this.bIndex], this.yPaint[this.bIndex], this.whoShot, this.idBullet));
         ++this.bIndex;
         break;
      case 20:
         this.bullets.addElement(new Bullet(Type, this.xPaint[this.bIndex], this.yPaint[this.bIndex], this.whoShot));
         ++this.nBull;
         ++this.bIndex;
         break;
      case 21:
         this.bullets.addElement(new Bullet(Type, this.xPaint[this.bIndex], this.yPaint[this.bIndex], this.whoShot, this.idBullet));
         ++this.bIndex;
         break;
      case 22:
         this.bullets.addElement(new Bullet(Type, this.xPaint[this.bIndex], this.yPaint[this.bIndex], this.whoShot));
         break;
      case 23:
         this.bullets.addElement(new Bullet(Type, this.xPaint[0], this.yPaint[0], this.whoShot));
         break;
      case 24:
         for(i = 1; i <= 7; ++i) {
            this.bullets.addElement(new Bullet(Type, this.xPaint[i], this.yPaint[i], this.whoShot));
         }

         return;
      case 25:
         this.bullets.addElement(new Bullet(Type, this.xPaint[0], this.yPaint[0], this.whoShot));
         break;
      case 26:
         this.bullets.addElement(new Bullet(Type, this.xPaint[0], this.yPaint[0], this.whoShot));
         break;
      case 27:
         for(i = 1; i <= 4; ++i) {
            this.bullets.addElement(new Bullet(Type, this.xPaint[i], this.yPaint[i], this.whoShot));
         }

         return;
      case 28:
         this.bullets.addElement(new Bullet(Type, this.xPaint[0], this.yPaint[0], this.whoShot));
         break;
      case 29:
         for(i = 1; i <= 13; ++i) {
            this.bullets.addElement(new Bullet(Type, this.xPaint[i], this.yPaint[i], this.whoShot));
         }

         return;
      case 30:
         this.bullets.addElement(new Bullet(Type, this.xPaint[0], this.yPaint[0], this.whoShot));
         break;
      case 32:
      case 34:
      case 35:
      case 37:
      case 41:
      case 42:
      case 45:
      case 50:
      case 51:
      case 52:
      case 54:
      case 55:
      case 57:
         this.bullets.addElement(new Bullet(Type, this.xPaint[this.bIndex], this.yPaint[this.bIndex], this.whoShot));
         ++this.bIndex;
         break;
      case 33:
      case 44:
      case 47:
         this.bullets.addElement(new Bullet(Type, this.xPaint[this.bIndex], this.yPaint[this.bIndex], this.whoShot));
         ++this.bIndex;
      case 38:
      case 39:
      case 46:
      case 53:
      default:
         break;
      case 43:
         this.bullets.addElement(new Bullet(Type, this.xPaint[this.bIndex], this.yPaint[this.bIndex], this.whoShot));
         ++this.bIndex;
         break;
      case 48:
         this.bullets.addElement(new Bullet(Type, this.xPaint[this.bIndex], this.yPaint[this.bIndex], this.whoShot, this.xHit[this.bIndex], this.yHit[this.bIndex]));
         ++this.bIndex;
         break;
      case 49:
         this.bullets.addElement(new Bullet(Type, this.xPaint[this.bIndex], this.yPaint[this.bIndex], this.whoShot, this.idBullet));
         ++this.bIndex;
         break;
      case 56:
         for(i = 0; i < 3; ++i) {
            this.bullets.addElement(new Bullet(Type, this.xPaint[i + this.bIndex], this.yPaint[i + this.bIndex], this.whoShot));
         }

         this.bIndex += 3;
      }

   }

   public void onInitSpecialBullet() {
      CCanvas.cutImage(PlayerEquip.bullets[7], 0, new IAction2() {
         public void perform(Object object) {
            BM.this.frameBulletSpecial[21][0] = new FrameImage((Image)object, 4);
         }
      });
      CCanvas.cutImage(PlayerEquip.bullets[3], this.idBullet, new IAction2() {
         public void perform(Object object) {
            BM.this.frameBulletSpecial[9][0] = new FrameImage((Image)object, 4);
         }
      });
      CCanvas.cutImage(PlayerEquip.bullets[8], this.idBullet, new IAction2() {
         public void perform(Object object) {
            BM.this.frameBulletSpecial[17][0] = new FrameImage((Image)object, 8);
         }
      });
      CCanvas.cutImage(PlayerEquip.bullets[6], this.idBullet, new IAction2() {
         public void perform(Object object) {
            BM.this.bulletChicken = new mImage((Image)object);
         }
      });
      this.frameBulletSpecial[7][0] = new FrameImage(Bullet.grenadeImg.image, 8);
      this.frameBulletSpecial[9][1] = new FrameImage(Bullet.chuoiImg2.image, 4);
      this.frameBulletSpecial[21][1] = new FrameImage(Bullet.boomerangBig.image, 4);
      this.frameBulletSpecial[2][0] = new FrameImage(PlayerEquip.bullets[2].image, 20, 20);
      this.frameBulletSpecial[2][1] = new FrameImage(PlayerEquip.bullets[2].image, 20, 20);
   }

   public void update() {
      try {
         int i;
         for(i = 0; i < this.bullets.size(); ++i) {
            ((Bullet)this.bullets.elementAt(i)).fixedUpdate();
         }

         if (!this.isEndDelayBull) {
            if (!isBombBalloon) {
               --this.delayBullCound;
               if (this.delayBullCound == 0 && this.nDelayBull > 0) {
                  this.createBullet(this.type);
                  this.delayBullCound = this.delayBullType;
                  --this.nDelayBull;
                  if (this.nDelayBull <= 1) {
                     this.isEndDelayBull = true;
                  }
               }
            } else {
               CPlayer p = PM.p[this.whoShot];
               if (this.xPaint[this.bIndex][0] <= p.x + 10 && this.xPaint[this.bIndex][0] >= p.x - 10) {
                  this.createBullet(this.type);
                  --this.nDelayBull;
                  if (this.nDelayBull <= 1) {
                     this.isEndDelayBull = true;
                  }
               }
            }
         } else if (this.bullets.size() == 0 && this.nBull <= 0 && this.numShoot > 0 && !allSendENDSHOOT) {
            this.createShootInfo();
            --this.numShoot;
            ++this.nWasShoot;
            if (this.nWasShoot == this.numShoot) {
               this.nWasShoot = 0;
            }
         }

         if (isActiveAirFly) {
            airPlaneX += airPlaneStartVx;
            if (!isActiveBomBay && airPlaneX >= this.x - 165) {
               this.createBullet((byte)3);
               GameScr.cam.setBulletMode((Bullet)this.bullets.elementAt(0));
               isActiveBomBay = true;
            }

            if (airPlaneX >= MM.mapWidth) {
               isActiveAirFly = false;
            }
         }

         if (isActiveLazer) {
            ++this.nLazerDelay;
            if (this.nLazerDelay == 10) {
               GameScr.cam.setTargetPointMode(lazerX, 100);
            }

            if (this.nLazerDelay == 20) {
               this.nLazerDelay = 0;
               isActiveLazer = false;
               CRes.out("======> CReate Bullet Lazer ");
               this.createBullet((byte)15);
               GameScr.cam.setLazerMode((Bullet)this.bullets.elementAt(0));
            }
         }

         if (vTornado.size() != 0) {
            isActiveTornado = true;

            for(i = 0; i < vTornado.size(); ++i) {
               ((Tornado)vTornado.elementAt(i)).update();
            }
         } else {
            isActiveTornado = false;
         }

         if (isAciveExplore) {
            isAciveExplore = false;
            this.createBullet((byte)18);
            GameScr.cam.setBulletMode((Bullet)this.bullets.elementAt(0));
            ((Bullet)this.bullets.elementAt(0)).vx = exploreVx;
            ((Bullet)this.bullets.elementAt(0)).vy = exploreVy;
         }

         if (isActiveEgg) {
            isActiveEgg = false;
            this.createBullet((byte)20);
            if (eggX > -500 && eggY > -500) {
               GameScr.cam.setBulletMode((Bullet)this.bullets.elementAt(this.bullets.size() - 1));
            }
         }

         if (isActiveMeteor) {
            ++this.nMeteorDelay;
            if (this.nMeteorDelay == 20) {
               this.nMeteorDelay = 0;
               isActiveMeteor = false;
               this.createBullet((byte)24);
               GameScr.cam.setMeteorMode((Bullet)this.bullets.elementAt(0));
            }
         }

         if (isActive4Missile) {
            isActive4Missile = false;
            this.createBullet((byte)27);
            GameScr.cam.setBulletMode((Bullet)this.bullets.elementAt(0));
         }

         if (isActiveMissileRain) {
            isActiveMissileRain = false;
            this.createBullet((byte)29);
            GameScr.cam.setMRainMode((Bullet)this.bullets.elementAt(0));
         }

         if (activeUFOLazer && CCanvas.gameTick % 3 == 0) {
            if (lazerPosition.size() != 0) {
               Position pos = (Position)lazerPosition.elementAt(0);
               GameScr.sm.addLazer(pos.xF, pos.yF, pos.xT, pos.yT, 1);
               int X = pos.xT + CRes.random(-35, 35);
               int Y = pos.yT + CRes.random(-10, 10);
               new Explosion(pos.xT, pos.yT, (byte)9);
               new Explosion(X, Y, (byte)9);
               new Explosion(X + CRes.random(-30, 30), Y + CRes.random(-10, 10), (byte)9);
               lazerPosition.removeElement(pos);
            } else {
               activeUFOLazer = false;
            }
         }
      } catch (Exception var4) {
         var4.printStackTrace();
         this.endShoot();
      }

   }

   public void removeAll(int x, int y, int x_Last, int y_Last, byte whoShot) {
      for(int i = 0; i < this.bullets.size(); ++i) {
         Bullet bull = (Bullet)this.bullets.elementAt(i);
         this.removeBullet(bull, true, x, y, x_Last, y_Last, whoShot);
      }

   }

   public void paint(mGraphics g) {
      int i;
      for(i = 0; i < this.bullets.size(); ++i) {
         ((Bullet)this.bullets.elementAt(i)).paint(g);
      }

      if (isActiveAirFly) {
         g.drawImage(airFighter, airPlaneX, airPlaneY + CCanvas.gameTick % 3, mGraphics.VCENTER | mGraphics.HCENTER, false);
      }

      if (isActiveLazer && this.nLazerDelay > 5 && this.nLazerDelay < 20) {
         g.setColor(16771821);
         g.fillRect(lazerX - 2, -100, 4, lazerY + 100, false);
      }

      if (isActiveTornado) {
         for(i = 0; i < vTornado.size(); ++i) {
            ((Tornado)vTornado.elementAt(i)).paint(g);
         }
      }

   }

   public boolean isHaveEgg() {
      for(int i = 0; i < this.bullets.size(); ++i) {
         if (((Bullet)this.bullets.elementAt(i)).type == 20) {
            return true;
         }
      }

      return false;
   }

   public void removeBullet(Bullet b, boolean isExplode, int x, int y, int x_Last, int y_Last, byte whoShot) {
      this.bullets.removeElement(b);
      if (b.type == 45) {
         this.endShoot();
      } else {
         if (this.bullets.size() > 0) {
            Bullet bull = (Bullet)this.bullets.elementAt(0);
            if (bull.type != 19 && bull.type != 43) {
               if (bull.type == 29) {
                  GameScr.cam.setMRainMode(bull);
               } else {
                  GameScr.cam.setBulletMode(bull);
               }
            }
         }

         if (!Bullet.isFlagBull(b.type) || this.type == 14) {
            ++nBum;
            int n = nBum - 1;
            if (isExplode) {
               bumX[n] = x;
               bumY[n] = y;
               bumX_Last[n] = x_Last;
               bumY_Last[n] = y_Last;
            } else {
               bumX[n] = -1;
               bumY[n] = -1;
               bumX_Last[n] = -1;
               bumY_Last[n] = -1;
            }
         }

         if (Bullet.isFlagBull(b.type)) {
            if (isExplode) {
               this.x = x;
               this.y = y;
            } else {
               this.nBull = 1;
            }
         }

         if (this.type == 19) {
            this.x = xChicken;
            this.y = yChicken;
         }

         --this.nBull;
         if (this.nBull == 0 && this.numShoot == 0) {
            if (whoShot == GameScr.myIndex) {
               allSendENDSHOOT = true;
               shootNextStep = true;
            }

            if (this.numShoot == 0) {
               this.endShoot();
            }

            GameService.gI().check_cross((byte)nBum, bumX_Last, bumY_Last);
         }

      }
   }

   public static void removeTornado() {
      vTornado.removeAllElements();
   }

   public void tornadoTurnUpd() {
      if (isActiveTornado) {
         for(int i = 0; i < vTornado.size(); ++i) {
            Tornado tornado = (Tornado)vTornado.elementAt(i);
            --tornado.nturn;
         }
      }

   }

   public void endShoot() {
      active = false;
      this.nBull = 0;
      this.numShoot = 0;
      this.endShoot = true;
      CPlayer.isStopFire = false;
      this.tornadoTurnUpd();
      CRes.out("END SHOOT");
      CCanvas.lockNotify = true;
      CCanvas.tNotify = 0;
      CPlayer.closeMirror = true;
   }

   public void activeAirplane(int X, int Y) {
      isActiveAirFly = true;
      airPlaneX = X - 400;
      airPlaneY = Y - 320;
      GameScr.cam.setTargetPointMode(X - 180, Y - 320);
      GameScr.isDarkEffect = false;
   }

   public void activeLazer(int X, int Y) {
      isActiveLazer = true;
      lazerX = X;
      lazerY = Y;
      GameScr.cam.setTargetPointMode(X, Y);
   }

   public void activeTornado(int X, int Y) {
      vTornado.addElement(new Tornado(X, Y, 3));
      tonardoX = X;
      tonardoY = Y;
   }

   public void activeExplore(int XD, int YD, int X, int Y, int VX, int VY, int force, int angle) {
      isAciveExplore = true;
      exploreX = X;
      exploreY = Y;
      exploreVx = 0;
      exploreVy = 0;
      exploreForce = (byte)force;
      int dx2 = this.x - X;
      int dy2 = this.y - Y;
      int a2 = CRes.angle(dx2, dy2);
      exploreForce = (byte)(force / 2);
      exploreAngel = 180 - (angle + a2);
   }

   public void activeEgg(int X, int Y) {
      isActiveEgg = true;
      eggX = X;
      eggY = Y;
   }

   public void activeMortarBum(int x, int y) {
      this.x = x;
      this.y = y - 500;
      this.createBullet((byte)12);
   }

   public void active4Missle(int XSource, int YSource, int XDes, int YDes) {
      isActive4Missile = true;
      missileXS = XSource;
      missileYS = YSource;
      missileXD = XDes;
      missileYD = YDes;
      missileP = angle < 90 && angle > -90 ? 1 : -1;
      missleAngle = missileP > 0 ? angle : 180 - angle;
   }

   public void activeMeteor(int X, int Y, int angle) {
      isActiveMeteor = true;
      meteorX = X;
      meteorY = -30;
      meteorDesX = X;
      meteorDesY = Y;
   }

   public void activeMissleRain(int X, int Y) {
      isActiveMissileRain = true;
      mRainX = X;
      mRainY = Y;
   }

   public void onClear() {
   }
}
