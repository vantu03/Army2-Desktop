package screen;

import CLib.mGraphics;
import CLib.mImage;
import com.teamobi.mobiarmy2.MainGame;
import coreLG.CCanvas;
import java.util.Vector;
import map.MM;
import model.Font;
import model.IAction;
import model.Language;
import network.Command;
import network.GameService;

public class RoomListScr extends CScreen {
   public static mImage imgArrowRed;
   public static mImage imgMap;
   public static mImage imgCurPos;
   public static mImage imgSmallCloud;
   public static Vector roomList;
   int selected;
   public int cmtoY;
   public int cmy;
   public int cmdy;
   public int cmvy;
   public int cmyLim;
   int nBoardPerLine;
   int defX;
   int numH;
   int dis;
   static int nMap;
   static int curMapIndex;
   static int[] _iconX;
   static int _centerIX;
   static int rangeSplit;
   static int imgMapIW;
   static int imgMapIH;
   static boolean isMoveMenu;
   static int[] cloudType;
   static int[] cloudX;
   static int[] cloudY;
   static int curPosIndex;
   static int radaX;
   static int radaY;
   static int mapX;
   static int mapY;
   static int k;
   static int roundCamera;
   public int NUMB;
   public boolean isBoss;
   int pa = 0;
   boolean trans = false;
   int speed = 1;

   static {
      imgMap = GameScr.imgMap;
      imgCurPos = GameScr.imgCurPos;
      imgSmallCloud = GameScr.imgSmallCloud;
      imgArrowRed = GameScr.imgArrowRed;
      nMap = MM.NUM_MAP;
      curMapIndex = 0;
      _iconX = new int[MM.NUM_MAP];
      rangeSplit = 100;
      imgMapIW = 80;
      imgMapIH = 50;
      isMoveMenu = false;
      mapX = 0;
      mapY = 0;
      k = 0;
      roundCamera = 80;
   }

   public void moveCamera() {
      if (this.cmy != this.cmtoY) {
         this.cmvy = this.cmtoY - this.cmy << 2;
         this.cmdy += this.cmvy;
         this.cmy += this.cmdy >> 4;
         this.cmdy &= 15;
      }

   }

   public void init() {
      this.nameCScreen = " RoomListScr screen!";
      this.NUMB = !this.isBoss ? MM.NUM_MAP - PrepareScr.mapBossID.length : PrepareScr.mapBossID.length;
      nMap = this.NUMB;
      this.nBoardPerLine = CCanvas.width / 90;
      this.defX = (CCanvas.width - this.nBoardPerLine * 90 >> 1) + 40;

      int y;
      for(y = 0; y < nMap; ++y) {
         _iconX[y] = y * rangeSplit;
      }

      _centerIX = w >> 1;
      curMapIndex = nMap / 2;
      this.dis = (_centerIX - _iconX[curMapIndex]) / 2;
      isMoveMenu = true;
      if (cloudX == null) {
         cloudType = new int[]{0, 1, 1};
         cloudX = new int[]{-50, 100, 190};
         cloudY = new int[]{-20, 0, 130};
      }

      this.center = new Command(Language.select(), new IAction() {
         public void perform() {
            RoomListScr.this.doSelect();
         }
      });
      this.right = new Command(Language.close(), new IAction() {
         public void perform() {
            CCanvas.prepareScr.show();
         }
      });
      this.dis = CCanvas.hieght - (5 + cmdH);
      this.numH = this.NUMB / this.nBoardPerLine;
      if (this.NUMB % this.nBoardPerLine != 0) {
         ++this.numH;
      }

      y = this.numH * 57 + 40;
      this.cmyLim = y - this.dis;
      if (this.cmyLim < 0) {
         this.cmyLim = 0;
      }

      this.setCamY();
   }

   public void show() {
      this.init();
      super.show();
   }

   protected void doSelect() {
      CCanvas.startWaitDlg(Language.starting());
      int tem = this.isBoss ? MM.NUM_MAP - PrepareScr.mapBossID.length : 0;
      if (curMapIndex < this.NUMB) {
         byte id = (byte)(curMapIndex + tem);
         if (id != -1) {
            GameService.gI().mapSelect(id);
            CCanvas.startWaitDlgWithoutCancel(Language.pleaseWait(), 11);
         }
      }
   }

   public void paint(mGraphics g) {
      paintDefaultBg(g);
      this.paintRoomList(g);
      super.paint(g);
   }

   public static void paintMapBar(int y, mGraphics g) {
   }

   private void paintRoomList(mGraphics g) {
      g.translate(0, -this.cmy);
      int tem = this.isBoss ? MM.NUM_MAP - PrepareScr.mapBossID.length : 0;

      for(int i = 0; i < this.NUMB; ++i) {
         int xP = i % this.nBoardPerLine;
         int yP = i / this.nBoardPerLine;
         int x = xP * 90 + this.defX;
         int y = yP * 57 + 60;
         if (i == curMapIndex) {
            g.setColor(3684546);
            g.fillRect(x - (imgMapIW >> 1) - 5, y - (imgMapIH >> 1) - 5, imgMapIW + 10, imgMapIH + 10, false);
         }

         g.setColor(16777215);
         g.fillRect(x - (imgMapIW >> 1) + 1, y - (imgMapIH >> 1) + 1, imgMapIW - 2, imgMapIH - 2, false);

         try {
            if (PrepareScr.imgMap[i + tem] != null) {
               g.drawImage(PrepareScr.imgMap[i + tem], x, y, 3, false);
               g.drawImage(PrepareScr.khungMap, x, y, 3, false);
            }
         } catch (Exception var9) {
         }
      }

      g.translate(-g.getTranslateX(), -g.getTranslateY());
      g.setColor(1133755);
      g.fillRect(0, 0, CCanvas.width, 20, false);
      Font.borderFont.drawString(g, curMapIndex + 1 + ". " + MM.mapName[curMapIndex + tem], w >> 1, 2, 2);
   }

   public void setCamY() {
      int yP = curMapIndex / this.nBoardPerLine;
      int Y = yP * 57 + 60 - (CCanvas.hieght / 2 - cmdH);
      this.cmtoY = Y;
      if (this.cmtoY > this.cmyLim) {
         this.cmtoY = this.cmyLim;
      }

      if (this.cmtoY < 0) {
         this.cmtoY = 0;
      }

      if (curMapIndex == this.NUMB - 1 || curMapIndex == 0) {
         this.cmy = this.cmtoY;
      }

   }

   public void onPointerPressed(int xPress, int yPress, int index) {
      super.onPointerPressed(xPress, yPress, index);
   }

   public void onPointerReleased(int xReleased, int yReleased, int index) {
      super.onPointerReleased(xReleased, yReleased, index);
      this.trans = false;
      int b = ITEM_HEIGHT + 5;
      int aa = (this.cmtoY + yReleased - b) / 57 * this.nBoardPerLine + (xReleased - 10) / 90;
      if (!MainGame.touchDrag) {
         if (aa == curMapIndex) {
            if (this.center != null) {
               if (CCanvas.isDoubleClick) {
                  this.center.action.perform();
               }
            } else if (this.left != null && CCanvas.isDoubleClick) {
               this.left.action.perform();
            }
         }

         curMapIndex = aa;
         if (curMapIndex < 0) {
            curMapIndex = 0;
         }

         if (curMapIndex >= this.NUMB) {
            curMapIndex = this.NUMB - 1;
         }
      }

   }

   public void onPointerDragged(int xDrag, int yDrag, int index) {
      super.onPointerDragged(xDrag, yDrag, index);
      if (!this.trans) {
         this.pa = this.cmy;
         this.trans = true;
      }

      if (!CCanvas.isPc()) {
         this.speed = 3;
      }

      this.cmtoY = this.pa + (CCanvas.pyFirst[index] - yDrag) * this.speed;
      if (this.cmtoY < 0) {
         this.cmtoY = 0;
      }

      if (this.cmtoY > this.cmyLim) {
         this.cmtoY = this.cmyLim;
      }

   }

   public void update() {
   }

   public void mainLoop() {
      super.mainLoop();
      this.moveCamera();
   }
}
