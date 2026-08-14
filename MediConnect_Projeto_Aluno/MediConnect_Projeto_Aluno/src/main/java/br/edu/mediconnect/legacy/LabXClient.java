package br.edu.mediconnect.legacy;
public class LabXClient { public int sendExam(String patient,String code){return patient!=null && code!=null?200:400;} public String result(String protocol){return "RESULT|"+protocol+"|NORMAL";} }
