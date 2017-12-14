package unicauca.movil.eventmpro;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.squareup.picasso.Callback;

import java.util.ArrayList;

import unicauca.movil.eventmpro.databinding.ActivityDetailProgramacionBinding;
import unicauca.movil.eventmpro.models.Dias;
import unicauca.movil.eventmpro.util.L;

public class DetailProgramacion extends AppCompatActivity {

    ActivityDetailProgramacionBinding binding;

    public static final String EXTRA_POS = "pos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail_programacion);


        int pos =  getIntent().getExtras().getInt(EXTRA_POS);
        Dias prog = L.data1.get(pos);



        binding.setProg(prog);
        binding.setHandler(this);
    }


}
