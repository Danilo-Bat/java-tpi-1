package com.informatorio.laboratorio.model;

import java.util.ArrayList;
import java.util.List;

/*
 * CLASE EXPERIMENTO QUÍMICO
 * 
 * Índice:
 * 1. Atributos específicos
 * 2. Constructor
 * 3. Getters y Setters
 * 4. Implementación de métodos abstractos
 * 5. toString
 */

public class ExperimentoQuimico extends Experimento {

    /* 1. Atributos específicos */
    // Los experimentos químicos tienen un reactivo y solo un investigador
    private String tipoReactivo;
    private Investigador investigador;

    /* 2. Constructor */
    // Llamo al constructor padre con super() y luego inicializo mis atributos
    public ExperimentoQuimico(String nombre, int duracionMinutos, boolean exitoso,
            String tipoReactivo, Investigador investigador) {
        super(nombre, duracionMinutos, exitoso);
        this.tipoReactivo = tipoReactivo;
        this.investigador = investigador;
    }

    /* 3. Getters y Setters */
    public String getTipoReactivo() {
        return tipoReactivo;
    }

    public void setTipoReactivo(String tipoReactivo) {
        this.tipoReactivo = tipoReactivo;
    }

    public Investigador getInvestigador() {
        return investigador;
    }

    public void setInvestigador(Investigador investigador) {
        this.investigador = investigador;
    }

    /* 4. Implementación de métodos abstractos */
    // Retorno una lista con el único investigador
    @Override
    public List<Investigador> getInvestigadores() {
        List<Investigador> lista = new ArrayList<>();
        lista.add(investigador);
        return lista;
    }

    @Override
    public String getTipo() {
        return "Químico";
    }

    /* 5. toString */
    @Override
    public String toString() {
        return "ID: " + getId() + " | Tipo: " + getTipo() + " | Nombre: " + getNombre() +
                " | Duración: " + getDuracionMinutos() + " min | Resultado: " + getResultado() +
                " | Reactivo: " + tipoReactivo + " | Investigador: " + investigador.getNombre();
    }
}