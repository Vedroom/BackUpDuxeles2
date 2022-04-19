package com.example.duxeles.pbebidas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.duxeles.AdminSQLiteOpenHelper;
import com.example.duxeles.R;
import com.example.duxeles.pingredientes.ingrediente;

public class bebidas extends AppCompatActivity {
    AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(bebidas.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bebidas);

    }
    public void Modificar (View view){
        Intent i = new Intent(bebidas.this, mod_bebida.class);
        //ENCARGADO DE ESTA PANTALLA
        // PASAR COMO VALUE, EL ID CORRESPONDIENTE A LA BEBIDA SELECCIONADA
        //NOTA: VALOR ACTUAL USADO PARA PRUEBA, MODIFICARLO
        int id = 1;
        i.putExtra("id_modificar", id);
        startActivity(i);
    }

    public void Agregar(View view){
        Intent a = new Intent(bebidas.this, ag_bebida.class);
        startActivity(a);
    }

    public void Eliminar (View view){
        //ENCARGADO DE ESTA PANTALLA
        // PASAR COMO VALUE, EL ID CORRESPONDIENTE A LA BEBIDA SELECCIONADA
        //NOTA: VALOR ACTUAL USADO PARA PRUEBA, MODIFICARLO
        int id = 1;
        final String [] Sid = {String.valueOf(id)};
        new AlertDialog.Builder(this)
                .setTitle("Eliminacion")
                .setMessage("¿Desea eliminar este articulo?")
                .setNegativeButton(R.string.cancelarEliminacion, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setPositiveButton(R.string.aceptarEliminacion, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try{
                            SQLiteDatabase Base = admin.getWritableDatabase();
                            Base.delete("bebidas","id_bebida=?",Sid);
                            Toast.makeText(getApplicationContext(), "Articulo Eliminado", Toast.LENGTH_LONG).show();
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(), "Error al eliminar", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .show();
    }
}
