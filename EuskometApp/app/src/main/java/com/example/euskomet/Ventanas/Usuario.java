package com.example.euskomet.Ventanas;

public class Usuario {
    private int cod_user;
    private String nombre;
    private String contra;
    private String pregunta;
    private String respuesta;

    public Usuario(int cod_user, String nombre, String contra, String pregunta, String respuesta) {
        this.cod_user = cod_user;
        this.nombre = nombre;
        this.contra = contra;
        this.pregunta = pregunta;
        this.respuesta = respuesta;
    }

    public Usuario() {

    }

    public int getCod_user() {
        return cod_user;
    }

    public void setCod_user(int cod_user) {
        this.cod_user = cod_user;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
}
