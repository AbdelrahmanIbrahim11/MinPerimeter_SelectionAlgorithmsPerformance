public class MinPerimeter {
    public double solve (Point [] xs, Point [] ys) {

        Point obj = new Point() ; 
        obj.sort(xs, 0) ;
        obj.sort(ys, 1) ;

        return  findMin(xs,0 ,xs.length - 1 , ys, 0, ys.length - 1) ; 
    }

    public double findMin (Point[] xs, int x1, int x2, Point[] ys, int y1, int y2) {
        if (x2 - x1 + 1 == 3) {
            return xs[x1].computePerimter(xs[x1+1], xs[x1+2]) ;
        }
        if (x2 - x1 + 1 < 3 ) {
            return Double.MAX_VALUE ;
        }

        int median = (x2 - x1) / 2 + x1 ;
        Point midPoint = xs[median] ;

        Point[] tmp = new Point[y2 - y1 + 1]; 
        for (int i = y1; i <= y2 ; i ++ ) {
            tmp[i - y1] = ys[i] ; 
        }

        int leftCount = median - x1 + 1;
        int c1 = y1;
        int c2 = y1 + leftCount;

        for (int i = x1; i <= median; i++) xs[i].leftHalf = true;
        for (int i = median + 1; i <= x2; i++) xs[i].leftHalf = false;

        for (Point p : tmp) {
        if (p.leftHalf) ys[c1++] = p;
        else ys[c2++] = p;
    }

        // divide step 
        double left = findMin(xs, x1, median, ys, y1, c1 - 1) ; 
        double right = findMin(xs, median + 1, x2, ys, c1, y2) ;

        for (int i = 0; i < tmp.length; i++) {
        ys[y1 + i] = tmp[i];
        } // return the ys as it was 

        double s = Math.min(left , right) ; 

        Point[] strip = new Point[y2 - y1 + 1];
        int strip_size = 0;
        double midx = midPoint.x;

        for (int i = y1; i <= y2; i++) {
        if (Math.abs(ys[i].x - midx) < s) {
            strip[strip_size] = ys[i];
            strip_size ++ ; 
        }
            }


            // s here is the delta which makes the window through the strip s * 2s
        for (int i = 0; i < strip_size; i++) {
            Point pointCheck_i = strip[i];
            for (int j = i + 1; j < strip_size && (strip[j].y - pointCheck_i.y) < s; j++) {
                Point pointCheck_j = strip[j];
                for (int k = j + 1; k < strip_size && (strip[k].y - pointCheck_i.y) < s; k++) {
                    Point pointCheck_k = strip[k];
                    double p = pointCheck_i.computePerimter(pointCheck_j, pointCheck_k);
                    if (p < s) {
                        s = p;
                    }
                }
            }
        }

        return s ;

        
    }
}
