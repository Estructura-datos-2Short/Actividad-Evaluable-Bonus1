package navegacion;

// Una arista representa una calle dirigida desde una intersección hacia otra.
// El peso indica la distancia o costo de recorrer esa calle.
public class Arista {

    private String destino;
    private int peso;

    // Constructor: recibe el nombre del destino y el peso de la conexión
    public Arista(String destino, int peso) {
        this.destino = destino;
        this.peso = peso;
    }

    public String getDestino() {
        return destino;
    }

    public int getPeso() {
        return peso;
    }

    @Override
    public String toString() {
        return destino + " (" + peso + ")";
    }
}
