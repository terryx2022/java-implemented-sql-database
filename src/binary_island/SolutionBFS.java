package binary_island;

import java.util.LinkedList;
import java.util.Queue;

public class SolutionBFS {
    public int getMaxArea(int[][] matrix) {
        // Corner cases: null, empty
        // Assuming all input 2D arrays are binary matrices, if not null or empty.
        if (matrix == null || matrix.length == 0) {
            return 0;
        }
        int globalMax = 0;
        boolean[][] visited = new boolean[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] != 0 && !visited[i][j]) {
                    int currArea = bfs(matrix, i, j, visited);
                    globalMax = Math.max(globalMax, currArea);
                }
            }
        }
        return globalMax;
    }

    private int bfs(int[][] grid, int i, int j, boolean[][] visited) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{i, j});
        visited[i][j] = true;
        int currArea = 1;
        while (!queue.isEmpty()) {
            int[] indices = queue.poll();
            i = indices[0];
            j = indices[1];
            // Find neighbors
            for (int row = i-1; row <= i+1; row++) {
                for (int col = j-1; col <= j+1; col++) {
                    if (withinBound(grid, row, col) && !visited[row][col] && grid[row][col] == 1) {
                        currArea++;
                        queue.offer(new int[]{row, col});
                        visited[row][col] = true;
                    }
                }
            }
        }
        return currArea;
    }

    private boolean withinBound(int[][] grid, int i, int j) {
        return i >= 0 && i < grid.length && j >= 0 && j < grid[0].length;
    }

    /**
     * Test: customized input (Please see the unit tests in TestSolutionBFS.java)
     * @param args
     */
    public static void main(String[] args) {
        SolutionBFS sol = new SolutionBFS();
        int[][] grid1 = new int[][]{{1,1,0,0},
                {0,0,1,1},
                {1,1,1,1}};
        int[][] grid2 = new int[][]{{0,1},
                {0,1}};
        System.out.println(sol.getMaxArea(grid1));
        System.out.println(sol.getMaxArea(grid2));
    }
}