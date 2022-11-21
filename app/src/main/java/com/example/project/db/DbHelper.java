package com.example.project.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2; //version de Db
    private static final String DATABASE_NOMBRE = "NoPlastic.db";
    public static final String TABLE_PLASTIC = "t_plastico";

    //Constructor
    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION); //especificacion de datos
    }

    //creando la tabla PLASTIC + atributos
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_PLASTIC + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "fecha TEXT NOT NULL," +
                "origen TEXT NOT NULL," +
                "ubicacion TEXT NOT NULL," +
                "descripcion TEXT NOT NULL," +
                "categoriaPL TEXT NOT NULL)");
    }

    //onUpgrade se ejecutara cuando cambie la version de db
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_PLASTIC); // borrartodo
        onCreate(sqLiteDatabase); //regenerar db con cambios de version
    }
}
