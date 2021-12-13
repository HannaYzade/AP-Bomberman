public class DecreaseBoomb extends PowerUp{
    public DecreaseBoomb(int ii, int jj) {
        super("/img/decreaseBombs.png", ii, jj);
    }


    @Override
    public void power() {
        MapPanel map = MapPanel.getMap();
        if(map.getBoombLimit() > 1)
            map.setBoombLimit(map.getBoombLimit() - 1);
    }
}
