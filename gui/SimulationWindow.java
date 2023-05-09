package com.trafficcongestionmitigation.gui;

import com.trafficcongestionmitigation.SimulationListener;
import com.trafficcongestionmitigation.model.Car;
import com.trafficcongestionmitigation.model.Intersection;
import com.trafficcongestionmitigation.model.Simulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SimulationWindow extends JFrame implements SimulationListener {
    private final Simulation simulation;
    private final SimulationPanel simulationPanel;
    private InformationPanel informationPanel;
    private final CarPanel carPanel;
    private final IntersectionPanel intersectionPanel;
    private final TrafficLightPanel[] trafficLightPanels;

    private final JLabel timeLabel;

    public SimulationWindow(Simulation simulation) {
        this.simulation = simulation;
        simulation.addSimulationListener(this);

        setTitle("Traffic Congestion Mitigation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 800);
        setLocationRelativeTo(null);

        simulationPanel = new SimulationPanel(simulation);
        informationPanel = new InformationPanel(simulation);

        setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        controlPanel.add(new ControlPanel(simulation, simulationPanel, informationPanel));
        timeLabel = new JLabel("Current Time: 00:00:00");
        timeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        controlPanel.add(timeLabel);
        add(controlPanel, BorderLayout.NORTH);

        carPanel = new CarPanel(simulation);
        intersectionPanel = new IntersectionPanel(simulation);
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
        bottomPanel.add(carPanel);
        bottomPanel.add(intersectionPanel);
        add(bottomPanel, BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2));
        JPanel simulationPanelWrapper = new JPanel(new BorderLayout());
        simulationPanelWrapper.add(simulationPanel, BorderLayout.CENTER);
        centerPanel.add(simulationPanelWrapper);

        JPanel intersectionsAndTrafficLightsPanel = new JPanel(new GridLayout(simulation.getIntersections().size(), 2));
        trafficLightPanels = new TrafficLightPanel[simulation.getIntersections().size()];
        for (int i = 0; i < simulation.getIntersections().size(); i++) {
            Intersection intersection = simulation.getIntersections().get(i);
            TrafficLightPanel trafficLightPanel = new TrafficLightPanel(intersection);
            trafficLightPanels[i] = trafficLightPanel;

            JPanel intersectionAndTrafficLightPanel = new JPanel(new BorderLayout());
            intersectionAndTrafficLightPanel.add(trafficLightPanel, BorderLayout.NORTH);
            intersectionAndTrafficLightPanel.add(intersectionPanel, BorderLayout.CENTER);
            intersectionsAndTrafficLightsPanel.add(intersectionAndTrafficLightPanel);
        }
        centerPanel.add(intersectionsAndTrafficLightsPanel);

        add(centerPanel, BorderLayout.CENTER);




        // Add the timer to update the time label every second
        int delay = 1000; //milliseconds
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                updateTimeLabel();
            }
        };
        new Timer(delay, taskPerformer).start();

        // Schedule a task to update traffic light panels every second
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Runnable updateTrafficLights = () -> {
            TrafficLightPanel[] trafficLightPanels = new TrafficLightPanel[simulation.getIntersections().size()];
            for (int i = 0; i < simulation.getIntersections().size(); i++) {
                trafficLightPanels[i] = new TrafficLightPanel(simulation.getIntersections().get(i));
            }
            for (TrafficLightPanel panel : trafficLightPanels) {
                panel.repaint();
            }
        };
        scheduler.scheduleAtFixedRate(updateTrafficLights, 1, 1, TimeUnit.SECONDS);

        // Schedule a task to update car labels every second
        ScheduledExecutorService carInfoScheduler = Executors.newScheduledThreadPool(1);
        Runnable updateCarLabels = () -> {
            for (int i = 0; i < carPanel.getCarLabels().size(); i++) {
                Car car = simulation.getCars().get(i);
                JLabel carLabel = carPanel.getCarLabels().get(i);
                carPanel.updateCarLabel(car, carLabel);
            }
        };
        carInfoScheduler.scheduleAtFixedRate(updateCarLabels, 1, 1, TimeUnit.SECONDS);

        setVisible(true);
    }

    @Override
    public void carAdded(Car car) {
        simulationPanel.repaint();
        carPanel.addCar(car);
    }

    private void updateTimeLabel() {
        String currentTime = simulation.getTimeAsString();
        timeLabel.setText(currentTime);
    }


    @Override
    public void intersectionAdded(Intersection intersection) {
        simulationPanel.repaint();
        intersectionPanel.addIntersection(intersection);
    }


}


