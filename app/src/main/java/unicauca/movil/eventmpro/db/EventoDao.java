package unicauca.movil.eventmpro.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import unicauca.movil.eventmpro.models.Evento;

/**
 * Created by RicardoM on 29/11/2017.
 */

public class EventoDao {

    static final String TABLE = "evento";
    static final String C_ID = "_id";
    static final String C_NAME = "nombre";
    static final String C_IMG = "imagen";
    static final String C_DAYS = "numerodias";
    static final String C_GOAL = "objetivo";
    static final String C_PLACE = "lugar";
    static final String C_DES = "descripcion";
    static final String C_DATE = "fecha";

    SQLiteDatabase db;

    public EventoDao(Context context){
        DataBaseHelper helper = new DataBaseHelper(context);
        db = helper.getWritableDatabase();
    }

    public void insert (Evento evento){
        ContentValues cV = new ContentValues();
        cV.put(C_ID, evento.getIde());
        cV.put(C_NAME, evento.getEventonombre());
        cV.put(C_IMG, evento.getEventoimg());
        cV.put(C_DAYS, evento.getNumerodias());
        cV.put(C_GOAL, evento.getObjetivo());
        cV.put(C_PLACE, evento.getLugarevento());
        cV.put(C_DES, evento.getDescripcion());
        cV.put(C_DATE, evento.getFecha());
        db.insert(TABLE,null,cV);
    }

    public void update (Evento evento){

        ContentValues cV = new ContentValues();
        cV.put(C_NAME, evento.getEventonombre());
        cV.put(C_IMG, evento.getEventoimg());
        cV.put(C_DAYS, evento.getNumerodias());
        cV.put(C_GOAL, evento.getObjetivo());
        cV.put(C_PLACE, evento.getLugarevento());
        cV.put(C_DES, evento.getDescripcion());
        cV.put(C_DATE, evento.getFecha());
        long id = db.update(TABLE,cV,"_id = ?",new String[]{evento.getIde()+" "});
    }

    public void delete (long id){
        db.delete(TABLE,"_id = "+id, null);
    }

    public void deleteAll (){
        db.execSQL("DELETE FROM evento");
    }

    public  Evento getByid (long id){

        Cursor c = db.rawQuery("SELECT * FROM evento WHERE _id="+id,null);
        return cursorToEvento(c);

    }

    public List<Evento> getAll (){

        Cursor c = db.rawQuery("SELECT * FROM evento",null);
        return cursorToList(c);
    }

    private Evento cursorToEvento(Cursor c){

        Evento evento= null;

        if (c.moveToNext()){
            evento = new Evento();
            evento.setIde(c.getLong(0));
            evento.setEventonombre(c.getString(1));
            evento.setEventoimg(c.getString(2));
            evento.setNumerodias(c.getInt(3));
            evento.setObjetivo(c.getString(4));
            evento.setLugarevento(c.getString(5));
            evento.setDescripcion(c.getString(6));
            evento.setFecha(c.getString(7));
        }
        return evento;
    }

    private List<Evento> cursorToList (Cursor c){

        List<Evento> data = new ArrayList<>();

        for (int i= 0; i< c.getCount();i++){
            Evento e = cursorToEvento(c);
            data.add(e);
        }

        return data;
    }

}
