package server.abstractObjects.animals;

import server.Game;

public class AbstractDragon extends AbstractWorstAnimal{

    public static int lev = 4;

    public AbstractDragon(int ii, int jj, Game game) {
        super(lev, AbstractGuy.guyNatSpeed, ii, jj, game);
    }

    @Override
    public String getName() {
        return "Dragon";
    }
}
