package screen;

import CLib.mGraphics;
import CLib.mImage;
import CLib.mSystem;
import coreLG.CCanvas;
import coreLG.TerrainMidlet;
import effect.Cloud;
import java.util.Vector;
import javax.microedition.midlet.MIDlet;
import model.AvatarInfo;
import model.CRes;
import model.Font;
import model.IAction;
import model.Language;
import model.MoneyInfo;
import network.Command;
import network.GameService;

public class MoneyScr extends CScreen {
   public static String url_Nap;
   Vector avs;
   public int selected;
   public int cmtoY;
   public int cmy;
   public int cmdy;
   public int cmvy;
   public int cmyLim;
   public int xL;
   public static mImage imgCoin;
   int pa = 0;
   boolean trans = false;

   static {
      try {
         imgCoin = mImage.createImage("/coin.png");
      } catch (Exception var1) {
         var1.printStackTrace();
      }

   }

   public boolean isHaveMoneyList() {
      if (this.avs == null) {
         return false;
      } else {
         return this.avs.size() > 0;
      }
   }

   public int priceFromID(int id) {
      for(int i = 0; i < this.avs.size(); ++i) {
         AvatarInfo av = (AvatarInfo)this.avs.elementAt(i);
         if (av.ID == id) {
            return av.price;
         }
      }

      return 0;
   }

   public void startAnimLeft() {
      this.xL = -CCanvas.width;
   }

   public MoneyScr() {
      this.nameCScreen = " MoneyScr screen!";
      IAction buyAction = new IAction() {
         public void perform() {
            MoneyScr.this.doBuy();
         }
      };
      this.center = new Command(Language.select(), buyAction);
      this.right = new Command(Language.close(), new IAction() {
         public void perform() {
            CCanvas.menuScr.show();
         }
      });
   }

   private void doLoadCard(final String typeStr, String subTitle) {
      CRes.out(" ====>doLoadCard typeStr " + typeStr.trim());
      final String[] text = new String[2];
      final String[] name = new String[]{Language.seriNumber() + ":", Language.pinNumber() + ":"};
      CCanvas.inputDlg.setInfo(name[0], new IAction() {
         public void perform() {
            if (CCanvas.inputDlg.tfInput.getText().equals("")) {
               CCanvas.startOKDlg(Language.inputSeri(), new IAction() {
                  public void perform() {
                     CCanvas.inputDlg.show();
                  }
               });
            } else {
               text[0] = CCanvas.inputDlg.tfInput.getText();
               if (typeStr.trim().equals("giftcode")) {
                  GameService.gI().doLoadCard(typeStr.trim(), text[0], "");
                  CCanvas.startOKDlg(Language.pleaseWait());
               } else {
                  CCanvas.inputDlg.setInfo(name[1], new IAction() {
                     public void perform() {
                        if (CCanvas.inputDlg.tfInput.getText().equals("")) {
                           CCanvas.startOKDlg(Language.inputPin(), new IAction() {
                              public void perform() {
                                 CCanvas.inputDlg.show();
                              }
                           });
                        } else {
                           text[1] = CCanvas.inputDlg.tfInput.getText();
                           GameService.gI().doLoadCard(typeStr, text[0], text[1]);
                           CCanvas.startOKDlg(Language.pleaseWait());
                        }
                     }
                  }, new IAction() {
                     public void perform() {
                        CCanvas.endDlg();
                     }
                  }, 1);
                  CCanvas.inputDlg.show();
               }
            }
         }
      }, new IAction() {
         public void perform() {
            CCanvas.endDlg();
         }
      }, 0);
      CCanvas.inputDlg.show();
   }

   protected void doBuy() {
      CRes.err(" ===========================> do buy a product!");
      if (this.avs != null) {
         MoneyInfo mi = (MoneyInfo)this.avs.elementAt(this.selected);
         if (mi.id.equals("napWeb")) {
            if (!CRes.isNullOrEmpty(url_Nap)) {
               mSystem.openUrl(url_Nap);
            }

            return;
         }

         if (mi.smsContent.startsWith("http")) {
            try {
               MIDlet.platformRequest(mi.smsContent + "?game=4&username=" + TerrainMidlet.myInfo.name);
               CCanvas.startOKDlg(Language.autoOpen());
            } catch (Exception var5) {
            }
         }

         String napthe = "napthe:";
         String link;
         if (mi.smsContent.indexOf(napthe) != -1) {
            int index = napthe.length();
            link = mi.smsContent.substring(index);
            CRes.out("=====Str168 = " + link);
            this.doLoadCard(link, mi.info);
            return;
         }

         CCanvas.startWaitDlg(Language.sendMessMoney());
         TerrainMidlet.sendSMS(mi.smsContent + TerrainMidlet.myInfo.name, "sms://" + mi.smsTo, new IAction() {
            public void perform() {
               CCanvas.startOKDlg(Language.sendMoneySucc());
            }
         }, new IAction() {
            public void perform() {
               CCanvas.startOKDlg(Language.sendSMSFail());
            }
         });
         final String str;
         if (mi.smsContent.indexOf("http://") != -1) {
            str = Font.replace(mi.smsContent, "@username", TerrainMidlet.myInfo.name);
            CCanvas.startOKDlg(Language.wantExit(), new IAction() {
               public void perform() {
                  try {
                     mSystem.connectHTTP(str);
                     TerrainMidlet.instance.notifyDestroyed();
                  } catch (Exception var2) {
                     var2.printStackTrace();
                  }

               }
            });
            return;
         }

         if (mi.smsContent.indexOf("napthe:") != -1) {
            str = mi.smsContent.substring(0, mi.smsContent.indexOf("napthe:") + "napthe:".length());
            link = Font.replace(mi.smsContent, str, "");
            CRes.out("=====Str200 = " + str);
            CRes.out("=====link200 = " + link);
            this.doLoadCard(link, mi.info);
            return;
         }

         CCanvas.startOKDlg(Language.sendMessMoney());
         GameService.gI().requestChargeMoneyInfo2((byte)1, this.getSelectMoney().id);
      }

   }

   public MoneyInfo getSelectMoney() {
      return this.avs == null ? null : (MoneyInfo)this.avs.elementAt(this.selected);
   }

   public void showInputCard() {
   }

   public void startAnimRight() {
      this.xL = CCanvas.width << 1;
   }

   public void moveCamera() {
      if (this.cmy != this.cmtoY) {
         this.cmvy = this.cmtoY - this.cmy << 2;
         this.cmdy += this.cmvy;
         this.cmy += this.cmdy >> 4;
         this.cmdy &= 15;
      }

   }

   public void paint(mGraphics g) {
      paintDefaultBg(g);
      Cloud.paintCloud(g);

      for(int i = 0; i <= CCanvas.width; i += 32) {
         g.drawImage(PrepareScr.imgBack, i, CCanvas.hieght - 62, 0, false);
      }

      g.setClip(0, 0, CCanvas.width, CCanvas.hieght);
      g.translate(this.xL, 0);
      Font.bigFont.drawString(g, Language.charge(), 10, 3, 0);
      g.setColor(1407674);
      g.fillRect(0, 25, CCanvas.width, ITEM_HEIGHT, false);
      Font.normalYFont.drawString(g, Language.payMethod(), 10, 28, 0);
      this.paintRichList(g);
      super.paint(g);
   }

   private void paintRichList(mGraphics g) {
      if (this.avs != null) {
         if (this.avs.size() > 0) {
            g.translate(0, ITEM_HEIGHT + 25);
            g.setClip(0, 1, CCanvas.width, CCanvas.hieght - 25 - 28 - ITEM_HEIGHT);
            g.translate(0, -this.cmy);
            int y = 0;

            for(int i = 0; i < this.avs.size(); ++i) {
               if (i == this.selected) {
                  g.setColor(16765440);
                  g.fillRect(0, y, CCanvas.width, 20, true);
               }

               MoneyInfo avi = (MoneyInfo)this.avs.elementAt(i);
               g.drawImage(imgCoin, 10, y + 2, 0, true);
               Font.borderFont.drawString(g, avi.info, 40, y + 2, 0, true);
               if (!CCanvas.isTouch) {
                  y += 20;
               } else {
                  y += 30;
               }
            }

         }
      }
   }

   public void onPointerPressed(int xPress, int yPress, int index) {
      super.onPointerPressed(xPress, yPress, index);
   }

   public void onPointerDragged(int xDrag, int yDrag, int index) {
      super.onPointerDragged(xDrag, yDrag, index);
      if (this.avs != null) {
         if (!this.trans) {
            this.pa = this.cmy;
            this.trans = true;
         }

         this.cmtoY = this.pa + (CCanvas.pyFirst[index] - yDrag);
         if (this.cmtoY < 0) {
            this.cmtoY = 0;
         }

         if (this.cmtoY > this.cmyLim) {
            this.cmtoY = this.cmyLim;
         }

         if (this.selected >= this.avs.size() - 1 || this.selected == 0) {
            this.cmy = this.cmtoY;
         }

      }
   }

   public void onPointerHold(int x, int y2, int index) {
      super.onPointerHold(x, y2, index);
   }

   public void onPointerReleased(int xReleased, int yReleased, int index) {
      super.onPointerReleased(xReleased, yReleased, index);
      if (this.avs != null) {
         if (CCanvas.isPointerDown[index]) {
            if (!this.trans) {
               this.pa = this.cmy;
               this.trans = true;
            }

            this.cmtoY = this.pa + (CCanvas.pyFirst[index] - yReleased);
            if (this.cmtoY < 0) {
               this.cmtoY = 0;
            }

            if (this.cmtoY > this.cmyLim) {
               this.cmtoY = this.cmyLim;
            }

            if (this.selected >= this.avs.size() - 1 || this.selected == 0) {
               this.cmy = this.cmtoY;
            }
         }

         this.trans = false;
         int aa = (this.cmtoY + yReleased - ITEM_HEIGHT - 25) / 30;
         if (aa == this.selected) {
            if (this.left != null && CCanvas.isDoubleClick) {
               this.left.action.perform();
            }

            if (CCanvas.isDoubleClick) {
               this.center.action.perform();
            }
         }

         if (aa >= 0 && aa < this.avs.size()) {
            this.selected = aa;
         }

      }
   }

   public void update() {
      if (this.xL != 0) {
         this.xL += -this.xL >> 1;
      }

      if (this.xL == -1) {
         this.xL = 0;
      }

      this.moveCamera();
      Cloud.updateCloud();
   }

   public void setMoneyList(Vector avatarList) {
      this.avs = avatarList;
   }

   public void setAvatarList(Vector avatarList) {
      this.avs = avatarList;
      this.selected = 0;
      this.cmy = this.cmtoY = 0;
      this.cmyLim = avatarList.size() * 20 - (CCanvas.hh - 40);
      if (this.cmyLim < 0) {
         this.cmyLim = 0;
      }

   }
}
