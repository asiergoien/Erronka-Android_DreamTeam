package com.example.euskomet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MunicipioAdapter extends RecyclerView.Adapter<MunicipioAdapter.MyViewHolder> {
    private ArrayList<Municipio> arrayMunicipio = new ArrayList<Municipio>();
    public OnItemClickListener listener = null;



    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView cod_mun;
        public TextView nombre;
        public MyViewHolder(View view) {
            super(view);
            cod_mun = (TextView) view.findViewById(R.id.cod_mun);
            nombre = (TextView) view.findViewById(R.id.nombre);

        }
    }

    public MunicipioAdapter(ArrayList<Municipio> munList, OnItemClickListener listener) {
        this.arrayMunicipio = munList;
        this.listener = listener;
    }

    public MunicipioAdapter(ArrayList<Municipio> munList) {
        this.arrayMunicipio = munList;
    }

    public ArrayList<Municipio> getArrayMunicipio() {
        return arrayMunicipio;
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {
        Municipio m = arrayMunicipio.get(position);
        holder.cod_mun.setText(String.valueOf(m.getCod_mun()));
        holder.nombre.setText(m.getNombre());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(m);
            }
        });

    }
    @Override
    public int getItemCount() {
        return arrayMunicipio.size();
    }


    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.linea, parent, false);
        return new MyViewHolder(v);
    }







}
