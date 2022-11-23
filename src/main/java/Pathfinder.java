import helpers.Branch;

import java.util.ArrayList;

import static helpers.BranchAndBoundMethod.*;
import static helpers.MatrixHelper.prepareMatrix;
import static helpers.MatrixHelper.printMatrix;

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

        createChildBranches(branches, branches.get(0));

        boolean isFound = false;
        int counter = 0;
        while (!isFound) {
            Branch minimalBranch = findMinimalBranch(branches);
            System.out.println("Найденная ветка с минимальной нижней границей: ");
            System.out.println(minimalBranch);

            if (!minimalBranch.getInfo().contains("!")) {
                printMatrix(minimalBranch.getPathMatrix());
                createChildBranches(branches, minimalBranch);
            } else {
                String branchInfo = minimalBranch.getInfo();
                int a = Integer.parseInt(branchInfo.substring(1, 2));
                int b = Integer.parseInt(branchInfo.substring(3, 4));
                minimalBranch.getPathMatrix()[a - 1][b - 1] = 9999;

                findRowMinimum(minimalBranch.getPathMatrix());
                printMatrix(pathMatrix);
                System.out.println("Минимумы строк успешно найдены");
                rowReduction(minimalBranch.getPathMatrix());
                printMatrix(pathMatrix);
                System.out.println("Редукция строк успешно проведена");
                findColumnMinimum(minimalBranch.getPathMatrix());
                printMatrix(pathMatrix);
                System.out.println("Минимумы столбцов успешно найдены");
                columnReduction(minimalBranch.getPathMatrix());
                printMatrix(pathMatrix);
                System.out.println("Редукция столбцов успешно проведена");
                printMatrix(minimalBranch.getPathMatrix());
                System.out.println("Найдены минимумы и проведены редукции для строк и столбцов новой таблицы");

                createChildBranches(branches, minimalBranch);
            }

            isFound = isFinished();
            if (++counter > 25) {
                System.out.println("Ответ не был найден за 25 циклов");
                return;
            }
        }
        Branch preLastBranch = branches.get(branches.size()-2);
        int[] lastOne = findLastOne(preLastBranch.getPathMatrix());
        if (lastOne == null) {
            System.out.println("Проблема при нахождении последнего отрезка пути");
            return;
        }
        System.out.println("Результат найден. Итоговое расстояние = " + preLastBranch.getMinBound());

        ArrayList<String> path = new ArrayList<>();
        path.add((lastOne[0] + 1) + "-" + (lastOne[1] + 1));
        Branch parentBranch = preLastBranch;
        for (int i = 0; i < pathMatrix.length-2; i++) {
            path.add(parentBranch.getInfo());
            parentBranch = parentBranch.getParent();
            if (parentBranch == null) break;
        }
        System.out.println(path);
        path.sort((o1, o2) -> {
            if (o1.charAt(0) == '1') return -1;
            if (o2.charAt(0) == '1') return 1;
            if (o1.charAt(2) == '1') return 1;
            if (o2.charAt(2) == '1') return -1;
            if (o1.charAt(2) == o2.charAt(0)) return -1;
            if (o2.charAt(2) == o1.charAt(0)) return 1;
            return 0;
        });
        System.out.println(path);

        System.out.println(preLastBranch);
    }
}
