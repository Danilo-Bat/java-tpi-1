package com.informatorio.laboratorio.repository;

import com.informatorio.laboratorio.model.Investigador;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para almacenar investigadores en memoria.
 */
public class InvestigadorRepository {

    private List<Investigador> investigadores;

    public InvestigadorRepository() {
        this.investigadores = new ArrayList<>();
    }

    public void guardar(Investigador investigador) {
        investigadores.add(investigador);
    }

    public List<Investigador> obtenerTodos() {
        return investigadores;
    }

    public Investigador buscarPorId(int id) {
        return investigadores.stream()
                .filter(inv -> inv.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean existeInvestigador() {
        return !investigadores.isEmpty();
    }
}