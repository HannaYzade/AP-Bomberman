import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class PowerUp {

    private BufferedImage img = null;
    protected int ii, jj;

    public PowerUp(String s, int ii, int jj) {
        try {
            img = ImageIO.read(getClass().getResource(s));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.ii = ii;
        this.jj = jj;
    }

    public abstract void power();

    public void draw(Graphics graphics) {
        graphics.drawImage(img, jj * Cell.width + 10, ii * Cell.hight + 10, Cell.width - 20, Cell.hight - 20, null);
    }

    public void save(int gameId) throws SQLException {
        PreparedStatement query = Main.insertObject();
        query.setInt(1, gameId);
        query.setString(2, getClass().getName());
        query.setInt(3, ii);
        query.setInt(4, jj);
        query.setInt(5, -1);
        query.execute();
    }
}