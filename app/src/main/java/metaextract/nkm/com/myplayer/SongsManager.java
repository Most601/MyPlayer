/*
package metaextract.nkm.com.myplayer;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

*/
/**
 * Created by Most601 on 27/11/2017.
 *//*


public class SongsManager extends Activity {


    ArrayList<HashMap<String,String> > songList = new ArrayList<HashMap<String, String>>();

    public SongsManager() {};

    public ArrayList<HashMap<String, String>> getPlayList(){
        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

        if(musicCursor!=null && musicCursor.moveToFirst()){
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            int idCossslumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.DISPLAY_NAME);

            //add songs to list
            do {
                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(idCossslumn);
                String thisArtist = musicCursor.getString(artistColumn);
                HashMap<String, String> song = new HashMap<String, String>();
                song.put("songTitle", thisTitle );
                song.put("thisArtist", thisArtist );
                songList.add( song);
            }
            while (musicCursor.moveToNext());
        }
        return songList;
    }








}
*/
