import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

public class ImageResized extends ComponentAdapter {
    ImagePanel p;

    ImageResized(ImagePanel panel) {
        p = panel;
    }

    public BufferedImage newImage(int width, int height) {
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        clean(newImage);
        return newImage;
    }

    @Override
    public void componentResized(ComponentEvent e) {
        Dimension size = new Dimension(p.img.getWidth(), p.img.getHeight());
        Dimension newSize = e.getComponent().getBounds().getSize();
        Dimension checkSize = size;
        // изображение нужно изменять только если новые размеры больше размера старого изображения
        if (newSize.width > p.img.getWidth() && newSize.height > p.img.getHeight())
            size = newSize;
        else if (newSize.width > p.img.getWidth())
            size = new Dimension(newSize.width, p.img.getHeight());
        else if (newSize.height > p.img.getHeight())
            size = new Dimension(p.img.getWidth(), newSize.height);
        if (checkSize != size)
            resizeImage(size);
    }

    private void resizeImage(Dimension size) {
        BufferedImage newImg = newImage(size.width, size.height);
        Graphics2D g2 = newImg.createGraphics();
        g2.drawImage(p.img, 0, 0, null);
        p.img = newImg;
        updatePanelSize(size);
    }

    public void loadImage(BufferedImage newImage) {
        // если полученное изображение больше текущего окна
        if (newImage.getWidth() > p.img.getWidth() && newImage.getHeight() > p.img.getHeight())
            p.img = newImage(newImage.getWidth(), newImage.getHeight());
        else if (newImage.getWidth() > p.img.getWidth())
            p.img = newImage(newImage.getWidth(), p.img.getHeight());
        else if (newImage.getHeight() > p.img.getHeight())
            p.img = newImage(p.img.getWidth(), newImage.getHeight());
        // если меньше, то просто рисуем его на текущем img
        Graphics2D g2 = p.img.createGraphics();
        g2.drawImage(newImage, 0, 0, null);
        updatePanelSize(new Dimension(p.img.getWidth(), p.img.getHeight()));
        p.repaint();
    }

    private void updatePanelSize(Dimension size) {
        //обновление предпочтительного размера панели, тк область рисования стала больше
        p.setPreferredSize(size);
        //сообщить панели прокрутки, чтобы она обновила себя и свои полосы прокрутки
        p.revalidate();
    }

    public void clean(BufferedImage img) {
        Graphics2D g2 = img.createGraphics();
        g2.setColor(Color.WHITE);
        g2.drawRect(0, 0, img.getWidth(), img.getHeight());
        g2.fillRect(0, 0, img.getWidth(), img.getHeight());
        p.repaint();
    }

}
