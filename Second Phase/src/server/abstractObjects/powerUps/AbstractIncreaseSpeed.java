package server.abstractObjects.powerUps;

import server.abstractObjects.animals.AbstractGuy;

public class AbstractIncreaseSpeed extends AbstractPowerUp {

    public AbstractIncreaseSpeed(int ii, int jj) {
        super(ii, jj);
    }

    @Override
    public void power(AbstractGuy guy) {
        guy.setSpeed(guy.getSpeed() + AbstractGuy.guyNatSpeed);
    }

    @Override
    public String name() {
        return "increaseSpeed";
    }
}
