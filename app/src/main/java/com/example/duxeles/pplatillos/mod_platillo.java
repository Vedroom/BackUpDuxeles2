package com.example.duxeles.pplatillos;

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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.duxeles.AdminSQLiteOpenHelper;
import com.example.duxeles.R;
import com.example.duxeles.pbebidas.mod_bebida;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class mod_platillo extends AppCompatActivity {
    private EditText nomPlat, desPlat, prePlat;
    private ImageView imgPlat;
    private ListView ingPlat;
    private ArrayList<String> ingredientesP = new ArrayList<>();
    /*
  BITMAP PARA GUARDAR IMG
  ASI EVITAR QUE SE PIERDA AL MOMENTO DE AGREGAR UN INGREDIENTE NUEVO
  SE PIERDA LA IMAGEN AGREGADA
  ESTO POR SOBREESCRIBIR EL METODO  onActivityResult
  PARA LA VENTANA FLOTANTE (POPUP)
    */
    Bitmap bitImgP;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(mod_platillo.this);
        SQLiteDatabase Base = admin.getWritableDatabase();
        //OBTENER ID PARA BUSQUEDA FORMATO ARRAY STRING
        String[] nid = {String.valueOf(getIntent().getIntExtra("id_modificar", 0))};

        setContentView(R.layout.activity_mod_platillo);
        nomPlat = (EditText) findViewById(R.id.txtNomPlat);
        desPlat = (EditText) findViewById(R.id.txtDesPlat);
        prePlat = (EditText) findViewById(R.id.txtPrePlat);
        imgPlat = (ImageView) findViewById(R.id.imgPlat);
        ingPlat = (ListView) findViewById(R.id.list_ingPlat);

        try {
            String[] campos = {"nombreP", "descripcionP", "precioP", "imgP", "ingreP"};
            Cursor cursor = Base.query("platillo", campos, "id_plat=?", nid, null, null, null);
            cursor.moveToFirst();
            nomPlat.setText(cursor.getString(0));
            desPlat.setText(cursor.getString(1));
            prePlat.setText(cursor.getString(2));
            byte[] blob = cursor.getBlob(3);
            imgPlat.setImageBitmap(BitmapFactory.decodeByteArray(blob, 0, blob.length));
        //TRANSFORMAR CADENA A LISTA
            Type type = new TypeToken<ArrayList<String>>() {}.getType();
            Gson gson = new Gson();
            ingredientesP = gson.fromJson(cursor.getString(4),type);
            adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1,ingredientesP);
            ingPlat.setAdapter(adapter);
        //-----------------------------
            cursor.close();
            Base.close();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "El platillo no existe", Toast.LENGTH_LONG).show();
        }
    }

    static final int AgregarValorStatic = 1;//VALOR PARA AGREGAR ING A LA LISTA
    static final int eliminarValorStatic = 2;//VALOR PARA ELIMINAR ING DE LA LISTA
//BOTONES AGREGAR / ELIMINAR INGREDIENTE DE LISTA
    public void agregarIng (View view){
        if (imgPlat.getDrawable()!=null){
            bitImgP = ((BitmapDrawable) this.imgPlat.getDrawable()).getBitmap();//GUARDAR IMG EN UN BITMAP PARA SU USO
        }
        Intent i = new Intent (mod_platillo.this,popup_agregar_ing.class);
        startActivityForResult(i, AgregarValorStatic);
    }
    public void eliminarIng(View view){
        if (imgPlat.getDrawable()!=null){
            bitImgP = ((BitmapDrawable) this.imgPlat.getDrawable()).getBitmap();//GUARDAR IMG EN UN BITMAP PARA SU USO
        }
        Intent i = new Intent (mod_platillo.this,popup_agregar_ing.class);
        startActivityForResult(i, eliminarValorStatic);
    }
//--------------------------------------------------

    public void cargarimg(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Seleccione la Aplicacion"), 10);
    }

//MODIFICACION PARA VENTANA FLOTANTE Y CARGAR IMAGEN
    Uri path;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //METODO PARA IMG
        if (resultCode == RESULT_OK) {
            path = data.getData();
            imgPlat.setImageURI(path);
        }
    //----------------
    //METODO PARA OBTENER INGREDIENTE DE VENTANA FLOTANTE
        switch (requestCode){
            //AGREGAR ING
            case(AgregarValorStatic):{

                if(resultCode== popup_agregar_ing.RESULT_OK){
                    String ning = data.getStringExtra("ing");
                    ingredientesP.add(ning);
                    adapter = new ArrayAdapter<>(this,
                            android.R.layout.simple_list_item_1,ingredientesP);
                    ingPlat.setAdapter(adapter);
                    imgPlat.setImageBitmap(bitImgP);
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
                        Toast.makeText(mod_platillo.this, "No existe el ingrediente",
                                Toast.LENGTH_LONG).show();
                    }
                }
                break;
            }
        //------------
        }
    }
//-------------------------------------------------------------------------------------

//METODO MODIFICAR PLATILLO
    public void actualizar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(mod_platillo.this);
        SQLiteDatabase Base = admin.getWritableDatabase();
        String nombreP = nomPlat.getText().toString();
        String descripcionP = desPlat.getText().toString();
        String precioP = prePlat.getText().toString();
        // TRANSFORMAR LISTA EN CADENA
        Gson gson = new Gson();
        String ingreP = gson.toJson(ingredientesP);
        //------------------
        if (!nombreP.isEmpty() && !descripcionP.isEmpty() && !precioP.isEmpty() && ingredientesP.size() != 0 && imgPlat.getDrawable() != null) {
            //GUARDAR IMG
            Bitmap imagenScaled;
                Bitmap bitmap = ((BitmapDrawable) this.imgPlat.getDrawable()).getBitmap();
                imagenScaled = Bitmap.createScaledBitmap(bitmap, 960, 960, false);
            ByteArrayOutputStream baos = new ByteArrayOutputStream(20480);
            imagenScaled.compress(Bitmap.CompressFormat.JPEG, 90, baos);
            byte[] imgP = baos.toByteArray();
            try{
                //ACTUALIZAR SQL
                //OBTENER ID FORMATO INT
                int id_mod = getIntent().getIntExtra("id_modificar", 0);
                ContentValues cplat = new ContentValues();
                cplat.put("nombreP",nombreP);
                cplat.put("descripcionP",descripcionP);
                cplat.put("precioP",precioP);
                cplat.put("imgP",imgP);
                cplat.put("ingreP",ingreP);
                Base.update("platillo",cplat,"id_plat="+id_mod,null);
                Toast.makeText(mod_platillo.this, "Acualizacion exitosa", Toast.LENGTH_LONG).show();
            }catch (Exception e){
                String error = String.valueOf(e);
                Toast.makeText(mod_platillo.this, error, Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(mod_platillo.this, "Completar todos los campos", Toast.LENGTH_LONG).show();
        }
        Base.close();
        nomPlat.setText("");
        desPlat.setText("");
        prePlat.setText("");
        ingPlat.setAdapter(null);
        imgPlat.setImageResource(0);
        finish();
    }
//---------------------------------------------------------------------------------------

//METODO REGRESAR PANTALLA ANTERIOR
    public void home(View view) {

    }
//---------------------------------------------------------------------------------------

//METODO REGRESAR PANTALLA ANTERIOR
    public void cancelar(View view) {
        nomPlat.setText("");
        desPlat.setText("");
        prePlat.setText("");
        ingPlat.setAdapter(null);
        imgPlat.setImageResource(0);
    }
//------------------------------------------------------------------------------------------

//METODO REGRESAR PANTALLA ANTERIOR
    public void regresar(View view) {
        finish();
    }
//---------------------------------------
}
