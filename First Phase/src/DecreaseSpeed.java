public class DecreaseSpeed extends  PowerUp{
    public DecreaseSpeed(int ii, int jj)
    {
        super("/img/decreaseSpeed.png", ii, jj);
    }

    @Override
    public void power() {
        Guy guy = MapPanel.getMap().getGuy();
        if(guy.getSpeed() > MapPanel.getMap().getGuyNatSpeed())
            guy.setSpeed(guy.getSpeed() - MapPanel.getMap().getGuyNatSpeed());
    }
}
