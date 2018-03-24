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

    private static final String TAG = "stepCounter";


    private SensorManager sMgr;
    private Sensor stepCounterSensor;
    private String msg;
    private SendToPhone STP ;

    public stepCounter(Context context){
        sMgr = (SensorManager)context.getSystemService(SENSOR_SERVICE);
        stepCounterSensor = sMgr.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        STP = SendToPhone.getInstance(context);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            msg = " Value Step Counter : " + (int)event.values[0];
            //////////////////////////
            STP.sendSensorData(event.sensor.getStringType() , event.sensor.getType(), event.accuracy, event.timestamp, event.values);
            //////////////////////////
            try {
                DataShow.print("SC" , event);
            }catch (Exception e){
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void startMeasurement() {
        if (stepCounterSensor != null) {
            sMgr.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Log.d(TAG, "No Step Counter Sensor found");
        }
    }

    public void stopMeasurement() {
        if (sMgr != null) {
            sMgr.unregisterListener(this);
        }
    }

}
