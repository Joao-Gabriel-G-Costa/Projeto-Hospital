package br.edu.mediconnect.patterns.observer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class HospitalPublisherTest {

    @Test
    void publishShouldNotifyAllSubscribedObservers() {
        HospitalPublisher publisher = new HospitalPublisher();

        int[] firstObserverCalls = {0};
        int[] secondObserverCalls = {0};

        HospitalObserver firstObserver = new HospitalObserver() {
            @Override
            public void update(String entityId, String event) {
                firstObserverCalls[0]++;
            }
        };

        HospitalObserver secondObserver = new HospitalObserver() {
            @Override
            public void update(String entityId, String event) {
                secondObserverCalls[0]++;
            }
        };

        publisher.subscribe(firstObserver);
        publisher.subscribe(secondObserver);

        publisher.publish("A1", "SCHEDULED");

        assertEquals(1, firstObserverCalls[0]);
        assertEquals(1, secondObserverCalls[0]);
    }
}