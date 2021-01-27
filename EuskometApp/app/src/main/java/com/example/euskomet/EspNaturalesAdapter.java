package com.example.euskomet;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class EspNaturalesAdapter extends RecyclerView.Adapter<EspNaturalesAdapter.MyViewHolder> {
    private ArrayList<EspacioNatural> arrayEspNat = new ArrayList<EspacioNatural>();
    public OnItemClickListenerEspNat listener = null;



    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView tipo;
        public TextView cod_esp;
        public TextView nombre;


        public MyViewHolder(View view) {
            super(view);
            cod_esp = (TextView) view.findViewById(R.id.cod_esp_natural);
            nombre = (TextView) view.findViewById(R.id.nombre);
            tipo = (TextView) view.findViewById(R.id.tipo);

        }
    }

    public EspNaturalesAdapter(ArrayList<EspacioNatural> munList, OnItemClickListenerEspNat listener) {
        this.arrayEspNat = munList;
        this.listener = listener;
    }

    public EspNaturalesAdapter(ArrayList<EspacioNatural> munList) {
        this.arrayEspNat = munList;
    }

    public ArrayList<EspacioNatural> getArrayMunicipio() {
        return arrayEspNat;
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {
        EspacioNatural m = arrayEspNat.get(position);
        Log.i(TAG, "onBindViewHolder: he entrado en el bind");
        holder.cod_esp.setText(String.valueOf(m.getCod_esp_natural()));
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
        return arrayEspNat.size();
    }


    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.linea_esp_naturales, parent, false);
        return new MyViewHolder(v);
    }







}
