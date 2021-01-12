package com.example.euskomet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
        Intent ListadoDatos = new Intent(this,ListadoDatos.class);
        startActivity(ListadoDatos);

    }
    public void Top_ranking(View view){

    }
    public void Espacios_naturales(View view){

    }
    public void Favoritos(View view){

    }
    public void Atras(View view){

    }
}