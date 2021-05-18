package com.example.gestordeproyectos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.List;

public class AdaptadorProyecto extends BaseAdapter{
    private Context context;
    private int layout;
    private List<Proyecto> names;
    public AdaptadorProyecto(Context context, int layout, List<Proyecto> names){
        this.context = context;
        this.layout = layout;
        this.names = names;
    }

    @Override
    public int getCount() {
        return this.names.size();
    }

    @Override
    public Object getItem(int position) {
        return this.names.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override

    public View getView(int position, View convertView, ViewGroup viewGroup) {
        // Copiamos la vista
        View v = convertView;

        //Inflamos la vista con nuestro propio layout
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);

        v= layoutInflater.inflate(R.layout.activity_adaptador_proyecto, null);
        // Valor actual según la posición

        String currentName = names.get(position).getNombre();
        String date = names.get(position).getFechaDeInicio();

        // Referenciamos el elemento a modificar y lo rellenamos
        TextView nombreVista = (TextView) v.findViewById(R.id.nombreVista);
        nombreVista.setText(currentName.toString());
        TextView fechaVista = (TextView) v.findViewById(R.id.fechaVista);
        fechaVista.setText(date);

        if (names.get(position).getCompletado()) {
            v.setBackgroundColor(ContextCompat.getColor(context, R.color.completado));
        }
        //Devolvemos la vista inflada
        return v;
    }

    /*private boolean compararFechas(String p, String a) {

        int fProyecto = getFechaEnNumero(p);
        int fActual = getFechaEnNumero(a);

        return fProyecto < fActual;
    }

    private int getFechaEnNumero(String p) {
        String valores1[] = p.split("[/]");
        String fProyecto  = valores1[2]+valores1[1]+valores1[0];
        int fProyecto1 = Integer.valueOf(fProyecto);
        return fProyecto1;
    }*/
}
