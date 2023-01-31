package me.pvpb0t;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class App extends JFrame {

    private JTextArea chatArea = new JTextArea();
    private JTextField inputField = new JTextField();

    private Server server;
    private Client client;

    private int port;

    public static void main(String[] args) {
        App app = new App();
        app.setVisible(true);
    }
    public void writeToChat(String text){
        System.out.println(text);
        this.chatArea.append(text+"\n");
    }

    public App() {
        super("Chat App");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(chatArea, BorderLayout.CENTER);
        JButton btnSend = new JButton("Send");
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
        inputField.setPreferredSize(new Dimension(350, 30));
        inputPanel.add(inputField);
        inputPanel.add(btnSend);
        add(inputPanel, BorderLayout.SOUTH);

        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = inputField.getText();
                if (!message.isEmpty()) {
                    // Send the message to the chat area
                    writeToChat(message);
                    // Clear the input field after sending the message
                    inputField.setText("");
                }
            }
        });


        client = new Client(chatArea, inputField);
        server = new Server(chatArea);

        JButton btnServer = new JButton("Start Server");
        JButton btnClient = new JButton("Start Client");
        JPanel topPanel = new JPanel();
        topPanel.add(btnServer);
        topPanel.add(btnClient);
        add(topPanel, BorderLayout.NORTH);

        port=45676;

        btnServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.setPort(port);
                try{
                    server.start();
                }catch (IllegalThreadStateException ex){
                    chatArea.append("Server has already been started\n");
                }
            }
        });
        btnClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.setPort(port);
                try{
                    client.start();
                }catch (IllegalThreadStateException ex){
                    chatArea.append("Client has already been started\n");
                }
            }
        });
    }


}

