package network;

import CLib.RMS;
import CLib.mImage;
import CLib.mSystem;
import Equipment.Equip;
import Equipment.PlayerEquip;
import InApp.MainActivity;
import com.teamobi.mobiarmy2.GameMidlet;
import coreLG.CCanvas;
import coreLG.TerrainMidlet;
import effect.GiftEffect;
import item.BM;
import item.Bullet;
import item.Item;

import java.util.Vector;

import map.MM;
import model.BoardInfo;
import model.CRes;
import model.CTime;
import model.Clan;
import model.ClanItem;
import model.Fomula;
import model.Font;
import model.IAction;
import model.ImageIcon;
import model.Language;
import model.LuckyGift;
import model.MaterialIconMn;
import model.Mission;
import model.MoneyInfo;
import model.MsgInfo;
import model.PlayerInfo;
import model.RoomInfo;
import model.TimeBomb;
import player.CPlayer;
import player.PM;
import screen.BoardListScr;
import screen.CScreen;
import screen.ChangePlayerCSr;
import screen.GameScr;
import screen.LevelScreen;
import screen.LoginScr;
import screen.LuckyGame;
import screen.LuckyGifrScreen;
import screen.MenuScr;
import screen.MoneyScr;
import screen.MoneyScrIOS;
import screen.PrepareScr;
import screen.QuangCao;
import screen.RoomListScr2;
import shop.ShopBietDoi;
import shop.ShopEquipment;
import shop.ShopItem;

public class MessageHandler implements IMessageHandler {
    IGameLogicHandler gameLogicHandler;
    protected static MessageHandler instance;
    public static boolean nextTurnFlag = false;
    public static boolean lag = false;
    public static Object LOCK = new Object();
    public static int dem = 0;
    public static long currt;
    public static long timePing;

    public static MessageHandler gI() {
        if (instance == null) {
            instance = new MessageHandler();
        }

        return instance;
    }

    public void onConnectOK() {
        this.gameLogicHandler.onConnectOK();
    }

    public void onConnectionFail() {
        this.gameLogicHandler.onConnectFail();
    }

    public void onDisconnected() {
        this.gameLogicHandler.onDisconnect();
    }

    @Override
    public void onMessage(Message msg) {
        if (msg != null) {
            try {
                block7:
                switch (msg.command) {
                    case 4: {
                        this.gameLogicHandler.onLoginFail(msg.reader().readUTF());
                        break;
                    }
                    case 3: {
                        CRes.out("=========> Cmd_Server2Client.LOGIN_SUCESS:");
                        GameService.gI().platform_request();
                        GameService.gI().bangxephang((byte) -1, -1);
                        currt = System.currentTimeMillis();
                        GameService.gI().ping(dem, -1L);
                        TerrainMidlet.myInfo = new PlayerInfo();
                        TerrainMidlet.myInfo.name = LoginScr.user.toLowerCase();
                        if (GameMidlet.server == 2) {
                            //TerrainMidlet.myInfo.name = msg.reader().readUTF();
                        }
                        TerrainMidlet.myInfo.IDDB = msg.reader().readInt();
                        TerrainMidlet.myInfo.xu = msg.reader().readInt();
                        TerrainMidlet.myInfo.luong = msg.reader().readInt();
                        TerrainMidlet.myInfo.gun = msg.reader().readByte();
                        CRes.out(" TerrainMidlet.myInfo.gun " + TerrainMidlet.myInfo.gun);
                        TerrainMidlet.myInfo.clanID = msg.reader().readShort();
                        TerrainMidlet.myInfo.isMaster = msg.reader().readByte();
                        int i = 0;
                        while (i < 10) {
                            int j;
                            TerrainMidlet.isVip[i] = msg.reader().readBoolean();
                            if (!TerrainMidlet.isVip[i]) {
                                j = 0;
                                while (j < 5) {
                                    TerrainMidlet.myInfo.equipID[i][j] = msg.reader().readShort();
                                    ++j;
                                }
                            } else {
                                j = 0;
                                while (j < 5) {
                                    TerrainMidlet.myInfo.equipVipID[i][j] = msg.reader().readShort();
                                    ++j;
                                }
                                j = 0;
                                while (j < 5) {
                                    TerrainMidlet.myInfo.equipID[i][j] = msg.reader().readShort();
                                    ++j;
                                }
                            }
                            ++i;
                        }
                        Vector<Item> SellItem = new Vector<Item>();
                        int i2 = 0;
                        while (i2 < 36) {
                            byte numI = msg.reader().readByte();
                            int price = msg.reader().readInt();
                            int price2 = msg.reader().readInt();
                            SellItem.addElement(new Item((byte) i2, numI, price, price2));
                            ++i2;
                        }
                        ShopItem.setItemVector(SellItem);
                        int j = 0;
                        while (j < 10) {
                            if (j < 3) {
                                ChangePlayerCSr.isUnlock[j] = 1;
                                ChangePlayerCSr.gunXu[j] = 0;
                                ChangePlayerCSr.gunLuong[j] = 0;
                            } else {
                                ChangePlayerCSr.isUnlock[j] = msg.reader().readByte();
                                ChangePlayerCSr.gunXu[j] = msg.reader().readShort() * 1000;
                                ChangePlayerCSr.gunLuong[j] = msg.reader().readShort();
                            }
                            ++j;
                        }
                        MenuScr.suKienStr = msg.reader().readUTF().toUpperCase();
                        MenuScr.linkWapStr = msg.reader().readUTF().toUpperCase();
                        MenuScr.linkTeam = msg.reader().readUTF().toUpperCase();
                        MenuScr.getIdMenu(0);
                        MenuScr.menuString[MenuScr.MENU_SUKIEN] = MenuScr.suKienStr.toUpperCase().toUpperCase();
                        if (!LoginScr.isLoadData) {
                            CCanvas.sendMapData();
                            break;
                        }
                        GameService.gI().sendVersion((byte) 5, (byte) 0);
                        GameLogicHandler.gI().onLoginSuccess();
                        break;
                    }
                    case -28: {
                        Vector<RoomInfo> roomList2 = new Vector<RoomInfo>();
                        byte raction = msg.reader().readByte();
                        int rlevel = -1;
                        int subLevel = -1;
                        while (msg.reader().available() > 0) {
                            RoomInfo roomInfo = new RoomInfo();
                            roomInfo.id = msg.reader().readByte();
                            if (roomInfo.id != -1) {
                                roomInfo.boardID = msg.reader().readByte();
                                roomInfo.mapID = msg.reader().readByte();
                                roomInfo.playerMax = String.valueOf(msg.reader().readByte()) + "/" + msg.reader().readByte();
                                roomInfo.money = msg.reader().readInt();
                                String mapName = roomInfo.mapID == 100 ? Language.random() : MM.mapName[roomInfo.mapID];
                                roomInfo.name = "P" + roomInfo.id + "-" + roomInfo.boardID + " " + (CCanvas.width > 200 ? "(" + mapName + ")" : "");
                                roomInfo.lv = (byte) rlevel;
                                roomList2.addElement(roomInfo);
                                continue;
                            }
                            roomInfo.name = msg.reader().readUTF();
                            roomList2.addElement(roomInfo);
                            subLevel = (byte) (subLevel + 1);
                            rlevel = (byte) (rlevel + 1);
                            RoomInfo roomCreate = new RoomInfo();
                            roomCreate.name = Language.createZone();
                            roomCreate.lv = (byte) rlevel;
                            roomCreate.boardID = (byte) -1;
                            roomList2.addElement(roomCreate);
                        }
                        CCanvas.endDlg();
                        CCanvas.roomListScr2.isEmptyRoom = true;
                        CCanvas.roomListScr2.setRoomList(roomList2);
                        CCanvas.roomListScr2.show();
                        if (CCanvas.gameScr != null) {
                            CCanvas.gameScr.onClearMap();
                            CCanvas.gameScr = null;
                        }
                        break;
                    }
                    case 6: {
                        Vector<RoomInfo> roomList = new Vector<RoomInfo>();
                        while (msg.reader().available() > 0) {
                            byte a;
                            RoomInfo roomInfo = new RoomInfo();
                            roomInfo.id = msg.reader().readByte();
                            roomInfo.roomFree = a = msg.reader().readByte();
                            roomInfo.roomWait = msg.reader().readByte();
                            roomInfo.lv = msg.reader().readByte();
                            roomList.addElement(roomInfo);
                        }
                        CCanvas.endDlg();
                        if (CCanvas.roomListScr2 == null) {
                            CCanvas.roomListScr2 = new RoomListScr2();
                        }
                        CCanvas.roomListScr2.isEmptyRoom = false;
                        CCanvas.roomListScr2.setRoomList(roomList);
                        CCanvas.roomListScr2.show();
                        if (CCanvas.gameScr != null) {
                            CCanvas.gameScr.onClearMap();
                            CCanvas.gameScr = null;
                        }
                        GameService.gI().changeRoomName();
                        break;
                    }
                    case 7: {
                        if (CCanvas.curScr == CCanvas.prepareScr) {
                            return;
                        }
                        Vector<BoardInfo> boardList = new Vector<BoardInfo>();
                        byte roomID = msg.reader().readByte();
                        while (msg.reader().available() > 0) {
                            BoardInfo boardInfo = new BoardInfo();
                            boardInfo.boardID = msg.reader().readByte();
                            boardInfo.nPlayer = msg.reader().readByte();
                            boardInfo.maxPlayer = msg.reader().readByte();
                            boardInfo.isPass = msg.reader().readBoolean();
                            boardInfo.money = msg.reader().readInt();
                            boardInfo.isPlaying = msg.reader().readBoolean();
                            boardInfo.name = msg.reader().readUTF();
                            boardInfo.mode = msg.reader().readByte();
                            boardList.addElement(boardInfo);
                        }
                        CCanvas.endDlg();
                        if (CCanvas.boardListScr == null) {
                            CCanvas.boardListScr = new BoardListScr();
                        }
                        CCanvas.boardListScr.roomID = roomID;
                        CCanvas.boardListScr.setBoardList(boardList);
                        CCanvas.boardListScr.show();
                        if (CCanvas.gameScr != null) {
                            CCanvas.gameScr.onClearMap();
                            CCanvas.gameScr = null;
                        }
                        break;
                    }
                    case 8: {
                        int ownerID = msg.reader().readInt();
                        int money = msg.reader().readInt();
                        byte map = msg.reader().readByte();
                        byte gameMODE = msg.reader().readByte();
                        Vector<PlayerInfo> players = new Vector<PlayerInfo>();
                        while (msg.reader().available() > 0) {
                            PlayerInfo playerInfo = new PlayerInfo();
                            playerInfo.IDDB = msg.reader().readInt();
                            if (playerInfo.IDDB == -1) {
                                playerInfo.name = "";
                            } else {
                                playerInfo.clanID = msg.reader().readShort();
                                playerInfo.name = msg.reader().readUTF();
                                playerInfo.xu = msg.reader().readInt();
                                playerInfo.level2 = msg.reader().readUnsignedByte();
                                playerInfo.getQuanHam();
                                playerInfo.gun = msg.reader().readByte();
                                int i = 0;
                                while (i < 5) {
                                    playerInfo.equipID[playerInfo.gun][i] = msg.reader().readShort();
                                    playerInfo.getMyEquip(1);
                                    ++i;
                                }
                                playerInfo.isReady = msg.reader().readBoolean();
                            }
                            players.addElement(playerInfo);
                        }
                        CCanvas.endDlg();
                        GameScr.trainingMode = false;
                        CCanvas.prepareScr.setPlayers(ownerID, money, players);
                        int i = 0;
                        while (i < players.size()) {
                            PlayerInfo p = (PlayerInfo) players.elementAt(i);
                            if (p.IDDB == ownerID) {
                                p.isReady = true;
                            }
                            ++i;
                        }
                        if (CCanvas.prepareScr == null) {
                            CCanvas.prepareScr = new PrepareScr();
                        }
                        CCanvas.prepareScr.show();
                        CCanvas.prepareScr.onResetPrepare();
                        CCanvas.prepareScr.getIcon();
                        this.gameLogicHandler.onJoinGameSuccess(ownerID, money, players, map);
                        break;
                    }
                    case 12: {
                        PlayerInfo joinPersonInfo = new PlayerInfo();
                        byte seat = msg.reader().readByte();
                        joinPersonInfo.IDDB = msg.reader().readInt();
                        joinPersonInfo.clanID = msg.reader().readShort();
                        joinPersonInfo.name = msg.reader().readUTF();
                        joinPersonInfo.level2 = msg.reader().readUnsignedByte();
                        joinPersonInfo.getQuanHam();
                        joinPersonInfo.gun = msg.reader().readByte();
                        int i = 0;
                        while (i < 5) {
                            joinPersonInfo.equipID[joinPersonInfo.gun][i] = msg.reader().readShort();
                            joinPersonInfo.getMyEquip(2);
                            ++i;
                        }
                        joinPersonInfo.isReady = false;
                        CCanvas.prepareScr.setAt(seat, joinPersonInfo);
                        GameService.gI().getClanIcon(joinPersonInfo.clanID);
                        this.gameLogicHandler.onSomeOneJoinBoard(seat, joinPersonInfo);
                        break;
                    }
                    case 14: {
                        int IDLeave = msg.reader().readInt();
                        int IDNewOwner = msg.reader().readInt();
                        CCanvas.prepareScr.playerLeave(IDLeave);
                        CCanvas.prepareScr.setOwner(IDNewOwner);
                        if (CCanvas.curScr == CCanvas.gameScr) {
                            int i = 0;
                            while (i < PM.p.length) {
                                CPlayer p = PM.p[i];
                                if (p != null && IDLeave == p.IDDB) {
                                    if (PrepareScr.currLevel != 7) {
                                        PM.p[i].setState((byte) 5);
                                        break;
                                    }
                                    PM.p[i] = null;
                                    break;
                                }
                                ++i;
                            }
                        }
                        if (PrepareScr.currLevel != 7) {
                            GameScr.cam.setPlayerMode(PM.curP);
                        }
                        Session_ME.receiveSynchronized = 0;
                        break;
                    }
                    case 16: {
                        int ID = msg.reader().readInt();
                        boolean isReady = msg.reader().readBoolean();
                        this.gameLogicHandler.onSomeOneReady(ID, isReady);
                        break;
                    }
                    case 19: {
                        msg.reader().readShort();
                        int money3 = msg.reader().readInt();
                        CCanvas.prepareScr.setMoney(money3);
                        break;
                    }
                    case 9: {
                        int from = msg.reader().readInt();
                        String text = msg.reader().readUTF();
                        this.gameLogicHandler.onChatFromBoard(text, from);
                        break;
                    }
                    case 11: {
                        msg.reader().readShort();
                        int kicked = msg.reader().readInt();
                        String reason = msg.reader().readUTF();
                        this.gameLogicHandler.onKicked(kicked, reason);
                        CRes.out("NHAN M KICK id: " + kicked);
                        break;
                    }
                    case 29: {
                        Vector<PlayerInfo> friendList = new Vector<PlayerInfo>();
                        while (msg.reader().available() > 0) {
                            PlayerInfo info = new PlayerInfo();
                            info.IDDB = msg.reader().readInt();
                            info.name = msg.reader().readUTF();
                            info.xu = msg.reader().readInt();
                            info.gun = msg.reader().readByte();
                            info.clanID = msg.reader().readShort();
                            info.isReady = msg.reader().readByte() != 0;
                            info.level2 = msg.reader().readUnsignedByte();
                            info.level2Percen = msg.reader().readByte();
                            info.getQuanHam();
                            short[] idEq = new short[5];
                            int i = 0;
                            while (i < 5) {
                                idEq[i] = msg.reader().readShort();
                                info.equipID[info.gun][i] = idEq[i];
                                info.getMyEquip(3);
                                ++i;
                            }
                            friendList.addElement(info);
                        }
                        this.gameLogicHandler.onFriendList(friendList);
                        break;
                    }
                    case 36: {
                        Vector<PlayerInfo> searchList = new Vector<PlayerInfo>();
                        while (msg.reader().available() > 0) {
                            PlayerInfo info = new PlayerInfo();
                            info.IDDB = msg.reader().readInt();
                            info.name = msg.reader().readUTF();
                            searchList.addElement(info);
                        }
                        this.gameLogicHandler.onSearchResult(searchList);
                        break;
                    }
                    case 32: {
                        byte addResult = msg.reader().readByte();
                        this.gameLogicHandler.onAddFriendResult(addResult);
                        break;
                    }
                    case 33: {
                        byte delResult = msg.reader().readByte();
                        this.gameLogicHandler.onDelFriendResult(delResult);
                        break;
                    }
                    case 5: {
                        MsgInfo msg1 = new MsgInfo();
                        msg1.fromID = msg.reader().readInt();
                        msg1.fromName = msg.reader().readUTF();
                        msg1.message = msg.reader().readUTF();
                        this.gameLogicHandler.onChatFrom(msg1);
                        break;
                    }
                    case 122: {
                        try {
                            Vector<MoneyInfo> avs = new Vector<MoneyInfo>();
                            CCanvas.isPurchaseIOS = CCanvas.isIos();
                            if (CCanvas.isIos()) {
                                int i = 0;
                                while (i < 5) {
                                    MoneyInfo _money = new MoneyInfo();
                                    _money.id = MainActivity.google_productIds[i];
                                    _money.info = MainActivity.google_listGems[i];
                                    _money.smsContent = MainActivity.google_price[i];
                                    avs.addElement(_money);
                                    ++i;
                                }
                                if (GameMidlet.versioncode < 11 && GameMidlet.versionByte >= 240) {
                                    MoneyInfo _money = new MoneyInfo();
                                    _money.id = "napWeb";
                                    _money.info = msg.reader().readUTF();
                                    _money.smsContent = "";
                                    MoneyScrIOS.url_Nap = msg.reader().readUTF();
                                    avs.addElement(_money);
                                }
                                this.gameLogicHandler.onMoneyInfo(avs);
                                break;
                            }
                            if (GameMidlet.versionByte >= 240) {
                                MoneyInfo _money = new MoneyInfo();
                                _money.id = "napWeb";
                                _money.info = msg.reader().readUTF();
                                _money.smsContent = "";
                                MoneyScr.url_Nap = msg.reader().readUTF();
                                avs.addElement(_money);
                                this.gameLogicHandler.onMoneyInfo(avs);
                                break;
                            }
                            byte actionC2 = msg.reader().readByte();
                            if (actionC2 == 0) {
                                while (msg.reader().available() > 0) {
                                    MoneyInfo info = new MoneyInfo();
                                    info.id = msg.reader().readUTF();
                                    info.info = msg.reader().readUTF();
                                    info.smsContent = msg.reader().readUTF();
                                    avs.addElement(info);
                                }
                                this.gameLogicHandler.onMoneyInfo(avs);
                            }
                            if (actionC2 == 2) {
                                String moneyInfo = msg.reader().readUTF();
                                String moneySms = msg.reader().readUTF();
                                String strinInfo = msg.reader().readUTF();
                                this.gameLogicHandler.onChargeMoneySms(moneyInfo, moneySms, strinInfo);
                            }
                        } catch (Exception avs) {
                        }
                        break;
                    }
                    case 45: {
                        this.gameLogicHandler.onServerMessage(msg.reader().readUTF());
                        break;
                    }
                    case 46: {
                        this.gameLogicHandler.onServerInfo(msg.reader().readUTF());
                        break;
                    }
                    case 48: {
                        try {
                            this.gameLogicHandler.onVersion(msg.reader().readUTF(), msg.reader().readUTF());
                        } catch (Exception avs) {
                        }
                        break;
                    }
                    case 47: {
                        this.gameLogicHandler.onAdminCommandResponse(msg.reader().readUTF());
                        break;
                    }
                    case 52: {
                        int whoBonusId = msg.reader().readInt();
                        int moneyBonus = msg.reader().readInt();
                        int newMoney = msg.reader().readInt();
                        if (whoBonusId == TerrainMidlet.myInfo.IDDB) {
                            TerrainMidlet.myInfo.xu = newMoney;
                        }
                        if (CCanvas.curScr == CCanvas.gameScr) {
                            CCanvas.gameScr.activeMoney2Fly(moneyBonus, whoBonusId);
                        }
                        break;
                    }
                    case 10: {
                        String error = msg.reader().readUTF();
                        this.gameLogicHandler.onSetMoneyError(error);
                        break;
                    }
                    case 20: {
                        CCanvas.isInGameRunTime = true;
                        CCanvas.prepareScr.bossInfos.removeAllElements();
                        short[] equipID = new short[5];
                        if (GameScr.trainingMode) {
                            int i = 0;
                            while (i < 5) {
                                equipID[i] = msg.reader().readShort();
                                ++i;
                            }
                        }
                        byte mapID = msg.reader().readByte();
                        byte time = msg.reader().readByte();
                        int team = msg.reader().readUnsignedShort();
                        int playerLent = 0;
                        playerLent = PrepareScr.currLevel == 7 ? (int) msg.reader().readByte() : 8;
                        short[] playerX = new short[playerLent];
                        short[] playerY = new short[playerLent];
                        short[] maxHP = new short[playerLent];
                        int i = 0;
                        while (i < playerLent) {
                            playerX[i] = msg.reader().readShort();
                            if (playerX[i] != -1) {
                                playerY[i] = msg.reader().readShort();
                                maxHP[i] = msg.reader().readShort();
                            } else {
                                playerY[i] = -1;
                            }
                            ++i;
                        }
                        if (CCanvas.gameScr == null) {
                            CCanvas.gameScr = new GameScr();
                        }
                        if (!GameScr.trainingMode) {
                            CCanvas.gameScr.initGame(mapID, time, playerX, playerY, maxHP, team);
                            CCanvas.gameScr.show(CCanvas.prepareScr);
                        } else {
                            CCanvas.menuScr.doTraining(mapID, time, playerX, playerY, maxHP, equipID);
                        }
                        if (CCanvas.prepareScr.itemCur[4] >= 0) {
                            ShopItem.getI((int) 12).num = (byte) (ShopItem.getI((int) 12).num - 1);
                        }
                        if (CCanvas.prepareScr.itemCur[5] >= 0) {
                            ShopItem.getI((int) 13).num = (byte) (ShopItem.getI((int) 13).num - 1);
                        }
                        if (CCanvas.prepareScr.itemCur[6] >= 0) {
                            ShopItem.getI((int) 14).num = (byte) (ShopItem.getI((int) 14).num - 1);
                        }
                        if (CCanvas.prepareScr.itemCur[7] >= 0) {
                            ShopItem.getI((int) 15).num = (byte) (ShopItem.getI((int) 15).num - 1);
                        }
                        CCanvas.endDlg();
                        CCanvas.menu.showMenu = false;
                        BM.removeTornado();
                        CScreen.isSetClip = false;
                        nextTurnFlag = true;
                        break;
                    }
                    case 93: {
                        byte whoFly = msg.reader().readByte();
                        short xF = msg.reader().readShort();
                        short yF = msg.reader().readShort();
                        GameScr.pm.flyTo(whoFly, xF, yF);
                        GameScr.cam.setPlayerMode(whoFly);
                        if (CCanvas.curScr == CCanvas.gameScr) {
                            Session_ME.receiveSynchronized = 1;
                        }
                        break;
                    }
                    case 21: {
                        byte whoMove = msg.reader().readByte();
                        short x = msg.reader().readShort();
                        short y = msg.reader().readShort();
                        CRes.out("=========================> rec move = " + x + "_" + y);
                        PM.p[whoMove].xToNow = x;
                        PM.p[whoMove].yToNow = y;
                        if (PM.p[whoMove].x != x || PM.p[whoMove].y != y) {
                            GameScr.pm.movePlayer(whoMove, x, y);
                            if (whoMove != GameScr.myIndex && !PM.p[whoMove].isRunSpeed) {
                                Session_ME.receiveSynchronized = 1;
                            }
                        }
                        break;
                    }
                    case 53: {
                        byte whoUpdateXY = msg.reader().readByte();
                        short xUpdate = msg.reader().readShort();
                        short yUpdate = msg.reader().readShort();
                        GameScr.pm.updatePlayerXY(whoUpdateXY, xUpdate, yUpdate);
                        PM.p[whoUpdateXY].bulletType = (byte) -1;
                        break;
                    }
                    case 22:
                    case 84: {
                        byte typeShoot = msg.reader().readByte();
                        byte critical = msg.reader().readByte();
                        byte whoFire = msg.reader().readByte();
                        byte type = msg.reader().readByte();
                        short xS = msg.reader().readShort();
                        short yS = msg.reader().readShort();
                        short angle = msg.reader().readShort();
                        byte force_2 = 0;
                        if (type == 17 || type == 19) {
                            force_2 = msg.reader().readByte();
                        }
                        if (type == 14 || type == 40) {
                            BM.angle = msg.reader().readByte();
                            BM.force = msg.reader().readByte();
                        }
                        if (type == 44 || type == 45 || type == 47) {
                            BM.angle = msg.reader().readByte();
                            CRes.out("ANGLE= " + BM.angle);
                        }
                        byte nShoot = msg.reader().readByte();
                        int nBullet = msg.reader().readByte();
                        BM.nOrbit = nBullet;
                        short[][] xArr = new short[nBullet][];
                        short[][] yArr = new short[nBullet][];
                        short[][] xHitArr = new short[nBullet][];
                        short[][] yHitArr = new short[nBullet][];
                        int k = 0;
                        while (k < nBullet) {
                            int i;
                            int lenght = msg.reader().readShort();
                            short[] xPaintLast = new short[lenght];
                            short[] yPaintLast = new short[lenght];
                            short[] xPaint = new short[lenght];
                            short[] yPaint = new short[lenght];
                            if (typeShoot == 0) {
                                i = 0;
                                while (i < lenght) {
                                    if (i == 0) {
                                        xPaintLast[i] = msg.reader().readShort();
                                        yPaintLast[i] = msg.reader().readShort();
                                        xPaint[i] = xPaintLast[i];
                                        yPaint[i] = yPaintLast[i];
                                    } else {
                                        if (i == lenght - 1 && type == 49) {
                                            try {
                                                xPaint[i] = msg.reader().readShort();
                                                yPaint[i] = msg.reader().readShort();
                                                if (type != 49) break;
                                                Bullet.dXLaser = msg.reader().readByte();
                                                Bullet.dYLaser = msg.reader().readByte();
                                                if (Bullet.dXLaser == 0) break;
                                                while (Math.abs(Bullet.dXLaser) < 15) {
                                                    Bullet.dXLaser += Bullet.dXLaser;
                                                    Bullet.dYLaser += Bullet.dYLaser;
                                                }
                                            } catch (Exception e) {
                                                CRes.out("error");
                                            }
                                            break;
                                        }
                                        xPaintLast[i] = msg.reader().readByte();
                                        yPaintLast[i] = msg.reader().readByte();
                                        xPaint[i] = (short) (xPaint[i - 1] + xPaintLast[i]);
                                        yPaint[i] = (short) (yPaint[i - 1] + yPaintLast[i]);
                                    }
                                    ++i;
                                }
                            }
                            if (typeShoot == 1) {
                                i = 0;
                                while (i < lenght) {
                                    xPaint[i] = msg.reader().readShort();
                                    yPaint[i] = msg.reader().readShort();
                                    ++i;
                                }
                            }
                            xArr[k] = xPaint;
                            yArr[k] = yPaint;
                            if (type == 48) {
                                int sizeHit = msg.reader().readByte();
                                short[] xHit = new short[sizeHit];
                                short[] yHit = new short[sizeHit];
                                int i3 = 0;
                                while (i3 < sizeHit) {
                                    xHit[i3] = msg.reader().readShort();
                                    yHit[i3] = msg.reader().readShort();
                                    ++i3;
                                }
                                xHitArr[k] = xHit;
                                yHitArr[k] = yHit;
                            }
                            ++k;
                        }
                        byte typeSuper = msg.reader().readByte();
                        int xSuper = -1;
                        int ySuper = -1;
                        if (typeSuper == 1 || typeSuper == 2) {
                            xSuper = msg.reader().readShort();
                            ySuper = msg.reader().readShort();
                        } else if (typeSuper != 3) {
                            // empty if block
                        }
                        PM.p[whoFire].shoot(critical, whoFire, xS, yS, type, xArr, yArr, nShoot, force_2, angle, xHitArr, yHitArr, xSuper, ySuper);
                        if (CCanvas.curScr == CCanvas.gameScr) {
                            Session_ME.receiveSynchronized = 1;
                        }
                        break;
                    }
                    case 51: {
                        byte whoUpdateHP = msg.reader().readByte();
                        int nextHP = msg.reader().readUnsignedShort();
                        byte pixel = msg.reader().readByte();
                        if (PrepareScr.currLevel != 7) {
                            if (PM.p[whoUpdateHP] != null) {
                                PM.p[whoUpdateHP].updateHP(nextHP, pixel);
                            }
                        } else if (PM.findPlayerByIndex(whoUpdateHP) != null) {
                            PM.findPlayerByIndex(whoUpdateHP).updateHP(nextHP, pixel);
                        }
                        break;
                    }
                    case 83: {
                        CCanvas.menuScr.show();
                        if (CCanvas.gameScr != null) {
                            CCanvas.gameScr.onClearMap();
                            CCanvas.gameScr = null;
                        }
                        CScreen.isSetClip = true;
                        Session_ME.receiveSynchronized = 0;
                        break;
                    }
                    case 24: {
                        byte whoNext = msg.reader().readByte();
                        GameScr.pm.setNextPlayer(whoNext);
                        GameScr.bm.nBull = 0;
                        if (nextTurnFlag) {
                            nextTurnFlag = false;
                            CCanvas.endDlg();
                        }
                        break;
                    }
                    case -92: {
                        break;
                    }
                    case 25: {
                        byte windx = msg.reader().readByte();
                        byte windy = msg.reader().readByte();
                        GameScr.changeWind(windx, windy);
                        break;
                    }
                    case 26: {
                        byte whoUse = msg.reader().readByte();
                        byte item = msg.reader().readByte();
                        CRes.err("=======> USED ITEM = " + item);
                        PM.p[whoUse].UseItem(item, true, 0);
                        break;
                    }
                    case 69: {
                        CRes.out("=========> Cmd_Server2Client.CHOOSE_GUN: ");
                        int userID1 = msg.reader().readInt();
                        byte itemGun = msg.reader().readByte();
                        if (userID1 == TerrainMidlet.myInfo.IDDB) {
                            TerrainMidlet.myInfo.gun = itemGun;
                        }
                        if (CCanvas.curScr == CCanvas.changePScr) {
                            CCanvas.changePScr.onChangeGun();
                        }
                        CCanvas.endDlg();
                        break;
                    }
                    case 70: {
                        break;
                    }
                    case 71: {
                        int userID2 = msg.reader().readInt();
                        byte position = msg.reader().readByte();
                        this.gameLogicHandler.onChangeTeam(userID2, position);
                        break;
                    }
                    case 50: {
                        byte whoWin = msg.reader().readByte();
                        byte exBonus = msg.reader().readByte();
                        int moneyBonus2 = msg.reader().readInt();
                        CCanvas.gameScr.setWin(whoWin, exBonus, moneyBonus2);
                        CCanvas.prepareScr.resetReady();
                        CCanvas.prepareScr.readyDelay = 5;
                        Session_ME.receiveSynchronized = 0;
                        break;
                    }
                    case 34: {
                        int id = msg.reader().readInt();
                        if (id != -1) {
                            String string = msg.reader().readUTF();
                            int money1 = msg.reader().readInt();
                            byte level = msg.reader().readByte();
                            byte levelPercen = msg.reader().readByte();
                            int money2 = msg.reader().readInt();
                            int xp = msg.reader().readInt();
                            int nextXp = msg.reader().readInt();
                            int cup = msg.reader().readInt();
                            PlayerInfo tem = new PlayerInfo();
                            CCanvas.archScreen.level = level;
                            CCanvas.archScreen.levelPercen = levelPercen;
                            CCanvas.archScreen.xu = money1;
                            CCanvas.archScreen.luong = money2;
                            CCanvas.archScreen.exp = xp;
                            CCanvas.archScreen.nextExp = nextXp;
                            CCanvas.archScreen.cup = cup;
                            CCanvas.archScreen.rank = msg.reader().readUTF();
                            if (CCanvas.iconMn.isExist(TerrainMidlet.myInfo.clanID)) {
                                CCanvas.archScreen.imgClan = new mImage(CCanvas.iconMn.getImage(TerrainMidlet.myInfo.clanID));
                            } else {
                                GameService.gI().getClanIcon(TerrainMidlet.myInfo.clanID);
                            }
                            String result = String.valueOf(Language.name()) + ": " + string + ". " + Language.money() + ": " + money1 + Language.xu() + "-" + money2 + Language.luong() + ". Level:" + level + "+" + levelPercen + "%";
                            if (CCanvas.curScr == CCanvas.prepareScr) {
                                CCanvas.startOKDlg(result);
                                break;
                            }
                            CCanvas.endDlg();
                            CCanvas.archScreen.show();
                            break;
                        }
                        CCanvas.startOKDlg(Language.cantsee());
                        break;
                    }
                    case 72: {
                        byte by = msg.reader().readByte();
                        byte[] idAfterBuy = new byte[by];
                        byte[] nAfterBuy = new byte[by];
                        int i = 0;
                        while (i < by) {
                            idAfterBuy[i] = msg.reader().readByte();
                            nAfterBuy[i] = msg.reader().readByte();
                            ++i;
                        }
                        int monneyAfterBuy = msg.reader().readInt();
                        int moneyAfterBuy2 = msg.reader().readInt();
                        ShopItem.receiveAItemBuy(by, idAfterBuy, nAfterBuy, monneyAfterBuy, moneyAfterBuy2);
                        break;
                    }
                    case 74: {
                        byte gunIDAfter = msg.reader().readByte();
                        ChangePlayerCSr.isUnlock[gunIDAfter + ChangePlayerCSr.gunPassiveIndexSub] = 1;
                        CCanvas.changePScr.doChangePlayer();
                        CCanvas.endDlg();
                        break;
                    }
                    case 42: {
                        GameMidlet.timePingPaint = (int) ((mSystem.currentTimeMillis() - timePing) / 2L);
                        GameMidlet.ping = true;
                        CCanvas.isReconnect = false;
                        break;
                    }
                    case 63: {
                        String textSMS = msg.reader().readUTF();
                        final String dataSMS = msg.reader().readUTF();
                        final String toSMS = msg.reader().readUTF();
                        CRes.out(dataSMS);
                        CRes.out("sms://" + toSMS);
                        CCanvas.startYesNoDlg(textSMS, new IAction() {

                            public void perform() {
                                TerrainMidlet.sendSMS((String) dataSMS, (String) ("sms://" + toSMS), (IAction) new IAction() {

                                    public void perform() {
                                        CCanvas.startOKDlg(Language.sendSuccess());
                                    }
                                }, (IAction) new IAction() {

                                    public void perform() {
                                        CCanvas.startOKDlg(Language.sendFail());
                                    }
                                });
                            }
                        }, new IAction() {

                            public void perform() {
                                CCanvas.endDlg();
                            }
                        });
                        break;
                    }
                    case 75: {
                        byte mID = msg.reader().readByte();
                        if (CCanvas.curScr != CCanvas.luckyGifrScreen) {
                            CCanvas.curScr = CCanvas.prepareScr;
                            CCanvas.prepareScr.resetReady();
                            CCanvas.prepareScr.show();
                            PrepareScr.curMap = mID;
                            if (mID != 27 && mID != 100) {
                                try {
                                    CCanvas.startWaitDlgWithoutCancel(Language.pleaseWait(), 3);
                                    if (MM.maps != null) {
                                        MM.maps.removeAllElements();
                                    }
                                    GameScr.mm.createMap(mID);
                                    CCanvas.endDlg();
                                } catch (Exception ex) {
                                    CCanvas.endDlg();
                                }
                            }
                            if (!CRes.isNullOrEmpty(GameScr.res)) {
                                GameService.gI().luckGift((byte) -3);
                            }
                            System.gc();
                            break;
                        }
                        PrepareScr.curMap = mID;
                        if (mID != 27 && mID != 100) {
                            try {
                                CCanvas.startWaitDlgWithoutCancel(Language.pleaseWait(), 3);
                                if (MM.maps != null) {
                                    MM.maps.removeAllElements();
                                }
                                GameScr.mm.createMap(mID);
                                CCanvas.endDlg();
                            } catch (Exception ex) {
                                CCanvas.endDlg();
                            }
                        }
                        if (!CRes.isNullOrEmpty(GameScr.res)) {
                            GameService.gI().luckGift((byte) -3);
                        }
                        break;
                    }
                    case 64: {
                        byte len1 = msg.reader().readByte();
                        byte[] b1 = new byte[len1];
                        msg.reader().read(b1, 0, len1);
                        int len3 = msg.reader().readByte();
                        short[] b3 = new short[len3];
                        int i = 0;
                        while (i < len3) {
                            b3[i] = msg.reader().readShort();
                            ++i;
                        }
                        byte len4 = msg.reader().readByte();
                        byte[] b4 = new byte[len4];
                        msg.reader().read(b4, 0, len4);
                        byte len5 = msg.reader().readByte();
                        byte[] b5 = new byte[len5];
                        msg.reader().read(b5, 0, len5);
                        PM.MAX_PLAYER = msg.reader().readByte();
                        int lenMapID = msg.reader().readByte();
                        PrepareScr.mapBossID = new byte[lenMapID];
                        int i4 = 0;
                        while (i4 < lenMapID) {
                            PrepareScr.mapBossID[i4] = msg.reader().readByte();
                            ++i4;
                        }
                        PrepareScr.bossID = new byte[lenMapID];
                        int i2 = 0;
                        while (i2 < lenMapID) {
                            PrepareScr.bossID[i2] = msg.reader().readByte();
                            ++i2;
                        }
                        PM.NUMB_PLAYER = msg.reader().readByte();
                        Bullet.BULLset_WIND_AFFECT = b1;
                        i2 = 0;
                        while (i2 < b3.length) {
                            CRes.out(String.valueOf(this.getClass().getName()) + " debug: " + b3[i2]);
                            ++i2;
                        }
                        CPlayer.angleLock = b3;
                        CPlayer.angleLockMain = b3;
                        ChangePlayerCSr.power = b4;
                        ChangePlayerCSr.number = b5;
                        CCanvas.changePScr = new ChangePlayerCSr();
                        CCanvas.roomListScr2 = new RoomListScr2();
                        break;
                    }
                    case 76: {
                        byte roomLevel;
                        byte currRoom;
                        PrepareScr.currentRoom = currRoom = msg.reader().readByte();
                        byte boardID = msg.reader().readByte();
                        String boardName = msg.reader().readUTF();
                        PrepareScr.currLevel = roomLevel = msg.reader().readByte();
                        BoardListScr.setBoardName(boardID, boardName);
                        break;
                    }
                    case 78: {
                        boolean isList = msg.reader().readBoolean();
                        if (isList) {
                            int usSize = msg.reader().readByte();
                            Vector<PlayerInfo> findPlayer = new Vector<PlayerInfo>();
                            int i = 0;
                            while (i < usSize) {
                                PlayerInfo info = new PlayerInfo();
                                info.name = msg.reader().readUTF();
                                info.IDDB = msg.reader().readInt();
                                info.gun = msg.reader().readByte();
                                info.xu = msg.reader().readInt();
                                info.level2 = msg.reader().readUnsignedByte();
                                info.level2Percen = msg.reader().readUnsignedByte();
                                int a = 0;
                                while (a < 5) {
                                    info.equipID[info.gun][a] = msg.reader().readShort();
                                    ++a;
                                }
                                info.getQuanHam();
                                info.getMyEquip(4);
                                info.isReady = true;
                                findPlayer.addElement(info);
                                ++i;
                            }
                            CCanvas.endDlg();
                            GameLogicHandler.gI().onInviteList(findPlayer);
                            break;
                        }
                        CRes.out("Someone invite ");
                        String info = msg.reader().readUTF();
                        final byte roomInvite = msg.reader().readByte();
                        final byte boardInvite = msg.reader().readByte();
                        final String boardNameInvite = msg.reader().readUTF();
                        CCanvas.startYesNoDlg(info, new IAction() {

                            public void perform() {
                                PrepareScr.currentRoom = roomInvite;
                                BoardListScr.setBoardName(boardInvite, boardNameInvite);
                                GameService.gI().joinBoard(roomInvite, boardInvite, "");
                            }
                        }, new IAction() {

                            public void perform() {
                                CCanvas.endDlg();
                            }
                        });
                        break;
                    }
                    case 94: {
                        GameService.gI().changeItem(CCanvas.prepareScr.itemCur);
                        break;
                    }
                    case 86: {
                        String A = msg.reader().readUTF();
                        String B = msg.reader().readUTF();
                        byte C = msg.reader().readByte();
                        this.gameLogicHandler.onURL(A, B, C);
                        break;
                    }
                    case 87: {
                        String myName;
                        TerrainMidlet.myInfo.name = myName = msg.reader().readUTF();
                        break;
                    }
                    case 88: {
                        int lenght = msg.reader().readByte();
                        RoomListScr2.roomLevelText = new String[lenght];
                        int i = 0;
                        while (i < lenght) {
                            String vn = msg.reader().readUTF();
                            String eng = msg.reader().readUTF();
                            RoomListScr2.roomLevelText[i] = Language.language == 0 ? vn : eng;
                            ++i;
                        }
                        break;
                    }
                    case 89: {
                        int lenBoss = msg.reader().readByte();
                        short[] bX = new short[lenBoss];
                        short[] bY = new short[lenBoss];
                        int gun = -1;
                        int i = 0;
                        while (i < lenBoss) {
                            PlayerInfo playerInfo = new PlayerInfo();
                            playerInfo.IDDB = msg.reader().readInt();
                            playerInfo.name = msg.reader().readUTF();
                            playerInfo.maxHP = msg.reader().readInt();
                            playerInfo.gun = msg.reader().readByte();
                            gun = playerInfo.gun;
                            playerInfo.isBoss = true;
                            bX[i] = msg.reader().readShort();
                            bY[i] = msg.reader().readShort();
                            if (PrepareScr.currLevel == 7) {
                                playerInfo.index = msg.reader().readByte();
                            }
                            CCanvas.prepareScr.bossInfos.addElement(playerInfo);
                            ++i;
                        }
                        GameScr.pm.initBoss(bX, bY);
                        if (gun == 23 || gun == 24) {
                            Session_ME.receiveSynchronized = 1;
                        }
                        break;
                    }
                    case 92: {
                        int lentTile = msg.reader().readShort();
                        MM.undestroyTile = new short[lentTile];
                        int i = 0;
                        while (i < lentTile) {
                            MM.undestroyTile[i] = msg.reader().readShort();
                            ++i;
                        }
                        break;
                    }
                    case 90: {
                        byte fileType = msg.reader().readByte();
                        switch (fileType) {
                            case 0: {
                                break block7;
                            }
                            case 1: {
                                byte fileVersion2 = msg.reader().readByte();
                                if (fileVersion2 != CCanvas.mapIconVersion) {
                                    int fileLenght2 = msg.reader().readUnsignedShort();
                                    PrepareScr.fileData = new byte[fileLenght2];
                                    msg.reader().read(PrepareScr.fileData, 0, fileLenght2);
                                    PrepareScr.init();
                                    CCanvas.saveVersion("iconversion2", fileVersion2);
                                    CCanvas.saveData("icondata2", PrepareScr.fileData);
                                }
                                LoginScr.currTime = 45;
                                LoginScr.maxTime = 60;
                                GameService.gI().sendVersion((byte) 3, CCanvas.mapIconVersion);
                                break block7;
                            }
                            case 2: {
                                LoginScr.isWait = true;
                                CRes.out("====================================================> NHAN FILE PACK 3");
                                byte fileVersion3 = msg.reader().readByte();
                                if (fileVersion3 != CCanvas.mapValuesVersion) {
                                    CRes.err(String.valueOf(this.getClass().getName()) + " cmd:90 load SUB_FILEPACK_3  ");
                                    int fileLenght3 = msg.reader().readUnsignedShort();
                                    byte[] fileData3 = new byte[fileLenght3];
                                    msg.reader().read(fileData3, 0, fileLenght3);
                                    CCanvas.readMess(fileData3, (byte) 0);
                                    CCanvas.saveData("valuesdata2", fileData3);
                                    CCanvas.saveVersion("valuesversion2", fileVersion3);
                                    fileData3 = null;
                                } else {
                                    CCanvas.startWaitDlgWithoutCancel(Language.pleaseWait(), 5);
                                    LoginScr.isWait = false;
                                }
                                LoginScr.currTime = 15;
                                LoginScr.maxTime = 30;
                                GameService.gI().sendVersion((byte) 1, CCanvas.tileMapVersion);
                                CRes.err("MessageHandler ================> SUB_FILEPACK_3 Load Done");
                                break block7;
                            }
                            case 3: {
                                CRes.out("====================================================> NHAN FILE PACK 4");
                                byte fileVersion4 = msg.reader().readByte();
                                if (fileVersion4 != CCanvas.playerVersion) {
                                    int fileLenght4 = msg.reader().readUnsignedShort();
                                    CPlayer.fileData = new byte[fileLenght4];
                                    CRes.err("SUB_FILEPACK_4 ======================> playerData fileLenght4 = " + fileLenght4);
                                    msg.reader().read(CPlayer.fileData, 0, fileLenght4);
                                    CPlayer.init();
                                    CCanvas.saveVersion("playerVersion2", fileVersion4);
                                    CCanvas.saveData("playerdata2", CPlayer.fileData);
                                }
                                LoginScr.currTime = 60;
                                LoginScr.maxTime = 75;
                                GameService.gI().sendVersion((byte) 4, CCanvas.equipVersion);
                                CRes.err("MessageHandler ================> SUB_FILEPACK_4 Load Done PlayerData");
                                break block7;
                            }
                            case 4: {
                                CRes.out("====================================================> NHAN FILE PACK 5");
                                byte fileVersion5 = msg.reader().readByte();
                                if (fileVersion5 != CCanvas.equipVersion) {
                                    int fileLenght5 = msg.reader().readInt();
                                    byte[] fileData5 = new byte[fileLenght5];
                                    msg.reader().read(fileData5, 0, fileLenght5);
                                    CCanvas.readMess(fileData5, (byte) 1);
                                    CCanvas.saveVersion("equipVersion2", fileVersion5);
                                    CCanvas.saveData("equipdata2", fileData5);
                                    fileData5 = null;
                                }
                                LoginScr.currTime = 75;
                                LoginScr.maxTime = 90;
                                GameService.gI().sendVersion((byte) 5, CCanvas.equipVersion);
                                CRes.err("MessageHandler ================> SUB_FILEPACK_5 Load Done Equipment");
                                break block7;
                            }
                            case 5: {
                                CRes.out("====================================================> NHAN FILE PACK 6");
                                byte fileVersion6 = msg.reader().readByte();
                                if (fileVersion6 != CCanvas.levelCVersion) {
                                    int fileLenght6 = msg.reader().readUnsignedShort();
                                    byte[] fileData6 = new byte[fileLenght6];
                                    msg.reader().read(fileData6, 0, fileLenght6);
                                    CCanvas.readMess(fileData6, (byte) 2);
                                    CCanvas.saveVersion("levelCVersion2", fileVersion6);
                                    CCanvas.saveData("levelCData2", fileData6);
                                    int i = 0;
                                    while (i < CCanvas.nBigImage) {
                                        GameService.gI().getBigImage((byte) i);
                                        ++i;
                                    }
                                    fileData6 = null;
                                } else {
                                    boolean isLoadRawFata = false;
                                    int i = 0;
                                    while (i < CCanvas.nBigImage) {
                                        byte[] dataBig = CCanvas.loadData("bigImage" + i);
                                        if (dataBig == null) {
                                            isLoadRawFata = false;
                                            break;
                                        }
                                        PlayerEquip.imgData[i] = mImage.createImage(dataBig, 0, dataBig.length, "bigImage" + i);
                                        isLoadRawFata = true;
                                        dataBig = null;
                                        ++i;
                                    }
                                    if (isLoadRawFata) {
                                        GameService.gI().sendVersion((byte) 6, (byte) 0);
                                        this.gameLogicHandler.onLoginSuccess();
                                    } else {
                                        i = 0;
                                        while (i < CCanvas.nBigImage) {
                                            RMS.clearRMS("bigImage" + i);
                                            ++i;
                                        }
                                        int fileLenght6 = msg.reader().readUnsignedShort();
                                        byte[] fileData6 = new byte[fileLenght6];
                                        msg.reader().read(fileData6, 0, fileLenght6);
                                        CCanvas.readMess(fileData6, (byte) 2);
                                        CCanvas.saveVersion("levelCVersion2", fileVersion6);
                                        CCanvas.saveData("levelCData2", fileData6);
                                        int i5 = 0;
                                        while (i5 < CCanvas.nBigImage) {
                                            GameService.gI().getBigImage((byte) i5);
                                            ++i5;
                                        }
                                        fileData6 = null;
                                    }
                                }
                                CRes.err("MessageHandler ================> SUB_FILEPACK_6 Load Done");
                                break block7;
                            }
                        }
                        break;
                    }
                    case 95: {
                        CRes.out("CAPTURE");
                        byte whoCapture = msg.reader().readByte();
                        byte whoCaptured = msg.reader().readByte();
                        PM.p[whoCapture].capture(whoCaptured);
                        if (CCanvas.curScr == CCanvas.gameScr) {
                            Session_ME.receiveSynchronized = 1;
                        }
                        break;
                    }
                    case 96: {
                        byte whoBit = msg.reader().readByte();
                        byte whoBited = msg.reader().readByte();
                        PM.p[whoBited].isPoison = true;
                        PM.p[whoBited].poisonEff = true;
                        PM.p[whoBit].sLook = 3;
                        if (CCanvas.curScr == CCanvas.gameScr) {
                            Session_ME.receiveSynchronized = 1;
                        }
                        break;
                    }
                    case 97: {
                        int expAdd = msg.reader().readInt();
                        TerrainMidlet.myInfo.exp = msg.reader().readInt();
                        TerrainMidlet.myInfo.nextExp = msg.reader().readInt();
                        CRes.out("expAdd= " + expAdd);
                        byte levelUp = msg.reader().readByte();
                        if (levelUp == 0) {
                            TerrainMidlet.myInfo.level2Percen = msg.reader().readByte();
                            CRes.out("percen 0 = " + TerrainMidlet.myInfo.level2Percen);
                        }
                        if (CCanvas.curScr == CCanvas.gameScr && PM.p[GameScr.myIndex] != null) {
                            PM.p[GameScr.myIndex].updateExp(expAdd);
                        }
                        if (levelUp == 1) {
                            short currPoint;
                            int currLevel = msg.reader().readUnsignedByte();
                            byte percenLevel = msg.reader().readByte();
                            TerrainMidlet.myInfo.point = currPoint = msg.reader().readShort();
                            CRes.out("currLevel= " + currLevel + " currPoint= " + currPoint);
                            TerrainMidlet.myInfo.level2 = currLevel;
                            TerrainMidlet.myInfo.level2Percen = percenLevel;
                            CRes.out("percen 1 = " + TerrainMidlet.myInfo.level2Percen);
                        }
                        break;
                    }
                    case 99: {
                        TerrainMidlet.myInfo.level2 = msg.reader().readUnsignedByte();
                        TerrainMidlet.myInfo.level2Percen = msg.reader().readByte();
                        TerrainMidlet.myInfo.getQuanHam();
                        CRes.out("level=" + TerrainMidlet.myInfo.lvl);
                        TerrainMidlet.myInfo.point = msg.reader().readShort();
                        int i = 0;
                        while (i < 5) {
                            TerrainMidlet.myInfo.ability[i] = msg.reader().readShort();
                            CRes.out("my ability= " + TerrainMidlet.myInfo.ability[i]);
                            ++i;
                        }
                        TerrainMidlet.myInfo.exp = msg.reader().readInt();
                        TerrainMidlet.myInfo.nextExp = msg.reader().readInt();
                        TerrainMidlet.myInfo.cup = msg.reader().readInt();
                        TerrainMidlet.myInfo.getAttribute();
                        if (MenuScr.viewInfo) {
                            MenuScr.viewInfo = false;
                            CCanvas.endDlg();
                            if (CCanvas.levelScreen == null) {
                                CCanvas.levelScreen = new LevelScreen();
                            }
                            CCanvas.levelScreen.show(CCanvas.menuScr);
                        }
                        break;
                    }
                    case 100: {
                        byte whoLuck = msg.reader().readByte();
                        PM.p[whoLuck].lucky();
                        Session_ME.receiveSynchronized = 1;
                        break;
                    }
                    case 104: {
                        byte action = msg.reader().readByte();
                        if (action == 0) {
                            int dbKey = msg.reader().readInt();
                            byte gl = msg.reader().readByte();
                            byte tp = msg.reader().readByte();
                            short idb = msg.reader().readShort();
                            String n = msg.reader().readUTF();
                            int li = msg.reader().readByte();
                            byte[] ab = new byte[li];
                            int a = 0;
                            while (a < li) {
                                ab[a] = msg.reader().readByte();
                                ++a;
                            }
                            byte dte = msg.reader().readByte();
                            byte vip = msg.reader().readByte();
                            int level2 = msg.reader().readUnsignedByte();
                            Equip equip = PlayerEquip.getEquip(gl, tp, idb);
                            equip.getInvAtribute(ab);
                            Equip tam = null;
                            if (equip != null) {
                                tam = new Equip();
                                tam.id = equip.id;
                                tam.type = equip.type;
                                tam.icon = equip.icon;
                                tam.glass = equip.glass;
                                tam.x = equip.x;
                                tam.y = equip.y;
                                tam.w = equip.w;
                                tam.h = equip.h;
                                tam.dx = equip.dx;
                                tam.dy = equip.dy;
                                tam.date = dte;
                                tam.name = String.valueOf(n) + (level2 != 0 ? " " + level2 : "");
                                tam.dbKey = dbKey;
                                tam.level = equip.level;
                                tam.vip = vip;
                                tam.slot = (byte) 3;
                                int a2 = 0;
                                while (a2 < 5) {
                                    tam.inv_attAddPoint[a2] = equip.inv_attAddPoint[a2];
                                    tam.inv_ability[a2] = equip.inv_ability[a2];
                                    tam.inv_percen[a2] = equip.inv_percen[a2];
                                    ++a2;
                                }
                                if (TerrainMidlet.myInfo.myEquip.equips[tam.type] != null && TerrainMidlet.myInfo.myEquip.equips[tam.type].id == tam.id) {
                                    TerrainMidlet.myInfo.myEquip.equips[tam.type].dbKey = dbKey;
                                }
                            }
                            CCanvas.equipScreen.addEquip(tam);
                        }
                        if (action == 1) {
                            String info = msg.reader().readUTF();
                            CCanvas.inventory.requestServer(info);
                        }
                        if (action == 2) {
                            PlayerInfo p = TerrainMidlet.myInfo;
                            int i = 0;
                            while (i < 10) {
                                int j = 0;
                                while (j < 5) {
                                    p.equipID[i][j] = msg.reader().readShort();
                                    ++j;
                                }
                                ++i;
                            }
                        }
                        break;
                    }
                    case 101: {
                        CRes.out("=====> Cmd_Server2Client.INVENTORY: 101");
                        Vector<Equip> inventory = new Vector<Equip>();
                        CRes.out("=======>Cmd_Server2Client.INVENTORY: TerrainMidlet.myInfo == null " + (TerrainMidlet.myInfo == null));
                        int lenI = msg.reader().readByte();
                        short[] idI = new short[lenI];
                        String[] name = new String[lenI];
                        int[] dbKey = new int[lenI];
                        byte[] glassI = new byte[lenI];
                        byte[] typeI = new byte[lenI];
                        byte[] date = new byte[lenI];
                        byte[] slot = new byte[lenI];
                        byte[] vip = new byte[lenI];
                        int[] level2 = new int[lenI];
                        CRes.out("=======> Cmd_Server2Client.INVENTORY: lenI" + lenI);
                        int i = 0;
                        while (i < lenI) {
                            dbKey[i] = msg.reader().readInt();
                            glassI[i] = msg.reader().readByte();
                            typeI[i] = msg.reader().readByte();
                            idI[i] = msg.reader().readShort();
                            name[i] = msg.reader().readUTF();
                            int nAbility = msg.reader().readByte();
                            byte[] currAb = new byte[nAbility];
                            int a = 0;
                            while (a < nAbility) {
                                currAb[a] = msg.reader().readByte();
                                ++a;
                            }
                            date[i] = msg.reader().readByte();
                            slot[i] = msg.reader().readByte();
                            vip[i] = msg.reader().readByte();
                            level2[i] = msg.reader().readUnsignedByte();
                            Equip e = PlayerEquip.createEquip(glassI[i], typeI[i], idI[i]);
                            e.level2 = level2[i];
                            e.removeAbility();
                            e.getInvAtribute(currAb);
                            Equip tam = new Equip();
                            if (e != null) {
                                e.date = date[i];
                                e.name = name[i];
                                tam.id = e.id;
                                tam.type = e.type;
                                tam.frame = e.frame;
                                tam.x = e.x;
                                tam.y = e.y;
                                tam.w = e.w;
                                tam.h = e.h;
                                tam.dx = e.dx;
                                tam.dy = e.dy;
                                tam.icon = e.icon;
                                tam.type = e.type;
                                tam.glass = glassI[i];
                                tam.date = e.date;
                                tam.name = String.valueOf(e.name) + (e.level2 != 0 ? " " + e.level2 : "");
                                tam.dbKey = dbKey[i];
                                tam.level = e.level;
                                tam.slot = slot[i];
                                tam.vip = vip[i];
                                TerrainMidlet.myInfo.getMyEquip(5);
                                tam.removeAbility();
                                tam.getInvAtribute(currAb);
                                inventory.addElement(tam);
                            }
                            ++i;
                        }
                        i = 0;
                        while (i < 5) {
                            int myDbKey = msg.reader().readInt();
                            if (TerrainMidlet.myInfo.myEquip.equips[i] != null) {
                                TerrainMidlet.myInfo.myEquip.equips[i].dbKey = myDbKey;
                                TerrainMidlet.myInfo.dbKey[i] = myDbKey;
                            }
                            ++i;
                        }
                        CCanvas.equipScreen.getEquip(inventory);
                        CCanvas.endDlg();
                        break;
                    }
                    case 102: {
                        byte change = msg.reader().readByte();
                        if (change == 0) {
                            CCanvas.endDlg();
                            CCanvas.equipScreen.resetEquip();
                            CCanvas.menuScr.show();
                        }
                        if (change == 1) {
                            CCanvas.equipScreen.getLastEquip();
                            CCanvas.menuScr.show();
                            CCanvas.endDlg();
                        }
                        if (change == 2) {
                            int i = 0;
                            while (i < 10) {
                                int j = 0;
                                while (j < 5) {
                                    TerrainMidlet.myInfo.equipID[i][j] = msg.reader().readShort();
                                    if (i == 0) {
                                        CRes.out("my equip= " + TerrainMidlet.myInfo.equipID[i][j]);
                                    }
                                    ++j;
                                }
                                ++i;
                            }
                            if (CCanvas.curScr != CCanvas.inventory) {
                                CCanvas.equipScreen.init();
                                CCanvas.equipScreen.show(CCanvas.menuScr);
                            }
                        }
                        break;
                    }
                    case 103: {
                        Vector<Equip> items = new Vector<Equip>();
                        int nE = msg.reader().readShort();
                        int i = 0;
                        while (i < nE) {
                            byte glassID = msg.reader().readByte();
                            byte typeE = msg.reader().readByte();
                            short idE = msg.reader().readShort();
                            String nameE = msg.reader().readUTF();
                            int xuE = msg.reader().readInt();
                            int luongE = msg.reader().readInt();
                            byte dateE = msg.reader().readByte();
                            byte levelE = msg.reader().readByte();
                            Equip eq = PlayerEquip.getEquip(glassID, typeE, idE);
                            if (eq != null) {
                                eq.date = dateE;
                                eq.name = nameE;
                                eq.xu = xuE;
                                eq.luong = luongE;
                                eq.level = levelE;
                                eq.glass = glassID;
                                eq.isSelect = false;
                                eq.index = i;
                                items.addElement(eq);
                            }
                            ++i;
                        }
                        if (CCanvas.shopEquipScr == null) {
                            CCanvas.shopEquipScr = new ShopEquipment();
                        }
                        CCanvas.shopEquipScr.setItems(items);
                        CCanvas.menuScr.doEquipItem();
                        CCanvas.endDlg();
                        break;
                    }
                    case 105: {
                        int xu = msg.reader().readInt();
                        int gold = msg.reader().readInt();
                        TerrainMidlet.myInfo.xu = xu;
                        TerrainMidlet.myInfo.luong = gold;
                        CRes.out("xu= " + xu + " luong= " + gold);
                        break;
                    }
                    case 106: {
                        byte typeSee = msg.reader().readByte();
                        byte whoCantSee = msg.reader().readByte();
                        CCanvas.gameScr.checkEyeSmoke(whoCantSee, typeSee);
                        break;
                    }
                    case 107: {
                        byte typeFreeze = msg.reader().readByte();
                        byte whoFreeze = msg.reader().readByte();
                        CCanvas.gameScr.checkFreeze(whoFreeze, typeFreeze);
                        break;
                    }
                    case 108: {
                        byte whoPoison = msg.reader().readByte();
                        CCanvas.gameScr.checkPostion(whoPoison);
                        break;
                    }
                    case 109: {
                        CRes.out("DAT BOM HEN GIO");
                        byte typeBomb = msg.reader().readByte();
                        byte bombId = msg.reader().readByte();
                        if (typeBomb == 0) {
                            int xBomb = msg.reader().readInt();
                            int yBomb = msg.reader().readInt();
                            CRes.out("bomb x= " + xBomb + " bomb y= " + yBomb);
                            TimeBomb bomb = new TimeBomb(bombId, xBomb, yBomb);
                            CCanvas.gameScr.addTimeBomb(bomb);
                        }
                        if (typeBomb == 1) {
                            CRes.out("BOM EXPLORE id=" + bombId);
                            CCanvas.gameScr.explodeTimeBomb(bombId);
                        }
                        break;
                    }
                    case 112: {
                        CRes.out("Tra ve 4 Slot");
                        ShopItem.getI((int) 12).num = msg.reader().readByte();
                        ShopItem.getI((int) 13).num = msg.reader().readByte();
                        ShopItem.getI((int) 14).num = msg.reader().readByte();
                        ShopItem.getI((int) 15).num = msg.reader().readByte();
                        break;
                    }
                    case 113: {
                        byte whoAngry = msg.reader().readByte();
                        byte angry = msg.reader().readByte();
                        CRes.out("angry= " + angry);
                        PM.p[whoAngry].updateAngry(angry);
                        break;
                    }
                    case 116: {
                        CCanvas.endDlg();
                        byte pageTop = msg.reader().readByte();
                        Vector<Clan> clans = new Vector<Clan>();
                        while (msg.reader().available() > 0) {
                            Clan cl = new Clan();
                            cl.id = msg.reader().readShort();
                            cl.name = msg.reader().readUTF();
                            cl.count = (byte) msg.reader().readUnsignedByte();
                            cl.max = (byte) msg.reader().readUnsignedByte();
                            cl.master = msg.reader().readUTF();
                            cl.money = msg.reader().readInt();
                            cl.money2 = msg.reader().readInt();
                            cl.cup = msg.reader().readInt();
                            cl.level = msg.reader().readByte();
                            cl.percen = msg.reader().readByte();
                            cl.slogan = msg.reader().readUTF();
                            clans.addElement(cl);
                        }
                        if (clans.size() > 0) {
                            CCanvas.topClanScreen.show(CCanvas.curScr);
                            CCanvas.topClanScreen.getClanList(pageTop, clans);
                            break;
                        }
                        CCanvas.startOKDlg(Language.clanSize());
                        break;
                    }
                    case 117: {
                        CRes.out("Cmd_Server2Client.CLAN_INFO: ======> Clan Info");
                        CCanvas.endDlg();
                        Clan clan = new Clan();
                        clan.id = msg.reader().readShort();
                        clan.name = msg.reader().readUTF();
                        clan.count = (byte) msg.reader().readUnsignedByte();
                        clan.max = (byte) msg.reader().readUnsignedByte();
                        clan.master = msg.reader().readUTF();
                        clan.money = msg.reader().readInt();
                        clan.money2 = msg.reader().readInt();
                        clan.cup = msg.reader().readInt();
                        clan.exp = msg.reader().readInt();
                        clan.nextExp = msg.reader().readInt();
                        clan.level = msg.reader().readByte();
                        clan.percen = msg.reader().readByte();
                        clan.slogan = msg.reader().readUTF();
                        clan.date = msg.reader().readUTF();
                        int itemL = msg.reader().readByte();
                        clan.item = new String[itemL];
                        clan.time = new int[itemL];
                        int i = 0;
                        while (i < itemL) {
                            clan.item[i] = msg.reader().readUTF();
                            clan.time[i] = msg.reader().readInt();
                            ++i;
                        }
                        CCanvas.clanScreen.clan = clan;
                        CCanvas.clanScreen.show(CCanvas.curScr);
                        break;
                    }
                    case 115: {
                        short idIcon = msg.reader().readShort();
                        short lenImg = msg.reader().readShort();
                        byte[] iconData = new byte[lenImg];
                        CRes.out("======> Cmd_Server2Client.CLAN_ICON lenImg = " + lenImg);
                        mImage icon = null;
                        if (lenImg > 1) {
                            msg.reader().read(iconData, 0, lenImg);
                            icon = mImage.createImage(iconData, 0, lenImg, "");
                        } else {
                            icon = CRes.empty;
                        }
                        if (icon == null) {
                            icon = CRes.imgEr;
                        }
                        Clan cl = new Clan();
                        cl.id = idIcon;
                        cl.icon = icon;
                        if (!CCanvas.iconMn.isExist(cl.id)) {
                            CCanvas.iconMn.addIcon(cl);
                        }
                        GameLogicHandler.gI().onGetImage(idIcon, icon.image);
                        break;
                    }
                    case 118: {
                        byte pageMem = msg.reader().readByte();
                        Vector<PlayerInfo> clanMember = new Vector<PlayerInfo>();
                        String clanName = msg.reader().readUTF();
                        while (msg.reader().available() > 0) {
                            PlayerInfo info1 = new PlayerInfo();
                            info1.IDDB = msg.reader().readInt();
                            info1.name = msg.reader().readUTF();
                            CRes.out("name= " + info1.name);
                            info1.xu = msg.reader().readInt();
                            info1.gun = msg.reader().readByte();
                            info1.isReady = msg.reader().readByte() != 0;
                            info1.level2 = msg.reader().readUnsignedByte();
                            info1.level2Percen = msg.reader().readByte();
                            info1.STT = msg.reader().readUnsignedByte();
                            info1.cup = msg.reader().readInt();
                            info1.getQuanHam();
                            short[] idEq = new short[5];
                            int i = 0;
                            while (i < 5) {
                                idEq[i] = msg.reader().readShort();
                                info1.equipID[info1.gun][i] = idEq[i];
                                info1.getMyEquip(6);
                                ++i;
                            }
                            info1.clanContribute1 = msg.reader().readUTF();
                            info1.clanContribute2 = msg.reader().readUTF();
                            clanMember.addElement(info1);
                        }
                        this.gameLogicHandler.onClanMemberList(pageMem, clanMember);
                        break;
                    }
                    case 110: {
                        Vector<LuckyGame.Gift> gifts = new Vector<LuckyGame.Gift>();
                        int i = 0;
                        while (i < 10) {
                            byte typer = msg.reader().readByte();
                            byte itemId = msg.reader().readByte();
                            int count = msg.reader().readInt();
                            LuckyGame.Gift g = new LuckyGame.Gift(typer, itemId, count);
                            gifts.addElement(g);
                            ++i;
                        }
                        byte numb = msg.reader().readByte();
                        CCanvas.endDlg();
                        CCanvas.luckyGame.getGifts(gifts, numb);
                        Session_ME.receiveSynchronized = 1;
                        break;
                    }
                    case 119: {
                        byte indxGift = msg.reader().readByte();
                        byte userGift = msg.reader().readByte();
                        byte typeGift = msg.reader().readByte();
                        CRes.out("Gift= " + typeGift);
                        String nameG = PM.p[userGift].name;
                        if (typeGift == 0) {
                            int xuGift = msg.reader().readUnsignedShort();
                            String text1 = String.valueOf(nameG) + ": +" + xuGift + Language.xu();
                            CCanvas.gameScr.vGift.addElement(new GiftEffect(text1, null));
                        }
                        if (typeGift == 1) {
                            byte idItem = msg.reader().readByte();
                            byte numbGift = msg.reader().readByte();
                            String text3 = String.valueOf(nameG) + " : +" + numbGift + "x " + Item.ITEM_NAME[idItem];
                            CCanvas.gameScr.vGift.addElement(new GiftEffect(text3, null));
                        }
                        if (typeGift == 2) {
                            byte glassGift = msg.reader().readByte();
                            byte tGift = msg.reader().readByte();
                            short idGift = msg.reader().readShort();
                            Equip eq = PlayerEquip.getEquip(glassGift, tGift, idGift);
                            String text3 = String.valueOf(nameG) + " : +" + msg.reader().readUTF();
                            CCanvas.gameScr.vGift.addElement(new GiftEffect(text3, eq));
                        }
                        if (typeGift == 3) {
                            byte expGift = msg.reader().readByte();
                            String text4 = String.valueOf(nameG) + " : +" + expGift + "xp";
                            CCanvas.gameScr.vGift.addElement(new GiftEffect(text4, null));
                        }
                        if (typeGift == 4) {
                            String material = msg.reader().readUTF();
                            CCanvas.gameScr.vGift.addElement(new GiftEffect("+ " + material, null));
                        }
                        break;
                    }
                    case 120: {
                        CRes.out(" =========================> Save BIG Image ");
                        byte idBigImg = msg.reader().readByte();
                        int lenBigImg = msg.reader().readUnsignedShort();
                        byte[] dataBig = new byte[lenBigImg];
                        msg.reader().read(dataBig, 0, lenBigImg);
                        CCanvas.saveData("bigImage" + idBigImg, dataBig);
                        if (PlayerEquip.imgData == null) {
                            PlayerEquip.imgData = new mImage[idBigImg + 1];
                        }
                        PlayerEquip.imgData[idBigImg] = mImage.createImage(dataBig, 0, lenBigImg, "");
                        dataBig = null;
                        LoginScr.maxTime = 90 + idBigImg;
                        if (idBigImg == 9) {
                            LoginScr.isWait = false;
                            GameService.gI().sendVersion((byte) 6, (byte) 0);
                            CCanvas.endDlg();
                            this.gameLogicHandler.onLoginSuccess();
                        }
                        CRes.out(" =========================> Load BigImage  DONE!");
                        break;
                    }
                    case -120: {
                        CRes.out(" =========================> Save GET_BIG_EQUIP_HD Image ");
                        CRes.out(" =========================> Save BIG Image ");
                        byte idBigImg = msg.reader().readByte();
                        CRes.out(" =========================> Save idBigImg " + idBigImg);
                        int lenBigImg = msg.reader().readInt();
                        CRes.out(" =========================> Save lenBigImg " + lenBigImg);
                        byte[] dataBig = new byte[lenBigImg];
                        msg.reader().read(dataBig, 0, lenBigImg);
                        CCanvas.saveData("bigImage" + idBigImg, dataBig);
                        if (PlayerEquip.imgData == null) {
                            PlayerEquip.imgData = new mImage[idBigImg + 1];
                        }
                        PlayerEquip.imgData[idBigImg] = mImage.createImage(dataBig, 0, lenBigImg, "");
                        dataBig = null;
                        LoginScr.maxTime = 90 + idBigImg;
                        if (idBigImg == 9) {
                            LoginScr.isWait = false;
                            GameService.gI().sendVersion((byte) 6, (byte) 0);
                            CCanvas.endDlg();
                            this.gameLogicHandler.onLoginSuccess();
                        }
                        break;
                    }
                    case 121: {
                        String smsReg2 = "";
                        boolean isReg2Succ = msg.reader().readBoolean();
                        if (!isReg2Succ) {
                            smsReg2 = msg.reader().readUTF();
                            CCanvas.startOKDlg(smsReg2);
                            break;
                        }
                        CCanvas.startOKDlg(Language.dangkySucceed(), new IAction() {

                            public void perform() {
                                LoginScr _loginScr = (LoginScr) ((Object) CCanvas.curScr);
                                if (_loginScr != null) {
                                    _loginScr.setLogin();
                                }
                            }
                        });
                        break;
                    }
                    case -93: {
                        boolean smsAvaiable = msg.reader().readBoolean();
                        String smsContent = msg.reader().readUTF();
                        String smsStr = msg.reader().readUTF();
                        String infoC = msg.reader().readUTF();
                        this.gameLogicHandler.onRegisterInfo2(smsContent, smsAvaiable, smsStr, infoC);
                        break;
                    }
                    case 123: {
                        System.out.println("CHAT TO TEAM");
                        MsgInfo msg2 = new MsgInfo();
                        msg2.fromName = String.valueOf(msg.reader().readUTF()) + " " + Language.chatAll();
                        msg2.message = msg.reader().readUTF();
                        this.gameLogicHandler.onChatFrom(msg2);
                        break;
                    }
                    case 124: {
                        byte gIndex = msg.reader().readByte();
                        byte pIndex = msg.reader().readByte();
                        PM.p[gIndex].ghostHit(pIndex);
                        PM.p[gIndex].checkGhostLook(PM.p[pIndex].x, PM.p[gIndex].x);
                        if (CCanvas.curScr == CCanvas.gameScr) {
                            Session_ME.receiveSynchronized = 1;
                        }
                        break;
                    }
                    case 125: {
                        byte typeMaterial = msg.reader().readByte();
                        if (typeMaterial == 0) {
                            int nMaterial = msg.reader().readByte();
                            int i = 0;
                            while (i < nMaterial) {
                                byte mId = msg.reader().readByte();
                                short numM = msg.reader().readShort();
                                String mName = msg.reader().readUTF();
                                String mDetail = msg.reader().readUTF();
                                Equip material = new Equip();
                                material.id = mId;
                                material.name = mName;
                                material.strDetail = mDetail;
                                material.isMaterial = true;
                                material.icon = mId;
                                material.num = numM;
                                if (material.materialIcon == null && MaterialIconMn.isExistIcon(mId)) {
                                    material.materialIcon = MaterialIconMn.getImageFromID(mId);
                                }
                                CCanvas.equipScreen.addMaterial(material);
                                ++i;
                            }
                        }
                        break;
                    }
                    case 126: {
                        Message messageTem = msg;
                        try {
                            byte indexIcon;
                            byte typeIcon = messageTem.reader().readByte();
                            int idMIcon = messageTem.reader().readUnsignedByte();
                            short lenMIcon = messageTem.reader().readShort();
                            byte[] dataMIcon = new byte[lenMIcon];
                            messageTem.reader().read(dataMIcon, 0, lenMIcon);
                            if (typeIcon == 0) {
                                MaterialIconMn.addIcon(new ImageIcon(idMIcon, dataMIcon, lenMIcon));
                                CCanvas.equipScreen.getMaterialIcon(idMIcon, dataMIcon, lenMIcon);
                            }
                            if (typeIcon == 1) {
                                MaterialIconMn.addIcon(new ImageIcon(idMIcon, dataMIcon, lenMIcon));
                                CCanvas.shopLinhtinh.getMaterialIcon(idMIcon, dataMIcon, lenMIcon);
                            }
                            if (typeIcon == 2) {
                                GameScr.mm.addImage(idMIcon, dataMIcon, lenMIcon);
                            }
                            if (typeIcon == 3) {
                                indexIcon = messageTem.reader().readByte();
                                CCanvas.luckyGifrScreen.getGiftByItemID(indexIcon).setIcon(dataMIcon, lenMIcon);
                            }
                            if (typeIcon == 4) {
                                indexIcon = messageTem.reader().readByte();
                            }
                            dataMIcon = null;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    case 17: {
                        byte iAction = msg.reader().readByte();
                        if (iAction == 0) {
                            String iMess = msg.reader().readUTF();
                            CCanvas.inventory.combineYesNo(iMess);
                        }
                        break;
                    }
                    case 27: {
                        int nAction = msg.reader().readByte();
                        CRes.out("================> inventory Update type= " + nAction);
                        int i = 0;
                        while (i < nAction) {
                            byte IdMaterial;
                            int IdbKey;
                            byte IAction2 = msg.reader().readByte();
                            CRes.out("action= " + IAction2);
                            if (IAction2 == 0) {
                                IdbKey = msg.reader().readInt();
                                byte nRemove = msg.reader().readByte();
                                CRes.out("nRemove= " + nRemove);
                                CCanvas.equipScreen.removeEquip(IdbKey, nRemove);
                                CCanvas.inventory.removeEquip(IdbKey, nRemove);
                            } else if (IAction2 == 2) {
                                IdbKey = msg.reader().readInt();
                                CRes.out("======> INVENTORY_UPDATE IDDB= " + IdbKey);
                                int nAb = msg.reader().readByte();
                                byte[] IAb = new byte[nAb];
                                int a = 0;
                                while (a < nAb) {
                                    IAb[a] = msg.reader().readByte();
                                    CRes.out("====> INVENTORY_UPDATE ability= " + IAb[a]);
                                    ++a;
                                }
                                byte slotUpdate = msg.reader().readByte();
                                byte dateUpdate = msg.reader().readByte();
                                Equip tam = null;
                                if (CCanvas.curScr == CCanvas.inventory) {
                                    tam = CCanvas.inventory.getEquip(IdbKey);
                                }
                                if (CCanvas.curScr == CCanvas.equipScreen) {
                                    tam = CCanvas.equipScreen.getEquip(IdbKey);
                                }
                                tam.getInvAtribute(IAb);
                                tam.slot = slotUpdate;
                                tam.date = dateUpdate;
                                if (CCanvas.curScr == CCanvas.inventory) {
                                    CCanvas.inventory.getDetail();
                                }
                                if (CCanvas.curScr == CCanvas.equipScreen) {
                                    CCanvas.equipScreen.getDetail();
                                }
                                if (TerrainMidlet.myInfo.myEquip.equips[tam.type] != null && TerrainMidlet.myInfo.myEquip.equips[tam.type].dbKey == tam.dbKey) {
                                    TerrainMidlet.myInfo.myEquip.equips[tam.type].changeToEquip(tam);
                                    TerrainMidlet.myInfo.clearAttAddPoint();
                                }
                                CCanvas.equipScreen.getBaseAttribute();
                            } else if (IAction2 == 1) {
                                IdMaterial = msg.reader().readByte();
                                String nameMaterial = msg.reader().readUTF();
                                String detailM = msg.reader().readUTF();
                                Equip eq = new Equip();
                                eq.id = IdMaterial;
                                eq.name = nameMaterial;
                                eq.strDetail = detailM;
                                eq.isMaterial = true;
                                CCanvas.equipScreen.addEquip(eq, false);
                            } else if (IAction2 == 3) {
                                IdMaterial = msg.reader().readByte();
                                byte numM = msg.reader().readByte();
                                String nameMaterial = msg.reader().readUTF();
                                String detailM = msg.reader().readUTF();
                                Equip eq = new Equip();
                                eq.id = IdMaterial;
                                eq.num = numM;
                                eq.name = nameMaterial;
                                eq.strDetail = detailM;
                                eq.isMaterial = true;
                                CCanvas.equipScreen.addEquip(eq, true);
                            }
                            ++i;
                        }
                        CCanvas.inventory.unSelectEquip();
                        break;
                    }
                    case 80: {
                        byte whoEnd = msg.reader().readByte();
                        CCanvas.gameScr.checkInvisible2(whoEnd);
                        break;
                    }
                    case 59: {
                        byte whoEndV = msg.reader().readByte();
                        CCanvas.gameScr.checkVampire(whoEndV);
                        break;
                    }
                    case -2: {
                        CRes.out("VIP EQUIP");
                        byte vipAction = msg.reader().readByte();
                        if (vipAction == 0) {
                            TerrainMidlet.isVip[TerrainMidlet.myInfo.gun] = false;
                            TerrainMidlet.myInfo.getMyEquip(7);
                        } else {
                            int i = 0;
                            while (i < 5) {
                                short vipID = msg.reader().readShort();
                                CRes.out(" vip ID= " + vipID);
                                TerrainMidlet.myInfo.equipVipID[TerrainMidlet.myInfo.gun][i] = vipID;
                                ++i;
                            }
                            TerrainMidlet.myInfo.getVipEquip();
                            TerrainMidlet.isVip[TerrainMidlet.myInfo.gun] = true;
                        }
                        CCanvas.equipScreen.getBaseAttribute();
                        break;
                    }
                    case -3: {
                        Vector<Equip> vipItems = new Vector<Equip>();
                        while (msg.reader().available() > 0) {
                            byte vipID = msg.reader().readByte();
                            String vipName = msg.reader().readUTF();
                            String vipInfo = msg.reader().readUTF();
                            int xuVip = msg.reader().readInt();
                            int luongVip = msg.reader().readInt();
                            byte dateVip = msg.reader().readByte();
                            byte isByteNum = msg.reader().readByte();
                            Equip vipEq = new Equip();
                            vipEq.id = vipID;
                            vipEq.name = vipName;
                            vipEq.strDetail = vipInfo;
                            vipEq.xu = xuVip;
                            vipEq.luong = luongVip;
                            vipEq.date = dateVip;
                            vipEq.isMaterial = true;
                            if (MaterialIconMn.isExistIcon(vipID)) {
                                vipEq.materialIcon = MaterialIconMn.getImageFromID(vipID);
                            } else {
                                GameService.gI().getMaterialIcon((byte) 1, vipID, -1);
                            }
                            if (isByteNum == 0) {
                                vipEq.isBuyNum = true;
                            }
                            vipItems.addElement(vipEq);
                        }
                        CCanvas.endDlg();
                        CCanvas.shopLinhtinh.setItems(vipItems);
                        CCanvas.shopLinhtinh.show(CCanvas.menuScr);
                        break;
                    }
                    case -6: {
                        byte tMID = msg.reader().readByte();
                        MM.maps.removeAllElements();
                        if (CCanvas.curScr == CCanvas.gameScr) {
                            CCanvas.curScr = CCanvas.prepareScr;
                        }
                        GameScr.mm.createMap(tMID);
                        System.gc();
                        CCanvas.endDlg();
                        break;
                    }
                    case -7: {
                        int i = 0;
                        while (i < 5) {
                            int currDbKey = msg.reader().readInt();
                            if (TerrainMidlet.myInfo.myEquip.equips[i] != null) {
                                TerrainMidlet.myInfo.myEquip.equips[i].dbKey = currDbKey;
                            }
                            ++i;
                        }
                        break;
                    }
                    case -10: {
                        int i = 0;
                        while (i < 8) {
                            GameScr.num[i] = msg.reader().readByte();
                            ++i;
                        }
                        break;
                    }
                    case -12: {
                        if (CCanvas.shopBietDoi == null) {
                            CCanvas.shopBietDoi = new ShopBietDoi();
                        }
                        int nCl = msg.reader().readByte();
                        Vector<ClanItem> clItems = new Vector<ClanItem>();
                        int i = 0;
                        while (i < nCl) {
                            ClanItem clanItem = new ClanItem();
                            clanItem.id = msg.reader().readByte();
                            clanItem.name = msg.reader().readUTF();
                            clanItem.xu = msg.reader().readInt();
                            clanItem.luong = msg.reader().readInt();
                            clanItem.expDate = msg.reader().readByte();
                            clanItem.levelRequire = msg.reader().readByte();
                            clItems.addElement(clanItem);
                            ++i;
                        }
                        CCanvas.shopBietDoi.setItems(clItems);
                        CCanvas.shopBietDoi.show();
                        break;
                    }
                    case -14: {
                        byte typeList = msg.reader().readByte();
                        if (typeList == -1) {
                            int lentArry = msg.reader().readByte();
                            MenuScr.subMenuString[7] = new String[lentArry];
                            int i = 0;
                            while (i < lentArry) {
                                MenuScr.subMenuString[7][i] = msg.reader().readUTF().toUpperCase();
                                ++i;
                            }
                            break;
                        }
                        byte pageThanhtich = msg.reader().readByte();
                        String title = msg.reader().readUTF();
                        Vector<PlayerInfo> thanhtich = new Vector<PlayerInfo>();
                        while (msg.reader().available() > 0) {
                            PlayerInfo myPlayers = new PlayerInfo();
                            myPlayers.IDDB = msg.reader().readInt();
                            myPlayers.name = msg.reader().readUTF();
                            myPlayers.gun = msg.reader().readByte();
                            myPlayers.clanID = msg.reader().readShort();
                            myPlayers.level2 = msg.reader().readUnsignedByte();
                            myPlayers.level2Percen = msg.reader().readByte();
                            myPlayers.getQuanHam();
                            myPlayers.STT = msg.reader().readUnsignedByte();
                            int i = 0;
                            while (i < 5) {
                                myPlayers.equipID[myPlayers.gun][i] = msg.reader().readShort();
                                ++i;
                            }
                            myPlayers.aa = msg.reader().readUTF();
                            CRes.out("aa= " + myPlayers.aa);
                            myPlayers.isReady = true;
                            myPlayers.getMyEquip(8);
                            thanhtich.addElement(myPlayers);
                        }
                        this.gameLogicHandler.onXepHanglist((byte) -typeList, pageThanhtich, thanhtich, title);
                        break;
                    }
                    case -17: {
                        byte luckyAction = msg.reader().readByte();
                        CRes.out("===>lucky gift action = " + luckyAction);
                        if (luckyAction == 0) {
                            byte idGift = msg.reader().readByte();
                            byte typeLuckGift = msg.reader().readByte();
                            byte idLuckyGift = msg.reader().readByte();
                            String strLuckyGift = msg.reader().readUTF();
                            LuckyGift g = new LuckyGift();
                            g.id = idGift;
                            g.type = typeLuckGift;
                            g.info = strLuckyGift;
                            g.itemID = idLuckyGift;
                            g.isWait = true;
                            g.isServerSend = true;
                            CCanvas.luckyGifrScreen.setGiftByItemID(g);
                            if (g.type == 2) {
                                GameService.gI().getMaterialIcon((byte) 3, g.itemID, idGift);
                            }
                        }
                        if (luckyAction == -1) {
                            if (CCanvas.curScr == CCanvas.gameScr) break;
                            byte timeGift = msg.reader().readByte();
                            String giftInfo = msg.reader().readUTF();
                            if (CCanvas.width < 200) {
                                CCanvas.startOKDlg(giftInfo);
                            }
                            LuckyGifrScreen.info = Font.normalFont.splitFontBStrInLine(giftInfo, CCanvas.width - 80);
                            LuckyGifrScreen.time = new CTime();
                            LuckyGifrScreen.time.initTimeInterval(timeGift);
                            LuckyGifrScreen.time.resetTime();
                            CCanvas.luckyGifrScreen.isShow = false;
                            CCanvas.luckyGifrScreen.show();
                        }
                        if (luckyAction == -2) {
                            int i = 0;
                            while (i < 12) {
                                LuckyGift g = new LuckyGift();
                                byte typeLuckGift = msg.reader().readByte();
                                if (typeLuckGift != -1) {
                                    byte idLuckyGift = msg.reader().readByte();
                                    String strLuckyGift = msg.reader().readUTF();
                                    g.id = i;
                                    g.type = typeLuckGift;
                                    g.info = strLuckyGift;
                                    g.itemID = idLuckyGift;
                                    g.isServerSend = true;
                                    g.isWait = true;
                                    CCanvas.luckyGifrScreen.setGiftByItemID(g);
                                    CCanvas.luckyGifrScreen.giftDelete[i] = -1;
                                    if (g.type == 2) {
                                        GameService.gI().getMaterialIcon((byte) 3, g.itemID, (byte) i);
                                    }
                                }
                                ++i;
                            }
                            CCanvas.luckyGifrScreen.isShow = true;
                            CCanvas.luckyGifrScreen.show();
                        }
                        break;
                    }
                    case -18: {
                        byte fomulaAction = msg.reader().readByte();
                        CRes.out("FOMULA= " + fomulaAction);
                        if (fomulaAction == 0) {
                            String fInfo = msg.reader().readUTF();
                            CRes.out("fInfo= " + fInfo);
                            CCanvas.startOKDlg(fInfo, new IAction() {

                                public void perform() {
                                    CCanvas.fomulaScreen.lastScr.show();
                                }
                            });
                        }
                        if (fomulaAction == 1) {
                            CCanvas.fomulaScreen.fomulas.removeAllElements();
                            CCanvas.endDlg();
                            byte idFomula = msg.reader().readByte();
                            int nFomula = msg.reader().readByte();
                            CRes.out(" nFomular= " + nFomula);
                            int i = 0;
                            while (i < nFomula) {
                                Fomula fomula = new Fomula();
                                byte idItemCreate = msg.reader().readByte();
                                String nameEquip = msg.reader().readUTF();
                                byte leveRequire = msg.reader().readByte();
                                byte gunFomula = msg.reader().readByte();
                                byte typeFomula = msg.reader().readByte();
                                CRes.out("id item create= " + idItemCreate + " type Fomula= " + typeFomula + " gun= " + gunFomula);
                                fomula.e = PlayerEquip.createEquip(gunFomula, typeFomula, idItemCreate);
                                fomula.e.name = nameEquip;
                                CRes.out("Name equip= " + nameEquip);
                                fomula.levelRequire = leveRequire;
                                fomula.ID = idFomula;
                                int nMaterial = msg.reader().readByte();
                                fomula.imgMaterial = new mImage[nMaterial];
                                fomula.numMaterial = new String[nMaterial];
                                fomula.materialName = new String[nMaterial];
                                fomula.idImage = new int[nMaterial];
                                int j = 0;
                                while (j < nMaterial) {
                                    String materialName;
                                    byte materialIcon = msg.reader().readByte();
                                    fomula.materialName[j] = materialName = msg.reader().readUTF();
                                    if (MaterialIconMn.isExistIcon(materialIcon)) {
                                        fomula.imgMaterial[j] = MaterialIconMn.getImageFromID(materialIcon);
                                    } else {
                                        GameService.gI().getMaterialIcon((byte) 4, materialIcon, (byte) i);
                                    }
                                    fomula.idImage[j] = materialIcon;
                                    int materialRequire = msg.reader().readUnsignedByte();
                                    int materialHave = msg.reader().readUnsignedByte();
                                    fomula.numMaterial[j] = String.valueOf(materialHave) + "/" + materialRequire;
                                    CRes.out("Image id= " + materialIcon + " numMaterial= " + fomula.numMaterial[i]);
                                    ++j;
                                }
                                byte idEquipRequire = msg.reader().readByte();
                                String nameEquipRequire = msg.reader().readUTF();
                                byte requireLevel = msg.reader().readByte();
                                nameEquipRequire = String.valueOf(nameEquipRequire) + (requireLevel != 0 ? " " + requireLevel : "");
                                boolean isHave = msg.reader().readBoolean();
                                boolean isFinish = msg.reader().readBoolean();
                                int nAbility = msg.reader().readByte();
                                fomula.ability = new String[nAbility];
                                int a = 0;
                                while (a < nAbility) {
                                    fomula.ability[a] = msg.reader().readUTF();
                                    ++a;
                                }
                                fomula.h1 = nAbility * 18;
                                fomula.isHave = isHave;
                                CRes.out("is Have= " + isHave);
                                fomula.equipRequire = PlayerEquip.createEquip(gunFomula, typeFomula, idEquipRequire);
                                fomula.equipRequire.name = nameEquipRequire;
                                fomula.finish = isFinish;
                                CRes.out("is Finish= " + isFinish);
                                CCanvas.fomulaScreen.setFomula(fomula);
                                ++i;
                            }
                            if (CCanvas.curScr == CCanvas.inventory) {
                                CCanvas.fomulaScreen.show(CCanvas.inventory);
                            }
                            if (CCanvas.curScr == CCanvas.shopLinhtinh) {
                                CCanvas.fomulaScreen.show(CCanvas.shopLinhtinh);
                            }
                        }
                        break;
                    }
                    case -19: {
                        int nRoomName = msg.reader().readByte();
                        int i = 0;
                        while (i < nRoomName) {
                            byte roomNameID = msg.reader().readByte();
                            String roomName = msg.reader().readUTF();
                            byte levelRoom = msg.reader().readByte();
                            CCanvas.roomListScr2.changeName(roomNameID + levelRoom + 1, roomName);
                            ++i;
                        }
                        break;
                    }
                    case -22: {
                        CCanvas.clanScreen.clan.money = msg.reader().readInt();
                        CCanvas.clanScreen.clan.money2 = msg.reader().readInt();
                        break;
                    }
                    case -23: {
                        CCanvas.endDlg();
                        Vector<Mission> missions = new Vector<Mission>();
                        while (msg.reader().available() > 0) {
                            Mission m = new Mission();
                            m.id = msg.reader().readByte();
                            m.level = msg.reader().readByte();
                            m.name = msg.reader().readUTF();
                            m.reward = msg.reader().readUTF();
                            m.require = msg.reader().readInt();
                            m.have = msg.reader().readInt();
                            m.isComplete = msg.reader().readBoolean();
                            missions.addElement(m);
                        }
                        CCanvas.missionScreen.setMission(missions);
                        CCanvas.missionScreen.show();
                        break;
                    }
                    case -24: {
                        int currCup;
                        byte cup = msg.reader().readByte();
                        TerrainMidlet.myInfo.cup = currCup = msg.reader().readInt();
                        if (CCanvas.curScr == CCanvas.gameScr && PM.p[GameScr.myIndex] != null) {
                            PM.p[GameScr.myIndex].updateCup(cup);
                        }
                        break;
                    }
                    case -25: {
                        final int idGiaHan = msg.reader().readInt();
                        String giahanInfo = msg.reader().readUTF();
                        CCanvas.startYesNoDlg(giahanInfo, new IAction() {

                            public void perform() {
                                GameService.gI().get_more_day((byte) 1, idGiaHan);
                            }
                        });
                        break;
                    }
                    case -100: {
                        CRes.out("Quang cao-----------------------------------------------------------------------------------");
                        String strGameName = msg.reader().readUTF();
                        String strContent = msg.reader().readUTF();
                        String linkGame = msg.reader().readUTF();
                        MenuScr.getIdMenu(1);
                        MenuScr.menuString[MenuScr.MENU_QUANGCAO] = strGameName.toUpperCase();
                        MenuScr.gameContent = strContent;
                        MenuScr.gameLink = linkGame;
                        if (CCanvas.quangCaoScr == null) {
                            CCanvas.quangCaoScr = new QuangCao();
                        }
                        QuangCao.content = strContent;
                        QuangCao.link = linkGame;
                        CCanvas.quangCaoScr.getCommand();
                        CRes.out("game = " + strGameName + " strContent= " + strContent + " linkGame= " + linkGame);
                        break;
                    }
                    case -26: {
                        String agent = msg.reader().readUTF();
                        byte provider = msg.reader().readByte();
                        CRes.out("==============> agent= " + agent + " provider= " + provider);
                        TerrainMidlet.PROVIDER = provider;
                        CRes.saveRMSInt("provider", provider);
                        TerrainMidlet.AGENT = agent;
                        CRes.saveRMS_String("agent", agent);
                        break;
                    }
                    case -101: {
                        boolean regSucceed = msg.reader().readBoolean();
                        if (regSucceed) {
                            CCanvas.msgdlg.setInfo("Đăng kí thành công", null, new Command("OK", new IAction() {

                                public void perform() {
                                    CCanvas.endDlg();
                                }
                            }), null);
                            CCanvas.msgdlg.show();
                            break;
                        }
                        String _error = msg.reader().readUTF();
                        CCanvas.msgdlg.setInfo(_error, null, new Command("OK", new IAction() {

                            public void perform() {
                                CCanvas.endDlg();
                            }
                        }), null);
                        CCanvas.msgdlg.show();
                        break;
                    }
                    case -103: {
                        byte status = msg.reader().readByte();
                        String announce = msg.reader().readUTF();
                        if (status == 0) {
                            CCanvas.startOKDlg(announce, new IAction() {

                                public void perform() {
                                    CCanvas.inputDlg.setInfo(Language.createCharName(), new IAction() {

                                        public void perform() {
                                            if (!CCanvas.inputDlg.tfInput.getText().isEmpty()) {
                                                GameService.gI().onSendChangeRequest(CCanvas.inputDlg.tfInput.getText());
                                            }
                                        }
                                    }, null, 1);
                                    CCanvas.inputDlg.show();
                                }
                            });
                            break;
                        }
                        CCanvas.startOKDlg(announce);
                        break;
                    }
                    case 30: {
                        CCanvas.endDlg();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setGameLogicHandler(IGameLogicHandler gameLogicHandler) {
        this.gameLogicHandler = gameLogicHandler;
    }
}
