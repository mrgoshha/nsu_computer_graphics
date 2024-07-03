package ru.nsu.fit20210.Butkhuzi;

import ru.nsu.fit20210.Butkhuzi.surface.SurfacePanel;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class SaveFile extends FileDialog {

    public SaveFile(SurfacePanel panel) {
        super(new Frame(), "Save file", SAVE);
        setFile("*.lab4");
        setVisible(true);

        if (getFile() != null) {
            File file = new File(getDirectory() + getFile());
            try {
                file.createNewFile();
                writer(panel, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writer(SurfacePanel panel, File file) {
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println(panel.getPoints().size());
            writer.println(panel.parameterN);
            writer.println(panel.parameterM);
            writer.println(panel.parameterM1);

            for (Point point : panel.getPoints()) {
                writer.println(point.x + " " + point.y);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
