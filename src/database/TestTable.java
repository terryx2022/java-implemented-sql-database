package database;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import javax.naming.Name;
import java.util.*;


class TestTable {
    Table TB;

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

        } catch (Exception e) {
            // This line is not reachable with the above input parameters
        }
    }

    @AfterEach
    void tearDown() {
        TB.deleteAll();
    }

    /*
        These test cases test the behaviors on 'Table' level
     */

    @Test
    void insertRow() {
        Object[] newRow = new Object[]{"004", "Dave", "ME", 3.7};
        try {
            this.TB.insertRow(newRow);
            Map<Object, Row> rows = this.TB.getAllRows();
            assertTrue(rows.size() == 4 && rows.containsKey("004"));
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
            Map<Object, Row> rows = this.TB.getAllRows();
            assertTrue(rows.size() == 3 && rows.get("004") == null);
        }
    }

    @Test
    void insertRowWithNullValueArray() {
        try {
            this.TB.insertRow(null);
        } catch (Exception e) {
            Map<Object, Row> rows = this.TB.getAllRows();
            assertEquals(3, rows.size());
        }
    }

    @Test
    void insertRowWithNullValue() {
        Object[] newRow = new Object[]{"004", "Dave", "ME", null};
        try {
            this.TB.insertRow(newRow);
        } catch (Exception e) {
            Map<Object, Row> rows = this.TB.getAllRows();
            assertEquals(3, rows.size());
        }
    }

    @Test
    void insertRowWithEmptyValueArray() {
        Object[] newRow = new Object[]{};
        try {
            this.TB.insertRow(newRow);
        } catch (Exception e) {
            Map<Object, Row> rows = this.TB.getAllRows();
            assertEquals(3, rows.size());
        }
    }

    @Test
    void insertRowWithEmptyValue() {
        Object[] newRow = new Object[]{"004", "Dave", "", 3.5};
        try {
            this.TB.insertRow(newRow);
            Map<Object, Row> rows = this.TB.getAllRows();
            assertTrue(rows.size() == 4 && rows.containsKey("004"));
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
            Map<Object, Row> rows = this.TB.getAllRows();
            assertTrue(rows.size() == 3 && rows.get("004") == null);
        }
    }

    @Test
    void deleteRow() {
        try {
            this.TB.deleteRow("001");
        } catch (Exception e) {
            Map<Object, Row> rows = this.TB.getAllRows();
            assertTrue(rows.size() == 2 && rows.get("001") == null);
        }
    }

    @Test
    void deleteRowWithNullKey() {
        try {
            this.TB.deleteRow(null);
            // This line is unreachable if the test can be passed
            fail("cannot handle null row key");
        } catch (Exception e) {
            Map<Object, Row> rows = this.TB.getAllRows();
            assertEquals(3, rows.size());
        }
    }

    @Test
    void deleteNonExistentRow() {
        try {
            this.TB.deleteRow("NonExistentKey");
            // This line is unreachable if the test can be passed
            fail("cannot handle non-existent row key");
        } catch (Exception e) {
            Map<Object, Row> rows = this.TB.getAllRows();
            assertEquals(3, rows.size());
        }
    }

    @Test
    void updateCell() {
        try {
            this.TB.updateCell("001", "Name", "Terry");
            assertTrue(this.TB.getAllRows().get("001").getColumn("Name") == "Terry");
        } catch (Exception e) {
            // Not reachable
        }
    }

    @Test
    void updateCellWithDuplicatePrimaryKey() {
        try {
            this.TB.updateCell("001", "ID", "002");
            // This line is unreachable if the test can be passed
            fail("cannot handle duplicate primary key");
        } catch (Exception e) {
            // Passed
        }
    }

    @Test
    void updateCellWithNullRowKey() {
        try {
            this.TB.updateCell(null, "Name", "Terry");
            // This line is unreachable if the test can be passed
            fail("cannot handle null row key");
        } catch (Exception e) {
            // Passed
        }
    }

    @Test
    void updateCellWithNonExistentRowKey() {
        try {
            this.TB.updateCell("10000", "Name", "Terry");
            // This line is unreachable if the test can be passed
            fail("cannot handle non-existent row key");
        } catch (Exception e) {
            // Passed
        }
    }

    @Test
    void updateCellWithNullColumnKey() {
        try {
            this.TB.updateCell("001", null, "Terry");
            // This line is unreachable if the test can be passed
            fail("cannot handle null column key");
        } catch (Exception e) {
            // Passed
        }
    }

    @Test
    void updateCellWithNonExistentColumnKey() {
        try {
            this.TB.updateCell("001", "NonExistentColumn", "Terry");
            // This line is unreachable if the test can be passed
            fail("cannot handle null row key");
        } catch (Exception e) {
            // Passed
        }
    }

    @Test
    void updateCellWithInvalidDataType() {
        try {
            this.TB.updateCell("001", "Name", 123);
            // This line is unreachable if the test can be passed
            fail("cannot handle invalid data type");
        } catch (Exception e) {
            // Passed
        }
    }

    @Test
    void getRows() {
        List<List<Object>> expected = Arrays.asList(
                Arrays.asList("001", "Alice", "EE", 3.8),
                Arrays.asList("003", "Cathy", "CS", 4.0),
                Arrays.asList("002", "Bob", "EE", 3.7)

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
                Arrays.asList("003", "Cathy", "CS", 4.0)
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