package com.trafficcongestionmitigation.gui;

import com.trafficcongestionmitigation.model.Car;
import com.trafficcongestionmitigation.model.Intersection;
import com.trafficcongestionmitigation.model.Simulation;
import com.trafficcongestionmitigation.model.TrafficLight;

import javax.swing.*;
import java.awt.*;

public class SimulationPanel extends JPanel {

    private static final int SIMULATION_WIDTH = 1000;
    private static final int SIMULATION_HEIGHT = 1000;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 400;

    private static final int CAR_SIZE = 10;
    private static final int INTERSECTION_SIZE = 40;

    private final Simulation simulation;

    public SimulationPanel(Simulation simulation) {
        this.simulation = simulation;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        simulation.addTimeListener(this::repaint);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        // Scale the components based on the panel size
        double scaleX = (double) getWidth() / SIMULATION_WIDTH;
        double scaleY = (double) getHeight() / SIMULATION_HEIGHT;
        g2.scale(scaleX, scaleY);
        drawIntersections(g);
        drawCars(g);
    }

    private void drawIntersections(Graphics g) {
        for (Intersection intersection : simulation.getIntersections()) {
            g.setColor(intersection.getTrafficLight().getCurrentState().getColor());
            int x = (int) (intersection.getPositionX() * WIDTH / (simulation.getIntersectionCount() * simulation.getDistanceBetweenIntersections()));
            int y = HEIGHT / 2 - INTERSECTION_SIZE / 2;
            g.fillRect(x, y, INTERSECTION_SIZE, INTERSECTION_SIZE);
        }
    }
    private void drawCars(Graphics g) {
        for (Car car : simulation.getCars()) {
            g.setColor(Color.BLUE);
            int x = (int) (car.getPositionX() * WIDTH / (simulation.getIntersectionCount() * simulation.getDistanceBetweenIntersections()));
            int y = HEIGHT / 2 - CAR_SIZE / 2;
            g.fillOval(x, y, CAR_SIZE, CAR_SIZE);
        }
    }


}
