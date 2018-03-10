package com.example.myplayerwear;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by Most601 on 27/02/2018.
 */

public class stepCounter implements SensorEventListener {

    private SensorManager sMgr;
    private Sensor stepCounterSensor;
    private String msg;

    public stepCounter(Context context){
        sMgr = (SensorManager)context.getSystemService(SENSOR_SERVICE);
        stepCounterSensor = sMgr.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_HEART_RATE) {
            msg = " Value Step Counter : " + (int)event.values[0];
        }
        DataShow.print("SC" , event);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void startMeasurement() {
        if (stepCounterSensor != null) {
            sMgr.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Log.d("T0AG", "No Step Counter Sensor found");
        }
    }

    public void stopMeasurement() {
        if (sMgr != null) {
            sMgr.unregisterListener(this);
        }
    }

}
