package com.informatorio.laboratorio.util;

import com.informatorio.laboratorio.model.Investigador;
import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/*
 * UTILIDAD PARA EXPORTAR CSV
 * 
 * Índice:
 * 1. Método estático de exportación
 * 2. Validación
 * 3. Escritura del archivo
 * 4. Manejo de excepciones
 */

public class CsvExporter {

    /* 1. Método de exportación */
    // Es estático porque no necesito crear instancias de esta clase
    public static void exportarInvestigadores(List<Investigador> investigadores, String rutaArchivo) {

        /* 2. Validación */
        if (investigadores.isEmpty()) {
            System.out.println("\n⚠️  No hay investigadores para exportar.");
            return;
        }

        /* 3. Escritura del archivo */
        try (CSVWriter writer = new CSVWriter(new FileWriter(rutaArchivo))) {

            String[] cabecera = { "nombre", "edad", "cantidad de experimentos" };
            writer.writeNext(cabecera);

            // Escribo cada investigador como una línea del CSV
            for (Investigador investigador : investigadores) {
                String[] datos = {
                        investigador.getNombre(),
                        String.valueOf(investigador.getEdad()),
                        String.valueOf(investigador.getCantidadExperimentos())
                };
                writer.writeNext(datos);
            }

            System.out.println("\n✅ Archivo CSV exportado exitosamente en: " + rutaArchivo);

            /* 4. Manejo de excepciones */
        } catch (IOException e) {
            System.out.println("\n❌ Error al exportar el archivo CSV: " + e.getMessage());
        }
    }
}