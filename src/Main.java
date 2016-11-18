public class Main {

    private static double[][] mtrA = {{0.6137, -0.0808, 0.0162, 0.0323, 0.1131},
            {0.0840, 0.9609, 0, -0.0646, 0.0646}, {0.0485, 0, 0.7720, -0.2261, 0.1292},
            {-0.0969, 0.2035, 0, 0.7591, -0.0323}, {0.4038, 0, 0.1454, 0.0162, 0.9044}};
    private static double[] freeMembers = {-3.4561, 2.9603, 2.8036, -2.0058, 2.3256};
    private static int size = 5;

    public static void main(String[] args) {
        gaussMethod();
        printResidualMatrix();

    }

    private static void gaussMethod() {
        Gauss gauss = new Gauss(mtrA, freeMembers);
        System.out.println("Original matrix:\n");
        gauss.printMatrix();
        System.out.println("Variable numbers before reduction:\n");
        gauss.printVarNum();
        gauss.matrixReduction();
        System.out.println("Matrix after reduction:\n");
        gauss.printMatrix();
        System.out.println("Variable numbers after reduction:\n");
        gauss.printVarNum();
        gauss.convertToTriangle();
        System.out.println("Triangle matrix:\n");
        gauss.printMatrix();
        gauss.calcRoots();
        System.out.println("Roots:\n");
        gauss.printRoots();
        System.out.println("Leading elements:\n");
        gauss.printLeadingElements();
        gauss.calcDeterminant();
        System.out.println("Determinant:\n");
        gauss.printDeterminant();
        gauss.calcResidualVector();
        System.out.println("Residual vector:\n");
        gauss.printResidualVector();
        gauss.calcResidualVectorNorm();
        System.out.println("Norm of residual vector:\n");
        gauss.printResidualVectorNorm();
    }

    private static void printResidualMatrix() {
        double[][] identityMatrix = new double[size][size];
        for (int i = 0; i < size; i++) {
            identityMatrix[i][i] = 1;
        }
        double[][] inverseMatrix = getInverseMatrix();
        System.out.println("Inverse matrix (A^(-1)):\n");
        printMatrix(inverseMatrix, size, size);
        double[][] residualMatrix = mulMatrix(mtrA, size, size, inverseMatrix, size);
        residualMatrix = subMatrix(residualMatrix, identityMatrix, size, size);
        System.out.println("Residual Matrix:\n");
        printMatrix(residualMatrix, size, size);
        System.out.println("Norm of residual matrix:\n");
        System.out.println(getMatrixNorm(residualMatrix) + "\n");
    }

    private static double[][] getInverseMatrix() {
        double[][] inverseMatrix = new double[size][size];
        double[][] identityMatrix = new double[size][size];
        for (int i = 0; i < size; i++) {
            identityMatrix[i][i] = 1;
        }
        for (int i = 0; i < size; i++) {
            Gauss gauss = new Gauss(mtrA, identityMatrix[i]);
            gauss.matrixReduction();
            gauss.convertToTriangle();
            gauss.calcRoots();
            double[] tmpRoots = gauss.getRoots();
            for (int j = 0; j < size; j++) {
                inverseMatrix[j][i] = tmpRoots[j];
            }
        }
        return inverseMatrix;
    }

    private static double getMatrixNorm(double[][] mtr) {
        double tmp = 0;
        double norm = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tmp += Math.abs(mtr[i][j]);
            }
            if (tmp > norm) {
                norm = tmp;
            }
            tmp = 0;
        }
        return norm;
    }

    public static double[][] mulMatrix(double[][] a, int n1, int m1, double[][] b, int m2) {
        double[][] c = new double[n1][m2];
        for (int i = 0; i < n1; i++) {
            for (int j = 0; j < m2; j++) {
                for (int k = 0; k < m1; k++) {
                    c[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return c;
    }

    public static double[][] subMatrix(double[][] a, double[][] b, int n, int m) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                a[i][j] -= b[i][j];
            }
        }
        return a;
    }

    private static void printArray(double[] a, int size) {
        for (int i = 0; i < size; i++) {
            System.out.print(a[i] + " ");
        }
    }

    private static void printMatrix(double[][] mtr, int n, int m) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(mtr[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

}
