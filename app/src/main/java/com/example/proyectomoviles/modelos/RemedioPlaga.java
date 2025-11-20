package com.example.proyectomoviles.modelos;

import java.io.Serializable;

public class RemedioPlaga implements Serializable {
    private String nombre, descripcion, ingredientes, preparativo, aplicacion, imgRemedio;

    public RemedioPlaga() {
    }

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

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getPreparativo() {
        return preparativo;
    }

    public void setPreparativo(String preparativo) {
        this.preparativo = preparativo;
    }

    public String getAplicacion() {
        return aplicacion;
    }

    public void setAplicacion(String aplicacion) {
        this.aplicacion = aplicacion;
    }

    public String getImgRemedio() {
        return imgRemedio;
    }

    public void setImgRemedio(String imgRemedio) {
        this.imgRemedio = imgRemedio;
    }
}
