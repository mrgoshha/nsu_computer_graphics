package ru.nsu.fit20210.Butkhuzi.surface;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Surface {
    private ArrayList<double[]> surfacePoints;

    private double[] translateMatrix;
    private double[] scaleMatrix;
    private double[] cameraMatrix;
    private double[] perspectiveMatrix;
    private double[] rotateXMatrix;
    private double[] rotateYMatrix;
    private double[] rotateZMatrix;


    private double[] rotateMatrix;


    double rotX = 0;
    double rotY = 0;
    double rotZ = 0;
    public double zn = 500;
    double zf = 1000;

    public int countSplinePoints;
    public int countCirclePoints;

    private int width;
    private int height;

    private double far = 10;
    public double near = 0.7;

    public double[] axis = {0, 1, 0};

    double xMin = Double.MAX_VALUE;
    double xMax = -Double.MAX_VALUE;
    double yMin = Double.MAX_VALUE;
    double yMax = -Double.MAX_VALUE;
    double zMin = Double.MAX_VALUE;
    double zMax = -Double.MAX_VALUE;

    private double[] getTranslationMatrix(double dx, double dy, double dz) {
        return new double[]{1, 0, 0, dx,
                0, 1, 0, dy,
                0, 0, 1, dz,
                0, 0, 0, 1};
    }

    private double[] getScaleMatrix(double sx, double sy, double sz) {
        return new double[]{sx, 0, 0, 0,
                0, sy, 0, 0,
                0, 0, sz, 0,
                0, 0, 0, 1};
    }

    private double getLength(double[] v) {
        return Math.sqrt(
                v[0] * v[0] + v[1] * v[1] + v[2] * v[2]
        );
    }

    public double[] normalize(double[] v) {
        double length = getLength(v);

        v[0] /= length;
        v[1] /= length;
        v[2] /= length;

        return v;
    }

    private double[] crossProduct(double[] v1, double[] v2) {
        return new double[]{
                v1[1] * v2[2] - v1[2] * v2[1],
                v1[2] * v2[0] - v1[0] * v2[2],
                v1[0] * v2[1] - v1[1] * v2[0],
        };
    }

    private double[] getCameraMatrix() {
        double[] PCam = {-10, 0, 0};
        double[] PView = {10, 0, 0};
        double[] PUp = {0, 1, 0};

        double vz[] = {PCam[0] - PView[0], PCam[1] - PView[1], PCam[2] - PView[2]};
        vz = normalize(vz);
        double vx[] = crossProduct(PUp, vz);
        vx = normalize(vx);
        double vy[] = crossProduct(vz, vx);
        vy = normalize(vy);

        double[] M1 = getTranslationMatrix(-PCam[0], -PCam[1], -PCam[2]);
        double[] M2 = new double[]{
                vx[0], vx[1], vx[2], 0,
                vy[0], vy[1], vy[2], 0,
                vz[0], vz[1], vz[2], 0,
                0, 0, 0, 1};

        double[] cameraMatrix = new double[]
                {0, 0, 0, 0,
                        0, 0, 0, 0,
                        0, 0, 0, 0,
                        0, 0, 0, 0};

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    cameraMatrix[i * 4 + j] += M1[i * 4 + k] * M2[k * 4 + j];
                }
            }
        }

        return cameraMatrix;
    }

    private double[] getRotateMatrix(double angle, double[] axis) {
        double rAngle = Math.toRadians(angle);
        double P = 1 - Math.cos(rAngle);
        return new double[]{
                Math.cos(rAngle) + Math.pow(axis[0], 2) * P, axis[0] * axis[1] * P - axis[2] * Math.sin(rAngle), axis[0] * axis[2] * P + axis[1] * Math.sin(rAngle), 0,
                axis[1] * axis[0] * P + axis[2] * Math.sin(rAngle), Math.cos(rAngle) + Math.pow(axis[1], 2) * P, axis[1] * axis[2] * P - axis[0] * Math.sin(rAngle), 0,
                axis[2] * axis[0] * P - axis[1] * Math.sin(rAngle), axis[2] * axis[1] * P + axis[0] * Math.sin(rAngle), Math.cos(rAngle) + Math.pow(axis[2], 2) * P, 0,
                0, 0, 0, 1.0};
    }


    private double[] getRotateXMatrix(double angle) {
        double rAngle = Math.toRadians(angle);
        return new double[]{1, 0, 0, 0,
                0, Math.cos(rAngle), -Math.sin(rAngle), 0,
                0, Math.sin(rAngle), Math.cos(rAngle), 0,
                0, 0, 0, 1};
    }

    private double[] getRotateYMatrix(double angle) {
        double rAngle = Math.toRadians(angle);
        return new double[]{Math.cos(rAngle), 0, Math.sin(rAngle), 0,
                0, 1, 0, 0,
                -Math.sin(rAngle), 0, Math.cos(rAngle), 0,
                0, 0, 0, 1};
    }

    private double[] getRotateZMatrix(double angle) {
        double rAngle = Math.toRadians(angle);
        return new double[]{Math.cos(rAngle), -Math.sin(rAngle), 0, 0,
                Math.sin(rAngle), Math.cos(rAngle), 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1};
    }

    private double[] getPerspectiveProjection() {
        var a = 600d / 800;
        var f = 1.0 / Math.tan(90 * (Math.PI / 180.0) / 2);
        var q = far / (far - near);
        return new double[]{
                a * f, 0, 0, 0,
                0, f, 0, 0,
                0, 0, q, 1,
                0, 0, -near * q, 0
        };
    }


    public Surface(int width, int height) {
        this.width = width;
        this.height = height;
        surfacePoints = new ArrayList<>();
        cameraMatrix = getCameraMatrix();
    }

    public void calculateSurfacePoints(ArrayList<Point2D.Double> spline, double rotX, double rotY, double rotZ, int MM1) {
        surfacePoints = new ArrayList<>();

        double step = 360d / MM1;
        double angle = 0;
        countSplinePoints = spline.size();
        countCirclePoints = MM1;
        for (int point = 0; point < spline.size(); point++) {
            double x = spline.get(point).y - 400 / 2d;
            double y = spline.get(point).y - 400 / 2d; // высота окна spline
            double z = spline.get(point).x - 600 / 2d;
            for (int a = 0; a < MM1; a++) {
                double radian = Math.toRadians(angle);
                double newX = x * Math.cos(radian);
                double newY = y * Math.sin(radian);

                xMax = Math.max(xMax, newX);
                xMin = Math.min(xMin, newX);
                yMax = Math.max(yMax, newY);
                yMin = Math.min(yMin, newY);
                zMax = Math.max(zMax, z);
                zMin = Math.min(zMin, z);

                addPoint(newX, newY, z);
                angle += step;
            }
        }
        build3DScene(rotX, rotY, rotZ);
    }

    public void build3DScene(double rotX, double rotY, double rotZ) {
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;

        normalize();
        scale(100, 100, 100);

        rotation();
        translation(700, 0, 0);
        projection();
        perspective();
        z();
        drawPixel();
    }

    private void addSurfacePoints() {
        addPoint(-1, 1, 1);// 0 вершина
        addPoint(-1, 1, -1);// 1 вершина
        addPoint(1, 1, -1);// 2 вершина
        addPoint(1, 1, 1);// 3 вершина
        addPoint(-1, -1, 1);// 4 вершина
        addPoint(-1, -1, -1);// 5 вершина
        addPoint(1, -1, -1);// 6 вершина
        addPoint(1, -1, 1);// 7 вершина
    }


    private void z() {
        for (int i = 0; i < surfacePoints.size(); i++) {
            surfacePoints.get(i)[0] = (surfacePoints.get(i)[0] / (surfacePoints.get(i)[3])) * (width);
            surfacePoints.get(i)[1] = (surfacePoints.get(i)[1] / (surfacePoints.get(i)[3])) * (height);
        }
    }

    private void addPoint(double x, double y, double z) {
        surfacePoints.add(new double[]{x, y, z, 1});
    }

    private void normalize() {
        double xDiff = (xMax - xMin) / 2d;
        double yDiff = (yMax - yMin) / 2d;
        double zDiff = (zMax - zMin) / 2d;

        double xMove = (xMax + xMin) / 2d;
        double yMove = (yMax + yMin) / 2d;
        double zMove = (zMax + zMin) / 2d;

        double maxDiff = Math.max(xDiff, Math.max(yDiff, zDiff));
        double factor = 1d / maxDiff;

        translation(-xMove, -yMove, -zMove);
        scale(factor, factor, factor);
    }


    private void projection() {
        for (int i = 0; i < surfacePoints.size(); i++) {
            double[] newPoint = {0, 0, 0, 0};
            for (int n = 0; n < 4; n++) {
                for (int k = 0; k < 4; k++) {
                    newPoint[n] += cameraMatrix[n * 4 + k] * surfacePoints.get(i)[k];
                }
            }
            surfacePoints.set(i, newPoint);
        }
    }

    private void perspective() {
        perspectiveMatrix = getPerspectiveProjection();
        for (int i = 0; i < surfacePoints.size(); i++) {
            double[] newPoint = {0, 0, 0, 0};
            for (int n = 0; n < 4; n++) {
                for (int k = 0; k < 4; k++) {
                    newPoint[n] += perspectiveMatrix[n * 4 + k] * surfacePoints.get(i)[k];
                }
            }
            surfacePoints.set(i, newPoint);
        }
    }

    private void scale(double x, double y, double z) {
        scaleMatrix = getScaleMatrix(x, y, z);
        for (int i = 0; i < surfacePoints.size(); i++) {
            double[] newPoint = {0, 0, 0, 0};
            for (int n = 0; n < 4; n++) {
                for (int k = 0; k < 4; k++) {
                    newPoint[n] += scaleMatrix[n * 4 + k] * surfacePoints.get(i)[k];
                }
            }
            surfacePoints.set(i, newPoint);
        }
    }

    private void translation(double dx, double dy, double dz) {
        translateMatrix = getTranslationMatrix(dx, dy, dz);
        for (int i = 0; i < surfacePoints.size(); i++) {
            double[] newPoint = {0, 0, 0, 0};
            for (int n = 0; n < 4; n++) {
                for (int k = 0; k < 4; k++) {
                    newPoint[n] += translateMatrix[n * 4 + k] * surfacePoints.get(i)[k];
                }
            }
            surfacePoints.set(i, newPoint);
        }
    }

    private void rotation() {

        //по Х
        rotateXMatrix = getRotateXMatrix(rotX);
        for (int i = 0; i < surfacePoints.size(); i++) {
            double[] newPoint = {0, 0, 0, 0};
            for (int n = 0; n < 4; n++) {
                for (int k = 0; k < 4; k++) {
                    newPoint[n] += rotateXMatrix[n * 4 + k] * surfacePoints.get(i)[k];
                }
            }
            surfacePoints.set(i, newPoint);
        }

        // по Y
        rotateYMatrix = getRotateYMatrix(rotY);
        for (int i = 0; i < surfacePoints.size(); i++) {
            double[] newPoint = {0, 0, 0, 0};
            for (int n = 0; n < 4; n++) {
                for (int k = 0; k < 4; k++) {
                    newPoint[n] += rotateYMatrix[n * 4 + k] * surfacePoints.get(i)[k];
                }
            }
            surfacePoints.set(i, newPoint);
        }
        // по Z
        rotateZMatrix = getRotateZMatrix(rotZ);
        for (int i = 0; i < surfacePoints.size(); i++) {
            double[] newPoint = {0, 0, 0, 0};
            for (int n = 0; n < 4; n++) {
                for (int k = 0; k < 4; k++) {
                    newPoint[n] += rotateZMatrix[n * 4 + k] * surfacePoints.get(i)[k];
                }
            }
            surfacePoints.set(i, newPoint);
        }

    }

    private void drawPixel() {
        for (int i = 0; i < surfacePoints.size(); i++) {
            surfacePoints.get(i)[1] = -surfacePoints.get(i)[1];
            surfacePoints.get(i)[0] += width / 2d; // перемещаем Х в центр канваса
            surfacePoints.get(i)[1] += (height - 100) / 2d; // перемещаем Y в центр канваса
        }
    }

    public ArrayList<double[]> getSurfacePoints() {
        return surfacePoints;
    }
}
