package unicauca.movil.eventmpro.fragments;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import unicauca.movil.eventmpro.R;
import unicauca.movil.eventmpro.adapters.DiasAdapter;
import unicauca.movil.eventmpro.db.DiasDao;
import unicauca.movil.eventmpro.models.Dias;
import unicauca.movil.eventmpro.net.HttpAsyncTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiaFragment extends Fragment {

    private static final String EXTRA_DAY = "day";
    int dia;
    List<Dias> data;
    DiasAdapter adapter;
    ListView list;
    DiasDao dao;
    Gson gson;

    public static DiaFragment newInstance(int day) {
        DiaFragment fragment = new DiaFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRA_DAY, day);

        fragment.setArguments(args);
        return fragment;
    }

    public DiaFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        dia = args.getInt(EXTRA_DAY);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dia, container, false);
        list = (ListView) view.findViewById(R.id.list);
        data = new ArrayList<>();
        adapter = new DiasAdapter(getContext(), data);
        dao = new DiasDao(getContext());
        list.setAdapter(adapter);
        gson = new Gson();
        loaddata();
        return view;
    }

    public void loaddata() {
            List<Dias> list = dao.getAllByDay(dia);
            for (Dias d : list){
                data.add(d);
            }
            adapter.notifyDataSetChanged();
    }
}
