package metaextract.nkm.com.myplayer;


import android.content.Intent;
import android.net.Uri;
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

public class SensorReceiverService extends WearableListenerService {


//    @Override
//    public void onCreate() {
//        super.onCreate();
//
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

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
       // Log.d(TAG, "onDataChanged()");
        MainActivity.print2("DATA", "sssssssss");


//        for (DataEvent dataEvent : dataEvents) {
//            if (dataEvent.getType() == DataEvent.TYPE_CHANGED) {
//                DataItem dataItem = dataEvent.getDataItem();
//                Uri uri = dataItem.getUri();
//                String path = uri.getPath();
//
//                if (path.startsWith("/sensors/")) {
//                    unpackSensorData(
//                        Integer.parseInt(uri.getLastPathSegment()),
//                        DataMapItem.fromDataItem(dataItem).getDataMap()
//                    );
//                }
//            }
//        }
    }

    private void unpackSensorData(int sensorType, DataMap dataMap) {
        int accuracy = dataMap.getInt("accuracy");
        long timestamp = dataMap.getLong("timestamp");
        float[] values = dataMap.getFloatArray("values");

        // Log.d(TAG, "Received sensor data " + sensorType + " = " + Arrays.toString(values));

       // sensorManager.addSensorData(sensorType, accuracy, timestamp, values);
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);
        String AAAA = messageEvent.getPath().toString();
        if (messageEvent.getPath().equals("a1")) {
            MainActivity.print2("DATA", AAAA);
            Intent startIntent = new Intent(this, DataShow.class);
            startActivity(startIntent);

        }

        if (messageEvent.getPath().equals("ssssss")) {
            //   stopService(new Intent(this, SensorService.class));
        }
    }
}
