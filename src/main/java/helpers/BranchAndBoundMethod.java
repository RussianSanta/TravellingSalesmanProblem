package helpers;

import static helpers.MatrixHelper.printMatrix;

public class BranchAndBoundMethod {
    private BranchAndBoundMethod() {

    }

    public static int[][] findRowMinimum(int[][] pathMatrix, boolean checkZero) {
        for (int i = 0; i < pathMatrix.length - 1; i++) {
            pathMatrix[i][pathMatrix[i].length - 1] = pathMatrix[i][0];
            for (int j = 0; j < pathMatrix[i].length - 1; j++) {
                if (checkZero) {
                    if (pathMatrix[i][j] < pathMatrix[i][pathMatrix[i].length - 1] && pathMatrix[i][j] != 0)
                        pathMatrix[i][pathMatrix[i].length - 1] = pathMatrix[i][j];
                } else {
                    if (pathMatrix[i][j] < pathMatrix[i][pathMatrix[i].length - 1])
                        pathMatrix[i][pathMatrix[i].length - 1] = pathMatrix[i][j];
                }
            }
        }
        return pathMatrix;
    }

    public static int[][] findColumnMinimum(int[][] pathMatrix, boolean checkZero) {
        for (int j = 0; j < pathMatrix[0].length - 1; j++) {
            pathMatrix[pathMatrix.length - 1][j] = pathMatrix[0][j];
            for (int i = 0; i < pathMatrix.length - 1; i++) {
                if (checkZero) {
                    if (pathMatrix[i][j] < pathMatrix[pathMatrix.length - 1][j] && pathMatrix[i][j] != 0)
                        pathMatrix[pathMatrix.length - 1][j] = pathMatrix[i][j];
                } else {
                    if (pathMatrix[i][j] < pathMatrix[pathMatrix.length - 1][j])
                        pathMatrix[pathMatrix.length - 1][j] = pathMatrix[i][j];
                }
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

    public static int getLocalMinimumBound(int[][] pathMatrix, int minBound) {
        int sum = minBound;
        for (int i = 0; i < pathMatrix.length; i++) {
            int min = pathMatrix[i][pathMatrix[i].length - 1];
            if (min < 9999)
                sum = sum + min;
        }
        for (int j = 0; j < pathMatrix[0].length; j++) {
            int min = pathMatrix[pathMatrix.length - 1][j];
            if (min < 9999)
                sum = sum + pathMatrix[pathMatrix.length - 1][j];
        }
        return sum;
    }

    public static int[][] calculateScore(int[][] pathMatrix) {
        findRowMinimum(pathMatrix, true);
        findColumnMinimum(pathMatrix, true);
        for (int i = 0; i < pathMatrix.length - 1; i++) {
            for (int j = 0; j < pathMatrix[i].length - 1; j++) {
                if (pathMatrix[i][j] == 0)
                    pathMatrix[i][j] = pathMatrix[i][j] - pathMatrix[pathMatrix.length - 1][j]
                            - pathMatrix[i][pathMatrix[i].length - 1];
            }
        }
        return pathMatrix;
    }

    public static int[] findMaxScore(int[][] pathMatrix) {
        int min = 9999;
        int lastI = -1;
        int lastJ = -1;
        for (int i = 0; i < pathMatrix.length - 1; i++) {
            for (int j = 0; j < pathMatrix[i].length - 1; j++) {
                if (pathMatrix[i][j] < min) {
                    min = pathMatrix[i][j];
                    lastI = i;
                    lastJ = j;
                }
            }
        }
        return new int[]{lastI, lastJ};
    }

    //  Ветвь, в которой путь НЕ включается в итоговый
    public static void findLeft() {

    }

    //  Ветвь, в которой путь включается в итоговый
    public static void findRight(int[] path, int[][] pathMatrix, Branch parentBranch) {
        int[][] matrix = new int[pathMatrix.length][];

        for (int i = 0; i < pathMatrix.length; i++) {
            matrix[i] = new int[pathMatrix[i].length];
            System.arraycopy(pathMatrix[i], 0, matrix[i], 0, pathMatrix[i].length);
        }

        for (int i = 0; i < matrix.length - 1; i++) {
            matrix[i][path[1]] = 9999;
        }

        for (int j = 0; j < matrix[0].length - 1; j++) {
            matrix[path[0]][j] = 9999;
        }

        matrix[path[1]][path[0]] = 9999;

        printMatrix(matrix);
        System.out.println("Редукция матрицы успешно проведена");

        findRowMinimum(matrix, false);
        rowReduction(matrix);
        findColumnMinimum(matrix, false);
        columnReduction(matrix);

        int minBound = getLocalMinimumBound(matrix, parentBranch.getMinBound());
        printMatrix(matrix);
        System.out.println("Найдены минимумы и проведены редукции для строк и столбцов новой таблицы");
        System.out.println("Корневая нижняя граница равна " + minBound);

        String info = (path[0] + 1) + "-" + (path[1] + 1);
        Branch newRightBranch = new Branch(minBound, info, matrix, parentBranch);
        parentBranch.setRightChild(newRightBranch);
        System.out.println("====================================================");
        System.out.println("Создана новая правая ветка:");
        System.out.println(newRightBranch);
        System.out.println("====================================================");
    }
}