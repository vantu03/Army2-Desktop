package network;

import CLib.Image;
import CLib.RMS;
import CLib.mImage;
import CLib.mSound;
import CLib.mSystem;
import com.teamobi.mobiarmy2.GameMidlet;
import com.teamobi.mobiarmy2.MainGame;
import coreLG.CCanvas;
import coreLG.TerrainMidlet;

import java.util.Vector;

import model.AvatarInfo;
import model.CRes;
import model.Font;
import model.IAction;
import model.Language;
import model.MsgInfo;
import model.PlayerInfo;
import model.UserData;
import player.PM;
import screen.BoardListScr;
import screen.ConfigScr;
import screen.ListScr;
import screen.LoginScr;
import screen.MenuScr;
import screen.PrepareScr;

public class GameLogicHandler implements IGameLogicHandler {
    public static boolean isServerThongBao;
    private static GameLogicHandler instance = new GameLogicHandler();
    public static boolean isTryGetIPFromWap;

    public static GameLogicHandler gI() {
        return instance;
    }

    public static byte[] loadRMS(String filename) {
        return RMS.loadRMS(filename);
    }

    public static void saveRMS(String filename, byte[] data) {
        try {
            RMS.saveRMS(filename, data);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public static void saveIP(String strID) {
        saveRMS("ARMY2", strID.getBytes());
    }

    public static String loadIP() {
        byte[] data = loadRMS("AMRY2");
        return data == null ? null : new String(data);
    }

    public void onConnectFail() {
        CCanvas.startOKDlg(Language.connectFail(), new IAction() {
            public void perform() {
                GameLogicHandler.this.onResetGame();
            }
        });
    }

    public void onConnectOK() {
        CCanvas.startWaitDlgWithoutCancel(Language.pleaseWait(), 10000);
    }

    public void onDisconnect() {
        this.onResetGame();
    }

    public void onResetGame() {
        try {
            CCanvas.onClearCCanvas();
            Session_ME.gI().close(1534);
            Session_ME.gI().start = true;
            CCanvas.endDlg();
            mSystem.my_Gc();
        } catch (Exception var2) {
        }

        Session_ME.gI().connected = false;
        GameMidlet.pingCount = 0;
        CCanvas.loadScreen();
        CCanvas.serverListScreen.show();
    }

    public void onLoginFail(String reason) {
        CCanvas.startOKDlg(reason);
    }

    public void onLoginSuccess() {
        CRes.out("========> Login thangh cong nha ae!!!!!!!");
        if (LoginScr.remember == 1) {
            if (GameMidlet.server != 2) {
                TerrainMidlet.myInfo.name = LoginScr.user;
            }

            CRes.saveRMS_String("caroun", LoginScr.user);
            CRes.saveRMS_String("caropass", LoginScr.pass);
        } else {
            if (GameMidlet.server != 2) {
                TerrainMidlet.myInfo.name = LoginScr.user;
            }

            CRes.saveRMS_String("caroun", "");
            CRes.saveRMS_String("caropass", "");
        }

        if (CCanvas.menuScr == null) {
            CCanvas.menuScr = new MenuScr();
        }

        CCanvas.menuScr.show();
        if (CCanvas.msgScr.list.size() != 0) {
            CCanvas.msgScr.show(CCanvas.menuScr);
        }

    }

    public void onRoomList(Vector roomList) {
    }

    public void onBoardList(byte roomID, Vector boardList) {
    }

    public void onJoinGameSuccess(int ownerID, int money, Vector players, byte map) {
        CRes.out(this.getClass().getName() + " onJoinGameSuccess ownerID/money/map  " + ownerID + "/" + money + "/" + map);
    }

    public void onJoinGameFail(String reason) {
        CCanvas.msgdlg.setInfo(reason, (Command) null, new Command("OK", new IAction() {
            public void perform() {
                CCanvas.endDlg();
                CCanvas.startWaitDlg(Language.getRoomlist());
                GameService.gI().requestRoomList();
            }
        }), (Command) null);
        CCanvas.msgdlg.show();
    }

    public void onSomeOneJoinBoard(int seat, PlayerInfo joinPersonInfo) {
        try {
            mSound.playSound(3, mSound.volumeSound, 1);
            MainGame.me.vibrate(ConfigScr.vibrate);
        } catch (Exception var4) {
        }

    }

    public void onSomeOneLeaveBoard(int leave, int newOwner) {
    }

    public void onSomeOneReady(int id, boolean isReady) {
        if (id == TerrainMidlet.myInfo.IDDB) {
            CCanvas.endDlg();
        }

        CCanvas.prepareScr.setReady(id, isReady);
    }

    public void onOwnerSetMoney(int money3) {
    }

    public void onChatFromBoard(String text, int fromID) {
        if (CCanvas.prepareScr.isShowing()) {
            if (PrepareScr.currLevel != 7) {
                CCanvas.prepareScr.showChat(fromID, text, 90);
            }
        } else if (CCanvas.gameScr != null && CCanvas.gameScr.isShowing()) {
            CCanvas.gameScr.showChat(fromID, text, 90);
        }

    }

    public void onKicked(int kicked, String reason) {
        CCanvas.prepareScr.playerLeave(kicked);
        if (PrepareScr.currLevel != 7) {
            if (kicked == TerrainMidlet.myInfo.IDDB) {
                if (BoardListScr.boardList == null) {
                    CCanvas.menuScr.show();
                } else {
                    CCanvas.boardListScr.show();
                }

                CCanvas.msgdlg.setInfo(reason, (Command) null, new Command("OK", new IAction() {
                    public void perform() {
                        CCanvas.endDlg();
                    }
                }), (Command) null);
                CCanvas.msgdlg.show();
            } else if (PM.getPlayerByIDDB(kicked) != null) {
                PM.getPlayerByIDDB(kicked).IDDB = -1;
            }
        } else if (kicked == TerrainMidlet.myInfo.IDDB) {
            CCanvas.boardListScr.show();
        } else if (PM.getPlayerByIDDB(kicked) != null) {
            PM.getPlayerByIDDB(kicked).IDDB = -1;
        }

    }

    public void onStartGame(byte boardID6, byte roomID6, Vector myCards, int whoMoveFirst, byte interval) {
    }

    public void onMove(byte roomID7, byte boardID7, int whoMove, byte[] movedCards, int nextMove) {
    }

    public void onForceLose(byte roomID8, byte boardID8, int whoLose) {
    }

    public void onMoveAndWin(byte roomID9, byte boardID9, int whoWin, byte x1, byte y1) {
    }

    public void onOpponentWantDraw(byte roomID10, byte boardID10) {
    }

    public void onGameDraw(byte roomID11, byte boardID11) {
    }

    public void onDenyDraw(byte roomID12, byte boardID12) {
    }

    public void onWantLose(byte roomID13, byte boardID13, int whoLose2) {
    }

    public void onRichestList(int page, Vector richestList) {
    }

    public void onStrongestList(int page, Vector strongestList) {
        if (CCanvas.listScr == null) {
            CCanvas.listScr = new ListScr();
        }

        CCanvas.endDlg();
        CCanvas.listScr.page = page;
        CCanvas.listScr.setList(1, strongestList);
        CCanvas.listScr.isArmy2 = true;
        CCanvas.listScr.show(CCanvas.menuScr);
    }

    public void onXepHanglist(byte type, byte page, Vector v, String title) {
        if (CCanvas.listScr == null) {
            CCanvas.listScr = new ListScr();
        }

        CCanvas.endDlg();
        CCanvas.listScr.typeList = title;
        CCanvas.listScr.page = page;
        CCanvas.listScr.setList(type, v);
        CCanvas.listScr.isArmy2 = true;
        CCanvas.listScr.show(CCanvas.menuScr);
    }

    public void onRegisterInfo(String username, boolean available, String smsPrefix, String smsTo) {
    }

    public void onRegisterInfo2(final String content, boolean available, final String smsTo, String info) {
        if (!available) {
            CCanvas.startOKDlg(Language.ExistsNick());
        } else {
            CCanvas.startYesNoDlg(info, new IAction() {
                public void perform() {
                    TerrainMidlet.sendSMS(content, "sms://" + smsTo, new IAction() {
                        public void perform() {
                        }
                    }, new IAction() {
                        public void perform() {
                        }
                    });
                }
            }, new IAction() {
                public void perform() {
                    CCanvas.endDlg();
                }
            });
        }

    }

    public void onChargeMoneySms(final String content, final String sendTo, String info) {
        CCanvas.startYesNoDlg(info, new IAction() {
            public void perform() {
                TerrainMidlet.sendSMS(content, "sms://" + sendTo, new IAction() {
                    public void perform() {
                    }
                }, new IAction() {
                    public void perform() {
                    }
                });
            }
        }, new IAction() {
            public void perform() {
                CCanvas.endDlg();
            }
        });
    }

    public void onFriendList(Vector friendList) {
        if (CCanvas.listScr == null) {
            CCanvas.listScr = new ListScr();
        }

        CCanvas.endDlg();
        CCanvas.listScr.setList(2, friendList);
        CCanvas.listScr.isArmy2 = true;
        if (CCanvas.curScr == CCanvas.prepareScr) {
            CCanvas.listScr.show(CCanvas.prepareScr);
        } else if (CCanvas.curScr == CCanvas.menuScr) {
            CCanvas.listScr.show(CCanvas.menuScr);
        }

    }

    public void onInviteList(Vector inviteList) {
        if (CCanvas.listScr == null) {
            CCanvas.listScr = new ListScr();
        }

        CCanvas.endDlg();
        CCanvas.listScr.isArmy2 = true;
        CCanvas.listScr.setInviteList(3, inviteList);
        if (CCanvas.curScr == CCanvas.prepareScr) {
            CCanvas.listScr.show(CCanvas.prepareScr);
        } else if (CCanvas.curScr == CCanvas.menuScr) {
            CCanvas.listScr.show(CCanvas.menuScr);
        }

    }

    public void onSearchResult(Vector searchList) {
        if (searchList.size() > 0) {
            for (int i = 0; i < searchList.size(); ++i) {
                PlayerInfo ch = (PlayerInfo) searchList.elementAt(i);
                if (ch.name.equals(CCanvas.inputDlg.tfInput.getText())) {
                    CCanvas.startWaitDlg(Language.them() + " " + ch.name + " " + Language.vao());
                    GameService.gI().addFriend(ch.IDDB);
                }
            }
        } else {
            CCanvas.startOKDlg(Language.notFindID());
        }

    }

    public void onAddFriendResult(byte addResult) {
        if (CCanvas.listScr == null) {
            CCanvas.listScr = new ListScr();
        }

        if (addResult == 0) {
            CCanvas.startOKDlg(Language.addFriendSuccess());
            if (CCanvas.curScr == CCanvas.listScr && CCanvas.listScr.type == 2) {
                GameService.gI().requestFriendList();
            }
        } else if (addResult == 2) {
            CCanvas.startOKDlg(Language.cannotaddFriend());
        } else {
            CCanvas.startOKDlg(Language.isExist());
        }

    }

    public void onDelFriendResult(byte delResult) {
        if (delResult == 0) {
            CCanvas.startOKDlg(Language.deleteFriendSc());
            GameService.gI().requestFriendList();
        } else {
            CCanvas.startOKDlg(Language.cannotdelete());
        }

    }

    public void onMatchResult(Vector matchResult) {
    }

    public void onChatFrom(MsgInfo msg1) {
        if (CCanvas.curScr != CCanvas.msgScr) {
            ++CCanvas.msgPopup.nMessage;
            CCanvas.msgPopup.show();
        }

        CCanvas.msgScr.addMsg(msg1);
    }

    public void onMyUserData(UserData userData) {
    }

    public void onAvatar(AvatarInfo avi) {
    }

    public void onPing() {
    }

    public void onAvatarList(Vector avs) {
    }

    public void onBuyAvtarSuccess(short id) {
    }

    public void onMoneyInfo(Vector mni) {
        CCanvas.endDlg();
        if (CCanvas.isIos()) {
            CCanvas.moneyScrIOS.setAvatarList(mni);
        } else {
            CCanvas.moneyScr.setAvatarList(mni);
        }

    }

    public void onServerInfo(String info) {
        CCanvas.infoPopup.setInfo(info);
        CCanvas.infoPopup.show();
    }

    public void onServerMessage(String msg) {
        CCanvas.startOKDlg(msg);
    }

    public void onVersion(String info, final String url) {
        IAction down = new IAction() {
            public void perform() {
                mSystem.connectHTTP(url);
            }
        };
        CCanvas.msgdlg.setInfo(info, new Command("Download", down), new Command("", down), new Command(Language.close(), new IAction() {
            public void perform() {
                CCanvas.endDlg();
            }
        }));
        CCanvas.msgdlg.show();
    }

    public void onURL(String info, String url, final byte Auto) {
        if (TerrainMidlet.myInfo != null && TerrainMidlet.myInfo.name != null) {
            Font.replace(url, "@username", TerrainMidlet.myInfo.name);
        }

        IAction down = new IAction() {
            public void perform() {
                IAction ac = new IAction() {
                    public void perform() {
                    }
                };
                if (Auto == 0) {
                    if (CCanvas.isBB) {
                        CCanvas.msgdlg.setInfo(Language.Question(), (Command) null, new Command(Language.exit(), ac), new Command(Language.no(), new IAction() {
                            public void perform() {
                                CCanvas.endDlg();
                            }
                        }));
                    } else {
                        CCanvas.msgdlg.setInfo(Language.Question(), new Command(Language.exit(), ac), new Command("", ac), new Command(Language.no(), new IAction() {
                            public void perform() {
                                CCanvas.endDlg();
                            }
                        }));
                    }
                } else if (Auto != 1 && Auto == 2) {
                    CCanvas.endDlg();
                }

            }
        };
        CCanvas.msgdlg.setInfo(info, new Command("OK", down), new Command("", down), new Command(Language.no(), new IAction() {
            public void perform() {
                CCanvas.endDlg();
            }
        }));
        CCanvas.msgdlg.show();
    }

    public void onAdminCommandResponse(String responseText) {
    }

    public void onSomeOneFinish(byte roomID10, byte boardID10, int whoFinish, byte finishPosition, int dMoney, int dExp) {
    }

    public void onStopGame(byte roomID11, byte boardID11, int whoShowCards, byte[] cardsShow) {
    }

    public void onMoveError(byte boardID19, byte roomID19, String info) {
    }

    public void onSetMoneyError(String error) {
        isServerThongBao = true;
        CCanvas.startOKDlg(error, new IAction() {
            public void perform() {
                GameLogicHandler.isServerThongBao = false;
            }
        });
    }

    public void onFireArmy(byte whoFire, byte type, short x, short y, short angle, byte force) {
    }

    public void onMoveArmy(byte whoMove, short x, short y) {
    }

    public void onUpdateXY(byte whoUpdateXY, short x, short y) {
    }

    public void onStartArmy(byte MapID, byte timeInterval, short[] playerX, short[] playerY) {
    }

    public void onUpdateHP(byte whoUpdateHP, short nextHP) {
    }

    public void onNextTurn(byte whoNext) {
    }

    public void onWind(byte wind) {
    }

    public void onUseItem(int whoUse, byte item) {
    }

    public void onChooseGun(int userID1, byte itemGun) {
    }

    public void onMapChanged(byte newMap) {
        CCanvas.endDlg();
        CCanvas.prepareScr.onMapChanged(newMap);
    }

    public void onChangeTeam(int userID2, byte seat) {
        CCanvas.endDlg();
        CCanvas.prepareScr.onChangeTeam(userID2, seat);
    }

    public void onBonusMoney(int whoBonus, int moneyBonus, int newMoney) {
    }

    public void onClanMemberList(byte page, Vector memberList) {
        if (CCanvas.listScr == null) {
            CCanvas.listScr = new ListScr();
        }

        CCanvas.endDlg();
        CCanvas.listScr.page = page;
        CCanvas.listScr.setList(5, memberList);
        CCanvas.listScr.isArmy2 = true;
        CCanvas.listScr.show(CCanvas.clanScreen);
    }

    public void onGetImage(short id, Image img) {
        if (CCanvas.curScr == CCanvas.topClanScreen) {
            CCanvas.topClanScreen.findClan(id).icon = new mImage(img);
        }

        if (CCanvas.curScr == CCanvas.clanScreen) {
            CCanvas.clanScreen.clan.icon = new mImage(img);
        }

        if (CCanvas.curScr == CCanvas.listScr) {
            CCanvas.listScr.getPlayerIcon(id, img);
        }

        if (CCanvas.curScr == CCanvas.prepareScr) {
            CCanvas.prepareScr.getPlayerIcon(id, new mImage(img));
        }

    }
}
