package com.example.euskomet.Ventanas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.euskomet.BuildConfig;
import com.example.euskomet.CargarDatos;
import com.example.euskomet.InsertDatos;
import com.example.euskomet.Municipio;
import com.example.euskomet.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class Mostrar_Informacion extends AppCompatActivity {
    private static final String TAG ="Mostrar_Informacion" ;
    private Button btnFotos;
    private Button btnLoc;
    private Button btnHis;
    private Button btnfav;
    private Button btnCom_loc;
    private Button btnCom_img;
    private Button btnAtras;
    private TextView Tipo_Prov;
    private TextView textview_Provincia;
    private TextView text_titulo;


    public static  TextView textView_Nom;
    public static TextView textView_Prov;
    public static EditText multilinea;

    public static  String nombre;
    public static  int cod;
    public static  int cod_prov;
    private int codusu;
    public static String desc;
    private String fav;

   private static Double Longitud;
   private static Double Latitud;


    private static ArrayList<Municipio> arrayDatosMunicipio = new ArrayList<Municipio>();
    private boolean favorito;

    @SuppressLint({"ResourceAsColor", "LongLogTag"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar__informacion);

        btnFotos = (Button)findViewById(R.id.btnFotos);
        text_titulo = (TextView)findViewById(R.id.text_titulo);
        btnLoc = (Button)findViewById(R.id.btnLoc);
        btnHis = (Button)findViewById(R.id.btnHis);
        btnfav = (Button)findViewById(R.id.btnfav);
        btnCom_loc = (Button)findViewById(R.id.btnCom);
        btnCom_img = (Button)findViewById(R.id.btnCom_img);
        btnAtras = (Button)findViewById(R.id.btn_atras);
        Tipo_Prov = (TextView)findViewById(R.id.label_Tipo_Prov);
        textview_Provincia = (TextView)findViewById(R.id.textview_Provincia);

        fav = getIntent().getStringExtra("fav");

        if (fav.equals("favoritos_mun")) {
            Tipo_Prov.setText("Provincia");
            btnLoc.setVisibility(View.GONE);
        }
        else if (fav.equals("favoritos_esp")) {
            Tipo_Prov.setText("Tipo");
            String nombre_esp =getIntent().getStringExtra("tipo");
            textview_Provincia.setText(nombre_esp);
        }

        codusu = getIntent().getIntExtra("cod_usuario", -1);

        Log.i("[MOSTRAR_INFORMACIO] oncreate", "------------[CODIGO_USUARIO]---------- " + codusu);
        cod =  getIntent().getIntExtra("cod",-1);


        try {
            esFavorito();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (favorito) {
            btnfav.setBackgroundColor(Color.YELLOW);
            btnfav.setTextColor(Color.BLACK);
        }
        else  {
            btnfav.setBackgroundColor(Color.parseColor("#6B9080"));
            btnfav.setTextColor(Color.parseColor("#F6FFF8"));
        }

        textView_Nom= (TextView)findViewById(R.id.textview_Nombre);
        textView_Prov= (TextView)findViewById(R.id.textview_Provincia);
        multilinea = (EditText)findViewById(R.id.Multiline_Desc);

        Longitud= getIntent().getDoubleExtra("lo",0);
        Latitud= getIntent().getDoubleExtra("la",0);

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onStart() {
        super.onStart();
        nombre =  getIntent().getStringExtra("nombre");
        desc = getIntent().getStringExtra("desc");
        cod =  getIntent().getIntExtra("cod",-1);
        cod_prov =  getIntent().getIntExtra("Cod_prov",-1);
        codusu = getIntent().getIntExtra("cod_usuario", -1);
        cargarDatos();

        fav = getIntent().getStringExtra("fav");


        if (fav.equals("favoritos_mun")) {
            Tipo_Prov.setText("Provincia");
        }
        else if (fav.equals("favoritos_esp")) {
            Tipo_Prov.setText("Tipo");
            String nombre_esp =getIntent().getStringExtra("tipo");
            textview_Provincia.setText(nombre_esp);

        }

        codusu = getIntent().getIntExtra("cod_usuario", -1);
        cod =  getIntent().getIntExtra("cod",-1);
        try {
            esFavorito();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (favorito) {
            btnfav.setBackgroundColor(Color.YELLOW);
            btnfav.setTextColor(Color.BLACK);
        }
        else  {
            btnfav.setBackgroundColor(Color.parseColor("#6B9080"));
            btnfav.setTextColor(Color.parseColor("#F6FFF8"));
        }

        textView_Nom= (TextView)findViewById(R.id.textview_Nombre);
        textView_Prov= (TextView)findViewById(R.id.textview_Provincia);
        multilinea = (EditText)findViewById(R.id.Multiline_Desc);

        Longitud= getIntent().getDoubleExtra("lo",0);
        Latitud= getIntent().getDoubleExtra("la",0);
    }

    private void esFavorito() throws InterruptedException {

        Log.i("esFavorito", "----------[CODIGO_USUARIO]: "+codusu);
        CargarDatos clienteThread = new CargarDatos("SELECT cod_relacion FROM "+fav+" WHERE cod_relacion = '" + cod+"-"+codusu+"'", 6);
        Thread thread = new Thread(clienteThread);
        thread.start();
        thread.join(); // Esperar respuesta del servidor...

        int i = clienteThread.getCliemteThread_ArrayList().size();
        Log.i("Favorito", "esFavorito: " + i);
        favorito = !(i == 0);
    }

    public void Fotos(View view){

        Log.i("FOTOS", "----------[CODIGO_USUARIO]: "+codusu);
        Log.i("FOTOS", "----------------[CODIGO FOTO]"+cod);

        Intent Fotos = new Intent(this, Fotos.class);
        Fotos.putExtra("cod", cod);
        Fotos.putExtra("foto", (fav.equals("favoritos_mun") ? "fotos_municipios" : (fav.equals("favoritos_esp") ? "fotos_esp_naturales" : null)));
        startActivity(Fotos);
    }

    public void Localizacion(View view){
          Longitud= getIntent().getDoubleExtra("lo",0);
          Latitud= getIntent().getDoubleExtra("la",0);
        Intent Localizacion = new Intent(this,Mapa.class);

        //Aqui le paso a la pantala mapa la longitud y latitud
        Localizacion.putExtra("lo",Longitud);
        Localizacion.putExtra("la",Latitud);
        Localizacion.putExtra("nombre",nombre);
        startActivity(Localizacion);

    }

    public void Historico(View view){
        Intent historico = new Intent(this, historico.class);
        historico.putExtra("cod_mun",cod);
        startActivity(historico);

    }
    @SuppressLint("ResourceAsColor")
    public void Favoritos(View view) throws InterruptedException {

        Log.i("Favoritos--Delete", "----------[CODIGO_USUARIO]: "+codusu);

        if (favorito) {
            Log.i("codusu", "Favoritos: " + codusu);
            String sql = "delete from " + fav +" where cod_relacion='"+cod+"-"+codusu+"'";
            InsertDatos in1 = new InsertDatos(sql);
            Thread thread = new Thread(in1);
            thread.start();
            thread.join(); // Esperar respuesta del servidor...
            if (in1.isBol()) {
                btnfav.setBackgroundColor(Color.parseColor("#6B9080"));
                btnfav.setTextColor(Color.parseColor("#F6FFF8"));
            }
            favorito = false;
        }
        else {
            String fav = getIntent().getStringExtra("fav");
            int codusu = getIntent().getIntExtra("cod_usuario", -1);
            Log.i("codusu", "Favoritos: " + codusu);
            String sql = "insert into " + fav + " (cod_relacion, " + (fav.equals("favoritos_mun") ? "cod_mun" : (fav.equals("favoritos_esp") ? "cod_esp_natural" : null)) + ", cod_user) values('" + cod + "-" + codusu + "','" + cod + "','" + codusu + "')";
            InsertDatos in1 = new InsertDatos(sql);
            Thread thread = new Thread(in1);
            thread.start();
            thread.join(); // Esperar respuesta del servidor...
            if (in1.isBol()) {
                btnfav.setBackgroundColor(Color.YELLOW);
                btnfav.setTextColor(Color.BLACK);
            }
            favorito = true;
        }
    }

    public void volver(View view) {
        finish();
    }


    public void Compartir_Loc (View view){
        try{
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT,getResources().getString(R.string.app_name));
            //String str=R.string.strCom+"\n";
            String str="Te ha compartido una ubicacion \n";
            if (Latitud!=0 && Longitud!=0){
                 str+= nombre +" https://www.google.es/maps/place/"+Latitud+","+Longitud;
                i.putExtra(Intent.EXTRA_TEXT,str);
                startActivity(i);
            }
        }catch (Exception exception){
            Toast.makeText(this, R.string.errorCom, Toast.LENGTH_SHORT).show();
        }


    }

    public void Compartir_Img (View view) throws InterruptedException {
         ArrayList<Bitmap> arrayBitmap;
          String fot_tabla= (fav.equals("favoritos_mun") ? "fotos_municipios" : (fav.equals("favoritos_esp") ? "fotos_esp_naturales" : null));

        CargarDatos clienteThread = new CargarDatos("SELECT * FROM "+fot_tabla+" WHERE "+(fot_tabla.equals("fotos_municipios") ? "cod_mun" : (fot_tabla.equals("fotos_esp_naturales") ? "cod_esp_natural" : null))+" = " + cod, 7);
        Thread thread = new Thread(clienteThread);
        thread.start();
        thread.join();

        arrayBitmap= new ArrayList<Bitmap>();
        ArrayList<Object> viejo = new ArrayList<Object>();

        viejo= clienteThread.getCliemteThread_ArrayList();
        for (Object ob : viejo){
            arrayBitmap.add((Bitmap) ob);
        }
            if (arrayBitmap.size()>0){

                Bitmap bitmap= arrayBitmap.get(0);
                try {
                    File file = new File(getApplicationContext().getExternalCacheDir(), File.separator +"temp.jpeg");
                    FileOutputStream fOut = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                    fOut.flush();
                    fOut.close();
                    file.setReadable(true, false);
                    final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_SUBJECT,getResources().getString(R.string.app_name));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID +".provider", file);

                    intent.putExtra(Intent.EXTRA_STREAM, photoURI);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.setType("image/jpeg");

                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(this, R.string.Error_Compartir, Toast.LENGTH_SHORT).show();
            }


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