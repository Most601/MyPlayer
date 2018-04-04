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

    private static final String TAG = "HeartrRate";

    private String msg;
    private SensorManager SM;
    private Sensor mHeartrateSensor = null;
    private ScheduledExecutorService mScheduler;
    private SendToPhone STP ;

    public HeartrRate(Context context){
        SM = (SensorManager)context.getSystemService(SENSOR_SERVICE);
        mHeartrateSensor = SM.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        STP = SendToPhone.getInstance(context);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (SM != null) {
            if (mHeartrateSensor != null) {
                if (event.sensor.getType() == Sensor.TYPE_HEART_RATE) {
                    msg = " Value sensor: " + (int) event.values[0];
                    STP.sendSensorData(
                            event.sensor.getStringType(),
                            event.sensor.getType(),
                            event.accuracy,
                            event.timestamp,
                            event.values);
                }
                try {
                    DataShow.print("H", event);
                } catch (Exception e) {
                }
            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void startMeasurement(){
        if (mHeartrateSensor != null) {
            SM.registerListener(this, mHeartrateSensor,SensorManager.SENSOR_DELAY_NORMAL);
//            final int measurementDuration   = 30;   // Seconds
//            final int measurementBreak      = 15;    // Seconds
//            mScheduler = Executors.newScheduledThreadPool(1);
//            mScheduler.scheduleAtFixedRate(
//                    new Runnable() {
//                        @Override
//                        public void run() {
//                            Log.d(TAG, "register Heartrate Sensor");
//                            SM.registerListener(HeartrRate.this,
//                                    mHeartrateSensor,
//                                    SensorManager.SENSOR_DELAY_FASTEST);
//
//                            try {
//                                Thread.sleep(measurementDuration * 1000);
//                            } catch (InterruptedException e) {
//                                Log.e(TAG, "Interrupted while waitting to unregister Heartrate Sensor");
//                            }
//
//                            Log.d(TAG, "unregister Heartrate Sensor");
//                            SM.unregisterListener(HeartrRate.this, mHeartrateSensor);
//
//                        }
//                    }, 3, measurementDuration + measurementBreak, TimeUnit.SECONDS);

        } else {
            Log.d(TAG, "No Heartrate Sensor found");
        }


    }

    public void stopMeasurement() {
        if (SM != null) {
            SM.unregisterListener(this);
        }
        if (mScheduler != null && !mScheduler.isTerminated()) {
            mScheduler.shutdown();
        }
    }


}
