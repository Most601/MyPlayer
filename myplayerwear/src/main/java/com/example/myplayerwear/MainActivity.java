package com.example.myplayerwear;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends WearableActivity {

    private TextView mTextViewLad;
    private TextView mTextViewLed;
    GPS gps ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewLad = (TextView) findViewById(R.id.text);
        mTextViewLed = (TextView) findViewById(R.id.text);

        gps = new GPS(this);
        // Enables Always-on
        setAmbientEnabled();
    }

    public void Gps_data(View view) {

        mTextViewLad.setText(gps.getLatitude());
        mTextViewLed.setText(gps.getLongitude());





    }

    public void blbl4blblb(View view) {
    }
}
