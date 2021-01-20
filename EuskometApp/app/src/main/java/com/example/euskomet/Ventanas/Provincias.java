package com.example.euskomet.Ventanas;

public class Provincias {

    private int cod_prov;
    private String nombre;


    public Provincias(int cod_prov, String nombre) {
        this.cod_prov = cod_prov;
        this.nombre = nombre;
    }

    public Provincias() {
    }

    public int getCod_prov() {
        return cod_prov;
    }

    public void setCod_prov(int cod_prov) {
        this.cod_prov = cod_prov;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
