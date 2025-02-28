package com.teamobi.mobiarmy2;

import CLib.RMS;
import CLib.mSystem;
import com.badlogic.gdx.Gdx;
import network.Message;

public class TemMidlet {
    public static TemCanvas temCanvas;
    public static TemMidlet instance;
    public static byte DIVICE = 2;
    public static final byte NONE = 0;
    public static final byte NOKIA_STORE = 1;
    public static final byte GOOGLE_STORE = 2;
    public static byte currentIAPStore = 0;
    public static boolean isBlockNOKIAStore = true;
    public static byte langServer = 0;
    public static final String[] productIds = new String[]{"1311457"};
    public static String[] listGems = new String[]{"24 Gems"};
    public static final String[] google_productIds = new String[]{"hs_gold_10_2", "hs_gold_30_2", "hs_gold_70_2", "hs_gold_180_2", "hs_gold_380_2", "hs_gold_800_2"};
    public static String[] google_listGems = new String[]{"24 Gems ($0.99)", "84 Gems ($2.99)", "150 Gems ($4.99)", "350 Gems ($9.99)", "1.000 Gems ($24.99)", "2.500 Gems ($49.99)"};

    public TemMidlet() {
        instance = this;
        temCanvas = new TemCanvas();
        temCanvas.start();
    }

    protected void destroyApp(boolean arg0) {
    }

    public static void makePurchase(String productId) {
    }

    protected void pauseApp() {
    }

    protected void startApp() {
    }

    public void destroy() {
        Gdx.app.exit();
    }

    public static byte[] encoding(byte[] array) {
        if (array != null) {
            for (int i = 0; i < array.length; ++i) {
                array[i] = (byte) (~array[i]);
            }
        }

        return array;
    }

    public static byte[] loadRMS(String filename) {
        return RMS.loadRMS(filename);
    }

    public static void openUrl(String url) {
        mSystem.openUrl(url);
    }

    public static void delRMS() {
    }

    public static String connectHTTP(String url) {
        return mSystem.connectHTTP(url);
    }

    public static void handleMessage(Message msg) {
    }

    public void call(String num) {
    }

    public static void submitPurchase() {
    }

    public static void handleAllMessage(Message msg) {
    }
}
