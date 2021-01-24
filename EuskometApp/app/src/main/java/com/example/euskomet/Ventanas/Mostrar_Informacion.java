package com.example.euskomet.Ventanas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.euskomet.ClienteThread;
import com.example.euskomet.Municipio;
import com.example.euskomet.R;

import java.util.ArrayList;

public class Mostrar_Informacion extends AppCompatActivity {
    private static final String TAG ="Mostrar_Informacion" ;
    private Button btnFotos;
    private Button btnLoc;
    private Button btnHis;
    private Button btnfav;
    private Button btnCom;
    private Button btnAtras;

    public static  TextView textView_Nom;
    public static TextView textView_Prov;
    public static EditText multilinea;

    public static  String nombre;
    public static  int cod_mun;
    public static  int cod_prov;
    public static String desc;


    private static ArrayList<Municipio> arrayDatosMunicipio = new ArrayList<Municipio>();

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




        textView_Nom= (TextView)findViewById(R.id.textview_Nombre);
        textView_Prov= (TextView)findViewById(R.id.textview_Provincia);
        multilinea = (EditText)findViewById(R.id.Multiline_Desc);


    }

    @Override
    public void onStart() {
        super.onStart();
        nombre =  getIntent().getStringExtra("nombre");
        desc = getIntent().getStringExtra("desc");
        cod_mun =  getIntent().getIntExtra("Cod_mun",-1);
        cod_prov =  getIntent().getIntExtra("Cod_prov",-1);
        cargarDatos();
    }

    public void Fotos(View view){
        Log.i(TAG, "Fotos: ------->"+cod_mun);
        Intent Fotos = new Intent(this, Fotos.class);
        Fotos.putExtra("Cod_mun", cod_mun);
        Fotos.putExtra("desc",desc);
        Fotos.putExtra("nombre",nombre);
        Fotos.putExtra("Cod_prov",cod_prov);
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
    public static void cargarDatos(){
        textView_Nom.setText(nombre);
        if (cod_prov==48){
            textView_Prov.setText("Bizkaia");
        }if (cod_prov==20){
            textView_Prov.setText("Gipuzkua");
        }if (cod_prov==1){
            textView_Prov.setText("Araba");
        }
        multilinea.setText(desc);
        //Aqui iria la descripcion
    }


}