import java.util.Arrays;

public class Point {
    int x ; 
    int y ;
    boolean leftHalf ; 

    public Point () {
        this.x = 0 ;
        this.y = 0 ;
    }
    
public double computeEuc(Point p1, Point p2) {
    double z1 = Math.pow(p1.x - p2.x, 2);
    double z2 = Math.pow(p1.y - p2.y, 2);

    return Math.sqrt(z1 + z2);
}

public double computePerimter (Point p1, Point p2) {
   double z1 = computeEuc(p1, p2) ; 
   double z2 = computeEuc(p1, this) ; 
   double z3 = computeEuc(p2, this) ; 

   return  z1 + z2 + z3 ; 
}

public void sort (Point [] points, int typeOFsorting ) {
    if (typeOFsorting == 0) {
        Arrays.sort(points, (p1, p2) -> Integer.compare(p1.x, p2.x));
    } else if (typeOFsorting == 1) {
        Arrays.sort(points, (p1, p2) -> Integer.compare(p1.y, p2.y));
    }
}

}
