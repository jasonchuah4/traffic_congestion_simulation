package com.trafficcongestionmitigation.gui;

import com.trafficcongestionmitigation.model.Car;
import com.trafficcongestionmitigation.model.Intersection;
import com.trafficcongestionmitigation.model.Simulation;
import com.trafficcongestionmitigation.model.TrafficLight;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

public class InformationPanel extends JPanel {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 600;

    private Simulation simulation;

    private JLabel timeLabel;
    private ArrayList<JLabel> trafficLightLabels;
    private ArrayList<JLabel> carInfoLabels;

    public InformationPanel(Simulation simulation) {
        this.simulation = simulation;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(5, 10, 5, 10);

        TitledBorder border = new TitledBorder("Simulation Information");
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitleFont(new Font("Arial", Font.BOLD, 18));
        setBorder(border);

        timeLabel = new JLabel();
        trafficLightLabels = new ArrayList<>();
        carInfoLabels = new ArrayList<>();

        gbc.gridy = 0;
        add(timeLabel, gbc);

        for (Intersection intersection : simulation.getIntersections()) {
            JLabel trafficLightLabel = new JLabel();
            trafficLightLabels.add(trafficLightLabel);
            gbc.gridy++;
            add(trafficLightLabel, gbc);
        }

        // Separator line
        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        add(new JSeparator(JSeparator.HORIZONTAL), gbc);

        // Reset GridBagConstraints
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;

        gbc.insets = new Insets(15, 10, 5, 10);
        for (Car car : simulation.getCars()) {
            JLabel carInfoLabel = new JLabel();
            carInfoLabels.add(carInfoLabel);
            gbc.gridy++;
            add(carInfoLabel, gbc);
        }

        simulation.addTimeListener(e -> updateInformation());
        updateInformation();
    }

    private void updateInformation() {
        timeLabel.setText("Current Time: " + simulation.getCurrentTime() + " s");
        timeLabel.setFont(new Font("Arial", Font.BOLD, 14));

        int i = 0;
        for (Intersection intersection : simulation.getIntersections()) {
            TrafficLight.State state = intersection.getTrafficLight().getCurrentState();
            trafficLightLabels.get(i).setText("Intersection " + intersection.getId() + ": " + state);
            trafficLightLabels.get(i).setFont(new Font("Arial", Font.PLAIN, 14));
            i++;
        }

        i = 0;
        for (Car car : simulation.getCars()) {
            carInfoLabels.get(i).setText("Car " + car.getId() + " - Position: (" + String.format("%.2f", car.getPositionX()) + ", " + String.format("%.2f", car.getPositionY()) + "), Speed: " + String.format("%.2f", car.getSpeed()) + " " + simulation.getSpeedUnit());
            carInfoLabels.get(i).setFont(new Font("Arial", Font.PLAIN, 14));
            i++;
        }
    }
}
