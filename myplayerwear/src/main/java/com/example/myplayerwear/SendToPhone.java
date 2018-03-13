package com.example.myplayerwear;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseLongArray;

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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Most601 on 06/03/2018.
 */

public class SendToPhone implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    //-------------------------
    public static SendToPhone STP ;

    public static synchronized SendToPhone getInstance(Context context) {
        if (STP == null) {
            STP = new SendToPhone(context.getApplicationContext());
        }
        return STP;
    }
    //-------------------------

    public static final String ACCURACY = "accuracy";
    public static final String TIMESTAMP = "timestamp";
    public static final String VALUES = "values";
    public static final String TYPE = "type";

    private Context context;
    private GoogleApiClient googleApiClient;
    private ExecutorService executorService;
    private SparseLongArray lastSensorData;
    private Node mNode;//represents the phone that I want to communicate with from the watch.



    private SendToPhone(Context context) {
        this.context = context;
        googleApiClient = new
                GoogleApiClient.Builder(context).
                addApi(Wearable.API).
                addConnectionCallbacks(this). //Callbacks from node - success or fails.
                //       .addOnConnectionFailedListener(this) //If I had a fail connection.
                build();
        googleApiClient.connect();
        executorService = Executors.newCachedThreadPool();
        lastSensorData = new SparseLongArray();
    }

//----------------------------- DATA -----------------------------------------

    public void sendSensorData(final String SensorTypeString ,final int sensorType, final int accuracy, final long timestamp, final float[] values) {
        long t = System.currentTimeMillis();
        long lastTimestamp = lastSensorData.get(sensorType);
        long timeAgo = t - lastTimestamp;
//        if (lastTimestamp != 0) {
//            if (filterId == sensorType && timeAgo < 100) {
//                return;
//            }
//
//            if (filterId != sensorType && timeAgo < 3000) {
//                return;
//            }
//        }
        lastSensorData.put(sensorType, t);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                sendSensorDataInBackground(SensorTypeString, sensorType, accuracy, timestamp, values);
            }
        });
    }

    private void sendSensorDataInBackground(String SensorTypeString , int sensorType, int accuracy, long timestamp, float[] values) {
        PutDataMapRequest dataMap = PutDataMapRequest.create("/sensors/" + sensorType);
        dataMap.getDataMap().putString(TYPE,SensorTypeString);
        dataMap.getDataMap().putInt(ACCURACY, accuracy);
        dataMap.getDataMap().putLong(TIMESTAMP, timestamp);
        dataMap.getDataMap().putFloatArray(VALUES, values);
        PutDataRequest putDataRequest = dataMap.asPutDataRequest();
        send(putDataRequest);
    }

    private boolean validateConnection() {
        if (googleApiClient.isConnected()) {
            return true;
        }
        else {
            googleApiClient.connect();
        }
        return googleApiClient.isConnected();
    }

    private synchronized void send(PutDataRequest putDataRequest) {
        if (validateConnection()) {
            Wearable.DataApi.putDataItem(googleApiClient, putDataRequest).setResultCallback
                    (new ResultCallback<DataApi.DataItemResult>() {
                        @Override
                        public void onResult(DataApi.DataItemResult dataItemResult) {
                            if(dataItemResult.getStatus().isSuccess() ) {
                                Log.d("SENDDDDDDDDD Data", "Sending sensor data: " +dataItemResult.getStatus().isSuccess() );
                            }
                            else {
                                Log.d("SENDDDDDDDDD Data", "Sending sensor data: " +dataItemResult.getStatus().isSuccess() );


                            }                        }
                    });
        }
    }


    //----------------- send from DataShow --------------------------------------------------------

    public synchronized void sendButtonPush(PutDataRequest putDataRequest) {
        if (validateConnection()) {
            Wearable.DataApi.putDataItem(googleApiClient, putDataRequest).setResultCallback
                    (new ResultCallback<DataApi.DataItemResult>() {
                        @Override
                        public void onResult(DataApi.DataItemResult dataItemResult) {
                            if(dataItemResult.getStatus().isSuccess() ) {
                                Log.d("SENDDDDDDDDD Data", "Sending sensor data: " +dataItemResult.getStatus().isSuccess() );
                            }
                            else {
                                Log.d("SENDDDDDDDDD Data", "Sending sensor data: " +dataItemResult.getStatus().isSuccess() );


                            }
                        }
                    });
        }
    }

//------------------------------ Message -------------------------------------------------

    // List<Node> nodes = Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).await().getNodes();
    //   for (Node node : nodes) {


    public synchronized void sendMessage(String type , String message) {
        resolveNode();
        if (googleApiClient != null &&
                validateConnection() &&
                mNode != null) {
            Wearable.MessageApi.sendMessage(
                    googleApiClient, mNode.getId(),
                    type,
                    message.getBytes())
                    .setResultCallback(new ResultCallback<MessageApi.SendMessageResult>() {
                            @Override
                            public void onResult(MessageApi.SendMessageResult sendMessageResult) {
                                if (sendMessageResult.getStatus().isSuccess()) {
                                    Log.d("SENDDDDDDDDD Message", "Sending sensor data: " +sendMessageResult.getStatus().isSuccess() );
                                }
                                else {
                                    Log.d("SENDDDDDDDDD Message", "Sending sensor data: " +sendMessageResult.getStatus().isSuccess() );
                                }
                            }
                        });

            }

    }

    private void resolveNode() {
        if (validateConnection()) {
            Wearable.NodeApi.getConnectedNodes(googleApiClient)
                    .setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
                        @Override
                        public void onResult(@NonNull NodeApi.GetConnectedNodesResult nodes) {
                            //Find the node I want to communicate with.
                            for (Node node : nodes.getNodes()) {
                                if (node != null && node.isNearby()) {
                                    mNode = node;
                                    Log.d("11111111111111", "Sending data to : " +node.getDisplayName());

                                }
                            }
                            if (mNode == null) {
                            }
                        }
                    }) //returns a set of nods.
            ;}
    }

//-----------------------------------------------------------------------------------


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Wearable.NodeApi.getConnectedNodes(googleApiClient)
                .setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
                    @Override
                    public void onResult(@NonNull NodeApi.GetConnectedNodesResult nodes) {
                        //Find the node I want to communicate with.
                        for (Node node : nodes.getNodes()){
                            if(node != null && node.isNearby()){
                                mNode = node;
                                Log.d("","Connected to??????????????????????? " + mNode.getDisplayName());
                            }
                        }
                        if(mNode == null){
                            Log.d("?", "Not connected!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                        }

                    }
                }) //returns a set of nods.
        ;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
