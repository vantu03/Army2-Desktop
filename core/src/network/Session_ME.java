package network;

import CLib.mSocket;
import CLib.mSystem;
import CLib.mVector;
import coreLG.CCanvas;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.SocketException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import model.CRes;
import model.Language;

public class Session_ME implements ISession {
    protected static Session_ME instance = new Session_ME();
    private final Session_ME.Sender sender = new Session_ME.Sender();
    private DataOutputStream dos;
    public DataInputStream dis;
    public static IMessageHandler messageHandler;
    private static mSocket _mSocket;
    public boolean connected;
    public boolean connecting;
    public boolean start = true;
    public Thread initThread;
    public Thread collectorThread;
    public Thread sendThread;
    public int sendByteCount;
    public int recvByteCount;
    private boolean getKeyComplete;
    public byte[] key = null;
    private byte curR;
    private byte curW;
    private long timeConnected;
    public String strRecvByteCount = "";
    public static BlockingQueue<Message> recieveMsg = new ArrayBlockingQueue(1000);
    public static int receiveSynchronized = 0;
    public static int countRead = 0;
    private int errip = 0;
    public static String h = "";
    public static int p;
    public static boolean isCancel;
    private int countMsg = 0;
    int err3 = 0;

    public static Session_ME gI() {
        return instance;
    }

    public boolean isConnected() {
        return this.connected;
    }

    public void setHandler(IMessageHandler messageHandler) {
        Session_ME.messageHandler = messageHandler;
    }

    public void connect(String host, int port) {
        h = host;
        p = port;
        CRes.out("==========================> connect to  " + this.connected + " _ " + this.connecting);
        if (!this.connected && !this.connecting) {
            CRes.out("==========================> connect to  " + host + " _ " + port);
            if (_mSocket != null && _mSocket.getState() == 1) {
                this.close(0);
            }

            this.sender.removeAllMessage();
            this.getKeyComplete = false;
            _mSocket = null;
            this.initThread = new Thread(new Session_ME.NetworkInit(host, port));
            this.initThread.start();
        }
    }

    public void onRecieveMsg(Message msg) {
        recieveMsg.offer(msg);
    }

    public static void update() {
        if (gI().connecting && !gI().start && _mSocket == null) {
            CCanvas.startWaitDlgWithoutCancel(Language.connecting(), 11111);
        } else {
            if (receiveSynchronized <= 0 && recieveMsg.size() > 0) {
                Message msg = (Message) recieveMsg.poll();
                if (msg == null) {
                    return;
                }

                messageHandler.onMessage(msg);
            }

        }
    }

    public void sendMessage(Message message) {
        this.sender.AddMessage(message);
    }

    private synchronized void doSendMessage(Message m) throws IOException {
        byte[] data = m.getData();

        try {
            if (this.getKeyComplete) {
                byte b = this.writeKey(m.command);
                this.dos.writeByte(b);
                CRes.err("send cmd " + m.command + " _ " + b);
            } else {
                this.dos.writeByte(m.command);
            }

            if (data != null) {
                int size = data.length;
                if (this.getKeyComplete) {
                    int byte1 = this.writeKey((byte) (size >> 8));
                    this.dos.writeByte(byte1);
                    int byte2 = this.writeKey((byte) (size & 255));
                    this.dos.writeByte(byte2);
                } else {
                    this.dos.writeShort(size);
                }

                if (this.getKeyComplete) {
                    for (int i = 0; i < data.length; ++i) {
                        data[i] = this.writeKey(data[i]);
                    }
                }

                this.dos.write(data);
                this.sendByteCount += 5 + data.length;
            } else {
                this.dos.writeShort(0);
                this.sendByteCount += 5;
            }

            this.dos.flush();
        } catch (IOException var6) {
            var6.printStackTrace();
        }

    }

    private byte readKey(byte b) {
        this.err3 = 2;
        byte[] var10000 = this.key;
        byte var10003 = this.curR;
        this.curR = (byte) (var10003 + 1);
        byte i = (byte) (var10000[var10003] & 255 ^ b & 255);
        this.err3 = 4;
        if (this.curR >= this.key.length) {
            this.curR = (byte) (this.curR % this.key.length);
        }

        return i;
    }

    private byte writeKey(byte b) {
        this.err3 = 2;
        byte[] var10000 = this.key;
        byte var10003 = this.curW;
        this.curW = (byte) (var10003 + 1);
        byte i = (byte) (var10000[var10003] & 255 ^ b & 255);
        this.err3 = 5;
        if (this.curW >= this.key.length) {
            this.curW = (byte) (this.curW % this.key.length);
        }

        return i;
    }

    private Message readMessage2(byte cmd) throws Exception {
        try {
            int datalen = this.dis.readInt();
            if (datalen > 0) {
                byte[] data = new byte[datalen];
                int len = 0;
                int byteRead = 0;

                while (len != -1 && byteRead < datalen) {
                    len = this.dis.read(data, byteRead, datalen - byteRead);
                    if (len > 0) {
                        byteRead += len;
                        this.recvByteCount += 5 + byteRead;
                    }
                }

                Message msg = new Message(cmd, data);
                return msg;
            }
        } catch (Exception var7) {
            var7.printStackTrace();
            CRes.out("" + var7.getCause());
        }

        return null;
    }

    public Message readMessage3(byte cmd) {
        try {
            short datalen = this.dis.readShort();
            CRes.err("===========. readMessage3 dataLen = " + datalen);
            if (datalen > 0) {
                byte[] data = new byte[datalen];
                int len = 0;
                int byteRead = 0;

                while (len != -1 && byteRead < datalen) {
                    len = this.dis.read(data, byteRead, datalen - byteRead);
                    if (len > 0) {
                        byteRead += len;
                        this.recvByteCount += 5 + byteRead;
                    }
                }

                Message msg = new Message(cmd, data);
                CRes.out("==========> readmessage 3 BigImage " + data.length);
                return msg;
            }
        } catch (Exception var7) {
            var7.printStackTrace();
            CRes.out("" + var7.getCause());
        }

        return null;
    }

    public void connect(String host, String port) {
        h = host;
        p = Short.parseShort(port);
        if (!this.connected && !this.connecting) {
            this.sender.removeAllMessage();
            this.getKeyComplete = false;
            _mSocket = null;
            this.initThread = new Thread(new Session_ME.NetworkInit(host, Short.parseShort(port)));
            this.initThread.start();
        }
    }

    public void close(int index) {
        CRes.out("==========================> Clean network " + index);
        this.cleanNetwork();
    }

    private void cleanNetwork() {
        this.key = null;
        this.curR = 0;
        this.curW = 0;

        try {
            recieveMsg.clear();
            this.connected = false;
            this.connecting = false;
            if (_mSocket != null) {
                _mSocket.close();
                _mSocket = null;
            }

            if (this.dos != null) {
                this.dos.close();
                this.dos = null;
            }

            if (this.dis != null) {
                this.dis.close();
                this.dis = null;
            }

            this.sendThread = null;
            this.collectorThread = null;
            if (this.initThread != null && this.initThread.isAlive()) {
                this.initThread.interrupt();
                this.initThread = null;
            }

            System.gc();
        } catch (SocketException var2) {
            _mSocket = null;
            recieveMsg.clear();
            System.gc();
            var2.printStackTrace();
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    private class MessageCollector implements Runnable {
        private MessageCollector() {
        }

        public void run() {
            Session_ME.this.errip = 0;

            try {
                Session_ME.this.errip = 1;

                try {
                    Session_ME.this.errip = 2;

                    while (Session_ME.this.isConnected()) {
                        Session_ME.this.errip = 3;
                        Message message = this.readMessage();
                        if (message == null) {
                            Session_ME.this.errip = 1200 + message.command;
                            break;
                        }

                        CRes.out("Receive message " + message.command);
                        Session_ME.this.errip = 4;

                        try {
                            Session_ME.this.errip = 500 + message.command;
                            if (message.command == -27) {
                                Session_ME.this.errip = 600 + message.command;
                                this.getKey(message);
                                Session_ME.this.errip = 700 + message.command;
                            } else {
                                Session_ME.this.errip = 800 + message.command;
                                Session_ME.this.onRecieveMsg(message);
                                Session_ME.this.errip = 900 + message.command;
                            }
                        } catch (Exception var4) {
                            Session_ME.this.errip = 1000 + message.command;
                            var4.printStackTrace();
                            Session_ME.this.errip = 1100 + message.command;
                        }

                        try {
                            Session_ME.this.errip = 6;
                            Thread.sleep(100L);
                            Session_ME.this.errip = 7;
                        } catch (InterruptedException var3) {
                            Session_ME.this.errip = 8;
                        }
                    }

                    Session_ME.this.errip = 13;
                } catch (Exception var5) {
                    Session_ME.this.errip = 14;
                }

                Session_ME.this.errip = 15;
                if (Session_ME.this.connected) {
                    Session_ME.this.errip = 16;
                    if (Session_ME.messageHandler != null) {
                        Session_ME.this.errip = 17;
                        if (mSystem.currentTimeMillis() - Session_ME.this.timeConnected > 500L) {
                            Session_ME.messageHandler.onDisconnected();
                        } else {
                            Session_ME.messageHandler.onConnectionFail();
                        }

                        Session_ME.this.errip = 18;
                    }

                    Session_ME.this.errip = 19;
                    if (Session_ME._mSocket != null) {
                        Session_ME.this.close(1);
                    }

                    Session_ME.this.errip = 20;
                }
            } catch (Exception var6) {
                var6.printStackTrace();
            }

        }

        private void getKey(Message message) throws IOException {
            byte keySize = message.reader().readByte();
            Session_ME.this.key = new byte[keySize];

            int i;
            for (i = 0; i < keySize; ++i) {
                Session_ME.this.key[i] = message.reader().readByte();
            }

            for (i = 0; i < Session_ME.this.key.length - 1; ++i) {
                byte[] var10000 = Session_ME.this.key;
                var10000[i + 1] ^= Session_ME.this.key[i];
            }

            String keyS = "";

            for (int ix = 0; ix < Session_ME.this.key.length - 1; ++ix) {
                keyS = keyS + Session_ME.this.key[ix] + "_";
            }

            CRes.out("======> key is " + keyS);
            Session_ME.this.getKeyComplete = true;
        }

        private Message readMessage() throws Exception {
            try {
                ++Session_ME.countRead;
                byte cmd = Session_ME.this.dis.readByte();
                if (Session_ME.this.getKeyComplete) {
                    cmd = Session_ME.this.readKey(cmd);
                }

                CRes.out("Receive 1 cmd " + cmd);
                if (cmd != -120 && cmd != 90) {
                    int size;
                    if (Session_ME.this.getKeyComplete) {
                        byte b1 = Session_ME.this.dis.readByte();
                        byte b2 = Session_ME.this.dis.readByte();
                        size = (Session_ME.this.readKey(b1) & 255) << 8 | Session_ME.this.readKey(b2) & 255;
                    } else {
                        size = Session_ME.this.dis.readUnsignedShort();
                    }

                    byte[] data = new byte[size];
                    int len = 0;
                    int byteRead = 0;

                    while (len != -1 && byteRead < size) {
                        len = Session_ME.this.dis.read(data, byteRead, size - byteRead);
                        if (len > 0) {
                            byteRead += len;
                            Session_ME var10000 = Session_ME.this;
                            var10000.recvByteCount += 5 + byteRead;
                        }
                    }

                    if (Session_ME.this.getKeyComplete) {
                        for (int i = 0; i < data.length; ++i) {
                            data[i] = Session_ME.this.readKey(data[i]);
                        }
                    }

                    Message msg = new Message(cmd, data);
                    return msg;
                }

                return Session_ME.this.readMessage2(cmd);
            } catch (EOFException var7) {
                CRes.out("====> Session readMessage() method  EOF exception ");
            } catch (Exception var8) {
                CRes.out("exception ");
            }

            return null;
        }

        // $FF: synthetic method
        MessageCollector(Session_ME.MessageCollector var2) {
            this();
        }
    }

    private class NetworkInit implements Runnable {
        private final String host;
        private final int port;
        int iErr = 0;

        NetworkInit(String host, int port) {
            this.host = host;
            this.port = port;
        }

        public void run() {
            try {
                this.iErr = 0;
                Session_ME.isCancel = false;
                this.iErr = 1;
                (new Thread(new Runnable() {
                    public void run() {
                        NetworkInit.this.iErr = 3;

                        try {
                            NetworkInit.this.iErr = 4;
                            Thread.sleep(20000L);
                        } catch (InterruptedException var3) {
                            NetworkInit.this.iErr = 5;
                        }

                        if (Session_ME.this.connecting) {
                            NetworkInit.this.iErr = 6;

                            try {
                                NetworkInit.this.iErr = 7;
                                Session_ME._mSocket.close();
                            } catch (Exception var2) {
                                NetworkInit.this.iErr = 8;
                            }

                            NetworkInit.this.iErr = 9;
                            Session_ME.isCancel = true;
                            Session_ME.this.connecting = false;
                            Session_ME.this.connected = false;
                            NetworkInit.this.iErr = 10;
                            Session_ME.messageHandler.onConnectionFail();
                            NetworkInit.this.iErr = 11;
                        }

                    }
                })).start();
                this.iErr = 12;
                Session_ME.this.connecting = true;
                Thread.currentThread().setPriority(1);
                this.iErr = 13;
                Session_ME.this.connected = true;
                this.iErr = 14;

                try {
                    this.iErr = 15;
                    CRes.out("1 do connect host =========> " + this.host + " __ " + this.port);
                    this.doConnect(this.host, this.port);
                    this.iErr = 16;
                    Session_ME.messageHandler.onConnectOK();
                    CCanvas.endDlg();
                    this.iErr = 17;
                } catch (Exception var4) {
                    var4.printStackTrace();
                    this.iErr = 18;

                    try {
                        this.iErr = 19;
                        Thread.sleep(500L);
                        this.iErr = 20;
                    } catch (InterruptedException var3) {
                        this.iErr = 21;
                    }

                    this.iErr = 22;
                    if (Session_ME.isCancel) {
                        return;
                    }

                    this.iErr = 23;
                    if (Session_ME.messageHandler != null) {
                        this.iErr = 24;
                        Session_ME.this.close(2);
                        this.iErr = 25;
                        Session_ME.messageHandler.onConnectionFail();
                        this.iErr = 26;
                    }
                }
            } catch (Exception var5) {
                var5.printStackTrace();
                CRes.err("throw Exception!!!!!");
            }

        }

        public void doConnect(String host, int port) throws Exception {
            Session_ME._mSocket = new mSocket(host, port);
            if (Session_ME._mSocket != null) {
                Session_ME._mSocket.setKeepAlive(true);
                Session_ME.this.dos = Session_ME._mSocket.getOutputStream();
                Session_ME.this.dis = Session_ME._mSocket.getInputStream();
                (new Thread(Session_ME.this.sender)).start();
                Session_ME.this.collectorThread = new Thread(Session_ME.this.new MessageCollector((Session_ME.MessageCollector) null));
                Session_ME.this.collectorThread.start();
                Session_ME.this.timeConnected = mSystem.currentTimeMillis();
                Session_ME.this.doSendMessage(new Message(-27));
                Session_ME.this.connecting = false;
                Session_ME.this.start = false;
            }

        }
    }

    private class Sender implements Runnable {
        public final mVector sendingMessage = new mVector();
        int iErrIp = 0;

        public Sender() {
        }

        public void AddMessage(Message message) {
            this.sendingMessage.addElement(message);
        }

        public void removeAllMessage() {
            if (this.sendingMessage != null) {
                this.sendingMessage.removeAllElements();
            }

        }

        public void run() {
            try {
                this.iErrIp = 0;

                while (Session_ME.this.connected) {
                    this.iErrIp = 1;
                    if (Session_ME.this.getKeyComplete) {
                        while (this.sendingMessage.size() > 0) {
                            this.iErrIp = 2;
                            Message m = (Message) this.sendingMessage.elementAt(0);
                            this.iErrIp = 300 + m.command;
                            this.sendingMessage.removeElementAt(0);
                            this.iErrIp = 400 + m.command;
                            Session_ME.this.doSendMessage(m);
                            this.iErrIp = 500 + m.command;
                        }
                    }

                    try {
                        this.iErrIp = 6;
                        Thread.sleep(10L);
                        this.iErrIp = 7;
                    } catch (InterruptedException var2) {
                        this.iErrIp = 8;
                    }
                }

                this.iErrIp = 9;
            } catch (SocketException var3) {
                var3.printStackTrace();
            } catch (IOException var4) {
                var4.printStackTrace();
            }

        }
    }
}
