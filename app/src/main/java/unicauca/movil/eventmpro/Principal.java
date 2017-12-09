package unicauca.movil.eventmpro;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import unicauca.movil.eventmpro.databinding.ActivityPrincipalBinding;
import unicauca.movil.eventmpro.db.EventoDao;
import unicauca.movil.eventmpro.db.NotificationDao;
import unicauca.movil.eventmpro.db.PonenteDao;
import unicauca.movil.eventmpro.models.Evento;
import unicauca.movil.eventmpro.models.Mensaje;
import unicauca.movil.eventmpro.models.Ponente;

public class Principal extends AppCompatActivity {
    ActivityPrincipalBinding binding;
    NotificationDao dao;
    PonenteDao dao1;
    EventoDao dao2;



    //LinearLayout buttonOpenDialog;
    //Button buttonUp;
    //TextView textFolder;

    //String KEY_TEXTPSS = "TEXTPSS";
    //static final int CUSTOM_DIALOG_ID = 0;
    //ListView dialog_ListView;

    //File root;
    //File curFolder;

    //int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 10001;
    private static final int READ_REQUEST_CODE = 42;

    String archivo;
    //private List<String> fileList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_principal);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_principal);
        binding.setHandler(this);


        Calendar calendario = Calendar.getInstance();
        int hora, min,dia,mes,ano;
        String fecha_sistema,hora_sistema;

        dia = calendario.get(Calendar.DAY_OF_MONTH);
        mes = calendario.get(Calendar.MONTH)+1;
        ano = calendario.get(Calendar.YEAR);
        hora = calendario.get(Calendar.HOUR_OF_DAY);
        min = calendario.get(Calendar.MINUTE);
        fecha_sistema = dia+"/"+mes+"/"+ano;
        hora_sistema = ""+hora+":"+min+"";
        dao = new NotificationDao(this);
        dao1 = new PonenteDao(this);
        dao2 = new EventoDao(this);

        /*Evento e = new Evento();
        e.setId(1);
        e.setNombre("TET 2016");
        e.setObjetivo("Facilitar el acceso a información de primera mano a estudiantes, profesores y profesionales afines con el área de Telecomunicaciones, que buscan aumentar y actualizar su conocimiento sobre las tecnologías que mayor penetran e impactan a la sociedad.");
        e.setLugar("Popayan (Cauca) - Teatro Guillermo Leon Valencia");
        e.setDescripcion("Seminario de Tecnologias Emergentes en Telecomunicaciones");
        e.setFecha("20, 21 y 22 de Octubre de 2016");

        dao2.insert(e);*/



        List<Ponente> list = dao1.getAll();
        if (list.size() > 0){

            Intent intent = new Intent(Principal.this, DetailEvent.class);
            startActivity(intent);
            finish();
        }



        //region Tomar datos de la notificacion de firebase
        if(getIntent().getExtras()!=null){

            for (String Key : getIntent().getExtras().keySet()){
                if (Key.equals("mensaje")){
                    String men = getIntent().getExtras().getString(Key);
                    Mensaje m = new Mensaje();
                    m.setMensaje(men);
                    m.setFecha(fecha_sistema);
                    m.setHora(hora_sistema);
                    dao.insert(m);
                }

            }
        }
        //endregion


    }

    public void goToExplorer(){
        //performFileSearch();
        Intent carga = new Intent(Principal.this, CargaDatos.class);
        startActivity(carga);
        finish();
    }
// region  manualchooser
 /*   @Override
    protected Dialog onCreateDialog(int id) {

        Dialog dialog = null;

        switch (id) {
            case CUSTOM_DIALOG_ID:
                dialog = new Dialog(Principal.this);
                dialog.setContentView(R.layout.dialoglayout);
                dialog.setTitle("Custom Dialog");
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);

                textFolder = (TextView) dialog.findViewById(R.id.folder);
                buttonUp = (Button) dialog.findViewById(R.id.up);
                buttonUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ListDir(curFolder.getParentFile());
                    }
                });

                dialog_ListView = (ListView) dialog.findViewById(R.id.dialoglist);
                dialog_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        File selected = new File(fileList.get(position));
                        if(selected.isDirectory()) {
                            ListDir(selected);
                        } else {
                            Toast.makeText(Principal.this, selected.toString() + " selected",
                                    Toast.LENGTH_LONG).show();
                            dismissDialog(CUSTOM_DIALOG_ID);
                        }
                    }
                });

                break;
        }
        return dialog;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        switch (id) {
            case CUSTOM_DIALOG_ID:
                ListDir(curFolder);
                break;
        }
    }

    void ListDir(File f) {
        if(f.equals(root)) {
            buttonUp.setEnabled(false);
        } else {
            buttonUp.setEnabled(true);
        }

        f = curFolder;
        textFolder.setText(f.getPath());

        File[] files = curFolder.listFiles();
        fileList.clear();

        if(files!=null) // condicionante del tamaño de archivos por si no los lee
        {
            for (File file : files) {
                fileList.add(file.getPath());
            }
        }

        ArrayAdapter<String> directoryList = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, fileList);
        dialog_ListView.setAdapter(directoryList);
    }*/


//endregion manualchooser

    /**
     * Fires an intent to spin up the "file chooser" UI and select an image.
     */
    public void performFileSearch() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("*/*");

        startActivityForResult(intent, READ_REQUEST_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                Toast.makeText(this, "URI: " +uri.toString(), Toast.LENGTH_LONG).show();
                archivo = uri.toString();

                //uri(archivo);
                //Intent inten = new Intent(this, CargaDatos.class);
                //startActivity(inten);

            }
        }
    }




}
