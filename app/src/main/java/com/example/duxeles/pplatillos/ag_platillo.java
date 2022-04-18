package com.example.duxeles.pplatillos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.duxeles.AdminSQLiteOpenHelper;
import com.example.duxeles.R;
import com.example.duxeles.pbebidas.ag_bebida;
import com.example.duxeles.pbebidas.bebidas;
import com.example.duxeles.pingredientes.ag_ing;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ag_platillo extends AppCompatActivity {
    private EditText nomPlat, desPlat, prePlat;
    private ImageView imgPlat;
    private ListView ingPlat;
    private ArrayList<String> ingredientesP = new ArrayList<>();
/*BITMAP PARA GUARDAR IMG
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
        setContentView(R.layout.activity_ag_platillo);
        nomPlat = (EditText) findViewById(R.id.txtNomPlat);
        desPlat = (EditText) findViewById(R.id.txtDesPlat);
        prePlat = (EditText) findViewById(R.id.txtPrePlat);
        imgPlat = (ImageView) findViewById(R.id.imgPlat);
        ingPlat = (ListView) findViewById(R.id.list_ingPlat);
        nomPlat.setText("");
        desPlat.setText("");
        prePlat.setText("");
        imgPlat.setImageResource(0);
    }

    static final int AgregarValorStatic = 1;//VALOR PARA AGREGAR ING A LA LISTA
    static final int eliminarValorStatic = 2;//VALOR PARA ELIMINAR ING DE LA LISTA
//BOTONES AGREGAR / ELIMINAR INGREDIENTE DE LISTA
    public void agregarIng (View view){
        if (imgPlat.getDrawable()!=null){
            bitImgP = ((BitmapDrawable) this.imgPlat.getDrawable()).getBitmap();//GUARDAR IMG EN UN BITMAP PARA SU USO
        }
        Intent i = new Intent (ag_platillo.this,popup_agregar_ing.class);
        startActivityForResult(i, AgregarValorStatic);
    }
    public void eliminarIng(View view){
        if (imgPlat.getDrawable()!=null){
            bitImgP = ((BitmapDrawable) this.imgPlat.getDrawable()).getBitmap();//GUARDAR IMG EN UN BITMAP PARA SU USO
        }
        Intent i = new Intent (ag_platillo.this,popup_agregar_ing.class);
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
                        Toast.makeText(ag_platillo.this, "No existe el ingrediente",
                                Toast.LENGTH_LONG).show();
                    }
                }
                break;
            }
        //------------
        }
    }
//-------------------------------------------------------------------------------------

//METODO AGREGAR INGREDIENTE
    public void agregar (View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(ag_platillo.this);
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
            Bitmap bitmap = ((BitmapDrawable) this.imgPlat.getDrawable()).getBitmap();
            Bitmap imagenScaled = Bitmap.createScaledBitmap(bitmap, 960, 960, false);
            ByteArrayOutputStream baos = new ByteArrayOutputStream(20480);
            imagenScaled.compress(Bitmap.CompressFormat.JPEG, 90, baos);
            byte[] imgP = baos.toByteArray();
            //--------------------------
            ContentValues registro = new ContentValues();
            registro.put("nombreP", nombreP);
            registro.put("descripcionP", descripcionP);
            registro.put("precioP", precioP);
            registro.put("imgP", imgP);
            registro.put("ingreP", ingreP);
            Base.insert("platillo", "id_plat", registro);
            Toast.makeText(ag_platillo.this, "Registro exitoso", Toast.LENGTH_LONG).show();
            Base.close();
            nomPlat.setText("");
            desPlat.setText("");
            prePlat.setText("");
            ingPlat.setAdapter(null);
            imgPlat.setImageResource(0);
        }else{
            Toast.makeText(ag_platillo.this, "Completar todos los campos", Toast.LENGTH_LONG).show();
        }
    }
//-------------------------------------------------------------------------------------

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
