import java.util.Arrays;
public class Naive_Method {

    public double selcet (double[] array, int k) {
        Arrays.sort(array) ; 
        return array[k-1] ; 
    }
}