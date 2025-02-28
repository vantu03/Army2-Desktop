package com.teamobi.mobiarmy2;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import model.CRes;

public class MyGdxGame implements ApplicationListener {
    private SpriteBatch batch;
    public static TemMidlet gmidlet;
    private InputMultiplexer inputMultiplexer;
    public boolean isIos78;
    public float zoom = 1.0F;
    Texture img;
    public static String mainThreadName;
    int i;
    int j;

    public void create() {
        this.batch = new SpriteBatch();
        this.img = new Texture("badlogic.jpg");
    }

    public void render() {
        ScreenUtils.clear(1.0F, 0.0F, 0.0F, 1.0F);
        this.batch.begin();
        CRes.out(" ===> w + H: " + this.img.getWidth() + "," + this.img.getHeight());
        ++this.i;
        ++this.j;
        if (this.j > 360) {
            this.j = 0;
        }

        this.batch.draw(this.img, 0.0F, 0.0F, (float) (this.img.getWidth() / 2), (float) (this.img.getHeight() / 2), (float) this.img.getWidth(), (float) this.img.getHeight(), 1.0F, 1.0F, (float) this.j, 0, 0, this.img.getWidth(), this.img.getHeight(), false, false);
        this.batch.end();
    }

    public void dispose() {
        this.batch.dispose();
        this.img.dispose();
    }

    public void resize(int width, int height) {
    }

    public void pause() {
    }

    public void resume() {
    }
}
