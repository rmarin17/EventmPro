package unicauca.movil.eventmpro.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.List;

import unicauca.movil.eventmpro.models.Dias;

/**
 * Created by RicardoM on 13/10/2016.
 */

public class DiasDao {

    static final String TABLE = "dias";
    static final String C_ID = "_id";
    static final String C_IDD = "idd";
    static final String C_IDO = "ido";
    static final String C_HORA = "hora";
    static final String C_EVENTO = "evento";
    static final String C_TITULO = "titulo";
    static final String C_CONFERENCISTA = "conferencista";
    static final String C_EMPRESA = "empresa";
    static final String C_LUGAR = "lugar";

    SQLiteDatabase db;

    public DiasDao(Context context){
        DataBaseHelper helper = new DataBaseHelper(context);
        db = helper.getWritableDatabase();
    }

    public void insert (Dias dias){
        ContentValues cV = new ContentValues();
        cV.put(C_ID, dias.getIdh());
        cV.put(C_IDD, dias.getIdd());
        cV.put(C_IDO, dias.getIdo());
        cV.put(C_HORA, dias.getHora());
        cV.put(C_EVENTO, dias.getEvento());
        cV.put(C_TITULO, dias.getTitulo());
        cV.put(C_CONFERENCISTA, dias.getConferencista());
        cV.put(C_EMPRESA, dias.getEmpresadias());
        cV.put(C_LUGAR, dias.getLugar());
        db.insert(TABLE,null,cV);
    }

    public void update (Dias dias){

        ContentValues cV = new ContentValues();
        cV.put(C_IDD, dias.getIdd());
        cV.put(C_IDO, dias.getIdo());
        cV.put(C_HORA, dias.getHora());
        cV.put(C_EVENTO, dias.getEvento());
        cV.put(C_TITULO, dias.getTitulo());
        cV.put(C_CONFERENCISTA, dias.getConferencista());
        cV.put(C_EMPRESA, dias.getEmpresadias());
        cV.put(C_LUGAR, dias.getLugar());
        long idh = db.update(TABLE,cV,"_id = ?",new String[]{dias.getIdh()+" "});
    }

    public void delete (long id){
        db.delete(TABLE,"_id = "+id, null);
    }

    public void deleteAll (){
        db.execSQL("DELETE FROM dias");
    }

    public  List<Dias> getAll(){

        Cursor c = db.rawQuery("SELECT * FROM dias",null);
        return cursorToList(c);

    }

    public List<Dias> getAllByDay (int day){

        Cursor c = db.rawQuery("SELECT * FROM dias WHERE idd = '"+day+"' ORDER BY ido ASC",null);
        return cursorToList(c);

    }

    public List<Dias> getAByName (String name){

        Cursor c = db.rawQuery("SELECT * FROM dias WHERE nombre LIKE '%"+name+"%'",null);
        return cursorToList(c);
    }

    private Dias cursorToDia (Cursor c){

        Dias dias= null;

        if (c.moveToNext()){
            dias = new Dias();
            dias.setIdh(c.getLong(0));
            dias.setIdd(c.getInt(1));
            dias.setIdo(c.getInt(2));
            dias.setHora(c.getString(3));
            dias.setEvento(c.getString(4));
            dias.setTitulo(c.getString(5));
            dias.setConferencista(c.getString(6));
            dias.setEmpresadias(c.getString(7));
            dias.setLugar(c.getString(8));
        }
        return dias;
    }

    private List<Dias> cursorToList (Cursor c){

        List<Dias> data = new ArrayList<>();

        for (int i= 0; i< c.getCount();i++){
            Dias d = cursorToDia(c);
            data.add(d);
        }

        return data;
    }
}
