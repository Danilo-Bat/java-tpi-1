package com.informatorio.laboratorio.model;

/*
 * CLASE INVESTIGADOR
 * 
 * Índice:
 * 1. Contador estático y atributos
 * 2. Constructor
 * 3. Getters y Setters
 * 4. Método incrementarExperimentos
 * 5. equals y hashCode
 * 6. toString
 */

public class Investigador {

    /* 1. Contador estático y atributos */
    // Uso un contador estático para generar IDs únicos automáticamente
    private static int contadorId = 1;

    private int id;
    private String nombre;
    private int edad;
    private int cantidadExperimentos;

    /* 2. Constructor */
    // Inicializo el investigador y le asigno un ID único
    public Investigador(String nombre, int edad) {
        this.id = contadorId++;
        this.nombre = nombre;
        this.edad = edad;
        this.cantidadExperimentos = 0;
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

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getCantidadExperimentos() {
        return cantidadExperimentos;
    }

    /* 4. Incrementar experimentos */
    // Cada vez que participa en un experimento, sumo uno
    public void incrementarExperimentos() {
        this.cantidadExperimentos++;
    }

    /* 5. equals y hashCode */
    // Implemento equals para comparar investigadores por ID
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Investigador otro = (Investigador) obj;
        return this.id == otro.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    /* 6. toString */
    @Override
    public String toString() {
        return "ID: " + id + " | Nombre: " + nombre + " | Edad: " + edad +
                " | Experimentos: " + cantidadExperimentos;
    }
}