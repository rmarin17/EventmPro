package unicauca.movil.eventmpro;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Canvas;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import unicauca.movil.eventmpro.adapters.MensajeAdapter;
import unicauca.movil.eventmpro.adapters.PonenteAdapter;
import unicauca.movil.eventmpro.databinding.ActivityNotificationBinding;
import unicauca.movil.eventmpro.databinding.ActivityPonentesBinding;
import unicauca.movil.eventmpro.db.NotificationDao;
import unicauca.movil.eventmpro.db.PonenteDao;
import unicauca.movil.eventmpro.models.Mensaje;
import unicauca.movil.eventmpro.models.Ponente;
import unicauca.movil.eventmpro.util.L;

public class Notification extends AppCompatActivity implements MensajeAdapter.OnMensajeListener, DialogInterface.OnClickListener {

    ActivityNotificationBinding binding;

    MensajeAdapter adapter;
    SwipeController swipeController = null;

    NotificationDao dao;

    long did;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_notification);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification);
        binding.setHandler(this);
        L.data2 = new ArrayList<>();
        adapter = new MensajeAdapter(getLayoutInflater(), L.data2,this);
        binding.recycler.setAdapter(adapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));

        dao = new NotificationDao(this);

        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                Mensaje men = L.data2.get(position);
                did = men.getId();
                generateAlert();
            }
        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(binding.recycler);

        binding.recycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });

        loadData();
    }

    public void loadData() {
        List<Mensaje> list = dao.getAll();
        L.data2.clear();
        if(list.size() > 0 ) {
            for (Mensaje m : list) {
                L.data2.add(m);
            }
            adapter.notifyDataSetChanged();
        }
        else {
            Toast.makeText(this, R.string.empy, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public void goToPrincipal(){
        Intent inten = new Intent(Notification.this, DetailEvent.class);
        startActivity(inten);
    }
    public void goToPonente(){
        Intent inten = new Intent(Notification.this, Ponentes.class);
        startActivity(inten);
    }
    public void goToHorario(){
        Intent inten = new Intent(Notification.this, Programacion.class);
        startActivity(inten);
    }
    public void goToUbicacion(){
        Intent inten = new Intent(Notification.this, MapsActivity.class);
        startActivity(inten);
    }

    public void generateAlert(){
        AlertDialog alert = new AlertDialog.Builder(this)
                .setTitle(R.string.alert_title_noti)
                .setIcon(R.drawable.ic_warning)
                .setMessage(R.string.alert_msg_noti)
                .setPositiveButton(R.string.ok,this)
                .setNegativeButton(R.string.cancel, this)
                .create();
        alert.show();
    }

    @Override
    public void onMensaje(int position) {
       /* Mensaje men = L.data2.get(position);
        did = men.getId();
        generateAlert();*/
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if( i == DialogInterface.BUTTON_POSITIVE) {
            dao.delete(did);
            loadData();
        }
        if( i == DialogInterface.BUTTON_NEGATIVE) {
        }
    }
}
