package client.frames;

import client.Console;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;


public class GameListFrame extends JFrame {

    private ArrayList<ArrayList<String> > list;
    private String string;
    private JButton join;
    private JButton next;
    private JButton prev;
    private JButton obs;
    private int h, w;
    private GameInformation information;
    int ind = 0;

    public GameListFrame(String str) {
        super();
        setSize(400,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        requestFocus();
        JPanel contentPane = (JPanel)getContentPane();
        contentPane.setLayout(new BorderLayout());
        string = str;
        information = new GameInformation();
        contentPane.add(information, BorderLayout.CENTER);
        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.SOUTH);
        panel.setLayout(new GridLayout(2, 2));
        creatList();
        next = new JButton("Next") {{
            if(list.size() == 1)
                setEnabled(false);
            addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    ind ++;
                    prev.setEnabled(true);
                    showPoints();
                    if(ind == list.size() - 1)
                        setEnabled(false);
                    else
                        setEnabled(true);
                }
            });
        }};
        prev = new JButton("Previous") {{
            setEnabled(false);
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    ind --;
                    next.setEnabled(true);
                    showPoints();
                    if(ind == 0)
                        setEnabled(false);
                    else
                        setEnabled(true);
                }
            });
        }};

        panel.add(prev);
        panel.add(next);
        join = new JButton("Join as a player") {
            {
                addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        Console.getConsole().getExsistGame(true, ind, Integer.valueOf(list.get(ind).get(0)), Integer.valueOf(list.get(ind).get(1)));
                        dispose();
                    }
                });
            }
        };

        obs = new JButton("Join as a viewer") {
            {
                addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        Console.getConsole().getExsistGame(false, ind, Integer.valueOf(list.get(ind).get(0)), Integer.valueOf(list.get(ind).get(1)));
                        dispose();
                    }
                });
            }
        };


        panel.add(join);
        panel.add(obs);
        if(str.length() > 4) {
            showPoints();
        }

    }

    private void showPoints() {
        information.setLevelLabel(Integer.valueOf(list.get(ind).get(2)));
        information.setTimeLabel(Integer.valueOf(list.get(ind).get(3)));
        for(int i = 4; i < list.get(ind).size(); i ++)
            information.setPointLabel(list.get(ind).get(i), i - 4);
        for(int i = list.get(ind).size(); i < 7; i ++)
            information.setPointLabel("", i - 4);
    }

    private void creatList() {
        list = new ArrayList<>();
        Scanner scanner = new Scanner(string);
        scanner.nextLine();
        int num = Integer.valueOf(scanner.nextLine());
        for(int i = 0; i < num; i ++) {
            loadGame(scanner.nextLine());
        }
    }

    private void loadGame(String s) {
        ArrayList<String> arr = new ArrayList<>();
        Scanner sc = new Scanner(s);
        arr.add(sc.next());
        arr.add(sc.next());
        arr.add(sc.next());
        arr.add(sc.next());
        int num = Integer.valueOf(sc.next());
        for(int i = 0; i < num; i ++)
            arr.add(sc.next());
        list.add(arr);

    }
}
