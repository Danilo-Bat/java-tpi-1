package com.informatorio.laboratorio.interfaces;

import com.informatorio.laboratorio.model.Investigador;
import java.util.List;
import java.util.Scanner;

/**
 * Contrato para el servicio de investigadores.
 */
public interface IInvestigadorService {

    // Métodos interactivos
    void registrarInvestigadorInteractivo(Scanner scanner);

    void exportarInvestigadoresInteractivo(Scanner scanner);

    void mostrarInvestigadoresDisponibles();

    // Métodos de negocio
    void registrarInvestigador(String nombre, int edad);

    List<Investigador> listarInvestigadores();

    Investigador buscarPorId(int id);

    Investigador obtenerInvestigadorConMasExperimentos();

    void exportarInvestigadoresACsv(String rutaArchivo);

    boolean existeInvestigador();
}