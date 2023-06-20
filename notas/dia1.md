# Bigdata

Conjunto de estrategias para trabajar con datos cuando las técnicas tradicionales que hemos venido usando para estos menesteres NO ME SON SUFICIENTES (No me dan capacidad, rendimiento...)

Por qué me puede pasar esto?
- Volumen de datos
- Volumen de generación de datos. Necesito procesar los dato muy rápido... bastantes datos
- Datos muy complejos

Llevar un listado de tareas -> Excel 100 tareas...
50000 tareas -> Ms Access
500.000  -> MySQL/MariaDB
5.000.000 -> Ms SQL Server
100.000.000 -> OracleDB
...

Llega un momento que las herramientas / hardware que existe no me es suficiente
En ese caso nos vamos a una infraestrutura BIG DATA:
Voy a trabajar con máquinas de mierda (commody hardware)... Vamos a trabajar contra un enjambre de maquinitas de mierda....
Que trabajan juntas para conseguir un fin.
Si la tarea es:
- Muy compleja
- Necesita hacerse muy rápido
- Necesito almacenar muchos datos

Lo que necesitaré será un enjambre más grande! más máquinas...
Sobre esas máquinas montamos una aplicación que permita usar el HW de todas esas máquinas como si fuese una!
Ser capaz de delegar/orquestar trabajos entre múltiples CPUs (decenas, cientos, miles)
Ser capaz de usar la RAM de múltiples computadoras, para alojar cierta info con la que necesito trabajar
Ser capaz de usar los HDD de múltiples computadoras, para almacenar info que necesito persistir.

Cuando montamos un cluster BigData necesito un software que me permita hacer esa gestión (uso de RAM, CPU, HDD...) de muchas máquinas como si fueran una. Eso es lo que hace Apache Hadoop (el equivalente a un SO en el mundo BigData)

Apache Spark es una librería / software montado sobre Apache Hadoop.
El problema es que Apache Hadoop, tal y como se diseño, vuelca todas las suboperaciones en HDD

    100 millones -> Cluster de Hadoop
                                        --> 10 millones a Maquina 1 (esta máquinba, al recibir los datos los guarda a HDD)
                                                                    los procesa
                                                                    y antes de dolverlos, el resultado lo guarda a HDD
                                        --> 10 millones a Maquina 2
                                        --> 10 millones a Maquina 3
                                            ...
                                        --> 10 millones a Maquina 10

Eso es muy poco eficiente... para según que actividades
Spark es una librería adicional que puedo montar en Hadoop... y que también implementa El patrón de diseño MAP REDUCE,
pero en la que los datos no son almacenados en HDD sino en RAM... lo que meojra extraordinariamente el rendimiento.

Apache Hadoop nos ofrece 3 cosas:
- Un software (YARN) que permite coordinar todos nodos (maquinas de mierda) de un cluster
- Un software para implementar operaciones MapReduce
- Un sistema de archivos: HDFS, que permite guardar archivos GIGANTES distribuidos en múltiples HDD de múltiples computadoras

  En un USB de 16 GBs vacio, puedo guardar un archivo de 5 Gbs? Depende del sistema de archivos del USB?
  FAT -> El tamaño máximo de archivo son 2 Gbs
  NTFS
  EXFAT
  ...
  Todos tiene su límite...
  Si quiero guardar un archivo de 10Pb... 1 exabyte?

Apache Spark (CORE) replantea (reemplaza) el módulo MapReduce de Apàche Hadoop, para hacerlo más eficiente.

## Software BigData:

### BBDD

- Cassandra
- HBase
- Hive
- MongoDB

### Análisis de datos

- Spark
- Mahout

## Gestión/Tratamiento de datos

Con los datos... podemos querer hacer muchas cosas:
- Almacenarlos... para su posterior consulta.
- Transmitirlos
  Clash royale (2v2) 4 jugadores en 4 telefonos
  En un segundo... un jugador puede hacer 2 movimientos
  De mi telefono tienen que salir 2 veces x 3 mensajes al resto de telefonos cada segundo = 6 mensajes/seg
  Pero somos 4 jugando: 24 mensajes a procesar por partida por segundo
  50k partidas simultaneas: 24 x 50.000 = más de 1 millón de mensajes por segundo.
- Análisis de datos:
    - Estádistica descriptiva (Business Intelligence)
    - Mineria de datos (Data mining)
    - Machine learning > Deep learning
      Para todas ellas, puede ser necesario TRANSFORMAR los datos.

# Cloud

Conjunto de servicios (IT) que una empresa ofrece a través de internet, normalmente de forma:
- Automatizada
- Mediante un modelo de pago por uso.

Esos servicios pueden ser de distinta naturaleza:
- IaaS - Infraestructura: Almacenamiento, Computadora, Redes
- PaaS - Plataforma: MariaDB, MongoDB, OracleDB, Dame un cluster de Hadoop (Spark)
- SaaS - Software: Entorno de desarrollo remoto

# Apache Spark

Es un libreria/aplicación que se instala en un cluster de Hadoop, para hacer operaciones según un patrón de diseño MapReduce
de forma eficiente, dentro de un cluster BigData.

Adicionalmente, Apache Spark, cuenta con algunas librerías, que puedo usar o no! para:
- Machine learning
- Procesar datos en modo Streaming (por defecto se procesan en modo batch)
- Trabajar con los datos con una sintaxis similar a SQL

---

Tengo una operación que hacer de forma CONTINUA (hora) sobre 10 millones de datos/hora
Y puedo elegir entre:
- Opción 1: Conseguir un HW que permita procesar esa cantidad de información <<<<<<<<<
- Opción 2: Montar un enjambre BigData y hacerlo con este tipo de herramientas que estamos hablando

Tengo una operación que hacer de vez en cuando (cada día) sobre 10 millones de datos.... que tardo 1 hora en hacerlo
Y puedo elegir entre:
- Opción 1: Conseguir un HW que permita procesar esa cantidad de información... que hago con ese HW las otras 23 horas del día?
  A chinchar como un capullo €€€€€
- Opción 2: Montar un enjambre BigData y hacerlo con este tipo de herramientas que estamos hablando <<<<<<<<<
  Y de donde saco esas máquinas cuando las necesito? De un cloud


Cálculo en UNA computadora... y tiene varias CPUs.
Es más eficiente hacer el cálculo en 1 CPU (core) o usando todos los disponibles?
Y si el cálculo es paralelizable? Depende

TareaA 1 hora
TareaB 1 hora
2 maquinas: 1 para cada tarea

100 tareas que ejecutar en 50 maquinas
que van entrando, van saliendo
que tardan distinto entre si.

---
  
  -----------red--------------------------------------------
  |                                                     |
  MI PC                             --->                Cluster de máquinas que tienen Spark
                                                        Maestro ---> Nodos Trabajadores (decenas)
  Aqui tengo el programa              datos
  Y desde aquí lo ejecuto.            código de las operaciones que hay que hacer con los datos.
  funciones... que transformen y operen sobre los datos

  java --jar

Aqui levanto una JVM

public List<String> partir(String texto){
String[] partes = texto.split("-");
return Arrays.asList(partes);
}


---

Programa que calcule el número PI

---

Entornos de desarrollo: IntelliJ.
Yo voy a usar IntelliJ

Instalar IntelliJ <- Community Web IntelliJ
---

# Paradigmas de programación:

Son distintas forma de expresar un mismo concepto (secuencia de órdenes) usando un determinado lenguaje.

- Imperativo: Expresamos las órdenes de forma secuencial
  Aunque, tenemos algunas palabras que nos permiten modificar (controlar) el flujo de órdenes que debe ejecutarse:
  if, else, for, switch, while
- Procedural: Cuando el lenguaje me permite crear y ejecutar procedimientos (métodos, funciones) reutilizables
  GWBASIC
- Funcional:  Esto es lo que se mete en Java 1.8... Y la base de Spark
  Cuando el lenguaje me permite que una variable apunte a una función... y que la función pueda ser invocada desde la variable
- Orientación a Objetos: Cuando el lenguaje me permite definir sus propios tipos de datos, con sus propiedades y funciones específicos.

                PROPIEDADES QUE LO CARACTERIZAN                 OPERACIONES(funciones)
  int         valor(el propio numero)                         valor absoluto, sumar, restar, multiplicar
  double      valor(el propio numero)                         valor absoluto, sumar, restar, multiplicar, parte entera, parte decimal
  String      secuencia de caracteres                         longitud, mayusculas, minusculas, empiezaPor??
  List        secuencia de objetos                            longitud, contiene?

  Usuario     id, nombre, apelliodos, edad, email             enviarEmail


# MapReduce

Desde JAVA 1.8, Java, de forma nativa permite realizar operaciones de tipo MapReduce
Apache Spark, me permite llevar esas operaciones a un cluster de Cpark distribuido

En JAVA 1.8 se introduce el concepto de Stream
Ese mismo concepto existe en Spark, bajo el nombre RDD

Qué es un Stream en JAVA (o un RDD en Spark)? Una secuencia de datos... cómo una lista? SI, IGUAL QUE UNA LISTA

Entonces? para que sirve, si ya tenemos la Lista?
Es un objeto preparado para programación funcional, en especial para funci0ones de tipo MAP REDUCE

Al aplicar un modelo de programación MAP REDUCE, usaremos:

- Operaciones MAP

  STREAM(RDD)         map(triple)                             STREAM(RDD)
  1               --->             --->     = triple(1) =         3
  2               --->             --->     = triple(2) =         6
  3               --->             --->     = triple(3)           9
  5               --->             --->     = funcion mapeo(5)
  9               --->             --->     = funcion mapeo(9)

Esa de ahí arriba es la operación de MAP más basíca: filter, flatMap
En general, llamaremos función de tipo "MAP", no sólo a la función MAP, sino a cualquier función que dado un Stream devuelva otro Stream

- Operaciones REDUCE
  En general, llamaremos función de tipo REDUCE, no solo a la función reduce, sino a cuqluier función que YA NO DEVUELVA UN STREAM

Si lo pensais, dado un conjunto de datos:
CONJUNTO DE DATOS
.stream()            // ME DA UN STREAM
.map( x0 )           // ME DA OTRO STREAM
.map( x1 )           // ME DA OTRO STREAM
.map( x2 )           // ME DA OTRO STREAM
.map( x3 )           // ME DA OTRO STREAM
.max()            // YA NO ME DA STREAM... y corta



40  \
12  /  40   \
                40   \
11 \  11    /                 40
11 /

12 \             12  /
10 /


FlatMap


StreamA                map ( .split(" "))      StreamB
"Linea1 del texto"                             ["Linea1", "del", "texto"]
"Linea2 del texto"                             ["Linea2", "del", "texto"]

                      flatmap( .split(" "))
                                               "Linea1"
                                               "del"
                                               "texto"
                                               "Linea2"
                                               "del"
                                               "texto"


(5,5,5,5,5)

5 -> (0,1,2,3,4)

(0,1,2,3,4,0,1,2,3,4,0,1,2,3,4,0,1,2,3,4,0,1,2,3,4)