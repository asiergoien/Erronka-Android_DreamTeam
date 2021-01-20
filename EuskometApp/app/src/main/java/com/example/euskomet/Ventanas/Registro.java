package com.example.euskomet.Ventanas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.euskomet.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.SecretKey;

public class Registro extends AppCompatActivity {

    private EditText etNombre;
    private EditText etContraseña1;
    private EditText etContraseña2;
    private static Spinner spinPregunta;
    private EditText etRespuesta;

    // ENCRIPTAR CONTRASEÑA
    private static String claveUsuario = "euskomet";
    public static SecretKey claveSecreta;
    private static byte[] mensajeCodificado = null;
    public static byte[] iv = null;

    private static byte[] mensajeDecodificado = null;


    private static String contraseñaEncriptada = "";
    private static String ivEncriptado = "";

    ImageView o_ImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //Crear las variables
        etNombre=(EditText)findViewById(R.id.etNombre);
        etContraseña1=(EditText)findViewById(R.id.etContraseña1);
        etContraseña2=(EditText)findViewById(R.id.etContraseña2);
        spinPregunta=(Spinner)findViewById(R.id.spinPregunta);
       etRespuesta=(EditText)findViewById(R.id.etRespuesta);

        o_ImageView = findViewById(R.id.imageView4);
        Animation oAnimacion = AnimationUtils.loadAnimation(getApplicationContext(),  R.anim.agrandar_logo);
        o_ImageView.startAnimation(oAnimacion);

    }

    public void registrar(View view) {

        String usuario = etNombre.getText().toString();
        String contraseña1 = etContraseña1.getText().toString();
        String contraseña2 = etContraseña2.getText().toString();

        String pregunta = spinPregunta.getSelectedItem().toString();
        String respuesta = etRespuesta.getText().toString();

        MainActivity.preferencias = getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=MainActivity.preferencias.edit();

        if ( contraseña1.equals(contraseña2) ) {
            // Encriptar contraseña
//            cifrar(contraseña1);

            String cifradoStr = "";
            for (byte b : cifrar(contraseña1)) {
                cifradoStr += b;
            }
            //Registrar usuario
            editor.putString(usuario + "_usuario", usuario).commit();
            editor.putString(usuario + "_contraseña", cifradoStr).commit();
            editor.putString(usuario + "_pregunta", pregunta).commit();
            editor.putString(usuario + "_respuesta", respuesta).commit();
            finish();
            Toast.makeText(this,"Usuario registrado correctamente",Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this,"Las contraseñas no coinciden",Toast.LENGTH_LONG).show();
            etNombre.setText("");
            etContraseña1.setText("");
            etContraseña2.setText("");
            spinPregunta.setSelection(0);
            etRespuesta.setText("");
        }
    }

    public byte[] cifrar (String texto) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte dataBytes[] = texto.getBytes();
        md.update(dataBytes);
        byte resumen[] = md.digest();

//		System.out.println("desde la funcion --> " + resumen.toString());

        return resumen;
    }

    public void volver(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}