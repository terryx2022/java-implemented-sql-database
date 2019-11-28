//package database;
//
//import java.util.*;
//
//
//class Table {
//    private Map<Object, Map<String, Object>> table; // keeps data within a table
//    private String primaryKey;
//    private String[] columnNames;
//    private String[] dataTypes;
//
//    /**
//     * Constructor: create a Map of Map to be used as a table,
//     * the first given column name will be taken as the primary key
//     * @param columnNames - name of each column
//     * @param dataTypes -  data type of each column
//     */
//    public Table(String[] columnNames, String[] dataTypes) {
//        table = new HashMap<Object, Map<String, Object>>();
//        this.primaryKey = columnNames[0];
//        this.columnNames = columnNames.clone();
//        this.dataTypes = dataTypes.clone();
//    }
//
//    /**
//     * @return the data type of each column
//     */
//    public String[] getDataTypes() {
//        return dataTypes;
//    }
//
//    /* --- The following methods provide implementation for database operations --- */
//
//    /**
//     * Insert a row into table
//     * @param values - value of each column
//     */
//    public void insert(Object[] values) {
//        Map<String, Object> row = new HashMap<>();
//        for (int i = 0; i < values.length; i++) {
//            row.put(columnNames[i], values[i]);
//        }
//        table.put(values[0], row);
//    }
//
//    /**
//     * SELECT * FROM this_table
//     * @return
//     */
//    public List<List<Object>> select() {
//        List<List<Object>> output = new ArrayList<>();
//        for (Map<String, Object> row : table.values()) {
//            List<Object> curRow = new ArrayList<>();
//            for (Object value : row.values()) {
//                curRow.add(value);
//            }
//            output.add(curRow);
//        }
//        return output;
//    }
//}
//
//
//class Database {
//    private Map<String, Table> TBList; // keeps all tables within this database
//
//    /**
//     * Constructor method: create a Map to store all tables
//     */
//    public Database() {
//        TBList = new HashMap<String, Table>();
//    }
//
//    /**
//     * Create a table with the given names and data types
//     * the first given column name will be taken as the primary key
//     * @param TBName : the table to be created
//     * @param dataTypes : data types for each column
//     */
//    public Table createTable(String TBName, String[] columnNames, String[] dataTypes) {
//        if (TBList.containsKey(TBName)) {
//            System.out.println("Failed to create table '" + TBName + "' : already exists!");
//            return null;
//        }
//        Table newTable = new Table(columnNames, dataTypes);
//        TBList.put(TBName, newTable);
//        System.out.println("Successfully created table '" + TBName + "'!");
//        return newTable;
//    }
//
//
//
//
//    /**
//     * Delete a table with the given name
//     * @param TBName : the table to be deleted
//     */
//    public void deleteTable(String TBName) {
//        if (TBList.remove(TBName) == null) {
//            System.out.println("Failed to delete table '" + TBName + "' : no such table!");
//        } else {
//            System.out.println("Successfully deleted table '" + TBName + "'!");
//        }
//    }
//
//    /**
//     * Get a table instance
//     * @param TBName : specify the name of the table
//     * @return The table (an instance of Database)
//     */
//    public Table getTable(String TBName) {
//        return TBList.get(TBName);
//    }
//
//    /**
//     * Get a set of strings of all existing tables
//     * @return a set of table names
//     */
//    public List<String> listAllTables() {
//        List<String> TBs = new ArrayList<>();
//        for (String str : TBList.keySet()) {
//            TBs.add(str);
//        }
//        return TBs;
//    }
//
//}
//
//
//public class DatabaseManager_backup {
//    private Map<String, Database> DBList; // keeps all existing databases
//
//    /**
//     * Constructor method: create a Map to store all databases
//     */
//    public DatabaseManager_backup() {
//        DBList = new HashMap<String, Database>();
//    }
//
//    /**
//     * Create a database with the given name
//     * @param DBName : the database to be created
//     */
//    public Database createDatabase(String DBName) {
//        if (DBList.containsKey(DBName)) {
//            System.out.println("Failed to create database '" + DBName + "' : already exists!");
//            return null;
//        }
//        Database newDB = new Database();
//        DBList.put(DBName, newDB);
//        System.out.println("Successfully created database '" + DBName + "'!");
//        return newDB;
//    }
//
//    /**
//     * Delete a database with the given name
//     * @param DBName : the database to be deleted
//     */
//    public void deleteDatabase(String DBName) {
//        if (DBList.remove(DBName) == null) {
//            System.out.println("Failed to delete database '" + DBName + "' : no such database!");
//        } else {
//            System.out.println("Successfully deleted database '" + DBName + "'!");
//        }
//    }
//
//    /**
//     * Get a database instance
//     * @param DBName : specify the name of the database
//     * @return The database (an instance of Database)
//     */
//    public Database getDatabase(String DBName) {
//        return DBList.get(DBName);
//    }
//
//    /**
//     * Get a set of strings of all existing databases
//     * @return a set of database names
//     */
//    public List<String> listAllDatabases() {
//        List<String> DBs = new ArrayList<>();
//        for (String str : DBList.keySet()) {
//            DBs.add(str);
//        }
//        return DBs;
//    }
//}
