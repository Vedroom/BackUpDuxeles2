package com.example.duxeles.pbebidas;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import com.example.duxeles.R;//<-----------------


public class RecyclerViewAdaptador extends RecyclerView.Adapter<RecyclerViewAdaptador.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView nom, precio, desc;
        ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);

            nom=(TextView)itemView.findViewById(R.id.nom);
            precio=(TextView)itemView.findViewById(R.id.precio);
            desc=(TextView)itemView.findViewById(R.id.desc);
            img=(ImageView)itemView.findViewById(R.id.img);
        }
    }

    //Variable para almacenar los datos
    public List<bebidas> bebidasLista;

    public RecyclerViewAdaptador(List<bebidas> bebidasLista) {
        this.bebidasLista = bebidasLista;
    }

    //Agrega un nuevo item a la lista haciendo uso de un layout dentro de otro layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_articulo,parent,false);
        ViewHolder viewHolder= new ViewHolder(view);
        return viewHolder;
    }


    //Realiza las modificaciones del contenido para cada item
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nom.setText(bebidasLista.get(position).getNom());
        holder.precio.setText(bebidasLista.get(position).getPrecio());
        holder.desc.setText(bebidasLista.get(position).getDesc());
        holder.img.setImageResource(bebidasLista.get(position).getImg()); //foto?
    }

    //Permite determinar al adaptador la cantidad de elementos que se van a procesar
    @Override
    public int getItemCount() {
        return bebidasLista.size();
    }
}
