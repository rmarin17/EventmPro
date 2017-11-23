package unicauca.movil.eventmpro;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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

public class Notification extends AppCompatActivity implements MensajeAdapter.OnMensajeListener {

    ActivityNotificationBinding binding;

    MensajeAdapter adapter;

    NotificationDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_notification);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification);

        L.data2 = new ArrayList<>();
        adapter = new MensajeAdapter(getLayoutInflater(), L.data2,this);
        binding.recycler.setAdapter(adapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));

        dao = new NotificationDao(this);

        loadData();
    }

    public void loadData() {

        List<Mensaje> list = dao.getAll();

        if(list.size() > 0 ) {
            for (Mensaje m : list) {
                L.data2.add(m);
            }
            adapter.notifyDataSetChanged();
        }
        else {
            Toast.makeText(this, R.string.empy, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onMensaje(int position) {

    }
}
