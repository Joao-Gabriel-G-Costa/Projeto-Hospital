package br.edu.mediconnect.patterns.abstractfactory; import br.edu.mediconnect.legacy.*;
public class PartnerFamilyFactory { public Object createAuthorization(String family){return new LegacyHealthPlanApi();} public Object createLab(String family){return new LabXClient();} public Object createNotifier(String family){return new WhatsappHospitalApi();} }
