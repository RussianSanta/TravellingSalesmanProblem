package helpers;

public class MatrixHelper {
    private MatrixHelper() {
    }

    public static int[][] prepareMatrix() {
        int[][] pathMatrix = new int[7][7];
/*
        1 - Московская фабрика
        2 - Самара
        3 - Нижний новгород
        4 - Екатеринбург
        5 - Архангельск
        6 - Санкт-Петербург
        7 - Вспомогательная строка(столбец) для расчетов
 */
        pathMatrix[0] = new int[]{9999, 1051, 406, 1903, 1234, 679, 9999};
        pathMatrix[1] = new int[]{1051, 9999, 775, 1046, 2285, 1730, 9999};
        pathMatrix[2] = new int[]{406, 775, 9999, 1497, 1640, 1085, 9999};
        pathMatrix[3] = new int[]{1903, 1046, 1497, 9999, 3118, 2582, 9999};
        pathMatrix[4] = new int[]{1234, 2285, 1640, 3118, 9999, 1435, 9999};
        pathMatrix[5] = new int[]{679, 1730, 1085, 2582, 1435, 9999, 9999};
        pathMatrix[6] = new int[]{9999, 9999, 9999, 9999, 9999, 9999, 0};

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (pathMatrix[i][j] != pathMatrix[j][i]) {
                    System.out.println("Ошибка при проверке матрицы. Расстояние между путями не совпадает");
                    System.out.println("Проблемный путь между точками: " + i + " и " + j);
                    System.out.println("Путь из " + i + " в " + j + " = " + pathMatrix[i][j]);
                    System.out.println("Путь из " + j + " в " + i + " = " + pathMatrix[j][i]);
                    return null;
                }
            }
        }

        System.out.println("Матрица успешно заполнена и проверена");
        printMatrix(pathMatrix);
        return pathMatrix;
    }

    public static void printMatrix(int[][] pathMatrix) {
        System.out.println("====================================================");
        for (int i = 0; i < pathMatrix.length; i++) {
            if (i < pathMatrix.length - 1) System.out.print("Пути из " + (i + 1) + ": [");
            else System.out.print("            ");
            for (int j = 0; j < pathMatrix[i].length; j++) {
                StringBuilder pathLength = new StringBuilder(String.valueOf(pathMatrix[i][j]));
                int lengthDelta = 4 - pathLength.length();
                pathLength.append(" ".repeat(lengthDelta));
                System.out.print(pathLength);
                if (j < pathMatrix[i].length - 2 && i != pathMatrix.length - 1) {
                    System.out.print(", ");
                    continue;
                }
                if (j == pathMatrix[i].length - 2 && i != pathMatrix.length - 1) System.out.print("] ");
                else System.out.print("  ");
            }
            System.out.println();
        }
        System.out.println("====================================================");
    }

    public static void setValue(int[][] pathMatrix, int i, int j, int newValue) {
        int oldValue = pathMatrix[i][j];
        pathMatrix[i][j] = newValue;
        if (i != pathMatrix.length - 1 && j != pathMatrix[i].length - 1) {
            pathMatrix[j][i] = newValue;
            System.out.println("Расстояние между путями " + i + " и " + j + " было изменено.");
            System.out.println("Старое расстояние: " + oldValue + " Новое расстояние: " + newValue);
        }
    }
}
