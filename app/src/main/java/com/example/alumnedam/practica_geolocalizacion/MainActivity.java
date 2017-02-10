package com.example.alumnedam.practica_geolocalizacion;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new BaseDatos(this, "Autobuseros", null, 1);


        Button entrar = (Button) findViewById(R.id.acceder);
        entrar.setOnClickListener(this);




    }


    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.acceder:
                String[] dades = declararVariables();
                BaseDatos bd = new BaseDatos(this, "Autobuseros", null, 2);
                SQLiteDatabase sql= bd.getWritableDatabase();

                Cursor c = sql.rawQuery("SELECT * FROM Otobuses WHERE ? != id_bus AND ? != contrasena", dades);

                if (c.moveToFirst()) {
                    do {

                        Toast toast1 =
                                Toast.makeText(getApplicationContext(),
                                        (c.getString(0) + " - " + c.getString(1)), Toast.LENGTH_SHORT);

                        toast1.show();



                    } while (c.moveToNext());
                }




        }













    }

    private String[] declararVariables() {

        EditText matri = (EditText) findViewById(R.id.matri);
        EditText user = (EditText) findViewById(R.id.user);
        String usuario = user.toString();
        String matricula = matri.toString();

        return  new String[] {usuario, matricula};
    }
}
