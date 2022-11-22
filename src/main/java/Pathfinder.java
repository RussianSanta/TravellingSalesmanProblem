import static helpers.MatrixHelper.*;
import static helpers.BranchAndBoundMethod.*;

public class Pathfinder {

    public static void main(String[] args) {
        //  i - номер строки, j - номер элемента в строке
        int[][] pathMatrix = prepareMatrix();

        assert pathMatrix != null;
        findRowMinimum(pathMatrix);
        printMatrix(pathMatrix);
        System.out.println("Минимумы строк успешно найдены");

        rowReduction(pathMatrix);
        printMatrix(pathMatrix);
        System.out.println("Редукция строк успешно проведена");

        findColumnMinimum(pathMatrix);
        printMatrix(pathMatrix);
        System.out.println("Минимумы столбцов успешно найдены");

        columnReduction(pathMatrix);
        printMatrix(pathMatrix);
        System.out.println("Редукция столбцов успешно проведена");

        int minBound = getLocalMinimumBound(pathMatrix);
        System.out.println("Корневая нижняя граница равна " + minBound);
    }
}
