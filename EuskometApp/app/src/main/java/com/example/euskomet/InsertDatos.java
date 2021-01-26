package com.example.euskomet;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class InsertDatos implements Runnable {
    private String sResultado;
    private static String sql;

    public InsertDatos(String sql) {
        this.sql= sql;

    }
    private boolean bol;


    @Override
    public void run() {
        bol = false;
        ResultSet rs = null;
        PreparedStatement st = null;
        Connection con = null;
        String sIP;
        String sPuerto;
        String sBBDD;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            //sIP = "192.168.106.12";  //Asier Klase
            //sIP = "localhost";
            sIP= "192.168.0.11"; // Asier casa
            //sIP = "192.168.0.13";  //Aitor Casa
            sPuerto = "3306";
            sBBDD = "euskomet"; //nombre de la base de datos
            con = DriverManager.getConnection("jdbc:mysql://" + sIP + ":" + sPuerto + "/" + sBBDD, "usuario", "1234");

            st = con.prepareStatement(sql);
            Log.i("SENTENCIA INSERT", "[SENTECIA INSERT] --------------------------------->"+sql);
            st.execute();

            bol = true;

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

    public boolean isBol() {
        return bol;
    }

    public void setBol(boolean bol) {
        this.bol = bol;
    }
}
