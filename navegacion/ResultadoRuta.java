package navegacion;

import java.util.ArrayList;

// Esta clase guarda el resultado de ejecutar el algoritmo de Dijkstra.
// Sirve para devolver de forma ordenada el camino encontrado y la distancia total.
public class ResultadoRuta {

    private ArrayList<String> camino;
    private int distancia;
    private boolean existeCamino;

    public ResultadoRuta(ArrayList<String> camino, int distancia, boolean existeCamino) {
        this.camino = camino;
        this.distancia = distancia;
        this.existeCamino = existeCamino;
    }

    public ArrayList<String> getCamino() {
        return camino;
    }

    public int getDistancia() {
        return distancia;
    }

    public boolean existeCamino() {
        return existeCamino;
    }

    // Muestra el resultado de la búsqueda de ruta en consola
    public void mostrarResultado(String origen, String destino) {
        System.out.println("\n--- Resultado de la búsqueda ---");
        if (!existeCamino) {
            System.out.println("No existe una ruta disponible desde " + origen + " hasta " + destino + ".");
        } else {
            System.out.print("Ruta más corta de " + origen + " a " + destino + ":\n");
            System.out.print("  ");
            for (int i = 0; i < camino.size(); i++) {
                System.out.print(camino.get(i));
                if (i < camino.size() - 1) {
                    System.out.print(" -> ");
                }
            }
            System.out.println();
            System.out.println("Distancia total: " + distancia);
        }
        System.out.println("--------------------------------");
    }
}
