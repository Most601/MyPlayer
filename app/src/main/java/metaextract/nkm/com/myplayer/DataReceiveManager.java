package metaextract.nkm.com.myplayer;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Most601 on 15/12/2017.
 */

public class DataReceiveManager {

    private ArrayList<Song> songsList = new ArrayList<Song>();
    private FileManager fileManager ;

    private String songName ;



    private static DataReceiveManager DM;

    public static synchronized DataReceiveManager getInstance(Context context) {
        if (DM == null) {
            DM = new DataReceiveManager(context.getApplicationContext());
        }
        return DM;
    }

    //--------------------------------------------------------------------------------------------------


    public DataReceiveManager(Context context){
        fileManager = new FileManager(context);
    }

    public DataReceiveManager(Context context , String filename ){
        fileManager = new FileManager(context , filename , false );
    }

    public void addSongList(ArrayList<Song> songsList ){
        fileManager.deleteFile();
        this.songsList = songsList;
        int i = 1;
        for (Song song : songsList) {
            long id = song.getID();
            String title = song.getTitle();
            String artist = song.getArtist();
            String data = song.getdata();
            String album = song.getAlbum();
            fileManager.writeInternalFileCsvNewLINE(Integer.toString(i) , true);
            fileManager.writeInternalFileCsvSameLine(Long.toString(id) , true);
            fileManager.writeInternalFileCsvSameLine(title , true);
//            fileManager.writeInternalFileCsvSameLine(artist, true);
//            fileManager.writeInternalFileCsvSameLine(album , true);
//           fileManager.writeInternalFileCsvSameLine(data , true );
            i++;
        }

    }

    //--------------------------------------------------------------------------------------------------

    public void addSensorData(String SensorTypeString , int sensorType, int accuracy, long timestamp, float[] values) {

//        event.sensor.getType() == Sensor.TYPE_HEART_RATE == sensorType


        try {
            fileManager.writeInternalFile(SensorTypeString , Float.toString(values[0]) , true);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }










}
