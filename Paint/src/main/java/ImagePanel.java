// package ru.nsu.fit.g20210.butkhuzi;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;


public class ImagePanel extends JPanel implements MouseListener {
    private DrawShape drawShape;
    public ImageResized imageResized;
    public Color color = Color.BLACK;
    public Color newColor;

    public boolean draw = true;
    public boolean isPolygon = false;
    public boolean isStar = false;
    public boolean isLine = false;
    public boolean isFillShape = false;

    //parameters
    public int thickness;
    public int countSides;
    public int radius;
    public int pivot;

    public BufferedImage img;

    private int start_x, start_y, end_x, end_y;

    public ImagePanel(Dimension d) {
        drawShape = new DrawShape(this);
        imageResized = new ImageResized(this);

        img = imageResized.newImage(d.width, d.height);

        addMouseListener(this);
        // работа с изображением
        // - увеличение размера изображения при необходимости
        // - вставка загруженного изображения
        addComponentListener(imageResized);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (draw) {
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(img, 0, 0, null);
            g2.dispose();
        }
    }

    @Override
    public void mouseClicked(MouseEvent ev) {
        if (isPolygon)
            drawShape.polygon(countSides, ev.getX(), ev.getY(), radius, pivot);
        else if (isStar)
            drawShape.star(countSides, ev.getX(), ev.getY(), radius, pivot);
        else if (isFillShape)
            drawShape.span(ev.getX(), ev.getY());
    }

    @Override
    public void mouseEntered(MouseEvent ev) {
    }

    @Override
    public void mouseExited(MouseEvent ev) {
    }

    @Override
    public void mousePressed(MouseEvent ev) {
        start_x = end_x = ev.getX();
        start_y = end_y = ev.getY();
    }

    @Override
    public void mouseReleased(MouseEvent ev) {
        end_x = ev.getX();
        end_y = ev.getY();
        if (isLine) {
            drawShape.line(start_x, start_y, end_x, end_y, true);
            if (thickness > 1) {
                drawShape.line(start_x, start_y, end_x, end_y, thickness);
            }
        }
    }

    public void line(int thickness) {
        this.thickness = thickness;
    }

    public void polygon(int countSides, int diameter, int pivot) {
        this.countSides = countSides;
        this.radius = diameter / 2;
        this.pivot = pivot;
    }

    public void clean() {
        imageResized.clean(img);
    }

    public void setRed() {
        color = Color.RED;
    }

    public void setYellow() {
        color = Color.YELLOW;
    }

    public void setGreen() {
        color = Color.GREEN;
    }

    public void setBlue() {
        color = Color.CYAN;
    }

    public void setDarkBlue() {
        color = Color.BLUE;
    }

    public void setViolet() {
        color = (new Color(140, 90, 180));
    }

    public void setWhite() {
        color = Color.white;
    }

    public void setNewColor(Color color) {
        this.color = color;

    }
}




