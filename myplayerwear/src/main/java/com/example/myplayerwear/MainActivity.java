package com.example.myplayerwear;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.io.Serializable;
import java.time.Instant;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends WearableActivity{// implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {



    private SendToPhone STP;
    private MessageReceiverService MRS ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MRS = new MessageReceiverService(this);
        STP = SendToPhone.getInstance(this);


        //------------------------- permission -----------------------------------------------------

        //------- Checking for permission ------
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        //------- If there is permission then we will get the song list ------
        if (permissionCheck == 0 ){
        }

        //------- Checks whether there was a request for permission ------
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //------ If permission denied ------
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            }
            //------ Requesting permission ------
            else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.BODY_SENSORS,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                        1);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

    //---------------------------------------------------------------------------------------------
    }



    //---------------------------------------------------------------------------------------------
    //----- Getting approval for permission ------
    /**
     * Getting approval for permission
     * @param requestCode - The code for the specific permission
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            //------ READ_EXTERNAL_STORAGE -------
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED //BODY_SENSORS
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED //BODY_SENSORS
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED)//ACCESS_COARSE_LOCATION
                {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    //Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    //startActivity(i);
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    //---------------------------------------------------------------------------------------------


//    @Override
//    //Connect to the external node.
//    protected void onStart() {
//        super.onStart();
//        mGoogleApiClient.connect();
//    }
//
//    @Override
//    //Clean up the connection as the activity stops.
//    protected void onStop() {
//        super.onStop();
//        mGoogleApiClient.disconnect();
//    }

    //---------------------------------------------------------------------------------------------
    public void play(View view) {
        STP.sendMessage("Player", "play");
    }
    public void backward(View view) {
        STP.sendMessage("Player", "backward");
    }
    public void Previous(View view) {
        STP.sendMessage("Player", "Previous");
    }
    public void Next(View view) {
        STP.sendMessage("Player", "Next");
    }
    public void Forward(View view) {STP.sendMessage("Player", "Forward");
    }

    //---------------------------------------------------------------------------------------------
    public void DataShow1(View view) {
        Intent intent = new Intent(this, DataShow.class);
      //  intent.putExtra("sampleObject", (Parcelable) mGoogleApiClient);
        startActivity(intent);

    }
}
