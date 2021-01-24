package com.example.euskomet;

public class Municipio {

    private int cod_mun;
    private String nombre;
    private int cod_prov;
    private String desc;

    public Municipio(int cod_mun, String nombre, int cod_prov,String desc ) {
        this.cod_mun = cod_mun;
        this.nombre = nombre;
        this.cod_prov = cod_prov;
        this.desc   = desc;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setCod_prov(int cod_prov) {
        this.cod_prov = cod_prov;
    }
}
