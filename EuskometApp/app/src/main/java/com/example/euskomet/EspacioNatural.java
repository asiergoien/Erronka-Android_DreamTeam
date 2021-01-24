package com.example.euskomet;

public class EspacioNatural {

    private int cod_esp_natural;
    private String nombre;
    private String tipo;
    private int cod_mun;
    private String desc;

    // Este constructor es solo para poder llenar la lista con datos.
    public EspacioNatural(int cod_esp_natural, String nombre, String tipo, String desc) {
        this.cod_esp_natural = cod_esp_natural;
        this.nombre = nombre;
        this.tipo = tipo;
        this.desc = desc;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
