package com.example.proyectomoviles.modelos;

import java.io.Serializable;
import java.util.List;

public class TipoCultivoItem implements Serializable {
    private String tipo;
    private int cantidad;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public static class RespuestaTiposCultivo{
        private String usuario_id;
        private int total_plantas;
        private List<TipoCultivoItem> tipos;

        public String getUsuario_id() {
            return usuario_id;
        }

        public void setUsuario_id(String usuario_id) {
            this.usuario_id = usuario_id;
        }

        public int getTotal_plantas() {
            return total_plantas;
        }

        public void setTotal_plantas(int total_plantas) {
            this.total_plantas = total_plantas;
        }

        public List<TipoCultivoItem> getTipos() {
            return tipos;
        }

        public void setTipos(List<TipoCultivoItem> tipos) {
            this.tipos = tipos;
        }
    }

}

