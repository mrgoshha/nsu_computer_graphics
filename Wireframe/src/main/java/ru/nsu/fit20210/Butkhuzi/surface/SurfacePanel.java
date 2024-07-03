package ru.nsu.fit20210.Butkhuzi.surface;

import ru.nsu.fit20210.Butkhuzi.spline.Spline;
import ru.nsu.fit20210.Butkhuzi.spline.SplinePanel;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SurfacePanel extends JPanel implements MouseInputListener, MouseWheelListener {
    private BufferedImage img;
    private Point2D.Double mousePoint;
    private Surface surface;
    private ArrayList<Point2D.Double> splinePoints;
    public Spline spline;
    public SplinePanel splinePanel;

    private int width;
    private int height;

    private double rotY = 20;
    private double rotX = 0;
    private double rotZ = 20;

    public int parameterN = 3;
    public int parameterM = 5;
    public int parameterM1 = 2;

    public boolean full = false;


    public SurfacePanel(int width, int height) {
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        surface = new Surface(width, height);

        paintSurfaceImage();

        addMouseListener(this);
        // слежение за мышкой
        addMouseMotionListener(this);
        addMouseWheelListener(this);
    }

    public void setSplinePoints() {
        splinePoints = spline.getSplinePoints();
        paintSurfaceImage();
    }

    public void setParameterN(int n) {
        parameterN = n;
    }

    public void setParameterM(int m) {
        parameterM = m;

    }

    public void setParameterM1(int m1) {
        parameterM1 = m1;
    }


    public void setSplinePanel(SplinePanel panel, Spline spline) {
        this.splinePanel = panel;
        this.spline = spline;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(img, 0, 0, null);
        g2.dispose();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        mousePoint = new Point2D.Double(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        mousePoint = null;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getPreciseWheelRotation() < 0) {
            if (surface.near - 0.1 > 0)
                surface.near -= 0.1;
        } else {
            if (surface.near + 0.1 <= 7)
                surface.near += 0.1;

        }
        paintSurfaceImage();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point2D.Double currentPoint = new Point2D.Double(e.getX(), e.getY());
        Point2D.Double difference = new Point2D.Double(currentPoint.x - mousePoint.x, currentPoint.y - mousePoint.y);

        rotY += difference.x / 8d;
        rotZ += difference.y / 8d;

        mousePoint = currentPoint;
        paintSurfaceImage();
    }

    private void paintSurfaceImage() {
        Graphics2D g2 = img.createGraphics();
        // фон
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, width, height);

        g2.setColor(Color.RED);
        g2.setStroke(new BasicStroke(2.5f));

        // рисуем фигуру
        if (splinePoints != null) {
            surface.calculateSurfacePoints(splinePoints, rotX, rotY, rotZ, parameterM * parameterM1);

            for (int point = 0; point < surface.countSplinePoints; point++) {
                for (int angle = 0; angle < surface.countCirclePoints; angle++) {
                    int idx = (point * surface.countCirclePoints + angle);
                    if (point % parameterN == 0 || full) {
                        int nextIdx = point * surface.countCirclePoints + (angle + 1) % surface.countCirclePoints;
                        g2.drawLine((int) surface.getSurfacePoints().get(idx)[0], (int) surface.getSurfacePoints().get(idx)[1],
                                (int) surface.getSurfacePoints().get(nextIdx)[0], (int) surface.getSurfacePoints().get(nextIdx)[1]);
                    }
                    if (point != 0 && (angle % parameterM1 == 0 || full)) {
                        int prevIdx = (point - 1) * surface.countCirclePoints + angle;
                        g2.drawLine((int) surface.getSurfacePoints().get(idx)[0], (int) surface.getSurfacePoints().get(idx)[1],
                                (int) surface.getSurfacePoints().get(prevIdx)[0], (int) surface.getSurfacePoints().get(prevIdx)[1]);
                    }

                }
            }
        }
        repaint();

    }

    public void reset() {
        rotZ = 0;
        rotY = 0;
        paintSurfaceImage();
    }

    public ArrayList<Point> getPoints() {
        return spline.getPoints();
    }


}
