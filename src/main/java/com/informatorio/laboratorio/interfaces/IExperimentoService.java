package com.informatorio.laboratorio.interfaces;

import com.informatorio.laboratorio.model.Experimento;
import com.informatorio.laboratorio.model.Investigador;
import java.util.List;
import java.util.Scanner;

/**
 * Contrato para el servicio de experimentos.
 */
public interface IExperimentoService {

        // Métodos interactivos
        void registrarExperimentoInteractivo(Scanner scanner, IInvestigadorService investigadorService);

        void listarExperimentosConMensajes();

        void mostrarTotales();

        void mostrarExperimentoDeMayorDuracion();

        void generarReporteCompleto(IInvestigadorService investigadorService);

        // Métodos de negocio
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