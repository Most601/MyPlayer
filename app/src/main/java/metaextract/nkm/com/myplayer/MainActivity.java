package metaextract.nkm.com.myplayer;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnCompletionListener, SeekBar.OnSeekBarChangeListener {
    //--- The buttons of the player: forward, backward, next, previous, play, repeat and shuffle.---
    private ImageButton btnForward;
    private ImageButton btnBackward;
    private ImageButton btnNext;
    private ImageButton btnPrevious;
    private ImageButton btnPlay;
    private ImageButton btnRepeat;
    private ImageButton btnShuffle;
    private ImageView songImageView;
    //----------------------------------------------------------------------------------------------
    //--- Titles: song name, current duration of the song and the duration of the song.-------------
    private TextView songTitleLabel;
    private TextView songCurrentDurationLabel;
    private TextView songTotalDurationLabel;
    //----------------------------------------------------------------------------------------------
    private SeekBar songProgressBar; //The seekbar.
    private MediaPlayer mp; //The media player.
    private Handler mHandler = new Handler(); //Handler to update UI timer, progress bar.
    private Utilities utils; //The object SeekBar named songProgressBar using the Utilities class.

    //--- The data we write to the file: -----------------------------------------------------------
    private int progress; //The current position in the SeekBar (percentage).
    private int currentSongIndex = 0; //The id song.
    private String LastSongName = "..."; //The last song that has been played.
    private String currentSongName = ""; //The song playing now.
    private String songTotalDuration = ""; //The duration of the song.
    private String songCurrentDuration = "00:00"; //The current duration of the song.
    //----------------------------------------------------------------------------------------------

    //--- The time to move for the forward and backward buttons ------------------------------------
    private final int seekForwardTime = 5000; // 5000 milliseconds
    private final int seekBackwardTime = 5000; // 5000 milliseconds
    //----------------------------------------------------------------------------------------------

    private boolean isShuffle = false; //If true the songs will be shuffled.
    private boolean isRepeat = false; //If true the song will be repeated.
    // FILE
    private SendToWear STW ;
    private MessageReceiveManager MRM;
    //------------------//
    private DataReceiveManager DM_SENSOR;
    private DataReceiveManager DM_ACC;
    private DataReceiveManager DM_Gravity;
    private DataReceiveManager DM_Pressure;
    //------------------//
    private DataReceiveManager DM_Activity;
    //------------------//
    private MediaMetadataRetriever metaRetriver;
    private ArrayList<Song> songsList = new ArrayList<Song>();
    private DataReceiveManager DM_SONGLIST ;
    private Calendar cc = Calendar.getInstance();
    private int year=cc.get(Calendar.YEAR);
    private int month=cc.get(Calendar.MONTH);
    private int mDay = cc.get(Calendar.DAY_OF_MONTH);
    private int mHour = cc.get(Calendar.HOUR_OF_DAY);
    private int mMinute = cc.get(Calendar.MINUTE);
    //------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // All player buttons
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        btnForward = (ImageButton) findViewById(R.id.btnForward);
        btnBackward = (ImageButton) findViewById(R.id.btnBackward);
        btnNext = (ImageButton) findViewById(R.id.btnNext);
        btnPrevious = (ImageButton) findViewById(R.id.btnPrevious);
        btnRepeat = (ImageButton) findViewById(R.id.btnRepeat);
        btnShuffle = (ImageButton) findViewById(R.id.btnShuffle);
        songProgressBar = (SeekBar) findViewById(R.id.songProgressBar);
        songTitleLabel = (TextView) findViewById(R.id.songTitle);
        songCurrentDurationLabel = (TextView) findViewById(R.id.songCurrentDurationLabel);
        songTotalDurationLabel = (TextView) findViewById(R.id.songTotalDurationLabel);
        songImageView = (ImageView) findViewById(R.id.songImage);
        //------------------------------------------------------------------------------------------



        // Mediaplayer
        mp = new MediaPlayer();
        utils = new Utilities();

        // Listeners
        songProgressBar.setOnSeekBarChangeListener(this); // Important
        mp.setOnCompletionListener(this); // Important

        // Data + Message
        STW = SendToWear.getInstance(this);
        MRM = MessageReceiveManager.getInstance(this);

        DM_SENSOR = DataReceiveManager.getInstance(this);
        DM_ACC = DataReceiveManager.getInstanceACC(this);
        DM_Gravity = DataReceiveManager.getInstanceGravity(this);
        DM_Pressure = DataReceiveManager.getInstancePressure(this);
        //------------------------- permission -----------------------------------------------------

        //------- Checking for permission ------
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        //------- If there is permission then we will get the song list ------
        if (permissionCheck == 0 ){
            songsList = getPlayList();
            //--------------- sort abc.... ----------------------
            Collections.sort(songsList, new Comparator<Song>(){
                public int compare(Song a, Song b){
                    return a.getTitle().compareTo(b.getTitle());
                }
            });
            //----------------------------------------------------
            if(songsList.size() > 0) {
                currentSongName = songsList.get(1).getTitle();
            }
            //------------------- SONGLIST FILE ----------------------------------------------------
            year=cc.get(Calendar.YEAR);
            month=cc.get(Calendar.MONTH);
            mDay = cc.get(Calendar.DAY_OF_MONTH);
            mHour = cc.get(Calendar.HOUR_OF_DAY);
            mMinute = cc.get(Calendar.MINUTE);
            DM_SONGLIST = new  DataReceiveManager(this ,
                    "Date "+String.format("%02d,%02d,%d", mDay , month, year)+" - SONGLIST");
            DM_SONGLIST.addSongList(songsList);
            //------------------- Activity FILE ----------------------------------------------------
            DM_Activity = new DataReceiveManager(this ,"Activity" );
            DM_Activity.ActivityFILE(
                    currentSongName,
                    currentSongIndex ,
                    songTotalDuration ,
                    LastSongName,
                    progress+"%",
                    "APP START");
        }

        //------- Checks whether there was a request for permission ------
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //------ If permission denied ------
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            }
            //------ Requesting permission ------
            else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.BODY_SENSORS,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                        1);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    //----------------------------------------------------------------------------------------------

    //----- Getting approval for permission ------
    /**
     * Getting approval for permission
     * @param requestCode - The code for the specific permission
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            //------ READ_EXTERNAL_STORAGE -------
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED //READ_EXTERNAL_STORAGE
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED //BODY_SENSORS
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED)//ACCESS_COARSE_LOCATION
                {
                    songsList = getPlayList();
                    //--------------- sort abc.... ----------------------
                    Collections.sort(songsList, new Comparator<Song>(){
                            public int compare(Song a, Song b){
                                return a.getTitle().compareTo(b.getTitle());
                            }
                    });
                    //----------------------------------------------------
                    if(songsList.size() > 0) {
                        currentSongName = songsList.get(0).getTitle();
                    }
                    //------------------- SONGLIST FILE --------------------------------------------------------

                    year=cc.get(Calendar.YEAR);
                    month=cc.get(Calendar.MONTH);
                    mDay = cc.get(Calendar.DAY_OF_MONTH);
                    mHour = cc.get(Calendar.HOUR_OF_DAY);
                    mMinute = cc.get(Calendar.MINUTE);
                    DM_SONGLIST = new  DataReceiveManager(this ,
                            "Date  "+String.format("%02d,%02d,%d", mDay , month, year)+" - SONGLIST");
                    DM_SONGLIST.addSongList(songsList);


                    //------------------- Activity FILE --------------------------------------------------------

                    DM_Activity = new DataReceiveManager(this ,"Activity" );
                    DM_Activity.ActivityFILE(
                            currentSongName,
                            currentSongIndex ,
                            songTotalDuration ,
                            LastSongName,
                            progress+"%",
                            "APP START");

                    //----------------------------------------------------
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(i);
                }
                return;
            }
                // other 'case' lines to check for other
                // permissions this app might request
        }
    }



    //------------------------- getPlayList ----------------------------------------------------

    /**
     * Moves the list of songs to the array
     * @return
     */
    public ArrayList<Song> getPlayList() {
        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

        metaRetriver = new MediaMetadataRetriever();

        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            int titleColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.DISPLAY_NAME);
            int iddata = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.DATA);
            int albumColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.ALBUM);
            int DurationColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.DURATION);
            //add songs to list
            do {
                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                String thiData = musicCursor.getString(iddata);
                String thiAlbum = musicCursor.getString(albumColumn);
                String DURATION =  utils.milliSecondsToTimer(musicCursor.getLong(DurationColumn));

                byte[] PicSong = null;
                Bitmap songImage = null;
                String genre = null;
                metaRetriver.setDataSource(thiData);
                genre = metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE);
                PicSong = metaRetriver.getEmbeddedPicture();
                try {
                    songImage = BitmapFactory.decodeByteArray(PicSong, 0, PicSong.length);
                } catch (Exception e) {
                }


                this.songsList.add( new Song(
                        thisId , thisTitle , thisArtist , thiData ,
                        thiAlbum , genre , DURATION , songImage));


            }
            while (musicCursor.moveToNext());
        }
        return songsList;
    }


    //------------------------- playSong -----------------------------------------------------------
    /**
     * Function to play a song
     * @param songIndex - index of song
     * */
     public void playSong(int songIndex){
        // Play song
        try {
            STW.sendMessage("Act" , "start");
            Uri uri= Uri.parse(songsList.get(songIndex).getdata());
            LastSongName = currentSongName;
            currentSongName = songsList.get(songIndex).getTitle();
            DM_ACC.setSongName(currentSongName+"_ACC");
            DM_SENSOR.setSongName(currentSongName);
            DM_Gravity.setSongName(currentSongName+"_Gravity");
            DM_Pressure.setSongName(currentSongName+"_Pressure");
            mp.reset();
            mp.setDataSource(this,uri);
            mp.prepare();
            mp.start();
            // Displaying Song title
            String songTitle = songsList.get(songIndex).getTitle();
            songTitleLabel.setText(songTitle);
            if (songsList.get(songIndex).getImage() == null ){
                songImageView.setImageResource(R.drawable.adele );
            }else {
                songImageView.setImageBitmap( songsList.get(songIndex).getImage());
            }

            // Changing Button Image to pause image
            btnPlay.setImageResource(R.drawable.img_btn_pause);
            // set Progress bar values
            songProgressBar.setProgress(0);
            songProgressBar.setMax(100);
            // Updating progress bar
            updateProgressBar();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //------------------------- updateProgressBar --------------------------------------------------
    /**
     * Update timer on seekbar
     */
    public void updateProgressBar() {
        mHandler.postDelayed( mUpdateTimeTask, 100);
    }
    /**
     * Background Runnable thread
     */
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
         //   long totalDuration = mp.getDuration();
         //   long currentDuration = mp.getCurrentPosition();
/////////////////////////// זה מה שגרם לנגן ליפול שחוזרים ///////////////////////////
            long totalDuration;
            long currentDuration ;
            try{
                totalDuration = mp.getDuration();
                currentDuration = mp.getCurrentPosition();
            }catch (Exception e){
                totalDuration = 0;
                currentDuration = 0;
            }
////////////////////////////////////////////////////////////////////////////////////
            // Displaying Total Duration time
            songTotalDuration = utils.milliSecondsToTimer(totalDuration);
                    songTotalDurationLabel.setText(" / "+songTotalDuration );
            // Displaying time completed playing
            songCurrentDuration = utils.milliSecondsToTimer(currentDuration);
            songCurrentDurationLabel.setText(" "+songCurrentDuration);
            // Updating progress bar
            progress = (utils.getProgressPercentage(currentDuration, totalDuration));
            //Log.d("Progress", ""+progress);
            songProgressBar.setProgress(progress);

            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 100);
        }
    };


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
        int totalDuration = mp.getDuration();
        int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);
        DM_Activity.ActivityFILE(
                currentSongName,
                currentSongIndex ,
                songTotalDuration ,
                LastSongName ,
                "From : "+progress+"% | TO : "+seekBar.getProgress()+"%",
                "progress BAR");

        // forward or backward to certain seconds
        mp.seekTo(currentPosition);

        // update timer progress again
        updateProgressBar();

    }

//------------------- onCompletion  השיר בסתיים ----------------------------------------------------
    /**
     * In the song completeness, checks for both isRepeat and isShuffle ,  and works accordingly
     * @param mp
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        // check for repeat is ON or OFF
        if(isRepeat){
            // repeat is on play same song again
            playSong(currentSongIndex);
        } else if(isShuffle){
            // shuffle is on - play a random song
            Random rand = new Random();
            currentSongIndex = rand.nextInt((songsList.size() - 1) - 0 + 1) + 0;
            playSong(currentSongIndex);
        } else{
            // no repeat or shuffle ON - play next song
            if(currentSongIndex < (songsList.size() - 1)){
                playSong(currentSongIndex + 1);
                currentSongIndex = currentSongIndex + 1;
            }else{
                // play first song
                playSong(0);
                currentSongIndex = 0;
            }
        }
    }


    //------------------ Playlist Activity ---------------------------------------------------------
    /**
     * Button Go to song list
     * @param view
     */
    public void ClicPlaylist(View view) {
        Intent i = new Intent(getApplicationContext(), PlayListActivity.class);
        startActivityForResult(i, 100);
    }
    /**
     * Receiving song index from playlist view
     * and play the song
     * */
    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 100){
            currentSongIndex = data.getExtras().getInt("songTitle");
            // play selected song
            playSong(currentSongIndex);
            DM_Activity.ActivityFILE(
                    currentSongName,
                    currentSongIndex ,
                    songTotalDuration ,
                    LastSongName,
                    progress+"%",
                    "fROM LIST");
        }

    }

    //-------------------------- onNewIntent -------------------------------------------------------

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if ( intent.getExtras().getInt("10") == 1 ){
            btnPlay.performClick();
        }
        if ( intent.getExtras().getInt("20") == 2 ){
            btnBackward.performClick();
        }
        if ( intent.getExtras().getInt("30") == 3 ){
            btnPrevious.performClick();
        }
        if ( intent.getExtras().getInt("40") == 4 ){
            btnNext.performClick();
        }
        if ( intent.getExtras().getInt("50") == 5 ){
            btnForward.performClick();
        }
    }

    //------------------- backward -------------------------------------------------------

    public void backward(View view) {
        // get current song position
        int currentPosition = mp.getCurrentPosition();
        // check if seekBackward time is greater than 0 sec
        if(currentPosition - seekBackwardTime >= 0){
            // forward song
            mp.seekTo(currentPosition - seekBackwardTime);
        }else{
            // backward to starting position
            mp.seekTo(0);
        }
        DM_Activity.ActivityFILE(
                currentSongName,
                currentSongIndex ,
                songTotalDuration ,
                LastSongName ,
                progress+"%",
                "backward");

    }

    //------------------- Previous -----------------------------------------------------------------

    public void Previous(View view) {

        if(currentSongIndex > 0){
            playSong(currentSongIndex - 1);
            currentSongIndex = currentSongIndex - 1;
        }else{
            // play last song
            playSong(songsList.size() - 1);
            currentSongIndex = songsList.size() - 1;
        }
        DM_Activity.ActivityFILE(
                currentSongName,
                currentSongIndex ,
                songTotalDuration ,
                LastSongName,
                progress+"%",
                "Previous");

    }

    //------------------- play ---------------------------------------------------------------------

    public void play(View view) {
        // check for already playing
        if(mp.isPlaying()){
            if(mp!=null){
                mp.pause();

                //----------------
                STW.sendMessage("Act" , "stop");
                DM_Activity.ActivityFILE(
                        currentSongName,
                        currentSongIndex ,
                        songTotalDuration ,
                        LastSongName,
                        progress+"%",
                        "stop");
                //----------------
                // Changing button image to play button
                btnPlay.setImageResource(R.drawable.img_btn_play);
            }
        }else{
            // Resume song
            if(mp!=null){
                mp.start();
                //----------------
                STW.sendMessage("Act" , "start");
                DM_Activity.ActivityFILE(
                        currentSongName,
                        currentSongIndex ,
                        songTotalDuration ,
                        LastSongName,
                        progress+"%",
                        "play");
                //----------------
                // Changing button image to pause button
                btnPlay.setImageResource(R.drawable.img_btn_pause);
            }
        }

    }

    //------------------- Forward ------------------------------------------------------------------

    public void Forward(View view) {
        // get current song position
        int currentPosition = mp.getCurrentPosition();
        // check if seekForward time is lesser than song duration
        if(currentPosition + seekForwardTime <= mp.getDuration()){
            // forward song
            mp.seekTo(currentPosition + seekForwardTime);
        }else{
            // forward to end position
            mp.seekTo(mp.getDuration());
        }
        DM_Activity.ActivityFILE(
                currentSongName,
                currentSongIndex ,
                songTotalDuration ,
                LastSongName,
                progress+"%",
                "Forward");

    }

    //------------------- Next ---------------------------------------------------------------------

    public void Next(View view) {

        // check if next song is there or not
        if(currentSongIndex < (songsList.size() - 1)){
            playSong(currentSongIndex + 1);
            currentSongIndex = currentSongIndex + 1;
        }else{
            // play first song
            playSong(0);
            currentSongIndex = 0;
        }
        DM_Activity.ActivityFILE(
                currentSongName,
                currentSongIndex ,
                songTotalDuration ,
                LastSongName,
                progress+"%",
                "Next");

    }

    //----------------- Repeat ---------------------------------------------------------------------

    public void Repeat(View view) {
        if(isRepeat){
            isRepeat = false;
            Toast.makeText(getApplicationContext(), "Repeat is OFF", Toast.LENGTH_SHORT).show();
            DM_Activity.ActivityFILE(
                    currentSongName,
                    currentSongIndex ,
                    songTotalDuration ,
                    LastSongName,
                    progress+"%",
                    "Repeat OFF");
            btnRepeat.setImageResource(R.drawable.img_btn_repeat);
        }else{
            // make repeat to true
            isRepeat = true;
            Toast.makeText(getApplicationContext(), "Repeat is ON", Toast.LENGTH_SHORT).show();
            // make shuffle to false
           /////    isShuffle = false;
            DM_Activity.ActivityFILE(
                    currentSongName,
                    currentSongIndex ,
                    songTotalDuration ,
                    LastSongName,
                    progress+"%",
                    "Repeat ON");
            btnRepeat.setImageResource(R.drawable.img_btn_repeat_pressed);
           /////    btnShuffle.setImageResource(R.drawable.btn_shuffle);
        }
    }

    //----------------- Shuffle --------------------------------------------------------

    public void Shuffle(View view) {
        if(isShuffle){
            isShuffle = false;
            Toast.makeText(getApplicationContext(), "Shuffle is OFF", Toast.LENGTH_SHORT).show();
            DM_Activity.ActivityFILE(
                    currentSongName,
                    currentSongIndex ,
                    songTotalDuration ,
                    LastSongName,
                    progress+"%",
                    "Shuffle OFF");
            btnShuffle.setImageResource(R.drawable.img_btn_shuffle);
        }else{
            // make repeat to true
            isShuffle= true;
            Toast.makeText(getApplicationContext(), "Shuffle is ON", Toast.LENGTH_SHORT).show();
            DM_Activity.ActivityFILE(
                    currentSongName,
                    currentSongIndex ,
                    songTotalDuration ,
                    LastSongName,
                    progress+"%",
                    "Shuffle ON");
            // make shuffle to false
             ////   isRepeat = false;
            btnShuffle.setImageResource(R.drawable.img_btn_shuffle_pressed);
             ////  btnRepeat.setImageResource(R.drawable.btn_repeat);
        }
    }


    //--------------- onDestroy ----------------------------------------------------------
    @Override
    protected void onRestart() {
        super.onRestart();
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        mp.release();
        DM_Activity.ActivityFILE(
                currentSongName,
                currentSongIndex ,
                songTotalDuration ,
                LastSongName,
                progress+"%",
                "Destroy");
       }

    //---------------- Info button ---------------------------------------------------------

    public void DataInformation2(View view) {
        Intent i = new Intent(getApplicationContext(), DataShow.class);
        startActivityForResult(i, 100);
    }

    //----------------------------------------------------------------------------------







    //----------------------------------------------------------------------------------


}
