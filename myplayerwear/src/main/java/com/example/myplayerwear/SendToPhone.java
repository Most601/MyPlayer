package com.example.myplayerwear;

import android.content.Context;
import android.util.SparseLongArray;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Most601 on 06/03/2018.
 */

public class SendToPhone {


    public static final String ACCURACY = "accuracy";
    public static final String TIMESTAMP = "timestamp";
    public static final String VALUES = "values";
    public static final String FILTER = "filter";


    public static SendToPhone STP ;



    public static SendToPhone getInstance(Context context) {
        if (STP == null) {
            STP = new SendToPhone(context.getApplicationContext());
        }
        return STP;
    }



    private Context context;
    private GoogleApiClient googleApiClient;
    private ExecutorService executorService;
    private SparseLongArray lastSensorData;


    private SendToPhone(Context context) {
        this.context = context;




        googleApiClient = new
                GoogleApiClient.Builder(context).
                addApi(Wearable.API).
                build();

        executorService = Executors.newCachedThreadPool();
        lastSensorData = new SparseLongArray();
    }


    public void sendSensorData(final int sensorType, final int accuracy, final long timestamp, final float[] values) {
        long t = System.currentTimeMillis();

        long lastTimestamp = lastSensorData.get(sensorType);
        long timeAgo = t - lastTimestamp;

        if (lastTimestamp != 0) {
//            if (filterId == sensorType && timeAgo < 100) {
//                return;
//            }
//
//            if (filterId != sensorType && timeAgo < 3000) {
//                return;
//            }
        }
        lastSensorData.put(sensorType, t);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                sendSensorDataInBackground(sensorType, accuracy, timestamp, values);
            }
        });
    }

    private void sendSensorDataInBackground(int sensorType, int accuracy, long timestamp, float[] values) {
        PutDataMapRequest dataMap = PutDataMapRequest.create("/sensors/" + sensorType);
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
        ConnectionResult result = googleApiClient.blockingConnect(15000, TimeUnit.MILLISECONDS);
        return result.isSuccess();
    }

    private void send(PutDataRequest putDataRequest) {
        if (validateConnection()) {
            Wearable.DataApi.putDataItem(googleApiClient, putDataRequest).setResultCallback
                    (new ResultCallback<DataApi.DataItemResult>() {
                        @Override
                        public void onResult(DataApi.DataItemResult dataItemResult) {
                            // Log.v(TAG, "Sending sensor data: " + dataItemResult.getStatus().isSuccess());
                        }
                    });
        }
    }

}
