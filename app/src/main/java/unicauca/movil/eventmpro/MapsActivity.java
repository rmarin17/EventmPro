package unicauca.movil.eventmpro;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import unicauca.movil.eventmpro.beacons.BeaconLocationService;
import unicauca.movil.eventmpro.beacons.BeaconReceiver;
import unicauca.movil.eventmpro.databinding.ActivityMapsBinding;
import unicauca.movil.eventmpro.db.UbicacionDao;
import unicauca.movil.eventmpro.models.Ponente;
import unicauca.movil.eventmpro.models.Ubicacion;
import unicauca.movil.eventmpro.util.L;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    ActivityMapsBinding binding;
    private GoogleMap mMap;


    Ubicacion u;
    UbicacionDao udao;

    Intent intent;
    BeaconReceiver receiver;

    Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_maps);
        binding.setHandler(this);

        u = new Ubicacion();
        udao = new UbicacionDao(this);

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

        List<Ubicacion> list = udao.getAll();

        if(list.size() > 0 ) {
            for (Ubicacion u : list) {
                LatLng mark = new LatLng(u.getLat(), u.getLng());
                mMap.addMarker(new MarkerOptions().position(mark).title(u.getTitulo()));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mark,16));
            }
        }

       /* // Add a marker in Sydney and move the camera
        LatLng teatro = new LatLng(2.443176, -76.606018);
        mMap.addMarker(new MarkerOptions().position(teatro).title("Teatro Guillermo Leon Valencia"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(teatro,16));

        //chivas
        LatLng chivas = new LatLng(2.444333, -76.605218);
        mMap.addMarker(new MarkerOptions().position(chivas).title("Punto de Encuentro Paseo Turistico en Chivas"));

        //salud
        LatLng salud = new LatLng(2.451072, -76.598935);
        mMap.addMarker(new MarkerOptions().position(salud).title("Facultad de Ciencias de la Salud"));

        //club leones
        LatLng club = new LatLng(2.442702, -76.607193);
        mMap.addMarker(new MarkerOptions().position(club).title("Club de Leones (Fiesta de Clausura)"));

        //casa mosquera
        LatLng mosquera = new LatLng(2.442974, -76.605119);
        mMap.addMarker(new MarkerOptions().position(mosquera).title("Casa Mosquera (Coctel de Bienvenida)"));*/
    }


    public void start() {

            SystemRequirementsChecker.checkWithDefaultDialogs(this);

            Toast.makeText(this, "Buscando Sugerencias", Toast.LENGTH_SHORT).show();

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
                            Integer major2 = integers[1];


                            Toast.makeText(MapsActivity.this, "" + integers[0] + " " + integers[1], Toast.LENGTH_SHORT).show();
                            //Log.i("BEACONINFO", "MARJOR1: " + integers[0] + " MAJOR2:" + integers[1]);
                        }
                    });

    }

    public void stop(){

        Toast.makeText(this, "Detener Servicio", Toast.LENGTH_SHORT).show();
        stopService(intent);
        disposable.dispose();
        unregisterReceiver(receiver);
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

}
