import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Solver {
    private final ArrayList<Board> solution = new ArrayList<>();

    private class Node {
        private final Board board;
        private final Node previous;
        private final int manhattan;
        private final int moves;

        private Node(Board board, Node previous, int moves) {
            this.board = board;
            this.previous = previous;
            this.moves = moves;
            this.manhattan = board.manhattan() + moves;
        }
    }

    private class ManhattanComparator implements Comparator<Node> {
        public int compare(Node node1, Node node2) {
            return node1.manhattan - node2.manhattan;
        }
    }

    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        MinPQ<Node> pq = new MinPQ<>(new ManhattanComparator());
        MinPQ<Node> pqTwin = new MinPQ<>(new ManhattanComparator());

        Node node = new Node(initial, null, 0);
        Node twinNode = new Node(initial.twin(), null, 0);

        pq.insert(node);
        pqTwin.insert(twinNode);

        while (!node.board.isGoal() && !twinNode.board.isGoal()) {
            node = pq.delMin();

            for (Board neighbor : node.board.neighbors()) {
                if (node.previous == null || !node.previous.board.equals(neighbor)) {
                    pq.insert(new Node(neighbor, node, node.moves + 1));
                }
            }


            twinNode = pqTwin.delMin();

            for (Board neighbor : twinNode.board.neighbors()) {
                if (twinNode.previous == null || !twinNode.previous.board.equals(neighbor)) {
                    pqTwin.insert(new Node(neighbor, twinNode, twinNode.moves + 1));
                }
            }
        }

        if (node.board.isGoal()) {
            while (node != null) {
                solution.add(node.board);
                node = node.previous;
            }

            Collections.reverse(solution);
        }
    }

    public boolean isSolvable() {
        return !solution.isEmpty();
    }

    public int moves() {
        return solution.size() - 1;
    }

    public Iterable<Board> solution() {
        if (solution.isEmpty()) {
            return null;
        }

        return solution;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
