package com.example.gestordeproyectos;


import java.io.Serializable;

public class Proyecto implements Serializable {

    String nombre, descripcion, fechaDeInicio;
    boolean carpeta;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaDeInicio() {
        return fechaDeInicio;
    }

    public void setFechaDeInicio(String fechaDeInicio) {
        this.fechaDeInicio = fechaDeInicio;
    }

    public Proyecto(String nombre, String descripcion, String fechaDeInicio, boolean carpeta) {
        this.fechaDeInicio = null;
        this.nombre = null;
        this.descripcion = null;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaDeInicio = fechaDeInicio;
        this.carpeta = carpeta;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public void actualizar(String descripcion, String nombre, String fecha) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaDeInicio = fecha;
    }

    public boolean getCarpeta() {
        return carpeta;
    }
}
