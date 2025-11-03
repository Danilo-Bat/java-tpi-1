package com.informatorio.laboratorio.util;

/*
 * CLASE VALIDADOR
 * 
 * Índice:
 * 1. Constantes de validación
 * 2. Validación de textos
 * 3. Validación de números
 * 
 * Centralizo las validaciones para aplicar DRY (Don't Repeat Yourself).
 */

public class Validador {

    /* 1. Constantes de validación */
    // Uso constantes para evitar números mágicos (KISS)
    private static final int EDAD_MINIMA = 1;
    private static final int EDAD_MAXIMA = 120;
    private static final int DURACION_MINIMA = 1;

    /* 2. Validación de textos */
    // Valido que un texto no sea null ni esté vacío
    public static boolean esTextoValido(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    /* 3. Validación de números */
    // Valido que la edad esté en un rango razonable
    public static boolean esEdadValida(int edad) {
        return edad >= EDAD_MINIMA && edad <= EDAD_MAXIMA;
    }

    // Valido que la duración sea positiva
    public static boolean esDuracionValida(int duracion) {
        return duracion >= DURACION_MINIMA;
    }
}