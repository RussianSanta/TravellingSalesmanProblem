package helpers;

import java.util.ArrayList;

import static helpers.MatrixHelper.printMatrix;

public class BranchAndBoundMethod {
    private static int lostPaths = -1;

    private BranchAndBoundMethod() {

    }

    public static int[][] findRowMinimum(int[][] pathMatrix) {
        for (int i = 0; i < pathMatrix.length - 1; i++) {
            pathMatrix[i][pathMatrix[i].length - 1] = pathMatrix[i][0];
            for (int j = 0; j < pathMatrix[i].length - 1; j++) {
                if (pathMatrix[i][j] < pathMatrix[i][pathMatrix[i].length - 1])
                    pathMatrix[i][pathMatrix[i].length - 1] = pathMatrix[i][j];
            }
        }
        return pathMatrix;
    }

    public static int[][] findColumnMinimum(int[][] pathMatrix) {
        for (int j = 0; j < pathMatrix[0].length - 1; j++) {
            pathMatrix[pathMatrix.length - 1][j] = pathMatrix[0][j];
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

    private static int calculateScore(int[][] pathMatrix, int a, int b) {
        int minRow = 10000;
        int minColumn = 10000;

        for (int i = 0; i < pathMatrix.length - 1; i++) {
            if (pathMatrix[i][b] < minRow && i != a) minRow = pathMatrix[i][b];
        }

        for (int j = 0; j < pathMatrix[0].length - 1; j++) {
            if (pathMatrix[a][j] < minColumn && j != b) minColumn = pathMatrix[a][j];
        }
        return minColumn + minRow;
    }

    public static int[] findMaxScore(int[][] pathMatrix) {
        int max = 0;
        int lastI = -1;
        int lastJ = -1;
        for (int i = 0; i < pathMatrix.length - 1; i++) {
            for (int j = 0; j < pathMatrix[i].length - 1; j++) {
                int potential = calculateScore(pathMatrix, i, j);
                if (potential > max && potential < 10000) {
                    max = potential;
                    lastI = i;
                    lastJ = j;
                }
            }
        }
        return new int[]{lastI, lastJ, max};
    }

    public static void createChildBranches(ArrayList<Branch> branches, Branch minimalBranch) {
        int[] cyclePath = findMaxScore(minimalBranch.getPathMatrix());

        System.out.println("Рассчитаны потенциалы нулевых ячеек. Выбран путь между точками " + (cyclePath[0] + 1) + " и " + (cyclePath[1] + 1));
        System.out.println("====================================================");
        System.out.println("====================================================");
        System.out.println("Приступаем к нахождению правой ветви графа");
        branches.add(findRight(cyclePath, minimalBranch.getPathMatrix(), minimalBranch));

        System.out.println("====================================================");
        System.out.println("====================================================");
        System.out.println("Приступаем к нахождению левой ветви графа");
        branches.add(findLeft(cyclePath, minimalBranch));
    }

    //  Ветвь, в которой путь НЕ включается в итоговый
    public static Branch findLeft(int[] path, Branch parentBranch) {
        int minBound = parentBranch.getMinBound() + path[2];
        String info = "!" + (path[0] + 1) + "-" + (path[1] + 1);
        Branch newLeftBranch = new Branch(minBound, info, parentBranch.getPathMatrix(), parentBranch);
        parentBranch.setLeftChild(newLeftBranch);
        System.out.println("====================================================");
        System.out.println("Создана новая левая ветка:");
        System.out.println(newLeftBranch);
        System.out.println("====================================================");
        return newLeftBranch;
    }

    //  Ветвь, в которой путь включается в итоговый
    public static Branch findRight(int[] path, int[][] pathMatrix, Branch parentBranch) {
        int[][] matrix = new int[pathMatrix.length][];

        for (int i = 0; i < pathMatrix.length; i++) {
            matrix[i] = new int[pathMatrix[i].length];
            System.arraycopy(pathMatrix[i], 0, matrix[i], 0, pathMatrix[i].length);
        }

        for (int i = 0; i < matrix.length - 1; i++) {
            matrix[i][path[1]] = 99999;
        }

        for (int j = 0; j < matrix[0].length - 1; j++) {
            matrix[path[0]][j] = 99999;
        }

        matrix[path[1]][path[0]] = 99999;

        printMatrix(matrix);
        System.out.println("Редукция матрицы успешно проведена");

        findRowMinimum(matrix);
        rowReduction(matrix);
        findColumnMinimum(matrix);
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
        System.out.println("Осталось рассмотреть путей: " + getCountOfPath(matrix));

        return newRightBranch;
    }

    private static int getCountOfPath(int[][] pathMatrix) {
        int sum = 0;
        for (int i = 0; i < pathMatrix.length - 1; i++) {
            for (int j = 0; j < pathMatrix[i].length - 1; j++) {
                if (pathMatrix[i][j] < 9999)
                    sum++;
            }
        }
        lostPaths = sum;
        return sum;
    }

    public static Branch findMinimalBranch(ArrayList<Branch> branches) {
        int min = 9999999;
        Branch minBranch = null;

        for (Branch branch : branches) {
            if (branch.getMinBound() < min && branch.getLeftChild() == null && branch.getRightChild() == null) {
                min = branch.getMinBound();
                minBranch = branch;
            }
        }

        return minBranch;
    }

    public static boolean isFinished() {
        return (lostPaths == 1);
    }

    public static int[] findLastOne(int[][] pathMatrix) {
        for (int i = 0; i < pathMatrix.length - 1; i++) {
            for (int j = 0; j < pathMatrix[i].length - 1; j++) {
                if (pathMatrix[i][j] < 9999)
                    return new int[]{i, j};
            }
        }
        return null;
    }
}