package com.trafficcongestionmitigation.gui;

import com.trafficcongestionmitigation.model.Simulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel implements ActionListener {
    private final JButton startButton;
    private final JButton pauseButton;
    private final JButton resumeButton;
    private final JButton stopButton;
    private final JButton addCarButton;
    private final JButton addIntersectionButton;

    private final Simulation simulation;
    private final SimulationPanel simulationPanel;
    private final InformationPanel informationPanel;

    public ControlPanel(Simulation simulation, SimulationPanel simulationPanel, InformationPanel informationPanel) {
        this.simulation = simulation;
        this.simulationPanel = simulationPanel;
        this.informationPanel = informationPanel;
        setLayout(new FlowLayout());

        startButton = new JButton("Start");
        startButton.addActionListener(this);
        add(startButton);

        pauseButton = new JButton("Pause");
        pauseButton.addActionListener(this);
        add(pauseButton);

        resumeButton = new JButton("Resume");
        resumeButton.addActionListener(this);
        add(resumeButton);

        stopButton = new JButton("Stop");
        stopButton.addActionListener(this);
        add(stopButton);

        addCarButton = new JButton("Add Car");
        addCarButton.addActionListener(this);
        add(addCarButton);

        addIntersectionButton = new JButton("Add Intersection");
        addIntersectionButton.addActionListener(this);
        add(addIntersectionButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == startButton) {
            simulation.start();
        } else if (source == pauseButton) {
            simulation.pause();
        } else if (source == resumeButton) {
            simulation.resume();
        } else if (source == stopButton) {
            simulation.stop();
        } else if (source == addCarButton) {
            simulation.addCar();
        } else if (source == addIntersectionButton) {
            simulation.addIntersection();
        }
    }
}

