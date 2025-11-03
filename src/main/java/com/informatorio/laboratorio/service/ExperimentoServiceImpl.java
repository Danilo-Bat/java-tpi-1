package com.informatorio.laboratorio.service;

import com.informatorio.laboratorio.interfaces.IExperimentoService;
import com.informatorio.laboratorio.model.Experimento;
import com.informatorio.laboratorio.model.ExperimentoFisico;
import com.informatorio.laboratorio.model.ExperimentoQuimico;
import com.informatorio.laboratorio.model.Investigador;
import com.informatorio.laboratorio.repository.ExperimentoRepository;
import java.util.Comparator;
import java.util.List;

/*
 * IMPLEMENTACIÓN DEL SERVICIO DE EXPERIMENTOS
 * 
 * Índice:
 * 1. Dependencias
 * 2. Constructor
 * 3. Métodos de registro
 * 4. Métodos de consulta
 * 5. Métodos de conteo con Streams
 * 6. Métodos de cálculo estadístico
 */

public class ExperimentoServiceImpl implements IExperimentoService {

    /* 1. Dependencias */
    private ExperimentoRepository experimentoRepository;

    /* 2. Constructor */
    public ExperimentoServiceImpl(ExperimentoRepository experimentoRepository) {
        this.experimentoRepository = experimentoRepository;
    }

    /* 3. Métodos de registro */
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
            String instrumentoUtilizado, List<Investigador> investigadores) {
        ExperimentoFisico experimento = new ExperimentoFisico(nombre, duracion, exitoso,
                instrumentoUtilizado, investigadores);

        // Incremento el contador de cada investigador que participó
        investigadores.forEach(Investigador::incrementarExperimentos);

        experimentoRepository.guardar(experimento);
    }

    /* 4. Métodos de consulta */
    @Override
    public List<Experimento> listarExperimentos() {
        return experimentoRepository.obtenerTodos();
    }

    /* 5. Métodos de conteo con Streams */
    // Uso filter() para contar los exitosos
    @Override
    public int contarExperimentosExitosos() {
        return (int) experimentoRepository.obtenerTodos().stream()
                .filter(Experimento::isExitoso)
                .count();
    }

    // Uso filter() con negación para contar los fallidos
    @Override
    public int contarExperimentosFallidos() {
        return (int) experimentoRepository.obtenerTodos().stream()
                .filter(exp -> !exp.isExitoso())
                .count();
    }

    // Uso max() con Comparator para encontrar el de mayor duración
    @Override
    public Experimento obtenerExperimentoDeMayorDuracion() {
        return experimentoRepository.obtenerTodos().stream()
                .max(Comparator.comparingInt(Experimento::getDuracionMinutos))
                .orElse(null);
    }

    /* 6. Métodos de cálculo estadístico */
    // Uso mapToInt() y average() para calcular el promedio
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
    public boolean existeExperimento() {
        return experimentoRepository.existeExperimento();
    }
}