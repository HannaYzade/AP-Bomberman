package client.frames;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MainFrame extends JFrame {
    public static final int fixMapW = 18;
    public static final int fixMapH = 13;
    public static final int cellWidth = 80;
    public static final int cellHeight = 80;
    private int width;
    private int height;
    private static MainFrame mainFrame = null;
    private Map map;
    private InformationPanel informationPanel;

    private BufferedImage gameOverImage;
    private boolean isGameOver = false;

    public MainFrame(int w, int h) {
        super();
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle bounds = env.getMaximumWindowBounds();
        width = bounds.width;
        height = bounds.height;
        setSize(width, height);
        informationPanel = new InformationPanel(width - fixMapW * cellWidth - 13, height);
        Map.makeMap();
        map = Map.getMap();
        map.setPreferredSize(new Dimension(w * cellWidth, h * cellHeight));
        JScrollPane scrollPane = new JScrollPane(map, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(fixMapW * cellWidth + 13, height));
        getContentPane().add(informationPanel);
        getContentPane().add(scrollPane);
        try {
            gameOverImage = ImageIO.read(getClass().getResource("/img/gameOver.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void makeFrame(int w, int h)
    {
        if(mainFrame == null)
            mainFrame = new MainFrame(w, h);

    }

    public static MainFrame getFrame() {
        return mainFrame;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(isGameOver) {
            g.drawImage(gameOverImage, 0, 0, width, height, null);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }
    }

    public InformationPanel getInformationPanel() {
        return informationPanel;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }


}
