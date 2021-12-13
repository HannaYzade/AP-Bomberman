package client.frames;

import javax.swing.*;
import java.awt.*;

public class GameInformation extends JPanel {
    private int width;
    private int height;
    private JLabel levelLabel;
    private JLabel timeLabel;
    private JLabel[] points;

    private Color[] colors = {Color.magenta, Color.blue, Color.cyan, Color.green, Color.yellow, Color.orange, Color.red};

    public GameInformation() {
        super();
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle bounds = env.getMaximumWindowBounds();
        width = bounds.width - MainFrame.fixMapW * MainFrame.cellWidth - 13;
        height = bounds.height / 2;
        setPreferredSize(new Dimension(width , height));
        setLayout(new GridLayout(2, 1));
        setBackground(new Color(195, 151, 131));
        JPanel panel = new JPanel();
        add(panel);
        panel.setLayout(new GridLayout(5, 1));
        panel.setBackground(new Color(195, 151, 131));
        JLabel label = new JLabel("LEVEL", JLabel.CENTER);
        label.setFont(new Font("Serif", Font.PLAIN, 30));
        panel.add(label);
        levelLabel = new JLabel("1", JLabel.CENTER);
        levelLabel.setFont(new Font("Serif", java.awt.Font.PLAIN, 30));
        panel.add(levelLabel);
        label = new JLabel("TIME",JLabel.CENTER);
        label.setFont(new Font("Serif", Font.PLAIN, 30));
        panel.add(label);
        timeLabel = new JLabel("0", JLabel.CENTER);
        timeLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        panel.add(timeLabel);
        label = new JLabel("SCORE", JLabel.CENTER);
        label.setFont(new Font("Serif", Font.PLAIN, 30));
        panel.add(label);
        JPanel pointPanel = new JPanel();
        points = new JLabel[7];
        add(pointPanel);
        pointPanel.setLayout(new GridLayout(7, 1));
        for(int i = 0; i < 7; i ++) {
            points[i] = new JLabel("", JLabel.CENTER);
            points[i].setOpaque(true);
            points[i].setFont(new Font("Serif", Font.PLAIN, 24));
            points[i].setBackground(colors[i]);
            pointPanel.add(points[i]);
        }
    }

    public void setPointLabel(String point, int i) {
        points[i].setText(point);
    }

    public void setLevelLabel(int level) {
        levelLabel.setText(String.valueOf(level));
    }

    public void setTimeLabel(int time) {
        timeLabel.setText(String.valueOf(time));
    }

    public int getTime() {
        return Integer.valueOf(timeLabel.getText());
    }
}
