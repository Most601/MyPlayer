package metaextract.nkm.com.myplayer;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity  implements   OnCompletionListener, SeekBar.OnSeekBarChangeListener {

    private ImageButton btnPlay;
    private ImageButton btnForward;
    private ImageButton btnBackward;
    private ImageButton btnNext;
    private ImageButton btnPrevious;
    private ImageButton btnPlaylist;
    private ImageButton btnRepeat;
    private ImageButton btnShuffle;
    private SeekBar songProgressBar;
    private TextView songTitleLabel;
    private TextView songCurrentDurationLabel;
    private TextView songTotalDurationLabel;
    // Media Player
    private  MediaPlayer mp;
    // Handler to update UI timer, progress bar etc,.
    private Handler mHandler = new Handler();;
   ////////////////////////////////---------------------- private SongsManager songManager;
    private Utilities utils;
    private int seekForwardTime = 5000; // 5000 milliseconds
    private int seekBackwardTime = 5000; // 5000 milliseconds
    private int currentSongIndex = 0;
    private boolean isShuffle = false;
    private boolean isRepeat = false;
    private ArrayList<Song> songsList = new ArrayList<Song>();
    // FILE






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
        btnPlaylist = (ImageButton) findViewById(R.id.btnPlaylist);
        btnRepeat = (ImageButton) findViewById(R.id.btnRepeat);
        btnShuffle = (ImageButton) findViewById(R.id.btnShuffle);
        songProgressBar = (SeekBar) findViewById(R.id.songProgressBar);
        songTitleLabel = (TextView) findViewById(R.id.songTitle);
        songCurrentDurationLabel = (TextView) findViewById(R.id.songCurrentDurationLabel);
        songTotalDurationLabel = (TextView) findViewById(R.id.songTotalDurationLabel);

        // Mediaplayer
        mp = new MediaPlayer();
       //////////////////////////////--------------------- songManager = new SongsManager();
        utils = new Utilities();

        // Listeners
        songProgressBar.setOnSeekBarChangeListener(this); // Important
        mp.setOnCompletionListener(this); // Important
        // Getting all songs list






        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);


        if (permissionCheck == 0 ){
            songsList = getPlayList();
            //--------------- sort abc.... ----------------------
            Collections.sort(songsList, new Comparator<Song>(){
                public int compare(Song a, Song b){
                    return a.getTitle().compareTo(b.getTitle());
                }
            });
            //----------------------------------------------------
        }





       //---------------------------------------------------------------------



        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }







        //--------------------------------------------------------------

        @Override
        public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
            switch (requestCode) {
                case 1: {
                    // If request is cancelled, the result arrays are empty.
                    if (grantResults.length > 0
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                        songsList = getPlayList();


                        //--------------- sort abc.... ----------------------
                        Collections.sort(songsList, new Comparator<Song>(){
                            public int compare(Song a, Song b){
                                return a.getTitle().compareTo(b.getTitle());
                            }
                        });
                        //----------------------------------------------------

                        // permission was granted, yay! Do the
                        // contacts-related task you need to do.

                    } else {

                        // permission denied, boo! Disable the
                        // functionality that depends on this permission.
                    }


                    return;
                }

                // other 'case' lines to check for other
                // permissions this app might request
            }



        }

//-------------------------------------------------------------------------

    public ArrayList<Song> getPlayList() {
        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int titleColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.DISPLAY_NAME);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            int iddata = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.DATA);


            //add songs to list
            do {
                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                String thiData = musicCursor.getString(iddata);


                this.songsList.add( new Song( thisId , thisTitle , thisArtist , thiData));
            }
            while (musicCursor.moveToNext());
        }
        return songsList;

    }









    /**
     * Function to play a song
     * @param songIndex - index of song
     * */
    public void  playSong(int songIndex){
        // Play song
        try {





            Uri uri= Uri.parse(songsList.get(songIndex).getdata());
            mp.reset();
            mp.setDataSource(this,uri);
            mp.prepare();
            mp.start();
            // Displaying Song title
            String songTitle = songsList.get(songIndex).getTitle();
            songTitleLabel.setText(songTitle);

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

//---------------------------------------------------------------------------------
    /**
     * Update timer on seekbar
     * */
    public void updateProgressBar() {
        mHandler.postDelayed( mUpdateTimeTask, 100);
    }
    /**
     * Background Runnable thread
     * */
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = mp.getDuration();
            long currentDuration = mp.getCurrentPosition();

            // Displaying Total Duration time
            songTotalDurationLabel.setText(""+utils.milliSecondsToTimer(totalDuration));
            // Displaying time completed playing
            songCurrentDurationLabel.setText(""+utils.milliSecondsToTimer(currentDuration));

            // Updating progress bar
            int progress = (int)(utils.getProgressPercentage(currentDuration, totalDuration));
            //Log.d("Progress", ""+progress);
            songProgressBar.setProgress(progress);

            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 100);
        }
    };


//---------------------------------------------------------------------------------











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

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
        int totalDuration = mp.getDuration();
        int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);

        // forward or backward to certain seconds
        mp.seekTo(currentPosition);

        // update timer progress again
        updateProgressBar();
    }

    //---------------------------------------------------------------------------------


    public void ClicPlaylist(View view) {
        Intent i = new Intent(getApplicationContext(), PlayListActivity.class);
        startActivityForResult(i, 100);
    }

    /**
     * Receiving song index from playlist view
     * and play the song
     * */
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 100){
            currentSongIndex = data.getExtras().getInt("songTitle");
            // play selected song
            playSong(currentSongIndex);
        }

    }

    //--------------------------------------------------------------------------------

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
    }

    public void Previous(View view) {
        if(currentSongIndex > 0){
            playSong(currentSongIndex - 1);
            currentSongIndex = currentSongIndex - 1;
        }else{
            // play last song
            playSong(songsList.size() - 1);
            currentSongIndex = songsList.size() - 1;
        }
    }

    public void play(View view) {
        // check for already playing
        if(mp.isPlaying()){
            if(mp!=null){
                mp.pause();
                // Changing button image to play button
                btnPlay.setImageResource(R.drawable.img_btn_play);
            }
        }else{
            // Resume song
            if(mp!=null){
                mp.start();
                // Changing button image to pause button
                btnPlay.setImageResource(R.drawable.img_btn_pause);
            }
        }

    }


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

    }

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
    }

    //--------------------------------------------------------------------------------

    public void Repeat(View view) {
        if(isRepeat){
            isRepeat = false;
            Toast.makeText(getApplicationContext(), "Repeat is OFF", Toast.LENGTH_SHORT).show();
            btnRepeat.setImageResource(R.drawable.img_btn_repeat);
        }else{
            // make repeat to true
            isRepeat = true;
            Toast.makeText(getApplicationContext(), "Repeat is ON", Toast.LENGTH_SHORT).show();
            // make shuffle to false
           /////    isShuffle = false;

            btnRepeat.setImageResource(R.drawable.img_btn_repeat_pressed);
           /////    btnShuffle.setImageResource(R.drawable.btn_shuffle);
        }
    }

    public void Shuffle(View view) {
        if(isShuffle){
            isShuffle = false;
            Toast.makeText(getApplicationContext(), "Shuffle is OFF", Toast.LENGTH_SHORT).show();
            ////    btnShuffle.setImageResource(R.drawable.img_btn_shuffle);
        }else{
            // make repeat to true
            isShuffle= true;
            Toast.makeText(getApplicationContext(), "Shuffle is ON", Toast.LENGTH_SHORT).show();
            // make shuffle to false
             ////   isRepeat = false;
            btnShuffle.setImageResource(R.drawable.img_btn_shuffle_pressed);
             ////  btnRepeat.setImageResource(R.drawable.btn_repeat);
        }
    }


    //---------------------------------------------------------------------------------

    @Override
    public void onDestroy(){
        super.onDestroy();

        // מפיל שעושים אחורה אבל לא שומר על הנגן -------------
        mp.release();
        // מפיל שעושים אחורה -------------
    }



    //---------------------------------------------------------------------------------





    public void DataInformation2(View view) {
        Intent i = new Intent(getApplicationContext(), DataShow.class);
        startActivityForResult(i, 100);





    }

    //----------------------------------------------------------------------------------


}
