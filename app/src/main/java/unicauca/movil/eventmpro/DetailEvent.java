package unicauca.movil.eventmpro;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import unicauca.movil.eventmpro.databinding.ActivityDetailEventBinding;
import unicauca.movil.eventmpro.databinding.ActivityDetailPonenteBinding;
import unicauca.movil.eventmpro.databinding.ContentDetailEventBinding;
import unicauca.movil.eventmpro.db.EventoDao;
import unicauca.movil.eventmpro.models.Evento;
import unicauca.movil.eventmpro.models.Ponente;
import unicauca.movil.eventmpro.util.L;

public class DetailEvent extends AppCompatActivity {


    ActivityDetailEventBinding binding;
    ContentDetailEventBinding binding2;
    EventoDao dao;

    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_detail_event);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_event);
        binding.setHandler(this);


        L.data3 = new ArrayList<>();

        dao = new EventoDao(this);


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

}
