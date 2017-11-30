package metaextract.nkm.com.myplayer;

/**
 * Created by Most601 on 29/11/2017.
 */

public class Song {

    private long id;
    private String title;
    private String artist;
    private String data;

    public Song(long songID, String songTitle, String songArtist, String songData) {
        id=songID;
        title=songTitle;
        artist=songArtist;
        data=songData;
    }

    public long getID(){return id;}
    public String getTitle(){return title;}
    public String getArtist(){return artist;}
    public String getdata(){return data;}


}
