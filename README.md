# Cajero automático: ejercicio de Colas en Java + Spring Boot

## Objetivo
Simular la fila de personas que esperan para usar un cajero automático. Cada persona tiene nombre, monto de retiro y fecha/hora aleatoria de llegada. El programa ordena las llegadas de la más antigua a la más reciente y las agrega a una cola para atenderlas en ese orden.
Compila el proyecto:
mvn clean package
```
Ejecuta la aplicación:
mvn spring-boot:run
```

## ¿Qué hace el programa?
1. Genera ocho personas con datos aleatorios.
2. Asigna a cada persona una hora de llegada aleatoria dentro de los 60 minutos anteriores.
3. Ordena las personas por fecha y hora de llegada.
4. Inserta las personas en la cola.
5. Muestra la fila en el orden correcto.
6. Atiende a las personas usando `poll()`, respetando el comportamiento FIFO.




