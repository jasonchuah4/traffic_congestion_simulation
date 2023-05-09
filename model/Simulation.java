package com.trafficcongestionmitigation.model;

import com.trafficcongestionmitigation.SimulationListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class Simulation {
    private static final int distanceBetweenIntersections = 1000;
    private static final int numIntersections = 3;
    private static final int initialCarCount = 3;

    private static final int timeStep = 1000;

    private final List<Car> cars;
    private final List<Intersection> intersections;
    private ScheduledExecutorService executor;
    private volatile boolean running;

    private final List<Consumer<Long>> timeListeners;
    private final String speedUnit = "m/s";

    private long currentTime;

    private final List<SimulationListener> simulationListeners;

    public Simulation() {
        cars = new ArrayList<>();
        intersections = new ArrayList<>();
        timeListeners = new ArrayList<>();
        simulationListeners = new ArrayList<>();
        currentTime = 0;
        initializeSimulation();
    }


    private void initializeSimulation() {
        for (int i = 0; i < numIntersections; i++) {
            int trafficLightDuration = 60; // seconds
            double positionX = i * distanceBetweenIntersections; // meters
            double positionY = 0.0; // meters
            intersections.add(new Intersection(i, positionX, positionY, trafficLightDuration));
        }

        double carSpacing = 50.0; // meters between each car
        for (int i = 0; i < initialCarCount; i++) {
            int intersectionId = 0;
            double initialSpeed = 10.0; // meters per second
            double positionX = i * carSpacing; // meters
            double positionY = 0.0; // meters
            cars.add(new Car(i, intersectionId, initialSpeed, positionX, positionY));
        }
    }

    public String getSpeedUnit() {
        return speedUnit;
    }
    public String getTimeAsString() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date time = new Date(currentTime * 1000); // Convert seconds to milliseconds
        return formatter.format(time);
    }


    public void addSimulationListener(SimulationListener listener) {
        simulationListeners.add(listener);
    }

    private void notifyCarAdded(Car car) {
        for (SimulationListener listener : simulationListeners) {
            listener.carAdded(car);
        }
    }

    private void notifyIntersectionAdded(Intersection intersection) {
        for (SimulationListener listener : simulationListeners) {
            listener.intersectionAdded(intersection);
        }
    }

    public void addCar() {
        Car car = createCar();
        synchronized (cars) {
            cars.add(car);
            notifyCarAdded(car);
        }
    }


    public void addIntersection() {
        Intersection intersection = createIntersection();
        synchronized (intersections) {
            intersections.add(intersection);
            notifyIntersectionAdded(intersection);
        }
    }


    public Car createCar() {
        int intersectionId = 0;
        double initialSpeed = 10.0; // meters per second
        double positionX = 0.0; // meters
        double positionY = 0.0; // meters
        return new Car(cars.size(), intersectionId, initialSpeed, positionX, positionY);
    }

    public Intersection createIntersection() {
        int id = intersections.size();
        double positionX = id * distanceBetweenIntersections; // meters
        double positionY = 0.0; // meters
        int trafficLightDuration = 60; // seconds
        return new Intersection(id, positionX, positionY, trafficLightDuration);
    }



    public int getIntersectionCount() {
        return intersections.size();
    }

    public double getDistanceBetweenIntersections() {
        return distanceBetweenIntersections;
    }




    public void start() {
        if (executor != null && !executor.isShutdown()) {
            return;
        }
        running = true;
        executor = Executors.newScheduledThreadPool(3); // Change to 3 threads
        executor.scheduleAtFixedRate(this::updateTime, 0, 1, TimeUnit.SECONDS);
        executor.scheduleAtFixedRate(this::updateTrafficLights, 0, 1, TimeUnit.SECONDS);
        executor.scheduleAtFixedRate(this::updateCarPositionsAndTime, 0, timeStep, TimeUnit.MILLISECONDS);
    }

    private void updateTime() {
        currentTime += 1;
        System.out.println("Current time: " + currentTime + " seconds");
    }





    public void updateCarPositionsAndTime() {
        if (!running) {
            return;
        }

        synchronized (intersections) {
            updateTrafficLights();
        }

        synchronized (cars) {
            updateCarPositions();
        }

        currentTime++; // Increase current time by 1 second
        notifyTimeListeners();
    }



    public void pause() {
        running = false;
        if (executor != null && !executor.isTerminated()) {
            executor.shutdown();
        }
    }

    public void resume() {
        if (!running) {
            start();
        }
    }

    public void stop() {
        running = false;
        if (executor != null) {
            executor.shutdown();
        }
    }

    private void updateCarPositions() {
        for (Car car : cars) {
            Intersection currentIntersection = intersections.get(car.getIntersectionId());
            if (car.getIntersectionId() < numIntersections - 1 &&
                    car.getPositionX() >= (car.getIntersectionId() + 1) * distanceBetweenIntersections &&
                    currentIntersection.getTrafficLight().getCurrentState() == TrafficLight.State.RED) {
                car.setSpeed(0);
            } else {
                car.setSpeed(10.0); // Speed in meters per second (m/s)
                car.setPositionX(car.getPositionX() + car.getSpeed()); // Position in meters (m), and Y is always 0
            }
        }
    }


    private void updateTrafficLights() {
        for (Intersection intersection : intersections) {
            intersection.getTrafficLight().update();
        }
    }




    public List<Car> getCars() {
        return cars;
    }

    public List<Intersection> getIntersections() {
        return intersections;
    }

    public long getCurrentTime() {
        return currentTime;
    }


    public void addTimeListener(Consumer<Long> listener) {
        timeListeners.add(listener);
    }

    private void notifyTimeListeners() {
        for (Consumer<Long> listener : timeListeners) {
            listener.accept(currentTime);
        }
    }
}

