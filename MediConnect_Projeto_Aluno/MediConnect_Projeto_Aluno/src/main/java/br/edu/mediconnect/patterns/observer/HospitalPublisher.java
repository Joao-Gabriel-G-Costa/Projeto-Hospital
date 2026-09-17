package br.edu.mediconnect.patterns.observer;

import java.util.ArrayList;
import java.util.List;

public class HospitalPublisher {

    private final List<HospitalObserver> observers = new ArrayList<>();

    public void subscribe(HospitalObserver observer) {
        observers.add(observer);
    }

    public void publish(String entityId, String event) {
        for (HospitalObserver observer : observers) {
            observer.update(entityId, event);
        }
    }
}