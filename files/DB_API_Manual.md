# API Doc

## **Class DatabaseManager**
### Constructor
****DatabaseManager*()***

### Methods
***`Database createDatabase(String DBName) throws Exception`*** <br/>
-exception will be thrown if input name is null/empty
  
***`Database deleteDatabase(String DBName) throws Exception`*** <br/>
-exception will be thrown if input name is null/empty/non-existent

***`Database getDatabase(String DBName) throws Exception`*** <br/>
-exception will be thrown if input name is null/empty/non-existent

***`void printDatabaseManagerInfo()`*** <br/>
-print database manager information to console

## **Class Database**
### **Constructor**
****`Database*(String DBName) throws Exception`*** <br/>
-exception will be thrown if input is null/empty

### **Methods**
***`Database createTable(String TBName) throws Exception`*** <br/>
-exception will be thrown if input name is null/empty
  
***`Database deleteTable(String TBName) throws Exception`*** <br/>
-exception will be thrown if input name is null/empty/non-existent

***`Database getTable(String TBName) throws Exception`*** <br/>
-exception will be thrown if input name is null/empty/non-existent

***`void getDatabaseInfo()`*** <br/>
-print database informatin to console


## **Class Table**
### **Constructor**
***`Table(String tableName, String[] columnNames, String[] dataTypeNames) throws Exception`*** <br/>
-elements in dataTypeNames can only be either "String" or "Number" (corresponding to java.lang.String and java.lang.Double) <br/>
-exception will be thrown if: null/empty tableName, null/empty columnname and invalue dataTypeNames <br/>

### **Methods**
***`void insertRow(Object[] values) throws Exception`*** <br/>
-exceptions will be thrown if values is null/empty, length of values and types of elements doesn't match table schema. <br/>

***`Row deleteRow(Object rowKey) throws Exception`*** <br/>
-exceptions will be thrown if rowkey is null/empty, or the desired row doesn't exist<br/> 

***`void updateCell(Object rowKey, String columnName, Object value) throws Exception`*** <br/>
-exceptions will be thrown if rowkey/column is null/empty, the desired row/column doesn't exist, or the value type is invalide<br/> 


***`List<List<Object>> getRows()`*** <br/>
-return every row in the order of insertion <br/>

***`List<List<Object>> getRows(int count) throws Exception`*** <br/>
-return a desired number of rows in the order of insertion<br/>
-count must be a posistive number or exception will be thrown <br/>

***`List<List<Object>> orderBy(String columnName) throws Exception`*** <br/>
-return every row in the natural order of values unber columnName<br/>
-count must be a posistive number or exception will be thrown <br/>
-column mustn't be null/empty/nonexistent or exception will be thrown <br/>

***`List<List<Object>> orderBy(String columnName, int count) throws Exception`*** <br/>
-return a desired number of rows in the natural order of values unber columnName<br/>
-count must be a posistive number or exception will be thrown <br/>
-column mustn't be null/empty/nonexistent or exception will be thrown <br/>

***`List<List<Object>> groupBy(String aggregateKey) throws Exception`*** <br/>
-Group by the given aggregate key and count occurrence<br/>
-each inner list has two elements: value under aggregateKey, occurrence
-aggregateKey mustn't be null/empty/nonexistent or exception will be thrown <br/>

***`void getTableInfo()`*** <br/>
-print table information to console<br/>

## **Class Row**
### **Constructor**
***`Row(String[] columnNames, Map<String, Class<?>> types, Object[] values, String hiddenColumn) throws Exception`*** <br/>
-If any of the following cases happen, an exception will be thrown: null/emepty parameters, length of the input arrays doesn't match, or invalid value types. <br/>

***`Object getColumn(Object columnName) throws Exception`*** <br/>
-Return the value of given columnName in this row <br/>
-exception will be thrown if columnName is null/empty/nonexistent <br/>

***`void updateColumn(String columnName, Class<?> type, Object newValue) throws Exceptio`*** <br/>
-Update the value of given columnName in this row with the given new value
-exception will be thrown if columnName is null/empty/nonexistent or type of newValue is invalid.





