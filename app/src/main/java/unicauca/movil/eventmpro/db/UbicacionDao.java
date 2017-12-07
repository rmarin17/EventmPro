package unicauca.movil.eventmpro.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import unicauca.movil.eventmpro.models.Ubicacion;

/**
 * Created by RicardoM on 07/12/2017.
 */

public class UbicacionDao {

    static final String TABLE = "ubicacion";
    static final String C_ID = "_id";
    static final String C_TITLE = "titulo";
    static final String C_LAT = "lat";
    static final String C_LNG = "lng";


    SQLiteDatabase db;

    public UbicacionDao(Context context){
        DataBaseHelper helper = new DataBaseHelper(context);
        db = helper.getWritableDatabase();
    }

    public void insert (Ubicacion ubi){
        ContentValues cV = new ContentValues();
        cV.put(C_ID, ubi.getId());
        cV.put(C_TITLE, ubi.getTitulo());
        cV.put(C_LAT, ubi.getLat());
        cV.put(C_LNG, ubi.getLng());
        db.insert(TABLE,null,cV);
    }

    public void update (Ubicacion ubi){

        ContentValues cV = new ContentValues();
        cV.put(C_TITLE, ubi.getTitulo());
        cV.put(C_LAT, ubi.getLat());
        cV.put(C_LNG, ubi.getLng());
        long id = db.update(TABLE,cV,"_id = ?",new String[]{ubi.getId()+" "});
    }

    public void delete (long id){
        db.delete(TABLE,"_id = "+id, null);
    }

    public void deleteAll (){
        db.execSQL("DELETE FROM ubicacion");
    }

    public  Ubicacion getByid (long id){

        Cursor c = db.rawQuery("SELECT * FROM ubicacion WHERE _id="+id,null);
        return cursorToBeacons(c);

    }

    public List<Ubicacion> getAll (int day){

        Cursor c = db.rawQuery("SELECT * FROM ubicacion",null);
        return cursorToList(c);

    }

    public List<Ubicacion> getAByName (String name){

        Cursor c = db.rawQuery("SELECT * FROM ubicacion WHERE nombre LIKE '%"+name+"%'",null);
        return cursorToList(c);
    }

    private Ubicacion cursorToBeacons (Cursor c){

        Ubicacion ubi= null;

        if (c.moveToNext()){
            ubi = new Ubicacion();
            ubi.setId(c.getLong(0));
            ubi.setTitulo(c.getString(1));
            ubi.setLat(c.getDouble(2));
            ubi.setLng(c.getDouble(3));
        }
        return ubi;
    }

    private List<Ubicacion> cursorToList (Cursor c){

        List<Ubicacion> data = new ArrayList<>();

        for (int i= 0; i< c.getCount();i++){
            Ubicacion ubi = cursorToBeacons(c);
            data.add(ubi);
        }

        return data;
    }
}
