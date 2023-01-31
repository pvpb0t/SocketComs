package me.pvpb0t.network.client;

import me.pvpb0t.Bootstrap;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Thread {

    private JTextArea chatArea;
    private JTextField inputField;

    Socket clientSocket = null;
    DataOutputStream os = null;
    BufferedReader is = null;
    private Socket socket;
    private String message = "";

    public Client(JTextArea chatArea, JTextField inputField) {
        this.chatArea = chatArea;
        this.inputField = inputField;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private int port;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }


    @Override
    public void run(){
        if(port>0){
            startClient();
        }else{
            Bootstrap.getGuiApp().writeToChat("Specify Port");
        }
    }

    public void sendMessageStream(String message){
        try {
            os.writeBytes( message + "\n" );
            Bootstrap.getGuiApp().writeToChat("Client sent: "+message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




    protected void startClient() {
        String hostname = "localhost";


        // declaration section:
        // clientSocket: our client socket
        // os: output stream
        // is: input stream



        // Initialization section:
        // Try to open a socket on the given port
        // Try to open input and output streams

        try {
            clientSocket = new Socket(hostname, port);
            os = new DataOutputStream(clientSocket.getOutputStream());
            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + hostname);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + hostname);
        }

        // If everything has been initialized then we want to write some data
        // to the socket we have opened a connection to on the given port

        if (clientSocket == null || os == null || is == null) {
            System.err.println( "Something is wrong. One variable is null." );
            return;
        }

        try {
            while ( true ) {

                String keyboardInput = "input";
                os.writeBytes( keyboardInput + "\n" );
                Bootstrap.getGuiApp().writeToChat("Client sent: "+keyboardInput);

                if(keyboardInput == "quit//v"){
                    break;
                }

                String responseLine = is.readLine();
                System.out.println("Server returns its square as: " + responseLine);
            }

            // clean up:
            // close the output stream
            // close the input stream
            // close the socket

            os.close();
            is.close();
            clientSocket.close();
        } catch (UnknownHostException e) {
            System.err.println("Trying to connect to unknown host: " + e);
        } catch (IOException e) {
            System.err.println("IOException:  " + e);
        }
    }
}


