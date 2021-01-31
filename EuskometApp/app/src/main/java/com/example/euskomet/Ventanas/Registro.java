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

import com.example.euskomet.CargarDatos;
import com.example.euskomet.InsertDatos;
import com.example.euskomet.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

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

    public void registrar(View view) throws InterruptedException {



        String usuario = etNombre.getText().toString();
        String contraseña1 = etContraseña1.getText().toString();
        String contraseña2 = etContraseña2.getText().toString();

        String pregunta = spinPregunta.getSelectedItem().toString();
        String respuesta = etRespuesta.getText().toString();


        Usuario us = new Usuario();
        us= MirarUsuario(usuario);


            if ( contraseña1.equals(contraseña2) ) {
                // Encriptar contraseña
                if (us == null){
                String cifradoStr = "";

                for (byte b : cifrar(contraseña1)) {
                   cifradoStr += b;
                }
                //Registrar usuario
                String sql= "insert into usuarios  (nombre,contra,pregunta,respuesta) values('"+usuario+"','"+cifradoStr+"','"+pregunta+"','"+respuesta+"')";
                InsertDatos in1 = new InsertDatos(sql);
                    Thread thread = new Thread(in1);
                    thread.start();
                    thread.join(); // Esperar respuesta del servidor...
                if (in1.isBol()==true){
                    Toast.makeText(this,R.string.usu_res,Toast.LENGTH_LONG).show();
                    etNombre.setText("");
                    etContraseña1.setText("");
                    etContraseña2.setText("");
                    spinPregunta.setSelection(0);
                    etRespuesta.setText("");
                    Intent intent = new Intent(this,MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(this,R.string.error_base,Toast.LENGTH_LONG).show();
                }
            }
        } else {
            Toast.makeText(this,R.string.contra_no,Toast.LENGTH_LONG).show();
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
        finish();
    }
    private ArrayList<Usuario> conectarUsuario() throws InterruptedException {
        CargarDatos clienteThread = new CargarDatos("SELECT * FROM usuarios", 5);
        Thread thread = new Thread(clienteThread);
        thread.start();
        thread.join(); // Esperar respuesta del servidor...

        ArrayList<Usuario> ArrayProv = new ArrayList<Usuario>();
        ArrayList<Object> viejo = new ArrayList<Object>();

        viejo= clienteThread.getCliemteThread_ArrayList();

        for (Object ob : viejo){
            ArrayProv.add((Usuario)ob);
        }
        return ArrayProv;
    }

    public Usuario MirarUsuario(String Usuario) throws InterruptedException {
    ArrayList<Usuario> usuarioArrayList = new ArrayList<Usuario>();
        usuarioArrayList= (ArrayList<com.example.euskomet.Ventanas.Usuario>) conectarUsuario().clone();

        for (Usuario us1 : usuarioArrayList){
            if (us1.getNombre().equals(Usuario)){
                return us1;
            }
        }
        return null;
    }

}