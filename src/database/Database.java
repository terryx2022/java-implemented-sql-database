package database;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class Database {
    public final String timeOfCreation;
    public final String databaseName;
    private Map<String, Table> tables; // keeps all tables within this database

    /**
     * Constructor method: create a Map to store all tables
     */
    public Database(String DBName) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        this.timeOfCreation = dtf.format(now);
        this.databaseName = DBName;
        this.tables = new HashMap<String, Table>();
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
        if (this.tables.containsKey(TBName)) {
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
        tables.put(TBName, newTable);
        System.out.println("Successfully created table '" + TBName + "'!");
        return newTable;
    }

    /**
     * Delete a table with the given name
     * @param TBName : the table to be deleted
     */
    public Table deleteTable(String TBName) throws Exception{
        if (TBName == null || TBName.length() == 0) {
            throw new Exception("Failed to delete table: no such table");
        }
        Table removedTable = tables.remove(TBName);
        if (removedTable == null) {
            throw new Exception("Failed to delete table: no such table");
        }
        System.out.println("Successfully deleted table '" + TBName + "'!");
        return removedTable;
    }

    /**
     * Get a table instance
     * @param TBName : specify the name of the table
     * @return The table (an instance of Database)
     */
    public Table getTable(String TBName) {
        return tables.get(TBName);
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
        for (String str : this.tables.keySet()) {
            System.out.printf("'%s', created at %s\n", str, tables.get(str).timeOfCreation);
        }
        System.out.printf("Total: %d", this.tables.size());
    }


    /*
    ==============================================================
        Following APIs within Class Table are for testing only!
    ==============================================================
     */

    /**
     * Return all tables - For testing only!
     * @return a map that contains all existent tables
     */
    Map<String, Table> getTables() {
        return this.tables;
    }

    /**
     * Add a table - For testing only!
     */
    void addTB(Table newTB) {
        this.tables.put(newTB.tableName, newTB);
    }

    /**
     * Remove all tables - For testing only!
     */
    void deleteAll() {
        this.tables.clear();
    }

}
