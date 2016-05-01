package org.hope6537.test;

/**
 * Created by Hope6537 on 2015/3/11.
 */
public class DataModel {

    private String title;
    private String date;
    private String abs;
    private String applicant;
    private String inventor;
    private String expert_name;
    private String expert_org;

    public DataModel() {
    }

    public DataModel(String title, String date, String abs, String applicant, String inventor, String expert_name, String expert_org) {
        this.title = title;
        this.date = date;
        this.abs = abs;
        this.applicant = applicant;
        this.inventor = inventor;
        this.expert_name = expert_name;
        this.expert_org = expert_org;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAbs() {
        return abs;
    }

    public void setAbs(String abs) {
        this.abs = abs;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getInventor() {
        return inventor;
    }

    public void setInventor(String inventor) {
        this.inventor = inventor;
    }

    public String getExpert_name() {
        return expert_name;
    }

    public void setExpert_name(String expert_name) {
        this.expert_name = expert_name;
    }

    public String getExpert_org() {
        return expert_org;
    }

    public void setExpert_org(String expert_org) {
        this.expert_org = expert_org;
    }
}
