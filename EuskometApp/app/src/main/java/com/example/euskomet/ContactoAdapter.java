package com.example.euskomet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class ContactoAdapter extends RecyclerView.Adapter<ContactoAdapter.MyViewHolder> {
    private List<EspacioNatural> espacioNaturalListList;
    /**
     * Clase ViewHolder * */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView cod_esp_natural;
        public TextView nombre;
        public TextView tipo;
        public MyViewHolder(View view) {
            super(view);
            cod_esp_natural = (TextView) view.findViewById(R.id.cod_esp_natural);
            nombre = (TextView) view.findViewById(R.id.nombre);
            tipo = (TextView) view.findViewById(R.id.tipo);
        }
    }

    private final AdapterView.OnItemClickListener listener;
    // Constructor del Adaptador.
    public ContactoAdapter(List<EspacioNatural> espacioNaturalListList, AdapterView.OnItemClickListener listener) {
        this.espacioNaturalListList = espacioNaturalListList;
        this.listener = listener;
    }

    // Este metodo es llamado por el RecyclerView para mostrar los datos del elemento de esa posición
    @Override
    public void onBindViewHolder(MyViewHolder holder, int posicion) {
        EspacioNatural c = espacioNaturalListList.get(posicion);
        holder.cod_esp_natural.setText(c.getCod_esp_natural());
        holder.nombre.setText(c.getNombre());
        holder.tipo.setText(c.getTipo());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                listener.onItemClick(c);
                Toast.makeText(null, "Pulsando elemento nº" + posicion, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return espacioNaturalListList.size();
    }

// A este metodo se le llama cuando necesitamos crear una nueva linea para el RecyclerView.
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.linea,parent, false);
        return new MyViewHolder(v);
    }
}

