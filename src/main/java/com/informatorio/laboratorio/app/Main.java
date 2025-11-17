package com.informatorio.laboratorio.app;

import com.informatorio.laboratorio.interfaces.IExperimentoService;
import com.informatorio.laboratorio.interfaces.IInvestigadorService;
import com.informatorio.laboratorio.repository.ExperimentoRepository;
import com.informatorio.laboratorio.repository.InvestigadorRepository;
import com.informatorio.laboratorio.service.ExperimentoServiceImpl;
import com.informatorio.laboratorio.service.InvestigadorServiceImpl;
import java.util.Scanner;

/**
 * Clase principal del sistema de gestión de experimentos.
 * Maneja el menú principal y delega las operaciones a los servicios
 * correspondientes.
 */
public class Main {

    private static final int OPCION_SALIR = 8;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        IInvestigadorService investigadorService = inicializarInvestigadorService();
        IExperimentoService experimentoService = inicializarExperimentoService();

        int opcion;
        do {
            mostrarMenu();
            opcion = leerOpcion(scanner);
            procesarOpcion(opcion, scanner, investigadorService, experimentoService);
        } while (opcion != OPCION_SALIR);

        scanner.close();
    }

    private static IInvestigadorService inicializarInvestigadorService() {
        InvestigadorRepository investigadorRepository = new InvestigadorRepository();
        return new InvestigadorServiceImpl(investigadorRepository);
    }

    private static IExperimentoService inicializarExperimentoService() {
        ExperimentoRepository experimentoRepository = new ExperimentoRepository();
        return new ExperimentoServiceImpl(experimentoRepository);
    }

    private static void mostrarMenu() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║   GESTOR DE EXPERIMENTOS - LABORATORIO CHAD               ║");
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        System.out.println("║  1. Registrar Investigador                                ║");
        System.out.println("║  2. Registrar Experimento (Quimico/Fisico)                ║");
        System.out.println("║  3. Listar Todos los Experimentos                         ║");
        System.out.println("║  4. Mostrar Totales (Exitos/Fallos)                       ║");
        System.out.println("║  5. Mostrar Experimento de Mayor Duracion                 ║");
        System.out.println("║  6. Generar Reporte Completo                              ║");
        System.out.println("║  7. Exportar Investigadores a CSV                         ║");
        System.out.println("║  8. Salir                                                 ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.print("Selecciona una opcion: ");
    }

    private static void procesarOpcion(int opcion, Scanner scanner,
            IInvestigadorService investigadorService,
            IExperimentoService experimentoService) {
        switch (opcion) {
            case 1:
                investigadorService.registrarInvestigadorInteractivo(scanner);
                break;
            case 2:
                experimentoService.registrarExperimentoInteractivo(scanner, investigadorService);
                break;
            case 3:
                experimentoService.listarExperimentosConMensajes();
                break;
            case 4:
                experimentoService.mostrarTotales();
                break;
            case 5:
                experimentoService.mostrarExperimentoDeMayorDuracion();
                break;
            case 6:
                experimentoService.generarReporteCompleto(investigadorService);
                break;
            case 7:
                investigadorService.exportarInvestigadoresInteractivo(scanner);
                break;
            case 8:
                System.out.println("\nSaliendo del sistema. Hasta pronto!");
                break;
            default:
                System.out.println("\n[ERROR] Opcion invalida. Intenta nuevamente.");
        }
    }

    private static int leerOpcion(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}