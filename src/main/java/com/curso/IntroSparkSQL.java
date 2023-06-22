package com.curso;

import org.apache.spark.sql.SparkSession;

public class IntroSparkSQL {

    public static void main(String[] args){
        // Abrimos conexion con Spark (MAESTRO)
        SparkSession conexion = SparkSession.builder()
                                            // Añadiendo configuraciones
                                            .appName("introSQL")
                                            .master("local")
                                            //.build()
                                            .getOrCreate();
        // Obteniamos datos de una fuente

        // Los procesamos

        // Los volcamos

        // Cerramos conexion
        conexion.stop();
    }

}
