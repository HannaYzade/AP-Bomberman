package server.abstractObjects.animals;

import server.Console;
import server.abstractObjects.AbstractBoomb;
import server.Game;
import server.abstractObjects.powerUps.AbstractBoombController;

import java.util.ArrayList;


public class AbstractGuy  extends AbstractAnimal {

    private int point;
    private boolean isGhost;
    private boolean isDie;
    private AbstractBoombController boombRemote;
    private int guyId;
    public static final int guyNatSpeed = 2;
    private int boombRadius = 1;
    private int boombLimit = 1;
    private ArrayList<AbstractBoomb> boombs;
    private Thread runGuy;
    private Console console;

    public AbstractGuy(int speed, int ii, int jj, int id, Game game, Console console) {
        super(speed, ii, jj, game);
        this.console = console;
        boombs = new ArrayList<>();
        initialize();
        guyId = id;
        runGuy = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (isRunning) {
                        isRunning = animalAnimations[currentDir].animate();
                        console.sendGame();
                    }
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void initialize() {
        isGhost = false;
        isRunning = false;
        isDie = false;
        point = 0;
        boombRemote = null;
    }

    public void move(int direction) {
        int disi= ii + animalAnimations[direction].getDy(), disj = jj + animalAnimations[direction].getDx();
        if(!isRunning && isOkCell(disi, disj, isGhost) ) {
            currentDir = direction;
            isRunning = true;
            if(!runGuy.isAlive())
                runGuy.start();
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
        if(point < 0) {
            isDie = true;
        }
    }

    public  void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setBoombRemote(AbstractBoombController boombRemote) {
        this.boombRemote = boombRemote;
    }

    public AbstractBoombController getBoombRemote() {
        return boombRemote;
    }

    public void setGhost(boolean ghost) {
        isGhost = ghost;
    }

    public boolean isGhost() {
        return isGhost;
    }

    public void setDie(boolean die) {
        isDie = die;
    }

    public boolean isDie() {
        return isDie;
    }

    public int getBoombRadius() {
        return boombRadius;
    }

    public void setBoombRadius(int boombRadius) {
        this.boombRadius = boombRadius;
    }

    public int getBoombLimit() {
        return boombLimit;
    }

    public void setBoombLimit(int boombLimit) {
        this.boombLimit = boombLimit;
    }

    public ArrayList<AbstractBoomb> getBoombs() {
        return boombs;
    }

    public int getGuyId() {
        return guyId;
    }

    @Override
    public String getName() {
        return "Guy";
    }

    public Console getConsole() {
        return console;
    }
}
