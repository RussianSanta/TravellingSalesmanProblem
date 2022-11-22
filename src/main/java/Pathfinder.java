import static helpers.MatrixHelper.*;
import static helpers.BranchAndBoundMethod.*;

public class Pathfinder {
    public static void main(String[] args) {
        prepareMatrix();

        if (findRowMinimum()) {
            System.out.println("Минимумы строк успешно найдены");
            printMatrix();
        } else {
            System.out.println("Минимумы строк не найдены");
            return;
        }

        if (rowReduction()) {
            System.out.println("Редукция строк успешно проведена");
            printMatrix();
        } else {
            System.out.println("Редукция строк не проведена");
            return;
        }

        if (findColumnMinimum()) {
            System.out.println("Минимумы столбцов успешно найдены");
            printMatrix();
        } else {
            System.out.println("Минимумы столбцов не найдены");
            return;
        }

        if (columnReduction()) {
            System.out.println("Редукция столбцов успешно проведена");
            printMatrix();
        } else {
            System.out.println("Редукция столбцов не проведена");
            return;
        }

        int minBound = getLocalMinimumBound();
        if (minBound > 0) {
            System.out.println("Корневая нижняя граница равна " + minBound);
        } else {
            System.out.println("Корневая нижняя граница не найдена");
            return;
        }

        
    }
}
