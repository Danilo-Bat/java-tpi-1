package com.informatorio.laboratorio.model;

import java.util.List;

/*
 * CLASE ABSTRACTA EXPERIMENTO
 * 
 * Índice:
 * 1. Contador estático y atributos comunes
 * 2. Constructor protegido
 * 3. Getters y Setters
 * 4. Método getResultado
 * 5. Métodos abstractos
 */

public abstract class Experimento {

    /* 1. Contador estático y atributos comunes */
    // Genero IDs únicos para cada experimento
    private static int contadorId = 1;

    private int id;
    private String nombre;
    private int duracionMinutos;
    private boolean exitoso;

    /* 2. Constructor protegido */
    // Solo las clases hijas pueden usar este constructor
    protected Experimento(String nombre, int duracionMinutos, boolean exitoso) {
        this.id = contadorId++;
        this.nombre = nombre;
        this.duracionMinutos = duracionMinutos;
        this.exitoso = exitoso;
    }

    /* 3. Getters y Setters */
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public boolean isExitoso() {
        return exitoso;
    }

    public void setExitoso(boolean exitoso) {
        this.exitoso = exitoso;
    }

    /* 4. Obtener resultado en texto */
    public String getResultado() {
        return exitoso ? "Éxito" : "Fallo";
    }

    /* 5. Métodos abstractos */
    // Cada tipo de experimento implementa estos métodos a su manera (polimorfismo)
    public abstract List<Investigador> getInvestigadores();

    public abstract String getTipo();
}