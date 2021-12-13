package client.frames;


import javax.swing.*;
import java.awt.*;

public class InformationPanel  extends JPanel{

    private int width;
    private int height;
    private GameInformation information;
    private ChatPanel chatPanel;

    public InformationPanel(int w, int h) {
        super();
        width = w;
        height = h;
        setPreferredSize(new Dimension(width, height));
        setLayout(new GridLayout(2, 1));
        information = new GameInformation();
        chatPanel = new ChatPanel();
        add(information);
        add(chatPanel);
    }

    public GameInformation getInformation() {
        return information;
    }

    public ChatPanel getChatPanel() {
        return chatPanel;
    }
}
