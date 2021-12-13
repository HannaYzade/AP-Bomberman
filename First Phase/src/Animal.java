import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public  abstract class Animal {

    protected AnimalAnimation[] animalAnimations;
    protected boolean isRunning;
    protected int currentDir;
    protected int gap;
    protected int speed;
    protected int x;
    protected int y;
    protected int ii;
    protected int jj;
    protected final BufferedImage natImage;
    protected BufferedImage nowImage;


    public Animal(int speed, int jj, int ii, String path, int gap) {
        this.speed = speed;
        this.gap = gap;
        this.jj = jj;
        this.ii = ii;
        this.x = jj * Cell.width;
        this.y = ii * Cell.hight;
        try {
            if(this instanceof Guy)
                nowImage = ImageIO.read(getClass().getResource(path + "nat.gif"));
            else
                nowImage = ImageIO.read(getClass().getResource(path + "nat.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        natImage = nowImage;
        BufferedImage moveMent[][] = new BufferedImage[4][3];
        for(int i = 0; i < 4; i ++) {
            for(int j = 1; j <= 3; j ++) {
                URL res;
                if(this instanceof Guy)
                    res = getClass().getResource(path + "/" + i + "/" + j + ".gif");
                else
                    res = getClass().getResource(path + "/" + i + "/" + j + ".png");
                try {
                    moveMent[i][j - 1] = ImageIO.read(res);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        animalAnimations = new AnimalAnimation[4];
        animalAnimations[0] = new AnimalAnimation(0, -1, moveMent[0], this);
        animalAnimations[1] = new AnimalAnimation(1, 0, moveMent[1], this);
        animalAnimations[2] = new AnimalAnimation(0, 1, moveMent[2], this);
        animalAnimations[3] = new AnimalAnimation(-1, 0 , moveMent[3], this);
    }

    protected boolean isOkCell(int ii, int jj, boolean isGhost) {
        if(ii < 0 || jj < 0)
            return false;
        if(ii >= MapPanel.getMap().getH() || jj >= MapPanel.getMap().getW()) {
            return false;
        }
        if((MapPanel.getMap().getCells())[ii][jj].getKind() == CellKind.GRASS && (MapPanel.getMap().getCells())[ii][jj].getBoomb() == null)
            return true;
        if(isGhost && (MapPanel.getMap().getCells())[ii][jj].getBoomb() == null)
            return true;
        return false;
    }

    public void  draw(Graphics graphics) {
        BufferedImage a = nowImage;
        if(this instanceof Guy && MapPanel.getMap().getCells()[ii][jj].getKind() != CellKind.GRASS)
            nowImage = MapPanel.getMap().getGuy().getGhost();
        graphics.drawImage(nowImage, x + gap, y + gap, Cell.width - 2 * gap, Cell.hight - 2 * gap, null);
        nowImage = a;
    }

    public int getSpeed() {
        return speed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setNowImage(BufferedImage nowImage) {
        this.nowImage = nowImage;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getIi() {
        return ii;
    }

    public int getJj() {
        return jj;
    }

    public BufferedImage getNowImage() {
        return nowImage;
    }

    public void setIi(int ii) {
        this.ii = ii;
    }

    public void setJj(int jj) {
        this.jj = jj;
    }

    public void save(int gameId) throws SQLException {
        int ind = 0;
        if(currentDir != -1) {
            ind = currentDir * 9 + animalAnimations[currentDir].getInd();
        }
        PreparedStatement query = Main.insertObject();
        query.setInt(1, gameId);
        query.setString(2, getClass().getName());
        query.setInt(3, x);
        query.setInt(4, y);
        query.setInt(5, ind);
        query.execute();
    }


    public void load(int frameInd) {
        int frameNum = frameInd % 9;
        int animNum = frameInd / 9;
        ii = y / Cell.hight;
        jj = x / Cell.width;
        if(frameNum >= 4 && frameNum < 9 && (animNum == 1 || animNum == 2)) {
            ii += animalAnimations[animNum].getDy();
            jj += animalAnimations[animNum].getDx();
        }
        if(frameNum < 4  && frameNum > 1 && (animNum == 0 || animNum == 3)) {
            ii -= animalAnimations[animNum].getDy();
            jj -= animalAnimations[animNum].getDx();
        }
        animalAnimations[animNum].setInd(frameNum);
        if(frameNum != 0) {
            isRunning = true;
            currentDir = animNum;
        }
        else {
            isRunning = false;
        }

        if(this instanceof  BadAnimal)
            MapPanel.getMap().getCells()[ii][jj].getBadanimals().add((BadAnimal) this);
        else
            MapPanel.getMap().getCells()[ii][jj].setGuy((Guy)this);
    }
}

