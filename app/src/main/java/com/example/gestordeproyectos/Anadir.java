package com.example.gestordeproyectos;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class Anadir extends AppCompatActivity {

    final static String EXTRA_ANIADIR = "nuevo proyecto";
    Button aceptar, cancelar;
    EditText nombre, descripcion, fechaDeInicio;
    CheckBox completo;
    boolean esEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir);
        aceptar = (Button) findViewById(R.id.aceptar);
        cancelar = (Button) findViewById(R.id.cancelar);
        nombre = (EditText) findViewById(R.id.nombreVista);
        descripcion = (EditText) findViewById(R.id.descripcion);
        fechaDeInicio = (EditText) findViewById(R.id.fechaDeInicio);
        completo = (CheckBox) findViewById(R.id.completo);
        Proyecto p = (Proyecto) this.getIntent().getSerializableExtra(MainActivity.EXTRA_PROYECTO);
        if (p != null) {
            esEditar = true;
            nombre.setText(p.getNombre());
            descripcion.setText(p.getDescripcion());
            fechaDeInicio.setText(p.getFechaDeInicio());
            completo.setChecked(p.getCompletado());
        } else {
            completo.setVisibility(View.INVISIBLE);
        }
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent;
                if (esEditar) {
                    MainActivity.actualizarProyecto(nombre.getText().toString(), descripcion.getText().toString(), fechaDeInicio.getText().toString(), completo.isChecked());
                    resultIntent = new Intent(String.valueOf(getApplicationContext()));
                } else {
                    Proyecto p = new Proyecto(nombre.getText().toString(), descripcion.getText().toString(), fechaDeInicio.getText().toString());
                    resultIntent = new Intent(String.valueOf(getApplicationContext()));
                    resultIntent.putExtra(EXTRA_ANIADIR, p);
                }
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