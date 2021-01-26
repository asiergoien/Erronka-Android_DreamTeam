package com.example.euskomet.Ventanas;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.euskomet.CargarDatos;
import com.example.euskomet.R;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.SecretKey;

public class Login extends AppCompatActivity {

    private EditText editTextUsuario,editTextContraseña;
    ImageView o_ImageView;


    // ENCRIPTAR CONTRASEÑA
    private static String claveUsuario = "euskomet";
    private static SecretKey claveSecreta;
    private static byte[] mensajeCodificado = null;
    private static byte[] iv = null;

    private static byte[] mensajeDecodificado = null;

    private static String contraseñaEncriptada = "";
    private static String contraguardada = "";

    private static  ArrayList<Usuario> usuarioArrayList =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsuario=(EditText) findViewById(R.id.editTextUsuario);
        editTextContraseña=(EditText) findViewById(R.id.editTextContraseña);

        o_ImageView = findViewById(R.id.imageView2);
        Animation oAnimacion = AnimationUtils.loadAnimation(getApplicationContext(),  R.anim.girar_logo);
        o_ImageView.startAnimation(oAnimacion);
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            usuarioArrayList= new  ArrayList<Usuario>();
            usuarioArrayList=conectarUsuario();
            Log.i("prueba", "onStart: -------------------------------------------------- "+usuarioArrayList.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void loguear(View v) throws UnsupportedEncodingException {
        String usuario=this.editTextUsuario.getText().toString();
        String contraseña=this.editTextContraseña.getText().toString();
        Usuario us = new Usuario();
        if (!usuario.isEmpty() || !contraseña.isEmpty()) { //comprueba si los campos estan vacios

             us= MirarUsuario(usuario);
            if (us != null) { // comprueba si el usuario existe

                contraguardada =  us.getContra();
                Log.i("tag", "   ----------------------------------------------------    contra guardada : " + contraguardada);

                String cifradoStr = "";
                for (byte b : cifrar(contraseña)) {
                    cifradoStr += b;
                }

                Log.i("tag", "   ----------------------------------------------------    contra encriptada : " +  contraseñaEncriptada);

                if (cifradoStr.equals(contraguardada)) { // si la contraseña guardada coincide con la contraseña introducida
//                if (contraseña.equals(contraguardada)) { // si la contraseña guardada coincide con la contraseña introducida
                    Toast.makeText(this, "Te has logueado", Toast.LENGTH_LONG).show();

                    //  USUARIO LOGUEADO    -   CAMBIO DE PANTALLA
                    cambioPantalla_MenuPrincipal(us);

                } else {
                    Toast.makeText(this, "La contraseña introducida no es correcta", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "No existe dicho usuario en el sistema", Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(this, "Introduzca usuario y contraseña", Toast.LENGTH_LONG).show();
        }

    }
    public Usuario MirarUsuario(String Usuario){

        for (Usuario us1 : usuarioArrayList){
            if (us1.getNombre().equals(Usuario)){
                    return us1;
            }
        }
        return null;
    }
    public void recuperarContraeña(View view) {

        String usuario=this.editTextUsuario.getText().toString();

        if (!usuario.isEmpty()) { //comprueba si el usuario está vacío

            RecuperarPswd.usuario = usuario; //enviar usuario a recuperar contraseña
            Usuario us = new Usuario();
            us= MirarUsuario(usuario);
            if (us != null) { // comprueba si el usuario existe
                String pregunta=us.getPregunta();
                Intent intent = new Intent(this,RecuperarPswd.class);
                intent.putExtra("nombre",us.getNombre());
                intent.putExtra("contra",us.getContra());
                startActivity(intent);
            }else{
                Toast.makeText(this, "No existe dicho usuario en el sistema", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, R.string.contraseñaOlvidada_toast, Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("LongLogTag")
    public void cambioPantalla_MenuPrincipal(Usuario us) {
        Intent intent = new Intent(this, MenuPrincipal.class);
        int cod = us.getCod_user();
        intent.putExtra("cod_usuario", cod);
        startActivity(intent);
    }

    public void volver(View view) {
        finish();
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


}