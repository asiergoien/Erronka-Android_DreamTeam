package com.example.euskomet.Ventanas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.euskomet.R;

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
    public boolean isConnected() {
        boolean ret = false;
        try {
            connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if ((networkInfo != null) && (networkInfo.isAvailable()) &&
                    (networkInfo.isConnected()))
                ret = true;
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), R.string.error_com, Toast.LENGTH_SHORT).show();
        }
        return ret;
    }


}

