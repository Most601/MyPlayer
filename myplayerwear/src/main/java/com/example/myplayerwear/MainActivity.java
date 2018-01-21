package com.example.myplayerwear;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

/////created by maria.

public class MainActivity extends WearableActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, SensorEventListener {

    public static final String WEARABLE_MAIN = "WEARABLE_MAIN";//logcat debugging.
    private Node mNode;//represents the phone that I want to communicate with from the watch.
    private GoogleApiClient mGoogleApiClient;//to connect and send messages.

    //for steps counter
    SensorManager sensorManager;
    TextView tv_steps;
    boolean running = false;
    //

    private TextView latitude;
    private TextView longitude;
    GPS gps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //for steps counter
        tv_steps = (TextView) findViewById(R.id.tv_steps);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //

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

    //////////for steps counter
    @Override
    protected void onResume() {
        super.onResume();
        running = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(countSensor != null){
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        }else {
            Toast.makeText(this,"Sensor not found!",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        running = false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(running){
            tv_steps.setText(String.valueOf(event.values[0]));
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    //////////for steps counter
}