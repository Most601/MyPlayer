package metaextract.nkm.com.myplayer;


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
    private double x, y, z;
    private Sensor mySensor;
    private SensorManager SM;
    private ArrayList<Double> acc = new ArrayList<Double>();

        public Accelerometer(Context context) {
            SM = (SensorManager) context.getSystemService(SENSOR_SERVICE);
            // Accelerometer Sensor
            mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            // Register sensor Listener
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            x = event.values[0];
            z = event.values[1];
            y = event.values[2];
            DataShow.print("AC",event);
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


