package ru.nsu.fit;

import ru.nsu.fit.filters.Filter;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class GraphicsPanel extends JPanel implements MouseInputListener {
    private BufferedImage filterImage;
    private BufferedImage originalImage;
    private BufferedImage realFilterImage;
    private BufferedImage realOriginalImage;
    private int realWidthImage, realHeightImage;
    private int screenWidth, screenHeight;
    private Filter filter;

    private boolean isFilter = false;
    private boolean isRealRegime = true;
    private Point mousePoint;
    private Object regimeInterpolation = RenderingHints.VALUE_INTERPOLATION_BILINEAR;
    private JScrollPane scrollPane;

    public GraphicsPanel(int width, int height) {
        //this.width = width;
        //this.height = height;
        setPreferredSize(new Dimension(width, height));
        addMouseListener(this);
        addComponentListener(new GraphicsPanel.ResizeListener());
        setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(new Color(getBackground().getRGB()), 5),
                        BorderFactory.createDashedBorder(Color.BLACK, 4, 4)
                )
        );

        // слежение за мышкой
        addMouseMotionListener(this);
    }

    static class ResizeListener extends ComponentAdapter {
        @Override
        public void componentResized(ComponentEvent e) {
            e.getComponent().setSize(new Dimension(e.getComponent().getWidth(), e.getComponent().getHeight()));
            super.componentResized(e);
        }
    }

    public void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    @Override
    public void paintComponent(Graphics g) {

        screenWidth = scrollPane.getViewport().getWidth();
        screenHeight = scrollPane.getViewport().getHeight();

        super.paintComponent(g);
        BufferedImage currentImg = (isFilter) ? filterImage : originalImage;

        if (currentImg != null) {
            g.drawImage(currentImg, 6, 6, currentImg.getWidth(), currentImg.getHeight(), null);
            setPreferredSize(new Dimension(currentImg.getWidth(), currentImg.getHeight()));
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        isFilter = !isFilter;
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        mousePoint = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
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
        Point dragEventPoint = e.getPoint();
        JViewport viewport = (JViewport) this.getParent();
        Point viewPos = viewport.getViewPosition();
        int maxViewPosX = this.getWidth() - viewport.getWidth();
        int maxViewPosY = this.getHeight() - viewport.getHeight();

        if (this.getWidth() > viewport.getWidth()) {
            viewPos.x -= dragEventPoint.x - mousePoint.x;

            if (viewPos.x < 0) {
                viewPos.x = 0;
                mousePoint.x = dragEventPoint.x;
            }

            if (viewPos.x > maxViewPosX) {
                viewPos.x = maxViewPosX;
                mousePoint.x = dragEventPoint.x;
            }
        }

        if (this.getHeight() > viewport.getHeight()) {
            viewPos.y -= dragEventPoint.y - mousePoint.y;

            if (viewPos.y < 0) {
                viewPos.y = 0;
                mousePoint.y = dragEventPoint.y;
            }

            if (viewPos.y > maxViewPosY) {
                viewPos.y = maxViewPosY;
                mousePoint.y = dragEventPoint.y;
            }
        }
        viewport.setViewPosition(viewPos);
    }

    public void setFilter(Filter filter) {
        if (this.filter == filter && filter.getParameters() == null)
            isFilter = !isFilter;
        else {
            isFilter = true;
            this.filter = filter;
            this.setWaitCursor();
            BufferedImage newImg = filter.execute(originalImage);
            this.setDefaultCursor();
            filterImage = newImg;
            realFilterImage = newImg;
        }
        revalidate();
        repaint();
    }

    public boolean isFilter() {
        return isFilter;
    }

    public void setRegimeInterpolation(Object regimeInterpolation) {
        this.regimeInterpolation = regimeInterpolation;
    }

    public void setImage(Image openImage) {
        realWidthImage = openImage.getWidth(this);
        realHeightImage = openImage.getHeight(this);
        isRealRegime = true;

        BufferedImage newImage = new BufferedImage(realWidthImage, realHeightImage, BufferedImage.TYPE_INT_ARGB);
        setPreferredSize(new Dimension(realWidthImage, realHeightImage));

        originalImage = newImage;
        filterImage = newImage;
        realOriginalImage = newImage;
        isFilter = false;
        this.filter = null;

        Graphics2D g = originalImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, regimeInterpolation);
        g.drawImage(openImage, 0, 0, openImage.getWidth(this), openImage.getHeight(this), this);

        this.revalidate();

        repaint();
    }

    public BufferedImage getFilterImage() {
        return filterImage;
    }

    public void fitToScreen() {
        BufferedImage currentImg = (isFilter) ? filterImage : originalImage;
        if (currentImg == null)
            return;

        if (isRealRegime) {
            double widthFrac = (double) currentImg.getWidth() / screenWidth;
            double heightFrac = (double) currentImg.getHeight() / screenHeight;
            double frac = Math.max(widthFrac, heightFrac);

            int dstWidth = (int) (currentImg.getWidth() / frac);
            int dstHeight = (int) (currentImg.getHeight() / frac);

            BufferedImage newOriginalImage = new BufferedImage(dstWidth, dstHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g1 = newOriginalImage.createGraphics();
            g1.setRenderingHint(RenderingHints.KEY_INTERPOLATION, regimeInterpolation);
            g1.drawImage(realOriginalImage, 0, 0, dstWidth, dstHeight, 0, 0, realOriginalImage.getWidth(), realOriginalImage.getHeight(), null);
            originalImage = newOriginalImage;

            BufferedImage newFilterImage = new BufferedImage(dstWidth, dstHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = newFilterImage.createGraphics();
            if (realFilterImage != null) {
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, regimeInterpolation);
                g2.drawImage(realFilterImage, 0, 0, dstWidth, dstHeight, 0, 0, realFilterImage.getWidth(), realFilterImage.getHeight(), null);
                filterImage = newFilterImage;
            }

            setPreferredSize(new Dimension(dstWidth, dstHeight));


            g1.dispose();
            g2.dispose();
        } else {
            originalImage = realOriginalImage;

            BufferedImage newFilterImage = new BufferedImage(realWidthImage, realHeightImage, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = newFilterImage.createGraphics();
            if (realFilterImage != null) {
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, regimeInterpolation);
                g2.drawImage(realFilterImage, 0, 0, realWidthImage, realHeightImage, 0, 0,
                        realFilterImage.getWidth(), realFilterImage.getHeight(), null);
                filterImage = newFilterImage;
            }

            setPreferredSize(new Dimension(realWidthImage, realHeightImage));

            g2.dispose();
        }

        isRealRegime = !isRealRegime;
        revalidate();
        repaint();
    }

    public void setWaitCursor() {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    }

    public void setDefaultCursor() {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
}
