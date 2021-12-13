import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class Cell {
    final static int width = 80;
    final static int hight = 80;
    private int x, y;
    private int ii, jj;
    private CellKind kind;
    private BufferedImage img;
    private BufferedImage explosionImg;
    private boolean isExplosion;
    private ExplosionAnimation explosion;
    private ArrayList<BadAnimal> badanimals;
    private PowerUp powerUp;
    private Boomb boomb;
    private Guy guy;


    public Cell(CellKind kind, int ii, int jj)
    {
        super();
        this.kind = kind;
        this.ii = ii;
        this.jj = jj;
        this.x = jj * width;
        this.y = ii * hight;
        badanimals = new ArrayList<>();
        powerUp = null;
        boomb = null;
        guy = null;
        URL res = getClass().getResource(kind.getPath());
        try {
            img = ImageIO.read(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Boomb getBoomb() {
        return boomb;
    }

    public void setBoomb(Boomb boomb) {
        this.boomb = boomb;
    }


    public void draw(Graphics g) {
        g.drawImage(img, x, y, width, hight, null);
        if(isExplosion)
            g.drawImage(explosionImg, x, y, width, hight, null);
    }

    public void save(PrintStream printer) {
        printer.println(getClass().toString());
    }

    /*public static Cell load(RandomAccessFile file, int x, int y) {
        String s = null;
        try {
            s = file.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (s) {
            case "class Wall": {
                Cell c = new Wall(x, y);
                try {
                    c.setBoomb(Boolean.valueOf(file.readLine()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return c;
            }
            case "class RockWall": {
                Cell c = new RockWall(x, y);
                try {
                    c.setBoomb(Boolean.valueOf(file.readLine()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return c;
            }
            case "class Grass": {
                Cell c = new Grass(x, y);
                try {
                    c.setBoomb(Boolean.valueOf(file.readLine()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return c;
            }
        }
        return null;
    }*/


    public boolean isExplosion() {
        return isExplosion;
    }

    public void setIsExplosion(boolean explosion) {
        isExplosion = explosion;
    }

    public ExplosionAnimation getExplosion() {
        return explosion;
    }

    public void setExplosion(ExplosionAnimation explosion) {
        this.explosion = explosion;
    }

    public void setExplosionImg(BufferedImage explosionImg) {
        this.explosionImg = explosionImg;
    }

    public ArrayList<BadAnimal> getBadanimals() {
        return badanimals;
    }

    public PowerUp getPowerUp() {
        return powerUp;
    }

    public void setPowerUp(PowerUp powerUp) {
        this.powerUp = powerUp;
    }

    public Guy getGuy() {
        return guy;
    }

    public void setGuy(Guy guy) {
        this.guy = guy;
    }

    public CellKind getKind() {
        return kind;
    }

    public void setKind(CellKind kind) {
        this.kind = kind;
        try {
            img = ImageIO.read(getClass().getResource(kind.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(int gameId) throws SQLException {
        PreparedStatement query = Main.insertObject();
        query.setInt(1, gameId);
        query.setString(2, kind.toString());
        query.setInt(3, ii);
        query.setInt(4, jj);
        if(isExplosion) {
            query.setInt(5, ExplosionAnimation.frameNumber - explosion.getImages().size());
        }
        else
            query.setInt(5, -1);
        query.execute();
        if(powerUp != null)
            powerUp.save(gameId);
    }


    public void load(int frameNum) {
        isExplosion = true;
        explosion = new ExplosionAnimation(frameNum, this);
        MapPanel.getMap().getExplosioningCells().add(this);
    }
}













