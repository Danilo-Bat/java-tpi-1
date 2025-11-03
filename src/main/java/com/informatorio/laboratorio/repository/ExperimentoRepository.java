package com.informatorio.laboratorio.repository;

import com.informatorio.laboratorio.model.Experimento;
import java.util.ArrayList;
import java.util.List;

/*
 * REPOSITORIO DE EXPERIMENTOS
 * 
 * Índice:
 * 1. Lista de almacenamiento
 * 2. Constructor
 * 3. Métodos CRUD básicos
 */

public class ExperimentoRepository {

    /* 1. Lista de almacenamiento */
    private List<Experimento> experimentos;

    /* 2. Constructor */
    public ExperimentoRepository() {
        this.experimentos = new ArrayList<>();
    }

    /* 3. Métodos CRUD básicos */
    public void guardar(Experimento experimento) {
        experimentos.add(experimento);
    }

    public List<Experimento> obtenerTodos() {
        return experimentos;
    }

    public boolean existeExperimento() {
        return !experimentos.isEmpty();
    }
}