package screen;

import CLib.mGraphics;
import CLib.mImage;
import coreLG.CCanvas;
import effect.Cloud;
import java.util.Vector;
import model.BoardInfo;
import model.Font;
import model.IAction;
import model.Language;
import model.RoomInfo;
import network.Command;
import network.GameService;

public class BoardListScr extends CScreen {
   public static mImage imgMode;
   public static mImage lock;
   public static String boardName;
   int nBoardPerLine;
   int defX;
   public static Vector boardList;
   public static int selected;
   public static int cmtoY;
   public static int cmy;
   public static int cmdy;
   public static int cmvy;
   public static int cmyLim;
   public byte roomID;
   int xList;
   int yList;
   static int boxX;
   static int boxY;
   static int boxW;
   static int boxH;
   int boxMaxW;
   int boxMaxH;
   int boxSpeed = 4;
   static boolean isOpenBox;
   static boolean isAllowPaintBoard;
   static Vector roomInfo;
   public static int currRoom;
   int pa = 0;
   boolean trans = false;

   static {
      imgMode = GameScr.imgMode;
      lock = GameScr.lock;
      isAllowPaintBoard = true;
   }

   public void moveCamera() {
      if (cmy != cmtoY) {
         cmvy = cmtoY - cmy << 2;
         cmdy += cmvy;
         cmy += cmdy >> 4;
         cmdy &= 15;
      }

   }

   public BoardListScr() {
      this.nBoardPerLine = CCanvas.width / 55;
      this.defX = CCanvas.width - this.nBoardPerLine * 55 >> 1;
      this.center = new Command(Language.join(), new IAction() {
         public void perform() {
            if (BoardListScr.boardList.size() != 0) {
               BoardListScr.this.doJoinBoard();
            }
         }
      });
      this.left = new Command(Language.update(), new IAction() {
         public void perform() {
            BoardListScr.this.doUpdate();
         }
      });
      this.right = new Command(Language.close(), new IAction() {
         public void perform() {
            BoardListScr.this.doExitBoardList();
            BoardListScr.isOpenBox = false;
         }
      });
      this.boxMaxW = CScreen.w - 20 > 170 ? CScreen.w - 20 : 170;
      this.boxMaxW = CScreen.w;
      this.boxMaxH = CScreen.h - 120 > 180 ? CScreen.h - 120 : 180;
      boxW = this.boxMaxW;
      this.nameCScreen = "BoardListScr screen!";
   }

   public static void setBoardName(int id, String name) {
      if (name != null && !name.equals("")) {
         boardName = Language.area() + ": " + name;
      } else {
         boardName = Language.area() + ": " + id;
      }

   }

   protected void doJoinBoard() {
      BoardInfo selectedBoard = (BoardInfo)boardList.elementAt(selected);
      setBoardName(selectedBoard.boardID, selectedBoard.name);
      if (selectedBoard.isPass) {
         CCanvas.inputDlg.show();
      } else {
         PrepareScr.currentRoom = this.roomID;
         GameService.gI().joinBoard(this.roomID, selectedBoard.boardID, "");
         CCanvas.startWaitDlgWithoutCancel(Language.pleaseWait(), 13);
      }

   }

   private void doExitBoardList() {
   }

   private void doUpdate() {
      CCanvas.startWaitDlgWithoutCancel(Language.pleaseWait(), 14);
      GameService.gI().requestBoardList(this.roomID);
   }

   public void show() {
      super.show();
      this.xList = CScreen.w;
      isOpenBox = true;
      selected = 0;
      cmtoY = selected * ITEM_HEIGHT - (CCanvas.hh - 2 * ITEM_HEIGHT);
      if (cmtoY > cmyLim) {
         cmtoY = cmyLim;
      }

      if (cmtoY < 0) {
         cmtoY = 0;
      }

      if (selected == boardList.size() - 1 || selected == 0) {
         cmy = cmtoY;
      }

   }

   public static void activeBox() {
      boxW = 0;
      boxH = 0;
      isOpenBox = true;
      isAllowPaintBoard = false;
   }

   private void updateBox() {
      if (isOpenBox) {
         if (boxW < this.boxMaxW) {
            boxW += Math.max(this.boxMaxW / this.boxSpeed, 1);
         }

         if (boxH < this.boxMaxH) {
            boxH += Math.max(this.boxMaxH / this.boxSpeed, 1);
         } else {
            isAllowPaintBoard = true;
         }

         if (boxX != CScreen.w / 2) {
            boxX += CScreen.w / 2 - boxX;
         }

         if (boxY != CScreen.h / 2) {
            boxY += CScreen.h / 2 - boxY;
         }
      } else {
         if (boxW > 0) {
            Math.max(boxW -= boxW / this.boxSpeed, 1);
         }

         if (boxH > 0) {
            Math.max(boxH -= boxH / this.boxSpeed, 1);
         }
      }

   }

   public void update() {
      Cloud.updateCloud();
      this.moveCamera();
      if (isOpenBox && this.xList > 0) {
         this.xList -= Math.max(this.xList / 2, 1);
      }

   }

   public void paint(mGraphics g) {
      paintDefaultBg(g);
      Cloud.paintCloud(g);

      for(int i = 0; i <= CCanvas.width; i += 32) {
         g.drawImage(PrepareScr.imgBack, i, CCanvas.hieght - 62, 0, false);
      }

      g.setClip(0, 0, CCanvas.width, CCanvas.hieght);
      currRoom = ((RoomInfo)roomInfo.elementAt(CCanvas.roomListScr2.selected)).id;
      Font.bigFont.drawString(g, Language.ROOM() + " " + this.roomID, 10, 3, 0);
      g.setColor(2378093);
      g.fillRect(0, 25, CCanvas.width, ITEM_HEIGHT, false);
      Font.normalYFont.drawString(g, Language.battleArea(), 10, 28, 0);
      if (isAllowPaintBoard) {
         paintRichList(this.xList, 0, g);
      }

      super.paint(g);
   }

   public static void paintRichList(int x, int y, mGraphics g) {
      int tam = CCanvas.isTouch ? 40 : ITEM_HEIGHT;
      g.translate(x, y + tam + (CCanvas.isTouch ? 14 : 25));
      g.setClip(x, y + (CCanvas.isTouch ? -10 : 1), CCanvas.width, CCanvas.hieght);
      g.translate(x, y - cmy);
      int _y = y - tam;

      for(int i = 0; i < boardList.size(); ++i) {
         _y += tam;
         if (_y >= cmy - tam && _y <= cmy + CCanvas.hieght) {
            if (i == selected) {
               g.setColor(16767817);
               g.fillRect(x, _y - (CCanvas.isTouch ? 10 : 0), CCanvas.width, tam, false);
            }

            BoardInfo boardInfo = (BoardInfo)boardList.elementAt(i);
            String name = Language.area() + " " + boardInfo.boardID;
            if (!boardInfo.name.equals("")) {
               name = boardInfo.name;
            }

            g.drawRegion(imgMode, 0, boardInfo.mode * 17, 18, 17, 0, x + 8, _y + 1, 0, false);
            Font.borderFont.drawString(g, name, x + 30, _y + 2, 0);
            Font.borderFont.drawString(g, boardInfo.nPlayer + "/" + boardInfo.maxPlayer, x + CCanvas.width - 3, _y + 2, 1);
            Font.borderFont.drawString(g, boardInfo.money + Language.xu(), x + CCanvas.width - 30, _y + 2, 1);
            BoardInfo selectedBoard = (BoardInfo)boardList.elementAt(i);
            if (selectedBoard.isPass) {
               g.drawImage(lock, x + CCanvas.width - 30 - Font.borderFont.getWidth(boardInfo.money + Language.xu()) - 5, _y + 1, 24, false);
            }
         }
      }

      g.translate(-g.getTranslateX(), -g.getTranslateY());
   }

   public void input() {
   }

   public void setBoardList(Vector boardList) {
      roomInfo = CCanvas.roomListScr2.roomList;
      BoardListScr.boardList = boardList;
      int tam = CCanvas.isTouch ? 40 : ITEM_HEIGHT;
      int aa = CCanvas.isTouch ? 5 : 0;
      cmyLim = BoardListScr.boardList.size() * tam - (CCanvas.hieght - ITEM_HEIGHT * 4 - aa);
   }
}
