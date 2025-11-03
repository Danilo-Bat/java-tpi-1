package com.informatorio.laboratorio.app;

import com.informatorio.laboratorio.interfaces.IExperimentoService;
import com.informatorio.laboratorio.interfaces.IInvestigadorService;
import com.informatorio.laboratorio.model.Experimento;
import com.informatorio.laboratorio.model.Investigador;
import com.informatorio.laboratorio.repository.ExperimentoRepository;
import com.informatorio.laboratorio.repository.InvestigadorRepository;
import com.informatorio.laboratorio.service.ExperimentoServiceImpl;
import com.informatorio.laboratorio.service.InvestigadorServiceImpl;
import com.informatorio.laboratorio.util.Validador;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/*
 * CLASE PRINCIPAL - GESTOR DE EXPERIMENTOS
 * 
 * Índice:
 * 1. Constantes y variables globales
 * 2. Método main
 * 3. Inicialización de servicios
 * 4. Menú principal
 * 5. Funcionalidades (1 a 8)
 * 6. Métodos auxiliares
 */

public class Main {

    /* 1. Constantes y variables globales */
    // Constantes para el menú (evito números mágicos)
    private static final int OPCION_SALIR = 8;
    private static final String NOMBRE_ARCHIVO_DEFAULT = "investigadores.csv";

    // Servicios y Scanner
    private static IInvestigadorService investigadorService;
    private static IExperimentoService experimentoService;
    private static Scanner scanner;

    /* 2. Método main */
    public static void main(String[] args) {

        inicializarServicios();
        scanner = new Scanner(System.in);

        int opcion;

        // Uso do-while para que se ejecute al menos una vez
        do {
            mostrarMenu();
            opcion = leerEntero();
            procesarOpcion(opcion);
        } while (opcion != OPCION_SALIR);

        scanner.close();
    }

    /* 3. Inicialización de servicios */
    // Creo los repositorios e inyecto las dependencias manualmente
    private static void inicializarServicios() {
        InvestigadorRepository investigadorRepository = new InvestigadorRepository();
        ExperimentoRepository experimentoRepository = new ExperimentoRepository();

        investigadorService = new InvestigadorServiceImpl(investigadorRepository);
        experimentoService = new ExperimentoServiceImpl(experimentoRepository);
    }

    /* 4. Menú principal */
    private static void mostrarMenu() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║   GESTOR DE EXPERIMENTOS - LABORATORIO CHAD               ║");
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        System.out.println("║  1. Registrar Investigador                                ║");
        System.out.println("║  2. Registrar Experimento (Químico/Físico)                ║");
        System.out.println("║  3. Listar Todos los Experimentos                         ║");
        System.out.println("║  4. Mostrar Totales (Éxitos/Fallos)                       ║");
        System.out.println("║  5. Mostrar Experimento de Mayor Duración                 ║");
        System.out.println("║  6. Generar Reporte Completo                              ║");
        System.out.println("║  7. Exportar Investigadores a CSV                         ║");
        System.out.println("║  8. Salir                                                 ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        System.out.print("Selecciona una opción: ");
    }

    private static void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                registrarInvestigador();
                break;
            case 2:
                registrarExperimento();
                break;
            case 3:
                listarExperimentos();
                break;
            case 4:
                mostrarTotales();
                break;
            case 5:
                mostrarExperimentoMayor();
                break;
            case 6:
                generarReporte();
                break;
            case 7:
                exportarCsv();
                break;
            case 8:
                System.out.println("\n👋 Saliendo del sistema. ¡Hasta pronto!");
                break;
            default:
                System.out.println("\n❌ Opción inválida. Intenta nuevamente.");
        }
    }

    /* 5. Funcionalidades */

    // Funcionalidad 1: Registrar investigador
    private static void registrarInvestigador() {
        System.out.println("\n--- REGISTRAR INVESTIGADOR ---");

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();

        // Valido que el nombre no esté vacío
        if (!Validador.esTextoValido(nombre)) {
            System.out.println("❌ El nombre no puede estar vacío.");
            return;
        }

        System.out.print("Edad: ");
        int edad = leerEntero();

        // Valido que la edad sea válida
        if (!Validador.esEdadValida(edad)) {
            System.out.println("❌ La edad debe estar entre 1 y 120 años.");
            return;
        }

        investigadorService.registrarInvestigador(nombre, edad);
        System.out.println("✅ Investigador registrado exitosamente.");
    }

    // Funcionalidad 2: Registrar experimento
    private static void registrarExperimento() {

        if (!investigadorService.existeInvestigador()) {
            System.out.println("\n❌ No hay investigadores registrados. Registra uno primero.");
            return;
        }

        System.out.println("\n--- REGISTRAR EXPERIMENTO ---");
        System.out.println("1. Químico");
        System.out.println("2. Físico");
        System.out.print("Tipo: ");

        int tipo = leerEntero();

        if (tipo == 1) {
            registrarQuimico();
        } else if (tipo == 2) {
            registrarFisico();
        } else {
            System.out.println("❌ Tipo inválido.");
        }
    }

    private static void registrarQuimico() {
        System.out.print("Nombre del experimento: ");
        String nombre = scanner.nextLine().trim();

        if (!Validador.esTextoValido(nombre)) {
            System.out.println("❌ El nombre no puede estar vacío.");
            return;
        }

        System.out.print("Duración (minutos): ");
        int duracion = leerEntero();

        if (!Validador.esDuracionValida(duracion)) {
            System.out.println("❌ La duración debe ser mayor a 0.");
            return;
        }

        System.out.print("¿Fue exitoso? (s/n): ");
        String respuesta = scanner.nextLine().trim();
        boolean exitoso = respuesta.equalsIgnoreCase("s") || respuesta.equalsIgnoreCase("si");

        System.out.print("Tipo de reactivo: ");
        String reactivo = scanner.nextLine().trim();

        if (!Validador.esTextoValido(reactivo)) {
            System.out.println("❌ El reactivo no puede estar vacío.");
            return;
        }

        mostrarInvestigadores();

        System.out.print("ID del investigador: ");
        int id = leerEntero();

        Investigador investigador = investigadorService.buscarPorId(id);

        if (investigador == null) {
            System.out.println("❌ Investigador no encontrado.");
            return;
        }

        experimentoService.registrarExperimentoQuimico(nombre, duracion, exitoso, reactivo, investigador);
        System.out.println("✅ Experimento químico registrado exitosamente.");
    }

    private static void registrarFisico() {
        System.out.print("Nombre del experimento: ");
        String nombre = scanner.nextLine().trim();

        if (!Validador.esTextoValido(nombre)) {
            System.out.println("❌ El nombre no puede estar vacío.");
            return;
        }

        System.out.print("Duración (minutos): ");
        int duracion = leerEntero();

        if (!Validador.esDuracionValida(duracion)) {
            System.out.println("❌ La duración debe ser mayor a 0.");
            return;
        }

        System.out.print("¿Fue exitoso? (s/n): ");
        String respuesta = scanner.nextLine().trim();
        boolean exitoso = respuesta.equalsIgnoreCase("s") || respuesta.equalsIgnoreCase("si");

        System.out.print("Instrumento utilizado: ");
        String instrumento = scanner.nextLine().trim();

        if (!Validador.esTextoValido(instrumento)) {
            System.out.println("❌ El instrumento no puede estar vacío.");
            return;
        }

        List<Investigador> todosInvestigadores = investigadorService.listarInvestigadores();
        mostrarInvestigadores();

        System.out.print("¿Cuántos investigadores participan?: ");
        int cantidad = leerEntero();

        if (cantidad < 1) {
            System.out.println("❌ Debe haber al menos 1 investigador.");
            return;
        }

        if (cantidad > todosInvestigadores.size()) {
            System.out.println("❌ No hay suficientes investigadores registrados.");
            return;
        }

        List<Investigador> investigadoresSeleccionados = new ArrayList<>();
        Set<Integer> idsSeleccionados = new HashSet<>();

        for (int i = 0; i < cantidad; i++) {
            System.out.print("ID del investigador " + (i + 1) + ": ");
            int id = leerEntero();

            // Valido que no sea un ID ya seleccionado
            if (idsSeleccionados.contains(id)) {
                System.out.println("❌ Este investigador ya fue seleccionado. Elige otro.");
                i--;
                continue;
            }

            Investigador investigador = investigadorService.buscarPorId(id);

            if (investigador == null) {
                System.out.println("❌ Investigador no encontrado. Intenta de nuevo.");
                i--;
                continue;
            }

            investigadoresSeleccionados.add(investigador);
            idsSeleccionados.add(id);
        }

        experimentoService.registrarExperimentoFisico(nombre, duracion, exitoso, instrumento,
                investigadoresSeleccionados);
        System.out.println("✅ Experimento físico registrado exitosamente.");
    }

    // Funcionalidad 3: Listar experimentos
    private static void listarExperimentos() {
        System.out.println("\n--- LISTADO DE EXPERIMENTOS ---");

        if (!experimentoService.existeExperimento()) {
            System.out.println("No hay experimentos registrados.");
            return;
        }

        // Uso forEach para recorrer la lista
        experimentoService.listarExperimentos().forEach(System.out::println);
    }

    // Funcionalidad 4: Mostrar totales
    private static void mostrarTotales() {
        System.out.println("\n--- TOTALES DE EXPERIMENTOS ---");

        if (!experimentoService.existeExperimento()) {
            System.out.println("No hay experimentos registrados.");
            return;
        }

        int exitosos = experimentoService.contarExperimentosExitosos();
        int fallidos = experimentoService.contarExperimentosFallidos();

        System.out.println("✅ Total de experimentos exitosos: " + exitosos);
        System.out.println("❌ Total de experimentos fallidos: " + fallidos);
        System.out.println("📊 Total general: " + (exitosos + fallidos));
    }

    // Funcionalidad 5: Experimento de mayor duración
    private static void mostrarExperimentoMayor() {
        System.out.println("\n--- EXPERIMENTO DE MAYOR DURACIÓN ---");

        Experimento exp = experimentoService.obtenerExperimentoDeMayorDuracion();

        if (exp == null) {
            System.out.println("No hay experimentos registrados.");
            return;
        }

        System.out.println(exp);
    }

    // Funcionalidad 6: Reporte completo
    private static void generarReporte() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                   REPORTE COMPLETO                         ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");

        if (!experimentoService.existeExperimento()) {
            System.out.println("No hay experimentos registrados.");
            return;
        }

        double promedio = experimentoService.calcularPromedioDuracion();
        double porcentaje = experimentoService.calcularPorcentajeExito();
        Investigador destacado = investigadorService.obtenerInvestigadorConMasExperimentos();

        System.out.printf("📊 Promedio de duración: %.2f minutos\n", promedio);
        System.out.printf("📈 Porcentaje de éxito global: %.2f%%\n", porcentaje);

        if (destacado != null && destacado.getCantidadExperimentos() > 0) {
            System.out.println("\n🏆 Investigador con más experimentos:");
            System.out.println("   " + destacado);
        } else {
            System.out.println("\n🏆 Ningún investigador ha realizado experimentos aún.");
        }
    }

    // Funcionalidad 7: Exportar CSV
    private static void exportarCsv() {
        System.out.println("\n--- EXPORTAR INVESTIGADORES A CSV ---");

        if (!investigadorService.existeInvestigador()) {
            System.out.println("❌ No hay investigadores para exportar.");
            return;
        }

        System.out.print("Nombre del archivo (Enter para usar 'investigadores.csv'): ");
        String archivo = scanner.nextLine().trim();

        // Si no ingresa nada, uso el nombre por defecto
        if (archivo.isEmpty()) {
            archivo = NOMBRE_ARCHIVO_DEFAULT;
        } else if (!archivo.endsWith(".csv")) {
            archivo += ".csv";
        }

        investigadorService.exportarInvestigadoresACsv(archivo);
    }

    /* 6. Métodos auxiliares */

    private static void mostrarInvestigadores() {
        System.out.println("\nInvestigadores disponibles:");
        // Uso method reference para imprimir cada investigador
        investigadorService.listarInvestigadores()
                .forEach(inv -> System.out.println("  ID: " + inv.getId() + " - " + inv.getNombre()));
    }

    private static int leerEntero() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}