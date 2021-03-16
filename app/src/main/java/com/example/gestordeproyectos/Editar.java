package com.example.gestordeproyectos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class Editar extends AppCompatActivity {

    final static String EXTRA_PROYECTO = "extra_proyecto";
    Button aceptar, cancelar;
    EditText nombre, descripcion, fechaDeInicio;
    Intent resultIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        Proyecto p = (Proyecto) Editar.this.getIntent().getSerializableExtra(MainActivity.EXTRA_PROYECTO);
        aceptar = (Button) findViewById(R.id.aceptar);
        cancelar = (Button) findViewById(R.id.cancelar);
        nombre = (EditText) findViewById(R.id.nombreVista);
        descripcion = (EditText) findViewById(R.id.descripcion);
        fechaDeInicio = (EditText) findViewById(R.id.fechaDeInicio);
        if(p.getNombre()!=null) nombre.setText(p.getNombre());
        if(p.getDescripcion()!= null) descripcion.setText(p.getDescripcion());
        if(p.getFechaDeInicio()!= null) fechaDeInicio.setText(p.getFechaDeInicio());
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.actualizarProyecto(nombre.getText().toString(), descripcion.getText().toString(), fechaDeInicio.getText().toString());
                p.actualizar(nombre.getText().toString(), descripcion.getText().toString(), fechaDeInicio.getText().toString());
                resultIntent = new Intent(String.valueOf(getApplicationContext()));
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        fechaDeInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirDialogoFecha();
            }
        });
    }

    private void abrirDialogoFecha() {
        DialogoCalendario newFragment = DialogoCalendario.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                fechaDeInicio.setText(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}