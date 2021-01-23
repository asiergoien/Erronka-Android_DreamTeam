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

import com.example.euskomet.Conexion;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotos);

        btnCamara = (ImageButton)findViewById(R.id.btnCamara);
        imagen = (ImageView)findViewById(R.id.imagen);


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

        int tam = (int) foto.length();
        byte leido[] = new byte[tam];
        try {
            FileInputStream f = new FileInputStream(foto);
            f.read(leido);
            Bundle extra = getIntent().getExtras();
            int cod = 0;
            if (extra != null) {
                cod = extra.getInt("cod");
            }

            Conexion con = new Conexion(cod, tam, leido);
            Thread t = new Thread(con);
            t.start();
            t.join();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}