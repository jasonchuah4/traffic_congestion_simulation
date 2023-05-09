package com.trafficcongestionmitigation.gui;

import com.trafficcongestionmitigation.model.Intersection;
import com.trafficcongestionmitigation.model.TrafficLight;

import javax.swing.*;
import java.awt.*;

public class TrafficLightPanel extends JPanel {
    private final Intersection intersection;

    public TrafficLightPanel(Intersection intersection) {
        this.intersection = intersection;
        setPreferredSize(new Dimension(50, 10));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw traffic lights for the intersection
        // You can customize the drawing code to represent the traffic light states more visually
        g2d.setColor(intersection.getTrafficLight().getCurrentState() == TrafficLight.State.GREEN ? Color.GREEN : Color.GRAY);
        g2d.fillOval(10, 10, 5, 5);
        g2d.setColor(intersection.getTrafficLight().getCurrentState() == TrafficLight.State.YELLOW ? Color.YELLOW : Color.GRAY);
        g2d.fillOval(10, 20, 5, 5);
        g2d.setColor(intersection.getTrafficLight().getCurrentState() == TrafficLight.State.RED ? Color.RED : Color.GRAY);
        g2d.fillOval(10, 30, 5, 5);
    }

}
