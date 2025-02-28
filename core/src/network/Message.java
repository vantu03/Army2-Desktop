package network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Message {
    public byte command;
    private ByteArrayOutputStream os = null;
    private DataOutputStream dos = null;
    private ByteArrayInputStream iss = null;
    private DataInputStream dis = null;

    public Message() {
    }

    public Message(int command) {
        this.command = (byte) command;
        this.os = new ByteArrayOutputStream();
        this.dos = new DataOutputStream(this.os);
    }

    public Message(byte command) {
        this.command = command;
        this.os = new ByteArrayOutputStream();
        this.dos = new DataOutputStream(this.os);
    }

    public Message(byte command, byte[] data) {
        this.command = command;
        this.iss = new ByteArrayInputStream(data);
        this.dis = new DataInputStream(this.iss);
    }

    public byte[] getData() {
        return this.os.toByteArray();
    }

    public DataInputStream reader() {
        return this.dis;
    }

    public DataOutputStream writer() {
        return this.dos;
    }

    public void cleanup() {
        try {
            if (this.dis != null) {
                this.dis.close();
            }

            if (this.dos != null) {
                this.dos.close();
            }
        } catch (IOException var2) {
        }

    }
}
