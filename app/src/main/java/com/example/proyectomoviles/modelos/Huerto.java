package com.example.proyectomoviles.modelos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Huerto implements Serializable {
    private String _id;
    private String nombre;
    private String usuarioId;
    private String fechaRegistro;
    private List<PlantaSembrada> plantasSembradas = new ArrayList<>();
    private String tipoSuelo;
    private Irrigacion irrigacion;
    private String notas;
    private boolean activo = true;

    // Getters y setters
    public String getId() { return _id; }
    public void setId(String id) { this._id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }
    public String getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(String fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    public List<PlantaSembrada> getPlantasSembradas() { return plantasSembradas; }
    public void setPlantasSembradas(List<PlantaSembrada> plantasSembradas) { this.plantasSembradas = plantasSembradas; }

    public String getTipoSuelo() { return tipoSuelo; }
    public void setTipoSuelo(String tipoSuelo) { this.tipoSuelo = tipoSuelo; }
    public Irrigacion getIrrigacion() { return irrigacion; }
    public void setIrrigacion(Irrigacion irrigacion) { this.irrigacion = irrigacion; }
    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    // Clase interna para Irrigacion
    public static class Irrigacion implements Serializable {
        private String tipo;
        private int frecuenciaSemanal;

        public Irrigacion() {}
        public Irrigacion(String tipo, int frecuenciaSemanal) {
            this.tipo = tipo;
            this.frecuenciaSemanal = frecuenciaSemanal;
        }

        public String getTipo() { return tipo; }
        public void setTipo(String tipo) { this.tipo = tipo; }
        public int getFrecuenciaSemanal() { return frecuenciaSemanal; }
        public void setFrecuenciaSemanal(int frecuenciaSemanal) { this.frecuenciaSemanal = frecuenciaSemanal; }
    }

    // Clase interna para PlantaSembrada
    public static class PlantaSembrada implements Serializable {
        private String plantaId;
        private String nombrePlanta;
        private String fechaSiembra;
        private int cantidad;
        private String etapaActual;
        private String notas;

        public String getPlantaId() { return plantaId; }
        public void setPlantaId(String plantaId) { this.plantaId = plantaId; }
        public String getNombrePlanta() { return nombrePlanta; }
        public void setNombrePlanta(String nombrePlanta) { this.nombrePlanta = nombrePlanta; }
        public String getFechaSiembra() { return fechaSiembra; }
        public void setFechaSiembra(String fechaSiembra) { this.fechaSiembra = fechaSiembra; }
        public int getCantidad() { return cantidad; }
        public void setCantidad(int cantidad) { this.cantidad = cantidad; }
        public String getEtapaActual() { return etapaActual; }
        public void setEtapaActual(String etapaActual) { this.etapaActual = etapaActual; }
        public String getNotas() { return notas; }
        public void setNotas(String notas) { this.notas = notas; }
    }
}
