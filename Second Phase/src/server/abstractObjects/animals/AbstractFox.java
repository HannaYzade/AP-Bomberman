package server.abstractObjects.animals;

import server.Game;

public class AbstractFox extends AbstractBadAnimal {

    public static int lev = 1;

    public AbstractFox(int ii, int jj, Game game) {
        super(1, AbstractGuy.guyNatSpeed / 2, ii, jj, game);
    }

    @Override
    public String getName() {
        return "Fox";
    }

    @Override
    public int findDirection() {
        return goAccident();
    }
}