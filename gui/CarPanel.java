package com.trafficcongestionmitigation.gui;

import com.trafficcongestionmitigation.model.Car;
import com.trafficcongestionmitigation.model.Simulation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CarPanel extends JPanel {
    private final Simulation simulation;
    private final GridBagConstraints constraints;
    private final List<JLabel> carLabels;

    public CarPanel(Simulation simulation) {
        this.simulation = simulation;

        setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.gridx = 0;
        constraints.gridy = 0;

        carLabels = new ArrayList<>();
        for (Car car : simulation.getCars()) {
            carLabels.add(addCar(car));
        }
    }

    public JLabel addCar(Car car) {
        JLabel carLabel = new JLabel("Car " + car.getId() + ": Position = " + car.getPositionX() + ", Speed = " + car.getSpeed() + " m/s, Intersection = " + car.getIntersectionId());
        add(carLabel, constraints);
        constraints.gridy++;
        return carLabel;
    }

    public List<JLabel> getCarLabels() {
        return carLabels;
    }

    public void updateCarLabel(Car car, JLabel carLabel) {
        carLabel.setText("Car " + car.getId() + ": Position = " + car.getPositionX() + ", Speed = " + car.getSpeed() + " m/s, Intersection = " + car.getIntersectionId());
    }
}


