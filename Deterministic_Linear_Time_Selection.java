public class Deterministic_Linear_Time_Selection {
    double select (double [] array,int p, int q, int i) {
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

        int med =p + 2 ;
        double [] medarray = new  double[k] ;

        for (int j = 0; j < k-1 ; j ++) {
            medarray[j] = array[med] ;
            med = med + 5 ; 
        }
        medarray[k-1] = array[(int) Math.ceil((double) (q - st) / 2) + st] ;

        double median = select(medarray,0 , medarray.length - 1 , (medarray.length+1) / 2) ; 

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

        double temp = 0 ; 
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
            array[m+1] = array[q] ;
            array[q] = temp ;

            // the pivot now is at index m+1
            // and we we need to find the i th element which is the i - 1 element
            
            if (i-1 == m+1) return array[m+1] ;

            if (i-1 < m+1) return select (array, p, m, i) ;
            else return  select(array, m+2 , q, i)  ;

    }

    void naive_sort (double [] array, int p, int q) {
        double temp = 0 ;
        for (int i = p ; i <= q - 1 ; i ++ ) {
            for (int j = i + 1 ; j <= q ; j ++ ) {
                if (array[j] < array[i]) {
                    temp = array[j] ;
                    array[j] = array[i] ;
                    array[i] = temp ;

                }
            }
        }
    }
}