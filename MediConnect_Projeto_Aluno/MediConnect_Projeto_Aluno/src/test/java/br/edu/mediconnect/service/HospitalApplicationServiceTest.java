package br.edu.mediconnect.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.edu.mediconnect.model.Appointment;
import br.edu.mediconnect.model.Patient;
import br.edu.mediconnect.repository.InMemoryAppointmentRepository;
import br.edu.mediconnect.repository.InMemoryPatientRepository;

class HospitalApplicationServiceTest {

    private HospitalApplicationService service;
    private InMemoryPatientRepository patientRepository;
    private InMemoryAppointmentRepository appointmentRepository;

    @BeforeEach
    void setUp() {
        patientRepository = new InMemoryPatientRepository();
        appointmentRepository = new InMemoryAppointmentRepository();

        service = new HospitalApplicationService(
                patientRepository,
                appointmentRepository
        );

        Patient patient = new Patient(
                "P1",
                "Ana",
                "Plano A",
                "62999999999",
                "ana@email.com"
        );

        patientRepository.save(patient);
    }

    @Test
    void scheduleShouldRegisterAppointmentAsScheduled() {
        Appointment appointment = new Appointment(
                "A1",
                "P1",
                "D1",
                "2026-09-20 10:00",
                "CONSULTATION"
        );

        boolean result = service.schedule(appointment);

        assertTrue(result);
        assertEquals("SCHEDULED", appointment.status);
        assertNotNull(appointmentRepository.find("A1"));
        assertEquals(
                appointment,
                appointmentRepository.find("A1")
        );
    }

    @Test
    void scheduleShouldFailWhenPatientDoesNotExist() {
        Appointment appointment = new Appointment(
                "A2",
                "P999",
                "D1",
                "2026-09-20 11:00",
                "CONSULTATION"
        );

        boolean result = service.schedule(appointment);

        assertFalse(result);
        assertEquals("CREATED", appointment.status);
        assertNull(appointmentRepository.find("A2"));
    }
}