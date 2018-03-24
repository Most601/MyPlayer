package metaextract.nkm.com.myplayer;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;



/**
 * Created by Most601 on 15/12/2017.
 */

public class GPS implements LocationListener {

    private LocationManager locationManager;
    private String latitude;
    private String longitude;
    private Criteria criteria;
    private String provider;

    private static DataReceiveManager DM;

    public GPS(Context context) {

        DM = DataReceiveManager.getInstance(context);

        locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        provider = locationManager.getBestProvider(criteria, true);


        if (ActivityCompat.checkSelfPermission(context , Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return ;
        }
        locationManager.requestLocationUpdates("gps", 5000,0, this);
        setMostRecentLocation(locationManager.getLastKnownLocation(provider));

    }

    private void setMostRecentLocation(Location lastKnownLocation) {

    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }




    @Override
    public void onLocationChanged(Location location) {
        float lon = (float)(location.getLongitude());/// * 1E6);
        float lat = (float)(location.getLatitude());// * 1E6);
        latitude = lat + "";
        longitude = lon + "";
        if (lat != 0 && lon != 0 ){
            float gpsArr[] = {lon ,lat};
            DM.addSensorData_s("GPS" , 0,0,0,gpsArr);
            stopGPS();
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void stopGPS (){
        locationManager.removeUpdates(this);
    }

}
