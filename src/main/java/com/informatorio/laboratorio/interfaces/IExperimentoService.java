package com.informatorio.laboratorio.interfaces;

import com.informatorio.laboratorio.model.Experimento;
import com.informatorio.laboratorio.model.Investigador;
import java.util.List;

/*
 * INTERFAZ DE SERVICIO DE EXPERIMENTOS
 * 
 * Contrato para el servicio de experimentos.
 */

public interface IExperimentoService {

    void registrarExperimentoQuimico(String nombre, int duracion, boolean exitoso,
            String tipoReactivo, Investigador investigador);

    void registrarExperimentoFisico(String nombre, int duracion, boolean exitoso,
            String instrumentoUtilizado, List<Investigador> investigadores);

    List<Experimento> listarExperimentos();

    int contarExperimentosExitosos();

    int contarExperimentosFallidos();

    Experimento obtenerExperimentoDeMayorDuracion();

    double calcularPromedioDuracion();

    double calcularPorcentajeExito();

    boolean existeExperimento();
}