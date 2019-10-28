package database;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import javax.naming.Name;
import java.util.*;


class TestDatabase {
    static Database DB;
    Table TB;

    @BeforeAll
    static void setUpForAll() {
        DB = new Database("database");
    }

    @BeforeEach
    void setUp() {
        String[] columnNames = new String[]{"ID", "Name", "Major", "GPA"};
        String[] typeNames = new String[]{"String", "String", "String", "Number"};
        String pk = "ID";
        try {
            this.TB = new Table("Student", columnNames, typeNames);
            Map<String, Class<?>> datatypes = this.TB.setUpTable(pk, columnNames, typeNames);

            Object[] one = new Object[]{"001", "Alice", "EE", 3.8};
            Object[] two = new Object[]{"002", "Bob", "EE", 3.7};
            Object[] three = new Object[]{"003", "Cathy", "CS", 4.0};

            Row row1 = new Row(columnNames, datatypes, one, "TimeStamp");
            this.TB.addRow(one[0], row1);
            Row row2 = new Row(columnNames, datatypes, two, "TimeStamp");
            this.TB.addRow(two[0], row2);
            Row row3 = new Row(columnNames, datatypes, three, "TimeStamp");
            this.TB.addRow(three[0], row3);
            DB.addTB(TB);
            
        } catch (Exception e) {
            // This line is not reachable with the above input parameters
        }
    }

    @AfterEach
    void tearDown() {
        DB.deleteAll();
    }

    /*
        These test cases test the behaviors on 'Database' level
     */

    @Test
    void createTable() {
        try {
            Table newTB = DB.createTable("new_table", new String[]{"Column"}, new String[]{"String"});
            Map<String, Table> tables = DB.getTables();
            assertTrue(tables.size() == 2
                    && tables.get("Student") != null && tables.get("new_table") != null);
        } catch (Exception e) {
            fail("failed to create a table");
        }
    }

    @Test
    void createTableWithNullTableName() {
        try {
            Table newTB = DB.createTable(null, new String[]{"Column"}, new String[]{"String"});
            // This line is unreachable if the test can be passed
            fail("cannot handle null table name");
        } catch (Exception e1) {
            Map<String, Table> tables = DB.getTables();
            assertTrue(tables.size() == 1 && tables.get("Student") != null);
        }
    }

    @Test
    void createTableWithEmptyTableName() {
        try {
            Table newTB2 = DB.createTable("", new String[]{"Column"}, new String[]{"String"});
            // This line is unreachable if the test can be passed
            fail("cannot handle empty table name");
        } catch (Exception e2) {
            Map<String, Table> tables = DB.getTables();
            assertTrue(tables.size() == 1 && tables.get("Student") != null);
        }
    }

    @Test
    void createTableWithNullColumnNameArray() {
        try {
            Table newTB = DB.createTable("new_table", null, new String[]{"String"});
            // This line is unreachable if the test can be passed
            fail("cannot handle null column name array");
        } catch (Exception e) {
            Map<String, Table> tables = DB.getTables();
            assertTrue(tables.size() == 1 && tables.get("Student") != null);
        }
    }

    @Test
    void createTableWithNullColumnName() {
        try {
            Table newTB2 = DB.createTable("new_table", new String[]{null}, new String[]{"String"});
            // This line is unreachable if the test can be passed
            fail("cannot handle null column name");
        } catch (Exception e) {
            Map<String, Table> tables = DB.getTables();
            assertTrue(tables.size() == 1 && tables.get("Student") != null);
        }
    }

    @Test
    void createTableWithEmptyColumnNameArray() {
        try {
            Table newTB = DB.createTable("new_table", new String[]{}, new String[]{"String"});
            // This line is unreachable if the test can be passed
            fail("cannot handle empty column name array");
        } catch (Exception e) {
            Map<String, Table> tables = DB.getTables();
            assertTrue(tables.size() == 1 && tables.get("Student") != null);
        }
    }

    @Test
    void createTableWithEmptyColumnName() {
        try {
            Table newTB2 = DB.createTable("new_table", new String[]{""}, new String[]{"String"});
            // This line is unreachable if the test can be passed
            fail("cannot handle empty column name");
        } catch (Exception e) {
            Map<String, Table> tables = DB.getTables();
            assertTrue(tables.size() == 1 && tables.get("Student") != null);
        }
    }

    @Test
    void createTableWithNullTypeNameArray() {
        try {
            Table newTB = DB.createTable("new_table", new String[]{"Column"}, null);
            // This line is unreachable if the test can be passed
            fail("cannot handle null type name array");
        } catch (Exception e) {
            Map<String, Table> tables = DB.getTables();
            assertTrue(tables.size() == 1 && tables.get("Student") != null);
        }
    }

    @Test
    void createTableWithNullTypeName() {
        try {
            Table newTB2 = DB.createTable("new_table", new String[]{"Column"}, new String[]{null});
            // This line is unreachable if the test can be passed
            fail("cannot handle null type name");
        } catch (Exception e) {
            Map<String, Table> tables = DB.getTables();
            assertTrue(tables.size() == 1 && tables.get("Student") != null);
        }
    }

    @Test
    void createTableWithEmptyTypeNameArray() {
        try {
            Table newTB = DB.createTable("new_table", new String[]{"Column"}, new String[]{});
            // This line is unreachable if the test can be passed
            fail("cannot handle empty type name array");
        } catch (Exception e) {
            Map<String, Table> tables = DB.getTables();
            assertTrue(tables.size() == 1 && tables.get("Student") != null);
        }
    }

    @Test
    void createTableWithEmptyTypeName() {
        try {
            Table newTB2 = DB.createTable("new_table", new String[]{"Column"}, new String[]{""});
            // This line is unreachable if test can be passed
            fail("cannot handle empty type name");
        } catch (Exception e) {
            Map<String, Table> tables = DB.getTables();
            assertTrue(tables.size() == 1 && tables.get("Student") != null);
        }
    }

    @Test
    void createTableWithInvalidTypeName() {
        try {
            Table newTB2 = DB.createTable("new_table", new String[]{"Column"}, new String[]{"InvalidTypeName"});
            // This line is unreachable if test can be passed
            fail("cannot handle invalid type name");
        } catch (Exception e) {
            Map<String, Table> tables = DB.getTables();
            assertTrue(tables.size() == 1 && tables.get("Student") != null);
        }
    }

    @Test
    void createDuplicateTable() {
        try {
            Table newTB = DB.createTable("Student", new String[]{"Column"}, new String[]{"String"});
            // This line is unreachable if test can be passed
            fail("cannot handle duplicate table name");
        } catch (Exception e) {
            Map<String, Table> tables = DB.getTables();
            assertTrue(tables.size() == 1 && tables.get("Student") != null);
        }
    }

    @Test
    void createTableWithDuplicateColumnNames() {
        try {
            Table newTB = DB.createTable("new_table", new String[]{"Column1", "Column1"}, new String[]{"String","String"});
            // This line is unreachable if test can be passed
            fail("cannot handle duplicate column name");
        } catch (Exception e) {
            Map<String, Table> tables = DB.getTables();
            assertTrue(tables.size() == 1 && tables.get("Student") != null);
        }
    }

    @Test
    void deleteTable() {
        try {
            DB.deleteTable("Student");
            Map<String, Table> tables = DB.getTables();
            assertTrue(tables.isEmpty());
        } catch (Exception e) {
            fail("cannot delete a existent table");
        }
    }

    @Test
    void deleteNonExistentTable() {
        try {
            DB.deleteTable("nonexsistent_table");
            // This line is unreachable if test can be passed
            fail("cannot handle deleting a non-existent table");
        } catch (Exception e) {
            Map<String, Table> tables = DB.getTables();
            assertTrue(tables.size() == 1 && tables.get("Student") != null);
        }
    }

}