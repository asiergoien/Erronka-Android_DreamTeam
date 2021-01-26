package com.example.euskomet.Ventanas;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
        String nombre = getIntent().getStringExtra("nombre");

        if (latitud>0 && longitud>0){
            GoogleMap mapa = googleMap;
            LatLng espacio_natural = new LatLng(latitud, -longitud);
            mapa.addMarker(new MarkerOptions().position(espacio_natural).title(nombre));
            mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(espacio_natural,18),5000,null);
        }
    }



    public void volver(View view) {
        finish();
    }
}

