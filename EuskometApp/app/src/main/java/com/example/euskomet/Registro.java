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

public class Registro extends AppCompatActivity {

    private EditText etNombre;
    private EditText etContraseña1;
    private EditText etContraseña2;
    public static Spinner spinPregunta;
    private EditText etRespuesta;


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

        if ( contraseña1.equals(contraseña2) ) {
            //Registrar usuario
            editor.putString(usuario + "_contraseña", usuario).commit();
            editor.putString(usuario + "_", contraseña1).commit();
            editor.putString(usuario + "_", pregunta).commit();
            editor.putString(usuario + "_", respuesta).commit();
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
}