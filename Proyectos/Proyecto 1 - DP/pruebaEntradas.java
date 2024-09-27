import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class pruebaEntradas {
    
    public static void main(String[] args) throws Exception {
        String directorio = System.getProperty("user.dir");
        String archivo = directorio + "/Proyectos/Proyecto 1 - DP/P1.in";
        pruebaEntradas instancia = new pruebaEntradas();
        try {
            FileInputStream fis = new FileInputStream(archivo);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            
            String line = br.readLine();
            
            int casos = Integer.parseInt(line);
            
            System.out.println("Hay " + casos + " casos de prueba.");
            for (int caso = 0; caso < casos; caso++) {
                line = br.readLine();
                if (line != null) {
                    String[] dimensiones = line.split(" ");
                    int r = Integer.parseInt(dimensiones[0]);
                    int c = Integer.parseInt(dimensiones[1]);
                    System.out.println("Caso " + (caso + 1) + ": matriz de" + r + " filas y " + c + " columnas.");

                    int[][] matriz = new int[r][c];
                    for (int i = 0; i < r; i++) {
                        line = br.readLine();
                        String[] elementos = line.split(" ");
                        for (int j = 0; j < c; j++) {
                            matriz[i][j] = Integer.parseInt(elementos[j]);
                        }

                        for (int j = 0; j < c; j++) {
                            System.out.print(matriz[i][j] + " ");
                        }
                        System.out.println();
                    }

                }
            }
            br.close();
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}
