package javax.microedition.midlet;

import CLib.mSystem;
import com.badlogic.gdx.Gdx;

public class MIDlet {
    public static void notifyDestroyed() {
        Gdx.app.exit();
    }

    public static void platformRequest(String url) {
        mSystem.openUrl(url);
    }
}
