package com.example.myplayerwear;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

public class MainActivity extends WearableActivity{// implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {



    private SendToPhone STP;
    private MessageReceiverService MRS ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MRS = new MessageReceiverService(this);
        STP = SendToPhone.getInstance(this);


    }



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

    //----------------------------------------------------------
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
    public void Forward(View view) {
        STP.sendMessage("Player", "Forward");
    }

    //------------------------------------------------------------
    public void DataShow1(View view) {
        Intent intent = new Intent(this, DataShow.class);
      //  intent.putExtra("sampleObject", (Parcelable) mGoogleApiClient);
        startActivity(intent);

    }
}
