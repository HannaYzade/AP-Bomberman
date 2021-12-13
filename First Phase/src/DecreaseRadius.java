public class DecreaseRadius extends PowerUp{
    public DecreaseRadius(int ii, int jj) {
        super("/img/decreaseRadius.png", ii, jj);
    }

    @Override
    public void power() {
        MapPanel map = MapPanel.getMap();
        if(map.getBoombRadius() > 1)
            map.setBoombRadius(map.getBoombRadius() - 1);
    }
}
