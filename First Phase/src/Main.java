import java.sql.*;

public class Main {

    static Connection connection = null;
    static Statement query = null;

    private static void connectToDataBase() {
        // JDBC driver name and database URL
        //final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        final String DB_URL = "jdbc:mysql://localhost/boomber_man";

        final String USER = "HannaYz";
        final String PASS = "13781999";

        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            //query = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE, ResultSet.CLOSE_CURSORS_AT_COMMIT);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static PreparedStatement insertGame() throws SQLException {
        //13
        return connection.prepareStatement("INSERT INTO games(name, lev, time, score, map_x, map_y, guy_speed, door_I, door_J, radius, boomb_lim, is_ghost, remote)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");

    }


    public static PreparedStatement insertObject() throws SQLException {
        //5
        return connection.prepareStatement("INSERT INTO objects(game_id, type, x, y, frame_num)" +
                " VALUES (?, ?, ?, ?, ?);");
    }


    public static PreparedStatement deletObjects() throws SQLException {
        //1
        return connection.prepareStatement("DELETE FROM objects WHERE game_id = ?");
    }

    public static PreparedStatement deletGames() throws SQLException {
        //1
        return connection.prepareStatement("DELETE FROM games WHERE id = ?");
    }


    public static PreparedStatement selectObjects() throws SQLException {
        //1
        return connection.prepareStatement("SELECT * FROM objects WHERE game_id = ?", ResultSet.TYPE_FORWARD_ONLY,
                ResultSet.CONCUR_UPDATABLE,
                ResultSet.CLOSE_CURSORS_AT_COMMIT);
    }

    public static PreparedStatement selectGames() throws SQLException {
        //1
        return connection.prepareStatement("SELECT * FROM games WHERE name = ?", ResultSet.TYPE_FORWARD_ONLY,
                ResultSet.CONCUR_UPDATABLE,
                ResultSet.CLOSE_CURSORS_AT_COMMIT);
    }

    public static PreparedStatement selectAllGames() throws SQLException {
        //1
        return connection.prepareStatement("SELECT * FROM games", ResultSet.TYPE_FORWARD_ONLY,
                ResultSet.CONCUR_UPDATABLE,
                ResultSet.CLOSE_CURSORS_AT_COMMIT);
    }





    public static void main(String[] args) {
        connectToDataBase();
        StFrame stFrame = StFrame.getFrame();
        stFrame.setVisible(true);
    }
}
