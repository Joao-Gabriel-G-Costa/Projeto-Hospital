package br.edu.mediconnect.patterns.factory; import br.edu.mediconnect.model.Appointment;
public class AppointmentFactory { public static Appointment create(String type,String id,String p,String d,String dt){ Appointment a=new Appointment(id,p,d,dt,type); if("EMERGENCY".equals(type)) a.priority="URGENT"; else if("TELEMEDICINE".equals(type)) a.priority="NORMAL"; return a; } }
