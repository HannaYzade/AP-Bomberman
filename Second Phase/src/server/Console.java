package server;

import server.abstractObjects.animals.AbstractGuy;

import java.awt.*;
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Console {

    private AbstractGuy guy;
    private Socket clientSocket;
    private Game game;
    private BufferedOutputStream outputStream;
    private BufferedInputStream inputStream;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private Thread gameThread;
    private Thread chatThread;

    public Console(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            outputStream = new BufferedOutputStream(clientSocket.getOutputStream());
            inputStream = new BufferedInputStream(clientSocket.getInputStream());
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
                while(clientSocket.isConnected()) {
                    try {
                        String task = (String) objectInputStream.readObject();
                        if(task != null) {
                            Scanner sc = new Scanner(task);
                            String first = sc.nextLine();
                            if(first.equals("new game")) {
                                creatGame( Integer.valueOf(sc.nextLine()), Integer.valueOf(sc.nextLine()), Integer.valueOf(sc.nextLine()) );
                                sendGame();
                            }
                            else if(first.equals("move")) {
                                if(!guy.isDie())
                                    guy.move(Integer.valueOf(sc.nextLine()));
                            }
                            else if(first.equals("boomb")) {
                                if(! guy.isDie())
                                    game.boombing(guy);
                            }
                            else if(first.equals("explosion")) {
                                if(! guy.isDie() && guy.getBoombRemote() != null) {
                                    guy.getBoombRemote().explosion(guy);
                                }
                            }
                            else if(first.equals("list")) {
                                sendGameList();
                            }
                            else if(first.equals("get")) {
                                boolean isPlayer = Boolean.valueOf(sc.nextLine());
                                game = Server.getServer().getGames().get(Integer.valueOf(sc.nextLine()));
                                game.getConsoles().add(Console.this);
                                if(isPlayer) {
                                    guy = new AbstractGuy(AbstractGuy.guyNatSpeed, 0, 0, game.getGuys().size(), game, Console.this);
                                    game.getGuys().add(guy);
                                    game.addGuy(guy);
                                }
                            }
                            else if(first.equals("close")) {
                                clientSocket.close();
                                break;
                            }
                            else if(first.equals("message")) {
                                String str = sc.nextLine();
                                game.sendMess(str, guy.getGuyId());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        gameThread.start();
    }

    public synchronized void sendGame() {
        try {
            synchronized (Color.blue) {
                objectOutputStream.reset();
                objectOutputStream.writeObject(game.toString());
                objectOutputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void creatGame(int w, int h, int players) {
        game = new Game(w, h, players);
        guy = new AbstractGuy(AbstractGuy.guyNatSpeed, 0, 0, game.getGuys().size(), game, this);
        game.getGuys().add(guy);
        game.getConsoles().add(this);
        game.addGuy(guy);
        game.getAnimationThread().start();
        game.getTimeThread().start();
        Server.getServer().getGames().add(game);
    }

    private void sendGameList() {
        try {
            objectOutputStream.reset();
            objectOutputStream.writeObject(Server.getServer().getGameList());
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendMessage(String str, int id) {
        try {
            synchronized (Color.blue) {
                objectOutputStream.reset();
                objectOutputStream.writeObject("m\n" + str + "\n" + id + "\n");
                objectOutputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
