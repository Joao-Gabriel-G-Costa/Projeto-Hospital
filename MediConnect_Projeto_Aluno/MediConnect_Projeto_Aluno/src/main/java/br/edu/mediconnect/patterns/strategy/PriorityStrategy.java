package br.edu.mediconnect.patterns.strategy; import br.edu.mediconnect.model.Appointment; public interface PriorityStrategy { int score(Appointment a); }
