package com.curso;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;
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
        conexion.parallelize(tweets)                                             // RDD<String>
                .map( tweet -> tweet.replaceAll("#", " #") )    // RDD<String> Añadir espacios en blanco antes de los #
                .flatMap( tweet -> Arrays.asList( tweet.split("[ .,_+(){}!?¿'\"<>/@|&-]") ).iterator() ) // RDD<String> Extraer todos los términos
                .filter( termino -> termino.startsWith("#") ) // RDD<String> Me quedo solo con los que empiezan por cuadradito
                .map( hashtag -> hashtag.toLowerCase() ) // RDD<String> Normalizar los hashtags
                .mapToPair( hashtag -> new Tuple2<>(hashtag, 1) ) // PairRDD<Tuple2<String, Integer>>    Añadirle a cada hashtag un 1
                .reduceByKey(Integer::sum) // PairRDD<Tuple2<String, Integer>>  Cuento las ocurrencias de cada hashtag
                            // (numeroOcurrencias, otroNumeroOcurrencias) -> numeroOcurrencias + otroNumeroOcurrencias)
                .mapToPair( parejita -> new Tuple2<>(parejita._2, parejita._1))
                .sortByKey(false)
                .take(10) // List<Tuple2>
        // Mostrar por pantalla los resultados
                .forEach( parejita ->  System.out.println("El hashtag: " + parejita._2 + " se ha mencionado " + parejita._1 + " veces") );

                //.forEach( System.out::println );
                //.forEach( parejita -> System.out.println(parejita) );



        // Cierro conexión con el cluster de Spark
        conexion.close();

    }



}