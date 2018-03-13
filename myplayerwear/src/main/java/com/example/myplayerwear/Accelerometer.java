package com.example.myplayerwear;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import java.util.ArrayList;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by Most601 on 16/12/2017.
 */

public class Accelerometer implements SensorEventListener{

    private ArrayList<Double> acc = new ArrayList<Double>();
    private double x, y, z;
    private Sensor mySensor;
    private SensorManager SM;
    private SendToPhone STP ;

        public Accelerometer(Context context) {
            SM = (SensorManager) context.getSystemService(SENSOR_SERVICE);
            // Accelerometer Sensor
            mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

            STP = SendToPhone.getInstance(context);
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            x = event.values[0];
            z = event.values[1];
            y = event.values[2];
            //print on datashow
            DataShow.print("AC",event);
            //////////////////////////
//            STP.sendSensorData(event.sensor.getStringType() , event.sensor.getType(), event.accuracy, event.timestamp, event.values);
            //////////////////////////
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        public void startMeasurement(){
            // Register sensor Listener
            SM.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        public void stopMeasurement() {
            // unRegister sensor Listener
            if (SM != null) {
                SM.unregisterListener(this);
            }
        }

        public ArrayList<Double> getAccelerometerData (){
            acc.add(x);
            acc.add(y);
            acc.add(z);
            return acc;
        }
    }


