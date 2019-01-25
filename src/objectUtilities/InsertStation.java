package objectUtilities;

import canvasObjects.Connection;
import canvasObjects.MetroLine;
import canvasObjects.Station;
import java.util.ArrayList;

public class InsertStation {

    static class Point {

        double x;
        double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public static int findWhereToInsert(MetroLine line, Station station) {
        int index = 0;
        double minDistance = Double.MAX_VALUE;
        Point target = new Point(station.getCircle().getCenterX(), station.getCircle().getCenterY());
        for (int i = 0; i < line.getConnections().size(); i++) {
            Connection c = line.getConnections().get(i);
            Point minPoint = minPoint(
                    new Point(c.getCurve().getStartX(), c.getCurve().getStartY()),
                    new Point(c.getCurve().getControlX(), c.getCurve().getControlY()),
                    new Point(c.getCurve().getEndX(), c.getCurve().getEndY()),
                    target
            );
            double temp = distance(target, minPoint);
            if (temp < minDistance) {
                minDistance = temp;
                index = i;
            }
        }
        return index;
    }

    private static Point minPoint(Point p0, Point p1, Point p2, Point p3) {
        
        double [] roots  = findRoots(p0, p1, p2, p3);
        double t = bestRoot(p0, p1, p2, p3, roots);
        Point min = new Point(
                    (1 - t)*(1 - t) * p0.x + 2 * (1 - t) * t * p1.x + t * t * p2.x,
                    (1 - t)*(1 - t) * p0.y + 2 * (1 - t) * t * p1.y + t * t * p2.y
        );
        return min;
    }

    private static double bestRoot(Point p0, Point p1, Point p2, Point p3, double[] roots) {
        double bestRoot = 1.0;

        ArrayList<Double> goodRoots = new ArrayList();
        for (double root : roots) {
            if (root >= 0 && root <= 1) {
                goodRoots.add(root);
            }
        }
        if (goodRoots.isEmpty()) {
            if (distance(p0, p3) < distance(p2, p3))
                bestRoot = 0;
        }
        else {
            double minDistance = Double.MAX_VALUE;
            for (int i = 0; i < goodRoots.size(); i++) {
                double t = goodRoots.get(i);
                Point temp = new Point(
                                (1 - t)*(1 - t) * p0.x + 2 * (1 - t) * t * p1.x + t * t * p2.x,
                                (1 - t)*(1 - t) * p0.y + 2 * (1 - t) * t * p1.y + t * t * p2.y
                );
                double distance = distance(temp, p3);
                if (distance < minDistance) {
                    minDistance = distance;
                    bestRoot = t;
                }
            } 
        }
            return bestRoot;
    }

    private static double[] findRoots(Point p0, Point p1, Point p2, Point p3) {
        double cubic = 4 * p0.x * p0.x + 16 * p1.x * p1.x + 4 * p2.x * p2.x - 16 * p0.x * p1.x
                + 8 * p0.x * p2.x - 16 * p1.x * p2.x
                     + 4 * p0.y * p0.y + 16 * p1.y * p1.y + 4 * p2.y * p2.y - 16 * p0.y * p1.y
                + 8 * p0.y * p2.y - 16 * p1.y * p2.y;
        double quadratic = -12 * p0.x * p0.x - 24 * p1.x * p1.x + 36 * p0.x * p1.x - 12 * p0.x * p2.x + 12 * p1.x * p2.x
                - 12 * p0.y * p0.y - 24 * p1.y * p1.y + 36 * p0.y * p1.y - 12 * p0.y * p2.y + 12 * p1.y * p2.y;
        double linear = 12 * p0.x * p0.x + 8 * p1.x * p1.x - 24 * p0.x * p1.x + 4 * p0.x * p2.x - 4 * p0.x * p3.x
                + 8 * p1.x * p3.x - 4 * p2.x * p3.x
                + 12 * p0.y * p0.y + 8 * p1.y * p1.y - 24 * p0.y * p1.y + 4 * p0.y * p2.y - 4 * p0.y * p3.y
                + 8 * p1.y * p3.y - 4 * p2.y * p3.y;
        double constant = -4 * p0.x * p0.x + 4 * p0.x * p1.x + 4 * p0.x * p3.x - 4 * p1.x * p3.x
                - 4 * p0.y * p0.y + 4 * p0.y * p1.y + 4 * p0.y * p3.y - 4 * p1.y * p3.y;
        
        double[] roots;
        if (cubic != 0) {
            Cubic equation = new Cubic();
            equation.solve(cubic, quadratic, linear, constant);
            if (equation.nRoots == 1) {
                roots = new double[]{equation.x1};
            } else {
                roots = new double[]{
                    equation.x1, equation.x2, equation.x3
                };
            }
        } else {
            if (quadratic != 0) {
                Quadratic equation = new Quadratic();
                equation.solve(quadratic, linear, constant);
                if (equation.nRoots == 2) {
                    roots = new double[]{
                        equation.x1, equation.x2
                    };
                } else {
                    roots = new double[]{};
                }
            } else {
                roots = new double[]{-constant / linear};
            }
        }
        return roots;
    }

    private static double distance(Point p0, Point p1) {
        return Math.sqrt((p0.x - p1.x) * (p0.x - p1.x) + (p0.y - p1.y) * (p0.y - p1.y));
    }

}
