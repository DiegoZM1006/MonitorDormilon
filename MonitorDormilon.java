import java.util.concurrent.*;

public class MonitorDormilon {

    static final int NUM_ESTUDIANTES = 10;
    static final int NUM_SILLAS = 3;

    static Semaphore semDespertar = new Semaphore(0); // Semáforo para despertar al monitor
    static Semaphore semMutex = new Semaphore(1); // Semáforo para acceso exclusivo al monitor
    static Semaphore semSillas = new Semaphore(NUM_SILLAS); // Semáforo para controlar el número de sillas libres
    static Semaphore semAtencion = new Semaphore(0); // Semáforo para atender a un estudiante

    public static void main(String[] args) {
        // Inicia el hilo del monitor
        Thread monitorThread = new Thread(new Monitor());
        Thread[] estudiantes = new Thread[NUM_ESTUDIANTES];

        monitorThread.start();
        for (int i = 0; i < NUM_ESTUDIANTES; i++) {
            estudiantes[i] = new Thread(new Estudiante(i));
            estudiantes[i].start();
        }
    }

    static class Estudiante implements Runnable {
        int id;

        Estudiante(int id) {
            this.id = id;
        }

        public void run() {
            while (true) {
                programar();
                buscarAyuda();
            }
        }

        // Simula el tiempo de programación
        void programar() {
            try {
                System.out.println("Estudiante " + id + " programando");
                Thread.sleep((long) (Math.random() * 3000)); // Simula el tiempo de programación
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Busca ayuda del monitor
        void buscarAyuda() {
            try {
                semMutex.acquire(); // Acceso exclusivo al monitor y sillas

                if (semSillas.tryAcquire()) {
                    // Hay sillas disponibles
                    System.out.println("Estudiante " + id + " se sienta en una silla.");

                    semMutex.release(); // Libera el mutex para que otros estudiantes puedan verificar sillas
                    semDespertar.release(); // Despierta al monitor

                    semAtencion.acquire(); // Espera a que el monitor lo atienda
                    System.out.println("Estudiante " + id + " está siendo atendido por el monitor.");
                    semSillas.release(); // Libera una silla tras la atención
                } else {
                    // No hay sillas disponibles
                    System.out.println("Estudiante " + id + " no encontró sillas, se va a la sala de cómputo.");
                    semMutex.release(); // Libera el mutex si no encontró sillas
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Monitor implements Runnable {
        public void run() {
            while (true) {
                try {
                    semDespertar.acquire(); // Espera que un estudiante lo despierte
                    semMutex.acquire(); // Acceso exclusivo para modificar el estado de las sillas

                    System.out.println("Monitor atendiendo a un estudiante...");
                    Thread.sleep((long) (Math.random() * 2000)); // Simula el tiempo de atención
                    System.out.println("Monitor terminó de atender a un estudiante.");

                    semAtencion.release(); // Notifica al estudiante que ha sido atendido

                    semMutex.release(); // Libera el mutex
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
