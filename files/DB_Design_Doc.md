# Documentation of Design
## High-Level Design
**DatabaseManager**: Map of (Name, Database) -> Map<String, Database> <br/>
**Database**: Map of (Name, Table) -> Map<String, Table> <br/>
**Table**: Map of (Primary Key, Row) -> Map<String, Row> <br/>
**Row**: Map of (Column Name, Value) -> Map<String, Objrect> <br/>
## Illustration in UML
![UML](/files/DB_UML.png?raw=true "demo")

## **Complexity Analysis**
notation: <br/>
&emsp;R: number of existent rows <br/>
&emsp;C: number of existent columns <br/>

***Key issues:***  <br/>
-Use HashMap to make finding database/table/row/cell O(1) time, but the worse case is still linear time when hashing is unreasonable <br>
-Implementation of orderBy and get a specific count of rows :<br>
&emsp; Use a PriorityQueue with size of *count*, so that we bring down the time of operations like pq.offer() and pq.poll() to O(log(count)) instead of O(R)


### **Create a datbase** <br/>
-Create an instance of Database  <br/>
-Underlaying: create a HashMap <br/>
-Time: O(1) <br/> 
-Space: O(1) <br/>

### **Create a table** <br/>
-Create an instance of Table <br/>
-Underlaying:  <br/>
&emsp;1.create a HashMap and put() it to the wrapper HashMap in Database <br/>
&emsp;2.Generate data type information for each column (O(C)) and store in a HashMap <br/>
-Time: O(C) <br/>
-Space: a HashMap of C elements ->  O(C) <br/>

### **Insert row** 
-When creating a row, we need to store a value for each column <br/>
-Uderlaying: create a HashMap and O(C) times HashMap.put() operation <br/>
-Time: check duplicate primary key, then create a new Row instance <br/>
&emsp; Average:  O(1) + O(C) -> O(C) <br/>
&emsp; Worst case:  O(R) + O(C) (a find-key operation of HashMap could take linear time) <br/>
-Space: a HashMap of C elements -> O(C)

### **Delete row** 
-Remove the entry of HashMap hosted in the Table instance, no extra space <br/>
-Underlaying: a HashMap.remove() operation <br/>
-Time: <br/>
&emsp; Average:  O(1) <br/>
&emsp; Worst case:  O(R) (a find-key operation of HashMap could take linear time) <br/>
-Space: O(1) <br/>

### **Update Row **
-Two continuous "get()" operations to locate a row then a cell, no extra space<br/>
-Time: <br/>
&emsp; Average:  O(1) <br/>
&emsp; Worst case:  O(R + C) (a find-key operation of HashMap could take linear time) <br/>
-Space: O(1)<br/>

### **Sort by a column**
-Use a PriorityQueue to sort the rows, and return results in a `list<List<Object>>` <br/>
-Underlaying: <br/>
&emsp; 1.Enqueue every row -> O(R * logR) <br/>
&emsp; 2.For each row, we need to poll it from PQ (O(logR)), wirte every value into a list (O(C)). We need to do it *R* times -> O(R*(logR + C)) <br/>
-Time: O(R*(logR + C))<br/>
-Space: a PQ of R elemnts and a list of R*C elements -> O(R + R * C) -> O(R * C)

### **Sort by a clumn and get specific count of rows**
-Use a PriorityQueue to sort the rows, and return results in a `list<List<Object>>` <br/>
-Underlaying: <br/>
&emsp; 1.Use a PQ as Max Heap, whose size will be kept <= count <br/>
&emsp; 2.Enqueue every row -> O(R * log(max(count, R))) <br/>
&emsp; 3.For each row, we need to poll it from PQ (O(log(max(count, R)))), write every value into a list (O(C)). We need to do it *max(count, R)* times -> O(max(count, R)*(log(max(count, R)) + C)) <br/>
-Time: O(max(count, R) * (log(max(count, R)) + C)) <br/>
-Space: a PQ of R elemnts and a list of max(count, R)*C elements -> O(R + max(count, R) * C)

### **Get all rows**
-Iterate both HashMaps of Table and Row, put every value in a `list<List<Object>>`.<br/>
-Underlaying: this operation is built is essentially a oderBy(R, "Timestamp) operation, where R is the number of all existent rows and "Timestamp" is a hidden primary key to keep the time of insertion of each row</br>
-Time: O(R*(logR + C))<br/>
-Space: a list of R*C elements -> O(R * C)<br/>

### **Get all rows with specific count**
-Iterate the HashMap in the Table instance, put a specific count of rows (or all rows, whichever is larger) in a list and every value of this row in an nested list.<br/>
-Underlaying: this operation is built is essentially a oderBy(count, "Timestamp) operation, where R is the number of all existent rows and "Timestamp" is a hidden primary key to keep the time of insertion of each row</br>
-Time: O(max(count, R) * (log(max(count, R)) + C)) <br/>
-Space: a PQ of R elemnts and a list of max(count, R)*C elements -> O(R + max(count, R) * C)

### **Groupy by a clumn**
-Return occurrences of values under a given aggregate key <br/>
-Underlaying: <br/>
&emsp;1. Iterate the desied column and use a HashMap to count occurrences <br/>
&emsp;2. Organize result in a `list<List<Object>>`, size will be R * 2 <br/>
-Time:






  
