package metaextract.nkm.com.myplayer;

import android.graphics.Bitmap;

/**
 * Created by Most601 on 29/11/2017.
 */

public class Song {

    private long id;
    private String title;
    private String artist;
    private String data;
    private String album;
    private String GENRE;
    private String DURATION;
    private Bitmap songImage;

    public Song(long songID, String songTitle ) {
        id = songID;
        title = songTitle;
    }

    public Song(long songID,
                String songTitle,
                String songArtist,
                String songData ,
                String songAlbum ,
                String songGenre , String songDuration ,  Bitmap songImage ) {
        id = songID;
        title = songTitle;
        artist = songArtist;
        data = songData;
        album = songAlbum;
        GENRE = songGenre;
        DURATION = songDuration;
        this.songImage = songImage;
    }

    public long getID(){return id;}
    public String getTitle(){return title;}
    public String getArtist(){return artist;}
    public String getdata(){return data;}
    public String getDuration(){return DURATION;}
    public String getAlbum(){return album;}
    public String getGenre(){return GENRE;}
    public Bitmap getImage(){return songImage;}
}
