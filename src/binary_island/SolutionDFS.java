package binary_island;

public class SolutionDFS {
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
                    int currArea = dfs(matrix, i, j, visited);
                    globalMax = Math.max(globalMax, currArea);
                }
            }
        }
        return globalMax;
    }

    private int dfs(int[][]matrix, int i, int j, boolean[][] visited) {
        // Base case: 1. index out of bound; 2.when there's no more unvisted neighbors
        if (!withinBound(matrix, i, j) || matrix[i][j] == 0 || visited[i][j]) {
            return 0;
        }
        int currArea = 1;
        visited[i][j] = true;
        for (int row = i-1; row <= i+1; row++) {
            for (int col = j-1; col <= j+1; col++) {
                currArea += dfs(matrix, row, col, visited);
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
        SolutionDFS sol = new SolutionDFS();
        int[][] grid1 = new int[][]{{1,1,0,0},
                {0,0,1,1},
                {1,1,1,1}};
        int[][] grid2 = new int[][]{{0,1},
                {0,1}};
        System.out.println(sol.getMaxArea(grid1));
        System.out.println(sol.getMaxArea(grid2));
    }
}
