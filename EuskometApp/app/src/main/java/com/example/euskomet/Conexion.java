package com.example.euskomet;

import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conexion implements Runnable {

    int cod;
    int tam;
    byte[] leido;

    public Conexion(int cod, int tam, byte[] leido) {
        this.cod = cod;
        this.tam = tam;
        this.leido = leido;
    }

    @Override
    public void run() {
        Connection con = null;
        String sIP;
        String sPuerto;
        String sBBDD;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            //Aqui pondriamos la IP y puerto.
            //sIP = "192.168.106.12";  //Asier Klase
            sIP = "localhost";
            //sIP= "192.168.0.11"; // Asier casa
            //sIP = "192.168.0.13";  //Aitor Casa
            sPuerto = "3306";
            sBBDD = "euskomet"; //nombre de la base de datos
            String url = "jdbc:mysql://" + sIP + ":" + sPuerto + "/" + sBBDD + "?serverTimezone=UTC";
//            con = DriverManager.getConnection( url, "root", "");
            // con = DriverManager.getConnection("jdbc:mysql://" + sIP + ":" + sPuerto + "/" + sBBDD, "usuario", "1234");
            Log.i("mysql ", "jdbc:mysql://" + sIP + ":" + sPuerto + "/" + sBBDD + "usuario" + "1234");
            con = DriverManager.getConnection("jdbc:mysql://" + sIP + ":" + sPuerto + "/" + sBBDD, "root", "");

            String sql = "INSERT INTO fotos_municipios (cod_foto, cod_mun, tam, archivo) VALUES(cod_mun, " + cod + ", " + tam + ", " + leido + ")";
            Log.i("conexion", con.toString());
            PreparedStatement st = con.prepareStatement(sql);
            st.execute();

            con.commit();
            con.close();
        } catch (IllegalAccessException illegalAccessException) {
            illegalAccessException.printStackTrace();
        } catch (InstantiationException instantiationException) {
            instantiationException.printStackTrace();
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
