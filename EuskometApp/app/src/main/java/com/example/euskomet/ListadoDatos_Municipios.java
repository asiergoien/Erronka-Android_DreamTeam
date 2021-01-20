package com.example.euskomet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class ListadoDatos_Municipios extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

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
        oRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        conectarOnClick();



        try {
            ArrayList<Municipio> arrayMunicipio = new ArrayList<Municipio>();
            arrayMunicipio = conectarMunicipios();
            for (int i=0;i<arrayMunicipio.size();i++) {
                arrayDatosMunicipio.add(arrayMunicipio.get(i));
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        oRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        oRecyclerView.setLayoutManager(llm);

        for (int i=0;i<arrayDatosMunicipio.size();i++) {
            if (arrayDatosMunicipio.get(i).getCod_prov()==48) { //Bizkaia
                arrayDatosMunicipio_Bizkaia.add(arrayDatosMunicipio.get(i));
            }
            if (arrayDatosMunicipio.get(i).getCod_prov()==20) { //Giputxilandia
                arrayDatosMunicipio_Gipuzkoa.add(arrayDatosMunicipio.get(i));
            }
            if (arrayDatosMunicipio.get(i).getCod_prov()==1) { //Araba
                arrayDatosMunicipio_Araba.add(arrayDatosMunicipio.get(i));
            }
        }

    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        String selec=spinner.getSelectedItem().toString();
        if (selec.equals("Bizkaia")) {
            oListaAdapter = new MunicipioAdapter(arrayDatosMunicipio_Bizkaia,null);
        } else if (selec.equals("Gipuzkoa")) {
            oListaAdapter = new MunicipioAdapter(arrayDatosMunicipio_Gipuzkoa,null);
        } else if (selec.equals("Araba")) {
            oListaAdapter = new MunicipioAdapter(arrayDatosMunicipio_Araba,null);
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
                for(int i = 0; i < arrayProvincias.size(); i++){
                    nombreProvincias.add(arrayProvincias.get(i).getNombre());
//                    nombreProvincias[i] = arrayProvincias.get(i).getNombre();
                }

                Log.i("XXXXXXXXXXXXXXXXXXXXXX", "----------------------------------------" + nombreProvincias.get(0));
                System.out.println("----------------------------------------" + nombreProvincias.get(0));

                if (null == arrayProvincias) { // Si la respuesta es null, una excepción ha ocurrido.
                    Toast.makeText(getApplicationContext(), "ERROR_COMUNICACION", Toast.LENGTH_SHORT).show();
                } else {

                    //EJECUTAR SPINNER + RECYCLER VIEW
                    ArrayAdapter<String> adapter= new ArrayAdapter <String> (this, android.R.layout.simple_spinner_item, nombreProvincias);
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
    }}