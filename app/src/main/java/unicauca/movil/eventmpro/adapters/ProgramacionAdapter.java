package unicauca.movil.eventmpro.adapters;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import unicauca.movil.eventmpro.R;
import unicauca.movil.eventmpro.databinding.TemplateProgramacionBinding;
import unicauca.movil.eventmpro.models.Dias;
import unicauca.movil.eventmpro.util.L;

import static unicauca.movil.eventmpro.util.L.data1;

/**
 * Created by RicardoM on 22/11/2017.
 */

public class ProgramacionAdapter extends RecyclerView.Adapter<ProgramacionAdapter.ProgramacionHolder>{


    public interface OnProgramacionListener{
            void onProgramacionClick(int position);
    }

    LayoutInflater inflater;
    List<Dias> data;
    OnProgramacionListener listener;

    public ProgramacionAdapter(LayoutInflater inflater, List<Dias> data, OnProgramacionListener listener) {
        this.inflater = inflater;
        this.data = data;
        this.listener = listener;
    }

    @Override
    public ProgramacionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.template_programacion, parent, false);
        return new ProgramacionHolder(v);
    }

    @Override
    public void onBindViewHolder(ProgramacionHolder holder, int position) {

        holder.binding.setProg(data.get(position));
        holder.binding.card.setTag(position);
        holder.binding.setHandler(this);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void onItemClick(int position){
        listener.onProgramacionClick(position);
    }


    static class ProgramacionHolder extends RecyclerView.ViewHolder{

        TemplateProgramacionBinding binding;

        public ProgramacionHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
