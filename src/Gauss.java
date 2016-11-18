class Gauss {
    private double[][] extendedMtr;
    private int size = 5;
    private int[] varNum = {0, 1, 2, 3, 4};
    private double[] roots = {0, 0, 0, 0, 0};
    private double[] leadingElements = {0, 0, 0, 0, 0};
    private double determinant = 0;
    private double[] residualVector = {0, 0, 0, 0, 0};

    public Gauss(double[][] mtrA, double[] freeMembers) {
        extendedMtr = new double[5][6];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                extendedMtr[i][j] = mtrA[i][j];
            }
            extendedMtr[i][5] = freeMembers[i];
        }
    }

    public void printMatrix() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size + 1; j++) {
                System.out.print(extendedMtr[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printVarNum() {
        for (int i = 0; i < size; i++) {
            System.out.println(varNum[i] + " ");
        }
        System.out.println();
    }

    public void printRoots() {
        for (int i = 0; i < size; i++) {
            System.out.println(roots[i] + " ");
        }
        System.out.println();
    }

    public void printLeadingElements() {
        for (int i = 0; i < size; i++) {
            System.out.println(leadingElements[i] + " ");
        }
        System.out.println();
    }

    public void printDeterminant() {
        System.out.println(determinant + "\n");
    }

    public void printResidualVector() {
        for (int i = 0; i < size; i++) {
            System.out.println(residualVector[i] + " ");
        }
        System.out.println();
    }

    private int findMaxInString(int strIndex) {
        int maxIndex = 0;
        for (int i = 0; i < size; i++) {
            if (Math.abs(extendedMtr[strIndex][maxIndex]) < Math.abs(extendedMtr[strIndex][i])) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    private void changeColumns(int k, int q) {
        double temp;
        int tmp;
        for (int i = 0; i < size; i++) {
            temp = extendedMtr[i][k];
            extendedMtr[i][k] = extendedMtr[i][q];
            extendedMtr[i][q] = temp;
            tmp = varNum[k];
            varNum[k] = varNum[q];
            varNum[q] = tmp;
        }
    }

    public void matrixReduction() {
        int k;
        int tmp;
        for (int i = 0; i < size; i++) {
            k = findMaxInString(i);
            if (k != i) {
                changeColumns(k, i);
            }
        }
    }

    private double[] subtractArrays(double[] a, double[] b) {
        for (int i = 0; i < size + 1; i++) {
            a[i] -= b[i];
        }
        return a;
    }

    private double[] divideArray(double[] a, double divider) {
        for (int i = 0; i < size + 1; i++) {
            a[i] /= divider;
        }
        return a;
    }

    private double[] multiplyArray(double[] a, double multiplier) {
        for (int i = 0; i < size + 1; i++) {
            a[i] *= multiplier;
        }
        return a;
    }

    private double[] copyString(double[] a, double[] b) {
        for (int i = 0; i < size + 1; i++) {
            a[i] = b[i];
        }
        return a;
    }

    public void convertToTriangle() {
        double[] temp = new double[size + 1];
        for (int i = 0; i < size; i++) {
            leadingElements[i] = extendedMtr[i][i];
            extendedMtr[i] = divideArray(extendedMtr[i], extendedMtr[i][i]);
            for (int j = i + 1; j < size; j++) {
                temp = copyString(temp, extendedMtr[i]);
                temp = multiplyArray(temp, extendedMtr[j][i]);
                extendedMtr[j] = subtractArrays(extendedMtr[j], temp);
            }
        }
    }

    public void calcRoots() {
        for (int i = 0; i < size; i++) {
            roots[varNum[size - 1 - i]] = extendedMtr[size - 1 - i][size];
            for (int j = 0; j < i; j++) {
                roots[varNum[size - 1 - i]] -= roots[varNum[size - 1 - j]] * extendedMtr[size - 1 - i][size - 1 - j];
            }
        }
    }

    public void calcDeterminant() {
        double det = 1;
        for (int i = 0; i < size; i++) {
            det *= leadingElements[i];
        }
        determinant = det;
    }

    public void calcResidualVector() {
        double[][] mtrA = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                mtrA[i][j] = extendedMtr[i][j];
            }
        }
        double[][] mtrX = new double[size][1];
        for (int i = 0; i < size; i++) {
            mtrX[i][0] = roots[i];
        }
        double[][] mtrB = new double[size][1];
        for (int i = 0; i < size; i++) {
            mtrB[i][0] = extendedMtr[i][5];
        }
        double[][] res = Main.mulMatrix(mtrA, size, size, mtrX, 1);
        res = Main.subMatrix(res, mtrB, size, 1);
        for (int i = 0; i < size; i++) {
            residualVector[i] = res[i][0];
        }
    }

    public double[] getRoots() {
        return roots;
    }
}
