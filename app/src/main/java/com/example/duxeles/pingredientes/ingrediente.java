package com.example.duxeles.pingredientes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.duxeles.R;

public class ingrediente extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingrediente);
    }
    public void Modificar (View view){
        Intent i = new Intent(ingrediente.this, mod_ing.class);
        //ENCARGADO DE ESTA PANTALLA
        // PASAR COMO VALUE, EL ID CORRESPONDIENTE A LA BEBIDA SELECCIONADA
        //NOTA: VALOR ACTUAL USADO PARA PRUEBA, MODIFICARLO
        int id = 1;
        i.putExtra("id_modificar", id);
        startActivity(i);
    }
    public void Agregar(View view){
        Intent a = new Intent(ingrediente.this, ag_ing.class);
        startActivity(a);
    }
}
