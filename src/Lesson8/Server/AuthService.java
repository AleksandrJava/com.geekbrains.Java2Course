package Lesson8.Server;

import java.sql.*;

public class AuthService {

    private static Connection connection;
    private static Statement stmt;

    public static void connect() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:mainDB.db");
            stmt = connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static String getNickByLoginAndPass(String login, String pass) {
        String sql = String.format("SELECT nickname, password FROM main\n" +
                "WHERE login = '%s'", login);

        int myhash = pass.hashCode();

        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String nick = rs.getString(1);
                int dbHash = rs.getInt(2);

                if (myhash == dbHash) {
                    return nick;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addUser(String login, String pass, String nick) {
        String sql = String.format("INSERT INTO main (login, password, nickname)" +
                "VALUES ('%s', '%s', '%s')", login, pass.hashCode(), nick);
        try {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addIntoBlackList(String nick, String nickId) {
        String sql = String.format("UPDATE main SET blackList = (blackList  || '%s ') WHERE nickName = ('%s')", nick, nickId);
        try {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static boolean checkInBlackList(String nick, String nickId) {
        boolean yesOrNot = false;
        try{
            String sql = String.format("SELECT blackList FROM main\n" + "where nickname = '%s'", nickId);
            ResultSet rs = stmt.executeQuery(sql);
            String res = "";

            if (rs.next()) {
                res = rs.getString(1);
                String[] tokens = res.split(" ");
                for (String s : tokens) {
                    if (nick.equals(s)) {
                        yesOrNot = true;
                        break;
                    } else {
                        yesOrNot = false;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(yesOrNot == true){
            return true;
        } else return false;

    }

    public static boolean checkHaveThisUser(String nick) {
        try {
            String sql = String.format("SELECT nickName FROM main\n" + "where nickname = '%s'", nick);
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                return true;
            } else return false;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
