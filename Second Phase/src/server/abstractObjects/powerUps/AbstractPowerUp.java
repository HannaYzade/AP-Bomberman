package server.abstractObjects.powerUps;

import server.abstractObjects.AbstractCell;
import server.abstractObjects.animals.AbstractGuy;

public abstract class AbstractPowerUp {

    protected int ii, jj;

    public AbstractPowerUp(int ii, int jj) {
        this.ii = ii;
        this.jj = jj;
    }

    public abstract void power(AbstractGuy guy);

    public abstract  String name();

    @Override
    public String toString() {
        String s = "";
        s += (jj * AbstractCell.width) + " " + (ii * AbstractCell.height) + " ";
        s += name() + "\n";
        return s;
    }
}
