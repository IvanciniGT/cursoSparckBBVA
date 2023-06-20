package com.curso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class CalcularPISpark {

    public static void main(String[] args) {

        // Abrir una sesión (conexión) con el maestro de un cluster de Apache Spark

        int NUMERO_TOTAL_DARDOS = 1000 * 1000;
        int NUMERO_TRABAJADORES = 10;
        int dardosPorTrabajador = NUMERO_TOTAL_DARDOS / NUMERO_TRABAJADORES;

        // Necesitaré crear un RDD, el equivalente en Spark a un Stream
        // Tengo funciones que por ejemplo, desde cualquier collection de Java (List, Set, Map) me generan un RDD

        // A ese RDD le aplico las operaciones map reduce que necesite

        double estimacionDePI = IntStream.range(0, NUMERO_TRABAJADORES)                     // Genera un Stream que contiene (0,1,2,3,4,5,6,7,8,9)
                                        .map( trabajador -> dardosPorTrabajador)            // Genera un stream con (2000000,200000,20000...)
                                        .parallel()
                                        .mapToDouble( CalcularPISpark::estimarPI )  // Stream de Estimaciones (3.14, 1.15, 1.16...)
                                        .sum();

        estimacionDePI = estimacionDePI / NUMERO_TRABAJADORES;
        System.out.println("Mi estimación de PI vale: " + estimacionDePI);
        // Cierro conexión con el cluster de Spark
    }

    public static double estimarPI(int numeroDeDardosATirar){
        int numeroDardosDentro = 0;

        for (int dardo = 0; dardo < numeroDeDardosATirar; dardo++) { // Haz un millón de veces
            double x = Math.random();
            double y = Math.random();

            double distanciaAlCentro = Math.sqrt(x * x + y * y);
            if (distanciaAlCentro <= 1) numeroDardosDentro++;
        }

        return 4. * numeroDardosDentro / numeroDeDardosATirar;

    }

}
