package com.example.euskomet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListadoDatos extends AppCompatActivity {

    private RecyclerView oRecyclerView;
    ArrayList<EspacioNatural> espaciosArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_datos);

        oRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // Llenar el ArrayList.
        espaciosArrayList = new ArrayList<EspacioNatural>();
        for (int i=0;i<100;i++) {
            espaciosArrayList.add(new EspacioNatural(i+1, "Urkiola", "montaña"));
        }
        oRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//        // inicializar el adapter con nuestros datos.
//        ContactoAdapter ca = new ContactoAdapter(contactoArrayList);
//        oRecyclerView.setAdapter(ca);
        // establecer el Layout Manager.
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        oRecyclerView.setLayoutManager(llm);

        //Inicializar el adapter con nuestros datos.
        ContactoAdapter oContactoAdapter = new ContactoAdapter(espaciosArrayList, new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(EspacioNatural item) {
//                Toast.makeText(null, "Nombre: " + item.getNombre() , Toast.LENGTH_LONG).show();
//            }

            @Override
            public void onItemClick(EspacioNatural item) {
                Toast.makeText(null, "Nombre: " + item.getNombre() , Toast.LENGTH_LONG).show();
            }
        });
        oRecyclerView.setAdapter(oContactoAdapter);

    }
}