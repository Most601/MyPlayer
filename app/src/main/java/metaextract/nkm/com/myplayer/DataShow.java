package metaextract.nkm.com.myplayer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DataShow extends AppCompatActivity {


    private Button b;
    private TextView textgps;
    private TextView textH;
    private LocationManager locationManager;
    private LocationListener listener;
    private GPS gps ;
    private HeartrRate heartrRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_show_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        textgps = (TextView) findViewById(R.id.textgps);
        gps = new GPS(this);

        textH = (TextView) findViewById(R.id.textH);
        heartrRate = new HeartrRate(this);

    }


    public void getGps(View view) {
        textgps.setText(gps.getLatitude() + " ---- " +gps.getLongitude());
    }

    public void getH(View view) {
        textH.setText(heartrRate.getH());




    }
}
