package br.edu.mediconnect.patterns.observer; public class AuditObserver implements HospitalObserver { public void update(String id,String event){System.out.println("AUDIT "+id+" "+event);} }
