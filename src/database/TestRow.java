package database;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.util.*;

public class TestRow {
    Row row;

    @BeforeEach
    void setUp() {
        String[] columnNames = new String[]{"ID", "Name", "Major", "GPA"};
        String[] typeNames = new String[]{"String", "String", "String", "Number"};
        Map<String, Class<?>> types = new HashMap<>();
        try {
            types.put("ID", Class.forName("java.lang.String"));
            types.put("Name", Class.forName("java.lang.String"));
            types.put("Major", Class.forName("java.lang.String"));
            types.put("GPA", Class.forName("java.lang.Double"));
        } catch (Exception e) {
            // Unreachable
        }
        Object[] values = new Object[]{"001", "Alice", "CS", 3.9};
        String hiddenkey = "Timestamp";
        try {
            row = new Row(columnNames, types, values, hiddenkey);
        } catch (Exception e) {
            // Unreachable
        }
    }

    @AfterEach
    void tearDown() {
        row.getInternalMap().clear();
    }

    @Test
    void getColumn() {
        try {
            Object actual = this.row.getColumn("ID");
            assertEquals("001", actual);
        } catch (Exception e) {
            fail("cannot handel valid input");
        }
    }

    @Test
    void updateColumn() {
        try {
            this.row.updateColumn("GPA", Class.forName("java.lang.Double"), 3.8);
            assertEquals(3.8, this.row.getInternalMap().get("GPA"));
        } catch (Exception e) {
            fail("cannot handel valid input");
        }
    }

    /* Corner cases of invalid input parameters to be added */


}
