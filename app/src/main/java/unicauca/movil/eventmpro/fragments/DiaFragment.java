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
import android.widget.Toast;

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
public class DiaFragment extends Fragment implements HttpAsyncTask.OnResponseReceived {

    private static final String EXTRA_DAY = "day";
    private static final String EXTRA_COMMAND = "commnad";

    String comando;
    int dia;

    List<Dias> data;
    DiasAdapter adapter;
    ListView list;

    DiasDao dao;

    Gson gson;


    public static DiaFragment newInstance(int day, String commnad) {
        DiaFragment fragment = new DiaFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRA_DAY, day);
        args.putString(EXTRA_COMMAND, commnad);

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
        comando = args.getString(EXTRA_COMMAND);


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

        if (!verificaConexion(getContext())) {
            Toast.makeText(getContext(),
                    "Por favor conectate a internet para obtener la programacion mas reciente.", Toast.LENGTH_SHORT)
                    .show();
            List<Dias> list = dao.getAllByDay(dia);
            for (Dias d : list){
                data.add(d);
            }
            adapter.notifyDataSetChanged();
        }

        else
        {
            HttpAsyncTask task = new HttpAsyncTask(this);
            task.execute(comando);
        }

    }

    @Override
    public void onResponse(boolean success, String json) {

        Type lista = new TypeToken<List<Dias>>() {
        }.getType();
        List<Dias> res = gson.fromJson(json, lista);

        data.clear();
        for (Dias d : res) {
            data.add(d);
        }
        adapter.notifyDataSetChanged();

    }

    public static boolean verificaConexion(Context ctx) {
        boolean bConectado = false;
        ConnectivityManager connec = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // No sólo wifi, también GPRS
        NetworkInfo[] redes = connec.getAllNetworkInfo();
        // este bucle debería no ser tan ñapa
        for (int i = 0; i < 2; i++) {
            // ¿Tenemos conexión? ponemos a true
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                bConectado = true;
            }
        }
        return bConectado;
    }
}
