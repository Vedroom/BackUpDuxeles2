package com.example.duxeles;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class ag_bebida extends AppCompatActivity {

    private EditText NomBebida, DesBebida, CantBebida, PreBebida;
    ImageView imagen;

    //------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ag_bebida);
        NomBebida = (EditText) findViewById(R.id.txtNomBebida);
        DesBebida = (EditText) findViewById(R.id.txtDesBebida);
        CantBebida = (EditText) findViewById(R.id.txtCantBebida);
        PreBebida = (EditText) findViewById(R.id.txtPreBebida);
        imagen = (ImageView) findViewById(R.id.imgBebida);

        //INICIALIZAR EN BLANCO
        NomBebida.setText("");
        DesBebida.setText("");
        CantBebida.setText("");
        PreBebida.setText("");
        imagen.setImageResource(0);
        //----------------------------
    }

    //METODO CARGAR IMG
    public void cargarimg(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Seleccione la Aplicacion"), 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri path = data.getData();
            imagen.setImageURI(path);
        }
    }
    //-----------------------------
//METODO ALTA DE BEBIDAS
    public void Register(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(ag_bebida.this);
        SQLiteDatabase Base = admin.getWritableDatabase();
        String nombreB = NomBebida.getText().toString();
        String descripcionB = DesBebida.getText().toString();
        String cantidadB = CantBebida.getText().toString();
        String precioB = PreBebida.getText().toString();
        if (!nombreB.isEmpty() && !descripcionB.isEmpty() && !cantidadB.isEmpty() && !precioB.isEmpty()&& imagen.getDrawable()!=null) {
        //GUARDAR IMG
            Bitmap bitmap = ((BitmapDrawable) this.imagen.getDrawable()).getBitmap();
            Bitmap imagenScaled = Bitmap.createScaledBitmap(bitmap, 960, 960, false);
            ByteArrayOutputStream baos = new ByteArrayOutputStream(20480);
            //bitmap.compress(Bitmap.CompressFormat.PNG, 0 , baos);
            imagenScaled.compress(Bitmap.CompressFormat.JPEG, 90, baos);
            byte[] img = baos.toByteArray();
        //--------------------------

            ContentValues registro = new ContentValues();
            registro.put("nombreB", nombreB);
            registro.put("cantidadB", cantidadB);
            registro.put("precioB", precioB);
            registro.put("descripcionB", descripcionB);
            registro.put("img", img);
            Base.insert("bebidas", "id_bebida", registro);
            Toast.makeText(ag_bebida.this, "Registro exitoso", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(ag_bebida.this, "Completar todos los campos", Toast.LENGTH_LONG).show();
        }
        Base.close();
        NomBebida.setText("");
        DesBebida.setText("");
        CantBebida.setText("");
        PreBebida.setText("");
        imagen.setImageResource(0);
    }
//----------------------------------------
//METODO REGRESAR PANTALLA ANTERIOR
    public void regresar(View view) {
        Intent i = new Intent(this, bebidas.class);
        startActivity(i);
    }
}
