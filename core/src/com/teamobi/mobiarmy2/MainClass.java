package com.teamobi.mobiarmy2;

import CLib.EasingFunction;
import CLib.Image;
import CLib.LibSysTem;
import CLib.mGraphics;
import CLib.mImage;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import coreLG.CCanvas;
import coreLG.CONFIG;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import model.FilePack;
import model.IAction2;

public class MainClass extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;
    MainClass.MyInputProcessor inputProcessor;
    private InputMultiplexer inputMultiplexer;
    public static MainClass.Point mousePoint;
    public static MainClass.Point mousePointOld;
    ShapeRenderer shapeRenderer;
    OrthographicCamera camera;
    private Sprite sprite;
    public static Texture texture;
    public static SpriteBatch _spriteBachl;
    private mGraphics _graphic;
    public static int zoomlevel = 1;
    public static int scaleX = 1;
    public static int scaleY = 1;
    public static float speed = 1.0F;
    public static long timeDeltaTem;
    public static long timeDelta;
    int width;
    int height;
    int errrender = 0;

    public void create() {
        this._graphic = new mGraphics(new SpriteBatch());
        this.shapeRenderer = new ShapeRenderer();
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
        this.sprite = new Sprite(new Texture(Gdx.files.internal("badlogic.jpg")));

        try {
            this.onCheckDataInputStreamAnyFile();
        } catch (IOException var2) {
            var2.printStackTrace();
        }

        _spriteBachl = new SpriteBatch();
    }

    private void onCheckData() {
        FileInputStream input = null;

        try {
            input = new FileInputStream("D:\\testout.txt");
            DataInputStream inst = new DataInputStream(input);
            int count = input.available();
            byte[] ary = new byte[count];
            inst.read(ary);
            byte[] var8 = ary;
            int var7 = ary.length;

            for (int var6 = 0; var6 < var7; ++var6) {
                byte bt = var8[var6];
                char k = (char) bt;
                System.out.print(k + "-");
            }
        } catch (FileNotFoundException var10) {
            var10.printStackTrace();
        } catch (IOException var11) {
            var11.printStackTrace();
        }

    }

    private void onCheckDataInputStream() {
        try {
            FilePack filePack = new FilePack(CCanvas.getClassPathConfig(CONFIG.PATH_EFFECT + "effect"));
            if (filePack != null) {
                filePack.loadImage("ex3.png", new IAction2() {
                    public void perform(Object object) {
                        mImage _mImage = new mImage((Image) object);
                        MainClass.texture = _mImage.image.texture;
                    }
                });
            }
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    private void onCheckDataInputStreamAnyFile() throws IOException {
        InputStream input = null;
        DataInputStream dis = null;

        try {
            input = Gdx.files.local(LibSysTem.res + "/barMove_x2.png").read();
            dis = new DataInputStream(input);
            int count = input.available();
            byte[] arr = new byte[count];
            dis.read(arr);
            byte[] var8 = arr;
            int var7 = arr.length;

            for (int var6 = 0; var6 < var7; ++var6) {
                byte bt = var8[var6];
                char k = (char) bt;
                System.out.print(k + "-");
            }
        } catch (IOException var13) {
        } finally {
            dis.close();
        }

    }

    public void render() {
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
        this.errrender = 4;
        this.camera.update();
        this.errrender = 5;
        this.batch.begin();
        this.errrender = 10;
        this.batch.draw(texture, 0.0F, 0.0F);
        this.errrender = 11;
        this.batch.end();
        this.errrender = 12;
    }

    public void dispose() {
        if (this.batch != null) {
            this.batch.dispose();
        }

        if (this.img != null) {
            this.img.dispose();
        }

    }

    public void initaliseInputProcessors() {
        this.inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(this.inputMultiplexer);
        this.inputProcessor = new MainClass.MyInputProcessor();
        this.inputMultiplexer.addProcessor(this.inputProcessor);
    }

    class MyInputProcessor implements InputProcessor {
        public boolean keyDown(int keycode) {
            if (keycode == 29) {
                ++MainClass.speed;
                return false;
            } else {
                return false;
            }
        }

        public boolean keyUp(int keycode) {
            return false;
        }

        public boolean keyTyped(char character) {
            return false;
        }

        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            float scrX = EasingFunction.EaseInBack(MainClass.mousePointOld.x, MainClass.mousePoint.y, 1.0F * MainClass.speed);
            float scrY = EasingFunction.EaseInBack(MainClass.mousePointOld.y, MainClass.mousePoint.y, 1.0F * MainClass.speed);
            MainClass.mousePoint = MainClass.this.new Point(scrX, scrY);
            return false;
        }

        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            MainClass.mousePointOld = MainClass.mousePoint;
            return false;
        }

        @Override
        public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        public boolean scrolled(float amountX, float amountY) {
            return false;
        }
    }

    public class Point {
        public float x;
        public float y;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}
