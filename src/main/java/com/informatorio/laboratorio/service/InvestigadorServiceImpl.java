package com.informatorio.laboratorio.service;

import com.informatorio.laboratorio.interfaces.IInvestigadorService;
import com.informatorio.laboratorio.model.Investigador;
import com.informatorio.laboratorio.repository.InvestigadorRepository;
import com.informatorio.laboratorio.util.CsvExporter;
import java.util.Comparator;
import java.util.List;

/*
 * IMPLEMENTACIÓN DEL SERVICIO DE INVESTIGADORES
 * 
 * Índice:
 * 1. Dependencias
 * 2. Constructor
 * 3. Métodos de registro y consulta
 * 4. Método con Streams para obtener investigador destacado
 * 5. Exportación CSV
 */

public class InvestigadorServiceImpl implements IInvestigadorService {

    /* 1. Dependencias */
    // Inyecto el repositorio por constructor (Inversión de Dependencias)
    private InvestigadorRepository investigadorRepository;

    /* 2. Constructor */
    public InvestigadorServiceImpl(InvestigadorRepository investigadorRepository) {
        this.investigadorRepository = investigadorRepository;
    }

    /* 3. Métodos de registro y consulta */
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

    /* 4. Investigador con más experimentos usando Streams */
    // Uso la API de Streams para encontrar el máximo
    @Override
    public Investigador obtenerInvestigadorConMasExperimentos() {
        return investigadorRepository.obtenerTodos().stream()
                .max(Comparator.comparingInt(Investigador::getCantidadExperimentos))
                .orElse(null);
    }

    /* 5. Exportación CSV */
    @Override
    public void exportarInvestigadoresACsv(String rutaArchivo) {
        List<Investigador> investigadores = investigadorRepository.obtenerTodos();
        CsvExporter.exportarInvestigadores(investigadores, rutaArchivo);
    }

    @Override
    public boolean existeInvestigador() {
        return investigadorRepository.existeInvestigador();
    }
}