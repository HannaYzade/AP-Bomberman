package server.abstractObjects;

import java.util.Date;

public class AbstractExplosionAnimation {

    private AbstractCell owner;
    private Date nextAnimation;
    private int stepDelay;
    private int ind;

    public static final int frameNumber = 22;

    public AbstractExplosionAnimation(AbstractCell owner) {
        this.owner = owner;
        nextAnimation = new Date();
        stepDelay = 50;
        ind = 0;
    }

    public boolean animate() {
        if(ind == frameNumber)
            return false;
        if(nextAnimation.before(new Date())) {
            ind ++;
            nextAnimation.setTime(stepDelay + nextAnimation.getTime());
        }
        return true;
    }

    public int getInd() {
        return ind;
    }
}
