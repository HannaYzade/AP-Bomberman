import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class BoombAnimation implements Animatable {

    private Boomb owner;
    private Date nextAnimation;
    private int stepDelay;
    private ArrayList<BufferedImage> images;
    public static final int frameNumber = 63;

    public BoombAnimation(Boomb owner) {
        construct(owner);

    }

    public BoombAnimation(Boomb owner, int frameInd) {
        construct(owner);
        for (int i = 0; i < frameInd; i ++)
            images.remove(0);
    }

    private void construct(Boomb owner) {
        this.owner = owner;
        nextAnimation = new Date();
        stepDelay = 80;
        images = new ArrayList<>();
        String path = "/img/volcanoes/";
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
            owner.setNowImage(images.get(0));
            images.remove(0);
            nextAnimation.setTime(stepDelay + nextAnimation.getTime());
        }
        return true;
    }

    public ArrayList<BufferedImage> getImages() {
        return images;
    }
}
