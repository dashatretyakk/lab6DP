import java.security.SecureRandom;
import java.util.Arrays;

public class Main {

    private static final int[] SIZES = {10, 100, 500, 1000, 2000}; // Масив розмірів матриць для обчислень

    public static void main(String[] args) {
        SecureRandom random = new SecureRandom(); // Ініціалізація генератора випадкових чисел
        long start;
        long end;
        System.out.println("SIZE\t\tSIMPLE\t\tTAPE(2)\t\tTAPE(4)\t\tFOX(2)\t\tFOX(4)\t\tCANNON(2)\t\tCANNON(4)\n"); // Виведення заголовка результатів

        // Цикл для обчислення для різних розмірів матриць
        for (int size : SIZES) {
            int[][] a = new int[size][size];
            int[][] b = new int[size][size];

            // Заповнення матриць випадковими числами
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    a[i][j] = random.nextInt(10); // Заповнення елементів першої матриці випадковими числами від 0 до 9
                    b[i][j] = random.nextInt(10); // Заповнення елементів другої матриці випадковими числами від 0 до 9
                }
            }

            long[] results = new long[7]; // Масив для збереження часу обчислень різних методів

            // Обчислення звичайного (послідовного) множення матриць
            start = System.currentTimeMillis();
            int[][] simple = simpleMultiplying(a, b);
            end = System.currentTimeMillis();
            results[0] = end - start;

            // Обчислення паралельного множення матриць методом стрічкової схеми з різною кількістю потоків
            start = System.currentTimeMillis();
            start = System.currentTimeMillis();
            int[][] tape2 = TapeMultiplying.multiply(a, b, 2);
            end = System.currentTimeMillis();
            results[1] = end - start;
            start = System.currentTimeMillis();
            int[][] tape4 = TapeMultiplying.multiply(a, b, 4);
            end = System.currentTimeMillis();
            results[2] = end - start;
            // Обчислення паралельного множення матриць методом Фокса з різною кількістю потоків
            start = System.currentTimeMillis();
            int[][] fox2 = FoxMultiplying.multiply(a, b, 2);
            end = System.currentTimeMillis();
            results[3] = end - start;
            start = System.currentTimeMillis();
            int[][] fox4 = FoxMultiplying.multiply(a, b, 4);
            end = System.currentTimeMillis();
            results[4] = end - start;
            // Обчислення паралельного множення матриць методом Кеннона з різною кількістю потоків
            start = System.currentTimeMillis();
            int[][] cannon2 = CannonMultiplying.multiply(a, b, 2);
            end = System.currentTimeMillis();
            results[5] = end - start;
            start = System.currentTimeMillis();
            int[][] cannon4 = CannonMultiplying.multiply(a, b, 4);
            end = System.currentTimeMillis();
            results[6] = end - start;

            // Виведення результатів обчислень
            System.out.println(Arrays.deepEquals(simple, tape2)
                    + " " + Arrays.deepEquals(simple, fox2)
                    + " " + Arrays.deepEquals(simple, cannon2));

            System.out.print(size);
            for (long r : results) {
                System.out.print("\t\t" + r + " ms");
            }
            System.out.println();

        }

    }

    // Функція для звичайного (послідовного) множення матриць
    public static int[][] simpleMultiplying(int[][] a, int[][] b) {
        int[][] res = new int[a.length][a.length];

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                res[i][j] = 0;
                for (int k = 0; k < a.length; k++) {
                    res[i][j] += a[i][k] * b[k][j];
                }
            }
        }

        return res;
    }
}
