package screen;

import CLib.mGraphics;
import com.badlogic.gdx.Gdx;
import com.teamobi.mobiarmy2.GameMidlet;
import coreLG.CCanvas;
import model.CRes;
import model.Font;
import model.GetString;
import model.IAction;
import model.Language;
import network.Command;
import network.GameService;
import network.Session_ME;

public class ServerListScreen extends CScreen {
   public static String[] nameServer;
   public static String[] address;
   public static boolean[] newServer;
   public static short[] port;
   public int selected;
   public int yPaint = 0;

   public ServerListScreen() {
      this.indexScreen = 1;
      this.nameCScreen = " ServerListScreen screen!";
      this.center = new Command(Language.select(), new IAction() {
         public void perform() {
            String name = ServerListScreen.nameServer[ServerListScreen.this.selected] + ":" + ServerListScreen.address[ServerListScreen.this.selected] + ":" + ServerListScreen.port[ServerListScreen.this.selected];
            ServerListScreen.this.OnConnectToServer(name);
            CCanvas.loginScr = new LoginScr();
            CCanvas.loginScr.show();
            Session_ME.gI().start = false;
         }
      });
      this.left = new Command(Language.update(), new IAction() {
         public void perform() {
            ServerListScreen.nameServer = null;
            GameMidlet.doUpdateServer();
         }
      });
      this.right = new Command(Language.exit(), new IAction() {
         public void perform() {
            Gdx.app.exit();
            System.exit(-1);
         }
      });
   }

   private void OnConnectToServer(String stringConnect) {
      stringConnect.trim();
      String[] svConfig = stringConnect.split(":");
      String nameSv = svConfig[0].trim().toLowerCase();
      String strIPConnect = svConfig[1];
      String strPortConnect = svConfig[2];
      GameMidlet.IP = strIPConnect;
      GameMidlet.PORT = Short.parseShort(strPortConnect);
      Session_ME.gI().connect(GameMidlet.IP, GameMidlet.PORT);
      GameMidlet.serverName = svConfig[0];
      if (GameMidlet.isTeamClient) {
         GameService.gI().setProvider(GameMidlet.PROVIDER);
         new GetString();
         GameService.gI().getString("abc");
         GameService.gI().platform_request();
      } else {
         GameMidlet.PROVIDER = (byte)CRes.loadRMSInt("provider");
         GameMidlet.AGENT = CRes.loadRMS_String("agent");
         if (GameMidlet.AGENT == null) {
            GameMidlet.AGENT = "";
         }

         if (GameMidlet.PROVIDER != -1) {
            GameService.gI().setProvider(GameMidlet.PROVIDER);
            GameService.gI().getString(GameMidlet.AGENT);
         }
      }

   }

   public void paint(mGraphics g) {
      g.setColor(7852799);
      g.fillRect(0, 0, w, h, false);
      if (nameServer != null) {
        
         this.yPaint = CCanvas.hieght / 2 - ITEM_HEIGHT;
         g.setColor(16767817);
         g.fillRect(0, this.yPaint + this.selected * 20 - 3, CCanvas.width, ITEM_HEIGHT, true);
         Font.borderFont.drawString(g, Language.chonmaychu(), CCanvas.width / 2, this.yPaint - ITEM_HEIGHT - 5, 3);
            
         for(int i = 0; i < nameServer.length; ++i) {
            if (nameServer[i] != null) {
               Font.normalFont.drawString(g, nameServer[i], CCanvas.width / 2, this.yPaint + i * 20, 2);
            }
         }
      }

      super.paint(g);
   }

   public void update() {
      super.update();
   }

   public void onPointerPressed(int x, int y2, int index) {
      super.onPointerPressed(x, y2, index);
      if (nameServer != null) {
        if (CCanvas.keyPressed[2]) {
            --this.selected;
            if (this.selected < 0) {
               this.selected = nameServer.length - 1;
            }
            CScreen.clearKey();
        }

        if (CCanvas.keyPressed[8]) {
            ++this.selected;
            if (this.selected > nameServer.length - 1) {
                this.selected = 0;
            }
            CScreen.clearKey();
        }
      }
   }

   public void onPointerReleased(int x, int y2, int index) {
      super.onPointerReleased(x, y2, index);
      if (nameServer != null) {
         if (!CCanvas.keyPressed[8] && !keyDown) {
            if (CCanvas.keyPressed[2] || keyUp) {
               clearKey();
               --this.selected;
               if (this.selected < 0) {
                  this.selected = nameServer.length - 1;
               }
            }
         } else {
            clearKey();
            ++this.selected;
            if (this.selected > nameServer.length - 1) {
               this.selected = 0;
            }
         }

         if (y2 < CCanvas.hieght - cmdH) {
            int aa = (y2 - this.yPaint) / 20;
            if (aa == this.selected && CCanvas.isDoubleClick && this.center != null) {
               this.center.action.perform();
            }

            if (aa >= 0 && aa < nameServer.length) {
               this.selected = aa;
            }
         }
      }

      GameMidlet.server = (byte)(this.selected >= 3 ? -1 : this.selected);
      if (GameMidlet.versionByte >= 240) {
         GameMidlet.server = 2;
      }

   }

   public void show() {
      super.show();
      GameMidlet.loadIP();
   }
}