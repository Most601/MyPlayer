package metaextract.nkm.com.myplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;



public class SongAdapter extends ArrayAdapter<Song> {
	
	private ArrayList<Song> SongList;
	private LayoutInflater songInf;
	

	/**
	 *
	 * @param context
	 * @param resource
	 * @param songList List of Song
	 */
	public SongAdapter(Context context, int resource , ArrayList<Song> songList){
		super(context, resource, songList);
		SongList = songList;
		songInf = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return SongList.size();
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;
		if (v == null) {
			LayoutInflater vi;
			vi = LayoutInflater.from(getContext());
			v = vi.inflate(R.layout.playlist_item, null);
		}
		Song p = getItem(position);
		if (p != null) {

			TextView tt1 = (TextView) v.findViewById(R.id.songTitle);

			if (tt1 != null) {
				tt1.setText(p.getTitle());
			}

		}
		return v;





		/*
		//map to song layout
		LinearLayout songLay = (LinearLayout)songInf.inflate
				(R.layout.playlist_item, parent, false);
		//get title and artist views
		TextView songView = (TextView)songLay.findViewById(R.id.songTitle);
		////////////	TextView artistView = (TextView)songLay.findViewById(R.id.song_artist);
		//get song using position
		Song currSong = songs.get(position);
		//get title and artist strings
		songView.setText(currSong.getTitle());
		/////////////    artistView.setText(currSong.getArtist());
		//set position as tag
		songLay.setTag(position);
		return songLay;
		*/



	}

}
