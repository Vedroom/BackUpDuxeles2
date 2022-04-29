package com.example.duxeles.pbebidas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.duxeles.AdminSQLiteOpenHelper;
import java.util.ArrayList;

public class DbBebidas extends AdminSQLiteOpenHelper {

    Context context;

    public DbBebidas(@Nullable Context context){
        super(context);
        this.context = context;
    }

    public ArrayList<bebidas> mostrarBebida(){

        AdminSQLiteOpenHelper dbHelper = new AdminSQLiteOpenHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<bebidas> listaBebida = new ArrayList<>();
        bebidas bebida = null;
        Cursor cursorBebida = null;

        cursorBebida = db.rawQuery("SELECT * FROM" + t_bebidas, null);

        if(cursorBebida.moveToFirst()){
            do{
                bebida = new bebidas();
                bebida.setNom(cursorBebida.getString(0);
                bebida.setPrecio(cursorBebida.getString(1));
                bebida.setDesc(cursorBebida.getString(2));
                bebida.setImg(cursorBebida.getInt(3));

                listaBebida.add(bebida); //va llenando lo que jale de la tabla t_bebidas

            }//do
            while (cursorBebida.moveToNext());
        }//if

        cursorBebida.close();
        return  listaBebida;
    }//mostrarBebida

}//DbBebidas