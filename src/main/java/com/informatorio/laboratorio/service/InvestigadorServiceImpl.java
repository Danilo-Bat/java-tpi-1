package com.informatorio.laboratorio.service;

import com.informatorio.laboratorio.interfaces.IInvestigadorService;
import com.informatorio.laboratorio.model.Investigador;
import com.informatorio.laboratorio.repository.InvestigadorRepository;
import com.informatorio.laboratorio.util.CsvExporter;
import com.informatorio.laboratorio.util.UtilScanner;
import com.informatorio.laboratorio.util.Validador;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * Implementación del servicio de investigadores.
 * Maneja toda la lógica de negocio relacionada con investigadores.
 */
public class InvestigadorServiceImpl implements IInvestigadorService {

    private InvestigadorRepository investigadorRepository;

    public InvestigadorServiceImpl(InvestigadorRepository investigadorRepository) {
        this.investigadorRepository = investigadorRepository;
    }

    @Override
    public void registrarInvestigadorInteractivo(Scanner scanner) {
        System.out.println("\n--- REGISTRAR INVESTIGADOR ---");

        String nombre = pedirNombre(scanner);
        if (nombre == null)
            return;

        int edad = pedirEdad(scanner);
        if (edad == -1)
            return;

        registrarInvestigador(nombre, edad);
        System.out.println("[OK] Investigador registrado exitosamente.");
    }

    @Override
    public void registrarInvestigador(String nombre, int edad) {
        Investigador investigador = new Investigador(nombre, edad);
        investigadorRepository.guardar(investigador);
    }

    @Override
    public List<Investigador> listarInvestigadores() {
        return investigadorRepository.obtenerTodos();
    }

    @Override
    public Investigador buscarPorId(int id) {
        return investigadorRepository.buscarPorId(id);
    }

    @Override
    public Investigador obtenerInvestigadorConMasExperimentos() {
        return investigadorRepository.obtenerTodos().stream()
                .max(Comparator.comparingInt(Investigador::getCantidadExperimentos))
                .orElse(null);
    }

    @Override
    public boolean existeInvestigador() {
        return investigadorRepository.existeInvestigador();
    }

    @Override
    public void exportarInvestigadoresInteractivo(Scanner scanner) {
        System.out.println("\n--- EXPORTAR INVESTIGADORES A CSV ---");

        if (!existeInvestigador()) {
            System.out.println("[ERROR] No hay investigadores para exportar.");
            return;
        }

        System.out.print("Nombre del archivo (Enter para usar 'investigadores.csv'): ");
        String archivo = scanner.nextLine().trim();

        if (archivo.isEmpty()) {
            archivo = "investigadores.csv";
        } else if (!archivo.endsWith(".csv")) {
            archivo += ".csv";
        }

        exportarInvestigadoresACsv(archivo);
    }

    @Override
    public void exportarInvestigadoresACsv(String rutaArchivo) {
        List<Investigador> investigadores = investigadorRepository.obtenerTodos();
        CsvExporter.exportarInvestigadores(investigadores, rutaArchivo);
    }

    @Override
    public void mostrarInvestigadoresDisponibles() {
        System.out.println("\nInvestigadores disponibles:");
        listarInvestigadores()
                .forEach(inv -> System.out.println("  ID: " + inv.getId() + " - " + inv.getNombre()));
    }

    // Métodos auxiliares para pedir y validar datos

    private String pedirNombre(Scanner scanner) {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();

        if (!Validador.esTextoValido(nombre)) {
            System.out.println("[ERROR] El nombre no puede estar vacio.");
            return null;
        }
        return nombre;
    }

    private int pedirEdad(Scanner scanner) {
        System.out.print("Edad: ");
        int edad = UtilScanner.leerEntero(scanner);

        if (!Validador.esEdadValida(edad)) {
            System.out.println("[ERROR] La edad debe estar entre 1 y 120 anios.");
            return -1;
        }
        return edad;
    }
}