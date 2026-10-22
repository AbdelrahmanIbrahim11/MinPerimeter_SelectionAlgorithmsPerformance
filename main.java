import java.util.Random;
import java.util.Arrays;

public class main {
    public static void main(String[] args) {
        Random rand = new Random(42);
    

        Randomized_divide_and_conquer obj = new Randomized_divide_and_conquer();

        long[] array = new long[100];

        for (int j = 0; j < array.length; j++) {
            array[j] = rand.nextInt(1000) + 1;
        }

        long[] sorted = array.clone();
        Arrays.sort(sorted);

        int p = 0;
        int q = array.length - 1;
        int i = 50;

        long result = obj.select(array, p, q, i);

        System.out.println("Original array: " + Arrays.toString(sorted));
        System.out.println("Selected element: " + result);
        System.out.println("Expected element: " + sorted[i - 1]);
    }
}