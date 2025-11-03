package com.informatorio.laboratorio.repository;

import com.informatorio.laboratorio.model.Investigador;
import java.util.ArrayList;
import java.util.List;

/*
 * REPOSITORIO DE INVESTIGADORES
 * 
 * Índice:
 * 1. Lista de almacenamiento
 * 2. Constructor
 * 3. Método guardar
 * 4. Método obtenerTodos
 * 5. Método buscarPorId
 * 6. Método existeInvestigador
 */

public class InvestigadorRepository {

    /* 1. Lista de almacenamiento */
    // Guardo todos los investigadores en memoria
    private List<Investigador> investigadores;

    /* 2. Constructor */
    public InvestigadorRepository() {
        this.investigadores = new ArrayList<>();
    }

    /* 3. Guardar */
    public void guardar(Investigador investigador) {
        investigadores.add(investigador);
    }

    /* 4. Obtener todos */
    public List<Investigador> obtenerTodos() {
        return investigadores;
    }

    /* 5. Buscar por ID */
    // Recorro la lista hasta encontrar el investigador con el ID que busco
    public Investigador buscarPorId(int id) {
        for (Investigador investigador : investigadores) {
            if (investigador.getId() == id) {
                return investigador;
            }
        }
        return null;
    }

    /* 6. Verificar existencia */
    public boolean existeInvestigador() {
        return !investigadores.isEmpty();
    }
}