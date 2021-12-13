package server.abstractObjects.powerUps;

import server.abstractObjects.AbstractBoomb;
import server.abstractObjects.animals.AbstractGuy;
import server.Game;

import java.awt.*;

public class AbstractBoombController extends AbstractPowerUp{

    private Game game;

    public AbstractBoombController(int ii, int jj, Game game) {
        super(ii, jj);
        this.game = game;
    }

    @Override
    public void power(AbstractGuy guy) {
        guy.setBoombRemote(this);
    }

    @Override
    public String name() {
        return "boombController";
    }

    public void explosion(AbstractGuy guy) {
        if(guy.getBoombs().size() == 0)
            return;
        AbstractBoomb boomb = guy.getBoombs().get(0);
        guy.getBoombs().remove(0);
        game.getWorkingBoombs().add(boomb);
        synchronized (Color.green) {
            game.getCells()[boomb.getIi()][boomb.getJj()].setBoomb(null);
            game.getBoombs().remove(boomb);
        }
    }

}
