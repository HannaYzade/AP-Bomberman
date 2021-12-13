import java.util.Random;

public abstract  class BadAnimal extends Animal {

    protected static int lev;

    public BadAnimal(int speed, int jj, int ii, String path, int gap) {
        super(speed, jj, ii, path, gap);
        isRunning = false;
    }

    //up 0
    //right 1
    //down 2
    //left 3

    public void move() {
        if(!isRunning) {
            currentDir = find_direction();
            if(currentDir == -1)
                return;
            isRunning = true;
        }
        isRunning = animalAnimations[currentDir].animate();
    }

    protected int goAccident() {
        boolean flag = false, mark = false;
        if(this instanceof  Dragon)
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

    public abstract int find_direction();

}
