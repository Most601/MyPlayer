package com.example.myplayerwear;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by Most601 on 15/12/2017.
 */

public class HeartrRate implements SensorEventListener {


    String msg;


    public HeartrRate(Context context){

        SensorManager sMgr;
        sMgr = (SensorManager)context.getSystemService(SENSOR_SERVICE);
        Sensor battito = null;
        battito = sMgr.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        sMgr.registerListener(this, battito,SensorManager.SENSOR_DELAY_NORMAL);



    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_HEART_RATE) {
            msg = " Value sensor: " + (int)event.values[0];

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        System.out.println("onAccuracyChanged - accuracy: " + accuracy);
    }

    public String getH() {
        return msg;

    }

}
