import java.math.BigInteger;

public class FloydWarshall {
    private int[][] matrix = {
            {0, 2, 1000, 5, 1000, 1000},
            {1000, 0, 4, 1000, 1000, 1000},
            {1000, 1000, 0, 1, 1000, 7},
            {3, 4, 6, 0, 5, 1000},
            {1000, 1000, 8, 1000, 0, 1},
            {1000, 1000, 1000, 1000, 1000, 0}
    };
    private int n = 0;
    private int infinity = 2147483647;
    private int maxValue = -1;

    public FloydWarshall(){}

    public void findMaxValue(){
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if(maxValue < matrix[i][j]){
                    maxValue = matrix[i][j];
                }
            }
        }
    }

    public void fillMatrix(){
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if(matrix[i][j] == -1){
                    matrix[i][j] = maxValue + 10;
                }
            }
        }
    }

    public void algorythm(){
        n  = matrix.length;
        for (int k = 0; k < n; ++k) {
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    matrix[i][j] = Math.min(matrix[i][j], matrix[i][k] + matrix[k][j]);
                }
            }
        }
    }

    public void printer(){
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                System.out.print(matrix[i][j]);
                System.out.print(" ");
            }
            System.out.print("\n");
        }
    }

}
