package br.edu.mediconnect.patterns.adapter; import br.edu.mediconnect.legacy.LabXClient;
public class LabAdapter { private final LabXClient client=new LabXClient(); public boolean request(String patient,String exam){return client.sendExam(patient,exam)==200;} public LabXClient rawClient(){return client;} }
