package com.example.alumnedam.practica_geolocalizacion;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class BaseDatos extends SQLiteOpenHelper{

    String sqlCreate;
    String sqlInsert;

    public BaseDatos(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    /**
     *
     * Cramos la tabla de otobuses para comparar el usuario que vayamos a meter 
     *
     */
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqlCreate= "CREATE TABLE Otobuses (id_bus String, contrasena String)";

        sqlInsert="INSERT INTO Otobuses ('id_bus', 'contrasena') VALUES ('1111A', '1111A')";

        //String fecha = diaActual();

        sqLiteDatabase.execSQL(sqlCreate);

        sqLiteDatabase.execSQL(sqlInsert);

    }

   // private String diaActual() {

      //  Calendar fecha = new GregorianCalendar();


      //  StringBuilder sb = new StringBuilder();

      //  sb.append(fecha.get(Calendar.DAY_OF_MONTH));
      //  sb.append("/");
       // sb.append(fecha.get(Calendar.MONTH));
     //   sb.append("/");
     //   sb.append(fecha.get(Calendar.YEAR));
     //   sb.append(" ");
       // sb.append(fecha.get(Calendar.HOUR_OF_DAY));
       // sb.append(":");
      //  sb.append(fecha.get(Calendar.MINUTE));
        //sb.append(":");
        //sb.append(fecha.get(Calendar.SECOND));

       // return sb.toString();

    //}


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
