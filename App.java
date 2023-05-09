package com.trafficcongestionmitigation;

import com.trafficcongestionmitigation.gui.SimulationWindow;
import com.trafficcongestionmitigation.model.Simulation;

public class App {
    public static void main(String[] args) {
        Simulation simulation = new Simulation();
        SimulationWindow simulationFrame = new SimulationWindow(simulation);
        simulation.addSimulationListener(simulationFrame);
        simulation.start();
    }
}
