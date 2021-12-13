package server.abstractObjects.powerUps;

import server.abstractObjects.animals.AbstractGuy;

public class AbstractMakeGhost extends AbstractPowerUp {


    public AbstractMakeGhost(int ii, int jj) {
        super(ii, jj);
    }

    @Override
    public void power(AbstractGuy guy) {
        guy.setGhost(true);
    }

    @Override
    public String name() {
        return "makeGhost";
    }
}
