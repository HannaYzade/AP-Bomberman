package server.abstractObjects.powerUps;

import server.abstractObjects.animals.AbstractGuy;

public class AbstractIncreaseBoomb extends AbstractPowerUp {

    public AbstractIncreaseBoomb(int ii, int jj) {
        super(ii, jj);
    }

    @Override
    public void power(AbstractGuy guy) {
        guy.setBoombLimit(guy.getBoombLimit() + 1);
    }

    @Override
    public String name() {
        return "increaseBoomb";
    }
}
