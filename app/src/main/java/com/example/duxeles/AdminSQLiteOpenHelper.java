package com.example.duxeles;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final int db_version = 1;
    private static final String db_nombre = "duxeles.db";
    public static final String t_bebidas = "bebidas";
    public static final String t_ing = "ingrediente";


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
    }

    @Override
    public void onUpgrade(SQLiteDatabase BD, int i, int i1) {
        BD.execSQL("DROP TABLE "+t_bebidas);
        BD.execSQL("DROP TABLE "+t_ing);

        onCreate(BD);

    }
}
