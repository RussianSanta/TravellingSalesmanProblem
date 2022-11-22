import helpers.Branch;

import java.util.ArrayList;

import static helpers.MatrixHelper.*;
import static helpers.BranchAndBoundMethod.*;

public class Pathfinder {

    public static void main(String[] args) {
        //  i - номер строки, j - номер элемента в строке
        int[][] pathMatrix = prepareMatrix();
        ArrayList<Branch> branches = new ArrayList<>();

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

        int minBound = getLocalMinimumBound(pathMatrix, 0);
        System.out.println("Корневая нижняя граница равна " + minBound);

        branches.add(new Branch(minBound, "Начало", pathMatrix, null));

        int[] path = findMaxScore(branches.get(0).getPathMatrix());
        System.out.println("Рассчитаны потенциалы нулевых ячеек. Выбран путь между точками " + (path[0]+1) + " и " + (path[1]+1));

        System.out.println("====================================================");
        System.out.println("====================================================");
        System.out.println("Приступаем к нахождению правой ветви графа");
        Branch newRightBranch = findFirstRight(path,pathMatrix,branches.get(0));
        branches.add(newRightBranch);
    }
}
