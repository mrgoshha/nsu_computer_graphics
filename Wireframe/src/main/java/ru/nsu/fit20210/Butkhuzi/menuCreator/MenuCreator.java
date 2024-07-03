package ru.nsu.fit20210.Butkhuzi.menuCreator;

import ru.nsu.fit20210.Butkhuzi.AboutFrame;
import ru.nsu.fit20210.Butkhuzi.MainFrame;
import ru.nsu.fit20210.Butkhuzi.OpenFile;
import ru.nsu.fit20210.Butkhuzi.SaveFile;
import ru.nsu.fit20210.Butkhuzi.spline.SplineDialog;
import ru.nsu.fit20210.Butkhuzi.spline.SplinePanel;

import javax.swing.*;

public class MenuCreator {
    private MainFrame mainFrame;
    private SplineDialog splineDialog;
    private SplinePanel splinePanel;


    public MenuCreator(MainFrame frame) {
        setMainFrame(frame);
        splinePanel = new SplinePanel(600, 400);
        mainFrame.getGPanel().setSplinePanel(splinePanel, splinePanel.spline);
        splineDialog = new SplineDialog(splinePanel, mainFrame.getGPanel());
    }

    public void createMenu() {
        MenuComponentCreator componentCreator = new MenuComponentCreator(mainFrame.getMenu(), mainFrame.getToolBar());

        JMenu file = componentCreator.createJMenu("File");

        JMenuItem exit = componentCreator.createJMenuItem("Exit", "Exit application", "src/main/resources/exit.gif", file);
        JButton toolBarExit = componentCreator.createJButton("Exit application", "src/main/resources/exit.gif");

        componentCreator.setAction(exit, toolBarExit,
                e -> System.exit(0));

        JMenuItem save = componentCreator.createJMenuItem("Save", "saveFile", "src/main/resources/save.png", file);
        JButton toolBarSave = componentCreator.createJButton("Save file", "src/main/resources/save.png");

        componentCreator.setAction(save, toolBarSave,
                e -> new SaveFile(mainFrame.getGPanel()));

        JMenuItem open = componentCreator.createJMenuItem("Open", "openFile", "src/main/resources/open.png", file);
        JButton toolBarOpen = componentCreator.createJButton("Open file", "src/main/resources/open.png");

        componentCreator.setAction(open, toolBarOpen,
                e -> new OpenFile(mainFrame.getGPanel(), splineDialog));

        JMenu help = componentCreator.createJMenu("Help");

        JMenuItem about = componentCreator.createJMenuItem("About", "About program", "src/main/resources/about.gif", help);
        JButton toolBarAbout = componentCreator.createJButton("About program", "src/main/resources/about.gif");

        componentCreator.setAction(about, toolBarAbout,
                e -> new AboutFrame(mainFrame));

        JMenu scene = componentCreator.createJMenu("Scene");

        JMenuItem spline = componentCreator.createJMenuItem("Spline", "Open spline menu", "src/main/resources/spline.png", scene);
        JButton toolBarSpline = componentCreator.createJButton("Open spline menu", "src/main/resources/spline.png");

        componentCreator.setAction(spline, toolBarSpline, splineDialog);

        JMenuItem reset = componentCreator.createJMenuItem("Reset", "Reset rotation angles", "src/main/resources/reset.png", scene);
        JButton toolBarReset = componentCreator.createJButton("Reset rotation angles", "src/main/resources/reset.png");

        componentCreator.setAction(reset, toolBarReset,
                e -> mainFrame.getGPanel().reset());

    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
}