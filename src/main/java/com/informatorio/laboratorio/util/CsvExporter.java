package com.informatorio.laboratorio.util;

import com.informatorio.laboratorio.model.Investigador;
import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Utilidad para exportar datos a archivos CSV.
 */
public class CsvExporter {

    public static void exportarInvestigadores(List<Investigador> investigadores, String rutaArchivo) {

        if (investigadores.isEmpty()) {
            System.out.println("\n[ADVERTENCIA] No hay investigadores para exportar.");
            return;
        }

        try (CSVWriter writer = new CSVWriter(new FileWriter(rutaArchivo))) {

            String[] cabecera = { "nombre", "edad", "cantidad de experimentos" };
            writer.writeNext(cabecera);

            for (Investigador investigador : investigadores) {
                String[] datos = {
                        investigador.getNombre(),
                        String.valueOf(investigador.getEdad()),
                        String.valueOf(investigador.getCantidadExperimentos())
                };
                writer.writeNext(datos);
            }

            System.out.println("\n[OK] Archivo CSV exportado exitosamente en: " + rutaArchivo);

        } catch (IOException e) {
            System.out.println("\n[ERROR] Error al exportar el archivo CSV: " + e.getMessage());
        }
    }
}