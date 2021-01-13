package com.example.euskomet;

public class EspacioNatural {

    private int cod_esp_natural;
    private String nombre;
    private String tipo;
    private int cod_mun;

    // Este constructor es solo para poder llenar la lista con datos.
    public EspacioNatural(int cod_esp_natural, String nombre, String tipo, int cod_mun) {
        this.cod_esp_natural = cod_esp_natural;
        this.nombre = nombre;
        this.tipo = tipo;
        this.cod_mun = cod_mun;
    }

    public int getCod_esp_natural() {
        return cod_esp_natural;
    }

    public void setCod_esp_natural(int cod_esp_natural) {
        this.cod_esp_natural = cod_esp_natural;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCod_mun() {
        return cod_mun;
    }

    public void setCod_mun(int cod_mun) {
        this.cod_mun = cod_mun;
    }
}
