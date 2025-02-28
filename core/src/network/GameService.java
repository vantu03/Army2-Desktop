package network;

import CLib.Image;
import CLib.mSystem;
import com.teamobi.mobiarmy2.GameMidlet;
import coreLG.CCanvas;
import item.Bullet;

import java.io.IOException;
import java.util.Vector;

import map.HoleInfo;
import model.CRes;
import model.UserData;
import screen.GameScr;

public class GameService {
    ISession session;
    protected static GameService instance;

    public void setSession(ISession gi) {
        this.session = gi;
    }

    public static GameService gI() {
        if (instance == null) {
            instance = new GameService();
        }

        return instance;
    }

    public void login(String username, String pass, String version) {
        Message m = new Message(1);

        try {
            m.writer().writeUTF(username);
            m.writer().writeUTF(pass);
            m.writer().writeUTF(version);
            this.session.sendMessage(m);
            m.cleanup();
        } catch (IOException var6) {
        }

    }

    public void requestRoomList() {
        this.requestEmptyRoom((byte) 0, (byte) -1, (String) null);
    }

    public void requestBoardList(byte id) {
        Message m = new Message((byte) 7);

        try {
            m.writer().writeByte(id);
        } catch (IOException var4) {
        }

        this.session.sendMessage(m);
        m.cleanup();
        CRes.out("SendM RequestBoardList BID: " + id);
    }

    public void requestEmptyRoom(byte type, byte level, String id) {
        CRes.out("=========> Gameservice request empty room type = " + type + " lv = " + level + " id = " + id);
        Message m = new Message((byte) -28);

        try {
            m.writer().writeByte(type);
            if (type == 1) {
                m.writer().writeByte(level);
            }

            if (type == 2) {
                m.writer().writeUTF(id);
            }
        } catch (IOException var6) {
        }

        this.session.sendMessage(m);
        m.cleanup();
    }

    public void joinBoard(byte roomID, byte boardID, String pass) {
        Message m = new Message((byte) 8);

        try {
            m.writer().writeByte(roomID);
            m.writer().writeByte(boardID);
            m.writer().writeUTF(pass);
        } catch (IOException var6) {
        }

        this.session.sendMessage(m);
        m.cleanup();
    }

    public void setBoardName(String boardName) {
        Message m = new Message((byte) 54);

        try {
            m.writer().writeUTF(boardName);
        } catch (IOException var4) {
        }

        this.session.sendMessage(m);
        m.cleanup();
    }

    public void changeMODE(byte MODE_TYPE) {
        Message m = new Message((byte) 73);

        try {
            m.writer().writeByte(MODE_TYPE);
        } catch (IOException var4) {
        }

        this.session.sendMessage(m);
        m.cleanup();
        CRes.out("GUI M - CHANGE_MODE: " + (MODE_TYPE == 0 ? "TEAM_MODE" : "FREE_MODE"));
    }

    public void leaveBoard() {
        Message m = new Message((byte) 15);
        this.session.sendMessage(m);
        m.cleanup();
        CRes.out("GUI MESSAGE: LEAVE BOARD");
    }

    public void ready(boolean isReady) {
        Message m = new Message((byte) 16);

        try {
            m.writer().writeBoolean(isReady);
        } catch (IOException var4) {
        }

        this.session.sendMessage(m);
        m.cleanup();
    }

    public void setMoney(int money) {
        Message m = new Message((byte) 19);

        try {
            m.writer().writeInt(money);
        } catch (IOException var4) {
        }

        this.session.sendMessage(m);
        m.cleanup();
    }

    public void setPassword(String pass) {
        Message m = new Message((byte) 18);

        try {
            m.writer().writeUTF(pass);
        } catch (IOException var4) {
        }

        this.session.sendMessage(m);
        m.cleanup();
    }

    public void chatToBoard(String text) {
        Message m = new Message((byte) 9);

        try {
            m.writer().writeUTF(text);
        } catch (IOException var4) {
        }

        this.session.sendMessage(m);
        m.cleanup();
    }

    public void kick(int kickID) {
        Message m = new Message((byte) 11);

        try {
            m.writer().writeInt(kickID);
        } catch (IOException var4) {
        }

        this.session.sendMessage(m);
        m.cleanup();
        CRes.out("SendM Kick: ID" + kickID);
    }

    public void joinAnyBoard(byte select) {
        Message m = new Message((byte) 28);

        try {
            m.writer().writeByte(select);
        } catch (IOException var4) {
        }

        this.session.sendMessage(m);
        m.cleanup();
    }

    public void checkFall(byte id, boolean isLand) {
        Message m = new Message((byte) 80);

        try {
            m.writer().writeByte(id);
            m.writer().writeBoolean(isLand);
        } catch (IOException var5) {
        }

        this.session.sendMessage(m);
        m.cleanup();
    }

    public void requestRichest(int page) {
        Message m = new Message((byte) 31);

        try {
            m.writer().writeByte(page);
        } catch (IOException var4) {
        }

        this.session.sendMessage(m);
        m.cleanup();
    }

    public void requestStrongest(int page) {
        Message m = new Message((byte) 30);

        try {
            m.writer().writeByte(page);
        } catch (IOException var4) {
        }

        this.session.sendMessage(m);
        m.cleanup();
    }

    public void requestRegister(String username, String accLogin, String pass) {
        Message m = new Message((byte) 121);

        try {
            m.writer().writeUTF(username);
            m.writer().writeUTF(accLogin);
            m.writer().writeUTF(pass);
        } catch (IOException var6) {
        }

        this.session.sendMessage(m);
        m.cleanup();
    }

    public void requestRegister3(String username, String pass, String version) {
        Message m = new Message((byte) -93);

        try {
            m.writer().writeUTF(username);
            m.writer().writeUTF(pass);
            m.writer().writeUTF(version);
        } catch (IOException var6) {
        }

        this.session.sendMessage(m);
        m.cleanup();
    }

    public void requestFriendList() {
        Message m = new Message((byte) 29);
        this.session.sendMessage(m);
        m.cleanup();
    }

    public void searchFriend(String text) {
        Message m = new Message((byte) 36);

        try {
            m.writer().writeUTF(text);
        } catch (IOException var4) {
        }

        this.session.sendMessage(m);
        m.cleanup();
    }

    public void addFriend(int id) {
        Message m = new Message((byte) 32);

        try {
            m.writer().writeInt(id);
        } catch (IOException var4) {
        }

        this.session.sendMessage(m);
        m.cleanup();
    }

    public void deleteFriend(int id) {
        Message m = new Message((byte) 33);

        try {
            m.writer().writeInt(id);
        } catch (IOException var4) {
        }

        this.session.sendMessage(m);
        m.cleanup();
    }

    public void requestAvatar(short avatar) {
        Message m = new Message((byte) 38);

        try {
            m.writer().writeShort(avatar);
        } catch (IOException var4) {
        }

        this.session.sendMessage(m);
        m.cleanup();
    }

    public void chatTo(int iddb, String text) {
        Message m = new Message((byte) 5);

        try {
            m.writer().writeInt(iddb);
            m.writer().writeUTF(text);
        } catch (IOException var5) {
        }

        this.session.sendMessage(m);
        m.cleanup();
    }

    public void requestUserData() {
        Message m = new Message((byte) 40);
        this.session.sendMessage(m);
        m.cleanup();
    }

    public void ping(int a, long b) {
        Message m = new Message((byte) 42);

        try {
            m.writer().writeInt(a);
            MessageHandler.timePing = mSystem.currentTimeMillis();
        } catch (IOException var6) {
        }

        this.session.sendMessage(m);
        m.cleanup();
    }

    public void requestAvatarShop() {
        Message m = new Message((byte) 39);
        this.session.sendMessage(m);
        m.cleanup();
    }

    public void updateDateProfile(UserData userData) {
        Message m = new Message((byte) 41);

        try {
            m.writer().writeUTF(userData.fullname);
            m.writer().writeByte(userData.gender);
            m.writer().writeInt(userData.birthYear);
            m.writer().writeUTF(userData.address);
            m.writer().writeUTF(userData.idnumber);
        } catch (IOException var4) {
        }

        this.session.sendMessage(m);
        m.cleanup();
    }

    public void buyAvatar(short id) {
        Message m = new Message((byte) 43);

        try {
            m.writer().writeShort(id);
        } catch (IOException var4) {
        }

        this.session.sendMessage(m);
    }

    public void setProvider(byte provider) {
        Message m = new Message((byte) 58);

        try {
            m.writer().writeByte(1);
        } catch (IOException var4) {
        }

        this.session.sendMessage(m);
    }

    public void requestChargeMoneyInfo2(byte type, String id) {
        Message m = new Message((byte) 122);

        try {
            m.writer().writeByte(type);
            if (type == 1) {
                m.writer().writeUTF(id);
            }
        } catch (IOException var5) {
            var5.printStackTrace();
        }

        CRes.out("====>requestChargeMoneyInfo2; type = " + type + " id = " + id);
        this.session.sendMessage(m);
        m.cleanup();
    }

    public void training(byte type) {
        Message m = new Message((byte) 83);

        try {
            m.writer().writeByte(type);
        } catch (IOException var4) {
            var4.printStackTrace();
        }

        this.session.sendMessage(m);
        m.cleanup();
    }

    public void doLoadCard(String text, String text2, String link) {
        Message m = new Message((byte) 77);

        try {
            m.writer().writeUTF(text);
            m.writer().writeUTF(text2);
            m.writer().writeUTF(link);
        } catch (IOException var6) {
            var6.printStackTrace();
        }

        CRes.out("====> send cmd 77");
        CRes.out("====> doLoadCard " + text);
        CRes.out("====> doLoadCard " + text2);
        CRes.out("====> doLoadCard " + link);
        this.session.sendMessage(m);
        m.cleanup();
    }

    public void sendAdminCommand(String cmd) {
        Message m = new Message((byte) 47);

        try {
            m.writer().writeUTF(cmd);
        } catch (IOException var4) {
        }

        this.session.sendMessage(m);
    }

    public void startGame() {
        Message m = new Message((byte) 20);
        this.session.sendMessage(m);
        m.cleanup();
    }

    public void mapSelect(byte mapID) {
        Message m = new Message((byte) 75);

        try {
            m.writer().writeByte(mapID);
        } catch (IOException var4) {
            var4.printStackTrace();
        }

        this.session.sendMessage(m);
        m.cleanup();
    }

    public void trainingMap() {
        Message m = new Message((byte) -6);
        this.session.sendMessage(m);
        m.cleanup();
    }

    public void check_cross(byte n, int[] x, int[] y) {
        Message m = new Message((byte) 79);

        try {
            m.writer().writeByte(n);

            for (int i = 0; i < n; ++i) {
                CRes.out("x= " + x[i]);
                m.writer().writeInt(x[i]);
                m.writer().writeInt(y[i]);
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        this.session.sendMessage(m);
        m.cleanup();
    }

    public void move(short x, short y) {
        if (!GameScr.trainingMode) {
            Message m = new Message((byte) 21);
            CRes.out(" move to " + x + "," + y);

            try {
                m.writer().writeShort(x);
                m.writer().writeShort(y);
            } catch (Exception var5) {
            }

            this.session.sendMessage(m);
            m.cleanup();
        }
    }

    public void waitForFIRETraining(byte type, short x, short y, short angle, byte force, byte force_2, byte numShoot) {
        Message m = new Message((byte) 84);

        try {
            m.writer().writeByte(type);
            m.writer().writeShort(x);
            m.writer().writeShort(y);
            m.writer().writeShort(angle);
            m.writer().writeByte(force);
            if (Bullet.isDoubleBull(type)) {
                m.writer().writeByte(force_2);
            }

            m.writer().writeByte(numShoot);
        } catch (Exception var10) {
        }

        this.session.sendMessage(m);
        m.cleanup();
        CRes.out("SendWait_Fire bull: " + type + " nShoot: " + numShoot + " force2= " + force_2);
    }

    public void waitForFIRE(byte type, short x, short y, short angle, byte force, byte force_2, byte numShoot) {
        Message m = new Message((byte) 22);

        try {
            m.writer().writeByte(type);
            m.writer().writeShort(x);
            m.writer().writeShort(y);
            m.writer().writeShort(angle);
            m.writer().writeByte(force);
            if (Bullet.isDoubleBull(type)) {
                m.writer().writeByte(force_2);
            }

            m.writer().writeByte(numShoot);
        } catch (Exception var10) {
        }

        this.session.sendMessage(m);
        m.cleanup();
    }

    public void shootResult() {
        if (!GameScr.trainingMode) {
            Message m = new Message((byte) 23);
            this.session.sendMessage(m);
            m.cleanup();
        }
    }

    public void requiredUpdateXY(short x, short y) {
        CRes.out("==> requiredUpdateXY " + x + "_" + y);
        if (!GameScr.trainingMode) {
            Message m = new Message((byte) 53);

            try {
                m.writer().writeShort(x);
                m.writer().writeShort(y);
            } catch (Exception var5) {
                var5.printStackTrace();
            }

            this.session.sendMessage(m);
            m.cleanup();
        }
    }

    public void inviteFriend(boolean isList, int id) {
        Message m = new Message((byte) 78);

        try {
            m.writer().writeBoolean(isList);
            m.writer().writeInt(id);
        } catch (Exception var5) {
        }

        this.session.sendMessage(m);
        m.cleanup();
    }

    public void skipTurn() {
        if (!GameScr.trainingMode) {
            Message m = new Message((byte) 49);
            this.session.sendMessage(m);
            m.cleanup();
        }
    }

    public void useItem(byte item) {
        if (!GameScr.trainingMode) {
            Message m = new Message((byte) 26);

            try {
                m.writer().writeByte(item);
            } catch (Exception var4) {
            }

            this.session.sendMessage(m);
            m.cleanup();
            CRes.out("==========> SendM UseITEM " + item);
        }
    }

    public void changeItem(int[] typeItem) {
        Message m = new Message((byte) 68);

        try {
            for (int i = 0; i < typeItem.length; ++i) {
                m.writer().writeByte(typeItem[i]);
            }
        } catch (Exception var4) {
        }

        this.session.sendMessage(m);
        m.cleanup();
        CRes.out("SendM changeGun " + typeItem.length);
    }

    public void changeGun(byte newGun) {
        Message m = new Message((byte) 69);

        try {
            m.writer().writeByte(newGun);
        } catch (Exception var4) {
        }

        this.session.sendMessage(m);
        m.cleanup();
        CRes.out("SendM chooseGun " + newGun);
    }

    public void selectMap(byte map) {
        Message m = new Message((byte) 70);

        try {
            m.writer().writeByte(map);
        } catch (Exception var4) {
        }

        this.session.sendMessage(m);
        m.cleanup();
        CRes.out("SendM ChangeMap " + map);
    }

    public void changeTeam() {
        Message m = new Message((byte) 71);
        this.session.sendMessage(m);
        m.cleanup();
    }

    public void disconnect() {
        Message m = new Message((byte) -4);
        this.session.sendMessage(m);
        m.cleanup();
    }

    public void setMaxPlayer(int max) {
        Message m = new Message((byte) 56);

        try {
            m.writer().writeByte(max);
        } catch (Exception var4) {
        }

        this.session.sendMessage(m);
    }

    public void requestInfoOf(int iDDB) {
        Message m = new Message((byte) 34);

        try {
            m.writer().writeInt(iDDB);
        } catch (Exception var4) {
        }

        this.session.sendMessage(m);
    }

    public void requestChangePass(String oldPass, String newPass) {
        Message m = new Message((byte) 81);

        try {
            m.writer().writeUTF(oldPass);
            m.writer().writeUTF(newPass);
        } catch (Exception var5) {
        }

        this.session.sendMessage(m);
    }

    public void requestBuyItem(byte action, byte id, byte numBuy) {
        Message m = new Message((byte) 72);

        try {
            m.writer().writeByte(action);
            m.writer().writeByte(id);
            m.writer().writeByte(numBuy);
        } catch (Exception var6) {
        }

        this.session.sendMessage(m);
    }

    public void buyGun(byte gunID, byte type) {
        Message m = new Message((byte) 74);

        try {
            m.writer().writeByte(gunID);
            m.writer().writeByte(type);
        } catch (Exception var5) {
        }

        this.session.sendMessage(m);
    }

    public void requestService(byte service, String arg) {
        if (arg == null) {
            arg = "";
        }

        Message m = new Message((byte) 85);

        try {
            m.writer().writeByte(service);
            m.writer().writeUTF(arg);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        this.session.sendMessage(m);
    }

    public void zingConnect(String user, String key, byte bigProvider, String version) {
        Message m = new Message((byte) 87);

        try {
            m.writer().writeUTF(user);
            m.writer().writeUTF(key);
            m.writer().write(bigProvider);
            m.writer().writeUTF(version);
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        this.session.sendMessage(m);
    }

    public void getString(String str) {
        Message m = new Message((byte) 127);

        try {
            CRes.out("STRING = " + str);
            m.writer().writeUTF(str);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        this.session.sendMessage(m);
    }

    public void getProviderAgent() {
        CRes.out("=====> get provider and agent");
        Message m = new Message((byte) -26);
        this.session.sendMessage(m);
    }

    public void sendVersion(byte type, byte version) {
        Message m = new Message((byte) 90);

        try {
            m.writer().writeByte(type);
            m.writer().writeByte(version);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        this.session.sendMessage(m);
    }

    public void addPoint(byte[] point) {
        Message m = new Message((byte) 98);

        try {
            m.writer().writeShort(point[0]);
            m.writer().writeShort(point[1]);
            m.writer().writeShort(point[2]);
            m.writer().writeShort(point[3]);
            m.writer().writeShort(point[4]);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        this.session.sendMessage(m);
    }

    public void changeEquip(int[] id) {
        CRes.out("CHANGE EQUIP");
        Message m = new Message((byte) 102);

        try {
            for (int i = 0; i < id.length; ++i) {
                m.writer().writeInt(id[i]);
                CRes.out("qweqwdasdsad= " + id[i]);
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        this.session.sendMessage(m);
    }

    public void getShopEquip() {
        Message m = new Message((byte) 103);
        this.session.sendMessage(m);
    }

    public void buy_sell_Equip(byte action, int[] index, short ind, byte money) {
        Message m = new Message((byte) 104);

        try {
            m.writer().writeByte(action);
            if (action == 1) {
                m.writer().writeByte(index.length);

                for (int i = 0; i < index.length; ++i) {
                    m.writer().writeInt(index[i]);
                }
            }

            if (action == 0) {
                m.writer().writeShort(ind);
                m.writer().writeByte(money);
            }
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        this.session.sendMessage(m);
    }

    public void sendRulet(byte money) {
        Message m = new Message((byte) 110);

        try {
            m.writer().writeByte(money);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        this.session.sendMessage(m);
    }

    public void charactorInfo() {
        Message m = new Message((byte) 99);
        this.session.sendMessage(m);
    }

    public void platform_request() {
        Message m = new Message((byte) 114);

        try {
            if (GameMidlet.DEVICE == 2) {
                m.writer().writeUTF("iphone");
            } else if (GameMidlet.DEVICE == 1) {
                m.writer().writeUTF("android");
            } else if (GameMidlet.DEVICE == 4) {
                m.writer().writeUTF("pc");
            } else if (GameMidlet.DEVICE == 0) {
                m.writer().writeUTF("j2me{HD}");
            } else {
                m.writer().writeUTF("j2me{HD}");
            }

            m.writer().writeByte(GameMidlet.versioncode);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        this.session.sendMessage(m);
    }

    public void vip_equip(byte action, int dbKey) {
        CRes.out("GUI + " + action);
        Message m = new Message((byte) -2);

        try {
            m.writer().writeByte(action);
            m.writer().writeInt(dbKey);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        this.session.sendMessage(m);
    }

    public void topClan(byte page) {
        Message m = new Message((byte) 116);

        try {
            m.writer().writeByte(page);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        this.session.sendMessage(m);
    }

    public void getClanIcon(short id) {
        if (id != 0 && id != -1) {
            Image img = null;
            if (CCanvas.iconMn.isExist(id)) {
                CRes.out("tim thay icon");
                img = CCanvas.iconMn.getImage(id);
                GameLogicHandler.gI().onGetImage(id, img);
            } else {
                CRes.out("request icon");
                Message m = new Message((byte) 115);

                try {
                    m.writer().writeShort(id);
                } catch (Exception var5) {
                    var5.printStackTrace();
                }

                this.session.sendMessage(m);
            }

        }
    }

    public void clanInfo(short id) {
        Message m = new Message((byte) 117);

        try {
            m.writer().writeShort(id);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        this.session.sendMessage(m);
    }

    public void clanMember(byte page, short id) {
        Message m = new Message((byte) 118);

        try {
            m.writer().writeByte(page);
            m.writer().writeShort(id);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        this.session.sendMessage(m);
    }

    public void getBigImage(byte id) {
        Message m = new Message((byte) 120);

        try {
            m.writer().writeByte(id);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        this.session.sendMessage(m);
    }

    public void chatTeam(String mess) {
        Message m = new Message((byte) 123);

        try {
            m.writer().writeUTF(mess);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        this.session.sendMessage(m);
    }

    public void getMaterialIcon(byte action, int id, int index) {
        CRes.out("get material icon " + id);
        Message m = new Message((byte) 126);

        try {
            m.writer().writeByte(action);
            m.writer().writeByte(id);
            if (action == 3) {
                m.writer().writeByte(index);
            }

            if (action == 4) {
                m.writer().writeByte(index);
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        this.session.sendMessage(m);
    }

    public void getFomula(byte id, byte action, byte level) {
        CRes.out("get fomula");
        Message m = new Message((byte) -18);

        try {
            m.writer().writeByte(id);
            m.writer().writeByte(action);
            if (action == 2) {
                m.writer().writeByte(level);
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        this.session.sendMessage(m);
    }

    public void imbue(byte type, byte num, int[] id, byte[] nSelect) {
        Message m = new Message((byte) 17);

        try {
            if (type == 0) {
                m.writer().writeByte(type);
                m.writer().writeByte(num);
                CRes.out("Num= " + num);

                for (int i = 0; i < num; ++i) {
                    m.writer().writeInt(id[i]);
                    m.writer().writeByte(nSelect[i]);
                    CRes.out("ID= " + id[i]);
                }
            }

            if (type == 1) {
                m.writer().writeByte(type);
            }
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        this.session.sendMessage(m);
    }

    public void getShopLinhtinh(byte action, byte typeMoney, byte id, byte num) {
        Message m = new Message((byte) -3);

        try {
            m.writer().writeByte(action);
            if (action == 1) {
                m.writer().writeByte(typeMoney);
                m.writer().writeByte(id);
                m.writer().writeByte(num);
            }
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        this.session.sendMessage(m);
    }

    public void signOut() {
        Message m = new Message((byte) -4);
        this.session.sendMessage(m);
    }

    public void changeRoomName() {
        Message m = new Message((byte) -19);
        this.session.sendMessage(m);
    }

    public void getShopBietDoi(byte action, byte money, byte id) {
        Message m = new Message((byte) -12);

        try {
            m.writer().writeByte(action);
            if (action == 1) {
                m.writer().writeByte(money);
                m.writer().writeByte(id);
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        this.session.sendMessage(m);
    }

    public void bangxephang(byte type, int page) {
        CRes.out("request list : page= " + page);
        Message m = new Message((byte) -14);

        try {
            m.writer().writeByte(type);
            m.writer().writeByte(page);
        } catch (IOException var5) {
        }

        this.session.sendMessage(m);
        m.cleanup();
    }

    public void luckGift(byte id) {
        CRes.out(" =======> send Lucky gift to server id == " + id);
        Message m = new Message((byte) -17);

        try {
            m.writer().writeByte(id);
        } catch (IOException var4) {
        }

        this.session.sendMessage(m);
        m.cleanup();
    }

    public void mission(byte type, byte id) {
        Message m = new Message((byte) -23);

        try {
            m.writer().writeByte(type);
            if (type == 1) {
                m.writer().writeByte(id);
            }
        } catch (IOException var5) {
        }

        this.session.sendMessage(m);
        m.cleanup();
    }

    public void inputMoney(byte type, int money) {
        Message m = new Message((byte) -21);

        try {
            m.writer().writeByte(type);
            m.writer().writeInt(money);
        } catch (IOException var5) {
        }

        this.session.sendMessage(m);
        m.cleanup();
    }

    public void get_more_day(byte action, int id) {
        CRes.out("Gia han");
        Message m = new Message((byte) -25);

        try {
            m.writer().writeByte(action);
            m.writer().writeInt(id);
        } catch (IOException var5) {
        }

        this.session.sendMessage(m);
        m.cleanup();
    }

    public void holeInfo(Vector holeInfo) {
        if (!CCanvas.isDebugging()) {
            Message m = new Message((byte) -92);

            try {
                m.writer().writeByte(holeInfo.size());

                for (int i = 0; i < holeInfo.size(); ++i) {
                    HoleInfo hole = (HoleInfo) holeInfo.elementAt(i);
                    m.writer().writeShort(hole.mapID);
                    m.writer().writeShort(hole.x);
                    m.writer().writeShort(hole.y);
                    m.writer().writeByte(hole.holeType);
                }
            } catch (IOException var5) {
            }

            this.session.sendMessage(m);
            m.cleanup();
        }
    }

    public void debugServer() {
        Message m = new Message(-25);
        this.session.sendMessage(m);
        m.cleanup();
    }

    public void onRegisterNickFree(String charname, String email_phone, String pass) {
        CRes.out("=========> START REGISTER NEW FREE ACCOUNT!");
        Message m = new Message((byte) 121);

        try {
            m.writer().writeUTF(charname);
            m.writer().writeUTF(email_phone);
            m.writer().writeUTF(pass);
        } catch (Exception var6) {
        }

        this.session.sendMessage(m);
        m.cleanup();
    }

    public void onSendChangeRequest(String charName) {
        Message m = new Message((byte) -103);

        try {
            m.writer().writeUTF(charName);
        } catch (Exception var4) {
        }

        this.session.sendMessage(m);
        m.cleanup();
    }

    public void onInApppurchaseToServer(String product_ID, String token) {
        if (product_ID == null) {
            product_ID = "Ko co product ID";
        }

        if (token == null) {
            token = "Ko co product token";
        }

        Message m = new Message((byte) -102);

        try {
            m.writer().writeUTF(product_ID);
            m.writer().writeUTF(token);
        } catch (Exception var5) {
        }

        this.session.sendMessage(m);
        m.cleanup();
    }
}
