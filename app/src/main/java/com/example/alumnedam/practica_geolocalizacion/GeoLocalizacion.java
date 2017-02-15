package com.example.alumnedam.practica_geolocalizacion;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by ALUMNEDAM on 15/02/2017.
 */
public class GeoLocalizacion extends Service {

    private LocationListener listener;
    private LocationManager locationManager;

    //ConexionWebService con = new ConexionWebService();
    String matricula;


    public GeoLocalizacion() {
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @SuppressWarnings("MissingPermission")
    @Override
    public void onCreate() {
        super.onCreate();
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                String localizacion;
                String latitud = String.valueOf(location.getLatitude()), longitut = String.valueOf(location.getLongitude());
                localizacion = latitud + "  " + longitut;
                Toast.makeText(GeoLocalizacion.this, "" + localizacion, Toast.LENGTH_SHORT).show();
                //con.execute(
                //       matricula,
                //      latitud,
                //     longitut);

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }



        };
    }
    //locationManager=(LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);


    //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,2000,0,listener);



}