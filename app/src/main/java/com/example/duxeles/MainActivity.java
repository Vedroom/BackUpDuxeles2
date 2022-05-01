package com.example.duxeles;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.duxeles.pbebidas.ListaBebidasAdapter;

import com.example.duxeles.pbebidas.bebidas;
import com.example.duxeles.pbebidas.bebidasActivity;
import com.example.duxeles.pingredientes.ingrediente;
import com.example.duxeles.pplatillos.platillo;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView listaBebidas;
    ArrayList<bebidas> listaArrayBebidas;
    //private RecyclerView recyclerView

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listaBebidas=findViewById(R.id.recyclerLista);
        listaBebidas.setLayoutManager(new LinearLayoutManager(this));

        AdminSQLiteOpenHelper dbBebidas = new AdminSQLiteOpenHelper(MainActivity.this);

        listaArrayBebidas = new ArrayList<>();

        ListaBebidasAdapter adapter = new ListaBebidasAdapter(dbBebidas.mostrarBebida());
        //Se envia la información estructurada
        listaBebidas.setAdapter(adapter);


    }

    //BOTON PROVICIONAL
    /*public void Bebida (View view){
        Intent i = new Intent(this, bebidas.class);
        startActivity(i);
    }*/
    public void ingredientes (View view){
        Intent i = new Intent(this, ingrediente.class);
        startActivity(i);
    }

    public void platillos (View view){
        Intent i = new Intent(this, platillo.class);
        startActivity(i);
    }
    //-------------------------
}
