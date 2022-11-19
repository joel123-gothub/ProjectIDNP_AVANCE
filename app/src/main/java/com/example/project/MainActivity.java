package com.example.project;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    EditText txtNombrePlastic, txtFecPlastic, txtOrigenPlastic, txtUbicacionPlastic, txtDescripcionPlastic;
    Spinner categoriaPlastic;
    Button btnRegistrarPlastic, btnTomarFotoPlastic;
    ImageView imgfotoPlastic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNombrePlastic = findViewById(R.id.txtNamePlastic);
        txtFecPlastic = findViewById(R.id.txtFecPlastic);
        txtOrigenPlastic= findViewById(R.id.txtOrigenPlastic);
        txtUbicacionPlastic = findViewById(R.id.txtUbicationPlastic);
        txtDescripcionPlastic= findViewById(R.id.txtDescriptionPlastic);
        categoriaPlastic = findViewById(R.id.categoria_plastic);

        btnRegistrarPlastic = findViewById(R.id.btnRegistrar);
        btnTomarFotoPlastic = findViewById(R.id.btnCamara);

        imgfotoPlastic = findViewById(R.id.FotoPlastic);

        ArrayList<ArrayList<String>> lista = new ArrayList<ArrayList<String>>();

        btnTomarFotoPlastic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camaraLauncher.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE)); //obtener foto y mostrarlo
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


                if (!NombrePl.isEmpty() && !FecPl.isEmpty() && !OrigenPL.isEmpty() && !UbicacionPL.isEmpty() && !DescripcionPL.isEmpty()) {
                    boolean flag = lista.add(new ArrayList<String>(Arrays.asList(NombrePl, FecPl, OrigenPL, UbicacionPL, DescripcionPL)));
                    if (flag) {
                        txtNombrePlastic.setText("");
                        txtFecPlastic.setText("");
                        txtOrigenPlastic.setText("");
                        txtUbicacionPlastic.setText("");
                        txtDescripcionPlastic.setText("");

                        Toast.makeText(MainActivity.this, "Plástico agregado exitasamente!!", Toast.LENGTH_LONG).show();
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
}