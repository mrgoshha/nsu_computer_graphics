import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DialogWindow implements ActionListener {
    JRadioButtonMenuItem menuItem;
    JPanel panel;
    ImagePanel mainPanel;
    JSlider thicknessSlider;
    JSlider countSidesSlider;
    JSlider pivotSlider;
    JSlider radiusSlider;
    JColorChooser colorChooser;

    private int countSides = 5;
    private int radius = 50;
    private int pivot = 0;
    private int thickness = 1;

    private boolean isLine = false;
    private boolean isColor = false;

    int minCountSides = 4;
    int maxCountSides = 8;
    int maxSize;
    Color color;

    DialogWindow(JRadioButtonMenuItem menu, ImagePanel panel) {
        this(menu);
        mainPanel = panel;
    }

    DialogWindow(JRadioButtonMenuItem menu) {
        this.menuItem = menu;
        if (menuItem.getToolTipText().equals("draw line"))
            isLine = true;
        else if (menuItem.getToolTipText().equals("create your color"))
            isColor = true;
        else if (menuItem.getToolTipText().equals("draw square"))
            countSides = 4;
        else if (menuItem.getToolTipText().equals("draw pentagon"))
            countSides = 5;
        else if (menuItem.getToolTipText().equals("draw hexagon"))
            countSides = 6;
        else if (menuItem.getToolTipText().equals("draw star")) {
            minCountSides = 3;
            maxCountSides = 16;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // thickness (в данном случае только для линии)
        if (isLine) {
            JPanel thicknessPanel = new JPanel(new FlowLayout());
            JLabel thicknessLabel = new JLabel("Thickness: ");
            thicknessSlider = new JSlider(JSlider.HORIZONTAL, 1, 10, thickness);
            thicknessSlider.setMajorTickSpacing(1);// большие деления
            thicknessSlider.setPaintTicks(true);// показывать деления
            thicknessSlider.setPaintLabels(true);// показывать цифры
            thicknessSlider.setSnapToTicks(true);// округление до ближайшего значения

            SpinnerNumberModel thicknessSpinModel = new SpinnerNumberModel(thickness, 1, 10, 1);
            JSpinner thicknessSpin = new JSpinner(thicknessSpinModel);


            thicknessPanel.add(thicknessLabel);
            thicknessPanel.add(thicknessSlider);
            thicknessPanel.add(thicknessSpin);


            thicknessSlider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    JSlider source = (JSlider) e.getSource();
                    thicknessSpin.setValue(source.getValue());
                }
            });

            thicknessSpin.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    JSpinner source = (JSpinner) e.getSource();
                    int value = Integer.parseInt(source.getValue().toString());
                    thicknessSlider.setValue(value);
                }
            });
            panel.add(thicknessPanel);
        }

        // параметр для создания нового цвета
        if (isColor) {
            colorChooser = new JColorChooser();
            panel.add(colorChooser);
        }

        // countSides (параметры далее только для звезд и многоугольников)
        if (!(isLine || isColor)) {
            maxSize = Math.min(mainPanel.img.getWidth(), mainPanel.img.getHeight());

            JPanel countSidesPanel = new JPanel(new FlowLayout());
            JLabel countSidesLabel = new JLabel("Sides: ");
            countSidesSlider = new JSlider(JSlider.HORIZONTAL, minCountSides, maxCountSides, countSides);
            countSidesSlider.setMajorTickSpacing(1);// большие деления
            countSidesSlider.setPaintTicks(true);// показывать деления
            countSidesSlider.setPaintLabels(true);// показывать цифры
            countSidesSlider.setSnapToTicks(true);// округление до ближайшего значения

            SpinnerNumberModel countSidesSpinModel = new SpinnerNumberModel(countSides, minCountSides, maxCountSides, 1);
            JSpinner countSidesSpin = new JSpinner(countSidesSpinModel);


            countSidesPanel.add(countSidesLabel);
            countSidesPanel.add(countSidesSlider);
            countSidesPanel.add(countSidesSpin);


            countSidesSlider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    JSlider source = (JSlider) e.getSource();
                    countSidesSpin.setValue(source.getValue());
                }
            });

            countSidesSpin.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    JSpinner source = (JSpinner) e.getSource();
                    int value = Integer.parseInt(source.getValue().toString());
                    countSidesSlider.setValue(value);
                }
            });
            panel.add(countSidesPanel);

            // Radius
            JPanel radiusPanel = new JPanel(new FlowLayout());
            JLabel radiusLabel = new JLabel("Radius: ");
            radiusSlider = new JSlider(JSlider.HORIZONTAL, 20, maxSize, radius);
            radiusSlider.setMajorTickSpacing((maxSize / 10));// большие деления
            radiusSlider.setPaintTicks(true);// показывать деления


            SpinnerNumberModel radiusSpinModel = new SpinnerNumberModel(radius, 20, maxSize, 10);
            JSpinner radiusSpin = new JSpinner(radiusSpinModel);


            radiusPanel.add(radiusLabel);
            radiusPanel.add(radiusSlider);
            radiusPanel.add(radiusSpin);


            radiusSlider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    JSlider source = (JSlider) e.getSource();
                    radiusSpin.setValue(source.getValue());
                }
            });

            radiusSpin.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    JSpinner source = (JSpinner) e.getSource();
                    int value = Integer.parseInt(source.getValue().toString());
                    radiusSlider.setValue(value);
                }
            });

            //pivot
            JPanel pivotPanel = new JPanel(new FlowLayout());
            JLabel pivotLabel = new JLabel("Pivot: ");
            pivotSlider = new JSlider(JSlider.HORIZONTAL, 0, 360, pivot);
            pivotSlider.setMajorTickSpacing(45);// большие деления
            pivotSlider.setPaintTicks(true);// показывать деления
            pivotSlider.setPaintLabels(true);// показывать цифры

            SpinnerNumberModel pivotSpinModel = new SpinnerNumberModel(pivot, 0, 360, 5);
            JSpinner pivotSpin = new JSpinner(pivotSpinModel);


            pivotPanel.add(pivotLabel);
            pivotPanel.add(pivotSlider);
            pivotPanel.add(pivotSpin);


            pivotSlider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    JSlider source = (JSlider) e.getSource();
                    pivotSpin.setValue(source.getValue());
                }
            });

            pivotSpin.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    JSpinner source = (JSpinner) e.getSource();
                    int value = Integer.parseInt(source.getValue().toString());
                    pivotSlider.setValue(value);
                }
            });

            panel.add(radiusPanel);
            panel.add(pivotPanel);

        }

        int option = JOptionPane.showOptionDialog(null, panel, "Enter the parameters",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

        if (option == JOptionPane.OK_OPTION) {
            if (isLine)
                thickness = thicknessSlider.getValue();
            else if (isColor)
                color = colorChooser.getColor();
            else {
                countSides = countSidesSlider.getValue();
                radius = radiusSlider.getValue();
                pivot = pivotSlider.getValue();
            }
        }
    }


    public int getRadius() {
        return radius;
    }

    public int getCountSides() {
        return countSides;
    }

    public int getPivot() {
        return pivot;
    }

    public int getThickness() {
        return thickness;
    }

    public Color getColor() {
        return color;
    }

}
