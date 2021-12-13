class IncreaseRadius extends PowerUp{
    public IncreaseRadius(int ii, int jj) {
        super("/img/increaseRadius.png", ii, jj);
    }


    @Override
    public void power() {
        MapPanel map = MapPanel.getMap();
        map.setBoombRadius(map.getBoombRadius() + 1);
    }
}
