package br.edu.mediconnect.service;

import br.edu.mediconnect.model.*;
import br.edu.mediconnect.repository.*;
import br.edu.mediconnect.patterns.adapter.*;
import br.edu.mediconnect.patterns.observer.*;

import java.util.*;

public class HospitalApplicationService {

    public final InMemoryPatientRepository patients;
    public final InMemoryAppointmentRepository appointments;

    private final NotificationService notifications =
            new NotificationService();

    private final HospitalPublisher publisher =
            new HospitalPublisher();

    private final List<Admission> admissions =
            new ArrayList<>();

    public HospitalApplicationService(
            InMemoryPatientRepository p,
            InMemoryAppointmentRepository a) {

        patients = p;
        appointments = a;

        publisher.subscribe(new PatientNotificationObserver());
        publisher.subscribe(new AuditObserver());
    }

    public boolean schedule(Appointment a) {

        Patient p = patients.find(a.patientId);

        if (p == null) {
            return false;
        }

        a.status = "SCHEDULED";

        appointments.save(a);

        notifications.notifyAppointmentScheduled(p, a);

        publisher.publish(a.id, "SCHEDULED");

        return true;
    }

    public boolean requestExam(
            ExamRequest e,
            double estimatedCost) {

        Patient p = patients.find(e.patientId);

        if (p == null) {
            return false;
        }

        e.authorized =
                new HealthPlanAdapter()
                        .authorize(
                                p.id,
                                e.examCode,
                                estimatedCost
                        );

        if (e.authorized) {

            boolean sent =
                    new LabAdapter()
                            .request(
                                    p.id,
                                    e.examCode
                            );

            e.status =
                    sent
                            ? "SENT_TO_LAB"
                            : "LAB_ERROR";

        } else {
            e.status = "DENIED";
        }

        notifications.notifyExamStatus(p, e);

        publisher.publish(e.id, e.status);

        return e.authorized;
    }

    public Admission admit(
            String id,
            String patientId,
            String room) {

        Admission a =
                new Admission(
                        id,
                        patientId,
                        room
                );

        admissions.add(a);

        publisher.publish(
                id,
                "ADMITTED"
        );

        return a;
    }

    public HospitalPublisher publisher() {
        return publisher;
    }

    public List<Admission> admissions() {
        return admissions;
    }
}