import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder output = new StringBuilder();

        while (scanner.hasNextInt()) {
            int n = scanner.nextInt();
            Point[] xs = new Point[n];
            Point[] ys = new Point[n];
            for (int i = 0; i < n; i++) {
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                Point p = new Point();
                p.x = x;
                p.y = y;
                xs[i] = p;
                ys[i] = p;
            }
            MinPerimeter solver = new MinPerimeter();
            double result = solver.solve(xs, ys);
            output.append(String.format("%.3f%n", result));
        }

        System.out.print(output);
    }
}