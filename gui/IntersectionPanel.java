package com.trafficcongestionmitigation.gui;

import com.trafficcongestionmitigation.model.Intersection;
import com.trafficcongestionmitigation.model.Simulation;

import javax.swing.*;
import java.awt.*;

public class IntersectionPanel extends JPanel {
    private final Simulation simulation;
    private final GridBagConstraints constraints;

    public IntersectionPanel(Simulation simulation) {
        this.simulation = simulation;

        setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.gridx = 0;
        constraints.gridy = 0;

        for (Intersection intersection : simulation.getIntersections()) {
            addIntersection(intersection);
        }
    }

    public void addIntersection(Intersection intersection) {
        add(new JLabel("Intersection " + intersection.getId() + ": Traffic Light State = " + intersection.getTrafficLight().getCurrentState()), constraints);
        constraints.gridy++;
    }
}
