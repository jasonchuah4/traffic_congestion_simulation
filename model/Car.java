package com.trafficcongestionmitigation.model;

import java.awt.*;

public class Car {
    private static final int idCounter = 0;

    private final int id;
    private int intersectionId;
    private double positionX;
    private double positionY;
    private double speed;
    private double initialSpeed;

    private Color color;

    public Car(int id, int intersectionId, double initialSpeed, double positionX, double positionY) {
        this.id = id;
        this.intersectionId = intersectionId;
        this.initialSpeed = initialSpeed;
        this.speed = initialSpeed;
        this.positionX = positionX;
        this.positionY = positionY; // Set positionY
        this.color = Color.RED; // Set the car color to red by default
    }

    public int getId() {
        return id;
    }

    public int getIntersectionId() {
        return intersectionId;
    }

    public void setIntersectionId(int intersectionId) {
        this.intersectionId = intersectionId;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }


    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getInitialSpeed() {
        return initialSpeed;
    }

    public void setInitialSpeed(double initialSpeed) {
        this.initialSpeed = initialSpeed;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
