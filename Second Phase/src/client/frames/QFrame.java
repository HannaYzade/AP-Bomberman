package client.frames;

import javax.swing.*;
import java.awt.*;

public class QFrame extends JFrame{
    QFrame()
    {
        super();
        setSize(500, 200);
        setResizable(false);
        setLocationRelativeTo(null);
        requestFocus();
        getContentPane().setBackground(new Color(198, 255, 78));
    }
}
