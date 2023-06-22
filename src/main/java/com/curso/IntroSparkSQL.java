package com.curso;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.get_json_object;

public class IntroSparkSQL {

    public static void main(String[] args){
        // Abrimos conexion con Spark (MAESTRO)
        SparkSession conexion = SparkSession.builder()
                                            // Añadiendo configuraciones
                                            .appName("introSQL")
                                            .master("local")
                                            //.build()
                                            .getOrCreate();

        try {
            // Obteniamos datos de una fuente
            Dataset<Row> usuarios = conexion.read().json("src/main/resources/usuarios.json");

            // Los procesamos
            usuarios.printSchema();
            //    Para operar con el esquema
            //    usuarios.schema()

            usuarios.groupBy("nombre").sum("edad").show();
            usuarios.select("nombre", "apellidos").show();
            usuarios.filter(col("edad").gt(50)).show();
            usuarios.select(col("nombre"), col("edad").plus(100)).show();
            usuarios.orderBy("edad").show();
            usuarios.orderBy(col("edad").desc()).show();

            usuarios.createTempView("usuarios");
            Dataset<Row> miDataset = conexion.sql("SELECT apellidos, edad FROM usuarios ORDER BY edad DESC");
            miDataset.show();

            // Los volcamos
            usuarios.show(40);


            // Trabajando con nuestros propios Objetos
            Dataset<String> usuarios2 = conexion.read().textFile("src/main/resources/usuarios.txt");
            usuarios2.show();

            JavaRDD<Usuario> objetosUsuario = usuarios2.toJavaRDD().map(Usuario::readUsuario);

            objetosUsuario.foreach( usuario -> System.out.println(usuario));
            objetosUsuario.filter( Usuario::isValid ).foreach( usuario -> System.out.println(usuario));;
            //objetosUsuario.filter( usuario -> usuario.isDniValido() && usuario.isEmailValido() );
            objetosUsuario.filter( usuario -> ! usuario.isValid() ).foreach( usuario -> System.out.println(usuario));;

            Dataset<Row> otrosUsuarios = conexion.createDataFrame(objetosUsuario, Usuario.class);
            otrosUsuarios.show();

            otrosUsuarios.createOrReplaceTempView("personas");
            conexion.sql("SELECT apellidos, edad, valid FROM personas ORDER BY edad DESC").show();


        }catch (Exception e){
            e.printStackTrace();
        }
        // Cerramos conexion
        conexion.stop();
    }

}
