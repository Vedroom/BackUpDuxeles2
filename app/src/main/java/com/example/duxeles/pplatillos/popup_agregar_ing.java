package com.example.duxeles.pplatillos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duxeles.R;

public class popup_agregar_ing extends AppCompatActivity {

    private EditText newing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_agregar_ing);
        newing = (EditText) findViewById(R.id.txtnewing);
        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);
        int ancho = medidasVentana.widthPixels;
        int alto = medidasVentana.heightPixels;
        getWindow().setLayout((int)(ancho*0.6),(int)(alto*0.25));
    }

    public void agregarnewing (View view){
        if(!newing.getText().toString().isEmpty()){
            Intent i = new Intent();
            i.putExtra("ing",newing.getText().toString());
            setResult(ag_platillo.RESULT_OK,i);
            finish();
        }else{
            Toast.makeText(popup_agregar_ing.this, "Ingrese Nombre",
                    Toast.LENGTH_LONG).show();
        }
    }
}
