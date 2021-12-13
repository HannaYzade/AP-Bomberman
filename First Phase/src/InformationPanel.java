import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class InformationPanel extends JPanel {

    private int width;
    private int height;
    private JLabel levelLabel;
    private JLabel pointLabel;
    private JLabel timeLabel;
    private Thread timeThread;

    public InformationPanel(int w, int h) {
        super();
        width = w;
        height = h;
        setPreferredSize(new Dimension(width , height));
        setLayout(new GridLayout(2, 1));
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));
        panel.setBackground(new Color(195, 151, 131));
        add(panel);
        JLabel label = new JLabel("LEVEL", JLabel.CENTER);
        label.setFont(new Font("Serif", Font.PLAIN, 48));
        panel.add(label);
        levelLabel = new JLabel("1", JLabel.CENTER);
        levelLabel.setFont(new Font("Serif", Font.PLAIN, 48));
        panel.add(levelLabel);
        label = new JLabel("TIME",JLabel.CENTER);
        label.setFont(new Font("Serif", Font.PLAIN, 48));
        panel.add(label);
        timeLabel = new JLabel("0", JLabel.CENTER);
        timeLabel.setFont(new Font("Serif", Font.PLAIN, 48));
        panel.add(timeLabel);
        label = new JLabel("SCORE", JLabel.CENTER);
        label.setFont(new Font("Serif", Font.PLAIN, 48));
        panel.add(label);
        pointLabel = new JLabel("0", JLabel.CENTER);
        pointLabel.setFont(new Font("Serif", Font.PLAIN, 48));
        panel.add(pointLabel);
        timeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    synchronized (Color.magenta) {
                        int cnt = getTime();
                        timeLabel.setText(String.valueOf(cnt + 1));
                        if (cnt >= 300)
                            MapPanel.getMap().getGuy().setPoint(MapPanel.getMap().getGuy().getPoint() - 1);
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void runThread() {
        if(! timeThread.isAlive())
            timeThread.start();
    }

    public void setPointLabel(int point) {
        pointLabel.setText(String.valueOf(point));
    }

    public void setLevelLabel(int level) {
        levelLabel.setText(String.valueOf(level));
    }

    public void setTimeLabel(int time) {
        synchronized (Color.magenta) {
            timeLabel.setText(String.valueOf(time));
        }
    }

    public int getTime() {
        return Integer.valueOf(timeLabel.getText());
    }
}
