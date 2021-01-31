package com.example.euskomet.Ventanas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.euskomet.InsertDatos;
import com.example.euskomet.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RecuperarPswd extends AppCompatActivity {

    public static String usuario,pregunta,respuestaGuardada;

    private EditText editTextRespuesta,etContraseña,etContraseña2;
    private TextView txtPregunta;
    private Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_pswd);

        editTextRespuesta=(EditText) findViewById(R.id.editTextRespuesta);
        txtPregunta=(TextView)findViewById(R.id.txtPregunta);
        txtPregunta.setText(pregunta);

        etContraseña=(EditText) findViewById(R.id.etContraseña);
        etContraseña.setVisibility(View.INVISIBLE);
        etContraseña2=(EditText) findViewById(R.id.etContraseña2);
        etContraseña2.setVisibility(View.INVISIBLE);
        btnRegistrar=(Button) findViewById(R.id.btnRegistrar);
        btnRegistrar.setVisibility(View.INVISIBLE);


    }

    public void recuperarPswd (View v){

        String respuesta=this.editTextRespuesta.getText().toString();

        String Pregunta_us= getIntent().getStringExtra("pregunta");
        if (respuesta.equals(Pregunta_us)) { // comprueba si la respuesta es la misma
            etContraseña.setVisibility(View.VISIBLE);
            etContraseña2.setVisibility(View.VISIBLE);
            btnRegistrar.setVisibility(View.VISIBLE);
            Toast.makeText(this, R.string.res_co, Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, R.string.res_in, Toast.LENGTH_LONG).show();
        }

    }

    public void guardarPswd (View v){
        String nombre= getIntent().getStringExtra("nombre");

        String contraseña1 = etContraseña.getText().toString();
        String contraseña2 = etContraseña2.getText().toString();

        if ( contraseña1.equals(contraseña2) ) {

            String cifradoStr = "";
            for (byte b : cifrar(contraseña1)) {
                cifradoStr += b;
            }

            //Cambiar contraseña
            String sql= "UPDATE usuarios set contra= '"+cifradoStr+"' where nombre='"+nombre+"'";
            InsertDatos in1 = new InsertDatos(sql);
            Toast.makeText(this,R.string.contra_cam,Toast.LENGTH_LONG).show();


        } else {
            Toast.makeText(this,R.string.contra_no,Toast.LENGTH_LONG).show();
            etContraseña.setText("");
            etContraseña2.setText("");
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


        return resumen;
    }

}