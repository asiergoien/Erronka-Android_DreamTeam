package com.example.euskomet;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/*
public class ListadoDatos_EspNaturales extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

    private ConnectivityManager connectivityManager = null;

    private MunicipioAdapter oListaAdapter = null;
    public RecyclerView oRecyclerView;

//    public ArrayList<Provincias> provArrayList = new ArrayList<Provincias>();
    ArrayList<String> nombreMunicipio = new ArrayList<String>();
    ArrayList<EspacioNatural> arrayDatosEspNatural = new ArrayList<EspacioNatural>();
    ArrayList<Municipio> arrayDatosEspNatural_Bizkaia = new ArrayList<Municipio>();
    ArrayList<Municipio> arrayDatosEspNatural_Gipuzkoa = new ArrayList<Municipio>();
    ArrayList<Municipio> arrayDatosEspNatural_Araba = new ArrayList<Municipio>();

    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_datos_esp_naturales);
//        provArrayList = new ArrayList<Provincias>();
        oRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        conectarOnClick();

        try {
            ArrayList<EspacioNatural> arrayEspNatural = new ArrayList<EspacioNatural>();
            arrayEspNatural = conectarEspaciosNaturales();
            for (int i=0;i<arrayEspNatural.size();i++) {
                arrayDatosEspNatural.add(arrayEspNatural.get(i));
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        oRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        oRecyclerView.setLayoutManager(llm);
/*
        for (int i=0;i<arrayDatosEspNatural.size();i++) {
            if (arrayDatosEspNatural.get(i).getCod_mun()==1) {
                arrayDatosEspNatural_Bizkaia.add(arrayDatosEspNatural.get(i));
            }
            if (arrayDatosEspNatural.get(i).getCod_mun()==2) {
                arrayDatosEspNatural_Gipuzkoa.add(arrayDatosEspNatural.get(i));
            }
            if (arrayDatosEspNatural.get(i).getCod_mun()==3) {
                arrayDatosEspNatural_Araba.add(arrayDatosEspNatural.get(i));
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
                ArrayList<Municipio> arrayMunicipios = new ArrayList<Municipio>();
                arrayMunicipios = conectarMunicipios();

//                ArrayList<String> nombreProvincias = new ArrayList<String>();
//                String nombreProvincias[] = new String[arrayProvincias.size()];
                for(int i = 0; i < arrayMunicipios.size(); i++){
                    nombreMunicipio.add(arrayMunicipios.get(i).getNombre());
//                    nombreProvincias[i] = arrayProvincias.get(i).getNombre();
                }

                Log.i("XXXXXXXXXXXXXXXXXXXXXX", "----------------------------------------" + nombreMunicipio.get(0));
                System.out.println("----------------------------------------" + nombreMunicipio.get(0));

                if (null == arrayMunicipios) { // Si la respuesta es null, una excepción ha ocurrido.
                    Toast.makeText(getApplicationContext(), "ERROR_COMUNICACION", Toast.LENGTH_SHORT).show();
                } else {

                    //EJECUTAR SPINNER + RECYCLER VIEW
                    ArrayAdapter<String> adapter= new ArrayAdapter <String> (this, android.R.layout.simple_spinner_item, nombreMunicipio);
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
    private ArrayList<Municipio> conectarMunicipios() throws InterruptedException {
        ClienteThreadMunicipios clienteThread = new ClienteThreadMunicipios();
        Thread thread = new Thread(clienteThread);
        thread.start();
        thread.join(); // Esperar respuesta del servidor...
        return clienteThread.getMunArrayList();
    }

    private ArrayList<EspacioNatural> conectarEspaciosNaturales() throws InterruptedException {
        ClienteThreadEspaciosNaturales clienteThread = new ClienteThreadEspaciosNaturales();
        Thread thread = new Thread(clienteThread);
        thread.start();
        thread.join(); // Esperar respuesta del servidor...
        return clienteThread.getEspNatArrayList();
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

 */