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

//public class ClienteThreadTipo implements Runnable {
//    private String sResultado;
//    public ClienteThreadTipo() {}
//
//    private ArrayList<String> tipoArrayList = new ArrayList<String>();
//
//    @Override
//    public void run() {
//        ResultSet rs = null;
//        PreparedStatement st = null;
//        Connection con = null;
//        String sIP;
//        String sPuerto;
//        String sBBDD;
//        try{
//            Class.forName("com.mysql.jdbc.Driver").newInstance();
//            //Aqui pondriamos la IP y puerto.
//            sIP = "192.168.106.12";  //Asier Klase
//            //sIP = "localhost";
//            //sIP= "192.168.0.11"; // Asier casa
//            //sIP = "192.168.0.13";  //Aitor Casa
//            sPuerto = "3306";
//            sBBDD = "euskomet"; //nombre de la base de datos
//            String url = "jdbc:mysql://" + sIP + ":" + sPuerto + "/" + sBBDD + "?serverTimezone=UTC";
////            con = DriverManager.getConnection( url, "root", "");
//           // con = DriverManager.getConnection("jdbc:mysql://" + sIP + ":" + sPuerto + "/" + sBBDD, "usuario", "1234");
//            Log.i("mysql ", "jdbc:mysql://" + sIP + ":" + sPuerto + "/" + sBBDD+ "usuario"+"1234");
//            con = DriverManager.getConnection("jdbc:mysql://" + sIP + ":" + sPuerto + "/" + sBBDD, "usuario", "1234");
//
//
//
//
//            // Consulta sencilla en este caso.
//            String sql = "SELECT DISTINCT tipo FROM espacios_naturales";
////            String sql = "SELECT * FROM usuarios";
//            st = con.prepareStatement(sql);
//            rs = st.executeQuery();
//
//            //--
//            while (rs.next()) {
//
//                tipoArrayList.add(rs.getString("tipo"));
//
//            }
//
//        } catch (ClassNotFoundException e) {
//            Log.e("ClassNotFoundException", "");
//            e.printStackTrace();
//        } catch (SQLException e) {
//            Log.e("SQLException", "");
//            e.printStackTrace();
//        } catch (Exception e) {
//            Log.e("Exception", "");
//            e.printStackTrace();
//        } finally {
//            // Intentamos cerrar _todo.
//            try {
//                // Cerrar ResultSet
//                if(rs!=null) {
//                    rs.close();
//                }
//                // Cerrar PreparedStatement
//                if(st!=null) {
//                    st.close();
//                }
//                // Cerrar Connection
//                if(con!=null) {
//                    con.close();
//                }
//            } catch (Exception e) {
//                Log.e("Exception_cerrando todo", "");
//                e.printStackTrace();
//            }
//        }
//    }

//    public ArrayList<String> gettipoArrayList() {
//        return tipoArrayList;
//    }

    public  class Listado_Espacios_Naturales extends AppCompatActivity implements AdapterView.OnItemSelectedListener , AdapterView.OnItemClickListener {

        private ConnectivityManager connectivityManager = null;

        private EspNaturalesAdapter oListaAdapter = null;
        public RecyclerView oRecyclerView;

        //    public ArrayList<Provincias> provArrayList = new ArrayList<Provincias>();
        ArrayList<String> nombreProvincias = new ArrayList<String>();
        ArrayList<EspacioNatural> arrayDatosEspacioNatural = new ArrayList<EspacioNatural>();
        ArrayList<EspacioNatural> arrayDatosEspacioNatural_Playa = new ArrayList<EspacioNatural>();
        ArrayList<EspacioNatural> arrayDatosEspacioNatural_Pantano = new ArrayList<EspacioNatural>();
        ArrayList<EspacioNatural> arrayDatosEspacioNatural_Rio = new ArrayList<EspacioNatural>();

        private Spinner spinner_;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_listado_datos_esp_naturales);
    //        provArrayList = new ArrayList<Provincias>();
            oRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_esp_nat);

            spinner_ = (Spinner) findViewById(R.id.spinner_esp_nat);
            spinner_.setOnItemSelectedListener(this);

            conectarOnClick();



            try {
                ArrayList<EspacioNatural> arrayMunicipio = new ArrayList<EspacioNatural>();
                arrayMunicipio = conectarEsp();
                for (int i = 0; i < arrayMunicipio.size(); i++) {
                    arrayDatosEspacioNatural.add(arrayMunicipio.get(i));
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            oRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_esp_nat);

            LinearLayoutManager llm = new LinearLayoutManager(this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            oRecyclerView.setLayoutManager(llm);

            Log.i("ArrayEspNat", arrayDatosEspacioNatural.size() + "");

            for (int i = 0; i < arrayDatosEspacioNatural.size(); i++) {
                if (arrayDatosEspacioNatural.get(i).getTipo().equals("Playas")) { //Playas
                    arrayDatosEspacioNatural_Playa.add(arrayDatosEspacioNatural.get(i));
                }
                if (arrayDatosEspacioNatural.get(i).getTipo().equals("Pantanos")) { //Pantanos
                    arrayDatosEspacioNatural_Pantano.add(arrayDatosEspacioNatural.get(i));
                }
                if (arrayDatosEspacioNatural.get(i).getTipo().equals("Rios")) { //Rios
                    arrayDatosEspacioNatural_Rio.add(arrayDatosEspacioNatural.get(i));
                }
            }

            Log.i("ArrayEspNatPlaya", arrayDatosEspacioNatural_Playa.size() + "");
            Log.i("ArrayEspNatPantano", arrayDatosEspacioNatural_Pantano.size() + "");
            Log.i("ArrayEspNatRio", arrayDatosEspacioNatural_Rio.size() + "");

        }


        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selec = spinner_.getSelectedItem().toString();
            if (selec.equals("Playas")) {
                Log.i("if playas", "entra");
                oListaAdapter = new EspNaturalesAdapter(arrayDatosEspacioNatural_Playa, new OnItemClickListenerEspNat() {
                    @Override
                    public void onItemClick(EspacioNatural item) {
                        Mostrar_informacion(item);
                        Log.i("tag", "   ----------------------------------------------------     Bizkaia : ");
                    }
                });
            } else if (selec.equals("Pantanos")) {
                oListaAdapter = new EspNaturalesAdapter(arrayDatosEspacioNatural_Pantano, new OnItemClickListenerEspNat() {
                    @Override
                    public void onItemClick(EspacioNatural item) {
                        Mostrar_informacion(item);
                        Log.i("tag", "   ----------------------------------------------------     Gipuzkoa : ");
                    }
                });
            } else if (selec.equals("Rios")) {
                oListaAdapter = new EspNaturalesAdapter(arrayDatosEspacioNatural_Rio, new OnItemClickListenerEspNat() {
                    @Override
                    public void onItemClick(EspacioNatural item) {
                        Mostrar_informacion(item);
                        Log.i("tag", "   ----------------------------------------------------     Araba : ");
                    }
                });
            }
            oRecyclerView.setAdapter(oListaAdapter);
        }

        public void onNothingSelected(AdapterView<?> parent) {

        }


        //  -------------------------------------------------------------------------------------------- CONEXIÓN BASE DE DATOS

        public void conectarOnClick() {

            try {
                if (isConnected()) {
                    ArrayList<String> arrayProvincias = new ArrayList<String>();
                    arrayProvincias = conectarTipo();

    //                ArrayList<String> nombreProvincias = new ArrayList<String>();
    //                String nombreProvincias[] = new String[arrayProvincias.size()];
                    for (int i = 0; i < arrayProvincias.size(); i++) {
                        nombreProvincias.add(arrayProvincias.get(i));
    //                    nombreProvincias[i] = arrayProvincias.get(i).getNombre();
                    }




                    if (null == arrayProvincias) { // Si la respuesta es null, una excepción ha ocurrido.
                        Toast.makeText(getApplicationContext(), "ERROR_COMUNICACION", Toast.LENGTH_SHORT).show();
                    } else {

                        //EJECUTAR SPINNER + RECYCLER VIEW
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nombreProvincias);
                        spinner_.setAdapter(adapter);
                        Log.i("entra", "conectarOnClick: -------- entra");

                    }
                } else {
                    Toast.makeText(getApplicationContext(), "ERROR_NO_INTERNET", Toast.LENGTH_SHORT).show();
                }
            } catch (InterruptedException e) {
                // This cannot happen!
                Toast.makeText(getApplicationContext(), "ERROR_GENERAL", Toast.LENGTH_SHORT).show();
            }

        }

        //----------------------------------------------------------- CONECTAR TIPO
        private ArrayList<String> conectarTipo() throws InterruptedException {
            ClienteThread clienteThread = new ClienteThread("SELECT DISTINCT tipo FROM espacios_naturales", 3);
            Thread thread = new Thread(clienteThread);
            thread.start();
            thread.join(); // Esperar respuesta del servidor...

            ArrayList<String> ArrayMun= new ArrayList<String>();
            ArrayList<Object> viejo = new ArrayList<Object>();

            viejo= clienteThread.getCliemteThread_ArrayList();
            for (Object ob : viejo){
                ArrayMun.add((String) ob);
            }
            return ArrayMun;
        }
        //----------------------------------------------------------- CONECTAR ESPACIOS NATURALES
        private ArrayList<EspacioNatural> conectarEsp() throws InterruptedException {

            ClienteThread clienteThread = new ClienteThread("SELECT * FROM espacios_naturales", 4);
            Thread thread = new Thread(clienteThread);
            thread.start();
            thread.join(); // Esperar respuesta del servidor...

            ArrayList<EspacioNatural> ArrayMun= new ArrayList<EspacioNatural>();
            ArrayList<Object> viejo = new ArrayList<Object>();

            viejo= clienteThread.getCliemteThread_ArrayList();
            for (Object ob : viejo){
                ArrayMun.add((EspacioNatural) ob);
            }
            return ArrayMun;
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

        public void Mostrar_informacion(EspacioNatural item) {
            Intent Mostrar_informacion = new Intent(this, Mostrar_Informacion.class);
            Mostrar_informacion.putExtra("Cod_esp", item.getCod_esp_natural());
            Mostrar_informacion.putExtra("desc",item.getDesc());
            Mostrar_informacion.putExtra("nombre",item.getNombre());
            Mostrar_informacion.putExtra("tipo",item.getTipo());

            startActivity(Mostrar_informacion);
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String text = parent.getItemAtPosition(position).toString();
        }
    }

