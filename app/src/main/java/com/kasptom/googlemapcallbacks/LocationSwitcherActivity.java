package com.kasptom.googlemapcallbacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class LocationSwitcherActivity extends AppCompatActivity
{
    public static final String INTENT_KEY_LAT_LONG = "com.kasptom.googlemapcallbacks.LocationSwitcherActivity lat long";
    private EditText longitude;
    private EditText latitude;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_switcher);
        longitude = (EditText) findViewById(R.id.edit_text_longitude);
        latitude = (EditText) findViewById(R.id.edit_text_latitude);
    }


    public void setLatitudeAndLongitude(View view)
    {
        Intent data = new Intent();
        data.putExtra(INTENT_KEY_LAT_LONG, getLatLong());
        setResult(RESULT_OK, data);
        finish();
    }

    public void closeActivity(View view)
    {
        finish();
    }

    private double[] getLatLong()
    {
        double longitude = Double.parseDouble(this.longitude.getText().toString());
        double latitude = Double.parseDouble(this.latitude.getText().toString());
        return new double[]{latitude, longitude};
    }
}
