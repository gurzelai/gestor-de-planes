package com.example.gestordeproyectos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final int CODE_AÑADIR = 1;
    final int CODE_EDITAR = 2;
    final static String EXTRA_PROYECTO = "proyecto tomado mediante la posicion de lista";
    static int proyectoEditando;

    FloatingActionButton btnproyecto;
    ListView listView;
    AdaptadorProyecto adaptador;
    TextView fechaActual;

    static List<Proyecto> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configuracionListas();
        btnproyecto = (FloatingActionButton) findViewById(R.id.addproyect);
        btnproyecto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Anadir.class);
                intent.putExtra("carpeta", false);
                startActivityForResult(intent, CODE_AÑADIR);
            }
        });

        fechaActual = (TextView) findViewById(R.id.fechaActual);
        //Actual.setText( new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        fechaActual.setText( new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

    }

    private void configuracionListas() {
        lista = new ArrayList<>();
        cargar();
        listView = (ListView) findViewById(R.id.listViewer);
        adaptador = new AdaptadorProyecto(this, R.layout.activity_adaptador_proyecto, lista);
        listView.setAdapter(adaptador);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, Anadir.class);
                intent.putExtra(EXTRA_PROYECTO, lista.get(position));
                startActivityForResult(intent, CODE_EDITAR);
                proyectoEditando = position;
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                lista.remove(position);
                adaptador.notifyDataSetChanged();
                guardar();
                return true;
            }
        });
    }

    private void cargar() {
        FileInputStream fi = null;
        try {
            fi = openFileInput("proyectos.txt");
            ObjectInputStream oi = new ObjectInputStream(fi);
            // Leer objetos
            lista  = (List<Proyecto>) oi.readObject();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }


    public void add(Proyecto p) {
        lista.add(p);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (CODE_AÑADIR): {
                if (resultCode == Activity.RESULT_OK) {
                    Proyecto nuevoProyecto = (Proyecto) data.getSerializableExtra(Anadir.EXTRA_ANIADIR);
                    lista.add(nuevoProyecto);
                    adaptador.notifyDataSetChanged(); //esto habría que hacerlo en el hilo de ui
                    guardar();
                    break;
                }
            }

            case (CODE_EDITAR):{
                if (resultCode == Activity.RESULT_OK) {
                    adaptador.notifyDataSetChanged(); //esto habría que hacerlo en el hilo de ui
                    guardar();
                    break;
                }
            }
        }
    }

    private void guardar() {
        try {
            FileOutputStream f = null;
            f = openFileOutput("proyectos.txt", Context.MODE_PRIVATE); //MODE PRIVATE
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(lista);
            f.close();
            o.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        if (item.getItemId() == R.id.agradecimientos) {
            Toast.makeText(this, "Gracias a Lucas por darme esta magnífica idea", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (item.getItemId() == R.id.info) {
            Toast.makeText(this, "Para eliminar un plan mantenlo pulsado.\nPara editar pulse en el plan.\nLos de color gris se han completado", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void actualizarProyecto(String nombre, String descripcion, String fecha, boolean completo) { //estaría bien hacer global y no estatico
        Proyecto p = lista.get(proyectoEditando);
        p.actualizar(descripcion, nombre, fecha, completo);
    }
}