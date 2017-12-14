package unicauca.movil.eventmpro;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import unicauca.movil.eventmpro.beacons.BeaconLocationService;
import unicauca.movil.eventmpro.beacons.BeaconReceiver;
import unicauca.movil.eventmpro.databinding.ActivityMapsBinding;
import unicauca.movil.eventmpro.db.BeaconsDao;
import unicauca.movil.eventmpro.db.UbicacionDao;
import unicauca.movil.eventmpro.models.Beacons;
import unicauca.movil.eventmpro.models.Ponente;
import unicauca.movil.eventmpro.models.Ubicacion;
import unicauca.movil.eventmpro.util.L;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, DialogInterface.OnClickListener {

    ActivityMapsBinding binding;
    private GoogleMap mMap;
    private UiSettings mUiSettings;


    Ubicacion u;
    Beacons b;
    UbicacionDao udao;
    BeaconsDao bdao;

    Double blat, blng;
    String titulo;

    Intent intent;
    BeaconReceiver receiver;

    Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_maps);
        binding.setHandler(this);

        u = new Ubicacion();
        b = new Beacons();
        udao = new UbicacionDao(this);
        bdao = new BeaconsDao(this);

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

        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);




        /*GoogleMapOptions options = new GoogleMapOptions();


        options.mapType(GoogleMap.MAP_TYPE_SATELLITE)
                .compassEnabled(true)
                .rotateGesturesEnabled(true)
                .tiltGesturesEnabled(true)
                ;*/




        /*boolean success = googleMap.setMapStyle(new MapStyleOptions(getResources()
                .getString(R.string.style_json)));

        if (!success) {

        }
        // Position the map's camera near Sydney, Australia.
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(-34, 151)));*/




        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            List<Ubicacion> list = udao.getAll();

            if(list.size() > 0 ) {
                for (Ubicacion u : list) {
                    LatLng mark = new LatLng(u.getLat(), u.getLng());
                    mMap.addMarker(new MarkerOptions().position(mark).title(u.getTitulo()));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mark,16));
                }
            }

            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setCompassEnabled(true);
        mUiSettings.setMyLocationButtonEnabled(true);
        mMap.setMyLocationEnabled(true);
        mUiSettings.setScrollGesturesEnabled(true);
        mUiSettings.setZoomGesturesEnabled(true);
        mUiSettings.setTiltGesturesEnabled(true);
        mUiSettings.setRotateGesturesEnabled(true);
        mUiSettings.setAllGesturesEnabled(true);
        mUiSettings.isCompassEnabled();
        mUiSettings.isRotateGesturesEnabled();
        mUiSettings.isScrollGesturesEnabled();




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

                            if(list.size() > 0 ) {

                                for (Beacons b : list) {
                                    if (major1 == b.getMajor()) {
                                        titulo = b.getBtitulo();
                                        blat = b.getBlat();
                                        blng = b.getBlong();
                                        beaconAlert(titulo);
                                    }
                                }
                            }
                            //Toast.makeText(MapsActivity.this, "" + integers[0] + " " + integers[1], Toast.LENGTH_SHORT).show();
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

    /*public void beaconAlert2() {

        AlertDialog alert = new AlertDialog.Builder(this)
                .setTitle(R.string.alert_beacon2)
                .setIcon(R.drawable.ic_warning)
                .setMessage(R.string.alert_msg_beacon)
                .setPositiveButton(R.string.ok,this)
                .setNegativeButton(R.string.cancel, this)
                .create();
        alert.show();

    }
*/
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
}
