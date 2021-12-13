package server.abstractObjects;

import java.util.Date;

public class AbstractBoombAnimation {

    private AbstractBoomb owner;
    private Date nextAnimation;
    private int stepDelay;
    private int ind;
    public static final int frameNumber = 63;

    public AbstractBoombAnimation(AbstractBoomb owner) {
        this.owner = owner;
        nextAnimation = new Date();
        stepDelay = 80;
        ind = 0;
    }

    public boolean animate() {
        if (ind == frameNumber)
            return false;
        if (nextAnimation.before(new Date())) {
            ind ++;
            nextAnimation.setTime(stepDelay + nextAnimation.getTime());
        }
        return true;
    }

    public int getInd() {
        return ind;
    }
}
