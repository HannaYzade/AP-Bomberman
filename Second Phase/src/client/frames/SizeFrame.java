package client.frames;

import client.Console;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SizeFrame extends QFrame {
    private static SizeFrame frame = null;
    private JPanel contentPane = (JPanel)getContentPane();
    private JLabel jl;
    private JInPanel jp1;
    private JInPanel jp2;
    private JInPanel jp3;
    private JButton jb;

    public SizeFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane.setLayout(new GridLayout(5, 1));
        jl = new JLabel("  Hey! Set the size of map and number of players!");
        jp1 = new JInPanel("Width: ");
        jp2 = new JInPanel("Height: ");
        jp3 = new JInPanel("Players: ");
        jb = new JButton("OK");
        addListener();
        contentPane.add(jl);
        contentPane.add(jp1);
        contentPane.add(jp2);
        contentPane.add(jp3);
        contentPane.add(jb);
    }

    private void addListener() {
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String ws = jp1.getFieldText();
                boolean flag = false;
                int w = Integer.valueOf(ws);
                if(w < 18) {
                    JOptionPane.showMessageDialog(frame, "Width must be at least 18");
                    flag = true;
                }
                String hs = jp2.getFieldText();
                int h = Integer.valueOf(hs);
                if(h < 13) {
                    JOptionPane.showMessageDialog(frame, "Height must be at least 13");
                    flag = true;
                }

                String player = jp3.getFieldText();
                int num = Integer.valueOf(player);
                if(num > 7 || num < 1) {
                    JOptionPane.showMessageDialog(frame, "Number of players must be between 1 and 7");
                    flag = true;
                }
                if(! flag) {
                    dispose();
                    Console.getConsole().getGame(w, h, num);
                }
            }
        });
    }

}
