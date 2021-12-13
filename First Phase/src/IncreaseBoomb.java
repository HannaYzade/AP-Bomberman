class IncreaseBoomb extends PowerUp{
    public IncreaseBoomb(int ii, int jj) {
        super("/img/increaseBombs.png", ii, jj);
    }


    @Override
    public void power() {
        MapPanel map = MapPanel.getMap();
        map.setBoombLimit(map.getBoombLimit() + 1);
    }
}
