package com.example.duxeles.pbebidas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duxeles.R;

import java.util.ArrayList;

public class ListaBebidasAdapter extends RecyclerView.Adapter<ListaBebidasAdapter.BebidasViewHolder> {

    ArrayList<bebidas> listaBebidas;

    public ListaBebidasAdapter( ArrayList<bebidas> listaBebidas){
        this.listaBebidas = listaBebidas;
    }

    @NonNull
    @Override //Asigna el diseño de cada elemento de la lista
    public BebidasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_articulo, null, false);

        return new BebidasViewHolder(view);
    }

    @Override //Asigna los elementos correspondidentes para cada campo
    public void onBindViewHolder(@NonNull BebidasViewHolder holder, int position) {
        holder.nom.setText(listaBebidas.get(position).getNom());
        holder.precio.setText(listaBebidas.get(position).getPrecio());
        holder.desc.setText(listaBebidas.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
       return listaBebidas.size();
    }

    public class BebidasViewHolder extends RecyclerView.ViewHolder {

        TextView nom, precio, desc;



        public BebidasViewHolder(@NonNull View itemView) {
            super(itemView);

            nom = itemView.findViewById(R.id.nom);
            precio = itemView.findViewById(R.id.precio);
            desc = itemView.findViewById(R.id.desc);
            //falta img
        }
    }
}
