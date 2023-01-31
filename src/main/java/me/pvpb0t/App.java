package me.pvpb0t;

import me.pvpb0t.network.client.Client;
import me.pvpb0t.network.server.Server;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App extends JFrame {

    private JTextArea chatArea = new JTextArea();
    private JTextField inputField = new JTextField();

    private Server server;
    private Client client;
    private boolean clientRun, serverRun;

    private int port;


    public void writeToChat(String text){
        System.out.println(text);
        this.chatArea.append(text+"\n");
    }

    public App() {
        super("Chat App");

        clientRun=false;
        serverRun=false;

        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        chatArea.setBackground(Color.DARK_GRAY);
        chatArea.setForeground(Color.WHITE);


        add(chatArea, BorderLayout.CENTER);
        DefaultCaret caret = (DefaultCaret) chatArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        JButton btnSend = new JButton("Send");
        btnSend.setBackground(Color.GRAY);
        btnSend.setForeground(Color.WHITE);

        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(Color.DARK_GRAY);
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
        inputField.setBackground(Color.GRAY);
        inputField.setForeground(Color.WHITE);
        inputField.setPreferredSize(new Dimension(350, 30));
        inputPanel.add(inputField);
        inputPanel.add(btnSend);
        add(inputPanel, BorderLayout.SOUTH);

        client = new Client(chatArea, inputField);
        server = new Server(chatArea);

        JButton btnServer = new JButton("Start Server");
        btnServer.setBackground(Color.GRAY);
        btnServer.setForeground(Color.WHITE);
        JButton btnClient = new JButton("Start Client");
        btnClient.setBackground(Color.GRAY);
        btnClient.setForeground(Color.WHITE);
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.DARK_GRAY);
        topPanel.add(btnServer);
        topPanel.add(btnClient);
        add(topPanel, BorderLayout.NORTH);

        port=45565;

        btnServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.setPort(port);
                try{
                    server.start();
                    serverRun=true;
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
                    clientRun=true;
                    client.start();
                }catch (IllegalThreadStateException ex){
                    chatArea.append("Client has already been started\n");
                }
            }
        });

        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = inputField.getText();
                if (!message.isEmpty()) {
                    // Send the message to the chat area
                    if(clientRun && client!=null){
                        client.sendMessageStream( message  );
                    }
                    //writeToChat(message);
                    // Clear the input field after sending the message
                    inputField.setText("");
                }
            }
        });
    }


}

