package com.example.myplayerwear;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
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

    public static final String WEARABLE_MAIN = "WEARABLE_MAIN";//logcat debugging.
    private Node mNode;//represents the phone that I want to communicate with from the watch.
    private GoogleApiClient mGoogleApiClient;//to connect and send messages.
    private static final String WEAR_PATH = "/from-wear";//to synchronize messages between the devices.


    private TextView latitude;
    private TextView longitude;
    private GPS gps;

    MessageReceiverService aaa ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        aaa = new MessageReceiverService(this);

//        //Initialize mGoogleApiClient.
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addApi(Wearable.API)
//         //       .addConnectionCallbacks(this) //Callbacks from node - success or fails.
//         //       .addOnConnectionFailedListener(this) //If I had a fail connection.
//                .build();//create my API object.




        //---------------





    }

//    public void buttonClickHandler(View view){
//        //Send message to handheld device.
//        Button button = (Button) view;
//        String text = button.getText().toString();
//        sendMessage(text);
//    }
//
//    private void sendMessage(String text) {
//        if(mNode != null && mGoogleApiClient != null){
//            Wearable.MessageApi.sendMessage(mGoogleApiClient,
//                    mNode.getId(),WEAR_PATH,text.getBytes())
//                    .setResultCallback(new ResultCallback<MessageApi.SendMessageResult>() {
//                        @Override
//                        public void onResult(@NonNull MessageApi.SendMessageResult sendMessageResult) {
//                            if(! sendMessageResult.getStatus().isSuccess()){
//                                Log.d(WEARABLE_MAIN, "Failed message: " + sendMessageResult.getStatus().getStatusCode());
//                            }else {
//                                Log.d(WEARABLE_MAIN, "Message succeeded: ");
//                            }
//                        }
//                    });
//        }
//    }
//
//
//    @Override
//    //From addConnectionCallbacks.
//    public void onConnected(@Nullable Bundle bundle) {
//        //Which node to connect to.
//        Wearable.NodeApi.getConnectedNodes(mGoogleApiClient)
//                .setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
//                    @Override
//                    public void onResult(@NonNull NodeApi.GetConnectedNodesResult nodes) {
//                        //Find the node I want to communicate with.
//                        for (Node node : nodes.getNodes()){
//                            if(node != null && node.isNearby()){
//                                mNode = node;
//                                Log.d(WEARABLE_MAIN,"Connected to??????????????????????? " + mNode.getDisplayName());
//                            }
//                        }
//                        if(mNode == null){
//                            Log.d(WEARABLE_MAIN, "Not connected!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//                        }
//
//                    }
//                }) //returns a set of nods.
//        ;
//
//    }
//
//    @Override
//    //From addConnectionCallbacks.
//    public void onConnectionSuspended(int i) {
//
//    }
//
//    @Override
//    //From addOnConnectionFailedListener.
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }

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

    public void Previous(View view) {
    }

    public void backward(View view) {
    }

    public void play(View view) {
    }

    public void Next(View view) {
    }
    public void Forward(View view) {
    }

    public void DataShow1(View view) {
        Intent intent = new Intent(this, DataShow.class);
      //  intent.putExtra("sampleObject", (Parcelable) mGoogleApiClient);
        startActivity(intent);

    }
}
