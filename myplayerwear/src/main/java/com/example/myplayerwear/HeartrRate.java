package com.example.myplayerwear;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by Most601 on 15/12/2017.
 */

public class HeartrRate implements SensorEventListener {

    private String msg;
    private SensorManager sMgr;
    private Sensor mHeartrateSensor = null;
    private ScheduledExecutorService mScheduler;

    public HeartrRate(Context context){
        sMgr = (SensorManager)context.getSystemService(SENSOR_SERVICE);
        mHeartrateSensor = sMgr.getDefaultSensor(Sensor.TYPE_HEART_RATE);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_HEART_RATE) {
            msg = " Value sensor: " + (int)event.values[0];
        }
        DataShow.print("H",event);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        System.out.println("onAccuracyChanged - accuracy: " + accuracy);
    }

    public String getH() {
        return msg;
    }

    public void startMeasurement(){
        sMgr.registerListener(this, mHeartrateSensor,SensorManager.SENSOR_DELAY_NORMAL);

        if (mHeartrateSensor != null) {
            final int measurementDuration   = 30;   // Seconds
            final int measurementBreak      = 15;    // Seconds
            mScheduler = Executors.newScheduledThreadPool(1);
            mScheduler.scheduleAtFixedRate(
                    new Runnable() {
                        @Override
                        public void run() {
                            Log.d("", "register Heartrate Sensor");
                            sMgr.registerListener(HeartrRate.this,
                                    mHeartrateSensor,
                                    SensorManager.SENSOR_DELAY_FASTEST);

                            try {
                                Thread.sleep(measurementDuration * 1000);
                            } catch (InterruptedException e) {
                                Log.e("", "Interrupted while waitting to unregister Heartrate Sensor");
                            }

                            Log.d("", "unregister Heartrate Sensor");
                            sMgr.unregisterListener(HeartrRate.this, mHeartrateSensor);

                        }
                    }, 3, measurementDuration + measurementBreak, TimeUnit.SECONDS);

        } else {
            Log.d("", "No Heartrate Sensor found");
        }


    }

    public void stopMeasurement() {
        if (sMgr != null) {
            sMgr.unregisterListener(this);
        }
        if (mScheduler != null && !mScheduler.isTerminated()) {
            mScheduler.shutdown();
        }
    }


}
