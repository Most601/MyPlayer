package metaextract.nkm.com.myplayer;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by Most601 on 09/03/2018.
 */

public class SendToWear implements GoogleApiClient.ConnectionCallbacks {

    private static SendToWear STW;

    public static synchronized SendToWear getInstance(Context context) {
        if (STW == null) {
            STW = new SendToWear(context.getApplicationContext());
        }
        return STW;
    }


//--------------------------------------------------------------------------------------------------


    private GoogleApiClient mGoogleApiClient ;
    private Node mNode;

    public SendToWear(Context context) {

        mGoogleApiClient = new
                GoogleApiClient.Builder(context)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
               // .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
    }

    private boolean validateConnection() {
        if (mGoogleApiClient.isConnected()) {
            return true;
        }
        else {
            mGoogleApiClient.connect();
        }
        return mGoogleApiClient.isConnected();
    }



//--------------------------------------------------------------------------------------------------


    public synchronized void sendMessage(String type , final String message ) {
        resolveNode();
        if(mGoogleApiClient != null &&
                validateConnection() &&
                mNode != null
                ) {
            Log.d("","Message is going to be sent to watch");

            Wearable.MessageApi.sendMessage(mGoogleApiClient,
                    mNode.getId(),
                    type,
                    message.getBytes())
                    .setResultCallback(new ResultCallback<MessageApi.SendMessageResult>() {
                        @Override
                        public void onResult(@NonNull MessageApi.SendMessageResult sendMessageResult) {
                            if(sendMessageResult.getStatus().isSuccess()) {
                                Log.e("111111111111111","Message Succesfully sent to watch=>"+message);
                            } else {
                                Log.e("000000000000000","Message FAILED TO BE SENT to watch=>"+message);
                            }
                        }
                    });
        }
    }

    private void resolveNode() {
        Wearable.NodeApi.getConnectedNodes(mGoogleApiClient)
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
        ;
    }
//------------------------------------------------------------------------------------



    public synchronized void send(PutDataRequest putDataRequest) {
        if (validateConnection()) {
            Wearable.DataApi.putDataItem(mGoogleApiClient, putDataRequest).setResultCallback
                    (new ResultCallback<DataApi.DataItemResult>() {
                        @Override
                        public void onResult(DataApi.DataItemResult dataItemResult) {
                            if(dataItemResult.getStatus().isSuccess() ) {
                                Log.d("SENDDDDDDDDD Data", "Sending data: " +dataItemResult.getStatus().isSuccess() );
                            }
                            else {
                                Log.d("SENDDDDDDDDD Data", "Sending data: " +dataItemResult.getStatus().isSuccess() );


                            }
                        }
                    });
        }
    }

//--------------------------------------------------------------------------------



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


}
