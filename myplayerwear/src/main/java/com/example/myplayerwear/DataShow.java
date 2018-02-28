package com.example.myplayerwear;

import android.content.Intent;
import android.hardware.Sensor;

import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.hardware.SensorEvent;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.Calendar;
import java.util.List;

public class DataShow extends WearableActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private Accelerometer AC ;
    private GPS gps ;
    private HeartrRate H;
    private stepCounter SC;
    private static TextView xText ,yText ,zText ,GpsText ,HeartrRateText ,stepCounterText ,timeDateText ,    x ;

    private GoogleApiClient mGoogleApiClient;
    private Node mNode;//represents the phone that I want to communicate with from the watch.


    private String latitude;
    private String longitude;
//----------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_show);

    //    Intent i = getIntent();
    //   mGoogleApiClient = (GoogleApiClient)i.getSerializableExtra("sampleObject");

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



        //Initialize mGoogleApiClient.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
         //       .addConnectionCallbacks(this) //Callbacks from node - success or fails.
         //       .addOnConnectionFailedListener(this) //If I had a fail connection.
                .build();//create my API object.
        x = (TextView)findViewById(R.id.textView9);


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

        @Override
    //Connect to the external node.
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    //Clean up the connection as the activity stops.
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }



    public void buttonClickHandler(View view){
        //Send message to handheld device.

        String text = "aaaaaaaaaaaaaaaaa";
        sendMessage(text);
        x.setText("555");
    }

    private void sendMessage(String text) {
//
//        PutDataMapRequest dataMap = PutDataMapRequest.create("/sensors/" );
//
//
//
//        PutDataRequest putDataRequest = dataMap.asPutDataRequest();
//
//
//        Wearable.DataApi.putDataItem(mGoogleApiClient, putDataRequest).setResultCallback
//                (new ResultCallback<DataApi.DataItemResult>() {
//                    @Override
//                    public void onResult(DataApi.DataItemResult dataItemResult) {
//                        // Log.v(TAG, "Sending sensor data: " + dataItemResult.getStatus().isSuccess());
//                    }
//                });









        Wearable.NodeApi.getConnectedNodes(mGoogleApiClient)
                .setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
                    @Override
                    public void onResult(@NonNull NodeApi.GetConnectedNodesResult nodes) {
                        //Find the node I want to communicate with.
                        for (Node node : nodes.getNodes()) {
                            if (node != null && node.isNearby()) {
                                mNode = node;
                                x.setText(mNode.getDisplayName());
                            }
                        }
                        if (mNode == null) {
                            x.setText("999888889999");
                        }

                    }
                }) //returns a set of nods.
        ;

        // List<Node> nodes = Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).await().getNodes();

        //   for (Node node : nodes) {
        if (mGoogleApiClient != null &&
                mGoogleApiClient.isConnected() &&
                mNode != null) {
            Wearable.MessageApi.sendMessage(
                    mGoogleApiClient, mNode.getId(), "a1", text.getBytes())
                    .setResultCallback(new ResultCallback<MessageApi.SendMessageResult>() {
                        @Override
                        public void onResult(MessageApi.SendMessageResult sendMessageResult) {
                            if (!sendMessageResult.getStatus().isSuccess()) {
                                x.setText("111");
                            } else {
                                x.setText("0000");
                            }
                        }
                    });
            //     }
        }


}






    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
