import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class sizeFrame extends QFrame{
    private static sizeFrame frame = null;
    private JPanel contentPane = (JPanel)getContentPane();
    private JLabel jl = null;
    private JInPanel jp1 = null;
    private JInPanel jp2 = null;
    private JButton jb = null;

    public sizeFrame(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane.setLayout(new GridLayout(4, 1));
        jl = new JLabel("  Hey! Set the size of map!");
        jp1 = new JInPanel("Width: ");
        jp2 = new JInPanel("Height: ");
        jb = new JButton("OK");
        addListener();
        contentPane.add(jl);
        contentPane.add(jp1);
        contentPane.add(jp2);
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
                if(! flag) {
                    dispose();
                    MainFrame.makeFrame(w, h);
                    MainFrame.getFrame().setVisible(true);
                }
            }
        });
    }

    public static sizeFrame getFrame()
    {
        if(frame == null)
            frame = new sizeFrame();
        return frame;
    }

}
