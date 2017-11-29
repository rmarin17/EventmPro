package unicauca.movil.eventmpro;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import unicauca.movil.eventmpro.databinding.ActivityDetailEventBinding;
import unicauca.movil.eventmpro.databinding.ActivityDetailPonenteBinding;

public class DetailEvent extends AppCompatActivity {


    ActivityDetailEventBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_detail_event);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_event);
        binding.setHandler(this);

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
