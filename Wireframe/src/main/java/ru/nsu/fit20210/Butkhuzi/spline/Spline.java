package ru.nsu.fit20210.Butkhuzi.spline;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Spline {
    private BufferedImage img;
    private ArrayList<Point> points;
    private ArrayList<Point2D.Double> splinePoints;
    private double[] matrixM = {-1 / 6d, 3 / 6d, -3 / 6d, 1 / 6d,
            3 / 6d, -6 / 6d, 3 / 6d, 0,
            -3 / 6d, 0, 3 / 6d, 0,
            1 / 6d, 4 / 6d, 1 / 6d, 0};


    public Spline(BufferedImage img) {
        this.img = img;
        points = new ArrayList<>();
        splinePoints = new ArrayList<>();
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public void removePoint(int idx) {
        points.remove(idx);
    }

    public void calculateSplinePoints(int count) {
        splinePoints = new ArrayList<>();
        // t * M * G
        double increment = 1d / count;
        for (int i = 1; i < points.size() - 2; i++) {
            // M * G - по х и у
            int[] Gx = {points.get(i - 1).x, points.get(i).x, points.get(i + 1).x, points.get(i + 2).x};
            int[] Gy = {points.get(i - 1).y, points.get(i).y, points.get(i + 1).y, points.get(i + 2).y};
            int[] MGx = new int[4];
            int[] MGy = new int[4];
            for (int n = 0; n < 4; n++) {
                MGx[n] = 0;
                MGy[n] = 0;
                for (int k = 0; k < 4; k++) {
                    MGx[n] += matrixM[n * 4 + k] * Gx[k];
                    MGy[n] += matrixM[n * 4 + k] * Gy[k];
                }
            }
            for (double t = 0; t <= 1; t += increment) {
                if (t == 0 && i != 1)
                    continue;//*/
                double[] tt = {Math.pow(t, 3), Math.pow(t, 2), t, 1};
                // t * M * G - по х и у
                double x = 0;
                double y = 0;
                for (int m = 0; m < 4; m++) {
                    x += (int) (tt[m] * MGx[m]);
                    y += (int) (tt[m] * MGy[m]);
                }
                Point2D.Double splinePoint = new Point2D.Double(x, y);
                splinePoints.add(splinePoint);
            }
        }
    }


    public ArrayList<Point> getPoints() {
        return this.points;
    }

    public ArrayList<Point2D.Double> getSplinePoints() {
        return this.splinePoints;
    }


    public void setPoints(ArrayList<Point> points) {
        this.points = points;
    }

}
