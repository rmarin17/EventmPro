package unicauca.movil.eventmpro;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.view.ViewTreeObserver;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import unicauca.movil.eventmpro.databinding.ActivityDetailPonenteBinding;
import unicauca.movil.eventmpro.models.Ponente;
import unicauca.movil.eventmpro.util.L;

public class DetailPonente extends AppCompatActivity implements Callback {

    ActivityDetailPonenteBinding binding;

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


        Picasso.with(this)
                .load(Uri.parse(pon.getImagen()))
                .into(binding.img, this);

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
}
