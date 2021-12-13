package server.abstractObjects.animals;

import client.frames.Map;
import server.abstractObjects.AbstractCell;
import server.Game;

import java.awt.*;
import java.util.Date;

public class AbstractAnimalAnimation {


    private int dx, dy;
    private Game game;
    private AbstractAnimal owner;
    private Date nextAnimation;
    private int stepDelay;
    private int step;
    private int ind;

    public AbstractAnimalAnimation(int dx, int dy, AbstractAnimal owner, Game game) {
        this.dx = dx;
        this.dy = dy;
        this.owner = owner;
        nextAnimation = new Date();
        stepDelay = 240 / owner.getSpeed();
        step = 10;
        ind = 0;
        this.game = game;
    }

    public boolean animate() {
        if (ind == 9) {
            if (owner instanceof AbstractGuy && owner.getIi() == game.getDoorI() && owner.getJj() == game.getDoorJ() && game.isDoorOpen()) {
                game.initialize(game.getLevel() + 1);
                Map.getMap().revalidate();
                Map.getMap().repaint();
            }
            ind = 0;
            return false;
        }
        if (nextAnimation.before(new Date())) {
            if (ind == 0)
                ind ++;
            if (ind != 0) {
                owner.setX(owner.getX() + step * dx);
                owner.setY(owner.getY() + step * dy);
            }
            nextAnimation.setTime(stepDelay + new Date().getTime());
            ind++;
            if (ind == 4) {
                AbstractCell cl = game.getCells()[owner.getIi()][owner.getJj()];
                if (owner instanceof AbstractBadAnimal)
                    cl.getBadanimals().remove(owner);
                else
                    cl.getGuy().remove(owner);
                owner.setIi(owner.getIi() + dy);
                owner.setJj(owner.getJj() + dx);
                cl = game.getCells()[owner.getIi()][owner.getJj()];
                if (owner instanceof AbstractBadAnimal) {
                    cl.getBadanimals().add((AbstractBadAnimal) owner);
                    for(int i = 0; i < cl.getGuy().size(); i ++) {
                        cl.getGuy().get(i).setDie(true);
                    }
                    cl.getGuy().clear();
                } else {
                    cl.getGuy().add((AbstractGuy) owner);
                    if(cl.getPowerUp() != null) {
                        cl.getPowerUp().power((AbstractGuy) owner);
                        synchronized (Color.yellow) {
                            game.getPowerUps().remove(cl.getPowerUp());
                        }
                        cl.setPowerUp(null);
                    }
                    if (cl.getBadanimals().size() > 0) {
                        ((AbstractGuy) owner).setDie(true);
                        cl.getGuy().remove(owner);
                    }
                }

            }
        }
        return true;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public void setInd(int ind) {
        this.ind = ind;
    }

    public int getInd() {
        return ind;
    }
}