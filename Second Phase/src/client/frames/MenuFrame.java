package client.frames;

import client.Console;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuFrame extends JFrame {

    JButton newGame;
    JButton join;

    public MenuFrame() {
        super();
        setSize(600, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        requestFocus();
        JPanel contentPane = (JPanel) getContentPane();
        contentPane.setLayout(new GridLayout(1, 2));
        newGame = new JButton("Creat a game") {{
            setFont(new Font("Serif", Font.PLAIN, 24));

            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    SizeFrame sizeFrame = new SizeFrame();
                    sizeFrame.setVisible(true);
                    dispose();

                }
            });
        }};

        join = new JButton("Join existing game"){{
            setFont(new Font("Serif", Font.PLAIN, 24));

            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    Console.getConsole().getList();
                    dispose();
                }
            });
        }};

        add(newGame);
        add(join);
    }


}
