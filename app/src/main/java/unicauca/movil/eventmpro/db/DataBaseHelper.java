package unicauca.movil.eventmpro.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by RicardoM on 12/10/2016.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME="EventmPro.db";
    public static int VERSION = 1;


    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(" CREATE TABLE ponente (_id INTEGER PRIMARY KEY AUTOINCREMENT"
                +", nombre VARCHAR"
                +", apellidos VARCHAR"
                +", empresa VARCHAR"
                +", estudios VARCHAR"
                +", img VARCHAR"
                +", experiencia VARCHAR"
                +", formacioninternacional VARCHAR"
                +", habilidades VARCHAR"
                +", tipo VARCHAR"
                +")"
            );

        db.execSQL(" CREATE TABLE dias (_id INTEGER PRIMARY KEY"
                +", idd INTEGER"
                +", ido INTEGER"
                +", hora VARCHAR"
                +", evento VARCHAR"
                +", titulo VARCHAR"
                +", conferencista VARCHAR"
                +", empresa VARCHAR"
                +", lugar VARCHAR"
                +")"
        );

        db.execSQL(" CREATE TABLE notification (_id INTEGER PRIMARY KEY AUTOINCREMENT"
                +", mensaje VARCHAR"
                +", fecha DATE"
                +", hora TIME"
                +")"
        );

        db.execSQL(" CREATE TABLE evento (_id INTEGER PRIMARY KEY"
                +", nombre VARCHAR"
                +", objetivo VARCHAR"
                +", lugar VARCHAR"
                +", descripcion VARCHAR"
                +", fecha VARCHAR"
                +")"
        );

        db.execSQL(" CREATE TABLE conections (_id INTEGER PRIMARY KEY"
                +", dias VARCHAR"
                +", ponentes VARCHAR"
                +", ubicacion VARCHAR"
                +", beacons VARCHAR"
                +")"
        );

        db.execSQL(" CREATE TABLE ubicacion (_id INTEGER PRIMARY KEY"
                +", titulo VARCHAR"
                +", lat DOUBLE"
                +", lng DOUBLE"
                +")"
        );
        db.execSQL(" CREATE TABLE beacons (_id INTEGER PRIMARY KEY"
                +", uuid VARCHAR"
                +", major VARCHAR"
                +", minor VARCHAR"
                +")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE ponente");
            db.execSQL("DROP TABLE dias");
            db.execSQL("DROP TABLE notification");
            db.execSQL("DROP TABLE conections");
            db.execSQL("DROP TABLE ubicacion");
            db.execSQL("DROP TABLE beacons");
            onCreate(db);
    }
}
