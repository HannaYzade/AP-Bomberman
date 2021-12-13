import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.sql.*;

public class MapPanel extends JPanel implements KeyListener{
    private static MapPanel panel = null;
    private final int guyNatSpeed = 2;
    private int boombRadius = 1;
    private int boombLimit = 1;
    private Cell[][] cells = null;
    private Guy guy;
    private int doorI, doorJ;
    private boolean isDoorVisible;
    private boolean isDoorOpen;
    private int h;
    private int w;
    private int level;
    private ArrayList<PowerUp> powerUps;
    private ArrayList<BadAnimal> badAnimals;
    private ArrayList<Boomb> boombs;
    private ArrayList<Cell> explosioningCells;
    private ArrayList<Boomb> workingBoomb;
    private Thread animationThread;
    private BufferedImage closeDoor;
    private BufferedImage openDoor;
    private String name;


    private MapPanel(int w, int h) {
        super();
        level = 0;
        name = null;
        addKeyListener(this);
        setFocusable(true);
        requestFocus();
        guy = new Guy(guyNatSpeed, 0, 0);
        animationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (Color.gray) {
                        synchronized (CellKind.GRASS) {
                            if (badAnimals.size() == 0 && isDoorVisible) {
                                isDoorOpen = true;
                                isDoorVisible = false;
                            }
                            for (int i = 0; i < workingBoomb.size(); i++) {
                                Boomb boomb = workingBoomb.get(i);
                                int dis = boomb.getExplosionDistance();
                                if (dis > boombRadius) {
                                    workingBoomb.remove(boomb);
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
                                Cell c = null, ad = null;
				boolean flag2 = false;
                                for (int k = 0; k < 4; k++) {
                                    int ix = arr[k][0], iy = arr[k][1];
                                    int ixadj = adj[k][0], iyadj = adj[k][1];
                                    if (iy + boomb.getIi() >= 0 && ix + boomb.getJj() >= 0 && iy + boomb.getIi() < h && ix + boomb.getJj() < w) {
                                        c = cells[iy + boomb.getIi()][ix + boomb.getJj()];
                                        ad = cells[iyadj + boomb.getIi()][ixadj + boomb.getJj()];
                                        if (!c.isExplosion() && c.getKind() != CellKind.ROCKWALL) {
                                            if (dis == 0 || ad.getExplosion() != null) {
						flag2 = true;
                                                synchronized (Color.blue) {
                                                    explosioningCells.add(c);
                                                }
                                                c.setIsExplosion(true);
                                                c.setExplosion(new ExplosionAnimation(c));
                                                if (c.getGuy() != null) {
                                                    MainFrame.getFrame().setGameOver(true);
                                                    MainFrame.getFrame().repaint();
                                                }
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
                                                    for (BadAnimal animal : c.getBadanimals()) {
                                                        synchronized (Color.red) {
                                                            guy.setPoint(guy.getPoint() + 20 * animal.lev);
                                                            badAnimals.remove(animal);
                                                        }
                                                    }
                                                    c.getBadanimals().clear();
                                                    if (c.getPowerUp() != null) {
                                                        synchronized (Color.yellow) {
                                                            powerUps.remove(c.getPowerUp());
                                                        }
                                                        c.setPowerUp(null);
                                                    }
                                                    if (c.getBoomb() != null) {
                                                        Boomb b = c.getBoomb();
                                                        synchronized (Color.green) {
                                                            boombs.remove(b);
                                                            c.setBoomb(null);
                                                        }
                                                        workingBoomb.add(b);
                                                        boomb.setExplosionDistance(1);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
				if(flag2)
                                	boomb.setExplosionDistance(dis + 1);
                            }
                            for (BadAnimal badAnimal : badAnimals) {
                                badAnimal.move();
                            }
                            for (int i = 0; i < boombs.size(); i++) {
                                Boomb boomb = boombs.get(i);
                                boolean flag = boomb.getAnimatable().animate();
                                if (!flag) {
                                    workingBoomb.add(boomb);
                                    Cell c = cells[boomb.getIi()][boomb.getJj()];
                                    synchronized (Color.green) {
                                        boombs.remove(boomb);
                                        c.setBoomb(null);
                                        i--;
                                    }
                                }
                            }
                            for (int i = 0; i < explosioningCells.size(); i++) {
                                Cell cell = explosioningCells.get(i);
                                boolean flag = cell.getExplosion().animate();
                                if (!flag) {
                                    synchronized (Color.blue) {
                                        explosioningCells.remove(cell);
                                        cell.setExplosion(null);
                                        cell.setIsExplosion(false);
                                        i--;
                                    }
                                }
                            }
                            repaint();
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
        initialize(w, h, false);
    }
    public static void makeMap(int w, int h)
    {
        if(panel == null)
            panel = new MapPanel(w, h);
    }

    public static MapPanel getMap()
    {
        return panel;
    }

    public void initialize(int w, int h, boolean isLoad)
    {
        synchronized (CellKind.GRASS) {
            setPreferredSize(new Dimension(w * Cell.width, h * Cell.hight));
            cells = new Cell[h][w];
            badAnimals = new ArrayList<>();
            boombs = new ArrayList<>();
            explosioningCells = new ArrayList<>();
            workingBoomb = new ArrayList<>();
            powerUps = new ArrayList<>();
            isDoorOpen = false;
            isDoorVisible = false;
            this.h = h;
            this.w = w;
            if (!isLoad) {
                level ++;
                fillCells();
            }
            if (!animationThread.isAlive())
                animationThread.start();
        }
    }

    private void fillCells() {

        for (int i = 1; i < h; i += 2) {
            for (int j = 1; j < w; j += 2) {
                cells[i][j] = new Cell(CellKind.ROCKWALL, i, j);
            }
        }

        do {
            doorI = (Math.abs(new Random().nextInt())) % h;
            doorJ = (Math.abs(new Random().nextInt())) % w;
        } while((doorI * doorJ) % 2 == 1 || (doorI == 0 && doorJ < 6));
        for(int i = 0; i < h; i ++) {
            for (int j = 0; j < w; j++) {
                if ((i * j) % 2 == 1)
                    continue;
                if(i == doorI && j == doorJ) {
                    cells[i][j] = new Cell(CellKind.WALL, i, j);
                    continue;
                }
                int a = (new Random().nextInt()) % 6;
                if(i == 0 && j < 6)
                    a = 1;
                Cell cl = null;
                if (a == 0) {
                    cl = new Cell(CellKind.WALL, i, j);
                    int b = (Math.abs(new Random().nextInt())) % 30;
                    if(b == 0) {
                        cl.setPowerUp(new DecreaseBoomb(i, j));
                    }
                    if(b == 1) {
                        cl.setPowerUp(new DecreasePoint(i, j));
                    }
                    if(b == 2) {
                        cl.setPowerUp(new DecreaseRadius(i, j));
                    }
                    if(b == 3) {
                        cl.setPowerUp(new DecreaseSpeed(i, j));
                    }
                    if(b == 4) {
                        cl.setPowerUp(new IncreaseBoomb(i, j));
                    }
                    if(b == 5) {
                        cl.setPowerUp(new IncreasePoint(i, j));
                    }
                    if(b == 6) {
                        cl.setPowerUp(new IncreaseRadius(i, j));
                    }
                    if(b == 7) {
                        cl.setPowerUp(new IncreaseSpeed(i, j));
                    }
                    if(b == 8) {
                        cl.setPowerUp(new BoombController(i, j));
                    }
                    if(b == 9) {
                        cl.setPowerUp(new MakeGhost(i, j));
                    }
                } else {
                    cl = new Cell(CellKind.GRASS, i, j);
                    if(i + j > 10) {
                        int b = (Math.abs(new Random().nextInt())) % (h * w);
                        int c = Math.min(h, w);
                        if(level > 4)
                            c += (level - 4) * c / 5;
                        if (b <= c) {
                            BadAnimal bad = null;
                            if(level > 3)
                                b %= 4;
                            else
                                b %= level;
                            if (b == 0) {
                                bad = new Fox(guyNatSpeed / 2, j, i);
                                badAnimals.add(bad);
                            }
                            if (b == 2) {
                                bad = new Lion(guyNatSpeed, j, i);
                                badAnimals.add(bad);
                            }
                            if (b == 1) {
                                bad = new Bear(guyNatSpeed / 2, j, i);
                                badAnimals.add(bad);
                            }
                            if (b == 3) {
                                bad = new Dragon(guyNatSpeed, j, i);
                                badAnimals.add(bad);
                            }
                            cl.getBadanimals().add(bad);
                        }
                    }

                }
                cells[i][j] = cl;
            }
        }
        guy.setIi(0); guy.setJj(0);
        guy.setX(0); guy.setY(0);
        cells[0][0].setGuy(guy);
        try {
            closeDoor = ImageIO.read(getClass().getResource("/img/door/0.gif"));
            openDoor = ImageIO.read(getClass().getResource("/img/door/1.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(! guy.isRunning()) {
            if (keyEvent.getKeyCode() == KeyEvent.VK_D) {
                guy.move(1);
            }
            if (keyEvent.getKeyCode() == KeyEvent.VK_A) {
                guy.move(3);
            }
            if (keyEvent.getKeyCode() == KeyEvent.VK_W) {
                guy.move(0);
            }
            if (keyEvent.getKeyCode() == KeyEvent.VK_S) {
                guy.move(2);
            }
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {
            if(cells[guy.getIi()][guy.getJj()].getBoomb() == null && boombs.size() < boombLimit && cells[guy.getIi()][guy.getJj()].getKind() == CellKind.GRASS) {
                Boomb b = new Boomb(guy.getIi(), guy.getJj());
                cells[guy.getIi()][guy.getJj()].setBoomb(b);
                boombs.add(b);
                repaint();
            }
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_B) {
            if(guy.isBoombRemote() == true)
                BoombController.explosion();
        }

        if(keyEvent.getKeyCode() == KeyEvent.VK_Q && keyEvent.isControlDown())
        {
            save();
        }

        if(keyEvent.getKeyCode() == KeyEvent.VK_O && keyEvent.isControlDown())
        {
            load();
            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                cells[i][j].draw(g);
            }
        }

        synchronized (Color.yellow) {
            for (PowerUp powerUp : powerUps) {
                powerUp.draw(g);
            }
        }
        if (isDoorVisible) {
            g.drawImage(closeDoor, doorJ * Cell.width, doorI * Cell.hight, Cell.width, Cell.hight, null);
        }
        if (isDoorOpen) {
            g.drawImage(openDoor, doorJ * Cell.width, doorI * Cell.hight, Cell.width, Cell.hight, null);
        }
        synchronized (Color.green) {
            for (Boomb boomb : boombs) {
                boomb.draw(g);
            }
        }

        synchronized (Color.blue) {
            for (Cell cell : explosioningCells) {
                cell.draw(g);
            }
        }

        synchronized (Color.red) {
            for (BadAnimal badAnimal : badAnimals) {
                badAnimal.draw(g);
            }
        }
        guy.draw(g);

    }

    private void save() {
        synchronized (Color.gray) {
            synchronized (Color.magenta) {
                if(name == null)
                    name = "";
                name = JOptionPane.showInputDialog(MainFrame.getFrame(), "Enter the game's name.", name);
                if(name == null)
                    return;
                if(name.equals("")) {
                    JOptionPane.showMessageDialog(MainFrame.getFrame(), "Eror: empty name!");
                    return;
                }
                try {

                    //delete pre game
                    PreparedStatement stmt = Main.selectGames();
                    stmt.setString(1, name);
                    stmt.execute();
                    ResultSet result = stmt.executeQuery();
                    if(result.next()) {
                        int id = result.getInt("id");
                        stmt = Main.deletGames();
                        stmt.setInt(1, id);System.out.println(w + " " + h);
                        stmt.execute();
                        stmt = Main.deletObjects();
                        stmt.setInt(1, id);
                        stmt.execute();
                    }

                    //add new game
                    PreparedStatement query = Main.insertGame();
                    query.setString(1, name);
                    query.setInt(2, level);
                    query.setInt(3, MainFrame.getFrame().getInformationPanel().getTime());
                    query.setInt(4, guy.getPoint());
                    query.setInt(5, w);
                    query.setInt(6, h);
                    query.setInt(7, guy.getSpeed());
                    query.setInt(8, doorI);
                    query.setInt(9, doorJ);
                    query.setInt(10, boombRadius);
                    query.setInt(11, boombLimit);
                    if(guy.isGhost() == false)
                        query.setInt(12, 0);
                    else
                        query.setInt(12, 1);
                    if(guy.isBoombRemote() == false)
                        query.setInt(13, 0);
                    else
                        query.setInt(13, 1);
                    query.execute();

                    //get game id
                    stmt = Main.selectGames();
                    stmt.setString(1, name);
                    stmt.execute();
                    result = stmt.executeQuery();
                    result.next();
                    int id = result.getInt("id");


                    //save objects
                    for(int i = 0; i < h; i ++) {
                        for(int j = 0; j < w; j ++) {
                            cells[i][j].save(id);
                        }
                    }

                    for(BadAnimal badAnimal: badAnimals) {
                        badAnimal.save(id);
                    }

                    for(Boomb boomb: boombs) {
                        boomb.save(id);
                    }

                    guy.save(id);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    /*private void save() {
        JFileChooser jfChoos = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfChoos.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnValue = jfChoos.showOpenDialog(this);
        String path = null;
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            path = jfChoos.getSelectedFile().toString();
            FileOutputStream fileOut = null;
            try {
                fileOut = new FileOutputStream(path + "/" + LocalDateTime.now().toString() + ".txt");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            PrintStream printer = new PrintStream(fileOut);
            printer.println(h + "\n" + w);
            printer.println(guy + "\n" + guyJ);
            printer.println(doorI + "\n" + doorJ);
            for(int i = 0; i < h; i ++)
                for(int j = 0; j < w; j ++)
                    cells[i][j].save(printer);
        }
    }

    public void load()
    {
        JFileChooser jfChoos = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = jfChoos.showOpenDialog(this);
        if(returnValue == JFileChooser.APPROVE_OPTION) {
            RandomAccessFile file = null;
            try {
                file = new RandomAccessFile(jfChoos.getSelectedFile(), "r");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            //load
            try {
                h = Integer.valueOf(file.readLine());
                w = Integer.valueOf(file.readLine());
                initialize(w, h, true);
                guyI = Integer.valueOf(file.readLine());
                guyJ = Integer.valueOf(file.readLine());
                doorI = Integer.valueOf(file.readLine());
                doorJ = Integer.valueOf(file.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < h; i++)
                for (int j = 0; j < w; j++)
                    cells[i][j] = Cell.load(file, j * Cell.width, i * Cell.hight);
            cells[guyI][guyJ].setGuy(true);
        }
    }*/

    public void  load() {
        synchronized (Color.gray) {
            synchronized (Color.magenta) {
                try {
                    PreparedStatement stmt = Main.selectAllGames();
                    stmt.execute();
                    ResultSet result = stmt.executeQuery();
                    ArrayList<String> strings = new ArrayList<>();
                    while (result.next()) {
                        strings.add(result.getString("name"));
                    }
                    String[] options = new String[strings.size()];
                    for (int i = 0; i < strings.size(); i++) {
                        options[i] = strings.get(i);
                    }
                    if(strings.size() == 0) {
                        JOptionPane.showMessageDialog(MainFrame.getFrame(), "Eror: there is no game!");
                        System.exit(1);
                    }
                    JComboBox optionList = new JComboBox(options);
                    JOptionPane.showMessageDialog(null, optionList, "Archived games", JOptionPane.QUESTION_MESSAGE);
                    name = (String)optionList.getSelectedItem();

                    //get game
                    stmt = Main.selectGames();
                    stmt.setString(1, name);
                    stmt.execute();
                    result = stmt.executeQuery();
                    result.next();
                    int id = result.getInt("id");
                    level = result.getInt("lev");
                    w = result.getInt("map_x");
                    h = result.getInt("map_y");
                    doorI = result.getInt("door_i");
                    doorJ = result.getInt("door_j");
                    boombRadius = result.getInt("radius");
                    boombLimit = result.getInt("boomb_lim");
                    int guySpd = result.getInt("guy_speed");
                    int scr = result.getInt("score");
                    int time = result.getInt("time");
                    int ghost = result.getInt("is_ghost");
                    int rem = result.getInt("remote");
                    initialize(w, h, true);


                    //Objects
                    stmt = Main.selectObjects();
                    stmt.setInt(1, id);
                    stmt.execute();
                    result = stmt.executeQuery();

                    while(result.next()) {
                        String type = result.getString("type");
                        PowerUp p = null;
                        BadAnimal bad = null;
                        int i = result.getInt("x");
                        int j = result.getInt("y");
                        if(type.equals("Grass")) {
                            cells[i][j] = new Cell(CellKind.GRASS, i, j);
                            if(result.getInt("frame_num") != -1)
                                cells[i][j].load(result.getInt("frame_num"));
                        }
                        if(type.equals("Wall")) {
                            cells[i][j] = new Cell(CellKind.WALL, i, j);
                        }
                        if(type.equals("RockWall")) {
                            cells[i][j] = new Cell(CellKind.ROCKWALL, i, j);
                        }
                        if(type.equals("DecreaseBoomb")) {
                            p = new DecreaseBoomb(i, j);
                        }
                        if(type.equals("DecreaseSpeed")) {
                            p = new DecreaseSpeed(i, j);
                        }
                        if(type.equals("DecreasePoint")) {
                            p = new DecreasePoint(i, j);
                        }
                        if(type.equals("DecreaseRadius")){
                            p = new DecreaseRadius(i, j);
                        }
                        if(type.equals("IncreaseBoomb")) {
                            p = new IncreaseBoomb(i, j);
                        }
                        if(type.equals("IncreaseSpeed")) {
                            p = new IncreaseSpeed(i, j);
                        }
                        if(type.equals("IncreasePoint")) {
                            p = new IncreasePoint(i, j);
                        }
                        if(type.equals("IncreaseRadius")) {
                            p = new IncreaseRadius(i, j);
                        }
                        if(type.equals("BoombController")) {
                            p = new BoombController(i, j);
                        }
                        if(type.equals("MakeGhost")) {
                            p = new MakeGhost(i, j);
                        }
                        if(p != null) {
                            cells[i][j].setPowerUp(p);
                            if(cells[i][j].getKind() == CellKind.GRASS)
                                powerUps.add(p);
                        }
                        if(type.equals("Boomb")) {
                            Boomb b = new Boomb(i, j);
                            boombs.add(b);
                            cells[i][j].setBoomb(b);
                            b.load(result.getInt("frame_num"));
                        }
                        if(type.equals("Guy")) {
                            guy.setX(i);
                            guy.setY(j);
                            guy.setSpeed(guySpd);
                            guy.setPoint(scr);
                            if(rem == 0)
                                guy.setBoombRemote(false);
                            else
                                guy.setBoombRemote(true);
                            if(ghost == 0)
                                guy.setGhost(false);
                            else
                                guy.setGhost(true);
                            guy.load(result.getInt("frame_num"));
                        }
                        if(type.equals("Fox")) {
                            bad = new Fox(guyNatSpeed / 2, 0, 0);
                        }
                        if(type.equals("Lion")) {
                            bad = new Lion(guyNatSpeed, 0, 0);
                        }
                        if(type.equals("Bear")) {
                            bad = new Bear(guyNatSpeed / 2, 0, 0);
                        }
                        if(type.equals("Dragon")) {
                            bad = new Dragon(guyNatSpeed, 0, 0);
                        }
                        if(bad != null) {
                            badAnimals.add(bad);
                            bad.setX(i);
                            bad.setY(j);
                            bad.load(result.getInt("frame_num"));
                        }
                    }

                    if(cells[doorI][doorJ].getKind() == CellKind.GRASS) {
                        isDoorVisible = true;
                        if(badAnimals.size() == 0) {
                            isDoorOpen = true;
                            isDoorVisible = false;
                        }
                    }

                    MainFrame.getFrame().getInformationPanel().setLevelLabel(level);
                    MainFrame.getFrame().getInformationPanel().setPointLabel(guy.getPoint());
                    MainFrame.getFrame().getInformationPanel().setTimeLabel(time);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public int getH() {
        return h;
    }

    public int getW() {
        return w;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public Guy getGuy() {
        return guy;
    }

    public int getGuyNatSpeed() {
        return guyNatSpeed;
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

    public ArrayList<Boomb> getBoombs() {
        return boombs;
    }

    public ArrayList<Boomb> getWorkingBoomb() {
        return workingBoomb;
    }

    public ArrayList<PowerUp> getPowerUps() {
        return powerUps;
    }

    public int getDoorI() {
        return doorI;
    }

    public int getDoorJ() {
        return doorJ;
    }

    public int getLevel() {
        return level;
    }

    public boolean isDoorOpen() {
        return isDoorOpen;
    }

    public ArrayList<Cell> getExplosioningCells() {
        return explosioningCells;
    }
}
