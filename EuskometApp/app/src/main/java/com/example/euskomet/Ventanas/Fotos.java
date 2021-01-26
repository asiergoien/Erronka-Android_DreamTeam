package com.example.euskomet.Ventanas;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.euskomet.CargarDatos;
import com.example.euskomet.Conexion;
import com.example.euskomet.EspacioNatural;
import com.example.euskomet.InsertDatos;
import com.example.euskomet.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Fotos extends AppCompatActivity {


    private ImageButton btnCamara;
    private ImageView imagen;
    public File foto;
    public Bitmap imgBitmap;
    private String fot_tabla;
    private int cod;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotos);

        btnCamara = (ImageButton)findViewById(R.id.btnCamara);
        imagen = (ImageView)findViewById(R.id.imagen);

        cod =  getIntent().getIntExtra("cod",-1);
        fot_tabla =  getIntent().getStringExtra("foto");

        try {
            CargarDatos clienteThread = new CargarDatos("SELECT * FROM "+fot_tabla+" WHERE "+(fot_tabla.equals("fotos_municipios") ? "cod_mun" : (fot_tabla.equals("fotos_esp_naturales") ? "cod_esp_natural" : null))+" = " + cod, 7);
            Thread thread = new Thread(clienteThread);
            thread.start();
            thread.join();

            ArrayList<Bitmap> arrayBitmap= new ArrayList<Bitmap>();
            ArrayList<Object> viejo = new ArrayList<Object>();

            viejo= clienteThread.getCliemteThread_ArrayList();
            for (Object ob : viejo){
                arrayBitmap.add((Bitmap) ob);
            }
            Log.i("FOTOS", "bitmap: " + arrayBitmap.size());
            if (arrayBitmap.size() != 0)
                imagen.setImageBitmap(arrayBitmap.get(0));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.O)

    public void TomarFoto(View view) throws IOException {
        Intent intento1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //El numero del requestCode es que que se ha podido sacar la foto
            startActivityForResult(intento1,101);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==101 ){
            if (resultCode == Activity.RESULT_OK && data!=null) {

                imgBitmap= (Bitmap) data.getExtras().get("data");
                imagen.setImageBitmap(imgBitmap);
                File outputDir = this.getCacheDir();
                try {
                    foto = File.createTempFile(new Date().toInstant().toString(), "jpeg", outputDir);
                    FileOutputStream fos = new FileOutputStream(foto);
                    imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    guardarBBDD();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
            Bundle extras = data.getExtras();
             imgBitmap = (Bitmap) extras.get("data");
            imagen.setImageBitmap(imgBitmap);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)

    public void guardarFoto(View view) {
        OutputStream fos = null;
        File file = null;
        //Este if es para saber que api tiene y dependiendo de cual se hara una manera o otra
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContentResolver resolver = getContentResolver();
            ContentValues values = new ContentValues();
            String Filename = new Date().toInstant().toString();
            Log.i("Filename", "guardarFoto: ---->" + Filename);
            values.put(MediaStore.Images.Media.DISPLAY_NAME, Filename);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/Euskomet");
            values.put(MediaStore.Images.Media.IS_PENDING, 1);
            Uri collecUri = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
            Uri imageUri = resolver.insert(collecUri, values);
            try {
                fos = resolver.openOutputStream(imageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            values.clear();
            values.put(MediaStore.Images.Media.IS_PENDING, 0);
            resolver.update(imageUri, values, null, null);
        } else {
            String imageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
            String Filename = new Date().toInstant().toString();
            file = new File(imageDir, Filename);
            try {
                fos = new FileOutputStream(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        boolean guardado = imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        if (guardado) {
            Toast.makeText(this, "Exito al guardar la imagen", Toast.LENGTH_SHORT).show();
        }
        if (fos != null) {
            try {
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if (file != null) {
            MediaScannerConnection.scanFile(this, new String[]{file.toString()}, null, null);
        }

    }

    public void guardarBBDD() {
      Bundle bundle;
        int tam = (int) foto.length();
        byte leido[] = new byte[tam];

        try {
            FileInputStream f = new FileInputStream(foto);
            f.read(leido);
            if (cod > 0){
                //Conexion con = new Conexion("INSERT INTO "+fot_tabla+" ("+(fot_tabla.equals("fotos_municipios") ? "cod_mun" : (fot_tabla.equals("fotos_esp_naturales") ? "cod_esp_natural" : null))+",tam,archivo) VALUES("+cod+","+ tam +",'" + leido + "')");

                Conexion con = new Conexion(cod,tam,f,fot_tabla,(fot_tabla.equals("fotos_municipios") ? "cod_mun" : (fot_tabla.equals("fotos_esp_naturales") ? "cod_esp_natural" : null)));
                Thread t = new Thread(con);
                t.start();
                t.join();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void volver(View view) {
        /*String  nombre = getIntent().getStringExtra("nombre");
        int cod_mun =  getIntent().getIntExtra("Cod_mun",-1);
        int cod_prov =  getIntent().getIntExtra("Cod_prov",-1);
        String desc = getIntent().getStringExtra("desc");
        Intent intent = new Intent(this,Mostrar_Informacion.class);
        intent.putExtra("Cod_mun", cod_mun);
        intent.putExtra("desc",desc);
        intent.putExtra("nombre",nombre);
        intent.putExtra("Cod_prov",cod_prov);
        startActivity(intent);*/

        finish();

    }


}