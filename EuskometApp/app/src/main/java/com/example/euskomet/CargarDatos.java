package com.example.euskomet;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CargarDatos implements Runnable {
    private String sResultado;
    private static String sql;
    private static  Integer tipo;

    public CargarDatos(String sql, int tipo ) {
        this.sql= sql;
        this.tipo = tipo;
    }

    private ArrayList<Object> ClienteThread_ArrayList = new ArrayList<Object>();


    @Override
    public void run() {
        ResultSet rs = null;
        PreparedStatement st = null;
        Connection con = null;
        String sIP;
        String sPuerto;
        String sBBDD;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            //Aqui pondriamos la IP y puerto.
            sIP = "192.168.106.12";  //Asier Klase
            //sIP = "localhost";
            //sIP= "192.168.0.11"; // Asier casa
            //sIP = "192.168.0.13";  //Aitor Casa
            sPuerto = "3306";
            sBBDD = "euskomet"; //nombre de la base de datos
            String url = "jdbc:mysql://" + sIP + ":" + sPuerto + "/" + sBBDD + "?serverTimezone=UTC";
//            con = DriverManager.getConnection( url, "root", "");
            // con = DriverManager.getConnection("jdbc:mysql://" + sIP + ":" + sPuerto + "/" + sBBDD, "usuario", "1234");
            Log.i("mysql ", "jdbc:mysql://" + sIP + ":" + sPuerto + "/" + sBBDD + "usuario" + "1234");
            con = DriverManager.getConnection("jdbc:mysql://" + sIP + ":" + sPuerto + "/" + sBBDD, "usuario", "1234");
            st = con.prepareStatement(sql);
            rs = st.executeQuery();

            //--
            while (rs.next()) {
                if(tipo==1) { //Prov
                    Log.i("TAG", "run: entra aqui en prov");
                    Integer cod = rs.getInt("cod_prov");
                    String nombre = rs.getString("nombre");
                    Provincias prov = new Provincias(cod, nombre);
                    ClienteThread_ArrayList.add(prov);
                }else if  (tipo == 2) { //Mun
                    Log.i("TAG", "run: entra aqui en mun");
                    Integer cod = rs.getInt("cod_mun");
                    String nombre =  rs.getString("nombre");
                    String desc =  rs.getString("descripcion");
                    Integer prov = rs.getInt("cod_prov");
                    Municipio mun = new Municipio(cod, nombre, prov,desc);
                    ClienteThread_ArrayList.add(mun);


                }else if (tipo==3){//Espacios naturales tipo
                    Log.i("TAG", "run: entra aqui en tipo");
                    String tipo =  rs.getString("tipo");
                    ClienteThread_ArrayList.add(tipo);


                }else if  (tipo == 4){//Todos Espacios naturales
                    Log.i("TAG", "run: entra aqui en espacios naturales");
                    Integer cod = rs.getInt("cod_esp_natural");
                    String nombre =  rs.getString("nombre");
                    String tipo = rs.getString("tipo");
                    String desc = rs.getString("descripcion");

                    EspacioNatural espNat = new EspacioNatural(cod, nombre, tipo, desc);
                    ClienteThread_ArrayList.add(espNat);

                }



            }

        } catch (ClassNotFoundException e) {
            Log.e("ClassNotFoundException", "");
            e.printStackTrace();
        } catch (SQLException e) {
            Log.e("SQLException", "");
            e.printStackTrace();
        } catch (Exception e) {
            Log.e("Exception", "");
            e.printStackTrace();
        } finally {
            // Intentamos cerrar _todo.
            try {
                // Cerrar ResultSet
                if (rs != null) {
                    rs.close();
                }
                // Cerrar PreparedStatement
                if (st != null) {
                    st.close();
                }
                // Cerrar Connection
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                Log.e("Exception_cerrando todo", "");
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Object> getCliemteThread_ArrayList() {
         ArrayList<Object> ArrayList = (ArrayList<Object>) ClienteThread_ArrayList.clone();
        ClienteThread_ArrayList.clear();
        return ArrayList;
    }
}
