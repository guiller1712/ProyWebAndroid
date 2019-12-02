package com.example.proywebtest.Models;

import com.google.gson.annotations.SerializedName;

public class Exceso {
    @SerializedName("folio")
    private String folio;

    public Exceso(String folio) {
        this.folio = folio;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }
}
