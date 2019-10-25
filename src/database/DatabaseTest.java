package database;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.util.*;


class DatabaseTest {
    static Database DB;
    Database.Table TB;

    @BeforeAll
    static void setUpForAll() {
        DB = new Database("database");
    }

    @BeforeEach
    void setUp() {
        String[] columns = new String[]{"ID", "Name", "Major", "GPA"};
        String[] types = new String[]{"String", "String", "String", "Number"};
        try {
            this.TB = new Database.Table("Student", columns, types);
            this.TB.setColumnNames(columns);
            this.TB.setDataTypeNames(types);
            this.TB.setPrimaryKey("ID");
            Map<String, Object> row1 = new HashMap<>();
            row1.put("ID", "001"); row1.put("Name", "Alice"); row1.put("Major", "EE"); row1.put("GPA", 3.8);
            row1.put("Timestamp", System.currentTimeMillis());
            this.TB.addRow("001", row1);
            Map<String, Object> row2 = new HashMap<>();
            row2.put("ID", "002"); row2.put("Name", "Bob"); row2.put("Major", "EE"); row2.put("GPA", 3.7);
            row2.put("Timestamp", System.currentTimeMillis());
            this.TB.addRow("002", row2);
            Map<String, Object> row3 = new HashMap<>();
            row3.put("ID", "003"); row3.put("Name", "Cathy"); row3.put("Major", "CS"); row3.put("GPA", 4.0);
            row3.put("Timestamp", System.currentTimeMillis());
            this.TB.addRow("003", row3);
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
            Database.Table newTB = DB.createTable("new_table", new String[]{"Column"}, new String[]{"String"});
            Map<String, Database.Table> tables = DB.getTBList();
            assertTrue(tables.size() == 2
                    && tables.get("Student") != null && tables.get("new_table") != null);
        } catch (Exception e) {
            fail("failed to create a table");
        }
    }

    @Test
    void createTableWithNullTableName() {
        try {
            Database.Table newTB = DB.createTable(null, new String[]{"Column"}, new String[]{"String"});
            // This line is unreachable if the test can be passed
            fail("cannot handle null table name");
        } catch (Exception e1) {
            Map<String, Database.Table> tables = DB.getTBList();
            assertTrue(tables.size() == 1 && tables.get("Student") != null);
        }
    }

    @Test
    void createTableWithEmptyTableName() {
        try {
            Database.Table newTB2 = DB.createTable("", new String[]{"Column"}, new String[]{"String"});
            // This line is unreachable if the test can be passed
            fail("cannot handle empty table name");
        } catch (Exception e2) {
            Map<String, Database.Table> tables = DB.getTBList();
            assertTrue(tables.size() == 1 && tables.get("Student") != null);
        }
    }

    @Test
    void createTableWithNullColumnNameArray() {
        try {
            Database.Table newTB = DB.createTable("new_table", null, new String[]{"String"});
            // This line is unreachable if the test can be passed
            fail("cannot handle null column name array");
        } catch (Exception e) {
            Map<String, Database.Table> tables = DB.getTBList();
            assertTrue(tables.size() == 1 && tables.get("Student") != null);
        }
    }

    @Test
    void createTableWithNullColumnName() {
        try {
            Database.Table newTB2 = DB.createTable("new_table", new String[]{null}, new String[]{"String"});
            // This line is unreachable if the test can be passed
            fail("cannot handle null column name");
        } catch (Exception e) {
            Map<String, Database.Table> tables = DB.getTBList();
            assertTrue(tables.size() == 1 && tables.get("Student") != null);
        }
    }

    @Test
    void createTableWithEmptyColumnNameArray() {
        try {
            Database.Table newTB = DB.createTable("new_table", new String[]{}, new String[]{"String"});
            // This line is unreachable if the test can be passed
            fail("cannot handle empty column name array");
        } catch (Exception e) {
            Map<String, Database.Table> tables = DB.getTBList();
            assertTrue(tables.size() == 1 && tables.get("Student") != null);
        }
    }

    @Test
    void createTableWithEmptyColumnName() {
        try {
            Database.Table newTB2 = DB.createTable("new_table", new String[]{""}, new String[]{"String"});
            // This line is unreachable if the test can be passed
            fail("cannot handle empty column name");
        } catch (Exception e) {
            Map<String, Database.Table> tables = DB.getTBList();
            assertTrue(tables.size() == 1 && tables.get("Student") != null);
        }
    }

    @Test
    void createTableWithNullTypeNameArray() {
        try {
            Database.Table newTB = DB.createTable("new_table", new String[]{"Column"}, null);
            // This line is unreachable if the test can be passed
            fail("cannot handle null type name array");
        } catch (Exception e) {
            Map<String, Database.Table> tables = DB.getTBList();
            assertTrue(tables.size() == 1 && tables.get("Student") != null);
        }
    }

    @Test
    void createTableWithNullTypeName() {
        try {
            Database.Table newTB2 = DB.createTable("new_table", new String[]{"Column"}, new String[]{null});
            // This line is unreachable if the test can be passed
            fail("cannot handle null type name");
        } catch (Exception e) {
            Map<String, Database.Table> tables = DB.getTBList();
            assertTrue(tables.size() == 1 && tables.get("Student") != null);
        }
    }

    @Test
    void createTableWithEmptyTypeNameArray() {
        try {
            Database.Table newTB = DB.createTable("new_table", new String[]{"Column"}, new String[]{});
            // This line is unreachable if the test can be passed
            fail("cannot handle empty type name array");
        } catch (Exception e) {
            Map<String, Database.Table> tables = DB.getTBList();
            assertTrue(tables.size() == 1 && tables.get("Student") != null);
        }
    }

    @Test
    void createTableWithEmptyTypeName() {
        try {
            Database.Table newTB2 = DB.createTable("new_table", new String[]{"Column"}, new String[]{""});
            // This line is unreachable if test can be passed
            fail("cannot handle empty type name");
        } catch (Exception e) {
            Map<String, Database.Table> tables = DB.getTBList();
            assertTrue(tables.size() == 1 && tables.get("Student") != null);
        }
    }

    @Test
    void createTableWithInvalidTypeName() {
        try {
            Database.Table newTB2 = DB.createTable("new_table", new String[]{"Column"}, new String[]{"InvalidTypeName"});
            // This line is unreachable if test can be passed
            fail("cannot handle invalid type name");
        } catch (Exception e) {
            Map<String, Database.Table> tables = DB.getTBList();
            assertTrue(tables.size() == 1 && tables.get("Student") != null);
        }
    }

    @Test
    void createDuplicateTable() {
        try {
            Database.Table newTB = DB.createTable("Student", new String[]{"Column"}, new String[]{"String"});
            // This line is unreachable if test can be passed
            fail("cannot handle duplicate table name");
        } catch (Exception e) {
            Map<String, Database.Table> tables = DB.getTBList();
            assertTrue(tables.size() == 1 && tables.get("Student") != null);
        }
    }

    @Test
    void createTableWithDuplicateColumnNames() {
        try {
            Database.Table newTB = DB.createTable("new_table", new String[]{"Column1", "Column1"}, new String[]{"String","String"});
            // This line is unreachable if test can be passed
            fail("cannot handle duplicate column name");
        } catch (Exception e) {
            Map<String, Database.Table> tables = DB.getTBList();
            assertTrue(tables.size() == 1 && tables.get("Student") != null);
        }
    }

    @Test
    void deleteTable() {
        try {
            DB.deleteTable("Student");
            Map<String, Database.Table> tables = DB.getTBList();
            assertTrue(tables.isEmpty());
        } catch (Exception e) {
            fail("cannot delete a existent table");
        }
    }

    @Test
    void deleteNonExistentDatabase() {
        try {
            DB.deleteTable("nonexsistent_table");
            // This line is unreachable if test can be passed
            fail("cannot handle deleting a non-existent table");
        } catch (Exception e) {
            Map<String, Database.Table> tables = DB.getTBList();
            assertTrue(tables.size() == 1 && tables.get("Student") != null);
        }
    }

    @Test
    void getTable() {
        assertEquals(this.TB, DB.getTBList().get("Student"));
    }

    /*
        These test cases test the behaviors on 'Table' level
     */
    @Test
    void insertRow() {
        Object[] newRow = new Object[]{"004", "Dave", "ME", 3.7};
        try {
            this.TB.insertRow(newRow);
            Map<Object, Map<String, Object>> table = this.TB.getTable();
            assertTrue(table.size() == 4 && table.containsKey("004"));
        } catch (Exception e) {
            fail("cannot handle insertRow() of normal cases");
        }
    }

    @Test
    void insertRowWithInvalidType() {
        Object[] newRow = new Object[]{"004", "Dave", "ME", "Invalid Type"};
        try {
            this.TB.insertRow(newRow);
        } catch (Exception e) {
            Map<Object, Map<String, Object>> table = this.TB.getTable();
            assertTrue(table.size() == 3 && table.get("004") == null);
        }
    }

    @Test
    void insertRowWithNullValueArray() {
        try {
            this.TB.insertRow(null);
        } catch (Exception e) {
            Map<Object, Map<String, Object>> table = this.TB.getTable();
            assertEquals(3, table.size());
        }
    }

    @Test
    void insertRowWithNullValue() {
        Object[] newRow = new Object[]{"004", "Dave", "ME", null};
        try {
            this.TB.insertRow(newRow);
        } catch (Exception e) {
            Map<Object, Map<String, Object>> table = this.TB.getTable();
            assertEquals(3, table.size());
        }
    }

    @Test
    void insertRowWithEmptyValueArray() {
        Object[] newRow = new Object[]{};
        try {
            this.TB.insertRow(newRow);
        } catch (Exception e) {
            Map<Object, Map<String, Object>> table = this.TB.getTable();
            assertEquals(3, table.size());
        }
    }

    @Test
    void insertRowWithEmptyValue() {
        Object[] newRow = new Object[]{"004", "Dave", "", 3.5};
        try {
            this.TB.insertRow(newRow);
            Map<Object, Map<String, Object>> table = this.TB.getTable();
            assertTrue(table.size() == 4 && table.containsKey("004"));
        } catch (Exception e) {
            fail("cannot handle empty value");
        }
    }

    @Test
    void insertRowWithDuplicatePrimaryKey() {
        Object[] newRow = new Object[]{"001", "Dave", "", 3.5};
        try {
            this.TB.insertRow(newRow);
            // This line is unreachable if the test can be passed
            fail("cannot handle duplicate primary key");
        } catch (Exception e) {
            Map<Object, Map<String, Object>> table = this.TB.getTable();
            assertTrue(table.size() == 3 && table.get("004") == null);
        }
    }

    @Test
    void deleteRow() {
        try {
            this.TB.deleteRow("001");
        } catch (Exception e) {
            Map<Object, Map<String, Object>> table = this.TB.getTable();
            assertTrue(table.size() == 2 && table.get("001") == null);
        }
    }

    @Test
    void deleteRowWithNullKey() {
        try {
            this.TB.deleteRow(null);
            // This line is unreachable if the test can be passed
            fail("cannot handle null row key");
        } catch (Exception e) {
            Map<Object, Map<String, Object>> table = this.TB.getTable();
            assertEquals(3, table.size());
        }
    }

    @Test
    void deleteNonExistentRow() {
        try {
            this.TB.deleteRow("NonExistentKey");
            // This line is unreachable if the test can be passed
            fail("cannot handle non-existent row key");
        } catch (Exception e) {
            Map<Object, Map<String, Object>> table = this.TB.getTable();
            assertEquals(3, table.size());
        }
    }

    @Test
    void updateCell() {
        try {
            this.TB.updateCell("001", "Name", "Terry");
        } catch (Exception e) {
            assertTrue(this.TB.getTable().get("001").get("Name") == "Terry");
        }
    }

    @Test
    void updateCellWithNullRowKey() {
        Map<Object, Map<String, Object>> originalTable = this.TB.deepCopy();
        try {
            this.TB.updateCell(null, "Name", "Terry");
            // This line is unreachable if the test can be passed
            fail("cannot handle null row key");
        } catch (Exception e) {
            assertEquals(originalTable, this.TB.getTable());
        }
    }

    @Test
    void updateCellWithNonExistentRowKey() {
        Map<Object, Map<String, Object>> originalTable = this.TB.deepCopy();
        try {
            this.TB.updateCell("10000", "Name", "Terry");
            // This line is unreachable if the test can be passed
            fail("cannot handle non-existent row key");
        } catch (Exception e) {
            assertEquals(originalTable, this.TB.getTable());
        }
    }

    @Test
    void updateCellWithNullColumnKey() {
        Map<Object, Map<String, Object>> originalTable = this.TB.deepCopy();
        try {
            this.TB.updateCell("001", null, "Terry");
            // This line is unreachable if the test can be passed
            fail("cannot handle null column key");
        } catch (Exception e) {
            assertEquals(originalTable, this.TB.getTable());
        }
    }

    @Test
    void updateCellWithNonExistentColumnKey() {
        Map<Object, Map<String, Object>> originalTable = this.TB.deepCopy();
        try {
            this.TB.updateCell("001", "NonExistentColumn", "Terry");
            // This line is unreachable if the test can be passed
            fail("cannot handle null row key");
        } catch (Exception e) {
            assertEquals(originalTable, this.TB.getTable());
        }
    }

    @Test
    void updateCellWithInvalidDataType() {
        Map<Object, Map<String, Object>> originalTable = this.TB.deepCopy();
        try {
            this.TB.updateCell("001", "Name", 123);
            // This line is unreachable if the test can be passed
            fail("cannot handle invalid data type");
        } catch (Exception e) {
            assertEquals(originalTable, this.TB.getTable());
        }
    }

    @Test
    void getRows() {
        List<List<Object>> expected = Arrays.asList(
                Arrays.asList("001", "Alice", "EE", 3.8),
                Arrays.asList("002", "Bob", "EE", 3.7),
                Arrays.asList("003", "Cathy", "CS", 4.0)
        );
        try {
            List<List<Object>> rows = this.TB.getRows();
            assertEquals(expected, rows);
        } catch (Exception e) {
            fail("Cannot output all rows");
        }
    }

    @Test
    void getRowsWithLimit() {
        List<List<Object>> expected = Arrays.asList(
                Arrays.asList("001", "Alice", "EE", 3.8),
                Arrays.asList("002", "Bob", "EE", 3.7)
        );
        try {
            List<List<Object>> rows = this.TB.getRows(2);
            assertEquals(expected, rows);
        } catch (Exception e) {
            fail("Cannot output a given number rows");
        }
    }

    @Test
    void orderBy() {
        List<List<Object>> expected = Arrays.asList(
                Arrays.asList("002", "Bob", "EE", 3.7),
                Arrays.asList("001", "Alice", "EE", 3.8),
                Arrays.asList("003", "Cathy", "CS", 4.0)
        );
        try {
            List<List<Object>> rows = this.TB.orderBy("GPA");
            assertEquals(expected, rows);
        } catch (Exception e) {
            fail("Cannot sorted rows according to a given column");
        }
    }

    @Test
    void orderByWithNullColumnName() {
        List<List<Object>> rows = null;
        try {
            rows = this.TB.orderBy(null);
            fail("cannot handle null column name");
        } catch (Exception e) {
            assertEquals(null, rows);
        }
    }

    @Test
    void orderByWithEmptyColumnName() {
        List<List<Object>> rows = null;
        try {
            rows = this.TB.orderBy("");
            fail("cannot handle empty column name");
        } catch (Exception e) {
            assertEquals(null, rows);
        }
    }

    @Test
    void orderByWithLimit() {
        List<List<Object>> expected = Arrays.asList(
                Arrays.asList("002", "Bob", "EE", 3.7),
                Arrays.asList("001", "Alice", "EE", 3.8)
        );
        try {
            List<List<Object>> rows = this.TB.orderBy("GPA", 2);
            assertEquals(expected, rows);
        } catch (Exception e) {
            fail("Cannot return a given number of rows after sorting");
        }
    }

    @Test
    void orderByWithLimitTooLarge() {
        List<List<Object>> expected = Arrays.asList(
                Arrays.asList("002", "Bob", "EE", 3.7),
                Arrays.asList("001", "Alice", "EE", 3.8),
                Arrays.asList("003", "Cathy", "CS", 4.0)
        );
        try {
            List<List<Object>> rows = this.TB.orderBy("GPA", 1000);
            assertEquals(expected, rows);
        } catch (Exception e) {
            fail("Cannot return a given number of rows after sorting");
        }
    }

    @Test
    void groupBy() {
        List<List<Object>> expected = Arrays.asList(
                Arrays.asList("EE", 2),
                Arrays.asList("CS", 1)
        );
        try {
            List<List<Object>> rows = this.TB.groupBy("Major");
            assertEquals(expected, rows);
        } catch (Exception e) {
            fail("Cannot group rows by a given column");
        }
    }

    @Test
    void groupByWithNullColumnName() {
        List<List<Object>> rows = null;
        try {
            rows = this.TB.groupBy(null);
            fail("Cannot handle null column name");
        } catch (Exception e) {
            assertEquals(null, rows);
        }
    }

    @Test
    void groupByWithEmptyColumnName() {
        List<List<Object>> rows = null;
        try {
            rows = this.TB.groupBy("");
            fail("Cannot handle null column name");
        } catch (Exception e) {
            assertEquals(null, rows);
        }
    }










}