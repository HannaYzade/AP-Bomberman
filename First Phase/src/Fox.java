import java.util.Random;

public class Fox extends BadAnimal{
    public Fox(int speed, int jj, int ii) {
        super(speed, jj, ii, "/img/fox/", 12);
        lev = 1;
    }

    @Override
    public int find_direction() {
        return goAccident();
    }
}
