package com.example.euskomet;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.euskomet.Ventanas.Mostrar_Informacion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClienteThread implements Runnable {
    private String sResultado;
    public ClienteThread() {}

    private ArrayList<Provincias> provArrayList = new ArrayList<Provincias>();

    @Override
    public void run() {
        ResultSet rs = null;
        PreparedStatement st = null;
        Connection con = null;
        String sIP;
        String sPuerto;
        String sBBDD;
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            //Aqui pondriamos la IP y puerto.
            //sIP = "192.168.106.12";  //Asier Klase
            //sIP = "localhost";
            sIP= "192.168.0.11"; // Asier casa
            //sIP = "192.168.0.13";  //Aitor Casa
            sPuerto = "3306";
            sBBDD = "euskomet"; //nombre de la base de datos
            String url = "jdbc:mysql://" + sIP + ":" + sPuerto + "/" + sBBDD + "?serverTimezone=UTC";
//            con = DriverManager.getConnection( url, "root", "");
           // con = DriverManager.getConnection("jdbc:mysql://" + sIP + ":" + sPuerto + "/" + sBBDD, "usuario", "1234");
            Log.i("mysql ", "jdbc:mysql://" + sIP + ":" + sPuerto + "/" + sBBDD+ "usuario"+"1234");
            con = DriverManager.getConnection("jdbc:mysql://" + sIP + ":" + sPuerto + "/" + sBBDD, "usuario", "1234");




            // Consulta sencilla en este caso.
            String sql = "SELECT * FROM provincias";
//            String sql = "SELECT * FROM usuarios";
            st = con.prepareStatement(sql);
            rs = st.executeQuery();

            //--
            while (rs.next()) {

                Integer cod = rs.getInt("cod_prov");
                String nombre =  rs.getString("nombre");

                Provincias prov = new Provincias(cod, nombre);
                provArrayList.add(prov);
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

    public ArrayList<Provincias> getprovArrayList() {
        return provArrayList;
    }

    public static class ListadoDatos_Municipios extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

        private ConnectivityManager connectivityManager = null;

        private MunicipioAdapter oListaAdapter = null;
        public RecyclerView oRecyclerView;

        //    public ArrayList<Provincias> provArrayList = new ArrayList<Provincias>();
        ArrayList<String> nombreProvincias = new ArrayList<String>();
        ArrayList<Municipio> arrayDatosMunicipio = new ArrayList<Municipio>();
        ArrayList<Municipio> arrayDatosMunicipio_Bizkaia = new ArrayList<Municipio>();
        ArrayList<Municipio> arrayDatosMunicipio_Gipuzkoa = new ArrayList<Municipio>();
        ArrayList<Municipio> arrayDatosMunicipio_Araba = new ArrayList<Municipio>();

        private Spinner spinner;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_listado_datos_municipios);
    //        provArrayList = new ArrayList<Provincias>();
            oRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

            spinner = (Spinner) findViewById(R.id.spinner);
            spinner.setOnItemSelectedListener(this);

            conectarOnClick();


            try {
                ArrayList<Municipio> arrayMunicipio = new ArrayList<Municipio>();
                arrayMunicipio = conectarMunicipios();
                for (int i = 0; i < arrayMunicipio.size(); i++) {
                    arrayDatosMunicipio.add(arrayMunicipio.get(i));
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            oRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

            LinearLayoutManager llm = new LinearLayoutManager(this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            oRecyclerView.setLayoutManager(llm);

            for (int i = 0; i < arrayDatosMunicipio.size(); i++) {
                if (arrayDatosMunicipio.get(i).getCod_prov() == 48) { //Bizkaia
                    arrayDatosMunicipio_Bizkaia.add(arrayDatosMunicipio.get(i));
                }
                if (arrayDatosMunicipio.get(i).getCod_prov() == 20) { //Giputxilandia
                    arrayDatosMunicipio_Gipuzkoa.add(arrayDatosMunicipio.get(i));
                }
                if (arrayDatosMunicipio.get(i).getCod_prov() == 1) { //Araba
                    arrayDatosMunicipio_Araba.add(arrayDatosMunicipio.get(i));
                }
            }

        }

        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
            String selec = spinner.getSelectedItem().toString();
            if (selec.equals("Bizkaia")) {
                oListaAdapter = new MunicipioAdapter(arrayDatosMunicipio_Bizkaia, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Municipio item) {
                        Mostrar_informacion(item);
                        Log.i("tag", "   ----------------------------------------------------     Bizkaia : ");
                    }
                });
            } else if (selec.equals("Gipuzkoa")) {
                oListaAdapter = new MunicipioAdapter(arrayDatosMunicipio_Gipuzkoa, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Municipio item) {
                        Mostrar_informacion(item);
                        Log.i("tag", "   ----------------------------------------------------     Gipuzkoa : ");
                    }
                });
            } else if (selec.equals("Araba")) {
                oListaAdapter = new MunicipioAdapter(arrayDatosMunicipio_Araba, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Municipio item) {
                        Mostrar_informacion(item);
                        Log.i("tag", "   ----------------------------------------------------     Araba : ");
                    }
                });
            }
            oRecyclerView.setAdapter(oListaAdapter);
        }

        public void onNothingSelected(AdapterView<?> parent) {

        }

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String text = parent.getItemAtPosition(position).toString();
        }

        //  -------------------------------------------------------------------------------------------- CONEXIÓN BASE DE DATOS

        public void conectarOnClick() {

            try {
                if (isConnected()) {
                    ArrayList<Provincias> arrayProvincias = new ArrayList<Provincias>();
                    arrayProvincias = conectarProvincias();

    //                ArrayList<String> nombreProvincias = new ArrayList<String>();
    //                String nombreProvincias[] = new String[arrayProvincias.size()];
                    for (int i = 0; i < arrayProvincias.size(); i++) {
                        nombreProvincias.add(arrayProvincias.get(i).getNombre());
    //                    nombreProvincias[i] = arrayProvincias.get(i).getNombre();
                    }




                    if (null == arrayProvincias) { // Si la respuesta es null, una excepción ha ocurrido.
                        Toast.makeText(getApplicationContext(), "ERROR_COMUNICACION", Toast.LENGTH_SHORT).show();
                    } else {

                        //EJECUTAR SPINNER + RECYCLER VIEW
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nombreProvincias);
                        spinner.setAdapter(adapter);

                    }
                } else {
                    Toast.makeText(getApplicationContext(), "ERROR_NO_INTERNET", Toast.LENGTH_SHORT).show();
                }
            } catch (InterruptedException e) {
                // This cannot happen!
                Toast.makeText(getApplicationContext(), "ERROR_GENERAL", Toast.LENGTH_SHORT).show();
            }

        }

        private ArrayList<Provincias> conectarProvincias() throws InterruptedException {
            ClienteThread clienteThread = new ClienteThread();
            Thread thread = new Thread(clienteThread);
            thread.start();
            thread.join(); // Esperar respuesta del servidor...
            return clienteThread.getprovArrayList();
        }

        private ArrayList<Municipio> conectarMunicipios() throws InterruptedException {
            ClienteThreadMunicipios clienteThread = new ClienteThreadMunicipios();
            Thread thread = new Thread(clienteThread);
            thread.start();
            thread.join(); // Esperar respuesta del servidor...
            return clienteThread.getMunArrayList();
        }

        public boolean isConnected() {
            boolean ret = false;
            try {
                connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if ((networkInfo != null) && (networkInfo.isAvailable()) &&
                        (networkInfo.isConnected()))
                    ret = true;
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Error_comunicación", Toast.LENGTH_SHORT).show();
            }
            return ret;
        }

        public void Mostrar_informacion(Municipio item) {
            Intent Mostrar_informacion = new Intent(this, Mostrar_Informacion.class);
            Mostrar_informacion.putExtra("Cod_mun", item.getCod_mun());
            Mostrar_informacion.putExtra("desc",item.getDesc());
            Mostrar_informacion.putExtra("nombre",item.getNombre());
            Mostrar_informacion.putExtra("Cod_prov",item.getCod_prov());

            startActivity(Mostrar_informacion);
        }
    }
}
