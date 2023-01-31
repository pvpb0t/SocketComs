package me.pvpb0t;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Server extends Thread{
    DataOutputStream os = null;
    BufferedReader is = null;
    private JTextArea chatArea;

    public Server(JTextArea chatArea){
        this.chatArea = chatArea;
    }

    private ArrayList<DataOutputStream> outputs = new ArrayList<>();
    private DataInputStream in;
    private DataOutputStream out;
    private Socket socket;
    private ServerSocket serverSocket;
    private int port;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void stopServer() {
        System.out.println( "Server cleaning up." );
        this.interrupt();
        //close server gui
    }

    @Override
    public void run(){
        if(port>0){
            startServer();

        }else{
            Bootstrap.getGuiApp().writeToChat("Specify Port");
        }
    }

    public void startServer() {
        String hostname = "localhost";
        try {
            serverSocket = new ServerSocket(port);

            /*new Thread(() -> {
                while (true) {
                    try {
                        String message = in.readUTF();
                        chatArea.append(message + "\n");
                        for (DataOutputStream o : outputs) {
                            o.writeUTF(message);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        outputs.remove(out);
                        break;
                    }
                }
            }).start();*/
        }
         catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + hostname);
        }


        while ( true ) {
            try {
                Bootstrap.getGuiApp().writeToChat("Initilize server on socketPort: "+serverSocket.getLocalPort());
                //waits till someone connects
                socket = serverSocket.accept();
                ServerConnection oneconnection = new ServerConnection(socket, this);
                oneconnection.start();
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }




    }
}

