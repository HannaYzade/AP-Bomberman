import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class Boomb {
    private int ii, jj;
    private int x, y;
    private BufferedImage nowImage;
    private BoombAnimation boombAnimation;
    private int explosionDistance;


    public Boomb(int ii, int jj) {
        this.ii = ii;
        this.jj = jj;
        x = jj * Cell.width;
        y = ii * Cell.hight;
        boombAnimation = new BoombAnimation(this);
        explosionDistance = 0;
    }

    public void draw(Graphics graphics) {
        graphics.drawImage(nowImage, x + 5, y + 5, Cell.width - 10, Cell.hight - 10, null);
    }

    public int getIi() {
        return ii;
    }

    public int getJj() {
        return jj;
    }


    public Animatable getAnimatable() {
        return boombAnimation;
    }

    public void setNowImage(BufferedImage nowImage) {
        this.nowImage = nowImage;
    }

    public int getExplosionDistance() {
        return explosionDistance;
    }

    public void setExplosionDistance(int explosionDistance) {
        this.explosionDistance = explosionDistance;
    }

    public void save(int gameId) throws SQLException {
        PreparedStatement query = Main.insertObject();
        query.setInt(1, gameId);
        query.setString(2, getClass().getName());
        query.setInt(3, ii);
        query.setInt(4, jj);
        query.setInt(5, BoombAnimation.frameNumber - boombAnimation.getImages().size());
        query.execute();
    }

    public void load(int frameInd) {
        boombAnimation = new BoombAnimation(this, frameInd);
    }
}
