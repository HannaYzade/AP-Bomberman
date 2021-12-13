package client;

import client.frames.MenuFrame;

import java.io.*;
import java.net.*;

public class Client {

    private static Client client;
    private String serverIP = "127.0.0.1";   //local host IP
    private int serverPort = 9090;
    private Socket socket;
    private MenuFrame menuFrame;
    private Console console;

    private Client() {
        try {
            socket = new Socket(serverIP, serverPort);
            Console.creatConsole(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        menuFrame = new MenuFrame();
        menuFrame.setVisible(true);
    }

    public static Client getClient(){
        if(client == null)
            client = new Client();
        return client;
    }
}
