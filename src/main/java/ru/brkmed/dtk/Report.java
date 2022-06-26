package ru.brkmed.dtk;

public class Report {
    public int id;
    public String surname;
    public String adress;
    public String type;
    public  String idString;

    public Report() {
    }

    public Report(String surname, String adress, String type) {
        this.surname = surname;
        this.adress = adress;
        this.type = type;

    }

    public Report(int id, String surname, String adress, String type) {
        this.id = id;
        this.surname = surname;
        this.adress = adress;
        this.type = type;

    }



    @Override
    public String toString() {
        return "Report{" +
                ", surname='" + surname + '\'' +
                ", adress='" + adress + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
