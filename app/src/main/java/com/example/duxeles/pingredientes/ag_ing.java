package com.example.duxeles.pingredientes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duxeles.AdminSQLiteOpenHelper;
import com.example.duxeles.R;
import com.example.duxeles.pbebidas.ag_bebida;
import com.example.duxeles.pbebidas.bebidas;

import java.beans.IndexedPropertyChangeEvent;
import java.util.concurrent.ExecutionException;

public class ag_ing extends AppCompatActivity {
    private EditText NomIng, CantIng, PreIng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ag_ing);
        NomIng = (EditText) findViewById(R.id.txtNomIng);
        CantIng = (EditText) findViewById(R.id.txtCantIng);
        PreIng = (EditText) findViewById(R.id.txtPreIng);

        //INICIALIZAR EN BLANCO
        NomIng.setText("");
        CantIng.setText("");
        PreIng.setText("");
        //----------------------------
    }
    public void Register (View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(ag_ing.this);
        SQLiteDatabase Base = admin.getWritableDatabase();
        String nombreI = NomIng.getText().toString();
        String cantidadI = CantIng.getText().toString();
        String precioI = PreIng.getText().toString();
        if (!nombreI.isEmpty() && !cantidadI.isEmpty() && !precioI.isEmpty()) {
            ContentValues registro = new ContentValues();
            registro.put("nombreI", nombreI);
            registro.put("cantidadI", cantidadI);
            registro.put("precioI", precioI);
            try{
                Base.insert("ingrediente", "id_ing", registro);
                Toast.makeText(ag_ing.this, "Registro exitoso", Toast.LENGTH_LONG).show();
            }catch (Exception e){
                String ex = String.valueOf(e);
                Toast.makeText(ag_ing.this,ex, Toast.LENGTH_LONG).show();
            }finally {
                Base.close();
                NomIng.setText("");
                CantIng.setText("");
                PreIng.setText("");
            }
        } else {
            Toast.makeText(ag_ing.this, "Completar todos los campos", Toast.LENGTH_LONG).show();
        }
    }

//METODO REGRESAR PANTALLA PRINCIPAL
    public void home(View view) {

    }
//---------------------------------------------------------------------------------------

//METODO REGRESAR PANTALLA ANTERIOR
    public void cancelar(View view) {
        NomIng.setText("");
        CantIng.setText("");
        PreIng.setText("");
    }
//------------------------------------------------------------------------------------------

//METODO REGRESAR PANTALLA ANTERIOR
    public void regresar(View view) {
        finish();
    }
//---------------------------------------
}
