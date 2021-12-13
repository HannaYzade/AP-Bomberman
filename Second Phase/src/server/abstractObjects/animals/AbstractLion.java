package server.abstractObjects.animals;

import server.Game;

public class AbstractLion extends  AbstractWorstAnimal {

    public static int lev = 3;

    public AbstractLion(int ii, int jj, Game game) {
        super(3, AbstractGuy.guyNatSpeed, ii, jj, game);
    }

    @Override
    public String getName() {
        return "Lion";
    }
}
