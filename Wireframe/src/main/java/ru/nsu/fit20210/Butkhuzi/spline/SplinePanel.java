package ru.nsu.fit20210.Butkhuzi.spline;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class SplinePanel extends JPanel implements MouseInputListener {
    private BufferedImage img;
    private Point mousePoint;
    private int radius = 8;
    private int movePoint = -1;
    public Spline spline;
    private int width, height;
    public boolean addPoint = false;
    public boolean removePoint = false;
    public boolean editPosPoint = true;

    public int parameterN = 3;

    public SplinePanel(int width, int height) {
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        spline = new Spline(img);
        paintSplineImage();

        addMouseListener(this);
        // слежение за мышкой
        addMouseMotionListener(this);
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
        boolean isPoint = false;
        if (addPoint) {
            for (int i = 0; i < spline.getPoints().size(); i++) {
                if (Math.pow((double) e.getX() - spline.getPoints().get(i).x, 2) +
                        Math.pow((double) e.getY() - spline.getPoints().get(i).y, 2) <= Math.pow(radius, 2)) {
                    isPoint = true;
                    break;
                }
            }
            if (!isPoint)
                spline.addPoint(new Point(e.getX(), e.getY()));
        } else if (removePoint && spline.getPoints().size() > 4) {
            for (int i = 0; i < spline.getPoints().size(); i++) {
                if (Math.pow((double) e.getX() - spline.getPoints().get(i).x, 2) +
                        Math.pow((double) e.getY() - spline.getPoints().get(i).y, 2) <= Math.pow(radius, 2)) {
                    spline.removePoint(i);
                    break;
                }
            }
        }
        paintSplineImage();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // если зажимается точка в круге, то мы предвигаем точку
        for (int i = 0; i < spline.getPoints().size(); i++) {
            if (Math.pow((double) e.getX() - spline.getPoints().get(i).x, 2) +
                    Math.pow((double) e.getY() - spline.getPoints().get(i).y, 2) <= Math.pow(radius, 2)) {
                movePoint = i;
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        movePoint = -1;
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
    public void mouseDragged(MouseEvent e) {
        if (movePoint != -1) {
            spline.getPoints().get(movePoint).move(e.getX(), e.getY());
            paintSplineImage();
        }
    }

    public void paintSplineImage() {
        Graphics2D g2 = img.createGraphics();
        // фон
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, width, height);
        // оси
        g2.setColor(Color.WHITE);
        g2.drawLine(0, height / 2, width, height / 2);
        g2.drawLine(width / 2, 0, width / 2, height);
        // точки
        g2.setColor(Color.MAGENTA);
        if (!spline.getPoints().isEmpty()) {
            for (int i = 0; i < spline.getPoints().size(); i++) {
                if (movePoint == i) {
                    g2.setStroke(new BasicStroke(2.5f));
                }
                g2.drawOval(spline.getPoints().get(i).x - radius, spline.getPoints().get(i).y - radius, radius * 2, radius * 2);
                g2.setStroke(new BasicStroke(1));
            }
        }
        // линии
        g2.setColor(Color.MAGENTA.darker());
        for (int i = 0; i < spline.getPoints().size() - 1; i++) {
            g2.drawLine(spline.getPoints().get(i).x, spline.getPoints().get(i).y,
                    spline.getPoints().get(i + 1).x, spline.getPoints().get(i + 1).y);
        }
        // сплайн
        g2.setColor(Color.CYAN);
        spline.calculateSplinePoints(parameterN);
        if (spline.getPoints().size() >= 4) {
            for (int i = 0; i < spline.getSplinePoints().size() - 1; i++) {
                g2.drawLine((int) spline.getSplinePoints().get(i).x, (int) spline.getSplinePoints().get(i).y,
                        (int) spline.getSplinePoints().get(i + 1).x, (int) spline.getSplinePoints().get(i + 1).y);
            }
        }
        repaint();
    }

    public void setParameterN(int n) {
        parameterN = n;
        paintSplineImage();
    }
}

