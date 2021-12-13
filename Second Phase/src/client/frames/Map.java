package client.frames;

import client.Client;
import client.Console;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Map extends JPanel implements KeyListener{

    private static Map map = null;
    private final int cellWidth = 80;
    private final int cellHeight = 80;
    private final int guyGap = 15;
    private final int boombGap = 5;
    private final int powerUpGap = 10;
    private final int boombFrames = 63;
    private final int explosionFrames = 22;
    private String nowGame;
    private BufferedImage ghostImage;
    private HashMap<String, BufferedImage> cellImages;
    private ArrayList<BufferedImage> explosion;
    private ArrayList<BufferedImage> boombImages;
    private BufferedImage[][] guyImages;
    private HashMap<String, BufferedImage[][]> animalImages;
    private HashMap<String, BufferedImage> powerUpImages;
    private HashMap<String, Integer> animalGap;
    private BufferedImage natGuy;
    private BufferedImage closeDoor;
    private BufferedImage openDoor;
    private int[] inds = {1, 2, 1, 0, 1, 2, 1, 0, 1};
    private Color[] colors = {Color.magenta, Color.blue, Color.cyan, Color.green, Color.yellow, Color.orange, Color.red};

    private Map() {
        super();
        setLayout(null);
        addKeyListener(this);
        setFocusable(true);
        requestFocus();
        cellImages = new HashMap<>();
        animalImages = new HashMap<>();
        animalGap = new HashMap<>();
        explosion = new ArrayList<>();
        boombImages = new ArrayList<>();
        guyImages = new BufferedImage[4][3];
        powerUpImages = new HashMap<>();
        initialize();
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                grabFocus();
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });
    }



    private void initialize() {
        BufferedImage img = null;
        BufferedImage[][] images  = null;
        try {
            img = ImageIO.read(getClass().getResource("/img/grass.png"));
            cellImages.put("Grass", img);
            img = ImageIO.read(getClass().getResource("/img/rockWall.jpg"));
            cellImages.put("RockWall", img);
            img = ImageIO.read(getClass().getResource("/img/wall.jpg"));
            cellImages.put("Wall", img);

            try {
                closeDoor = ImageIO.read(getClass().getResource("/img/door/0.gif"));
                openDoor = ImageIO.read(getClass().getResource("/img/door/1.gif"));
            } catch (IOException e) {
                e.printStackTrace();
            }


            for(int i = 0; i < explosionFrames; i ++) {
                img = ImageIO.read(getClass().getResource("/img/explosion/" + i + ".gif"));
                explosion.add(img);
            }

            for(int i = 0; i < 4; i ++) {
                for(int j = 1; j <= 3; j ++) {
                    guyImages[i][j - 1] = ImageIO.read(getClass().getResource("/img/rabbit/" + "/" + i + "/" + j + ".gif"));
                }
            }
            natGuy = ImageIO.read(getClass().getResource("/img/rabbit/nat.gif"));
            ghostImage = ImageIO.read(getClass().getResource("/img/rabbit/ghost.png"));

            images = new BufferedImage[4][3];
            for(int i = 0; i < 4; i ++) {
                for(int j = 1; j <= 3; j ++) {
                    images[i][j - 1] = ImageIO.read(getClass().getResource("/img/fox/" + "/" + i + "/" + j + ".png"));
                }
            }
            animalImages.put("Fox", images);
            animalGap.put("Fox", 12);

            images = new BufferedImage[4][3];
            for(int i = 0; i < 4; i ++) {
                for(int j = 1; j <= 3; j ++) {
                    images[i][j - 1] = ImageIO.read(getClass().getResource("/img/bear/" + "/" + i + "/" + j + ".png"));
                }
            }
            animalImages.put("Bear", images);
            animalGap.put("Bear", 14);

            images = new BufferedImage[4][3];
            for(int i = 0; i < 4; i ++) {
                for(int j = 1; j <= 3; j ++) {
                    images[i][j - 1] = ImageIO.read(getClass().getResource("/img/lion/" + "/" + i + "/" + j + ".png"));
                }
            }
            animalImages.put("Lion", images);
            animalGap.put("Lion", 12);

            images = new BufferedImage[4][3];
            for(int i = 0; i < 4; i ++) {
                for(int j = 1; j <= 3; j ++) {
                    images[i][j - 1] = ImageIO.read(getClass().getResource("/img/dragon/" + i + "/" + j + ".png"));
                }
            }
            animalImages.put("Dragon", images);
            animalGap.put("Dragon", 0);

            images = new BufferedImage[4][3];
            for(int i = 0; i < 4; i ++) {
                for(int j = 1; j <= 3; j ++) {
                    images[i][j - 1] = ImageIO.read(getClass().getResource("/img/monster.png"));
                }
            }
            animalImages.put("New", images);
            animalGap.put("New", 10);


            for(int i = 0; i < boombFrames; i ++) {
                img = ImageIO.read(getClass().getResource("/img/volcanoes/" + i + ".gif"));
                boombImages.add(img);
            }

            img = ImageIO.read(getClass().getResource("/img/boombController.png"));
            powerUpImages.put("boombController", img);
            img = ImageIO.read(getClass().getResource("/img/decreaseBoomb.png"));
            powerUpImages.put("decreaseBoomb", img);
            img = ImageIO.read(getClass().getResource("/img/decreasePoint.png"));
            powerUpImages.put("decreasePoint", img);
            img = ImageIO.read(getClass().getResource("/img/decreaseRadius.png"));
            powerUpImages.put("decreaseRadius", img);
            img = ImageIO.read(getClass().getResource("/img/decreaseSpeed.png"));
            powerUpImages.put("decreaseSpeed", img);
            img = ImageIO.read(getClass().getResource("/img/ghost.png"));
            powerUpImages.put("makeGhost", img);
            img = ImageIO.read(getClass().getResource("/img/increaseBoomb.png"));
            powerUpImages.put("increaseBoomb", img);
            img = ImageIO.read(getClass().getResource("/img/increasePoint.png"));
            powerUpImages.put("increasePoint", img);
            img = ImageIO.read(getClass().getResource("/img/increaseRadius.png"));
            powerUpImages.put("increaseRadius", img);
            img = ImageIO.read(getClass().getResource("/img/increaseSpeed.png"));
            powerUpImages.put("increaseSpeed", img);




        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void makeMap() {
        map = new Map();
    }

    public static Map getMap() {
        return map;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(Console.getConsole().isPlayer()) {
            if (keyEvent.getKeyCode() == KeyEvent.VK_D) {
                Console.getConsole().move(1);
            }
            if (keyEvent.getKeyCode() == KeyEvent.VK_A) {
                Console.getConsole().move(3);
            }
            if (keyEvent.getKeyCode() == KeyEvent.VK_W) {
                Console.getConsole().move(0);
            }
            if (keyEvent.getKeyCode() == KeyEvent.VK_S) {
                Console.getConsole().move(2);
            }
            if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {
                Console.getConsole().boomb();
            }
            if (keyEvent.getKeyCode() == KeyEvent.VK_B) {
                Console.getConsole().explosion();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    public void setNowGame(String nowGame) {
        this.nowGame = nowGame;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        Scanner sc = new Scanner(nowGame);
        int w = Integer.valueOf(sc.nextLine());
        int h = Integer.valueOf(sc.nextLine());
        int doorI = Integer.valueOf(sc.nextLine());
        int doorJ = Integer.valueOf(sc.nextLine());
        boolean isDoorVisible = Boolean.valueOf(sc.nextLine());
        boolean isDoorOpen = Boolean.valueOf(sc.nextLine());
        int time = Integer.valueOf(sc.nextLine());
        int level = Integer.valueOf(sc.nextLine());
        int num = 0;

        MainFrame.getFrame().getInformationPanel().getInformation().setTimeLabel(time);
        MainFrame.getFrame().getInformationPanel().getInformation().setLevelLabel(level);


        for(int i = 0; i < h; i ++) {
            for(int j = 0; j < w; j ++) {
                drawCell(graphics, sc.nextLine());
            }
        }

        if(isDoorVisible) {
            graphics.drawImage(closeDoor, doorJ * cellWidth, doorI * cellHeight, cellWidth, cellHeight, null);
        }

        if(isDoorOpen) {
            graphics.drawImage(openDoor, doorJ * cellWidth, doorI * cellHeight, cellWidth, cellHeight, null);
        }

        num = Integer.valueOf(sc.nextLine());
        for(int i = 0; i < num; i ++) {
            drawBoomb(graphics, sc.nextLine());
        }

        num = Integer.valueOf(sc.nextLine());
        for(int i = 0; i < num; i ++) {
            drawPowerUp(graphics, sc.nextLine());
        }

        num = Integer.valueOf(sc.nextLine());
        for(int i = 0; i < num; i ++) {
            drawGuy(graphics, sc.nextLine());
        }

        num = Integer.valueOf(sc.nextLine());
        for(int i = 0; i < num; i ++) {
            drawAnimal(graphics, sc.nextLine());
        }
    }

    private void drawPowerUp(Graphics graphics, String s) {
        Scanner sc = new Scanner(s);
        int x = Integer.valueOf(sc.next());
        int y = Integer.valueOf(sc.next());
        String type = sc.next();
        graphics.drawImage(powerUpImages.get(type), x + powerUpGap, y + powerUpGap, cellWidth - 2 * powerUpGap, cellHeight - 2 * powerUpGap, null);
    }

    private void drawBoomb(Graphics graphics, String s){
        Scanner sc = new Scanner(s);
        int x = Integer.valueOf(sc.next());
        int y = Integer.valueOf(sc.next());
        int ind = Integer.valueOf(sc.next());
        if(ind == boombFrames)
            ind --;
        graphics.drawImage(boombImages.get(ind), x + boombGap, y + boombGap, cellWidth - 2 * boombGap, cellHeight - 2 * boombGap, null);
    }

    private void drawAnimal(Graphics graphics, String s) {
        Scanner sc = new Scanner(s);
        int x = Integer.valueOf(sc.next());
        int y = Integer.valueOf(sc.next());
        String type = sc.next();
        BufferedImage[][] imgs = new BufferedImage[4][3];
        imgs = animalImages.get(type);
        int gap = animalGap.get(type);
        int dir = Integer.valueOf(sc.next());
        int ind = -1;
        if(dir != -1) {
            ind = Integer.valueOf(sc.next());
            if(ind == 9)
                ind = 0;
        }
        if (dir == -1)
            graphics.drawImage(imgs[2][1], x + gap, y + gap, cellWidth - 2 * gap, cellHeight - 2 * gap, null);
        else
            graphics.drawImage(imgs[dir][inds[ind]], x + gap, y + gap, cellWidth - 2 * gap, cellHeight - 2 * gap, null);

    }

    private void drawGuy(Graphics graphics, String s) {
        Scanner sc = new Scanner(s);
        int x = Integer.valueOf(sc.next());
        int y = Integer.valueOf(sc.next());
        String type = sc.next();
        int dir = Integer.valueOf(sc.next());
        int ind = -1;
        if(dir != -1) {
            ind = Integer.valueOf(sc.next());
        }
        int id = Integer.valueOf(sc.next());
        Boolean isDie = Boolean.valueOf(sc.next());
        Boolean isGhost = Boolean.valueOf(sc.next());
        int point = Integer.valueOf(sc.next());
        if(isDie == false) {
            MainFrame.getFrame().getInformationPanel().getInformation().setPointLabel(String.valueOf(point), id);
            graphics.setColor(colors[id]);
            graphics.fillOval(x + guyGap, y + guyGap, cellWidth - 2 * guyGap, cellHeight - 2 * guyGap);
            if(isGhost) {
                graphics.drawImage(ghostImage, x + guyGap, y + guyGap, cellWidth - 2 * guyGap, cellHeight - 2 * guyGap, null);
                return;
            }
            if(ind == 9)
                ind = 0;
            if (dir == -1)
                graphics.drawImage(natGuy, x + guyGap, y + guyGap, cellWidth - 2 * guyGap, cellHeight - 2 * guyGap, null);
            else
                graphics.drawImage(guyImages[dir][inds[ind]], x + guyGap, y + guyGap, cellWidth - 2 * guyGap, cellHeight - 2 * guyGap, null);
        }
        else
            MainFrame.getFrame().getInformationPanel().getInformation().setPointLabel("Dead", id);
    }

    private void drawCell(Graphics graphics, String s) {
        Scanner sc = new Scanner(s);
        int x = Integer.valueOf(sc.next());
        int y = Integer.valueOf(sc.next());
        String type = sc.next();
        graphics.drawImage(cellImages.get(type), x, y, cellWidth, cellHeight, null);
        int ind = Integer.valueOf(sc.next());
        if(ind != 100) {
            if(ind == explosionFrames)
                ind --;
            graphics.drawImage(explosion.get(ind), x, y, cellWidth, cellHeight, null);
        }
    }
}
