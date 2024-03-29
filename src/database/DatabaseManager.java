package database;

import javax.xml.crypto.Data;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;


public class DatabaseManager {
    private Map<String, Database> databases; // keeps all existing databases

    /**
     * Constructor method: create a Map to store all databases
     */
    public DatabaseManager() {
        databases = new HashMap<String, Database>();
    }

    /**
     * Create a database with the given name
     * @param DBName : the database to be created
     */
    public Database createDatabase(String DBName) throws Exception{
        if (DBName == null || DBName.length() == 0) {
            throw new Exception("Failed to create database: invalid database name");
        }
        if (databases.containsKey(DBName)) {
            throw new Exception("Failed to create database: database name in use");
        }
        Database newDB = new Database(DBName);
        databases.put(DBName, newDB);
        System.out.println("Successfully created database '" + DBName + "'!");
        return newDB;
    }

    /**
     * Delete a database with the given name
     * @param DBName : the database to be deleted
     * @return the database removed
     */
    public Database deleteDatabase(String DBName) throws Exception{
        Database removedDB = databases.remove(DBName);
        if (removedDB == null) {
            throw new Exception("Failed to delete database: no such database!");
        } else {
            System.out.println("Successfully deleted database '" + DBName + "'!");
        }
        return removedDB;
    }

    /**
     * Get a database instance
     * @param DBName : specify the name of the database
     * @return The database (an instance of Database)
     */
    public Database getDatabase(String DBName) {
        return databases.get(DBName);
    }

    /**
     * Print the basic information of this database
     */
    public void printDatabaseManagerInfo() {
        System.out.println("Databases: ");
        for (String str : this.databases.keySet()) {
            System.out.printf("'%s', created at %s\n", str, databases.get(str).timeOfCreation);
        }
        System.out.printf("Total: %d", this.databases.size());
    }

    /*
    ==============================================================
        Following APIs within Class Table are for testing only!
    ==============================================================
     */

    /**
     * Return all databases - For testing only!
     * @return a map containing all existent databases
     */
    Map<String, Database> getDatabases() {
        return this.databases;
    }

    /**
     * Add a database - For testing only!
     */
    void addDB(Database newDB) {
        this.databases.put(newDB.databaseName, newDB);
    }

    /**
     * Remove all databases - For testing only!
     */
    void deleteAll() {
        this.databases.clear();
    }

}
