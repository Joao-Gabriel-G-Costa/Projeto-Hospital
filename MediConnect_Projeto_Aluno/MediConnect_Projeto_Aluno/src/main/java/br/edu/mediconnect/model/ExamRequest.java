package br.edu.mediconnect.model;
public class ExamRequest { public String id; public String patientId; public String examCode; public String status="REQUESTED"; public boolean authorized; public ExamRequest(String id,String p,String code){this.id=id;this.patientId=p;this.examCode=code;} }
