package com.example.euskomet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class ListadoDatos extends AppCompatActivity {

    private ConnectivityManager connectivityManager = null;

    public RecyclerView oRecyclerView;
    public ArrayList<Municipio> MuniArrayList;

    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MuniArrayList = new ArrayList<Municipio>();
        oRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        spinner = (Spinner) findViewById(R.id.spinner);

        conectarOnClick();

    }

    //  -------------------------------------------------------------------------------------------- CONEXIÓN BASE DE DATOS

    public void conectarOnClick() {

        try {
            if (isConnected()) {
                ArrayList<Provincias> arrayRespuesta = new ArrayList<Provincias>();
                arrayRespuesta = conectarProvincias();
                ArrayList<String> nombreProvincias = new ArrayList<String>();
                for(int i = 0; i < arrayRespuesta.size(); i++){
                    nombreProvincias.add(arrayRespuesta.get(i).getNombre());
                }

                if (null == arrayRespuesta) { // Si la respuesta es null, una excepción ha ocurrido.
                    Toast.makeText(getApplicationContext(), "ERROR_COMUNICACION", Toast.LENGTH_SHORT).show();
                } else {
                    ArrayAdapter<String> adapter= new ArrayAdapter <String> (this,  android.R.layout.simple_spinner_item, nombreProvincias);
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