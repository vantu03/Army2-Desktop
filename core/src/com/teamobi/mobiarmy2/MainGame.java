package com.teamobi.mobiarmy2;

import CLib.MapKey;
import CLib.mGraphics;
import CLib.mSystem;
import Debug.Debug;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import coreLG.CCanvas;
import effect.Camera;
import java.awt.event.KeyEvent;
import model.CRes;
import model.IAction;
import network.Command;
import network.GameLogicHandler;
import network.MessageHandler;
import network.Session_ME;
import player.PM;
import screen.BoardListScr;
import screen.CScreen;
import screen.ChangePlayerCSr;
import screen.GameScr;
import screen.LoginScr;
import screen.MenuScr;
import screen.ServerListScreen;

public class MainGame implements ApplicationListener {
    public static MainGame me;
    private InputMultiplexer inputMultiplexer;
    private MainGame.MyInputProcessor inputProcessor;
    private MainGame.MyGestureHandler gestureHandler;
    public boolean isIos78 = false;
    public float zoom = 1.0F;
    private mGraphics _graphic;
    public ShapeRenderer shapeRenderer;
    public OrthographicCamera camera;
    private long timeUpdate;
    private int errup = 0;
    private int errrender = 0;
    public static String mainThreadName;
    public static boolean isStart;
    public static boolean isPause = false;
    public static int wOld;
    public static int hOld;
    public static boolean inputHold;
    public static int keyHold;
    public static int keyHoldX;
    public static int keyHoldY;
    public static int indexHold;
    public static boolean touchDrag;
    public Debug _debug;
    long timeRunInBackGround;

    public void create() {
        me = this;
        this._debug = new Debug();
        this._debug.setup();
        CCanvas.isDebugging();
        if (Thread.currentThread().getName() != "Main") {
            Thread.currentThread().setName("Main");
        }

        mainThreadName = Thread.currentThread().getName();
        this.initaliseInputProcessors();
        MainGame.MyInputProcessor inputProcessor = new MainGame.MyInputProcessor((MainGame.MyInputProcessor) null);
        Gdx.input.setInputProcessor(inputProcessor);
        MapKey.load();
        this.camera = new OrthographicCamera((float) getWidth(), (float) getHeight());
        this.camera.setToOrtho(true);
        GameScr.cam = new Camera();
        GameScr.cam.init(me.camera);
        this._graphic = new mGraphics(new SpriteBatch());
        GameMidlet.instance.initGame();
        CRes.init();
        inputHold = false;
        if (CCanvas.isAndroid()) {
            Gdx.input.setCatchBackKey(true);
        }
    }

    public void initaliseInputProcessors() {
        this.inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(this.inputMultiplexer);
        this.inputProcessor = new MainGame.MyInputProcessor((MainGame.MyInputProcessor) null);
        this.gestureHandler = new MainGame.MyGestureHandler((MainGame.MyGestureHandler) null);
        this.inputMultiplexer.addProcessor(new GestureDetector(this.gestureHandler));
        this.inputMultiplexer.addProcessor(this.inputProcessor);
    }

    public void render() {
        if (!isPause) {
            this.errrender = 0;
            Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
            this.errrender = 1;
            Gdx.gl.glClear(16384);
            this.errrender = 111;
            Gdx.gl.glEnable(3042);
            this.errrender = 112;
            Gdx.gl.glBlendFunc(770, 771);
            this.errrender = 114;
            Gdx.gl.glDisable(3042);
            this.errrender = 2;
            this._graphic.g.setProjectionMatrix(this.camera.combined);
            this.errrender = 3;
            this.camera.zoom = this.zoom;
            this.errrender = 4;
            this.camera.update();
            this.errrender = 5;
            this.mainLoop();
            if (mSystem.currentTimeMillis() - this.timeUpdate > 10L) {
                this.errrender = 9;
                this.subLoop();
                this.timeUpdate = mSystem.currentTimeMillis() + 10L;
                this.errrender = 19;
            }

            this._graphic.begin();
            this.errrender = 10;
            GameMidlet.gameCanvas.paint(this._graphic);
            this._debug.paint(this._graphic);
            this.errrender = 11;
            this._graphic.end();
            this.errrender = 12;
        }
    }

    private void mainLoop() {
        this.errup = 1;
        Session_ME.update();
        this.errup = 2;
        if (mSystem.isOnConnectFail) {
            this.errup = 3;
            MessageHandler.gI().onConnectionFail();
            mSystem.isOnConnectFail = false;
            this.errup = 4;
        }

        this.errup = 5;
        if (mSystem.isOnConnectOK) {
            this.errup = 6;
            MessageHandler.gI().onConnectOK();
            CCanvas.clearKeyHold();
            mSystem.isOnConnectOK = false;
            this.errup = 7;
        }

        this.errup = 8;
        if (mSystem.isOnDisconnect) {
            this.errup = 9;
            MessageHandler.gI().onDisconnected();
            mSystem.isOnDisconnect = false;
            this.errup = 10;
        }

        this.errup = 11;
        if (mSystem.isOnLoginFail) {
            this.errup = 12;
            mSystem.isOnLoginFail = false;
            mSystem.reasonFail = "";
            this.errup = 13;
        }

        this.errup = 14;
        if (GameMidlet.gameCanvas != null) {
            if (inputHold) {
                GameMidlet.gameCanvas.onPointerHolder(keyHoldX, keyHoldY, indexHold);
            }

            GameMidlet.gameCanvas.mainLoop();
        }

    }

    private void subLoop() {
        this.errup = 0;
        if (GameMidlet.gameCanvas != null) {
            GameMidlet.gameCanvas.update();
        }

    }

    public static int getWidth() {
        return Gdx.graphics.getWidth();
    }

    public static int getHeight() {
        return Gdx.graphics.getHeight();
    }

    public void resize(int width, int height) {
        //resizeFuntion(width, height);
    }

    private void resizeFuntion(int width, int height) {
        if (CCanvas.isPc()) {
            int wmin = 640;
            int hmin = 320;
            if (wOld <= 0 || hOld <= 0) {
                wOld = wmin;
                hOld = hmin;
            }

            int wmax = wmin * 2;
            int hmax = hmin * 2;
            int percentX = CRes.abs(width - wOld) * 100 / (wmax - wmin);
            int percentY = CRes.abs(height - hOld) * 100 / (hmax - hmin);
            int dxfix;
            if (percentX > percentY) {
                dxfix = percentX * (hmax - hmin) / 100 * (width - wOld > 0 ? 1 : -1);
                height = hOld + dxfix;
            } else if (percentX < percentY) {
                dxfix = percentY * (wmax - wmin) / 100 * (height - hOld > 0 ? 1 : -1);
                width = wOld + dxfix;
            }

            if (width < wmin) {
                width = wmin;
            } else if (width > wmax) {
                width = wmax;
            }

            if (height < hmin) {
                height = hmin;
            } else if (height > hmax) {
                height = hmax;
            }

            Gdx.graphics.setWindowedMode(width, height);
            wOld = width;
            hOld = height;
        }

    }

    public void pause() {
        this.timeRunInBackGround = mSystem.currentTimeMillis();
        isPause = true;
    }

    public void resume() {
        isPause = false;
    }

    public void dispose() {
        Session_ME.gI().close(1111);
    }

    public void vibrate(int milliseconds) {
        Gdx.input.vibrate(milliseconds);
    }

    public static int getNumberFingerOnScreen() {
        int number = 0;
        if (Gdx.input.isTouched()) {
            for (int i = 0; i < 10; ++i) {
                if (Gdx.input.isTouched(i)) {
                    ++number;
                }
            }
        }

        return number;
    }

    private class MyGestureHandler implements GestureDetector.GestureListener {
        public float initialScale;

        private MyGestureHandler() {
            this.initialScale = 1.0F;
        }

        public boolean touchDown(float x, float y, int pointer, int button) {
            return false;
        }

        public boolean zoom(float initialDistance, float distance) {
            return true;
        }

        public boolean tap(float x, float y, int count, int button) {
            return false;
        }

        public boolean longPress(float x, float y) {
            return false;
        }

        public boolean fling(float velocityX, float velocityY, int button) {
            return false;
        }

        public boolean pan(float x, float y, float deltaX, float deltaY) {
            return false;
        }

        public boolean panStop(float x, float y, int pointer, int button) {
            return false;
        }

        public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
            return false;
        }

        public void pinchStop() {
        }

        // $FF: synthetic method
        MyGestureHandler(MainGame.MyGestureHandler var2) {
            this();
        }
    }

    private class MyInputProcessor implements InputProcessor {
        int zoomIn;

        private MyInputProcessor() {
            this.zoomIn = 1;
        }

        public boolean scrolled(int amount) {
            if (amount > 0) {
            }

            if (amount < 0) {
            }

            return true;
        }

        public boolean keyDown(int keycode) {
            System.out.print("keyDown="+keycode);
            if (CCanvas.currentDialog != null) {
                if (keycode == 131 && CCanvas.currentDialog.left != null) {
                    CCanvas.currentDialog.left.action.perform();
                }
                if (keycode == 132 && CCanvas.currentDialog.right != null) {
                    CCanvas.currentDialog.right.action.perform();
                }
                if (keycode == 66 && CCanvas.currentDialog.center != null) {
                    CCanvas.currentDialog.center.action.perform();
                }
            } else {
                if (keycode == 19) {
                    CCanvas.keyPressed[2] = true;
                }
                if (keycode == 20) {
                    CCanvas.keyPressed[8] = true;
                }
                if (keycode == 66) {
                    CCanvas.keyPressed[5] = true;
                }
                if (keycode == 21) {
                    CCanvas.keyPressed[4] = true;
                }
                if (keycode == 22) {
                    CCanvas.keyPressed[6] = true;
                }
                if (CCanvas.menu != null && CCanvas.menu.showMenu) {
                    CCanvas.menu.onPointerPressed(0, 0, 0);
                } if (CCanvas.curScr != null) {
                    CCanvas.curScr.onPointerPressed(0, 0, 0);
                }
                if (CCanvas.menu != null && CCanvas.menu.showMenu) {
                    if (keycode == 131 || keycode == 66) {
                        CCanvas.menu.showMenu = false;
                        ((Command)CCanvas.menu.menuItems.get(CCanvas.menu.menuSelectedItem)).action.perform();
                    }
                    if (keycode == 132) {
                        CCanvas.menu.showMenu = false;
                    }
                } else if (CCanvas.curScr != null) {
                    if ((keycode == 131 || (keycode == 66 && CCanvas.curScr.center == null && CCanvas.curScr instanceof MenuScr)) && CCanvas.curScr.left != null) {
                        CCanvas.curScr.left.action.perform();
                    } else if (keycode == 132 && CCanvas.curScr.right != null) {
                        CCanvas.curScr.right.action.perform();
                    } else if (keycode == 66 && CCanvas.curScr.center != null) {
                        CCanvas.curScr.center.action.perform();
                    }
                }
            }
            if (keycode == 4) {
                GameMidlet.gameCanvas.backAndroid();
                return false;
            } else {
                if (CCanvas.isDebugging() && keycode == 29) {
                    CRes.ONCal();
                }

                return false;
            }
        }

        public boolean keyUp(int keycode) {
            System.out.println("keyUp="+keycode);
            int k = MapKey.map(keycode);
            GameMidlet.gameCanvas.keyReleased(k);
            MainGame.keyHold = -1;
            return false;
        }

        public boolean keyHold(int keycode) {
            GameMidlet.gameCanvas.keyHold(keycode);
            return false;
        }

        public boolean keyTyped(char character) {
            System.out.print("keyTyped="+(int)character);
            GameMidlet.gameCanvas.keyHold(character);
            return false;
        }

        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            MainGame.inputHold = true;
            int oy;
            if (MainGame.this.isIos78) {
                int rotation = Gdx.input.getRotation();
                if (rotation == 90) {
                    oy = screenX;
                    screenX = screenY;
                    screenY = Gdx.graphics.getHeight() - oy;
                } else if (rotation == 270) {
                    oy = screenY;
                    screenY = screenX;
                    screenX = Gdx.graphics.getWidth() - oy;
                }
            }

            if (pointer < 2) {
                Vector3 touch = new Vector3((float) screenX, (float) screenY, 0.0F);
                MainGame.this.camera.unproject(touch);
                oy = (int) touch.x - screenX;
                int delY = (int) touch.y - screenY;
                GameMidlet.gameCanvas.onPointerPressed((screenX + oy) / mGraphics.zoomLevel, (screenY + delY) / mGraphics.zoomLevel, pointer, button);
                MainGame.keyHoldX = (screenX + oy) / mGraphics.zoomLevel;
                MainGame.keyHoldY = (screenY + delY) / mGraphics.zoomLevel;
                MainGame.indexHold = 1;
            }

            return false;
        }

        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            MainGame.inputHold = false;
            int oy;
            if (MainGame.this.isIos78) {
                int rotation = Gdx.input.getRotation();
                if (rotation == 90) {
                    oy = screenX;
                    screenX = screenY;
                    screenY = Gdx.graphics.getHeight() - oy;
                } else if (rotation == 270) {
                    oy = screenY;
                    screenY = screenX;
                    screenX = Gdx.graphics.getWidth() - oy;
                }
            }

            if (pointer < 2) {
                Vector3 touch = new Vector3((float) screenX, (float) screenY, 0.0F);
                MainGame.this.camera.unproject(touch);
                oy = (int) touch.x - screenX;
                int delY = (int) touch.y - screenY;
                MainGame.touchDrag = false;
                GameMidlet.gameCanvas.onPointerReleased((screenX + oy) / mGraphics.zoomLevel, (screenY + delY) / mGraphics.zoomLevel, pointer, button);
            }

            return false;
        }

        @Override
        public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        public boolean touchDragged(int screenX, int screenY, int pointer) {
            MainGame.inputHold = true;
            MainGame.touchDrag = true;
            int oy;
            if (MainGame.this.isIos78) {
                int rotation = Gdx.input.getRotation();
                if (rotation == 90) {
                    oy = screenX;
                    screenX = screenY;
                    screenY = Gdx.graphics.getHeight() - oy;
                } else if (rotation == 270) {
                    oy = screenY;
                    screenY = screenX;
                    screenX = Gdx.graphics.getWidth() - oy;
                }
            }

            if (pointer < 2) {
                Vector3 touch = new Vector3((float) screenX, (float) screenY, 0.0F);
                MainGame.this.camera.unproject(touch);
                oy = (int) touch.x - screenX;
                int delY = (int) touch.y - screenY;
                MainGame.keyHoldX = (screenX + oy) / mGraphics.zoomLevel;
                MainGame.keyHoldY = (screenY + delY) / mGraphics.zoomLevel;
                GameMidlet.gameCanvas.onPointerDragged((screenX + oy) / mGraphics.zoomLevel, (screenY + delY) / mGraphics.zoomLevel, pointer);
            }

            return false;
        }

        public boolean mouseMoved(int screenX, int screenY) {
            Debug.xM = screenX;
            Debug.yM = screenY;
            return false;
        }

        public boolean scrolled(float amountX, float amountY) {
            return false;
        }

        // $FF: synthetic method
        MyInputProcessor(MainGame.MyInputProcessor var2) {
            this();
        }
    }
}
