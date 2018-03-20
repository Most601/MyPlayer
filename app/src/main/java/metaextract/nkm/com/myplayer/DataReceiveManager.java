package metaextract.nkm.com.myplayer;

import android.content.Context;
import android.hardware.Sensor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Most601 on 15/12/2017.
 */

public class DataReceiveManager {

    private static DataReceiveManager DM;
    public static synchronized DataReceiveManager getInstance(Context context) {
        if (DM == null) {
            DM = new DataReceiveManager(context.getApplicationContext());
        }
        return DM;
    }

    private static DataReceiveManager DM_ACC;
    public static DataReceiveManager getInstanceACC(Context context) {
        if (DM_ACC == null) {
            DM_ACC = new DataReceiveManager(context.getApplicationContext());
        }
        return DM_ACC;
    }

    //----------------------------------------------------------------------------------------------

    private Context context;
    private ArrayList<Song> songsList = new ArrayList<Song>();
    private FileManager fileManager ;
    private String songName ;
    private GPS gps;

    private Calendar cc = Calendar.getInstance();
    private int year=cc.get(Calendar.YEAR);
    private int month=cc.get(Calendar.MONTH);
    private int mDay = cc.get(Calendar.DAY_OF_MONTH);
    private int mHour = cc.get(Calendar.HOUR_OF_DAY);
    private int mMinute = cc.get(Calendar.MINUTE);
    private int nSeceend = cc.get(Calendar.SECOND);


    public DataReceiveManager(Context context){
        this.context = context;
        fileManager = new FileManager(context);
    }

    public DataReceiveManager(Context context , String filename ){
        this.context = context;

        fileManager = new FileManager(context , filename , false );
    }

    //------------------- SONGLIST FILE ------------------------------------------------------------

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

    //------------------- Activity FILE --------------------------------------------------------

    public void ActivityFILE(String currentSongName ,int currentSongIndex , String timeOfSong,String LastSongName, String progress , String Activity ){
        cc = Calendar.getInstance();
        year=cc.get(Calendar.YEAR);
        month=cc.get(Calendar.MONTH);
        mDay = cc.get(Calendar.DAY_OF_MONTH);
        mHour = cc.get(Calendar.HOUR_OF_DAY);
        mMinute = cc.get(Calendar.MINUTE);
        nSeceend = cc.get(Calendar.SECOND);
        fileManager.writeInternalFileCsvNewLINE("Date : "+year+"/"+month+"/"+mDay  , true );
        fileManager.writeInternalFileCsvSameLine("time : "+String.format("%02d:%02d:%02d", mHour , mMinute,nSeceend) , true);
        fileManager.writeInternalFileCsvSameLine(Integer.toString(currentSongIndex) ,true);
        fileManager.writeInternalFileCsvSameLine(currentSongName ,true);
        fileManager.writeInternalFileCsvSameLine(timeOfSong ,true);
        fileManager.writeInternalFileCsvSameLine(LastSongName ,true);
        fileManager.writeInternalFileCsvSameLine(progress ,true);
        fileManager.writeInternalFileCsvSameLine(Activity ,true);


    }














    //------------------ SensorData ACC  ----------------------------------------------------------------

    public void addSensorData(String SensorTypeString , int sensorType, int accuracy, long timestamp, float[] values) {

//        event.sensor.getType() == Sensor.TYPE_HEART_RATE == sensorType

//        try {
//            fileManager.writeInternalFile(SensorTypeString , Float.toString(values[0]) , true);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        cc = Calendar.getInstance();
        year=cc.get(Calendar.YEAR);
        month=cc.get(Calendar.MONTH);
        mDay = cc.get(Calendar.DAY_OF_MONTH);
        mHour = cc.get(Calendar.HOUR_OF_DAY);
        mMinute = cc.get(Calendar.MINUTE);
        nSeceend = cc.get(Calendar.SECOND);
        fileManager.writeInternalFileCsvNewLINE("Date : "+year+"/"+month+"/"+mDay  , true );
        fileManager.writeInternalFileCsvSameLine("time : "+String.format("%02d:%02d:%02d", mHour , mMinute,nSeceend) , true);
        if (  Sensor.TYPE_ACCELEROMETER == sensorType ){
            fileManager.writeInternalFileCsvSameLine(  Float.toString(values[0]) ,true);
            fileManager.writeInternalFileCsvSameLine(  Float.toString(values[1]) ,true);
            fileManager.writeInternalFileCsvSameLine(  Float.toString(values[2]) ,true);
            fileManager.writeInternalFileCsvSameLine( Long.toString(timestamp) ,true);

        }
    }

    //------------------ SensorData   ----------------------------------------------------------------
    public void addSensorData_s(String SensorTypeString , int sensorType, int accuracy, long timestamp, float[] values) {

//        event.sensor.getType() == Sensor.TYPE_HEART_RATE == sensorType

        cc = Calendar.getInstance();
        year=cc.get(Calendar.YEAR);
        month=cc.get(Calendar.MONTH);
        mDay = cc.get(Calendar.DAY_OF_MONTH);
        mHour = cc.get(Calendar.HOUR_OF_DAY);
        mMinute = cc.get(Calendar.MINUTE);
        nSeceend = cc.get(Calendar.SECOND);
        fileManager.writeInternalFileCsvNewLINE("Date : "+year+"/"+month+"/"+mDay  , true );
        fileManager.writeInternalFileCsvSameLine("time : "+String.format("%02d:%02d:%02d", mHour , mMinute,nSeceend) , true);
        if (SensorTypeString == "GPS"){
            fileManager.writeInternalFileCsvSameLine(  SensorTypeString ,true);
            fileManager.writeInternalFileCsvSameLine(  Float.toString(values[0])+" - "+Float.toString(values[1]) ,true);
        }
        else {
            fileManager.writeInternalFileCsvSameLine(  SensorTypeString ,true);
            fileManager.writeInternalFileCsvSameLine(  Float.toString(values[0]) ,true);
        }




    }


    public void setSongName (String songName){
        gps = new GPS(context);
        this.songName = songName ;
        fileManager = new FileManager(context,songName,false);
    }









}
