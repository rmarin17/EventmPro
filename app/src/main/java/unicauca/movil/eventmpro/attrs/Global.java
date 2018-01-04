package unicauca.movil.eventmpro.attrs;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import unicauca.movil.eventmpro.R;

/**
 * Created by Ricardo Marin on 29/11/2016.
 */

public class Global {

    @BindingAdapter("app:imgUrl")
    public static void setImageUrl(ImageView imageView, String url){
        Context context =  imageView.getContext();
        Uri uri = Uri.parse(url);
        if (!verificaConexion(context)) {
            Toast.makeText(context,
                    R.string.conection_internet_imagenes, Toast.LENGTH_SHORT)
                    .show();
            imageView.setImageResource(R.drawable.ic_businessman);
        }

        else{
            Picasso.with(context).load(uri).into(imageView);
        }

    }

    @BindingAdapter("app:imgUrlEvent")
    public static void setImageUrlEvent(ImageView imageView, String url){
        Context context =  imageView.getContext();
       if(url!=null) {
            Uri uri = Uri.parse(url);
            if (!verificaConexion(context)) {
                Toast.makeText(context,
                        R.string.conection_internet_imagenes, Toast.LENGTH_SHORT)
                        .show();
                imageView.setImageResource(R.drawable.event);
            } else {
                Picasso.with(context).load(uri).into(imageView);
            }
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
