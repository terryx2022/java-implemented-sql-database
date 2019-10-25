package database;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

/**
 * These test cases test the behaviors on 'DatabaseManger' level
 */
class DatabaseManagerTest {
    static DatabaseManager DBManager;

    @BeforeAll
    static void setUpForAll() {
        DBManager = new DatabaseManager();
    }

    @BeforeEach
    void setUp() {
        DBManager.addDB(new Database("database"));
    }

    @AfterEach
    void tearDown() {
        DBManager.deleteAll();
    }

    @Test
    void createDatabase() {
        try {
            setUp();
            Database newDB = DBManager.createDatabase("new_database");
            assertEquals(DBManager.getDBList().get("new_database"), newDB);
        } catch (Exception e) {
            fail("failed to create a database");
        } finally {
            tearDown();
        }
    }

    @Test
    void createDatabaseWithNullName() {
        try {
            setUp();
            Database newDB = DBManager.createDatabase(null);
            // This line is unreachable if test can be passed
            fail("cannot handle null database name");
        } catch (Exception e) {
            // Test succeeds if reaching this line
        } finally {
            tearDown();
        }
    }

    @Test
    void createDatabaseWithEmptyName() {
        try {
            setUp();
            Database newDB = DBManager.createDatabase("");
            // This line is unreachable if test can be passed
            fail("cannot handle empty database name");
        } catch (Exception e) {
            // Test succeeds if reaching this line
        } finally {
            tearDown();
        }
    }

    @Test
    void createDuplicateDatabase() {
        try {
            setUp();
            Database newDB = DBManager.createDatabase("database");
            // This line is unreachable if test can be passed
            fail("cannot handle duplicate database name");
        } catch (Exception e) {
            // Test succeeds if reaching this line
        } finally {
            tearDown();
        }
    }

    @Test
    void deleteDatabase() {
        try {
            setUp();
            Database newDB = DBManager.deleteDatabase("database");
        } catch (Exception e) {
            fail("cannot delete a existent database");
        } finally {
            tearDown();
        }
    }

    @Test
    void deleteNonExistentDatabase() {
        try {
            setUp();
            Database newDB = DBManager.deleteDatabase("nonexsistent_database");
            // This line is unreachable if test can be passed
            fail("cannot handle deleting a non-existent database");
        } catch (Exception e) {
            // Test succeeds if reaching this line
        } finally {
            tearDown();
        }
    }

    @Test
    void getDatabase() {
        assertEquals(DBManager.getDatabase("database"), DBManager.getDBList().get("database"));
    }
}
















