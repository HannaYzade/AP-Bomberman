package server.abstractObjects.powerUps;

import server.abstractObjects.animals.AbstractGuy;

public class AbstractDecreaseSpeed extends  AbstractPowerUp{
    public AbstractDecreaseSpeed(int ii, int jj) {
        super(ii, jj);
    }

    @Override
    public void power(AbstractGuy guy) {
        if(guy.getSpeed() > AbstractGuy.guyNatSpeed) {
            guy.setSpeed(guy.getSpeed() - AbstractGuy.guyNatSpeed);
        }
    }

    @Override
    public String name() {
        return "decreaseSpeed";
    }

}
