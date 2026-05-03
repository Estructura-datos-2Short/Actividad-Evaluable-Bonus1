package navegacion;

// Interfaz que representa el patrón Strategy para algoritmos de búsqueda de rutas.
// Si en el futuro se quisiera usar otro algoritmo como Bellman-Ford,
// solo habría que crear una clase nueva que implemente esta interfaz.
public interface EstrategiaRuta {

    ResultadoRuta calcularRuta(Grafo grafo, String origen, String destino);
}
