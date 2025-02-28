package network;

public interface ISession {
    boolean isConnected();

    void setHandler(IMessageHandler var1);

    void connect(String var1, String var2);

    void sendMessage(Message var1);
}
