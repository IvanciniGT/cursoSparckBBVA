package com.curso;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

public class TrendingTopicsStreaming {

    //private static int dardosPorTrabajador ;

    public static void main(String[] args) throws Exception {
        // Vamos a conseguir un streaming context
            // Creando uno
            // Recuperando un streaming context que ya existiera
        JavaStreamingContext javaStreamingContext=JavaStreamingContext.getOrCreate("checkpoint",
                TrendingTopicsStreaming::crearStreamingContextSiNoExiste);
        // Poner ese Streaming context en marcha
        javaStreamingContext.start();
        // Por siempre jamás!
        javaStreamingContext.awaitTermination();
        javaStreamingContext.close();
    }
    public static JavaStreamingContext crearStreamingContextSiNoExiste(){
        // Abrir una sesión (conexión) con el maestro de un cluster de Apache Spark
        final SparkConf configuracion = new SparkConf().setAppName("ProcesarTweetsStreaming");//.setMaster("local");

        // Usar la palabra "local" como master, levanta un cluster de spark en nuestro pc, para poder hacer pruebas y jugar
        final JavaStreamingContext conexion = new JavaStreamingContext(configuracion, Durations.seconds(5));
        JavaDStream<String> tweets = conexion.socketTextStream("localhost", 5555);

        tweets
                .map( tweet -> tweet.replaceAll("#", " #") )    // RDD<String> Añadir espacios en blanco antes de los #
                .flatMap( tweet -> Arrays.asList( tweet.split("[ .,_+(){}!?¿'\"<>/@|&-]") ).iterator() ) // RDD<String> Extraer todos los términos
                .filter( termino -> termino.startsWith("#") ) // RDD<String> Me quedo solo con los que empiezan por cuadradito
                .map( hashtag -> hashtag.toLowerCase() ) // RDD<String> Normalizar los hashtags
                .foreachRDD(    /// Fucnion de reduccion cuando opero con DSTreams
                        rdd -> {
                            List<String> hashtagsInvalidos = ListaDeHashtagsProhibidos.getInstance(JavaSparkContext.fromSparkContext(rdd.context())).getValue();
                            rdd.filter( hashtag -> {
                                return ! hashtagsInvalidos.contains(hashtag.substring(1));
                            } )
                            .mapToPair( hashtag -> new Tuple2<>(hashtag, 1) ) // PairRDD<Tuple2<String, Integer>>    Añadirle a cada hashtag un 1
                            .reduceByKey(Integer::sum) // PairRDD<Tuple2<String, Integer>>  Cuento las ocurrencias de cada hashtag
                            // (numeroOcurrencias, otroNumeroOcurrencias) -> numeroOcurrencias + otroNumeroOcurrencias)
                            .mapToPair( parejita -> new Tuple2<>(parejita._2, parejita._1))
                            .sortByKey(false)
                            .take(10) // List<Tuple2>
                            // Mostrar por pantalla los resultados
                            .forEach( parejita ->  System.out.println("El hashtag: " + parejita._2 + " se ha mencionado " + parejita._1 + " veces") );
                        }
                );

        return conexion;
    }

}