import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class BruteCollinearPoints {
    private final ArrayList<LineSegment> lineSegments = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        int n = points.length;

        for (int i = 0; i < n; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
        }

        ArrayList<Point> pointList = new ArrayList<>(Arrays.asList(points));
        points = pointList.toArray(new Point[0]);
        Arrays.sort(points);

        if (n > 1) {
            for (int i = 0; i < n - 1; i++) {
                if (points[i].equals(points[i + 1])) {
                    throw new IllegalArgumentException();
                }
            }
        }

        if (n < 4) {
            return;
        }

        Comparator<Point> comparator;

        for (int p = 0; p < n - 3; p++) {
            comparator = points[p].slopeOrder();

            for (int q = p + 1; q < n - 2; q++) {
                for (int r = q + 1; r < n - 1; r++) {
                    if (comparator.compare(points[q], points[r]) != 0) {
                        continue;
                    }

                    for (int s = r + 1; s < n; s++) {
                        if (comparator.compare(points[q], points[s]) == 0) {
                            lineSegments.add(new LineSegment(points[p], points[s]));
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return lineSegments.size();
    }

    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
