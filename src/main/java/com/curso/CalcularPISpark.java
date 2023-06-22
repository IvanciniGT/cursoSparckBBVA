package com.curso;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CalcularPISpark {

    //private static int dardosPorTrabajador ;

    public static void main(String[] args) throws Exception {

        int NUMERO_TOTAL_DARDOS = 1000 * 1000 * 1000;
        int NUMERO_TRABAJADORES = 10;
        int dardosPorTrabajador = NUMERO_TOTAL_DARDOS / NUMERO_TRABAJADORES;
        List<Integer> trabajadores = IntStream.range(0, NUMERO_TRABAJADORES).boxed().collect(Collectors.toList());
                                                // (0,1,2,3,4,5,6,7,8,9)


        // Abrir una sesión (conexión) con el maestro de un cluster de Apache Spark
        final SparkConf configuracion = new SparkConf().setAppName("CalcularPI").setMaster("local");
        // Usar la palabra "local" como master, levanta un cluster de spark en nuestro pc, para poder hacer pruebas y jugar
        final JavaSparkContext conexion = new JavaSparkContext(configuracion);
        //final JavaRDD<Integer> datosPartida =
        double estimacionDePI = conexion.parallelize(trabajadores) //, NUMERO_TRABAJADORES/2)   // Tengo un RDD con (0,1,2,3,4,5,6,7,8,9)
                .map(trabajador -> dardosPorTrabajador)           // Tengo un RDD con (2000,2000,2000,200,2000)
                //.map(CalcularPISpark::asignarDardos)           // Tengo un RDD con (2000,2000,2000,200,2000)
                    // Esto solo configura en Spark que hay que hacer este mapeo
                .repartition(NUMERO_TRABAJADORES)                 // El equivalente al parallel de los Stream de JAVA
                .mapToDouble(CalcularPISpark::estimarPI)            // Tengo un RDD con (3.13, 3.14, 3.16, 3.15)
                    // Esto solo configura en Spark que hay que calcular PI
                .sum();
                    // La función de reducción, manda to-do al cluster a que se ejecute!

        // Cierro conexión con el cluster de Spark
        conexion.close();


        // Necesitaré crear un RDD, el equivalente en Spark a un Stream
        estimacionDePI = estimacionDePI / NUMERO_TRABAJADORES;
        System.out.println("Mi estimación de PI vale: " + estimacionDePI);

    }

    public static double estimarPI(int numeroDeDardosATirar) {
        int numeroDardosDentro = 0;

        for (int dardo = 0; dardo < numeroDeDardosATirar; dardo++) { // Haz un millón de veces
            double x = Math.random();
            double y = Math.random();

            double distanciaAlCentro = Math.sqrt(x * x + y * y);
            if (distanciaAlCentro <= 1) numeroDardosDentro++;
        }

        return 4. * numeroDardosDentro / numeroDeDardosATirar;

    }

    //public static int asignarDardos(int numeroDeTrabajador) {
    //    return dardosPorTrabajador;
    //}

}