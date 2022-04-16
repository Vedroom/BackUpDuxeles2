package com.example.duxeles;

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

import java.io.ByteArrayOutputStream;

public class mod_bebida extends AppCompatActivity {
    //VARIABLES PARA MODIFICAR
    //String idb = getIntent().getStringExtra("id");
    String[] id = {"1"};
    private EditText NomBebida, DesBebida, CantBebida, PreBebida;
    ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod_bebida);
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(mod_bebida.this);
        SQLiteDatabase Base = admin.getWritableDatabase();


        NomBebida = (EditText) findViewById(R.id.txtNomBebida);
        DesBebida = (EditText) findViewById(R.id.txtDesBebida);
        CantBebida = (EditText) findViewById(R.id.txtCantBebida);
        PreBebida = (EditText) findViewById(R.id.txtPreBebida);
        imagen = (ImageView) findViewById(R.id.imgBebida);
        try {
            String[] campos = {"nombreB", "cantidadB", "precioB", "descripcionB", "img"};
            Cursor cursor = Base.query("bebidas", campos, "id_bebida=?", id, null, null, null);
            cursor.moveToFirst();
            NomBebida.setText(cursor.getString(0));
            CantBebida.setText(cursor.getString(1));
            PreBebida.setText(cursor.getString(2));
            DesBebida.setText(cursor.getString(3));
            byte[] blob = cursor.getBlob(4);
            imagen.setImageBitmap(BitmapFactory.decodeByteArray(blob, 0, blob.length));
            cursor.close();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "La bebida no existe", Toast.LENGTH_LONG).show();
        }
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
//METODO ACTUALIZAR BEBIDAS
    public void actualizar(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(mod_bebida.this);
        SQLiteDatabase Base = admin.getWritableDatabase();
        String nombreB = NomBebida.getText().toString();
        String descripcionB = DesBebida.getText().toString();
        String cantidadB = CantBebida.getText().toString();
        String precioB = PreBebida.getText().toString();

        //GUARDAR IMG
        Bitmap bitmap = ((BitmapDrawable) this.imagen.getDrawable()).getBitmap();
        Bitmap imagenScaled = Bitmap.createScaledBitmap(bitmap, 960, 960, false);
        ByteArrayOutputStream baos = new ByteArrayOutputStream(20480);
        //bitmap.compress(Bitmap.CompressFormat.PNG, 0 , baos);
        imagenScaled.compress(Bitmap.CompressFormat.JPEG, 90, baos);
        byte[] img = baos.toByteArray();
        //--------------------------
        if (!nombreB.isEmpty() && !descripcionB.isEmpty() && !cantidadB.isEmpty() && !precioB.isEmpty()) {
            try{
                Base.execSQL("UPDATE " + "bebidas " + "SET nombreB='" + nombreB + "', cantidadB='" + cantidadB +
                        "', precioB='" + precioB + "', descripcionB='" + descripcionB + "', img='" + img + "'WHERE id_bebida='" + id + "'");
            }catch (Exception e){
                Toast.makeText(mod_bebida.this, "Error al actualizar", Toast.LENGTH_LONG).show();
            }
            Toast.makeText(mod_bebida.this, "Acualizacion exitosa", Toast.LENGTH_LONG).show();
    } else{
        Toast.makeText(mod_bebida.this, "Completar todos los campos", Toast.LENGTH_LONG).show();
    }
        Base.close();
        NomBebida.setText("");
        DesBebida.setText("");
        CantBebida.setText("");
        PreBebida.setText("");
        imagen.setImageResource(0);
    }
    //METODO REGRESAR PANTALLA ANTERIOR
    public void regresar(View view) {
        Intent i = new Intent(this, bebidas.class);
        startActivity(i);
    }
}
