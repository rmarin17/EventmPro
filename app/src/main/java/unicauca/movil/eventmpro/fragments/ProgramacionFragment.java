package unicauca.movil.eventmpro.fragments;




import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.List;

import unicauca.movil.eventmpro.R;
import unicauca.movil.eventmpro.adapters.ProgramacionAdapter;
import unicauca.movil.eventmpro.databinding.FragmentProgramacionBinding;
import unicauca.movil.eventmpro.db.DiasDao;
import unicauca.movil.eventmpro.models.Dias;
import unicauca.movil.eventmpro.util.L;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProgramacionFragment extends Fragment implements ProgramacionAdapter.OnProgramacionListener{//, HttpAsyncTask.OnResponseReceived {

    public static ProgramacionFragment newInstance(int dia){//, String commnad) {
        ProgramacionFragment fragment = new ProgramacionFragment();
        Bundle args = new Bundle();
        args.putInt("day", dia);
        fragment.setArguments(args);
        return fragment;
    }

    FragmentProgramacionBinding binding;
    int dia;
    ProgramacionAdapter adapter;
    DiasDao dao;

     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_programacion, container, false);

        if(getArguments() != null){
            dia = getArguments().getInt("day", 0);
        }

        adapter = new ProgramacionAdapter(getLayoutInflater(), L.data1, this);
        binding.recycler.setAdapter(adapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        dao = new DiasDao(getContext());
        loadData();

        return binding.getRoot();
    }

    public void loadData() {
        L.data1.clear();
        List<Dias> list = dao.getAllByDay(dia);
        for (Dias d : list){
            L.data1.add(d);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onProgramacionClick(int position) {

      /*  Intent intent = new Intent(getContext(), DetailProgramacion.class);
        intent.putExtra(DetailProgramacion.EXTRA_POS, position);
        startActivity(intent);*/

    }

}
