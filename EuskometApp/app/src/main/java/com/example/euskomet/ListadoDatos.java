package com.example.euskomet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class ListadoDatos extends AppCompatActivity {
    public RecyclerView oRecyclerView;
    public ArrayList<Municipio> MuniArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MuniArrayList = new ArrayList<Municipio>();
        oRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        for (int i = 0; i < 7; i++){
            MuniArrayList.add(new Municipio());
        }

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        //Aqui da el fallo
        oRecyclerView.setLayoutManager(llm);
        MunicipioAdapter oMunicipioAdapter = new MunicipioAdapter(MuniArrayList, new OnItemClickListener() {
            @Override
            public void onItemClick(Municipio item) {
                Toast.makeText(ListadoDatos.this, "Nombre: " + item.getNombre() +"\nApellido: "+item.getCod_mun(), Toast.LENGTH_LONG).show();
            }
        });
        oRecyclerView.setAdapter(oMunicipioAdapter);

    }
}