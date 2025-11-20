package com.example.proyectomoviles.modelos;

import java.util.List;

public class Plaga {

    private String _id, nombreP, nombreCien, definicion, img;
    private List<RemedioPlaga> remedio;



    public Plaga() {
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNombreP() {
        return nombreP;
    }

    public void setNombreP(String nombreP) {
        this.nombreP = nombreP;
    }

    public String getNombreCien() {
        return nombreCien;
    }

    public void setNombreCien(String nombreCien) {
        this.nombreCien = nombreCien;
    }

    public String getDefinicion() {
        return definicion;
    }

    public void setDefinicion(String definicion) {
        this.definicion = definicion;
    }

    public List<RemedioPlaga> getRemedio() {
        return remedio;
    }

    public void setRemedio(List<RemedioPlaga> remedio) {
        this.remedio = remedio;
    }
}
