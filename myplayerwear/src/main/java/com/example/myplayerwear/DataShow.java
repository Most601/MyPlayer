package com.example.myplayerwear;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.TextView;
import android.hardware.SensorEvent;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import java.util.Calendar;

public class DataShow extends WearableActivity {
    private Accelerometer AC ;
    private GPS gps ;
    private HeartrRate H;
    private stepCounter SC;
    private static TextView
            AxText ,AyText ,AzText ,GpsText ,
            HeartrRateText ,stepCounterText ,timeDateText , sendToPhone ,
            GxText ,GyText ,GzText ;

    int count = 0 ;
    private SendToPhone STP ;

    private ManageOfSensors MOS ;

    private String latitude;
    private String longitude;
//----------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_show);


        MOS = ManageOfSensors.getInstance(this);


        gps = new GPS(this);
        GpsText = (TextView)findViewById(R.id.textView2);

//        H = new HeartrRate(this);
        HeartrRateText = (TextView)findViewById(R.id.textView3);
        HeartrRateText.setText("Push To Start");

//        AC = new Accelerometer(this);
        AxText = (TextView)findViewById(R.id.textView5);
        AyText = (TextView)findViewById(R.id.textView6);
        AzText = (TextView)findViewById(R.id.textView7);
        StartAccelerometer();

//        SC = new stepCounter(this);
        stepCounterText = (TextView)findViewById(R.id.textView4);
        StartstepCounter();

//        Gravity = new stepCounter(this);
        GxText = (TextView)findViewById(R.id.textView10);
        GyText = (TextView)findViewById(R.id.textView11);
        GzText = (TextView)findViewById(R.id.textView12);
        StartGravity();




        timeDate();

        STP = SendToPhone.getInstance(this);
        sendToPhone = (TextView)findViewById(R.id.textView9);


    }
//------------------- stepCounter ----------------------------.

    public void StartstepCounter (){
        MOS.StartStepCounter();
    }

    public void StopstepCounter (){
        MOS.StopStepCounter();
    }


//------------------- Accelerometer ---------------------------

    public void StartAccelerometer (){
        MOS.StartAccelerometer();
    }

    public void StopAccelerometer (){
        MOS.StopAccelerometer();
    }



//-------------------- HeartrRate --------------------------

    public void HeartrRateStartButten(View view) {
        HeartrRateText.setText("Wait ....");
        MOS.StartHeartrRate();
    }
    public void HeartrRateStopButten(View view) {
        MOS.StopHeartrRate();

    }

//-------------------- Gravity --------------------------

    public void StartGravity (){
        MOS.StartGravity();
    }

    public void StopGravity (){
        MOS.StopGravity();
    }

//----------------------- Gps -----------------------------

    public void GpsButten(View view) {
        longitude = gps.getLongitude();
        latitude= gps.getLatitude();
        GpsText.setText("latitude ="+latitude+" , "+"longitude = "+longitude);
    }

//--------------------- time + Date ---------------------------

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

//------------------------ print to screen -----------------------------

    public static void print(String sensor ,SensorEvent event){
        if (sensor == "AC"){
            AxText.setText("X: " + (int)event.values[0]+"   ;   ");
            AyText.setText("Y: " + (int)event.values[1]+"   ;   ");
            AzText.setText("Z: " + (int)event.values[2]);
        }
        else if (sensor == "H"){
            String msgH = " Value sensor : " + (int)event.values[0];
            HeartrRateText.setText(msgH);
        }
        else if (sensor == "SC"){
            String msgSC = " Value Step Counter : " + (int)event.values[0];
            stepCounterText.setText(msgSC);
        }
        else if (sensor == "Gravity"){
            GxText.setText("X: " + event.values[0]+"   ;   ");
            GyText.setText("Y: " + event.values[1]+"   ;   ");
            GzText.setText("Z: " + event.values[2]);
        }

    }






//--------------------- send Message/Data test ---------------------------

    public void buttonClickHandler(View view){
        //Send message to handheld device.
        String text = "aaaaaaaaaaaaaaaaa";
        sendMessage(text);
    }

    private void sendMessage(String text) {
        STP.sendMessage("Data_Shoe_Click",text);
        sendToPhone.setText("sending Message");
    }
    private void sendData(String text) {
        count++;
        PutDataMapRequest dataMap = PutDataMapRequest.create("/sensors/");
        dataMap.getDataMap().putInt("1", count);
        PutDataRequest putDataRequest = dataMap.asPutDataRequest();
        STP.sendButtonPush(putDataRequest);
        sendToPhone.setText("sending data");
    }

//--------------------------------------------------------------------------






}
