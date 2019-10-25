import database.Database;
import database.DatabaseManager;


import java.util.List;

public class Temporay {
    public static void main(String[] args) throws  Exception{
        DatabaseManager dbmaganer = new DatabaseManager();
        Database db = dbmaganer.createDatabase("USC");
        String[] columns = new String[]{"ID", "Name", "Major", "GPA"};
        String[] types = new String[]{"String", "String", "String", "Number"};

        Database.Table table = db.createTable("Students", columns, types);

        Object[] values1 = new Object[]{"001", "Alice", "EE", 3.9};
        Object[] values2 = new Object[]{"002", "Alice", "EE", 3.6};
        Object[] values3 = new Object[]{"003", "Alice", "EE", 3.0};
        Object[] values4 = new Object[]{"004", "Alice", "CS", 3.1};

        table.insertRow(values1);
        table.insertRow(values2);
        table.insertRow(values3);
        table.insertRow(values4);

        List<List<Object>> all = table.getRows();

        table.getTableInfo();

        List<List<Object>> all2 = table.getRows(2);


        table.orderBy("GPA", 2);

        table.groupBy("Major");



    }
}
