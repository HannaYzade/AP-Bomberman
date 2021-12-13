package client.frames;

import client.Console;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ChatPanel extends JPanel {
    ;
    private JLabel[] messages;
    private JTextField field;
    private BufferedImage background;
    private final int gap = 30;
    private final int mesNum = 6;
    private Color[] colors = {Color.magenta, Color.blue, Color.cyan, Color.green, Color.yellow, Color.orange, Color.red};

    public ChatPanel() {
        setLayout(null);
        try {
            background = ImageIO.read(getClass().getResource("/img/chat.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        messages = new JLabel[mesNum];
        field = new JTextField() {
            {
                addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent keyEvent) {

                    }

                    @Override
                    public void keyPressed(KeyEvent keyEvent) {
                        if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                            if (field.getText().length() > 0) {
                                Console.getConsole().sendMessage(field.getText());
                                field.setText(""); }
                        }
                    }

                    @Override
                    public void keyReleased(KeyEvent keyEvent) {

                    }
                });
            }
        };

        if(!Console.getConsole().isPlayer())
            field.setEnabled(false);
        for(int i = 0; i < mesNum; i ++) {
            messages[i] = new JLabel();
            messages[i].setOpaque(false);
            messages[i].setBackground(Color.white);
            messages[i].setFont(new Font("Serif", Font.BOLD, 15));
        }

    }

    @Override
    public void paintComponent(Graphics graphics) {
        graphics.drawImage(background, 0 , 0, getWidth(), getHeight(), null);
        int h = gap;
        for(int i = mesNum - 1; i >= 0; i --) {
            add(messages[i]);
            messages[i].setBounds(gap, h, getWidth() - 2 * gap, 3 * gap / 2);
            h += 2 * gap;
        }
        add(field);
        field.setBounds(gap, getHeight() - 3 * gap, getWidth() - 2 * gap, 2 * gap);
    }

    public void showw(String str, int id) {
        for(int i = mesNum - 1; i > 0; i --) {
            messages[i].setBackground(messages[i - 1].getBackground());
            messages[i].setText(messages[i - 1].getText());
            if(messages[i].getBackground() == Color.white)
                messages[i].setOpaque(false);
            else
                messages[i].setOpaque(true);
        }
        messages[0].setText(" " + str + " ");
        messages[0].setBackground(colors[id]);
        messages[0].setOpaque(true);
    }
}
