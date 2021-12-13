public enum CellKind {
    GRASS("/img/grass.png"),
    WALL("/img/wall.jpg"),
    ROCKWALL("/img/rockWall.jpg");

    private String path, name;

    CellKind(String s) {
        path = s;
        if(path.equals("/img/grass.png")) {
            name = "Grass";
        }
        if(path.equals("/img/wall.jpg")) {
            name = "Wall";
        }
        if(path.equals("/img/rockWall.jpg")) {
            name = "RockWall";
        }
    }

    public String getPath() {
        return path;
    }


    @Override
    public String toString() {
        return name;
    }
}
