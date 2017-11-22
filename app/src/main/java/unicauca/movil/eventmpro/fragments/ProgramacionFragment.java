package unicauca.movil.eventmpro.fragments;


import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
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

import unicauca.movil.eventmpro.DetailPonente;
import unicauca.movil.eventmpro.Programacion;
import unicauca.movil.eventmpro.R;
import unicauca.movil.eventmpro.adapters.PonenteAdapter;
import unicauca.movil.eventmpro.adapters.ProgramacionAdapter;
import unicauca.movil.eventmpro.databinding.FragmentProgramacionBinding;
import unicauca.movil.eventmpro.db.DiasDao;
import unicauca.movil.eventmpro.db.PonenteDao;
import unicauca.movil.eventmpro.models.Dias;
import unicauca.movil.eventmpro.net.HttpAsyncTask;
import unicauca.movil.eventmpro.util.L;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProgramacionFragment extends Fragment implements HttpAsyncTask.OnResponseReceived, ProgramacionAdapter.OnProgramacionListener {

    private static final String EXTRA_DAY = "day";
    private static final String EXTRA_COMMAND = "commnad";

    String comando;
    int dia;

    ProgramacionAdapter adapter;

    DiasDao dao;

    Gson gson;

    FragmentProgramacionBinding binding;


    public static ProgramacionFragment newInstance(int day, String commnad) {
        ProgramacionFragment fragment = new ProgramacionFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRA_DAY, day);
        args.putString(EXTRA_COMMAND, commnad);

        fragment.setArguments(args);
        return fragment;
    }

    public ProgramacionFragment() {
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

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_programacion, container, false);



        L.data1 = new ArrayList<>();
        adapter = new ProgramacionAdapter(getLayoutInflater(), L.data1, this);
        binding.recycler.setAdapter(adapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        dao = new DiasDao(getContext());


        gson = new Gson();
        loadData();



        return binding.getRoot();
    }

    public void loadData() {

        if (!verificaConexion(getContext())) {
            Toast.makeText(getContext(),
                    "Por favor conectate a internet para obtener la programacion mas reciente.", Toast.LENGTH_SHORT)
                    .show();
            List<Dias> list = dao.getAllByDay(dia);

            if (list.size() <= 0) {
                //region Agragar los datos a la base de datos
                //Dia1
                Dias d1 = new Dias();
                d1.setIdd(1);
                d1.setIdo(1);
                d1.setHora("07:00 am - 08:20 am");
                d1.setEvento("");
                d1.setTitulo("Registro & Acreditación de los Asistentes");
                d1.setConferencista("");
                d1.setEmpresa("");
                d1.setLugar("Teatro Guillermo Leon Valencia");

                Dias d2 = new Dias();
                d2.setIdd(1);
                d2.setIdo(2);
                d2.setHora("08:20 am - 09:10 am");
                d1.setEvento("");
                d2.setTitulo("Bienvenida - Inauguración");
                d1.setConferencista("");
                d1.setEmpresa("");
                d2.setLugar("Teatro Guillermo Leon Valencia");

                Dias d3 = new Dias();
                d3.setIdd(1);
                d3.setIdo(3);
                d3.setHora("09:10 am - 10:00 am");
                d1.setEvento("");
                d3.setEvento("CONFERENCIA DE APERTURA");
                d3.setTitulo("\"La Red del Futuro\"");
                d3.setConferencista("Jairo Angulo");
                d3.setEmpresa("STI");
                d3.setLugar("Teatro Guillermo Leon Valencia");

                Dias d4 = new Dias();
                d4.setIdd(1);
                d4.setIdo(4);
                d4.setHora("10:00 am - 10:30 am");
                d1.setEvento("");
                d4.setTitulo("Refrigerio");
                d1.setConferencista("");
                d1.setEmpresa("");
                d4.setLugar("Teatro Guillermo Leon Valencia");

                Dias d5 = new Dias();
                d5.setIdd(1);
                d5.setIdo(5);
                d5.setHora("10:30 am - 11:15 am");
                d5.setEvento("CONFERENCIA");
                d5.setTitulo("Liderando la era digital con soluciones inteligentes");
                d5.setConferencista("Jhonny Lopez Sandoval");
                d5.setEmpresa("AVAYA & Yeapdata");
                d5.setLugar("Teatro Guillermo Leon Valencia");

                Dias d6 = new Dias();
                d6.setIdd(1);
                d6.setIdo(6);
                d6.setHora("11:15 am - 12:00 m");
                d6.setEvento("CONFERENCIA");
                d6.setTitulo("\"Transformación Digital de Negocios\"");
                d6.setConferencista("José Roure");
                d6.setEmpresa("Claro");
                d6.setLugar("Teatro Guillermo Leon Valencia");

                Dias d7 = new Dias();
                d7.setIdd(1);
                d7.setIdo(7);
                d7.setHora("12:00 m - 02:00 pm");
                d7.setEvento("");
                d7.setTitulo("RECESO");
                d7.setConferencista("");
                d7.setEmpresa("");
                d7.setLugar("Alrededores Ver ubicación Para Guisarse");

                Dias d8 = new Dias();
                d8.setIdd(1);
                d8.setIdo(8);
                d8.setHora("02:00 pm - 02: 50 pm");
                d8.setEvento("CONFERENCIA");
                d8.setTitulo("Towards Networked Society");
                d8.setConferencista("Edwin Bravo");
                d8.setEmpresa("Ericsson");
                d8.setLugar("Teatro Guillermo Leon Valencia");

                Dias d9 = new Dias();
                d9.setIdd(1);
                d9.setIdo(9);
                d9.setHora("02:50 pm - 03:40 pm");
                d9.setEvento("CONFERENCIA");
                d9.setTitulo("New Trends in Telco Digitization");
                d9.setConferencista("Andrés Cosme");
                d9.setEmpresa("Huawei");
                d9.setLugar("Teatro Guillermo Leon Valencia");

                Dias d10 = new Dias();
                d10.setIdd(1);
                d10.setIdo(10);
                d10.setHora("03:40 pm - 04:10 pm");
                d10.setEvento("");
                d10.setTitulo("REFRIGERIO");
                d10.setConferencista("");
                d10.setEmpresa("");
                d10.setLugar("Teatro Guillermo Leon Valencia");

                Dias d11 = new Dias();
                d11.setIdd(1);
                d11.setIdo(11);
                d11.setHora("04:10 pm - 05:10 pm ");
                d11.setEvento("CONFERENCIA");
                d11.setTitulo("Nokia, Internet of Things");
                d11.setConferencista("Hugo Campos");
                d11.setEmpresa("Nokia");
                d11.setLugar("Teatro Guillermo Leon Valencia");

                Dias d12 = new Dias();
                d12.setIdd(1);
                d12.setIdo(12);
                d12.setHora("05:10 pm - 06:00 pm");
                d12.setEvento("CONFERENCIA");
                d12.setTitulo("Big Broadband");
                d12.setConferencista("Alejandra Joaquí");
                d12.setEmpresa("ZTE");
                d12.setLugar("Teatro Guillermo Leon Valencia");

                Dias d13 = new Dias();
                d13.setIdd(1);
                d13.setIdo(13);
                d13.setHora("8:00 p.m. - 11:00 p.m");
                d13.setEvento("");
                d13.setTitulo("Coctel de Bienvenida");
                d13.setConferencista("");
                d13.setEmpresa("");
                d13.setLugar("Casa Mosquera");

                //Dia 2
                Dias d21 = new Dias();
                d21.setIdd(2);
                d21.setIdo(1);
                d21.setHora("07:30 am - 08:00 am");
                d21.setEvento("");
                d21.setTitulo("Recepción de los Asistentes");
                d21.setConferencista("");
                d21.setEmpresa("");
                d21.setLugar("Teatro Guillermo Leon Valencia");

                Dias d22 = new Dias();
                d22.setIdd(2);
                d22.setIdo(2);
                d22.setHora("08:00 am - 09:00 am");
                d22.setEvento("CONFERENCIA");
                d22.setTitulo("Banda no Licenciada: una solución para el diseño de una Red Multiservicios de Cobertura");
                d22.setConferencista("Alejandro Muñoz");
                d22.setEmpresa("Dobleclick Software e Ingeniería");
                d22.setLugar("Teatro Guillermo Leon Valencia");

                Dias d23 = new Dias();
                d23.setIdd(2);
                d23.setIdo(3);
                d23.setHora("09:00 am - 09:50 am");
                d23.setEvento("CONFERENCIA");
                d23.setTitulo("Popayán: Ciudad Inteligente en Movilidad");
                d23.setConferencista("Daniel Pajoy");
                d23.setEmpresa("EMTEL S.A. ESP");
                d23.setLugar("Teatro Guillermo Leon Valencia");

                Dias d24 = new Dias();
                d24.setIdd(2);
                d24.setIdo(4);
                d24.setHora("09:50 am - 10:20 am\n");
                d24.setEvento("Refrigerio");
                d24.setTitulo("");
                d24.setConferencista("");
                d24.setEmpresa("");
                d24.setLugar("Teatro Guillermo Leon Valencia");

                Dias d25 = new Dias();
                d25.setIdd(2);
                d25.setIdo(5);
                d25.setHora("09:50 am - 10:20 am");
                d25.setEvento("CONFERENCIA");
                d25.setTitulo("");
                d25.setConferencista("");
                d25.setEmpresa("");
                d25.setLugar("Teatro Guillermo Leon Valencia");

                Dias d26 = new Dias();
                d26.setIdd(2);
                d26.setIdo(6);
                d26.setHora("10:20 am - 11:10 am");
                d26.setEvento("CONFERENCIA");
                d26.setTitulo("Política publica para el uso y apropiación de las TIC en Colombia");
                d26.setConferencista("Nicolás Silva Cortés");
                d26.setEmpresa("MINTIC");
                d26.setLugar("Teatro Guillermo Leon Valencia");

                Dias d27 = new Dias();
                d27.setIdd(2);
                d27.setIdo(7);
                d27.setHora("12:00 m - 02:00 pm");
                d27.setEvento("RECESO");
                d27.setTitulo("");
                d27.setConferencista("");
                d27.setEmpresa("");
                d27.setLugar("Alrededores, Consultar Ubicación");

                Dias d28 = new Dias();
                d28.setIdd(2);
                d28.setIdo(8);
                d28.setHora("02:00 pm - 02: 40 pm");
                d28.setEvento("CONFERENCIA");
                d28.setTitulo("Virtualización de ambientes de desarrollo, pruebas y producción");
                d28.setConferencista("Miguel Díaz Solarte");
                d28.setEmpresa("CodeScrum");
                d28.setLugar("Teatro Guillermo Leon Valencia");

                Dias d29 = new Dias();
                d29.setIdd(2);
                d29.setIdo(9);
                d29.setHora("02:40 pm - 03:40 pm ");
                d29.setEvento("CONFERENCIA");
                d29.setTitulo("Mercado mayorista de fibra óptica: base para el Internet de las Nano Cosas");
                d29.setConferencista("ván Sanchez Medina");
                d29.setEmpresa("UFINET");
                d29.setLugar("Teatro Guillermo Leon Valencia");

                Dias d210 = new Dias();
                d210.setIdd(2);
                d210.setIdo(10);
                d210.setHora("03:40 pm - 04:10 pm");
                d210.setEvento("Refrigerio");
                d210.setTitulo("");
                d210.setConferencista("");
                d210.setEmpresa("");
                d210.setLugar("Teatro Guillermo Leon Valencia");

                Dias d211 = new Dias();
                d211.setIdd(2);
                d211.setIdo(11);
                d211.setHora("04:10 pm - 05:10 pm");
                d211.setEvento("CONFERENCIA");
                d211.setTitulo("Big Data");
                d211.setConferencista("Diego Gomez Sevilla");
                d211.setEmpresa("Oracle");
                d211.setLugar("Teatro Guillermo Leon Valencia");

                Dias d212 = new Dias();
                d212.setIdd(2);
                d212.setIdo(12);
                d212.setHora("05:10 pm - 06:00 pm");
                d212.setEvento("CONFERENCIA");
                d212.setTitulo("Emprender, Fracasar, Reinventarse y Gozárselo");
                d212.setConferencista("Jose Gerardo Acuña");
                d212.setEmpresa("Infomedia");
                d212.setLugar("Teatro Guillermo Leon Valencia");

                Dias d213 = new Dias();
                d213.setIdd(2);
                d213.setIdo(13);
                d213.setHora("08:00 pm - 11:00 pm");
                d213.setEvento("");
                d213.setTitulo("Paseo Turístico en Chiva & Rumba en Discoteca");
                d213.setConferencista("");
                d213.setEmpresa("");
                d213.setLugar("Reloj de sol, Consultar Ubicación");

                //Dia3

                Dias d31 = new Dias();
                d31.setIdd(3);
                d31.setIdo(1);
                d31.setHora("08:00 am - 09:00 am");
                d31.setEvento("");
                d31.setTitulo("Bienvenida");
                d31.setConferencista("");
                d31.setEmpresa("");
                d31.setLugar("Auditorio Facultad de Sauld");

                Dias d32 = new Dias();
                d32.setIdd(3);
                d32.setIdo(2);
                d32.setHora("9:00 am - 10:00 am");
                d32.setEvento("CONFERENCIA");
                d32.setTitulo("Internet de la cosas y algunos casos de éxito");
                d32.setConferencista("Carolina Campo, Guillaume Leblanc, Jair Mazabue");
                d32.setEmpresa("Makrosoft & Phaxi");
                d32.setLugar("Auditorio Facultad de Sauld");

                Dias d33 = new Dias();
                d33.setIdd(3);
                d33.setIdo(3);
                d33.setHora("10:00 am - 11:00 am");
                d33.setEvento("CONFERENCIA");
                d33.setTitulo("El impacto de la tecnología en la Experiencia del Cliente");
                d33.setConferencista("Ma. del Pilar Rivera T.");
                d33.setEmpresa("PMK & 360ucsolutions");
                d33.setLugar("Auditorio Facultad de Sauld");

                Dias d34 = new Dias();
                d34.setIdd(3);
                d34.setIdo(4);
                d34.setHora("11:00 a. m. - 11:30 am");
                d34.setEvento("");
                d34.setTitulo("Refrigerio");
                d34.setConferencista("");
                d34.setEmpresa("");
                d34.setLugar("Auditorio Facultad de Sauld");

                Dias d35 = new Dias();
                d35.setIdd(3);
                d35.setIdo(5);
                d35.setHora("11:30 am - 12:15 pm");
                d35.setEvento("CONFERENCIA");
                d35.setTitulo("Operadores Móviles Virtuales – OMV, retos y oportunidades");
                d35.setConferencista("Carlos Eduardo Delgado Arana");
                d35.setEmpresa("TigoUNE");
                d35.setLugar("Auditorio Facultad de Sauld");

                Dias d36 = new Dias();
                d36.setIdd(3);
                d36.setIdo(6);
                d36.setHora("12:15 pm - 1:00 pm");
                d36.setEvento("CONFERENCIA");
                d36.setTitulo("¿Cómo construir Redes y una Marca Personal para Emprender?");
                d36.setConferencista("Patricia Helena Fierro Vitola");
                d36.setEmpresa("SkyOne SAS");
                d36.setLugar("Auditorio Facultad de Sauld");

                Dias d37 = new Dias();
                d37.setIdd(3);
                d37.setIdo(7);
                d37.setHora("1:00 pm - 2:00 pm");
                d37.setEvento("Almuerzo");
                d37.setTitulo("");
                d37.setConferencista("");
                d37.setEmpresa("");
                d37.setLugar("Auditorio Facultad de Sauld");

                Dias d38 = new Dias();
                d38.setIdd(3);
                d38.setIdo(8);
                d38.setHora("2:00 p.m. - 3:30 p.m");
                d38.setEvento("FORO");
                d38.setTitulo("Competencias del Egresado de Ingeniería EyT para encarar el actual mercado laboraL");
                d38.setConferencista("Carlos Eduardo Delgado, Eva Juliana Maya, Fabián Muñoz Erazo, Iván Sánchez Medina, Ma. del Pilar Rivera T., Oscar Calderón Cortés, Patricia Helena Fierro");
                d38.setEmpresa("Conduce:ASEFIET\n");
                d38.setLugar("Auditorio Facultad de Sauld");

                Dias d39 = new Dias();
                d39.setIdd(3);
                d39.setIdo(9);
                d39.setHora("3:30 pm - 04:00 pm");
                d39.setEvento("");
                d39.setTitulo("CLAUSURA Y ENTREGA DE CERTIFICADOS");
                d39.setConferencista("");
                d39.setEmpresa("");
                d39.setLugar("Auditorio Facultad de Sauld");

                Dias d310 = new Dias();
                d310.setIdd(3);
                d310.setIdo(10);
                d310.setHora("07:30 pm - 3:00 am ");
                d310.setEvento("");
                d310.setTitulo("FIESTA DE CLAUSURA (TRAJE DE GALA)");
                d310.setConferencista("");
                d310.setEmpresa("");
                d310.setLugar("Club de Leones");


                dao.insert(d1);
                dao.insert(d2);
                dao.insert(d3);
                dao.insert(d4);
                dao.insert(d5);
                dao.insert(d6);
                dao.insert(d7);
                dao.insert(d8);
                dao.insert(d9);
                dao.insert(d10);
                dao.insert(d11);
                dao.insert(d12);
                dao.insert(d13);
                dao.insert(d21);
                dao.insert(d22);
                dao.insert(d23);
                dao.insert(d24);
                dao.insert(d25);
                dao.insert(d26);
                dao.insert(d27);
                dao.insert(d28);
                dao.insert(d29);
                dao.insert(d210);
                dao.insert(d211);
                dao.insert(d212);
                dao.insert(d213);
                dao.insert(d31);
                dao.insert(d32);
                dao.insert(d33);
                dao.insert(d34);
                dao.insert(d35);
                dao.insert(d36);
                dao.insert(d37);
                dao.insert(d38);
                dao.insert(d39);
                dao.insert(d310);
                //endregion
            }

            for (Dias d : list){
                L.data1.add(d);
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

        L.data1.clear();
        for (Dias d : res) {
            L.data1.add(d);
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


    @Override
    public void onProgramacionClick(int position) {

    }
}
