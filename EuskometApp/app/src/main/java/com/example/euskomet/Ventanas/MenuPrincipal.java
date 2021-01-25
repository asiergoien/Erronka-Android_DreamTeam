package com.example.euskomet.Ventanas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
        startActivity(ListadoDatos);

    }
    public void Top_ranking(View view){

    }
    public void Espacios_naturales(View view){
        Intent ListadoDatos = new Intent(this, Listado_Espacios_Naturales.class);
        startActivity(ListadoDatos);
    }
    public void Favoritos(View view){

    }
    public void volver(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}