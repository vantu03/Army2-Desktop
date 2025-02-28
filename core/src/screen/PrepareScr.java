package screen;

import CLib.mGraphics;
import CLib.mImage;
import Equipment.PlayerEquip;
import coreLG.CCanvas;
import coreLG.CONFIG;
import coreLG.TerrainMidlet;
import effect.Cloud;
import item.Bullet;
import item.Item;
import item.MyItemIcon;

import java.util.Vector;

import map.MM;
import model.CRes;
import model.ChatPopup;
import model.FilePack;
import model.Font;
import model.IAction;
import model.Language;
import model.PlayerInfo;
import model.TField;
import network.Command;
import network.GameService;
import player.CPlayer;
import shop.ShopItem;

public class PrepareScr extends CScreen {
    public static final String[] GUN_NAME = new String[]{"GUNNER", "MISS 6", "ELECTICIAN", "KING KONG", "ROCKETER", "GRANOS", "CHICKY", "TARZAN", "APACHE", "MAGENTA", "LASER GIRL", "COW GIRL"};
    private static final int MAPCOUNT;
    boolean isStartGame;
    public static byte numPlayer;
    public static byte currentRoom;
    public static byte currLevel;
    public Vector<PlayerInfo> playerInfos = new Vector();
    public Vector bossInfos = new Vector();
    public int showChatInterval;
    public static mImage[] imgMap;
    public static mImage imgGun;
    public static mImage imgQuanHam;
    public static mImage imgBack;
    public static mImage khungMap;
    public static mImage line;
    public static mImage iconChat;
    public static byte[] mapBossID;
    public static byte[] bossID;
    private int[] x_pos;
    private int[] y_pos;
    int x_player = 30;
    int y_player = 60;
    int y_owner = 0;
    int money;
    int ownerID;
    int indexOfMe = -1;
    int height = 30;
    int y_Map = 0;
    int x_Name = 0;
    public static byte curMap;
    private byte numMap = 3;
    public static byte numCurItemSlot;
    public int[] itemCur;
    public static MyItemIcon prepareScrItemIcon;
    public static byte numUseSlot;
    private boolean isChooseItem;
    private boolean isChooseGun;
    private int selectedItem;
    private int selectedGunLv2;
    private int curUsing;
    int x_item;
    int y_item;
    int y_item_touch;
    int x_item_touch;
    public static short bulletType;
    public static TField tfChat;
    boolean isGameEnd;
    Command cmdExit;
    Command cmdStart;
    Command cmdReady;
    Command cmdMenu;
    Command cmdChangeTeam;
    Command cmdAddFriend;
    Command cmdSelect;
    Command cmdLeft;
    Command cmdRight;
    Command cmdBack;
    Command cmdBanBe;
    Command cmdTinNhan;
    Command cmdMap;
    Command cmdInvite;
    Command findRoom;
    static int roomID;
    static int zoneID;
    public static mImage[] imgReady;
    public static mImage[] imgCloud;
    public static mImage rockImg;
    public static mImage rock2Img;
    public static mImage glassFly;
    public static mImage chickenHair;
    public static mImage cloud1;
    public static mImage imgSun;
    public static mImage randomMap;
    public static FilePack filePak;
    public static byte[] fileData;
    public int chatDelay;
    public int readyDelay;
    int xPaintMap;
    int yPaintMap;
    int anchorPainMap;
    public static boolean isBossRoom;
    boolean isTouchItem;

    static {
        MAPCOUNT = MM.NUM_MAP;
        numPlayer = 8;
        curMap = 0;
        numCurItemSlot = 8;
        numUseSlot = 4;
        bulletType = 0;
        roomID = -1;
        zoneID = -1;
        imgReady = new mImage[9];
        imgReady[0] = GameScr.imgReady[0];
        imgReady[1] = GameScr.imgReady[1];
        imgReady[2] = GameScr.imgReady[2];
        imgReady[3] = GameScr.imgReady[3];
        imgReady[4] = GameScr.imgReady[4];
        imgQuanHam = GameScr.imgQuanHam;
        imgBack = GameScr.imgBack;
        imgGun = Bullet.imgGun;

        try {
            randomMap = mImage.createImage("/randomMap.png");
            line = mImage.createImage("/map/line.png");
            iconChat = mImage.createImage("/iconChat.png");
        } catch (Exception var1) {
            var1.printStackTrace();
        }

        isBossRoom = false;
    }

    public static void init() {
        CCanvas.roomListScr = new RoomListScr();
        imgMap = new mImage[MM.NUM_MAP];

        int i;
        try {
            filePak = new FilePack(fileData);
            if (filePak != null) {
                for (i = 0; i < MM.NUM_MAP; ++i) {
                    imgMap[i] = filePak.loadImage(MM.mapFileName[i] + ".png");
                }

                khungMap = filePak.loadImage("khungmap.png");
            }
        } catch (Exception var2) {
            var2.printStackTrace();
        }

        filePak = null;

        for (i = 0; i < MAPCOUNT - 1; ++i) {
            String mapName = CCanvas.getClassPathConfig(CONFIG.PATH_MAP + "map" + i + ".png");
            imgMap[i] = mImage.createImage(mapName);
        }

    }

    public void getPlayerIcon(short clanID, mImage icon) {
        for (int i = 0; i < this.playerInfos.size(); ++i) {
            PlayerInfo info = (PlayerInfo) this.playerInfos.elementAt(i);
            if (info.clanID == clanID) {
                info.clanIcon = icon;
            }
        }

    }

    public void initItemCurrent() {
        for (int i = 0; i < this.itemCur.length; ++i) {
            this.itemCur[i] = -2;
        }

        this.itemCur[0] = 0;
        this.itemCur[1] = 0;
        this.itemCur[2] = 1;
        this.itemCur[3] = 1;
    }

    public static int[] getStrainingItem() {
        int[] item = new int[numCurItemSlot];
        item[0] = 0;
        item[1] = 0;
        item[2] = 0;
        item[3] = 0;
        item[4] = 0;
        item[5] = 0;
        return item;
    }

    public int[] copyItemCurrent() {
        int len = this.itemCur.length;
        int[] returnItem = new int[len];

        for (int i = 0; i < len; ++i) {
            returnItem[i] = this.itemCur[i];
        }

        return returnItem;
    }

    public int getIDBySeat(int i) {
        return ((PlayerInfo) this.playerInfos.elementAt(i)).IDDB;
    }

    public void setAt(int seat, PlayerInfo joinPersonInfo) {
        this.playerInfos.setElementAt(joinPersonInfo, seat);
    }

    public void doViewMessage() {
        Vector<Command> menu = new Vector();

        for (int i = 0; i < this.playerInfos.size(); ++i) {
            final PlayerInfo p = (PlayerInfo) this.playerInfos.elementAt(i);
            if (p.IDDB != TerrainMidlet.myInfo.IDDB && p.IDDB != -1) {
                menu.addElement(new Command(p.name, new IAction() {
                    public void perform() {
                        GameService.gI().requestInfoOf(p.IDDB);
                        CCanvas.startWaitDlg(Language.pleaseWait());
                    }
                }));
            }
        }

        CCanvas.menu.startAt(menu, 0);
    }

    public void initPosPlayers() {
        this.x_pos = new int[numPlayer];
        this.y_pos = new int[numPlayer];
        this.x_pos[0] = 24;
        this.y_pos[0] = CCanvas.hieght - 128;
        this.x_pos[1] = CCanvas.width - 24;
        this.y_pos[1] = CCanvas.hieght - 128;
        this.x_pos[2] = 62;
        this.y_pos[2] = CCanvas.hieght - 80;
        this.x_pos[3] = CCanvas.width - 62;
        this.y_pos[3] = CCanvas.hieght - 80;
        this.x_pos[4] = 62;
        this.y_pos[4] = CCanvas.hieght - 128;
        this.x_pos[5] = CCanvas.width - 62;
        this.y_pos[5] = CCanvas.hieght - 128;
        this.x_pos[6] = 24;
        this.y_pos[6] = CCanvas.hieght - 80;
        this.x_pos[7] = CCanvas.width - 24;
        this.y_pos[7] = CCanvas.hieght - 80;
    }

    public PrepareScr() {
        for (int i = 0; i < 8; i++) {
            this.playerInfos.add(new PlayerInfo());
        }
        this.itemCur = new int[numCurItemSlot];
        this.isChooseItem = false;
        this.isChooseGun = false;
        this.curUsing = 0;
        this.x_item = 0;
        this.y_item = 0;
        this.y_item_touch = 0;
        this.x_item_touch = 0;
        this.chatDelay = 0;
        this.xPaintMap = CCanvas.hw;
        this.yPaintMap = 36;
        this.anchorPainMap = 3;
        this.initPosPlayers();
        this.initItemCurrent();
        int dis = CCanvas.isTouch ? 25 : 0;
        this.x_item = CCanvas.width - (Item.iWitdh * 18 + 5);
        this.y_item = CCanvas.hieght - (cmdH + 42) + 5;
        this.x_item_touch = CCanvas.width / 2 - 80 + 12;
        this.y_item_touch = CCanvas.hieght / 2 - 40;
        switch (CCanvas.width) {
            case 176:
                this.x_player = 10;
                this.y_player = 40;
                this.height = 30;
                this.y_Map = 15;
                this.x_Name = 3;
                this.y_owner = 2;
                break;
            case 240:
                this.x_player = 30;
                this.y_player = 60;
                this.height = 45;
                this.y_Map = 20;
                this.x_Name = 10;
                this.y_owner = 8;
                break;
            case 320:
                this.x_player = 30;
                this.y_player = 40;
                this.height = 33;
                this.y_Map = 20;
                this.x_Name = 10;
                this.y_owner = 2;
        }

        tfChat = new TField();
        tfChat.x = 2;
        tfChat.y = CCanvas.hieght - ITEM_HEIGHT - 25;
        if (CCanvas.isTouch) {
            tfChat.y = CCanvas.hieght - CScreen.cmdH - ITEM_HEIGHT;
        }

        tfChat.width = CCanvas.width - 4;
        tfChat.height = ITEM_HEIGHT + 2;
        tfChat.setisFocus(true);
        this.cmdMenu = new Command("Menu", new IAction() {
            public void perform() {
                PrepareScr.this.doShowMenuPrepare();
            }
        });
        this.cmdChangeTeam = new Command(Language.changeTeam(), new IAction() {
            public void perform() {
                PrepareScr.this.doChangeTeam();
            }
        });
        this.cmdAddFriend = new Command(Language.makeFriend(), new IAction() {
            public void perform() {
                PrepareScr.this.doAddFriend();
            }
        });
        this.findRoom = new Command(Language.findArea(), new IAction() {
            public void perform() {
                PrepareScr.this.doFindRoom();
            }
        });
        this.cmdMap = new Command(Language.selectMap(), new IAction() {
            public void perform() {
                PrepareScr.this.doSelectMap();
            }
        });
        this.cmdInvite = new Command(Language.findFriend(), new IAction() {
            public void perform() {
                PrepareScr.this.doInvite();
            }
        });
        this.cmdReady = new Command(Language.ready(), new IAction() {
            public void perform() {
                PrepareScr.this.doReady();
            }
        });
        this.cmdStart = new Command(Language.begin(), new IAction() {
            public void perform() {
                PrepareScr.this.doStartGame();
            }
        });
        this.cmdExit = new Command(Language.leaveBattle(), new IAction() {
            public void perform() {
                PlayerInfo curP = (PlayerInfo) PrepareScr.this.playerInfos.elementAt(PrepareScr.this.indexOfMe);
                if (curP.isReady && curP.IDDB != PrepareScr.this.ownerID) {
                    CCanvas.startOKDlg(Language.cannotLeave());
                } else {
                    PrepareScr.this.doLeaveBoard();
                }
            }
        });
        this.cmdBack = new Command(Language.back(), new IAction() {
            public void perform() {
                PrepareScr.this.isTouchItem = false;
                PrepareScr.this.isChooseItem = false;
            }
        });
        this.cmdBanBe = new Command(Language.friends(), new IAction() {
            public void perform() {
                PrepareScr.this.doShowFriend();
            }
        });
        this.cmdTinNhan = new Command(Language.message(), new IAction() {
            public void perform() {
                PrepareScr.this.doViewTinNhan();
            }
        });
        this.cmdSelect = new Command(Language.select(), new IAction() {
            public void perform() {
                PrepareScr.this.doChooseItemEquip();
            }
        });
        this.cmdLeft = new Command("Left", new IAction() {
            public void perform() {
                if (PrepareScr.curMap > 0) {
                    --PrepareScr.curMap;
                } else {
                    PrepareScr.curMap = (byte) (PrepareScr.this.numMap - 1);
                }

            }
        });
        this.cmdRight = new Command(Language.back(), new IAction() {
            public void perform() {
                if (PrepareScr.this.isChooseItem) {
                    PrepareScr.this.isChooseItem = false;
                } else if (PrepareScr.this.isChooseGun) {
                    PrepareScr.this.isChooseGun = false;
                } else if (PrepareScr.curMap < PrepareScr.this.numMap - 1) {
                    ++PrepareScr.curMap;
                } else {
                    PrepareScr.curMap = 0;
                }

            }
        });
        this.left = this.cmdMenu;
        this.right = this.cmdExit;
        this.center = null;
    }

    private void doChooseItemEquip() {
        CRes.err("========================= doChooseItemEquip!!!! " + this.curUsing);
        if (this.curUsing == 0) {
            if (this.selectedItem >= this.itemCur.length) {
                return;
            }

            if (this.itemCur[this.selectedItem] == -1) {
                CCanvas.startOKDlg(Language.buyMoreBag());
            } else {
                ShopItem.checkItemWhenChose(this.itemCur);
                Item curItemC = ShopItem.getI(prepareScrItemIcon.select);
                if (this.isChooseItem && !curItemC.isPassive_Item) {
                    if (curItemC.num <= 0 && !curItemC.isFreeItem) {
                        CCanvas.startOKDlg(Language.empty());
                    } else if (curItemC.numUsed < curItemC.nCurMaxUsed) {
                        this.itemCur[this.selectedItem] = curItemC.type;
                        this.isChooseItem = false;
                        this.showTipItem();
                    } else {
                        CCanvas.startOKDlg(Language.chicothe() + curItemC.nCurMaxUsed + Language.itemnay());
                    }
                } else {
                    this.isChooseItem = true;
                }
            }
        } else if (this.curUsing == 1) {
            if (this.isChooseGun) {
                CCanvas.startWaitDlg(Language.pleaseWait());
                this.isChooseGun = false;
                if (this.selectedGunLv2 == 3 && ShopItem.getI(11).num == 0) {
                    return;
                }

                GameService.gI().changeGun((byte) this.selectedGunLv2);
            } else {
                this.isChooseGun = true;
                this.showTipChooseWeapon(this.x_item - 45, this.y_item - 22);
            }
        }

    }

    private void doFindRoom() {
        CCanvas.inputDlg.setInfo(Language.nhapSoPhong(), new IAction() {
            public void perform() {
                if (CCanvas.inputDlg.tfInput.getText() != null && !CCanvas.inputDlg.tfInput.getText().equals("")) {
                    if (!CCanvas.inputDlg.tfInput.isNotNumber()) {
                        PrepareScr.roomID = Integer.parseInt(CCanvas.inputDlg.tfInput.getText());
                        CCanvas.inputDlg.setInfo(Language.nhapKhuVuc(), new IAction() {
                            public void perform() {
                                if (CCanvas.inputDlg.tfInput.getText() != null && !CCanvas.inputDlg.tfInput.getText().equals("")) {
                                    PrepareScr.zoneID = Integer.parseInt(CCanvas.inputDlg.tfInput.getText());
                                    GameService.gI().requestEmptyRoom((byte) 2, (byte) -1, String.valueOf(PrepareScr.roomID * 1000 + PrepareScr.zoneID));
                                    CCanvas.endDlg();
                                    CCanvas.startOKDlg(Language.pleaseWait());
                                }
                            }
                        }, new IAction() {
                            public void perform() {
                                CCanvas.endDlg();
                            }
                        }, 1);
                    }
                }
            }
        }, new IAction() {
            public void perform() {
                CCanvas.endDlg();
            }
        }, 1);
        CCanvas.inputDlg.show();
    }

    private void doShowFriend() {
        GameService.gI().requestFriendList();
        CCanvas.startWaitDlg(Language.pleaseWait());
    }

    private void doViewTinNhan() {
        CCanvas.msgScr.show(this);
    }

    protected void doChangeTeam() {
        GameService.gI().changeTeam();
        CCanvas.startWaitDlg(Language.pleaseWait());
    }

    public void onChangeTeam(int userID, byte newSeat) {
        int oldSeat = this.getNumberByID(userID);
        this.playerInfos.setElementAt((PlayerInfo) this.playerInfos.elementAt(oldSeat), newSeat);
        this.playerInfos.setElementAt(new PlayerInfo(), oldSeat);
        if (userID == TerrainMidlet.myInfo.IDDB) {
            this.indexOfMe = newSeat;
        }

    }

    protected void doAddFriend() {
        Vector<Command> menu = new Vector();

        for (int i = 0; i < this.playerInfos.size(); ++i) {
            final PlayerInfo p = (PlayerInfo) this.playerInfos.elementAt(i);
            if (p.IDDB != TerrainMidlet.myInfo.IDDB && p.IDDB != -1) {
                menu.addElement(new Command(p.name, new IAction() {
                    public void perform() {
                        GameService.gI().addFriend(p.IDDB);
                        CCanvas.startWaitDlg(Language.adding() + " " + p.name);
                    }
                }));
            }
        }

        CCanvas.menu.startAt(menu, 0);
    }

    protected void doKick() {
        Vector<Command> menu = new Vector();

        for (int i = 0; i < this.playerInfos.size(); ++i) {
            final PlayerInfo p = (PlayerInfo) this.playerInfos.elementAt(i);
            if (p.IDDB != TerrainMidlet.myInfo.IDDB && p.IDDB != -1) {
                menu.addElement(new Command(p.name, new IAction() {
                    public void perform() {
                        if (p.isReady) {
                            CCanvas.startOKDlg(Language.cannotKick());
                        } else {
                            GameService.gI().kick(p.IDDB);
                            p.IDDB = -1;
                            p.name = "";
                        }
                    }
                }));
            }
        }

        CCanvas.menu.startAt(menu, 0);
    }

    protected void doStartGame() {
        int readyCount = 0;
        int unReadyCount = 0;
        int team1Count = 0;
        int team2Count = 0;

        int i;
        for (i = 0; i < this.playerInfos.size(); ++i) {
            PlayerInfo p = (PlayerInfo) this.playerInfos.elementAt(i);
            if (p.IDDB != TerrainMidlet.myInfo.IDDB && p.IDDB != -1) {
                if (p.isReady) {
                    ++readyCount;
                } else {
                    ++unReadyCount;
                }
            }

            if (p.IDDB != -1) {
                if (i % 2 == 0) {
                    ++team1Count;
                } else {
                    ++team2Count;
                }
            }
        }

        if (readyCount != 0 && unReadyCount <= 0) {
            if (this.money > TerrainMidlet.myInfo.xu) {
                CCanvas.startOKDlg(Language.notEnoughMoney());
            }

            CCanvas.startWaitDlg(Language.loading());
            if (this.itemCur != null && this.itemCur.length > 0) {
                for (i = 0; i < this.itemCur.length; ++i) {
                    if (i == this.itemCur.length - 1 && ShopItem.getI(15).num <= 0) {
                        this.itemCur[i] = -1;
                    }

                    if (i == this.itemCur.length - 2 && ShopItem.getI(14).num <= 0) {
                        this.itemCur[i] = -1;
                    }

                    if (i == this.itemCur.length - 3 && ShopItem.getI(13).num <= 0) {
                        this.itemCur[i] = -1;
                    }

                    if (i == this.itemCur.length - 4 && ShopItem.getI(12).num <= 0) {
                        this.itemCur[i] = -1;
                    }
                }
            }

            GameService.gI().changeItem(this.itemCur);
            GameService.gI().startGame();
        } else {
            CCanvas.startOKDlg(Language.notReady());
        }
    }

    protected void doChat() {
        if (this.chatDelay == 0) {
            String text = tfChat.getText();
            if (!text.trim().equals("")) {
                tfChat.setText("");
                GameService.gI().chatToBoard(text);
                this.showChat(TerrainMidlet.myInfo.IDDB, text, 90);
                this.chatDelay = 30;
            }
        }
    }

    private void doReady() {
        if (this.readyDelay == 0) {
            boolean newReadyStat = !((PlayerInfo) this.playerInfos.elementAt(this.indexOfMe)).isReady;
            if (newReadyStat) {
                if (TerrainMidlet.myInfo.xu < this.money) {
                    CCanvas.startOKDlg(Language.cannotReady());
                    return;
                }

                this.readyDelay = 30;
            }

            CCanvas.startWaitDlg(Language.areReady());
            if (newReadyStat) {
                if (this.itemCur != null && this.itemCur.length > 0) {
                    for (int i = 0; i < this.itemCur.length; ++i) {
                        if (i == this.itemCur.length - 1 && ShopItem.getI(15).num <= 0) {
                            this.itemCur[i] = -1;
                        }

                        if (i == this.itemCur.length - 2 && ShopItem.getI(14).num <= 0) {
                            this.itemCur[i] = -1;
                        }

                        if (i == this.itemCur.length - 3 && ShopItem.getI(13).num <= 0) {
                            this.itemCur[i] = -1;
                        }

                        if (i == this.itemCur.length - 4 && ShopItem.getI(12).num <= 0) {
                            this.itemCur[i] = -1;
                        }
                    }
                }

                GameService.gI().changeItem(this.itemCur);
            }

            GameService.gI().ready(newReadyStat);
            this.resetItemEquip();
        }
    }

    protected void doSelectMap() {
        if (currLevel == 5) {
            CCanvas.roomListScr.isBoss = true;
        } else {
            CCanvas.roomListScr.isBoss = false;
        }

        CCanvas.roomListScr.show();
    }

    private void doInvite() {
        GameService.gI().inviteFriend(true, -1);
        CCanvas.startOKDlg(Language.pleaseWait());
    }

    protected void doSettingMoney() {
        CCanvas.inputDlg.setInfo(Language.totalstakes(), new IAction() {
            public void perform() {
                try {
                    int money = Integer.parseInt(CCanvas.inputDlg.tfInput.getText());
                    if (money < 0) {
                        return;
                    }

                    CCanvas.endDlg();
                    if (money > TerrainMidlet.myInfo.xu) {
                        CCanvas.startOKDlg(Language.onlyHave() + TerrainMidlet.myInfo.xu);
                        return;
                    }

                    GameService.gI().setMoney(money);

                    for (int i = 0; i < PrepareScr.this.playerInfos.size(); ++i) {
                        ((PlayerInfo) PrepareScr.this.playerInfos.elementAt(i)).isReady = false;
                    }
                } catch (Exception var3) {
                }

            }
        }, new IAction() {
            public void perform() {
                CCanvas.endDlg();
            }
        }, 1);
        CCanvas.inputDlg.show();
    }

    private void doSettingPassword() {
        CCanvas.inputDlg.setInfo(Language.setPass(), new IAction() {
            public void perform() {
                GameService.gI().setPassword(CCanvas.inputDlg.tfInput.getText());
                CCanvas.startOKDlg(Language.setPassed());
            }
        }, new IAction() {
            public void perform() {
                CCanvas.endDlg();
            }
        }, 3);
        CCanvas.inputDlg.show();
    }

    private void doSettingBoardName() {
        CCanvas.inputDlg.setInfo(Language.setBoardName(), new IAction() {
            public void perform() {
                if (!CCanvas.inputDlg.tfInput.getText().equals("")) {
                    String name = CCanvas.inputDlg.tfInput.getText();
                    GameService.gI().setBoardName(name);
                    BoardListScr.boardName = name;
                    CCanvas.startOKDlg(Language.setBoardNamefinish());
                }
            }
        }, new IAction() {
            public void perform() {
                CCanvas.endDlg();
            }
        }, 0);
        CCanvas.inputDlg.show();
    }

    protected void doLeaveBoard() {
        GameService.gI().leaveBoard();
        if (BoardListScr.boardList == null) {
            CCanvas.menuScr.show();
        } else {
            CCanvas.boardListScr.show();
        }

    }

    protected void doSetMaxPlayer() {
        Vector<Command> menu = new Vector();
        menu.addElement(new Command("4 vs 4", new IAction() {
            public void perform() {
                GameService.gI().setMaxPlayer(8);
            }
        }));
        menu.addElement(new Command("3 vs 3", new IAction() {
            public void perform() {
                GameService.gI().setMaxPlayer(6);
            }
        }));
        menu.addElement(new Command("2 vs 2", new IAction() {
            public void perform() {
                GameService.gI().setMaxPlayer(4);
            }
        }));
        menu.addElement(new Command("1 vs 1", new IAction() {
            public void perform() {
                GameService.gI().setMaxPlayer(2);
            }
        }));
        CCanvas.menu.startAt(menu, 0);
    }

    public void doShowMenuPrepare() {
        Vector<Command> menu = new Vector();
        menu.addElement(this.findRoom);
        if (this.ownerID == TerrainMidlet.myInfo.IDDB) {
            menu.addElement(this.cmdMap);
            menu.addElement(this.cmdInvite);
        }

        Command cmdConfig = new Command(Language.setting(), new IAction() {
            public void perform() {
                Vector<Command> menu = new Vector();
                menu.addElement(new Command(Language.setMoney(), new IAction() {
                    public void perform() {
                        PrepareScr.this.doSettingMoney();
                    }
                }));
                menu.addElement(new Command(Language.setPass(), new IAction() {
                    public void perform() {
                        PrepareScr.this.doSettingPassword();
                    }
                }));
                menu.addElement(new Command(Language.setPerson(), new IAction() {
                    public void perform() {
                        PrepareScr.this.doSetMaxPlayer();
                    }
                }));
                menu.addElement(new Command(Language.setBoardName(), new IAction() {
                    public void perform() {
                        PrepareScr.this.doSettingBoardName();
                    }
                }));
                CCanvas.menu.startAt(menu, 0);
            }
        });
        Command cmdKick = new Command("Kick..", new IAction() {
            public void perform() {
                PrepareScr.this.doKick();
            }
        });
        if (!((PlayerInfo) this.playerInfos.elementAt(this.indexOfMe)).isReady || this.ownerID == TerrainMidlet.myInfo.IDDB) {
            menu.addElement(this.cmdChangeTeam);
        }

        if (this.ownerID == TerrainMidlet.myInfo.IDDB) {
            menu.addElement(cmdConfig);
        }

        boolean canMakeFriend = false;

        for (int i = 0; i < this.playerInfos.size(); ++i) {
            PlayerInfo p = (PlayerInfo) this.playerInfos.elementAt(i);
            if (p.IDDB != TerrainMidlet.myInfo.IDDB && p.IDDB != -1) {
                canMakeFriend = true;
                break;
            }
        }

        if (canMakeFriend) {
            menu.addElement(this.cmdAddFriend);
        }

        if (canMakeFriend && this.ownerID == TerrainMidlet.myInfo.IDDB) {
            menu.addElement(cmdKick);
        }

        if (canMakeFriend) {
            Command cmdShowMessage = new Command(Language.viewScore(), new IAction() {
                public void perform() {
                    PrepareScr.this.doViewMessage();
                }
            });
            menu.addElement(cmdShowMessage);
        }

        menu.addElement(this.cmdBanBe);
        menu.addElement(this.cmdTinNhan);
        menu.addElement(this.cmdExit);
        CCanvas.menu.startAt(menu, 0);
    }

    public void onMapChanged(byte map) {
        CCanvas.endDlg();
        curMap = map;
    }

    public void onSomeOneChangeGun(int userID, byte gunID) {
        this.getPlayerFromID(userID).gun = gunID;
        this.showChat(userID, "Doi sung\n" + GUN_NAME[gunID] + " !", 40);
    }

    public int getNumberByID(int ID) {
        for (int i = 0; i < this.playerInfos.size(); ++i) {
            PlayerInfo p = (PlayerInfo) this.playerInfos.elementAt(i);
            if (p.IDDB == ID) {
                return i;
            }
        }

        return -1;
    }

    private void showTipWeapon() {
        byte myGun = ((PlayerInfo) this.playerInfos.elementAt(this.indexOfMe)).gun;
    }

    private void showTipChooseWeapon(int x, int y) {
    }

    private void resetItemEquip() {
        this.itemCur = ShopItem.checkSetItem(this.itemCur);
    }

    private void showTipItem() {
    }

    public void update() {
        if (this.readyDelay > 0) {
            --this.readyDelay;
        }

        if (this.chatDelay > 0) {
            --this.chatDelay;
        }

        if (!this.isChooseItem) {
            this.left = this.cmdMenu;
            if (this.ownerID == TerrainMidlet.myInfo.IDDB) {
                this.right = this.cmdStart;
                this.cmdStart.caption = Language.begin();
            } else {
                if (currLevel != 7) {
                    this.right = this.cmdReady;
                } else {
                    this.right = this.cmdStart;
                }

                this.cmdReady.caption = Language.ready();

                for (int i = 0; i < this.playerInfos.size(); ++i) {
                    PlayerInfo p = (PlayerInfo) this.playerInfos.elementAt(i);
                    if (p.IDDB == TerrainMidlet.myInfo.IDDB && p.isReady) {
                        this.cmdReady.caption = Language.noReady();
                        this.center = null;
                        this.right = this.cmdReady;
                    }
                }
            }
        } else {
            this.right = this.cmdBack;
        }

        this.doChat();
        tfChat.update();
        if (this.isChooseItem) {
            prepareScrItemIcon.update();
        }

        Cloud.updateCloud();
    }

    public void mainLoop() {
        super.mainLoop();
        if (this.isChooseItem) {
            prepareScrItemIcon.mainLoop();
        }

    }

    public void paint(mGraphics g) {
        paintDefaultBg(g);
        this.paintBack(g);
        Cloud.paintCloud(g);
        if (!GameScr.trainingMode) {
            this.paintPlayerPos(g);
            this.paintPlayer(g);
            this.paintMap(g);
            this.paintItem(g, this.x_item, this.y_item);
            if (this.isChooseItem) {
                this.paintChooseItem(CScreen.w - prepareScrItemIcon.shopW >> 1, this.y_item - 102, g);
            }

            g.translate(-g.getTranslateX(), -g.getTranslateY());
            if (CCanvas.isTouch) {
                g.drawImage(iconChat, 30, 10, 0, false);
            }

            super.paint(g);
        }
    }

    private void paintPlayerPos(mGraphics g) {
        if (CCanvas.isTouch) {
            g.translate(0, -15);
            g.translate(0, -5);
        }

        g.setColor(12965614);
        g.fillRect(0, CCanvas.hieght - 96, CCanvas.width, 45, false);

        int i;
        for (i = 0; i < CCanvas.width / line.image.getWidth(); ++i) {
            g.drawRegion(line, 0, 16, 33, 8, 0, (i + 1) * 33, CCanvas.hieght - 96, 0, false);
        }

        g.fillRect(0, CCanvas.hieght - 68, CCanvas.width, 68, false);
        g.fillRect(0, CCanvas.hieght - 96 - 12, 75, 45, false);
        g.fillRect(CCanvas.width - 75, CCanvas.hieght - 96 - 12, 75, 45, false);
        g.setColor(6985149);
        g.drawRect(-1, CCanvas.hieght - 96 - 12, 76, 45, false);
        g.drawRect(CCanvas.width - 76, CCanvas.hieght - 96 - 12, 76, 45, false);
        g.setColor(9289);
        g.drawLine(76, CCanvas.hieght - 96 - 12, 76, CCanvas.hieght - 96 + 33, false);
        g.drawLine(CCanvas.width - 75, CCanvas.hieght - 96 - 12, CCanvas.width - 75, CCanvas.hieght - 96 + 33, false);

        for (i = 0; i < CCanvas.width; ++i) {
            g.drawRegion(line, 0, 16, 33, 8, 0, i * 33, CCanvas.hieght - 68, 0, false);
        }

        g.setColor(6985149);
        g.drawLine(0, CCanvas.hieght - 60, CCanvas.width, CCanvas.hieght - 60, false);

        for (i = 0; i < 2; ++i) {
            g.drawRegion(line, 0, 16, 33, 8, 0, i * 33, CCanvas.hieght - 96 - 20, 0, false);
            g.drawRegion(line, 0, 16, 33, 8, 0, CCanvas.width - i * 33, CCanvas.hieght - 96 - 20, 24, false);
        }

        g.drawRegion(line, 0, 0, 33, 8, 0, 66, CCanvas.hieght - 96 - 20, 0, false);
        g.drawRegion(line, 0, 8, 33, 8, 0, CCanvas.width - 66, CCanvas.hieght - 96 - 20, 24, false);
        if (CCanvas.hieght >= 300) {
            g.drawRegion(line, 0, 16, 33, 8, 0, CCanvas.width / 2, CCanvas.hieght - 96, mGraphics.TOP | mGraphics.HCENTER, false);
            g.drawRegion(line, 0, 0, 33, 8, 0, CCanvas.width / 2 + 16, CCanvas.hieght - 96, 0, false);
            g.drawRegion(line, 0, 8, 33, 8, 0, CCanvas.width / 2 - 16, CCanvas.hieght - 96, 24, false);
            g.setColor(12965614);
            g.fillRect(CCanvas.width / 2 - 30, CCanvas.width - 96 + 8, 60, 20, false);
            g.setColor(6985149);
            g.drawRect(CCanvas.width / 2 - 29, CCanvas.width - 96 + 7, 58, 21, false);
            g.setColor(9289);
            g.drawRect(CCanvas.width / 2 - 30, CCanvas.width - 96 + 7, 60, 21, false);
        }

        g.translate(0, -g.getTranslateY());
    }

    private void paintMap(mGraphics g) {
        boolean isPaint = false;
        if (CCanvas.hieght < 300) {
            if (CCanvas.width <= 300) {
                Font.borderFont.drawString(g, Language.room() + ": " + currentRoom, CCanvas.hw, 10, 2);
                Font.borderFont.drawString(g, BoardListScr.boardName, CCanvas.hw, 25, 2);
                Font.borderFont.drawString(g, curMap + 1 + ". " + MM.mapName[curMap], CCanvas.hw, 40, 2);
                isPaint = false;
            } else {
                isPaint = true;
                g.setColor(16777215);
                g.fillRect(CCanvas.hw - 88, 13, 76, 46, false);
                Font.borderFont.drawString(g, Language.room() + ": " + currentRoom, CCanvas.hw, 12, 0);
                Font.borderFont.drawString(g, BoardListScr.boardName, CCanvas.hw, 27, 0);
                Font.borderFont.drawString(g, curMap + 1 + ". " + MM.mapName[curMap], CCanvas.hw, 42, 0);
                this.xPaintMap = CCanvas.hw - 50;
                this.yPaintMap = 36;
                this.anchorPainMap = 3;
                if (this.money != 0) {
                    Font.borderFont.drawString(g, this.money + Language.xu(), CCanvas.width / 2, 65, 3);
                }
            }

            if (CCanvas.width < 200 && this.money != 0) {
                Font.borderFont.drawString(g, this.money + Language.xu(), CCanvas.width / 2, 60, 3);
            }
        } else {
            if (this.money != 0) {
                Font.borderFont.drawString(g, this.money + Language.xu(), CCanvas.width / 2, 120, 3);
            }

            isPaint = true;
            this.xPaintMap = CCanvas.hw;
            this.yPaintMap = 36;
            this.anchorPainMap = 3;
            g.setColor(16777215);
            g.fillRect(this.xPaintMap - 38, this.yPaintMap - 26 + 1, 76, this.yPaintMap + 10, false);
            Font.borderFont.drawString(g, Language.room() + ": " + currentRoom, this.xPaintMap, 85, 2);
            Font.borderFont.drawString(g, BoardListScr.boardName, this.xPaintMap, 100, 2);
            Font.borderFont.drawString(g, curMap + 1 + ". " + MM.mapName[curMap], this.xPaintMap, 70, 2);
        }

        if (isPaint) {
            try {
                if (imgMap[curMap] != null) {
                    g.drawImage(imgMap[curMap], this.xPaintMap, this.yPaintMap, mGraphics.VCENTER | mGraphics.HCENTER, false);
                }
            } catch (Exception var6) {
                g.drawImage(randomMap, this.xPaintMap, this.yPaintMap, mGraphics.VCENTER | mGraphics.HCENTER, false);
            }

            g.drawImage(khungMap, this.xPaintMap, this.yPaintMap, 3, false);
        }

        PlayerInfo m = TerrainMidlet.myInfo;
        String money = CCanvas.width >= 200 ? m.getStrMoney() : m.getStrMoney2();
        Font.normalFont.drawString(g, "Lvl " + m.level2 + "  " + money, 6, CCanvas.hieght - cmdH - 35, 0, false);
        Font.normalFont.drawString(g, "Exp ", 6, CCanvas.hieght - cmdH - 19, 0, false);
        LevelScreen.paintLevelPercen(g, 5, CCanvas.hieght - cmdH - 20);
        if (CCanvas.width > 200) {
            int xCup = 105 + (this.x_item - 105) / 2;
            g.drawImage(CScreen.cup, xCup, CCanvas.hieght - cmdH - 26, 3, false);
            Font.borderFont.drawString(g, m.cup + " ", xCup, CCanvas.hieght - cmdH - 17, 3);
        }

    }

    public void paintBack(mGraphics g) {
        for (int i = 0; i < CCanvas.width; i += 32) {
            g.drawImage(imgBack, i, CCanvas.hieght - 96 - 15, 0, false);
        }

    }

    public static void paintQuanHam(int quanHamType, int x, int y, int anchorpoint, mGraphics g) {
        try {
            g.drawRegion(imgQuanHam, 0, 12 * quanHamType, 12, 12, 0, x, y, anchorpoint, false);
        } catch (Exception var6) {
        }

    }

    public void paintPlayer(mGraphics g) {
        g.translate(0, -15);
        if (this.playerInfos != null) {
            if (!GameScr.trainingMode) {
                int i;
                PlayerInfo p;
                boolean a;
                if (currLevel != 7) {
                    for (i = 0; i < this.playerInfos.size(); ++i) {
                        p = (PlayerInfo) this.playerInfos.elementAt(i);
                        if (p.IDDB != -1) {
                            a = i % 2 != 0;
                            CPlayer.paintSimplePlayer(p.gun, 5, this.x_pos[i] + (!a ? 2 : -2), this.y_pos[i] + (CCanvas.isTouch ? 8 : 13), !a ? 2 : 0, p.myEquip, g);
                            if (p.clanIcon != null) {
                                g.drawImage(p.clanIcon, this.x_pos[i] - 18, this.y_pos[i] - (cmdH - 4), 0, false);
                            }

                            paintQuanHam(p.nQuanHam2, this.x_pos[i], this.y_pos[i] - 15, mGraphics.VCENTER | mGraphics.HCENTER, g);
                            if (i % 2 == 0) {
                                Font.smallFontRed.drawString(g, p.name.toUpperCase(), this.x_pos[i] + 5, this.y_pos[i] + 15, 2);
                            } else {
                                Font.smallFontYellow.drawString(g, p.name.toUpperCase(), this.x_pos[i] + 5, this.y_pos[i] + 15, 2);
                            }

                            if (p.isReady && p.IDDB != this.ownerID) {
                                Font.borderFont.drawString(g, "ok", this.x_pos[i], this.y_pos[i] - 32, 2);
                            }

                            if (p.IDDB == this.ownerID) {
                                Font.borderFont.drawString(g, Language.boss(), this.x_pos[i], this.y_pos[i] - 32, 2);
                            }
                        }
                    }
                } else {
                    for (i = 0; i < this.playerInfos.size(); ++i) {
                        p = (PlayerInfo) this.playerInfos.elementAt(i);
                        if (p.IDDB != -1) {
                            a = i % 2 != 0;
                            CPlayer.paintSimplePlayer(p.gun, 5, this.x_pos[0] + (!a ? 2 : -2), this.y_pos[0] + (CCanvas.isTouch ? 8 : 13), !a ? 2 : 0, p.myEquip, g);
                            if (p.clanIcon != null) {
                                g.drawImage(p.clanIcon, this.x_pos[0] - 18, this.y_pos[0] - (cmdH - 4), 0, false);
                            }

                            paintQuanHam(p.nQuanHam2, this.x_pos[0], this.y_pos[0] - 15, mGraphics.VCENTER | mGraphics.HCENTER, g);
                            if (i % 2 == 0) {
                                Font.smallFontRed.drawString(g, p.name.toUpperCase(), this.x_pos[0] + 5, this.y_pos[0] + 15, 2);
                            } else {
                                Font.smallFontYellow.drawString(g, p.name.toUpperCase(), this.x_pos[0] + 5, this.y_pos[0] + 15, 2);
                            }

                            if (p.isReady && p.IDDB != this.ownerID) {
                                Font.borderFont.drawString(g, "ok", this.x_pos[0], this.y_pos[0] - 32, 2);
                            }

                            if (p.IDDB == this.ownerID) {
                                Font.borderFont.drawString(g, Language.boss(), this.x_pos[0], this.y_pos[0] - 32, 2);
                            }
                        }
                    }
                }

                if (CCanvas.hieght > 220 && currLevel == 5) {
                    i = mapBossID.length - (MM.NUM_MAP - curMap);
                    if (i >= 0) {
                        CPlayer.paintSimplePlayer(bossID[i], CCanvas.gameTick % 10 > 5 ? 0 : 1, CCanvas.width / 2, CCanvas.hieght - 95 - (CCanvas.isTouch ? 4 : 0), 2, (PlayerEquip) null, g);
                    }
                }

                g.translate(0, -g.getTranslateY());
            }
        }
    }

    public void paintChooseItem(int x, int y, mGraphics g) {
        int Y = (CCanvas.hieght - CScreen.cmdH) / 2 - 80;
        paintBorderRect(g, Y, CCanvas.isTouch ? 4 : 3, CCanvas.isTouch ? 138 : 118, Language.chonItem());
        prepareScrItemIcon.paint(x, Y + 25, g, true, ShopItem.getItemNum());
        prepareScrItemIcon.setPosTitle(CCanvas.width / 2, Y + 3);
        int var10003 = CCanvas.width / 2;
        int var10004 = Y + (CCanvas.isTouch ? 115 : 95);
        Font.normalFont.drawString(g, ShopItem.getI(prepareScrItemIcon.select).decription, var10003, var10004, 3);
    }

    public void paintChooseGun(int x, int y, mGraphics g) {
        for (int i = 0; i < 10; ++i) {
            if (i == this.selectedGunLv2) {
                g.setColor(CCanvas.gameTick % 10 > 5 ? 16776960 : 16711680);
                g.fillRect(x + i * 26 - 1, y - 1, 26, 18, false);
            }

            g.drawRegion(imgGun, 0, i * 16, 24, 16, 0, x + i * 26, y, 0, false);
        }

    }

    public void getIcon() {
        for (int i = 0; i < this.playerInfos.size(); ++i) {
            PlayerInfo info = (PlayerInfo) this.playerInfos.elementAt(i);
            GameService.gI().getClanIcon(info.clanID);
        }

    }

    public void show() {
        super.show();
        CPlayer.isShooting = false;
        if (CCanvas.gameScr != null) {
            CCanvas.gameScr.onClearMap();
            CCanvas.gameScr = null;
        }

        this.x_item = CCanvas.width - (Item.iWitdh * 18 + 5);
        this.y_item = CCanvas.hieght - (cmdH + 42) + 5;
        this.x_item_touch = CCanvas.width / 2 - 80 + 12;
        this.y_item_touch = CCanvas.hieght / 2 - 40;
        prepareScrItemIcon.setIAction(new IAction() {
            public void perform() {
                PrepareScr.this.doChooseItemEquip();
            }
        });
        this.resetItemEquip();
        this.resetReady();
        prepareScrItemIcon.titleTem = Language.chonItem();
    }

    public void onResetPrepare() {
        this.resetItemEquip();
    }

    public void paintItem(mGraphics g, int x, int y) {
        Item.DrawSetItem(g, this.itemCur, this.selectedItem, x, y, false, (byte[]) null);

        for (int i = 0; i < this.itemCur.length; ++i) {
            if (i == this.itemCur.length - 1) {
                this.paintPassiveItem_NumUse(ShopItem.getI(15).num, x + 54 + 9, y + 28, g);
            }

            if (i == this.itemCur.length - 2) {
                this.paintPassiveItem_NumUse(ShopItem.getI(14).num, x + 36 + 9, y + 28, g);
            }

            if (i == this.itemCur.length - 3) {
                this.paintPassiveItem_NumUse(ShopItem.getI(13).num, x + 18 + 9, y + 28, g);
            }

            if (i == this.itemCur.length - 4) {
                this.paintPassiveItem_NumUse(ShopItem.getI(12).num, x + 9, y + 28, g);
            }
        }

    }

    public void paintPassiveItem_NumUse(int num, int x, int y, mGraphics g) {
        Font.smallFontYellow.drawString(g, String.valueOf(num), x, y, 0);
    }

    public String getPlayerNameFromID(int id) {
        for (int i = 0; i < this.playerInfos.size(); ++i) {
            PlayerInfo p = (PlayerInfo) this.playerInfos.elementAt(i);
            if (p.IDDB == id) {
                return p.name;
            }
        }

        return "";
    }

    public PlayerInfo getPlayerFromID(int id) {
        for (int i = 0; i < this.playerInfos.size(); ++i) {
            PlayerInfo p = (PlayerInfo) this.playerInfos.elementAt(i);
            if (p.IDDB == id) {
                return p;
            }
        }

        return null;
    }

    public void setPlayers(int ownerID, int money, Vector playerInfos) {
        this.money = money;
        this.playerInfos = (Vector) playerInfos.clone();
        this.setOwner(ownerID);

        for (int i = 0; i < playerInfos.size(); ++i) {
            if (((PlayerInfo) playerInfos.elementAt(i)).IDDB == TerrainMidlet.myInfo.IDDB) {
                this.indexOfMe = i;
                break;
            }
        }

    }

    public void setOwner(int newOwner) {
        this.ownerID = newOwner;

        for (int i = 0; i < this.playerInfos.size(); ++i) {
            PlayerInfo p = (PlayerInfo) this.playerInfos.elementAt(i);
            if (p.IDDB == this.ownerID) {
                p.isReady = true;
            }
        }

    }

    public void showChat(int fromID, String text, int Interval) {
        int index = this.getNumberByID(fromID);
        ChatPopup cp = new ChatPopup();
        cp.show(Interval, this.x_pos[index], this.y_pos[index] - 30, text);
        CCanvas.arrPopups.addElement(cp);
    }

    public void stopGame() {
        this.isGameEnd = true;
    }

    public void playerLeave(int leaveID) {
        for (int i = 0; i < this.playerInfos.size(); ++i) {
            PlayerInfo p = (PlayerInfo) this.playerInfos.elementAt(i);
            if (p.IDDB == leaveID) {
                p.IDDB = -1;
                p.name = "";
                p.isReady = false;
            }
        }

    }

    public void resetReady() {
        for (int i = 0; i < this.playerInfos.size(); ++i) {
            PlayerInfo p = (PlayerInfo) this.playerInfos.elementAt(i);
            if (p.IDDB == this.ownerID) {
                p.isReady = true;
            } else {
                p.isReady = false;
            }
        }

    }

    public void setReady(int id, boolean isReady) {
        for (int i = 0; i < this.playerInfos.size(); ++i) {
            PlayerInfo p = (PlayerInfo) this.playerInfos.elementAt(i);
            if (p.IDDB == id) {
                p.isReady = isReady;
            }
        }

    }

    public void setMoney(int money) {
        this.money = money;

        for (int i = 0; i < this.playerInfos.size(); ++i) {
            ((PlayerInfo) this.playerInfos.elementAt(i)).isReady = false;
        }

    }

    public Vector<PlayerInfo> getPlayerInfos() {
        return this.playerInfos;
    }

    public void onPointerPressed(int xPress, int yPress, int index) {
        super.onPointerPressed(xPress, yPress, index);
    }

    public void onPointerReleased(int xReleased, int yReleased, int index) {
        super.onPointerReleased(xReleased, yReleased, index);
        if (this.isChooseItem) {
            prepareScrItemIcon.onPointerReleased(xReleased, yReleased, index);
        } else {
            if (this.anchorPainMap == 3) {
                this.xPaintMap -= 38;
                this.yPaintMap -= 23;
            }

            if (CCanvas.isPointer(this.xPaintMap, this.yPaintMap, 76, 46, index)) {
                if (TerrainMidlet.myInfo.IDDB == this.ownerID) {
                    this.doSelectMap();
                }

            } else if (CCanvas.isPointer(this.x_item - 10, this.y_item - 10, 300, 50, index)) {
                this.isTouchItem = true;
                if (CCanvas.isDoubleClick) {
                    this.isChooseItem = true;
                    this.center = null;
                    this.left = null;
                }

                if (CCanvas.isPointer(this.x_item, this.y_item, 300, 50, index)) {
                    int aa = (yReleased - this.y_item) / 20 * 4 + (xReleased - this.x_item) / 20;
                    if (this.selectedItem != aa) {
                        this.selectedItem = aa;
                    } else if (this.center != null && this.center.action != null && CCanvas.isDoubleClick) {
                        this.cmdSelect.action.perform();
                    }
                }

            } else {
                this.isTouchItem = false;
                if (CCanvas.isPointer(30, 0, 50, 50, index)) {
                    tfChat.doChangeToTextBox();
                }

            }
        }
    }

    public void onPointerDragged(int xDrag, int yDrag, int index) {
        super.onPointerDragged(xDrag, yDrag, index);
        if (this.isChooseItem) {
            prepareScrItemIcon.onPointerDragged(xDrag, yDrag, index);
        }
    }
}
