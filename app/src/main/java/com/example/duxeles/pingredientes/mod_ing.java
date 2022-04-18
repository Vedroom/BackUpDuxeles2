package com.example.duxeles.pingredientes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duxeles.AdminSQLiteOpenHelper;
import com.example.duxeles.R;
import com.example.duxeles.pbebidas.bebidas;
import com.example.duxeles.pbebidas.mod_bebida;

public class mod_ing extends AppCompatActivity {
    private EditText NomIng, CantIng, PreIng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod_ing);
        NomIng = (EditText) findViewById(R.id.txtNomIng);
        CantIng = (EditText) findViewById(R.id.txtCantIng);
        PreIng = (EditText) findViewById(R.id.txtPreIng);
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(mod_ing.this);
        SQLiteDatabase Base = admin.getWritableDatabase();
        //OBTENER ID PARA BUSQUEDA FORMATO ARRAY STRING
        String[] nid = {String.valueOf(getIntent().getIntExtra("id_modificar", 0))};
        try {
            String[] campos = {"nombreI", "cantidadI", "precioI"};
            Cursor cursor = Base.query("ingrediente", campos, "id_ing=?", nid, null, null, null);
            cursor.moveToFirst();
            NomIng.setText(cursor.getString(0));
            CantIng.setText(cursor.getString(1));
            PreIng.setText(cursor.getString(2));
            cursor.close();
            Base.close();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "El ingrediente no existe", Toast.LENGTH_LONG).show();
        }
    }
    public void actualizar (View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(mod_ing.this);
        SQLiteDatabase Base = admin.getWritableDatabase();
        String nombreI = NomIng.getText().toString();
        String cantidadI = CantIng.getText().toString();
        String precioI = PreIng.getText().toString();
        if (!nombreI.isEmpty() && !cantidadI.isEmpty() && !precioI.isEmpty()) {
            try {
                //ACTUALIZAR SQL
                //OBTENER ID FORMATO INT
                int id_mod = getIntent().getIntExtra("id_modificar", 0);
                ContentValues cimg = new ContentValues();
                cimg.put("nombreI", nombreI);
                cimg.put("cantidadI", cantidadI);
                cimg.put("precioI", precioI);
                Base.update("ingrediente", cimg, "id_ing=" + id_mod, null);
                Toast.makeText(mod_ing.this, "Acualizacion exitosa", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                String error = String.valueOf(e);
                Toast.makeText(mod_ing.this, error, Toast.LENGTH_LONG).show();
            }finally {
                Base.close();
                NomIng.setText("");
                CantIng.setText("");
                PreIng.setText("");
            }
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
