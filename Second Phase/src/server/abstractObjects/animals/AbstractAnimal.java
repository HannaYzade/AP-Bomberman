package server.abstractObjects.animals;

import server.abstractObjects.AbstractCell;
import server.abstractObjects.CellKind;
import server.Game;

public abstract class AbstractAnimal {
    protected int ii, jj;
    protected int x, y;
    protected int speed;
    protected Game game;
    protected AbstractAnimalAnimation[] animalAnimations;
    protected boolean isRunning;
    protected int currentDir;

    public AbstractAnimal(int speed, int ii, int jj, Game game) {
        this.speed = speed;
        this.game = game;
        this.jj = jj;
        this.ii = ii;
        this.x = jj * AbstractCell.width;
        this.y = ii * AbstractCell.height;
        currentDir = -1;
        animalAnimations = new AbstractAnimalAnimation[4];
        animalAnimations[0] = new AbstractAnimalAnimation(0, -1, this, game);
        animalAnimations[1] = new AbstractAnimalAnimation(1, 0, this, game);
        animalAnimations[2] = new AbstractAnimalAnimation(0, 1, this, game);
        animalAnimations[3] = new AbstractAnimalAnimation(-1, 0 , this, game);
    }

    protected boolean isOkCell(int ii, int jj, boolean isGhost) {
        if(ii < 0 || jj < 0)
            return false;
        if(ii >= game.getHeight() || jj >= game.getWidth()) {
            return false;
        }
        if(game.getCells()[ii][jj].getKind() == CellKind.GRASS && game.getCells()[ii][jj].getBoomb() == null)
            return true;
        if(isGhost && game.getCells()[ii][jj].getBoomb() == null)
            return true;
        return false;
    }


    public int getIi() {
        return ii;
    }

    public int getJj() {
        return jj;
    }

    public void setIi(int ii) {
        this.ii = ii;
    }

    public void setJj(int jj) {
        this.jj = jj;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public  abstract String getName();
    @Override
    public String toString() {
        String s = "";
        s += x + " " + y + " ";
        s += getName() + " ";
        s += currentDir + " ";
        if(currentDir != -1) {
            s += animalAnimations[currentDir].getInd() + " ";
        }

        if(this instanceof AbstractGuy) {
            s += ((AbstractGuy)this).getGuyId() + " ";
            s += ((AbstractGuy)this).isDie() + " ";
            s += (((AbstractGuy)this).isGhost() && game.getCells()[ii][jj].getKind() != CellKind.GRASS) + " ";
            s += ((AbstractGuy)this).getPoint() + "\n";
        }
        else
            s += "\n";
        return s;
    }
}
