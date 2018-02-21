package com.example.myplayerwear;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;

public class DataShow extends WearableActivity {// implements SensorEventListener{
  //  private Sensor mySensor;
  //  private SensorManager SM;
   // private TextView xText, yText, zText;
//----------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_show);




//
//        SM = (SensorManager)getSystemService(SENSOR_SERVICE);
//        // Accelerometer Sensor
//        mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        // Register sensor Listener
//        SM.registerListener((SensorEventListener) this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);
//        xText = (TextView)findViewById(R.id.textView5);
//        yText = (TextView)findViewById(R.id.textView6);
//        zText = (TextView)findViewById(R.id.textView7);
//
//
//
    }
//
//    @Override
//    public void onSensorChanged(SensorEvent event) {
//        xText.setText("X: " + (int)event.values[0]);
//        yText.setText("Y: " + (int)event.values[1]);
//        zText.setText("Z: " + (int)event.values[2]);
//    }
//
//    @Override
//    public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
//    }
}
