package com.example.euskomet.Ventanas;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.euskomet.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Mapa extends FragmentActivity implements OnMapReadyCallback   {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        // Obtenemos el mapa de forma asíncrona (notificará cuando esté listo)
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Double latitud = getIntent().getDoubleExtra("la",0);
        Double longitud = getIntent().getDoubleExtra("lo",0);
        Log.i("Mapas", "----------[latitud]: "+latitud);
        Log.i("Mapas", "----------[longitud]: "+longitud);

        String nombre = getIntent().getStringExtra("nombre");
        Log.i("Mapas", "----------[nombre]: "+nombre);

        if (latitud!=0 && longitud!=0){
            GoogleMap mapa = googleMap;
            LatLng espacio_natural = new LatLng(latitud, longitud);
            mapa.addMarker(new MarkerOptions().position(espacio_natural).title(nombre));
           mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(espacio_natural,18),5000,null);
           // mapa.moveCamera(CameraUpdateFactory.newLatLng(espacio_natural));

//                GoogleMap mapa = googleMap;
//                LatLng oElorrieta = new LatLng(43.283531, -2.965031); // Elorriet 43.283531, -2.965031
//                mapa.addMarker(new MarkerOptions().position(oElorrieta).title("Marker Elorrieta"));
//                        mapa.moveCamera(CameraUpdateFactory.newLatLng(oElorrieta));
       }else{
            Toast.makeText(this, "No hay Coordenadas de "+nombre, Toast.LENGTH_LONG).show();
        }
    }



    public void volver(View view) {
        finish();
    }
}

