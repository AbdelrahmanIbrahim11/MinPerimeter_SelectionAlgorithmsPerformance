import java.util.Random;

public class Randomized_divide_and_conquer {

    public long select (long [] array , int p, int q, int i) {
        int index = p + i - 1 ;
        if (p == q) return  array[index] ;

        int r = Random_Partition(array, p, q) ; // r is the order in terms of that slice only
        
        if (i < r)  return select(array, p, p + r - 2, i ) ; 
        else return select(array, p + r  , q, i - r);

    }

    int Random_Partition(long[] array, int p, int q) {
        Random random = new Random(42);
        int pivot = random.nextInt(q - p + 1) + p ;
        // swap 
        long temp = 0 ; 
        temp = array[pivot] ; 
        array[pivot] = array[q] ;
        array[q] = temp ; 
        
        // the pivot in the end 
        int i = p - 1 ; 
        for(int j = p ; j <= q - 1 ; j ++) {
            if (array[j] < array[q]) {
                i ++ ; 
                temp = array[i] ; 
                array[i] = array[j] ;
                array[j] = temp ; 
            }
        }
        temp = array[i+1] ; 
        array[i+1] = array[q] ;
        array[q] = temp ; 

        return i + 1 - p + 1 ; // the order not the index 
        
    }
    
}
