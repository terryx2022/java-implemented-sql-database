package database;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class Table {
    public final String timeOfCreation;
    public final String tableName;
    private Map<Object, Row> rows; // keeps all existent rows
    //    private LinkedList<Object> rowList; // Acts as a hidden primary key
    private final String HIDDEN_PK = "Timestamp"; // Add a hidden primary key for each table
    private String primaryKey;
    private String[] columnNames;
    private String[] dataTypeNames;
    private Map<String, Class<?>> dataTypes; // Maps columnName and corresponding dataType

    /**
     * Constructor: create a Map of Map to be used as a table,
     * the first given column name will be taken as the primary key
     * @param tableName - name of this table
     * @param columnNames - name of each column
     * @param dataTypeNames -  data type of each column
     * @throws Exception
     */
    public Table(String tableName, String[] columnNames, String[] dataTypeNames) throws ClassNotFoundException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        this.timeOfCreation = dtf.format(now);
        this.tableName = tableName;
        this.rows = new HashMap<Object, Row>();
        this.primaryKey = columnNames[0];
        this.columnNames = columnNames;
        this.dataTypeNames = dataTypeNames;
        this.dataTypes = new HashMap<>();
        for (int i = 0; i < columnNames.length; i++) {
            if (dataTypeNames[i] == "String") {
                this.dataTypes.put(columnNames[i], Class.forName("java.lang.String"));
            } else if (dataTypeNames[i] == "Number") {
                this.dataTypes.put(columnNames[i], Class.forName("java.lang.Double"));
            }
        }
        this.dataTypes.put(this.HIDDEN_PK, Class.forName("java.time.LocalDateTime")); // Add a hidden primary key
    }

    /**
     * INSERT INTO this_table VALUES (columns..) VALUES (values..);
     * @param values - value of each column
     * @throws Exception
     */
    public void insertRow(Object[] values) throws Exception{
        if (values == null || values.length != this.columnNames.length) {
            throw new Exception("Failed to insert: values cannot be null or empty");
        }
        if (this.rows.containsKey(values[0])) {
            throw new Exception("Failed to insert: duplicate primary key");
        }

        Row newRow = new Row(this.columnNames, this.dataTypes, values, this.HIDDEN_PK);
        Map<String, Object> row = new HashMap<>();

        this.rows.put(values[0], newRow); // Create the row
    }

    /**
     * Delete the row associated with the given row key
     * @param rowKey - must be a primary key
     * @throws Exception
     */
    public Row deleteRow(Object rowKey) throws Exception{
        if (rowKey == null) {
            throw new Exception("Failed to delete row: invalid row key!");
        }
        Row deletedRow = this.rows.remove(rowKey);
        if (deletedRow == null) {
            throw new Exception("Failed to delete row: no such row!");
        }
        System.out.print("Successfully delete the row associated with '" + rowKey + "'!");
        return deletedRow;
    }

    /**
     * Update a cell with a given value
     * @param rowKey
     * @param columnName
     * @param value
     * @throws Exception
     */
    public void updateCell(Object rowKey, String columnName, Object value) throws Exception {
        if (rowKey == null || columnName == null || value == null) {
            throw new Exception("Failed to update: parameters cannot be null!");
        }
        if (columnName == this.columnNames[0] && this.rows.containsKey(value)) {
            throw new Exception("Failed to update: will result generate a duplicate primary key!");
        }
        try {
            this.rows.get(rowKey).updateColumn(columnName, this.dataTypes.get(columnName), value);
        } catch (NullPointerException e1) {
            throw new Exception("Failed to update: no such row!");
        }
    }

    /**
     * SELECT * FROM tableName;
     * Will print and return all rows
     * @return a list of rows
     */
    public List<List<Object>> getRows(){
        try {
            return orderBy(this.HIDDEN_PK);
        } catch (Exception e) {
            // Shouldn't reach this line
        }
        return null;
    }

    /**
     * SELECT * FROM tableName LIMIT count;
     * Will print and return given number of rows
     * @return a list of rows
     */
    public List<List<Object>> getRows(int count) throws Exception{
            return orderBy(this.HIDDEN_PK, count);
    }

    /**
     * SELECT * FROM tableName ORDER BY columnName;
     * Will print and return all rows sorted according to the natural order of values in columnName
     * @param columnName
     * @return a list of rows
     */
    public List<List<Object>> orderBy(String columnName) throws Exception {
        return orderBy(columnName, this.rows.size());
    }

    /**
     * SELECT * FROM tableName ORDER BY columnName LIMIT count
     * Will print and return a give number of rows sorted according to the natural order of values in columnName
     * @param columnName
     * @param count
     * @return
     */
    public List<List<Object>> orderBy(String columnName, int count) throws Exception{
        if (columnName == null || columnName.length() == 0) {
            throw new Exception("Failed to ORDER BY: column name cannot be null or empty");
        }
        if (count <= 0) {
            throw new Exception("Failed to ORDER BY: count must be positive");
        }
        List<List<Object>> output = new ArrayList<>();
        Class<?> type = this.dataTypes.get(columnName);
        if (type == null) {
            throw new Exception("Failed to ORDER BY: no such column");
        }

        PriorityQueue<Row> maxHeap = new PriorityQueue<>(
                // Comparator
                (m1, m2) -> {
                    try {
                        switch (type.getName()) {
                            case "java.lang.String":
                                String s1 = (String) type.cast(m1.getColumn(columnName));
                                String s2 = (String) type.cast(m2.getColumn(columnName));
                                return s2.compareTo(s1);
                            case "java.lang.Double":
                                Double d1 = (Double) type.cast(m1.getColumn(columnName));
                                Double d2 = (Double) type.cast(m2.getColumn(columnName));
                                return d2.compareTo(d1);
                            case "java.time.LocalDateTime":
                                System.out.println("LocalDateTime!!!!");
                                LocalDateTime t1 = (LocalDateTime) type.cast(m1.getColumn(columnName));
                                LocalDateTime t2 = (LocalDateTime) type.cast(m2.getColumn(columnName));
                                return t2.compareTo(t1);
                        }
                    } catch (Exception e) {
                        // Not reachable
                    }
                    return 0; // Not reachable
                }
        );
        for (Row row : this.rows.values()) {
            maxHeap.offer(row);
            if (maxHeap.size() > count) {
                maxHeap.poll();
            }
        }
        while (!maxHeap.isEmpty()) {
            Row currRow = maxHeap.poll();
            List<Object> nestedList = new LinkedList<>();
            for (String col : this.columnNames) {
                nestedList.add(currRow.getColumn(col));
            }
            output.add(nestedList);
            count--;
        }
        Collections.reverse(output);
        printRows(this.columnNames, output);
        return output;
    }

    /**
     * SELECT aggregate_key, COUNT(*) FROM tableName GROUP BY columnName;
     * @param aggregateKey
     * @return
     */
    public List<List<Object>> groupBy(String aggregateKey) throws Exception{
        if (aggregateKey == null || aggregateKey.length() == 0) {
            throw new Exception("Failed to GROUP BY: aggregate key cannot be null or empty");
        }
        List<List<Object>> output = new ArrayList<>();
        Map<Object, Integer> count = new HashMap<>();
        for (Map.Entry<Object, Row> entry : this.rows.entrySet()) {
            Object value = entry.getValue().getColumn(aggregateKey);
            if (value == null) {
                throw new Exception("Failed to GROUP BY: no such aggregate key");
            }
            count.put(value, count.getOrDefault(value, 0) + 1);
        }
        for (Map.Entry<Object, Integer> entry : count.entrySet()) {
            List<Object> nestedList = new ArrayList<>();
            nestedList.add(entry.getKey());
            nestedList.add(entry.getValue());
            output.add(nestedList);
        }
        printRows(new String[]{aggregateKey, "Count"}, output);
        return output;
    }


    /**
     * Print the basic information of this table
     */
    public void getTableInfo() {
        System.out.printf(
                "Table '%s': \n" +
                        "  created at: %s, \n" +
                        "  column Names: ",
                this.tableName, this.timeOfCreation
        );
        for (String colName : this.columnNames) {
            System.out.print("'" + colName + "' ");
        }

        System.out.printf("\n  column Data Types: ");
        for (String type : this.dataTypeNames) {
            System.out.print("'" + type + "' ");
        }
        System.out.printf("\n  existing rows: %d", this.rows.size());
    }

    /**
     * Print rows
     * @param columns - the columns to be printed
     * @param rows - the rows to be printed
     */
    private void printRows(String[] columns, List<List<Object>> rows) {
        if (rows == null) {
            System.out.println("No rows to be printed");
            return;
        }
        System.out.printf("columns printed: \n");
        System.out.print("| ");
        for (String col : columns) {
            System.out.print(col + " | ");
        }
        System.out.printf("\n");
        for (List<Object> row : rows) {
            for (Object obj : row) {
                System.out.print("  " + obj + "  ");
            }
            System.out.println();
        }
    }


    /*
    ==============================================================
        Following APIs within Class Table are for testing only!
    ==============================================================
     */

    /**
     * Initialize a table instance - For testing only
     * @param pk
     * @param columnNames
     * @param dataTypeNames
     * @throws Exception
     * @return data type of each column stored in a map
     */
    Map<String, Class<?>> setUpTable(String pk, String[] columnNames, String[] dataTypeNames) throws Exception {
        this.primaryKey = pk;
        this.columnNames = columnNames;
        this.dataTypeNames = dataTypeNames;
        for (int i = 0; i < columnNames.length; i++) {
            switch (dataTypeNames[i]) {
                case "String":
                    this.dataTypes.put(columnNames[i], Class.forName("java.lang.String"));
                    break;
                case "Number":
                    this.dataTypes.put(columnNames[i], Class.forName("java.lang.Double"));
                    break;
            }
        }
        return this.dataTypes;
    }

    /**
     * Add a row to table - For testing only
     * @param pk
     * @param row
     */
    void addRow(Object pk, Row row) {
        this.rows.put(pk, row);
    }

    /**
     * Get all original data from table - For testing only!
     * @return
     */
    Map<Object, Row> getAllRows() {
        return this.rows;
    }

    /**
     * Reset table - For testing only
     */
    void deleteAll() {
        this.rows.clear();
    }

}
