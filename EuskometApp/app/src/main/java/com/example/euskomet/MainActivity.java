package com.example.euskomet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ConnectivityManager connectivityManager = null;
    ImageView o_ImageView;

    public static SharedPreferences preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferencias=getSharedPreferences("usuarios", Context.MODE_PRIVATE);

        o_ImageView = findViewById(R.id.logo);
        Animation oAnimacion = AnimationUtils.loadAnimation(getApplicationContext(),  R.anim.aparecer_logo);
        o_ImageView.startAnimation(oAnimacion);
    }

    public void cambioPantallaLogin(View view) {

        Intent intent = new Intent(this,Login.class);
        startActivity(intent);
    }

    public void cambioPantallaRegistro(View view) {

        Intent intent = new Intent(this,Registro.class);
        startActivity(intent);
    }

    //  -------------------------------------------------------------------------------------------- CONEXIÓN BASE DE DATOS
    public void conectarOnClick(View v) {

        Log.i("tag", " *********************************** entra en conectarOnClick()");

        try {
            if (isConnected()) {
                Log.i("tag", " *********************************** entra en el if de conectarOnClick()");
                String sRespuesta = conectar();
                Log.i("tag", " *********************************** entra en el if de conectarOnClick() y ejecuta conectar()");
                if (null == sRespuesta) { // Si la respuesta es null, una excepción ha ocurrido.
                    Toast.makeText(getApplicationContext(), "ERROR_COMUNICACION", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), sRespuesta, Toast.LENGTH_SHORT).show(); // Mostramos en el textView el nombre.
                }
            } else {
                Log.i("tag", " *********************************** no entra en el if de conectarOnClick()");
                Toast.makeText(getApplicationContext(), "ERROR_NO_INTERNET", Toast.LENGTH_SHORT).show();
            }
        } catch (InterruptedException e) {
            // This cannot happen!
            Toast.makeText(getApplicationContext(), "ERROR_GENERAL", Toast.LENGTH_SHORT).show();
        }

        Log.i("tag", " *********************************** sale de conectarOnClick()");
    }
    private String conectar() throws InterruptedException {
        Log.i("tag", " *********************************** entra en conectar()");
        ClienteThread clienteThread = new ClienteThread(); Log.i("tag", " *********************************** conectar() - 1");
        Thread thread = new Thread(clienteThread); Log.i("tag", " *********************************** conectar() - 2");
        thread.start(); Log.i("tag", " *********************************** conectar() - 3");
        thread.join(); // Esperar respuesta del servidor...
        Log.i("tag", " *********************************** sale de conectar()");
        return clienteThread.getResponse();
    }
    public boolean isConnected() {
        Log.i("tag", " *********************************** entra en isConnected()");
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
        Log.i("tag", " *********************************** sale de isConnected()");
        return ret;
    }
}

