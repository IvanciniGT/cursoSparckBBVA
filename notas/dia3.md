

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