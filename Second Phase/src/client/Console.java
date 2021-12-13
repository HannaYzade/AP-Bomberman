package client;

import client.frames.GameListFrame;
import client.frames.MainFrame;
import client.frames.Map;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Console {


    private Socket socket;
    private BufferedOutputStream outputStream;
    private BufferedInputStream inputStream;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private Thread gameThread;
    private static Console console;
    private boolean isPlayer;

    private Console(Socket socket) {
        this.socket = socket;
        try {
            outputStream = new BufferedOutputStream(socket.getOutputStream());
            inputStream = new BufferedInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.write(5);
            objectOutputStream.flush();
            objectInputStream = new ObjectInputStream(inputStream);
            objectInputStream.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        gameThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()) {
                    try {
                        String str = (String)objectInputStream.readObject();
                        if(str != null) {
                            if(str.charAt(0) == 'o'){
                                GameListFrame gameListFrame = new GameListFrame(str);
                                if(str.length() == 4) {
                                    JOptionPane.showMessageDialog(gameListFrame, "No game exsists!");
                                    System.exit(0);
                                }
                                gameListFrame.setVisible(true);
                            }
                            else if(str.charAt(0) == 'm') {
                                Scanner sc = new Scanner(str);
                                sc.nextLine();
                                MainFrame.getFrame().getInformationPanel().getChatPanel().showw(sc.nextLine(), Integer.valueOf(sc.nextLine()));
                            }
                            else {
                                Map.getMap().setNowGame(str);
                                Map.getMap().repaint();
                                if (!MainFrame.getFrame().isVisible())
                                    MainFrame.getFrame().setVisible(true);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void creatConsole(Socket socket) {
        console = new Console(socket);
    }

    public static Console getConsole() {
        return console;
    }

    public void getGame(int w, int h, int players) {
        isPlayer = true;
        try {
            objectOutputStream.reset();
            objectOutputStream.writeObject("new game\n" + w + "\n" + h + "\n" + players + "\n");
            objectOutputStream.flush();
            MainFrame.makeFrame(w, h);
            gameThread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void move(int direction) {
        try {
            synchronized (Color.blue) {
                objectOutputStream.reset();
                objectOutputStream.writeObject("move\n" + direction);
                objectOutputStream.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void boomb() {
        try {
            synchronized (Color.blue) {
                objectOutputStream.reset();
                objectOutputStream.writeObject("boomb\n");
                objectOutputStream.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void explosion() {
        try {
            synchronized (Color.blue) {
                objectOutputStream.reset();
                objectOutputStream.writeObject("explosion\n");
                objectOutputStream.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getList() {
        try {
            synchronized (Color.blue) {
                objectOutputStream.reset();
                objectOutputStream.writeObject("list\n");
                objectOutputStream.flush();
                gameThread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getExsistGame(boolean isPlayer, int ind, int w, int h) {
        this.isPlayer = isPlayer;
        try {
            synchronized (Color.blue) {
                MainFrame.makeFrame(w, h);
                objectOutputStream.reset();
                objectOutputStream.writeObject("get\n" + isPlayer + "\n" + ind + "\n");
                objectOutputStream.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String str) {
        try {
            synchronized (Color.blue) {
                objectOutputStream.reset();
                objectOutputStream.writeObject("message\n" + str + "\n");
                objectOutputStream.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isPlayer() {
        return isPlayer;
    }
}
