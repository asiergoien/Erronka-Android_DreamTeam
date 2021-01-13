package com.example.euskomet;

import java.util.ArrayList;
import java.util.Date;

public class Municipio {

    private int cod_mun;
    private String nombre;
    private int cod_prov;

    public Municipio(int cod_mun, String nombre, int cod_prov) {
        this.cod_mun = cod_mun;
        this.nombre = nombre;
        this.cod_prov = cod_prov;
    }

    public int getCod_mun() {
        return cod_mun;
    }

    public void setCod_mun(int cod_mun) {
        this.cod_mun = cod_mun;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCod_prov() {
        return cod_prov;
    }

    public void setCod_prov(int cod_prov) {
        this.cod_prov = cod_prov;
    }
}
