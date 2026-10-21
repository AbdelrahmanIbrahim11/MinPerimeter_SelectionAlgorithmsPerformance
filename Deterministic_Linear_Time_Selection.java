public class Deterministic_Linear_Time_Selection {
    long select (long [] array,int p, int q, int i) {
        // / is integer divison
        // base case of n <= 140 using the bubble sort left 
        if (q - p + 1 <= 140 ) {
            naive_sort(array, p, q);
            return array[i-1] ; 
        }
        
        int size = q - p + 1 ;
        int k = ( size + 4 ) / 5 ; // the number of columns 
        int st = p ;
        int ed = p + 4 ;

        for (int j = 0 ; j < k-1 ; j ++ ) {
            naive_sort(array, st, ed );
            st += 5 ; ed += 5 ;
        }
        naive_sort(array, st, q); // for sorting the last chunck 

        int med = 2 ;
        long [] medarray = new  long[k] ;

        for (int j = 0; j < k-1 ; j ++) {
            medarray[j] = array[med] ;
            med = med + 5 ; 
        }
        medarray[k] = array[(int) Math.ceil((double) (q - st) / 2) + st] ;

        long median = select(medarray,0 , medarray.length - 1 , medarray.length / 2) ; 

        int medianIndexSlice = 0 ; 
        int searchMed = p + 2 ; 

        for(int j = 0 ; j < k-1 ; j ++ ) { // loop through all slices
            if (array[searchMed] == median){
                medianIndexSlice = searchMed ;
                break ; 
            }
            searchMed += 5 ; 
        }
        if (searchMed > st ) medianIndexSlice = (int) Math.ceil((double) (q - st) / 2) + st ;

        // medianIndexSlice is the index of the pivot 

        long temp = 0 ; 
        temp = array[medianIndexSlice]  ;
        array[medianIndexSlice] = array[q] ;
        array[q] = temp ; 

        int m = p - 1 ; 
        for(int j = p ; j < q ; j ++ ) {

            if (array[j] < array[q]) {
                m ++ ; 
                temp = array[m]  ;
                array[m] = array[j] ;
                array[j] = temp ;
            }

        }

            temp = array[m+1]  ;
            array[m+1] = array[array.length-1] ;
            array[array.length-1] = temp ;

            // the pivot now is at index m+1
            // and we we need to find the i th element which is the i - 1 element
            
            if (i-1 == m+1) return array[m+1] ;

            if (i-1 < m+1) return select (array, p, m, i) ;
            else return  select(array, m+2 , q, i - m)  ;

    }

    void naive_sort (long [] array, int p, int q) {
        long temp = 0 ;
        for (int i = 0 ; i < array.length - 1 ; i ++ ) {
            for (int j = i + 1 ; j < array.length ; j ++ ) {
                if (array[j] < array[i]) {
                    temp = array[j] ;
                    array[j] = array[i] ;
                    array[i] = temp ;

                }
            }
        }
    }
}