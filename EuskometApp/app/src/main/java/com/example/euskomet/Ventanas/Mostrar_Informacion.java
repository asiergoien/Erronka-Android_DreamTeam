package com.example.euskomet.Ventanas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.euskomet.ClienteThread;
import com.example.euskomet.R;

public class Mostrar_Informacion extends AppCompatActivity {
    private Button btnFotos;
    private Button btnLoc;
    private Button btnHis;
    private Button btnfav;
    private Button btnCom;
    private Button btnAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar__informacion);

        btnFotos = (Button)findViewById(R.id.btn_municipios);
        btnLoc = (Button)findViewById(R.id.btn_Top);
        btnHis = (Button)findViewById(R.id.btn_esp);
        btnfav = (Button)findViewById(R.id.btn_fav);
        btnCom = (Button)findViewById(R.id.btn_atras);
        btnAtras = (Button)findViewById(R.id.btn_atras);
    }

    public void Fotos(View view){
        Intent Fotos = new Intent(this, Fotos.class);
        startActivity(Fotos);

    }
    /*
    public void Localizacion(View view){
        Intent Fotos = new Intent(this, ClienteThread.ListadoDatos_Municipios.class);
        startActivity(Fotos);

    }
    public void Historico(View view){
        Intent Fotos = new Intent(this, ClienteThread.ListadoDatos_Municipios.class);
        startActivity(Fotos);

    }
    public void Favoritos(View view){
        Intent Fotos = new Intent(this, ClienteThread.ListadoDatos_Municipios.class);
        startActivity(Fotos);

    }
    */
    public void volver(View view) {
        Intent intent = new Intent(this,MenuPrincipal.class);
        startActivity(intent);
    }


}