# Sistema de navegación urbana usando Dijkstra

Materia: Estructura de Datos  
Estudiante: Larry Davi Botia Contreras
Docente: Endi Jainner Romero

---

## Descripción

Este proyecto representa un mapa urbano mediante un grafo dirigido y ponderado. Permite calcular la ruta más corta entre dos intersecciones usando el algoritmo de Dijkstra. También muestra propiedades del grafo como los grados de entrada y salida de cada vértice, y determina si el grafo es fuertemente conexo.

---

## Estructura del proyecto

```
SistemaNavegacion/
│
├── data/
│   └── mapa.txt          <- Archivo con las conexiones del grafo
│
├── src/
│   └── navegacion/
│       ├── Arista.java            <- Representa una calle dirigida con peso
│       ├── ResultadoRuta.java     <- Guarda el resultado de Dijkstra
│       ├── EstrategiaRuta.java    <- Interfaz del patrón Strategy
│       ├── DijkstraEstrategia.java <- Implementación del algoritmo Dijkstra
│       ├── Grafo.java             <- Estructura del grafo y sus métodos
│       └── Principal.java         <- Clase principal con menú
│
└── README.md
```

---

## Estructuras usadas

- Grafo dirigido y ponderado
- Lista de adyacencia (`HashMap<String, ArrayList<Arista>>`)
- Matriz de adyacencia (arreglo 2D)
- `PriorityQueue` para el algoritmo de Dijkstra
- `HashMap` para mapear nombres a índices
- `ArrayList` para reconstruir el camino
- Patrón Strategy para separar el algoritmo de la clase principal
- Archivo de texto para cargar el grafo

---

## Archivo de entrada

El grafo se carga desde:

```
data/mapa.txt
```

Formato de cada línea:

```
Origen Destino Peso
```

Ejemplo:

```
Centro Parque 4
Centro Hospital 8
Parque Universidad 5
```

Si el archivo no existe, el programa carga automáticamente un grafo base definido en código.

---

## Funcionalidades

- Cargar grafo desde archivo de texto
- Mostrar lista de adyacencia
- Mostrar matriz de adyacencia
- Calcular ruta más corta con Dijkstra
- Mostrar camino completo y distancia total
- Detectar si no existe camino entre dos vértices
- Mostrar grados de entrada y salida de cada vértice
- Verificar si el grafo es fuertemente conexo
- Visualizar el grafo con arte ASCII en consola

---

## Cómo compilar

Desde la raíz del proyecto:

```
javac -d bin src/navegacion/*.java
```

---

## Cómo ejecutar

```
java -cp bin navegacion.Principal
```

---

## Ejemplos de búsqueda para el video

1. **Centro → Estadio** — Ruta larga con varios pasos intermedios  
2. **Hospital → Terminal** — Ruta directa y una alternativa más larga  
3. **Biblioteca → Alcaldia** — Ruta corta existente

---

## Decisiones de implementación

Se usó **lista de adyacencia** porque permite representar fácilmente las conexiones de cada intersección y es eficiente en grafos con pocas aristas por vértice.

Se generó también la **matriz de adyacencia** para cumplir con la representación solicitada y poder visualizar las conexiones entre todos los vértices.

Se usó **PriorityQueue** en Dijkstra para seleccionar siempre el vértice con menor distancia acumulada de forma eficiente.

Se aplicó el **patrón Strategy** de forma sencilla para separar el algoritmo de búsqueda de la clase principal. Así, si en el futuro se quisiera implementar otro algoritmo como Bellman-Ford, solo habría que crear una clase nueva que implemente la interfaz `EstrategiaRuta`.
