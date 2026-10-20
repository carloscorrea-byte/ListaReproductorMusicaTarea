# Cajero automático: ejercicio de Colas en Java + Spring Boot

## Objetivo
Simular la fila de personas que esperan para usar un cajero automático. Cada persona tiene nombre, monto de retiro y fecha/hora aleatoria de llegada. El programa ordena las llegadas de la más antigua a la más reciente y las agrega a una cola para atenderlas en ese orden.

## Conceptos de estructura de datos
- `Queue<Persona>`: representa la cola.
- `LinkedList`: implementación de la cola.
- `offer(persona)`: agrega una persona al final.
- `poll()`: retira y devuelve la primera persona.
- `isEmpty()`: permite comprobar si quedan personas.
- **FIFO** (*First In, First Out*): primero en entrar, primero en salir.
- `ArrayList` se utiliza temporalmente para generar y ordenar las llegadas antes de encolarlas.

## Requisitos
- JDK 17 o superior.
- Maven 3.6+ instalado, o usar el wrapper de Maven si se añade uno al proyecto.
- Conexión a internet la primera vez para descargar las dependencias de Spring Boot.

## Ejecutar desde la terminal

1. Descomprime `CajeroColasSpringBoot.zip`.
2. Abre una terminal en la carpeta que contiene `pom.xml`.
3. Comprueba las instalaciones:

```bash
java -version
mvn -version
```

4. Compila el proyecto:

```bash
mvn clean package
```

5. Ejecuta la aplicación:

```bash
mvn spring-boot:run
```

También puedes ejecutar el JAR generado:

```bash
java -jar target/cajero-colas-1.0.0.jar
```

En Windows PowerShell, si estás en la carpeta del proyecto, los comandos son los mismos.

## ¿Qué hace el programa?
1. Genera ocho personas con datos aleatorios.
2. Asigna a cada persona una hora de llegada aleatoria dentro de los 60 minutos anteriores.
3. Ordena las personas por fecha y hora de llegada.
4. Inserta las personas en la cola.
5. Muestra la fila en el orden correcto.
6. Atiende a las personas usando `poll()`, respetando el comportamiento FIFO.

Los datos se generan de nuevo en cada ejecución.

## Estructura del proyecto

```text
CajeroColasSpringBoot/
├── pom.xml
├── README.md
└── src/
    └── main/
        └── java/
            └── com/
                └── ejemplo/
                    └── cajero/
                        ├── CajeroColasApplication.java
                        ├── Persona.java
                        └── SimuladorCajero.java
```

## Explicación breve para presentar al profesor
“Este programa simula un cajero automático usando una cola FIFO. Cada persona almacena su nombre, el monto que desea retirar y su fecha y hora de llegada. Como las horas se generan aleatoriamente, primero se ordenan cronológicamente y luego se agregan a la cola. La operación `offer()` agrega personas al final y `poll()` atiende y elimina a la primera persona, respetando el orden de llegada. Spring Boot se usa para iniciar la aplicación y ejecutar la simulación mediante `CommandLineRunner`.”
