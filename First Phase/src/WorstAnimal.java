public class WorstAnimal extends BadAnimal{

    private int accidentlyMove = -1;

    public WorstAnimal(int speed, int jj, int ii, String path, int gap) {
        super(speed, jj, ii, path, gap);
    }


    @Override
    public int find_direction() {
        int dir = -1;
        if(accidentlyMove >= 0) {
            dir = goAccident();
            accidentlyMove ++;
            if(accidentlyMove == 10)
                accidentlyMove = -1;
            return dir;
        }
        else {
            Guy guy = MapPanel.getMap().getGuy();
            int di = guy.getIi() - getIi(), dj = guy.getJj() - getJj();
            if(di != 0)
                di /= Math.abs(di);
            if(dj != 0)
                dj /= Math.abs(dj);
            AnimalAnimation anim = null;
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
            if(this instanceof  Dragon)
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
