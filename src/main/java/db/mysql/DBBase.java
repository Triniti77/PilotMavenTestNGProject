package db.mysql;

import java.sql.*;

public class DBBase {
    private final Connection conn;
    private final String dbname;
    private Statement statement;
    private boolean dbUse = false;

    public DBBase(Connection conn, String dbname) {
        this.conn = conn;
        this.dbname = dbname;
    }

    public boolean isCreated() throws SQLException {
        ResultSet r = exec("SHOW DATABASES");
        boolean dbPresent = false;
        while (r.next()) {
            String name = r.getString("Database");
            if (name.equals(dbname)) {
                dbPresent = true;
            }
        }
        return dbPresent;
    }


    public boolean isDatabaseEmpty() throws SQLException {
        useDB();
        ResultSet r = exec("SHOW TABLES");
        boolean hasRows = false;
        while (r.next()) {
            hasRows = true;
        }
        return !hasRows;
    }

    protected void useDB() {
        try {
            if (isCreated()) {
                conn.setCatalog(dbname);
                dbUse = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected PreparedStatement prepare(String query) {
        PreparedStatement pr;
        try {
            pr = conn.prepareStatement(query);
            return pr;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected ResultSet upd(PreparedStatement pstm) throws SQLException {
        useDB();
        ResultSet r;
        try {
            pstm.executeUpdate();
            r = pstm.getResultSet();
        }
        finally {
            pstm.close();
        }
        return r;
    }

    protected void upd(String query) throws SQLException {
        useDB();
        try (Statement st = conn.createStatement()) {
            st.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected ResultSet exec(String query) {
        if (!query.startsWith("SHOW") && !query.startsWith("show")) {
            useDB();
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            statement = null;
        }
        try {
            statement = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet r = null;
        try {
            r = statement.executeQuery(query);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return r;
    }


    protected ResultSet exec(PreparedStatement pstm) {
        ResultSet r = null;
        try {
            pstm.execute();
            r = pstm.getResultSet();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return r;
    }
}
