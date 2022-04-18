package com.example.duxeles.pplatillos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.duxeles.R;
import com.example.duxeles.pbebidas.bebidas;
import com.example.duxeles.pingredientes.ag_ing;

import java.util.ArrayList;
import java.util.List;

public class ag_platillo extends AppCompatActivity {
    private EditText nomPlat, desPlat, prePlat;
    private ImageView imgPlat;
    private ListView ingPlat;
    private ArrayList<String> ingredientesP = new ArrayList<>();
    //String ning;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ag_platillo);
        nomPlat = (EditText) findViewById(R.id.txtNomPlat);
        desPlat = (EditText) findViewById(R.id.txtDesPlat);
        prePlat = (EditText) findViewById(R.id.txtPrePlat);
        imgPlat = (ImageView) findViewById(R.id.imgPlat);
        ingPlat = (ListView) findViewById(R.id.list_ingPlat);
    }

    static final int AgregarValorStatic = 1;//VALOR PARA AGREGAR ING A LA LISTA
    static final int eliminarValorStatic = 2;//VALOR PARA ELIMINAR ING DE LA LISTA
//BOTONES AGREGAR / ELIMINAR INGREDIENTE DE LISTA
    public void agregarIng (View view){
        Intent i = new Intent (ag_platillo.this,popup_agregar_ing.class);
        startActivityForResult(i, AgregarValorStatic);
    }
    public void eliminarIng(View view){
        Intent i = new Intent (ag_platillo.this,popup_agregar_ing.class);
        startActivityForResult(i, eliminarValorStatic);
    }
//--------------------------------------------------

//MODIFICACION PARA VENTANA FLOTANTE
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
        //AGREGAR ING
            case(AgregarValorStatic):{
                if(resultCode== popup_agregar_ing.RESULT_OK){
                    String ning = data.getStringExtra("ing");
                    ingredientesP.add(ning);
                    adapter = new ArrayAdapter<>(this,
                            android.R.layout.simple_list_item_1,ingredientesP);
                    ingPlat.setAdapter(adapter);
                }
                break;
            }
        //-------------

        //ELIMINAR ING
            case(eliminarValorStatic):{
                if(resultCode== popup_agregar_ing.RESULT_OK){
                    String ning = data.getStringExtra("ing");
                    boolean existe = ingredientesP.contains(ning);
                    if(existe){
                        ingredientesP.remove(ning);
                        adapter = new ArrayAdapter<>(this,
                                android.R.layout.simple_list_item_1,ingredientesP);
                        ingPlat.setAdapter(adapter);
                    }else{
                        Toast.makeText(ag_platillo.this, "No existe el ingrediente",
                                Toast.LENGTH_LONG).show();
                    }
                }
                break;
            }
        //------------
        }
    }
//-------------------------------------------------------------------------------------
//METODO REGRESAR PANTALLA ANTERIOR
    public void regresar(View view) {
        Intent i = new Intent(this, platillo.class);
        startActivity(i);
    }
//---------------------------------------
}
