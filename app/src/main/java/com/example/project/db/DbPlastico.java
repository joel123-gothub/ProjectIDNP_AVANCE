package com.example.project.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class DbPlastico extends DbHelper{

    Context  context;

    public DbPlastico(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long registrarPlastico(String nombre, String fecha, String origen, String ubicacion, String descripcion, String categoriaPL) {

        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            //funcion para insertar valores
            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("fecha", fecha);
            values.put("origen", origen);
            values.put("ubicacion", ubicacion);
            values.put("descripcion", descripcion);
            values.put("categoriaPL", categoriaPL);

            // metodo insert devuelve el id del object insertado a la tabla
            id = db.insert(TABLE_PLASTIC, null, values);
        } catch (Exception e) {
            e.toString();
        }
        return id;
    }
}
