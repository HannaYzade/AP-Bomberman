public class IncreasePoint extends PowerUp{

    private final int point = 100;

    public IncreasePoint(int ii, int jj)
    {
        super("/img/increasePoints.png", ii, jj);
    }

    @Override
    public void power() {
        Guy guy = MapPanel.getMap().getGuy();
        guy.setPoint(guy.getPoint() + point);
    }
}
