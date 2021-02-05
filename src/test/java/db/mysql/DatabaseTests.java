package db.mysql;

import org.testng.annotations.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

import static org.testng.Assert.*;

public class DatabaseTests {
    String mysqlConnectionUri = "mysql://localhost?user=triniti&password=tr1n1t1";

    Connection conn;
    TrinitiDB trinitiDB;

    @BeforeClass
    public void warmup() throws SQLException {
        conn = DriverManager.getConnection("jdbc:"+mysqlConnectionUri);
        trinitiDB = new TrinitiDB(conn);
        trinitiDB.dropAll();
    }

    @AfterClass
    public void shutdown() throws SQLException {
        if (conn != null) {
            conn.close();
            conn = null;
        }
    }

    @Test()
    public void createDBTest() throws Exception {
        assertFalse(trinitiDB.isCreated(), "DB " + trinitiDB.getDBName() + " is not created");
        trinitiDB.createDB();
        assertTrue(trinitiDB.isCreated(), "DB " + trinitiDB.getDBName() + " is created");
    }

    @Test(dependsOnMethods = {"createDBTest"})
    public void createTableTest() throws Exception {
        assertTrue(trinitiDB.isDatabaseEmpty(), "Should not have any table(s)");
        trinitiDB.createSampleTables();
        assertFalse(trinitiDB.isDatabaseEmpty(), "Should have all table(s)");
    }

    @Test(dependsOnMethods = {"createDBTest", "createTableTest"})
    public void insertTest() throws SQLException {
        TrinitiDB.User[] users = trinitiDB.getUsers(0,50);
        assertNull(users[0], "Users table should be empty");
        trinitiDB.insertSampleData();
        users = trinitiDB.getUsers(0,50);
        assertNotNull(users[0], "Users table should be filled with users");
    }

    @Test(dependsOnMethods = {"createDBTest", "createTableTest", "insertTest"})
    public void selectTest1() {
        TrinitiDB.User[] users = trinitiDB.getUsers(0,10);
        assertTrue(Arrays.stream(users).anyMatch(r -> r.getRate() > 50), "Has users with rate > 50");
    }

    @Test(dependsOnMethods = {"createDBTest", "createTableTest", "insertTest", "selectTest1"})
    public void updateTest() {
        trinitiDB.setRate(2, 45);
        TrinitiDB.User user = trinitiDB.getUser(2);
        assertEquals(45, user.getRate(), "User id(2) rate should be changed to 45");
    }

    @Test(dependsOnMethods = {"createDBTest", "createTableTest", "insertTest", "selectTest1", "updateTest"})
    public void deleteTest() {
        trinitiDB.deleteUser(3);
        TrinitiDB.User user = trinitiDB.getUser(3);
        assertNull(user, "User id(3) should be deleted");
    }

    @Test(dependsOnMethods = {"createDBTest", "createTableTest", "insertTest", "selectTest1", "updateTest", "deleteTest"})
    public void dropTableTest() throws SQLException {
        trinitiDB.disaster();
        assertTrue(trinitiDB.isDatabaseEmpty(), "Should not have any table(s)");
    }

    @Test(dependsOnMethods = {"createDBTest", "createTableTest", "insertTest", "selectTest1", "updateTest", "deleteTest", "dropTableTest"})
    public void dropDatabaseTest() throws SQLException {
        trinitiDB.catastrofa();
        assertFalse(trinitiDB.isCreated(), "Database should be non existant");
    }
}
