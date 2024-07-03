import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuCreator {
    private Paint paint;

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


    public MenuCreator(Paint paint) {
        this.paint = paint;
    }

    public void createMenu() {
        JMenu file = new JMenu("File");

        JMenuItem exit = new JMenuItem("Exit");
        exit.setToolTipText("Exit application");
        exit.setIcon(new ImageIcon(getClass().getResource("Exit.gif"), "Exit"));
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paint.onExit();
            }
        });
        file.add(exit);

        JMenuItem save = new JMenuItem("Save");
        save.setToolTipText("Save file");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paint.saveFile();
            }
        });
        file.add(save);

        JMenuItem load = new JMenuItem("Load");
        load.setToolTipText("load file");
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paint.loadFile();
            }
        });
        file.add(load);
        paint.menuBar.add(file);

        JMenu view = new JMenu("View");

        JMenu figure = new JMenu("Figure");
        view.add(figure);

        JMenu color = new JMenu("Color");
        view.add(color);

        JMenuItem eraser = new JMenuItem("Eraser");
        eraser.setIcon(new ImageIcon(getClass().getResource("Erase.png"), "Eraser"));
        eraser.setToolTipText("erase");
        eraser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paint.clearDrawingArea();
            }
        });
        view.add(eraser);

        ButtonGroup instrument = new ButtonGroup();

        fill = new JRadioButtonMenuItem("Fill", false);
        fill.setToolTipText("fill shape");
        fill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paint.fillShape();
            }
        });
        instrument.add(fill);
        view.add(fill);

        line = new JRadioButtonMenuItem("Line", false);
        line.setToolTipText("draw line");
        line.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paint.drawLine();
            }
        });
        line.addActionListener(dialogLineParameters = new DialogWindow(line, paint.panel));
        instrument.add(line);
        figure.add(line);

        square = new JRadioButtonMenuItem("Square", false);
        square.setToolTipText("draw square");
        square.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paint.drawSquare();
            }
        });
        square.addActionListener(dialogSquareParameters = new DialogWindow(square, paint.panel));
        instrument.add(square);
        figure.add(square);

        pentagon = new JRadioButtonMenuItem("Pentagon", false);
        pentagon.setToolTipText("draw pentagon");
        pentagon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paint.drawPentagon();
            }
        });
        pentagon.addActionListener(dialogPentagonParameters = new DialogWindow(pentagon, paint.panel));
        instrument.add(pentagon);
        figure.add(pentagon);

        hexagon = new JRadioButtonMenuItem("Hexagon", false);
        hexagon.setToolTipText("draw hexagon");
        hexagon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paint.drawHexagon();
            }
        });
        hexagon.addActionListener(dialogHexagonParameters = new DialogWindow(hexagon, paint.panel));
        instrument.add(hexagon);
        figure.add(hexagon);

        star = new JRadioButtonMenuItem("Star", false);
        star.setToolTipText("draw star");
        star.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paint.drawStar();
            }
        });
        star.addActionListener(dialogStarParameters = new DialogWindow(star, paint.panel));
        instrument.add(star);
        figure.add(star);

        ButtonGroup groupColor = new ButtonGroup();

        palette = new JRadioButtonMenuItem("Palette");
        palette.setIcon(new ImageIcon(getClass().getResource("Color/Palette.png"), "Palette"));
        palette.setToolTipText("create your color");
        palette.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paint.chooseNewColor();
            }
        });
        palette.addActionListener(dialogGetNewColor = new DialogWindow(palette, paint.panel));
        groupColor.add(palette);
        color.add(palette);

        red = new JRadioButtonMenuItem("Red", false);
        red.setForeground(Color.RED);
        red.setToolTipText("choose red color");
        red.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paint.setRed();
            }
        });
        groupColor.add(red);
        color.add(red);

        yellow = new JRadioButtonMenuItem("Yellow", false);
        yellow.setForeground(Color.YELLOW.darker());
        yellow.setToolTipText("choose yellow color");
        yellow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paint.setYellow();
            }
        });
        groupColor.add(yellow);
        color.add(yellow);

        green = new JRadioButtonMenuItem("Green", false);
        green.setForeground(Color.GREEN);
        green.setToolTipText("choose green color");
        green.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paint.setGreen();
            }
        });
        groupColor.add(green);
        color.add(green);


        blue = new JRadioButtonMenuItem("Blue", false);
        blue.setForeground(Color.CYAN);
        blue.setToolTipText("choose blue color");
        blue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paint.setBlue();
            }
        });
        groupColor.add(blue);
        color.add(blue);

        darkBlue = new JRadioButtonMenuItem("DarkBlue", false);
        darkBlue.setForeground(Color.BLUE);
        darkBlue.setToolTipText("choose dark blue color");
        groupColor.add(darkBlue);
        darkBlue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paint.setDarkBlue();
            }
        });
        color.add(darkBlue);

        violet = new JRadioButtonMenuItem("Violet", false);
        violet.setForeground(new Color(140, 90, 180));
        violet.setToolTipText("choose violet color");
        violet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paint.setViolet();
            }
        });
        groupColor.add(violet);
        color.add(violet);

        white = new JRadioButtonMenuItem("White", false);
        white.setToolTipText("choose white color");
        palette.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paint.setWhite();
            }
        });
        groupColor.add(white);
        color.add(white);

        newColor = new JRadioButtonMenuItem("newColor", false);
        newColor.setToolTipText("choose your color");
        newColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paint.setNewColor();
            }
        });
        groupColor.add(newColor);
        color.add(newColor);

        paint.menuBar.add(view);

        JMenu help = new JMenu("Help");

        JMenuItem about = new JMenuItem("About");
        about.setToolTipText("Shows program version and copyright information");
        about.setIcon(new ImageIcon(getClass().getResource("About.gif"), "About"));
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paint.onAbout();
            }
        });
        help.add(about);

        paint.menuBar.add(help);

        //toolbar
        JButton toolBarExit = new JButton();
        toolBarExit.setToolTipText("Exit application");
        toolBarExit.setIcon(new ImageIcon(getClass().getResource("Exit.gif"), "Exit"));
        toolBarExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paint.onExit();
            }
        });
        paint.toolBar.add(toolBarExit);

        JButton toolBarAbout = new JButton();
        toolBarAbout.setToolTipText("Shows program version and copyright information");
        toolBarAbout.setIcon(new ImageIcon(getClass().getResource("About.gif"), "About"));
        toolBarAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paint.onAbout();
            }
        });
        paint.toolBar.add(toolBarAbout);

        paint.toolBar.addSeparator(new Dimension(10, 1));

        ButtonGroup toolBarInstrument = new ButtonGroup();
        toolBarLine = new JToggleButton();
        toolBarLine.setIcon(new ImageIcon(getClass().getResource("Figure/Line.png"), "Line"));
        toolBarLine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paint.drawLine();
            }
        });
        toolBarInstrument.add(toolBarLine);
        toolBarLine.addActionListener(dialogLineParameters);
        paint.toolBar.add(toolBarLine);

        toolBarSquare = new JToggleButton();
        toolBarSquare.setIcon(new ImageIcon(getClass().getResource("Figure/Square.png"), "Square"));
        toolBarSquare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paint.drawSquare();
            }
        });
        toolBarInstrument.add(toolBarSquare);
        toolBarSquare.addActionListener(dialogSquareParameters);
        paint.toolBar.add(toolBarSquare);

        toolBarPentagon = new JToggleButton();
        toolBarPentagon.setIcon(new ImageIcon(getClass().getResource("Figure/Pentagon.png"), "Pentagon"));
        toolBarPentagon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paint.drawPentagon();
            }
        });
        toolBarInstrument.add(toolBarPentagon);
        toolBarPentagon.addActionListener(dialogPentagonParameters);
        paint.toolBar.add(toolBarPentagon);

        toolBarHexagon = new JToggleButton();
        toolBarHexagon.setIcon(new ImageIcon(getClass().getResource("Figure/Hexagon.png"), "Hexagon"));
        toolBarHexagon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paint.drawHexagon();
            }
        });
        toolBarInstrument.add(toolBarHexagon);
        toolBarHexagon.addActionListener(dialogHexagonParameters);
        paint.toolBar.add(toolBarHexagon);

        toolBarStar = new JToggleButton();
        toolBarStar.setIcon(new ImageIcon(getClass().getResource("Figure/Star.png"), "Star"));
        toolBarStar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paint.drawStar();
            }
        });
        toolBarInstrument.add(toolBarStar);
        toolBarStar.addActionListener(dialogStarParameters);
        paint.toolBar.add(toolBarStar);

        paint.toolBar.addSeparator(new Dimension(10, 1));

        toolBarFill = new JToggleButton();
        toolBarFill.setIcon(new ImageIcon(getClass().getResource("Fill.png"), "Fill"));
        toolBarFill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paint.fillShape();
            }
        });
        toolBarInstrument.add(toolBarFill);
        paint.toolBar.add(toolBarFill);

        ButtonGroup toolBarColor = new ButtonGroup();
        toolBarRed = new JToggleButton();
        toolBarRed.setIcon(new ImageIcon(getClass().getResource("Color/Red.png"), "Red"));
        toolBarRed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paint.setRed();
            }
        });
        toolBarColor.add(toolBarRed);
        paint.toolBar.add(toolBarRed);

        toolBarYellow = new JToggleButton();
        toolBarYellow.setIcon(new ImageIcon(getClass().getResource("Color/Yellow.png"), "Yellow"));
        toolBarYellow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paint.setYellow();
            }
        });
        toolBarColor.add(toolBarYellow);
        paint.toolBar.add(toolBarYellow);

        toolBarGreen = new JToggleButton();
        toolBarGreen.setIcon(new ImageIcon(getClass().getResource("Color/Green.png"), "Green"));
        toolBarGreen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paint.setGreen();
            }
        });
        toolBarColor.add(toolBarGreen);
        paint.toolBar.add(toolBarGreen);

        toolBarBlue = new JToggleButton();
        toolBarBlue.setIcon(new ImageIcon(getClass().getResource("Color/Blue.png"), "Blue"));
        toolBarBlue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paint.setBlue();
            }
        });
        toolBarColor.add(toolBarBlue);
        paint.toolBar.add(toolBarBlue);

        toolBarDarkBlue = new JToggleButton();
        toolBarDarkBlue.setIcon(new ImageIcon(getClass().getResource("Color/DarkBlue.png"), "DarkBlue"));
        toolBarDarkBlue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paint.setDarkBlue();
            }
        });
        toolBarColor.add(toolBarDarkBlue);
        paint.toolBar.add(toolBarDarkBlue);

        toolBarViolet = new JToggleButton();
        toolBarViolet.setIcon(new ImageIcon(getClass().getResource("Color/Violet.png"), "Violet"));
        toolBarViolet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paint.setViolet();
            }
        });
        toolBarColor.add(toolBarViolet);
        paint.toolBar.add(toolBarViolet);


        toolBarWhite = new JToggleButton();
        toolBarWhite.setIcon(new ImageIcon(getClass().getResource("Color/White.png"), "White"));
        toolBarWhite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paint.setWhite();
            }
        });
        toolBarColor.add(toolBarWhite);
        paint.toolBar.add(toolBarWhite);


        toolBarPalette = new JToggleButton();
        toolBarPalette.setIcon(new ImageIcon(getClass().getResource("Color/Palette.png"), "Palette"));
        toolBarPalette.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paint.chooseNewColor();
            }
        });
        toolBarPalette.addActionListener(dialogGetNewColor);
        toolBarColor.add(toolBarPalette);
        paint.toolBar.add(toolBarPalette);

        toolBarNewColor = new JToggleButton("your color");
        toolBarNewColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paint.setNewColor();
            }
        });
        toolBarColor.add(toolBarNewColor);
        paint.toolBar.add(toolBarNewColor);

        paint.toolBar.addSeparator(new Dimension(10, 1));

        JButton toolBarEraser = new JButton();
        toolBarEraser.setIcon(new ImageIcon(getClass().getResource("Erase.png"), "Eraser"));
        toolBarEraser.setToolTipText("fill shape");
        toolBarEraser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paint.clearDrawingArea();
            }
        });
        paint.toolBar.add(toolBarEraser);
    }

    public JRadioButtonMenuItem getLine() {
        return line;
    }

    public JRadioButtonMenuItem getFill() {
        return fill;
    }

    public JRadioButtonMenuItem getSquare() {
        return square;
    }

    public JRadioButtonMenuItem getPentagon() {
        return pentagon;
    }

    public JRadioButtonMenuItem getHexagon() {
        return hexagon;
    }

    public JRadioButtonMenuItem getStar() {
        return star;
    }

    public DialogWindow getDialogLineParameters() {
        return dialogLineParameters;
    }

    public DialogWindow getDialogSquareParameters() {
        return dialogSquareParameters;
    }

    public DialogWindow getDialogPentagonParameters() {
        return dialogPentagonParameters;
    }

    public DialogWindow getDialogHexagonParameters() {
        return dialogHexagonParameters;
    }

    public DialogWindow getDialogStarParameters() {
        return dialogStarParameters;
    }

    public JRadioButtonMenuItem getRed() {
        return red;
    }

    public JRadioButtonMenuItem getYellow() {
        return yellow;
    }

    public JRadioButtonMenuItem getBlue() {
        return blue;
    }

    public JRadioButtonMenuItem getDarkBlue() {
        return darkBlue;
    }

    public JRadioButtonMenuItem getGreen() {
        return green;
    }

    public JRadioButtonMenuItem getViolet() {
        return violet;
    }

    public JRadioButtonMenuItem getWhite() {
        return white;
    }

    public JRadioButtonMenuItem getNewColor() {
        return newColor;
    }

    public JRadioButtonMenuItem getPalette() {
        return palette;
    }

    public DialogWindow getDialogGetNewColor() {
        return dialogGetNewColor;
    }

    public JToggleButton getToolBarLine() {
        return toolBarLine;
    }

    public JToggleButton getToolBarSquare() {
        return toolBarSquare;
    }

    public JToggleButton getToolBarStar() {
        return toolBarStar;
    }

    public JToggleButton getToolBarFill() {
        return toolBarFill;
    }

    public JToggleButton getToolBarPentagon() {
        return toolBarPentagon;
    }

    public JToggleButton getToolBarHexagon() {
        return toolBarHexagon;
    }

    public JToggleButton getToolBarRed() {
        return toolBarRed;
    }

    public JToggleButton getToolBarYellow() {
        return toolBarYellow;
    }

    public JToggleButton getToolBarBlue() {
        return toolBarBlue;
    }

    public JToggleButton getToolBarDarkBlue() {
        return toolBarDarkBlue;
    }

    public JToggleButton getToolBarGreen() {
        return toolBarGreen;
    }

    public JToggleButton getToolBarViolet() {
        return toolBarViolet;
    }

    public JToggleButton getToolBarWhite() {
        return toolBarWhite;
    }

    public JToggleButton getToolBarNewColor() {
        return toolBarNewColor;
    }

    public JToggleButton getToolBarPalette() {
        return toolBarPalette;
    }
}
