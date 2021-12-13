import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MainFrame extends JFrame {

    private final int fixMapW = 18;
    private final int fixMapH = 13;
    private static MainFrame mainFrame = null;
    private MapPanel mapPanel;
    private InformationPanel informationPanel;
    private int width;
    private int height;
    private BufferedImage gameOverImage;
    private boolean isGameOver = false;

    public MainFrame(int w, int h) {
        super();
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        setUndecorated(true);
        pack();
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle bounds = env.getMaximumWindowBounds();
        width = bounds.width;
        height = bounds.height;
        informationPanel = new InformationPanel(width - fixMapW * Cell.width - 13, height);
        MapPanel.makeMap(w, h);
        mapPanel = MapPanel.getMap();
        JScrollPane scrollPane = new JScrollPane(mapPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(fixMapW * Cell.width + 13, height));
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
        informationPanel.runThread();
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

    public  void load() {
        mapPanel.load();
    }

}