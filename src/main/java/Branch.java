import java.util.Arrays;

public class Branch {
    private int minBound;
    private String info;
    private int[][] pathMatrix;
    private Branch leftChild = null;
    private Branch rightChild = null;

    public Branch(int minBound, String info, int[][] pathMatrix) {
        this.minBound = minBound;
        this.info = info;

        this.pathMatrix = new int[pathMatrix.length][];
        for (int i = 0; i < pathMatrix.length; i++) {
            this.pathMatrix[i] = new int[pathMatrix[i].length];
            System.arraycopy(pathMatrix[i], 0, this.pathMatrix[i], 0, pathMatrix[i].length);
        }

    }

    public Branch getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Branch leftChild) {
        this.leftChild = leftChild;
    }

    public Branch getRightChild() {
        return rightChild;
    }

    public void setRightChild(Branch rightChild) {
        this.rightChild = rightChild;
    }

    public int getMinBound() {
        return minBound;
    }

    public String getInfo() {
        return info;
    }

    public int[][] getPathMatrix() {
        return pathMatrix;
    }
}
