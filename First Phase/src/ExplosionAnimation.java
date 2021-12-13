import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class ExplosionAnimation implements Animatable {

    private Cell owner;
    private Date nextAnimation;
    private int stepDelay;
    private ArrayList<BufferedImage> images;
    public static final int frameNumber = 22;

    public ExplosionAnimation(Cell owner) {
        construct(owner);
    }

    public ExplosionAnimation(int frameNum, Cell owner) {
        construct(owner);
        for(int i = 0; i < frameNum; i ++) {
            images.remove(0);
        }
    }

    private void construct(Cell owner) {
        this.owner = owner;
        nextAnimation = new Date();
        stepDelay = 50;
        images = new ArrayList<>();
        String path = "/img/explosion/";
        BufferedImage img = null;
        for(int i = 0; i < frameNumber; i ++) {
            try {
                img = ImageIO.read(getClass().getResource(path + i + ".gif"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            images.add(img);
        }
    }

    @Override
    public boolean animate() {
        if(images.size() == 0)
            return false;
        if(nextAnimation.before(new Date())) {
            owner.setExplosionImg(images.get(0));
            images.remove(0);
            nextAnimation.setTime(stepDelay + nextAnimation.getTime());
        }
        return true;
    }

    public ArrayList<BufferedImage> getImages() {
        return images;
    }
}
