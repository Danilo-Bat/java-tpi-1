package com.informatorio.laboratorio.model;

import java.util.List;
import java.util.stream.Collectors;

/*
 * CLASE EXPERIMENTO FÍSICO
 * 
 * Índice:
 * 1. Atributos específicos
 * 2. Constructor
 * 3. Getters y Setters
 * 4. Implementación de métodos abstractos
 * 5. toString con Streams
 */

public class ExperimentoFisico extends Experimento {

    /* 1. Atributos específicos */
    // Los experimentos físicos usan un instrumento y pueden tener varios
    // investigadores
    private String instrumentoUtilizado;
    private List<Investigador> investigadores;

    /* 2. Constructor */
    public ExperimentoFisico(String nombre, int duracionMinutos, boolean exitoso,
            String instrumentoUtilizado, List<Investigador> investigadores) {
        super(nombre, duracionMinutos, exitoso);
        this.instrumentoUtilizado = instrumentoUtilizado;
        this.investigadores = investigadores;
    }

    /* 3. Getters y Setters */
    public String getInstrumentoUtilizado() {
        return instrumentoUtilizado;
    }

    public void setInstrumentoUtilizado(String instrumentoUtilizado) {
        this.instrumentoUtilizado = instrumentoUtilizado;
    }

    /* 4. Implementación de métodos abstractos */
    @Override
    public List<Investigador> getInvestigadores() {
        return investigadores;
    }

    public void setInvestigadores(List<Investigador> investigadores) {
        this.investigadores = investigadores;
    }

    @Override
    public String getTipo() {
        return "Físico";
    }

    /* 5. toString con Streams */
    // Uso Streams para armar la lista de nombres de investigadores
    @Override
    public String toString() {
        String nombresInvestigadores = investigadores.stream()
                .map(Investigador::getNombre)
                .collect(Collectors.joining(", "));

        return "ID: " + getId() + " | Tipo: " + getTipo() + " | Nombre: " + getNombre() +
                " | Duración: " + getDuracionMinutos() + " min | Resultado: " + getResultado() +
                " | Instrumento: " + instrumentoUtilizado +
                " | Investigadores: " + nombresInvestigadores;
    }
}