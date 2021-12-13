package server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ListenThread extends Thread {

    private ServerSocket listener;
    private int port = 9090;
    private ArrayList<Console> consoles;

    public ListenThread() {
        consoles = new ArrayList<>();
        try {
            listener = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        while (!listener.isClosed()) {
            try {
                Socket clientSocket = listener.accept();
                Console console = new Console(clientSocket);
                consoles.add(console);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
