package database;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Row {
    private Map<String, Object> row; // stores pairs of (rowkey, value)

    /**
     * Constructor
     * @param columnNames
     * @param types
     * @param values
     * @param hiddenColumn
     * @throws Exception
     */
    public Row(String[] columnNames, Map<String, Class<?>> types, Object[] values, String hiddenColumn) throws Exception {
        this.row = new HashMap<>();
        for(int i = 0; i < columnNames.length; i++) {
            if (values[i] == null) {
                throw new Exception("Failed to create a new row: values cannot be null!");
            }
            // Impose type check
            if (!types.get(columnNames[i]).isInstance(values[i])) {
                throw new Exception("Failed to create a new row: data type not acceptable!");
            }
            this.row.put(columnNames[i], values[i]);
        }
        this.row.put(hiddenColumn, LocalDateTime.now());
    }

    /**
     * Get the value of a cell
     * @param columnName
     * @return
     * @throws Exception
     */
    Object getColumn(Object columnName) throws Exception {
        Object value = this.row.get(columnName);
        if (value == null) {
            throw new Exception("Failed to create a new row: no such column!");
        }
        return value;
    }

    /**
     * Update the value in a cell
     * @param columnName
     * @param newValue
     * @throws Exception
     */
    void updateColumn(String columnName, Class<?> type, Object newValue) throws Exception {
        if (!this.row.containsKey(columnName)) {
            throw new Exception("Failed to update: no such column!");
        }
        if (!type.isInstance(newValue)) {
            throw new Exception("Failed to update: data type doesn't match!");
        }
        this.row.put(columnName, newValue);
    }
}
