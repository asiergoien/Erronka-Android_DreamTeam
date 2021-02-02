package com.example.euskomet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.euskomet.Ventanas.Mostrar_Informacion;

import java.util.ArrayList;

public class Mostrar_Top extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {

    private ConnectivityManager connectivityManager = null;

    private MunicipioAdapter oListaAdapter = null;
    public RecyclerView oRecyclerView;

    //    public ArrayList<Provincias> provArrayList = new ArrayList<Provincias>();
    ArrayList<String> nombreProvincias = new ArrayList<String>();

    ArrayList<Municipio> arrayDatosMunicipio = new ArrayList<Municipio>();
    ArrayList<Municipio> arrayDatosMunicipio_Bizkaia = new ArrayList<Municipio>();
    ArrayList<Municipio> arrayDatosMunicipio_Gipuzkoa = new ArrayList<Municipio>();
    ArrayList<Municipio> arrayDatosMunicipio_Araba = new ArrayList<Municipio>();

//    ArrayList<FavoritosMun> arrayDatosFav = new ArrayList<FavoritosMun>();
    ArrayList<FavoritosMun> arrayDatosFavBizkaia = new ArrayList<FavoritosMun>();
    ArrayList<FavoritosMun> arrayDatosFavGipuzkoa = new ArrayList<FavoritosMun>();
    ArrayList<FavoritosMun> arrayDatosFavAraba = new ArrayList<FavoritosMun>();




    private Spinner spinner;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_datos_municipios);
        //        provArrayList = new ArrayList<Provincias>();
        oRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        conectarOnClick();


        try {
            ArrayList<Municipio> arrayMunicipio = new ArrayList<Municipio>();
            arrayMunicipio = conectarMunicipios();
            for (int i = 0; i < arrayMunicipio.size(); i++) {
                arrayDatosMunicipio.add(arrayMunicipio.get(i));
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        try {
//            ArrayList<FavoritosMun> arrayTop = new ArrayList<FavoritosMun>();
//            arrayTop = conectarTopBiz();
//            for (int i = 0; i < arrayTop.size(); i++) {
//                arrayDatosFav.add(arrayTop.get(i));
//            }
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        try {
            ArrayList<FavoritosMun> arrayTopBizkaia = new ArrayList<FavoritosMun>();
            arrayTopBizkaia = conectarTopBiz();
            for (int i = 0; i < arrayTopBizkaia.size(); i++) {
                arrayDatosFavBizkaia.add(arrayTopBizkaia.get(i));
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            ArrayList<FavoritosMun> arrayTopGipuzkoa = new ArrayList<FavoritosMun>();
            arrayTopGipuzkoa = conectarTopGi();
            for (int i = 0; i < arrayTopGipuzkoa.size(); i++) {
                arrayDatosFavGipuzkoa.add(arrayTopGipuzkoa.get(i));
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            ArrayList<FavoritosMun> arrayTopAraba = new ArrayList<FavoritosMun>();
            arrayTopAraba = conectarTopAra();
            for (int i = 0; i < arrayTopAraba.size(); i++) {
                arrayDatosFavAraba.add(arrayTopAraba.get(i));
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        oRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        oRecyclerView.setLayoutManager(llm);

        for (FavoritosMun favMun : arrayDatosFavBizkaia) {
            for (int i = 0; i < arrayDatosMunicipio.size(); i++) {
                if(favMun.getCod_mun() == arrayDatosMunicipio.get(i).getCod_mun()){
                    arrayDatosMunicipio_Bizkaia.add(arrayDatosMunicipio.get(i));
                }
            }
        }

        for (FavoritosMun favMun : arrayDatosFavGipuzkoa) {
            for (int i = 0; i < arrayDatosMunicipio.size(); i++) {
                if(favMun.getCod_mun() == arrayDatosMunicipio.get(i).getCod_mun()){
                    arrayDatosMunicipio_Gipuzkoa.add(arrayDatosMunicipio.get(i));
                }
            }
        }

        for (FavoritosMun favMun : arrayDatosFavAraba) {
            for (int i = 0; i < arrayDatosMunicipio.size(); i++) {
                if(favMun.getCod_mun() == arrayDatosMunicipio.get(i).getCod_mun()){
                    arrayDatosMunicipio_Araba.add(arrayDatosMunicipio.get(i));
                }
            }
        }


//        for (FavoritosMun favMun : arrayDatosFav) {
//            for (int i = 0; i < arrayDatosMunicipio.size(); i++) {
//                if (arrayDatosMunicipio.get(i).getCod_prov() == 48 && favMun.getCod_mun() == arrayDatosMunicipio.get(i).getCod_mun()) { //Bizkaia
//                    arrayDatosMunicipio_Bizkaia.add(arrayDatosMunicipio.get(i));
//                    Log.i("[FOR MUNICIPIOS-- arrayDatosMunicipio_Bizkaia", "--------------------[ArrayDatosMun.size]-----"+ arrayDatosMunicipio_Bizkaia.size());
//
//                }
//                if (arrayDatosMunicipio.get(i).getCod_prov() == 20 && favMun.getCod_mun() == arrayDatosMunicipio.get(i).getCod_mun()) { //Giputxilandia
//                    arrayDatosMunicipio_Gipuzkoa.add(arrayDatosMunicipio.get(i));
//                    Log.i("[FOR MUNICIPIOS-- arrayDatosMunicipio_Gipuzkoa", "--------------------[ArrayDatosMun.size]-----"+ arrayDatosMunicipio_Gipuzkoa.size());
//                }
//                if (arrayDatosMunicipio.get(i).getCod_prov() == 1 && favMun.getCod_mun() == arrayDatosMunicipio.get(i).getCod_mun()) {
//                    { //Araba
//                        arrayDatosMunicipio_Araba.add(arrayDatosMunicipio.get(i));
//                        Log.i("[FOR MUNICIPIOS-- arrayDatosMunicipio_Araba", "--------------------[ArrayDatosMun.size]-----"+ arrayDatosMunicipio_Araba.size());
//                    }
//                }
//            }
//
//        }





//        for (int i = 0; i < arrayDatosMunicipio.size(); i++) {
//            if (arrayDatosMunicipio.get(i).getCod_prov() == 48) { //Bizkaia
//                arrayDatosMunicipio_Bizkaia.add(arrayDatosMunicipio.get(i));
//            }
//            if (arrayDatosMunicipio.get(i).getCod_prov() == 20) { //Giputxilandia
//                arrayDatosMunicipio_Gipuzkoa.add(arrayDatosMunicipio.get(i));
//            }
//            if (arrayDatosMunicipio.get(i).getCod_prov() == 1) { //Araba
//                arrayDatosMunicipio_Araba.add(arrayDatosMunicipio.get(i));
//            }
//        }

    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        String selec = spinner.getSelectedItem().toString();
        if (selec.equals("Bizkaia")) {
            oListaAdapter = new MunicipioAdapter(arrayDatosMunicipio_Bizkaia, new OnItemClickListener() {
                @Override
                public void onItemClick(Municipio item) {
                    Mostrar_informacion(item);
                    Log.i("tag", "   ----------------------------------------------------     Bizkaia : ");
                }
            });
        } else if (selec.equals("Gipuzkoa")) {
            oListaAdapter = new MunicipioAdapter(arrayDatosMunicipio_Gipuzkoa, new OnItemClickListener() {
                @Override
                public void onItemClick(Municipio item) {
                    Mostrar_informacion(item);
                    Log.i("tag", "   ----------------------------------------------------     Gipuzkoa : ");
                }
            });
        } else if (selec.equals("Araba")) {
            oListaAdapter = new MunicipioAdapter(arrayDatosMunicipio_Araba, new OnItemClickListener() {
                @Override
                public void onItemClick(Municipio item) {
                    Mostrar_informacion(item);
                    Log.i("tag", "   ----------------------------------------------------     Araba : ");
                }
            });
        }
        oRecyclerView.setAdapter(oListaAdapter);
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    //  -------------------------------------------------------------------------------------------- CONEXIÓN BASE DE DATOS

    public void conectarOnClick() {

        try {
            if (isConnected()) {
                ArrayList<Provincias> arrayProvincias = new ArrayList<Provincias>();
                arrayProvincias = conectarProvincias();
                Log.i("pruebas", "arrayList prov"+arrayProvincias.size());
                for (int i = 0; i < arrayProvincias.size(); i++) {
                    nombreProvincias.add(arrayProvincias.get(i).getNombre());
                }
                if (null == arrayProvincias) { // Si la respuesta es null, una excepción ha ocurrido.
                    Toast.makeText(getApplicationContext(), R.string.error_base, Toast.LENGTH_SHORT).show();
                } else {

                    //EJECUTAR SPINNER + RECYCLER VIEW
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nombreProvincias);
                    spinner.setAdapter(adapter);
                }
            } else {
                Toast.makeText(getApplicationContext(), R.string.error_com, Toast.LENGTH_SHORT).show();
            }
        } catch (InterruptedException e) {
            // This cannot happen!
            Toast.makeText(getApplicationContext(), R.string.error_com, Toast.LENGTH_SHORT).show();
        }

    }



    //----------------------------------------------------------- CONECTAR PROVINCIAS
    private ArrayList<Provincias> conectarProvincias() throws InterruptedException {
        CargarDatos clienteThread = new CargarDatos("SELECT * FROM provincias", 1);
        Thread thread = new Thread(clienteThread);
        thread.start();
        thread.join(); // Esperar respuesta del servidor...

        ArrayList<Provincias> ArrayProv = new ArrayList<Provincias>();
        ArrayList<Object> viejo = new ArrayList<Object>();

        viejo= clienteThread.getCliemteThread_ArrayList();
        for (Object ob : viejo){
            ArrayProv.add((Provincias)ob);
        }

        return ArrayProv;
    }


    //----------------------------------------------------------- CONECTAR MUNICIPIOS
    private ArrayList<Municipio> conectarMunicipios() throws InterruptedException {
        CargarDatos clienteThread = new CargarDatos("SELECT * FROM municipios", 2);
        Thread thread = new Thread(clienteThread);
        thread.start();
        thread.join(); // Esperar respuesta del servidor...

        ArrayList<Municipio> ArrayMun= new ArrayList<Municipio>();
        ArrayList<Object> viejo = new ArrayList<Object>();

        viejo= clienteThread.getCliemteThread_ArrayList();
        for (Object ob : viejo){
            ArrayMun.add((Municipio) ob);
        }

        return ArrayMun;
    }
    @SuppressLint("LongLogTag")
    private ArrayList<FavoritosMun> conectarTopBiz() throws InterruptedException {
        CargarDatos clienteThread = new CargarDatos("SELECT fm.cod_mun FROM favoritos_mun fm, municipios m WHERE fm.cod_mun = m.cod_mun AND m.cod_prov = 48 GROUP BY m.cod_mun ORDER BY COUNT(fm.cod_mun) DESC Limit 5", 8);
       // WHERE municipios.provincias.nombre = 'Bizkaia' GROUP BY municipios.codMun ORDER BY COUNT(municipios.codMun) DESC
        Thread thread = new Thread(clienteThread);
        thread.start();
        thread.join(); // Esperar respuesta del servidor...

        ArrayList<FavoritosMun> ArrayTop= new ArrayList<FavoritosMun>();
        ArrayList<Object> viejo = new ArrayList<Object>();

        viejo= clienteThread.getCliemteThread_ArrayList();
        for (Object ob : viejo){
            ArrayTop.add((FavoritosMun) ob);
        }

        Log.i("[FOR TOP BIZKAIA-- conectarTopBiz", "--------------------[Array.size]-----"+ ArrayTop.size());

        return ArrayTop;
    }
    @SuppressLint("LongLogTag")
    private ArrayList<FavoritosMun> conectarTopAra() throws InterruptedException {
        CargarDatos clienteThread = new CargarDatos("SELECT fm.cod_mun FROM favoritos_mun fm, municipios m WHERE fm.cod_mun = m.cod_mun AND m.cod_prov = 1 GROUP BY m.cod_mun ORDER BY COUNT(fm.cod_mun) DESC Limit 5", 8);
        // WHERE municipios.provincias.nombre = 'Bizkaia' GROUP BY municipios.codMun ORDER BY COUNT(municipios.codMun) DESC
        Thread thread = new Thread(clienteThread);
        thread.start();
        thread.join(); // Esperar respuesta del servidor...

        ArrayList<FavoritosMun> ArrayTop= new ArrayList<FavoritosMun>();
        ArrayList<Object> viejo = new ArrayList<Object>();

        viejo= clienteThread.getCliemteThread_ArrayList();
        for (Object ob : viejo){
            ArrayTop.add((FavoritosMun) ob);
        }

        Log.i("[FOR TOP araba patatas-- conectarTopAraba", "--------------------[Array.size]-----"+ ArrayTop.size());

        return ArrayTop;
    }
    @SuppressLint("LongLogTag")
    private ArrayList<FavoritosMun> conectarTopGi() throws InterruptedException {
        CargarDatos clienteThread = new CargarDatos("SELECT fm.cod_mun FROM favoritos_mun fm, municipios m WHERE fm.cod_mun = m.cod_mun AND m.cod_prov = 20 GROUP BY m.cod_mun ORDER BY COUNT(fm.cod_mun) DESC Limit 5", 8);
        // WHERE municipios.provincias.nombre = 'Bizkaia' GROUP BY municipios.codMun ORDER BY COUNT(municipios.codMun) DESC
        Thread thread = new Thread(clienteThread);
        thread.start();
        thread.join(); // Esperar respuesta del servidor...

        ArrayList<FavoritosMun> ArrayTop= new ArrayList<FavoritosMun>();
        ArrayList<Object> viejo = new ArrayList<Object>();

        viejo= clienteThread.getCliemteThread_ArrayList();
        for (Object ob : viejo){
            ArrayTop.add((FavoritosMun) ob);
        }

        Log.i("[FOR TOP Giputxilandia-- conectarTopGi", "--------------------[Array.size]-----"+ ArrayTop.size());

        return ArrayTop;
    }

    public boolean isConnected() {
        boolean ret = false;
        try {
            connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if ((networkInfo != null) && (networkInfo.isAvailable()) &&
                    (networkInfo.isConnected()))
                ret = true;
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), R.string.error_com, Toast.LENGTH_SHORT).show();
        }
        return ret;
    }

    @SuppressLint("LongLogTag")
    public void Mostrar_informacion(Municipio item) {
        Intent Mostrar_informacion = new Intent(this, Mostrar_Informacion.class);
        Mostrar_informacion.putExtra("cod", item.getCod_mun());
        Mostrar_informacion.putExtra("desc",item.getDesc());
        Mostrar_informacion.putExtra("nombre",item.getNombre());
        Mostrar_informacion.putExtra("Cod_prov",item.getCod_prov());

        int codusu = getIntent().getIntExtra("cod_usuario", -1);

        Log.i("MOSTRARINFORMACION_MUN", "---------------------------------------MOSTRARINFORMACION_MUN--------------------------------------------- ");
        Log.i("[INTENT MOSTRARINFORMACION MUNICIPIO]--Cod_usuario", "--------------------[CODIGO_USUARIO]-----"+codusu);
        Log.i("[INTENT MOSTRARINFORMACION MUNICIPIO]--cod_mun", "--------------------[CODIGO_MUN]-----"+item.getCod_mun());
        Log.i("[INTENT MOSTRARINFORMACION MUNICIPIO]--nombre_municipio", "--------------------[NOMBRE_MUN]-----"+item.getNombre());
        Log.i("[INTENT MOSTRARINFORMACION MUNICIPIO]--cod_prov", "--------------------[CODIGO_PROV]-----"+item.getCod_prov());
        Mostrar_informacion.putExtra("cod_usuario", codusu);
        Mostrar_informacion.putExtra("fav", "favoritos_mun");
        startActivity(Mostrar_informacion);
    }
}
