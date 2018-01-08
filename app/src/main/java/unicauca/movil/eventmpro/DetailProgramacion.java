package unicauca.movil.eventmpro;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.squareup.picasso.Callback;

import java.util.ArrayList;
import java.util.List;

import unicauca.movil.eventmpro.databinding.ActivityDetailProgramacionBinding;
import unicauca.movil.eventmpro.db.DiasDao;
import unicauca.movil.eventmpro.models.Dias;
import unicauca.movil.eventmpro.util.L;

public class DetailProgramacion extends AppCompatActivity {

    ActivityDetailProgramacionBinding binding;
    DiasDao ddao;

    public static final String EXTRA_POS = "pos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail_programacion);

        ddao = new DiasDao(this);
        int hora = getIntent().getExtras().getInt("hour");
        int dia = getIntent().getExtras().getInt("day");

        //Dias prog = L.data1.get(pos);
        List<Dias> list = ddao.getByDayHour(dia, hora);
        if(list.size()>0) {
            L.datad.clear();
            for (Dias d : list) {
                L.datad.add(d);
            }
            binding.setProg(L.datad.get(0));
            binding.setHandler(this);
        }


    }
}
