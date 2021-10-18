import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final ArrayList<LineSegment> lineSegments = new ArrayList<>();
    private final ArrayList<Point> addedPoints = new ArrayList<>();
    private final ArrayList<Double> slopes = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
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

        Point originPoint, minPoint, maxPoint;
        ArrayList<Point> otherPoints;

        for (int p = 0; p < n - 3; p++) {
            originPoint = points[p];
            minPoint = originPoint;
            maxPoint = originPoint;
            otherPoints = new ArrayList<>(Arrays.asList(points).subList(p + 1, n));
            otherPoints.sort(originPoint.slopeOrder());

            int count = 0;
            Point previousPoint = null;
            double previousSlope = 0;

            for (Point point : otherPoints) {
                if (previousPoint == null || previousSlope != originPoint.slopeTo(point)) {
                    if (count >= 3) {
                        addSegment(minPoint, maxPoint);
                    }

                    previousPoint = point;
                    minPoint = originPoint;
                    maxPoint = originPoint;
                    previousSlope = originPoint.slopeTo(previousPoint);
                    count = 1;
                }
                else {
                    count++;
                }

                if (point.compareTo(minPoint) < 0) {
                    minPoint = point;
                }

                if (point.compareTo(maxPoint) > 0) {
                    maxPoint = point;
                }
            }

            if (count >= 3) {
                addSegment(minPoint, maxPoint);
            }
        }
    }

    private void addSegment(Point minPoint, Point maxPoint) {
        int n = addedPoints.size();

        for (int i = 0; i < n; i++) {
            Point currentPoint = addedPoints.get(i);
            double currentSlope = slopes.get(i);
            double slopeToMin = currentPoint.slopeTo(minPoint);
            double slopeToMax = currentPoint.slopeTo(maxPoint);

            if ((Double.compare(slopeToMin, Double.NEGATIVE_INFINITY) == 0 || Double
                    .compare(slopeToMin, currentSlope) == 0)
                    && Double.compare(slopeToMax, currentSlope) == 0) {
                return;
            }
        }

        addedPoints.add(minPoint);
        slopes.add(minPoint.slopeTo(maxPoint));
        lineSegments.add(new LineSegment(minPoint, maxPoint));
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
