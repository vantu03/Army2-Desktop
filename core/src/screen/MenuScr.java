package screen;

import CLib.mGraphics;
import CLib.mSystem;
import Equipment.PlayerEquip;
import com.teamobi.mobiarmy2.GameMidlet;
import com.teamobi.mobiarmy2.MainGame;
import coreLG.CCanvas;
import coreLG.TerrainMidlet;
import effect.Camera;
import effect.Cloud;
import java.util.Vector;
import map.Background;
import model.CRes;
import model.Font;
import model.IAction;
import model.Language;
import model.PlayerInfo;
import model.Position;
import network.Command;
import network.GameService;
import player.CPlayer;
import player.PM;
import shop.ShopEquipment;
import shop.ShopItem;

public class MenuScr extends CScreen {
   public int select = 0;
   int nselect = 12;
   public static byte MENU_CHOINGAY;
   public static byte MENU_CUAHANG;
   public static byte MENU_SUKIEN;
   public static byte MENU_NAPTIEN;
   public static byte MENU_BANGHOI;
   public static byte MENU_TINTUC;
   public static byte MENU_CAUHINH;
   public static byte MENU_QUANGCAO;
   public static byte MENU_NGAUNHIEN = 8;
   public static byte MENU_CHONBAN = 9;
   public static final byte MENU_CHOINHANH = 6;
   public static final byte MENU_XEPHANG = 7;
   public static final byte MENU_TUYCHONKHAC = 5;
   public static final byte MENU_1VS1 = 0;
   public static final byte MENU_2VS2 = 1;
   public static final byte MENU_3VS3 = 2;
   public static final byte MENU_4VS4 = 3;
   public static final byte MENU_DENKHUVUC = 0;
   public static final byte MENU_CHONNHANVAT = 1;
   public static final byte MENU_LEVEL = 2;
   public static final byte MENU_TRANGBI = 3;
   public static final byte MENU_LUYENTAP = 4;
   public static final byte MENU_QUAYSO = 5;
   public static final byte MENU_BANXEPHANG = 0;
   public static final byte MENU_BANBE = 1;
   public static final byte MENU_TOPDOI = 2;
   public static final byte MENU_THANHTICH = 3;
   public static final byte MENU_ARCHIVEMENT = 4;
   public static final byte MENU_TINNHAN = 5;
   public static final byte MENU_DOIMATKHAU = 6;
   public static final byte MENU_CUAHANG_ITEM = 0;
   public static final byte MENU_CUAHANG_TRANGBI = 1;
   public static final byte MENU_CUAHANG_LINHTINH = 2;
   public static final byte MENU_CUAHANG_BIEDOI = 3;
   public static final byte MENU_CAUHINH1 = 0;
   public static final byte MENU_GAMEKHAC = 1;
   public static final byte MENU_ABOUT = 2;
   public static final byte TOPCAOTHU = 0;
   public static final byte TOP_DAIGIAXU = 1;
   public static final byte TOP_DAIGIALUONG = 2;
   public static final byte TOP_CAOTHUTUAN = 3;
   public static final byte TOP_XUTUAN = 4;
   public static String suKienStr;
   public static String linkWapStr;
   public static String linkTeam;
   int yB;
   int hB;
   int hBMax;
   public static boolean isTraining = false;
   public static String[][] subMenuString = new String[10][];
   public static int curMenuLevel;
   public static int curMenuSelect;
   private static int curSubMenuSelect;
   public static boolean viewInfo;
   public static String[] menuString;
   public static String gameContent;
   public static String gameLink;
   String[] str = new String[]{Language.playnow(), Language.toArea(), Language.selectCharactor(), Language.training(), Language.shop(), Language.topScore(), Language.FRIEND(), Language.achievement(), Language.MESS(), Language.charge(), Language.option(), Language.otherGame()};
   public static Vector menuCroll;
   public static int[] menuX;
   Command cmdExit;
   Command cmdExitGame;
   int dis;
   public int yMenu;
   int nItemShow;
   Position transText1 = new Position(0, 1);
   boolean scrollUp = false;
   boolean scrollDown = false;
   int dyUp;
   int dyDown = 20;
   boolean levelUp;
   int time;
   public static int dem2;
   public boolean hide;
   public static boolean IS_TEST_POS;
   public int cmtoY;
   public int cmy;
   public int cmdy;
   public int cmvy;
   public int cmyLim;
   public int xL;
   int hMenu;
   int W;
   int pa = 0;
   boolean trans = false;
   int speed = 1;

   static {
      subMenuString[7] = new String[]{Language.topCaothu(), Language.topDaiGiaXu(), Language.topDaigiaLuong(), Language.topCaothuTuan(), Language.topXuTuan()};
      subMenuString[5] = new String[]{Language.option(), Language.otherGame(), "ABOUT"};
      menuCroll = new Vector();
      dem2 = 0;
      IS_TEST_POS = false;
   }

   public static void getIdMenu(int type) {
      CRes.out(" getIdMenu type = " + type);
      curMenuLevel = 0;
      curMenuSelect = 0;
      curSubMenuSelect = 0;
      if (type == 0) {
         MENU_CHOINGAY = 0;
         MENU_CUAHANG = 1;
         MENU_SUKIEN = 2;
         MENU_NAPTIEN = 3;
         MENU_BANGHOI = 4;
         MENU_TINTUC = 5;
         MENU_CAUHINH = 6;
         menuString = new String[]{Language.startGame(), Language.CUAHANG(), Language.event(), Language.charge(), Language.BIETDOI(), Language.information(), Language.option()};
      }

      if (type == 1) {
         MENU_CHOINGAY = 0;
         MENU_CUAHANG = 1;
         MENU_QUANGCAO = 2;
         MENU_NAPTIEN = 3;
         MENU_BANGHOI = 4;
         MENU_TINTUC = 5;
         MENU_CAUHINH = 6;
         menuString = new String[]{Language.startGame(), Language.CUAHANG(), Language.charge(), Language.BIETDOI(), Language.information(), Language.option()};
      }

      subMenuString[MENU_CHOINGAY] = new String[]{Language.toArea(), Language.selectCharactor(), Language.NANGCAP(), Language.INVENTORY(), Language.training(), Language.QUAYSO()};
      subMenuString[MENU_TINTUC] = new String[]{Language.topScore(), Language.FRIEND(), Language.TOPCLAN(), Language.achievement(), Language.banthan(), Language.MESS(), Language.changePass()};
      subMenuString[6] = new String[]{Language.emptyRoom(), "1 VS 1", "2 VS 2", "3 VS 3", "4 VS 4", Language.bossbattle()};
      subMenuString[MENU_CUAHANG] = new String[]{Language.shop(), Language.shop_eq(), Language.DODACBIET(), Language.ITEM_DOI()};
      subMenuString[MENU_NGAUNHIEN] = new String[]{"1 VS 1", "2 VS 2", "3 VS 3", "4 VS 4", "ĐẤU TRÙM"};
   }

   public MenuScr() {
      this.nameCScreen = " MenuScr screen!";
      w = CCanvas.width;
      h = CCanvas.hieght;
      this.activeCroll(curMenuLevel, curMenuSelect);
      this.createIAction();
      TerrainMidlet.myInfo.getMyEquip(10);
      if (TerrainMidlet.isVip[TerrainMidlet.myInfo.gun]) {
         TerrainMidlet.myInfo.getVipEquip();
      }

      this.menuScroll = true;
      if (CCanvas.isTouch) {
         this.dis = 32;
      } else {
         this.dis = 20;
      }

      this.dis = 32;
   }

   public void goToGame() {
      CCanvas.quangCaoScr.show();
   }

   public Position transTextLimit(Position pos, int limit) {
      pos.x += pos.y;
      if (pos.y == -1 && Math.abs(pos.x) > limit) {
         pos.y *= -1;
      }

      if (pos.y == 1 && pos.x > 5) {
         pos.y *= -1;
      }

      return pos;
   }

   public void getRectHeight() {
      this.yMenu = !CCanvas.isTouch ? (h >> 1) - 10 : 80;
      this.yB = this.yMenu - 25;
      this.nItemShow = (CCanvas.hh - 20) / this.dis;
      if (CCanvas.isTouch) {
         this.nItemShow = (CCanvas.hieght - this.yMenu - cmdH - 10) / this.dis;
      }

      if (this.nItemShow >= menuX.length) {
         this.nItemShow = menuX.length;
      }

      int deltaY = this.nItemShow * this.dis + 35;
      this.hBMax = deltaY;
      if (this.hBMax > (!CCanvas.isTouch ? 154 : CCanvas.hieght - cmdH + 15 - this.yMenu)) {
         this.hBMax = !CCanvas.isTouch ? 154 : CCanvas.hieght - cmdH + 15 - this.yMenu;
      }

   }

   public void show() {
      LoginScr.isLoadData = true;
      CRes.err("===================> show MenuScr");
      TerrainMidlet.myInfo.getMyEquip(15);
      TerrainMidlet.myInfo.getVipEquip();
      this.hide = false;
      this.getRectHeight();
      this.startScrollDown();
      super.show();
   }

   public void activeCroll(int level, int select) {
      if (level == 0) {
         menuX = new int[menuString.length];
      } else if (level == 1) {
         menuX = new int[subMenuString[select].length];
      } else if (level == 2) {
         menuX = new int[subMenuString[select].length];
      }

      curMenuLevel = level;

      int aa;
      for(aa = 0; aa < menuX.length; ++aa) {
         menuX[aa] = CCanvas.width >> 1;
      }

      if (level == 0) {
         this.select = select;
         this.activeCMTOY(select, menuX.length);
         this.right = this.cmdExitGame;
      } else if (level == 1) {
         curMenuSelect = select;
         this.select = 0;
         this.activeCMTOY(0, menuX.length + 2);
         this.right = this.cmdExit;
      }

      aa = CCanvas.isTouch ? 5 : 0;
      this.cmyLim = this.yMenu + menuX.length * this.dis - (CCanvas.hieght - cmdH - 30 - aa);
   }

   public void startScrollDown() {
      this.hide = false;
      this.scrollDown = true;
      this.hB = 50;
      int aa = CCanvas.isTouch ? 5 : 0;
      this.cmyLim = this.yMenu + menuX.length * this.dis - (CCanvas.hieght - cmdH - 30 - aa);
   }

   public void startScrollUp(boolean levelUp) {
      this.hide = false;
      this.scrollUp = true;
      this.levelUp = levelUp;
   }

   public void scrollUp() {
      if (this.scrollUp) {
         this.hB -= this.hBMax / 4;
         this.hMenu = this.hB - 30;
         if (this.hMenu < 40) {
            this.hMenu = 0;
         }

         if (this.hB < 38) {
            this.hB = 38;
            this.hMenu = 0;
            if (this.levelUp) {
               this.getMenuLevel();
            } else {
               int level = curMenuLevel - 1;
               if (level < 0) {
                  level = 0;
               }

               this.activeCroll(level, curMenuSelect);
               this.getRectHeight();
            }

            this.scrollUp = false;
            if (!this.hide) {
               this.scrollDown = true;
            }
         }
      }

   }

   public void scrollDown() {
      if (this.scrollDown) {
         if (this.nItemShow > 6) {
            this.nItemShow = 6;
         }

         int hMenuMax = this.nItemShow * this.dis;
         this.hB += this.hBMax / 4;
         if (this.hBMax <= 0) {
            this.hBMax = !CCanvas.isTouch ? 154 : CCanvas.hieght - cmdH + 15 - this.yMenu;
         }

         this.hMenu = this.hB - 30;
         if (this.hMenu > hMenuMax) {
            this.hMenu = hMenuMax;
         }

         if (this.hB > this.hBMax) {
            this.hB = this.hBMax;
            this.hMenu = hMenuMax;
            this.scrollDown = false;
         }

         CRes.out(this.getClass().getName() + " debug: hmenu/hMenuMax/hBMax " + this.hMenu + "/" + hMenuMax + "/" + this.hBMax);
      }

   }

   public void createIAction() {
      IAction selectAction = new IAction() {
         public void perform() {
            MenuScr.this.doFire();
         }
      };
      this.cmdExit = new Command(Language.back(), new IAction() {
         public void perform() {
            MenuScr.this.hide = false;
            MenuScr.this.startScrollUp(false);
         }
      });
      this.cmdExitGame = new Command(Language.exit(), new IAction() {
         public void perform() {
            MenuScr.this.doExit();
         }
      });
      this.left = new Command(Language.select(), selectAction);
      this.center = null;
      this.right = this.cmdExitGame;
   }

   protected void doExit() {
      IAction actionSkip = new IAction() {
         public void perform() {
            GameService.gI().signOut();
         }
      };
      CCanvas.msgdlg.setInfo(Language.wantExit(), new Command(Language.yes(), actionSkip), new Command("", actionSkip), new Command(Language.no(), new IAction() {
         public void perform() {
            CCanvas.endDlg();
         }
      }));
      CCanvas.msgdlg.show();
   }

   public void update() {
      GameScr.sm.update();
      Cloud.updateCloud();
      Cloud.balloonUpdate();
      super.update();
   }

   public void mainLoop() {
      super.mainLoop();
      Camera.x += 3;
      this.moveCamera();
      this.scrollDown();
      this.scrollUp();
   }

   public void doClan() {
      PlayerInfo m = TerrainMidlet.myInfo;
      if (m.clanID > 0) {
         GameService.gI().clanInfo(m.clanID);
         CCanvas.startWaitDlgWithoutCancel(Language.pleaseWait(), 2000L + mSystem.currentTimeMillis(), new IAction() {
            public void perform() {
               CCanvas.startOKDlg(Language.noTimeRespond());
            }
         });
         ClanScreen.isFromMenu = true;
      } else {
         CCanvas.startYesNoDlg(Language.chuacodoi(), new IAction() {
            public void perform() {
               MenuScr.this.doGoToTeam(MenuScr.linkTeam);
            }
         });
      }

   }

   public void doLevelUp() {
      if (CCanvas.levelScreen == null) {
         CCanvas.levelScreen = new LevelScreen();
      }

      CCanvas.levelScreen.show(this);
   }

   public void doEquip() {
      CCanvas.equipScreen.init();
      CCanvas.equipScreen.show(this);
   }

   public void doTopClan() {
      ClanScreen.isFromMenu = false;
      CCanvas.startOKDlg(Language.pleaseWait());
      GameService.gI().topClan((byte)0);
   }

   private void doGoToTeam(String link) {
      mSystem.openUrl(link);
   }

   public void getMenuLevel() {
      if (curMenuLevel == 0) {
         if (this.select == MENU_CHOINGAY) {
            this.hide = false;
            this.activeCroll(1, this.select);
         } else if (this.select == MENU_CUAHANG) {
            this.hide = false;
            this.activeCroll(1, this.select);
         } else if (this.select == MENU_NAPTIEN) {
            this.hide = true;
            GameService.gI().requestChargeMoneyInfo2((byte)0, "");
            this.doChargeMoney();
         } else if (this.select == MENU_QUANGCAO) {
            this.goToGame();
         } else if (this.select == MENU_SUKIEN) {
            this.hide = true;
            CCanvas.startYesNoDlg(Language.vaoxemtin(), new IAction() {
               public void perform() {
                  MenuScr.this.doGoToWap(MenuScr.linkWapStr);
               }
            }, new IAction() {
               public void perform() {
                  if (MenuScr.this.cmdExit != null) {
                     MenuScr.this.cmdExit.action.perform();
                  }

                  CCanvas.endDlg();
               }
            });
         } else if (this.select == MENU_BANGHOI) {
            this.hide = true;
            this.doClan();
         } else if (this.select == MENU_TINTUC) {
            this.activeCroll(1, this.select);
         } else if (this.select == MENU_CAUHINH) {
            this.hide = true;
            if (CCanvas.configScr == null) {
               CCanvas.configScr = new ConfigScr();
            }

            CCanvas.configScr.show();
         }
      } else if (curMenuLevel == 1) {
         if (curMenuSelect == MENU_CHOINGAY) {
            this.hide = true;
            switch(this.select) {
            case 0:
               PrepareScr.isBossRoom = false;
               this.doRequestRoomList();
               this.hide = false;
               this.activeCroll(2, this.select);
               break;
            case 1:
               this.doChangePlayer();
               break;
            case 2:
               this.hide = true;
               viewInfo = true;
               GameService.gI().charactorInfo();
               CCanvas.startOKDlg(Language.pleaseWait());
               this.doLevelUp();
               break;
            case 3:
               this.hide = true;
               this.doEquip();
               break;
            case 4:
               GameScr.trainingMode = true;
               isTraining = true;
               GameService.gI().trainingMap();
               CCanvas.startOKDlg(Language.pleaseWait());
               break;
            case 5:
               CCanvas.luckyGame.show(this);
            }
         } else if (curMenuSelect == MENU_TINTUC) {
            this.hide = true;
            switch(this.select) {
            case 0:
               this.doShowLeaderBoard((byte)1);
               this.hide = false;
               this.activeCroll(2, 7);
               break;
            case 1:
               this.doShowFriend();
               break;
            case 2:
               this.doTopClan();
               break;
            case 3:
               this.doShowInfo();
               if (CCanvas.missionScreen == null) {
                  CCanvas.missionScreen = new MissionScreen();
               }

               GameService.gI().mission((byte)0, (byte)-1);
               CCanvas.startOKDlg(Language.pleaseWait());
               break;
            case 4:
               GameService.gI().requestInfoOf(TerrainMidlet.myInfo.IDDB);
               CCanvas.startOKDlg(Language.pleaseWait());
               break;
            case 5:
               this.doViewMessage();
               break;
            case 6:
               this.doChangePass();
            }

            switch(this.select) {
            case 1:
               this.doGoToWap("");
            case 2:
            }
         } else if (curMenuLevel == MENU_CUAHANG) {
            this.hide = true;
            switch(this.select) {
            case 0:
               this.hide = true;
               this.doShowShopItem();
               break;
            case 1:
               this.hide = true;
               GameService.gI().getShopEquip();
               CCanvas.startOKDlg(Language.pleaseWait());
               break;
            case 2:
               this.hide = true;
               GameService.gI().getShopLinhtinh((byte)0, (byte)-1, (byte)-1, (byte)-1);
               CCanvas.startOKDlg(Language.pleaseWait());
               break;
            case 3:
               this.hide = true;
               GameService.gI().getShopBietDoi((byte)0, (byte)-1, (byte)-1);
               CCanvas.startOKDlg(Language.pleaseWait());
            }
         }
      } else if (curMenuLevel == 2) {
         this.hide = true;
         if (curMenuSelect == MENU_CHOINGAY) {
            if (this.select == 0) {
               GameService.gI().requestEmptyRoom((byte)0, (byte)-1, (String)null);
            } else {
               this.doPlayNow((byte)this.select);
            }
         } else if (curMenuSelect == MENU_TINTUC) {
            GameService.gI().bangxephang((byte)this.select, 0);
         }
      } else if (curMenuLevel == 3 && curMenuSelect == 0) {
         this.hide = true;
         this.doPlayNow((byte)(this.select - 1));
      }

      this.getRectHeight();
   }

   protected void doFire() {
      this.startScrollUp(true);
   }

   private void doShowLeaderBoard(byte type) {
      CCanvas.startWaitDlg(Language.pleaseWait());
      GameService.gI().requestStrongest(0);
   }

   private void doChangePlayer() {
      if (CCanvas.changePScr != null) {
         CCanvas.changePScr.show(this);
      } else {
         CCanvas.changePScr = new ChangePlayerCSr();
         CCanvas.changePScr.show(this);
      }

   }

   private void doGoToWap(String link) {
      mSystem.openUrl(link);
   }

   public void doTraining(byte MapID, byte timeInterval, short[] playerX, short[] playerY, short[] maxHP, short[] equipID) {
       System.out.println("choi game");
      CCanvas.prepareScr.playerInfos.removeAllElements();
      PlayerInfo me;
      int i;
      if (!IS_TEST_POS) {
         me = new PlayerInfo();
         me.name = "";
         me.gun = TerrainMidlet.myInfo.gun;
         me.level2 = 1;
         me.getQuanHam();

         for(i = 0; i < 5; ++i) {
            me.equipID[me.gun][i] = equipID[i];
         }

         me.getMyEquip(11);
         CCanvas.prepareScr.playerInfos.addElement(me);
         me = new PlayerInfo();
         me.name = "Enemy";
         me.gun = TerrainMidlet.myInfo.gun;
         me.level2 = 1;
         me.getQuanHam();

         for(i = 0; i < 5; ++i) {
            me.equipID[me.gun][i] = equipID[i];
         }

         me.getMyEquip(12);
         CCanvas.prepareScr.playerInfos.addElement(me);
      }

      if (IS_TEST_POS) {
         for(i = 0; i < 8; ++i) {
            me = new PlayerInfo();
            me.name = "p" + i;
            CCanvas.prepareScr.playerInfos.addElement(me);
         }

         CCanvas.gameScr.initGame(MapID, timeInterval, playerX, playerY, maxHP, 0);
      } else {
         CCanvas.gameScr.initGame(MapID, timeInterval, playerX, playerY, maxHP, 0);
      }

      CCanvas.gameScr.show();
      GameScr.trainingMode = true;
      GameScr.cam.setPlayerMode(0);
      GameScr.pm.setNextPlayer((byte)0);
      PM.p[0].isCom = false;
      PM.p[0].item = PrepareScr.getStrainingItem();
      GameScr.trainingStep = 0;
      PM.p[0].hp = 70;
      GameScr.myIndex = 0;
   }

   private void doShowShopItem() {
      ShopItem var10000 = CCanvas.shopItemScr;
      ShopItem.resetItemBuy();
      CCanvas.shopItemScr.show(this);
   }

   public void doEquipItem() {
      if (CCanvas.shopEquipScr == null) {
         CCanvas.shopEquipScr = new ShopEquipment();
      }

      CCanvas.shopEquipScr.show(this);
   }

   private void doShowInfo() {
      GameService.gI().requestInfoOf(TerrainMidlet.myInfo.IDDB);
      CCanvas.startWaitDlg(Language.pleaseWait());
   }

   public void doChargeMoney() {
      if (!CCanvas.isIos()) {
         if (CCanvas.moneyScr != null) {
            CCanvas.moneyScr.show();
         }
      } else if (CCanvas.moneyScrIOS != null) {
         CCanvas.moneyScrIOS.show();
      }

   }

   public void doRequestRoomList() {
      GameService.gI().requestRoomList();
      CCanvas.startWaitDlg(Language.pleaseWait());
   }

   private void doPlayNow(byte select) {
      if (select == 5) {
         PrepareScr.isBossRoom = true;
      } else {
         PrepareScr.isBossRoom = false;
      }

      GameService.gI().joinAnyBoard(select);
      CCanvas.startWaitDlg(Language.pleaseWait());
   }

   private void doShowFriend() {
      GameService.gI().requestFriendList();
      CCanvas.startWaitDlg(Language.pleaseWait());
   }

   private void doViewMessage() {
      CCanvas.msgScr.show(this);
   }

   private void doChangePass() {
      final String[] text = new String[3];
      final String[] name = new String[]{Language.oldPass(), Language.newPass(), Language.retypeNewPass()};
      CCanvas.inputDlg.setInfo(name[0], new IAction() {
         public void perform() {
            if (CCanvas.inputDlg.tfInput.getText().equals("")) {
               CCanvas.startOKDlg(Language.plOldPass(), new IAction() {
                  public void perform() {
                     CCanvas.inputDlg.show();
                  }
               });
            } else {
               text[0] = CCanvas.inputDlg.tfInput.getText();
               CCanvas.inputDlg.setInfo(name[1], new IAction() {
                  public void perform() {
                     if (CCanvas.inputDlg.tfInput.getText().equals("")) {
                        CCanvas.startOKDlg(Language.plNewPass(), new IAction() {
                           public void perform() {
                              CCanvas.inputDlg.show();
                           }
                        });
                     } else {
                        text[1] = CCanvas.inputDlg.tfInput.getText();
                        CCanvas.inputDlg.setInfo(name[2], new IAction() {
                           public void perform() {
                              if (CCanvas.inputDlg.tfInput.getText().equals("")) {
                                 CCanvas.startOKDlg(Language.plRetypeNewPass(), new IAction() {
                                    public void perform() {
                                       CCanvas.inputDlg.show();
                                    }
                                 });
                              } else {
                                 text[2] = CCanvas.inputDlg.tfInput.getText();
                                 if (text[2].equals(text[1])) {
                                    GameService.gI().requestChangePass(text[0], text[1]);
                                    CCanvas.endDlg();
                                    CCanvas.startOKDlg(Language.pleaseWait());
                                 } else {
                                    CCanvas.startOKDlg(Language.newPassNotMath());
                                 }

                              }
                           }
                        }, new IAction() {
                           public void perform() {
                              MenuScr.this.startScrollDown();
                              CCanvas.endDlg();
                           }
                        }, 2);
                     }
                  }
               }, new IAction() {
                  public void perform() {
                     MenuScr.this.startScrollDown();
                     CCanvas.endDlg();
                  }
               }, 2);
               CCanvas.inputDlg.show();
            }
         }
      }, new IAction() {
         public void perform() {
            MenuScr.this.startScrollDown();
            CCanvas.endDlg();
         }
      }, 2);
      CCanvas.inputDlg.show();
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
      Background.paintTree(g);
      Cloud.paintBalloonWithCloud(g);
      super.paint(g);
      if (!this.hide) {
         int nTab = 3;
         paintBorderRect(g, this.yB, nTab, this.hB, (String)null);
         int x;
         int a;
         if (curMenuLevel == 1 || curMenuLevel == 2) {
//            int pInfoY = false;
//            int pInfoX = false;
            a = CCanvas.width / 2 - this.W / 2;
            x = this.yB;
            if (curMenuSelect == MENU_CHOINGAY) {
               this.paintInformation(g, a, x);
            }

            if (!CCanvas.isTouch) {
               Font.bigFont.drawString(g, menuString[curMenuSelect], w >> 1, 5, 2);
            }
         }

         x = CCanvas.width / 2 - this.W / 2;
         this.W = nTab * 32 + 23 + 33;
         g.setClip(x + 5, this.yMenu, this.W - 10, this.hB);
         g.translate(0, -this.cmy);
         a = CCanvas.isTouch ? 5 : 0;
         int i;
         if (!this.scrollUp) {
            g.setColor(3374591);
            g.fillRect(x, this.yMenu + this.select * this.dis + a, this.W, 22, true);
            i = this.yMenu + this.select * this.dis + a;
            g.drawImage(GameScr.arrowMenu, x, i, 0, true);
            g.drawRegion(GameScr.arrowMenu, 0, 0, 14, 21, 3, x + this.W, i, 24, true);
         }

         for(i = 0; i < menuX.length; ++i) {
            if (curMenuLevel == 0) {
//               int cc = false;
               String strMenu = menuString[i];
               int bb = Font.normalFont.getWidth(strMenu);
               if (bb > 85) {
                  this.transTextLimit(this.transText1, bb - 85);
               }

               int cc = this.transText1.x;
               Font.bigFont.drawString(g, menuString[i], bb > 85 ? x + 8 + cc : menuX[i], this.yMenu + i * this.dis + a, bb > 85 ? 0 : 2);
            } else if (curMenuLevel == 1) {
               Font.bigFont.drawString(g, subMenuString[curMenuSelect][i], menuX[i], this.yMenu + i * this.dis + a, 2);
            } else if (curMenuLevel == 2) {
               if (curMenuSelect == MENU_CHOINGAY) {
                  Font.bigFont.drawString(g, subMenuString[6][i], menuX[i], this.yMenu + i * this.dis + a, 2);
               }

               if (curMenuSelect == MENU_TINTUC) {
                  Font.bigFont.drawString(g, subMenuString[7][i], menuX[i], this.yMenu + i * this.dis + a, 2);
               }
            } else if (curMenuLevel == 3 && curMenuSelect == 0) {
               Font.bigFont.drawString(g, subMenuString[MENU_NGAUNHIEN][i], menuX[i], this.yMenu + i * this.dis + a, 2);
            }
         }

         g.translate(0, this.cmy);
         GameMidlet.serverInformation(Font.normalFont, g);
      }
   }

   private void paintInformation(mGraphics g, int pInfoX, int pInfoY) {
      pInfoY -= 5;
      Font.borderFont.drawString(g, Language.name() + ": " + TerrainMidlet.myInfo.name, pInfoX + 20, pInfoY - Font.borderFont.getHeight() * 3, 0, false);
      Font.borderFont.drawString(g, "Level: " + TerrainMidlet.myInfo.level2 + "   " + Language.cup() + ": " + TerrainMidlet.myInfo.cup, pInfoX + 20, pInfoY - Font.borderFont.getHeight() * 2, 0, false);
      Font.borderFont.drawString(g, TerrainMidlet.myInfo.xu + Language.xu() + " - " + TerrainMidlet.myInfo.luong + Language.luong(), pInfoX + 20, pInfoY - Font.borderFont.getHeight(), 0, false);
      int quanHamType = TerrainMidlet.myInfo.nQuanHam2;
      g.drawRegion(PrepareScr.imgQuanHam, 0, 12 * quanHamType, 12, 12, 0, pInfoX + 6, pInfoY - Font.borderFont.getHeight() * 2 - 5, mGraphics.TOP | mGraphics.HCENTER, false);
      PlayerEquip e = null;
      if (TerrainMidlet.isVip[TerrainMidlet.myInfo.gun]) {
         e = TerrainMidlet.myInfo.myVipEquip;
      } else {
         e = TerrainMidlet.myInfo.myEquip;
      }

      CPlayer.paintSimplePlayer(TerrainMidlet.myInfo.gun, CCanvas.gameTick % 10 > 2 ? 5 : 4, pInfoX + 6, pInfoY, 0, e, g);
   }

   public void paintRoundR(int x, int y, int W, int H, mGraphics g) {
      g.setColor(8040447);
      g.fillRoundRect(x, y, W, H, 10, 10, false);
      g.setColor(16777215);
      g.drawRoundRect(x, y, W, H, 10, 10, false);
   }

   public void onPointerPressed(int x, int y2, int index) {
        super.onPointerPressed(x, y2, index);
        if (CCanvas.keyPressed[2] || CCanvas.keyPressed[8]) {
            if (CCanvas.keyPressed[2]) {
                --this.select;
                if (this.select < 0) {
                    this.select = menuX.length - 1;
                }
            }
            if (CCanvas.keyPressed[8]) {
                ++this.select;
                if (this.select > menuX.length - 1) {
                    this.select = 0;
                }
            }
            this.activeCMTOY(this.select, menuX.length);
            CScreen.clearKey();
        }
   }

   public void onPointerReleased(int x, int y2, int index) {
      if (!this.scrollDown && !this.scrollUp) {
         super.onPointerReleased(x, y2, index);
         this.trans = false;
         if (CCanvas.isPointer(0, this.yMenu, CCanvas.width, this.nItemShow * this.dis + 33, index)) {
            int currSelect = (y2 - this.yMenu + this.cmy) / this.dis;
            if (currSelect < 0) {
               currSelect = 0;
            }

            if (currSelect > menuX.length - 1) {
               currSelect = menuX.length - 1;
            }

            if (!MainGame.touchDrag) {
               if (this.select != currSelect) {
                  this.select = currSelect;
               } else {
                  this.doFire();
               }
            }
         }

      }
   }

   public void onPointerDragged(int x, int y2, int index) {
      if (!this.scrollDown && !this.scrollUp) {
         super.onPointerDragged(x, y2, index);
         if (!this.trans) {
            this.pa = this.cmy;
            this.trans = true;
         }

         if (!CCanvas.isPc()) {
            this.speed = 3;
         }

         if (CCanvas.isTouch) {
            this.cmtoY = this.pa + (CCanvas.pyFirst[index] - y2) * this.speed;
         }

         this.cmtoY = Clamp(this.cmtoY, 0, (menuX.length - this.nItemShow) * this.dis);
      }
   }

   public void activeCMTOY(int select, int nselect) {
      this.cmtoY = select * this.dis - this.hMenu / 2;
      if (CCanvas.isTouch && select != 0) {
         this.cmtoY += 10;
      }

      int hMenuMax = this.nItemShow * this.dis;
      if (this.cmtoY > nselect * this.dis - hMenuMax) {
         this.cmtoY = nselect * this.dis - hMenuMax;
      }

      if (this.cmtoY < 0) {
         this.cmtoY = 0;
      }

   }
}
