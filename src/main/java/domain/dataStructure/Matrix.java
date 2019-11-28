package domain.dataStructure;

public class Matrix<Object> {
    private Object[][] elements;
    private int numberOfRows;
    private int numberOfColumns;

    public Matrix(int numberOfRows, int numberOfColumns, Object[][] elements) {
        this.elements = elements;
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
    }

    public Object[][] getElements() {
        return this.elements;
    }

    public void setElements(Object[][] elements) {
        this.elements = elements;
    }

    public void setElementAt(Object element, int i, int j) {
        this.elements[i][j] = element;
    }

    public Object getElementAt(int i, int j) {
        return this.elements[i][j];
    }

    public int getNumberOfRows() {
        return this.numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public int getNumberOfColumns() {
        return this.numberOfColumns;
    }

    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix<?> matrix = (Matrix<?>) o;
        return numberOfRows == matrix.numberOfRows && numberOfColumns == matrix.numberOfColumns && compareMatrix(matrix);
    }


    private boolean compareMatrix(Matrix<?> matrix) {
        boolean equal = true;
        for (int i = 0; i < elements.length && equal; ++i) {
            for (int j = 0; j < elements[i].length && equal; ++j) {
                equal = (elements[i][j]).equals(matrix.elements[i][j]);
            }
        }
        return equal;
    }
}
