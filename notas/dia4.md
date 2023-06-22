# MAVEN

Es una herramienta de AUTOMATIZACION.

Nos permite automatizar tareas habituales de nuestros proyectos:
- compilación
- empaquetado
- pruebas
- mandar mi codigo a sonarqube
- la gestión de dependencias

# ETL

Uno de los grandes (posiblemente el mayor) uso de Spark es montar ETLs.

Extracción -> Transformación -> Carga de datos


BBDD Producción         -->
Archivos con datos      -->  Leer con Spark , transformarlo y llevarlo a otro destino  -----> Cluster Bigdata
                                                                                                    HIVE
                                                                                                    Cassandra
                                                                                                    HBase
                                                                                                    Mongo

## DATA PIPE

Cluster BigData
    Ficheros
        avro        (FILAS)
        parquet*    (COLUMNAS)
        csv
        json


Carpeta compartida en RED
    Ficheros

BBDD Producción

### Transformar con Spark

FUERA DEL           CLUSTER                         
-------------------------------------------------------------------------->
TWEETS              Procesamiento                   100 hashtags BBDD (Fuera del cluster)
BBDD Producción     Procesamiento                   Datawarehouse-BBDD (dentro del cluster)

DENTRO DEL CLUSTER
--------------------------------------------------------------------------->
Ficheros enormes        Procesar                    Ficheros (dentro o fuera)
                                                    BBDD (dentro o fuera)
FicheroA
    FA-parte1-nodo1     -> nodo1
    FA-parte2-nodo1
    FA-parte3-nodo2
    FA-parte4-nodo3     -> nodo3
    FA-parte5-nodo5
    FA-parte6-nodo8

Sitio web de amazon
    App web que se ejecuta en un cluster de servidores web(applicaciones)
        150 -> .log
                Montamos 2 archivos log:    Archivo1 (10Mbs)
                                                ^   v
                                            Archivo2 (10Mbs)


        Fluentd Flume FileBeat   ---> Kafka   <---- Spark  ---> HDFS


# Cluster Bigdata (Hadoop)

HDFS

Aunque un arhivo me entre en otro sistema de archivos X.
Qué rendimiento obtengo al leer o escribir el archivo.
    HDD (150Mbit/s)-> SDD(600) -> NVME(2200) x RAID(5)

200Gbs

Velocidad de transferencia de la RED
1 Gbit/s
10 Gbit/s 

Servidor   1M€
    Varias tarjetas de red (BOND entre las tarjetas de red)
    Uso varias tarjetas de red como si fueran 1... para conseguir más velocidad
                                                    poder usar menos velocidad pero varios a la vez

# Paradigma: Orientación a Objetos

Cuando el lenguaje me da la capacidad de poder definir mis propios tipos de datos, con sus propiedades y funciones

    String          secuencia de caracteres     a mayusculas
    Integer         numero                      valor absoluto
    Long
    Date            mes, dia, año               dia de la semana cae


---


# Ejecución dentro de un cluster real con spark-submit:

bin\spark-class org.apache.spark.deploy.master.Master

bin\spark-class org.apache.spark.deploy.worker.Worker spark://172.31.24.132:7077

bin\spark-submit --master spark://172.31.24.132:7077 --class com.curso.CalcularPISpark ../cursoSpark-1.0-SNAPSHOT.jar

bin\spark-submit --master spark://172.31.24.132:7077 --class com.curso.TrendingTopicsStreaming ../cursoSpark-1.0-SNAPSHOT.jar

nc -l -p 5555

#goodVibes
De veraneo #GoodVibes
#caca de verano
hola amigos
#goodVibes
De veraneo #GoodVibes
#caca de verano
hola amigos
#goodVibes
De veraneo #GoodVibes
#caca de verano
hola amigos
#viva#felipe
#viva#federico