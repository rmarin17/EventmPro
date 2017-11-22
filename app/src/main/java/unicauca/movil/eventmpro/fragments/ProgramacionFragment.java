package unicauca.movil.eventmpro.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import unicauca.movil.eventmpro.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProgramacionFragment extends Fragment {


    public ProgramacionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_programacion, container, false);
    }

}
