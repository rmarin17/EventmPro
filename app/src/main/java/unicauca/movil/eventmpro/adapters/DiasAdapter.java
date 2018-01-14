package unicauca.movil.eventmpro.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

;import unicauca.movil.eventmpro.R;
import unicauca.movil.eventmpro.models.Dias;

/**
 * Created by RicardoM on 15/10/2016.
 */

public class DiasAdapter extends BaseAdapter {
    Context context;

    List<Dias> data;


    public DiasAdapter(Context context, List<Dias> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null)
            v = View.inflate(context, R.layout.item_dias, null);

        Dias d = data.get(position);

        TextView hora = (TextView) v.findViewById(R.id.hora);
        TextView evento = (TextView) v.findViewById(R.id.evento);
        TextView titulo = (TextView) v.findViewById(R.id.titulo);
        TextView conferencista = (TextView) v.findViewById(R.id.conferencista);
        TextView empresa = (TextView) v.findViewById(R.id.empresa);
        TextView lugar = (TextView) v.findViewById(R.id.lugar);

        hora.setText(d.getHora());
        evento.setText(d.getEvento());
        titulo.setText(d.getTitulo());
        conferencista.setText(d.getConferencista());
        empresa.setText(d.getEmpresadias());
        lugar.setText(d.getLugar());

        return v;
    }
}
