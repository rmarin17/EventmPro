package unicauca.movil.eventmpro;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import unicauca.movil.eventmpro.adapters.PonenteAdapter;
import unicauca.movil.eventmpro.databinding.ActivityPonentesBinding;
import unicauca.movil.eventmpro.db.PonenteDao;
import unicauca.movil.eventmpro.models.Ponente;
import unicauca.movil.eventmpro.util.L;

public class Ponentes extends AppCompatActivity implements PonenteAdapter.OnPonenteListener {

    ActivityPonentesBinding binding;

    PonenteAdapter adapter;

    PonenteDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_ponentes);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_ponentes);


        L.data = new ArrayList<>();
        adapter = new PonenteAdapter(getLayoutInflater(), L.data,this);
        binding.recycler.setAdapter(adapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));

        dao = new PonenteDao(this);

        loadData();
    }


    public void loadData() {

        List<Ponente> list = dao.getAll();

        if(list.size() > 0 ) {
            for (Ponente p : list) {
                L.data.add(p);
            }
            adapter.notifyDataSetChanged();
        }
        else {
            Toast.makeText(this, R.string.empy, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onPonente(int position) {

        //int pos =  binding.recycler.getChildAdapterPosition(position);

        Intent intent = new Intent(this, DetailPonente.class);
        intent.putExtra(DetailPonente.EXTRA_POS, position);

        startActivity(intent);

    }
}
