package metaextract.nkm.com.myplayer;

import android.content.Context;
import android.media.MediaPlayer;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Most601 on 15/12/2017.
 */

public class DataReceiveManager {

    private ArrayList<Song> songsList = new ArrayList<Song>();
    private FileManager fileManager ;



    private static DataReceiveManager DM;

    public static synchronized DataReceiveManager getInstance(Context context) {
        if (DM == null) {
            DM = new DataReceiveManager(context.getApplicationContext());
        }
        return DM;
    }







    public DataReceiveManager(Context context){
        fileManager = new FileManager(context);
    }



}
