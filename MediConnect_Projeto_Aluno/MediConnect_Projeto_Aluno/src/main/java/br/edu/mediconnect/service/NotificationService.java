package br.edu.mediconnect.service;

import br.edu.mediconnect.legacy.WhatsappHospitalApi;
import br.edu.mediconnect.model.Appointment;
import br.edu.mediconnect.model.ExamRequest;
import br.edu.mediconnect.model.Patient;

public class NotificationService {

    public void notify(String channel, String destination, String text) {

        if ("EMAIL".equals(channel)) {
            System.out.println("EMAIL " + destination + ": " + text);

        } else if ("SMS".equals(channel)) {
            System.out.println("SMS " + destination + ": " + text);

        } else if ("WHATSAPP".equals(channel)) {
            new WhatsappHospitalApi().sendMessage(destination, text);
        }
    }

    public void notifyAppointmentScheduled(
            Patient patient,
            Appointment appointment) {

        notify(
                "EMAIL",
                patient.email,
                "Consulta " + appointment.id
                        + " agendada em "
                        + appointment.dateTime
        );
    }

    public void notifyExamStatus(
            Patient patient,
            ExamRequest examRequest) {

        notify(
                "WHATSAPP",
                patient.phone,
                "Exame " + examRequest.id
                        + " = "
                        + examRequest.status
        );
    }
}