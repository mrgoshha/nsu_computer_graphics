package ru.nsu.fit20210.Butkhuzi.spline;

import ru.nsu.fit20210.Butkhuzi.surface.SurfacePanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SplineDialog implements ActionListener {
    private SplinePanel splinePanel;
    private SurfacePanel surfacePanel;

    public int n = 3;// число отрезков между двумя точками
    public int m = 5;
    public int m1 = 2;

    private JRadioButton addPoint;
    private JRadioButton removePoint;
    private JRadioButton editPosPoint;


    public SplineDialog(SplinePanel splinePanel, SurfacePanel surfacePanel) {
        this.splinePanel = splinePanel;
        this.surfacePanel = surfacePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JPanel allPanel = new JPanel(new BorderLayout());
        JPanel parameterPanel = new JPanel();

        // k
        JPanel parameterKSplinePanel = new JPanel(new FlowLayout());
        ButtonGroup groupMode = new ButtonGroup();

        ActionListener listenerMode = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setMode();
            }
        };

        addPoint = new JRadioButton("add point", false);
        addPoint.setIcon(new ImageIcon("src/main/resources/add.png"));
        groupMode.add(addPoint);
        addPoint.addActionListener(listenerMode);

        removePoint = new JRadioButton("remove point", false);
        removePoint.setIcon(new ImageIcon("src/main/resources/remove.png"));
        groupMode.add(removePoint);
        removePoint.addActionListener(listenerMode);

        editPosPoint = new JRadioButton(new ImageIcon("src/main/resources/editMode.png"));
        groupMode.add(editPosPoint);
        editPosPoint.addActionListener(listenerMode);

        parameterKSplinePanel.add(addPoint);
        parameterKSplinePanel.add(removePoint);
        parameterKSplinePanel.add(editPosPoint);

        parameterPanel.add(parameterKSplinePanel);

        // n
        JPanel parameterNSplinePanel = new JPanel(new FlowLayout());
        JLabel parameterNSplineLabel = new JLabel("N  ");

        SpinnerNumberModel nSpinModel = new SpinnerNumberModel(n, 1, 100, 1);
        JSpinner nSpin = new JSpinner(nSpinModel);

        nSpin.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSpinner source = (JSpinner) e.getSource();
                int value = Integer.parseInt(source.getValue().toString());
                n = value;
                splinePanel.setParameterN(value);
                surfacePanel.setParameterN(value);

            }
        });

        parameterNSplinePanel.add(parameterNSplineLabel);
        parameterNSplinePanel.add(nSpin);

        parameterPanel.add(parameterNSplinePanel);

        //m
        JPanel parameterMSplinePanel = new JPanel(new FlowLayout());
        JLabel parameterMSplineLabel = new JLabel("M  ");

        SpinnerNumberModel mSpinModel = new SpinnerNumberModel(m, 3, 100, 1);
        JSpinner mSpin = new JSpinner(mSpinModel);

        mSpin.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSpinner source = (JSpinner) e.getSource();
                int value = Integer.parseInt(source.getValue().toString());
                m = value;
                surfacePanel.setParameterM(value);
            }
        });

        parameterMSplinePanel.add(parameterMSplineLabel);
        parameterMSplinePanel.add(mSpin);

        parameterPanel.add(parameterMSplinePanel);

        //m
        JPanel parameterM1SplinePanel = new JPanel(new FlowLayout());
        JLabel parameterM1SplineLabel = new JLabel("M1  ");

        SpinnerNumberModel m1SpinModel = new SpinnerNumberModel(m1, 1, 100, 1);
        JSpinner m1Spin = new JSpinner(m1SpinModel);

        m1Spin.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSpinner source = (JSpinner) e.getSource();
                int value = Integer.parseInt(source.getValue().toString());
                m1 = value;
                surfacePanel.setParameterM1(value);
            }
        });

        parameterM1SplinePanel.add(parameterM1SplineLabel);
        parameterM1SplinePanel.add(m1Spin);

        parameterPanel.add(parameterM1SplinePanel);


        allPanel.add(splinePanel, BorderLayout.CENTER);
        allPanel.add(parameterPanel, BorderLayout.SOUTH);

        int option = JOptionPane.showOptionDialog(null, allPanel, "Enter the parameters",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

        if (option == JOptionPane.OK_OPTION) {
            surfacePanel.setSplinePoints();
        }
    }

    private void setMode() {
        splinePanel.addPoint = addPoint.isSelected();
        splinePanel.removePoint = removePoint.isSelected();
        splinePanel.editPosPoint = editPosPoint.isSelected();
    }

}
