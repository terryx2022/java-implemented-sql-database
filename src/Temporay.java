import database.Database;
import database.DatabaseManager;
import database.Table;


import java.util.List;

public class Temporay {
    public static void main(String[] args) throws  Exception{
        DatabaseManager dbManager = new DatabaseManager();
        Database db = dbManager.createDatabase("USC");
        String[] columns = new String[]{"ID", "Name", "Major", "GPA"};
        String[] types = new String[]{"String", "String", "String", "Number"};

        Table table = db.createTable("Students", columns, types);

        Object[] values1 = new Object[]{"001", "Alice", "EE", 3.9};
        Object[] values2 = new Object[]{"002", "Bob", "EE", 3.6};
        Object[] values3 = new Object[]{"003", "Cathy", "EE", 3.0};
        Object[] values4 = new Object[]{"004", "Dave", "CS", 3.1};

        table.insertRow(values1);
        Thread.sleep(1);
        table.insertRow(values2);
        Thread.sleep(1);
        table.insertRow(values3);
        Thread.sleep(1);
        table.insertRow(values4);


        List<List<Object>> all = table.orderBy("Timestamp");

        System.out.println("done");

//        table.getTableInfo();
//
//        List<List<Object>> all2 = table.getRows(2);
//
//
//        table.orderBy("GPA", 2);
//
//        table.groupBy("Major");



    }
}
