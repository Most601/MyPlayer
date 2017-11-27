package metaextract.nkm.com.myplayer;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Most601 on 27/11/2017.
 */

public class SongsManager {

    final String PATH = new String("/sdcard/Movies/");
    private ArrayList<HashMap<String,String> > songlist = new ArrayList<HashMap<String, String>>();

    public SongsManager(){};

    public ArrayList<HashMap<String, String>> getPlayList(){
        File home = new File(PATH);

        if (home.listFiles(new FileExtensionFilter()).length > 0) {
            for (File file : home.listFiles(new FileExtensionFilter())) {
                HashMap<String, String> song = new HashMap<String, String>();
                song.put("songTitle", file.getName().substring(0, (file.getName().length() - 4)));
                song.put("songPath", file.getPath());

                // Adding each song to SongList
                songlist.add(song);
            }
        }
        // return songs list array
        return songlist;
    }

    /**
     * Class to filter files which are having .mp3 extension
     * */
    class FileExtensionFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            return (name.endsWith(".mp3") || name.endsWith(".MP3"));
        }
    }




}
