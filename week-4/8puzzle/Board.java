import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private final int[][] tiles;
    private final int dimension;
    private final int hamming;
    private final int manhattan;
    private final int blankRow, blankCol;

    public Board(int[][] tiles) {
        if (tiles == null) {
            throw new IllegalArgumentException();
        }

        dimension = tiles[0].length;
        this.tiles = cloneTiles(tiles);
        int hammingDistance = 0;
        int manhattanDistance = 0;
        int bRow = 0;
        int bCol = 0;


        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                if (tiles[row][col] != row * dimension + col + 1) {
                    if (tiles[row][col] == 0) {
                        bRow = row;
                        bCol = col;
                        continue;
                    }

                    int goalPosition = tiles[row][col] - 1;
                    hammingDistance++;
                    manhattanDistance += Math.abs(goalPosition / dimension - row) + Math
                            .abs(goalPosition % dimension - col);
                }
            }
        }

        blankRow = bRow;
        blankCol = bCol;
        hamming = hammingDistance;
        manhattan = manhattanDistance;
    }

    private int[][] cloneTiles(int[][] currentTiles) {
        int n = currentTiles[0].length;
        int[][] newTiles = new int[n][n];

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                newTiles[row][col] = currentTiles[row][col];
            }
        }

        return newTiles;
    }

    private Board neighborSwap(int row1, int col1, int row2, int col2) {
        int[][] newTiles = cloneTiles(tiles);
        int tmp = newTiles[row1][col1];
        newTiles[row1][col1] = newTiles[row2][col2];
        newTiles[row2][col2] = tmp;

        return new Board(newTiles);
    }

    public String toString() {
        StringBuilder output = new StringBuilder("\n" + dimension + "");

        for (int row = 0; row < dimension; row++) {
            output.append('\n');

            for (int col = 0; col < dimension; col++) {
                output.append(" " + tiles[row][col]);
            }
        }

        return output.toString();
    }

    public int dimension() {
        return dimension;
    }

    public int hamming() {
        return hamming;
    }

    public int manhattan() {
        return manhattan;
    }

    public boolean isGoal() {
        return hamming == 0;
    }

    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }

        if (getClass() != y.getClass()) {
            return false;
        }

        Board board = (Board) y;

        return Arrays.deepEquals(tiles, board.tiles);
    }

    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<>();

        if (blankRow > 0) {
            neighbors.add(neighborSwap(blankRow, blankCol, blankRow - 1, blankCol));
        }

        if (blankRow < dimension - 1) {
            neighbors.add(neighborSwap(blankRow, blankCol, blankRow + 1, blankCol));
        }

        if (blankCol > 0) {
            neighbors.add(neighborSwap(blankRow, blankCol, blankRow, blankCol - 1));
        }

        if (blankCol < dimension - 1) {
            neighbors.add(neighborSwap(blankRow, blankCol, blankRow, blankCol + 1));
        }

        return neighbors;
    }

    public Board twin() {
        int row, col;

        if (blankRow == 0) {
            row = blankRow + 1;
        }
        else {
            row = blankRow - 1;
        }

        if (blankCol == 0) {
            col = blankCol + 1;
        }
        else {
            col = blankCol - 1;
        }

        return neighborSwap(row, blankCol, row, col);
    }

    public static void main(String[] args) {
        int[][] tiles = {
                { 8, 1, 3 },
                { 4, 0, 2 },
                { 7, 6, 5 }
        };

        int[][] goalTiles = {
                { 1, 2, 3 },
                { 4, 5, 6 },
                { 7, 8, 0 }
        };

        Board board = new Board(tiles);

        System.out.println(board.hamming());
        System.out.println(board.manhattan());
        System.out.println(board.toString());

        for (Board neighbor : board.neighbors()) {
            System.out.println(neighbor.toString());
        }

        Board equalBoard = new Board(tiles);
        System.out.println(board.equals(equalBoard));

        Board goalBoard = new Board(goalTiles);
        System.out.println(goalBoard.isGoal());

        Board twinBoard = board.twin();
        System.out.println(board.toString());
        System.out.println(twinBoard.toString());
    }
}
