package com.example.proywebtest.Models;

public class Reservacion {
    private String idCamion;
    private String folioReservacion;
    private String sedeOrigen;
    private String sedeDestino;
    private String fechaReservacion;
    private String nombreCliente;
    private String primerApellidoCliente;
    private String segundoApellidoCliente;
    private String celularCliente;

    public Reservacion() {
        this.idCamion = "";
        this.folioReservacion = "";
        this.sedeOrigen = "";
        this.sedeDestino = "";
        this.fechaReservacion = "";
        this.nombreCliente = "";
        this.primerApellidoCliente = "";
        this.segundoApellidoCliente = "";
        this.celularCliente = "";
    }

    public Reservacion(String idCamion, String folioReservacion, String sedeOrigen, String sedeDestino, String fechaReservacion, String nombreCliente, String primerApellidoCliente, String segundoApellidoCliente, String celularCliente) {
        this.idCamion = idCamion;
        this.folioReservacion = folioReservacion;
        this.sedeOrigen = sedeOrigen;
        this.sedeDestino = sedeDestino;
        this.fechaReservacion = fechaReservacion;
        this.nombreCliente = nombreCliente;
        this.primerApellidoCliente = primerApellidoCliente;
        this.segundoApellidoCliente = segundoApellidoCliente;
        this.celularCliente = celularCliente;
    }

    public String getIdCamion() {
        return idCamion;
    }

    public void setIdCamion(String idCamion) {
        this.idCamion = idCamion;
    }

    public String getFolioReservacion() {
        return folioReservacion;
    }

    public void setFolioReservacion(String folioReservacion) {
        this.folioReservacion = folioReservacion;
    }

    public String getSedeOrigen() {
        return sedeOrigen;
    }

    public void setSedeOrigen(String sedeOrigen) {
        this.sedeOrigen = sedeOrigen;
    }

    public String getSedeDestino() {
        return sedeDestino;
    }

    public void setSedeDestino(String sedeDestino) {
        this.sedeDestino = sedeDestino;
    }

    public String getFechaReservacion() {
        return fechaReservacion;
    }

    public void setFechaReservacion(String fechaReservacion) {
        this.fechaReservacion = fechaReservacion;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getPrimerApellidoCliente() {
        return primerApellidoCliente;
    }

    public void setPrimerApellidoCliente(String primerApellidoCliente) {
        this.primerApellidoCliente = primerApellidoCliente;
    }

    public String getSegundoApellidoCliente() {
        return segundoApellidoCliente;
    }

    public void setSegundoApellidoCliente(String segundoApellidoCliente) {
        this.segundoApellidoCliente = segundoApellidoCliente;
    }

    public String getCelularCliente() {
        return celularCliente;
    }

    public void setCelularCliente(String celularCliente) {
        this.celularCliente = celularCliente;
    }
}
