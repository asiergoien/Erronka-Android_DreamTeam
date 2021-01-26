package com.example.euskomet.Ventanas;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.euskomet.Listado_Espacios_Naturales;
import com.example.euskomet.Listado_datosMun;
import com.example.euskomet.R;

public class MenuPrincipal extends AppCompatActivity {
    private Button btnMun;
    private Button btnTop;
    private Button btnEsp;
    private Button btnFav;
    private Button btnAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        btnMun = (Button)findViewById(R.id.btn_municipios);
        btnTop = (Button)findViewById(R.id.btn_Top);
        btnEsp = (Button)findViewById(R.id.btn_esp);
        btnFav = (Button)findViewById(R.id.btn_fav);
        btnAtras = (Button)findViewById(R.id.btn_atras);
    }
    public void Municipios(View view){
        Intent ListadoDatos = new Intent(this, Listado_datosMun.class);
        int codusu = getIntent().getIntExtra("cod_usuario", -1);
        Log.i("codusu", "MenuPrincMun: " + codusu);
        ListadoDatos.putExtra("cod_usuario", codusu);
        startActivity(ListadoDatos);

    }
    public void Top_ranking(View view){

    }
    @SuppressLint("LongLogTag")
    public void Espacios_naturales(View view){
        Intent ListadoDatos = new Intent(this, Listado_Espacios_Naturales.class);
        //aqui xq pasamos codusu?
        int codusu = getIntent().getIntExtra("cod_usuario", -1);
        Log.i("[Espacios_naturales - MENUPRINCIPAL]--Cod_usuario", "--------------------[CODIGO_USUARIO]-----"+codusu);
        ListadoDatos.putExtra("cod_usuario", codusu);
        ListadoDatos.putExtra("Municipio", codusu);
        startActivity(ListadoDatos);
    }
    public void Favoritos(View view){

    }

    public void volver(View view) {
        finish();
    }
}