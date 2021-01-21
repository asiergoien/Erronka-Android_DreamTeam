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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.euskomet.Ventanas.Login;
import com.example.euskomet.Ventanas.Registro;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ConnectivityManager connectivityManager = null;
    ImageView o_ImageView;

    public static SharedPreferences preferencias;

    public static android.widget.TextView TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferencias=getSharedPreferences("usuarios", Context.MODE_PRIVATE);

        o_ImageView = findViewById(R.id.logo);
        Animation oAnimacion = AnimationUtils.loadAnimation(getApplicationContext(),  R.anim.aparecer_logo);
        o_ImageView.startAnimation(oAnimacion);

        TextView=(TextView) findViewById(R.id.textView);
    }

    public void cambioPantallaLogin(View view) {

        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void cambioPantallaRegistro(View view) {

        Intent intent = new Intent(this, Registro.class);
        startActivity(intent);
    }

    //  -------------------------------------------------------------------------------------------- CONEXIÓN BASE DE DATOS


    public void conectarOnClick(View v) {

        try {
            if (isConnected()) {
                String sRespuesta =  conectarUsuarios();
                if (null == sRespuesta) { // Si la respuesta es null, una excepción ha ocurrido.
                    Toast.makeText(getApplicationContext(), "ERROR_COMUNICACION", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), sRespuesta, Toast.LENGTH_SHORT).show(); // Mostramos en el textView el nombre.
                    TextView.setText(sRespuesta);
                }
            } else {
                Toast.makeText(getApplicationContext(), "ERROR_NO_INTERNET", Toast.LENGTH_SHORT).show();
            }
        } catch (InterruptedException e) {
            // This cannot happen!
            Toast.makeText(getApplicationContext(), "ERROR_GENERAL", Toast.LENGTH_SHORT).show();
        }

    }
    private String conectarUsuarios() throws InterruptedException {
        ClienteThreadPrueba clienteThread = new ClienteThreadPrueba();
        Thread thread = new Thread(clienteThread);
        thread.start();
        thread.join(); // Esperar respuesta del servidor...
        return clienteThread.getResulset();
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


}

