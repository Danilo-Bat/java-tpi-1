package com.informatorio.laboratorio.util;

import java.util.Scanner;

/**
 * Clase utilitaria para lectura segura de datos desde Scanner.
 */
public class UtilScanner {

    /**
     * Lee un entero de forma segura. Retorna -1 si hay error.
     */
    public static int leerEntero(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Lee un texto y elimina espacios al inicio/final.
     */
    public static String leerTexto(Scanner scanner) {
        return scanner.nextLine().trim();
    }
}