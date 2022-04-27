package com.example.duxeles.pbebidas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duxeles.R;
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
        recyclerViewBebidas=(RecyclerView)findViewById(R.id.recyclerLista);
        //Definimos la forma de la lista
        recyclerViewBebidas.setLayoutManager(new LinearLayoutManager(this));

      //  AdminSQLiteOpenHelper SQLiteHelper = new AdminSQLiteOpenHelper(getApplicationContext());

       // bebidasAdaptador = new RecyclerViewAdaptador(obtenerBebidas());
        //Asignamos toda la informacion en el recycler view del layout
        recyclerViewBebidas.setAdapter(adaptadorBebidas);
    }

    public List<bebidas> obtenerBebidas() {

        List<bebidas> bebida = new ArrayList<>();
        bebida.add(new bebidas("Nombre1", "precio", "descripcion", R.drawable.platillos)); //se agregan los datos a agregar en la lista
        bebida.add(new bebidas("Nombre2", "precio", "descripcion", R.drawable.platillos)); //se agregan los datos a agregar en la lista
        bebida.add(new bebidas("Nombre3", "precio", "descripcion", R.drawable.platillos)); //se agregan los datos a agregar en la lista
        bebida.add(new bebidas("Nombre4", "precio", "descripcion", R.drawable.platillos)); //se agregan los datos a agregar en la lista
        return bebida;
    }
}
