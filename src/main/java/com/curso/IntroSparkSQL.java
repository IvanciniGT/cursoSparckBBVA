package com.curso;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import static org.apache.spark.sql.functions.col;

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
        Dataset<Row> usuarios = conexion.read().json("src/main/resources/usuarios.json");

        // Los procesamos
        usuarios.printSchema();
        //    Para operar con el esquema
        //    usuarios.schema()

        usuarios.groupBy("nombre").sum("edad").show();
        usuarios.select("nombre", "apellidos").show();
        usuarios.filter( col("edad").gt(50) ).show();
        usuarios.select( col("nombre"),      col("edad").plus(100)        ).show();

        // Los volcamos
        usuarios.show(40);

        // Cerramos conexion
        conexion.stop();
    }

}
