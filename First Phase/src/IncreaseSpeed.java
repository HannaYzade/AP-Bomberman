public class IncreaseSpeed extends PowerUp{

    public IncreaseSpeed(int ii, int jj)
    {
        super("/img/increaseSpeed.png", ii, jj);
    }

    @Override
    public void power() {
        Guy guy = MapPanel.getMap().getGuy();
        guy.setSpeed(guy.getSpeed() + MapPanel.getMap().getGuyNatSpeed());
    }
}
