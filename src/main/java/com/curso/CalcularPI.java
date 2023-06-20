package com.curso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class CalcularPI {

    public static void main(String[] args) {

        // Tengo a un hilo (persona) ejecutando ese código para 2000 Millones de dardos
        // Podría tener a 10 personas haciendo su estimación para 200Millones...
        // Y luego hacer la media de las estimaciones
        int NUMERO_TOTAL_DARDOS = 1000 * 1000;
        int NUMERO_TRABAJADORES = 10;

        List<Double> estimaciones = new ArrayList<>();
        int dardosPorTrabajador = NUMERO_TOTAL_DARDOS / NUMERO_TRABAJADORES;

/*        // PASO 1: Calcular las estimacioones de cada trabajador
        for(int trabajador = 0; trabajador < NUMERO_TRABAJADORES; trabajador ++){ // Para cada trabajador
            estimaciones.add( CalcularPI.estimarPI(dardosPorTrabajador) );
        }
        // PASO 2: Hacer la media de las estimaciones
            // Sumo todas las estimaciones
        double estimacionDePI = 0;
        for(int trabajador = 0; trabajador < NUMERO_TRABAJADORES; trabajador ++){ // Para cada trabajador
            estimacionDePI += estimaciones.get( trabajador );
        }
        // Las divido entre el número de trabajadores para calcular la media
 */
        double estimacionDePI = IntStream.range(0, NUMERO_TRABAJADORES)                     // Genera un Stream que contiene (0,1,2,3,4,5,6,7,8,9)
                                        .map( trabajador -> dardosPorTrabajador)            // Genera un stream con (2000000,200000,20000...)
                                        .parallel()
                                        .mapToDouble( CalcularPI::estimarPI )  // Stream de Estimaciones (3.14, 1.15, 1.16...)
                                        .sum();

        estimacionDePI = estimacionDePI / NUMERO_TRABAJADORES;

        System.out.println("Mi estimación de PI vale: " + estimacionDePI);
        // Tengo a esos 10 tios calculando en paralelo? NO... uno detrás de otro... de forma secuencial
        // Para que ese trabajo se hiciera en paralelo en una computadora, necesito: ABRIR HILOS !


        double otraEstimacionDePI = IntStream.range(0, NUMERO_TRABAJADORES)    // Genera un Stream que contiene (0,1,2,3,4,5,6,7,8,9)
                .map( trabajador -> dardosPorTrabajador)                       // Genera un stream con (2000000,200000,20000...)
                .parallel()
                .flatMap( numeroDeDardosDelTrabajador -> IntStream.range(0, numeroDeDardosDelTrabajador))
                .mapToDouble(numeroDeDardo -> Math.sqrt( Math.pow(Math.random(),2)+Math.pow(Math.random(),2)))
                .filter( distancia -> distancia <=1)
                .count() * 4.0 / NUMERO_TOTAL_DARDOS;
        System.out.println("Mi otra estimación de PI vale: " + otraEstimacionDePI);


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
