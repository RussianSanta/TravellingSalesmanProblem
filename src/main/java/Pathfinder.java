import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static helpers.MatrixHelper.*;
import static helpers.BranchAndBoundMethod.*;

public class Pathfinder {

    public static void main(String[] args) {
        //  i - номер строки, j - номер элемента в строке
        int[][] pathMatrix = prepareMatrix();
        ArrayList<Branch> branches = new ArrayList<>();

        assert pathMatrix != null;
        findRowMinimum(pathMatrix, false);
        printMatrix(pathMatrix);
        System.out.println("Минимумы строк успешно найдены");

        rowReduction(pathMatrix);
        printMatrix(pathMatrix);
        System.out.println("Редукция строк успешно проведена");

        findColumnMinimum(pathMatrix, false);
        printMatrix(pathMatrix);
        System.out.println("Минимумы столбцов успешно найдены");

        columnReduction(pathMatrix);
        printMatrix(pathMatrix);
        System.out.println("Редукция столбцов успешно проведена");

        int minBound = getLocalMinimumBound(pathMatrix);
        System.out.println("Корневая нижняя граница равна " + minBound);

        branches.add(new Branch(minBound, "Корень", pathMatrix));

        calculateScore(branches.get(0).getPathMatrix());
        printMatrix(branches.get(0).getPathMatrix());
        System.out.println("Рассчитаны потенциалы нулевых ячеек");

        int[] path = findMaxScore(branches.get(0).getPathMatrix());
        System.out.println("Выбран путь между точками " + (path[0]+1) + " и " + (path[1]+1));
    }
}
