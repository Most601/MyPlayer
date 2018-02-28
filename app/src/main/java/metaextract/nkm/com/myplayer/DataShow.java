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

import java.util.Calendar;

public class DataShow extends AppCompatActivity implements SensorEventListener{

    private static TextView timeDateText , GpsText , HeartrRateText ,xText , yText , zText , x ;
    private LocationManager locationManager;
    private LocationListener listener;
    private Accelerometer accelerometer;
    private GPS gps ;
    private HeartrRate heartrRate;

//----------------------------------------------

//----------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_show_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //----------------- gps --------------------

        GpsText = (TextView) findViewById(R.id.textgps);
        gps = new GPS(this);

        //----------------- HeartrRate -------------

        HeartrRateText = (TextView) findViewById(R.id.textH);
        heartrRate = new HeartrRate(this);

        //----------------- Accelerometer -------------

        accelerometer = new Accelerometer(this);
        xText = (TextView)findViewById(R.id.acc_x);
        yText = (TextView)findViewById(R.id.acc_y);
        zText = (TextView)findViewById(R.id.acc_z);

        //-------------------- timeDate ----------------
        timeDate();



        x = (TextView)findViewById(R.id.Massage);


    }




    //---------------- Gps ------------------------
    public void getGps(View view) {
        GpsText.setText("latitude ="+gps.getLatitude()+" , "+"longitude = "+gps.getLongitude());
    }

    //---------------- Heartr Rate ------------------------

    public void HeartrRateStartButten(View view) {
        HeartrRateText.setText("Wait ....");
        heartrRate.startMeasurement();
    }
    public void HeartrRateStopButten(View view) {
        heartrRate.stopMeasurement();
    }

    //----------------- Accelerometer --------------
    @Override
    public void onSensorChanged(SensorEvent event) {
        xText.setText("X: " + (int)event.values[0]);
        yText.setText("Y: " + (int)event.values[1]);
        zText.setText("Z: " + (int)event.values[2]);
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }




    //-------------------- timeDate -----------------------------

    public void timeDate (){
        timeDateText = (TextView) findViewById(R.id.time_date);
        Calendar cc = Calendar.getInstance();
        int year=cc.get(Calendar.YEAR);
        int month=cc.get(Calendar.MONTH);
        int mDay = cc.get(Calendar.DAY_OF_MONTH);
        int mHour = cc.get(Calendar.HOUR_OF_DAY);
        int mMinute = cc.get(Calendar.MINUTE);
        timeDateText.append("Date : "+ year+"/"+month+"/"+mDay +" --- ");
        timeDateText.append("time : "+String.format("%02d:%02d", mHour , mMinute ));
    }

//---------------------- print -------------------------------

    public static void print(String sensor ,SensorEvent event){
        if (sensor == "AC"){
            xText.setText("X: " + (int)event.values[0]+"   ;   ");
            yText.setText("Y: " + (int)event.values[1]+"   ;   ");
            zText.setText("Z: " + (int)event.values[2]);
        }
        else if (sensor == "H"){
            String msgH = " Value sensor : " + (int)event.values[0];
            HeartrRateText.setText(msgH);
        }

    }

    public static void print2(String data, String aaaa) {

            x.setText(aaaa);



    }


//------------------------------------------------------------




}
