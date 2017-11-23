package unicauca.movil.eventmpro.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import unicauca.movil.eventmpro.R;
import unicauca.movil.eventmpro.databinding.TemplateNotificationBinding;
import unicauca.movil.eventmpro.models.Mensaje;

/**
 * Created by RicardoM on 23/11/2017.
 */

public class MensajeAdapter extends RecyclerView.Adapter<MensajeAdapter.MensajeViewHolder>{


    public interface OnMensajeListener{
        void onMensaje(int position);
    }

    LayoutInflater inflater;
    List<Mensaje> data;
    OnMensajeListener listener;


    public MensajeAdapter(LayoutInflater inflater, List<Mensaje> data, OnMensajeListener listener) {
        this.inflater = inflater;
        this.data = data;
        this.listener = listener;
    }


    @Override
    public MensajeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.template_notification,parent, false);
        MensajeViewHolder holder = new MensajeViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(MensajeViewHolder holder, int position) {
        holder.binding.setNoti(data.get(position));
        holder.binding.card.setTag(position);
        holder.binding.setHandler(this);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void onItemClick(int position){
        listener.onMensaje(position);
    }

    public static class MensajeViewHolder extends RecyclerView.ViewHolder{

        TemplateNotificationBinding binding;

        public MensajeViewHolder(View itemView) {
            super(itemView);

            binding = DataBindingUtil.bind(itemView);

        }
    }

}
