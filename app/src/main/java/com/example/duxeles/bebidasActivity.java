package com.example.duxeles;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duxeles.pbebidas.RecyclerViewAdaptador;
import com.example.duxeles.pbebidas.bebidas;
import com.example.duxeles.pingredientes.ingrediente;
import com.example.duxeles.pplatillos.platillo;

import java.util.ArrayList;
import java.util.List;

public class bebidasActivity extends AppCompatActivity {

    //Parte para listar los elementos
    private RecyclerView recyclerViewBebidas;
    private RecyclerViewAdaptador adaptadorBebidas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Vinculamos la instancia recycler view con el recycler view del layout
        recyclerViewBebidas = (RecyclerView) findViewById(R.id.recyclerLista);
        //Definimos la forma de la lista
        recyclerViewBebidas.setLayoutManager(new LinearLayoutManager(this));

        //Asignamos toda la informacion en el recycler view del layout
        adaptadorBebidas = new RecyclerViewAdaptador(obtenerBebidas());
        recyclerViewBebidas.setAdapter(adaptadorBebidas);
    }

    public List<bebidas> obtenerBebidas() {

        List<bebidas> bebida = new ArrayList<>();
        bebida.add(new bebidas("Nombre", "precio", "descripcion", 1)); //se agregan los datos a agregar en la lista
        return bebida;
    }
}
