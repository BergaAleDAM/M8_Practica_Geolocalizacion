package com.example.alumnedam.practica_geolocalizacion;

import android.app.Service;
import android.content.Context;
import android.content.Entity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.CalendarContract;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.sql.ConnectionEventListener;

import static com.google.android.gms.internal.b.df;

/**
 * Created by ALUMNEDAM on 15/02/2017.
 */
public class GeoLocalizacion extends Service {

    private LocationListener listener;
    private LocationManager lM;

    double altitud, latitud;
    String date;
    String matricula;


    public GeoLocalizacion() {}


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

                WService ws = new WService();


                String latitud = String.valueOf(location.getLatitude()),altitud = String.valueOf(location.getLongitude());

                //Toast.makeText(GeoLocalizacion.this, "" + localizacion, Toast.LENGTH_SHORT).show();

                 date = devolverHora();

                ws.execute();

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

            private String devolverHora(){
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                Date ahorita = Calendar.getInstance().getTime();
                return df.format(ahorita);
            }



        };

        lM = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        lM.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0, listener);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Servicio iniciado", Toast.LENGTH_SHORT).show();
        matricula = intent.getStringExtra("matricula");
        Toast.makeText(this, ""+matricula, Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    private class WService extends AsyncTask<Void, Void, Boolean> {

        public WService() {}

        @Override
        protected Boolean doInBackground(Void... cosas) {
            boolean result = true;
            HttpClient httpC= new DefaultHttpClient();//Hay que ir con ojo que esta deprecated

            //HttpPost httpP = new HttpPost("http://192.168.120.81:8080/WebClientRest/webresources/generic");
            httpP.setHeader("content-type", "application/json");//Ojo deprecated

            try{

                JSONObject puntero = new JSONObject();

                puntero.put("matricula", matricula);
                puntero.put("latitud", latitud);
                puntero.put("altitud", altitud);
                puntero.put("fecha", date);

                httpP.setEntity(new StringEntity(puntero.toString()));

                HttpResponse httpR = httpC.execute(httpP);

                if(EntityUtils.toString(httpR.getEntity()).equals("false")){
                    result = true;
                }


            } catch (JSONException | UnsupportedEncodingException |ClientProtocolException e) {
                e.printStackTrace();
                result = false;
            } catch (IOException e) {
                e.printStackTrace();
                result = false;
            }


            return  result;

        }




        @Override
        protected void onPostExecute(Boolean result) {

            if (result) {
                Toast.makeText(GeoLocalizacion.this, "Sitio Marcado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(GeoLocalizacion.this, "No se ha marcado nada inutil", Toast.LENGTH_SHORT).show();
            }
        }

    }

}