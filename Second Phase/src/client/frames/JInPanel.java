package client.frames;

import javax.swing.*;
import java.awt.*;

public class JInPanel extends JPanel {
    private JTextField jtf;
    private JLabel jl;
    JInPanel(String str)
    {
        super();
        jtf = new JTextField("", 15);
        jl = new JLabel(str);
        add(jl);
        add(jtf);
        setBackground(new Color(198, 255, 78));
    }
    String getFieldText()
    {
        return jtf.getText();
    }
}