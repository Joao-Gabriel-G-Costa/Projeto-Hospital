package br.edu.mediconnect.repository; import br.edu.mediconnect.model.Patient; import java.util.*;
public class InMemoryPatientRepository { private final Map<String,Patient> data=new HashMap<>(); public void save(Patient p){data.put(p.id,p);} public Patient find(String id){return data.get(id);} }
