package com.example.proyectomoviles.modelos;

import java.io.Serializable;
import java.util.List;

public class Planta implements Serializable {
    private String _id, nombre, nombreC, descripcion, tipo, clima,
            temperaturaMin, temperaturaMax, numRiegoXSemana, suelo, urlImagen;
    private List<Etapa> etapas;
    private int faseInicial;
    private int faseReproductiva;
    private int faseVegetativa;
    private List<Integer> mesesSiembra;


    // Getters y setters para todos los campos

    public String getId() { return _id; }
    public void setId(String id) { this._id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getNombreC() { return nombreC; }
    public void setNombreC(String nombreC) { this.nombreC = nombreC; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getClima() { return clima; }
    public void setClima(String clima) { this.clima = clima; }

    public String getTemperaturaMin() { return temperaturaMin; }
    public void setTemperaturaMin(String temperaturaMin) { this.temperaturaMin = temperaturaMin; }

    public String getTemperaturaMax() { return temperaturaMax; }
    public void setTemperaturaMax(String temperaturaMax) { this.temperaturaMax = temperaturaMax; }

    public String getNumRiegoXSemana() { return numRiegoXSemana; }
    public void setNumRiegoXSemana(String numRiegoXSemana) { this.numRiegoXSemana = numRiegoXSemana; }

    public String getSuelo() { return suelo; }
    public void setSuelo(String suelo) { this.suelo = suelo; }

    public List<Etapa> getEtapas() { return etapas; }
    public void setEtapas(List<Etapa> etapas) { this.etapas = etapas; }

    public int getFaseInicial() { return faseInicial; }
    public void setFaseInicial(int faseInicial) { this.faseInicial = faseInicial; }

    public int getFaseReproductiva() { return faseReproductiva; }
    public void setFaseReproductiva(int faseReproductiva) { this.faseReproductiva = faseReproductiva; }

    public int getFaseVegetativa() { return faseVegetativa; }
    public void setFaseVegetativa(int faseVegetativa) { this.faseVegetativa = faseVegetativa; }

    public List<Integer> getMesesSiembra() { return mesesSiembra; }
    public void setMesesSiembra(List<Integer> mesesSiembra) { this.mesesSiembra = mesesSiembra; }

    public String getUrlImagen() { return urlImagen; }
    public void setUrlImagen(String urlImagen) { this.urlImagen = urlImagen; }




    // Clase anidada para Etapa
    public static class Etapa implements Serializable {
        private String nombre;
        private String descripcion;
        private String cuidados;
        private String diasDuracion;

        // Getters y setters
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

        public String getCuidados() { return cuidados; }
        public void setCuidados(String cuidados) { this.cuidados = cuidados; }

        public String getDiasDuracion() { return diasDuracion; }
        public void setDiasDuracion(String diasDuracion) { this.diasDuracion = diasDuracion; }
    }
}
