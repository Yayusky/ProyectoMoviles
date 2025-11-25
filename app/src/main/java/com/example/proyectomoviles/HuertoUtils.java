package com.example.proyectomoviles;

import com.example.proyectomoviles.modelos.Planta;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class HuertoUtils {

    // Calcula la fecha del próximo riego (formato dd/MM/yyyy)
    public static String calcularProximoRiego(String fechaSiembraISO, int numRiegoXSemana) {
        int intervaloDias = (int) Math.round(7.0 / numRiegoXSemana);
        LocalDate fechaSiembra = LocalDate.parse(fechaSiembraISO.substring(0, 10));
        LocalDate hoy = LocalDate.now();
        long diasDesdeSiembra = ChronoUnit.DAYS.between(fechaSiembra, hoy);
        long diasHastaProximo = intervaloDias - (diasDesdeSiembra % intervaloDias);
        LocalDate proximaFecha = hoy.plusDays(diasHastaProximo);
        return proximaFecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    // Determina la fase actual de la planta según los días transcurridos desde la siembra
    public static Planta.Etapa calcularFaseActual(String fechaSiembraISO, List<Planta.Etapa> etapas) {
        LocalDate fechaSiembra = LocalDate.parse(fechaSiembraISO.substring(0, 10));
        LocalDate hoy = LocalDate.now();
        long diasPasados = ChronoUnit.DAYS.between(fechaSiembra, hoy);

        int[] diasFase = new int[etapas.size()];
        for (int i = 0; i < etapas.size(); i++) {
            String dur = etapas.get(i).getDiasDuracion();
            int dias = extraerDias(dur); // Ejemplo: "15 a 20 Dias" → 15
            diasFase[i] = dias;
        }

        int acumulado = 0;
        for (int i = 0; i < diasFase.length; i++) {
            acumulado += diasFase[i];
            if (diasPasados < acumulado) {
                return etapas.get(i); // fase actual
            }
        }
        return etapas.get(etapas.size() - 1); // última fase si ya pasó todo
    }

    // Helper: extrae el primer número de días de la cadena como "15 a 20 Dias"
    private static int extraerDias(String s) {
        s = s.replaceAll("[^0-9]", " ");
        String[] parts = s.trim().split("\\s+");
        return parts.length > 0 ? Integer.parseInt(parts[0]) : 0;
    }
}
