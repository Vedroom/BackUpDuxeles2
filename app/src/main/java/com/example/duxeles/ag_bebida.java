package com.example.duxeles;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ag_bebida extends AppCompatActivity {

    private EditText NomBebida, DesBebida, CantBebida, PreBebida;
    ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ag_bebida);
        NomBebida = (EditText)findViewById(R.id.txtNomBebida);
        DesBebida = (EditText)findViewById(R.id.txtDesBebida);
        CantBebida = (EditText)findViewById(R.id.txtCantBebida);
        PreBebida = (EditText)findViewById(R.id.txtPreBebida);
    }

    //METODO CARGAR IMG
    public void cargarimg(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("Imagen/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicacion"),10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Uri path = data.getData();
            imagen.setImageURI(path);
        }
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
