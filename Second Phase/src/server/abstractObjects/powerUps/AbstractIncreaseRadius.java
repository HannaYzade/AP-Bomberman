package server.abstractObjects.powerUps;

import server.abstractObjects.animals.AbstractGuy;

public class AbstractIncreaseRadius extends AbstractPowerUp{

    public AbstractIncreaseRadius(int ii, int jj) {
        super(ii, jj);
    }

    @Override
    public void power(AbstractGuy guy) {
        guy.setBoombRadius(guy.getBoombRadius() + 1);
    }

    @Override
    public String name() {
        return "increaseRadius";
    }
}
