package unicauca.movil.eventmpro.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import unicauca.movil.eventmpro.models.Mensaje;

/**
 * Created by RicardoM on 28/03/2017.
 */

public class NotificationDao {

    static final String TABLE = "notification";
    static final String C_MESSAGE = "mensaje";
    static final String C_DATE = "fecha";
    static final String C_TIME = "hora";

    SQLiteDatabase db;

    public NotificationDao(Context context){
        DataBaseHelper helper = new DataBaseHelper(context);
        db = helper.getWritableDatabase();
    }

    public void insert (Mensaje mensaje){
        ContentValues cV = new ContentValues();
        cV.put(C_MESSAGE, mensaje.getMensaje());
        cV.put(C_DATE, mensaje.getFecha());
        cV.put(C_TIME, mensaje.getHora());
        long id = db.insert(TABLE,null,cV);
        mensaje.setId(id);

    }

    public void update (Mensaje mensaje){

        ContentValues cV = new ContentValues();
        cV.put(C_MESSAGE, mensaje.getMensaje());
        cV.put(C_DATE, mensaje.getFecha());
        cV.put(C_TIME, mensaje.getHora());
        long id = db.update(TABLE,cV,"_id = ?",new String[]{mensaje.getId()+" "});
    }

    public void delete (long id){
        db.delete(TABLE,"_id = "+id, null);
    }

    public void deleteAll (){
        db.execSQL("DELETE FROM notification");
    }

    public  Mensaje getByid (long id){

        Cursor c = db.rawQuery("SELECT * FROM notification WHERE _id="+id,null);
        return cursorToMensaje(c);

    }

    public List<Mensaje> getAll (){

        Cursor c = db.rawQuery("SELECT * FROM notification ORDER BY _id DESC",null);
        return cursorToList(c);
    }

    private Mensaje cursorToMensaje (Cursor c){

        Mensaje mensaje= null;

        if (c.moveToNext()){
            mensaje = new Mensaje();
            mensaje.setId(c.getLong(0));
            mensaje.setMensaje(c.getString(1));
            mensaje.setFecha(c.getString(2));
            mensaje.setHora(c.getString(3));

        }
        return mensaje;
    }

    private List<Mensaje> cursorToList (Cursor c){

        List<Mensaje> data = new ArrayList<>();

        for (int i= 0; i< c.getCount();i++){
            Mensaje m = cursorToMensaje(c);
            data.add(m);
        }

        return data;
    }

}
