package effect;

import CLib.mGraphics;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import coreLG.CCanvas;
import item.BM;
import item.Bullet;
import map.MM;
import model.CRes;
import player.PM;
import screen.CScreen;
import screen.GameScr;

public class Camera {
    public static final byte FREE_MODE = 0;
    public static final byte PLAYER_MODE = 1;
    static final byte BULLET_MODE = 2;
    static final byte AIRPLANE_MODE = 3;
    static final byte TARGETPOINT_MODE = 4;
    static final byte TARGETPOINT_MODE_NORETRICT = 5;
    static final byte LAZER_MODE = 6;
    static final byte METEOR_MODE = 7;
    static final byte MISSILE_RAIN_MODE = 8;
    public static byte mode;
    public static int shaking = 0;
    int vx = 15;
    int vy = 15;
    public static int x = 0;
    public static int y = 0;
    public static int startx = 0;
    public static int starty = 0;
    public static int cameraW = 0;
    public static int cameraH = 0;
    int player;
    Bullet bullet;
    int count = 0;
    public int dx;
    public int dy;
    public static int dx2;
    public static int dy2;
    int indexX;
    int indexY;
    int setIndexX;
    int setIndexY;
    public static OrthographicCamera camera;
    int deltaY = 20;

    public Camera() {
        startx = 0;
        starty = 0;
    }

    public Camera(int x, int y) {
        this.setxy(x, y);
        startx = x;
        starty = y;
        this.setFreeMode();
    }

    public Camera(int x, int y, int w, int h) {
        this.setxy(x, y);
        startx = x;
        starty = y;
        cameraW = w;
        cameraH = h;
        this.setFreeMode();
    }

    public void init(OrthographicCamera _camera) {
        shaking = 0;
        this.setFreeMode();
    }

    public void setxy(int x, int y) {
        Camera.x = x;
        Camera.y = y;
    }

    public void setFreeMode() {
        mode = 0;
        dx2 = 0;
        dy2 = 0;
    }

    public void setPlayerMode(int target) {
        mode = 1;
        this.player = target;
    }

    public void setBulletMode(Bullet target) {
        mode = 2;
        this.bullet = target;
    }

    public void setMeteorMode(Bullet target) {
        mode = 7;
        this.bullet = target;
    }

    public void setAirPlaneMode() {
        mode = 3;
    }

    public void setLazerMode(Bullet target) {
        mode = 6;
        this.bullet = target;
    }

    public void setMRainMode(Bullet target) {
        mode = 8;
        this.bullet = target;
    }

    public void setTargetPointMode(int index_x, int index_y) {
        mode = 4;
        this.setIndexX = index_x - CScreen.w / 2;
        this.setIndexY = index_y - CScreen.h / 2;
    }

    public void setTargetPointModeNoRetrict(int index_x, int index_y) {
        mode = 5;
        this.setIndexX = index_x - CScreen.w / 2;
        this.setIndexY = index_y - CScreen.h / 2;
    }

    public void setZoom(float zoom) {
        camera.zoom = zoom;
    }

    public Matrix4 Combine() {
        return camera.combined;
    }

    public void update() {
        startx = x;
        starty = y;
        this.indexX = 0;
        this.indexY = 0;
        switch (mode) {
            case 1:
                if (PM.p[this.player] != null) {
                    if (PM.p[this.player].look == 0) {
                        this.indexX = PM.p[this.player].x - 40 - CScreen.w / 2;
                    } else if (PM.p[this.player].look == 2) {
                        this.indexX = PM.p[this.player].x + 40 - CScreen.w / 2;
                    }

                    this.indexY = PM.p[this.player].y - CScreen.h / 2 - 12;
                    if (GameScr.pm.isYourTurn()) {
                        if (PM.p[this.player].getState() != 1) {
                            this.checkIndex(4);
                        }
                    } else {
                        this.checkIndex(4);
                    }
                }
                break;
            case 2:
                if (this.bullet != null && this.bullet.paintCount < this.bullet.yPaint.length && (this.bullet.type != 37 || this.bullet.angle != -90 && this.bullet.angle != 270 || this.bullet.yPaint[this.bullet.paintCount] > -300)) {
                    this.indexX = this.bullet.xPaint[this.bullet.paintCount] - CScreen.w / 2;
                    this.indexY = this.bullet.yPaint[this.bullet.paintCount] - CScreen.h / 2;
                    if (this.bullet.type != 37) {
                        this.checkIndex(2);
                    } else {
                        this.checkIndex(1);
                    }
                }
                break;
            case 3:
                this.indexX = BM.airPlaneX - CScreen.w / 4;
                this.indexY = BM.airPlaneY - CScreen.h / 2;
                this.checkIndex(2);
                break;
            case 4:
                this.indexX = this.setIndexX;
                this.indexY = this.setIndexY;
                this.checkIndex(2);
                break;
            case 5:
                this.indexX = this.setIndexX;
                this.indexY = this.setIndexY;
                this.checkIndex(3);
                break;
            case 6:
                this.indexX = this.bullet.xPaint[this.bullet.paintCount] - CScreen.w / 2;
                this.indexY = this.bullet.yPaint[this.bullet.paintCount] - CScreen.h / 3;
                this.checkIndex(1);
                break;
            case 7:
                this.indexX = this.bullet.xPaint[this.bullet.paintCount] - CScreen.w / 2;
                this.indexY = this.bullet.yPaint[this.bullet.paintCount] - CScreen.h / 3;
                this.checkIndex(1);
                break;
            case 8:
                this.indexX = PM.getCurPlayer().x - CScreen.w / 2;
                this.indexY = this.bullet.yPaint[this.bullet.paintCount] - CScreen.h / 2;
                this.checkIndex(2);
        }

        if (shaking != 0) {
            if (shaking == 1) {
                x += CRes.random(-5, 5);
                y += CRes.random(-5, 5);
            } else if (shaking == 2) {
                x += CRes.random(-20, 20);
                y += CRes.random(-20, 20);
            } else if (shaking == 3) {
                if (CCanvas.gameTick % 3 == 0) {
                    y += this.deltaY;
                } else {
                    y -= this.deltaY;
                }

                if (this.count % 2 == 0) {
                    --this.deltaY;
                }

                if (this.deltaY < 0) {
                    this.deltaY = 0;
                }
            }

            ++this.count;
            if (this.count > (shaking != 3 ? 10 : 30)) {
                this.deltaY = 20;
                shaking = 0;
                this.count = 0;
            }
        }

        if (mode != 5) {
            restrict(0, MM.mapWidth, -1000, MM.mapHeight);
        }

    }

    public void mainLoop() {
        switch (mode) {
            case 0:
                x += dx2;
                if (dx2 != 0) {
                    dx2 = 0;
                }

                y += dy2;
                if (dy2 != 0) {
                    dy2 = 0;
                }
            default:
                if (mode != 5) {
                    restrict(0, MM.mapWidth, -1000, MM.mapHeight);
                }

        }
    }

    void checkIndex(int rangeBit) {
        if (x != this.indexX) {
            this.dx = x - this.indexX;
            x -= this.dx >> rangeBit;
        }

        if (y != this.indexY) {
            this.dy = y - this.indexY;
            y -= this.dy >> rangeBit;
        }

    }

    public static void restrict(int left, int right, int up, int down) {
        if (y < up) {
            y = up;
        }

        if (y > down - CScreen.h) {
            y = down - CScreen.h;
            starty = y;
        }

        if (x < left) {
            x = left;
        }

        if (x > right - CScreen.w) {
            x = right - CScreen.w;
        }

    }

    public static void translate(mGraphics g) {
        g.translate(-x, -y);
    }

    public static void reTranslate(mGraphics g) {
        g.translate(-g.getTranslateX(), -g.getTranslateY());
    }

    public static String getMode() {
        if (mode == 0) {
            return "FREE_MODE";
        } else if (mode == 1) {
            return "PLAYER_MODE";
        } else if (mode == 2) {
            return "BULLET_MODE";
        } else if (mode == 3) {
            return "AIRPLANE_MODE";
        } else if (mode == 4) {
            return "TARGETPOINT_MODE";
        } else if (mode == 5) {
            return "TARGETPOINT_MODE_NORETRICT";
        } else if (mode == 6) {
            return "LAZER_MODE";
        } else if (mode == 7) {
            return "METEOR_MODE";
        } else {
            return mode == 8 ? "MISSILE_RAIN_MODE" : "NONE";
        }
    }

    public void onClearCamera() {
        this.bullet = null;
        this.setFreeMode();
    }
}
