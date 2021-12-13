package server.abstractObjects;

import server.abstractObjects.animals.AbstractBadAnimal;
import server.abstractObjects.animals.AbstractGuy;
import server.abstractObjects.powerUps.AbstractPowerUp;

import java.util.ArrayList;

public class AbstractCell {

    public static final int width = 80;
    public static final int height = 80;
    private int ii, jj;
    private CellKind kind;
    private AbstractExplosionAnimation explosion;
    private ArrayList<AbstractBadAnimal> badanimals;
    private ArrayList<AbstractGuy> guys;
    private AbstractBoomb boomb;
    private AbstractPowerUp powerUp;

    public AbstractCell(CellKind kind, int ii, int jj)
    {
        super();
        this.kind = kind;
        this.ii = ii;
        this.jj = jj;
        badanimals = new ArrayList<>();
        guys = new ArrayList<>();
        powerUp = null;
        boomb = null;
    }

    public ArrayList<AbstractBadAnimal> getBadanimals() {
        return badanimals;
    }

    public ArrayList<AbstractGuy> getGuy() {
        return guys;
    }

    public CellKind getKind() {
        return kind;
    }

    public void setKind(CellKind kind) {
        this.kind = kind;
    }

    public AbstractBoomb getBoomb() {
        return boomb;
    }

    public void setBoomb(AbstractBoomb boomb) {
        this.boomb = boomb;
    }

    public AbstractPowerUp getPowerUp() {
        return powerUp;
    }

    public void setPowerUp(AbstractPowerUp powerUp) {
        this.powerUp = powerUp;
    }

    public AbstractExplosionAnimation getExplosion() {
        return explosion;
    }

    public void setExplosion(AbstractExplosionAnimation explosion) {
        this.explosion = explosion;
    }

    public int getIi() {
        return ii;
    }

    public int getJj() {
        return jj;
    }

    @Override
    public String toString()
    {
        String s = "";
        s += (jj * AbstractCell.width) + " " + (ii * AbstractCell.height) + " ";
        s += kind.toString() + " ";
        if(explosion == null) {
            s += 100 + "\n";
        }
        else {
            s += explosion.getInd() + "\n";
        }
        return s;
    }
}















