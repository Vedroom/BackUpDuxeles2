package com.example.duxeles.pplatillos;

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
import com.example.duxeles.pingredientes.ag_ing;
import com.example.duxeles.pingredientes.ingrediente;
import com.example.duxeles.pingredientes.mod_ing;

public class platillo extends AppCompatActivity {
    AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(platillo.this);
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
                    Base.delete("platillo","id_plat=?",Sid);
                    Toast.makeText(getApplicationContext(), "Articulo Eliminado", Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Error al eliminar", Toast.LENGTH_LONG).show();
                }

                //Toast.makeText(getApplicationContext(), "Usted acepto", Toast.LENGTH_LONG).show();
                }
            })
            .show();
    }
}
