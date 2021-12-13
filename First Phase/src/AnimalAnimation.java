import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.Random;

public class AnimalAnimation implements Animatable {

    private int dx, dy, dir;
    private BufferedImage[] images;
    private Animal owner;
    private Date nextAnimation;
    private int stepDelay;
    private int step;
    private int ind;
    private int[] inds = {1, 2, 1, 0, 1, 2, 1, 0, 1};

    public AnimalAnimation(int dx, int dy, BufferedImage[] images, Animal owner) {
        this.dx = dx;
        this.dy = dy;
        this.images = images;
        this.owner = owner;
        nextAnimation = new Date();
        stepDelay = 240 / owner.getSpeed();
        step = 10;
        ind = 0;
    }

    public boolean animate() {
        if(ind == 9) {
            if(owner instanceof  Guy && owner.getIi() == MapPanel.getMap().getDoorI() && owner.getJj() == MapPanel.getMap().getDoorJ() && MapPanel.getMap().isDoorOpen()) {
                MapPanel map = MapPanel.getMap();
                map.initialize(map.getW(), map.getH(), false);
                map.repaint();
                MainFrame.getFrame().getInformationPanel().setLevelLabel(map.getLevel());
                MainFrame.getFrame().getInformationPanel().setTimeLabel(0);
            }
            ind = 0;
            return false;
        }
        if(nextAnimation.before(new Date())) {
            if(ind == 0 && owner.getNowImage() == images[inds[ind]])
                ind ++;
            if(ind != 0) {
                owner.setX(owner.getX() + step * dx);
                owner.setY(owner.getY() + step * dy);
            }
            owner.setNowImage(images[inds[ind]]);
            nextAnimation.setTime(stepDelay + new Date().getTime());
            ind ++;
            if(ind == 4) {
                Cell cl = (MapPanel.getMap().getCells()
                )[owner.getIi()][owner.getJj()];
                if(owner  instanceof  BadAnimal)
                    cl.getBadanimals().remove(owner);
                else
                    cl.setGuy(null);
                owner.setIi(owner.getIi() + dy);
                owner.setJj(owner.getJj() + dx);
                cl = (MapPanel.getMap().getCells())[owner.getIi()][owner.getJj()];
                if(owner  instanceof  BadAnimal) {
                    cl.getBadanimals().add((BadAnimal) owner);
                }
                else {
                    cl.setGuy((Guy) owner);
                    if(cl.getPowerUp() != null) {
                        cl.getPowerUp().power();
                        synchronized (Color.yellow) {
                            MapPanel.getMap().getPowerUps().remove(cl.getPowerUp());
                        }
                        cl.setPowerUp(null);
                    }
                }
                if(cl.getGuy() != null && cl.getBadanimals().size() > 0) {
                    MainFrame.getFrame().setGameOver(true);
                    MainFrame.getFrame().repaint();
                }
            }
        }
        return  true;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public BufferedImage[] getImages() {
        return images;
    }

    public void setInd(int ind) {
        this.ind = ind;
    }

    public int getInd() {
        return ind;
    }
}
