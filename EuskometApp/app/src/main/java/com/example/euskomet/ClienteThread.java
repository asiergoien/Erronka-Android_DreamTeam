package com.example.euskomet;
import android.util.Log;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClienteThread implements Runnable {
    private String sResultado;
    public ClienteThread() {}
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
            //sIP = "192.168.2.91";
            sIP = "127.0.0.1";
            sPuerto = "3306";
            sBBDD = "euskomet"; //nombre de la base de datos
            String url = "jdbc:mysql://" + sIP + ":" + sPuerto + "/" + sBBDD + "?serverTimezone=UTC";
            con = DriverManager.getConnection( url, "root", "");

            /*
            // Consulta sencilla en este caso.
            String sql = "SELECT * FROM usuarios";
            st = con.prepareStatement(sql);
            rs = st.executeQuery();
            //--
            while (rs.next()) {
                String var1 = rs.getString("Nombre");
                Log.i("XXXXXXX", var1);
                sResultado = var1;
            }

            */

            Statement estado = con.createStatement();
            System.out.println("Conexion establecida");
            String peticion ="select contraseña from usuarios where nombre='Markel'";
            ResultSet result = estado.executeQuery(peticion);

            sResultado = result.toString();

            Log.i("tag", " ---------------------------------------- " + sResultado);

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
    public String getResponse() {
        return sResultado;
    }
}
