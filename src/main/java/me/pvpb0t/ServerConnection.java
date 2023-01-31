package me.pvpb0t;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

class ServerConnection extends Thread{
    BufferedReader is;
    PrintStream os;
    Socket clientSocket;
    Server server;

    public ServerConnection(Socket clientSocket, Server server) {
        this.clientSocket = clientSocket;
        this.server = server;
        Bootstrap.getGuiApp().writeToChat( "Connection established with: " + clientSocket );
        try {
            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            os = new PrintStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println(e);
        }
    }


    @Override
    public void run() {
        String line;
        try {
            boolean serverStop = false;

            while (true) {
                line = is.readLine();
                Bootstrap.getGuiApp().writeToChat( "Server received " + line );

                if(line == "closeCon//V"){
                    break;
                }


                //os.println("" + n*n );
            }

            System.out.println( "Connection closed." );
            is.close();
            os.close();
            clientSocket.close();

            if ( serverStop ) server.stopServer();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}