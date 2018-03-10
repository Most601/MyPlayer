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


    private Context context;


    public ManageOfSensors(Context context) {
        this.context = context;
    }

    public void StartAllSensors (){

    }

    public void StopAllSensors (){

    }

    public void StartHeartrRateSensors (){

    }


}
