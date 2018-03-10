package metaextract.nkm.com.myplayer;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.net.Uri;
import android.content.ContentResolver;
import android.database.Cursor;
import android.widget.SimpleAdapter;

public class PlayListActivity extends ListActivity {


    private ArrayList<Song> songList= new ArrayList<Song>();
    private ListView songView;
    MediaMetadataRetriever metaRetriver;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playlist);

        metaRetriver = new MediaMetadataRetriever();



        //------------------------- getPlayList ----------------------------------------------------

        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

        if(musicCursor!=null && musicCursor.moveToFirst()){
            //get columns
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int titleColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.DISPLAY_NAME);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            int iddata = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.DATA);
            int albumColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.ALBUM);

            String fullPath = musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA));

            //add songs to list
            do {
                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                String thiData = musicCursor.getString(iddata);
                String thiAlbum = musicCursor.getString(albumColumn);
                this.songList.add( new Song( thisId , thisTitle , thisArtist , thiData , thiAlbum ));




//---------------------------- GETIN DATA FROM SONG + PIC ------------------------
//                metaRetriver.setDataSource(thiData);
//                try {
//                    byte[] PicSong = metaRetriver.getEmbeddedPicture();
//                    //Bitmap songImage = BitmapFactory.decodeByteArray(art, 0, art.length);
//                    //album_art.setImageBitmap(songImage);
//                    String ALBUM = metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
//                    String ARTIST = metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
//                    String GENRE = metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE);
//                    Log.e("000000000000000000" , ARTIST);
//                } catch (Exception e) {
//                    album_art.setBackgroundColor(Color.GRAY);
//                    album.setText("Unknown Album");
//                    artist.setText("Unknown Artist");
//                    genre.setText("Unknown Genre");
//                }




            }
            while (musicCursor.moveToNext());
        }


        //--------------- sort abc.... ----------------------
        Collections.sort(songList, new Comparator<Song>(){
            public int compare(Song a, Song b){
                return a.getTitle().compareTo(b.getTitle());
            }
        });
        //----------------------------------------------------


        //--------------- ListView Adapter use ---------------
        songView = getListView();
        SongAdapter songAdt = new SongAdapter(this , R.layout.playlist , songList);
        songView.setAdapter(songAdt);
        // Adding menuItems to ListView

        //-----------------------------------------------------



        //-------------- selecting Song -----------------------
        // selecting single ListView item
        ListView lv =  getListView();
        // listening to single listitem click
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting listitem index
                int songIndex = position;

                // Starting new intent
                Intent in = new Intent(getApplicationContext(),
                        MainActivity.class);
                // Sending songIndex to PlayerActivity
                in.putExtra("songTitle", songIndex);
                setResult(100, in);
                // Closing PlayListView
                finish();
            }
        });

        //-----------------------------------------------------




    }

}







/*
//--------------------------------------------------------------------------------

    public ArrayList<HashMap<String, String>> getPlayList() {
        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            int idCossslumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.DATA);

            String fullPath = musicCursor.getString(musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            // ...process entry...
            //add songs to list
            do {
                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(idCossslumn);
                String thisArtist = musicCursor.getString(artistColumn);
                HashMap<String, String> song = new HashMap<String, String>();
                song.put("songTitle", fullPath);
                song.put("thisArtist", thisArtist);
                songList.add(song);
            }
            while (musicCursor.moveToNext());
        }
        return songList;

    }



//--------------------------------------------------------------------------------



/*
    public ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playlist);
        ArrayList<HashMap<String, String>> songsListData = new ArrayList<HashMap<String, String>>();

        SongsManager plm = new SongsManager();
        // get all songs from sdcard
        this.songsList = plm.getPlayList();

        // looping through playlist
        for (int i = 0; i < songsList.size(); i++) {
            // creating new HashMap
            HashMap<String, String> song = songsList.get(i);

            // adding HashList to ArrayList
            songsListData.add(song);
        }

        // Adding menuItems to ListView
        ListAdapter adapter = new SimpleAdapter(this, songsListData,
                R.layout.playlist_item, new String[] { "songTitle" }, new int[] {
                R.id.songTitle });

        setListAdapter(adapter);

        // selecting single ListView item
        ListView lv =  getListView();
        // listening to single listitem click
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // getting listitem index
                int songIndex = position;

                // Starting new intent
                Intent in = new Intent(getApplicationContext(),
                        MainActivity.class);
                // Sending songIndex to PlayerActivity
                in.putExtra("songIndex", songIndex);
                setResult(100, in);
                // Closing PlayListView
                finish();
            }
        });
    }
*/

















