package metaextract.nkm.com.myplayer;

import android.content.Context;
import android.hardware.Sensor;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Most601 on 15/12/2017.
 */

public class DataReceiveManager {

    private static DataReceiveManager DM;
    private static DataReceiveManager DM_ACC;
    private static DataReceiveManager DM_Gravity;
    private static DataReceiveManager DM_Pressure;
    private static DataReceiveManager DM_MagneticField;
    private static DataReceiveManager DM_Orientation;
    private static DataReceiveManager DM_RotationVector;


    public static synchronized DataReceiveManager getInstance(Context context) {
        if (DM == null) {
            DM = new DataReceiveManager(context.getApplicationContext());
        }
        return DM;
    }

    public static synchronized DataReceiveManager getInstanceACC(Context context) {
        if (DM_ACC == null) {
            DM_ACC = new DataReceiveManager(context.getApplicationContext());
        }
        return DM_ACC;
    }

    public static synchronized DataReceiveManager getInstanceGravity (Context context) {
        if (DM_Gravity == null) {
            DM_Gravity = new DataReceiveManager(context.getApplicationContext());
        }
        return DM_Gravity;
    }

    public static synchronized DataReceiveManager getInstancePressure (Context context) {
        if (DM_Pressure == null) {
            DM_Pressure = new DataReceiveManager(context.getApplicationContext());
        }
        return DM_Pressure;
    }

    public static synchronized DataReceiveManager getInstanceMagneticField (Context context) {
        if (DM_MagneticField == null) {
            DM_MagneticField = new DataReceiveManager(context.getApplicationContext());
        }
        return DM_MagneticField;
    }

    public static synchronized DataReceiveManager getInstanceOrientation(Context context) {
        if (DM_Orientation == null) {
            DM_Orientation = new DataReceiveManager(context.getApplicationContext());
        }
        return DM_Orientation;
    }

    public static synchronized DataReceiveManager getInstanceRotationVector(Context context) {
        if (DM_RotationVector == null) {
            DM_RotationVector = new DataReceiveManager(context.getApplicationContext());
        }
        return DM_RotationVector;
    }
    //----------------------------------------------------------------------------------------------

    //-------------- Class variables ---------------------------------------------------------------
    private Context context;
    private FileManager fileManager;

    private ArrayList<Song> songsList = new ArrayList<Song>();
    private String songName;
    private GPS gps;

    private Calendar cc;
    private int year, month, mDay, mHour, mMinute, mSecond;
    //----------------------------------------------------------------------------------------------

    //-------------- Constructors ------------------------------------------------------------------
    public DataReceiveManager(Context context){
        this.context = context;
    }

    public DataReceiveManager(Context context ,String filename){
        this.context = context;
        fileManager = new FileManager( filename , false );
    }
    //----------------------------------------------------------------------------------------------

    /**
     * The function writes to file named SONGLIST.
     * @param songsList - song list.
     */
    public void addSongList(ArrayList<Song> songsList){
        fileManager.deleteFile();
        this.songsList = songsList;
        int i = 1;
        fileManager.writeInternalFileCsvNewLINE("Date : "+mDay+"/"+month+"/"+ year,true);
        for (Song song : songsList) {
            long id = song.getID();
            String title = song.getTitle();
            String artist = song.getArtist();
            String data = song.getdata();
            String album = song.getAlbum();
            String genre = song.getGenre();
            String Duration = song.getDuration();
            fileManager.writeInternalFileCsvNewLINE(Integer.toString(i) , true);
            fileManager.writeInternalFileCsvSameLine(Long.toString(id) , true);
            fileManager.writeInternalFileCsvSameLine(title , true);
            fileManager.writeInternalFileCsvSameLine(artist, true);
            fileManager.writeInternalFileCsvSameLine(album , true);
            fileManager.writeInternalFileCsvSameLine(Duration , true );
            fileManager.writeInternalFileCsvSameLine(genre , true );
            fileManager.writeInternalFileCsvSameLine(data , true );
            i++;
        }
    }

    //----------------------------------------------------------------------------------------------

    /**
     * The function writes to file named Activity.
     * @param currentSongName - current song name.
     * @param currentSongIndex - id of the song.
     * @param timeOfSong - duration of the song.
     * @param lastSongName - last song that was played.
     * @param progress - position of the last song in the SeekBar.
     * @param activity - activity that was performed: play, stop etc.
     */
    public void ActivityFILE(String currentSongName, int currentSongIndex, String timeOfSong, String lastSongName, String progress, String activity){
        cc = Calendar.getInstance();
        year = cc.get(Calendar.YEAR);
        month = cc.get(Calendar.MONTH);
        mDay = cc.get(Calendar.DAY_OF_MONTH);
        mHour = cc.get(Calendar.HOUR_OF_DAY);
        mMinute = cc.get(Calendar.MINUTE);
        mSecond = cc.get(Calendar.SECOND);
        fileManager.writeInternalFileCsvNewLINE("Date : "+mDay+"/"+month+"/"+ year  ,true );
        fileManager.writeInternalFileCsvSameLine("time : "+String.format("%02d:%02d:%02d", mHour , mMinute, mSecond) ,true);
        fileManager.writeInternalFileCsvSameLine(Integer.toString(currentSongIndex) ,true);
        fileManager.writeInternalFileCsvSameLine(currentSongName ,true);
        fileManager.writeInternalFileCsvSameLine(timeOfSong ,true);
        fileManager.writeInternalFileCsvSameLine(lastSongName ,true);
        fileManager.writeInternalFileCsvSameLine(progress ,true);
        fileManager.writeInternalFileCsvSameLine(activity ,true);
    }

    //----------------------------------------------------------------------------------------------

    /**
     * The function writes to file named .
     * @param SensorTypeString - sensor type.
     * @param sensorType - integer that represents the sensor type.
     * @param accuracy - accuracy of the sensor.
     * @param timestamp - time that the data of the sensor was collected.
     * @param values - data of the sensor, because it is accelerometer we have 3 values (x,y,z).
     */
    public void addSensorData(String SensorTypeString , int sensorType, int accuracy, long timestamp, float[] values) {

//      event.sensor.getType() == Sensor.TYPE_HEART_RATE == sensorTypesString
        cc = Calendar.getInstance();
        year=cc.get(Calendar.YEAR);
        month=cc.get(Calendar.MONTH);
        mDay = cc.get(Calendar.DAY_OF_MONTH);
        mHour = cc.get(Calendar.HOUR_OF_DAY);
        mMinute = cc.get(Calendar.MINUTE);
        mSecond = cc.get(Calendar.SECOND);
        fileManager.writeInternalFileCsvNewLINE("Date: "+mDay+"/"+month+"/"+ year ,true );
        fileManager.writeInternalFileCsvSameLine("time: "+String.format("%02d:%02d:%02d", mHour , mMinute, mSecond) ,true);
        if (Sensor.TYPE_ACCELEROMETER == sensorType){
            fileManager.writeInternalFileCsvSameLine(Float.toString(values[0]) ,true);
            fileManager.writeInternalFileCsvSameLine(Float.toString(values[1]) ,true);
            fileManager.writeInternalFileCsvSameLine(Float.toString(values[2]) ,true);
            fileManager.writeInternalFileCsvSameLine(Long.toString(timestamp) ,true);
        }
        else if (Sensor.TYPE_GRAVITY == sensorType){
            fileManager.writeInternalFileCsvSameLine(Float.toString(values[0]) ,true);
            fileManager.writeInternalFileCsvSameLine(Float.toString(values[1]) ,true);
            fileManager.writeInternalFileCsvSameLine(Float.toString(values[2]) ,true);
            fileManager.writeInternalFileCsvSameLine(Long.toString(timestamp) ,true);
        }
        else if (Sensor.TYPE_MAGNETIC_FIELD == sensorType){
            fileManager.writeInternalFileCsvSameLine(Float.toString(values[0]) ,true);
            fileManager.writeInternalFileCsvSameLine(Float.toString(values[1]) ,true);
            fileManager.writeInternalFileCsvSameLine(Float.toString(values[2]) ,true);
            fileManager.writeInternalFileCsvSameLine(Long.toString(timestamp) ,true);
        }
        else if (Sensor.TYPE_ORIENTATION == sensorType){
            fileManager.writeInternalFileCsvSameLine(Float.toString(values[0]) ,true);
            fileManager.writeInternalFileCsvSameLine(Float.toString(values[1]) ,true);
            fileManager.writeInternalFileCsvSameLine(Float.toString(values[2]) ,true);
            fileManager.writeInternalFileCsvSameLine(Long.toString(timestamp) ,true);
        }
        else if (Sensor.TYPE_ROTATION_VECTOR == sensorType){
            fileManager.writeInternalFileCsvSameLine(Float.toString(values[0]) ,true);
            fileManager.writeInternalFileCsvSameLine(Float.toString(values[1]) ,true);
            fileManager.writeInternalFileCsvSameLine(Float.toString(values[2]) ,true);
            fileManager.writeInternalFileCsvSameLine(Float.toString(values[3]) ,true);
            fileManager.writeInternalFileCsvSameLine(Float.toString(values[4]) ,true);
            fileManager.writeInternalFileCsvSameLine(Long.toString(timestamp) ,true);
        }
        else if (SensorTypeString == "GPS"){
            fileManager.writeInternalFileCsvSameLine(SensorTypeString ,true);
            fileManager.writeInternalFileCsvSameLine(Float.toString(values[0]) ,true);
            fileManager.writeInternalFileCsvSameLine(Float.toString(values[1]) ,true);
        }
        else {
            fileManager.writeInternalFileCsvSameLine(SensorTypeString ,true);
            fileManager.writeInternalFileCsvSameLine(Float.toString(values[0]) ,true);
        }
    }





    //----------------------------------------------------------------------------------------------

    /**
     *
     * @param songName
     */
    public void setSongName (String songName){
        this.songName = songName ;
        fileManager = new FileManager( songName,false);
        gps = new GPS(context);
    }
}
