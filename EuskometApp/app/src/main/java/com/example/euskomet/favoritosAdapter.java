package com.example.euskomet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class favoritosAdapter extends RecyclerView.Adapter<favoritosAdapter.MyViewHolder> {


    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView cod_mun;
        public TextView nombre;


        public MyViewHolder(View view) {
            super(view);
            cod_mun = (TextView) view.findViewById(R.id.cod_mun);
            nombre = (TextView) view.findViewById(R.id.nombre);

        }
    }
    @NonNull
    @Override
    public favoritosAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.linea_municipios, parent, false);
        return new favoritosAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }



    @Override
    public int getItemCount() {
        return 0;
    }

}
