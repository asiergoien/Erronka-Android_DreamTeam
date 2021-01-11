package com.example.euskomet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private EditText editTextUsuario,editTextContraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsuario=(EditText) findViewById(R.id.editTextUsuario);
        editTextContraseña=(EditText) findViewById(R.id.editTextContraseña);
    }

    public void loguear(View v) {
        String usuario=this.editTextUsuario.getText().toString();
        String contraseña=this.editTextContraseña.getText().toString();

        if (!usuario.isEmpty() || !contraseña.isEmpty()) { //comprueba si los campos estan vacios
            if ( MainActivity.preferencias.contains(usuario)) { // comprueba si el usuario existe
                String contraguardada =  MainActivity.preferencias.getString(usuario, ""); // guardar en la variable la contraseña asociada al usuario

                if (contraseña.equals(contraguardada)) { // si la contraseña guardada coincide con la contraseña introducida
                    Toast.makeText(this, "Te has logueado", Toast.LENGTH_LONG).show();

                    //  USUARIO LOGUEADO    -   CAMBIO DE PANTALLA

                } else {
                    Toast.makeText(this, "La contraseña introducida no es correcta", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "no existe dicho usuario en el sistema", Toast.LENGTH_LONG).show();
            }
        }else {
            Toast.makeText(this, "Introduzca usuario y contraseña", Toast.LENGTH_LONG).show();
        }

    }

    public void recuperarContraeña(View view) {
        Toast.makeText(this, R.string.contraseñaOlvidada_toast, Toast.LENGTH_LONG).show();
    }

    public void cambioPantallaMain(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}