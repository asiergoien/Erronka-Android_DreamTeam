package com.example.euskomet;

import java.util.Date;

public class Municipio {

    private String cod_mun;
    private String nombre;

    // Este constructor es solo para poder llenar la lista con datos.
    public Municipio(String cod_mun, String nombre) {
        this.cod_mun = cod_mun;
        this.nombre = nombre;
    }

    public String getCod_mun() {
        return cod_mun;
    }

    public void setCod_mun(String cod_mun) {
        this.cod_mun = cod_mun;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
