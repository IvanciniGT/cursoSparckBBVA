package com.curso;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TrendingTopics {

    //private static int dardosPorTrabajador ;

    public static void main(String[] args) throws Exception {

        List<String> tweets = List.of(
                "En la playa con mis amigos!!! (#goodVibes)" ,
                "Politicos de mierda#goodvibes#summerLove"
        );

        // Abrir una sesión (conexión) con el maestro de un cluster de Apache Spark
        final SparkConf configuracion = new SparkConf().setAppName("CalcularPI").setMaster("local");
        // Usar la palabra "local" como master, levanta un cluster de spark en nuestro pc, para poder hacer pruebas y jugar
        final JavaSparkContext conexion = new JavaSparkContext(configuracion);

        //final JavaRDD<Integer> datosPartida =
        conexion.parallelize(tweets)



        // Cierro conexión con el cluster de Spark
        conexion.close();

        // Mostrar por pantalla los resultados


    }

}