import java.util.BitSet;
import java.util.Random;

public class BitComparison {

    // Clase para manejar arreglos de bits con binarios
    static class BitArray {
        long high; // Primeros 64 bits
        long low;  // Últimos 36 bits (se usan solo 100 bits)

        public BitArray(long high, long low) {
            this.high = high;
            this.low = low;
        }

        public boolean intersects(BitArray other) {
            return (this.high & other.high) != 0 || (this.low & other.low) != 0;
        }
    }

    // Método para generar datos aleatorios como BitArray
    static BitArray[] generateBitArrayData(int size) {
        Random random = new Random();
        BitArray[] data = new BitArray[size];
        for (int i = 0; i < size; i++) {
            long high = random.nextLong();
            long low = random.nextLong();
            data[i] = new BitArray(high, low);
        }
        return data;
    }

    // Método para generar datos aleatorios como BitSet
    static BitSet[] generateBitSetData(int size, int bitSize) {
        Random random = new Random();
        BitSet[] data = new BitSet[size];
        for (int i = 0; i < size; i++) {
            BitSet bitSet = new BitSet(bitSize);
            for (int j = 0; j < bitSize; j++) {
                if (random.nextBoolean()) {
                    bitSet.set(j);
                }
            }
            data[i] = bitSet;
        }
        return data;
    }

    // Prueba de intersección usando BitArray
    static long testBitArrayIntersects(BitArray[] data) {
        long count = 0;
        for (int i = 0; i < data.length; i++) {
            for (int j = i + 1; j < data.length; j++) { // Evitar repeticiones
                if (data[i].intersects(data[j])) {
                    count++;
                }
            }
        }
        return count;
    }

    // Prueba de intersección usando BitSet
    static long testBitSetIntersects(BitSet[] data) {
        long count = 0;
        for (int i = 0; i < data.length; i++) {
            for (int j = i + 1; j < data.length; j++) { // Evitar repeticiones
                if (data[i].intersects(data[j])) {
                    count++;
                }
            }
        }
        return count;
    }

    // Método principal para comparar tiempos
    public static void main(String[] args) {
        int size = 500000; // Reducir para pruebas iniciales; aumentar según hardware
        int bitSize = 100; // 100 bits en los datos

        // Generar datos
        System.out.println("Generating data...");
        BitArray[] bitArrayData = generateBitArrayData(size);
        BitSet[] bitSetData = generateBitSetData(size, bitSize);

        // Medir tiempo para BitArray
        System.out.println("Testing BitArray...");
        long start = System.nanoTime();
        long bitArrayCount = testBitArrayIntersects(bitArrayData);
        long bitArrayTime = System.nanoTime() - start;
        System.out.println("BitArray Count: " + bitArrayCount);
        System.out.println("BitArray Time: " + (bitArrayTime / 1e9) + " seconds");

        // Medir tiempo para BitSet
        System.out.println("Testing BitSet...");
        start = System.nanoTime();
        long bitSetCount = testBitSetIntersects(bitSetData);
        long bitSetTime = System.nanoTime() - start;
        System.out.println("BitSet Count: " + bitSetCount);
        System.out.println("BitSet Time: " + (bitSetTime / 1e9) + " seconds");

        // Comparación
        System.out.println("Comparison:");
        System.out.println("BitArray is " + (bitSetTime / (double) bitArrayTime) + " times faster/slower than BitSet.");
    }
}
