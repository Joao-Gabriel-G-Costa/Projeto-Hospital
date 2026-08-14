package br.edu.mediconnect.patterns.strategy; import br.edu.mediconnect.model.Appointment;
public class TriageEngine { private PriorityStrategy strategy; public void setStrategy(PriorityStrategy s){strategy=s;} public int calculate(Appointment a){ if("EMERGENCY".equals(a.type)) return 100; if("RETURN".equals(a.type)) return 20; return strategy==null?10:strategy.score(a); } }
