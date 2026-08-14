package br.edu.mediconnect.patterns.adapter; import br.edu.mediconnect.legacy.LegacyHealthPlanApi;
public class HealthPlanAdapter extends LegacyHealthPlanApi { public boolean authorize(String member,String procedure,double amount){return authorizeProcedure(member,procedure,amount).endsWith("OK");} public String legacyAuthorize(String m,String p,double a){return authorizeProcedure(m,p,a);} }
