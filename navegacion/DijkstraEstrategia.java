package navegacion;

import java.util.*;

// Esta clase implementa el algoritmo de Dijkstra usando PriorityQueue.
// Dijkstra encuentra la ruta más corta desde un origen hasta un destino
// en un grafo dirigido y ponderado con pesos no negativos.
public class DijkstraEstrategia implements EstrategiaRuta {

    @Override
    public ResultadoRuta calcularRuta(Grafo grafo, String origen, String destino) {

        // Verificar que los vértices existan en el grafo
        if (!grafo.getListaAdyacencia().containsKey(origen)) {
            System.out.println("El vértice de origen '" + origen + "' no existe en el grafo.");
            return new ResultadoRuta(new ArrayList<>(), 0, false);
        }
        if (!grafo.getListaAdyacencia().containsKey(destino)) {
            System.out.println("El vértice de destino '" + destino + "' no existe en el grafo.");
            return new ResultadoRuta(new ArrayList<>(), 0, false);
        }

        // Mapa de distancias: guarda la distancia mínima conocida desde el origen
        HashMap<String, Integer> distancias = new HashMap<>();

        // Mapa de anteriores: sirve para reconstruir el camino al final
        HashMap<String, String> anteriores = new HashMap<>();

        // Inicializar todas las distancias con un valor muy grande (infinito)
        for (String vertice : grafo.getVertices()) {
            distancias.put(vertice, Integer.MAX_VALUE);
            anteriores.put(vertice, null);
        }

        // La distancia del origen a sí mismo es 0
        distancias.put(origen, 0);

        // PriorityQueue ordenada por distancia acumulada (de menor a mayor)
        // Cada arreglo tiene: [nombre del vértice, distancia acumulada]
        PriorityQueue<int[]> cola = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));

        // Usamos los índices del grafo para trabajar con la cola
        // Guardamos en la cola el índice del vértice y su distancia
        HashMap<String, Integer> indices = grafo.getIndices();
        ArrayList<String> vertices = grafo.getVertices();

        // Agregar el origen a la cola con distancia 0
        cola.add(new int[]{indices.get(origen), 0});

        // Conjunto de vértices ya procesados
        HashSet<String> visitados = new HashSet<>();

        // Proceso principal de Dijkstra
        while (!cola.isEmpty()) {
            int[] actual = cola.poll();
            String nombreActual = vertices.get(actual[0]);

            // Si ya fue procesado, lo saltamos
            if (visitados.contains(nombreActual)) continue;
            visitados.add(nombreActual);

            // Si llegamos al destino, podemos parar
            if (nombreActual.equals(destino)) break;

            // Revisar cada vecino del vértice actual
            ArrayList<Arista> vecinos = grafo.getListaAdyacencia().get(nombreActual);
            if (vecinos == null) continue;

            for (Arista arista : vecinos) {
                String vecino = arista.getDestino();
                int pesoArista = arista.getPeso();

                // Calcular nueva distancia posible hacia el vecino
                int distanciaActual = distancias.get(nombreActual);
                if (distanciaActual == Integer.MAX_VALUE) continue;

                int nuevaDistancia = distanciaActual + pesoArista;

                // Si encontramos un camino más corto, actualizamos
                if (nuevaDistancia < distancias.get(vecino)) {
                    distancias.put(vecino, nuevaDistancia);
                    anteriores.put(vecino, nombreActual);
                    cola.add(new int[]{indices.get(vecino), nuevaDistancia});
                }
            }
        }

        // Verificar si se encontró un camino al destino
        if (distancias.get(destino) == Integer.MAX_VALUE) {
            return new ResultadoRuta(new ArrayList<>(), 0, false);
        }

        // Reconstruir el camino usando el mapa de anteriores
        ArrayList<String> camino = new ArrayList<>();
        String paso = destino;
        while (paso != null) {
            camino.add(0, paso); // agregar al inicio para que quede en orden
            paso = anteriores.get(paso);
        }

        return new ResultadoRuta(camino, distancias.get(destino), true);
    }
}
