package com.example.duxeles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //BOTON PROVICIONAL
    public void Ag_Bebida (View view){
        Intent i = new Intent(this, ag_bebida.class);
        startActivity(i);
    }
    //-------------------------
}
