import java.awt.*;

public class BoombController extends PowerUp{
    public BoombController(int ii, int jj)
    {
        super("/img/bombController.png", ii, jj);
    }

    @Override
    public void power() {
        MapPanel.getMap().getGuy().setBoombRemote(true);
    }

    public static void explosion() {
        MapPanel map = MapPanel.getMap();
        if(map.getBoombs().size() == 0)
            return;
        Boomb boomb = map.getBoombs().get(0);
        map.getWorkingBoomb().add(boomb);
        map.getBoombs().remove(0);
        Cell c = map.getCells()[boomb.getIi()][boomb.getJj()];
        synchronized (Color.green) {
            map.getBoombs().remove(boomb);
            c.setBoomb(null);
        }
    }
}
