package com.example.duxeles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.duxeles.pbebidas.RecyclerViewAdaptador;
import com.example.duxeles.pbebidas.bebidas;
import com.example.duxeles.pingredientes.ingrediente;
import com.example.duxeles.pplatillos.platillo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //BOTON PROVICIONAL
    public void Bebida (View view){
        Intent i = new Intent(this, bebidas.class);
        startActivity(i);
    }
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
