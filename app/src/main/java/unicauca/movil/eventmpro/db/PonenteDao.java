package unicauca.movil.eventmpro.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.List;

import unicauca.movil.eventmpro.models.Ponente;

/**
 * Created by RicardoM on 12/10/2016.
 */

public class PonenteDao {

    static final String TABLE = "ponente";
    static final String C_ID = "_id";
    static final String C_NAME = "nombre";
    static final String C_LASTNAME = "apellidos";
    static final String C_EMPRESA = "empresa";
    static final String C_EXPERIENCIA = "experiencia";
    static final String C_HABILIDAD = "habilidades";
    static final String C_EST = "estudios";
    static final String C_IMAGEN = "img";
    static final String C_INTERNACIONAL = "formacioninternacional";
    static final String C_TYPE = "tipo";

    SQLiteDatabase db;

    public PonenteDao(Context context){
        DataBaseHelper helper = new DataBaseHelper(context);
        db = helper.getWritableDatabase();
    }

    public void insert (Ponente ponente){
        ContentValues cV = new ContentValues();
        cV.put(C_ID, ponente.getIdp());
        cV.put(C_NAME, ponente.getNombre());
        cV.put(C_LASTNAME, ponente.getApellidos());
        cV.put(C_EMPRESA, ponente.getEmpresa());
        cV.put(C_EST, ponente.getEstudios());
        cV.put(C_IMAGEN, ponente.getImagen());
        cV.put(C_EXPERIENCIA, ponente.getExperiencia());
        cV.put(C_INTERNACIONAL, ponente.getFormacioninternacional());
        cV.put(C_HABILIDAD, ponente.getHabilidad());
        cV.put(C_TYPE, ponente.getTipo());
        db.insert(TABLE,null,cV);

    }

    public void update (Ponente ponente){

        ContentValues cV = new ContentValues();
        cV.put(C_NAME, ponente.getNombre());
        cV.put(C_LASTNAME, ponente.getApellidos());
        cV.put(C_EMPRESA, ponente.getEmpresa());
        cV.put(C_EST, ponente.getEstudios());
        cV.put(C_IMAGEN, ponente.getImagen());
        cV.put(C_EXPERIENCIA, ponente.getExperiencia());
        cV.put(C_INTERNACIONAL, ponente.getFormacioninternacional());
        cV.put(C_HABILIDAD, ponente.getHabilidad());
        cV.put(C_TYPE, ponente.getTipo());
        long id = db.update(TABLE,cV,"_id = ?",new String[]{ponente.getIdp()+" "});
    }

    public void delete (long id){
        db.delete(TABLE,"_id = "+id, null);
    }

    public void deleteAll (){
        db.execSQL("DELETE FROM ponente");
    }

    public  Ponente getByid (long id){

        Cursor c = db.rawQuery("SELECT * FROM ponente WHERE _id="+id,null);
        return cursorToPonente(c);

    }

    public List<Ponente> getAll (){

        Cursor c = db.rawQuery("SELECT * FROM ponente ORDER BY nombre",null);
        return cursorToList(c);

    }

    public List<Ponente> getAByName (String name){

        Cursor c = db.rawQuery("SELECT * FROM ponente WHERE nombre LIKE '%"+name+"%'",null);
        return cursorToList(c);
    }

    private Ponente cursorToPonente (Cursor c){
        Ponente ponente= null;
        if (c.moveToNext()){
            ponente = new Ponente();
            ponente.setIdp(c.getLong(0));
            ponente.setNombre(c.getString(1));
            ponente.setApellidos(c.getString(2));
            ponente.setEmpresa(c.getString(3));
            ponente.setEstudios(c.getString(4));
            ponente.setImagen(c.getString(5));
            ponente.setExperiencia(c.getString(6));
            ponente.setFormacioninternacional(c.getString(7));
            ponente.setHabilidad(c.getString(8));
            ponente.setTipo(c.getString(9));
        }
        return ponente;
    }

    private List<Ponente> cursorToList (Cursor c){

        List<Ponente> data = new ArrayList<>();

        for (int i= 0; i< c.getCount();i++){
            Ponente p = cursorToPonente(c);
            data.add(p);
        }

        return data;
    }

}
