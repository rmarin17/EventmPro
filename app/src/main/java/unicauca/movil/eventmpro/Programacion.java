package unicauca.movil.eventmpro;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import unicauca.movil.eventmpro.adapters.PagerAdapter;
import unicauca.movil.eventmpro.databinding.ActivityDetailPonenteBinding;
import unicauca.movil.eventmpro.databinding.ActivityPonentesBinding;
import unicauca.movil.eventmpro.databinding.ActivityProgramacionBinding;
import unicauca.movil.eventmpro.fragments.ProgramacionFragment;

public class Programacion extends AppCompatActivity {

    ActivityProgramacionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_programacion);


            List<Fragment> data = new ArrayList<>();

            ProgramacionFragment dia1 = ProgramacionFragment.newInstance(1, "http://www.unicauca.edu.co/moocmaker/pagapp/consultandroid.php?idd=1");
            ProgramacionFragment dia2 = ProgramacionFragment.newInstance(2, "http://www.unicauca.edu.co/moocmaker/pagapp/consultandroid.php?idd=2");
            ProgramacionFragment dia3 = ProgramacionFragment.newInstance(3, "http://www.unicauca.edu.co/moocmaker/pagapp/consultandroid.php?idd=3");

            data.add(dia1);
            data.add(dia2);
            data.add(dia3);


            PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), data);
            binding.pager.setAdapter(adapter);

            setSupportActionBar(binding.toolbar);
            binding.tabs.setupWithViewPager(binding.pager);

    }
}
