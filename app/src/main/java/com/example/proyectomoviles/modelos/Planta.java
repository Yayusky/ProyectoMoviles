package com.example.proyectomoviles.modelos;

import java.util.List;

public class Planta {
    private String _id;
    private String nombre;
    private String nombreC;
    private String descripcion;
    private String tipo;
    private String clima;
    private String temperaturaMin;
    private String temperaturaMax;
    private String riego;
    private int diasGerminacion;
    private int diasCosecha;
    private String suelo;
    private List<EtapaPlanta> etapas;

    public Planta() {}

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreC() {
        return nombreC;
    }

    public void setNombreC(String nombreC) {
        this.nombreC = nombreC;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getClima() {
        return clima;
    }

    public void setClima(String clima) {
        this.clima = clima;
    }

    public String getTemperaturaMin() {
        return temperaturaMin;
    }

    public void setTemperaturaMin(String temperaturaMin) {
        this.temperaturaMin = temperaturaMin;
    }

    public String getTemperaturaMax() {
        return temperaturaMax;
    }

    public void setTemperaturaMax(String temperaturaMax) {
        this.temperaturaMax = temperaturaMax;
    }

    public String getRiego() {
        return riego;
    }

    public void setRiego(String riego) {
        this.riego = riego;
    }

    public int getDiasGerminacion() {
        return diasGerminacion;
    }

    public void setDiasGerminacion(int diasGerminacion) {
        this.diasGerminacion = diasGerminacion;
    }

    public int getDiasCosecha() {
        return diasCosecha;
    }

    public void setDiasCosecha(int diasCosecha) {
        this.diasCosecha = diasCosecha;
    }

    public String getSuelo() {
        return suelo;
    }

    public void setSuelo(String suelo) {
        this.suelo = suelo;
    }

    public List<EtapaPlanta> getEtapas() {
        return etapas;
    }

    public void setEtapas(List<EtapaPlanta> etapas) {
        this.etapas = etapas;
    }


}
