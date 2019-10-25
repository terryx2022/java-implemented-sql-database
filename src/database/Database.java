package database;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Database {
    public static class Table {
        public final String timeOfCreation;
        public final String tableName;
        private Map<Object, Map<String, Object>> table; // keeps data within a table
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
            timeOfCreation = dtf.format(now);
            this.tableName = tableName;
            this.table = new HashMap<Object, Map<String, Object>>();
            this.primaryKey = columnNames[0];
            this.columnNames = columnNames.clone();
            this.dataTypeNames = dataTypeNames.clone();
            this.dataTypes = new HashMap<>();
            for (int i = 0; i < columnNames.length; i++) {
                if (dataTypeNames[i] == "String") {
                    this.dataTypes.put(columnNames[i], Class.forName("java.lang.String"));
                } else if (dataTypeNames[i] == "Number") {
                    this.dataTypes.put(columnNames[i], Class.forName("java.lang.Double"));
                }
            }
            this.dataTypes.put(this.HIDDEN_PK, Class.forName("java.lang.Long")); // Add a hidden primary key
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
            if (this.table.containsKey(values[0])) {
                throw new Exception("Failed to insert: duplicate primary key");
            }

            Map<String, Object> row = new HashMap<>();
            for (int i = 0; i < values.length; i++) {
                if (values[i] == null) {
                    throw new Exception("Failed to insert: values cannot be null or empty");
                }
                this.dataTypes.get(this.columnNames[i]).cast(values[i]); // Enforce type check
                row.put(this.columnNames[i], values[i]);
            }
            row.put(this.HIDDEN_PK, System.currentTimeMillis());
            this.table.put(values[0], row); // Create the row
        }

        /**
         * Delete the row associated with the given row key
         * @param rowKey - must be a primary key
         * @throws Exception
         */
        public void deleteRow(Object rowKey) throws Exception{
            if (rowKey == null) {
                throw new Exception("Failed to delete row: invalid row key!");
            }
            Map<String, Object> deletedRow = this.table.remove(rowKey);
            if (deletedRow == null) {
                throw new Exception("Failed to delete row: no such row!");
            }
            System.out.print("Successfully delete the row associated with '" + rowKey + "'!");
        }

        /**
         * Update a cell with a given value
         * @param rowKey
         * @param columnName
         * @param value
         * @throws Exception
         */
        public void updateCell(Object rowKey, String columnName, Object value) throws Exception {
            if (rowKey == null || columnName == null || value == null ) {
                throw new Exception("Failed to update: parameters cannot be null!");
            }
            Map<String, Object> row = this.table.get(rowKey);
            if (row == null || !row.containsKey(columnName)) {
                throw new Exception("Failed to delete row: no such cell (either row or column non-existent!");
            }

            this.dataTypes.get(columnName).cast(value); // Enforce type check

            row.put(columnName, value);
            System.out.print("Successfully updated the entry at row '" + rowKey + "' and column '" + columnName + "'!");
        }

        /**
         * SELECT * FROM tableName;
         * Will print and return all rows
         * @return a list of rows
         */
        public List<List<Object>> getRows() throws Exception{
            return getRows(this.table.size());
//            return orderBy(this.HIDDEN_PK);
        }

        /**
         * SELECT * FROM tableName LIMIT count;
         * Will print and return given number of rows
         * @return a list of rows
         */
        public List<List<Object>> getRows(int count) throws Exception{
            List<List<Object>> output = new ArrayList<>();
            Iterator<Map.Entry<Object, Map<String, Object>>> iterator = this.table.entrySet().iterator();
            while (count > 0 && iterator.hasNext()) {
                List<Object> nestedLIst = new ArrayList<>();
                Map<String, Object> currRow = iterator.next().getValue();
                for (String col : this.columnNames) {
                    nestedLIst.add(currRow.get(col));
                }
                output.add(nestedLIst);
                count--;
            }
            printRows(this.columnNames, output);
            return output;
//            return orderBy(this.HIDDEN_PK, count);
        }

        /**
         * SELECT * FROM tableName ORDER BY columnName;
         * Will print and return all rows sorted according to the natural order of values in columnName
         * @param columnName
         * @return a list of rows
         */
        // TODO
        public List<List<Object>> orderBy(String columnName) throws Exception {
            return orderBy(columnName, table.size());
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
            List<List<Object>> output = new ArrayList<>();
            Class<?> type = this.dataTypes.get(columnName);
            if (type == null) {
                throw new Exception("Failed to ORDER BY: no such column");
            }
            PriorityQueue<Map<String, Object>> pq = new PriorityQueue<Map<String, Object>>(
                    (m1, m2) -> {
                        switch (type.getName()) {
                            case "java.lang.String":
                                String s1 = (String) type.cast(m1.get(columnName));
                                String s2 = (String) type.cast(m2.get(columnName));
                                return s1.compareTo(s2);
                            case "java.lang.Double":
                                Double d1 = (Double) type.cast(m1.get(columnName));
                                Double d2 = (Double) type.cast(m2.get(columnName));
                                return d1.compareTo(d2);
                            case "java.lang.long":
                                Long l1 = (Long) type.cast(m1.get(columnName));
                                Long l2 = (Long) type.cast(m2.get(columnName));
                                return l1.compareTo(l2);
                        }
                        return 0; // Not reachable
                    }
            );
            for (Map<String, Object> map : this.table.values()) {
                pq.offer(map);
            }
            while (!pq.isEmpty() && count > 0) {
                Map<String, Object> currRow = pq.poll();
                List<Object> nestedList = new LinkedList<>();
                for (String col : this.columnNames) {
                    nestedList.add(currRow.get(col));
                }
                output.add(nestedList);
                count--;
            }
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
            for (Map.Entry<Object, Map<String, Object>> entry : this.table.entrySet()) {
                Object value = entry.getValue().get(aggregateKey);
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
            System.out.printf("\n  existing rows: %d", this.table.size());
        }


        /* -- Following APIs within Class Table are for testing only! -- */

        void addRow(Object pk, Map<String, Object> row) {
            this.table.put(pk, row);
        }

        /**
         * Set the primary key for the table - For testing only!
         * @param pk
         */
        void setPrimaryKey(String pk) {
            this.primaryKey = pk;
        }

        /**
         * Set name of each column - For testing only!
         * @param columns
         */
         void setColumnNames(String[] columns) {
            this.columnNames = columns.clone();
        }

        /**
         * Set data type name of each column - For testing only!
         * @param column
         */
        void setDataTypeNames(String[] column) {
            this.dataTypeNames = column.clone();
        }

        /**
         * Set data type of each column - For testing only!
         * @param types
         */
        void setDataTypes(Class<?>[] types) {
            for (int i = 0; i < this.columnNames.length; i++) {
                this.dataTypes.put(columnNames[i], types[i]);
            }
        }

        /**
         * Get all original data from table - For testing only!
         * @return
         */
        Map<Object, Map<String, Object>> getTable() {
            return this.table;
        }

        /**
         * Deep copy a table - For testing only!
         * @param
         * @return
         */
        Map<Object, Map<String, Object>> deepCopy() {
            Map<Object, Map<String, Object>> copy = new HashMap<Object, Map<String, Object>>();
            for (Map.Entry<Object, Map<String, Object>> entry : this.table.entrySet()) {
                Map<String, Object> row = entry.getValue();
                Map<String, Object> rowCopy = new HashMap<>();
                for (Map.Entry<String, Object> cell : row.entrySet()) {
                    rowCopy.put(cell.getKey(), cell.getValue());
                }
                copy.put(entry.getKey(), rowCopy);
            }
            return copy;
        }

    }
    public final String timeOfCreation;
    public final String databaseName;
    private Map<String, Table> TBList; // keeps all tables within this database

    /**
     * Constructor method: create a Map to store all tables
     */
    public Database(String DBName) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        timeOfCreation = dtf.format(now);
        this.databaseName = DBName;
        TBList = new HashMap<String, Table>();
    }

    /**
     * Create a table with the given names and data types
     * the first given column name will be taken as the primary key
     * @param TBName : the table to be created
     * @param dataTypeNames : data types for each column
     */
    public Table createTable(String TBName, String[] columnNames, String[] dataTypeNames) throws Exception {
        if (TBName == null || TBName.length() == 0 ||
                columnNames == null || columnNames.length ==0 ||
                dataTypeNames == null || dataTypeNames.length == 0) {
            throw new Exception("Failed to create table: parameters cannot be null or empty");
        }
        if (this.TBList.containsKey(TBName)) {
            throw new Exception("Failed to create table - table name is in use!");
        }
        if (columnNames.length != dataTypeNames.length) {
            throw new Exception("Failed to create table: the numbers of column names and data types must be equal!");
        }
        Set<String> duplicateCheck = new HashSet<>();
        for (String str : columnNames) {
            if (duplicateCheck.contains(str)) {
                throw new Exception("Failed to creat table: column contains duplicate names");
            }
            duplicateCheck.add(str);
        }
        for (int i = 0; i < columnNames.length; i++) {
            if (columnNames[i] == null || columnNames[i].length() == 0) {
                throw new Exception("Failed to create table: column names cannot be null or empty");
            }
            if (dataTypeNames[i] != "String" && dataTypeNames[i] != "Number") {
                throw new Exception("Failed to create table: data type names can only be String or Number");
            }
        }
        Table newTable = new Table(TBName, columnNames, dataTypeNames);
        TBList.put(TBName, newTable);
        System.out.println("Successfully created table '" + TBName + "'!");
        return newTable;
    }

    /**
     * Delete a table with the given name
     * @param TBName : the table to be deleted
     */
    public void deleteTable(String TBName) throws Exception{
        if (TBName == null || TBName.length() == 0 || TBList.remove(TBName) == null) {
            throw new Exception("Failed to delete table: no such table");
        }
        System.out.println("Successfully deleted table '" + TBName + "'!");
    }

    /**
     * Get a table instance
     * @param TBName : specify the name of the table
     * @return The table (an instance of Database)
     */
    public Table getTable(String TBName) {
        return TBList.get(TBName);
    }

    /**
     * Print the basic information of this database
     */
    public void getDatabaseInfo() {
        System.out.printf(
                "Database '%s': \n" +
                        "  created at: %s, \n" +
                        "  Tables: \n",
                this.databaseName, this.timeOfCreation
        );
        for (String str : this.TBList.keySet()) {
            System.out.printf("'%s', created at %s\n", str, TBList.get(str).timeOfCreation);
        }
        System.out.printf("Total: %d", this.TBList.size());
    }

    /**
     * Return all tables - For testing only!
     * @return a map that contains all existent tables
     */
    Map<String, Table> getTBList() {
        return this.TBList;
    }

    /**
     * Add a table - For testing only!
     */
    void addTB(Table newTB) {
        this.TBList.put(newTB.tableName, newTB);
    }

    /**
     * Remove all tables - For testing only!
     */
    void deleteAll() {
        this.TBList.clear();
    }

}
