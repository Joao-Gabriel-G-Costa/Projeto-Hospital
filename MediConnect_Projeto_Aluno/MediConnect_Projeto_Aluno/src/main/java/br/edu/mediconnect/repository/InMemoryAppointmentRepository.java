package br.edu.mediconnect.repository; import br.edu.mediconnect.model.Appointment; import java.util.*;
public class InMemoryAppointmentRepository { private final Map<String,Appointment> data=new LinkedHashMap<>(); public void save(Appointment a){data.put(a.id,a);} public Appointment find(String id){return data.get(id);} public Collection<Appointment> all(){return data.values();} }
