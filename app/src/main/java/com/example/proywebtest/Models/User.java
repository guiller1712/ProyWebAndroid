package com.example.proywebtest.Models;

public class User {
    String modemid;
    String nombreuser;
    String telefonouser;
    String contra;

    public User() {
        this.modemid = "";
        this.nombreuser = "";
        this.telefonouser = "";
        this.contra = "";
    }

    public User(String modemid, String nombreuser, String telefonouser, String contra) {
        this.modemid = modemid;
        this.nombreuser = nombreuser;
        this.telefonouser = telefonouser;
        this.contra = contra;
    }

    public String getModemid() {
        return modemid;
    }

    public void setModemid(String modemid) {
        this.modemid = modemid;
    }

    public String getNombreuser() {
        return nombreuser;
    }

    public void setNombreuser(String nombreuser) {
        this.nombreuser = nombreuser;
    }

    public String getTelefonouser() {
        return telefonouser;
    }

    public void setTelefonouser(String telefonouser) {
        this.telefonouser = telefonouser;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }
}
