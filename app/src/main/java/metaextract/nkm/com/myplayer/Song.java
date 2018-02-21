package metaextract.nkm.com.myplayer;

/**
 * Created by Most601 on 29/11/2017.
 */

public class Song {

    private long id;
    private String title;
    private String artist;
    private String data;
    private String album;

    public Song(long songID, String songTitle, String songArtist, String songData ,String songAlbum) {
        id = songID;
        title = songTitle;
        artist = songArtist;
        data = songData;
        album = songAlbum;
    }

    public long getID(){return id;}
    public String getTitle(){return title;}
    public String getArtist(){return artist;}
    public String getdata(){return data;}
    public String getAlbum(){return album;}

}
