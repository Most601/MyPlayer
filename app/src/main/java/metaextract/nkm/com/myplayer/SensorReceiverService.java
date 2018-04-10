package metaextract.nkm.com.myplayer;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by Most601 on 28/02/2018.
 */

public class SensorReceiverService extends WearableListenerService  {

    private static final String TAG1 = "GETING DATA";
    private static final String TAG2 = "GETING MESSAGE";

    private static final String ACCURACY = "accuracy";
    private static final String TIMESTAMP = "timestamp";
    private static final String VALUES = "values";
    private static final String TYPE = "type";

    private DataReceiveManager DM_ACC = DataReceiveManager.getInstanceACC(this);
    private DataReceiveManager DM_Sensor = DataReceiveManager.getInstance(this);
    private DataReceiveManager DM_Gravity = DataReceiveManager.getInstanceGravity(this);
    private DataReceiveManager DM_Pressure = DataReceiveManager.getInstancePressure(this);
    private MessageReceiveManager MRM = MessageReceiveManager.getInstance(this);

//    @Override
//    public void onCreate() {
//        super.onCreate();
//     //   MRM = MessageReceiveManager.getInstance(this);
//    }
//
//    @Override
//    public void onPeerConnected(Node peer) {
//        super.onPeerConnected(peer);
//
//        //Log.i(TAG, "Connected: " + peer.getDisplayName() + " (" + peer.getId() + ")");
//    }
//
//    @Override
//    public void onPeerDisconnected(Node peer) {
//        super.onPeerDisconnected(peer);
//
//       // Log.i(TAG, "Disconnected: " + peer.getDisplayName() + " (" + peer.getId() + ")");
//    }

//----------------------- Data Changed ----------------------------------------------------

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {


        for (DataEvent dataEvent : dataEvents) {
            if (dataEvent.getType() == DataEvent.TYPE_CHANGED) {
                DataItem dataItem = dataEvent.getDataItem();
                Uri uri = dataItem.getUri();
                String path = uri.getPath();

                if (path.startsWith("/sensors/")) {
                    unpackSensorData(
                        Integer.parseInt(uri.getLastPathSegment()),
                        DataMapItem.fromDataItem(dataItem).getDataMap()
                    );
                }
            }
        }
    }

    private void unpackSensorData(int sensorType, DataMap dataMap) {
        String SensorTypeString = dataMap.getString(TYPE);
        int accuracy = dataMap.getInt(ACCURACY);
        long timestamp = dataMap.getLong(TIMESTAMP);
        float[] values = dataMap.getFloatArray(VALUES);
        if (  Sensor.TYPE_ACCELEROMETER == sensorType ){
          //  Log.d(TAG1, "Received sensor TYPE_ACCELEROMETER data " + sensorType + " = " +values[0] );
            DM_ACC.addSensorData(SensorTypeString, sensorType, accuracy, timestamp, values);
        }
        else if (Sensor.TYPE_GRAVITY == sensorType){
          //  Log.d(TAG1, "Received sensor TYPE_GRAVITY data " + sensorType + " = " +values[0] );
            DM_Gravity.addSensorData(SensorTypeString, sensorType, accuracy, timestamp, values);
        }
        else if (Sensor.TYPE_PRESSURE == sensorType){
              Log.d(TAG1, "Received sensor TYPE_PRESSURE data " + sensorType + " = " +values[0] );
            DM_Pressure.addSensorData(SensorTypeString, sensorType, accuracy, timestamp, values);
        }
        else {
            //Log.d(TAG1, "Received sensor data " + sensorType + " = " +SensorTypeString );
            DM_Sensor.addSensorData(SensorTypeString, sensorType, accuracy, timestamp, values);


        }
    }

//----------------------- Message Received ----------------------------------------------------

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);

        Log.d(TAG2, "geting message. Path : "+ messageEvent.getPath()+ " , Data : "+new String(messageEvent.getData()));
        if (messageEvent.getPath().equals("Player")) {
                MRM.MessageReceive(messageEvent);
                
        }
        if (messageEvent.getPath().equals("Data_Shoe_Click")) {
            Intent i = new Intent(getApplicationContext(), DataShow.class);
            startActivity(i);
        }
    }
}
