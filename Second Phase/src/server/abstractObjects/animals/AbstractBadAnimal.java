package server.abstractObjects.animals;

import server.Game;

import java.util.Random;

public abstract class AbstractBadAnimal extends AbstractAnimal {

    private int lev;

    public AbstractBadAnimal(int lev, int speed, int ii, int jj, Game game) {
        super(speed, ii, jj, game);
        this.lev = lev;
        isRunning = false;
    }

    //up 0
    //right 1
    //down 2
    //left 3

    public void move() {
        if(!isRunning) {
            currentDir = findDirection();
            if(currentDir == -1)
                return;
            isRunning = true;
        }
        isRunning = animalAnimations[currentDir].animate();

    }

    protected int goAccident() {
        boolean flag = false, mark = false;
        if(this instanceof AbstractDragon)
            flag = true;
        for(int i = 0; i < 4; i ++)
            if(isOkCell(ii + animalAnimations[i].getDy(), jj + animalAnimations[i].getDx(), flag))
                mark = true;
        if(! mark)
            return -1;
        int dir = (Math.abs(new Random().nextInt())) % 4;
        while(! isOkCell(ii + animalAnimations[dir].getDy(), jj + animalAnimations[dir].getDx(), flag)) {
            dir = (Math.abs(new Random().nextInt())) % 4;
        }
        return dir;
    }

    public abstract int findDirection();

    public int getLev() {
        return lev;
    }
}
