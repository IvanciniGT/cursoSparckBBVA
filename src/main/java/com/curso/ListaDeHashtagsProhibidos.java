package com.curso;

import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;

import java.util.List;

class ListaDeHashtagsProhibidos {

    private static volatile Broadcast<List<String>> listaDeHashtagsAEliminar = null;

    public static Broadcast<List<String>> getInstance(SparkContext conexion){
        if(listaDeHashtagsAEliminar == null) {
            synchronized (ListaDeHashtagsProhibidos.class) {
                if (listaDeHashtagsAEliminar == null) {
                    List<String> hashtagsInvalidos = List.of("caca", "culo", "pedo", "pis");
                    listaDeHashtagsAEliminar = conexion.broadcast(hashtagsInvalidos);
                }
            }
        }
        return listaDeHashtagsAEliminar;
    }
}
