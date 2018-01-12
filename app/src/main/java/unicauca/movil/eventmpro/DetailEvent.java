package unicauca.movil.eventmpro;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import unicauca.movil.eventmpro.databinding.ActivityDetailEventBinding;
import unicauca.movil.eventmpro.db.BeaconsDao;
import unicauca.movil.eventmpro.db.ConectionsDao;
import unicauca.movil.eventmpro.db.DiasDao;
import unicauca.movil.eventmpro.db.EventoDao;
import unicauca.movil.eventmpro.db.NotificationDao;
import unicauca.movil.eventmpro.db.PonenteDao;
import unicauca.movil.eventmpro.db.UbicacionDao;
import unicauca.movil.eventmpro.models.Conections;
import unicauca.movil.eventmpro.models.Dias;
import unicauca.movil.eventmpro.models.Evento;
import unicauca.movil.eventmpro.models.Ponente;
import unicauca.movil.eventmpro.net.HttpAsyncTask;
import unicauca.movil.eventmpro.util.L;

public class DetailEvent extends AppCompatActivity implements DialogInterface.OnClickListener, HttpAsyncTask.OnResponseReceived, Callback {


    ActivityDetailEventBinding binding;
    EventoDao edao;
    DiasDao ddao;
    NotificationDao ndao;
    PonenteDao pdao;
    BeaconsDao bdao;
    UbicacionDao udao;
    Conections c;
    ConectionsDao cdao;

    Gson gson;

    String comando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_detail_event);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_event);
        binding.setHandler(this);

        L.data3 = new ArrayList<>();

        c = new Conections();
        edao = new EventoDao(this);
        ddao = new DiasDao(this);
        ndao = new NotificationDao(this);
        pdao = new PonenteDao(this);
        bdao = new BeaconsDao(this);
        udao = new UbicacionDao(this);
        cdao = new ConectionsDao(this);
        gson = new Gson();

        List<Evento> elist = edao.getAll();
        long ide = elist.get(0).getIde();
        List<Conections> list = cdao.getAll();
        if(list.size() > 0 ) {
            for (Conections c : list) {
                comando = c.getEvento()+""+ide;
            }
        }
        loadData();
    }


    public void loadData() {
        List<Evento> list = edao.getAll();
        if (!verificaConexion(this)) {
            Toast.makeText(this,
                    R.string.conection_internet, Toast.LENGTH_SHORT)
                    .show();
            //Carga el evento de la base de datos local
            if(list.size() > 0 ) {
                for (Evento e : list) {
                    L.data3.add(e);
                    binding.setEvent(e);
                    Picasso.with(this)
                            .load(Uri.parse(e.getEventoimg()))
                            .into(binding.img, this);
                }
            }
            else {
                Toast.makeText(this, R.string.empy, Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            HttpAsyncTask task = new HttpAsyncTask(this);
            task.execute(comando);

            if(list.size() > 0 ) {
                for (Evento e : list) {
                    L.data3.add(e);
                    binding.setEvent(e);
                    Picasso.with(this)
                            .load(Uri.parse(e.getEventoimg()))
                            .into(binding.img, this);
                }


            }
            else {
                Toast.makeText(this, R.string.empy, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void goToPonente(){
        Intent inten = new Intent(DetailEvent.this, Ponentes.class);
        startActivity(inten);
    }
    public void goToHorario(){
        Intent inten = new Intent(DetailEvent.this, Programacion.class);
        startActivity(inten);
    }
    public void goToUbicacion(){
        Intent inten = new Intent(DetailEvent.this, MapsActivity.class);
        startActivity(inten);
    }
    public void goToNotificaciones(){
        Intent inten = new Intent(DetailEvent.this, Notification.class);
        startActivity(inten);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_prin, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.salir:
                generateAlert();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void generateAlert(){
        AlertDialog alert = new AlertDialog.Builder(this)
                .setTitle(R.string.alert_title_event)
                .setIcon(R.drawable.ic_warning)
                .setMessage(R.string.alert_msg_event)
                .setPositiveButton(R.string.ok,this)
                .setNegativeButton(R.string.cancel, this)
                .create();
        alert.show();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if( i == DialogInterface.BUTTON_POSITIVE) {
            edao.deleteAll();
            ddao.deleteAll();
            ndao.deleteAll();
            pdao.deleteAll();
            cdao.deleteAll();
            udao.deleteAll();
            bdao.deleteAll();

            Intent inten = new Intent(this, Principal.class);
            inten.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(inten);
            //finish();
        }
        if( i == DialogInterface.BUTTON_NEGATIVE) {
        }
    }
    public static boolean verificaConexion(Context ctx) {
        boolean bConectado = false;
        ConnectivityManager connec = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // No sólo wifi, también GPRS
        NetworkInfo[] redes = connec.getAllNetworkInfo();
        // este bucle debería no ser tan ñapa
        for (int i = 0; i < 2; i++) {
            // ¿Tenemos conexión? ponemos a true
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                bConectado = true;
            }
        }
        return bConectado;
    }

    @Override
    public void onResponse(boolean success, String json) {
        Type lista = new TypeToken<List<Evento>>() {
        }.getType();
        List<Evento> res = gson.fromJson(json, lista);
        edao.deleteAll();
        for (Evento e : res) {
            edao.insert(e);
        }
    }

    @Override
    public void onSuccess() {
        BitmapDrawable drawable = (BitmapDrawable) binding.img.getDrawable();
        Palette palette =  new Palette
                .Builder(drawable.getBitmap())
                .generate();

        int colorDefault = ContextCompat.getColor(this, R.color.colorPrimary);
        int color = palette.getVibrantColor(colorDefault);
        binding.collapsingToolbar.setContentScrimColor(color);
    }

    @Override
    public void onError() {

    }
}
