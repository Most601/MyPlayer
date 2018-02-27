package com.example.myplayerwear;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

public class DataShow extends WearableActivity {
    private Accelerometer AC ;
    private GPS gps ;
    private HeartrRate H;
    private stepCounter SC;
    private static TextView xText ,yText ,zText ,GpsText ,HeartrRateText ,stepCounterText ,timeDateText;


    private String latitude;
    private String longitude;
//----------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_show);

        gps = new GPS(this);
        GpsText = (TextView)findViewById(R.id.textView2);

        H = new HeartrRate(this);
        HeartrRateText = (TextView)findViewById(R.id.textView3);

        AC = new Accelerometer(this);
        xText = (TextView)findViewById(R.id.textView5);
        yText = (TextView)findViewById(R.id.textView6);
        zText = (TextView)findViewById(R.id.textView7);

        SC = new stepCounter(this);
        stepCounterText = (TextView)findViewById(R.id.textView4);
        timeDate();
    }
//-----------------------------------------------------------

    public void HeartrRateStartButten(View view) {
        HeartrRateText.setText("Wait ....");
        H.startMeasurement();
    }
    public void HeartrRateStopButten(View view) {
        H.stopMeasurement();

    }

//----------------------------------------------------------

    public void GpsButten(View view) {
        longitude = gps.getLongitude();
        latitude= gps.getLatitude();
        GpsText.setText("latitude ="+latitude+" , "+"longitude = "+longitude);
    }

//----------------------------------------------------------

    public void timeDate (){
        timeDateText = (TextView) findViewById(R.id.textView8);
        Calendar cc = Calendar.getInstance();
        int year=cc.get(Calendar.YEAR);
        int month=cc.get(Calendar.MONTH);
        int mDay = cc.get(Calendar.DAY_OF_MONTH);
        int mHour = cc.get(Calendar.HOUR_OF_DAY);
        int mMinute = cc.get(Calendar.MINUTE);
        timeDateText.append("Date : "+ year+"/"+month+"/"+mDay +" --- ");
        timeDateText.append("time : "+String.format("%02d:%02d", mHour , mMinute ));
    }

//----------------------------------------------------------

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
        else if (sensor == "SC"){
            String msgSC = " Value Step Counter : " + (int)event.values[0];
            stepCounterText.setText(msgSC);
        }

    }


//------------------------------------------------------------




}
