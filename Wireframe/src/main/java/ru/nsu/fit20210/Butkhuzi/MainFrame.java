package ru.nsu.fit20210.Butkhuzi;


import ru.nsu.fit20210.Butkhuzi.menuCreator.MenuCreator;
import ru.nsu.fit20210.Butkhuzi.surface.SurfacePanel;

import javax.swing.*;
import java.awt.*;


public class MainFrame extends JFrame {
    private JMenuBar menu;
    private JToolBar toolBar;
    private SurfacePanel gPanel;

    public MainFrame() {
        setPreferredSize(new Dimension(800, 600));
        setTitle("Filter");

        //чтобы изображение было по центру любого экрана
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width / 2 - getPreferredSize().width / 2,
                screenSize.height / 2 - getPreferredSize().height / 2);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //панель
        gPanel = new SurfacePanel(800, 600);
        add(gPanel);

        // создание меню
        menu = new JMenuBar();
        setJMenuBar(menu);
        toolBar = new JToolBar();
        add(toolBar, BorderLayout.PAGE_START);

        MenuCreator menuCreator = new MenuCreator(this);
        menuCreator.createMenu();

        pack();
        setVisible(true);
    }

    public JMenuBar getMenu() {
        return this.menu;
    }

    public JToolBar getToolBar() {
        return this.toolBar;
    }

    public SurfacePanel getGPanel() {
        return this.gPanel;
    }
}

