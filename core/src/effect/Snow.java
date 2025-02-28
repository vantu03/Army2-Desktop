package effect;

import CLib.mGraphics;
import CLib.mImage;
import coreLG.CCanvas;
import map.Background;
import map.MM;
import screen.GameScr;

public class Snow {
    int[] x;
    int[] y;
    int[] vx;
    int[] vy;
    public static mImage imgSnow;
    int[] type;
    int sum;
    int state;
    int typeSnow;
    int xx;
    public int waterY = 0;
    boolean[] isRainEffect;
    int[] frame;
    int[] t;
    boolean[] activeEff;
    public int min = 99;
    public int max = 100;
    public int vymin = 15;
    public int vymax = 20;
    public int vxmin = 7;

    public void startSnow(int typeS) {
        this.typeSnow = typeS;
        if (this.typeSnow == 0) {
            this.sum = CCanvas.random(150, 200);
        }

        if (this.typeSnow == 1) {
            this.sum = CCanvas.random(this.min, this.max);
        }

        if (imgSnow == null) {
            try {
                imgSnow = mImage.createImage("/tuyet.png");
            } catch (Exception var3) {
                var3.printStackTrace();
            }
        }

        this.x = new int[this.sum];
        this.y = new int[this.sum];
        this.vx = new int[this.sum];
        this.vy = new int[this.sum];
        this.type = new int[this.sum];
        this.isRainEffect = new boolean[this.sum];

        for (int i = 0; i < this.sum; ++i) {
            this.x[i] = CCanvas.random(-10, MM.mapWidth);
            this.y[i] = CCanvas.random(-100, MM.mapHeight - this.waterY);
            this.vx[i] = 0;
            if (this.typeSnow == 0) {
                this.vy[i] = CCanvas.random(1, 3);
            }

            if (this.typeSnow == 1) {
                this.vy[i] = CCanvas.random(this.vymin, this.vymax);
            }

            this.type[i] = CCanvas.random(1, 3);
            if (this.type[i] == 2 && i % 2 == 0) {
                this.isRainEffect[i] = true;
            }
        }

    }

    public void update() {
        if (this.state != 100) {
            for (int i = 0; i < this.sum; ++i) {
                if (this.state == 0) {
                    int[] var10000 = this.y;
                    var10000[i] += this.vy[i];
                    if (this.typeSnow == 0) {
                        this.vx[i] = GameScr.windx >> 4;
                    }

                    if (this.typeSnow == 1) {
                        if (GameScr.windx == 0) {
                            this.vx[i] = 1;
                        } else {
                            this.vx[i] = GameScr.windx > 0 ? this.vxmin : -this.vxmin;
                        }
                    }

                    var10000 = this.x;
                    var10000[i] += this.vx[i];
                }

                if (this.y[i] < -200 || this.y[i] > Background.waterY + 40 || this.x[i] > MM.mapWidth || this.x[i] < -10) {
                    if (this.y[i] > Background.waterY + 40) {
                        new Explosion(this.x[i], this.y[i], (byte) 6);
                    }

                    this.x[i] = CCanvas.random(-10, MM.mapWidth);
                    this.y[i] = CCanvas.random(-100, Background.waterY + 40);
                }
            }

        }
    }

    public void paintBigSnow(mGraphics g) {
        if (this.state != 100) {
            for (int i = 0; i < this.sum; ++i) {
                if (this.type[i] == 2) {
                    if (this.typeSnow == 0) {
                        g.drawImage(imgSnow, this.x[i], this.y[i], 0, false);
                    }

                    if (this.typeSnow == 1) {
                        g.setColor(15987699);
                        int wind = Math.abs(GameScr.windx);
                        int nWind = GameScr.windx;
                        if (wind == 0) {
                            g.drawLine(this.x[i], this.y[i], this.x[i] + 1, this.y[i] + 4, false);
                        }

                        if (wind > 0) {
                            g.drawLine(this.x[i], this.y[i], this.x[i] + (nWind > 0 ? 4 : -4), this.y[i] + 4, false);
                            g.drawLine(this.x[i], this.y[i], this.x[i] + (nWind > 0 ? 3 : -3), this.y[i] + 4, false);
                        }
                    }
                }
            }

        }
    }

    public void paintOnlySmall(mGraphics g) {
        for (int i = 0; i < this.sum; ++i) {
            if (this.typeSnow == 1) {
                g.setColor(10921638);
                int wind = Math.abs(GameScr.windx);
                int nWind = GameScr.windx;
                if (wind == 0) {
                    g.drawLine(this.x[i], this.y[i], this.x[i] + 1, this.y[i] + 2, false);
                }

                if (wind > 0) {
                    g.drawLine(this.x[i], this.y[i], this.x[i] + (nWind > 0 ? 2 : -2), this.y[i] + 2, false);
                }
            }
        }

    }

    public void paintSmallSnow(mGraphics g) {
        if (this.state != 100) {
            g.setColor(10742731);

            for (int i = 0; i < this.sum; ++i) {
                if (this.type[i] != 2) {
                    if (this.typeSnow == 0) {
                        g.fillRect(this.x[i], this.y[i], 2, 2, false);
                    }

                    if (this.typeSnow == 1) {
                        g.setColor(11908790);
                        int wind = Math.abs(GameScr.windx);
                        int nWind = GameScr.windx;
                        if (wind == 0) {
                            g.drawLine(this.x[i], this.y[i], this.x[i] + 1, this.y[i] + 2, false);
                        }

                        if (wind > 0) {
                            g.drawLine(this.x[i], this.y[i], this.x[i] + (nWind > 0 ? 2 : -2), this.y[i] + 2, false);
                        }
                    }
                }
            }

        }
    }
}
