package server.abstractObjects.powerUps;

import server.abstractObjects.animals.AbstractGuy;

public class AbstractDecreasePoint extends AbstractPowerUp {

    public AbstractDecreasePoint(int ii, int jj) {
        super(ii, jj);
    }

    @Override
    public void power(AbstractGuy guy) {
        guy.setPoint(guy.getPoint() - 100);
    }

    @Override
    public String name() {
        return "decreasePoint";
    }
}
