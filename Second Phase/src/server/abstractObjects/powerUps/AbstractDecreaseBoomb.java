package server.abstractObjects.powerUps;

import server.abstractObjects.animals.AbstractGuy;

public class AbstractDecreaseBoomb extends AbstractPowerUp {

    public AbstractDecreaseBoomb(int ii, int jj) {
        super(ii, jj);
    }

    @Override
    public void power(AbstractGuy guy) {
        if(guy.getBoombLimit() > 1)
            guy.setBoombLimit(guy.getBoombLimit() - 1);
    }

    @Override
    public String name() {
        return "decreaseBoomb";
    }
}
