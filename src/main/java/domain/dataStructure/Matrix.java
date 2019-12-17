package domain.dataStructure;

public class Matrix<Object> {
    private Object[][] elements;
    private int numberOfRows;
    private int numberOfColumns;

    /**
     * Constructs a new {@link Matrix}
     *
     * @param numberOfRows    the number of rows
     * @param numberOfColumns the number of columns
     * @param elements        the matrix elements
     */
    public Matrix(int numberOfRows, int numberOfColumns, Object[][] elements) {
        this.elements = elements;
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
    }

    /**
     * Sets an element at a given position
     *
     * @param element the element to add
     * @param i       the row position index
     * @param j       the column position index
     */
    public void setElementAt(Object element, int i, int j) {
        this.elements[i][j] = element;
    }

    /**
     * Gets an element from a given position
     *
     * @param i the row position index
     * @param j the column position index
     * @return the element in that position
     */
    public Object getElementAt(int i, int j) {
        return this.elements[i][j];
    }

    /**
     * Gets the number of rows
     *
     * @return the number of rows
     */
    public int getNumberOfRows() {
        return this.numberOfRows;
    }

    /**
     * Gets the number of columns
     *
     * @return the number of columns
     */
    public int getNumberOfColumns() {
        return this.numberOfColumns;
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
