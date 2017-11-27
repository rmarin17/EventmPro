package unicauca.movil.eventmpro;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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

        // Add a marker in Sydney and move the camera
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
        mMap.addMarker(new MarkerOptions().position(mosquera).title("Casa Mosquera (Coctel de Bienvenida)"));
    }
}
