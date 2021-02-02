package com.example.euskomet.Ventanas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.euskomet.CargarDatos;
import com.example.euskomet.Provincias;
import com.example.euskomet.R;
import com.example.euskomet.historicoObj;

import java.util.ArrayList;

public class historico extends AppCompatActivity {
    public static EditText multilinea;
    private static int cod_mun;
    private  static  ArrayList<historicoObj> ArrayHist = new ArrayList<historicoObj>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);
        multilinea = (EditText)findViewById(R.id.editTextTextMultiLine);
        cod_mun= getIntent().getIntExtra("cod_mun",0);

        try {
            ArrayHist=cargarHistorico();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (ArrayHist.size()>0){
            String str="";
            for (historicoObj hi1 : ArrayHist){
                str+= hi1.getFecha()+"\t-->\t"+hi1.getICA_estacion()+"\n";
            }
            multilinea.setText(str);
        }else{
            Toast.makeText(this, R.string. Sin_datos, Toast.LENGTH_SHORT).show();
        }


    }
    public ArrayList<historicoObj> cargarHistorico() throws InterruptedException {
        CargarDatos clienteThread = new CargarDatos("SELECT his.fecha, his.ICA_estacion FROM historico his JOIN estaciones est ON his.cod_est=est.cod_est WHERE est.cod_mun = "+cod_mun,9);
        Thread thread = new Thread(clienteThread);
        thread.start();
        thread.join(); // Esperar respuesta del servidor...

        ArrayList<historicoObj> ArrayHist = new ArrayList<historicoObj>();
        ArrayList<Object> viejo = new ArrayList<Object>();

        viejo= clienteThread.getCliemteThread_ArrayList();
        for (Object ob : viejo){
            ArrayHist.add((historicoObj)ob);
        }

        return ArrayHist;

    }
    public void volver(View view) {
        finish();
    }
}