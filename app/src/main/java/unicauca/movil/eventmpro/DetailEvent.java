package unicauca.movil.eventmpro;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import unicauca.movil.eventmpro.databinding.ActivityDetailEventBinding;
import unicauca.movil.eventmpro.db.DiasDao;
import unicauca.movil.eventmpro.db.EventoDao;
import unicauca.movil.eventmpro.db.NotificationDao;
import unicauca.movil.eventmpro.db.PonenteDao;
import unicauca.movil.eventmpro.models.Evento;
import unicauca.movil.eventmpro.util.L;

public class DetailEvent extends AppCompatActivity {


    ActivityDetailEventBinding binding;
    EventoDao dao;
    DiasDao diasdao;
    NotificationDao notidao;
    PonenteDao pdao;


    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_detail_event);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_event);
        binding.setHandler(this);


        L.data3 = new ArrayList<>();

        dao = new EventoDao(this);
        diasdao = new DiasDao(this);
        notidao = new NotificationDao(this);
        pdao = new PonenteDao(this);

        loadData();

    }


    public void loadData() {

        List<Evento> list = dao.getAll();


        if(list.size() > 0 ) {
            for (Evento e : list) {
                L.data3.add(e);
                binding.setEvent(e);
            }

        }
        else {
            Toast.makeText(this, R.string.empy, Toast.LENGTH_LONG).show();
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

                dao.deleteAll();
                diasdao.deleteAll();
                notidao.deleteAll();
                pdao.deleteAll();

                Intent inten = new Intent(DetailEvent.this, Principal.class);
                startActivity(inten);

                break;
        }


        return super.onOptionsItemSelected(item);
    }
}
