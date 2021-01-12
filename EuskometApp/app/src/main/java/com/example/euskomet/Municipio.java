package com.example.euskomet;

import java.util.ArrayList;
import java.util.Date;

public class Municipio {

    private String cod_mun;
    private String nombre;
    static int kont;
    static int kont_nom=0;

   public String[] municipios ={"Abadiño","Abaltzisketa","Abanto-Zierbena","Aduna","Agurain","Aia","Abadiño","Aiara","Aizarnazabal"};



    // Este constructor es solo para poder llenar la lista con datos.
    public Municipio() {
        //Por ejemplo
        this.cod_mun = ""+kont++;
        this.nombre = municipios[kont_nom];
        kont_nom ++;
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
