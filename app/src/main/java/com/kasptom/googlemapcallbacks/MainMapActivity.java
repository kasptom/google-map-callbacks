package com.kasptom.googlemapcallbacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import timber.log.Timber;


public class MainMapActivity extends AppCompatActivity implements OnMapReadyCallback
{
    public static final int LAT_LONG_REQUEST_CODE = 1234;
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
        Timber.d("onMapReady");
        map = googleMap;
        LatLng krakow = new LatLng(50.06465, 19.94497);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(krakow, 13.0f));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null)
        {
            return;
        }

        double latLong[] = data.getDoubleArrayExtra(LocationSwitcherActivity.INTENT_KEY_LAT_LONG);
        LatLng latLng = new LatLng(latLong[0], latLong[1]);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13.0f));
    }

    public void openLocationSwitcherActivity(View view)
    {
        Intent intent = new Intent(this, LocationSwitcherActivity.class);
        startActivityForResult(intent, LAT_LONG_REQUEST_CODE);
    }
}
