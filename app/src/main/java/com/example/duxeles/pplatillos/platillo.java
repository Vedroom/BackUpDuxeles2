package com.example.duxeles.pplatillos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.duxeles.R;
import com.example.duxeles.pingredientes.ag_ing;
import com.example.duxeles.pingredientes.ingrediente;
import com.example.duxeles.pingredientes.mod_ing;

public class platillo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platillo);
    }

    public void Modificar (View view){
        Intent i = new Intent(platillo.this,mod_platillo.class);
        //ENCARGADO DE ESTA PANTALLA
        // PASAR COMO VALUE, EL ID CORRESPONDIENTE A LA BEBIDA SELECCIONADA
        //NOTA: VALOR ACTUAL USADO PARA PRUEBA, MODIFICARLO
        int id = 1;
        i.putExtra("id_modificar", id);
        startActivity(i);
    }
    public void Agregar(View view){
        Intent a = new Intent(platillo.this, ag_platillo.class);
        startActivity(a);
    }
}
