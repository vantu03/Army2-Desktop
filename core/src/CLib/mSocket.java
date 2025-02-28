package CLib;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import model.CRes;

public class mSocket {
   Socket _socket;

   public mSocket(String str, int port) {
      try {
         this._socket = new Socket(str, port);
      } catch (ConnectException var4) {
         var4.printStackTrace();
         CRes.err("====> ConnectException ");
      } catch (IOException var5) {
         var5.printStackTrace();
      }

   }

   public void close() {
      try {
         if (this._socket != null) {
            this._socket.close();
         }
      } catch (IOException var2) {
         var2.printStackTrace();
      }

   }

   public void setKeepAlive(boolean isAlive) {
      try {
         if (this._socket != null) {
            this._socket.setKeepAlive(isAlive);
         }
      } catch (ConnectException var3) {
         var3.printStackTrace();
      } catch (IOException var4) {
         var4.printStackTrace();
      }

   }

   public DataOutputStream getOutputStream() {
      try {
         DataOutputStream dos = new DataOutputStream(this._socket.getOutputStream());
         return dos;
      } catch (IOException var2) {
         var2.printStackTrace();
         return null;
      }
   }

   public DataInputStream getInputStream() {
      try {
         DataInputStream dis = new DataInputStream(this._socket.getInputStream());
         return dis;
      } catch (IOException var2) {
         var2.printStackTrace();
         return null;
      }
   }

   public String getIP() {
      return this._socket == null ? "do not connect!" : this._socket.getInetAddress().getHostAddress() + " " + this._socket.getPort();
   }

   public byte getState() {
      if (this._socket == null) {
         return -1;
      } else if (this._socket.isInputShutdown()) {
         return 2;
      } else if (this._socket.isOutputShutdown()) {
         return 3;
      } else if (this._socket.isConnected()) {
         return 1;
      } else {
         return (byte)(this._socket.isClosed() ? 0 : -1);
      }
   }
}
