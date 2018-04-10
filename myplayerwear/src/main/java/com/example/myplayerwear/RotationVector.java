package com.example.myplayerwear;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by Most601 on 04/04/2018.
 */

public class RotationVector implements SensorEventListener {

    private static final String TAG = "RotationVector";

    private Sensor mySensor;
    private SensorManager SM;
    private SendToPhone STP ;

    public RotationVector(Context context) {
        SM = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        mySensor = SM.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        STP = SendToPhone.getInstance(context);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //print on datashow
        try {
            DataShow.print("RotationVector",event);
        }catch (Exception e){
        }

        STP.sendSensorData(
                event.sensor.getStringType() ,
                event.sensor.getType(),
                event.accuracy,
                event.timestamp,
                event.values);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void startMeasurement(){
        // Register sensor Listener
        if (SM != null) {
            if (mySensor != null) {
                SM.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);
            } else {
                Log.w(TAG, "No RotationVector found");
            }
        }
    }

    public void stopMeasurement() {
        // unRegister sensor Listener
        if (SM != null) {
            SM.unregisterListener(this);
        }
    }

}
