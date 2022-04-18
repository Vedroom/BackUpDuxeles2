package com.example.duxeles.pbebidas;

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

import com.example.duxeles.AdminSQLiteOpenHelper;
import com.example.duxeles.R;

import java.io.ByteArrayOutputStream;

public class mod_bebida extends AppCompatActivity {
    private EditText NomBebida, DesBebida, CantBebida, PreBebida;
    ImageView imagen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod_bebida);
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(mod_bebida.this);
        SQLiteDatabase Base = admin.getWritableDatabase();
        //OBTENER ID PARA BUSQUEDA FORMATO ARRAY STRING
        String[] nid = {String.valueOf(getIntent().getIntExtra("id_modificar", 0))};

        NomBebida = (EditText) findViewById(R.id.txtNomIng);
        DesBebida = (EditText) findViewById(R.id.txtDesBebida);
        CantBebida = (EditText) findViewById(R.id.txtCantIng);
        PreBebida = (EditText) findViewById(R.id.txtPreIng);
        imagen = (ImageView) findViewById(R.id.imgBebida);
        try {
            String[] campos = {"nombreB", "cantidadB", "precioB", "descripcionB", "img"};
            Cursor cursor = Base.query("bebidas", campos, "id_bebida=?", nid, null, null, null);
            cursor.moveToFirst();
            NomBebida.setText(cursor.getString(0));
            CantBebida.setText(cursor.getString(1));
            PreBebida.setText(cursor.getString(2));
            DesBebida.setText(cursor.getString(3));
            byte[] blob = cursor.getBlob(4);
            imagen.setImageBitmap(BitmapFactory.decodeByteArray(blob, 0, blob.length));
            cursor.close();
            Base.close();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "La bebida no existe", Toast.LENGTH_LONG).show();
        }
    }
//METODO CARGAR IMG
    public void cargarimg(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Seleccione la Aplicacion"), 10);
        //usoboton = true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            imagen.setImageResource(0);
            Uri path = data.getData();
            imagen.setImageURI(path);
        }
    }
//-----------------------------
//METODO ACTUALIZAR BEBIDAS
    public void actualizar(View view) {

        String nombreB = NomBebida.getText().toString();
        String descripcionB = DesBebida.getText().toString();
        String cantidadB = CantBebida.getText().toString();
        String precioB = PreBebida.getText().toString();
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(mod_bebida.this);
        SQLiteDatabase Base = admin.getWritableDatabase();
        //VALIDACION DE CAMPOS COMPLETOS
        if (!nombreB.isEmpty() && !descripcionB.isEmpty() && !cantidadB.isEmpty() && !precioB.isEmpty() && imagen.getDrawable()!=null) {

        //GUARDAR IMG
        Bitmap bitmap = ((BitmapDrawable) this.imagen.getDrawable()).getBitmap();
        Bitmap imagenScaled = Bitmap.createScaledBitmap(bitmap, 960, 960, false);
        ByteArrayOutputStream baos = new ByteArrayOutputStream(20480);
        imagenScaled.compress(Bitmap.CompressFormat.JPEG, 90, baos);
        byte[] img = baos.toByteArray();
        //--------------------------
            try{
                //ACTUALIZAR SQL
                    //OBTENER ID FORMATO INT
                int id_mod = getIntent().getIntExtra("id_modificar", 0);
                ContentValues cimg = new ContentValues();
                cimg.put("nombreB",nombreB);
                cimg.put("cantidadB",cantidadB);
                cimg.put("precioB",precioB);
                cimg.put("descripcionB",descripcionB);
                cimg.put("img",img);
                Base.update("bebidas",cimg,"id_bebida="+id_mod,null);
                Toast.makeText(mod_bebida.this, "Acualizacion exitosa", Toast.LENGTH_LONG).show();
            }catch (Exception e){
                String error = String.valueOf(e);
                Toast.makeText(mod_bebida.this, error, Toast.LENGTH_LONG).show();
            }

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

//METODO REGRESAR PANTALLA PRINCIPAL
    public void home(View view) {

    }
//---------------------------------------------------------------------------------------

//METODO REGRESAR PANTALLA ANTERIOR
    public void cancelar(View view) {
        NomBebida.setText("");
        DesBebida.setText("");
        CantBebida.setText("");
        PreBebida.setText("");
        imagen.setImageResource(0);
    }
//------------------------------------------------------------------------------------------

//METODO REGRESAR PANTALLA ANTERIOR
    public void regresar(View view) {
        finish();
    }
//---------------------------------------
}
