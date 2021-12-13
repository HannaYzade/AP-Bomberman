import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Guy extends Animal{

    private Thread runGuy;
    private int point;
    private boolean boombRemote;
    private boolean isGhost;
    private BufferedImage ghost;

    public Guy(int speed, int jj, int ii) {
        super(speed, jj, ii, "/img/rabbit/", 15);
        runGuy = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (isRunning) {
                        isRunning = animalAnimations[currentDir].animate();
                        MapPanel.getMap().repaint();
                    }
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        isGhost = false;
        isRunning = false;
        point = 0;
        boombRemote = false;
        try {
            ghost = ImageIO.read(getClass().getResource("/img/rabbit/ghost.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void move(int direction) {
        int disi= ii + animalAnimations[direction].getDy(), disj = jj + animalAnimations[direction].getDx();
        if( isOkCell(disi, disj, isGhost) ) {
            isRunning = true;
            currentDir = direction;
            if (!runGuy.isAlive())
                runGuy.start();
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
        MainFrame.getFrame().getInformationPanel().setPointLabel(point);
        if(point < 0) {
            MainFrame.getFrame().setGameOver(true);
            MainFrame.getFrame().repaint();
        }
    }

    public void setBoombRemote(boolean boombRemote) {
        this.boombRemote = boombRemote;
    }

    public boolean isBoombRemote() {
        return boombRemote;
    }

    public void setGhost(boolean ghost) {
        isGhost = ghost;
    }

    public BufferedImage getGhost() {
        return ghost;
    }

    public boolean isGhost() {
        return isGhost;
    }
}
