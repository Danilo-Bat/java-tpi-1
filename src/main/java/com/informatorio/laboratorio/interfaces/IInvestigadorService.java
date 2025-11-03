package com.informatorio.laboratorio.interfaces;

import com.informatorio.laboratorio.model.Investigador;
import java.util.List;

/*
 * INTERFAZ DE SERVICIO DE INVESTIGADORES
 * 
 * Defino el contrato que debe cumplir la implementación del servicio.
 * Esto aplica el principio de Inversión de Dependencias (SOLID).
 */

public interface IInvestigadorService {

    void registrarInvestigador(String nombre, int edad);

    List<Investigador> listarInvestigadores();

    Investigador buscarPorId(int id);

    Investigador obtenerInvestigadorConMasExperimentos();

    void exportarInvestigadoresACsv(String rutaArchivo);

    boolean existeInvestigador();
}