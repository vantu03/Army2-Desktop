package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.teamobi.mobiarmy2.MainGame;

public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(40);
        config.setWindowIcon("res/icon.png");
        config.setTitle("Army2");

        config.setWindowedMode(1280, 720); 
        config.setResizable(true);

        MainGame game = new MainGame();
        new Lwjgl3Application(game, config);
    }
}
