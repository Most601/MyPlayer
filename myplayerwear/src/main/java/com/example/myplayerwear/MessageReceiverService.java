package com.example.myplayerwear;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

public class MessageReceiverService extends WearableListenerService {

//--------------------------------------------------------------------

    private ManageOfSensors manageOfSensors ;

    public MessageReceiverService(){
    }

    public MessageReceiverService(Context Context){
        manageOfSensors = ManageOfSensors.getInstance(Context);
    }

//--------------------------------------------------------------------


    @Override
    public void onCreate() {
        super.onCreate();
    }

//------------------------------------------------------------------------------------

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        super.onDataChanged(dataEvents);
        Log.e("sssssssssssssssssss", "onDatadddddddddddddddddddddddddddddddddChanged()");
        Intent startIntent = new Intent(this, DataShow.class);
        startActivity(startIntent);

//        for (DataEvent dataEvent : dataEvents) {
//            if (dataEvent.getType() == DataEvent.TYPE_CHANGED) {
//                DataItem dataItem = dataEvent.getDataItem();
//                Uri uri = dataItem.getUri();
//                String path = uri.getPath();
//
//                if (path.startsWith("/filter")) {
//                    DataMap dataMap = DataMapItem.fromDataItem(dataItem).getDataMap();
//                    int filterById = dataMap.getInt("filter");
//                    deviceClient.setSensorFilter(filterById);
//                }
//            }
//        }
    }

//------------------------------------------------------------------------------------

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {



        Log.e("`1`1`1`1`1`1`1", "Received Path: " + messageEvent.getPath());
        Log.d("2`2`2`2`2`2`2`2", "Received message: " + new String(messageEvent.getData()));
        manageOfSensors.StartAllSensors();
//        if (messageEvent.getPath().equals(ClientPaths.START_MEASUREMENT)) {
//            startService(new Intent(this, SensorService.class));
//        }
//
//        if (messageEvent.getPath().equals(ClientPaths.STOP_MEASUREMENT)) {
//            stopService(new Intent(this, SensorService.class));
//        }
    }
}
