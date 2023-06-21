

1 dato que se va a usar muchas
veces en los workers
Posiblemente eso me interesa mandarlo 1 vez

MI PC                                       MAESTRO                     WORKER1                 WORKER2
JVM                                         JVM                         JVM                     JVM
        .sum()
          trabajador -> dardosPorTrabajador
          ---
          NUMERO_TRABAJADORES
          trabajadores
          ---
          CalcularPISpark::estimarPI
        ------------------------------>         ----------------------->
                                                -------------------------------------------------->
                                                <-----------------------
                                                <--------------------------------------------------
        <---------------------------------------
        

----
# Cómo se manda cada cosa:

## FUNCIÓN ANÓNIMA

        trabajador -> dardosPorTrabajador
        
        Al cluster... por la red... se manda el código de la función, junto con los datos que utiliza:
                - dardosPorTrabajador = 100000000
        
        Para mandar ese código, se transforma a bytes (SERIALIZACION)

## Objetos

        NUMERO_TRABAJADORES             int
        trabajadores                    List<Integer>

        Esto también se serializa.... pero... qué se serializa?
                Mando el código de la clase List? Mando el código de la clase Integer? NO
                Aqui mandamos los datos necesarios para que en el otro lado se contruyan un List<Integer> igual al que yo tengo en mi máquina.

## CalcularPISpark::estimarPI 

        Una función nominal de una clase

        Qué mando? El nombre de la función que hay que ejecutar.
        Y saben en el otro lado lo que es un CalcularPISpark? Más vale que si.
        Spark... antes esa línea de código, NO MANDA LA CLASE 

        Me tocará haberlo mandado de ANTEMANO

---

# Queremos montar el sistema de cálculo de los Trending topics de Twitter

1 hora se mandan un güevo de tweets... Esos tweets llevan hashtags
Identificar los hashtags de todos los tweets... y contabilizar cuanto se repiten
Para finalmente sacar un listado de los 10 más repetidos.

        "En la playa con mis amigos!!! (#goodVibes)"
        "Politicos de mierda#goodVibes"

Pasos:
- Lo primero de todo, antes de cada # metemos el qué? Un espacio en blanco          map
    .replace("#"," #")

- split(" ") por un Güevo de caracteres      flatMap    JavaRDD<String>
                                                map    JavaRDD<String[]>

          "En"
          "la"
          "playa"
          "con"
          "mis"
          "amigos!!!"
          "#goodVibes"
          "Politicos"
          "de"
          "mierda"
          "#goodVibes"

- filter() con los que empiecen por #

        "#goodVibes"
        "#goodvibes"
        "#goodVibes"
        "#loveSummer"

- normalizar       map

        "#goodvibes"
        "#goodvibes"
        "#goodvibes"
        "#lovesummer"

- Añadir algo con lo que pueda hacer sumas        mapToPair

          "#goodvibes" , 1
          "#goodvibes" , 1
          "#goodvibes" , 1
          "#lovesummer", 1

- reduce que sume los números agrupando por el primer valor de la pareja      reduceByKey

          "#goodvibes" , 3
          "#lovesummer", 1
          "#mierdaPaTi"

- ordenar por el número

- Cortar en un determinado Número de interés    REDUCE !!!!

---

"#goodvibes" , 1 \
"#goodvibes" , 1 / 2  \
"#goodvibes" , 1 \ 2  / 4   
"#goodvibes" , 1 / 
"#lovesummer", 1 


"En la playa con mis amigos"            .split

En, la, playa, con, mis, amigos

En
la
playa
con
mis
amigos


----

# BroadCast

Variable que podemos distribuir a los nodos y que se quedan con ella, de forma que no la tenga que enviar de continuo

## Singletons

Una clase de la que solo generamos una instancia

## Spark Streaming

Los procesos que teníamos funcionaban en modo Batch.
Un trabajo por lotes, que lanzo y se ejecuta y acaba

Lo que vamos a querer es que el programa de Spark no acabe nunca jamás en la vida!
Al programa le configuraré una ventana de tiempo: 1 minuto
En ese minuto (segundo), irá recibiendo tweets... al cabo del minuto, manda su procesamiento... 
mientras sigue acumulando una nueva partida de tweets.

A ese programa le llegan tweets... y produce un listado de hashtags cuantificados --> 
Ese listado lo guardaré en una BBDD
Lo mandaré a otro proceso que irá calculando trending topics con otra periodicidad


    ---0..-.-.---5-...--.-CATAPLOT !---------------------------->   Tiempo
                 v
                Se lanza la ejecución de los trabajos
                    Esto se pone en marcha en paralelo

// Qué pasa si en un momento dado, el programa se cae! Hay en error... Me quedé sin memoria... lo que sea!
Estaría perdiendo información
    Al trabajar en modo streaming, le damos a Spark un directorio, 
    donde va a ir guardando el estado en el que se encuentra en cada momento el programa

Si en un momento dado el programa se cae.... intentara recuperarse de nuevo, cuando el programa sea reiniciado

StreamingContext
- Periodicidad
- Trabajos que ejecutará cada 5 seg

En los procesos en streaming tenemos el concepto de DStream... cuidado no confundir con el Stream de Java

Stream Java: Colección de datos con métodos preparados para programación funcional 
  ||
RDD Spark: Es el quivalente... solo que los RDD se ejecutan DISTRIBIDOS EN NODOS

DStream Spark = Coleccion de RDDs



---

En paralelo, se iba gestando la libreria SQL de Spark
Y en ella se mete el conceto de SparkSession

Spark... a pelo... es muy complejo de manejar para el tratamiento de datos más o menos complejos.
La libreria SQL nos permite trabajar con conjuntos de datos mas complejos Datasets
Y se monta un API para poder operar con los Dataset como si fuera SQL