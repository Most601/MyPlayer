package metaextract.nkm.com.myplayer;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.location.LocationListener;
import android.location.LocationManager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DataShow extends AppCompatActivity implements SensorEventListener{



    private Button b;
    private TextView textgps;
    private TextView textH;
    private LocationManager locationManager;
    private LocationListener listener;
    private GPS gps ;
    private HeartrRate heartrRate;


    private Accelerometer accelerometer;
    private Sensor mySensor;
    private SensorManager SM;
    private TextView xText, yText, zText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_show_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        textgps = (TextView) findViewById(R.id.textgps);
        gps = new GPS(this);

        textH = (TextView) findViewById(R.id.textH);
        heartrRate = new HeartrRate(this);



        //----------------------------
        SM = (SensorManager)getSystemService(SENSOR_SERVICE);

        // Accelerometer Sensor
        mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Register sensor Listener
        SM.registerListener((SensorEventListener) this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);

        xText = (TextView)findViewById(R.id.acc_x);
        yText = (TextView)findViewById(R.id.acc_y);
        zText = (TextView)findViewById(R.id.acc_z);



    }


    public void getGps(View view) {
        textgps.setText(gps.getLatitude() + " ---- " +gps.getLongitude());
    }

    public void getH(View view) {
        textH.setText(heartrRate.getH());

    }







    @Override
    public void onSensorChanged(SensorEvent event) {
        xText.setText("X: " + (int)event.values[0]);
        yText.setText("Y: " + (int)event.values[1]);
        zText.setText("Z: " + (int)event.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
