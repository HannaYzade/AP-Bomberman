public class DecreasePoint extends PowerUp{

    private final int point = 100;

    public DecreasePoint(int ii, int jj)
    {
        super("/img/decreasePoints.png", ii, jj);
    }

    @Override
    public void power() {
        Guy guy = MapPanel.getMap().getGuy();
        guy.setPoint(guy.getPoint() - point);
    }
}
