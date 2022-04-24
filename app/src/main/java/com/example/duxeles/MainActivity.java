package com.example.duxeles;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;

import com.example.duxeles.pbebidas.RecyclerViewAdaptador;
import com.example.duxeles.pbebidas.bebidas;
import com.example.duxeles.pingredientes.ingrediente;
import com.example.duxeles.pplatillos.platillo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btnMostrarBebidas;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMostrarBebidas=(Button)findViewById(R.id.Bebida);

        btnMostrarBebidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mostrarBebidas = new Intent(getApplicationContext(), bebidasActivity.class);
                startActivity(mostrarBebidas);
            }
        });
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
