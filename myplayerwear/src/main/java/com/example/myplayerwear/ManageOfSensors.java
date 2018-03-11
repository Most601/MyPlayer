package com.example.myplayerwear;

import android.content.Context;

/**
 * Created by Most601 on 06/03/2018.
 */

public class ManageOfSensors {


    //--------------------------------
    public static ManageOfSensors MOS ;

    public static ManageOfSensors getInstance(Context context) {
        if (MOS == null) {
            MOS = new ManageOfSensors(context.getApplicationContext());
        }
        return MOS;
    }
    //--------------------------------


    private GPS gps ;
    private Context context ;
    private HeartrRate H ;
    private stepCounter SC ;
    private Accelerometer AC ;


    public ManageOfSensors(Context context) {
        this.context = context;
        AC = new Accelerometer(context);
        H = new HeartrRate(context);
        SC = new stepCounter(context);
       // gps = new GPS(context);


    }

//-------------------------- ALL SENSORS---------------------------------------------

    public void StartAllSensors (){
        SC.startMeasurement();
        AC.startMeasurement();
        H.startMeasurement();
    }

    public void StopAllSensors (){
        SC.stopMeasurement();
        AC.stopMeasurement();
        H.stopMeasurement();
    }


    //------------------- stepCounter ----------------------------.

    public void StartStepCounter (){
        SC.startMeasurement();
    }

    public void StopStepCounter (){
        SC.stopMeasurement();
    }


//------------------- Accelerometer ---------------------------

    public void StartAccelerometer (){
        AC.startMeasurement();
    }

    public void StopAccelerometer (){
        AC.stopMeasurement();
    }



//-------------------- HeartrRate --------------------------

    public void StartHeartrRate() {
        H.startMeasurement();
    }
    public void StopHeartrRate() {
        H.stopMeasurement();

    }



}
