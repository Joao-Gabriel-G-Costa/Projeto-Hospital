package br.edu.mediconnect.model;
public class Appointment { public String id; public String patientId; public String doctorId; public String dateTime; public String type; public String status="CREATED"; public String priority="NORMAL"; public Appointment(String id,String p,String d,String dt,String type){this.id=id;this.patientId=p;this.doctorId=d;this.dateTime=dt;this.type=type;} }
