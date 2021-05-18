package com.example.gestordeproyectos;


import java.io.Serializable;

public class Proyecto implements Serializable {

    String nombre, descripcion, fechaDeInicio;
    boolean completado;

    public String getNombre() {
        return nombre;
    }


    public String getDescripcion() {
        return descripcion;
    }


    public String getFechaDeInicio() {
        return fechaDeInicio;
    }


    public Proyecto(String nombre, String descripcion, String fechaDeInicio) {
        this.fechaDeInicio = null;
        this.nombre = null;
        this.descripcion = null;
        completado = false;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaDeInicio = fechaDeInicio;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public void actualizar(String descripcion, String nombre, String fecha, boolean completo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaDeInicio = fecha;
        this.completado = completo;
    }

    public boolean getCompletado() {
        return completado;
    }
}
