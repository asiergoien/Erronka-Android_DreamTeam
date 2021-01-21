package com.example.euskomet.Ventanas;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.euskomet.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class Fotos extends AppCompatActivity {

    private ImageButton btnCamara;
    private ImageView imagen;
    public File foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotos);

        btnCamara = (ImageButton)findViewById(R.id.btnCamara);
        imagen = (ImageView)findViewById(R.id.imagen);


    }
    @RequiresApi(api = Build.VERSION_CODES.O)

    public void TomarFoto(View view) throws IOException {
        String url  = new Date().toInstant().toString();
        Intent intento1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
         foto = File.createTempFile(url,".jpg");
        Log.i("URLFOTO-nueva", "TomarFoto: "+ foto.getAbsoluteFile().toString());

//        File foto = new File(getExternalFilesDir(null),url);
//        Log.i("URLFOTO", "TomarFoto: "+getExternalFilesDir(null)+url);
        startActivityForResult(intento1,1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imgBitmap = (Bitmap) extras.get("data");
            imagen.setImageBitmap(imgBitmap);
        }
    }
}