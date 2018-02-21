package metaextract.nkm.com.myplayer;

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
    private String msg;
    private SensorManager sMgr;
    private Sensor battito = null;

    public HeartrRate(Context context){


        sMgr = (SensorManager)context.getSystemService(SENSOR_SERVICE);
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
