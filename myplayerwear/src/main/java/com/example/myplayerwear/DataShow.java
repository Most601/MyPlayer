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

    private static TextView
            AxText ,AyText ,AzText ,
            HeartrRateText ,stepCounterText ,timeDateText , sendToPhone ,
            GxText ,GyText ,GzText ,
            MFxText ,MFyText ,MFzText ,
            OxText ,OyText ,OzText ,
            PressureText ,
            RVxText ,RVyText ,RVzText , RVcosText , RVAccuracyText ;

    int count = 0 ;
    private SendToPhone STP ;
    private ManageOfSensors MOS ;

//----------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_show);


        MOS = ManageOfSensors.getInstance(this);



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

//        Gravity = new Gravity(this);
        GxText = (TextView)findViewById(R.id.textView10);
        GyText = (TextView)findViewById(R.id.textView11);
        GzText = (TextView)findViewById(R.id.textView12);
        // StartGravity();

//        MagneticField = new MagneticField(this);
        MFxText = (TextView)findViewById(R.id.textView13);
        MFyText = (TextView)findViewById(R.id.textView14);
        MFzText = (TextView)findViewById(R.id.textView15);
      //  StartMagneticField ();

//        Orientation = new Orientation(this);
        OxText = (TextView)findViewById(R.id.textView17);
        OyText = (TextView)findViewById(R.id.textView18);
        OzText = (TextView)findViewById(R.id.textView19);
      //  StartOrientation ();

//        Pressure = new Pressure(this);
        PressureText = (TextView)findViewById(R.id.textView16);
        StartPressure();

//        RotationVector = new RotationVector(this);
        RVxText = (TextView)findViewById(R.id.textView20);
        RVyText = (TextView)findViewById(R.id.textView21);
        RVzText = (TextView)findViewById(R.id.textView22);
        RVcosText = (TextView)findViewById(R.id.textView23);
        RVAccuracyText = (TextView)findViewById(R.id.textView24);
     //   StartRotationVector ();

//        SendToPhone = new SendToPhone(this);
        STP = SendToPhone.getInstance(this);
        sendToPhone = (TextView)findViewById(R.id.textView9);


        timeDate();


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
        MOS.StartHeartrRate();}
    public void HeartrRateStopButten(View view) {MOS.StopHeartrRate();}
//-------------------- Gravity --------------------------
    public void StartGravity (){
        MOS.StartGravity();
    }
    public void StopGravity (){
        MOS.StopGravity();
    }
//-------------------- MagneticField --------------------------
    public void StartMagneticField (){
        MOS.StartMagneticField();
    }
    public void StopMagneticField (){
        MOS.StopMagneticField();
    }
//-------------------- Orientation --------------------------
    public void StartOrientation (){MOS.StartOrientation();}
    public void StopOrientation (){MOS.StopOrientation();}
//-------------------- Pressure --------------------------
    public void StartPressure (){MOS.StartPressure();}
    public void StopPressure (){MOS.StopPressure();}
//-------------------- RotationVector --------------------------
    public void StartRotationVector (){MOS.StartRotationVector();}
    public void StopRotationVector (){MOS.StopRotationVector();}
//----------------------- Gps -----------------------------
    public void GpsButten(View view) {}
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
            GxText.setText("X: " + (int)event.values[0]+" ; ");
            GyText.setText("Y: " + (int)event.values[1]+" ; ");
            GzText.setText("Z: " + (int)event.values[2]);
        }
        else if (sensor == "MagneticField"){
            MFxText.setText("X: " + (int)event.values[0]+" ; ");
            MFyText.setText("Y: " + (int)event.values[1]+" ; ");
            MFzText.setText("Z: " + (int)event.values[2]);
        }
        else if (sensor == "Orientation"){
            OxText.setText("X: " + (int)event.values[0]+" ; ");
            OyText.setText("Y: " + (int)event.values[1]+" ; ");
            OzText.setText("Z: " + (int)event.values[2]);
        }
        else if (sensor == "Pressure"){
            PressureText.setText((int)event.values[0]);
        }
        else if (sensor == "RotationVector"){
            RVxText.setText("X: " + (int)event.values[0]+" ; ");
            RVyText.setText("Y: " + (int)event.values[1]+" ; ");
            RVzText.setText("Z: " + (int)event.values[2]);
            RVcosText.setText("cos : " + (int)event.values[4]+" ; ");
            RVAccuracyText.setText("Accuracy : " + (int)event.values[5]);
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
