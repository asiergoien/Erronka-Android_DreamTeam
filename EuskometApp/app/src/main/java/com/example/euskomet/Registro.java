package com.example.euskomet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class Registro extends AppCompatActivity {

    private EditText etNombre;
    private EditText etContraseña1;
    private EditText etContraseña2;
    private static Spinner spinPregunta;
    private EditText etRespuesta;

    // ENCRIPTAR CONTRASEÑA
    private static String claveUsuario = "euskomet";
    private static SecretKey claveSecreta;
    private static byte[] mensajeCodificado = null;
    private static byte[] iv = null;

    private static byte[] mensajeDecodificado = null;


    private static String contraseñaEncriptada = "";

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

    }

    public void registrar(View view) {

        String usuario = etNombre.getText().toString();
        String contraseña1 = etContraseña1.getText().toString();
        String contraseña2 = etContraseña2.getText().toString();

        String pregunta = spinPregunta.getSelectedItem().toString();
        String respuesta = etRespuesta.getText().toString();

        MainActivity.preferencias = getSharedPreferences("usuarios", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=MainActivity.preferencias.edit();

//        encriptar(contraseña1);

        if ( contraseña1.equals(contraseña2) ) {
            //Registrar usuario
            editor.putString(usuario + "_usuario", usuario).commit();
            editor.putString(usuario + "_contraseña", contraseña1).commit();
//            editor.putString(usuario + "_contraseña", contraseñaEncriptada).commit();
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

    public static void encriptar(String cadena) {
        SecretKeyFactory skf;

        try {

            skf = SecretKeyFactory.getInstance("DES");
            DESKeySpec clavEspec;
            clavEspec = new DESKeySpec(claveUsuario.getBytes());
            claveSecreta = skf.generateSecret(clavEspec);
            Cipher cipher;
            cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, claveSecreta);
            mensajeCodificado = cipher.doFinal(cadena.getBytes());
            iv = cipher.getIV();

        } catch (NoSuchAlgorithmException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (InvalidKeySpecException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (NoSuchPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

//        System.out.println("Mensaje [ encriptar() ] --> " + cadena);

        String cifradoStr = "";
        for (byte b : mensajeCodificado) {
            cifradoStr += b;
        }

//        System.out.println("Mensaje encriptado [ encriptar() ] --> " + cifradoStr);

        contraseñaEncriptada = cifradoStr;
    }

    public static void desencriptar(SecretKey claveSecreta) {

        Cipher cipher;
        try {
            cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            IvParameterSpec dps = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, claveSecreta, dps);
            mensajeDecodificado = cipher.doFinal(mensajeCodificado);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String cifradoStr = "";
        for (byte b : mensajeCodificado) {
            cifradoStr += b;
        }

//        System.out.println("Mensaje encriptado [ desencriptar() ] --> " + cifradoStr);



//        System.out.println("Mensaje desencriptado [ desencriptar() ] --> " + new String(mensajeDecodificado));

    }
}