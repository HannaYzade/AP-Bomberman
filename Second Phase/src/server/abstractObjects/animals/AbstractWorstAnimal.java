package server.abstractObjects.animals;

import server.Game;

import java.util.Random;

public abstract class AbstractWorstAnimal extends AbstractBadAnimal {

    private int accidentlyMove = -1;

    public AbstractWorstAnimal(int lev, int speed, int ii, int jj, Game game) {
        super(lev, speed, ii, jj, game);
    }

    @Override
    public int findDirection() {
        int dir = -1;
        if(accidentlyMove >= 0) {
            dir = goAccident();
            accidentlyMove ++;
            if(accidentlyMove == 10)
                accidentlyMove = -1;
            return dir;
        }
        else {
            int a = new Random().nextInt(game.getGuys().size());
            AbstractGuy guy = game.getGuys().get(a);
            int di = guy.getIi() - getIi(), dj = guy.getJj() - getJj();
            if(di != 0)
                di /= Math.abs(di);
            if(dj != 0)
                dj /= Math.abs(dj);
            AbstractAnimalAnimation anim = null;
            for(int i = 0; i < 4; i ++) {
                anim = animalAnimations[i];
                if(anim.getDx() == dj && anim.getDy() == di) {
                    dir = i;
                }
            }
            if(dir == -1) {
                for(int i = 0; i < 4; i ++) {
                    anim = animalAnimations[i];
                    if(anim.getDx() == dj || anim.getDy() == di) {
                        dir = i;
                    }
                }
            }
            boolean flag = false;
            if(this instanceof AbstractDragon)
                flag = true;
            if(isOkCell(ii + animalAnimations[dir].getDy(), jj + animalAnimations[dir].getDx(), flag)) {
                return dir;
            }
            else {
                accidentlyMove = 1;
                return goAccident();

            }
        }
    }
}
