package binary_island;

import static org.junit.Assert.*;
import org.junit.*;
import org.junit.Test;

public class TestSolutionBFS {
    SolutionBFS sol;
    @Before
    public void initialize() {
        sol = new SolutionBFS();
    }

    /**
     * Test case of null input
     */
    @Test
    public void testNullInput() {
        String message = "Failed to handle the corner case where the input matrix is null";
        int[][] input = null;
        int res = sol.getMaxArea(input);
        assertEquals(message,0, res);
    }

    /**
     * Test case of empty input
     */
    @Test
     public void testEmptyInput() {
        String message = "Failed to handle the corner case where the input matrix is empty";
        int[][] input = new int[0][0];
        int res = sol.getMaxArea(input);
        assertEquals(message,0, res);
     }

    /**
     * Test case of invalid input
     * Note: This test is automatically passed as per the assumption that the input 2D array is a binary matrix
     */
    @Test
     public void testBadInput() {
        String message = "Failed to handle the corner case where the input 2D array is non-rectangular or non-binary";
     }

    /**
     * Test case of random input
     */
     @Test
     public void testRandomInput() {
         String message = "The Result is incorrect";
         int[][] matrix1 = new int[][]{{1,1,0,0},
                                       {0,0,1,1},
                                       {1,1,1,1}};
         int[][] matrix2 = new int[][]{{0,1},
                                       {0,1}};
         int[][] matrix3 = new int[][]{{0,1,0,1,1},
                                       {1,0,0,0,1},
                                       {0,1,1,0,1},
                                       {0,0,0,0,1}};
         int res1 = sol.getMaxArea(matrix1);
         int res2 = sol.getMaxArea(matrix2);
         int res3 = sol.getMaxArea(matrix3);
         assertEquals(res1, 8);
         assertEquals(res2, 2);
         assertEquals(res3, 5);
    }
}
