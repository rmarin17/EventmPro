package unicauca.movil.eventmpro;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import unicauca.movil.eventmpro.adapters.PonenteAdapter;
import unicauca.movil.eventmpro.databinding.ActivityPonentesBinding;
import unicauca.movil.eventmpro.db.ConectionsDao;
import unicauca.movil.eventmpro.db.EventoDao;
import unicauca.movil.eventmpro.db.PonenteDao;
import unicauca.movil.eventmpro.models.Conections;
import unicauca.movil.eventmpro.models.Evento;
import unicauca.movil.eventmpro.models.Ponente;
import unicauca.movil.eventmpro.net.HttpAsyncTask;
import unicauca.movil.eventmpro.util.L;

public class Ponentes extends AppCompatActivity implements PonenteAdapter.OnPonenteListener, HttpAsyncTask.OnResponseReceived {

    ActivityPonentesBinding binding;
    PonenteAdapter adapter;
    PonenteDao pdao;
    Conections c;
    ConectionsDao cdao;
    EventoDao edao;
    Gson gson;
    String comando;
    int tamaño;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_ponentes);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ponentes);
        binding.setHandler(this);

        L.data = new ArrayList<>();

        c = new Conections();
        cdao = new ConectionsDao(this);
        edao = new EventoDao(this);
        pdao = new PonenteDao(this);
        gson = new Gson();

        adapter = new PonenteAdapter(getLayoutInflater(), L.data,this);
        binding.recycler.setAdapter(adapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));

        List<Evento> elist = edao.getAll();
        long ide = elist.get(0).getIde();
        List<Conections> list = cdao.getAll();
        if(list.size() > 0 ) {
            for (Conections c : list) {
                comando = c.getPonentes()+""+ide;
            }
        }
        loadData();
    }


    public void loadData() {
        L.data.clear();
        List<Ponente> list = pdao.getAll();
        tamaño=list.size();
        if (!verificaConexion(this)) {
            Toast.makeText(this,
                    R.string.conection_internet, Toast.LENGTH_SHORT)
                    .show();
            if(list.size() > 0 ) {
                for (Ponente p : list) {
                    L.data.add(p);
                }
                adapter.notifyDataSetChanged();
            }
            else {
                Toast.makeText(this, R.string.empy, Toast.LENGTH_LONG).show();
                finish();
            }
        }
        else
        {
            HttpAsyncTask task = new HttpAsyncTask(this);
            task.execute(comando);
            if(list.size() > 0 ) {
                for (Ponente p : list) {
                    L.data.add(p);
                }
                adapter.notifyDataSetChanged();
            }
            else {
                Toast.makeText(this, R.string.empy, Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    public void goToPrincipal(){
        Intent inten = new Intent(Ponentes.this, DetailEvent.class);
        startActivity(inten);
    }
    public void goToHorario(){
        Intent inten = new Intent(Ponentes.this, Programacion.class);
        startActivity(inten);
    }
    public void goToUbicacion(){
        Intent inten = new Intent(Ponentes.this, MapsActivity.class);
        startActivity(inten);
    }
    public void goToNotificaciones(){
        Intent inten = new Intent(Ponentes.this, Notification.class);
        startActivity(inten);
    }

    @Override
    public void onPonente(int position) {
        //int pos =  binding.recycler.getChildAdapterPosition(position);
        Intent intent = new Intent(this, DetailPonente.class);
        intent.putExtra(DetailPonente.EXTRA_POS, position);
        startActivity(intent);
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
        Type lista = new TypeToken<List<Ponente>>() {
        }.getType();
        List<Ponente> res = gson.fromJson(json, lista);

        if (res!=null) {
            if (tamaño!= res.size()) {
                pdao.deleteAll();
                for (Ponente p : res) {
                    pdao.insert(p);
                }
            } else {
                for (Ponente p : res) {
                    pdao.update(p);
                }
            }
        }else {
            pdao.deleteAll();
        }
    }
}
