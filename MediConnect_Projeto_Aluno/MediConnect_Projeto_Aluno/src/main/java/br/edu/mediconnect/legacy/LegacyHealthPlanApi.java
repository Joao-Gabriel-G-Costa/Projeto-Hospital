package br.edu.mediconnect.legacy;
public class LegacyHealthPlanApi { public String authorizeProcedure(String member,String procedure,double amount){return member+";"+procedure+";"+(amount<5000?"OK":"MANUAL");} }
