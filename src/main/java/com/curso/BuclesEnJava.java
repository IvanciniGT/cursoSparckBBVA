package com.curso;

import java.util.Arrays;
import java.util.function.Function;
import java.util.List;
import java.util.function.Consumer;

public class BuclesEnJava {

    public static void main(String[] args) {

        List<String> textos = Arrays.asList("Texto1", "TEXTO2", "texto3") ;

        // Quiero mostrarlos por pantalla
        // Hasta Java 1.5
        for (int indice = 0; indice < textos.size(); indice++ ){
            System.out.println(textos.get(indice));
        }
        // Desde java 1.5., hasta Java 1.8
        for(String texto: textos){ // Los Iterable pueden aplicarse a un ForEach
            System.out.println(texto);
        }


        String texto = "hola";
        // Este código hace 3 cosas
        // "hola"           Pone un objeto de tipo String en la memoria con el valor "hola"
        // String texto     Crea una variable que puede apuntar a objetos de tipo String... y la llama: texto
        // =                Asigna la variable texto al valor "hola"

        texto = "adios";
        // "adios"          Pone un objeto de tipo String en la memoria con el valor "adios"
        //                  En el mismo sitio de antes? o en otro? EN OTRO
        //                  Llegados a este punto, cuántas cosas hay en memoria? 2: "hola" y "adios"
        // texto            Toma la variable "texto"
        // =                Asigna la variable al nuevo valor

        // String texto     = "hola";
        // Paquete java.util.function. ME ofrece tipos de datos para definir vaariables que apunten a funciones
        // Function     Una función que recibe un dato y devuelve un dato
        // Consumer     Una función que recibe un dato y no devuelve nada
        // Producer     Una función que no recibe nada y devuelve un dato
        // Predicate    Una función que recibe un dato y devuelve un valor lógico (true | false)
        // En java 1.8, se introuduce un operador nuevo :: que permite referenciar una función... no ejecutarla
        Consumer<String> miFuncion = System.out::println;
        // La variable miFuncion apunta a la función println definida en System.out
        miFuncion.accept("Que bueno!!!!");
        // Este es el concepto de la programación funcional
        // El concepto es sencillo... el tema es el impacto de esto.
        // Desde el momento que puedo apuntar a una función desde una variable, puedo:
        // - Hacer que una función acepte como argumentos otras funciones
        // - Hacer que una función devuelva otra función


        // Desde Java 1.8, cómo podría escribir esto?
        // En JAVA 1.8 se añade a JAVA el paradigma funcional
        // A la función forEach, le voy a pasar UNA FUNCION como argumento
        textos.forEach( System.out::println );


        textos = Arrays.asList("Texto1", "TEXTO2", "texto3","Federico", "Federico2") ;

        textos              // PArto de una coleccion de datos
                .stream()  // PASO 1: Convierto la lista en un Stream... eso me da un objeto STREAM (como si fuera la lista), pero que le puedo aplciar programación funcional
                .map(String::toLowerCase)   // PASO 2: Aplico una función de mapeo:
                          // Cada dato del stream quiero se se pase a la función suministrada))
                .filter(BuclesEnJava::acabaEn2)
                .map(String::length)
                .forEach(System.out::println); // PASO 3: Función de reducción: En este caso un ForEach

        // Funciones LAMBDA
        // En java 1.8 se añade un segundo operador.
        // Ya conociamos uno nuevo    ::     Referenciar a una función
        // El otro nuevo es           ->     Permite definir una expresión que devuelva una función ANONIMA

                // Expresión?
        // System.out.print("HOLA!");       Statement, Sentencia, Frase
        // String texto = "hola" + "adios"; Otro statement
                          ////////////////   Esa porción de código, devuelve un valor: EXPRESSION: Expresión, Sintagma nominal

               // v  Lo que recibe                 // EXDPRESSION LAMBDA
        Function<String, String> miFuncioncita = (String palabra) -> {
                       // ^ Lo que devuelve
            return palabra.toUpperCase()+"__EUREKA!!!";
        };
        System.out.println(  miFuncioncita.apply("Amigo") );
        System.out.println(  miFuncioncita.apply("felipe") );

        miFuncioncita = (palabra) -> {
            // Lo que devuelve
            return palabra.toUpperCase()+"__EUREKA!!!";
        };
        System.out.println(  miFuncioncita.apply("Amigo") );
        System.out.println(  miFuncioncita.apply("felipe") );

        miFuncioncita = palabra -> {
            // Lo que devuelve
            return palabra.toUpperCase()+"__EUREKA!!!";
        };
        System.out.println(  miFuncioncita.apply("Amigo") );
        System.out.println(  miFuncioncita.apply("felipe") );


        miFuncioncita = palabra -> palabra.toUpperCase()+"__EUREKA!!!";
        System.out.println(  miFuncioncita.apply("Amigo") );
        System.out.println(  miFuncioncita.apply("felipe") );

        List<Integer> numeros = Arrays.asList(1,2,5,9,8,17,23);
        numeros.parallelStream()        // Quiero un Stream, que abra tanto hilo en la máquina como cores tenga disponibles..
                .map( numero -> numero * 7 )
                .map( numero -> numero - 4 )
                .map( numero -> numero + 3 )
                .filter( numero -> numero % 2 == 1 ) // Los impares
                .forEach( System.out::println );


        texto= "Linea 1 del texto\nLinea 2 del texto";
        texto.lines()   // En este stream tengo? 2 cosas
                .flatMap( linea -> Arrays.stream(linea.split(" ")) ) // En este stream cuantas cosas tengo?
                .forEach(System.out::println);

    }

    public static boolean acabaEn2(String texto){
        return texto.endsWith("2");
    }

    public static String miFuncioncita (String palabra){
        // Lo que devuelve
        return palabra.toUpperCase()+"__EUREKA!!!";
    };

}
