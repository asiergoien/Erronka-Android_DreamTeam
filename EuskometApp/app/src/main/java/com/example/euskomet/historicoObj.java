package com.example.euskomet;

import java.util.Date;

public class historicoObj {

    private Date fecha;
    private String ICA_estacion;

    public historicoObj( Date fecha, String ICA_estacion) {
        this.fecha = fecha;
        this.ICA_estacion = ICA_estacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getICA_estacion() {
        return ICA_estacion;
    }

    public void setICA_estacion(String ICA_estacion) {
        this.ICA_estacion = ICA_estacion;
    }
}
