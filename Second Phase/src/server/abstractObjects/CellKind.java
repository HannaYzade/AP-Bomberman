package server.abstractObjects;

public enum CellKind {
    GRASS("Grass"),
    WALL("Wall"),
    ROCKWALL("RockWall");

    private String name;

    CellKind(String s) {
        name = s;
    }

    @Override
    public String toString() {
        return name;
    }
}
