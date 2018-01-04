package unicauca.movil.eventmpro.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import unicauca.movil.eventmpro.models.Beacons;

/**
 * Created by RicardoM on 07/12/2017.
 */

public class BeaconsDao {

    static final String TABLE = "beacons";
    static final String C_ID = "_id";
    static final String C_TITLE = "btitulo";
    static final String C_MAJOR = "major";
    static final String C_MINOR = "minor";
    static final String C_BLAT = "blat";
    static final String C_BLONG = "blng";

    SQLiteDatabase db;

    public BeaconsDao(Context context){
        DataBaseHelper helper = new DataBaseHelper(context);
        db = helper.getWritableDatabase();
    }

    public void insert (Beacons beacon){
        ContentValues cV = new ContentValues();
        cV.put(C_ID, beacon.getIdb());
        cV.put(C_TITLE, beacon.getBtitulo());
        cV.put(C_MAJOR, beacon.getMajor());
        cV.put(C_MINOR, beacon.getMinor());
        cV.put(C_BLAT, beacon.getBlat());
        cV.put(C_BLONG, beacon.getBlng());
        db.insert(TABLE,null,cV);
    }

    public void update (Beacons beacon){

        ContentValues cV = new ContentValues();
        cV.put(C_TITLE, beacon.getBtitulo());
        cV.put(C_MAJOR, beacon.getMajor());
        cV.put(C_MINOR, beacon.getMinor());
        cV.put(C_BLAT, beacon.getBlat());
        cV.put(C_BLONG, beacon.getBlng());
        long id = db.update(TABLE,cV,"_id = ?",new String[]{beacon.getIdb()+" "});
    }

    public void delete (long id){
        db.delete(TABLE,"_id = "+id, null);
    }

    public void deleteAll (){
        db.execSQL("DELETE FROM beacons");
    }

    public  Beacons getByid (long id){

        Cursor c = db.rawQuery("SELECT * FROM beacons WHERE _id="+id,null);
        return cursorToBeacons(c);

    }

    public List<Beacons> getAll (){

        Cursor c = db.rawQuery("SELECT * FROM beacons",null);
        return cursorToList(c);

    }

    public List<Beacons> getAByName (String name){

        Cursor c = db.rawQuery("SELECT * FROM beacons WHERE nombre LIKE '%"+name+"%'",null);
        return cursorToList(c);
    }

    private Beacons cursorToBeacons (Cursor c){

        Beacons beacons= null;

        if (c.moveToNext()){
            beacons = new Beacons();
            beacons.setIdb(c.getLong(0));
            beacons.setBtitulo(c.getString(1));
            beacons.setMajor(c.getInt(2));
            beacons.setMinor(c.getInt(3));
            beacons.setBlat(c.getDouble(4));
            beacons.setBlng(c.getDouble(5));
        }
        return beacons;
    }

    private List<Beacons> cursorToList (Cursor c){

        List<Beacons> data = new ArrayList<>();

        for (int i= 0; i< c.getCount();i++){
            Beacons beacon = cursorToBeacons(c);
            data.add(beacon);
        }

        return data;
    }

}
