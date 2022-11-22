package helpers;

import static helpers.MatrixHelper.getPathMatrix;
import static helpers.MatrixHelper.setPathMatrix;


public class BranchAndBoundMethod {
    private BranchAndBoundMethod() {

    }

    public static boolean findRowMinimum() {
        int[][] pathMatrix = getPathMatrix();
        if (pathMatrix != null) {
            for (int i = 0; i < pathMatrix.length - 1; i++) {
                for (int j = 0; j < pathMatrix[i].length - 1; j++) {
                    if (pathMatrix[i][j] < pathMatrix[i][pathMatrix[i].length - 1])
                        pathMatrix[i][pathMatrix[i].length - 1] = pathMatrix[i][j];
                }
            }
            setPathMatrix(pathMatrix);
            return true;
        } else {
            System.out.println("Матрица не получена");
            return false;
        }
    }

    public static boolean findColumnMinimum() {
        int[][] pathMatrix = getPathMatrix();
        if (pathMatrix != null) {
            for (int j = 0; j < pathMatrix[0].length - 1; j++) {
                for (int i = 0; i < pathMatrix.length - 1; i++) {
                    if (pathMatrix[i][j] < pathMatrix[pathMatrix.length - 1][j])
                        pathMatrix[pathMatrix.length - 1][j] = pathMatrix[i][j];
                }
            }
            setPathMatrix(pathMatrix);
            return true;
        } else {
            System.out.println("Матрица не получена");
            return false;
        }
    }

    public static boolean rowReduction() {
        int[][] pathMatrix = getPathMatrix();
        if (pathMatrix != null) {
            for (int i = 0; i < pathMatrix.length - 1; i++) {
                for (int j = 0; j < pathMatrix[i].length - 1; j++) {
                    if (pathMatrix[i][j] < 9999)
                        pathMatrix[i][j] = pathMatrix[i][j] - pathMatrix[i][pathMatrix[i].length - 1];
                }
            }
            setPathMatrix(pathMatrix);
            return true;
        } else {
            System.out.println("Матрица не получена");
            return false;
        }
    }

    public static boolean columnReduction() {
        int[][] pathMatrix = getPathMatrix();
        if (pathMatrix != null) {
            for (int j = 0; j < pathMatrix[0].length - 1; j++) {
                for (int i = 0; i < pathMatrix.length - 1; i++) {
                    if (pathMatrix[i][j] < 9999)
                        pathMatrix[i][j] = pathMatrix[i][j] - pathMatrix[pathMatrix.length - 1][j];
                }
            }
            setPathMatrix(pathMatrix);
            return true;
        } else {
            System.out.println("Матрица не получена");
            return false;
        }
    }

    public static int getLocalMinimumBound() {
        int[][] pathMatrix = getPathMatrix();
        if (pathMatrix != null) {
            int sum = 0;
            for (int i = 0; i < pathMatrix.length; i++) {
                sum = sum + pathMatrix[i][pathMatrix[i].length - 1];
            }
            for (int j = 0; j < pathMatrix[0].length; j++) {
                sum = sum + pathMatrix[pathMatrix.length - 1][j];
            }
            return sum;
        } else {
            System.out.println("Матрица не получена");
            return -1;
        }
    }
}
