package screen;

import CLib.Image;
import CLib.mGraphics;
import CLib.mImage;
import Equipment.Equip;
import Equipment.PlayerEquip;
import coreLG.CCanvas;
import coreLG.TerrainMidlet;
import java.util.Vector;
import model.CRes;
import model.Font;
import model.IAction;
import model.Language;
import model.MaterialIconMn;
import model.PlayerInfo;
import model.Position;
import network.Command;
import network.GameService;
import player.CPlayer;

public class EquipScreen extends TabScreen {
   int num;
   int select;
   int[] xE;
   int[] yE;
   byte[] typeE;
   int[] dbKeyChange = new int[5];
   boolean isSelect;
   public static Vector<Equip> inventory = new Vector();
   public Vector<Equip> myEquips = new Vector();
   public static mImage imgIcon;
   public static mImage imgMaterial;
   public static mImage[] imgIconEQ = new mImage[5];
   public int wTab;
   public int wIndex;
   public int hIndex;
   public int wP;
   public Command cmdSelect;
   public Command menu;
   public static boolean isEquip = false;
   int dem;
   boolean isCompine;
   short mSelect;
   short mComSelect;
   public short[] lastDb = new short[5];
   public Vector vLastE = new Vector();
   public Equip[] lastEquip = new Equip[5];
   int W;
   static int cmtoYI;
   static int cmyI;
   static int cmdyI;
   static int cmvyI;
   int nLine;
   int ind;
   public byte[] addPoint;
   public int[] atts;
   public Equip equipSelect;
   int dx;
   String attribute;
   String name;
   int xName;
   int wName;
   int wDetail;
   boolean scroll;
   int ds;
   Position transText1;
   Position transText2;
   int cc;
   int ee;
   public Equip[] currEq;
   PlayerEquip equip;
   int pa;
   boolean trans;
   int speed;
   int cmtoYITem;

   public EquipScreen() {
      this.W = CCanvas.width;
      this.addPoint = new byte[6];
      this.atts = new int[5];
      this.dx = -1;
      this.attribute = "";
      this.name = "";
      this.transText1 = new Position(0, 1);
      this.transText2 = new Position(0, 1);
      this.currEq = new Equip[5];
      this.equip = null;
      this.pa = 0;
      this.trans = false;
      this.speed = 1;
      this.nameCScreen = "EquipScreen screen!";
      this.xPaint = CCanvas.width / 2 - 75;
      this.yPaint = (CCanvas.hieght - CScreen.cmdH) / 2 - 85;
      this.wTabScreen = 150;
      this.hTabScreen = 170;
      this.xE = new int[]{this.W / 2 - 52, this.W / 2 - 77, this.W / 2 - 27, this.W / 2 - 37, this.W / 2 - 67};
      this.yE = new int[]{this.yPaint + 33, this.yPaint + 55, this.yPaint + 55, this.yPaint + 83, this.yPaint + 83};
      this.typeE = new byte[]{0, 1, 2, 3, 4};
      this.menu = new Command("Menu", new IAction() {
         public void perform() {
            Command detail = new Command(Language.detail(), new IAction() {
               public void perform() {
                  EquipScreen.this.doDetail();
               }
            });
            Command inventory = new Command(Language.ruongdo(), new IAction() {
               public void perform() {
                  if (EquipScreen.inventory.size() == 0) {
                     CCanvas.startOKDlg(Language.beNotInventory());
                  } else {
                     EquipScreen.this.doInventory();
                  }

               }
            });
            String title = !EquipScreen.this.isCompine ? Language.kethop() : Language.trangbi();
            new Command(title, new IAction() {
               public void perform() {
                  EquipScreen.this.isCompine = !EquipScreen.this.isCompine;
                  if (!EquipScreen.this.isCompine) {
                     for(int i = 0; i < EquipScreen.this.myEquips.size(); ++i) {
                        ((Equip)EquipScreen.this.myEquips.elementAt(i)).isSelect = false;
                     }
                  }

               }
            });
            Vector<Command> menu = new Vector();
            if (EquipScreen.this.myEquips.size() != 0) {
               if (EquipScreen.this.getEquipSelect().date == 0) {
                  Command giahan = new Command("Gia hạn", new IAction() {
                     public void perform() {
                        CCanvas.startOKDlg(Language.pleaseWait());
                        GameService.gI().get_more_day((byte)0, EquipScreen.this.getEquipSelect().dbKey);
                     }
                  });
                  menu.addElement(giahan);
               }

               menu.addElement(detail);
            }

            menu.addElement(inventory);
            CCanvas.menu.startAt(menu, 0);
         }
      });
      this.left = this.menu;
      this.cmdSelect = new Command(Language.select(), new IAction() {
         public void perform() {
            EquipScreen.this.doFire();
         }
      });
      this.center = this.cmdSelect;
      this.right = new Command(Language.close(), new IAction() {
         public void perform() {
            for(int i = 0; i < EquipScreen.this.lastDb.length; ++i) {
               if (EquipScreen.this.dbKeyChange[i] != EquipScreen.this.lastDb[i]) {
                  CCanvas.startYesNoDlg(Language.saveEquip(), new IAction() {
                     public void perform() {
                        EquipScreen.this.doAgree();
                     }
                  }, new IAction() {
                     public void perform() {
                        EquipScreen.this.doClose();
                     }
                  });
                  return;
               }
            }

            EquipScreen.this.doClose();
         }
      });
      this.title = Language.trangbi();
      this.n = 4;
      this.getW();
      if (CCanvas.isTouch) {
         this.wTab = 30;
         this.wIndex = 2;
         this.wP = 5;
      } else {
         this.wTab = 20;
         this.wIndex = 3;
         this.wP = 0;
      }

   }

   public void init() {
      isEquip = true;
      this.isClose = false;
      this.select = 0;
      PlayerInfo m = TerrainMidlet.myInfo;
      PlayerInfo.vipID = m.equipVipID[m.gun][1];
      this.getLastEquip();
      this.getDetail();
      TerrainMidlet.myInfo.getMyEquip(9);
      TerrainMidlet.myInfo.setAllEquipEffect();

      for(int i = 0; i < 5; ++i) {
         this.dbKeyChange[i] = -1;
         this.lastDb[i] = -1;
      }

      this.getMyEquip();
      if (inventory.size() == 0) {
         inventory = inventory;
      }

      this.vLastE = this.myEquips;
      this.setCurrEquip();
      this.getBaseAttribute();
      this.seeNextAttribute();
   }

   public void show(CScreen lastScreen) {
      if (this.select < 0) {
         this.select = 0;
      }

      if (this.select > this.myEquips.size() - 1) {
         this.select = this.myEquips.size() - 1;
      }

      if (this.getEquipSelect() != null) {
         this.getDetail();
      }

      super.show(lastScreen);
   }

   public Equip getEquip(int dbKey) {
      for(int i = 0; i < this.myEquips.size(); ++i) {
         Equip e = (Equip)this.myEquips.elementAt(i);
         if (e.dbKey == dbKey) {
            return e;
         }
      }

      return null;
   }

   public void removeEquip(int dbKey, int nDelete) {
      for(int i = 0; i < this.myEquips.size(); ++i) {
         Equip e = (Equip)this.myEquips.elementAt(i);
         if (!e.isMaterial) {
            if (e.dbKey == dbKey) {
               e.num -= nDelete;
               if (e.num <= 0) {
                  e.num = 0;
                  this.myEquips.removeElement(e);
                  this.currEq[i] = null;
               }

               return;
            }
         } else if (e.id == dbKey) {
            e.num -= nDelete;
            if (e.num <= 0) {
               e.num = 0;
               this.myEquips.removeElement(e);
            }

            return;
         }
      }

   }

   public int countCombine() {
      int a = 0;

      for(int i = 0; i < this.myEquips.size(); ++i) {
         Equip e = (Equip)this.myEquips.elementAt(i);
         if (e.isSelect) {
            ++a;
         }
      }

      return a;
   }

   public void getMyEquip() {
      this.myEquips.removeAllElements();

      for(int i = 0; i < inventory.size(); ++i) {
         Equip e = (Equip)inventory.elementAt(i);
         if (!e.isMaterial && e.glass == TerrainMidlet.myInfo.gun) {
            this.myEquips.addElement(e);
         }
      }

      byte materialId = -1;

      for(int i = 0; i < inventory.size(); ++i) {
         Equip e = (Equip)inventory.elementAt(i);
         if (e.isMaterial && materialId != (byte)e.id) {
            materialId = (byte)e.id;
            if (e.materialIcon == null) {
               if (MaterialIconMn.isExistIcon(e.icon)) {
                  e.materialIcon = MaterialIconMn.getImageFromID(e.icon);
               }

               GameService.gI().getMaterialIcon((byte)0, materialId, -1);
            }
         }
      }

      this.hIndex = this.myEquips.size() / this.wIndex;
      if (this.myEquips.size() % this.wIndex != 0) {
         ++this.hIndex;
      }

      PlayerInfo info = TerrainMidlet.myInfo;

      for(int i = 0; i < this.myEquips.size(); ++i) {
         Equip tam = (Equip)this.myEquips.elementAt(i);
         if (info.myEquip.equips[tam.type] != null && info.myEquip.equips[tam.type].dbKey == tam.dbKey) {
            info.myEquip.equips[tam.type].removeAbility();
            info.myEquip.equips[tam.type].addAbilityFromEquip(tam);
         }
      }

   }

   public void addEquip(Equip e, boolean isNum) {
      boolean tam = true;

      for(int i = 0; i < inventory.size(); ++i) {
         Equip eq = (Equip)inventory.elementAt(i);
         if (eq.isMaterial && eq.id == e.id) {
            if (!isNum) {
               ++eq.num;
            } else {
               eq.num += e.num;
            }

            tam = false;
            break;
         }
      }

      if (tam) {
         if (MaterialIconMn.isExistIcon(e.icon)) {
            e.materialIcon = MaterialIconMn.getImageFromID(e.id);
         } else {
            GameService.gI().getMaterialIcon((byte)0, (byte)e.id, -1);
         }

         inventory.insertElementAt(e, 0);
      }

   }

   public void addMaterial(Equip e) {
      inventory.addElement(e);
   }

   public void getEquip(Vector icon) {
      inventory = new Vector();
      inventory = icon;
      this.getMyEquip();
   }

   public void getMaterialIcon(int id, byte[] dataRawImages, int len) {
      int i;
      Equip e;
      for(i = 0; i < inventory.size(); ++i) {
         e = (Equip)inventory.elementAt(i);
         if (e.isMaterial && e.id == id) {
            e.materialIcon = mImage.createImage(dataRawImages, 0, len, "EquipScrenn" + e.id, (String)null);
         }
      }

      for(i = 0; i < this.myEquips.size(); ++i) {
         e = (Equip)this.myEquips.elementAt(i);
         if (e.isMaterial && e.id == id) {
            e.materialIcon = mImage.createImage(dataRawImages, 0, len, "EquipScrenn" + e.id, (String)null);
         }
      }

   }

   public void getMaterialIcon(int id, Image img) {
      int i;
      Equip e;
      for(i = 0; i < inventory.size(); ++i) {
         e = (Equip)inventory.elementAt(i);
         if (e.isMaterial && e.id == id) {
            e.materialIcon = new mImage(img);
         }
      }

      for(i = 0; i < this.myEquips.size(); ++i) {
         e = (Equip)this.myEquips.elementAt(i);
         if (e.isMaterial && e.id == id) {
            e.materialIcon = new mImage(img);
         }
      }

   }

   public void addEquip(Equip e) {
      inventory.insertElementAt(e, 0);
      this.myEquips.insertElementAt(e, 0);
   }

   public void doClose() {
      this.isClose = true;
      CCanvas.endDlg();
      this.resetEquip();
   }

   public void doAgree() {
      CCanvas.startWaitDlg(Language.pleaseWait());
      GameService.gI().changeEquip(this.dbKeyChange);
   }

   public void doFire() {
      try {
         if (this.myEquips.size() == 0) {
            return;
         }

         if (this.getEquipSelect() == null) {
            return;
         }

         if (this.getEquipSelect().isMaterial) {
            return;
         }

         if (this.getEquipSelect().date == 0) {
            CCanvas.startYesNoDlg(Language.noticGiahanTrangBi(), new IAction() {
               public void perform() {
                  CCanvas.startOKDlg(Language.pleaseWait());
                  GameService.gI().get_more_day((byte)0, EquipScreen.this.getEquipSelect().dbKey);
               }
            }, new IAction() {
               public void perform() {
                  CCanvas.endDlg();
               }
            });
            return;
         }

         PlayerInfo m = TerrainMidlet.myInfo;
         Equip e = this.getEquipSelect();
         if (e.vip == 1) {
            if (e.id != PlayerInfo.vipID) {
               GameService.gI().vip_equip((byte)1, e.dbKey);
            } else {
               boolean isVip = TerrainMidlet.isVip[m.gun];
               if (isVip) {
                  e.isVip = true;
                  GameService.gI().vip_equip((byte)0, e.dbKey);
               } else {
                  e.isVip = false;
                  GameService.gI().vip_equip((byte)1, e.dbKey);
               }
            }

            return;
         }

         if (e.level > m.level2) {
            CCanvas.startOKDlg(Language.banphaitren() + e.level + Language.moicothe());
            return;
         }

         int i;
         for(i = 0; i < 5; ++i) {
            if (m.myEquip.equips[i] != null && e.dbKey == m.myEquip.equips[i].dbKey) {
               return;
            }
         }

         for(i = 0; i < this.typeE.length; ++i) {
            if (e.type == this.typeE[i]) {
               this.dbKeyChange[i] = e.dbKey;
            }
         }

         short id = m.equipID[m.gun][e.type];
         Equip currE = PlayerEquip.createEquip(m.gun, e.type, id);
         m.addChangeEquip(e, currE);
         this.changeEquip();
         this.setCurrEquip();
         this.getBaseAttribute();
      } catch (Exception var5) {
      }

   }

   public void doDetail() {
      CCanvas.startOKDlg(this.attribute);
   }

   public Equip getEquipSelect() {
      if (this.myEquips.size() > 0) {
         if (this.select <= 0) {
            this.select = 0;
         } else if (this.select >= this.myEquips.size()) {
            this.select = this.myEquips.size() - 1;
         }

         Equip eS = (Equip)this.myEquips.elementAt(this.select);
         return eS;
      } else {
         return null;
      }
   }

   public void resetEquip() {
      try {
         PlayerInfo m = TerrainMidlet.myInfo;

         for(int i = 0; i < this.lastEquip.length; ++i) {
            if (this.lastEquip[i] != null) {
               m.myEquip.equips[i].changeToEquip(this.lastEquip[i]);
            }
         }

         this.myEquips = this.vLastE;
      } catch (Exception var3) {
      }

   }

   public void getLastEquip() {
      PlayerInfo m = TerrainMidlet.myInfo;

      for(int i = 0; i < 5; ++i) {
         if (m.myEquip.equips[i] != null) {
            this.lastDb[i] = (short)m.myEquip.equips[i].dbKey;
            m.equipID[m.gun][i] = m.myEquip.equips[i].id;
            this.lastEquip[i] = new Equip();
            this.lastEquip[i].changeToEquip(m.myEquip.equips[i]);
         } else {
            this.lastDb[i] = -1;
         }

         this.dbKeyChange[i] = this.lastDb[i];
      }

   }

   public void changeEquip() {
      PlayerInfo m = TerrainMidlet.myInfo;
      Equip sl = this.getEquipSelect();
      if (sl != null) {
         if (m.myEquip.equips[sl.type] == null) {
            m.myEquip.equips[sl.type] = PlayerEquip.getEquip(sl.glass, sl.type, sl.id);
         }

         m.myEquip.equips[sl.type].changeToEquip(sl);
      }

      m.myEquip.equips[sl.type].icon = sl.icon;
      m.myEquip.equips[sl.type].x = sl.x;
      m.myEquip.equips[sl.type].y = sl.y;
      m.myEquip.equips[sl.type].dx = sl.dx;
      m.myEquip.equips[sl.type].dy = sl.dy;
      m.myEquip.equips[sl.type].w = sl.w;
      m.myEquip.equips[sl.type].h = sl.h;
      m.myEquip.equips[sl.type].bullet = sl.bullet;
      m.myEquip.equips[sl.type].frame = sl.frame;
      m.myEquip.equips[sl.type].addAbilityFromEquip(sl);
      m.myEquip.equips[sl.type].dbKey = sl.dbKey;
   }

   public void doInventory() {
      this.isClose = true;
      CCanvas.inventory.show(CCanvas.menuScr);
   }

   public void paintEquip(mGraphics g, Image img, int X, int Y) {
      g.setColor(4156571);
      g.fillRoundRect(X - 1 - 9, Y - 1 - 9, 20, 20, 4, 4, false);
      g.setColor(16774532);
      g.fillRect(X - 1 - 8, Y - 1 - 8, 18, 18, false);
   }

   public void itemCamera() {
      if (cmyI != cmtoYI) {
         cmvyI = cmtoYI - cmyI << 2;
         cmdyI += cmvyI;
         cmyI += cmdyI >> 4;
         cmdyI &= 15;
      }

      this.nLine = this.num / this.wIndex;
      if (this.num % this.wIndex != 0) {
         ++this.nLine;
      }

      int yLim = this.nLine * this.wTab - 60;
      if (cmyI > yLim) {
         cmyI = yLim;
      }

      if (cmyI < 0) {
         cmyI = 0;
      }

   }

   public void paintItem(mGraphics g, int X, int Y) {
      g.setColor(4156571);
      g.fillRoundRect(X - 5, this.yPaint + 96, 72, 67, 6, 6, false);
      g.setClip(X - 1, Y - 1, 62, 60);
      g.translate(0, -cmyI);
      int j = 0;
      int i = 0;
      Equip e = null;

      for(int n = 0; n < this.myEquips.size(); ++n) {
         e = (Equip)this.myEquips.elementAt(n);
         int x1 = X + i * this.wTab + this.wP;
         int y1 = Y + j * this.wTab + this.wP;
         int xIcon = x1;
         int yIcon = y1;
         if (e != null) {
            PlayerInfo m = TerrainMidlet.myInfo;
            if (m.myEquip.equips[e.type] != null && e.dbKey == m.myEquip.equips[e.type].dbKey) {
               g.setColor(4819660);
               g.fillRect(x1, y1, 16, 16, true);
            }

            if (e.vip == 1) {
               g.setColor(5361158);
               g.fillRect(x1, y1, 16, 16, true);
               if (TerrainMidlet.isVip[m.gun] && e.id == PlayerInfo.vipID) {
                  g.setColor(5963263);
                  g.fillRect(x1, y1, 16, 16, true);
               }
            }

            if (e.date == 0) {
               g.setColor(9014930);
               g.fillRect(x1, y1, 16, 16, true);
            }

            if (this.select == n) {
               g.setColor(16767817);
               g.fillRect(x1 - 1, y1 - 1, 18, 18, true);
               if (!CCanvas.isTouch) {
                  cmtoYI = y1 - (Y + 20);
               }

               Equip eS = (Equip)this.myEquips.elementAt(this.select);
               if (e != null) {
                  for(int a = 0; a < this.typeE.length; ++a) {
                     if (eS.type == this.typeE[a]) {
                        this.ind = a;
                     }
                  }
               }
            }

            if (e.isSelect) {
               g.setColor(16777215);
               g.fillRect(x1, y1, 16, 16, true);
            }

            e.drawIcon(g, x1, y1, true);
            if (!e.isMaterial) {
               for(int a = 0; a < 3 - e.slot; ++a) {
                  if (i != this.select) {
                     g.setColor(16377901);
                     g.fillRect(xIcon + a * 4, yIcon, 2, 2, true);
                  } else {
                     g.setColor(0);
                     g.fillRect(xIcon + a * 4, yIcon, 2, 2, true);
                  }
               }
            }
         }

         ++i;
         if (i == this.wIndex) {
            ++j;
            i = 0;
         }
      }

      g.translate(0, -g.getTranslateY());
   }

   public void getBaseAttribute() {
      PlayerInfo info = TerrainMidlet.myInfo;
      int[] ability = new int[5];
      int[] percen = new int[5];
      Equip vip = null;
      int j;
      Equip eq;
      if (TerrainMidlet.isVip[TerrainMidlet.myInfo.gun]) {
         CRes.out("DANG VIP");

         for(j = 0; j < this.myEquips.size(); ++j) {
            eq = (Equip)this.myEquips.elementAt(j);
            if (eq.id == PlayerInfo.vipID) {
               vip = eq;
               break;
            }
         }
      }

      for(j = 0; j < 5; ++j) {
         eq = info.myEquip.equips[j];
         if (eq != null) {
            for(j = 0; j < 5; ++j) {
               ability[j] += eq.inv_ability[j];
               percen[j] += eq.inv_percen[j];
            }
         }
      }

      if (vip != null) {
         for(j = 0; j < 5; ++j) {
            ability[j] += vip.inv_ability[j];
            percen[j] += vip.inv_percen[j];
         }
      }

      this.atts[0] = 1000 + info.ability[0] * 10 + ability[0] * 10;
      int[] var10000 = this.atts;
      var10000[0] += (1000 + info.ability[0]) * percen[0] / 100;
      int maxDamage = PlayerEquip.getEquipGlass(info.gun).maxDamage;
      int damPoint = ability[1] + info.ability[1];
      j = ability[2] + info.ability[2];
      int luckPoint = ability[3] + info.ability[3];
      int teamPoint = ability[4] + info.ability[4];
      this.atts[1] = maxDamage * (damPoint / 3 + 100 + percen[1]) / 100;
      this.atts[2] = j * 10;
      var10000 = this.atts;
      var10000[2] += this.atts[2] * percen[2] / 100;
      this.atts[3] = luckPoint * 10;
      var10000 = this.atts;
      var10000[3] += this.atts[3] * percen[3] / 100;
      this.atts[4] = teamPoint * 10;
      var10000 = this.atts;
      var10000[4] += this.atts[4] * percen[4] / 100;
   }

   public void paintAbility(mGraphics g) {
      Font.normalFont.drawString(g, "Level: " + TerrainMidlet.myInfo.level2, this.W / 2 + 24, this.yPaint + 22, 3);
      Font.normalFont.drawString(g, "%", this.W / 2 + 75, this.yPaint + 22, 3);

      for(int i = 0; i < 5; ++i) {
         g.drawRegion(LevelScreen.ability, 0, i * 16, 16, 16, 0, this.W / 2 - 1, this.yPaint + 46 + i * 18, 3, false);
         g.setColor(2378093);
         g.fillRect(CCanvas.width / 2 + 9, this.yPaint + 38 + i * 18, 35, 16, false);
         g.fillRect(CCanvas.width / 2 + 46, this.yPaint + 38 + i * 18, 18, 16, false);
         g.fillRect(CCanvas.width / 2 + 66, this.yPaint + 38 + i * 18, 19, 16, false);
         PlayerInfo info = TerrainMidlet.myInfo;
         String attAddP = String.valueOf(Math.abs(info.attAddPoint1[i]));
         String perAddP = String.valueOf(Math.abs(info.attAddPoint2[i]));
         int attribute = this.atts[i];
         Font.normalYFont.drawString(g, String.valueOf(attribute), this.W / 2 + 26, this.yPaint + 39 + i * 18, 3);
         byte var10000 = info.UpOrDown1[i];
         info.getClass();
         if (var10000 == 0) {
            Font.normalYFont.drawString(g, attAddP, this.W / 2 + 56, this.yPaint + 39 + i * 18, 3);
         }

         var10000 = info.UpOrDown1[i];
         info.getClass();
         if (var10000 == 2) {
            Font.normalRFont.drawString(g, attAddP, this.W / 2 + 56, this.yPaint + 39 + i * 18, 3);
         }

         var10000 = info.UpOrDown1[i];
         info.getClass();
         if (var10000 == 1) {
            Font.normalGFont.drawString(g, attAddP, this.W / 2 + 56, this.yPaint + 39 + i * 18, 3);
         }

         var10000 = info.UpOrDown2[i];
         info.getClass();
         if (var10000 == 0) {
            Font.normalYFont.drawString(g, perAddP, this.W / 2 + 75, this.yPaint + 39 + i * 18, 3);
         }

         var10000 = info.UpOrDown2[i];
         info.getClass();
         if (var10000 == 2) {
            Font.normalRFont.drawString(g, perAddP, this.W / 2 + 75, this.yPaint + 39 + i * 18, 3);
         }

         var10000 = info.UpOrDown2[i];
         info.getClass();
         if (var10000 == 1) {
            Font.normalGFont.drawString(g, perAddP, this.W / 2 + 75, this.yPaint + 39 + i * 18, 3);
         }
      }

   }

   public void getDetail() {
      this.dx = -1;
      this.ds = 0;
      this.scroll = false;
      this.attribute = "";
      Equip eq = this.getEquipSelect();
      if (eq != null) {
         this.xName = this.W / 2 - 4;
         this.wName = Font.normalFont.getWidth(eq.name);
         this.name = eq.name;
         if (eq.isMaterial) {
            this.attribute = eq.strDetail;
         } else {
            this.attribute = eq.getStrInvDetail();
         }

         this.wDetail = Font.normalFont.getWidth(this.name);
      }
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

   public void paintMoney(mGraphics g) {
      g.setColor(1521982);
      g.setClip(this.W / 2 - 9, this.yPaint + 40 + 90, 95, 60);
      g.fillRoundRect(this.W / 2 - 9, this.yPaint + 40 + 90, 95, 16, 6, 6, false);
      g.fillRoundRect(this.W / 2 - 9, this.yPaint + 58 + 90, 95, 16, 6, 6, false);
      PlayerInfo m = TerrainMidlet.myInfo;
      String money = CRes.getMoneys(m.xu) + Language.xu() + "-" + m.luong + Language.luong2();
      int yMoney = this.yPaint + 41 + 90;
      Font.normalYFont.drawString(g, money, this.xName + this.cc, yMoney, 0);
      int xName = this.W / 2 - 4;
      int yName = this.yPaint + 59 + 90;
      Font.normalGFont.drawString(g, this.name, xName + this.dx + this.ee, yName, 0);
      g.setClip(0, 0, CCanvas.width, CCanvas.hieght);
   }

   public void setCurrEquip() {
      for(int i = 0; i < 5; ++i) {
         PlayerInfo info = TerrainMidlet.myInfo;
         byte id = -1;
         if (info.myEquip.equips[i] != null) {
            id = (byte)info.myEquip.equips[i].id;
         }

         this.currEq[i] = PlayerEquip.getEquip(info.gun, (byte)i, id);
      }

   }

   public void paintPlayer(mGraphics g) {
      g.setColor(16767817);
      g.drawRect(this.xE[this.ind] - 9, this.yE[this.ind] - 9, 17, 17, false);
      g.drawRect(this.xE[this.ind] - 10, this.yE[this.ind] - 10, 19, 19, false);
      g.setColor(1521982);
      g.drawRect(this.xE[this.ind] - 11, this.yE[this.ind] - 11, 21, 21, false);

      for(int i = 0; i < 5; ++i) {
         this.paintEquip(g, GameScr.s_imgITEM.image, this.xE[i], this.yE[i]);
         if (this.currEq[i] != null) {
            this.currEq[i].drawIcon(g, this.xE[i] - 8, this.yE[i] - 8, true);
         } else {
            g.drawRegion(GameScr.s_imgITEM, 0, 0, 16, 16, 0, this.xE[i], this.yE[i], 3, true);
         }
      }

      PlayerInfo myInfo = TerrainMidlet.myInfo;
      int gun = myInfo.gun;
      if (TerrainMidlet.isVip[myInfo.gun]) {
         this.equip = myInfo.myVipEquip;
      } else {
         this.equip = myInfo.myEquip;
      }

      CPlayer.paintSimplePlayer(gun, CCanvas.gameTick % 5 > 2 ? 5 : 4, CCanvas.width / 2 - 52, this.yPaint + 71, 0, this.equip, g);
   }

   public void paint(mGraphics g) {
      super.paint(g);
      this.paintPlayer(g);
      this.paintItem(g, this.W / 2 - 78, this.yPaint + 102);
      this.paintMoney(g);
      this.paintAbility(g);
      this.paintSuper(g);
   }

   public void seeNextAttribute() {
      if (this.myEquips.size() != 0) {
         try {
            PlayerInfo m = TerrainMidlet.myInfo;
            Equip e = this.getEquipSelect();
            if (e == null) {
               return;
            }

            Equip currE = null;
            PlayerEquip.getEquip(m.gun, e.type, m.equipID[m.gun][e.type]);
            currE = m.myEquip.equips[e.type];
            if (e.isMaterial || this.isCompine || e.vip == 1) {
               currE = e;
            }

            m.compareEquip(e, currE);
            this.getDetail();
            this.transText2.x = -1;
         } catch (Exception var4) {
            var4.printStackTrace();
         }

      }
   }

   public void update() {
      super.update();
      this.num = this.myEquips.size();
      this.center = this.cmdSelect;
      this.left = this.menu;
      this.itemCamera();
      PlayerInfo m = TerrainMidlet.myInfo;
      PlayerInfo.vipID = m.equipVipID[m.gun][1];
      String money = CRes.getMoneys(TerrainMidlet.myInfo.xu) + Language.xu() + "-" + TerrainMidlet.myInfo.luong + Language.luong2();
      int bb = Font.normalFont.getWidth(money);
      if (bb > 85) {
         this.transTextLimit(this.transText1, bb - 80);
      }

      this.cc = this.transText1.x;
      int dd = Font.normalFont.getWidth(this.name);
      if (dd > 85) {
         this.transTextLimit(this.transText2, dd - 80);
      }

      this.ee = this.transText2.x;
   }

   public void onPointerDragged(int xDrag, int yDrag, int index) {
      super.onPointerDragged(xDrag, yDrag, index);
      if (!this.trans) {
         this.pa = cmyI;
         this.trans = true;
      }

      if (CCanvas.isPc()) {
         this.speed = 3;
      }

      cmtoYI = this.pa + (CCanvas.pyFirst[index] - yDrag) * this.speed;
      this.cmtoYITem = this.pa + (CCanvas.pyFirst[index] - yDrag);
      if (cmtoYI <= 0) {
         cmtoYI = 0;
         this.cmtoYITem = 0;
      }

   }

   public void onPointerReleased(int xReleased, int yReleased, int index) {
      super.onPointerReleased(xReleased, yReleased, index);
      this.trans = false;
      int xDraw = this.W / 2 - 86;
      int yDraw = this.yPaint + 96;
      if (CCanvas.isPointer(xDraw, yDraw + this.wP, 72, 87, index)) {
         int aa = (this.cmtoYITem + yReleased - yDraw - this.wP) / this.wTab * this.wIndex + (xReleased - xDraw - this.wP) / this.wTab;
         if (aa == this.select && this.center != null && CCanvas.isDoubleClick) {
            this.center.action.perform();
         }

         this.select = aa;
         this.getDetail();
         if (this.select < 0) {
            this.select = 0;
         }

         if (this.select > this.myEquips.size() - 1) {
            this.select = this.myEquips.size() - 1;
         }
      }

      this.seeNextAttribute();
   }

   public void onPointerPressed(int xPress, int yPress, int index) {
      super.onPointerPressed(xPress, yPress, index);
      int xDraw = this.W / 2 - 78;
      int yDraw = this.yPaint + 102;
      if (CCanvas.isPointer(xDraw, yDraw, 72, 87, index)) {
         int aa = (this.cmtoYITem + yPress - yDraw) / this.wTab * this.wIndex + (xPress - xDraw - this.wP) / this.wTab;
         this.select = aa;
         this.getDetail();
         if (this.select < 0) {
            this.select = 0;
         }

         if (this.select > this.myEquips.size() - 1) {
            this.select = this.myEquips.size() - 1;
         }
      }
      
      if (CCanvas.keyPressed[2] || CCanvas.keyPressed[4] || CCanvas.keyPressed[6] || CCanvas.keyPressed[8]) {
            if (CCanvas.keyPressed[2]) {
                this.select -= this.wIndex;
            }
            if (CCanvas.keyPressed[8]) {
                this.select += this.wIndex;
            }
            if (CCanvas.keyPressed[4]) {
                this.select--;
            }
            if (CCanvas.keyPressed[6]) {
                this.select++;
            }
            if (select > this.myEquips.size() - 1) {
                select = 0;
            }
            if (select < 0) {
                select = this.myEquips.size() - 1;
            }
            cmtoYI = (select / this.wIndex) * 40 - 20;
//            if (cmtoYI > cm) {
//                cmtoYI = cmyILim;
//            }
            if (cmtoYI < 0) {
                cmtoYI = 0;
            }
            CScreen.clearKey();
       }

   }
}
