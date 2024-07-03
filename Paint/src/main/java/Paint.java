
import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Paint extends JFrame {
     JMenuBar menuBar;
     JToolBar toolBar;

    ImagePanel panel;
    //menu
    private JRadioButtonMenuItem fill;
    private JRadioButtonMenuItem line;
    private DialogWindow dialogLineParameters;
    private JRadioButtonMenuItem square;
    private DialogWindow dialogSquareParameters;
    private JRadioButtonMenuItem pentagon;
    private DialogWindow dialogPentagonParameters;
    private JRadioButtonMenuItem hexagon;
    private DialogWindow dialogHexagonParameters;
    private JRadioButtonMenuItem star;
    private DialogWindow dialogStarParameters;

    private JRadioButtonMenuItem red;
    private JRadioButtonMenuItem yellow;
    private JRadioButtonMenuItem green;
    private JRadioButtonMenuItem blue;
    private JRadioButtonMenuItem darkBlue;
    private JRadioButtonMenuItem violet;
    private JRadioButtonMenuItem white;
    private JRadioButtonMenuItem newColor;
    private JRadioButtonMenuItem palette;
    private DialogWindow dialogGetNewColor;

    //toolbar
    private JToggleButton toolBarLine;
    private JToggleButton toolBarSquare;
    private JToggleButton toolBarPentagon;
    private JToggleButton toolBarHexagon;
    private JToggleButton toolBarStar;
    private JToggleButton toolBarFill;

    private JToggleButton toolBarRed;
    private JToggleButton toolBarYellow;
    private JToggleButton toolBarGreen;
    private JToggleButton toolBarBlue;
    private JToggleButton toolBarDarkBlue;
    private JToggleButton toolBarViolet;
    private JToggleButton toolBarWhite;
    private JToggleButton toolBarNewColor;
    private JToggleButton toolBarPalette;

    JFileChooser saveFile;
    JFileChooser loadFile;

    FileReader fileReader;

    File file;

    public static void main(String[] args) { new Paint(); }

    public Paint() {
        setPreferredSize(new Dimension(800, 600));
        setMinimumSize(new Dimension(640, 480));
        setTitle("ICG Paint");

        //чтобы изображение было по центру любого экрана
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width / 2 - getPreferredSize().width / 2,
                screenSize.height / 2 - getPreferredSize().height / 2);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        toolBar = new JToolBar();
        toolBar.setRollover(true);
        add(toolBar, BorderLayout.PAGE_START);

        panel = new ImagePanel(this.getSize());
        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane, BorderLayout.CENTER);

        saveFile = new JFileChooser();
        loadFile = new JFileChooser();
        fileReader = new FileReader();

        MenuCreator menuCreator = new MenuCreator(this);
        menuCreator.createMenu();

        //menu
        line = menuCreator.getLine();
        square = menuCreator.getSquare();
        pentagon = menuCreator.getPentagon();
        hexagon = menuCreator.getHexagon();
        star = menuCreator.getStar();
        fill = menuCreator.getFill();

        dialogLineParameters = menuCreator.getDialogLineParameters();
        dialogSquareParameters = menuCreator.getDialogSquareParameters();
        dialogPentagonParameters = menuCreator.getDialogPentagonParameters();
        dialogHexagonParameters = menuCreator.getDialogHexagonParameters();
        dialogStarParameters = menuCreator.getDialogStarParameters();

        red = menuCreator.getRed();
        yellow = menuCreator.getYellow();
        green = menuCreator.getGreen();
        blue = menuCreator.getBlue();
        darkBlue = menuCreator.getDarkBlue();
        violet = menuCreator.getViolet();
        white = menuCreator.getWhite();
        newColor = menuCreator.getNewColor();
        palette = menuCreator.getPalette();
        dialogGetNewColor = menuCreator.getDialogGetNewColor();

        //toolbar
        toolBarLine = menuCreator.getToolBarLine();
        toolBarSquare = menuCreator.getToolBarSquare();
        toolBarPentagon = menuCreator.getToolBarPentagon();
        toolBarHexagon = menuCreator.getToolBarHexagon();
        toolBarStar = menuCreator.getToolBarStar();
        toolBarFill = menuCreator.getToolBarFill();

        toolBarRed = menuCreator.getToolBarRed();
        toolBarYellow = menuCreator.getToolBarYellow();
        toolBarGreen = menuCreator.getToolBarGreen();
        toolBarBlue = menuCreator.getToolBarBlue();
        toolBarDarkBlue = menuCreator.getToolBarDarkBlue();
        toolBarViolet = menuCreator.getToolBarViolet();
        toolBarWhite = menuCreator.getToolBarWhite();
        toolBarNewColor = menuCreator.getToolBarNewColor();
        toolBarPalette = menuCreator.getToolBarPalette();


        pack();
        setVisible(true);
    }

    public void setInstrument(){
       panel.isLine = line.isSelected();
       panel.isStar = star.isSelected();
       panel.isPolygon = square.isSelected() || pentagon.isSelected() || hexagon.isSelected();
       panel.isFillShape = fill.isSelected();
    }

    private void setNewColorInButton(Color color){
        newColor.setForeground(color);
        toolBarNewColor.setForeground(color);
        panel.newColor = color;
    }

    public void setColor(){
        if(red.isSelected()){
            panel.setRed();
        } else if(yellow.isSelected()){
            panel.setYellow();
        } else if(green.isSelected()){
            panel.setGreen();
        } else if(blue.isSelected()){
            panel.setBlue();
        } else if(darkBlue.isSelected()){
            panel.setDarkBlue();
        } else if(violet.isSelected()){
            panel.setViolet();
        } else if (white.isSelected()){
            panel.setWhite();
        } else if (palette.isSelected()){
            setNewColorInButton(dialogGetNewColor.getColor());
            panel.setNewColor(newColor.getForeground());
        } else if (newColor.isSelected()){
            panel.setNewColor(newColor.getForeground());
        }
    }

    public void onAbout() {
        JOptionPane.showMessageDialog(this, fileReader.getText(), "About program", JOptionPane.INFORMATION_MESSAGE);
    }

    public void onExit() {

        System.exit(0);
    }

    public void drawLine() {
        line.setSelected(true);
        toolBarLine.setSelected(true);
        setInstrument();
        panel.line(dialogLineParameters.getThickness());
    }

    public void drawSquare() {
        square.setSelected(true);
        toolBarSquare.setSelected(true);
        setInstrument();
        panel.polygon(dialogSquareParameters.getCountSides(), dialogSquareParameters.getRadius(), dialogSquareParameters.getPivot());
    }

    public void drawPentagon() {
        pentagon.setSelected(true);
        toolBarPentagon.setSelected(true);
        setInstrument();
        panel.polygon(dialogPentagonParameters.getCountSides(), dialogPentagonParameters.getRadius(), dialogPentagonParameters.getPivot());
    }

    public void drawHexagon() {
        hexagon.setSelected(true);
        toolBarHexagon.setSelected(true);
        setInstrument();
        panel.polygon(dialogHexagonParameters.getCountSides(), dialogHexagonParameters.getRadius(),dialogHexagonParameters.getPivot());
    }

    public void drawStar() {
        star.setSelected(true);
        toolBarStar.setSelected(true);
        setInstrument();
        panel.polygon(dialogStarParameters.getCountSides(), dialogStarParameters.getRadius(), dialogStarParameters.getPivot());
    }

    public void fillShape(){
        fill.setSelected(true);
        toolBarFill.setSelected(true);
        setInstrument();
    }

    public void clearDrawingArea() {
        panel.clean();
    }

    public void setRed() {
        red.setSelected(true);
        toolBarRed.setSelected(true);
        setColor();
    }

    public void setYellow() {
        yellow.setSelected(true);
        toolBarGreen.setSelected(true);
        setColor();
    }
    public void setGreen() {
        green.setSelected(true);
        toolBarGreen.setSelected(true);
        setColor();
    }
    public void setBlue() {
        blue.setSelected(true);
        toolBarBlue.setSelected(true);
        setColor();
    }
    public void setDarkBlue() {
        darkBlue.setSelected(true);
        toolBarDarkBlue.setSelected(true);
        setColor();
    }
    public void setViolet() {
        violet.setSelected(true);
        toolBarViolet.setSelected(true);
        setColor();
    }
    public void setWhite() {
        white.setSelected(true);
        toolBarWhite.setSelected(true);
        setColor();
    }

    public void chooseNewColor(){
        palette.setSelected(true);
        toolBarPalette.setSelected(true);
        setColor();
    }

    public void setNewColor() {
        palette.setSelected(true);
        setColor();
    }

    public void saveFile(){
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "*.png", "png");
        saveFile.setFileFilter(filter);
        int result = saveFile.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = saveFile.getSelectedFile();
            if (!file.getName().endsWith(".png")) {
                file = new File(file.getAbsolutePath() + ".png");
            }
            try {
                ImageIO.write(panel.img, "png", file);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving image", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void loadFile(){
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "*.png, *.jpg, *.jpeg, *.gif, *.bmp", "png", "jpg","jpeg", "gif", "bmp");
        loadFile.setFileFilter(filter);
        int result = loadFile.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            file = loadFile.getSelectedFile();
            try {
                panel.imageResized.loadImage(ImageIO.read(file));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error loading image", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
