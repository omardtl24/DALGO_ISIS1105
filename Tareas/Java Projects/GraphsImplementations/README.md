# Instrucciones para Ejecutar el Código de Algoritmos de Grafos en Java

Este proyecto contiene una implementación de varios algoritmos de grafos en el paquete `uniandes.algorithms.graphs`, que se pueden ejecutar desde la línea de comandos. A continuación se explican cómo ejecutar el código y los diferentes casos que cubre.

## Casos Cubiertos

El código cubre los siguientes casos de uso relacionados con algoritmos de grafos:

1. **Cálculo de Costos Mínimos** (`minCost`)
   - Acciones disponibles:
     - `Dijkstra`: Algoritmo de Dijkstra para encontrar el costo mínimo desde un nodo origen.
     - `BellmanFord`: Algoritmo de Bellman-Ford para calcular el costo mínimo, manejando pesos negativos.
     - `FloydWarshall`: Algoritmo de Floyd-Warshall para encontrar costos mínimos entre todos los pares de nodos.

2. **Encontrar Componentes Conectados** (`components`)
   - Acciones disponibles:
     - `BFS`: Algoritmo de búsqueda en anchura (BFS) para identificar componentes conectados en un grafo no dirigido.

3. **Resolución de Problemas Específicos** (`problems`)
   - Acciones disponibles:
     - `CityCosts`: Calcula el árbol de expansión mínima (MST) usando el algoritmo de Kruskal.
     - `BooksDelivery`: Resuelve el problema de entrega de libros usando el algoritmo de flujo máximo (Edmonds-Karp).

## Ejecución del Código

El código se ejecuta desde la línea de comandos con los siguientes parámetros:

1. Para el caso de los programas que reciben un solo argumento (Es decir todos los que pertenecen al modo `minCost`, `components` y del modo `problems` solamente el asociado a la acción `cityCosts`) se debe escribir el comando de la siguiente manera:

```bash
java uniandes.algorithms.graphs.GraphImplementations <modo> <acción> <archivo_entrada> <archivo_salida> <imprimir_resultados>
```
1. Para el caso del programa que reciben dos argumento (Es decir unicamente en el modo `problems` con acción `BooksDelivery`) se debe realizar de la siguiente manera:

```bash
java uniandes.algorithms.graphs.GraphImplementations <modo> <acción> <archivo_entrada1> <archivo_entrada2> <archivo_salida> <imprimir_resultados>
```

Para el caso del problema de dos entradas. La primera entrada debe ser un archivo con las capacidades de las bodegas; y la segunda entrada debe contener la información de los buses.

Adicionalmente, en la carpeta `data` se pueden encontrar algunos ejemplos de las entradas y salidas de este proyecto.

En el archivo `Tarea04_Grafos.ipynb` se muestra los comandos de ejecución para las entradas y salidas adjuntas.

### Ejemplos Adicionales de Ejecución

Para la parte 1:

```bash
java uniandes.algorithms.graphs.GraphImplementations minCost Dijkstra inputGraph.txt outputResults.txt 1
```

Para la parte 2:

```bash
java uniandes.algorithms.graphs.GraphImplementations components BFS inputGraph.txt outputResults.txt 1
```

Para la parte 3:

```bash
java uniandes.algorithms.graphs.GraphImplementations problems CityCosts inputGraph.txt outputResults.txt 1
```

Para la parte 4:

```bash
java uniandes.algorithms.graphs.GraphImplementations problems BooksDelivery inputGraph1.txt inputGraph2.txt outputResults.txt 1
```

