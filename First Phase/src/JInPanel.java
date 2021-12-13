import javax.swing.*;

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
    }
    String getFieldText()
    {
        return jtf.getText();
    }
}
