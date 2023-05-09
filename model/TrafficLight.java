package com.trafficcongestionmitigation.model;

import java.awt.Color;

public class TrafficLight {
    public enum State {
        GREEN(Color.GREEN),
        YELLOW(Color.YELLOW),
        RED(Color.RED);

        private final Color color;

        State(Color color) {
            this.color = color;
        }

        public Color getColor() {
            return color;
        }
    }

    private State currentState;
    private long stateStartTime;
    private final int greenDuration;
    private final int yellowDuration;
    private final int redDuration;

    public TrafficLight(int trafficLightDuration) {
        this.greenDuration = trafficLightDuration / 2;
        this.yellowDuration = trafficLightDuration / 7;
        this.redDuration = trafficLightDuration / 2;
        currentState = State.GREEN;
        stateStartTime = System.currentTimeMillis();
    }

    public TrafficLight() {
        this(67_000); // Default duration of 67 seconds
    }
    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public void update() {
        long elapsedTime = System.currentTimeMillis() - stateStartTime;
        State nextState = currentState;

        switch (currentState) {
            case RED:
                if (elapsedTime >= redDuration) {
                    nextState = State.GREEN;
                }
                break;
            case YELLOW:
                if (elapsedTime >= yellowDuration) {
                    nextState = State.RED;
                }
                break;
            case GREEN:
                if (elapsedTime >= greenDuration) {
                    nextState = State.YELLOW;
                }
                break;
        }

        if (nextState != currentState) {
            setCurrentState(nextState);
            stateStartTime = System.currentTimeMillis();
        }
    }
}
