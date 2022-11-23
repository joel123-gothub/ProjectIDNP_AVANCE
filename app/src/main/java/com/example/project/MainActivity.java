package com.example.project;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.project.db.DbHelper;
import com.example.project.db.DbPlastico;


public class MainActivity extends AppCompatActivity {

    EditText txtNombrePlastic, txtFecPlastic, txtOrigenPlastic, txtUbicacionPlastic, txtDescripcionPlastic;
    Spinner categoriaPlastic;
    Button btnRegistrarPlastic, btnTomarFotoPlastic;
    ImageView imgfotoPlastic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI(); //asignaciones
        crearDB(); //creación DB

        btnTomarFotoPlastic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camaraLauncher.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE)); //obtener foto y mostrarlo
                btnTomarFotoPlastic.setText("CAMBIAR IMAGEN PLÁSTICO");
            }
        });

        btnRegistrarPlastic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String NombrePl = txtNombrePlastic.getText().toString();
                String FecPl = txtFecPlastic.getText().toString();
                String OrigenPL = txtOrigenPlastic.getText().toString();
                String UbicacionPL = txtUbicacionPlastic.getText().toString();
                String DescripcionPL = txtDescripcionPlastic.getText().toString();
                String categoriaPL = categoriaPlastic.getSelectedItem().toString();

                if (!NombrePl.isEmpty() && !FecPl.isEmpty() && !OrigenPL.isEmpty() && !UbicacionPL.isEmpty() && !DescripcionPL.isEmpty()) {
                    DbPlastico dbPlastico = new DbPlastico(MainActivity.this);
                    long id = dbPlastico.registrarPlastico(NombrePl, FecPl, OrigenPL, UbicacionPL, DescripcionPL, categoriaPL);

                    if( id > 0) {
                        Toast.makeText(MainActivity.this, "Plástico registrado exitasamente!!", Toast.LENGTH_LONG).show();
                        Limpiar();
                    } else {
                        Toast.makeText(MainActivity.this, "Ups, Ocurrió un error en registro!!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Debe agregar todos los datos para el Registro!!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //visualizar foto en activity antes del registro
    ActivityResultLauncher<Intent> camaraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK) {
                Bundle extras = result.getData().getExtras();
                Bitmap imgBitmap = (Bitmap) extras.get("data");
                imgfotoPlastic.setImageBitmap(imgBitmap);
            }
        }
    });

    //Creacion db
    private void crearDB() {
        DbHelper dbHelper = new DbHelper(MainActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase(); //escribir en db
        if(db != null) {
            Toast.makeText(MainActivity.this, "BASE DE DATOS CREADA", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MainActivity.this, "ERROR AL CREAR BASE DE DATOS", Toast.LENGTH_LONG).show();
        }
    }

    //Limpiar campos registro
    private void Limpiar() {
        txtNombrePlastic.setText("");
        txtFecPlastic.setText("");
        txtOrigenPlastic.setText("");
        txtUbicacionPlastic.setText("");
        txtDescripcionPlastic.setText("");
    }

    //initUI
    private void initUI() {
        //Asignaciones
        txtNombrePlastic = findViewById(R.id.txtNamePlastic);
        txtFecPlastic = findViewById(R.id.txtFecPlastic);
        txtOrigenPlastic= findViewById(R.id.txtOrigenPlastic);
        txtUbicacionPlastic = findViewById(R.id.txtUbicationPlastic);
        txtDescripcionPlastic= findViewById(R.id.txtDescriptionPlastic);
        categoriaPlastic = findViewById(R.id.categoria_plastic);

        btnRegistrarPlastic = findViewById(R.id.btnRegistrar);
        btnTomarFotoPlastic = findViewById(R.id.btnCamara);

        imgfotoPlastic = findViewById(R.id.FotoPlastic); //por procesar
    }
}
