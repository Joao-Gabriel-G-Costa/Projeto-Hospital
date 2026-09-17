package br.edu.mediconnect.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import br.edu.mediconnect.model.Appointment;
import br.edu.mediconnect.patterns.facade.MediConnectFacade;
import br.edu.mediconnect.patterns.factory.AppointmentFactory;

public class AppointmentConsole {

    private static final String PATIENT_ID = "P1";
    private static final String PATIENT_NAME = "Ana";

    private static final String DOCTOR_ID = "D1";
    private static final String DOCTOR_NAME = "Médico D1";

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final DateTimeFormatter TIME_FORMAT =
            DateTimeFormatter.ofPattern("HH:mm");

    private final MediConnectFacade facade;
    private final Scanner scanner;

    private int appointmentSequence = 1;

    public AppointmentConsole(MediConnectFacade facade) {
        this.facade = facade;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        boolean running = true;

        while (running) {
            showMainMenu();

            String option = scanner.nextLine().trim();

            switch (option) {
                case "1":
                    scheduleAppointment();
                    break;

                case "0":
                    System.out.println();
                    System.out.println("Encerrando o MediConnect...");
                    running = false;
                    break;

                default:
                    showInvalidOption();
                    break;
            }
        }
    }

    private void showMainMenu() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("              MEDICONNECT");
        System.out.println("========================================");
        System.out.println();
        System.out.println("1 - Agendar consulta");
        System.out.println("0 - Sair");
        System.out.println();
        System.out.print("Escolha uma opção: ");
    }

    private void scheduleAppointment() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("       AGENDAMENTO DE CONSULTA");
        System.out.println("========================================");

        String patientId = selectPatient();

        if (patientId == null) {
            return;
        }

        String doctorId = selectDoctor();

        if (doctorId == null) {
            return;
        }

        LocalDate date = readDate();
        LocalTime time = readTime();

        String appointmentId = generateAppointmentId();

        LocalDateTime dateTime = LocalDateTime.of(date, time);

        showAppointmentSummary(
                appointmentId,
                PATIENT_NAME,
                DOCTOR_NAME,
                date,
                time
        );

        if (!confirmAppointment()) {
            System.out.println();
            System.out.println("Agendamento cancelado.");
            return;
        }

        Appointment appointment = AppointmentFactory.create(
                "CONSULTATION",
                appointmentId,
                patientId,
                doctorId,
                dateTime.toString()
        );

        boolean scheduled = facade.schedule(appointment);

        if (scheduled) {
            showSuccess(appointment, date, time);
        } else {
            showError();
        }
    }

    private String selectPatient() {
        while (true) {
            System.out.println();
            System.out.println("Pacientes disponíveis:");
            System.out.println();
            System.out.println("1 - " + PATIENT_NAME + " (" + PATIENT_ID + ")");
            System.out.println("0 - Voltar");
            System.out.println();
            System.out.print("Escolha o paciente: ");

            String option = scanner.nextLine().trim();

            switch (option) {
                case "1":
                    return PATIENT_ID;

                case "0":
                    return null;

                default:
                    System.out.println();
                    System.out.println("Paciente inválido. Escolha uma opção da lista.");
                    break;
            }
        }
    }

    private String selectDoctor() {
        while (true) {
            System.out.println();
            System.out.println("Médicos disponíveis:");
            System.out.println();
            System.out.println("1 - " + DOCTOR_NAME);
            System.out.println("0 - Voltar");
            System.out.println();
            System.out.print("Escolha o médico: ");

            String option = scanner.nextLine().trim();

            switch (option) {
                case "1":
                    return DOCTOR_ID;

                case "0":
                    return null;

                default:
                    System.out.println();
                    System.out.println("Médico inválido. Escolha uma opção da lista.");
                    break;
            }
        }
    }

    private LocalDate readDate() {
        while (true) {
            System.out.println();
            System.out.print("Data da consulta (DD/MM/AAAA): ");

            String input = scanner.nextLine().trim();

            try {
                return LocalDate.parse(input, DATE_FORMAT);
            } catch (DateTimeParseException exception) {
                System.out.println();
                System.out.println("Data inválida. Exemplo válido: 20/09/2026");
            }
        }
    }

    private LocalTime readTime() {
        while (true) {
            System.out.println();
            System.out.print("Horário da consulta (HH:MM): ");

            String input = scanner.nextLine().trim();

            try {
                return LocalTime.parse(input, TIME_FORMAT);
            } catch (DateTimeParseException exception) {
                System.out.println();
                System.out.println("Horário inválido. Exemplo válido: 10:30");
            }
        }
    }

    private void showAppointmentSummary(
            String appointmentId,
            String patientName,
            String doctorName,
            LocalDate date,
            LocalTime time
    ) {
        System.out.println();
        System.out.println("========================================");
        System.out.println("        RESUMO DO AGENDAMENTO");
        System.out.println("========================================");
        System.out.println();
        System.out.println("Consulta: " + appointmentId);
        System.out.println("Paciente: " + patientName);
        System.out.println("Médico: " + doctorName);
        System.out.println("Data: " + date.format(DATE_FORMAT));
        System.out.println("Horário: " + time.format(TIME_FORMAT));
    }

    private boolean confirmAppointment() {
        while (true) {
            System.out.println();
            System.out.print("Confirmar agendamento? (S/N): ");

            String confirmation = scanner.nextLine().trim();

            if (confirmation.equalsIgnoreCase("S")) {
                return true;
            }

            if (confirmation.equalsIgnoreCase("N")) {
                return false;
            }

            System.out.println();
            System.out.println("Digite S para confirmar ou N para cancelar.");
        }
    }

    private String generateAppointmentId() {
        String id = "A" + appointmentSequence;
        appointmentSequence++;
        return id;
    }

    private void showSuccess(
            Appointment appointment,
            LocalDate date,
            LocalTime time
    ) {
        System.out.println();
        System.out.println("========================================");
        System.out.println("       AGENDAMENTO REALIZADO");
        System.out.println("========================================");
        System.out.println();
        System.out.println("Consulta: " + appointment.id);
        System.out.println("Paciente: " + PATIENT_NAME);
        System.out.println("Médico: " + DOCTOR_NAME);
        System.out.println("Data: " + date.format(DATE_FORMAT));
        System.out.println("Horário: " + time.format(TIME_FORMAT));
        System.out.println("Status: " + appointment.status);
        System.out.println();
        System.out.println("Consulta agendada com sucesso.");
    }

    private void showError() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("      AGENDAMENTO NÃO REALIZADO");
        System.out.println("========================================");
        System.out.println();
        System.out.println("Não foi possível realizar o agendamento.");
        System.out.println("Verifique os dados e tente novamente.");
    }

    private void showInvalidOption() {
        System.out.println();
        System.out.println("Opção inválida. Escolha uma opção do menu.");
    }
}