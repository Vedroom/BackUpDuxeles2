package com.example.duxeles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ag_bebida extends AppCompatActivity {

    private EditText NomBebida, DesBebida, CantBebida, PreBebida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ag_bebida);
        NomBebida = (EditText)findViewById(R.id.txtNomBebida);
        DesBebida = (EditText)findViewById(R.id.txtDesBebida);
        CantBebida = (EditText)findViewById(R.id.txtCantBebida);
        PreBebida = (EditText)findViewById(R.id.txtPreBebida);
    }
    //METODO ALTA DE BEBIDAS
   public void Register(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(ag_bebida.this);
       SQLiteDatabase Base = admin.getWritableDatabase();
       String nombreB = NomBebida.getText().toString();
       String descripcionB = DesBebida.getText().toString();
       String cantidadB = CantBebida.getText().toString();
       String precioB = PreBebida.getText().toString();
       if(!nombreB.isEmpty() && !descripcionB.isEmpty() && !cantidadB.isEmpty() && !cantidadB.isEmpty()){

           ContentValues registro = new ContentValues();
           registro.put("nombreB", nombreB);
           registro.put("cantidadB",cantidadB);
           registro.put("precioB",precioB);
           registro.put("descripcionB", descripcionB);

           Base.insert("bebidas",null, registro);
           Base.close();

           NomBebida.setText("");
           DesBebida.setText("");
           CantBebida.setText("");
           PreBebida.setText("");

           Toast.makeText(ag_bebida.this,"Registro exitoso", Toast.LENGTH_LONG).show();
       }else{
           Toast.makeText(ag_bebida.this,"Completar todos los campos", Toast.LENGTH_LONG).show();
       }
   }

}
