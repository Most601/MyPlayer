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



    private Context context ;
    private HeartrRate H ;
    private stepCounter SC ;
    private Accelerometer AC ;
    private Gravity gravity;
    private MagneticField magneticField;
    private Orientation orientation;
    private Pressure pressure;
    private RotationVector rotationVector;

    public ManageOfSensors(Context context) {
        this.context = context;
        AC = new Accelerometer(context);
        H = new HeartrRate(context);
        SC = new stepCounter(context);
        gravity = new Gravity(context);
        magneticField = new MagneticField(context);
        orientation = new Orientation(context);
        pressure = new Pressure(context);
        rotationVector = new RotationVector(context);



    }

//-------------------------- ALL SENSORS---------------------------------------------

    public void StartAllSensors (){
        StartStepCounter();
        StartAccelerometer();
        H.startMeasurement();
        StartGravity();
        StartMagneticField();
        //StartOrientation();
        StartPressure();
        //StartRotationVector() ;
    }

    public void StopAllSensors (){
        StopStepCounter();
        StopAccelerometer();
        H.stopMeasurement();
        StopGravity();
        StopMagneticField();
       // StopOrientation();
        StopPressure();
       // StopRotationVector() ;
    }

    //------------------- stepCounter ----------------------------.
    public void StartStepCounter (){SC.startMeasurement();}
    public void StopStepCounter (){SC.stopMeasurement();}
//------------------- Accelerometer ---------------------------
    public void StartAccelerometer (){AC.startMeasurement();}
    public void StopAccelerometer (){AC.stopMeasurement();}
//-------------------- HeartrRate --------------------------
    public void StartHeartrRate() {H.startMeasurement();}
    public void StopHeartrRate() {H.stopMeasurement();}
//-------------------- Gravity --------------------------
    public void StartGravity() {gravity.startMeasurement();}
    public void StopGravity() {gravity.stopMeasurement();}
//-------------------- magneticField --------------------------
    public void StartMagneticField() {magneticField.startMeasurement();}
    public void StopMagneticField() {magneticField.stopMeasurement();}
//-------------------- Orientation --------------------------
    public void StartOrientation() {orientation.startMeasurement();}
    public void StopOrientation() {orientation.stopMeasurement();}
//-------------------- Pressure --------------------------
    public void StartPressure() {pressure.startMeasurement();}
    public void StopPressure() {pressure.stopMeasurement();}
//-------------------- RotationVector --------------------------
    public void StartRotationVector() {rotationVector.startMeasurement();}
    public void StopRotationVector() {rotationVector.stopMeasurement();}


}
