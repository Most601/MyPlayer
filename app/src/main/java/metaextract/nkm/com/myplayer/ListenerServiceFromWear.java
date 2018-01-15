package metaextract.nkm.com.myplayer;

import android.content.Intent;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

/**
 * Created by mariavinogradov on.
 */

public class ListenerServiceFromWear extends WearableListenerService{

    private static final String WEARPATH = "/from-wear";//to synchronize messages between the devices.

    @Override
    //When the message sent from the watch to the phone this method will be triggered.
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);

        //Check that I receive the right message.
        if(messageEvent.getPath().equals(WEARPATH)){
            //Get the data.
            //String text = new String(messageEvent.getData());
            Intent i = new Intent(this, DataShow.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
            Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
    }
}
