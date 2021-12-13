public class MakeGhost extends PowerUp {
    public MakeGhost(int ii, int jj) {
        super("/img/ghost.png", ii, jj);
    }

    @Override
    public void power() {
        MapPanel.getMap().getGuy().setGhost(true);
    }
}
