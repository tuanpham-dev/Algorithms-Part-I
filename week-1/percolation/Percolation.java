import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int size, top, bottom;
    private boolean[][] sites;
    private int openSites;
    private final WeightedQuickUnionUF unionFind, uf;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n is not valid");
        }

        size = n;
        top = 0;
        bottom = size * size + 1;
        sites = new boolean[size][size];
        openSites = 0;
        unionFind = new WeightedQuickUnionUF(size * size + 2);
        uf = new WeightedQuickUnionUF(size * size + 1);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sites[i][j] = false;
            }
        }
    }

    private int translatePosition(int row, int col) {
        return row * size + col + 1;
    }

    private void validateInput(int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size) {
            throw new IllegalArgumentException("row or col is not valid");
        }
    }

    public void open(int row, int col) {
        validateInput(row, col);

        if (isOpen(row, col)) {
            return;
        }

        int zbRow = row - 1;
        int zbCol = col - 1;

        sites[zbRow][zbCol] = true;
        openSites++;

        if (zbRow == 0) {
            unionFind.union(translatePosition(zbRow, zbCol), top);
            uf.union(translatePosition(zbRow, zbCol), top);
        }
        else if (sites[zbRow - 1][zbCol]) {
            unionFind.union(translatePosition(zbRow - 1, zbCol), translatePosition(zbRow, zbCol));
            uf.union(translatePosition(zbRow - 1, zbCol), translatePosition(zbRow, zbCol));
        }

        if (zbRow == size - 1) {
            unionFind.union(translatePosition(zbRow, zbCol), bottom);
        }
        else if (sites[zbRow + 1][zbCol]) {
            unionFind.union(translatePosition(zbRow + 1, zbCol), translatePosition(zbRow, zbCol));
            uf.union(translatePosition(zbRow + 1, zbCol), translatePosition(zbRow, zbCol));
        }

        if (zbCol > 0 && sites[zbRow][zbCol - 1]) {
            unionFind.union(translatePosition(zbRow, zbCol - 1), translatePosition(zbRow, zbCol));
            uf.union(translatePosition(zbRow, zbCol - 1), translatePosition(zbRow, zbCol));
        }

        if (zbCol < size - 1 && sites[zbRow][zbCol + 1]) {
            unionFind.union(translatePosition(zbRow, zbCol + 1), translatePosition(zbRow, zbCol));
            uf.union(translatePosition(zbRow, zbCol + 1), translatePosition(zbRow, zbCol));
        }
    }

    public boolean isOpen(int row, int col) {
        validateInput(row, col);

        return sites[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        validateInput(row, col);

        return uf.find(top) == uf.find(translatePosition(row - 1, col - 1));
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return unionFind.find(top) == unionFind.find(bottom);
    }

    public static void main(String[] args) {
        // pass
    }
}
