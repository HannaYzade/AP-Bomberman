import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class StFrame extends QFrame {
    private static StFrame frame = null;
    private JLabel jl;
    private JButton jb1;
    private JButton jb2;
    private JPanel contentPane = (JPanel)getContentPane();

    public StFrame()
    {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        jl = new JLabel("  Continue or new?");
        jb1 = new JButton("Continue");
        jb2 = new JButton("New");
        contentPane.setLayout(new GridLayout(2, 1));
        contentPane.add(jl);
        JPanel jp = new JPanel();
        contentPane.add(jp);
        jp.add(jb1);
        jp.add(jb2);
        jb1.setPreferredSize(new Dimension(120, 30));
        jb2.setPreferredSize(new Dimension(120, 30));
        addListener();
    }


    private void addListener() {
        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
                sizeFrame sizeframe = sizeFrame.getFrame();
                sizeframe.setVisible(true);
            }
        });

        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
                MainFrame.makeFrame(18, 13);
                MainFrame frame = MainFrame.getFrame();
                frame.load();
                frame.setVisible(true);
            }
        });
    }

    public static StFrame getFrame()
    {
        if(frame == null)
        {
            frame = new StFrame();
        }
        return frame;
    }
}
