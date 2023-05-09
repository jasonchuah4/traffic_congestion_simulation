package com.trafficcongestionmitigation;
import com.trafficcongestionmitigation.model.Car;
import com.trafficcongestionmitigation.model.Intersection;


public interface SimulationListener {
    void carAdded(Car car);
    void intersectionAdded(Intersection intersection);
}
