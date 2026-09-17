package br.edu.mediconnect;

import br.edu.mediconnect.model.Patient;
import br.edu.mediconnect.patterns.facade.MediConnectFacade;
import br.edu.mediconnect.ui.AppointmentConsole;

public class Main {

    public static void main(String[] args) {
        MediConnectFacade app = new MediConnectFacade();

        app.registerPatient(
                new Patient(
                        "P1",
                        "Ana",
                        "PLAN-A",
                        "62999999999",
                        "ana@example.com"
                )
        );

        AppointmentConsole console = new AppointmentConsole(app);
        console.run();
    }
}