package server.abstractObjects.animals;

import server.Game;

public class AbstractBear extends AbstractWorstAnimal {

    public static int lev = 2;

    public AbstractBear(int ii, int jj, Game game) {
        super(lev, AbstractGuy.guyNatSpeed / 2, ii, jj, game);
    }

    @Override
    public String getName() {
        return "Bear";
    }
}
