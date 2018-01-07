package unicauca.movil.eventmpro;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.view.MenuItem;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import unicauca.movil.eventmpro.databinding.ActivityDetailPonenteBinding;
import unicauca.movil.eventmpro.databinding.ContentDetailPonenteBinding;
import unicauca.movil.eventmpro.models.Ponente;
import unicauca.movil.eventmpro.util.L;

public class DetailPonente extends AppCompatActivity implements Callback {

    ActivityDetailPonenteBinding binding;
    ContentDetailPonenteBinding binding2;

    String link=null;

    public static final String EXTRA_POS = "pos";
    public static final int DARKEN = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_detail_ponente);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_detail_ponente);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int pos =  getIntent().getExtras().getInt(EXTRA_POS);
        Ponente pon = L.data.get(pos);

        binding.setPonen(pon);
        binding.setHandler(this);
        link = pon.getLink();


        if (!verificaConexion(this)) {
            Toast.makeText(this,
                    R.string.conection_internet_imagenes, Toast.LENGTH_SHORT)
                    .show();
            binding.img.setImageResource(R.drawable.ic_businessman);
        }

        else{
            Picasso.with(this)
                    .load(Uri.parse(pon.getImagen()))
                    .into(binding.img, this);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();

    }

    @Override
    public void onSuccess() {

        BitmapDrawable drawable = (BitmapDrawable) binding.img.getDrawable();
        Palette palette =  new Palette
                .Builder(drawable.getBitmap())
                .generate();

        int colorDefault = ContextCompat.getColor(this, R.color.colorPrimary);
        int color = palette.getVibrantColor(colorDefault);
        binding.collapsingToolbar.setContentScrimColor(color);

    }

    @Override
    public void onError() {

    }

    public void onLink() {
        if (link.isEmpty()) {
            Toast.makeText(this,
                    R.string.conection_internet_linkedin, Toast.LENGTH_SHORT)
                    .show();
        }
        else{
            Uri uri = Uri.parse(link);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
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


}
