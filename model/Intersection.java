package com.trafficcongestionmitigation.model;

public class Intersection {
    private final int id;
    private final double positionX;
    private final double positionY;
    private final TrafficLight trafficLight;

    public Intersection(int id, double positionX, double positionY, int trafficLightDuration) {
        this.id = id;
        this.positionX = positionX;
        this.positionY = positionY;
        this.trafficLight = new TrafficLight();
    }

    public int getId() {
        return id;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public TrafficLight getTrafficLight() {
        return trafficLight;
    }
}

