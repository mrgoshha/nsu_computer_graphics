import java.awt.*;

import java.util.Stack;


public class DrawShape {
    private ImagePanel p;
    private int borderColor = Color.BLACK.getRGB();

    DrawShape(ImagePanel panel) {
        this.p = panel;
    }

    private class Span {
        int right;
        int left;
        int y;

        Span(int r, int l, int y) {
            right = r;
            left = l;
            this.y = y;
        }

    }

    private Span findSpan(int x, int y) {
        int spanR = x;
        int spanL = x;

        while (checkBounds(spanR + 1, y) && p.img.getRGB(spanR + 1, y) != borderColor) {//  + граница
            spanR++;
        }
        while (checkBounds(spanL - 1, y) && p.img.getRGB(spanL - 1, y) != borderColor) {
            spanL--;
        }
        return new Span(spanR, spanL, y);
    }

    public void span(int seedX, int seedY) {
        Stack<Span> spanStack = new Stack<>();

        //  ищем  1 спан и кладем в стек
        spanStack.push(findSpan(seedX, seedY));
        int color = p.color.getRGB();

        // основной цикл
        while (!spanStack.empty()) {

            Span span = spanStack.pop();
            // закрашиваем спан
            for (int i = span.left; i <= span.right; i++) {
                setPixel(i, span.y, color);
            }

            // ищем спаны сверху
            // идем в цикле по-текущему спану
            // смотрим если сверху не граница и не цвет заливки, ищем там спаны
            for (int i = span.left; i <= span.right; i++) {
                if (checkBounds(i, span.y - 1) && p.img.getRGB(i, span.y - 1) != borderColor &&
                        p.img.getRGB(i, span.y - 1) != color) {
                    spanStack.push(findSpan(i, span.y - 1));
                    i = Math.min(span.right, spanStack.peek().right);
                }
            }

            // ищем спаны снизу
            // идем в цикле по-текущему спану
            // смотрим если снизу не граница и не цвет заливки, ищем там спаны
            for (int i = span.left; i < span.right; i++) {
                if (checkBounds(i, span.y + 1) && p.img.getRGB(i, span.y + 1) != borderColor &&
                        p.img.getRGB(i, span.y + 1) != color) {
                    spanStack.push(findSpan(i, span.y + 1));
                    i = Math.min(span.right, spanStack.peek().right);
                }
            }
        }
        p.repaint();
    }

    public void polygon(int sides, int cenX, int cenY, int R, int pivot) {
        int[] x = new int[sides];//координаты точек
        int[] y = new int[sides];//
        // угол между сторонами
        double theta = 2 * Math.PI / sides;
        // просто что бы красиво рисовалось
        if (sides == 4)
            pivot += 45;
        else
            pivot -= 90;
        for (int i = 0; i < sides; i++) {
            double dtheta = theta * i + Math.toRadians(pivot);
            x[i] = cenX + (int) (Math.cos(dtheta) * R);
            y[i] = cenY + (int) (Math.sin(dtheta) * R);
        }
        paint(x, y, sides);
    }

    public void star(int sides, int cenX, int cenY, int R, int pivot) {
        int[] x = new int[sides * 2];//координаты точек
        int[] y = new int[sides * 2];
        // угол между сторонами большой окружности
        double theta1 = 2 * Math.PI / sides;
        // сдвиг относительно точек большой окружности и точек малой окружности
        double theta2 = theta1 / 2;
        // зададим радиус малой окружности как 1/3 от радиуса большой окружности
        int r = R / 3;
        for (int i = 0; i < sides * 2; i++) {
            double dtheta = theta2 * i + Math.toRadians(pivot);
            if (i % 2 == 0) {
                x[i] = cenX + (int) (Math.cos(dtheta) * R);
                y[i] = cenY + (int) (Math.sin(dtheta) * R);
            } else {
                x[i] = cenX + (int) (Math.cos(dtheta) * r);
                y[i] = cenY + (int) (Math.sin(dtheta) * r);
            }
        }
        paint(x, y, sides * 2);
    }

    private void paint(int[] x, int[] y, int sides) {
        for (int i = 0; i < sides; i++) {
            line(x[i], y[i], x[(i + 1) % sides], y[(i + 1) % sides], false);
        }
    }

    public void line(int x0, int y0, int x1, int y1, boolean isLine) {
        int check = (Math.abs(y1 - y0) > Math.abs(x1 - x0) ? 1 : 0);
        int swap;
        if (check == 1) { // - то отражаем по диагонали
            swap = x0;
            x0 = y0;
            y0 = swap;

            swap = x1;
            x1 = y1;
            y1 = swap;
        }
        if (x0 > x1) {// - то меняем местами координаты начала и конца отрезка
            swap = x0;
            x0 = x1;
            x1 = swap;

            swap = y0;
            y0 = y1;
            y1 = swap;
        }
        int step = (y0 < y1) ? 1 : -1;

        int color = isLine ? p.color.getRGB() : borderColor;
        bresenhamLineAlgorithm(x0, y0, x1, y1, step, check, color);

    }

    private void bresenhamLineAlgorithm(int x0, int y0, int x1, int y1, int step, int check, int color) {
        int x = x0;
        int y = y0;
        int dx = x1 - x0;
        int dy = Math.abs(y1 - y0);
        int err = -dx;

        setPixel((check == 1) ? y : x, (check == 1) ? x : y, color);
        for (int i = x0; i < x1; ++i) {
            x++;
            err += 2 * dy;
            if (err > 0) {
                err -= 2 * dx;
                y += step; // -- или ++
            }

            // возвращаем "оси" на место если меняли их местами
            setPixel((check == 1) ? y : x, (check == 1) ? x : y, color);
        }
        p.repaint();
    }

    public void line(int x0, int y0, int x1, int y1, int thickness) {
        Graphics2D g2 = p.img.createGraphics();
        g2.setColor(p.color);
        g2.setStroke(new BasicStroke(thickness));// толщина это параметр
        g2.drawLine(x0, y0, x1, y1);
        p.repaint();
    }


    private void setPixel(int x, int y, int color) {
        if (x < p.img.getWidth() && x > 0 && y < p.img.getHeight() && y > 0)
            p.img.setRGB(x, y, color);
    }

    private boolean checkBounds(int x, int y) {
        return (x < p.img.getWidth() && x > 0 && y < p.img.getHeight() && y > 0);
    }

}
