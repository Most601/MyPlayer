package com.example.myplayerwear;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

public class MainActivity extends WearableActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static final String WEARABLE_MAIN = "WEARABLE_MAIN";//logcat debugging.
    private Node mNode;//represents the phone that I want to communicate with from the watch.
    private GoogleApiClient mGoogleApiClient;//to connect and send messages.
    private static final String WEAR_PATH = "/from-wear";//to synchronize messages between the devices.


    private TextView latitude;
    private TextView longitude;
    GPS gps;
    private HeartrRate HR = new HeartrRate(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize mGoogleApiClient.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this) //Callbacks from node - success or fails.
                .addOnConnectionFailedListener(this) //If I had a fail connection.
                .build();//create my API object.

    }


    @Override
    //From addConnectionCallbacks.
    public void onConnected(@Nullable Bundle bundle) {
        //Which node to connect to.
        Wearable.NodeApi.getConnectedNodes(mGoogleApiClient)
                .setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
                    @Override
                    public void onResult(@NonNull NodeApi.GetConnectedNodesResult nodes) {
                        //Find the node I want to communicate with.
                        for (Node node : nodes.getNodes()){
                            if(node != null && node.isNearby()){
                                mNode = node;
                                Log.d(WEARABLE_MAIN,"Connected to " + mNode.getDisplayName());
                            }
                        }
                        if(mNode == null){
                            Log.d(WEARABLE_MAIN, "Not connected!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                        }

                    }
                }) //returns a set of nods.
        ;

    }

    @Override
    //From addConnectionCallbacks.
    public void onConnectionSuspended(int i) {

    }

    @Override
    //From addOnConnectionFailedListener.
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

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
}
