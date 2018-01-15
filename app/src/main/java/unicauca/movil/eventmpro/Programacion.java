package unicauca.movil.eventmpro;


import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import unicauca.movil.eventmpro.adapters.PagerAdapter;
import unicauca.movil.eventmpro.databinding.ActivityProgramacionBinding;
import unicauca.movil.eventmpro.db.ConectionsDao;
import unicauca.movil.eventmpro.db.DiasDao;
import unicauca.movil.eventmpro.db.EventoDao;
import unicauca.movil.eventmpro.fragments.DiaFragment;
import unicauca.movil.eventmpro.fragments.ProgramacionFragment;
import unicauca.movil.eventmpro.models.Conections;
import unicauca.movil.eventmpro.models.Dias;
import unicauca.movil.eventmpro.models.Evento;
import unicauca.movil.eventmpro.net.HttpAsyncTask;

public class Programacion extends AppCompatActivity implements HttpAsyncTask.OnResponseReceived {

    ActivityProgramacionBinding binding;
    DiasDao ddao;
    Gson gson;
    Conections c;
    ConectionsDao cdao;
    EventoDao edao;

    String comando1;
    int tamaño = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_programacion);
        binding.setHandler(this);

        c = new Conections();
        cdao = new ConectionsDao(this);
        edao = new EventoDao(this);
        ddao = new DiasDao(this);
        gson = new Gson();
        //region Otra forma anterior

        List<Evento> elist = edao.getAll();
        List<Dias> dlist = ddao.getAll();
        tamaño = dlist.size();
        int dias;
        long  ide;
        dias = elist.get(0).getNumerodias();
        ide = elist.get(0).getIde();


        List<Fragment> pages =  new ArrayList<>();

        if(dias > 0 ) {
            for (int i = 1; i < dias+1; i++) {
                pages.add(DiaFragment.newInstance(i));
            }
        }

        //endregion
        List<Conections> list = cdao.getAll();
        if(list.size() > 0 ) {
            for (Conections c : list) {
                comando1 = c.getDias()+""+ide;
            }
        }
        loadData();

        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), pages);
        binding.pager.setAdapter(adapter);

        setSupportActionBar(binding.toolbar);
        binding.tabs.setupWithViewPager(binding.pager);

    }

    public void loadData() {
        if (!verificaConexion(this)) {
            Toast.makeText(this,
                    R.string.conection_internet, Toast.LENGTH_SHORT)
                    .show();
        }
        else
        {
            HttpAsyncTask task = new HttpAsyncTask(this);
            task.execute(comando1);
        }
    }


    @Override
    public void onResponse(boolean success, String json) {

        Type lista = new TypeToken<List<Dias>>() {
        }.getType();
        List<Dias> res = gson.fromJson(json, lista);

        if (tamaño!=res.size())
        {
            ddao.deleteAll();
            for (Dias d : res) {
                ddao.insert(d);
            }
        }
        else {
            for (Dias d : res) {
                ddao.update(d);
            }
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

    public void goToPrincipal(){
        Intent inten = new Intent(Programacion.this, DetailEvent.class);
        startActivity(inten);
    }
    public void goToPonente(){
        Intent inten = new Intent(Programacion.this, Ponentes.class);
        startActivity(inten);
    }

    public void goToUbicacion(){
        Intent inten = new Intent(Programacion.this, MapsActivity.class);
        startActivity(inten);
    }
    public void goToNotificaciones(){
        Intent inten = new Intent(Programacion.this, Notification.class);
        startActivity(inten);
    }
}
