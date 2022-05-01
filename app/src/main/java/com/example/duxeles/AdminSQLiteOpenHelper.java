package com.example.duxeles;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.*;
import android.content.Context;
import androidx.annotation.Nullable;

import com.example.duxeles.pbebidas.bebidas;

import java.util.ArrayList;
import java.util.List;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final int db_version = 1;
    private static final String db_nombre = "duxeles.db";
    public static final String t_bebidas = "bebidas";
    public static final String t_ing = "ingrediente";
    public static final String t_platillo = "platillo";
    Context context;


    public AdminSQLiteOpenHelper(@Nullable Context context) {
        super(context, db_nombre, null, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase BD) {
        BD.execSQL("CREATE TABLE "+t_bebidas+"("+
                "id_bebida INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombreB TEXT not null," +
                "cantidadB INT not null," +
                "precioB DOUBLE not null," +
                "descripcionB TEXT not null,"+
                "img BLOB not null)");

        BD.execSQL("CREATE TABLE "+t_ing+"("+
                "id_ing INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombreI TEXT not null," +
                "cantidadI INT not null," +
                "precioI DOUBLE not null)");

        BD.execSQL("CREATE TABLE "+t_platillo+"("+
                "id_plat INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombreP TEXT not null," +
                "descripcionP TEXT not null," +
                "precioP DOUBLE not null," +
                "imgP BLOB not null," +
                "ingreP TEXT not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase BD, int i, int i1) {
        BD.execSQL("DROP TABLE "+t_bebidas);
        BD.execSQL("DROP TABLE "+t_ing);
        BD.execSQL("DROP TABLE "+t_platillo);
        onCreate(BD);

    }


    public ArrayList<bebidas> mostrarBebida(){

        AdminSQLiteOpenHelper dbHelper = new AdminSQLiteOpenHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<bebidas> listaBebida = new ArrayList<>();
        bebidas bebida = null;
        Cursor cursorBebida = null;

        cursorBebida = db.rawQuery("SELECT * FROM " + t_bebidas, null);

        if(cursorBebida.moveToFirst()){
            do{
                bebida = new bebidas();
                bebida.setNom(cursorBebida.getString(0));
                bebida.setPrecio(cursorBebida.getString(1));
                bebida.setDesc(cursorBebida.getString(2));
                bebida.setImg(cursorBebida.getInt(3));

                listaBebida.add(bebida); //va llenando una lista con lo que jale de la tabla t_bebidas

            }//do
            while (cursorBebida.moveToNext());
        }//if

        cursorBebida.close();
        return  listaBebida;
    }//mostrarBebida

}
