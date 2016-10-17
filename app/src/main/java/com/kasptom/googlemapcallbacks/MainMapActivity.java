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


public class MainMapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.CancelableCallback
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

        setCallbacks(map);
    }

    private void setCallbacks(GoogleMap map)
    {
        if (map == null)
        {
            Timber.d("Map is null. No callbacks set");
            return;
        }
        map.setOnCameraMoveStartedListener(

                movementReason -> {
                    Timber.d("Camera move detected");
                    logMovementReason(movementReason);
                    map.setOnMapLoadedCallback(
                            () -> Timber.d("Map loaded callback after move"));

                }
        );
        map.setOnMapLoadedCallback(() -> Timber.d("onMapLoadedCallback that has to be done"));
    }

    private void logMovementReason(int movementReason)
    {
        String reason = "unknown";
        if (movementReason == GoogleMap.OnCameraMoveStartedListener.REASON_API_ANIMATION)
        {
            reason = "animation";
        }
        else if (movementReason == GoogleMap.OnCameraMoveStartedListener.REASON_DEVELOPER_ANIMATION)
        {
            reason = "developer animation";
        }
        else if (movementReason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE)
        {
            reason = "gesture";
        }

        Timber.d("Movement reason: %s", reason);
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


    //CancellableCallback implementation
    @Override
    public void onFinish()
    {
        Timber.d("onFinish");
    }

    @Override
    public void onCancel()
    {
        Timber.d("onCancel");
    }
    //end of interface implementation
}
