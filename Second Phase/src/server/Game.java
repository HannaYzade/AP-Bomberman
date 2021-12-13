package server;

import server.abstractObjects.animals.*;
import server.abstractObjects.*;
import server.abstractObjects.powerUps.*;

import java.awt.*;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Random;

public class Game {

    private int playersLim;
    private ArrayList<AbstractCell> grasses;
    private int level;
    private int time;
    private int doorI, doorJ;
    private boolean isDoorOpen;
    private boolean isDoorVisible;
    private int height, width;
    private AbstractCell[][] cells;
    private ArrayList<AbstractGuy> guys;
    private ArrayList<AbstractBadAnimal> badAnimals;
    private ArrayList<AbstractBoomb> boombs;
    private ArrayList<AbstractPowerUp> powerUps;
    private ArrayList<AbstractBoomb> workingBoombs;
    private ArrayList<AbstractCell> explosioningCells;
    private Thread animationThread;
    private Thread timeThread;
    private ArrayList<Console> consoles;
    private ArrayList<Class> enemies;

    public Game(int width, int height, int players) {
        playersLim = players;
        this.height = height;
        this.width = width;
        guys = new ArrayList<>();
        enemies = new ArrayList<>();
        consoles = new ArrayList<>();
        timeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (Color.cyan) {
                        time++;
                        if (time >= 300) {
                            for (int i = 0; i < guys.size(); i++) {
                                guys.get(i).setPoint(guys.get(i).getPoint() - 1);
                            }
                        }
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        animationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (Color.orange) {
                        if (badAnimals.size() == 0 && isDoorVisible) {
                            isDoorOpen = true;
                            isDoorVisible = false;
                        }
                        boolean flag = false;
                        for (int i = 0; i < guys.size(); i++) {
                            if (!guys.get(i).isDie())
                                flag = true;
                        }
                        if (!flag) {
                            Server.getServer().getGames().remove(this);
                        }
                        for (int i = 0; i < workingBoombs.size(); i++) {
                            AbstractBoomb boomb = workingBoombs.get(i);
                            AbstractGuy guy = boomb.getOwner();
                            int dis = boomb.getExplosionDistance();
                            if (dis > guy.getBoombRadius()) {
                                workingBoombs.remove(boomb);
                                i--;
                                continue;
                            }
                            int[][] arr = {
                                    {0, dis},
                                    {0, -dis},
                                    {-dis, 0},
                                    {dis, 0}
                            };
                            int[][] adj = {
                                    {0, dis - 1},
                                    {0, -dis + 1},
                                    {-dis + 1, 0},
                                    {dis - 1, 0}
                            };
                            if (dis == 0) {
                                for (int k = 0; k < 4; k++)
                                    for (int l = 0; l < 2; l++)
                                        adj[k][l] = 0;
                            }
                            AbstractCell c = null, ad = null;
                            boolean flag2 = false;
                            for (int k = 0; k < 4; k++) {
                                int ix = arr[k][0], iy = arr[k][1];
                                int ixadj = adj[k][0], iyadj = adj[k][1];
                                if (iy + boomb.getIi() >= 0 && ix + boomb.getJj() >= 0 && iy + boomb.getIi() < height && ix + boomb.getJj() < width) {
                                    c = cells[iy + boomb.getIi()][ix + boomb.getJj()];
                                    ad = cells[iyadj + boomb.getIi()][ixadj + boomb.getJj()];
                                    if (c.getExplosion() == null && c.getKind() != CellKind.ROCKWALL) {
                                        if (dis == 0 || (ad.getExplosion() != null && ad.getExplosion().getInd() > 3)) {
                                            flag2 = true;
                                            explosioningCells.add(c);
                                            c.setExplosion(new AbstractExplosionAnimation(c));
                                            for (AbstractGuy guuy : c.getGuy()) {
                                                guuy.setDie(true);
                                            }
                                            c.getGuy().clear();
                                            if (c.getKind() == CellKind.WALL) {
                                                guy.setPoint(guy.getPoint() + 10);
                                                if (iy + boomb.getIi() == doorI && ix + boomb.getJj() == doorJ)
                                                    isDoorVisible = true;
                                                if (c.getPowerUp() != null) {
                                                    synchronized (Color.yellow) {
                                                        powerUps.add(c.getPowerUp());
                                                    }
                                                }
                                                c.setKind(CellKind.GRASS);
                                            } else {
                                                for (AbstractBadAnimal animal : c.getBadanimals()) {
                                                    guy.setPoint(guy.getPoint() + 20 * animal.getLev());
                                                    badAnimals.remove(animal);
                                                }
                                                c.getBadanimals().clear();
                                                if (c.getPowerUp() != null) {
                                                    synchronized (Color.yellow) {
                                                        powerUps.remove(c.getPowerUp());
                                                    }
                                                    c.setPowerUp(null);
                                                }
                                                if (c.getBoomb() != null) {
                                                    AbstractBoomb b = c.getBoomb();
                                                    synchronized (Color.green) {
                                                        boombs.remove(b);
                                                        b.getOwner().getBoombs().remove(b);
                                                        c.setBoomb(null);
                                                    }
                                                    workingBoombs.add(b);
                                                    boomb.setExplosionDistance(1);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (flag2)
                                boomb.setExplosionDistance(dis + 1);
                        }
                        for (AbstractBadAnimal badAnimal : badAnimals) {
                            badAnimal.move();
                        }
                        for (int i = 0; i < boombs.size(); i++) {
                            AbstractBoomb boomb = boombs.get(i);
                            flag = boomb.getBoombAnimation().animate();
                            if (!flag) {
                                workingBoombs.add(boomb);
                                AbstractCell c = cells[boomb.getIi()][boomb.getJj()];
                                synchronized (Color.green) {
                                    boombs.remove(boomb);
                                    boomb.getOwner().getBoombs().remove(boomb);
                                    c.setBoomb(null);
                                    i--;
                                }
                            }
                        }
                        for (int i = 0; i < explosioningCells.size(); i++) {
                            AbstractCell cell = explosioningCells.get(i);
                            flag = cell.getExplosion().animate();
                            if (!flag) {
                                explosioningCells.remove(cell);
                                cell.setExplosion(null);
                                i--;
                            }
                        }
                        for(int i = 0; i < consoles.size(); i ++)
                            consoles.get(i).sendGame();
                    }
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        initialize(1);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void initialize(int lev) {
        synchronized (Color.cyan) {
            synchronized (Color.orange) {
                synchronized (Color.red) {
                    cells = new AbstractCell[height][width];
                    enemies = Server.getServer().getEnemies();
                    boombs = new ArrayList<>();
                    powerUps = new ArrayList<>();
                    badAnimals = new ArrayList<>();
                    workingBoombs = new ArrayList<>();
                    explosioningCells = new ArrayList<>();
                    grasses = new ArrayList<>();
                    isDoorOpen = false;
                    isDoorVisible = false;
                    this.level = lev;
                    time = 0;
                    fillCells();
                    for (int i = 0; i < guys.size(); i++) {
                        if (guys.get(i).isDie()) {
                            guys.get(i).initialize();
                        }
                        addGuy(guys.get(i));
                    }
                }
            }
        }
    }

    public void fillCells() {
        for (int i = 1; i < height; i += 2) {
            for (int j = 1; j < width; j += 2) {
                cells[i][j] = new AbstractCell(CellKind.ROCKWALL, i, j);
            }
        }

        do {
            doorI = (Math.abs(new Random().nextInt())) % height;
            doorJ = (Math.abs(new Random().nextInt())) % width;
        } while((doorI * doorJ) % 2 == 1);
        for(int i = 0; i < height; i ++) {
            for (int j = 0; j < width; j++) {
                if ((i * j) % 2 == 1)
                    continue;
                if(i == doorI && j == doorJ) {
                    cells[i][j] = new AbstractCell(CellKind.WALL, i, j);
                    continue;
                }
                int a = (new Random().nextInt()) % 6;
                AbstractCell cl = null;
                if (a == 0) {
                    cl = new AbstractCell(CellKind.WALL, i, j);
                    int b = (Math.abs(new Random().nextInt())) % 30;
                    if(b == 0) {
                        cl.setPowerUp(new AbstractDecreaseBoomb(i, j));
                    }
                    if(b == 1) {
                        cl.setPowerUp(new AbstractDecreasePoint(i, j));
                    }
                    if(b == 2) {
                        cl.setPowerUp(new AbstractDecreaseRadius(i, j));
                    }
                    if(b == 3) {
                        cl.setPowerUp(new AbstractDecreaseSpeed(i, j));
                    }
                    if(b == 4) {
                        cl.setPowerUp(new AbstractIncreaseBoomb(i, j));
                    }
                    if(b == 5) {
                        cl.setPowerUp(new AbstractIncreasePoint(i, j));
                    }
                    if(b == 6) {
                        cl.setPowerUp(new AbstractIncreaseRadius(i, j));
                    }
                    if(b == 7) {
                        cl.setPowerUp(new AbstractIncreaseSpeed(i, j));
                    }
                    if(b == 8) {
                        cl.setPowerUp(new AbstractBoombController(i, j, this));
                    }
                    if(b == 9) {
                        cl.setPowerUp(new AbstractMakeGhost(i, j));
                    }
                } else {
                    cl = new AbstractCell(CellKind.GRASS, i, j);
                    grasses.add(cl);
                    int b = (Math.abs(new Random().nextInt())) % (height * width);
                    int c = Math.min(height, width);
                    if(level > 4)
                        c += (level - 4) * c / 5;
                    if (b <= c) {
                        AbstractBadAnimal bad = null;
                        if(level > 3)
                            b %= 4;
                        else
                            b %= level;
                        //b %= 4;
                        int count = 0;
                        for(int l = 0; l < enemies.size(); l ++)
                        {
                            Class enemy = enemies.get(l);
                            try {
                                int field = enemy.getField("lev").getInt(null);
                                if(b == field - 1) {
                                    count ++;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        int d = Math.abs(new Random().nextInt()) % count;
                        for(int l = 0; l < enemies.size(); l ++)
                        {
                            Class enemy = enemies.get(l);
                            try {
                                int field = enemy.getField("lev").getInt(null);
                                if(b == field - 1) {
                                    if (d == 0) {
                                        Constructor cons = enemy.getConstructor(int.class, int.class, this.getClass());
                                        bad = (AbstractBadAnimal) cons.newInstance(i, j, this);
                                        cl.getBadanimals().add(bad);
                                        badAnimals.add(bad);
                                    }
                                    d --;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }
                cells[i][j] = cl;
            }
        }
    }

    public int getDoorI() {
        return doorI;
    }

    public int getDoorJ() {
        return doorJ;
    }

    public boolean isDoorOpen() {
        return isDoorOpen;
    }

    public AbstractCell[][] getCells() {
        return cells;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public ArrayList<AbstractGuy> getGuys() {
        return guys;
    }

    public ArrayList<AbstractBoomb> getBoombs() {
        return boombs;
    }

    public ArrayList<AbstractBoomb> getWorkingBoombs() {
        return workingBoombs;
    }

    public ArrayList<AbstractPowerUp> getPowerUps() {
        return powerUps;
    }

    public void boombing(AbstractGuy guy) {
        if(cells[guy.getIi()][guy.getJj()].getBoomb() == null && guy.getBoombs().size() < guy.getBoombLimit() && cells[guy.getIi()][guy.getJj()].getKind() == CellKind.GRASS) {
            AbstractBoomb b = new AbstractBoomb(guy, guy.getIi(), guy.getJj());
            cells[guy.getIi()][guy.getJj()].setBoomb(b);
            boombs.add(b);
            guy.getBoombs().add(b);
        }
    }

    @Override
    public String toString() {
        synchronized (Color.red) {
            String s = "";
            s += width + "\n" + height + "\n";
            s += doorI + "\n" + doorJ + "\n";
            s += isDoorVisible + "\n" + isDoorOpen + "\n";
            s += time + "\n" + level + "\n";

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    s += cells[i][j].toString();
                }
            }

            s += boombs.size() + "\n";
            for (int i = 0; i < boombs.size(); i++) {
                s += boombs.get(i).toString();
            }

            s += powerUps.size() + "\n";
            for (int i = 0; i < powerUps.size(); i++) {
                s += powerUps.get(i).toString();
            }

            s += guys.size() + "\n";
            for (int i = 0; i < guys.size(); i++) {
                s += guys.get(i).toString();
            }

            s += badAnimals.size() + "\n";
            for (int i = 0; i < badAnimals.size(); i++) {
                s += badAnimals.get(i).toString();
            }

            return s;
        }
    }

    public void addGuy(AbstractGuy guy) {
        int r = new Random().nextInt(grasses.size());
        AbstractCell c = grasses.get(r);
        c.getGuy().add(guy);
        guy.setIi(c.getIi());
        guy.setJj(c.getJj());
        guy.setX(c.getJj() * AbstractCell.width);
        guy.setY(c.getIi() * AbstractCell.height);
    }

    public Thread getAnimationThread() {
        return animationThread;
    }

    public Thread getTimeThread() {
        return timeThread;
    }

    public String getInf() {
        String s = width + " ";
        s += height + " ";
        s += level + " ";
        s += time + " ";
        s += guys.size() + " ";
        for(int i = 0; i < guys.size(); i ++) {
            if(guys.get(i).isDie())
                s += "Dead ";
            else
                s += String.valueOf(guys.get(i).getPoint()) + " ";
        }
        return s;
    }

    public void sendMess(String str, int id) {
        for(int i = 0; i < consoles.size(); i ++) {
            consoles.get(i).sendMessage(str, id);
        }
    }

    public ArrayList<Console> getConsoles() {
        return consoles;
    }
}
