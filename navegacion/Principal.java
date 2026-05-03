package navegacion;

import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Crear el grafo
        Grafo grafo = new Grafo();

        // Intentar cargar desde archivo, si falla usar el grafo base
        System.out.println("==========================================");
        System.out.println("  Sistema de Navegación Urbana - Dijkstra");
        System.out.println("==========================================");

        boolean cargado = grafo.cargarDesdeArchivo("data/mapa.txt");
        if (cargado) {
            System.out.println("Grafo cargado desde archivo.");
        } else {
            grafo.crearGrafoBase();
            System.out.println("No se encontró el archivo. Se cargó un grafo base definido en código.");
        }

        // Crear la estrategia usando el patrón Strategy
        // Si en el futuro se quisiera usar otro algoritmo, solo se cambia esta línea
        EstrategiaRuta estrategia = new DijkstraEstrategia();

        // Menú principal
        int opcion = 0;
        do {
            System.out.println("\n========== MENÚ PRINCIPAL ==========");
            System.out.println("1. Mostrar lista de adyacencia");
            System.out.println("2. Mostrar matriz de adyacencia");
            System.out.println("3. Mostrar grados de entrada y salida");
            System.out.println("4. Verificar si el grafo es fuertemente conexo");
            System.out.println("5. Buscar ruta más corta");
            System.out.println("6. Mostrar grafo en consola (ASCII)");
            System.out.println("7. Ejecutar tres búsquedas de prueba");
            System.out.println("8. Salir");
            System.out.println("=====================================");
            System.out.print("Elige una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1:
                    grafo.mostrarListaAdyacencia();
                    break;

                case 2:
                    grafo.mostrarMatrizAdyacencia();
                    break;

                case 3:
                    grafo.mostrarGrados();
                    break;

                case 4:
                    System.out.println();
                    if (grafo.esFuertementeConexo()) {
                        System.out.println("El grafo es fuertemente conexo.");
                    } else {
                        System.out.println("El grafo no es fuertemente conexo.");
                    }
                    break;

                case 5:
                    System.out.print("Ingresa el origen: ");
                    String origen = scanner.nextLine().trim();
                    System.out.print("Ingresa el destino: ");
                    String destino = scanner.nextLine().trim();

                    ResultadoRuta resultado = estrategia.calcularRuta(grafo, origen, destino);
                    resultado.mostrarResultado(origen, destino);
                    break;

                case 6:
                    grafo.mostrarGrafoAscii();
                    break;

                case 7:
                    ejecutarBusquedasDePrueba(grafo, estrategia);
                    break;

                case 8:
                    System.out.println("Saliendo del sistema. ¡Hasta luego!");
                    break;

                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
            }

        } while (opcion != 8);

        scanner.close();
    }

    // Ejecuta tres búsquedas de ejemplo con resultados diferentes
    // para demostrar el funcionamiento del algoritmo
    private static void ejecutarBusquedasDePrueba(Grafo grafo, EstrategiaRuta estrategia) {
        System.out.println("\n========================================");
        System.out.println("       Tres búsquedas de prueba         ");
        System.out.println("========================================");

        // Búsqueda 1: ruta larga con varios pasos
        System.out.println("\nBúsqueda 1: Centro → Estadio");
        ResultadoRuta r1 = estrategia.calcularRuta(grafo, "Centro", "Estadio");
        r1.mostrarResultado("Centro", "Estadio");

        // Búsqueda 2: ruta directa conocida
        System.out.println("\nBúsqueda 2: Hospital → Terminal");
        ResultadoRuta r2 = estrategia.calcularRuta(grafo, "Hospital", "Terminal");
        r2.mostrarResultado("Hospital", "Terminal");

        // Búsqueda 3: puede no existir camino dependiendo del grafo
        System.out.println("\nBúsqueda 3: Biblioteca → Alcaldia");
        ResultadoRuta r3 = estrategia.calcularRuta(grafo, "Biblioteca", "Alcaldia");
        r3.mostrarResultado("Biblioteca", "Alcaldia");

        System.out.println("\n========================================");
    }
}
