package com.example.euskomet;
import android.annotation.SuppressLint;
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
            CargarDatos clienteThread = new CargarDatos("SELECT DISTINCT tipo FROM espacios_naturales", 3);
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

            CargarDatos clienteThread = new CargarDatos("SELECT * FROM espacios_naturales", 4);
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

        @SuppressLint("LongLogTag")
        public void Mostrar_informacion(EspacioNatural item) {
            Intent Mostrar_informacion = new Intent(this, Mostrar_Informacion.class);
            Mostrar_informacion.putExtra("cod", item.getCod_esp_natural());
            Mostrar_informacion.putExtra("desc",item.getDesc());
            Mostrar_informacion.putExtra("nombre",item.getNombre());
            Mostrar_informacion.putExtra("tipo",item.getTipo());

            //Fallo al pasar el cod_user

            //Para los mapas
            Mostrar_informacion.putExtra("lo",item.getLongitud());
            Mostrar_informacion.putExtra("la",item.getLatitud());


            int codusu = getIntent().getIntExtra("cod_usuario", -1);

            Log.i("MOSTRARINFORMACION ESPACIO NATURAL", "---------------------------------------MOSTRARINFORMACION ESPACIO NATURAL---------------------------------------------[ ");
            Log.i("[INTENT MOSTRARINFORMACION ESPACIO NATURAL]--Cod_usuario", "--------------------[CODIGO_USUARIO]-----["+codusu);
            Log.i("[INTENT MOSTRARINFORMACION ESPACIO NATURAL]--cod_esp_natural", "--------------------[CODIGO_ESP_NATURAL]-----["+item.getCod_esp_natural());
            Log.i("[INTENT MOSTRARINFORMACION ESPACIO NATURAL]--nombre_espacio_natural", "--------------------[NOMBRE_ESPACIO_NATURAL]-----["+item.getNombre());
            Log.i("[INTENT MOSTRARINFORMACION ESPACIO NATURAL]--tipo", "--------------------[TIPO_DE_ESPACIO_NATURAL]-----["+item.getTipo());


            Mostrar_informacion.putExtra("cod_usuario", codusu);
            Mostrar_informacion.putExtra("fav", "favoritos_esp");

            startActivity(Mostrar_informacion);
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String text = parent.getItemAtPosition(position).toString();
        }
    }

