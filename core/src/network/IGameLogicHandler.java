package network;

import CLib.Image;
import java.util.Vector;
import model.AvatarInfo;
import model.MsgInfo;
import model.PlayerInfo;
import model.UserData;

public interface IGameLogicHandler {
   void onLoginSuccess();

   void onLoginFail(String var1);

   void onConnectOK();

   void onConnectFail();

   void onDisconnect();

   void onRoomList(Vector var1);

   void onBoardList(byte var1, Vector var2);

   void onJoinGameSuccess(int var1, int var2, Vector var3, byte var4);

   void onJoinGameFail(String var1);

   void onSomeOneJoinBoard(int var1, PlayerInfo var2);

   void onSomeOneLeaveBoard(int var1, int var2);

   void onSomeOneReady(int var1, boolean var2);

   void onOwnerSetMoney(int var1);

   void onChatFromBoard(String var1, int var2);

   void onKicked(int var1, String var2);

   void onStartGame(byte var1, byte var2, Vector var3, int var4, byte var5);

   void onMove(byte var1, byte var2, int var3, byte[] var4, int var5);

   void onForceLose(byte var1, byte var2, int var3);

   void onMoveAndWin(byte var1, byte var2, int var3, byte var4, byte var5);

   void onOpponentWantDraw(byte var1, byte var2);

   void onGameDraw(byte var1, byte var2);

   void onDenyDraw(byte var1, byte var2);

   void onWantLose(byte var1, byte var2, int var3);

   void onRichestList(int var1, Vector var2);

   void onStrongestList(int var1, Vector var2);

   void onRegisterInfo(String var1, boolean var2, String var3, String var4);

   void onRegisterInfo2(String var1, boolean var2, String var3, String var4);

   void onChargeMoneySms(String var1, String var2, String var3);

   void onFriendList(Vector var1);

   void onClanMemberList(byte var1, Vector var2);

   void onInviteList(Vector var1);

   void onSearchResult(Vector var1);

   void onAddFriendResult(byte var1);

   void onDelFriendResult(byte var1);

   void onMatchResult(Vector var1);

   void onChatFrom(MsgInfo var1);

   void onMyUserData(UserData var1);

   void onAvatar(AvatarInfo var1);

   void onPing();

   void onAvatarList(Vector var1);

   void onBuyAvtarSuccess(short var1);

   void onMoneyInfo(Vector var1);

   void onServerMessage(String var1);

   void onServerInfo(String var1);

   void onVersion(String var1, String var2);

   void onAdminCommandResponse(String var1);

   void onSomeOneFinish(byte var1, byte var2, int var3, byte var4, int var5, int var6);

   void onStopGame(byte var1, byte var2, int var3, byte[] var4);

   void onMoveError(byte var1, byte var2, String var3);

   void onSetMoneyError(String var1);

   void onStartArmy(byte var1, byte var2, short[] var3, short[] var4);

   void onMoveArmy(byte var1, short var2, short var3);

   void onUpdateXY(byte var1, short var2, short var3);

   void onFireArmy(byte var1, byte var2, short var3, short var4, short var5, byte var6);

   void onUpdateHP(byte var1, short var2);

   void onNextTurn(byte var1);

   void onWind(byte var1);

   void onUseItem(int var1, byte var2);

   void onChooseGun(int var1, byte var2);

   void onMapChanged(byte var1);

   void onChangeTeam(int var1, byte var2);

   void onBonusMoney(int var1, int var2, int var3);

   void onURL(String var1, String var2, byte var3);

   void onGetImage(short var1, Image var2);

   void onXepHanglist(byte var1, byte var2, Vector var3, String var4);
}
