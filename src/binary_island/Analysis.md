# Recap of Problem
## Verbally
Given the topography information, find the area of the largest island.
## Mathematical Model
**Input** : an M-by-N binary matrix, where "0" represents "water" and "1" represents "land" <br/>
**Output**: a number representing the larget area

**Definitions**:
  * Area equals the sum of all 1's within an island
  * An island is formed by connected 1's
  * Any two 1's adjacent to each other (vertically, horizontally and diagonally) is deemed as connected.
  * There're 2 islands in the following example<br/>
    0  1  0  0 <br/>
    1  0  1  1 <br/>
    0 0 1 0


# High-level Solution
Run a graph search algorithm against each "1" of the matrix, and all adjacent/connected 1's are deemed as neighbors. Count the 1's during each graph search and return a global max value.

# Solution #1
**Algorithm:** Breadth-First Search <br/>
**Implementation:** Iteration <br/>
**Datasture:** <br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-a queue to track states of a BFS <br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-a 2D array to mark visited elements  <br/>
**Time Complexity**: <br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;We keep marking visted elements, and the number of neighbors of each node is a constant number (<=8). The answer is O(M * N) (M and N are dimensions of the matrix) <br/>
**Space(auxiliary)**: <br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Space of array is fixed (M *N). The maximum space the queue could use is also M * N, which gives us O(M * N)


# Solution #2
**Algorithm:** Depth-First Search <br/>
**Implementation:** Recursion <br/>
**Datasture:** <br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-a 2D array to mark visited elements  <br/>
**Time Complexity**: <br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;During each recursion, we keep mark the element as visted (O(1)), and examine neighbors (O(1)). We need to do DFS for every node
Thus, the answer is also O(M*N) (M and N are dimensions of the matrix) <br/>
**Space(auxiliary)**: <br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Space of array is fixed (M * N), plus the space used by recursion callstack (M * N callstack frames at most), which also gives us O(M * N).





