package com.example.euskomet;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClienteThreadEspaciosNaturales implements Runnable {
    private String sResultado;
    public ClienteThreadEspaciosNaturales() {}

    private ArrayList<EspacioNatural> espNatArrayList = new ArrayList<EspacioNatural>();

    @Override
    public void run() {
        ResultSet rs = null;
        PreparedStatement st = null;
        Connection con = null;
        String sIP;
        String sPuerto;
        String sBBDD;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            //Aqui pondriamos la IP y puerto.
            sIP = "192.168.106.26";
//            sIP = "127.0.0.1";
            sPuerto = "3306";
            sBBDD = "euskomet"; //nombre de la base de datos
            String url = "jdbc:mysql://" + sIP + ":" + sPuerto + "/" + sBBDD + "?serverTimezone=UTC";
//            con = DriverManager.getConnection( url, "root", "");
            con = DriverManager.getConnection("jdbc:mysql://" + sIP + ":" + sPuerto + "/" + sBBDD, "usuario", "1234");


            // Consulta sencilla en este caso.
            String sql = "SELECT * FROM espacios_naturales";
//            String sql = "SELECT * FROM usuarios";
            st = con.prepareStatement(sql);
            rs = st.executeQuery();

            //--
            while (rs.next()) {

                Integer cod = rs.getInt("cod_esp_natural");
                String nombre =  rs.getString("nombre");
                String tipo = rs.getString("tipo");
                Integer cod_mun = rs.getInt("cod_mun");

                EspacioNatural espNat = new EspacioNatural(cod, nombre, tipo, cod_mun);
                espNatArrayList.add(espNat);
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
                if(rs!=null) {
                    rs.close();
                }
                // Cerrar PreparedStatement
                if(st!=null) {
                    st.close();
                }
                // Cerrar Connection
                if(con!=null) {
                    con.close();
                }
            } catch (Exception e) {
                Log.e("Exception_cerrando todo", "");
                e.printStackTrace();
            }
        }
    }

    public ArrayList<EspacioNatural> getEspNatArrayList() {
        return espNatArrayList;
    }
}