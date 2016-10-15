package com.kasptom.googlemapcallbacks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import timber.log.Timber;


public class MainMapActivity extends AppCompatActivity implements OnMapReadyCallback
{
    GoogleMap map;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_map);


        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        supportMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        LatLng krakow = new LatLng(50.06465, 19.94497);
        map = googleMap;
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(krakow, 13.0f));
        Timber.d("onMapReady");

        map.setOnMapLoadedCallback(() -> Timber.d("onMapLoadedCallback"));
    }
}
