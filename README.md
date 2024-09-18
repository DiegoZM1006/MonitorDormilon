# Monitor Dormilón

## Integrantes
- Diego Fernando Zuñiga - A00382021
- Daniel Montezuma - A00382231
- Juan David Patiño - A00381293

## Problema

El departamento de CSI de la Universidad Icesi dispone de un monitor que ayuda a los estudiantes de los cursos de algoritmos con sus tareas de programación. El monitor trabaja en una oficina pequeña que tiene espacio limitado: una silla para el monitor, una para el estudiante en consulta y tres sillas en el corredor para los estudiantes que esperan su turno. Cuando no hay estudiantes esperando, el monitor se duerme. Si un estudiante llega mientras el monitor está dormido, lo despierta. Si el monitor está ocupado y no hay sillas libres en el corredor, el estudiante se va y regresa más tarde.

El objetivo del problema es implementar esta situación utilizando hilos en Java y semáforos, asegurando que los estudiantes se atiendan en orden y se gestionen adecuadamente las sillas disponibles y el estado del monitor.

## Descripción del Código

El código está compuesto por dos clases principales: Monitor y Estudiante, ambas implementadas como hilos en Java. Para coordinar las interacciones entre el monitor y los estudiantes, se usan varios semáforos.

### Estructura:

1. Monitor:

    - El monitor alterna entre dormir y atender estudiantes. Si un estudiante llega y el monitor está dormido, lo despierta usando un semáforo. Después de atender a un estudiante, el monitor verifica si hay más estudiantes esperando. Si no los hay, vuelve a dormir.
    - El monitor usa los semáforos semDespertar para ser despertado por un estudiante y semAtencion para liberar al estudiante una vez ha terminado de ayudarlo.

2. Estudiante:

    - Cada estudiante alterna entre programar y buscar la ayuda del monitor. Si hay una silla libre, el estudiante se sienta y despierta al monitor si está dormido. Si no hay sillas disponibles, el estudiante vuelve a la sala de cómputo y regresa más tarde.
    - Los estudiantes interactúan con los semáforos semSillas para controlar las sillas disponibles y semAtencion para recibir ayuda del monitor cuando les corresponde.
3. Semáforos:

    - semDespertar: Controla el estado del monitor, permitiendo que los estudiantes lo despierten si está dormido.
    - semMutex: Controla el acceso exclusivo al monitor y las sillas para evitar conflictos de acceso simultáneo.
    - semSillas: Maneja la disponibilidad de las tres sillas del corredor.
    - semAtencion: Coordina la interacción entre el monitor y los estudiantes mientras estos son atendidos.

## Conclusiones

- Sincronización entre hilos: Este ejercicio nos permitió comprender cómo gestionar la concurrencia y la sincronización entre múltiples hilos utilizando semáforos. Aprendimos a controlar el acceso a recursos limitados, como las sillas y el monitor, asegurando que los estudiantes sean atendidos de manera eficiente y sin conflictos.

- Manejo de recursos compartidos: Aprendimos la importancia de manejar correctamente los recursos compartidos en un entorno de multihilos. Este ejercicio nos enseñó a utilizar semáforos para evitar problemas de concurrencia, asegurando que las interacciones entre estudiantes y monitor sigan un orden lógico y evitando condiciones de carrera.