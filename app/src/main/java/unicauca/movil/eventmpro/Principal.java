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
import unicauca.movil.eventmpro.db.BeaconsDao;
import unicauca.movil.eventmpro.db.ConectionsDao;
import unicauca.movil.eventmpro.db.DiasDao;
import unicauca.movil.eventmpro.db.EventoDao;
import unicauca.movil.eventmpro.db.NotificationDao;
import unicauca.movil.eventmpro.db.PonenteDao;
import unicauca.movil.eventmpro.db.UbicacionDao;
import unicauca.movil.eventmpro.models.Beacons;
import unicauca.movil.eventmpro.models.Conections;
import unicauca.movil.eventmpro.models.Dias;
import unicauca.movil.eventmpro.models.Evento;
import unicauca.movil.eventmpro.models.Mensaje;
import unicauca.movil.eventmpro.models.Ponente;
import unicauca.movil.eventmpro.models.Ubicacion;

public class Principal extends AppCompatActivity {
    ActivityPrincipalBinding binding;

    NotificationDao ndao;
    PonenteDao pdao;
    BeaconsDao bdao;
    ConectionsDao cdao;
    DiasDao ddao;
    EventoDao edao;
    UbicacionDao udao;


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

        pdao = new PonenteDao(this);
        bdao = new BeaconsDao(this);
        cdao = new ConectionsDao(this);
        ddao = new DiasDao(this);
        edao = new EventoDao(this);
        udao = new UbicacionDao(this);
        ndao = new NotificationDao(this);

        List<Ponente> listp = pdao.getAll();
        List<Beacons> listb = bdao.getAll();
        List<Conections> listc = cdao.getAll();
        List<Dias> listd = ddao.getAll();
        List<Evento> liste = edao.getAll();
        List<Ubicacion> listu = udao.getAll();
        if (listp.size() > 0 && listb.size() > 0 && listc.size() > 0 && listd.size() > 0 && liste.size() > 0 && listu.size() > 0  ){
            Intent intent = new Intent(this, DetailEvent.class);
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
                    ndao.insert(m);
                }
            }
        }
        //endregion

    }

    public void goToExplorer(){
        Intent carga = new Intent(Principal.this, CargaDatos.class);
        startActivity(carga);
        finish();
    }

}
