package server;

import server.abstractObjects.animals.AbstractBear;
import server.abstractObjects.animals.AbstractDragon;
import server.abstractObjects.animals.AbstractFox;
import server.abstractObjects.animals.AbstractLion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

public class Server {

    private static Server server;
    private ListenThread listenThread;
    private ArrayList<Game> games;
    private JButton button;
    private ArrayList<Class> enemies;

    private Server() {
        enemies = new ArrayList<>();
        enemies.add(AbstractFox.class);
        enemies.add(AbstractBear.class);
        enemies.add(AbstractLion.class);
        enemies.add(AbstractDragon.class);

        JFrame frame;
        frame = new JFrame() {
            {
                setSize(500, 500);
                setResizable(false);
                setLocationRelativeTo(null);
                requestFocus();
                getContentPane().setLayout(null);
                getContentPane().add(new JTextArea("\n" +
                        " No package;\n" +
                        " Extend AbstractBadAnimal;\n" +
                        " Implement findDirection();\n" +
                        " Implement getName() and return \"New\";\n" +
                        " Constructor must be take i, j and game;\n" +
                        " Declare a static field lev, first level of enemy;\n"){{
                    setFont(new Font("Serif", Font.BOLD, 16));
                    setEditable(false);
                    setBounds(20, 20, 460, 350);
                }});

                button = new JButton("Add a new enemy"){{
                    setFont(new Font("Serif", Font.PLAIN, 22));
                }};
                getContentPane().add(button);
                button.setBounds(125, 385, 250, 70);
                getContentPane().setBackground(new Color(198, 255, 78));
            }
        };


        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String name = JOptionPane.showInputDialog(null, "Enter the class fullname");
                JFileChooser chooser = new JFileChooser();
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    file = new File(file.getPath().substring(0, file.getPath().length() - file.getName().length() - 1));
                    try {
                        URL[] urls = new URL[]{file.toURI().toURL()};
                        URLClassLoader classLoader = new URLClassLoader(urls);
                        Class cls = classLoader.loadClass(name);
                        classLoader.close();
                        addEnemy(cls);
                        System.out.println(enemies.size());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        frame.setVisible(true);
        games = new ArrayList<>();
        listenThread = new ListenThread();
        listenThread.start();
    }

    public static Server getServer() {
        if(server == null) {
            server = new Server();
        }
        return server;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public String getGameList() {
        String s = "o\n";
        s += games.size() + "\n";
        for (int i = 0; i < games.size(); i ++) {
            s += games.get(i).getInf() + "\n";
        }
        return s;
    }

    public void addEnemy(Class cls) {
        for (int i = 0; i < enemies.size(); i++)
            if (cls == enemies.get(i))
                return;
        enemies.add(cls);
    }

    public ArrayList<Class> getEnemies() {
        return enemies;
    }
}
