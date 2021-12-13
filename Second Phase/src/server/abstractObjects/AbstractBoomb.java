package server.abstractObjects;

import server.abstractObjects.animals.AbstractGuy;

public class AbstractBoomb {

    private AbstractGuy owner;
    private int ii, jj;
    private AbstractBoombAnimation boombAnimation;
    private int explosionDistance;


    public AbstractBoomb(AbstractGuy owner, int ii, int jj) {
        this.owner = owner;
        this.ii = ii;
        this.jj = jj;
        boombAnimation = new AbstractBoombAnimation(this);
        explosionDistance = 0;
    }


    public int getIi() {
        return ii;
    }

    public int getJj() {
        return jj;
    }

    public int getExplosionDistance() {
        return explosionDistance;
    }

    public void setExplosionDistance(int explosionDistance) {
        this.explosionDistance = explosionDistance;
    }

    public AbstractGuy getOwner() {
        return owner;
    }

    public AbstractBoombAnimation getBoombAnimation() {
        return boombAnimation;
    }

    @Override
    public String toString() {
        String s = "";
        s += (jj * AbstractCell.width) + " " + (ii * AbstractCell.height) + " ";
        s += boombAnimation.getInd() + "\n";
        return  s;
    }
}
