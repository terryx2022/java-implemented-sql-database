# API
* The basic operations required by instructions are all implemented 
* Utilized HashMap to speed up operations

# Tests
* Wrote 51 unit tests to test funcionality of APIs
* Most corner case should be covered. There could be a few corner cases of invalid input missing, given that there're so many different combinations of invalid input.


# Concurrent Read/Write
I've been self-learning know of concurrency and have come up a few of ideas. But I haven't got time to implement these:
* Use "synchronized" keyword to add locks to read/write operations
* Replace my internal maps with ConcurrentHashMap. Since ConcurrentHashMap divides a whole map into many submaps with individual locks, the problem is the consistensy between write and read during iteration.
* Use ReadWriteLock to add locks only to where there could be data race, allowing concurrent read to happen, which improves proformance.