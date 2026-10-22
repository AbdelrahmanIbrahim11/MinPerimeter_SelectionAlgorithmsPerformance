import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;

public class Benchmark {
    public static void main(String[] args) {
        int[] sizes = {10, 100, 1000, 10000, 100000, 1000000, 10000000};
        int trials = 5;

        Randomized_divide_and_conquer randomized = new Randomized_divide_and_conquer();
        Deterministic_Linear_Time_Selection deterministic = new Deterministic_Linear_Time_Selection();
        Naive_Method naive = new Naive_Method();
        Random rand = new Random();

        try (FileWriter writer = new FileWriter("results.csv")) {
            writer.write("size,randomized_ms,deterministic_ms,naive_ms\n");

            for (int w = 0; w < 1000; w++) {
                double[] warm = new double[100];
                for (int j = 0; j < 100; j++) warm[j] = rand.nextInt(1000) + 1;
                randomized.select(warm.clone(), 0, 99, 50);
                deterministic.select(warm.clone(), 0, 99, 50);
                naive.selcet(warm.clone(), 50);
            }

            for (int n : sizes) {
                long randTotal = 0;
                long detTotal = 0;
                long naiveTotal = 0;

                for (int t = 0; t < trials; t++) {
                    double[] base = new double[n];
                    for (int j = 0; j < n; j++) {
                        base[j] = rand.nextInt(1000000) + 1;
                    }

                    int target = n / 2;

                    double[] arr1 = base.clone();
                    long start1 = System.nanoTime();
                    randomized.select(arr1, 0, n - 1, target);
                    randTotal += System.nanoTime() - start1;

                    double[] arr2 = base.clone();
                    long start2 = System.nanoTime();
                    deterministic.select(arr2, 0, n - 1, target);
                    detTotal += System.nanoTime() - start2;

                    double[] arr3 = base.clone();
                    long start3 = System.nanoTime();
                    naive.selcet(arr3, target);
                    naiveTotal += System.nanoTime() - start3;
                }

                double randAvgMs = (randTotal / (double) trials) / 1_000_000.0;
                double detAvgMs = (detTotal / (double) trials) / 1_000_000.0;
                double naiveAvgMs = (naiveTotal / (double) trials) / 1_000_000.0;

                String line = n + "," + randAvgMs + "," + detAvgMs + "," + naiveAvgMs + "\n";
                writer.write(line);
                writer.flush();

                System.out.println("n=" + n + " done: randomized=" + randAvgMs
                        + "ms, deterministic=" + detAvgMs + "ms, naive=" + naiveAvgMs + "ms");
            }

            System.out.println("Results written to results.csv");

        } catch (IOException e) {
            System.err.println("Failed to write results.csv: " + e.getMessage());
        }
    }
}