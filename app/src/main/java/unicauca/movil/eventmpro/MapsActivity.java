package unicauca.movil.eventmpro;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import unicauca.movil.eventmpro.beacons.BeaconLocationService;
import unicauca.movil.eventmpro.beacons.BeaconReceiver;
import unicauca.movil.eventmpro.databinding.ActivityMapsBinding;
import unicauca.movil.eventmpro.db.BeaconsDao;
import unicauca.movil.eventmpro.db.ConectionsDao;
import unicauca.movil.eventmpro.db.EventoDao;
import unicauca.movil.eventmpro.db.UbicacionDao;
import unicauca.movil.eventmpro.models.Beacons;
import unicauca.movil.eventmpro.models.Conections;
import unicauca.movil.eventmpro.models.Dias;
import unicauca.movil.eventmpro.models.Evento;
import unicauca.movil.eventmpro.models.Ponente;
import unicauca.movil.eventmpro.models.Ubicacion;
import unicauca.movil.eventmpro.net.HttpAsyncTask;
import unicauca.movil.eventmpro.util.L;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, DialogInterface.OnClickListener, HttpAsyncTask.OnResponseReceived {

    ActivityMapsBinding binding;
    private GoogleMap mMap;
    private UiSettings mUiSettings;

    Ubicacion u;
    Beacons b;
    Conections c;
    ConectionsDao cdao;
    EventoDao edao;
    UbicacionDao udao;
    BeaconsDao bdao;
    Gson gson;
    Double blat, blng;
    String titulo, comando1, comando2;
    int ban1=0, ban2=0, tamañoU=0, tamañoB=0;
    Intent intent;
    BeaconReceiver receiver;
    Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_maps);
        binding.setHandler(this);

        c = new Conections();
        u = new Ubicacion();
        b = new Beacons();
        udao = new UbicacionDao(this);
        bdao = new BeaconsDao(this);
        cdao = new ConectionsDao(this);
        edao = new EventoDao(this);
        gson = new Gson();



        List<Evento> elist = edao.getAll();
        long ide = elist.get(0).getIde();
        List<Conections> listc = cdao.getAll();
        if(listc.size() > 0 ) {
            for (Conections c : listc) {
                comando1 = c.getUbicacion()+""+ide;
                comando2 = c.getBeacons()+""+ide;
            }
        }
        start();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mUiSettings = mMap.getUiSettings();

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            //something here
        }

        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setCompassEnabled(true);
        mUiSettings.setMyLocationButtonEnabled(true);
        mUiSettings.setScrollGesturesEnabled(true);
        mUiSettings.setZoomGesturesEnabled(true);
        mUiSettings.setTiltGesturesEnabled(true);
        mUiSettings.setRotateGesturesEnabled(true);
        mUiSettings.setAllGesturesEnabled(true);
        mUiSettings.isCompassEnabled();
        mUiSettings.isRotateGesturesEnabled();
        mUiSettings.isScrollGesturesEnabled();

        List<Ubicacion> list = udao.getAll();
        cargaOnlineUbicacion();
        for (Ubicacion u : list) {
            LatLng mark = new LatLng(u.getLat(), u.getLng());
            mMap.addMarker(new MarkerOptions().position(mark).title(u.getTituloubicacion()).snippet(u.getDireccion()));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mark,16));
        }

    }

    public void start() {

            SystemRequirementsChecker.checkWithDefaultDialogs(this);
            //Toast.makeText(this, "Buscando Sugerencias", Toast.LENGTH_SHORT).show();
            //BEACONS
            intent = new Intent(this, BeaconLocationService.class);
            startService(intent);
            receiver = new BeaconReceiver();
            IntentFilter filter = new IntentFilter(BeaconReceiver.ACTION_BEACON);
            registerReceiver(receiver, filter);
            disposable = receiver.getBeaconNotify()
                    .distinctUntilChanged(new Function<Integer[], Object>() {
                        @Override
                        public Object apply(Integer[] integers) throws Exception {
                            return integers[0];
                        }
                    })
                    .subscribe(new Consumer<Integer[]>() {
                        @Override
                        public void accept(Integer[] integers) throws Exception {
                            // integer = MAJOR del beacon.
                            Integer major1 = integers[0];
                            List<Beacons> list = bdao.getAll();
                            cargaOnlineBeacons();
                                if(list.size() > 0 ) {
                                    for (Beacons b : list) {
                                        if (major1 == b.getMajor()) {
                                            titulo = b.getBtitulo();
                                            blat = b.getBlat();
                                            blng = b.getBlng();
                                            beaconAlert(titulo);
                                        }
                                    }
                                }
                            }
                            //Toast.makeText(MapsActivity.this, "" + integers[0] + " " + integers[1], Toast.LENGTH_SHORT).show();
                            //Log.i("BEACONINFO", "MARJOR1: " + integers[0] + " MAJOR2:" + integers[1]);
                    });
    }

    public void stop(){
        Toast.makeText(this, "Detener Servicio", Toast.LENGTH_SHORT).show();
        stopService(intent);
        disposable.dispose();
        unregisterReceiver(receiver);
    }

    public void beaconAlert(String titulo) {
        AlertDialog alert = new AlertDialog.Builder(this)
                .setTitle(titulo)
                .setIcon(R.drawable.ic_warning)
                .setMessage(R.string.alert_msg_beacon)
                .setPositiveButton(R.string.ok,this)
                .setNegativeButton(R.string.cancel, this)
                .create();
        alert.show();
    }

    public void normal(){
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }
    public void hibrido(){
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }

    public void goToPrincipal(){
        Intent intent = new Intent(MapsActivity.this, DetailEvent.class);
        startActivity(intent);
    }
    public void goToPonente(){
        Intent intent = new Intent(MapsActivity.this, Ponentes.class);
        startActivity(intent);
    }
    public void goToHorario(){
        Intent intent = new Intent(MapsActivity.this, Programacion.class);
        startActivity(intent);
    }
    public void goToNotificaciones(){
        Intent intent = new Intent(MapsActivity.this, Notification.class);
        startActivity(intent);
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if( i == DialogInterface.BUTTON_POSITIVE) {
                LatLng encuentro = new LatLng(blat, blng);
                mMap.addMarker(new MarkerOptions().position(encuentro).title(titulo));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(encuentro, 30));
        }
    }
    public void cargaOnlineUbicacion() {
        if (!verificaConexion(this)) {
            Toast.makeText(this,
                    R.string.conection_internet, Toast.LENGTH_SHORT)
                    .show();
        }
        else
        {
            HttpAsyncTask tasku = new HttpAsyncTask(this);
            List<Ubicacion> listu = udao.getAll();
                tamañoU = listu.size();
                ban1 = 1;
                tasku.execute(comando1);
        }
    }

    public void cargaOnlineBeacons() {
        if (!verificaConexion(this)) {
            Toast.makeText(this,
                    R.string.conection_internet, Toast.LENGTH_SHORT)
                    .show();
        }
        else
        {
            HttpAsyncTask taskb = new HttpAsyncTask(this);
            List<Beacons> listb = bdao.getAll();
            tamañoB=listb.size();
            ban2 = 1;
            taskb.execute(comando2);
        }
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
    public void onResponse(boolean success, String json) {

        if (ban1 == 1) {
            Type lista = new TypeToken<List<Ubicacion>>() {
            }.getType();
            List<Ubicacion> res = gson.fromJson(json, lista);
            if (res!=null) {
                if (tamañoU != res.size()) {
                    udao.deleteAll();
                    for (Ubicacion u : res) {
                        udao.insert(u);
                    }
                    ban1 = 0;
                } else {
                    for (Ubicacion u : res) {
                        udao.update(u);
                    }
                    ban1 = 0;
                }
            }
        }

        if (ban2 == 1) {
            Type lista = new TypeToken<List<Beacons>>() {
            }.getType();
            List<Beacons> res = gson.fromJson(json, lista);

            if (res!=null) {
                if (tamañoB != res.size()) {
                    bdao.deleteAll();
                    for (Beacons b : res) {
                        bdao.insert(b);
                    }
                    ban2 = 0;
                } else {
                    for (Beacons b : res) {
                        bdao.update(b);
                    }
                    ban2 = 0;
                }
            }
            }

    }
}
