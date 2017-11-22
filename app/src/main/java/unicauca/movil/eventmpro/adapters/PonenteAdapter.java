package unicauca.movil.eventmpro.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import unicauca.movil.eventmpro.R;
import unicauca.movil.eventmpro.databinding.TemplatePonentesBinding;
import unicauca.movil.eventmpro.models.Ponente;
import unicauca.movil.eventmpro.util.L;

/**
 * Created by RicardoM on 10/11/2017.
 */

public class PonenteAdapter extends RecyclerView.Adapter<PonenteAdapter.PonenteViewHolder> {


    public interface OnPonenteListener{
        void onPonente(int position);
    }

    LayoutInflater inflater;
    OnPonenteListener onPonenteListener;
    List<Ponente> data;

    public PonenteAdapter(LayoutInflater inflater, List<Ponente> data, OnPonenteListener onPonenteListener) {
        this.onPonenteListener = onPonenteListener;
        this.data = data;
        this.inflater = inflater;
    }

    @Override
    public PonenteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v  = inflater.inflate(R.layout.template_ponentes, parent, false);
        PonenteViewHolder holder = new PonenteViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(PonenteViewHolder holder, int position) {
        holder.binding.setPonen(data.get(position));
        holder.binding.card.setTag(position);
        holder.binding.setHandler(this);
    }

    @Override
    public int getItemCount() {
        return L.data.size();
    }

    public void onClickPonente(int position){
        onPonenteListener.onPonente(position);
    }

    //Retorna el tipo de view o Celda
    /*@Override
    public int getItemViewType(int position) { return 0;}*/

    public static class PonenteViewHolder extends RecyclerView.ViewHolder{

        TemplatePonentesBinding binding;

        public PonenteViewHolder(View itemView) {
            super(itemView);

            binding = DataBindingUtil.bind(itemView);

        }
    }
}
