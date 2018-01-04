package unicauca.movil.eventmpro.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import unicauca.movil.eventmpro.models.Conections;

/**
 * Created by RicardoM on 07/12/2017.
 */

public class ConectionsDao {

    static final String TABLE = "conections";
    static final String C_ID = "_id";
    static final String C_DAYS = "dias";
    static final String C_SPEAKERS = "ponentes";
    static final String C_LOCATION = "ubicacion";
    static final String C_BEACONS = "beacons";

    SQLiteDatabase db;

    public ConectionsDao(Context context){
        DataBaseHelper helper = new DataBaseHelper(context);
        db = helper.getWritableDatabase();
    }

    public void insert (Conections con){
        ContentValues cV = new ContentValues();
        cV.put(C_ID, con.getIdc());
        cV.put(C_DAYS, con.getDias());
        cV.put(C_SPEAKERS, con.getPonentes());
        cV.put(C_LOCATION, con.getUbicacion());
        cV.put(C_BEACONS, con.getBeacons());
        db.insert(TABLE,null,cV);
    }

    public void update (Conections con){

        ContentValues cV = new ContentValues();
        cV.put(C_DAYS, con.getDias());
        cV.put(C_SPEAKERS, con.getPonentes());
        cV.put(C_LOCATION, con.getUbicacion());
        cV.put(C_BEACONS, con.getBeacons());
        long id = db.update(TABLE,cV,"_id = ?",new String[]{con.getIdc()+" "});
    }

    public void delete (long id){
        db.delete(TABLE,"_id = "+id, null);
    }

    public void deleteAll (){
        db.execSQL("DELETE FROM conections");
    }

    public  Conections getByid (long id){

        Cursor c = db.rawQuery("SELECT * FROM conections WHERE _id="+id,null);
        return cursorToConections(c);

    }

    public List<Conections> getAll(){

        Cursor c = db.rawQuery("SELECT * FROM conections",null);
        return cursorToList(c);

    }

    public List<Conections> getAByName (String name){

        Cursor c = db.rawQuery("SELECT * FROM conections WHERE nombre LIKE '%"+name+"%'",null);
        return cursorToList(c);
    }

    private Conections cursorToConections (Cursor c){

        Conections con= null;

        if (c.moveToNext()){
            con = new Conections();
            con.setIdc(c.getLong(0));
            con.setDias(c.getString(1));
            con.setPonentes(c.getString(2));
            con.setUbicacion(c.getString(3));
            con.setBeacons(c.getString(4));
        }
        return con;
    }

    private List<Conections> cursorToList (Cursor c){

        List<Conections> data = new ArrayList<>();

        for (int i= 0; i< c.getCount();i++){
            Conections con = cursorToConections(c);
            data.add(con);
        }

        return data;
    }

}
