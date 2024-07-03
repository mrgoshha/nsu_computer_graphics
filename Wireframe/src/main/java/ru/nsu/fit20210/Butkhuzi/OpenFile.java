package ru.nsu.fit20210.Butkhuzi;


import ru.nsu.fit20210.Butkhuzi.spline.SplineDialog;
import ru.nsu.fit20210.Butkhuzi.surface.SurfacePanel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class OpenFile extends JFrame {
    private SurfacePanel panel;
    private SplineDialog dialog;

    public OpenFile(SurfacePanel panel, SplineDialog splineDialog) {
        setDialog(splineDialog);
        setPanel(panel);
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("lab4", "lab4"));

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            setFigure(selectedFile);
        }
    }

    private void setFigure(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            int splineSize = Integer.parseInt(reader.readLine());
            int n = Integer.parseInt(reader.readLine());
            int m = Integer.parseInt(reader.readLine());
            int m1 = Integer.parseInt(reader.readLine());

            panel.setParameterN(n);
            dialog.n = n;
            panel.splinePanel.setParameterN(n);
            panel.setParameterM(m);
            dialog.m = m;
            panel.setParameterM1(m1);
            dialog.m1 = m1;

            ArrayList<Point> points = new ArrayList<>();
            for (int i = 0; i < splineSize; i++) {
                String[] pointsString = reader.readLine().split(" ");
                int x = Integer.parseInt(pointsString[0]);
                int y = Integer.parseInt(pointsString[1]);

                points.add(new Point(x, y));
            }

            panel.spline.setPoints(points);
            panel.splinePanel.paintSplineImage();
            panel.setSplinePoints();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPanel(SurfacePanel panel) {
        this.panel = panel;
    }

    public void setDialog(SplineDialog dialog) {
        this.dialog = dialog;
    }
}

