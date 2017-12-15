package metaextract.nkm.com.myplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.provider.MediaStore;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Most601 on 15/12/2017.
 */

public class DataManager {

    private ArrayList<Song> songsList = new ArrayList<Song>();
    private FileManager fileManager ;
    private static final String BEST_SCORE_FILE = "BestScore.txt";
    private GPS gps;
    private HeartrRate heartrRate ;
    private MediaPlayer mp;





    public DataManager(Context context){

        fileManager = new FileManager(context);

    }


    public void W (int songIndex){

        try {
            fileManager.writeInternalFile(BEST_SCORE_FILE, songsList.get(songIndex).getdata(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }









}
