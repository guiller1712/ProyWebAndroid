package com.example.proywebtest.Models;

import com.google.gson.annotations.SerializedName;

public class Chofer {
    @SerializedName("token")
    private String token;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("primerApellido")
    private String primerApellido;

    @SerializedName("segundoApellido")
    private String segundoApellido;

    @SerializedName("telefono")
    private String telefono;

    @SerializedName("correoElectronico")
    private String correoElectronico;

    @SerializedName("rol")
    private String rol;

    @SerializedName("password")
    private String password;

    public Chofer(String correoElectronico, String password) {
        this.correoElectronico = correoElectronico;
        this.password = password;
    }

    public Chofer(String nombre, String primerApellido, String segundoApellido, String telefono, String correoElectronico, String rol) {
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
        this.rol = rol;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
