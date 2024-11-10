import java.util.HashMap;
import java.util.List;

public class Grafo {

    private HashMap<Integer, Nodo> nodos = new HashMap<>(); 
    private HashMap<Integer, List<Integer[]>> ejes = new HashMap<>();
    private int d = 0;
    private int superIniciadora = Math.MIN_VALUE;
    private int superEjecutora = Math.MAX_VALUE;

    public Grafo(int d){
        this.d = d;
    }


    public void agregarNodo(Nodo nodo){
        nodos.put(nodo.getId(), nodo);
        configurarEjes(nodo.getId());
    }

    public void configurarEjes(int idNodo){
        Nodo nodo = nodos.get(idNodo);
        for (int id : nodos.keySet()){
            if (id != idNodo && calcularDistancia(nodos.get(idNodo), nodos.get(id)) <= d){
                actualizarLista(idNodo, id, 1); //TODO: Cambiar el peso por la cantidad de pensamientos
            }
        }
    }

    private double calcularDistancia(Nodo nodo1, Nodo nodo2){
        int x1 = nodo1.getX();
        int y1 = nodo1.getY();
        int x2 = nodo2.getX();
        int y2 = nodo2.getY();
        double distancia = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        return distancia;
    }

    private void actualizarLista(int idNodo, int idNodoAdyacente, int peso){
        Nodo nodo1 = nodos.get(idNodo);
        Nodo nodo2 = nodos.get(idNodoAdyacente);

        if (nodo1.getTipo().equals("calculadora") && nodo2.getTipo().equals("calculadora")){
            ejes.get(idNodo).add(crearEje(idNodoAdyacente, peso));
            configurarAntiparalelo(idNodo, idNodoAdyacente);
        } else if (nodo1.getTipo().equals("calculadora") && nodo2.getTipo().equals("iniciadora")){
            ejes.get(idNodoAdyacente).add(crearEje(idNodo, peso));
        } else if (nodo1.getTipo().equals("iniciadora") && nodo2.getTipo().equals("calculadora")){
            ejes.get(idNodo).add(crearEje(idNodoAdyacente, peso));
        } else if (nodo1.getTipo().equals("calculadora") && nodo2.getTipo().equals("ejecutora")){
            ejes.get(idNodo).add(crearEje(idNodoAdyacente, peso));
        } else if (nodo1.getTipo().equals("ejecutora") && nodo2.getTipo().equals("calculadora")){
            ejes.get(idNodoAdyacente).add(crearEje(idNodo, peso));
        }

        if (nodo1.getTipo().equals("iniciadora")){
            ejes.get(superIniciadora).add(crearEje(idNodo, peso));
        } else if (nodo1.getTipo().equals("ejecutora")){
            ejes.get(idNodo).add(crearEje(idNodo, peso));
        }
    }

    private Integer[] crearEje(int idNodo, int peso){
        Integer[] eje = {idNodo, peso};
        return eje;
    }

    private void configurarAntiparalelo(int idNodo, int idNodoAdyacente){
        //TODO: Implementar para que cree el nodo intermedio para el camino antiparalelo
    }
}   
