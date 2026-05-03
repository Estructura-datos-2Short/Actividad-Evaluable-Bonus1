package navegacion;

import java.util.*;
import java.io.*;

// El grafo representa el mapa de la ciudad.
// Cada vértice es una intersección y cada arista es una calle dirigida con peso.
public class Grafo {

    // Lista de adyacencia: para cada vértice guardamos sus aristas de salida
    private HashMap<String, ArrayList<Arista>> listaAdyacencia;

    // Mapa de índices: relaciona el nombre de cada vértice con un número
    // Esto sirve para construir la matriz de adyacencia
    private HashMap<String, Integer> indices;

    // Lista de vértices en orden de inserción
    private ArrayList<String> vertices;

    public Grafo() {
        listaAdyacencia = new HashMap<>();
        indices = new HashMap<>();
        vertices = new ArrayList<>();
    }

    // Agrega una nueva intersección al grafo
    public void agregarVertice(String nombre) {
        if (!listaAdyacencia.containsKey(nombre)) {
            listaAdyacencia.put(nombre, new ArrayList<>());
            indices.put(nombre, vertices.size());
            vertices.add(nombre);
        }
    }

    // Agrega una conexión dirigida desde origen hacia destino con un peso
    public void agregarArista(String origen, String destino, int peso) {
        // Si los vértices no existen, los creamos automáticamente
        agregarVertice(origen);
        agregarVertice(destino);
        listaAdyacencia.get(origen).add(new Arista(destino, peso));
    }

    // Getters necesarios para otras clases
    public HashMap<String, ArrayList<Arista>> getListaAdyacencia() {
        return listaAdyacencia;
    }

    public HashMap<String, Integer> getIndices() {
        return indices;
    }

    public ArrayList<String> getVertices() {
        return vertices;
    }

    // Muestra todas las conexiones del grafo usando la lista de adyacencia
    public void mostrarListaAdyacencia() {
        System.out.println("\n========== Lista de Adyacencia ==========");
        for (String vertice : vertices) {
            System.out.print(vertice + " -> ");
            ArrayList<Arista> aristas = listaAdyacencia.get(vertice);
            if (aristas.isEmpty()) {
                System.out.print("(sin conexiones)");
            } else {
                for (int i = 0; i < aristas.size(); i++) {
                    System.out.print(aristas.get(i));
                    if (i < aristas.size() - 1) System.out.print(", ");
                }
            }
            System.out.println();
        }
        System.out.println("=========================================");
    }

    // Construye y devuelve la matriz de adyacencia como arreglo 2D
    public int[][] obtenerMatrizAdyacencia() {
        int n = vertices.size();
        int[][] matriz = new int[n][n];

        for (String origen : vertices) {
            int fila = indices.get(origen);
            for (Arista arista : listaAdyacencia.get(origen)) {
                int columna = indices.get(arista.getDestino());
                matriz[fila][columna] = arista.getPeso();
            }
        }
        return matriz;
    }

    // Muestra la matriz de adyacencia en consola de forma legible
    public void mostrarMatrizAdyacencia() {
        System.out.println("\n========== Matriz de Adyacencia ==========");
        int n = vertices.size();
        int[][] matriz = obtenerMatrizAdyacencia();

        // Calcular el ancho máximo de los nombres para alinear columnas
        int ancho = 12;

        // Imprimir encabezado de columnas
        System.out.printf("%-" + ancho + "s", "");
        for (String v : vertices) {
            // Abreviamos a 10 caracteres para que quepa en pantalla
            String abrev = v.length() > 9 ? v.substring(0, 9) : v;
            System.out.printf("%-" + ancho + "s", abrev);
        }
        System.out.println();

        // Imprimir separador
        System.out.println("-".repeat(ancho * (n + 1)));

        // Imprimir filas
        for (int i = 0; i < n; i++) {
            String abrev = vertices.get(i).length() > 9 ? vertices.get(i).substring(0, 9) : vertices.get(i);
            System.out.printf("%-" + ancho + "s", abrev);
            for (int j = 0; j < n; j++) {
                System.out.printf("%-" + ancho + "d", matriz[i][j]);
            }
            System.out.println();
        }
        System.out.println("==========================================");
    }

    // Calcula el grado de entrada de un vértice (cuántas aristas llegan a él)
    public int getGradoEntrada(String vertice) {
        int contador = 0;
        for (String origen : vertices) {
            for (Arista arista : listaAdyacencia.get(origen)) {
                if (arista.getDestino().equals(vertice)) {
                    contador++;
                }
            }
        }
        return contador;
    }

    // Calcula el grado de salida de un vértice (cuántas aristas salen de él)
    public int getGradoSalida(String vertice) {
        return listaAdyacencia.get(vertice).size();
    }

    // Muestra el grado de entrada y salida de cada vértice del grafo.
    // El grado de salida indica las calles que salen de una intersección.
    // El grado de entrada indica las calles que llegan a una intersección.
    public void mostrarGrados() {
        System.out.println("\n========== Grados de los Vértices ==========");
        for (String vertice : vertices) {
            int entrada = getGradoEntrada(vertice);
            int salida = getGradoSalida(vertice);
            System.out.println(vertice + " -> Entrada: " + entrada + " | Salida: " + salida);
        }
        System.out.println("============================================");
    }

    // Hace un recorrido BFS desde un vértice de origen.
    // Devuelve el conjunto de vértices que se pueden alcanzar desde ahí.
    public HashSet<String> recorridoDesde(String origen) {
        HashSet<String> visitados = new HashSet<>();
        Queue<String> cola = new LinkedList<>();
        cola.add(origen);
        visitados.add(origen);

        while (!cola.isEmpty()) {
            String actual = cola.poll();
            for (Arista arista : listaAdyacencia.get(actual)) {
                String vecino = arista.getDestino();
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    cola.add(vecino);
                }
            }
        }
        return visitados;
    }

    // Verifica si el grafo dirigido es fuertemente conexo.
    // Un grafo dirigido es fuertemente conexo si desde cada vértice
    // se puede llegar a todos los demás vértices.
    public boolean esFuertementeConexo() {
        for (String vertice : vertices) {
            HashSet<String> alcanzables = recorridoDesde(vertice);
            if (alcanzables.size() != vertices.size()) {
                return false;
            }
        }
        return true;
    }

    // Muestra una visualización sencilla del grafo en consola usando caracteres ASCII
    public void mostrarGrafoAscii() {
        System.out.println("\n========== Visualización del Grafo ==========");
        for (String vertice : vertices) {
            System.out.println(vertice);
            ArrayList<Arista> aristas = listaAdyacencia.get(vertice);
            if (aristas.isEmpty()) {
                System.out.println(" └── (sin conexiones)");
            } else {
                for (int i = 0; i < aristas.size(); i++) {
                    if (i == aristas.size() - 1) {
                        System.out.println(" └── " + aristas.get(i));
                    } else {
                        System.out.println(" ├── " + aristas.get(i));
                    }
                }
            }
            System.out.println();
        }
        System.out.println("=============================================");
    }

    // Carga el grafo desde un archivo de texto con el formato:
    // Origen Destino Peso
    // Devuelve true si pudo cargar el archivo, false si no
    public boolean cargarDesdeArchivo(String rutaArchivo) {
        try {
            File archivo = new File(rutaArchivo);
            if (!archivo.exists()) return false;

            Scanner lector = new Scanner(archivo);
            int lineasLeidas = 0;

            while (lector.hasNextLine()) {
                String linea = lector.nextLine().trim();
                if (linea.isEmpty()) continue; // ignorar líneas vacías

                String[] partes = linea.split("\\s+");
                if (partes.length != 3) continue; // ignorar líneas con formato incorrecto

                String origen = partes[0];
                String destino = partes[1];
                int peso;

                try {
                    peso = Integer.parseInt(partes[2]);
                } catch (NumberFormatException e) {
                    continue; // ignorar líneas con peso inválido
                }

                agregarArista(origen, destino, peso);
                lineasLeidas++;
            }

            lector.close();
            return lineasLeidas > 0;

        } catch (Exception e) {
            return false;
        }
    }

    // Crea el grafo base directamente en código como respaldo,
    // por si no se encuentra el archivo mapa.txt
    public void crearGrafoBase() {
        // 10 intersecciones de ejemplo
        agregarArista("Centro",      "Parque",      4);
        agregarArista("Centro",      "Hospital",    8);
        agregarArista("Parque",      "Universidad", 5);
        agregarArista("Parque",      "Biblioteca",  7);
        agregarArista("Hospital",    "Universidad", 2);
        agregarArista("Hospital",    "Terminal",   10);
        agregarArista("Universidad", "Estacion",    6);
        agregarArista("Estacion",    "Terminal",    4);
        agregarArista("Estacion",    "Estadio",     2);
        agregarArista("Terminal",    "Estadio",     5);
        agregarArista("Biblioteca",  "Museo",       3);
        agregarArista("Biblioteca",  "Alcaldia",    5);
        agregarArista("Museo",       "Alcaldia",    4);
        agregarArista("Alcaldia",    "Centro",      6);
        agregarArista("Estadio",     "Parque",      9);
    }
}
