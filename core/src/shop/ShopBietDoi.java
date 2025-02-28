package shop;

import CLib.mGraphics;
import CLib.mSystem;
import coreLG.CCanvas;
import effect.Cloud;
import java.util.Vector;
import model.ClanItem;
import model.Font;
import model.IAction;
import model.Language;
import network.Command;
import network.GameService;
import screen.CScreen;
import screen.PrepareScr;

public class ShopBietDoi extends CScreen {
   private long currentTimeClick;
   public Vector items;
   public int cmtoY;
   public int cmy;
   public int cmdy;
   public int cmvy;
   public int cmyLim;
   int selected;
   int disY = 50;
   boolean isPaintItemLish;
   int pa = 0;
   boolean trans = false;

   public ClanItem getCurrItem() {
      return (ClanItem)this.items.elementAt(this.selected);
   }

   public ClanItem getClanItem(byte id) {
      for(int i = 0; i < this.items.size(); ++i) {
         ClanItem item = (ClanItem)this.items.elementAt(i);
         if (item.id == id) {
            return item;
         }
      }

      return null;
   }

   public void initCommand() {
      final Command xu = new Command(Language.muaXu(), new IAction() {
         public void perform() {
            CCanvas.startYesNoDlg(Language.areYouSure(), new IAction() {
               public void perform() {
                  if (ShopBietDoi.this.getCurrItem() != null) {
                     GameService.gI().getShopBietDoi((byte)1, (byte)0, ShopBietDoi.this.getCurrItem().id);
                  }

               }
            });
         }
      });
      final Command luong = new Command(Language.muaLuong(), new IAction() {
         public void perform() {
            CCanvas.startYesNoDlg(Language.areYouSure(), new IAction() {
               public void perform() {
                  if (ShopBietDoi.this.getCurrItem() != null) {
                     GameService.gI().getShopBietDoi((byte)1, (byte)1, ShopBietDoi.this.getCurrItem().id);
                  }

               }
            });
         }
      });
      if (this.getCurrItem() != null && this.getCurrItem().xu != -1 && this.getCurrItem().luong != -1) {
         this.center = new Command("Menu", new IAction() {
            public void perform() {
               Vector menu = new Vector();
               menu.addElement(xu);
               menu.addElement(luong);
               CCanvas.menu.startAt(menu, 2);
            }
         });
      } else {
         if (this.getCurrItem().xu != -1) {
            this.center = xu;
         }

         if (this.getCurrItem().luong != -1) {
            this.center = luong;
         }
      }

      this.right = new Command(Language.back(), new IAction() {
         public void perform() {
            CCanvas.menuScr.show();
         }
      });
   }

   public void setItems(Vector items) {
      this.items = items;
      this.cmyLim = items.size() * 50 - (CCanvas.hieght - (ITEM_HEIGHT + cmdH)) + 10;
      if (this.cmyLim < 0) {
         this.cmyLim = 0;
      }

   }

   public void show() {
      this.nameCScreen = " ShopBietDoi screen!";
      super.show();
      this.initCommand();
      CCanvas.endDlg();
   }

   public void paint(mGraphics g) {
      g.setClip(0, 0, CCanvas.width, CCanvas.hieght);
      paintDefaultBg(g);
      Cloud.paintCloud(g);

      for(int i = 0; i <= CCanvas.width; i += 32) {
         g.drawImage(PrepareScr.imgBack, i, CCanvas.hieght - 62, 0, true);
      }

      Font.bigFont.drawString(g, Language.ITEM_DOI(), 10, 3, 0);
      this.paintItems(g);
      super.paint(g);
   }

   public void moveCamera() {
      if (this.cmy != this.cmtoY) {
         this.cmvy = this.cmtoY - this.cmy >> 2;
         this.cmy += this.cmvy;
      }

   }

   public void paintItems(mGraphics g) {
      g.translate(0, 30);
      g.setClip(0, 1, CCanvas.width, CCanvas.hieght - cmdH);
      g.translate(0, -this.cmy);
      int y = 0;

      for(int i = 0; i < this.items.size(); ++i) {
         if (i == this.selected) {
            g.setColor(16765440);
            g.fillRect(0, y, CCanvas.width, 49, true);
         }

         if (i * 50 > -g.getTranslateY() && i * 50 < -g.getTranslateY() + CScreen.h) {
            ClanItem item = (ClanItem)this.items.elementAt(i);
            String name = item.name;
            Font.borderFont.drawString(g, "Level: " + item.levelRequire + ": " + name, 5, y, 0);
            String gia = Language.price() + ": ";
            if (item.xu != -1 && item.luong != -1) {
               gia = gia + item.xu + Language.xu() + " - " + item.luong + " " + Language.luong();
            } else {
               if (item.xu != -1) {
                  gia = gia + item.xu + Language.xu();
               }

               if (item.luong != -1) {
                  gia = gia + item.luong + Language.luong();
               }
            }

            Font.normalFont.drawString(g, Language.price() + ": " + gia, 5, y + 18, 0);
            Font.normalFont.drawString(g, Language.time() + ": " + item.expDate + " " + Language.gio(), 5, y + 34, 0);
         }

         y += 50;
      }

   }

   public void update() {
      Cloud.updateCloud();
   }

   public void mainLoop() {
      super.mainLoop();
      this.moveCamera();
   }

   public void onPointerPressed(int xPressed, int yPressed, int index) {
      super.onPointerPressed(xPressed, yPressed, index);
   }

   public void onPointerReleased(int xRealsed, int yRealsed, int index) {
      super.onPointerReleased(xRealsed, yRealsed, index);
      this.trans = false;
      if (CCanvas.isPointer(0, 0, w, CCanvas.hieght - cmdH, index)) {
         int b = ITEM_HEIGHT;
         int aa = (this.cmtoY + yRealsed - b) / this.disY;
         if (aa == this.selected && mSystem.currentTimeMillis() - this.currentTimeClick > 100L) {
            if (this.center != null) {
               if (CCanvas.isDoubleClick) {
                  this.center.action.perform();
               }
            } else if (this.left != null && CCanvas.isDoubleClick) {
               this.left.action.perform();
            }
         }

         this.selected = aa;
         this.initCommand();
         if (this.selected < 0) {
            this.selected = 0;
         }

         if (this.selected >= this.items.size()) {
            this.selected = this.items.size() - 1;
         }
      }

      this.cmtoY = this.cmy;
      this.currentTimeClick = mSystem.currentTimeMillis();
   }

   public void onPointerDragged(int xDrag, int yDrag, int index) {
      super.onPointerDragged(xDrag, yDrag, index);
      this.currentTimeClick = mSystem.currentTimeMillis();
      if (!this.trans) {
         this.pa = this.cmy;
         this.trans = true;
      }

      this.cmtoY = this.pa + (CCanvas.pyFirst[index] - yDrag);
      if (this.cmtoY > this.cmyLim) {
         this.cmtoY = this.cmyLim;
      }

      if (this.cmtoY < 0) {
         this.cmtoY = 0;
      }

   }
}
