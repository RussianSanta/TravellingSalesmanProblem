package helpers;

public class BranchAndBoundMethod {
    private BranchAndBoundMethod() {

    }

    public static int[][] findRowMinimum(int[][] pathMatrix) {
        for (int i = 0; i < pathMatrix.length - 1; i++) {
            for (int j = 0; j < pathMatrix[i].length - 1; j++) {
                if (pathMatrix[i][j] < pathMatrix[i][pathMatrix[i].length - 1])
                    pathMatrix[i][pathMatrix[i].length - 1] = pathMatrix[i][j];
            }
        }
        return pathMatrix;
    }

    public static int[][] findColumnMinimum(int[][] pathMatrix) {
        for (int j = 0; j < pathMatrix[0].length - 1; j++) {
            for (int i = 0; i < pathMatrix.length - 1; i++) {
                if (pathMatrix[i][j] < pathMatrix[pathMatrix.length - 1][j])
                    pathMatrix[pathMatrix.length - 1][j] = pathMatrix[i][j];
            }
        }
        return pathMatrix;
    }

    public static int[][] rowReduction(int[][] pathMatrix) {
        for (int i = 0; i < pathMatrix.length - 1; i++) {
            for (int j = 0; j < pathMatrix[i].length - 1; j++) {
                if (pathMatrix[i][j] < 9999)
                    pathMatrix[i][j] = pathMatrix[i][j] - pathMatrix[i][pathMatrix[i].length - 1];
            }
        }
        return pathMatrix;
    }

    public static int[][] columnReduction(int[][] pathMatrix) {
        for (int j = 0; j < pathMatrix[0].length - 1; j++) {
            for (int i = 0; i < pathMatrix.length - 1; i++) {
                if (pathMatrix[i][j] < 9999)
                    pathMatrix[i][j] = pathMatrix[i][j] - pathMatrix[pathMatrix.length - 1][j];
            }
        }
        return pathMatrix;
    }

    public static int getLocalMinimumBound(int[][] pathMatrix) {
        int sum = 0;
        for (int i = 0; i < pathMatrix.length; i++) {
            sum = sum + pathMatrix[i][pathMatrix[i].length - 1];
        }
        for (int j = 0; j < pathMatrix[0].length; j++) {
            sum = sum + pathMatrix[pathMatrix.length - 1][j];
        }
        return sum;
    }
}