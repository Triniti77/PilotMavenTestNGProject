package db.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrinitiDB extends DBBase {
    public static final String DBNAME = "triniti";

    TrinitiDB(Connection conn) {
        super(conn, DBNAME);
    }


    public String getDBName() {
        return DBNAME;
    }


    public void createDB() throws SQLException {
        upd("CREATE DATABASE "+DBNAME+" CHARACTER SET 'utf8'");
        useDB();
    }

    public void dropAll() throws SQLException {

        upd("DROP DATABASE IF EXISTS "+DBNAME);
    }

    public void createSampleTables() throws SQLException {
        upd("CREATE TABLE users(id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(30) NOT NULL, " +
                "login DATETIME DEFAULT CURRENT_TIMESTAMP, rate INT NOT NULL DEFAULT 0)");
    }

    public User[] getUsers(int from, int limit) {
        PreparedStatement pr = prepare("SELECT * FROM users LIMIT ? OFFSET ?");
        User[] users = {};
        try {
            pr.setInt(1, limit);
            pr.setInt(2, from);
            pr.execute();
            ResultSet r = pr.getResultSet();
            users = new User[limit];
            int i = 0;
            while (r.next()) {
                users[i] = new User(r.getInt("id"), r.getString("name"), r.getString("login"), r.getInt("rate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void insertSampleData() throws SQLException {
        upd("INSERT INTO users(name, login, rate) VALUES ('Jim Hurry', DEFAULT, 10), ('Bob Williams', '2020-07-07 19:49', 20), " +
                "('Jack Morgan', DEFAULT, 5), ('Will Smith', '2020-06-28 05:43', 100)," +
                "('Evan Johnes', '2019-06-15 16:01', 54), ('Erica Willow', '2018-03-03 11:56', 89)");
    }

    public void setRate(int id, int value) {
        PreparedStatement pr = prepare("UPDATE users SET rate = ? WHERE id = ?");
        try {
            pr.setInt(1, value);
            pr.setInt(2, id);
            upd(pr);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUser(int id) {
        User u = null;
        try {
            PreparedStatement pr = prepare("SELECT * FROM users WHERE id=?");
            pr.setInt(1, id);
            ResultSet r = exec(pr);
            while (r.next()) {
                u = new User(r.getInt("id"), r.getString("name"), r.getString("login"), r.getInt("rate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }

    public void deleteUser(int id) {
        PreparedStatement pr = prepare("DELETE FROM users WHERE id = ?");
        try {
            pr.setInt(1, id);
            upd(pr);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void disaster() {
        try {
            upd("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void catastrofa() {
        try {
            dropAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public class User {
        private String name;
        private int id;
        private String lastLogin;
        private int rate;

        private User(int id, String name, String lastLogin, int rate) {
            this.id = id;
            this.name = name;
            this.lastLogin = lastLogin;
            this.rate = rate;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        public String getLastLogin() {
            return lastLogin;
        }

        public int getRate() {
            return rate;
        }
    }
}
