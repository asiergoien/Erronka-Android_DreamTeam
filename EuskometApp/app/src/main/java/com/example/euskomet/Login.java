package com.example.euskomet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class Login extends AppCompatActivity {

    private EditText editTextUsuario,editTextContraseña;


    // ENCRIPTAR CONTRASEÑA
    private static String claveUsuario = "euskomet";
    private static SecretKey claveSecreta;
    private static byte[] mensajeCodificado = null;
    private static byte[] iv = null;

    private static byte[] mensajeDecodificado = null;

    private static String contraseñaEncriptada = "";
    private static String contraguardada = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsuario=(EditText) findViewById(R.id.editTextUsuario);
        editTextContraseña=(EditText) findViewById(R.id.editTextContraseña);
    }

    public void loguear(View v) throws UnsupportedEncodingException {
        String usuario=this.editTextUsuario.getText().toString();
        String contraseña=this.editTextContraseña.getText().toString();

        if (!usuario.isEmpty() || !contraseña.isEmpty()) { //comprueba si los campos estan vacios
            if ( MainActivity.preferencias.contains(usuario+"_usuario")) { // comprueba si el usuario existe
                contraguardada =  MainActivity.preferencias.getString(usuario+"_contraseña", ""); // guardar en la variable la contraseña asociada al usuario

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

    public void recuperarContraeña(View view) {

        String usuario=this.editTextUsuario.getText().toString();

        if (!usuario.isEmpty()) { //comprueba si el usuario está vacío

            RecuperarPswd.usuario = usuario; //enviar usuario a recuperar contraseña

            MainActivity.preferencias = getSharedPreferences("usuarios", Context.MODE_PRIVATE);
            if (MainActivity.preferencias.contains(usuario+"_usuario")) { // comprueba si el usuario existe
                RecuperarPswd.pregunta =  MainActivity.preferencias.getString(usuario+"_pregunta", "");
                Intent intent = new Intent(this,RecuperarPswd.class);
                startActivity(intent);
            }else{
                Toast.makeText(this, "No existe dicho usuario en el sistema", Toast.LENGTH_LONG).show();
            }

        }else{
            Toast.makeText(this, R.string.contraseñaOlvidada_toast, Toast.LENGTH_LONG).show();
        }
    }

    public void cambioPantallaMain(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
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

}