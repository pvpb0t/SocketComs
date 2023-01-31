package me.pvpb0t.network.protocol;


import java.io.*;
import java.net.Socket;

public abstract class Protocol {
    protected Socket socket;
    protected DataOutputStream os;
    protected BufferedReader is;

    public void sendMessageStream(String message) throws IOException {
        os.writeBytes(message + "\n");
    }

    public abstract void run();
}
