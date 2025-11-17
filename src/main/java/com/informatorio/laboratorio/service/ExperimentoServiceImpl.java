package com.informatorio.laboratorio.service;

import com.informatorio.laboratorio.interfaces.IExperimentoService;
import com.informatorio.laboratorio.interfaces.IInvestigadorService;
import com.informatorio.laboratorio.model.Experimento;
import com.informatorio.laboratorio.model.ExperimentoFisico;
import com.informatorio.laboratorio.model.ExperimentoQuimico;
import com.informatorio.laboratorio.model.Investigador;
import com.informatorio.laboratorio.repository.ExperimentoRepository;
import com.informatorio.laboratorio.util.UtilScanner;
import com.informatorio.laboratorio.util.Validador;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Implementación del servicio de experimentos.
 * Gestiona el registro, consulta y estadísticas de experimentos.
 */
public class ExperimentoServiceImpl implements IExperimentoService {

    private ExperimentoRepository experimentoRepository;

    public ExperimentoServiceImpl(ExperimentoRepository experimentoRepository) {
        this.experimentoRepository = experimentoRepository;
    }

    @Override
    public void registrarExperimentoInteractivo(Scanner scanner,
            IInvestigadorService investigadorService) {
        if (!investigadorService.existeInvestigador()) {
            System.out.println("\n[ERROR] No hay investigadores registrados. Registra uno primero.");
            return;
        }

        System.out.println("\n--- REGISTRAR EXPERIMENTO ---");
        System.out.println("1. Quimico");
        System.out.println("2. Fisico");
        System.out.print("Tipo: ");

        int tipo = UtilScanner.leerEntero(scanner);

        if (tipo == 1) {
            registrarQuimicoInteractivo(scanner, investigadorService);
        } else if (tipo == 2) {
            registrarFisicoInteractivo(scanner, investigadorService);
        } else {
            System.out.println("[ERROR] Tipo invalido.");
        }
    }

    @Override
    public void registrarExperimentoQuimico(String nombre, int duracion, boolean exitoso,
            String tipoReactivo, Investigador investigador) {
        ExperimentoQuimico experimento = new ExperimentoQuimico(nombre, duracion, exitoso,
                tipoReactivo, investigador);
        investigador.incrementarExperimentos();
        experimentoRepository.guardar(experimento);
    }

    @Override
    public void registrarExperimentoFisico(String nombre, int duracion, boolean exitoso,
            String instrumentoUtilizado,
            List<Investigador> investigadores) {
        ExperimentoFisico experimento = new ExperimentoFisico(nombre, duracion, exitoso,
                instrumentoUtilizado, investigadores);

        investigadores.forEach(Investigador::incrementarExperimentos);
        experimentoRepository.guardar(experimento);
    }

    @Override
    public List<Experimento> listarExperimentos() {
        return experimentoRepository.obtenerTodos();
    }

    @Override
    public void listarExperimentosConMensajes() {
        System.out.println("\n--- LISTADO DE EXPERIMENTOS ---");

        if (!existeExperimento()) {
            System.out.println("No hay experimentos registrados.");
            return;
        }

        listarExperimentos().forEach(System.out::println);
    }

    @Override
    public boolean existeExperimento() {
        return experimentoRepository.existeExperimento();
    }

    @Override
    public int contarExperimentosExitosos() {
        return (int) experimentoRepository.obtenerTodos().stream()
                .filter(Experimento::isExitoso)
                .count();
    }

    @Override
    public int contarExperimentosFallidos() {
        return (int) experimentoRepository.obtenerTodos().stream()
                .filter(exp -> !exp.isExitoso())
                .count();
    }

    @Override
    public Experimento obtenerExperimentoDeMayorDuracion() {
        return experimentoRepository.obtenerTodos().stream()
                .max(Comparator.comparingInt(Experimento::getDuracionMinutos))
                .orElse(null);
    }

    @Override
    public double calcularPromedioDuracion() {
        return experimentoRepository.obtenerTodos().stream()
                .mapToInt(Experimento::getDuracionMinutos)
                .average()
                .orElse(0.0);
    }

    @Override
    public double calcularPorcentajeExito() {
        List<Experimento> experimentos = experimentoRepository.obtenerTodos();

        if (experimentos.isEmpty()) {
            return 0.0;
        }

        return ((double) contarExperimentosExitosos() / experimentos.size()) * 100;
    }

    @Override
    public void mostrarTotales() {
        System.out.println("\n--- TOTALES DE EXPERIMENTOS ---");

        if (!existeExperimento()) {
            System.out.println("No hay experimentos registrados.");
            return;
        }

        int exitosos = contarExperimentosExitosos();
        int fallidos = contarExperimentosFallidos();

        System.out.println("[OK] Total de experimentos exitosos: " + exitosos);
        System.out.println("[FALLO] Total de experimentos fallidos: " + fallidos);
        System.out.println("[TOTAL] Total general: " + (exitosos + fallidos));
    }

    @Override
    public void mostrarExperimentoDeMayorDuracion() {
        System.out.println("\n--- EXPERIMENTO DE MAYOR DURACION ---");

        Experimento exp = obtenerExperimentoDeMayorDuracion();

        if (exp == null) {
            System.out.println("No hay experimentos registrados.");
            return;
        }

        System.out.println(exp);
    }

    @Override
    public void generarReporteCompleto(IInvestigadorService investigadorService) {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                   REPORTE COMPLETO                         ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");

        if (!existeExperimento()) {
            System.out.println("No hay experimentos registrados.");
            return;
        }

        double promedio = calcularPromedioDuracion();
        double porcentaje = calcularPorcentajeExito();
        Investigador destacado = investigadorService.obtenerInvestigadorConMasExperimentos();

        System.out.printf("[ESTADISTICA] Promedio de duracion: %.2f minutos\n", promedio);
        System.out.printf("[ESTADISTICA] Porcentaje de exito global: %.2f%%\n", porcentaje);

        if (destacado != null && destacado.getCantidadExperimentos() > 0) {
            System.out.println("\n[DESTACADO] Investigador con mas experimentos:");
            System.out.println("   " + destacado);
        } else {
            System.out.println("\n[INFO] Ningun investigador ha realizado experimentos aun.");
        }
    }

    // Métodos privados para registro de experimentos específicos

    private void registrarQuimicoInteractivo(Scanner scanner,
            IInvestigadorService investigadorService) {
        String nombre = pedirNombreExperimento(scanner);
        if (nombre == null)
            return;

        int duracion = pedirDuracion(scanner);
        if (duracion == -1)
            return;

        boolean exitoso = pedirSiExitoso(scanner);

        String reactivo = pedirReactivo(scanner);
        if (reactivo == null)
            return;

        investigadorService.mostrarInvestigadoresDisponibles();
        Investigador investigador = seleccionarInvestigador(scanner, investigadorService);
        if (investigador == null)
            return;

        registrarExperimentoQuimico(nombre, duracion, exitoso, reactivo, investigador);
        System.out.println("[OK] Experimento quimico registrado exitosamente.");
    }

    private void registrarFisicoInteractivo(Scanner scanner,
            IInvestigadorService investigadorService) {
        String nombre = pedirNombreExperimento(scanner);
        if (nombre == null)
            return;

        int duracion = pedirDuracion(scanner);
        if (duracion == -1)
            return;

        boolean exitoso = pedirSiExitoso(scanner);

        String instrumento = pedirInstrumento(scanner);
        if (instrumento == null)
            return;

        List<Investigador> investigadores = seleccionarVariosInvestigadores(scanner,
                investigadorService);
        if (investigadores == null || investigadores.isEmpty())
            return;

        registrarExperimentoFisico(nombre, duracion, exitoso, instrumento, investigadores);
        System.out.println("[OK] Experimento fisico registrado exitosamente.");
    }

    // Métodos auxiliares para pedir y validar datos

    private String pedirNombreExperimento(Scanner scanner) {
        System.out.print("Nombre del experimento: ");
        String nombre = scanner.nextLine().trim();

        if (!Validador.esTextoValido(nombre)) {
            System.out.println("[ERROR] El nombre no puede estar vacio.");
            return null;
        }
        return nombre;
    }

    private int pedirDuracion(Scanner scanner) {
        System.out.print("Duracion (minutos): ");
        int duracion = UtilScanner.leerEntero(scanner);

        if (!Validador.esDuracionValida(duracion)) {
            System.out.println("[ERROR] La duracion debe ser mayor a 0.");
            return -1;
        }
        return duracion;
    }

    private boolean pedirSiExitoso(Scanner scanner) {
        System.out.print("Fue exitoso? (s/n): ");
        String respuesta = scanner.nextLine().trim();
        return respuesta.equalsIgnoreCase("s") || respuesta.equalsIgnoreCase("si");
    }

    private String pedirReactivo(Scanner scanner) {
        System.out.print("Tipo de reactivo: ");
        String reactivo = scanner.nextLine().trim();

        if (!Validador.esTextoValido(reactivo)) {
            System.out.println("[ERROR] El reactivo no puede estar vacio.");
            return null;
        }
        return reactivo;
    }

    private String pedirInstrumento(Scanner scanner) {
        System.out.print("Instrumento utilizado: ");
        String instrumento = scanner.nextLine().trim();

        if (!Validador.esTextoValido(instrumento)) {
            System.out.println("[ERROR] El instrumento no puede estar vacio.");
            return null;
        }
        return instrumento;
    }

    private Investigador seleccionarInvestigador(Scanner scanner,
            IInvestigadorService investigadorService) {
        System.out.print("ID del investigador: ");
        int id = UtilScanner.leerEntero(scanner);

        Investigador investigador = investigadorService.buscarPorId(id);

        if (investigador == null) {
            System.out.println("[ERROR] Investigador no encontrado.");
            return null;
        }

        return investigador;
    }

    private List<Investigador> seleccionarVariosInvestigadores(Scanner scanner,
            IInvestigadorService investigadorService) {
        List<Investigador> todosInvestigadores = investigadorService.listarInvestigadores();
        investigadorService.mostrarInvestigadoresDisponibles();

        System.out.print("Cuantos investigadores participan?: ");
        int cantidad = UtilScanner.leerEntero(scanner);

        if (cantidad < 1) {
            System.out.println("[ERROR] Debe haber al menos 1 investigador.");
            return null;
        }

        if (cantidad > todosInvestigadores.size()) {
            System.out.println("[ERROR] No hay suficientes investigadores registrados.");
            return null;
        }

        List<Investigador> investigadoresSeleccionados = new ArrayList<>();
        Set<Integer> idsSeleccionados = new HashSet<>();

        for (int i = 0; i < cantidad; i++) {
            System.out.print("ID del investigador " + (i + 1) + ": ");
            int id = UtilScanner.leerEntero(scanner);

            if (idsSeleccionados.contains(id)) {
                System.out.println("[ERROR] Este investigador ya fue seleccionado. Elige otro.");
                i--;
                continue;
            }

            Investigador investigador = investigadorService.buscarPorId(id);

            if (investigador == null) {
                System.out.println("[ERROR] Investigador no encontrado. Intenta de nuevo.");
                i--;
                continue;
            }

            investigadoresSeleccionados.add(investigador);
            idsSeleccionados.add(id);
        }

        return investigadoresSeleccionados;
    }
}