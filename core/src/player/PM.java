package player;

import CLib.mGraphics;
import coreLG.CCanvas;
import coreLG.TerrainMidlet;
import effect.Camera;
import item.BM;
import map.MM;
import model.CRes;
import model.PlayerInfo;
import network.GameService;
import screen.CScreen;
import screen.GameScr;
import screen.PrepareScr;

public class PM {
    public static int MAX_PLAYER;
    public static int NUMB_PLAYER;
    public static CPlayer[] p;
    public static int[] npXsend;
    public static int[] npYsend;
    public static int[] npNumSend;
    public static byte curP;
    static int curUpdateP;
    public static short[] xPResult;
    public static short[] yPResult;
    public int playerCount;
    public int allCount;
    int planeX;
    int planeY;
    public static int tKC;
    public static int deltaYKC;

    public void init() {
        p = new CPlayer[MAX_PLAYER];
        npXsend = new int[MAX_PLAYER];
        npYsend = new int[MAX_PLAYER];
        npNumSend = new int[MAX_PLAYER];
        xPResult = new short[MAX_PLAYER];
        yPResult = new short[MAX_PLAYER];
    }

    public void insertPlayer(short x, short y, byte index, PlayerInfo player, int currHP) {
        boolean isCom = TerrainMidlet.myInfo.IDDB != player.IDDB;
        p[index] = new CPlayer(player.IDDB, index, x, y, isCom, 0, player.gun, player.myEquip, player.maxHP);
        p[index].name = player.name;
        p[index].nQuanHam = player.nQuanHam2;
        p[index].hp = currHP;
        p[index].hpRectW = currHP * 25 / p[index].maxhp;
        p[index].clanIcon = player.clanIcon;
        ++this.playerCount;
    }

    public void initPlayer(short[] pX, short[] pY, short[] maxHP) {
        this.playerCount = 0;
        this.allCount = 0;
        int num = 8;
        if (PrepareScr.currLevel == 7) {
            num = NUMB_PLAYER;
        }

        int i;
        for (i = 0; i < num; ++i) {
            p[i] = null;
            if (pX[i] != -1) {
                PlayerInfo pi = (PlayerInfo) CCanvas.prepareScr.playerInfos.elementAt(i);
                boolean isCom = TerrainMidlet.myInfo.IDDB != pi.IDDB;
                if (!pi.isBoss) {
                    p[i] = new CPlayer(pi.IDDB, (byte) i, pX[i], pY[i], isCom, i % 2 == 0 ? 2 : 0, pi.gun, pi.myEquip, maxHP[i]);
                    p[i].clanIcon = pi.clanIcon;
                    p[i].equip = pi.myEquip;
                } else {
                    p[i] = new Boss(pi.IDDB, (byte) i, pX[i], pY[i], isCom, i % 2 == 0 ? 2 : 0, pi.gun, maxHP[i]);
                }

                p[i].name = pi.name;
                p[i].nQuanHam = pi.nQuanHam2;
                p[i].maxhp = maxHP[i];
                if (!isCom) {
                    p[i].item = CCanvas.prepareScr.copyItemCurrent();
                    GameScr.myIndex = (byte) i;
                }
            }

            ++this.playerCount;
        }

        this.allCount = this.playerCount;

        for (i = 0; i < num; npNumSend[i] = i++) {
            npXsend[i] = -1;
            npYsend[i] = -1;
        }

    }

    public String getPlayerNameFromID(int id) {
        for (int i = 0; i < p.length; ++i) {
            if (p[i].IDDB == id) {
                return p[i].name;
            }
        }

        return "";
    }

    public CPlayer getPlayerFromID(int id) {
        if (p == null) {
            return null;
        } else {
            for (int i = 0; i < p.length; ++i) {
                if (p[i] != null && p[i].IDDB == id) {
                    return p[i];
                }
            }

            return null;
        }
    }

    public void initBoss(short[] bX, short[] bY) {
        for (int i = 0; i < bX.length; ++i) {
            p[i + this.allCount] = null;
            if (bX[i] != -1) {
                PlayerInfo pi = (PlayerInfo) CCanvas.prepareScr.bossInfos.elementAt(i);
                boolean isCom = TerrainMidlet.myInfo.IDDB != pi.IDDB;
                p[i + this.allCount] = new Boss(pi.IDDB, (byte) 1, bX[i], bY[i], isCom, 2, pi.gun, pi.maxHP);
                p[i + this.allCount].name = pi.name;
                p[i + this.allCount].hp = pi.maxHP;
                p[i + this.allCount].maxhp = pi.maxHP;
                p[i + this.allCount].team = false;
                p[i + this.allCount].index = pi.index;
            }
        }

        this.allCount += CCanvas.prepareScr.bossInfos.size();
        CCanvas.prepareScr.bossInfos.removeAllElements();
    }

    public static void getXYResult() {
        for (int i = 0; i < p.length; ++i) {
            CPlayer player = p[i];
            if (player != null) {
                xPResult[i] = (short) p[i].x;
                yPResult[i] = (short) p[i].y;
            } else {
                xPResult[i] = -1;
                yPResult[i] = -1;
            }
        }

    }

    public static int getIndexByIDDB(int iddb) {
        for (int i = 0; i < 8; ++i) {
            PlayerInfo pi = (PlayerInfo) CCanvas.prepareScr.playerInfos.elementAt(i);
            if (pi.IDDB == iddb) {
                return i;
            }
        }

        return -1;
    }

    public static CPlayer getCurPlayer() {
        return p[curP] != null ? p[curP] : null;
    }

    public static CPlayer getMyPlayer() {
        return p[GameScr.myIndex];
    }

    public static CPlayer getPlayerByIndex(int index) {
        return p[index];
    }

    public static CPlayer getPlayerByIDDB(int IDDB) {
        if (p == null) {
            return null;
        } else {
            for (int i = 0; i < p.length; ++i) {
                if (p[i] != null && p[i].IDDB == IDDB) {
                    return p[i];
                }
            }

            return null;
        }
    }

    public static CPlayer findPlayerByIndex(int index) {
        if (p == null) {
            return null;
        } else {
            for (int i = 0; i < p.length; ++i) {
                if (p[i] != null && p[i].index == index) {
                    return p[i];
                }
            }

            return null;
        }
    }

    public void onPointerPressed(int xScreen, int yScreen, int index) {
        p[GameScr.myIndex].onPointerPressed(xScreen, yScreen, index);
    }

    public void onPointerDrag(int xScreen, int yScreen, int index) {
        p[GameScr.myIndex].onPointerDrag(xScreen, yScreen, index);
    }

    public void onPointerDragRighCorner(int xScreen, int yScreen, int index) {
    }

    public void onPointerHold(int xScreen, int yScreen, int index) {
        p[GameScr.myIndex].onPointerHold(xScreen, yScreen, index);
    }

    public void onPointerReleased(int x, int y, int index) {
        p[GameScr.myIndex].onPointerReleased(x, x, index);
    }

    public void flyAnimation() {
        if (CCanvas.gameTick % 2 == 0) {
            ++tKC;
            if (tKC == 10) {
                deltaYKC = 0;
                tKC = 0;
            }

            if (tKC <= 5) {
                ++deltaYKC;
            } else {
                --deltaYKC;
            }
        }

    }

    public void update() {
        byte nPstand = 0;
        boolean pStillFalling = false;

        for (int i = 0; i < p.length; ++i) {
            if (p[i] != null) {
                curUpdateP = i;
                p[i].update();
                if (p[i].isAllowSendPosAfterShoot) {
                    npNumSend[nPstand] = i;
                    npXsend[nPstand] = p[i].x;
                    npYsend[nPstand] = p[i].y;
                    ++nPstand;
                }

                if (p[i].falling && p[i].getState() != 5) {
                    pStillFalling = true;
                }
            }
        }

        this.flyAnimation();
        if (BM.allSendENDSHOOT && !pStillFalling) {
            GameService.gI().shootResult();
            getCurPlayer().shootFrame = false;
            BM.allSendENDSHOOT = false;
            GameService.gI().holeInfo(MM.vHoleInfo);
        }

    }

    public static boolean isLand(byte index) {
        CPlayer player = getPlayerByIndex(index);
        return GameScr.mm.isLand(player.x, player.y);
    }

    public void paint(mGraphics g) {
        for (int i = 0; i < p.length; ++i) {
            if (p[i] != null && p[i].x + 75 > Camera.x && p[i].x - 75 < Camera.x + CScreen.w) {
                if (GameScr.cantSee) {
                    if (i == GameScr.myIndex) {
                        p[i].paint(g);
                    }
                } else {
                    p[i].paint(g);
                }
            }
        }

    }

    public boolean isYourTurn() {
        return curP == GameScr.myIndex;
    }

    public boolean isMyPlayerUpdate() {
        return curUpdateP == GameScr.myIndex;
    }

    public void setNextPlayer(byte whoNext) {
        if (CRes.isNullOrEmpty(GameScr.res)) {
            int i;
            for (i = 0; i < p.length; ++i) {
                if (p[i] != null) {
                    p[i].isPaintCountDown = false;
                }
            }

            p[whoNext].isPaintCountDown = true;
            p[whoNext].active = true;
            if (p[whoNext].getState() != 8) {
                p[whoNext].setState((byte) 0);
                p[whoNext].checkAngleForSprite();
            }

            p[whoNext].movePoint = 0;
            p[whoNext].itemUsed = -1;
            if (p[whoNext].isUsedItem) {
                p[whoNext].isUsedItem = false;
            }

            if (p[whoNext].gun != 15) {
                p[whoNext].falling = true;
            }

            if (p[whoNext].isStopWind) {
                GameScr.changeWind(0, 0);
            }

            for (i = 0; i < p.length; ++i) {
                if (p[i] != null) {
                    p[i].shootFrame = false;
                    if (p[i].getState() != 5) {
                        p[i].isAllowSendPosAfterShoot = false;
                    }

                    BM.nBum = 0;
                    if (i == GameScr.myIndex) {
                        p[i].resetXYwhenNEXTTURN();
                        if (!p[i].chophepGuiUpdateXY) {
                            GameService.gI().move((short) p[i].x, (short) p[i].y);
                            p[i].chophepGuiUpdateXY = true;
                        }
                    }
                }
            }

            GameScr.time.resetTime();
            curP = whoNext;
            if (curP == GameScr.myIndex) {
                if (p[curP].cantSee) {
                    GameScr.cantSee = true;
                } else {
                    GameScr.cantSee = false;
                }

                CPlayer.isShooting = false;
            }

            if (GameScr.cam != null) {
                GameScr.cam.setPlayerMode(whoNext);
            }

        }
    }

    public void movePlayer(int whoMove, short x, short y) {
        p[whoMove].nextx = x;
        p[whoMove].nexty = y;
        p[whoMove].isMove = true;
        p[whoMove].tMove = 0;
        if (whoMove == GameScr.myIndex) {
            p[whoMove].resetLastUpdateXY(x, y);
        }

    }

    public void flyTo(int whoFly, short xF, short yF) {
        p[whoFly].flyToPoint(xF, yF);
    }

    public void updatePlayerXY(int whoUpdateXY, short x, short y) {
        p[whoUpdateXY].x = x;
        p[whoUpdateXY].y = y;
        p[whoUpdateXY].nextx = x;
        p[whoUpdateXY].lastx = x;
        p[whoUpdateXY].nexty = y;
        if (whoUpdateXY == GameScr.myIndex) {
            p[whoUpdateXY].resetLastUpdateXY(x, y);
        }

    }

    public void setPlayerAfterSetWin(boolean teamWin) {
        for (int i = 0; i < p.length; ++i) {
            if (p[i] != null) {
                p[i].active = false;
                if (p[i].hp > 0) {
                    if (p[i].team && teamWin) {
                        p[i].setWin();
                    } else if (!p[i].team && !teamWin) {
                        p[i].setWin();
                    }

                    if (i == GameScr.myIndex) {
                        CRes.out("After Set Win");
                    }
                }
            }
        }

    }

    public void setPlayerAfterDraw() {
        for (int i = 0; i < p.length; ++i) {
            if (p[i] != null) {
                p[i].active = false;
                if (i == GameScr.myIndex) {
                    CRes.out("After Draw");
                }
            }
        }

    }

    public void onClearMap() {
    }
}
